package javaSE.Streams.ask;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ask3 {

    static Map<String, String> subjectReport(List<ExamResult> results) {

        return results.stream()
                .collect(Collectors.groupingBy(
                        e -> e.subject,
                        Collectors.teeing(
                                Collectors.maxBy(
                                        Comparator.comparingInt(
                                                (ExamResult e) -> e.score
                                        )
                                ),
                                Collectors.minBy(
                                        Comparator.comparingInt(
                                                (ExamResult e) -> e.score
                                        )
                                ),
                                (a, b) -> "Max: " + a.orElseThrow().score
                                        + ", Min: " + b.orElseThrow().score
                        )
                ));
    }

    public static void main(String[] args) {

        List<ExamResult> results = List.of(
                new ExamResult("Anna", "Java", 92),
                new ExamResult("Nikos", "Java", 58),
                new ExamResult("Maria", "Python", 85),
                new ExamResult("Petros", "Python", 74),
                new ExamResult("Eleni", "Java", 81),
                new ExamResult("Anna", "Python", 95)
        );

        System.out.println(subjectReport(results));
    }
}