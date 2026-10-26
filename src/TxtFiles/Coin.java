package TxtFiles;

public class Coin {
    private String symbol;
    private double price;
    public Coin(String symbol,double price) {
        this.price = price;
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }
}
