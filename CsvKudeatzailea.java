import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CsvKudeatzailea {
    public static void menua() {
        System.out.println("CsvKudeatzailea menua");
    }

    public static void csvToXmlCreate(String csvFile, String xmlFile)
            throws FileNotFoundException, IOException, ParserConfigurationException, TransformerException {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] headers = br.readLine().split(",");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("pertsonak");
            doc.appendChild(root);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = br.readLine().split(",");

                Element pertsona = doc.createElement("pertsona");

                for (int i = 0; i < headers.length; i++) {
                    Element campo = doc.createElement(headers[i]);
                    campo.appendChild(doc.createTextNode(data[i]));
                    pertsona.appendChild(campo);
                }

                root.appendChild(pertsona);

                TransformerFactory transfromerFactory = TransformerFactory.newInstance();
                Transformer transformer = transfromerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new FileWriter(xmlFile));
                transformer.transform(source, result);

                System.out.println("Archivo XML creado correctamente: " + xmlFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void csvToXmlAppend(String csvFile, String xmlFile)
        throws FileNotFoundException, IOException, ParserConfigurationException, TransformerException {

    File file = new File(xmlFile);
    if (!file.exists()) {
        System.out.println("Error: Ez da aurkitu XML fitxategia: " + xmlFile);
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String[] headers = br.readLine().split(",");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(file);

        Element root = doc.getDocumentElement();

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            Element pertsona = doc.createElement("pertsona");

            for (int i = 0; i < headers.length; i++) {
                Element campo = doc.createElement(headers[i].trim());
                campo.appendChild(doc.createTextNode(data[i].trim()));
                pertsona.appendChild(campo);
            }

            root.appendChild(pertsona);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

        System.out.println("Archivo XML actualizado correctamente: " + xmlFile);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
