
package javaSE.practice3;

import java.util.*;

public class code_pad {

    static class Alert {
        String source;
        String detail;

        Alert(String source, String detail) {
            this.source = source;
            this.detail = detail;
        }

        @Override
        public String toString() {
            return source + "(" + detail + ")";
        }
    }

    static int longestUniqueSources(List<Alert> alerts) {
        // Question B: Implement the sliding window algorithm.
        int max=0,n = alerts.size(),i=0,max_now=0;
        HashSet<String> uniqueSources = new HashSet<>();
        for (Alert alert : alerts) {
            System.out.println(uniqueSources.contains(alert.source));
            if (uniqueSources.contains(alert.source)) {
                uniqueSources.clear();
                if (max_now > max) {
                    max = max_now;
                }
                max_now =1;
            } else {
                max_now++;
            }
            uniqueSources.add(alert.source);
        }
        if (max_now > max) {
            max = max_now;
        }
        return max;
    }

    static void test(int testNumber, int expected, String... sources) {
        List<Alert> alerts = new ArrayList<>();

        for (String source : sources) {
            alerts.add(new Alert(source, "test"));
        }

        int actual = longestUniqueSources(alerts);

        System.out.println("TEST " + testNumber);
        System.out.println("Input:    " + Arrays.toString(sources));
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println("PASS: " + (expected == actual));
        System.out.println("------------------------------");
    }

    public static void main(String[] args) {

        test(1, 0);

        test(2, 1, "AUTH");

        test(3, 3,
                "AUTH", "API", "DB");

        test(4, 1,
                "AUTH", "AUTH", "AUTH");

        test(5, 3,
                "AUTH", "API", "AUTH", "DB", "API");

        test(6, 3,
                "API", "DB", "API", "AUTH");

        test(7, 2,
                "DB", "API", "DB", "API");

        test(8, 3,
                "AUTH", "API", "DB", "AUTH");

        test(9, 4,
                "AUTH", "API", "DB", "PAYMENT");

        test(10, 4,
                "AUTH", "API", "AUTH",
                "DB", "PAYMENT", "API");

        test(11, 3,
                "A", "B", "C", "B", "C", "D");

        test(12, 2,
                "AUTH", "AUTH", "API");
    }
}

