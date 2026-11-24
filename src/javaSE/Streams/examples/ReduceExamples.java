
package javaSE.Streams.examples;

import java.util.List;
import java.util.Optional;

public class ReduceExamples {

    static class Person {
        String name;
        double salary;

        Person(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }
    }

    public static void main(String[] args) {

        List<Integer> nums = List.of(2, 3, 4);

        // 1. reduce(accumulator)
        Optional<Integer> result1 = nums.stream()
                .reduce((acc, x) -> acc + x);

        System.out.println("A: " + result1.orElse(0));

        // 2. reduce(identity, accumulator)
        int result2 = nums.stream()
                .reduce(10, (acc, x) -> acc + x);

        System.out.println("B: " + result2);

        List<Person> people = List.of(
                new Person("Anna", 1000),
                new Person("Nikos", 1500),
                new Person("Maria", 2000)
        );

        // 3. reduce(identity, accumulator, combiner)
        double result3 = people.parallelStream()
                .reduce(
                        0.0,
                        (acc, p) -> acc + p.getSalary(),
                        Double::sum
                );

        System.out.println("C: " + result3);
    }
}
