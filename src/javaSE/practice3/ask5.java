
package javaSE.practice3;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ScanRule {
}

public class ask5 {

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

    /**
     * Detects failed authentication attempts.
     *
     * The username must start with a lowercase letter and contain
     * between 3 and 12 characters in total.
     *
     * Examples:
     * AUTH;user=alex;status=FAIL   -> AUTH(alex)
     * AUTH;user=anna_1;status=FAIL -> AUTH(anna_1)
     * AUTH;user=1alex;status=FAIL  -> null
     * AUTH;user=alex;status=OK     -> null
     *
     * @param log The authentication log to validate.
     * @return An AUTH alert if the login failed, otherwise null.
     */
    @ScanRule
    static Alert checkAuth(String log) {
        Pattern regex = Pattern.compile(
                "^(AUTH);user=([a-z][a-z0-9_]{2,11});status=FAIL$"
        );

        Matcher matcher = regex.matcher(log);

        if (matcher.matches()) {
            return new Alert(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    /**
     * Detects API requests that returned a 5xx status code.
     *
     * Example:
     * API;path=/orders/42;code=503 -> API(/orders/42)
     *
     * @param log The raw API log.
     * @return An API alert, or null if no server error is detected.
     */
    @ScanRule
    static Alert checkApi(String log) {
        Pattern regex = Pattern.compile(
                "^(API);path=(/[a-z]+/[0-9]{1,4});code=5[0-9]{2}$"
        );
        Matcher matcher = regex.matcher(log);

        if (matcher.matches()) {
            return new Alert(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    /**
     * Detects database queries that take at least 200 ms.
     *
     * Example:
     * DB;table=users;duration=350ms -> DB(users)
     *
     * @param log The raw database log.
     * @return A DB alert, or null if the query is not slow.
     */
    @ScanRule
    static Alert checkDb(String log) {
        // Question A3: Implement the database RegEx.
        Pattern regex = Pattern.compile("^(DB);table=([a-z]+);duration=([0-9]{1,4})ms$");
        Matcher matcher = regex.matcher(log);
        if (matcher.matches() && Integer.parseInt(matcher.group(3)) >=200) {
            return new Alert(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    /**
     * Finds the longest consecutive sequence of alerts
     * containing no duplicate sources.
     *
     * Uses a sliding window with two pointers and a HashSet.
     * When a duplicate source appears, the left boundary
     * moves forward until the duplicate is eliminated.
     *
     * Example:
     * [API, DB, API, AUTH] -> 3
     *
     * @param alerts The alerts in their original order.
     * @return The maximum length of a window with unique sources.
     *
     * Time complexity: O(n) on average.
     * Space complexity: O(k), where k is the number of unique sources.
     */
    static int longestUniqueSources(List<Alert> alerts) {
        HashSet<String> uniqueSources = new HashSet<>();

        int left = 0;
        int max = 0;

        for (int right = 0; right < alerts.size(); right++) {
            String current = alerts.get(right).source;

            // Shrink the window until the current source is unique.
            while (uniqueSources.contains(current)) {
                uniqueSources.remove(alerts.get(left).source);
                left++;
            }

            // Add the current source to the valid window.
            uniqueSources.add(current);

            // Update the maximum window length.
            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    /**
     * Scans logs using methods annotated with @ScanRule.
     *
     * Uses Reflection to discover and invoke scanner methods.
     * Each method is executed once per log.
     * Non-null alerts are collected in the original log order.
     *
     * @param logs The raw log messages.
     * @return A list of detected alerts in their original order.
     * @throws ReflectiveOperationException If a reflective invocation fails.
     */
    static List<Alert> scanLogs(String[] logs)
            throws ReflectiveOperationException {
        List<Alert> alerts = new ArrayList<>();
        Method[] methods = ask5.class.getDeclaredMethods();
        // Process logs in their original order.
        for (String log : logs) {
            // Find methods annotated with @ScanRule.
            for (Method method : methods) {
                if (method.isAnnotationPresent(ScanRule.class)) {
                    // Invoke the static scanner method only once.
                    Alert result = (Alert) method.invoke(null, log);
                    // Keep only successfully detected alerts.
                    if (result != null) {
                        alerts.add(result);
                    }
                }
            }
        }
        return alerts;
    }

    public static void main(String[] args) throws Exception {
        String[] logs = {
                "AUTH;user=alex;status=FAIL",
                "API;path=/orders/42;code=503",
                "DB;table=users;duration=350ms",
                "AUTH;user=maria;status=OK",
                "API;path=/cart/4;code=500"
        };

        // Test the three regex rules.
        System.out.println(checkAuth(logs[0]));
        System.out.println(checkApi(logs[1]));
        System.out.println(checkDb(logs[2]));

        // Question B and C:

        //C
        List<Alert> alerts = scanLogs(logs);
        System.out.println("====================");
        for (Alert alert : alerts) {
            System.out.println(alert);
        }
        System.out.println("====================");
        Map<String, Long> map1 = alerts.stream()
                .collect(Collectors.groupingBy(
                        item ->item.source,Collectors.counting()));
        System.out.println(map1);
        // Scan all logs, count alerts, and find unique source windows.
    }
}
