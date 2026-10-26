import java.util.Arrays;
import java.util.List;

public class ArraysExamples {

    public static void main(String[] args) {

        // --------------------------------------------------
        // 1. Arrays.toString()
        // Μετατρέπει έναν μονοδιάστατο πίνακα σε String
        // ώστε να μπορούμε να τον εκτυπώσουμε σωστά.
        // --------------------------------------------------

        int[] numbers = {1, 2, 3, 4, 5};

        System.out.println(Arrays.toString(numbers));


        // --------------------------------------------------
        // 2. Arrays.deepToString()
        // Χρησιμοποιείται κυρίως για πολυδιάστατους πίνακες.
        // --------------------------------------------------

        int[][] matrix = {
                {1, 2},
                {3, 4}
        };

        System.out.println(Arrays.deepToString(matrix));


        // --------------------------------------------------
        // 3. Arrays.hashCode()
        // Επιστρέφει hash code με βάση τα στοιχεία
        // ενός μονοδιάστατου πίνακα.
        // --------------------------------------------------

        int hash = Arrays.hashCode(numbers);

        System.out.println("HashCode: " + hash);


        // --------------------------------------------------
        // 4. Arrays.deepHashCode()
        // Αντίστοιχο hashCode για πολυδιάστατους πίνακες.
        // --------------------------------------------------

        int deepHash = Arrays.deepHashCode(matrix);

        System.out.println("Deep HashCode: " + deepHash);


        // --------------------------------------------------
        // 5. Arrays.copyOf()
        // Δημιουργεί νέο πίνακα αντιγράφοντας από index 0.
        // Το δεύτερο όρισμα είναι το μέγεθος του νέου πίνακα.
        // --------------------------------------------------

        int[] copy = Arrays.copyOf(numbers, 3);

        System.out.println(
                "copyOf: " + Arrays.toString(copy)
        );


        // Αν ζητήσουμε μεγαλύτερο μέγεθος,
        // οι επιπλέον θέσεις γεμίζουν με default τιμές.

        int[] biggerCopy = Arrays.copyOf(numbers, 8);

        System.out.println(
                "Bigger copy: " + Arrays.toString(biggerCopy)
        );


        // --------------------------------------------------
        // 6. Arrays.copyOfRange()
        // Αντιγράφει συγκεκριμένο range.
        // from συμπεριλαμβάνεται, to δεν συμπεριλαμβάνεται.
        // [from, to)
        // --------------------------------------------------

        int[] range =
                Arrays.copyOfRange(numbers, 1, 4);

        System.out.println(
                "copyOfRange: " + Arrays.toString(range)
        );


        // --------------------------------------------------
        // 7. Arrays.fill()
        // Γεμίζει ΟΛΟ τον πίνακα με την ίδια τιμή.
        // --------------------------------------------------

        int[] fillArray = new int[5];

        Arrays.fill(fillArray, 7);

        System.out.println(
                "fill: " + Arrays.toString(fillArray)
        );


        // --------------------------------------------------
        // 8. Arrays.fill(array, from, to, value)
        // Γεμίζει μόνο συγκεκριμένο range [from, to).
        // --------------------------------------------------

        int[] partialFill = {1, 2, 3, 4, 5};

        Arrays.fill(partialFill, 1, 4, 0);

        System.out.println(
                "Partial fill: " + Arrays.toString(partialFill)
        );


        // --------------------------------------------------
        // 9. Arrays.equals()
        // Επιστρέφει true αν οι δύο πίνακες έχουν
        // ίδια στοιχεία στην ίδια σειρά.
        // --------------------------------------------------

        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        int[] c = {1, 2, 4};

        System.out.println(
                "a equals b: " + Arrays.equals(a, b)
        );

        System.out.println(
                "a equals c: " + Arrays.equals(a, c)
        );


        // --------------------------------------------------
        // 10. Arrays.deepEquals()
        // Το ίδιο, αλλά για πολυδιάστατους πίνακες.
        // --------------------------------------------------

        int[][] matrix2 = {
                {1, 2},
                {3, 4}
        };

        System.out.println(
                "deepEquals: " +
                        Arrays.deepEquals(matrix, matrix2)
        );


        // --------------------------------------------------
        // 11. Arrays.mismatch()
        // Επιστρέφει το πρώτο index όπου οι δύο πίνακες
        // έχουν διαφορετικό στοιχείο.
        //
        // Αν είναι ίδιοι, επιστρέφει -1.
        // --------------------------------------------------

        int[] x = {1, 2, 3, 4};
        int[] y = {1, 2, 9, 4};

        int mismatch = Arrays.mismatch(x, y);

        System.out.println(
                "First mismatch index: " + mismatch
        );


        // --------------------------------------------------
        // 12. Arrays.compare()
        // Κάνει λεξικογραφική σύγκριση δύο πινάκων.
        //
        // < 0 -> πρώτος μικρότερος
        // = 0 -> ίσοι
        // > 0 -> πρώτος μεγαλύτερος
        // --------------------------------------------------

        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 5};

