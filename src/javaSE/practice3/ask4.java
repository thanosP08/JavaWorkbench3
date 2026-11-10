
package javaSE.practice3;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;


public class ask4 {

    static class LogEntry {
        String service;
        String level;
        String message;

        LogEntry(String service, String level, String message) {
            this.service = service;
            this.level = level;
            this.message = message;
        }

        @Override
        public String toString() {
            return service + "|" + level + "|" + message;
        }
    }

    // Question A: Validate and parse log messages using RegEx.
    static List<LogEntry> parseLogs(String[] logs) {
        ArrayList<LogEntry> passed = new ArrayList<>();
        Pattern regex = Pattern.compile("^(AUTH|API|DB)\\|(INFO|WARN|ERROR)\\|([a-z ]+)$");
        for (String log : logs) {
            Matcher matcher = regex.matcher(log);
            if (matcher.matches()) {
                passed.add(new LogEntry(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return passed;
    }

    // Question B: Find all non-overlapping literal matches.
    /**
     * Finds all non-overlapping occurrences of a keyword in a text.
     *
     * The algorithm scans the text from left to right and stores
     * the starting index of every matching occurrence.
     *
     * When a match is found, the search skips the entire keyword
     * to prevent overlapping matches.
     *
     * Examples:
     *   findLiteralOccurrences("hello hello", "hello") -> [0, 6]
     *   findLiteralOccurrences("aaaaa", "aa")          -> [0, 2]
     *   findLiteralOccurrences("banana", "ana")        -> [1]
     *   findLiteralOccurrences("passed", "sse")        -> [2]
     *   findLiteralOccurrences("java", "python")       -> []
     *
     * @param text The original text to search.
     * @param keyword The exact sequence of characters to find.
     * @return A list containing the starting indices of all matches.
     *
     * Time complexity: O(n * m), where n is the text length
     * and m is the keyword length.
     */
    static List<Integer> findLiteralOccurrences(String text, String keyword) {
        ArrayList<Integer> result = new ArrayList<>();

        int n1 = text.length();
        int n2 = keyword.length();

        // Return an empty list if the keyword is empty
        // or longer than the original text.
        if (n1 < n2 || n2 == 0) return result;

        int k = 0;

        // Search while the remaining text can contain the keyword.
        while (k <= n1 - n2) {

            // Compare the current substring with the keyword.
            if (text.substring(k, k + n2).equals(keyword)) {
                result.add(k);

                // Skip the matched characters to avoid overlaps.
                k += n2;
            } else {
                // No match: move forward by one character.
                k++;
            }
        }

        return result;
    }


    // Question C: Count keyword occurrences in error logs by service.
    /**
     * Counts non-overlapping keyword occurrences in ERROR logs,
     * grouped by service.
     *
     * Examples:
     *   API: "timeout timeout" + "timeout" -> 3
     *   DB:  "connection timeout"          -> 1
     *   AUTH: "login failed"               -> 0
     *
     * @param entries The parsed log entries.
     * @param keyword The exact text to search for.
     * @return A map containing each service and its keyword count.
     */

    /**
     * Counts all non-overlapping keyword occurrences in ERROR logs.
     *
     * The results are grouped by service.
     * Each occurrence is counted, even when a message
     * contains the same keyword multiple times.
     *
     * Examples for keyword "timeout":
     *   API: "timeout timeout" + "timeout" -> 3
     *   DB: "connection timeout"          -> 1
     *   AUTH: "login failed"              -> 0
     *
     * @param entries The list of parsed log entries.
     * @param keyword The exact text to search for.
     * @return A map containing the total keyword count per service.
     */
    static Map<String, Integer> countKeywordInErrors(
            List<LogEntry> entries, String keyword) {

        return entries.stream()
                // Keep only error logs.
                .filter(le -> le.level.equals("ERROR"))

                // Group errors by service and sum keyword occurrences.
                .collect(Collectors.groupingBy(
                        le -> le.service,
                        Collectors.summingInt(
                                le -> findLiteralOccurrences(
                                        le.message, keyword
                                ).size()
                        )
                ));
    }


    public static void main(String[] args) {
        String[] logs = {
                "AUTH|ERROR|login failed",
                "API|INFO|request accepted",
                "API|ERROR|timeout timeout",
                "DB|WARN|slow query",
                "AUTH|ERROR|invalid password",
                "API|ERROR|timeout",
                "DB|ERROR|connection timeout",
                "API|ERROR|timeouttimeout",
                "AUTH|WARN|slow login",
                "API|INFO|request complete",
                "OTHER|ERROR|ignored",
                "API|DEBUG|timeout",
                "DB|ERROR|"
        };

        // Test question A.
        List<LogEntry> entries = parseLogs(logs);
        System.out.println("Valid logs: " + entries);
        System.out.println("Valid count: " + entries.size());

        // Test question B.
        System.out.println(findLiteralOccurrences("aaaaa", "aa"));
        System.out.println(findLiteralOccurrences("banana", "ana"));
        System.out.println(findLiteralOccurrences("timeout timeout", "timeout"));

        // Test question C.
        Map<String, Integer> result =
                countKeywordInErrors(entries, "timeout");

        System.out.println("Keyword counts: " + result);
    }
}
