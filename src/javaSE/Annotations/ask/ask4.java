package javaSE.Annotations.ask;

import java.lang.annotation.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@interface ReportInfo {
    String title();
    int version() default 1;
}

@ReportInfo(title = "Sales Ranking", version = 2)
class SalesReport {

    static Map<String, Integer> frequencies(String[] codes) {

        Map<String, Integer> dictionary = new HashMap<>();

        for (String code : codes) {
            dictionary.put(code, dictionary.getOrDefault(code, 0) + 1);
        }

        return dictionary;
    }

    static TreeMap<Integer, TreeSet<String>> ranking(
            Map<String, Integer> counts) {

        TreeMap<Integer, TreeSet<String>> ranking =
                new TreeMap<>(Comparator.reverseOrder());

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {

            String product = entry.getKey();
            int sales = entry.getValue();

            ranking.putIfAbsent(sales, new TreeSet<>());
            ranking.get(sales).add(product);
        }

        return ranking;
    }
}

public class ask4 {

    public static void main(String[] args) {

        String[] sales = {
                "P12", "P04", "P12", "P08",
                "P04", "P12", "P08", "P04"
        };

        ReportInfo info =
                SalesReport.class.getAnnotation(ReportInfo.class);

        System.out.println(info.title() + " v" + info.version());

        Map<String, Integer> counts =
                SalesReport.frequencies(sales);

        TreeMap<Integer, TreeSet<String>> result =
                SalesReport.ranking(counts);

        if (result.isEmpty()) {
            System.out.println("No sales");
            return;
        }

        for (Map.Entry<Integer, TreeSet<String>> entry : result.entrySet()) {

            for (String product : entry.getValue()) {
                System.out.println(product + " -> " + entry.getKey());
            }
        }
    }
}