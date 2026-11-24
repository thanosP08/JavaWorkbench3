
package javaSE.practice3;

import java.util.*;

public class code_pad {

    static class SingleLabelCheck {

        @CheckRule(id = "S-01", priority = 1)
        @Severity(2)
        @Label("api")
        static boolean check() {
            return true;
        }
    }

    static class MultipleLabelsCheck {

        @CheckRule(id = "M-01", priority = 1)
        @Severity(3)
        @Label("security")
        @Label("authentication")
        static boolean check() {
            return true;
        }
    }

    static class FailedCheck {

        @CheckRule(id = "F-01", priority = 1)
        @Severity(2)
        @Label("api")
        @Label("backend")
        static boolean check() {
            return false;
        }
    }

    static class DisabledCheck {

        @CheckRule(id = "D-01", priority = 1, enabled = false)
        @Severity(3)
        @Label("experimental")
        @Label("internal")
        static boolean check() {
            return false;
        }
    }

    static class OrderChecks {

        @CheckRule(id = "P-03", priority = 3)
        @Severity(1)
        @Label("database")
        @Label("storage")
        static boolean checkThree() {
            return true;
        }

        @CheckRule(id = "P-01", priority = 1)
        @Severity(3)
        @Label("security")
        @Label("auth")
        static boolean checkOne() {
            return true;
        }

        @CheckRule(id = "P-02", priority = 2)
        @Severity(2)
        @Label("api")
        @Label("network")
        static boolean checkTwo() {
            return true;
        }
    }

    static class HelperCheck {

        @CheckRule(id = "H-01", priority = 1)
        @Severity(1)
        @Label("database")
        @Label("storage")
        static boolean check() {
            return true;
        }

        static boolean helperMethod() {
            return false;
        }
    }

    static void test(int number, Class<?> clazz,
                     String... expectedValues) {

        List<String> expected = Arrays.asList(expectedValues);

        System.out.println("TEST " + number);
        System.out.println("Class: " + clazz.getSimpleName());
        System.out.println("Expected: " + expected);

        try {
            List<ask7.CheckResult> results =
                    ask7.runChecks(clazz);

            List<String> actual = new ArrayList<>();

            for (ask7.CheckResult result : results) {
                actual.add(result.toString());
            }

            System.out.println("Actual: " + actual);
            System.out.println("PASS: " + expected.equals(actual));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println("PASS: false");
        }

        System.out.println("------------------------------");
    }

    public static void main(String[] args) {

        test(1, SingleLabelCheck.class,
                "S-01 | PASS | severity=2 | labels=[api]");

        test(2, MultipleLabelsCheck.class,
                "M-01 | PASS | severity=3 | labels=[security, authentication]");

        test(3, FailedCheck.class,
                "F-01 | FAIL | severity=2 | labels=[api, backend]");

        test(4, DisabledCheck.class);

        test(5, OrderChecks.class,
                "P-01 | PASS | severity=3 | labels=[security, auth]",
                "P-02 | PASS | severity=2 | labels=[api, network]",
                "P-03 | PASS | severity=1 | labels=[database, storage]");

        test(6, HelperCheck.class,
                "H-01 | PASS | severity=1 | labels=[database, storage]");

        test(7, DeploymentChecks.class,
                "SEC-01 | PASS | severity=3 | labels=[security, authentication]",
                "API-02 | FAIL | severity=2 | labels=[api]",
                "DB-03 | PASS | severity=1 | labels=[database]");
    }
}

