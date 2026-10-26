package Thread_lessons.time_examples.examples;

import java.util.List;
import java.util.concurrent.*;

public class TimeExample2 {

    public static void main(String[] args)
            throws InterruptedException {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        List<Long> btcTimes = List.of(0L, 1L, 3L, 7L);
        List<Long> ethTimes = List.of(0L, 2L, 5L);

        long start = System.nanoTime();

        for (long time : btcTimes) {
            scheduler.schedule(
                    () -> printEvent("BTC", start),
                    time,
                    TimeUnit.SECONDS
            );
        }

        for (long time : ethTimes) {
            scheduler.schedule(
                    () -> printEvent("ETH", start),
                    time,
                    TimeUnit.SECONDS
            );
        }

        scheduler.shutdown();

        if (!scheduler.awaitTermination(12, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        System.out.println("Simulation finished!");
    }

    private static void printEvent(String symbol, long start) {

        double elapsed =
                (System.nanoTime() - start) / 1_000_000_000.0;

        System.out.printf(
                "%.2fs -> %s | Thread: %s%n",
                elapsed,
                symbol,
                Thread.currentThread().getName()
        );
    }
}
