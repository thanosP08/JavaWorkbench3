
package javaSE.Streams.ask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ask6 {

    static class ServiceTest {
        String module;
        int durationMs;
        boolean passed;
        boolean critical;

        ServiceTest(String module, int durationMs, boolean passed, boolean critical) {
            this.module = module;
            this.durationMs = durationMs;
            this.passed = passed;
            this.critical = critical;
        }

        @Override
        public String toString() {
            return module + " (" + durationMs + "ms)";
        }
    }

    static class TestAnalyzer {

        // A1: Find tests by status, sorted ascending.
        List<ServiceTest> findTests(List<ServiceTest> tests, boolean passed) {
            return tests.stream()
                    .filter(t -> t.passed == passed)
                    .sorted(Comparator.comparingInt(t -> t.durationMs))
                    .toList();
        }

        // A2: Find tests within the maximum duration.
        List<ServiceTest> findTests(List<ServiceTest> tests, int maxDurationMs) {
            return tests.stream()
                    .filter(t -> t.durationMs <= maxDurationMs)
                    .sorted(Comparator.comparingInt(t -> t.durationMs))
                    .toList();
        }

        // B: Validate deployment requirements.
        boolean canDeploy(List<ServiceTest> tests) {
            return tests.stream()
                    .allMatch(t -> t.durationMs <= 650)

                    && tests.stream()
                    .noneMatch(t -> t.critical && !t.passed)

                    && tests.stream()
                    .anyMatch(t -> t.module.equals("Security") && t.passed);
        }

        // C: Generate a descending failure report.
        List<String> failureReport(List<ServiceTest> tests) {
            return tests.stream()
                    .filter(t -> !t.passed)
                    .sorted(
                            Comparator.comparingInt(
                                    (ServiceTest t) -> t.durationMs
                            ).reversed()
                    )
                    .map(ServiceTest::toString)
                    .collect(
                            ArrayList<String>::new,
                            ArrayList<String>::add,
                            ArrayList<String>::addAll
                    );
        }
    }

    public static void main(String[] args) {

        List<ServiceTest> tests = List.of(
                new ServiceTest("Authentication", 180, true, true),
                new ServiceTest("API", 520, false, true),
                new ServiceTest("Database", 340, true, false),
                new ServiceTest("Security", 220, true, true),
                new ServiceTest("Payments", 610, false, false),
                new ServiceTest("Search", 480, true, false)
        );

        TestAnalyzer analyzer = new TestAnalyzer();

        System.out.println("A1 - Failed Tests:");
        analyzer.findTests(tests, false)
                .forEach(System.out::println);

        System.out.println("\nA2 - Tests <= 400ms:");
        analyzer.findTests(tests, 400)
                .forEach(System.out::println);

        System.out.println("\nB - Can Deploy:");
        System.out.println(analyzer.canDeploy(tests));

        System.out.println("\nC - Failure Report:");
        System.out.println(analyzer.failureReport(tests));
    }
}


