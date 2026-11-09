
package javaSE.Annotations.ask;

import java.lang.annotation.*;
import java.util.*;

// DONE 1
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
@interface JobInfo {
    String name();
    int version() default 1;
}

// TODO 2: Repeatable annotation
@interface Tag {
    String value();
}

// TODO 2: Container annotation
@interface Tags {
    Tag[] value();
}

// TODO 2: javaSE.Annotations της SequenceWorker
class SequenceWorker {

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

    /*
     * TODO 6: THREAD SYSTEM
     *
     * Σχεδίασε μόνος σου την κλάση.
     *
     * Requirements:
     *
     * - Δέχεται int[] μέσω constructor.
     * - Εκτελεί την ανάλυση σε ξεχωριστό thread.
     * - Καλεί τη longestConsecutive().
     * - Αποθηκεύει το αποτέλεσμα.
     * - Παρέχει getResult().
     *
     * Εσύ αποφασίζεις:
     * - Thread ή Runnable
     * - Πεδία
     * - Constructor
     * - run()
     * - getResult()
     */

    // TODO 3: Annotation στη μέθοδο
    static int longestConsecutive(int[] nums) {
        int[] copyArr = Arrays.copyOf(nums, nums.length);
        MyMergeSort(copyArr, 0, copyArr.length - 1);
        int step=0,max=0;
        for (int i = 0; i < copyArr.length; i++) {
            if  (copyArr[i] == copyArr[i+1] - 1) {
                step++;
            } else  {
                max = step;
                step=0;
            }
        }
        if (step>max) {
            max=step;
        }
        return max;
    }

    // TODO 4: Deprecated method
}

public class ask7 {

    public static void main(String[] args)
            throws Exception {

        int[] a = {100, 4, 200, 1, 3, 2};
        int[] b = {9, 1, 4, 7, 3, 5, 8, 6};

        // TODO 3: javaSE.Annotations.Reflection

        // TODO 4: Built-in annotations

        /*
         * TODO 6: THREAD EXECUTION
         *
         * Job A -> Worker 1 -> αποτέλεσμα 4
         * Job B -> Worker 2 -> αποτέλεσμα 7
         *
         * Δημιούργησε τους workers.
         * Δημιούργησε τα threads αν χρειάζεται.
         * Ξεκίνησε την εκτέλεση.
         * Περίμενε να ολοκληρωθούν.
         * Εμφάνισε τα αποτελέσματα.
         */
    }
}
