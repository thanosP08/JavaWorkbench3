package Thread_lessons.time_examples.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeExample5 {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        long startTime = System.nanoTime();
        System.out.println("Simulation started!");

        for (int i = 1; i < 6; i++) {
            long delay = i;
            scheduler.schedule(() -> {
                double elapsed = (System.nanoTime() - startTime) / 1e9;
                System.out.printf("Task %d | Actual: %.2fs%n", delay, elapsed);
            }, delay, TimeUnit.SECONDS);
        }
        scheduler.scheduleAtFixedRate(() -> {
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task 6 | Actual: %.2fs%n", elapsed);
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 8, TimeUnit.SECONDS);
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        long endTime = System.nanoTime();
        double totalDuration = (endTime - startTime) / 1e9;
        System.out.printf("Total duration: %.2fs%n", totalDuration);
    }
}

