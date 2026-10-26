package Thread_lessons.time_examples.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeExample8 {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        ScheduledFuture<?> task = scheduler.schedule(() -> {
            System.out.println("Hello!");
        }, 3, TimeUnit.SECONDS);

        System.out.println(task.isDone());

        scheduler.schedule(() -> scheduler.shutdown(), 7, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        System.out.println("Simulation finished!");

    }
}
