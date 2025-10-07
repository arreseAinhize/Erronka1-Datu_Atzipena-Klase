import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonKudeatzailea {

    private static final String JSON_DIR = "./fitxategiak/json/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Scanner sc = new Gehigarriak().in;

    public static void menua() {
        String aukera;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. JSON fitxategia sortu.");
            System.out.println("2. JSON fitxategiak bistaratu.");
            System.out.println("3. JSON fitxategia irakurri.");
            System.out.println("4. JSON fitxategian datuak gehitu");
            System.out.println("5. JSON fitxategia eguneratu.");
            System.out.println("6. JSON fitxategia ezabatu.");
            System.out.println(Gehigarriak.Urdina + "7. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = sc.next();

            if (Filtroak.isnumeric(aukera)) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("JSON fitxategia sortu");
                        jsonFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiakBistaratu();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("JSON fitxategian datuak gehitu");
                        jsonFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiaEguneratu();
                        System.out.println("JSON fitxategia eguneratu");
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        jsonFitxategiaEzabatu();
                        System.out.println("JSON fitxategia ezabatu");
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Atzera!");
                        MainApp.main(null);
                        return;
                    default:
                        System.out.println(Gehigarriak.Gorria + "Aukera okerra, saiatu berriro.");
                        break;
                }
            } else {
                System.out.println(Gehigarriak.Gorria + "Zenbaki bat sartu behar duzu!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("7"));
    }

    public static void jsonFitxategiakBistaratu() {
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .json-ak:" + Gehigarriak.Urdina);
        try {
            Files.list(Paths.get(JSON_DIR))
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.json$", "")));
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }

    public static void jsonFitxategiaSortu() {
        String fileName;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName);
            String path = JSON_DIR + fileName + ".json";

            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, zehiatu beste batekin." + Gehigarriak.RESET);
                continue;
            }

            try {
                // Sortu JSON hutsa (lista hutsa)
                List<Pertsona> emptyList = new ArrayList<>();
                try (Writer writer = new FileWriter(path)) {
                    gson.toJson(emptyList, writer);
                }
                System.out.println(Gehigarriak.Berdea + "Fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
            } catch (IOException e) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da sortu: " + path + Gehigarriak.RESET);
                e.printStackTrace();
            }
            break;
        } while (true);
    }

    public static void jsonFitxategiaIrakurri() {
        jsonFitxategiakBistaratu();
        System.out.print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = JSON_DIR + fileName + ".json";

        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Pertsona>>() {}.getType();
            List<Pertsona> pertsonak = gson.fromJson(reader, listType);
            if (pertsonak == null || pertsonak.isEmpty()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago edo ez da ondo irakurri." + Gehigarriak.RESET);
                return;
            }
            System.out.println(Gehigarriak.Berdea + "Fitxategiko datuak:" + Gehigarriak.RESET);
            for (Pertsona p : pertsonak) {
                System.out.println(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println(Gehigarriak.Gorria + "Fitxategia ez da aurkitu: " + path + Gehigarriak.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void jsonFitxategiaGehitu() {
        jsonFitxategiakBistaratu();
        System.out.print(Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = JSON_DIR + fileName + ".json";

        List<Pertsona> pertsonak = new ArrayList<>();
        // Kargatu fitxategiko datuak
        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Pertsona>>() {}.getType();
            pertsonak = gson.fromJson(reader, listType);
            if (pertsonak == null) {
                pertsonak = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea fitxategia irakurtzean." + Gehigarriak.RESET);
            return;
        }

        sc.nextLine(); // buffer garbitu
        String nan;
        // NAN balidazioa + existitzen den ala ez
        do {
            System.out.print("NAN (8 zenbaki + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu." + Gehigarriak.RESET);
                continue;
            }
            boolean exists = pertsonak.stream().anyMatch(p -> p.getNan().equalsIgnoreCase(nan));
            if (exists) {
                System.out.println(Gehigarriak.Gorria + "NAN hori jada existitzen da fitxategian." + Gehigarriak.RESET);
                continue;
            }
            break;
        } while (true);

        // Izena balidazioa
        String izena;
        do {
            System.out.print("Izena: ");
            izena = sc.nextLine();
            if (!Filtroak.isIzena(izena)) {
                System.out.println(Gehigarriak.Gorria + "Izena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(izena));

        // Abizena balidazioa
        String abizena;
        do {
            System.out.print("Abizena: ");
            abizena = sc.nextLine();
            if (!Filtroak.isIzena(abizena)) {
                System.out.println(Gehigarriak.Gorria + "Abizena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(abizena));

        // Adina balidazioa
        int adina;
        do {
            System.out.print("Adina: ");
            String adinaStr = sc.nextLine();
            if (!Filtroak.isAdina(adinaStr)) {
                System.out.println(Gehigarriak.Gorria + "Adina okerra. Zenbaki bakarrik sartu behar da." + Gehigarriak.RESET);
                continue;
            }
            adina = Integer.parseInt(adinaStr);
            break;
        } while (true);

        // Helbidea
        System.out.print("Helbidea: ");
        String helbidea = sc.nextLine();
        helbidea = Filtroak.removeSpaces(helbidea);

        // Pertsona objektua sortu
        Pertsona p = new Pertsona(nan, izena, abizena, adina, helbidea);
        pertsonak.add(p);

        // Gorde JSON fitxategian
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(pertsonak, writer);
            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira fitxategian." + Gehigarriak.RESET);
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu fitxategian." + Gehigarriak.RESET);
            e.printStackTrace();
        }
    }

    public static void jsonFitxategiaEguneratu() {
        jsonFitxategiakBistaratu();
        System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = JSON_DIR + fileName + ".json";

        List<Pertsona> pertsonak;
        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Pertsona>>() {}.getType();
            pertsonak = gson.fromJson(reader, listType);
            if (pertsonak == null) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia hutsik dago edo ez da ondo irakurri." + Gehigarriak.RESET);
                return;
            }
        } catch (IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea fitxategia irakurtzean." + Gehigarriak.RESET);
            return;
        }

        sc.nextLine(); // buffer garbitu

        // NAN aukeratu eguneratzeko
        String nan;
        Pertsona pertsonaEguneratu = null;
        do {
            System.out.print(Gehigarriak.Horia + "Eguneratu nahi duzun pertsonaren NAN-a sartu: " + Gehigarriak.RESET);
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(Gehigarriak.Gorria + "NAN okerra. 8 zenbaki + 1 letra izan behar du." + Gehigarriak.RESET);
                continue;
            }
            for (Pertsona p : pertsonak) {
                if (p.getNan().equalsIgnoreCase(nan)) {
                    pertsonaEguneratu = p;
                    break;
                }
            }
            if (pertsonaEguneratu == null) {
                System.out.println(Gehigarriak.Gorria + "Ez da NAN hori aurkitu." + Gehigarriak.RESET);
            }
        } while (pertsonaEguneratu == null);

        // Datu berriak sartzeko balidazioa
        String izena;
        do {
            System.out.print("Izena: ");
            izena = sc.nextLine();
            if (!Filtroak.isIzena(izena)) {
                System.out.println("Izena okerra. Letra bakarrik sartu.");
            }
        } while (!Filtroak.isIzena(izena));

        String abizena;
        do {
            System.out.print("Abizena: ");
            abizena = sc.nextLine();
            if (!Filtroak.isIzena(abizena)) {
                System.out.println("Abizena okerra. Letra bakarrik sartu.");
            }
        } while (!Filtroak.isIzena(abizena));

        int adina;
        do {
            System.out.print("Adina: ");
            String adinaStr = sc.nextLine();
            if (!Filtroak.isAdina(adinaStr)) {
                System.out.println("Adina okerra. Zenbaki bakarrik sartu.");
                continue;
            }
            adina = Integer.parseInt(adinaStr);
            break;
        } while (true);

        System.out.print("Helbidea: ");
        String helbidea = sc.nextLine();
        helbidea = Filtroak.removeSpaces(helbidea);

        // Pertsona eguneratu
        pertsonaEguneratu.setIzena(izena);
        pertsonaEguneratu.setAbizena(abizena);
        pertsonaEguneratu.setAdina(adina);
        pertsonaEguneratu.setHelbidea(helbidea);

        // Gorde berriro JSON fitxategian
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(pertsonak, writer);
            System.out.println(Gehigarriak.Berdea + "Fitxategia eguneratu da!" + Gehigarriak.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void jsonFitxategiaEzabatu() {
        jsonFitxategiakBistaratu();
        System.out.print(Gehigarriak.Horia + "Zein fitxategi ezabatu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = JSON_DIR + fileName + ".json";

        File fitx = new File(path);
        if (fitx.exists()) {
            if (fitx.delete()) {
                System.out.println(Gehigarriak.Berdea + "Fitxategia ondo ezabatu da: " + path + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da ezabatu." + Gehigarriak.RESET);
            }
        } else {
            System.out.println(Gehigarriak.Gorria + "Fitxategia ez da aurkitu: " + path + Gehigarriak.RESET);
        }
    }
}
