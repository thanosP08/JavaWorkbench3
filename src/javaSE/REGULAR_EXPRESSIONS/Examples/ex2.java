package javaSE.REGULAR_EXPRESSIONS.Examples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex2 {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("(ab)(cd)");
        Matcher m = p.matcher("XXabcdeeYY");

        if (m.find()) {
            System.out.println(m.group());
            System.out.println(m.group(1));
            System.out.println(m.group(2));
        }
    }
}
