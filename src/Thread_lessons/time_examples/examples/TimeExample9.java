package Thread_lessons.time_examples.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeExample9 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            System.out.println("Task executed!");
        }, 2, TimeUnit.SECONDS);

        System.out.println("Before execution:");
        System.out.println("isDone: " + task.isDone());
        System.out.println("isCancelled: " + task.isCancelled());

        scheduler.schedule(() -> {
            System.out.println("After execution:");
            System.out.println("isDone: " + task.isDone());
            System.out.println("isCancelled: " + task.isCancelled());
        }, 4, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 5, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}

