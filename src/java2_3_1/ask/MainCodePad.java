package java2_3_1.ask;

import java.util.Arrays;
import java.util.HashSet;

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

    static int longestConsecutive(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }
        int[] copyArr = Arrays.copyOf(nums, nums.length);
        MyMergeSort(copyArr, 0, copyArr.length - 1);
        int max = 1;
        int current = 1;
        for (int i = 1; i < copyArr.length; i++) {
            if (copyArr[i] == copyArr[i - 1]) {
                continue;
            }
            if ((long) copyArr[i] == (long) copyArr[i - 1] + 1) {
                current++;
            } else {
                current = 1;
            }
            max = Math.max(max, current);
        }
        return max;
    }

    static int longestConsecutive_top(int[] nums) {

        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (int num : set) {
            // Ξεκινάμε μόνο από την αρχή μιας ακολουθίας
            if (num != Integer.MIN_VALUE && set.contains(num - 1)) {
                continue;
            }
            int current = num;
            int length = 1;
            while (current != Integer.MAX_VALUE && set.contains(current + 1)) {

                current++;
                length++;
            }
            max = Math.max(max, length);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {10,90,1,5,4,80,3,100,6,19999,100,100};
        System.out.println(longestConsecutive(arr));
        MyMergeSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
