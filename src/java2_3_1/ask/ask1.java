package java2_3_1.ask;

class Order1 {

    int finalPrice(int price) {
        return price + 3;
    }
    @Deprecated
    int oldFinalPrice(int price) {
        return price + 5;
    }
}

public class ask1 extends Order1 {
    @Override
    int finalPrice(int price) {
        return price + 8;
    }
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Order1 order = new ask1();

        System.out.println(order.finalPrice(20));
        System.out.println(order.oldFinalPrice(20));
    }
}