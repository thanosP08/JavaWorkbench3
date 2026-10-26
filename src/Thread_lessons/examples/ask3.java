package Thread_lessons.examples;

class MyThreadWorker implements Runnable {

    @Override
    public void run() {
        int cnt = 1;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("(Worker) " + cnt);
            cnt++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("(Worker) Controller told me to stop!");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("(Worker) Finished!");
    }
}

class MyThreadController implements Runnable {
    private final Thread worker;
    public MyThreadController(Thread worker) {
        this.worker = worker;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("(Controller) Sending interrupt signal...");
            worker.interrupt();
        } catch (InterruptedException e) {
            System.out.println("(Controller) Interrupted!");
            Thread.currentThread().interrupt();
        }
        System.out.println("(Controller) Finished!");
    }
}

public class ask3 {

    public static void main(String[] args) throws InterruptedException {

        MyThreadWorker worker = new MyThreadWorker();
        Thread t1 = new Thread(worker, "Worker");
        MyThreadController controller = new MyThreadController(t1);
        Thread t2 = new Thread(controller, "Controller");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("All threads finished!");
    }
}

