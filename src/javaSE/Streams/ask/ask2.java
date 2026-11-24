package javaSE.Streams.ask;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ExamResult {
    String student;
    String subject;
    int score;

    ExamResult(String student, String subject, int score) {
        this.student = student;
        this.subject = subject;
        this.score = score;
    }

    static List<String> excellentStudents(List<ExamResult> results) {
        // Question A
        return results.stream()
                .filter(r->r.score >= 80)
                .map(r ->r.student)
                .distinct()
                .sorted()
                .toList();
    }
    static Map<String, Double> averageBySubject(List<ExamResult> results) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        e -> e.subject,
                        Collectors.averagingDouble(e -> e.score)
                ));
    }

    /**
     * Returns the three highest-scoring exam results
     * in descending order.
     *
     * Example:
     * [Anna(Java,92), Maria(Python,85), Anna(Python,95)]
     * -> [Anna (Python): 95, Anna (Java): 92, Maria (Python): 85]
     *
     * @param results The list of exam results.
     * @return Up to three formatted results.
     *
     * Time complexity: O(n log n).
     */
    static List<String> topThree(List<ExamResult> results) {
        return results.stream()
                .sorted(Comparator.comparingInt((ExamResult e) -> e.score).reversed())
                .limit(3)
                .map(e -> e.student + " (" + e.subject + "): " + e.score)
                .collect(Collectors.toList());
    }
}

public class ask2 {
    public static void main(String[] args) {
        List<ExamResult> results = List.of(
                new ExamResult("Anna", "Java", 92),
                new ExamResult("Nikos", "Java", 58),
                new ExamResult("Maria", "Python", 85),
                new ExamResult("Petros", "Python", 74),
                new ExamResult("Anna", "Python", 95),
                new ExamResult("Eleni", "Java", 81),
                new ExamResult("Giorgos", "Java", 40),
                new ExamResult("Nikos", "Python", 65)
        );
        System.out.println(ExamResult.excellentStudents(results));
    }
}
