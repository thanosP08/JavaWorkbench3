package javaSE.REGULAR_EXPRESSIONS.ask;

import java.lang.annotation.*;
import java.util.*;
import java.util.regex.*;

// Ερώτημα 1
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Documented
@interface BatchInfo {
    String name();
    int version() default 1;
}

// Ερώτημα 2
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Tags.class)
@interface Tag {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Tags {
    Tag[] value();
}

@BatchInfo(name = "Log Analyzer", version = 2)
@Tag("regex")
@Tag("concurrency")
class LogWorker implements Runnable {

    private final String[] logs;
    private int validCount;
    private int largestGroup;

    public LogWorker(String[] logs) {
        this.logs = logs;
    }

    // Ερώτημα 4: RegEx
    static List<String> extractValid(String[] logs) {
        Pattern p = Pattern.compile("LOG.*END");
        List<String> result = new ArrayList<>();

        for (String log : logs) {
            Matcher m = p.matcher(log);

            if (m.matches()) {
                String word =
                        log.substring(3, log.length() - 3);
                result.add(word);
            }
        }

        return result;
    }

    // Ερώτημα 5: LeetCode 49
    @BatchInfo(name = "Anagram Solver", version = 1)
    static List<List<String>> groupAnagrams(
            List<String> words) {

        Map<String, List<String>> map = new HashMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        return new ArrayList<>(map.values());
    }

    // Ερώτημα 6: Thread execution
    @Override
    public void run() {
        List<String> words = extractValid(logs);

        validCount = words.size();

        List<List<String>> groups =
                groupAnagrams(words);

        for (List<String> group : groups) {
            largestGroup = Math.max(
                    largestGroup, group.size()
            );
        }
    }

    public int getValidCount() {
        return validCount;
    }

    public int getLargestGroup() {
        return largestGroup;
    }
}

public class ask2 {

    public static void main(String[] args)
            throws Exception {

        String[] batchA = {
                "LOGabcEND",
                "LOGbcaEND",
                "LOGcabEND",
                "LOGxyEND",
                "LOGyxEND",
                "ABCabcEND",
                "LOGbadFAIL"
        };

        String[] batchB = {
                "LOGaabEND",
                "LOGabaEND",
                "LOGabbEND",
                "LOGbbaEND",
                "LOGzzEND",
                "LOGEND",
                "BADzzEND"
        };

        // Ερώτημα 3: javaSE.Annotations.Reflection
        BatchInfo classInfo = LogWorker.class.getAnnotation(BatchInfo.class);

        System.out.println("Class: " + classInfo.name() + " v" + classInfo.version());

        BatchInfo methodInfo =
                LogWorker.class
                        .getDeclaredMethod(
                                "groupAnagrams", List.class
                        )
                        .getAnnotation(BatchInfo.class);

        System.out.println(
                "Method: " + methodInfo.name()
                        + " v" + methodInfo.version()
        );

        Tag[] tags = LogWorker.class
                .getAnnotationsByType(Tag.class);

        for (Tag tag : tags) {
            System.out.println("Tag: " + tag.value());
        }

        // Ερώτημα 6: Threads
        LogWorker worker1 = new LogWorker(batchA);
        LogWorker worker2 = new LogWorker(batchB);

        Thread t1 = new Thread(worker1);
        Thread t2 = new Thread(worker2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(
                "Worker 1: " + worker1.getValidCount()
                        + " valid, largest group = "
                        + worker1.getLargestGroup()
        );

        System.out.println(
                "Worker 2: " + worker2.getValidCount()
                        + " valid, largest group = "
                        + worker2.getLargestGroup()
        );
    }
}
