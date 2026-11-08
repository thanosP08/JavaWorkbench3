package REGULAR_EXPRESSIONS.Examples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex3 {
    public static void main(String[] args) {
        Pattern p1 = Pattern.compile("ERROR");
        Matcher m1 = p1.matcher("OK ERROR X ERROR");
        while (m1.find()) {
            System.out.println(m1.start() + " - " + m1.end() + " : " + m1.group());
        }

        Pattern p2 = Pattern.compile("(ab)(cd)");
        Matcher m2 = p2.matcher("XXabcdYY");
        if (m2.find()) {
            System.out.println(m2.group());      // abcd
            System.out.println(m2.group(1));     // ab
            System.out.println(m2.group(2));     // cd
            System.out.println(m2.groupCount()); // 2
        }

//        String s = "abc123";
//        System.out.println(s.matches("abc"));           // false
//        System.out.println(Pattern.compile("abc").matcher(s).find()); // true
    }
}
