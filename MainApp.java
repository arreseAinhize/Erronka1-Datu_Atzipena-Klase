public class MainApp {
    private static String aukera;

    public static void main(String[] args) {
        Gehigarriak.kontsolaGarbitu();
        do {
            System.out.println(Gehigarriak.Urdina + "Zer egin nahi duzu?");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.println(Gehigarriak.Berdea + "1. .TXT fitxategiak kudeatu.");
            System.out.println(Gehigarriak.Berdea + "2. .XML fitxategiak kudeatu.");
            System.out.println(Gehigarriak.Berdea + "3. .JSON fitxategiak kudeatu.");
            System.out.println(Gehigarriak.Berdea + "4. .CSV fitxategiak kudeatu.");
            System.out.println(Gehigarriak.Urdina + "5. Irten");
            System.out.println(Gehigarriak.Cyan + "================================");
            System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
            aukera = new Gehigarriak().in.next();
            if(Filtroak.isnumeric(aukera)==true){
                switch (aukera) {
                    case "1": Gehigarriak.kontsolaGarbitu(); TxtKudeatzailea.menua(); break;
                    case "2": Gehigarriak.kontsolaGarbitu(); XmlKudeatzailea.menua(); break;
                    case "3": Gehigarriak.kontsolaGarbitu(); JsonKudeatzailea.menua(); break;
                    case "4": Gehigarriak.kontsolaGarbitu(); CsvKudeatzailea.menua(); break;
                    case "5": System.out.println(Gehigarriak.Gorria + "Agur!"); break;
                    default:
                        Gehigarriak.kontsolaGarbitu();
                        System.out.println(Gehigarriak.Gorria + "Aukera okerra, saiatu berriro.");
                        System.out.print(Gehigarriak.Horia + "Aukera: " + Gehigarriak.RESET);
                        aukera = new Gehigarriak().in.next();
                        break;
                }                
            }else{
                Gehigarriak.kontsolaGarbitu();
                System.out.print(Gehigarriak.Gorria + "Zenbaki bat sartu behar duzu!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!aukera.equals("5"));
    }
}