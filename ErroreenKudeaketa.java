import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ErroreenKudeaketa {
    public static boolean fitxategiaIrakurri(String path) {
        try {
            java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
            return true;
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da irakurri.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
            return false;
        }
    }

    public static boolean fitxategiaIdatzi(String path, String edukia) {
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(path), edukia.getBytes());
            return true;
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da idatzi.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
            return false;
        }
    }

    public static boolean fitxategiaSortu(String path) {
        try {
            java.nio.file.Files.createFile(java.nio.file.Paths.get(path));
            return true;
        } catch (java.io.IOException e) {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da sortu.");
            System.err.println(e.getMessage() + Gehigarriak.RESET);
            return false;
        }
    }
    
    public  static boolean fitxategiaEzabatu(String path) {
            try {
                java.nio.file.Files.delete(java.nio.file.Paths.get(path));
                return true;
            } catch (java.io.IOException e) {
                System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da ezabatu.");
                System.err.println(e.getMessage() + Gehigarriak.RESET);
                return false;
            }
        }
    public static boolean fitxategiaAurkitu(String path) {
        java.nio.file.Path p = java.nio.file.Paths.get(path);
        if (java.nio.file.Files.exists(p)) {
            return true;
        } else {
            System.out.println(Gehigarriak.Gorria + "Errorea: Fitxategia ez da existitzen." + Gehigarriak.RESET);
            return false;
        }
    }

public static boolean ifExistsNan(String nan, String path) {
    // Comprobamos si el archivo existe
    File fitx = new File(path);
    if (!fitx.exists()) {
        return false; // si no existe el archivo, el NAN tampoco existe
    }

    // Leemos el archivo y buscamos el NAN
    try (BufferedReader br = new BufferedReader(new FileReader(fitx))) {
        String lerroa;
        while ((lerroa = br.readLine()) != null) {
            String[] zatiak = lerroa.split(","); // asumimos formato CSV: NAN,Izena,Abizena,Adina,Helbidea
            if (zatiak.length > 0 && zatiak[0].equalsIgnoreCase(nan)) {
                System.out.println(Gehigarriak.Gorria + "Errorea: NAN hori jada existitzen da fitxategian!" + Gehigarriak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true; // NAN encontrado
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return false; // NAN no encontrado
}

}
