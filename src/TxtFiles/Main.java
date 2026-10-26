package TxtFiles;


import java.util.HashMap;
import java.util.Scanner;

import static TxtFiles.TxtManager.handleLoadPrices;


public class Main {
    public static void main(String[] args) {
        Market market1 = new Market();
        Coin BTC = new Coin("BTC",600000.0);
        Coin ETH = new Coin("ETH",600000.0);
        market1.addCoin(BTC);
        market1.addCoin(ETH);
        Scanner input = new Scanner(System.in);

        while (true) {

            System.out.println("give your choice():");
            String choice = input.nextLine();
            HashMap<String, Double> prices;
            if (choice.equals("1")) {
                try {
                    prices = TxtManager.loadPrices("src/TxtFiles/file1.txt", 2, 1, 0);
                    System.out.println("File loaded successfully!");
                } catch (loadPricesExecpiton e) {
                    System.err.println("ERROR: " + e.getMessage());

                }
            } else {
                System.out.println("...");
            }
        }
    }

}