        int result = Arrays.compare(arr1, arr2);

        System.out.println(
                "Compare result: " + result
        );


        // --------------------------------------------------
        // 13. Arrays.compareUnsigned()
        // Συγκρίνει αριθμούς θεωρώντας τους unsigned.
        // Χρήσιμο κυρίως σε byte/int/long περιπτώσεις.
        // --------------------------------------------------

        int[] unsigned1 = {-1};
        int[] unsigned2 = {1};

        System.out.println(
                "Unsigned compare: " +
                        Arrays.compareUnsigned(
                                unsigned1,
                                unsigned2
                        )
        );


        // --------------------------------------------------
        // 14. Arrays.asList()
        // Δημιουργεί List από array / στοιχεία.
        //
        // Η λίστα έχει fixed size.
        // Μπορούμε να αλλάξουμε υπάρχον στοιχείο,
        // αλλά όχι να κάνουμε add/remove.
        // --------------------------------------------------

        String[] namesArray = {
                "Nikos",
                "Maria",
                "Giorgos"
        };

        List<String> names =
                Arrays.asList(namesArray);

        System.out.println(names);

        names.set(0, "Thanos");

        System.out.println(names);


        // --------------------------------------------------
        // 15. Arrays.stream()
        // Δημιουργεί Stream από έναν πίνακα.
        // --------------------------------------------------

        int[] streamArray = {1, 2, 3, 4, 5};

        int sum =
                Arrays.stream(streamArray)
                        .sum();

        System.out.println(
                "Stream sum: " + sum
        );


        // --------------------------------------------------
        // 16. Arrays.stream(array, start, finish)
        // Stream μόνο για συγκεκριμένο range [start, finish).
        // --------------------------------------------------

        int partialSum =
                Arrays.stream(streamArray, 1, 4)
                        .sum();

        System.out.println(
                "Partial stream sum: " + partialSum
        );


        // --------------------------------------------------
        // 17. Arrays.parallelPrefix()
        // Εφαρμόζει σωρευτικά μία πράξη.
        //
        // [1, 2, 3, 4]
        //
        // γίνεται:
        // [1, 3, 6, 10]
        //
        // επειδή:
        // 1
        // 1+2 = 3
        // 3+3 = 6
        // 6+4 = 10
        // --------------------------------------------------

        int[] prefix =
                {1, 2, 3, 4};

        Arrays.parallelPrefix(
                prefix,
                (first, second) -> first + second
        );

        System.out.println(
                "Prefix sum: " +
                        Arrays.toString(prefix)
        );


        // --------------------------------------------------
        // 18. parallelPrefix με πολλαπλασιασμό
        //
        // [1, 2, 3, 4]
        // ->
        // [1, 2, 6, 24]
        // --------------------------------------------------

        int[] product =
                {1, 2, 3, 4};

        Arrays.parallelPrefix(
                product,
                (first, second) -> first * second
        );

        System.out.println(
                "Prefix product: " +
                        Arrays.toString(product)
        );
    }
}