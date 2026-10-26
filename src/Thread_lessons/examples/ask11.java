package Thread_lessons.examples;

import java.util.Random;
import java.util.concurrent.*;

class NumberProducer implements Runnable {

    private final BlockingQueue<Integer> queue;
    public NumberProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        Random rand = new Random();
        for (int i=1;i<=10;i++) {
            try {
                this.queue.put(rand.nextInt(9) + 1);
            } catch (InterruptedException e) {
                break;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }
        }
        try {
            this.queue.put(-1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

class NumberConsumer implements Runnable {
    BlockingQueue<Integer> queue;

    public NumberConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int num = queue.take();
                if (num == -1) {
                    break;
                } else {
                    System.out.println("the square is: " + num*num);
                }
            } catch (InterruptedException e) {
                break;
            }

        }
    }
}


public class ask11 {
    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        NumberProducer producer = new NumberProducer(queue);
        NumberConsumer consumer = new NumberConsumer(queue);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit both tasks
        executor.execute(producer);
        executor.execute(consumer);

        // Stop accepting new tasks
        executor.shutdown();

        try {
            // Wait for both tasks to finish
            boolean finished = executor.awaitTermination(
                    30, TimeUnit.SECONDS
            );

            if (finished) {
                System.out.println("All tasks completed!");
            } else {
                System.out.println("Tasks did not finish in time!");
                executor.shutdownNow();
            }

        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

