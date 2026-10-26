package TxtFiles;

import java.io.*;
import java.util.HashMap;

public abstract class TxtManager {
    public static void FileAppendData(String path,String data) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path,true))) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void writeFile(String path,String data) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static int countLines(String path) {
        int cnt=0;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cnt++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return cnt;
    }

    public static HashMap<String, Double> loadPrices(
            String path, int len, int priceIndex, int symbolIndex) {

        HashMap<String, Double> dict = new HashMap<>();

        // Έλεγχος παραμέτρων
        if (len <= 0 || priceIndex < 0 || priceIndex >= len || symbolIndex < 0 || symbolIndex >= len
                || priceIndex == symbolIndex) {
            throw new loadPricesExecpiton("Invalid column configuration.");
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",", -1);
                if (parts.length != len) {
                    throw new loadPricesExecpiton("Line " + lineNumber + ": Expected "
                            + len + " columns, but found " + parts.length);
                }
                String symbol = parts[symbolIndex].trim();
                if (symbol.isEmpty()) {
                    throw new loadPricesExecpiton("Line " + lineNumber + ": Coin symbol cannot be empty.");
                }
                try {
                    double price = Double.parseDouble(parts[priceIndex].trim());
                    if (!Double.isFinite(price) || price <= 0) {
                        throw new loadPricesExecpiton("Line " + lineNumber
                                + ": Invalid price for " + symbol + ". Price must be positive and finite.");
                    }
                    if (dict.containsKey(symbol)) {
                        throw new loadPricesExecpiton("Line " + lineNumber + ": Duplicate coin symbol: " + symbol);
                    }
                    dict.put(symbol, price);
                } catch (NumberFormatException e) {
                    throw new loadPricesExecpiton("Line " + lineNumber + ": Invalid numeric price for " + symbol);
                }
            }
        } catch (IOException e) {
            throw new loadPricesExecpiton("Cannot read file '" + path + "': " + e.getMessage());
        }
        return dict;
    }

    public static void handleLoadPrices(
            String path, int len, int priceIndex, int symbolIndex) {
        try {
            HashMap<String, Double> prices = TxtManager.loadPrices(path, len, priceIndex, symbolIndex);
            System.out.println("File loaded successfully!");
            for (String symbol : prices.keySet()) {
                System.out.println(symbol + ": " + prices.get(symbol));
            }
        } catch (loadPricesExecpiton e) {
            System.err.println("ERROR: " + e.getMessage());

        }
    }

}
