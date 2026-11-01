package REGULAR_EXPRESSIONS.ask;
import java.util.*;
import java.util.regex.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TaskInfo {
    String name();
}

@TaskInfo(name = "Message Processor")
class BatchWorker implements Runnable {
    String name, s;
    int time_in_seconds;

    public BatchWorker(String s, int time_in_seconds, String name) {
        this.name = name;
        this.s = s;
        this.time_in_seconds = time_in_seconds;
    }

    static List<String> extract(String[] logs) {
        Pattern p = Pattern.compile("MSG.*END");
        List<String> result = new ArrayList<>();
        for (String log : logs) {
            Matcher matcher = p.matcher(log);
            if (matcher.matches()) {
                result.add(log.substring(3, log.length() - 3));
            }
        }
        return result;
    }

    static int firstUniqueIndex(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) return i;
        }
        return -1;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time_in_seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("Worker " + name + ": " + firstUniqueIndex(s));
    }
}

public class ask3 {
    public static void main(String[] args) throws Exception {
        String[] batchA = {"MSGabcEND", "MSGaabEND", "MSGxxyyEND", "BADabcEND"};
        String[] batchB = {"MSGaaaaEND", "MSGabcaEND", "MSGEND", "BADxyzEND"};

        System.out.println("Batch A: " + BatchWorker.extract(batchA));
        System.out.println("Batch B: " + BatchWorker.extract(batchB));
        System.out.println("First unique: " + BatchWorker.firstUniqueIndex("aabbcdce"));

        BatchWorker w1 = new BatchWorker("aabbbtvoovol", 1, "chatGPT");
        BatchWorker w2 = new BatchWorker("aabvl", 2, "gemini");
        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("completed");
        TaskInfo info = BatchWorker.class.getAnnotation(TaskInfo.class);
        if (info != null) System.out.println("Metadata: " + info.name());
    }
}