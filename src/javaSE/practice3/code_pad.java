
package javaSE.practice3;

import java.util.*;

public class code_pad {
    static List<String> manualSplit1(String input, String delimiter) {
        ArrayList<String> result = new ArrayList<>();
        String str_helper = "";
        int count = 0;
        char[] chars = input.toCharArray();
        int i = 0;

        for (; i < input.length() - delimiter.length(); i++) {
            String pattern_now = input.substring(i, i + delimiter.length());
            if (pattern_now.equals(delimiter)) {
//                if (!str_helper.equals("")) {
//                    result.add(str_helper);
//                    str_helper = "";
//                }
                result.add(str_helper);
                str_helper = "";
                count = delimiter.length() - 1;
            } else if (count == 0) {
                str_helper += String.valueOf(chars[i]);
            } else {
                count--;
            }
        }
        //last loop
        if (!input.substring(i, input.length()-1 ).equals(delimiter)){
            str_helper = input.substring(i, input.length()-1);
        }
        result.add(str_helper);
        return result;
    }

    static void test(String input, String delimiter, List<String> expected) {
        List<String> actual = manualSplit1(input, delimiter);
        System.out.println("Input: " + input);
        System.out.println("Delimiter: " + delimiter);
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println("PASS: " + actual.equals(expected));
        System.out.println("--------------------------");
    }

    public static void main(String[] args) {
        test("A|ERROR|503", "|", List.of("A", "ERROR", "503"));
        test("java>isthebetter", "isthe", List.of("java>", "better"));
        test("a|b|c", "|", List.of("a", "b", "c"));
        test("A||503|", "|", List.of("A", "", "503", ""));
        test("a|", "|", List.of("a", ""));
        test("|", "|", List.of("", ""));
        test("", "|", List.of(""));
        test("abisthe", "isthe", List.of("ab", ""));
        test("a--b--c", "--", List.of("a", "b", "c"));

        System.out.println(manualSplit1("a--b--c","--"));
    }
}

