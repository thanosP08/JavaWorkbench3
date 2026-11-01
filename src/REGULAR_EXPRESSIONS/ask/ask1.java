import java.util.regex.*;

public class ask1 {

    static int countValid(String[] codes) {
        Pattern p = Pattern.compile("ORD.*OK");
        int count = 0;
        for (String code : codes) {
            Matcher matcher = p.matcher(code);
            boolean result = matcher.matches();
            if (result) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String[] codes = {
                "ORD123OK",
                "ORDOK",
                "ORDXFAIL",
                "ABC123OK",
                "ORD77OK"
        };
        System.out.println(countValid(codes));
    }
}