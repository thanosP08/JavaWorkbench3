package REGULAR_EXPRESSIONS.Examples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex4 {
    public static void main(String[] args) {
        //1
        System.out.println("abbb".matches("ab+"));    // true
        System.out.println("abb".matches("ab{3,}"));  // false
        System.out.println("abab".matches("(ab){2}"));// true
        System.out.println("abc".matches("ab?c"));    // true
        //2
        String text = "a1b2b";
        for (String regex : new String[]{"a.*b", "a.*?b", "a.*+b"}) {
            Matcher m = Pattern.compile(regex).matcher(text);
            System.out.println(m.find() ? m.group() : "NO MATCH");
        }
        //3
        System.out.println("file.txt".matches("file.txt"));   // true
        System.out.println("fileXtxt".matches("file.txt"));   // true
        System.out.println("fileXtxt".matches("file\\.txt")); // false
        System.out.println("file.txt".matches("file\\.txt")); // true
        //4
        System.out.println("b".matches("[abc]"));       // true
        System.out.println("d".matches("[abc]"));       // false
        System.out.println("d".matches("[^abc]"));      // true
        System.out.println("H".matches("[a-zA-Z]"));    // true
        System.out.println("e".matches("[a-z&&[def]]"));// true
        //5
        String regex = "\\d+";
        System.out.println("12345".matches("\\d+")); // true
        System.out.println("12a45".matches("\\d+")); // false
        System.out.println("ABC_12".matches("\\w+"));// true
        //6
        regex = "[a-zA-Z]{3}[0-9]{2}";
        System.out.println("ABC12".matches(regex)); // true
        System.out.println("AB12".matches(regex));  // false
        System.out.println("ABC123".matches(regex));// false
        //7
        System.out.println("cat".matches("cat|dog")); // true
        System.out.println("dog".matches("cat|dog")); // true
        System.out.println("bird".matches("cat|dog"));// false
        //8
        Pattern p = Pattern.compile("([a-z]+):([0-9]+)");
        Matcher m = p.matcher("anna:25");

        if (m.matches()) {
            System.out.println(m.group(0)); // anna:25
            System.out.println(m.group(1)); // anna
            System.out.println(m.group(2)); // 25
        }
        //9
        text = "OK\nERROR\nDONE";
        //Pattern
        p = Pattern.compile("^ERROR$", Pattern.MULTILINE);
        //Matcher
        m = p.matcher(text);
        System.out.println(m.find()); // true
        //10
        System.out.println("START abc".matches("\\ASTART.*")); // true
        System.out.println("abc END".matches(".*END\\z"));     // true
    }
}
