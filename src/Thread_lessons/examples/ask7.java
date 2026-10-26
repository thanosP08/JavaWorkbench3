
package Thread_lessons.examples;

class SharedSignal {

    private boolean ready;

    public SharedSignal() {
        this.ready = false;
    }
    public synchronized void waitForSignal() throws InterruptedException {

        while (!ready) {
            wait();
        }
    }
    public synchronized void sendSignal() {
        ready = true;
        notifyAll();
    }
}

class WaitingWorker implements Runnable {
    private final SharedSignal signal;
    public WaitingWorker(SharedSignal signal) {
        this.signal = signal;
    }

    @Override
    public void run() {

        System.out.println("Worker waiting for signal...");
        try {
            signal.waitForSignal();
            System.out.println("Worker resumed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Worker interrupted!");
        }
    }
}

class SignalController implements Runnable {
    private final SharedSignal signal;
    public SignalController(SharedSignal signal) {
        this.signal = signal;
    }

    @Override
    public void run() {
        System.out.println("Controller preparing signal...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        signal.sendSignal();
        System.out.println("Controller sent signal!");
    }
}

public class ask7 {

    public static void main(String[] args) throws InterruptedException {
        SharedSignal signal = new SharedSignal();
        WaitingWorker worker = new WaitingWorker(signal);
        SignalController controller = new SignalController(signal);
        Thread t1 = new Thread(worker, "Worker");
        Thread t2 = new Thread(controller, "Controller");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("All threads finished!");
    }
}
