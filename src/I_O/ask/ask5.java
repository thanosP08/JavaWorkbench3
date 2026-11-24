
package i_o;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BinaryOperation {
    String action();
    int version();
}

public class ask5 {

    static class Student {
        String name;
        int age;
        double grade;
        boolean active;

        Student(String name, int age, double grade, boolean active) {
            this.name = name;
            this.age = age;
            this.grade = grade;
            this.active = active;
        }
    }

    static boolean isValidStudent(String data) {
        Pattern regex = Pattern.compile(
                "^([a-zA-Z]{2,12});([0-9]{2,3});([0-9]{1,2}(?:\\.[0-9]+)?);(true|false)$"
        );

        Matcher m = regex.matcher(data);

        if (!m.matches()) {
            return false;
        }

        int age = Integer.parseInt(m.group(2));
        double grade = Double.parseDouble(m.group(3));

        return age >= 16 && age <= 100
                && grade >= 0 && grade <= 10;
    }

    @BinaryOperation(action = "WRITE", version = 1)
    static int saveStudents(String path, List<String> students)
            throws IOException {

        int count = 0;

        // First pass: count valid students.
        for (String data : students) {
            if (isValidStudent(data)) {
                count++;
            }
        }

        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(path))) {

            // File header
            dos.writeInt(count);

            // Second pass: write valid students.
            for (String data : students) {
                if (!isValidStudent(data)) {
                    continue;
                }

                String[] parts = data.split(";");

                dos.writeUTF(parts[0]);
                dos.writeInt(Integer.parseInt(parts[1]));
                dos.writeDouble(Double.parseDouble(parts[2]));
                dos.writeBoolean(Boolean.parseBoolean(parts[3]));
            }
        }

        return count;
    }

    @BinaryOperation(action = "READ", version = 1)
    static List<Student> loadStudents(String path)
            throws IOException {

        List<Student> students = new ArrayList<>();

        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(path))) {

            // Read the file header.
            int count = dis.readInt();

            if (count < 0) {
                throw new IOException("Invalid student count");
            }

            // Read exactly count students.
            for (int i = 0; i < count; i++) {
                String name = dis.readUTF();
                int age = dis.readInt();
                double grade = dis.readDouble();
                boolean active = dis.readBoolean();

                students.add(new Student(name, age, grade, active));
            }

            // Check for unexpected extra bytes.
            if (dis.read() != -1) {
                throw new IOException("Extra bytes detected");
            }

        } catch (EOFException e) {
            throw new IOException("Incomplete binary file", e);
        }

        return students;
    }

    static void printBinaryOperations() {
        Method[] methods = ask5.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BinaryOperation.class)) {
                BinaryOperation annotation = method.getAnnotation(BinaryOperation.class);
                System.out.println(
                        method.getName()
                                + " -> " + annotation.action()
                                + ", version " + annotation.version()
                );
            }
        }
    }

    public static void main(String[] args) throws IOException {

        String path = "src/i_o/files/file_ask5.bin";

        List<String> data = List.of(
                "Nikos;20;8.5;true",
                "Maria;21;9.2;true",
                "ABC123;19;7.5;false",
                "Giorgos;22;12.5;true",
                "Eleni;20;7.8;false",
                "Petros;-5;6.0;true"
        );

        int saved = saveStudents(path, data);

        System.out.println("Saved students: " + saved);

        List<Student> students = loadStudents(path);

        System.out.println("\nLoaded students:");

        for (Student student : students) {
            System.out.println(
                    student.name + " | "
                            + student.age + " | "
                            + student.grade + " | "
                            + student.active
            );
        }

        System.out.println("\nBinary operations:");
        printBinaryOperations();
    }
}
