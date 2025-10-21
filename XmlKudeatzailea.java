import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
                    Gehigarriak.Horia + "Sartu sortu nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
            fileName = sc.next();
            fileName = Filtroak.removeSpaces(fileName); // hutsuneak kendu
            path = XML_DIR + fileName + ".xml";

            File fitx = new File(path);
            if (fitx.exists()) {
                System.out.println(Gehigarriak.Gorria + "Jada fitxategi batek izen hori du, saiatu beste batekin."
                        + Gehigarriak.RESET);
                continue; // berriro galdetu
            }

            try {
                // === Crear el documento XML con cabecera y raíz ===
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // Crear nodo raíz <pertsonak>
                Element rootElement = doc.createElement("pertsonak");
                doc.appendChild(rootElement);

                // Guardar el documento con cabecera y formato bonito
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(fitx);
                transformer.transform(source, result);

                System.out.println(Gehigarriak.Berdea + "XML fitxategia ondo sortu da: " + path + Gehigarriak.RESET);
                break; // fitxategia ondo sortu bada, irten loop-etik

            } catch (Exception e) {
                System.out.println(Gehigarriak.Gorria + "Errorea XML fitxategia sortzean." + Gehigarriak.RESET);
                e.printStackTrace();
            }

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

        while (true) {
            Gehigarriak.kontsolaGarbitu();
            xmlFitxategiakBistaratu(); // Mostrar XML existentes

            System.out.print(Gehigarriak.Horia + "Sartu irakurri nahi duzun fitxategiaren izena: " + Gehigarriak.RESET);
            fileName = sc.nextLine(); // leer toda la línea para evitar problemas de buffer
            fileName = Filtroak.removeSpaces(fileName); // quitar espacios
            path = XML_DIR + fileName + ".xml";

            File fitx = new File(path);
            if (!fitx.exists()) {
                System.out.println(
                        Gehigarriak.Gorria + "Ez da fitxategi hori aurkitu, saiatu beste batekin." + Gehigarriak.RESET);
                System.out.print(Gehigarriak.Horia + "Sakatu Enter aurrera joateko..." + Gehigarriak.RESET);
                sc.nextLine();
                continue;
            }

            System.out.println(Gehigarriak.Urdina + "Fitxategiaren edukia: " + path + Gehigarriak.RESET);

            // === LEER XML ===
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fitx);
                doc.getDocumentElement().normalize();

                NodeList pertsonaList = doc.getElementsByTagName("pertsona");
                if (pertsonaList.getLength() == 0) {
                    System.out.println(Gehigarriak.Horia + "Ez dago pertsonarik fitxategian." + Gehigarriak.RESET);
                } else {
                    for (int i = 0; i < pertsonaList.getLength(); i++) {
                        Node pertsonaNode = pertsonaList.item(i);
                        if (pertsonaNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element pertsonaElem = (Element) pertsonaNode;
                            String nan = pertsonaElem.getElementsByTagName("nan").item(0).getTextContent();
                            String helbidea = pertsonaElem.getElementsByTagName("helbidea").item(0).getTextContent();

                            System.out.println(Gehigarriak.Berdea + "Pertsona " + (i + 1) + ":" + Gehigarriak.RESET);
                            System.out.println("  NAN: " + nan);
                            System.out.println("  Helbidea: " + helbidea);
                        }
                    }
                }
            } catch (Exception e) {
                System.out
                        .println(Gehigarriak.Gorria + "Errorea: Fitxategia ezin izan da irakurri." + Gehigarriak.RESET);
                e.printStackTrace();
            }
            break;
        }
    }

    private static void xmlFitxategiaGehitu() {
        System.out.println("XML fitxategian datuak gehitu");
        Scanner sc = new Gehigarriak().in;

        // Mostrar XML existentes
        xmlFitxategiakBistaratu();

        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiri datuak gehitu nahi dizkiozu? Sartu izena: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = XML_DIR + fileName + ".xml";

        sc.nextLine(); // limpiar buffer

        // === AÑADIR AL XML ===
        try {
            File xmlFile = new File(path);
            if (!xmlFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen: " + path + Gehigarriak.RESET);
                Thread.sleep(2000);
                return;
            }

            // === PEDIR DATOS ===
            String nan, helbidea;

            // Validar NAN
            do {
                System.out.print("NAN (8 zenbaki + 1 letra): ");
                nan = sc.nextLine();
                if (!Filtroak.isDNI(nan)) {
                    System.out.println(
                            Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu."
                                    + Gehigarriak.RESET);
                    continue;
                }
                if (ErroreenKudeaketa.ifExistsNanXML(nan, path)) { // método que verifica si el nan ya existe
                    continue;
                }
                break;
            } while (true);

            // Helbidea
            System.out.print("Helbidea: ");
            helbidea = sc.nextLine();
            helbidea = Filtroak.removeSpaces(helbidea);

            // Cargar XML existente
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Crear nuevo nodo <pertsona>
            Element pertsona = doc.createElement("pertsona");

            Element nanElem = doc.createElement("nan");
            nanElem.appendChild(doc.createTextNode(nan));
            pertsona.appendChild(nanElem);

            Element helbElem = doc.createElement("helbidea");
            helbElem.appendChild(doc.createTextNode(helbidea));
            pertsona.appendChild(helbElem);

            // Añadirlo al nodo raíz <pertsonak>
            doc.getDocumentElement().appendChild(pertsona);

            // Guardar cambios con formato bonito
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println(Gehigarriak.Berdea + "Datuak ondo gehitu dira fitxategian." + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: XML fitxategian datuak ezin izan dira gehitu." + Gehigarriak.RESET);
            e.printStackTrace();
        }
    }

    private static void xmlFitxategiaEguneratu() {
        System.out.println("XML fitxategian datuak eguneratu");
        Scanner sc = new Gehigarriak().in;

        // Mostrar XML existentes
        xmlFitxategiakBistaratu();

        System.out.print(
                Gehigarriak.Horia + "Zein fitxategiko datuak eguneratu nahi dituzu? Sartu izena: " + Gehigarriak.RESET);
        String fileName = sc.next();
        fileName = Filtroak.removeSpaces(fileName);
        String path = XML_DIR + fileName + ".xml";

        sc.nextLine(); // limpiar buffer

        try {
            File xmlFile = new File(path);
            if (!xmlFile.exists()) {
                System.out.println(Gehigarriak.Gorria + "Fitxategia ez da existitzen: " + path + Gehigarriak.RESET);
                Thread.sleep(2000);
                return;
            }

            // === PEDIR NAN DE LA PERSONA A ACTUALIZAR ===
            String nan;
            do {
                System.out.print("Eguneratu nahi duzun pertsonaren NAN (8 zenbaki + 1 letra): ");
                nan = sc.nextLine();
                if (!Filtroak.isDNI(nan)) {
                    System.out.println(
                            Gehigarriak.Gorria + "NAN okerra. 8 zenbaki eta 1 letra izan behar ditu."
                                    + Gehigarriak.RESET);
                    continue;
                }
                if (!ErroreenKudeaketa.ifExistsNanXML(nan, path)) { // método que verifica si existe
                    System.out.println(Gehigarriak.Gorria + "NAN ez da aurkitzen fitxategian." + Gehigarriak.RESET);
                    return;
                }
                break;
            } while (true);

            // === CARGAR XML EXISTENTE ===
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Buscar el nodo <pertsona> con el NAN dado
            NodeList pertsonak = doc.getElementsByTagName("pertsona");
            boolean eguneratua = false;

            for (int i = 0; i < pertsonak.getLength(); i++) {
                Node pNode = pertsonak.item(i);
                if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pertsona = (Element) pNode;
                    String currentNan = pertsona.getElementsByTagName("nan").item(0).getTextContent();
                    if (currentNan.equalsIgnoreCase(nan)) {
                        // Pedir nuevos datos
                        System.out.print("Helbidea berria: ");
                        String helbidea = sc.nextLine();
                        helbidea = Filtroak.removeSpaces(helbidea);

                        pertsona.getElementsByTagName("helbidea").item(0).setTextContent(helbidea);
                        eguneratua = true;
                        break;
                    }
                }
            }

            if (!eguneratua) {
                System.out.println(Gehigarriak.Gorria + "NAN ez da aurkitu fitxategian." + Gehigarriak.RESET);
                return;
            }

            // Guardar cambios con formato bonito
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println(Gehigarriak.Berdea + "Datuak ondo eguneratu dira fitxategian." + Gehigarriak.RESET);

        } catch (Exception e) {
            System.out.println(
                    Gehigarriak.Gorria + "Errorea: XML fitxategian datuak ezin izan dira eguneratu."
                            + Gehigarriak.RESET);
            e.printStackTrace();
        }
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
        String fileName, path, csvPath;
        Scanner sc = new Gehigarriak().in;

        // Mostrar XML existentes
        xmlFitxategiakBistaratu();

        System.out.println("\nXML fitxategia CSV formatura bihurtu");
        System.out.print(Gehigarriak.Horia + "Zein XML fitxategi bihurtu nahi duzu? Sartu izena: " + Gehigarriak.RESET);
        fileName = sc.next();
        Filtroak.removeSpaces(fileName);

        path = "./fitxategiak/xml/" + fileName + ".xml";
        csvPath = "./fitxategiak/csv/Datuak-CSV.csv";

        try {
            File xmlFile = new File(path);
            if (!xmlFile.exists()) {
                System.out.println("Fitxategia ez da aurkitu.");
                return;
            }

            File csvFile = new File(csvPath);
            boolean csvExists = csvFile.exists();

            // --- Leer datos actuales del CSV en memoria ---
            List<String> lineasCSV = new ArrayList<>();
            Map<String, String> mapaPorNan = new HashMap<>(); // nan -> línea completa

            if (csvExists) {
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        lineasCSV.add(linea);
                        if (!linea.startsWith("NAN;") && !linea.trim().isEmpty()) {
                            String[] partes = linea.split(";", -1);
                            if (partes.length > 0) {
                                mapaPorNan.put(partes[0], linea);
                            }
                        }
                    }
                }
            }

            // --- Procesar el XML ---
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList pertsonak = document.getElementsByTagName("pertsona");
            for (int i = 0; i < pertsonak.getLength(); i++) {
                Node nodo = pertsonak.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element pertsona = (Element) nodo;

                    String nan = getTagValue("nan", pertsona);
                    String helbidea = getTagValue("helbidea", pertsona);

                    // Nuevo formato CSV
                    String csvLerroa = nan + ";" + "" + ";" + "" + ";" + "" + ";" + helbidea;

                    // Si ya existe el NAN, actualiza su línea
                    if (mapaPorNan.containsKey(nan)) {
                        mapaPorNan.put(nan, csvLerroa);
                    } else {
                        mapaPorNan.put(nan, csvLerroa);
                    }
                }
            }

            // --- Reescribir el CSV completo ---
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                // Cabecera
                bw.write("NAN;Izena;Abizena;Adina;Helbidea");
                bw.newLine();

                // Escribir todas las líneas del mapa (actualizadas o nuevas)
                for (String linea : mapaPorNan.values()) {
                    bw.write(linea);
                    bw.newLine();
                }
            }

            System.out.println(Gehigarriak.Berdea + "Fitxategia eguneratu da: " + csvPath + Gehigarriak.RESET);

        } catch (Exception e) {
            System.err.println("Errorea XML fitxategia irakurtzean edo idaztean: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Laguntzailea: etiketa baten balioa itzultzen du.
     */
    private static String getTagValue(String tag, Element element) {
        NodeList nList = element.getElementsByTagName(tag);
        if (nList != null && nList.getLength() > 0) {
            Node node = nList.item(0);
            return node.getTextContent().trim();
        }
        return "";
    }

}
