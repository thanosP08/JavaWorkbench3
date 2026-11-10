
package javaSE.practice3;

import java.util.regex.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ScanRule1 {}

public class code_pad {

    static class Alert {
        String source, detail;

        Alert(String source, String detail) {
            this.source = source;
            this.detail = detail;
        }

        @Override
        public String toString() {
            return source + "(" + detail + ")";
        }
    }

    @ScanRule1
    static Alert checkAuth(String log) {
        Pattern regex = Pattern.compile("^(AUTH);user=([a-z|0-9]{3,12});stuts=FAIL");
        Matcher matcher = regex.matcher(log);

        if (matcher.find()) {
            return new Alert(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    static void test(String log, String expected) {
        Alert alert = checkAuth(log);
        String actual = alert == null ? "null" : alert.toString();

        System.out.println("Input:    " + log);
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println("PASS: " + expected.equals(actual));
        System.out.println("--------------------------");
    }

    public static void main(String[] args) {
        test("AUTH;user=alex;status=FAIL", "AUTH(alex)");
        test("AUTH;user=anna_1;status=FAIL", "AUTH(anna_1)");
        test("AUTH;user=alex;status=OK", "null");
        test("AUTH;user=ab;status=FAIL", "null");
        test("AUTH;user=1alex;status=FAIL", "null");
        test("API;user=alex;status=FAIL", "null");
        test("AUTH;user=alex;stuts=FAIL", "null");
        test("AUTH;user=alex;status=FAIL;extra", "null");
    }
}
