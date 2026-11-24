
package I_O.ask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ask1 {

    static void writeNumbers(String path, ArrayList<Integer> list) {
        try (FileOutputStream out = new FileOutputStream(path)) {

            for (Integer integer : list) {
                out.write(integer);
            }
        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
    }
    static int sumNumbers(String path) {
        int sum = 0;
        try (FileInputStream in = new FileInputStream(path)) {
            int value;
            while ((value = in.read()) != -1) {
                sum += value;
            }
        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
        return sum;
    }
    public static void main(String[] args) {

        String path = "src/I_O/files/file_ask1.bin";
        ArrayList<Integer> list = new ArrayList<>(
                List.of(1,2,3,4,5,6,7,8,9,10)
        );
        writeNumbers(path, list);

        int sum = sumNumbers(path);
        System.out.println(sum);
    }
}
