
package java2_3_1.ask;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

// TODO 1: Meta-annotations
@interface SessionInfo {
    String name();
    int threshold() default 3;
}

// TODO 2: Annotation στην κλάση
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

    // TODO 2: Annotation στη μέθοδο
    static int longestUniqueWindow(int[] pages) {

        // TODO 4: Αλγόριθμος

        return 0;
    }

    int getResult() {
        return result;
    }

    // TODO 2: Deprecated annotation
    static int oldScore(int[] pages) {
        return pages.length;
    }
}

public class ask6 {

    // TODO 2: SuppressWarnings
    public static void main(String[] args)
            throws Exception {

        int[] a = {4, 7, 4, 9, 2, 7};
        int[] b = {1, 2, 3, 1, 2, 3};

        SessionWorker w1 = new SessionWorker(a);
        SessionWorker w2 = new SessionWorker(b);

        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w2);

        // TODO 5: Εκτέλεση threads

        // TODO 3: Reflection

        // TODO 3: Έλεγχος αποτελεσμάτων

    }
}
