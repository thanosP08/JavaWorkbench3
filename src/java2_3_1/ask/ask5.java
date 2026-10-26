package java2_3_1.ask;

import java.lang.annotation.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@interface AlgorithmInfo {
    String name();
    int level() default 2;
}

@AlgorithmInfo(name = "Subset Solver", level = 3)
class Solver {
    //Χρόνο: (O(n*target))
    //Μνήμη: (O(n*target))
    static int countWays(int[] a, int k, int target) {

        if (target < 0) return 0;
        int[][] memo = new int[a.length + 1][target + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return solve(a, k, target, memo);
    }

    private static int solve(int[] a, int i, int target, int[][] memo) {
        // Βρήκαμε έγκυρο υποσύνολο
        if (target == 0) return 1;
        // Δεν υπάρχει λύση σε αυτή τη διαδρομή
        if (i == a.length || target < 0) return 0;
        // Έχουμε ήδη υπολογίσει αυτή την κατάσταση
        if (memo[i][target] != -1) {
            return memo[i][target];
        }
        // Δεν επιλέγουμε το τωρινό στοιχείο
        int skip = solve(a, i + 1, target, memo);
        // Επιλέγουμε το τωρινό στοιχείο
        int take = solve(a, i + 1, target - a[i], memo);
        // Αποθηκεύουμε το αποτέλεσμα
        memo[i][target] = skip + take;
        return memo[i][target];
    }

    static int minElements(int[] a, int i, int target) {

        // Βρήκαμε ακριβώς το άθροισμα
        if (target == 0) return 0;
        // Δεν υπάρχει λύση σε αυτή τη διαδρομή
        if (i == a.length || target < 0) return -1;
        // Δεν επιλέγουμε το τωρινό στοιχείο
        int skip = minElements(a, i + 1, target);
        // Επιλέγουμε το τωρινό στοιχείο
        int take = minElements(a, i + 1, target - a[i]);
        // Αν η επιλογή πέτυχε, μετράμε και το στοιχείο που πήραμε
        if (take != -1) {
            take++;
        }
        // Διαχείριση περιπτώσεων χωρίς λύση
        if (take == -1) return skip;
        if (skip == -1) return take;
        // Επιλέγουμε τον συνδυασμό με τα λιγότερα στοιχεία
        return Math.min(take, skip);
    }

    static int countWaysMemo(int[] a, int i, int target,
                             Map<String, Integer> memo) {
        // TODO 4
        return 0;
    }

    static int targetSumWays(int[] a, int i,
                             int sum, int target) {
        // TODO 5
        return 0;
    }
}

public class ask5 {

    public static void main(String[] args) {

        int[] nums = {1, 2, 7};
        System.out.println(Solver.countWays(nums, 0, 10));
        System.out.println(Solver.minElements(nums, 0, 10));
    }
}