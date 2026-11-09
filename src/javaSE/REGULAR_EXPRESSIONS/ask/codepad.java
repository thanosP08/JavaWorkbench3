package javaSE.REGULAR_EXPRESSIONS.ask;

public class codepad {
    static boolean regexMatch(String text, String regex) {
        int n = text.length();
        int m = regex.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[n][m] = true;

        System.out.println("TEXT: " + text);
        System.out.println("REGEX: " + regex);
        System.out.println("dp[" + n + "][" + m + "] = true (δύο κενά Strings)");
        System.out.println("----------------------------");

        for (int i = n; i >= 0; i--) {
            System.out.println("\n===== i = " + i + " =====");
            System.out.println("Text που απομένει: \"" + text.substring(i) + "\"");

            for (int j = m - 1; j >= 0; j--) {
                String remainingRegex = regex.substring(j);
                boolean firstMatch = i < n &&
                        (text.charAt(i) == regex.charAt(j) || regex.charAt(j) == '.');

                System.out.println("\ni=" + i + ", j=" + j);
                System.out.println("Regex που απομένει: \"" + remainingRegex + "\"");
                System.out.println("firstMatch = " + firstMatch);

                if (j + 1 < m && regex.charAt(j + 1) == '*') {
                    boolean skip = dp[i][j + 2];
                    boolean consume = firstMatch && dp[i + 1][j];
                    dp[i][j] = skip || consume;

                    System.out.println("Βρέθηκε * μετά το '" + regex.charAt(j) + "'");
                    System.out.println("Επιλογή 1: παράλειψη -> dp[" + i + "][" + (j + 2) + "] = " + skip);
                    System.out.println("Επιλογή 2: κατανάλωση -> " + consume);
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                    System.out.println("Χωρίς *: " + firstMatch + " && dp[" + (i + 1) + "][" + (j + 1) + "]");
                }
                System.out.println("ΑΠΟΤΕΛΕΣΜΑ: dp[" + i + "][" + j + "] = " + dp[i][j]);
            }
        }

        System.out.println("\n========== ΤΕΛΙΚΟΣ ΠΙΝΑΚΑΣ DP ==========");
        System.out.print("i\\j\t");
        for (int j = 0; j <= m; j++) System.out.print(j + "\t");
        System.out.println();

        for (int i = 0; i <= n; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j <= m; j++) {
                System.out.print((dp[i][j] ? "T" : "F") + "\t");
            }
            System.out.println();
        }
        return dp[0][0];
    }
//    for (int i = n; i >= 0; i--) {
//        for (int j = m - 1; j >= 0; j--) {
//            boolean firstMatch = i < n && (text.charAt(i) == regex.charAt(j) || regex.charAt(j) == '.');
//            if (j + 1 < m && regex.charAt(j + 1) == '*') {
//                dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
//            } else {
//                dp[i][j] = firstMatch && dp[i + 1][j + 1];
//            }
//        }
//    }

    public static void main(String[] args) {
        String text = "tts";
        String regex = "t.*s";

        boolean result = regexMatch(text, regex);
        System.out.println("\n=========================");
        System.out.println("ΤΕΛΙΚΟ ΑΠΟΤΕΛΕΣΜΑ: " + result);
        System.out.println("=========================");
    }
}
