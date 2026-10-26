package NumericLiterals;

import java.util.Locale;

public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        // ============================================================
        // 1. ΕΠΙΣΤΗΜΟΝΙΚΗ ΓΡΑΦΗ ΑΡΙΘΜΩΝ (SCIENTIFIC NOTATION)
        // ============================================================

        System.out.println("===== 1. SCIENTIFIC NOTATION =====");

        /*
         * Το γράμμα e ή E χρησιμοποιείται για να γράφουμε
         * δυνάμεις του 10.
         *
         * Γενική μορφή:
         *
         * aeb = a * 10^b
         *
         * Παραδείγματα:
         *
         * 1e3   = 1 * 10^3  = 1000
         * 2e4   = 2 * 10^4  = 20000
         * 3e-2  = 3 * 10^-2 = 0.03
         *
         * ΠΡΟΣΟΧΗ:
         * Το e εδώ ΔΕΝ είναι ο αριθμός Euler (2.71828...).
         *
         * Στη Java οι αριθμοί που χρησιμοποιούν
         * αυτή τη γραφή είναι τύπου double,
         * εκτός αν βάλουμε το επίθημα f ή F.
         */

        double a = 1e3;
        double b = 2.5e4;
        double c = 3e-2;
        double d = 1e-6;
        double e = 1E9;
        double f = 5e+2;

        System.out.println("1e3 = " + a);
        System.out.println("2.5e4 = " + b);
        System.out.println("3e-2 = " + c);
        System.out.println("1e-6 = " + d);
        System.out.println("1E9 = " + e);
        System.out.println("5e+2 = " + f);

        /*
         * Το 1e9 ισοδυναμεί αριθμητικά με:
         *
         * 1_000_000_000.0
         *
         * Είναι πολύ χρήσιμο για μετατροπές χρόνου.
         */

        System.out.println(
                "1e9 == 1000000000.0: "
                        + (1e9 == 1_000_000_000.0)
        );


        // ============================================================
        // 2. ΚΑΤΩ ΠΑΥΛΑ ΣΤΟΥΣ ΑΡΙΘΜΟΥΣ (_)
        // ============================================================

        System.out.println("\n===== 2. UNDERSCORES =====");

        /*
         * Η κάτω παύλα χρησιμοποιείται μόνο
         * για να κάνει ευανάγνωστους τους αριθμούς.
         *
         * Δεν επηρεάζει την αριθμητική τιμή.
         *
         * Οι παρακάτω αριθμοί είναι ίσοι.
         */

        int num1 = 1000000;
        int num2 = 1_000_000;
        int num3 = 10_00_000;

        System.out.println(num1);
        System.out.println(num2);
        System.out.println(num3);

        /*
         * Μπορούμε να χρησιμοποιήσουμε κάτω παύλες
         * και σε δεκαδικούς αριθμούς.
         */

        double decimal = 123_456.789_123;

        System.out.println(decimal);

        /*
         * ΛΑΝΘΑΣΜΕΝΕΣ ΜΟΡΦΕΣ:
         *
         * int x = _1000;
         * int y = 1000_;
         * double z = 3_.14;
         * double w = 3._14;
         *
         * Η κάτω παύλα δεν επιτρέπεται
         * στην αρχή ή στο τέλος ενός αριθμού
         * ούτε ακριβώς δίπλα στην υποδιαστολή.
         */


        // ============================================================
        // 3. ΑΡΙΘΜΗΤΙΚΟΙ ΤΥΠΟΙ ΚΑΙ ΕΠΙΘΗΜΑΤΑ (L, F, D)
        // ============================================================

        System.out.println("\n===== 3. NUMERIC TYPES =====");

        /*
         * Ακέραιος χωρίς επίθημα:
         *
         * 100 -> int
         *
         * Ακέραιος με L:
         *
         * 100L -> long
         *
         * Δεκαδικός χωρίς επίθημα:
         *
         * 3.14 -> double
         *
         * Δεκαδικός με f ή F:
         *
         * 3.14f -> float
         *
         * Δεκαδικός με d ή D:
         *
         * 3.14d -> double
         */

        int integerNumber = 100;

        long longNumber = 100L;

        float floatNumber = 3.14f;

        double doubleNumber = 3.14;

        double explicitDouble = 3.14d;

        System.out.println("int: " + integerNumber);
        System.out.println("long: " + longNumber);
        System.out.println("float: " + floatNumber);
        System.out.println("double: " + doubleNumber);
        System.out.println("double με d: " + explicitDouble);

        /*
         * Χρησιμοποιούμε long όταν χρειαζόμαστε
         * ακέραιες τιμές μεγαλύτερες από τα όρια του int.
         *
         * Ένας αριθμός όπως ο παρακάτω
         * δεν χωράει σε int.
         *
         * Το L δηλώνει ότι ο αριθμός είναι long.
         */

        long bigNumber = 5_000_000_000L;

        System.out.println("Big number: " + bigNumber);

        /*
         * Η επιστημονική γραφή με e δεν δημιουργεί
         * αυτόματα long.
         *
         * double x = 1e9;   // Σωστό
         * long y = 1e9;     // Λάθος: double προς long
         *
         * long z = 1_000_000_000L; // Σωστό
         */


        // ============================================================
        // 4. ΑΚΕΡΑΙΑ ΚΑΙ ΔΕΚΑΔΙΚΗ ΔΙΑΙΡΕΣΗ
        // ============================================================

        System.out.println("\n===== 4. DIVISION =====");

        /*
         * Αν και οι δύο τελεστέοι είναι ακέραιοι,
         * η Java πραγματοποιεί ακέραια διαίρεση.
         *
         * 7 / 2 = 3
         *
         * Το δεκαδικό μέρος απορρίπτεται.
         */

        double result1 = 7 / 2;

        /*
         * Επειδή το 2.0 είναι double,
         * η διαίρεση γίνεται με δεκαδικούς.
         *
         * 7 / 2.0 = 3.5
         */

        double result2 = 7 / 2.0;

        double result3 = 7.0 / 2;

        System.out.println("7 / 2 = " + result1);
        System.out.println("7 / 2.0 = " + result2);
        System.out.println("7.0 / 2 = " + result3);

        /*
         * ΠΟΛΥ ΣΗΜΑΝΤΙΚΟ:
         *
         * Η Java πρώτα υπολογίζει την παράσταση
         * και μετά αποθηκεύει το αποτέλεσμα.
         *
         * Επομένως:
         *
         * double x = 7 / 2;
         *
         * δεν επιστρέφει 3.5.
         *
         * Επιστρέφει 3.0.
         */


        // ============================================================
        // 5. ΜΕΤΑΤΡΟΠΗ ΤΥΠΩΝ (TYPE CASTING)
        // ============================================================

        System.out.println("\n===== 5. TYPE CASTING =====");

        /*
         * Μερικές φορές θέλουμε να μετατρέψουμε
         * έναν αριθμό από έναν τύπο σε άλλον.
         *
         * Αυτό ονομάζεται Type Casting.
         */

        double value = 8.75;

        int converted = (int) value;

        /*
         * Η μετατροπή double σε int
         * απορρίπτει το δεκαδικό μέρος.
         *
         * Δεν γίνεται στρογγυλοποίηση.
         *
         * 8.75 -> 8
         */

        System.out.println("Original: " + value);
        System.out.println("Converted: " + converted);

        /*
         * Μπορούμε επίσης να χρησιμοποιήσουμε casting
         * για να αποφύγουμε την ακέραια διαίρεση.
         */

        int x = 7;
        int y = 2;

        double division1 = x / y;
        double division2 = (double) x / y;

        System.out.println("Without casting: " + division1);
        System.out.println("With casting: " + division2);


        // ============================================================
        // 6. ΔΥΑΔΙΚΟ, ΟΚΤΑΔΙΚΟ ΚΑΙ ΔΕΚΑΕΞΑΔΙΚΟ ΣΥΣΤΗΜΑ
        // ============================================================

        System.out.println("\n===== 6. NUMBER SYSTEMS =====");

        /*
         * Η Java επιτρέπει να γράφουμε ακέραιους
         * σε διαφορετικά αριθμητικά συστήματα.
         *
         * 0b -> Δυαδικό σύστημα (βάση 2)
         *
         * 0x -> Δεκαεξαδικό σύστημα (βάση 16)
         *
         * 0 στην αρχή -> Οκταδικό σύστημα (βάση 8)
         *
         * Χωρίς πρόθεμα -> Δεκαδικό σύστημα (βάση 10)
         */

        int binary = 0b1010;

        int hexadecimal = 0xA;

        int octal = 012;

        int decimalNumber = 10;

        /*
         * Όλοι οι παραπάνω αριθμοί έχουν
         * την ίδια δεκαδική τιμή: 10.
         */

        System.out.println("Binary: " + binary);
        System.out.println("Hexadecimal: " + hexadecimal);
        System.out.println("Octal: " + octal);
        System.out.println("Decimal: " + decimalNumber);

        /*
         * Μερικά ακόμη παραδείγματα:
         *
         * 0b1111 = 15
         * 0xFF   = 255
         * 0x10   = 16
         *
         * Προσοχή στους ακέραιους που ξεκινούν με 0:
         * μπορεί να ερμηνευτούν ως οκταδικοί.
         */

        int binaryExample = 0b1111;
        int hexExample = 0xFF;

        System.out.println(binaryExample);
        System.out.println(hexExample);


        // ============================================================
        // 7. ΑΚΡΙΒΕΙΑ FLOAT ΚΑΙ DOUBLE
        // ============================================================

        System.out.println("\n===== 7. FLOATING POINT PRECISION =====");

        /*
         * Οι τύποι float και double αποθηκεύουν
         * αριθμούς κινητής υποδιαστολής.
         *
         * Δεν μπορούν να αναπαραστήσουν
         * όλους τους δεκαδικούς αριθμούς ακριβώς.
         *
         * Για παράδειγμα, η παράσταση:
         *
         * 0.1 + 0.2
         *
         * δεν δίνει απαραίτητα ακριβώς 0.3
         * στην εσωτερική αναπαράσταση double.
         */

        double p = 0.1;
        double q = 0.2;

        double sum = p + q;

        System.out.println("0.1 + 0.2 = " + sum);

        /*
         * Το double έχει μεγαλύτερη ακρίβεια
         * από το float.
         *
         * Αυτό δεν σημαίνει ότι είναι
         * απολύτως ακριβές.
         *
         * Για χρηματικά ποσά που απαιτούν
         * ακριβή δεκαδική αριθμητική,
         * συχνά χρησιμοποιούμε BigDecimal.
         */


        // ============================================================
        // 8. ΜΟΡΦΟΠΟΙΗΣΗ ΑΡΙΘΜΩΝ ΜΕ PRINTF
        // ============================================================

        System.out.println("\n===== 8. PRINTF =====");

        /*
         * Το printf μάς επιτρέπει να ελέγχουμε
         * τον τρόπο εμφάνισης των αριθμών.
         *
         * %d   -> Ακέραιος
         * %f   -> Δεκαδικός
         * %.2f -> Δεκαδικός με δύο ψηφία
         * %.4f -> Δεκαδικός με τέσσερα ψηφία
         * %e   -> Επιστημονική γραφή
         * %n   -> Αλλαγή γραμμής
         *
         * Το Locale.US χρησιμοποιεί τελεία
         * ως δεκαδικό διαχωριστικό.
         */

        double pi = Math.PI;

        System.out.printf(
                Locale.US,
                "PI: %.2f%n",
                pi
        );

        System.out.printf(
                Locale.US,
                "PI: %.4f%n",
                pi
        );

        System.out.printf(
                Locale.US,
                "Scientific notation: %e%n",
                1_000_000.0
        );

        System.out.printf(
                Locale.US,
                "Integer: %d%n",
                150
        );


        // ============================================================
        // 9. ΕΦΑΡΜΟΓΗ ΣΤΟΝ ΧΡΟΝΟ ΤΩΝ THREADS
        // ============================================================

        System.out.println("\n===== 9. TIME CONVERSION =====");

        /*
         * Το System.nanoTime() επιστρέφει
         * έναν αριθμό τύπου long.
         *
         * Χρησιμοποιείται για τη μέτρηση
         * χρονικών διαστημάτων.
         *
         * 1 second = 1_000_000_000 nanoseconds
         *
         * Δηλαδή:
         *
         * 1 second = 1e9 nanoseconds
         */

        long nanoseconds = 2_500_000_000L;

        /*
         * Εδώ κάνουμε ακέραια διαίρεση,
         * επειδή και οι δύο τελεστέοι
         * είναι ακέραιοι τύποι.
         *
         * Άρα χάνουμε το δεκαδικό μέρος.
         */

        double wrongSeconds =
                nanoseconds / 1_000_000_000L;

        /*
         * Εδώ χρησιμοποιούμε double
         * για να διατηρήσουμε τα δεκαδικά.
         */

        double correctSeconds =
                nanoseconds / 1e9;

        System.out.println("Wrong: " + wrongSeconds);
        System.out.println("Correct: " + correctSeconds);


        // ============================================================
        // 10. ΠΡΑΓΜΑΤΙΚΗ ΜΕΤΡΗΣΗ ΧΡΟΝΟΥ
        // ============================================================

        System.out.println("\n===== 10. NANO TIME TEST =====");

        /*
         * Αποθηκεύουμε τον χρόνο πριν από
         * την εκτέλεση μιας εργασίας.
         *
         * Το nanoTime() δεν επιστρέφει
         * την πραγματική ώρα του ρολογιού.
         *
         * Μας ενδιαφέρει η διαφορά
         * ανάμεσα σε δύο μετρήσεις.
         */

        long start = System.nanoTime();

        /*
         * Προσομοιώνουμε μια εργασία
         * που περιμένει περίπου 150 milliseconds.
         *
         * 150 milliseconds = 0.15 seconds.
         */

        Thread.sleep(150);

        /*
         * Παίρνουμε δεύτερη μέτρηση
         * μετά την ολοκλήρωση της αναμονής.
         */

        long end = System.nanoTime();

        /*
         * Υπολογίζουμε πρώτα τα nanoseconds
         * που πέρασαν και μετά τα μετατρέπουμε
         * σε seconds.
         */

        long elapsedNanoseconds = end - start;

        double elapsedSeconds =
                elapsedNanoseconds / 1e9;

        System.out.println(
                "Elapsed nanoseconds: " + elapsedNanoseconds
        );

        System.out.printf(
                Locale.US,
                "Elapsed seconds: %.6f%n",
                elapsedSeconds
        );

        /*
         * Το αποτέλεσμα θα είναι περίπου 0.15s,
         * αλλά όχι υποχρεωτικά ακριβώς 0.15s.
         *
         * Η Thread.sleep() δεν εγγυάται
         * απόλυτα ακριβή χρόνο αφύπνισης.
         */


        // ============================================================
        // 11. ΟΡΙΑ ΑΚΕΡΑΙΩΝ ΤΥΠΩΝ
        // ============================================================

        System.out.println("\n===== 11. INTEGER LIMITS =====");

        /*
         * Ο int χρησιμοποιεί 32 bits.
         * Ο long χρησιμοποιεί 64 bits.
         *
         * Κάθε τύπος έχει ένα ελάχιστο
         * και ένα μέγιστο επιτρεπτό όριο.
         */

        System.out.println(
                "INT MIN: " + Integer.MIN_VALUE
        );

        System.out.println(
                "INT MAX: " + Integer.MAX_VALUE
        );

        System.out.println(
                "LONG MIN: " + Long.MIN_VALUE
        );

        System.out.println(
                "LONG MAX: " + Long.MAX_VALUE
        );

        /*
         * ΠΡΟΣΟΧΗ ΣΤΟ OVERFLOW!
         *
         * Αν ξεπεράσουμε το μέγιστο όριο
         * ενός ακέραιου τύπου,
         * το αποτέλεσμα μπορεί να υπερχειλίσει.
         */

        int max = Integer.MAX_VALUE;

        System.out.println(
                "INT MAX + 1 = " + (max + 1)
        );


        System.out.println("\n===== END OF LESSON =====");
    }
}
