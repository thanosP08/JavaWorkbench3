
package javaSE.Streams.ask;

import java.util.*;

public class ask5 {

    static class TestResult {
        String module;
        boolean passed;
        int durationMs;

        TestResult(String module, boolean passed, int durationMs) {
            this.module = module;
            this.passed = passed;
            this.durationMs = durationMs;
        }

        @Override
        public String toString() {
            return module + " (" + durationMs + "ms)";
        }
    }

    /**
     * Finds the fastest failed test.
     */
    static Optional<TestResult> fastestFailure(
            List<TestResult> tests) {

        return tests.stream()
                .filter(t -> !t.passed)
                .min(Comparator.comparingInt(t -> t.durationMs));
    }

    /**
     * Finds the slowest failed test.
     */
    static Optional<TestResult> slowestFailure(
            List<TestResult> tests) {

        return tests.stream()
                .filter(t -> !t.passed)
                .max(Comparator.comparingInt(t -> t.durationMs));
    }

    /**
     * Checks whether all release conditions are satisfied.
     */
    static boolean readyForRelease(
            List<TestResult> tests) {

        return tests.stream()
                .allMatch(t -> t.durationMs <= 700)

                && tests.stream()
                .noneMatch(t -> !t.passed)

                && tests.stream()
                .anyMatch(t -> t.module.equals("Security") && t.passed);
    }

    /**
     * Creates a sorted report of failed tests
     * using the three-argument collect method.
     */
    static List<String> failureReport(
            List<TestResult> tests) {

        return tests.stream()
                .filter(t -> !t.passed)
                .sorted(Comparator.comparingInt((TestResult t) -> t.durationMs).reversed())
                .map(TestResult::toString)
                .collect(
                        ArrayList<String>::new,
                        ArrayList<String>::add,
                        ArrayList<String>::addAll
                );
    }

    public static void main(String[] args) {

        List<TestResult> tests = List.of(
                new TestResult("Authentication", true, 180),
                new TestResult("API", false, 520),
                new TestResult("Database", true, 340),
                new TestResult("Security", true, 220),
                new TestResult("Payments", false, 610),
                new TestResult("Search", true, 480),
                new TestResult("Reports", false, 420)
        );

        System.out.println("A1: " +
                fastestFailure(tests).orElse(null));

        System.out.println("A2: " +
                slowestFailure(tests).orElse(null));

        System.out.println("B: " +
                readyForRelease(tests));

        System.out.println("C: " +
                failureReport(tests));
    }
}

