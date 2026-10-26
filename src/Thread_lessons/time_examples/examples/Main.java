package Thread_lessons.time_examples.examples;

import java.util.ArrayList;
import java.util.List;

class TimePrinter implements Runnable {

    private final ArrayList<Long> times;

    public TimePrinter(ArrayList<Long> times) {
        this.times = new ArrayList<>(times);
    }

    @Override
    public void run() {
        long previousTime = 0;
        try {
            for (long time : times) {
                long delay = time - previousTime;
                if (delay < 0) {
                    throw new IllegalArgumentException("Times must be sorted!");
                }
                Thread.sleep(delay * 1000);
                System.out.println(Thread.currentThread().getName() + " -> Print at " + time + "s");
                previousTime = time;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted!");
        }
    }
}

public class Main {
    public static void main(String[] args)
            throws InterruptedException {
        ArrayList<Long> times = new ArrayList<>(List.of(0L, 1L, 3L, 7L, 10L));

        Thread thread = new Thread(new TimePrinter(times), "Timer-1");
        thread.start();
        thread.join();
        System.out.println("Main finished!");
    }
}
