package I_O.ask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ask8 {

    static long calculateSize(File file) {
        if (file.isFile()) return file.length();
        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) return 0;
            for (File f : files) {
                size += calculateSize(f);
            }
        }
        return size;
    }

    static  Map<String, Long> analyzeStorage(String directory) {
        Map<String, Long> map = new HashMap<>();
        File[] files = new File(directory).listFiles();
        if (files == null) return map;
        for (File file : files) {
            if (file.isDirectory()) {
                map.put(file.getName(), calculateSize(file));
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String path = "src/files/ask7_test";
        File file = new File(path);
        System.out.println(calculateSize(file));
        Map<String, Long> map = analyzeStorage(path);
        assert map != null;
        for (String key : map.keySet()) {
            System.out.println("{"+key + " : " + map.get(key)+"}");
        }
    }
}
//--
