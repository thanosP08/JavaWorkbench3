
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;

public class TimeExample13 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task started: %.2fs%n", start);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            double end = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task finished: %.2fs%n", end);
        }, 1, TimeUnit.SECONDS);

        System.out.println("Main: Before get()");

        try {
            task.get(2, TimeUnit.SECONDS);
            System.out.println("Task completed successfully!");
        } catch (TimeoutException e) {
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Timeout at %.2fs%n", elapsed);
        }

        System.out.println("Main: After get()");
        scheduler.shutdown();

        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        System.out.println("Simulation finished!");
    }
}
