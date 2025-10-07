import java.util.Scanner;

public class Gehigarriak {
    public Scanner in = new Scanner(System.in);
    // Textu koloreak
    public static final String RESET = "\u001B[0m";
    public static final String Beltza = "\u001B[30m";
    public static final String Gorria = "\u001B[31m";
    public static final String Berdea = "\u001B[32m";
    public static final String Horia = "\u001B[33m";
    public static final String Urdina = "\u001B[34m";
    public static final String Morea = "\u001B[35m";
    public static final String Cyan = "\u001B[36m";
    public static final String Zuria = "\u001B[37m";

    public static void kontsolaGarbitu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void aurreraJarraitu(){
        System.out.println(Horia + "Sakatu enter aurrera egiteko..." + RESET);
        new Gehigarriak().in.nextLine();
    }
}
