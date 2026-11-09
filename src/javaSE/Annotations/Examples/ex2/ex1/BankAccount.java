package javaSE.Annotations.Examples.ex2.ex1;

public interface BankAccount {
    @TransactionLimit(limit = 2000.0, description = "VIP Όριο")
    void withdraw(double amount);
}