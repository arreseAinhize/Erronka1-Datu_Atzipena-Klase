import java.io.File;
import java.util.Scanner;

public class XmlKudeatzailea {
    final static String XML_DIR = "./fitxategiak/xml/";

    public static void menua() {
        String aukera;
        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. XML fitxategia sortu.");
            System.out.println("2. XML fitxategiak bistaratu.");
            System.out.println("3. XML fitxategia irakurri.");
            System.out.println("4. XML fitxategian datuak gehitu");
            System.out.println("5. XML fitxategia eguneratu.");
            System.out.println("6. XML fitxategia ezabatu.");
            System.out.println("7. XML fitxategia CSV formatura bihurtu.");
            System.out.println(Gehigarriak.Urdina + "8. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = new Gehigarriak().in.next();
            if (Filtroak.isnumeric(aukera) == true) {
                switch (aukera) {
                    case "1":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("XML fitxategia sortu");
                        xmlFitxategiaSortu();
                        break;
                    case "2":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiakBistaratu();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "3":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaIrakurri();
                        Gehigarriak.aurreraJarraitu(); // erabiltzaileak enter sakatu aurrera joateko
                        break;
                    case "4":
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println("XML fitxategian datuak gehitu");
                        xmlFitxategiaGehitu();
                        break;
                    case "5":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaEguneratu();
                        System.out.println("XML fitxategia eguneratu");
                        break;
                    case "6":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaEzabatu();
                        System.out.println("XML fitxategia ezabatu");
                        break;
                    case "7":
                        Gehigarriak.kontsolaGarbitu();
                        xmlFitxategiaCSVraBihurtu();
                        System.out.println("XML fitxategia CSV formatura bihurtu");
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

    private static void xmlFitxategiaSortu() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena sartu: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = XML_DIR + fileName + ".xml";

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

    private static void xmlFitxategiakBistaratu() {
        Gehigarriak.kontsolaGarbitu();
        System.out.println(Gehigarriak.Urdina + "XML fitxategiak: " + Gehigarriak.RESET);
        File karpeta = new File(XML_DIR);
        File[] fitxategiak = karpeta.listFiles();

        if (fitxategiak != null && fitxategiak.length > 0) {
            for (File fitxategia : fitxategiak) {
                if (fitxategia.isFile() && fitxategia.getName().endsWith(".xml")) {
                    System.out.println(Gehigarriak.Berdea + "- " + fitxategia.getName() + Gehigarriak.RESET);
                }
            }
        } else {
            System.out.println(Gehigarriak.Gorria + "Ez da XML fitxategirik aurkitu." + Gehigarriak.RESET);
        }
    }

    private static void xmlFitxategiaIrakurri() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = XML_DIR + fileName + ".xml";

            File fitx = new File(path);
            if (!fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Ez da fitxategi hori aurkitu, saiatu beste batekin."
                        + Gehigarriak.RESET);
                System.out.print(Gehigarriak.Horia + "Sakatu enter aurrera joateko..." + Gehigarriak.RESET);
                sc.nextLine(); // lerro hau gehitu behar da next() eta nextLine() arteko traba saihesteko
                continue; // berriro galdetu
            }

            System.out.println(Gehigarriak.Urdina + "Fitxategiaren edukia: " + path + Gehigarriak.RESET);
            ErroreenKudeaketa.fitxategiaIrakurri(path);
            break; // fitxategia ondo irakurri bada, irten loop-etik

        } while (true);
    }

    private static void xmlFitxategiaGehitu() {
        Scanner sc = new Gehigarriak().in;
        System.out.println("Gehitu XML fitxategiaren datuak");
        System.out.print(Gehigarriak.Horia + "Sakatu enter aurrera joateko..." + Gehigarriak.RESET);
        sc.nextLine(); // lerro hau gehitu behar da next() eta nextLine() arteko traba saihesteko
    }

    private static void xmlFitxategiaEguneratu() {
        Scanner sc = new Gehigarriak().in;
        System.out.println("Eguneratu XML fitxategia");
        System.out.print(Gehigarriak.Horia + "Sakatu enter aurrera joateko..." + Gehigarriak.RESET);
        sc.nextLine(); // lerro hau gehitu behar da next() eta nextLine() arteko traba saihesteko
    }

    private static void xmlFitxategiaEzabatu() {
        String fileName, path;
        Scanner sc = new Gehigarriak().in;

        do {
            Gehigarriak.kontsolaGarbitu();
            System.out.print(Gehigarriak.Horia + "Sartu ezabatu nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = XML_DIR + fileName + ".xml";

            File fitx = new File(path);
            if (!fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Ez da fitxategi hori aurkitu, saiatu beste batekin."
                        + Gehigarriak.RESET);
                System.out.print(Gehigarriak.Horia + "Sakatu enter aurrera joateko..." + Gehigarriak.RESET);
                sc.nextLine(); // lerro hau gehitu behar da next() eta nextLine() arteko traba saihesteko
                continue; // berriro galdetu
            }

            if (ErroreenKudeaketa.fitxategiaEzabatu(path)) {
                System.out.println(Gehigarriak.Berdea + "Fitxategia ondo ezabatu da: " + path + Gehigarriak.RESET);
            } else {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da ezabatu: " + path + Gehigarriak.RESET);
                ErroreenKudeaketa.fitxategiaEzabatu(path);
            }
            break; // fitxategia ondo ezabatu bada, irten loop-etik

        } while (true);
    }

    private static void xmlFitxategiaCSVraBihurtu() {
        Scanner sc = new Gehigarriak().in;
        System.out.println("XML fitxategia CSV formatura bihurtu");
        System.out.print(Gehigarriak.Horia + "Sakatu enter aurrera joateko..." + Gehigarriak.RESET);
        sc.nextLine(); // lerro hau gehitu behar da next() eta nextLine() arteko traba saihesteko
    }
}
