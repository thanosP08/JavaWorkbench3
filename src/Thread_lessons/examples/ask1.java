package Thread_lessons.examples;

class MyThread1 implements Runnable {
    String type;
    public MyThread1(String type) {
        this.type =type;
    }
    public void run() {
        if (type.equals("1")) {
            for (int i =0;i<=5;i++) {
                System.out.println(i);
            }
        } else if(type.equals("2")) {
            for (int i =10;i<=50;i++) {
                System.out.println(i);
            }
        } else {
            System.out.println("0");
        }
    }
}

public class ask1 {
    public static void main(String[] args) {
        MyThread1 worker1 = new MyThread1("1");
        MyThread1 worker2 = new MyThread1("2");

        Thread t1 = new Thread(worker1);
        Thread t2 = new Thread(worker2);

        t1.start();
        t2.start();
    }
}
