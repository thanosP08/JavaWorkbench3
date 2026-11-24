
package I_O.examples;

import java.io.File;
import java.io.IOException;

public class Example6_3 {

    public static void main(String[] args) throws IOException {

        File folder = new File("src/i_o/files/backup/2026");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "data.bin");

        boolean created = file.createNewFile();

        System.out.println("Created: " + created);
        System.out.println("Exists: " + file.exists());
    }
}
