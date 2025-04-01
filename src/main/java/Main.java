import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SortingAlgorithm countSort = SortingAlgorithms::countingSort;
        SortingAlgorithm ARUCountSort = SortingAlgorithms::ARUCountingSort;

        long countSortTime;
        long ARUCountSortTime;
        for(int i = 0; i < 100; i++){
            countSortTime = benchmarkSort(countSort, 100000000, 100000000);
            ARUCountSortTime = benchmarkSort(ARUCountSort, 100000000,100000000);
            System.out.println("Time :\tCount :\t"+countSortTime/1e9+"\tARU Count :\t"+ARUCountSortTime/1e9+"\tDelta : \t"+ (countSortTime - ARUCountSortTime)/1e9 + "\t" + (((double)countSortTime/(double)ARUCountSortTime)*100 - 100) + "%");
        }


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