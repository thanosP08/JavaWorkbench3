
package Thread_lessons.time_examples.askstime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ask3 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        long startTime = System.nanoTime();
        System.out.println("Simulation started!");

        scheduler.scheduleAtFixedRate(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task A | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 1, 3, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(() -> {
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
        }, 0, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task C | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 11, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(20, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        long endTime = System.nanoTime();
        double totalDuration = (endTime - startTime) / 1e9;
        System.out.println("Simulation finished!");
        System.out.printf("Total duration: %.2fs%n", totalDuration);
    }
}
