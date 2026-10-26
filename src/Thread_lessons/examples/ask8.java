
package Thread_lessons.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskWorker implements Runnable {

    private final int taskId;

    public TaskWorker(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();

        System.out.println(
                "Task " + taskId + " started by " + threadName
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(
                "Task " + taskId + " finished by " + threadName
        );
    }
}

public class ask8 {

    public static void main(String[] args) throws InterruptedException {

        // Create a pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit 5 tasks
        for (int i = 1; i <= 5; i++) {
            executor.execute(new TaskWorker(i));
        }

        // Stop accepting new tasks
        executor.shutdown();

        // Wait for all tasks to finish
        boolean finished = executor.awaitTermination(
                30, TimeUnit.SECONDS
        );

        if (finished) {
            System.out.println("All tasks completed!");
        } else {
            System.out.println("Some tasks are still running!");
            executor.shutdownNow();
        }
    }
}
