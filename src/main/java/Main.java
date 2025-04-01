import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.pow;

public class Main {
    public static void main(String[] args) {
        SortingAlgorithm countSort = SortingAlgorithms::countingSort;
        SortingAlgorithm ARUCountSort = SortingAlgorithms::ARUCountingSort;

        FileWriter outputFile;
        try {outputFile = new FileWriter("sortingTimes2.csv");}catch (Exception e){
            throw new RuntimeException("File failed to open");
        }

        long countSortTime;
        long ARUCountSortTime;
        int n;
        for (int exp = 1; exp <= 100; exp++){
            for (int i = 0; i < 10; i++) {
                n = (int)1e5 * exp;
                countSortTime = benchmarkSort(countSort, n, n);
                ARUCountSortTime = benchmarkSort(ARUCountSort, n, n);
                System.out.println("Time :\tCount :\t" + countSortTime / 1e9 + "\tARU Count :\t" + ARUCountSortTime / 1e9 + "\tDelta : \t" + (countSortTime - ARUCountSortTime) / 1e9 + "\t" + (((double) countSortTime / (double) ARUCountSortTime) * 100 - 100) + "%");

                try {
                    outputFile.write((int)pow(2,exp)+","+countSortTime/ 1e9 + "," + ARUCountSortTime/ 1e9 + "\n");
                } catch (IOException e) {
                    throw new RuntimeException("File failed to be written to");
                }
            }
            System.out.println(exp);
        }

        try {
            outputFile.close();
        } catch (IOException e) {
            throw new RuntimeException("File failed to close");
        }
        System.out.println("Test finished");
    }

    public static long benchmarkSort(SortingAlgorithm sort, int l, int k){

        Random rand = new Random();
        int [] test = new int[l];
        int max = 0;
        for(int i = 0; i < l; i++){
            test[i] = rand.nextInt(k);
            if (test[i] > max){
                max = test[i];
            }
        }

        long start = System.nanoTime();
        sort.sort(test,max);
        return System.nanoTime() - start;

    }
}