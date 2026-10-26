
package Thread_lessons.examples;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

class SumWorker implements Callable<Integer> {

    private final int start;
    private final int end;

    public SumWorker(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int sum = 0;

        for (int i = start; i <= end; i++) {
            sum += i;
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " calculated the sum from "
                        + start + " to " + end
        );

        return sum;
    }
}

public class ask10 {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Submit the three tasks
            Future<Integer> future1 =
                    executor.submit(new SumWorker(1, 100));

            Future<Integer> future2 =
                    executor.submit(new SumWorker(101, 200));

            Future<Integer> future3 =
                    executor.submit(new SumWorker(201, 300));

            // Wait for the results
            int sum1 = future1.get();
            int sum2 = future2.get();
            int sum3 = future3.get();

            // Calculate the total sum
            int total = sum1 + sum2 + sum3;

            System.out.println("Sum 1: " + sum1);
            System.out.println("Sum 2: " + sum2);
            System.out.println("Sum 3: " + sum3);
            System.out.println("Total sum: " + total);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {
            System.out.println("Task failed: " + e.getCause());

        } finally {
            executor.shutdown();
        }
    }
}
