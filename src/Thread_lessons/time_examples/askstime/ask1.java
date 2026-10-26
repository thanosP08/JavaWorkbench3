
package Thread_lessons.time_examples.askstime;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ask1 {

    public static void main(String[] args)
            throws InterruptedException {

        List<Long> timesA = List.of(0L, 2L, 5L, 9L);
        List<Long> timesB = List.of(1L, 3L, 6L);
        List<Long> timesC = List.of(0L, 4L, 7L, 10L);

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(3);

        // Κοινή χρονική αφετηρία
        long start = System.nanoTime();

        for (long time : timesA) {
            scheduler.schedule(
                    () -> printDevice("Device A", time, start),
                    time,
                    TimeUnit.SECONDS
            );
        }

        for (long time : timesB) {
            scheduler.schedule(
                    () -> printDevice("Device B", time, start),
                    time,
                    TimeUnit.SECONDS
            );
        }

        for (long time : timesC) {
            scheduler.schedule(
                    () -> printDevice("Device C", time, start),
                    time,
                    TimeUnit.SECONDS
            );
        }

        System.out.println("All events scheduled!");

        scheduler.shutdown();

        if (!scheduler.awaitTermination(12, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        System.out.println("Simulation finished!");
    }

    private static void printDevice(
            String symbol, long time, long start) {

        double elapsed =
                (System.nanoTime() - start) / 1_000_000_000.0;
        System.out.printf(Locale.US, "%s | Scheduled: %ds | Actual: %.2fs%n", symbol, time, elapsed);
    }
}
