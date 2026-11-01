import java.util.regex.*;

public class ex1 {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("ab.*cd");
        Matcher matcher = pattern.matcher("ab123cd");
        boolean result = matcher.matches();
        System.out.println(result);
    }
}