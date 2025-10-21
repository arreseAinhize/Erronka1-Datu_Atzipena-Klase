import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CsvKudeatzailea {
    private static Scanner sc = new Gehigarriak().in;
    private static final String CSV_DIR = "./fitxategiak/csv/";
    //private static final String XML_DIR = "./fitxategiak/xml/";
    private static final String TXT_DIR = "./fitxategiak/txt/";
    //private static final String JSON_DIR = "./fitxategiak/json/";


    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. CSV fitxategia bistaratu.");
            System.out.println("2. CSV fitxategiak irakurri.");
            System.out.println("3. CSV fitxategia TXT formatura bihurtu.");
            System.out.println("4. CSV fitxategian JSON formatura bihurtu");
            System.out.println("5. CSV fitxategia XML formatura bihurtu");
            System.out.println("6. CSV fitxategia ezabatu.");
            System.out.println(Gehigarriak.Urdina + "7. Menu nagusira itzuli");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = new Gehigarriak().in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("CSV fitxategiak bistaratu");
                        csvFitxategiaBistaratu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("CSV fitxategia TXT formatura bihurtu da.");
                        csvFitxategiaTXTraBihurtu();
                        Gehigarriak.aurreraJarraitu();// erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("CSV fitxategia JSON formatura bihurtu da.");
                        //txtFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("CSV fitxategia XML formatura bihurtu da.");
                        //txtFitxategiaEguneratu();
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        csvFitxategiaEzabatu();
                        System.out.println("CSV fitxategia ezabatu da.");
                        break;
                    case "7":
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

    private static void csvFitxategiaBistaratu(){
        System.out.println(Gehigarriak.Cyan + "================================");
        System.out.println(Gehigarriak.Berdea + "Fitxategi .csv-ak:" + Gehigarriak.Urdina);
        try {
            java.nio.file.Files.list(java.nio.file.Paths.get(CSV_DIR))
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(p -> System.out.println(p.getFileName().toString().replaceFirst("\\.csv$", "")));
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategiak ezin izan dira erakutsi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
        }
        System.out.println(Gehigarriak.Cyan + "================================");
    }

    private static void csvFitxategiaIrakurri(){
        String fileName, path;

        // .csv fitxategiak bistaratu:
        csvFitxategiaBistaratu();

        // fitxategi bat irakurri:
        System.out.print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
        fileName = new Gehigarriak().in.next();
        Filtroak.removeSpaces(fileName);
        path = CSV_DIR + fileName + ".csv";
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

    private static void csvFitxategiaTXTraBihurtu(){
        String fileName, csvPath,txtPath;

        // .csv fitxategiak bistaratu:
        csvFitxategiaBistaratu();

        // fitxategi bat irakurri:
        System.out.print(Gehigarriak.Horia + "Sartu .TXT bihurtu nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
        fileName = new Gehigarriak().in.next();
        Filtroak.removeSpaces(fileName);
        csvPath = CSV_DIR + fileName + ".csv";
        txtPath = TXT_DIR + fileName + "-convert_Form_CSV.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(txtPath))){
            final String TXT_BANATZAILEA = ",";             // TXT-ren banatzailea (adibidez, espazioa. Aldatu behar baduzu)
            final String CSV_BANATZAILEA = ";";             // CSV-ren banatzailea

            String lerroa, txtLerroa ="";
            while ((lerroa = br.readLine()) != null) {
                if(lerroa == "NAN;Izena;Abizena;Adina;Helbidea"){
                    System.out.println("CSV cavezala irakurrita;");
                    continue;
                }else{
                    String[] datuak = lerroa.split(CSV_BANATZAILEA);

                    if(datuak.length <=5 && datuak.length > 0){
                        txtLerroa = String.join(TXT_BANATZAILEA,datuak);
                    }else{
                        System.err.println("OHARRA: Lerroak ez du espero den formatua (o eta 5 zutabe bitartean): " + lerroa);
                        continue;
                    }
                }
                bw.write(txtLerroa);
                bw.newLine();
            }
            System.out.println(Gehigarriak.Berdea + "Fitxategia ondo bihurtu da: " + txtPath + Gehigarriak.RESET);
        } catch (FileNotFoundException e) {
            System.out.println("Fitxategia ez da aurkitu.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void csvFitxategiaEzabatu(){
        String fileName, path;
        System.out.print(Gehigarriak.Horia + "Zein fitxategi ezabatu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);
        path = CSV_DIR + fileName + ".csv";
        String erantzuna;

        do{
            System.out.print(Gehigarriak.Horia + "Zihur zaide fitxategia ezabatu nahi duzula? (Bai/Ez) " + Gehigarriak.RESET);
            erantzuna = sc.next().toLowerCase();
        }while(erantzuna != "bai" || erantzuna != "ez");

        File fitxategia = new File(path);

        if(erantzuna.equals("bai")){
            if(fitxategia.exists()){
                if (fitxategia.delete()){
                    System.out.println(Gehigarriak.Berdea + "Fitxategia ondo ezabatu da: " + path + Gehigarriak.RESET);
                } else {
                    System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da ezabatu." + Gehigarriak.RESET);
                }
            }else{
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da aurkitu: " + path + Gehigarriak.RESET);
            }
        }
    }
}
