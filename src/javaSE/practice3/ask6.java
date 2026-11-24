
package javaSE.practice3;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogParser {
    String prefix();
}

public class ask6 {

    static class Event {
        String type;
        String module;
        String status;
        int durationMs;

        Event(String type, String module,
              String status, int durationMs) {
            this.type = type;
            this.module = module;
            this.status = status;
            this.durationMs = durationMs;
        }

        @Override
        public String toString() {
            return type + "(" + module + ","
                    + status + "," + durationMs + ")";
        }
    }

    /**
     * Parses a BUILD log using a regular expression.
     *
     * Example:
     * BUILD|api|FAIL|320 -> BUILD(api,FAIL,320)
     *
     * @param log The raw build log.
     * @return A parsed Event, or null for invalid input.
     */
    @LogParser(prefix = "BUILD|")
    static Event parseBuild(String log) {
        Pattern regex = Pattern.compile(
                "^(BUILD)\\|([a-z]{2,10})\\|(FAIL|PASS)\\|([0-9]{1,4})$"
        );

        Matcher matcher = regex.matcher(log);

        if (matcher.matches()) {
            return new Event(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    Integer.parseInt(matcher.group(4))
            );
        }

        return null;
    }

    /**
     * Parses a TEST log using a regular expression.
     *
     * Example:
     * TEST;module=auth;status=PASS;time=95ms
     * -> TEST(auth,PASS,95)
     *
     * @param log The raw test log.
     * @return A parsed Event, or null for invalid input.
     */
    @LogParser(prefix = "TEST;")
    static Event parseTest(String log) {
        Pattern regex = Pattern.compile(
                "^(TEST);module=([a-z]{2,10});status=(PASS|FAIL);time=([0-9]{1,4})ms$"
        );

        Matcher matcher = regex.matcher(log);

        if (matcher.matches()) {
            return new Event(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    Integer.parseInt(matcher.group(4))
            );
        }

        return null;
    }

    /**
     * Finds the shortest consecutive window containing
     * at least k failed events.
     *
     * Uses a sliding window with two pointers.
     * The right pointer expands the window, while the
     * left pointer shrinks it whenever enough failures exist.
     *
     * Examples:
     * [FAIL, PASS, FAIL, FAIL], k=2 -> 2
     * [PASS, FAIL, PASS, FAIL], k=2 -> 3
     * [PASS, PASS, FAIL], k=2      -> -1
     *
     * @param events The events in their original order.
     * @param k The required number of failures (k >= 1).
     * @return The minimum window length, or -1 if impossible.
     *
     * Time complexity: O(n).
     * Space complexity: O(1).
     */
    static int minFailureWindow(List<Event> events, int k) {
        int left = 0;
        int failures = 0;
        int min = Integer.MAX_VALUE;

        for (int right = 0; right < events.size(); right++) {

            // Expand the window and count new failures.
            if (events.get(right).status.equals("FAIL")) {
                failures++;
            }

            // Shrink the window while it contains enough failures.
            while (failures >= k) {

                // Record the current valid window length.
                min = Math.min(min, right - left + 1);

                // Remove the leftmost event from the window.
                if (events.get(left).status.equals("FAIL")) {
                    failures--;
                }

                left++;
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    /**
     * Scans logs using methods annotated with @LogParser.
     *
     * Uses Reflection to discover parser methods and reads
     * their annotation prefixes to select the correct parser.
     *
     * Processes logs in their original order and ignores
     * invalid logs or null parser results.
     *
     * @param logs The raw log messages.
     * @return A list of valid events in their original order.
     * @throws ReflectiveOperationException
     *         If a reflective invocation fails.
     *
     * Time complexity: O(n * m), where n is the number
     * of logs and m is the number of declared methods.
     */
    static List<Event> scanLogs(String[] logs)
            throws ReflectiveOperationException {

        ArrayList<Event> result = new ArrayList<>();

        Class<?> clazz = ask6.class;
        Method[] methods = clazz.getDeclaredMethods();

        // Process logs in their original order.
        for (String log : logs) {

            for (Method method : methods) {

                // Ignore methods without @LogParser.
                LogParser annotation =
                        method.getAnnotation(LogParser.class);

                if (annotation == null) {
                    continue;
                }

                String prefix = annotation.prefix();

                // Select the parser matching the log prefix.
                if (log.startsWith(prefix)) {

                    // Invoke the static parser method only once.
                    Event event = (Event) method.invoke(null, log);

                    // Ignore invalid logs.
                    if (event != null) {
                        result.add(event);
                    }

                    // Stop after finding the matching parser.
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Counts PASS and FAIL events for every module.
     *
     * Groups events first by module and then by status.
     *
     * Example:
     * api -> {PASS=2, FAIL=1}
     * auth -> {FAIL=2}
     *
     * @param events The parsed events.
     * @return A nested map containing the number of events
     *         for each module and status.
     *
     * Time complexity: O(n) on average.
     * Space complexity: O(n).
     */
    static Map<String, Map<String, Long>> groupStatistics(
            List<Event> events) {

        return events.stream()
                .collect(Collectors.groupingBy(
                        event -> event.module,
                        Collectors.groupingBy(
                                event -> event.status,
                                Collectors.counting()
                        )
                ));
    }

    public static void main(String[] args) throws Exception {

        String[] logs = {
                "BUILD|api|FAIL|320",
                "TEST;module=api;status=PASS;time=80ms",
                "BUILD|auth|FAIL|410",
                "TEST;module=auth;status=FAIL;time=95ms",
                "BUILD|api|PASS|190",
                "TEST;module=db;status=FAIL;time=115ms",
                "BUILD|db|PASS|220",
                "INVALID"
        };

        // Question C1: Parse all logs using Reflection.
        List<Event> events = scanLogs(logs);

        System.out.println("Events: " + events);

        // Question B: Minimum failure windows.
        System.out.println(
                "Minimum window (k=3): "
                        + minFailureWindow(events, 3)
        );

        System.out.println(
                "Minimum window (k=2): "
                        + minFailureWindow(events, 2)
        );

        // Question C2: Nested Streams (pending).
        System.out.println(
                "Statistics: " + groupStatistics(events)
        );
    }
}
