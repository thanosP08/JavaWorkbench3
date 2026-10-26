package Thread_lessons.time_examples.examples;

import java.util.Locale;
import java.util.concurrent.*;

public class TimeExample4 {

    public static void main(String[] args)
            throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long simulationStart = System.nanoTime();

        scheduler.schedule(() -> {
            long taskStart = System.nanoTime();
            double actualStart = (taskStart - simulationStart) / 1e9;

            System.out.printf(Locale.US, "Task started at %.2fs%n", actualStart);
            try {
                // Προσομοίωση εργασίας 1.5 δευτερολέπτου
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            long taskEnd = System.nanoTime();
            double actualEnd = (taskEnd - simulationStart) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf(Locale.US, "Task ended at %.2fs%n", actualEnd);

            System.out.printf(Locale.US, "Execution Duration: %.2fs%n", duration);
            }, 2, TimeUnit.SECONDS);

        System.out.println("Task scheduled!");
        scheduler.shutdown();
        if (!scheduler.awaitTermination(6, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}
