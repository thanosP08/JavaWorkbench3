package I_O.examples;

import java.io.File;
import java.io.IOException;

public class Example6_5 {
    public static void main(String[] args) throws IOException {

        File file = new File("src/i_o/files/data.bin");

        System.out.println(file.isAbsolute());
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
    }
}