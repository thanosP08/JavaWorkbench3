
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeExample7 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();

        ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(() -> {
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Server checked at %.2fs%n", elapsed);
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            task.cancel(false);
            System.out.println("Task cancelled!");
        }, 6, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 7, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        System.out.println("Simulation finished!");
    }
}
