import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvKudeatzailea {
    public static void menua() {
        System.out.println("CsvKudeatzailea menua");
    }

    public static void CSVFitxategiaJSONraBihurtu() {
        String csvFile = "./fitxategiak/csv/fitxategia.csv";
        String jsonFile = "./fitxategiak/json/fitxategia.json";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter writer = new FileWriter(jsonFile)) {

            br.readLine();
            String line;
            List<String> jsonObjects = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String obj = String.format(
                    "  {\"NAN\": \"%s\", \"Izena\": \"%s\", \"Abizena\": \"%s\"}",
                    values[0], values[1], values[2]
                );
                jsonObjects.add(obj);
            }

            String json = "[\n" + String.join(",\n", jsonObjects) + "\n]";
            writer.write(json);

            System.out.println("Konbertsioa Eginda! Egindako Artxiboa: " + jsonFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
