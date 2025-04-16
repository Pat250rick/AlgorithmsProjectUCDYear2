import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SortingAlgorithm countSort = SortingAlgorithms::countingSort;
        SortingAlgorithm ARUCountSort = SortingAlgorithms::ARUCountingSort;

        int[] nValues = {1000, 10000, 100000, 1000000};
        int[] kExponents = {3, 4, 5, 6, 7, 8, 9};
        int numRepeats = 5;

        try (FileWriter outputFile = new FileWriter("sortingTimes3.csv")) {
            outputFile.write("n,k,count_time,aru_time,count_space_bytes,aru_space_bytes\n");

            for (int n : nValues) {
                for (int exp : kExponents) {
                    int k = (int) Math.pow(10, exp);
                    if (k < n) continue;

                    double totalCountTime = 0;
                    double totalARUTime = 0;

                    for (int i = 0; i < numRepeats; i++) {
                        totalCountTime += benchmarkSort(countSort, n, k);
                        totalARUTime += benchmarkSort(ARUCountSort, n, k);
                    }

                    double countSeconds = totalCountTime / (numRepeats * 1e9);
                    double aruSeconds = totalARUTime / (numRepeats * 1e9);

                    int countSpaceBytes = 4 * (2 * n + k);
                    int aruSpaceBytes = 4 * (2 * n + (int) (2 * Math.sqrt(k)));

                    System.out.printf("n=%d, k=%d -> count: %.6fs, aru: %.6fs%n", n, k, countSeconds, aruSeconds);

                    outputFile.write(n + "," + k + "," + countSeconds + "," + aruSeconds + "," + countSpaceBytes + "," + aruSpaceBytes + "\n");
                    outputFile.flush();
                }
            }

            System.out.println("Finished generating sortingTimes3.csv");

        } catch (IOException e) {
            throw new RuntimeException("File failed to open/write", e);
        }

        System.out.println("Test finished");
    }

    public static long benchmarkSort(SortingAlgorithm sort, int l, int k) {
        Random rand = new Random();
        int[] test = new int[l];
        int max = 0;
        for (int i = 0; i < l; i++) {
            test[i] = rand.nextInt(k);
            if (test[i] > max) {
                max = test[i];
            }
        }

        long start = System.nanoTime();
        sort.sort(test, max);
        return System.nanoTime() - start;
    }
}
