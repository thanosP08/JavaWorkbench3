package Thread_lessons.time_examples.askstime;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ask6 {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        long startTime = System.nanoTime();
        System.out.println("Simulation started!");

        ScheduledFuture<?> taskA = scheduler.scheduleAtFixedRate(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task A | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            taskA.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task A cancelled at %.2fs%n", elapsed);
        }, 8, TimeUnit.SECONDS);

        ScheduledFuture<?> taskB = scheduler.scheduleWithFixedDelay(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task B | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 0, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            taskB.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task B cancelled at %.2fs%n", elapsed);
        }, 10, TimeUnit.SECONDS);
    }

}
