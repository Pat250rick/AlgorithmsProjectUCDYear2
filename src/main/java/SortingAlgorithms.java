import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;


public final class SortingAlgorithms {
    public static int [] countingSort(int [] a, int k){
        int n = a.length;

        int [] b = new int [n];
        int [] c = new int [k + 1];

        int i = 0;
        int j = 0;

        while ( i < k+1 ) {
            c[i] = 0;
            i++;
        }

        while ( j < n )
        {
            c[a[j]]++;
            j++;
        }

        i = 1;
        j = n-1;

        while ( i <= k)
        {
            c[i] = c[i] + c[i-1];
            i++;
        }

        while ( j >= 0)
        {
            b[c[a[j]] - 1] = a[j];
            c[a[j]]--;
            j--;
        }

        return b;
    }

    public static int [] ARUCountingSort(int [] a, int k){
        int n = a.length;
        int m = (int)ceil(sqrt(k));

        int [] b = new int[n];
        int [] q = new int[m+1];    // Represents the quotient array
        int [] r = new int[m+1];    // Represents the remainder array

        int i = 1;
        int j = 0;

        while ( j < n )
        {
            q[a[j]/m]++;
            r[a[j]%m]++;
            j++;
        }

        while ( i <= m )
        {
            q[i] = q[i] + q[i-1];
            r[i] = r[i] + r[i-1];
            i++;
        }

        j = n-1;
        int d;

        while ( j >= 0 )
        {
            d = a[j]%m;
            r[d]--;
            b[r[d]] = a[j];
            j--;
        }

        i = n-1;

        while ( i >= 0 )
        {
            d = b[i]/m;
            q[d]--;
            a[q[d]] = b[i];
            i = i - 1;
        }

        return a;
    }

    public static int[] mergeSort(int[] a, int k) {
        return mergeSort(a);
    }

    public static int[] mergeSort(int[] a) {
        if (a.length <= 1){
            return a;
        }else{
            mergeSortHelper(a, 0, a.length - 1);
            return a;
        }
    }

    private static void mergeSortHelper(int[] a, int left, int right){
        if(left < right){
            int mid = (left + right) / 2;
            mergeSortHelper(a , left, mid);
            mergeSortHelper(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    private static void merge(int[] a, int left, int mid, int right){
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while(i <= mid && j <= right){
            temp[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        }
        while(i <= mid) temp[k++] = a[i++];
        while(j <= right) temp[k++] = a[j++];

        System.arraycopy(temp, 0, a, left, temp.length);
    }

    public static int[] quickSort(int[] a, int k) {
        return quickSort(a);
    }

    public static int[] quickSort(int[] a) {
        quickSortHelper(a, 0, a.length - 1);
        return a;
    }

    private static void quickSortHelper(int[] a, int low, int high){
        if(low < high){
            int pivotIndex = partition(a, low, high);
            quickSortHelper(a, low, pivotIndex - 1);
            quickSortHelper(a, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] a, int low, int high){
        int pivot = a[high];
        int i = low - 1;
        for(int j = low; j < high; j++){
            if(a[j] <= pivot){
                i++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        return i + 1;
    }

    public static int[] radixSort(int[] a, int k) {
        return radixSort(a);
    }

    public static int[] radixSort(int[] a) {
        int max = 0;
        for(int val : a){
            if (val > max){
                max = val;
            }
        }
        for (int exp = 1; max / exp > 0; exp *= 10){
            countingSortByDigit(a, exp);
        }
        return a;
    }

    private static void countingSortByDigit(int[] a, int exp){
        int n = a.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for(int i = 0; i < n; i++){
            count[(a[i] / exp) % 10]++;
        }

        for(int i = 1; i < 10; i++){
            count[i] += count[i - 1];
        }

        for(int i = n - 1; i >= 0; i--){
            int digit = (a[i] / exp) % 10;
            output[count[digit] - 1] = a[i];
            count[digit]--;
        }
        System.arraycopy(output, 0, a, 0, n);
    }
}
