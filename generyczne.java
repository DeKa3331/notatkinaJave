import java.util.*;
import java.util.stream.Stream;

public class CustomList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    // Węzeł listy jednokierunkowej
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) {
            this.value = value;
        }
    }

    // Zadanie 1

    // Dodaje wartość na koniec listy
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) { // lista pusta
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Zwraca wartość z końca listy
    public T getLast() {
        if (tail == null) throw new NoSuchElementException("Lista jest pusta");
        return tail.value;
    }

    // Dodaje wartość na początek listy
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // Zwraca wartość z początku listy
    public T getFirst() {
        if (head == null) throw new NoSuchElementException("Lista jest pusta");
        return head.value;
    }

    // Zwraca i usuwa element z początku listy
    public T removeFirst() {
        if (head == null) throw new NoSuchElementException("Lista jest pusta");
        T val = head.value;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return val;
    }

    // Zwraca i usuwa element z końca listy
    public T removeLast() {
        if (tail == null) throw new NoSuchElementException("Lista jest pusta");

        if (head == tail) { // tylko 1 element
            T val = tail.value;
            head = tail = null;
            size--;
            return val;
        }

        // Znajdź przedostatni węzeł
        Node<T> current = head;
        while (current.next != tail) {
            current = current.next;
        }

        T val = tail.value;
        tail = current;
        tail.next = null;
        size--;
        return val;
    }

    // Zadanie 2 - metody AbstractList

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    // Zadanie 3 - iterator i stream

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T val = current.value;
                current = current.next;
                return val;
            }
        };
    }

    public Stream<T> stream() {
        // Stream ze wszystkich elementów listy
        return Stream.iterate(head, Objects::nonNull, n -> n.next)
                .map(n -> n.value);
    }
}
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    // Zwraca listę obiektów dokładnie klasy clazz
    public static <T> List<T> filterByClass(List<T> list, Class<?> clazz) {
        return list.stream()
                .filter(e -> e != null && e.getClass().equals(clazz))
                .collect(Collectors.toList());
    }

    // Zwraca listę obiektów, które są instancjami (dziedziczą) klasy clazz
    public static <T> List<T> filterBySuperClass(List<T> list, Class<?> clazz) {
        return list.stream()
                .filter(e -> e != null && clazz.isAssignableFrom(e.getClass()))
                .collect(Collectors.toList());
    }
}
import java.util.List;
import java.util.function.Predicate;

public class Predykaty {

    // Predykat zwracający true, jeśli x jest w otwartym przedziale (min, max)
    public static <T extends Comparable<T>> Predicate<T> inOpenInterval(T min, T max) {
        return x -> x.compareTo(min) > 0 && x.compareTo(max) < 0;
    }

    // Liczy elementy listy spełniające predykat inOpenInterval
    public static <T extends Comparable<T>> long countInOpenInterval(List<T> list, T min, T max) {
        Predicate<T> pred = inOpenInterval(min, max);
        return list.stream()
                .filter(pred)
                .count();
    }
}
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Comparators {

    // Porównuje dwie kolekcje pod względem liczby elementów
    public static <T> Comparator<Collection<T>> bySize() {
        return Comparator.comparingInt(Collection::size);
    }

    // Porównuje dwie kolekcje liczb pod względem sumy elementów
    public static <N extends Number & Comparable<N>> Comparator<Collection<N>> bySum() {
        return (c1, c2) -> {
            double sum1 = c1.stream().mapToDouble(Number::doubleValue).sum();
            double sum2 = c2.stream().mapToDouble(Number::doubleValue).sum();
            return Double.compare(sum1, sum2);
        };
    }
}





AbstractList
Klasa bazowa dla list w Javie, wymaga nadpisania metod get(int) i size(). Dzięki temu dostajesz implementacje innych metod kolekcji "za darmo".

Iterator
Definiuje jak przejść kolejno po elementach kolekcji. W naszej liście chodzi o przechodzenie po węzłach.

Stream
Strumień elementów do przetwarzania funkcyjnego (filtracja, mapowanie, redukcja). Używamy Stream.iterate() do generowania strumienia od głowy listy aż do końca.

Class<?> i isAssignableFrom
Klasa Class reprezentuje typ w Javie. Metoda isAssignableFrom pozwala sprawdzić, czy obiekt dziedziczy po danej klasie lub implementuje interfejs.

Predicate<T>
Interfejs funkcyjny do testowania warunku logicznego. Tworzymy predykaty do filtrowania elementów.

Comparator
Obiekt do porównywania dwóch elementów. Pomaga sortować kolekcje.
