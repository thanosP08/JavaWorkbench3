
package javaSE.practice3;

import java.util.*;
import java.util.stream.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

// QUESTION A: Create and configure all five annotations.

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@interface WorkflowInfo {
    // Define name() and version().
    String name();
    int version() default 1;
}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CheckRule {
    // Define id(), priority() and enabled().
    String id();
    int priority();
    boolean enabled() default true;
}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Severity {
    // Define value().
    int value();
}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Labels.class)
@interface Label {
    // Define value() and make this annotation repeatable.
    String value();
}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Labels {
    // Define the container for @Label.
    Label[] value();
}


// QUESTION A: Annotate this class with @WorkflowInfo.
@WorkflowInfo(name = "production-deployment",version = 2)

class BaseDeploymentChecks {
}

class DeploymentChecks extends BaseDeploymentChecks {

    // Add @CheckRule, @Severity and two @Label annotations.
    // ID: SEC-01, priority: 1, severity: 3
    // Labels: security, authentication
    @CheckRule(id = "SEC-01", priority = 1)
    @Severity(3)
    @Label(value = "security")
    @Label(value = "authentication")
    static boolean checkAuthentication() {
        return true;
    }

    // ID: API-02, priority: 2, severity: 2
    // Label: api
    @CheckRule(id = "API-02", priority = 2)
    @Severity(2)
    @Label(value = "api")
    static boolean checkApi() {
        return false;
    }

    // ID: DB-03, priority: 3, severity: 1
    // Label: database
    @CheckRule(id = "DB-03", priority = 3)
    @Severity(1)
    @Label(value = "database")
    static boolean checkDatabase() {
        return true;
    }

    // ID: EXP-04, priority: 4, severity: 3
    // Enabled: false
    // Label: experimental
    @CheckRule(id = "EXP-04", priority = 4,enabled = false)
    @Severity(3)
    @Label(value = "experimental")
    static boolean checkExperimental() {
        return false;
    }

    // No annotation: this method must be ignored.
    static boolean helperMethod() {
        return false;
    }
}


public class ask7 {

    static class CheckResult {
        String id;
        int priority;
        boolean passed;
        int severity;
        List<String> labels;

        CheckResult(String id, int priority,
                    boolean passed, int severity,
                    List<String> labels) {
            this.id = id;
            this.priority = priority;
            this.passed = passed;
            this.severity = severity;
            this.labels = labels;
        }

        @Override
        public String toString() {
            return id
                    + " | " + (passed ? "PASS" : "FAIL")
                    + " | severity=" + severity
                    + " | labels=" + labels;
        }
    }

    /**
     * Reads workflow metadata, including inherited annotations.
     *
     * @param clazz The class to inspect.
     * @return Workflow description, or UNKNOWN if absent.
     */
    static String workflowDescription(Class<?> clazz) {
        WorkflowInfo annotation = clazz.getAnnotation(WorkflowInfo.class);

        if (annotation == null) {
            return "UNKNOWN";
        }
        return annotation.name() + " (v" + annotation.version() + ")";
    }

    /**
     * Discovers and executes enabled deployment checks.
     *
     * Reads custom annotations using Reflection,
     * invokes static check methods and sorts results
     * by ascending priority.
     *
     * @param clazz The class containing the check methods.
     * @return Check results ordered by priority.
     */
    static List<CheckResult> runChecks(Class<?> clazz) throws ReflectiveOperationException {
        ArrayList<CheckResult> results = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            CheckRule anoCheckRule = method.getAnnotation(CheckRule.class);
            Severity  anoSeverity = method.getAnnotation(Severity.class);
            Label[] anoLabels = method.getAnnotationsByType(Label.class);
            List<String> anoLabelsList = new ArrayList<>();
            if (anoSeverity == null || anoCheckRule == null || anoLabels == null) {
                continue;
            }
            for (Label anoLabel : anoLabels) {
                String le = anoLabel.value();
                anoLabelsList.add(le);
            }
            if (anoCheckRule.enabled()) {
                boolean passed = (boolean) method.invoke(null);
                CheckResult result = new CheckResult(anoCheckRule.id(), anoCheckRule.priority(),
                        passed, anoSeverity.value(),anoLabelsList);
                results.add(result);
            }
        }
        results.sort(Comparator.comparingInt(r -> r.priority));
        // Question B2
        return results;
    }

    /**
     * Counts failed checks for each severity level.
     *
     * All severity levels present in the results
     * must appear in the map, even with zero failures.
     *
     * @param results The completed check results.
     * @return A map from severity to failure count.
     */
    static Map<Integer, Integer> failuresBySeverity(List<CheckResult> results) {
        return results.stream()
                .collect(Collectors.groupingBy(r -> r.severity,
                        Collectors.summingInt(r -> r.passed ? 0 : 1)));
    }

    public static void main(String[] args) throws Exception {

        Class<?> clazz = DeploymentChecks.class;

        System.out.println(
                "Workflow: " + workflowDescription(clazz)
        );

        List<CheckResult> results = runChecks(clazz);

        System.out.println("===== CHECK RESULTS =====");

        for (CheckResult result : results) {
            System.out.println(result);
        }

        System.out.println("=========================");

        Map<Integer, Integer> statistics =
                failuresBySeverity(results);

        System.out.println("Failed per severity: " + statistics);
    }
}
