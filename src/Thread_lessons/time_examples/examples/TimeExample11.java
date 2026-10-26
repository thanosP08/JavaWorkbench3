
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeExample11 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            System.out.println("Task executed!");
        }, 5, TimeUnit.SECONDS);

        double remaining1 = task.getDelay(TimeUnit.MILLISECONDS) / 1000.0;
        System.out.printf("Remaining time: %.2fs%n", remaining1);

        Thread.sleep(2000);

        double remaining2 = task.getDelay(TimeUnit.MILLISECONDS) / 1000.0;
        System.out.printf("Remaining time: %.2fs%n", remaining2);

        scheduler.shutdown();
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}
