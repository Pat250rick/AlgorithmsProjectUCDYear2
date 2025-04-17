import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SortingAlgorithm countSort = SortingAlgorithms::countingSort;
        SortingAlgorithm ARUCountSort = SortingAlgorithms::ARUCountingSort;
        SortingAlgorithm quickSort = SortingAlgorithms::quickSort;
        SortingAlgorithm mergeSort = SortingAlgorithms::mergeSort;
        SortingAlgorithm radixSort = SortingAlgorithms::radixSort;

        //used for getting fixed values for bar charts
        int[] nValues = {1000, 5000,10000, 50000,100000, 500000,1000000};
        double[] kExponents = {3,3.5, 4,4.5, 5,5.5, 6,6.5, 7,7.5, 8,8.5, 9};
        int numRepeats = 15;

        try (FileWriter outputFile = new FileWriter("sortingTimes5.csv")) {
            outputFile.write("n,k,count_time,aru_time,quick_time,merge_time,radix_time,count_space_bytes,aru_space_bytes\n");

//            used for getting random values for other graphs
//            for (int n = 5000; n <= 1000000; n+=(1000000-5000)/30) {
//                for (double exp = 3; exp <= 8; exp+=(double)(8-3)/(double)30 ){
//                    int k = (int) Math.pow(10, exp);
//                    if (k < n) continue;

            //used for getting fixed values for bar chart
            for (int n : nValues) {
                for (double exp : kExponents) {
                    int k = (int) Math.pow(10, exp);
                    if (k < n) continue;


                    double totalCountTime = 0;
                    double totalARUTime = 0;
                    double totalQuickTime = 0;
                    double totalMergeTime = 0;
                    double totalRadixTime = 0;

                    for (int i = 0; i < numRepeats; i++) {
                        totalCountTime += benchmarkSort(countSort, n, k);
                        totalARUTime += benchmarkSort(ARUCountSort, n, k);
                        totalQuickTime += benchmarkSort(quickSort, n ,k);
                        totalMergeTime += benchmarkSort(mergeSort, n, k);
                        totalRadixTime += benchmarkSort(radixSort, n, k);
                    }

                    double countSeconds = totalCountTime / (numRepeats * 1e9);
                    double aruSeconds = totalARUTime / (numRepeats * 1e9);
                    double quickSeconds = totalQuickTime / (numRepeats * 1e9);
                    double mergeSeconds = totalMergeTime / (numRepeats * 1e9);
                    double radixSeconds = totalRadixTime / (numRepeats * 1e9);

                    long countSpaceBytes = (long)4 * ((long)2 * (long)n + (long)k);
                    long aruSpaceBytes = (long)4 * ((long)2 * (long)n + (int) ((long)2 * (long)Math.sqrt((long)k)));

                    System.out.printf("n=%d, k=%d -> count: %.6fs, aru: %.6fs, quick: %.6fs, merge: %.6fs, radix: %.6fs%n", n, k, countSeconds, aruSeconds, quickSeconds, mergeSeconds, radixSeconds);

                    outputFile.write(n + "," + k + "," + countSeconds + "," + aruSeconds + "," + quickSeconds + "," + mergeSeconds + "," + radixSeconds + "," + countSpaceBytes + "," + aruSpaceBytes + "\n");
                    outputFile.flush();
                }
            }

            System.out.println("Finished generating sortingTimes4.csv");

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
