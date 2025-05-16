Jan,10
Anna,20
Piotr,15

public class DataEntry<T extends Number> {
    private String label;
    private T value;

    public DataEntry(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public T getValue() { return value; }
}

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class CSVReader {
    public static List<DataEntry<Integer>> readCSV(String path) throws IOException {
        return Files.lines(Paths.get(path))
            .map(line -> line.split(","))
            .map(parts -> new DataEntry<>(parts[0], Integer.parseInt(parts[1])))
            .collect(Collectors.toList());
    }
}


import java.io.*;
import java.util.List;

public class SVGGenerator {
    public static void generateBarChart(List<DataEntry<Integer>> data, String filename) throws IOException {
        int width = 500;
        int barHeight = 40;
        int max = data.stream().mapToInt(DataEntry::getValue).max().orElse(1);

        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("<svg xmlns='http://www.w3.org/2000/svg' width='" + width + "' height='" + (data.size() * barHeight) + "'>");

            for (int i = 0; i < data.size(); i++) {
                DataEntry<Integer> entry = data.get(i);
                int barWidth = (int)((entry.getValue() * 1.0 / max) * (width - 100));

                int y = i * barHeight;

                out.printf("<rect x='0' y='%d' width='%d' height='%d' fill='steelblue' />%n", y, barWidth, barHeight - 5);
                out.printf("<text x='%d' y='%d' fill='black'>%s (%d)</text>%n", barWidth + 5, y + 20, entry.getLabel(), entry.getValue());
            }

            out.println("</svg>");
        }
    }
}




import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<DataEntry<Integer>> data = CSVReader.readCSV("data.csv");
        SVGGenerator.generateBarChart(data, "output.svg");
        System.out.println("Wygenerowano plik SVG.");
    }
}