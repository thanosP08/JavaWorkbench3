package javaSE.Annotations.ask;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

public class MainCodePad {

    static void marge(int[] arr,int left,int mid,int right) {
        int n1 = mid -left+ 1;
        int n2 = right - mid;
        int[] l = new int[n1];
        int[] r = new int[n2];

        for (int i = 0; i < n1; i++) {
            l[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            r[j] = arr[mid + 1 + j];
        }
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (l[i] <= r[j]) {
                arr[k] = l[i];
                i++;
            } else {
                arr[k] = r[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = l[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = r[j];
            j++;
            k++;
        }
    }
    static void MyMergeSort ( int[] arr, int left, int right){
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        MyMergeSort(arr, left, mid);
        MyMergeSort(arr, mid + 1, right);
        marge(arr, left, mid, right);
    }

    static void QS(ArrayList<Long> arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = Par(arr,left,right);
        QS(arr,left,pivot - 1);
        QS(arr,pivot + 1,right);
    }
    static int Par(ArrayList<Long> arr,int left,int right) {
        int i = left-1;
        long pivot = arr.get(right);
        for (int j = left; j<right; j++) {
            if (arr.get(j) <= pivot ) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,right);
        return i+1;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {10,90,1,5,4,80,3,100,6,19999,100,100};
        ArrayList<Long> arr1 =  new ArrayList<>(List.of(10111L,90L,1L,5L,4L,80L,3L,100L,6L,19999L,100L,100L));
        System.out.println(arr1);
        QS(arr1, 0, arr.length-1);
        System.out.println(arr1);

    }
}
