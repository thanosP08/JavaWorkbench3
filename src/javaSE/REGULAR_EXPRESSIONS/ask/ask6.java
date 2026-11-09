package javaSE.REGULAR_EXPRESSIONS.ask;


import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;

public class ask6 {
    static List<String> extractErrorUsers(String[] logs) {
        // Ερώτημα 1
        ArrayList<String> errorUsers = new ArrayList<>();
        Pattern p = Pattern.compile("USER:(.*);STATUS:(.*)");
        for(String log : logs) {
            Matcher m = p.matcher(log);
            if (m.matches() && m.group(2).equals("ERROR")) {
                errorUsers.add(m.group(1));
            }
        }
        return errorUsers;
    }

    static boolean regexMatch(String text, String regex) {
        int n = text.length();
        int m = regex.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;

        for (int i = n; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                boolean firstMatch = i < n && (text.charAt(i) == regex.charAt(j) || regex.charAt(j) == '.');
                if (j + 1 < m && regex.charAt(j + 1) == '*') {
                    dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }


    public static void main(String[] args) throws InterruptedException {
        String[] batchA = {
                "USER:anna;STATUS:ERROR",
                "USER:nikos;STATUS:OK",
                "USER:anna;STATUS:ERROR",
                "USER:maria;STATUS:ERROR",
                "USER:gio;STATUS:ERROR",
                "INVALID"
        };
        String[] batchB = {
                "USER:leo;STATUS:ERROR",
                "USER:eva;STATUS:ERROR",
                "USER:eva;STATUS:ERROR",
                "USER:leo;STATUS:OK",
                "USER:eva;STATUS:ERROR"
        };
        System.out.println(extractErrorUsers(batchA));

        List<Long> timesA = List.of(1L, 2L);
        List<Long> timesB = List.of(2L, 3L);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.nanoTime();
        System.out.println("Simulation started!");

        Future<?> taskA = scheduler.schedule(()->{
            long taskStart = System.nanoTime();
            Pattern p = Pattern.compile("USER:(.*);STATUS:(.*)");
            for (String item : batchA) {
                Matcher m = p.matcher(item);
                if (m.matches() && !m.group(2).equals("ERROR")) {
                    System.out.println(m.group(1) + " -> accept IN A");
                }
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task A | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        },timesA.get(0),TimeUnit.SECONDS);

        Future<?> taskB = scheduler.schedule(()->{
            long taskStart = System.nanoTime();
            Pattern p = Pattern.compile("USER:(.*);STATUS:(.*)");
            for (String item : batchB) {
                Matcher m = p.matcher(item);
                if (m.matches() && !m.group(2).equals("ERROR")) {
                    System.out.println(m.group(1) + " -> accept IN B");
                }
            }
            long taskEnd = System.nanoTime();
            double start = (taskStart - startTime) / 1e9;
            double end = (taskEnd - startTime) / 1e9;
            double duration = (taskEnd - taskStart) / 1e9;
            System.out.printf("Task B | Start: %.2fs | End: %.2fs | Duration: %.2fs%n", start, end, duration);
        },timesB.get(0),TimeUnit.SECONDS);

        scheduler.shutdown();
        if (!scheduler.awaitTermination(6, TimeUnit.SECONDS)) scheduler.shutdownNow();
    }
}
