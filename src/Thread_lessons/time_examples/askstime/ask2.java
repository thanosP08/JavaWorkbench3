
package Thread_lessons.time_examples.askstime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ask2 {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();
        scheduler.scheduleAtFixedRate(() -> {
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Server is running | Actual: %.2fs%n", elapsed);
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Security check completed | Actual: %.2fs%n", elapsed);
        }, 4, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 8, TimeUnit.SECONDS);
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1e9;
        System.out.printf("Total duration: %.2fs%n", duration);

    }
}
