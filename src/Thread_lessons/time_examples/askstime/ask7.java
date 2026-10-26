
package Thread_lessons.time_examples.askstime;
import java.util.concurrent.*;

public class ask7 {
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
            boolean cancelled = taskA.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task A | Cancel result: %b | Time: %.2fs%n", cancelled, elapsed);
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
        }, 0, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            boolean cancelled = taskB.cancel(false);
            double elapsed = (System.nanoTime() - startTime) / 1e9;
            System.out.printf("Task B | Cancel result: %b | Time: %.2fs%n", cancelled, elapsed);
        }, 8, TimeUnit.SECONDS);

        ScheduledFuture<?> taskC = scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task C | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        }, 2, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.printf("Task C | isDone: %b | isCancelled: %b%n", taskC.isDone(), taskC.isCancelled());
        }, 7, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.printf("Task A | isDone: %b | isCancelled: %b%n", taskA.isDone(), taskA.isCancelled());
            System.out.printf("Task B | isDone: %b | isCancelled: %b%n", taskB.isDone(), taskB.isCancelled());
        }, 9, TimeUnit.SECONDS);

        scheduler.schedule(() -> scheduler.shutdown(), 11, TimeUnit.SECONDS);

        try {
            taskC.get(3, TimeUnit.SECONDS);
            System.out.println("Task C | COMPLETED!");
        } catch (TimeoutException e) {
            System.out.println("Task C | TIMEOUT!");
        } catch (ExecutionException e) {
            System.out.println("Task C | FAILED: " + e.getCause());
        }

        System.out.println("Main continues...");

        if (!scheduler.awaitTermination(20, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }

        long endTime = System.nanoTime();
        double totalDuration = (endTime - startTime) / 1e9;
        System.out.println("Simulation finished!");
        System.out.printf("Total duration: %.2fs%n", totalDuration);
    }
}
