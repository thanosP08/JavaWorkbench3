
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeExample10 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            System.out.println("Hello");
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            boolean result = task.cancel(false);
            System.out.println("Cancellation successful: " + result);
            System.out.println("isDone: " + task.isDone());
            System.out.println("isCancelled: " + task.isCancelled());
        }, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            boolean result = task.cancel(false);
            System.out.println("Second cancellation: " + result);
        }, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 6, TimeUnit.SECONDS);
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}
