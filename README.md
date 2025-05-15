
# Formatowanie tekstu w Javie (`String.format`)

### Co to jest?
Metoda `String.format()` pozwala tworzyć łańcuchy znaków z dynamicznymi wartościami, używając specjalnych znaczników (specifierów) formatujących.

### Składnia ogólna:
```java
String s = String.format("tekst %specifier", wartość);
```

### Najczęściej używane znaczniki formatu:

| Specyfikator | Typ danych       | Przykład użycia                       | Wynik             |
|--------------|------------------|----------------------------------------|--------------------|
| `%d`         | liczba całkowita | `String.format("%d", 42)`              | "42"              |
| `%f`         | liczba zmiennoprzecinkowa | `String.format("%.2f", 3.14159)`    | "3.14"            |
| `%s`         | tekst (String)   | `String.format("%s", "napis")`         | "napis"           |
| `%c`         | pojedynczy znak  | `String.format("%c", 'A')`             | "A"               |
| `%%`         | znak `%`         | `String.format("Cena: 10%%")`          | "Cena: 10%"       |

### Formatowanie liczb:

#### Dodawanie zer z przodu:
```java
String.format("%02d", 7)    // "07"
String.format("%03d", 5)    // "005"
```

#### Liczby zmiennoprzecinkowe z dokładnością:
```java
String.format("%.1f", 3.456)   // "3.5"
String.format("%.3f", 3.456)   // "3.456"
```

### Szerokość pola:
```java
String.format("%5d", 42)    // "   42"
String.format("%-5d", 42)   // "42   "
```

### Łączenie wielu wartości:
```java
int h = 9, m = 4, s = 7;
String time = String.format("%02d:%02d:%02d", h, m, s);  // "09:04:07"
```

### Porównanie z `System.out.printf()`
```java
System.out.printf("Liczba: %d\n", 100);  // wypisze "Liczba: 100"
```

### Wskazówki:
- `%02d` → liczba całkowita z zerem wiodącym (np. godzina, minuta, sekunda).
- `%.2f` → dwie cyfry po przecinku (np. wartości pieniężne).
- `%n` → nowa linia (lepiej niż `\n` w niektórych systemach).
