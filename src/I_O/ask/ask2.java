package I_O.ask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ask2 {
    static void writeNumbers(String path, ArrayList<Integer> list) {
        try (FileOutputStream out = new FileOutputStream(path)) {

            for (Integer integer : list) {
                out.write(integer);
            }
        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    static void copyFile(String source, String destination) {
        ArrayList<Integer> list = new ArrayList<>();
        int value=0;
        try(FileInputStream in = new FileInputStream(source)) {
            while ((value = in.read()) != -1) {
                list.add(value);
            }
        }catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }

        try(FileOutputStream out = new FileOutputStream(destination)) {
            for (Integer integer : list) {
                out.write(integer);
            }
        }catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    static void printFile(String path) {
        int value=0;
        try(FileInputStream in = new FileInputStream(path)) {
            while ((value = in.read()) != -1) {
                System.out.println(value);
            }
        }catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        String source = "src/I_O/files/file_ask1.bin";
        writeNumbers(source, list);
        String destination = "src/I_O/files/file_ask2.bin";
        copyFile(source, destination);
        printFile(destination);
    }
}
