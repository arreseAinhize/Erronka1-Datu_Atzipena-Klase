public class Filtroak {
    public static boolean isnumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    // DNI egiaztatu: 8 zenbaki + 1 letra amaieran
    public static boolean isDNI(String testua) {
        if (testua == null || testua.length() != 9) return false;

        String zenbakiak = testua.substring(0, 8);
        char letra = testua.charAt(8);

        // lehenengo 8ak zenbakiak diren egiaztatu
        for (int i = 0; i < zenbakiak.length(); i++) {
            if (!Character.isDigit(zenbakiak.charAt(i))) {
                return false;
            }
        }

        // azkena letra den egiaztatu
        return Character.isLetter(letra);
    }

    // Izena edo abizena: letrak eta hutsuneak bakarrik
    public static boolean isIzena(String testua) {
        if (testua == null || testua.isEmpty()) return false;

        for (int i = 0; i < testua.length(); i++) {
            char c = testua.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    // Adina: zenbakiak bakarrik
    public static boolean isAdina(String testua) {
        if (testua == null || testua.isEmpty()) return false;

        for (int i = 0; i < testua.length(); i++) {
            if (!Character.isDigit(testua.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //Hutsuneak, - edo _ kendu
    public static String removeSpaces(String text) {
        if (text == null) return null;
        return text.replaceAll("[\\s-_.]+", "");
    }
}
