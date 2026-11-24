
package javaSE.Streams.ask;

import java.util.List;
import java.util.Optional;

public class ask4 {

    static class Order {
        String product;
        int quantity;
        double unitPrice;

        Order(String product, int quantity, double unitPrice) {
            this.product = product;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }

    /**
     * Finds the maximum quantity using reduce
     * without an identity.
     */
    static Optional<Integer> maxQuantity(
            List<Integer> quantities) {
        return quantities.stream()
                .reduce((a, b) -> a>b ? a : b);
    }

    /**
     * Multiplies all quantities using reduce
     * with an identity.
     */
    static int productOfQuantities(
            List<Integer> quantities) {

        // Question B
        return quantities.stream()
                .reduce(1, (a, b) -> a*b);
    }

    /**
     * Calculates total revenue using parallelStream
     * and the three-argument reduce.
     */
    static double totalRevenue(List<Order> orders) {

        // Question C
        return orders.parallelStream()
                .reduce(0.0,(a,o2) -> a + o2.quantity* o2.unitPrice
                ,Double::sum); //(a,b) -> a + b)
    }

    public static void main(String[] args) {

        List<Integer> quantities = List.of(2, 3, 4, 5);

        List<Order> orders = List.of(
                new Order("Laptop", 2, 800.0),
                new Order("Mouse", 5, 20.0),
                new Order("Keyboard", 3, 50.0),
                new Order("Monitor", 2, 200.0)
        );

        System.out.println(
                "A: " + maxQuantity(quantities).orElse(null)
        );

        System.out.println(
                "B: " + productOfQuantities(quantities)
        );

        System.out.println(
                "C: " + totalRevenue(orders)
        );
    }
}
