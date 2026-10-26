package java2_3_1.ask;

class Order2 {

    int shippingCost(int subtotal) {
        return subtotal >= 60 ? 0 : 5;
    }

    @Deprecated
    int oldShippingCost() {
        return 9;
    }
}

public class ask2 extends Order2 {

    @Override
    int shippingCost(int subtotal) {
        return subtotal >= 100 ? 3 : 10;
    }

    static int calculateTotal(int[] prices, Order2 order) {
        int sum = 0;

        for (int price : prices) {
            if (price > 0) {
                sum += price;
            }
        }
        if (sum == 0) {
            return 0;
        }
        if (sum >= 100) {
            sum = sum - (sum * 10 / 100);
        }
        return sum + order.shippingCost(sum);
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Order2 order = new ask2();

        int[] prices = {35, 40, 45, -10};

        System.out.println(calculateTotal(prices, order));
        System.out.println(order.oldShippingCost());
    }
}