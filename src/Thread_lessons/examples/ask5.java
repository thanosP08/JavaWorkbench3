package Thread_lessons.examples;

class Counter5 extends Counter {
    public Counter5() {
        super();
    }
    @Override
    public synchronized void increment() {
        count++;
    }
}

public class ask5 {
    public static void main(String[] args) {
        Counter5 cnt = new Counter5();
        CounterWorker Worker1 = new CounterWorker(cnt);
        Thread w1 = new Thread(Worker1, "Worker1");
        CounterWorker Worker2 = new CounterWorker(cnt);
        Thread w2 = new Thread(Worker2, "Worker2");
        w1.start();
        w2.start();
        try {
            w1.join();
            w2.join();
            System.out.println("Final Counter: " + cnt.count);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
