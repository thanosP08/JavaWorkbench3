package javaSE.REGULAR_EXPRESSIONS.ask;


import java.util.*;
import java.util.regex.*;

public class ask4 {
    static int countValid(String[] codes) {
        Pattern p = Pattern.compile(Pattern.quote("ERR.42"), Pattern.CASE_INSENSITIVE);
        int count = 0;
        for (String c : codes) {
            if (p.matcher(c).matches()) count++;
        }
        return count;
    }

    static String[] splitMessage(String message) {
        Pattern p = Pattern.compile(";");
        return p.split(message);
    }

    static boolean containsError(String message) {
        Pattern p = Pattern.compile("ERROR", Pattern.CASE_INSENSITIVE);
        return p.asPredicate().test(message);
    }

    public static void main(String[] args) {
        String[] codes = {"ERR.42", "err.42", "ERRx42", "WARN.42"};
        System.out.println(countValid(codes));
        System.out.println(Arrays.toString(splitMessage("Alice;Login;Success")));
        System.out.println(containsError("Server ERROR detected"));
        System.out.println(containsError("Servesfsf dg edghege gherror detected")); //true
        System.out.println(containsError("Servesfsf dg edghege gherro detected")); //false
    }
}
