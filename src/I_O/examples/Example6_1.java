package I_O.examples;

import java.io.File;

public class Example6_1 {
    public static void main(String[] args) {

        File file = new File("src/i_o/files/data.bin");

        System.out.println("Exists: " + file.exists());
        System.out.println("Is file: " + file.isFile());
        System.out.println("Is directory: " + file.isDirectory());
        System.out.println("Name: " + file.getName());
        System.out.println("Size: " + file.length());
    }
}
