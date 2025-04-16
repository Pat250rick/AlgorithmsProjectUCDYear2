import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("sortingTimes4.csv")
df = df.astype({
    'n': int,
    'k': int,
    'count_time': float,
    'aru_time': float,
    'count_space_bytes': int,
    'aru_space_bytes': int
})

n_values = sorted(df['n'].unique())

for n in n_values:
    subset = df[df['n'] == n]

    plt.figure(figsize=(10, 6))
    plt.plot(subset['k'], subset['count_time'], label='Counting Sort', marker='o', color='blue')
    plt.plot(subset['k'], subset['aru_time'], label='ARU Counting Sort', marker='s', color='green')
    plt.xscale('log')
    plt.xlabel("Maximum Value of k")
    plt.ylabel("Time (seconds)")
    plt.title(f"Time Complexity Comparison for N = {n}")
    plt.legend()
    plt.tight_layout()
    plt.savefig(f"time_for_n{n}.png")
    plt.show()

    plt.figure(figsize=(10, 6))
    plt.plot(subset['k'], subset['count_space_bytes'], label='Counting Sort', marker='o', color='blue')
    plt.plot(subset['k'], subset['aru_space_bytes'], label='ARU Counting Sort', marker='s', color='green')
    plt.xscale('log')
    plt.xlabel("Maximum Value of k")
    plt.ylabel("Estimated Memory Usage (Bytes)")
    plt.title(f"Space Complexity Comparison for n = {n}")
    plt.legend()
    plt.tight_layout()
    plt.savefig(f"space_for_n{n}.png")
    plt.show()
