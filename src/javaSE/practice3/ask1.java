
package javaSE.practice3;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogRule {
    String regex();
    int delaySeconds();
}

public class ask1 {
    static class Event {
        String server, status;
        int code;

        Event(String server, String status, int code) {
            this.server = server;
            this.status = status;
            this.code = code;
        }

        @Override
        public String toString() {
            return server + "|" + status + "|" + code;
        }
    }

    @LogRule(
            regex = "^([AB])\\|(ERROR|WARN|OK)\\|([0-9]{3})$",
            delaySeconds = 1
    )
    static List<Event> parseLogs(String[] logs) throws ReflectiveOperationException {
        ArrayList<Event> events = new ArrayList<>();

        Class<?> clazz = ask1.class;
        Method method = clazz.getDeclaredMethod("parseLogs", String[].class);
        LogRule logRule = method.getAnnotation(LogRule.class);

        if (logRule == null) return events;

        Pattern pattern = Pattern.compile(logRule.regex());

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);

            if (matcher.matches()) {
                Event item = new Event(
                        matcher.group(1),
                        matcher.group(2),
                        Integer.parseInt(matcher.group(3))
                );
                events.add(item);
            }
        }
        return events;
    }

    static List<String> manualSplit(String input, String pattern) {
        if (pattern.isEmpty()) return null;

        List<String> result = new ArrayList<>();
        int start = 0;
        int i = 0;

        while (i <= input.length() - pattern.length()) {
            if (input.substring(i, i + pattern.length()).equals(pattern)) {
                result.add(input.substring(start, i));
                i += pattern.length();
                start = i;
            } else {
                i++;
            }
        }

        result.add(input.substring(start));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String[] batchA = {
                "A|ERROR|503",
                "A|OK|200",
                "B|ERROR|404",
                "A|WARN|429",
                "INVALID"
        };

        String[] batchB = {
                "B|ERROR|503",
                "B|ERROR|500",
                "A|ERROR|503",
                "B|OK|200",
                "A|ERROR|xx"
        };

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        Class<?> clazz = ask1.class;
        Method method = clazz.getDeclaredMethod("parseLogs", String[].class);
        LogRule logRule = method.getAnnotation(LogRule.class);

        int time = logRule.delaySeconds();
        long startTime = System.nanoTime();

        ScheduledFuture<Void> taskA = scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            List<?> events = (List<?>) method.invoke(null, (Object) batchA);
            Map<String, Long> errors = events.stream()
                    .map(Event.class::cast)
                    .filter(e -> e.status.equals("ERROR"))
                    .collect(Collectors.groupingBy(
                            e -> e.server,
                            Collectors.counting()
                    ));
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf(
                    "Batch A | Scheduled: %ds | Actual: %.3fs | Duration: %.6fs%n",
                    time, start, duration
            );
            System.out.println(
                    "Errors: A=" + errors.getOrDefault("A", 0L)
                            + ", B=" + errors.getOrDefault("B", 0L)
            );
            return null;
        }, time, TimeUnit.SECONDS);

        ScheduledFuture<Void> taskB = scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            List<?> events = (List<?>) method.invoke(null, (Object) batchB);
            Map<String, Long> errors = events.stream()
                    .map(Event.class::cast)
                    .filter(e -> e.status.equals("ERROR"))
                    .collect(Collectors.groupingBy(
                            e -> e.server,
                            Collectors.counting()
                    ));
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf(
                    "Batch B | Scheduled: %ds | Actual: %.3fs | Duration: %.6fs%n",
                    time + 2, start, duration
            );
            System.out.println(
                    "Errors: A=" + errors.getOrDefault("A", 0L)
                            + ", B=" + errors.getOrDefault("B", 0L)
            );
            return null;
        }, time + 2, TimeUnit.SECONDS);
        scheduler.shutdown();
        taskA.get();
        taskB.get();
        if (!scheduler.awaitTermination(6, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}
