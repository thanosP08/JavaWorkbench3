package Thread_lessons.time_examples.examples;

import java.util.List;
import java.util.concurrent.*;

public class TimeExample3 {

    public static void main(String[] args)
            throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            System.out.println("Hello");
        }, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            task.cancel(false);
        }, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.println(task.isDone());
            System.out.println(task.isCancelled());
        }, 3, TimeUnit.SECONDS);

        scheduler.shutdown();
        if (!scheduler.awaitTermination(6, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}