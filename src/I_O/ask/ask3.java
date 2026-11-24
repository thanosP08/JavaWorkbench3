
package I_O.ask;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ask3 {

    static int countBytes(String path) throws IOException {
        int count = 0;

        try (FileInputStream fis = new FileInputStream(path)) {
            int value;

            while ((value = fis.read()) != -1) {
                count++;
            }
        }

        return count;
    }

    static int filterEvenBytes(String source, String destination)
            throws IOException {

        int count = 0;

        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {

            int value;

            while ((value = fis.read()) != -1) {
                if (value % 2 == 0) {
                    fos.write(value);
                    count++;
                }
            }
        }

        return count;
    }

    static boolean verifyFilter(String source, String destination)
            throws IOException {

        try (FileInputStream fis1 = new FileInputStream(source);
             FileInputStream fis2 = new FileInputStream(destination)) {

            int value;

            while ((value = fis1.read()) != -1) {
                if (value % 2 == 0) {
                    if (value != fis2.read()) {
                        return false;
                    }
                }
            }

            // Check that destination has no extra bytes.
            return fis2.read() == -1;
        }
    }

    public static void main(String[] args) throws IOException {

        String path1 = "src/I_O/files/file1_ask3.bin";
        String path2 = "src/I_O/files/file2_ask3.bin";

        System.out.println(filterEvenBytes(path1, path2));

        int bytes = countBytes(path2);
        System.out.println(bytes);

        System.out.println(verifyFilter(path1, path2));
    }
}
