
package Thread_lessons.time_examples.askstime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ask5 {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        long startTime = System.nanoTime();
        System.out.println("Simulation started!");

        ScheduledFuture<?> taskA = scheduler.scheduleAtFixedRate(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task A | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 1, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            taskA.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task A cancelled at %.2fs%n", elapsed);
        }, 8, TimeUnit.SECONDS);

        ScheduledFuture<?> taskB = scheduler.scheduleWithFixedDelay(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task B | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 0, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            taskB.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task B cancelled at %.2fs%n", elapsed);
        }, 10, TimeUnit.SECONDS);

        ScheduledFuture<?> taskC =scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task C | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 5, TimeUnit.SECONDS);

        ScheduledFuture<?> taskD =scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task D | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 12, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            taskD.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task D cancelled at %.2fs%n", elapsed);
        }, 6, TimeUnit.SECONDS);




        scheduler.schedule(() -> {
            System.out.printf("Task C | isDone: %b | isCancelled: %b%n", taskC.isDone(), taskC.isCancelled());
            System.out.printf("Task D | isDone: %b | isCancelled: %b%n", taskD.isDone(), taskD.isCancelled());
        }, 7, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.printf("Task A | isDone: %b | isCancelled: %b%n", taskA.isDone(), taskA.isCancelled());
        }, 9, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.printf("Task B | isDone: %b | isCancelled: %b%n", taskB.isDone(), taskB.isCancelled());
        }, 11, TimeUnit.SECONDS);


        scheduler.schedule(() -> scheduler.shutdown(), 14, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(20, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        long endTime = System.nanoTime();
        double totalDuration = (endTime - startTime) / 1e9;
        System.out.println("Simulation finished!");
        System.out.printf("Total duration: %.2fs%n", totalDuration);
    }
}
