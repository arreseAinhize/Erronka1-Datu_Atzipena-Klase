import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TxtKudeatzailea {

    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. TXT fitxategia sortu.");
            System.out.println("2. TXT fitxategiak bistaratu.");
            System.out.println("3. TXT fitxategia irakurri.");
            System.out.println("4. TXT fitxategian datuak gehitu");
            System.out.println("5. TXT fitxategia eguneratu.");
            System.out.println("6. TXT fitxategia ezabatu.");
            System.out.println("7. TXT fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "8. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = new Gehigarriak().in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("TXT fitxategia sortu");
                        txtFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiakBistaratu();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu();// erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("TXT fitxategian datuak gehitu");
                        txtFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaEguneratu();
                        System.out.println("TXT fitxategia eguneratu");
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaEzabatu();
                        System.out.println("TXT fitxategia ezabatu");
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        txtFitxategiaCSVraBihurtu();
                        System.out.println("TXT fitxategia CSV formatura bihurtu");
                        break;
                    case "8":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Atzera!");
                        MainApp.main(null);
                        return;
                    default:
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Aukera okerra, saiatu berriro.");
                        System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
                        aukera = new Gehigarriak().in.next();
                        break;
                }
            } else {
                Gehigarriak.kontsolaGarbitu();
                System.out.print(Gehigarriak.Gorria + "Zenbaki bat sartu behar duzu!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("8"));
    }

    private static void txtFitxategiakBistaratu() {
        // .txt fitxategiak bistaratu:
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .txt-ak:" + Gehigarriak.Urdina);
        try {
            java.nio.file.Files.list(java.nio.file.Paths.get("./fitxategiak/txt/"))
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.txt$", "")));
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");

    }

    private static void txtFitxategiaSortu() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = "./fitxategiak/txt/" + fileName + ".txt";

            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, zehiatu beste batekin."
                        + Gehigarriak.RESET);
                continue; // berriro galdetu
            }

            if (ErroreenKudeaketa.fitxategiaSortu(path)) {
                System.out.println(Gehigarriak.Berdea + "Fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da sortu: " + path + Gehigarriak.RESET);
                ErroreenKudeaketa.fitxategiaSortu(path);
            }
            break; // fitxategia ondo sortu bada, irten loop-etik

        } while (true);

    }

    private static void txtFitxategiaIrakurri() {
        String fileName, path;

        // .txt fitxategiak bistaratu:
        txtFitxategiakBistaratu();

        // fitxategi bat irakurri:
        System.out.print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
        fileName = new Gehigarriak().in.next();
        Filtroak.removeSpaces(fileName);
        path = "./fitxategiak/txt/" + fileName + ".txt";
        if (ErroreenKudeaketa.fitxategiaIrakurri(path) == true) {
            // System.out.println(Gehigarriak.Berdea + "Fitxategia ondo irakurri da: " +
            // path + Gehigarriak.RESET);
            try {
                java.util.List<String> lerroak = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
                for (String lerroa : lerroak) {
                    System.out.println(lerroa);
                }
            } catch (java.io.IOException e) {
                System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da irakurri.");
                System.err.println(e.getMessage() + Gehigarriak.RESET);
            }
        } else {
            // Errorea bistaratu:
            System.out.println(Gehigarriak.Gorria + "Fitxategia ez da irakurri: " + path + Gehigarriak.RESET);
            ErroreenKudeaketa.fitxategiaIrakurri(path);
        }
    }

    private static void txtFitxategiaGehitu() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;

        System.out.println("TXT fitxategian datuak gehitu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu
        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        path = "./fitxategiak/txt/" + fileName + ".txt";

        sc.nextLine(); // Scanner buffer garbitu
        String nan, izena, abizena, adinaStr, helbidea;
        int adina;

        // NAN balidazioa + existitzen den ala ez
        do {
            System.out.print("NAN (8 zenbaki + 1 letra): ");
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu." + Gehigarriak.RESET);
                continue;
            }
            if (ErroreenKudeaketa.ifExistsNan(nan, path)) {
                // NAN existitzen da, beste bat sartu
                continue;
            }
            break; // NAN baliozkoa eta ez dago fitxategian
        } while (true);

        // Izena balidazioa
        do {
            System.out.print("Izena: ");
            izena = sc.nextLine();
            if (!Filtroak.isIzena(izena)) {
                System.out.println(
                        Gehigarriak.Gorria + "Izena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(izena));

        // Abizena balidazioa
        do {
            System.out.print("Abizena: ");
            abizena = sc.nextLine();
            if (!Filtroak.isIzena(abizena)) {
                System.out.println(
                        Gehigarriak.Gorria + "Abizena okerra. Letra bakarrik sartu behar da." + Gehigarriak.RESET);
            }
        } while (!Filtroak.isIzena(abizena));

        // Adina balidazioa
        do {
            System.out.print("Adina: ");
            adinaStr = sc.nextLine();
            if (!Filtroak.isAdina(adinaStr)) {
                System.out.println(
                        Gehigarriak.Gorria + "Adina okerra. Zenbaki bakarrik sartu behar da." + Gehigarriak.RESET);
                continue;
            }
            adina = Integer.parseInt(adinaStr);
            break;
        } while (true);

        // Helbidea
        System.out.print("Helbidea: ");
        helbidea = sc.nextLine();
        helbidea = Filtroak.removeSpaces(helbidea);

        // Pertsona objektua sortu
        Pertsona p = new Pertsona(nan, izena, abizena, adina, helbidea);

        // Fitxategian gehitu
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(p.toCSV());
            bw.newLine();
            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira fitxategian." + Gehigarriak.RESET);
        } catch (IOException e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: Datuak ezin izan dira gehitu fitxategian." + Gehigarriak.RESET);
            System.err.println(e.getMessage());
        }
    }

    private static void txtFitxategiaEguneratu() {
        String fileName, path;
        List<Pertsona> pertsonak = new ArrayList<>();
        Scanner sc = new Gehigarriak().in;

        System.out.println("TXT fitxategia eguneratu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu
        System.out.print(Gehigarriak.Horia + "Zein fitxategi eguneratu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = "./fitxategiak/txt/" + fileName + ".txt";

        // Fitxategia irakurri eta Pertsona objektuak sortu
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lerroa;
            System.out.println("Datuak fitxategian:");
            while ((lerroa = br.readLine()) != null) {
                System.out.println(lerroa);
                String[] zatiak = lerroa.split(",");
                if (zatiak.length == 5) { // NAN, Izena, Abizena, Adina, Helbidea
                    Pertsona p = new Pertsona(zatiak[0], zatiak[1], zatiak[2], Integer.parseInt(zatiak[3]), zatiak[4]);
                    pertsonak.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fitxategia ez da aurkitu.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        sc.nextLine(); // Scanner buffer garbitu

        // NAN aukeratu eguneratzeko
        String nan;
        Pertsona pertsonaEguneratu = null;
        do {
            System.out.print(Gehigarriak.Horia + "Eguneratu nahi duzun pertsonaren NAN-a sartu: " + Gehigarriak.RESET);
            nan = sc.nextLine();
            if (!Filtroak.isDNI(nan)) {
                System.out.println(
                        Gehigarriak.Gorria + "NAN okerra. 8 zenbaki + 1 letra izan behar du." + Gehigarriak.RESET);
                continue;
            }
            // Pertsona bilatu
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

        // Fitxategia berriro gorde CSV formatuan
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (Pertsona p : pertsonak) {
                bw.write(p.toCSV()); // lerro bakoitza CSV formatuan
                bw.newLine();
            }
            System.out.println(Gehigarriak.Berdea + "Fitxategia eguneratu da!" + Gehigarriak.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void txtFitxategiaEzabatu() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;
        txtFitxategiakBistaratu();
        System.out.print(Gehigarriak.Horia + "Zein fitxategi ezabatu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = "./fitxategiak/txt/" + fileName + ".txt";
        String erantzuna;

        do {
            System.out.print(
                    Gehigarriak.Horia + "Zihur zaide fitxategia ezabatu nahi duzula? (Bai/Ez) " + Gehigarriak.RESET);
            erantzuna = sc.next().toLowerCase();
        } while (erantzuna != "bai" || erantzuna != "ez");

        java.nio.file.Path p = java.nio.file.Paths.get(path);

        if (erantzuna.equals("bai")) {
            try {
                boolean deleted = java.nio.file.Files.deleteIfExists(p);
                if (deleted) {
                    System.out.println(Gehigarriak.Berdea + "Fitxategia ondo ezabatu da: " + path + Gehigarriak.RESET);
                } else {
                    System.out.println(Gehigarriak.Gorria + "Fitxategia ez da aurkitu: " + path + Gehigarriak.RESET);
                }
            } catch (IOException e) {
                System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da ezabatu.");
                System.err.println(e.getMessage() + Gehigarriak.RESET);
            }
        }
    }

    private static void txtFitxategiaCSVraBihurtu() {
        String fileName, path, csvPath;
        Scanner sc = new Gehigarriak().in;

        System.out.println("TXT fitxategia CSV formatura bihurtu");
        txtFitxategiakBistaratu();

        // Fitxategia aukeratu
        System.out.print(Gehigarriak.Horia + "Zein fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = "./fitxategiak/txt/" + fileName + ".txt";
        csvPath = "./fitxategiak/csv/Datuak-CSV.csv";

        try {
            File txtFile = new File(path);
            if (!txtFile.exists()) {
                System.out.println("TXT fitxategia ez da aurkitu.");
                return;
            }

            File csvFile = new File(csvPath);
            boolean csvExists = csvFile.exists();

            // --- Leer CSV existente ---
            Map<String, String> mapaPorNan = new LinkedHashMap<>(); // NAN -> línea CSV
            if (csvExists) {
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String linea;
                    boolean primeraLinea = true;
                    while ((linea = br.readLine()) != null) {
                        if (primeraLinea) { // ignorar cabecera
                            primeraLinea = false;
                            continue;
                        }
                        String[] partes = linea.split(";", -1);
                        if (partes.length > 0) {
                            mapaPorNan.put(partes[0], linea);
                        }
                    }
                }
            }

            // --- Leer TXT y actualizar/añadir datos ---
            final String TXT_BANATZAILEA = ",";
            final String CSV_BANATZAILEA = ";";

            try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
                String lerroa;
                while ((lerroa = br.readLine()) != null) {
                    String[] datuak = lerroa.split(TXT_BANATZAILEA);
                    String csvLerroa = "";

                    if (datuak.length == 2) {
                        String nan = datuak[0];
                        String adina = datuak[1];
                        csvLerroa = nan + CSV_BANATZAILEA + "" + CSV_BANATZAILEA + "" + CSV_BANATZAILEA + adina
                                + CSV_BANATZAILEA + "";
                        mapaPorNan.put(nan, csvLerroa); // añadir o actualizar
                    } else if (datuak.length == 5) {
                        String nan = datuak[0];
                        csvLerroa = String.join(CSV_BANATZAILEA, datuak);
                        mapaPorNan.put(nan, csvLerroa); // añadir o actualizar
                    } else {
                        System.err.println("OHARRA: Lerroak ez du espero den formatua (2 edo 5 zutabe): " + lerroa);
                        continue;
                    }
                }
            }

            // --- Reescribir CSV completo ---
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                // Cabecera
                bw.write("NAN;Izena;Abizena;Adina;Helbidea");
                bw.newLine();

                // Escribir todas las líneas del mapa
                for (String linea : mapaPorNan.values()) {
                    bw.write(linea);
                    bw.newLine();
                }
            }

            System.out.println(Gehigarriak.Berdea + "Fitxategia eguneratu da: " + csvPath + Gehigarriak.RESET);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}