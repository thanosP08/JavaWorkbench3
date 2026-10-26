

package Thread_lessons.time_examples.examples;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeExample6 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();

        scheduler.scheduleWithFixedDelay(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task started: %.2fs%n", start);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            double end = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task ended: %.2fs%n", end);
        }, 0, 2, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(
                () -> {
                    double end = (System.nanoTime() - startTime) / 1e9;
                    System.out.printf("hi: %.2fs%n", end);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                },
                1,
                3,
                TimeUnit.SECONDS
        );

        scheduler.schedule(() -> scheduler.shutdown(), 12, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(20, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        System.out.println("Simulation finished!");
    }
}
