package I_O.examples;

import java.nio.file.Path;

public class example7_2 {
    public static void main(String[] args) {
        Path path = Path.of("src/files/data.bin");

        System.out.println(path.getFileName());
        System.out.println(path.getParent());
        System.out.println(path.getNameCount());
        System.out.println(path.getName(0));
        System.out.println(path.getName(1));
        System.out.println(path.getName(2));

        Path p1 = Path.of("src/files");
        Path p2 = Path.of("src/files/backup/users.bin");

        System.out.println(p1.relativize(p2));
        System.out.println(p2.relativize(p1));

        p1 = Path.of("src/files/backup");
        p2 = Path.of("src/users/data.bin");

        System.out.println(p1.relativize(p2));
        System.out.println(p2.relativize(p1));
    }
}
