package javaSE.Reflection.examples;

class Person {
    public int age;
    private String name;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void greet() {
        System.out.println("Hello " + name);
    }

    private void secret() {
        System.out.println("Private method");
    }
}

public class example2 {
    public static void main(String[] args) {
        //2.1 Τρεις τρόποι να αποκτήσουμε αντικείμενο Class

        //1 Πρώτος τρόπος — getClass()
        Person p = new Person("Alex", 20);
        Class<?> c = p.getClass();
        System.out.println(c.getName());

        //2 Δεύτερος τρόπος — .class
        Class<Person> c2 = Person.class;
        System.out.println(c2.getName()); // Person

        //3 Τρίτος τρόπος — Class.forName()
        try {
            Class<?> c3 = Class.forName("Person");
            System.out.println(c3.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        //Αν η Person ανήκει σε package, πρέπει να χρησιμοποιηθεί το πλήρες όνομα, για παράδειγμα:
        //Class<?> c = Class.forName("model.Person");
        //Αυτό λειτουργεί εφόσον η συγκεκριμένη κλάση είναι διαθέσιμη με αυτό το όνομα.

        //2.2 Τι σημαίνει Class<?>;
            //        Έχουμε:
            Class<?> c_ = Person.class;


            //        Το ? είναι wildcard των Java Generics.
            //
            //        Σημαίνει ότι το c μπορεί να αναφέρεται σε αντικείμενο Class οποιουδήποτε τύπου.

            //        Σύγκρινε:
            Class<Person> c1_ = Person.class;
            Class<?> c2_ = String.class;

            //        Το πρώτο είναι συγκεκριμένα για Person, ενώ το δεύτερο μπορεί να περιγράφει οποιονδήποτε τύπο.
            //        Δεν σημαίνει ότι το c2 περιέχει αντικείμενο Person ή String. Περιέχει πληροφορίες για έναν τύπο.

        //2.3 Τι μπορούμε να ανακαλύψουμε από την Class;

        //        Τι μπορούμε να ανακαλύψουμε από την Class;
        //        Μέθοδος	Λειτουργία
        //        getName()	Πλήρες όνομα της κλάσης
        //        getSimpleName()	Απλό όνομα χωρίς package
        //        getFields()	Public πεδία
        //        getMethods()	Public μέθοδοι
        //        getConstructors()	Public constructors
        //        getPackage()	Package στο οποίο ανήκει
        //        getInterfaces()	Interfaces που υλοποιεί άμεσα
        //        Παράδειγμα:
        Class<?> cq = Person.class;
        System.out.println(cq.getSimpleName());
        for (var method : cq.getMethods()) {
            System.out.print("2.3-"+method.getName());
        }


//     Το πρώτο println εμφανίζει Person.
//     Ο βρόχος εμφανίζει ονόματα public μεθόδων, συμπεριλαμβανομένων και όσων κληρονομούνται από την Object, όπως η toString().
    }
}
