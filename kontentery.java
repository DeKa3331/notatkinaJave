import java.time.LocalDate;
import java.util.*;

public class Person implements Comparable<Person> {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Set<Person> children = new HashSet<>();

    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean adopt(Person child) {
        return children.add(child);
    }

    public Person getYoungestChild() {
        return children.stream()
                .max(Comparator.naturalOrder()) // najpóźniej urodzony
                .orElse(null);
    }

    public List<Person> getChildren() {
        List<Person> sorted = new ArrayList<>(children);
        sorted.sort(Comparator.naturalOrder()); // od najstarszego do najmłodszego
        return sorted;
    }

    @Override
    public int compareTo(Person other) {
        return this.birthDate.compareTo(other.birthDate); // wcześniejsza osoba = starsza
    }

    @Override
    public String toString() {
        return getFullName() + " (" + birthDate + ")";
    }
}


import java.util.*;

public class Family {
    private final Map<String, List<Person>> people = new HashMap<>();

    public void add(Person... persons) {
        for (Person person : persons) {
            String key = person.getFullName();
            people.computeIfAbsent(key, k -> new ArrayList<>()).add(person);
        }
    }

    public Person[] get(String fullName) {
        List<Person> matches = people.get(fullName);
        if (matches == null) return new Person[0];
        matches.sort(Comparator.naturalOrder()); // od najstarszej do najmłodszej
        return matches.toArray(new Person[0]);
    }

    public Map<String, List<Person>> getAll() {
        return people;
    }
}



import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Person anna = new Person("Anna", "Nowak", LocalDate.of(1980, 5, 20));
        Person bartek = new Person("Bartek", "Nowak", LocalDate.of(2005, 4, 10));
        Person celina = new Person("Celina", "Nowak", LocalDate.of(2008, 12, 1));
        Person daniel = new Person("Daniel", "Nowak", LocalDate.of(2010, 7, 15));

        anna.adopt(bartek);
        anna.adopt(celina);
        anna.adopt(daniel);

        System.out.println("Najmłodsze dziecko Anny: " + anna.getYoungestChild());
        System.out.println("Dzieci Anny (od najstarszego): " + anna.getChildren());

        Family rodzina = new Family();
        rodzina.add(anna, bartek, celina, daniel);
        rodzina.add(new Person("Anna", "Nowak", LocalDate.of(1990, 1, 1))); // druga Anna Nowak

        System.out.println("\nWszyscy o imieniu 'Anna Nowak':");
        for (Person p : rodzina.get("Anna Nowak")) {
            System.out.println(p);
        }
    }
}
