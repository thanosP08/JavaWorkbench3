
package javaSE.Streams.ask;

import java.util.*;

public class code_pad {

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

        Map<String, Long> expectedA = Map.of(
                "Laptop", 3L,
                "Mouse", 2L,
                "Keyboard", 2L
        );

        Map<String, Integer> expectedB = Map.of(
                "Laptop", 6,
                "Mouse", 9,
                "Keyboard", 3
        );

        Map<String, Integer> expectedC = Map.of(
                "Laptop", 1,
                "Mouse", 2,
                "Keyboard", 0
        );

        System.out.println("A: " +
                expectedA.equals(Order.countOrders(orders)));

        System.out.println("B: " +
                expectedB.equals(Order.totalQuantities(orders)));

        System.out.println("C: " +
                expectedC.equals(Order.countLargeOrders(orders)));

        System.out.println(
                "C result: " + Order.countLargeOrders(orders)
        );
    }
}
