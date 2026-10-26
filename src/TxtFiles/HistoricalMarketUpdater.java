package TxtFiles;

import java.util.HashMap;

public class HistoricalMarketUpdater implements Runnable {
    HashMap<String,Double> prices;
    Market market;

    public HistoricalMarketUpdater(HashMap<String,Double> prices,Market market) {
        this.prices = prices;
        this.market = market;
    }

    @Override
    public void run() {
        while (true) {

        }
    }

}