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

}
