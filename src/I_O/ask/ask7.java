package I_O.ask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ask7 {
    static File findLargestBinaryFile(String directory) {
        File folder = new File(directory);
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }
        File maxFile = null;
        for (File f : files) {
            if (f.isDirectory()) {
                File result = findLargestBinaryFile(f.getPath());
                if (result != null && (maxFile == null || result.length() > maxFile.length())) {
                    maxFile = result;
                }
            } else if (f.isFile() && f.getName().endsWith(".bin")) {

                if (maxFile == null || f.length() > maxFile.length()) {
                    maxFile = f;
                }
            }
        }
        return maxFile;
    }
    public static void main(String[] args) throws IOException {

        String directory = "src/files/ask7_test";

        new File(directory + "/backup").mkdirs();

        try (FileOutputStream out =
                     new FileOutputStream(directory + "/a.bin")) {
            for (int i = 0; i < 100; i++) {
                out.write(1);
            }
        }

        try (FileOutputStream out =
                     new FileOutputStream(directory + "/backup/b.bin")) {
            for (int i = 0; i < 500; i++) {
                out.write(1);
            }
        }

        File result = findLargestBinaryFile(directory);

        if (result != null) {
            System.out.println("Name: " + result.getName());
            System.out.println("Size: " + result.length());
        } else {
            System.out.println("No binary files found");
        }
    }
}
