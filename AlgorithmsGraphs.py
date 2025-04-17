import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import cm
import numpy as np

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
""""
    plt.figure(figsize=(10, 6))
    plt.plot(subset['k'], subset['count_time'], label='Counting Sort', color='blue')
    plt.plot(subset['k'], subset['aru_time'], label='ARU Counting Sort', color='green')
    plt.xscale('log')
    plt.xlabel("Maximum Value of k")
    plt.ylabel("Time seconds (log 10)")
    plt.yscale('log')
    plt.title(f"Time Complexity Comparison for N = {n}")
    plt.legend()
    plt.tight_layout()
    plt.savefig(f"time_for_n{n}.png")
    plt.show()

    plt.figure(figsize=(10, 6))
    plt.plot(subset['k'], subset['count_space_bytes'], label='Counting Sort', color='blue')
    plt.plot(subset['k'], subset['aru_space_bytes'], label='ARU Counting Sort', color='green')
    plt.xscale('log')
    plt.xlabel("Maximum Value of k")
    plt.ylabel("Estimated Memory Usage (Bytes)")
    plt.yscale('log')
    plt.title(f"Space Complexity Comparison for n = {n}")
    plt.legend()
    plt.tight_layout()
    plt.savefig(f"space_for_n{n}.png")
    plt.show()
"""

plt.figure(figsize=(16, 7))

plt.subplot(1, 2, 1)
sizes = df['count_space_bytes'] / 1e5
colors = df['count_time']

sc1 = plt.scatter(df['k'], df['n'], s=sizes, c=colors, cmap='viridis', alpha=0.7, edgecolors='k')
plt.xscale('log')
plt.yscale('log')
plt.xlabel('Maximum Value of k (log scale)')
plt.ylabel('Input Size n (log scale)')
plt.title('Counting Sort - Bubble Plot')
plt.colorbar(sc1, label='Time (s)')

plt.subplot(1, 2, 2)
sizes = df['aru_space_bytes'] / 1e5
colors = df['aru_time']

sc2 = plt.scatter(df['k'], df['n'], s=sizes, c=colors, cmap='plasma', alpha=0.7, edgecolors='k')
plt.xscale('log')
plt.yscale('log')
plt.xlabel('Maximum Value of k (log scale)')
plt.title('ARU Counting Sort - Bubble Plot')
plt.colorbar(sc2, label='Time (s)')

plt.tight_layout()
plt.savefig("bubble_plot_comparison.png")
plt.show()

df['log_n'] = np.log10(df['n'])
df['log_k'] = np.log10(df['k'])
df['log_count_time'] = np.log10(df['count_time'])
df['log_aru_time'] = np.log10(df['aru_time'])

pivot_count = df.pivot(index='log_n', columns='log_k', values='log_count_time')
pivot_aru = df.pivot(index='log_n', columns='log_k', values='log_aru_time')

X_count, Y_count = np.meshgrid(pivot_count.columns, pivot_count.index)
Z_count = pivot_count.values

X_aru, Y_aru = np.meshgrid(pivot_aru.columns, pivot_aru.index)
Z_aru = pivot_aru.values

plt.figure(figsize=(16, 7))

plt.subplot(1, 2, 1)
cs1 = plt.contourf(X_count, Y_count, Z_count, cmap='viridis')
plt.colorbar(cs1, label='log10(Time in seconds)')
plt.xlabel('log10(k)')
plt.ylabel('log10(n)')
plt.title('Counting Sort - Time Contour')

plt.subplot(1, 2, 2)
cs2 = plt.contourf(X_aru, Y_aru, Z_aru, cmap='plasma')
plt.colorbar(cs2, label='log10(Time in seconds)')
plt.xlabel('log10(k)')
plt.ylabel('log10(n)')
plt.title('ARU Counting Sort - Time Contour')

plt.tight_layout()
plt.savefig("contour_plot_comparison.png")
plt.show()
