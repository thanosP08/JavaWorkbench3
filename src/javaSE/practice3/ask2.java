package javaSE.practice3;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ask2 {
    static class Request {
        String server;
        int time;
        int duration;

        Request(String server, int time, int duration) {
            this.server = server;
            this.time = time;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return server + ";" + time + ";" + duration;
        }
    }

    // A1: Μετατροπή έγκυρων logs σε Request
    static List<Request> parseLogs(String[] logs) {
        List<Request> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("^([AB]);(\\d+);(\\d+)$");

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);

            if (matcher.matches()) {
                try {
                    Request request = new Request(
                            matcher.group(1),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))
                    );
                    result.add(request);
                } catch (NumberFormatException e) {
                    // Αγνοούμε αριθμούς εκτός των ορίων int.
                }
            }
        }
        return result;
    }

    // A2: Μέσος χρόνος απόκρισης ανά server
    static Map<String, Double> averageDuration(List<Request> requests) {
        return requests.stream()
                .collect(Collectors.groupingBy(
                        r -> r.server,
                        Collectors.averagingInt(r -> r.duration)
                ));
    }

    // B: Μέγιστο πλήθος αιτημάτων σε χρονικό παράθυρο
    static int maxBurst(List<Integer> times, int window) {
        if (times.isEmpty()) return 0;

        List<Integer> sorted = new ArrayList<>(times);
        Collections.sort(sorted);

        int left = 0;
        int maxCount = 0;

        for (int right = 0; right < sorted.size(); right++) {
            while ((long) sorted.get(right) - sorted.get(left) > window) {
                left++;
            }

            int count = right - left + 1;
            maxCount = Math.max(maxCount, count);
        }

        return maxCount;
    }

    public static void main(String[] args) throws Exception {
        String[] batchA = {
                "A;1;100",
                "A;2;200",
                "B;2;300",
                "A;3;150",
                "B;8;100",
                "A;10;50",
                "A;11;250",
                "INVALID",
                "B;12;-20"
        };

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        AtomicInteger executions = new AtomicInteger(0);
        long startTime = System.nanoTime();

        // Γ: Περιοδική εκτέλεση στα 1s, 3s, 5s
        ScheduledFuture<?> taskA = scheduler.scheduleAtFixedRate(() -> {
            long taskStart = System.nanoTime();
            int execution = executions.incrementAndGet();

            // 1. Ανάλυση των logs
            List<Request> requests = parseLogs(batchA);

            // 2. Μέσος χρόνος απόκρισης ανά server
            Map<String, Double> average = averageDuration(requests);

            // 3. Χρονικές στιγμές μόνο του server A
            List<Integer> timesA = requests.stream()
                    .filter(r -> r.server.equals("A"))
                    .map(r -> r.time)
                    .toList();

            // 4. Μέγιστη συγκέντρωση αιτημάτων σε 2s
            int max = maxBurst(timesA, 2);

            // 5. Χρονομέτρηση και εμφάνιση
            long taskEnd = System.nanoTime();

            double start = (taskStart - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;

            System.out.printf(
                    "Execution %d | Start: %.2fs | Duration: %.4fs%n",
                    execution, start, duration
            );
            System.out.println("Average duration: " + average);
            System.out.println("Max Burst A: " + max);
            System.out.println();

        }, 1, 2, TimeUnit.SECONDS);

        // Ακύρωση της περιοδικής εργασίας στο 6ο δευτερόλεπτο
        scheduler.schedule(() -> {
            taskA.cancel(false);
            System.out.println("Task A cancelled.");

            // Δεν δεχόμαστε άλλες εργασίες.
            scheduler.shutdown();
        }, 6, TimeUnit.SECONDS);

        // Περιμένουμε να ολοκληρωθούν οι προγραμματισμένες εργασίες.
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        long endTime = System.nanoTime();
        double totalDuration = (endTime - startTime) / 1e9;

        System.out.println("Simulation finished!");
        System.out.printf("Total duration: %.2fs%n", totalDuration);
    }
}
