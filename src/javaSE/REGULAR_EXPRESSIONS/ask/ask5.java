package javaSE.REGULAR_EXPRESSIONS.ask;

import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;

import static java.lang.Math.abs;
import static java.util.Collections.swap;

public class ask5 {
    static List<Integer> findErrors(String log) {
        Pattern p = Pattern.compile("ERROR");
        Matcher m = p.matcher(log);
        List<Integer> result = new ArrayList<>();
        while (m.find()) {
            result.add(m.start());
        }
        return result;
    }
    static void QS(ArrayList<Long> arr,int left,int right) {
        if (left >= right) {
            return;
        }
        int pivot = Par(arr,left,right);
        QS(arr,left,pivot - 1);
        QS(arr,pivot + 1,right);
    }
    static int Par(ArrayList<Long> arr,int left,int right) {
        int i = left-1;
        long pivot = arr.get(right);
        for (int j = left; j<right; j++) {
            if (arr.get(j) <= pivot ) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,right);
        return i+1;
    }

    static List<Long> filterTimes(List<Long> times, long minGap) {
        ArrayList<Long> sorted = new ArrayList<>(times);
        Collections.sort(sorted);
        List<Long> result = new ArrayList<>();
        for (long time : sorted) {
            if (time < 0) continue;
            if (result.isEmpty() || time - result.get(result.size() - 1) >= minGap) {
                result.add(time);
            }
        }
        return result;
    }

//    static List<Long> filterTimes(List<Long> times, long minGap) {
//        HashSet<Long> set = new HashSet<>();
//        for (Long time : times) {
//            if (time >= 0) {
//                set.add(time);
//            }
//        }
//        ArrayList<Long> arr = new ArrayList<>();
//        int i = 0;
//        for (long time : set) {
//            arr.add(time);
//            i++;
//        }
//        QS(arr,0,arr.size()-1);
//        System.out.println(arr);
//        int count_remove = 0;
//        for (int j = 1; j < arr.size()+count_remove; j++) {
//            //System.out.println(arr.get(j-count_remove)+"-"+arr.get(j-1-count_remove));
//            long distance = abs(arr.get(j-count_remove) - arr.get(j-1-count_remove));
//            System.out.println("j is:"+j);
//            if (distance < minGap) {
//                System.out.println("before ,we will remove:" +arr.get(j-count_remove));
//                arr.remove(arr.get(j-count_remove));
//                System.out.println(arr);
//                count_remove++;
//            }
//        }
//        return arr;
//    }

    public static void main(String[] args) throws InterruptedException {
        int num_end = 0,time1=0,time2=0;
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Enter number for the end(num < 15): ");
            String line = in.nextLine();
            try {
                num_end = Integer.parseInt(line);
                if(num_end < 15) {
                    System.out.println("Valid number,good job");
                    break;
                }
                System.out.println("Invalid input...,try again");
            } catch (Exception e) {
                System.out.println("Invalid input...,try again");
            }
        }
        while (true) {
            System.out.println("Enter number for the start of task1(num < num_end): ");
            String line = in.nextLine();
            try {
                time1 = Integer.parseInt(line);
                if(time1 < num_end) {
                    System.out.println("Valid number,good job");
                    break;
                }
            }catch (Exception e) {
                System.out.println("Invalid input...,try again");
            }
        }
        while (true) {
            System.out.println("Enter number for the start of task2(num < num_end): ");
            String line = in.nextLine();
            try {
                time2 = Integer.parseInt(line);
                if(time2 < num_end) {
                    System.out.println("Valid number,good job");
                    break;
                }
            }catch (Exception e) {
                System.out.println("Invalid input...,try again");
            }
        }
        List<Long> timesA = List.of(4L, 0L, 2L, 3L, 5L);
        List<Long> timesB = List.of(1L, 3L, 2L, 5L);
//        String logA = "OK ERROR X ERROR";
//        String logB = "ERROR OK";
        String logA = "OK E X OR";
        String logB = "ER OK";
        //GT ERROR EDW??
//        logA = "S";
//        logB = "B";



        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long start = System.nanoTime();

        ScheduledFuture<?> task1 = scheduler.schedule(()->{
            List<Long> arr1 = filterTimes(timesB,3L);
            if (findErrors(logA).isEmpty()) {
                System.out.println(""+arr1);
            } else  {
                System.out.println("we found errors in task1");
            }
        },time1,TimeUnit.SECONDS);

        ScheduledFuture<?> task2 = scheduler.schedule(()->{
            List<Long> arr2 = filterTimes(timesA,3L);
            if (findErrors(logB).isEmpty()) {
                System.out.println(""+arr2);
            }else  {
                System.out.println("we found errors in task2");
            }
        },time2,TimeUnit.SECONDS);


        // Ερώτημα 3: Δικός σου χρονικός προγραμματισμός
        scheduler.schedule(() -> scheduler.shutdown(), num_end-1, TimeUnit.SECONDS);

        if (!scheduler.awaitTermination(num_end, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }
}

