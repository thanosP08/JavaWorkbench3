package TxtFiles;

import java.util.HashMap;

public class Market {

    double marketTrend;
    double marketVolatility;
    private final HashMap<String, Coin> coins;

    public Market() {
        this.coins = new HashMap<>();
    }

    public HashMap<String, Coin> getCoins() {
        return new HashMap<>(coins);
    }
    public Double getMarketTrend() {
        return this.marketTrend;
    }
    public Double getMarketVolatility() {
        return this.marketVolatility;
    }
    public void addCoin(Coin coin) {
        this.coins.put(coin.getSymbol(), coin);
    }
    public Coin findCoin(String symbol) {
        return coins.get(symbol);
    }
    public boolean removeCoin(String symbol) {
        return coins.remove(symbol) != null;
    }

}