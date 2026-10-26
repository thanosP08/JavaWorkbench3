
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class TimeExample12 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            double start = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task started: %.2fs%n", start);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            double end = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task finished: %.2fs%n", end);
        }, 2, TimeUnit.SECONDS);

        System.out.println("Main: Before get()");
        task.get();
        System.out.println("Main: After get()");

        scheduler.shutdown();
    }
}
