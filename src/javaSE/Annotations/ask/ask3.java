package javaSE.Annotations.ask;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Feature {
    String name();
    int version() default 1;
}

@Feature(name = "Shipping", version = 3)
class Order {

    int shipping(int total) {
        if (total < 40) return 7;
        if (total < 80) return 3;
        return 0;
    }
}

public class ask3 {

    public static void main(String[] args) {

        Feature info =
                Order.class.getAnnotation(Feature.class);

        System.out.println(info.name());
        System.out.println(info.version());

        Order order = new Order();
        System.out.println(order.shipping(60));
    }
}