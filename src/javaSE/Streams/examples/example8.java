package javaSE.Streams.examples;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class example8 {
    public static void main(String[] args) {
        //8.1 -Εναλλακτικοί τρόποι δημιουργίας Stream

        // 1-Stream.builder()
        Stream<Integer> s = Stream.<Integer>builder()
                .add(10)
                .add(20)
                .add(30)
                .build();

        System.out.println(s.collect(Collectors.toList())); // [10, 20, 30]
        // 2-Stream.generate()
        List<String> result = Stream.generate(() -> "OK")
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(result); // [OK, OK, OK]
        // 3-Stream.iterate()
        List<Integer> result2 = Stream.iterate(10, x -> x + 2)
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(result2); // [10, 12, 14, 16, 18]
        // 4-IntStream.range()
        IntStream.range(2, 7).forEach(System.out::println);

        //8.2 -Η κλάση Collectors
        //        Η Collectors προσφέρει έτοιμους τρόπους για να συγκεντρώνουμε τα αποτελέσματα ενός Stream.
        //        Collector	Λειτουργία
        //        toList()	Συγκέντρωση σε λίστα
        //        toSet()	Συγκέντρωση σε σύνολο
        //        toCollection(...)	Συγκέντρωση σε Collection συγκεκριμένου τύπου
        //        toMap(...)	Δημιουργία Map
        //        joining(separator)	Ένωση Strings με διαχωριστικό
        //        summarizingInt(...)	Υπολογισμός στατιστικών για int
        //        summarizingLong(...)	Αντίστοιχο για long
        //        summarizingDouble(...)	Αντίστοιχο για double


        //1 Παράδειγμα toSet()
        List<Integer> numbers = List.of(1, 1, 2, 2, 3);
        Set<Integer> result3 = numbers.stream().collect(Collectors.toSet());
        System.out.println(result3.size()); // 3

        //2 Παράδειγμα joining()
        List<String> names = List.of("Java", "Python", "C");
        String result4 = names.stream().collect(Collectors.joining(" - "));
        System.out.println(result4); // Java - Python - C

        //3 Παράδειγμα toMap()
        List<String> words = List.of("Java", "Python", "C");
        Map<String, Integer> result5 = words.stream()
                .collect(Collectors.toMap(
                        x -> x,
                        x -> x.length()
                ));

        System.out.println(result5.get("Python")); // 6

        //4 Παράδειγμα summarizingInt()
        numbers = List.of(2, 4, 6, 8);
        IntSummaryStatistics stats = numbers.stream()
                .collect(Collectors.summarizingInt(x -> x));
        System.out.println(stats.getCount());   // 4
        System.out.println(stats.getSum());     // 20
        System.out.println(stats.getMin());     // 2
        System.out.println(stats.getMax());     // 8
        System.out.println(stats.getAverage()); // 5.0
    }
}
