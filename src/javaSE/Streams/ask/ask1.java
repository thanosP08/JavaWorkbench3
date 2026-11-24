package javaSE.Streams.ask;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javaSE.Streams.ask.Order.countOrders;

class Order {
    String product;
    int quantity;

    Order(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Counts the number of orders for each product.
     *
     * @param orders The list of orders.
     * @return A map containing order counts by product.
     */
    static Map<String, Long> countOrders(List<Order> orders) {
        // Question A: groupingBy + counting
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o ->o.product,
                        Collectors.counting()
                ));
    }

    /**
     * Calculates the total quantity ordered for each product.
     *
     * @param orders The list of orders.
     * @return A map containing total quantities by product.
     */
    static Map<String, Integer> totalQuantities(List<Order> orders) {
        // Question B: groupingBy + summingInt
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o ->o.product,
                        Collectors.summingInt(o -> o.quantity)
                ));
    }

    /**
     * Counts orders with quantity greater than 2.
     * Products with zero qualifying orders must be included.
     *
     * @param orders The list of orders.
     * @return A map containing qualifying order counts.
     */
    static Map<String, Integer> countLargeOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.product,
                        Collectors.summingInt(o -> o.quantity > 2 ? 1 : 0)
                ));
    }
}

public class ask1{
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 2),
                new Order("Mouse", 5),
                new Order("Laptop", 3),
                new Order("Keyboard", 2),
                new Order("Mouse", 4),
                new Order("Laptop", 1),
                new Order("Keyboard", 1)
        );

        System.out.println("A: " + countOrders(orders));
        System.out.println("B: " + Order.totalQuantities(orders));
        System.out.println("C: " + Order.countLargeOrders(orders));
    }

}
