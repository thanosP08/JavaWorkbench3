
package Thread_lessons.examples;

class SharedControl {
    private volatile boolean running;

    public SharedControl() {
        this.running = true;
    }

    public boolean getRunning() {
        return running;
    }

    public void stopRunning() {
        running = false;
    }
}

class LoopWorker implements Runnable {
    private final SharedControl item;

    public LoopWorker(SharedControl item) {
        this.item = item;
    }

    @Override
    public void run() {
        int cnt = 0;

        while (item.getRunning()) {
            System.out.println(Thread.currentThread().getName() + ": " + cnt);
            cnt++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(
                Thread.currentThread().getName() + " finished!"
        );
    }
}

class StopController implements Runnable {
    private final SharedControl item;

    public StopController(SharedControl item) {
        this.item = item;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        item.stopRunning();
        System.out.println("StopController: Stopping all workers!");
    }
}

public class ask6 {
    public static void main(String[] args) {

        SharedControl sc = new SharedControl();

        LoopWorker worker1 = new LoopWorker(sc);
        LoopWorker worker2 = new LoopWorker(sc);

        Thread w1 = new Thread(worker1, "Worker1");
        Thread w2 = new Thread(worker2, "Worker2");

        StopController stop = new StopController(sc);
        Thread stopCon = new Thread(stop, "StopController");

        w1.start();
        w2.start();
        stopCon.start();

        try {
            w1.join();
            w2.join();
            stopCon.join();

            System.out.println("All threads finished!");

        } catch (InterruptedException e) {
            sc.stopRunning();

            w1.interrupt();
            w2.interrupt();
            stopCon.interrupt();

            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted!");
        }
    }
}
