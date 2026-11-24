package I_O.ask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ask6 {

    static void prepareFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        if (!file.isFile()) {
            throw new IOException("Invalid file: " + path);
        }
    }

    static List<File> findBinaryFiles(String directory) {
        List<File> list = new ArrayList<>();
        File folder = new File(directory);
        File[] files = folder.listFiles();
        if (files == null) {
            return list;
        }
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".bin")) {
                list.add(f);
            }
        }
        return list;
    }

    //G
    static long totalBinarySize(String directory) {
        long sum =0;
        List<File> files = findBinaryFiles(directory);
        for (File f : files) {
            sum += f.length();
        }
        return sum;
    }

    // D
    static List<File> findAllBinaryFiles(String directory) {
        List<File> list = new ArrayList<>();
        File folder = new File(directory);

        if (!folder.isDirectory()) {
            return list;
        }

        helperD(list, folder);
        return list;
    }

    static void helperD(List<File> list, File f) {

        if (f.isFile() && f.getName().endsWith(".bin")) {
            list.add(f);
        }

        if (f.isDirectory()) {
            File[] files = f.listFiles();

            if (files == null) {
                return;
            }

            for (File file : files) {
                helperD(list, file);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        String directory = "src/files/ask6_test";

        prepareFile(directory + "/data.bin");
        prepareFile(directory + "/backup/users.bin");
        prepareFile(directory + "/backup/notes.txt");

        List<File> files = findAllBinaryFiles(directory);

        for (File f : files) {
            System.out.println(f.getName());
        }

        System.out.println("Total files: " + files.size());
    }
}
