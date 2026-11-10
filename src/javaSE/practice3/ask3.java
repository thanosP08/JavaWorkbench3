
package javaSE.practice3;

import java.util.*;
import java.util.stream.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

import static java.util.Collections.sort;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyTest {
    int priority();
}

public class ask3 {

    // Stores the result of a single test.
    static class TestResult {
        String name;
        int priority;
        boolean passed;

        TestResult(String name, int priority, boolean passed) {
            this.name = name;
            this.priority = priority;
            this.passed = passed;
        }

        @Override
        public String toString() {
            return name + " | Priority: " + priority
                    + " | Passed: " + passed;
        }
    }

    // Contains the methods that we want to test.
    static class Calculator {
        static int add(int a, int b) {
            return a + b;
        }

        // Contains an intentional bug for testing purposes.
        static int subtract(int a, int b) {
            return a + b;
        }
    }

    // Contains the test methods.
    static class SampleTests {

        @MyTest(priority = 2)
        public static boolean testAddition() {
            return Calculator.add(2, 3) == 5;
        }

        @MyTest(priority = 1)
        public static boolean testNegativeAddition() {
            return Calculator.add(-2, 5) == 3;
        }

        @MyTest(priority = 3)
        public static boolean testSubtraction() {
            return Calculator.subtract(9, 4) == 5;
        }

        @MyTest(priority = 2)
        public static boolean testZero() {
            return Calculator.subtract(0, 0) == 0;
        }

        // This method must not be executed by the framework.
        public static boolean helperMethod() {
            return true;
        }
    }

    // Question A: Discover and execute annotated tests.
    // Discover and execute annotated test methods.
    static List<TestResult> runTests(Class<?> testClass) throws ReflectiveOperationException {
        ArrayList<TestResult> result = new ArrayList<>();
        Method[] methods = testClass.getDeclaredMethods();
        for (Method m : methods) {
            MyTest myTest = m.getAnnotation(MyTest.class);
            if (myTest != null) {
                // Execute the static test method.
                boolean passed = (boolean) m.invoke(null);
                // Store the test name, priority, and result.
                result.add(new TestResult(
                        m.getName(),
                        myTest.priority(),
                        passed
                ));
            }
        }
        return result;
    }

    //B
    // Sort test results by priority and name using insertion sort.
    static void insertionSort(ArrayList<TestResult> arr) {
        for (int i = 1; i < arr.size(); i++) {
            TestResult key = arr.get(i);
            int j = i - 1;

            while (j >= 0 && (
                    arr.get(j).priority > key.priority ||
                            (arr.get(j).priority == key.priority &&
                                    arr.get(j).name.compareTo(key.name) > 0)
            )) {
                // Shift larger elements one position to the right.
                arr.set(j + 1, arr.get(j));
                j--;
            }

            // Insert the saved element into its correct position.
            arr.set(j + 1, key);
        }
    }

    // Return a sorted copy without modifying the original list.
    static List<TestResult> sortResults(List<TestResult> results) {
        ArrayList<TestResult> sorted = new ArrayList<>(results);
        insertionSort(sorted);
        return sorted;
    }


    // Question C: Generate the final report using Streams.
    // Print the test results and calculate summary statistics.
    static void printReport(List<TestResult> results) {
        System.out.println("===== TEST REPORT =====");

        // Display every test and its execution status.
        results.forEach(t ->
                System.out.println(
                        t.name + (t.passed ? " PASSED" : " FAILED")
                )
        );

        // Count the total number of tests.
        long total = results.size();

        // Count successful tests using a stream.
        long passed = results.stream()
                .filter(t -> t.passed)
                .count();

        // Calculate the number of failed tests.
        long failed = total - passed;

        // Calculate the success percentage.
        double successRate = total == 0 ? 0.0 : (passed * 100.0) / total;

        // Print the final statistics.
        System.out.println("\nTotal: " + total);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.printf("Success Rate: %.1f%%%n", successRate);
    }


    public static void main(String[] args)
            throws ReflectiveOperationException {

        List<TestResult> results = runTests(SampleTests.class);

        List<TestResult> sorted = sortResults(results);

        printReport(sorted);
    }
}
