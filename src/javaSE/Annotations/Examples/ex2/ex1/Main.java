package javaSE.Annotations.Examples.ex2.ex1;

public class Main {
    public static void main(String[] args) {

        BankAccount secureAccount = SecurityProxy.createSecureProxy(
                new RealBankAccount(5000.0),
                BankAccount.class
        );

        System.out.println("--- Προσπάθεια 1: 1500 ---");
        secureAccount.withdraw(1500.0);

        System.out.println("\n--- Προσπάθεια 2: 2500 ---");
        secureAccount.withdraw(2500.0);
    }
}