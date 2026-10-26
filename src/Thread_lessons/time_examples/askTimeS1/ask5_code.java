import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ask5_code {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        long startTime = System.nanoTime();

        scheduler.scheduleAtFixedRate(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task A started: %.2fs%n", start);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task B started: %.2fs%n", start);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }, 2, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task C started: %.2fs%n", start);
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 12, TimeUnit.SECONDS);
        if (!scheduler.awaitTermination(20, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        double total = (System.nanoTime() - startTime) / 1e9;
        System.out.printf("Total duration: %.2fs%n", total);
    }
}