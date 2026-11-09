
package javaSE.Annotations.ask;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@interface SessionInfo {
    String name();
    int threshold() default 3;
}

@SessionInfo(name = "Session Diversity", threshold = 4)
class SessionWorker implements Runnable {

    private final int[] pages;
    private int result;

    SessionWorker(int[] pages) {
        this.pages = pages;
    }

    @Override
    public void run() {
        result = longestUniqueWindow(pages);
    }

    @SessionInfo(name = "Unique Window Algorithm", threshold = 3)
    static int longestUniqueWindow(int[] pages) {
        HashSet<Integer> set = new HashSet<>();
        int left = 0;
        int max = 0;
        for (int right = 0; right < pages.length; right++) {
            while (set.contains(pages[right])) {
                set.remove(pages[left]);
                left++;
            }
            set.add(pages[right]);

            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    int getResult() {
        return result;
    }

    @Deprecated
    static int oldScore(int[] pages) {
        return pages.length;
    }
}

public class ask6 {

    @SuppressWarnings("deprecation")
    public static void main(String[] args)
            throws Exception {

        int[] a = {4, 7, 4, 9, 2, 7};
        int[] b = {1, 2, 3, 1, 2, 3};

        SessionWorker w1 = new SessionWorker(a);
        SessionWorker w2 = new SessionWorker(b);

        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // 1. Παίρνουμε το annotation της κλάσης
        SessionInfo classInfo =
                SessionWorker.class.getAnnotation(SessionInfo.class);

        System.out.println("Class: " + classInfo.name());
        System.out.println("Threshold: " + classInfo.threshold());


        // 2. Βρίσκουμε τη μέθοδο μέσω javaSE.Annotations.Reflection
        Method method = SessionWorker.class.getDeclaredMethod(
                "longestUniqueWindow", int[].class
        );

        // 3. Παίρνουμε το annotation της μεθόδου
        SessionInfo methodInfo =
                method.getAnnotation(SessionInfo.class);

        System.out.println("Method: " + methodInfo.name());


        // 4. Χρησιμοποιούμε το threshold της ΚΛΑΣΗΣ
        int threshold = classInfo.threshold();

        System.out.println(
                "Worker 1: " +
                        (w1.getResult() >= threshold ? "PASS" : "REVIEW")
        );

        System.out.println(
                "Worker 2: " +
                        (w2.getResult() >= threshold ? "PASS" : "REVIEW")
        );

    }
}
