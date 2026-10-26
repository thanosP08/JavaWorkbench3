package Thread_lessons.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class BankAccount {
    double balance;
    public BankAccount() {
        this.balance = 0;
    }

    public synchronized void deposit(double amount) {
        this.balance += amount;
    }
    public double getBalance() {
        return this.balance;
    }
}

class DepositWorker implements Runnable {
    double amount;
    BankAccount acc;
    int id;
    public DepositWorker(BankAccount acc, int id,double amount) {
        this.amount = amount;
        this.acc = acc;
        this.id = id;
    }
    @Override
    public void run() {
        int cnt = 0;
        while (cnt < 1000) {
            this.acc.deposit(amount);
            cnt++;
        }
        System.out.println("Worker" + this.id + "end the task");
    }
}


public class ask9 {

    public static void main(String[] args) {

        BankAccount acc = new BankAccount();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            executor.execute(new DepositWorker(acc, i, 10));
        }

        executor.shutdown();

        try {
            boolean finished = executor.awaitTermination(
                    30, TimeUnit.SECONDS
            );

            if (finished) {
                System.out.println("Balance now is: " + acc.getBalance());
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

