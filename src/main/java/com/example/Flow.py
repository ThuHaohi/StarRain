import pyshark
import pandas as pd
import numpy as np
from collections import defaultdict
import os

doh_servers = {
    "1.1.1.1",
    "8.8.8.8",
    "9.9.9.9",
    # ... thêm IP server DoH bạn có
}

# Load PCAP
pcap_path = r'D:\haro\IT_project\StarRainGit\StarRain\src\main\resources\tshark\output\chrome_doh.pcap'
cap = pyshark.FileCapture(pcap_path, only_summaries=False)


# Lưu flow theo 5-tuple nhưng gom flow 2 chiều chung lại (bi-directional flow)
# key chuẩn: tuple (ip1, ip2, sport, dport, proto) theo thứ tự ip1 < ip2 để gom cả 2 chiều
flows = defaultdict(list)

def normalize_flow_key(src, dst, sport, dport, proto):
    # Chuẩn hóa 5-tuple để flow 2 chiều cùng key
    if src < dst:
        return (src, dst, sport, dport, proto), 'forward'
    else:
        return (dst, src, dport, sport, proto), 'backward'

def get_flow_key(pkt):
    try:
        proto = pkt.transport_layer
        src = pkt.ip.src
        dst = pkt.ip.dst
        sport = pkt[pkt.transport_layer].srcport
        dport = pkt[pkt.transport_layer].dstport
        return normalize_flow_key(src, dst, sport, dport, proto)
    except:
        return None, None

# Tách các packet thành flow 2 chiều
for pkt in cap:
    key, direction = get_flow_key(pkt)
    if key:
        timestamp = float(pkt.sniff_time.timestamp())
        length = int(pkt.length)
        flows[key].append((timestamp, length, direction))

# Hàm tính burst và pause (threshold 10ms)
def compute_bursts_pauses(timestamps, threshold=0.01):
    if len(timestamps) < 2:
        return 0, 0, 0
    ipds = np.diff(timestamps)
    bursts = 0
    pauses = 0
    in_burst = False
    for ipd in ipds:
        if ipd <= threshold:
            if not in_burst:
                bursts += 1
                in_burst = True
        else:
            if in_burst:
                pauses += 1
                in_burst = False
    ratio = bursts / pauses if pauses > 0 else bursts
    return bursts, pauses, ratio

# Hàm tính autocorrelation đơn giản trên kích thước gói (delay cũng có thể làm)
def compute_autocorrelation(sizes):
    if len(sizes) < 2:
        return 0
    sizes = np.array(sizes) - np.mean(sizes)
    corr = np.correlate(sizes, sizes, mode='full')
    mid = len(corr) // 2
    # Trả về autocorrelation tại lag 1 (vị trí mid + 1)
    return corr[mid+1] / corr[mid] if corr[mid] != 0 else 0

# Hàm tính symmetry từng phần 1/3 flow dựa trên tổng kích thước incoming và outgoing
def compute_symmetry(timestamps, sizes, directions):
    if len(timestamps) < 3:
        return 0,0,0
    total_len = timestamps[-1] - timestamps[0]
    thirds = [timestamps[0] + total_len/3, timestamps[0] + 2*total_len/3]

    def symmetry_in_part(start, end):
        inc = 0
        out = 0
        for t,s,d in zip(timestamps, sizes, directions):
            if start <= t < end:
                if d == 'forward':
                    out += s
                else:
                    inc += s
        # Để tránh chia 0
        if (inc + out) == 0:
            return 0
        return abs(inc - out) / (inc + out)

    s1 = symmetry_in_part(timestamps[0], thirds[0])
    s2 = symmetry_in_part(thirds[0], thirds[1])
    s3 = symmetry_in_part(thirds[1], timestamps[-1])
    return s1, s2, s3

# Trích xuất đặc trưng từ mỗi flow
def extract_features(flow_key, packets):
    packets.sort(key=lambda x: x[0])  # sắp xếp theo thời gian
    timestamps = [x[0] for x in packets]
    sizes = [x[1] for x in packets]
    directions = [x[2] for x in packets]

    # Phân loại kích thước gói theo chiều
    incoming_sizes = [s for s, d in zip(sizes, directions) if d == 'backward']
    outgoing_sizes = [s for s, d in zip(sizes, directions) if d == 'forward']

    ipds = np.diff(timestamps)
    if len(ipds) == 0: ipds = [0]

    bursts, pauses, burst_pause_ratio = compute_bursts_pauses(timestamps)
    autocorr = compute_autocorrelation(sizes)
    sym1, sym2, sym3 = compute_symmetry(timestamps, sizes, directions)

    features = {
        'Duration': timestamps[-1] - timestamps[0] if len(timestamps) > 1 else 0,
        'Min_IPD': np.min(ipds),
        'Max_IPD': np.max(ipds),
        'Avg_IPD': np.mean(ipds),
        'Var_In_Size': np.var(incoming_sizes) if incoming_sizes else 0,
        'Var_Out_Size': np.var(outgoing_sizes) if outgoing_sizes else 0,
        'Avg_In_Size': np.mean(incoming_sizes) if incoming_sizes else 0,
        'Avg_Out_Size': np.mean(outgoing_sizes) if outgoing_sizes else 0,
        'Median_In_Size': np.median(incoming_sizes) if incoming_sizes else 0,
        'Median_Out_Size': np.median(outgoing_sizes) if outgoing_sizes else 0,
        'Bytes_Ratio': sum(incoming_sizes) / sum(outgoing_sizes) if sum(outgoing_sizes) > 0 else 0,
        'Packets_Ratio': len(incoming_sizes) / len(outgoing_sizes) if len(outgoing_sizes) > 0 else 0,
        'Bursts': bursts,
        'Pauses': pauses,
        'Burst_Pause_Ratio': burst_pause_ratio,
        'Autocorrelation': autocorr,
        'Symmetry_1st': sym1,
        'Symmetry_2nd': sym2,
        'Symmetry_3rd': sym3,
        'Label': None,
        'Data_Source': 'chrome' #có thể thay đổi
    }

    # Gán label: nếu IP server (địa chỉ IP thứ 2 trong key) nằm trong doh_servers
    if flow_key[4] == 'TCP' and flow_key[3] == '443':
        features['Label'] = 1
    else:
        features['Label'] = 0

    return features

# Tạo bảng dữ liệu
records = []
for flow_key, packets in flows.items():
    features = extract_features(flow_key, packets)
    records.append(features)

df = pd.DataFrame(records)
df.to_csv("flows.csv", index=False)
print("Done, saved to flows.csv")
