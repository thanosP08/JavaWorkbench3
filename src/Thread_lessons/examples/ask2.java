package Thread_lessons.examples;

class MyThread2 implements Runnable {
    String type;
    public MyThread2(String type) {
        this.type =type;
    }
    public void run() {
        if (type.equals("1")) {
            for (int i =1;i<=5;i++) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        } else if(type.equals("2")) {
            for (int i =10;i<=50;i+=10) {
                System.out.println(i);
                try {
                    Thread.sleep(500);
                } catch (Exception e){
                    System.out.println(e);
                }
            }

        } else {
            System.out.println("0");
        }

    }
}
public class ask2 {
    public static void main(String[] args) {
        MyThread2 worker1 = new MyThread2("1");
        MyThread2 worker2 = new MyThread2("2");

        Thread t1 = new Thread(worker1);
        Thread t2 = new Thread(worker2);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("All threads finished!");
    }
}
