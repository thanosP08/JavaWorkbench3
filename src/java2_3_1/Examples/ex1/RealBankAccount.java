package java2_3_1.Examples.ex1;

public class RealBankAccount implements BankAccount {
    private double balance;

    public RealBankAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
        System.out.println("Επιτυχής ανάληψη. Νέο υπόλοιπο: " + balance);
    }
}