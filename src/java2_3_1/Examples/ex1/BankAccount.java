package java2_3_1.Examples.ex1;

public interface BankAccount {
    @TransactionLimit(limit = 2000.0, description = "VIP Όριο")
    void withdraw(double amount);
}