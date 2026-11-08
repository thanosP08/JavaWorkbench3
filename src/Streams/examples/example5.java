package Streams.examples;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class example5 {
    //1
    public static void main(String[] args) {
        List<Integer> numbers = List.of(5, 1, 4, 2, 3);

        List<Integer> result = numbers.stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(result); // [1, 2, 3, 4, 5]

        List<Integer> result2 = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(result2); // [5, 4, 3, 2, 1]
        //2
        List<Integer> numbers2 = List.of(10, 20, 30, 40, 50);

        System.out.println(
                numbers2.stream().limit(3).collect(Collectors.toList())
        ); // [10, 20, 30]

        System.out.println(
                numbers2.stream().skip(2).collect(Collectors.toList())
        ); // [30, 40, 50]

        List<Integer> result3 = numbers.stream()
                .skip(1)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(result3); // [20, 30, 40]
        //3
        List<Integer> numbers3 = List.of(1, 2, 3, 8, 4, 5);

        System.out.println(
                numbers3.stream().filter(x -> x < 5).toList()
        ); // [1, 2, 3, 4]

        System.out.println(
                numbers3.stream().takeWhile(x -> x < 5).toList()
        ); // [1, 2, 3]
        //4
        List<Integer> numbers4 = List.of(1, 2, 3);

        List<Integer> result4 = numbers.stream()
                .map(x -> x * 2)
                .peek(x -> System.out.println("Value: " + x))
                .collect(Collectors.toList());
        //5
        List<String> values = List.of("5.2", "1.6", "4.4");

        double sum = values.stream()
                .mapToDouble(Double::parseDouble)
                .sum();

        System.out.println(sum); // 11.2
    }
}
