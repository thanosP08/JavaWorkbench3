package Thread_lessons.examples;

class Counter {
    int count;
    public Counter() {
        this.count = 0;
    }
    public void increment() {
        count++;
    }
}

class Counter4 extends Counter {
    public Counter4() {
        super();
    }
}

class CounterWorker<T extends Counter> implements Runnable {
    private final T cnt;
    public CounterWorker(T cnt) {
        this.cnt = cnt;
    }
    @Override
    public void run() {
        int i = 0;

        while (i < 100000) {
            cnt.increment();
            i++;
        }
    }
}

public class ask4 {
    public static void main(String[] args) {
        Counter4 cnt = new Counter4();
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
