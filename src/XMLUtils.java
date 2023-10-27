import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.crypto.dsig.Transform;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLUtils  {

    public static void createXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("pack");
        document.appendChild(rootElement);

        Element letters = document.createElement("letters");
        rootElement.appendChild(letters);

        addLetterElement(document, letters, "ABC00001", "Recepient 1", "Somewhere NSW 2345");
        addLetterElement(document, letters, "DEF00002", "Recepient 2", "Somewhere NSW 2345");
        addLetterElement(document, letters, "GHI00003", "Recepient 3", "Somewhere NSW 2345");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("C:/Users/user/IdeaProjects/quiz2/src/a.xml"));

        transformer.transform(domSource, streamResult);
    }

    private static void addLetterElement(Document document, Element parentElement, String reference, String line, String line2) {
        Element letterElement = document.createElement("letter");
        parentElement.appendChild(letterElement);
        
        Element postalElement = document.createElement("postal");
        letterElement.appendChild(postalElement);

        Element referenceElement = document.createElement("reference");
        referenceElement.appendChild(document.createTextNode(reference));
        letterElement.appendChild(referenceElement);

        Element lineElement1 = document.createElement("line");
        lineElement1.appendChild(document.createTextNode(line));
        postalElement.appendChild(lineElement1);

        Element lineElement2 = document.createElement("line");
        lineElement2.appendChild(document.createTextNode(line2));
        postalElement.appendChild(lineElement2);
    }

    public static void parseXML() throws ParserConfigurationException, IOException, SAXException {

        File file = new File("C:/Users/user/IdeaProjects/quiz2/src/a.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        document.getDocumentElement().normalize();

        NodeList lettersList = document.getElementsByTagName("letter");

        for (int i = 0; i < lettersList.getLength(); i++) {
            Node item = lettersList.item(i);
            Element letterElement = (Element) item;
            System.out.println(letterElement.getElementsByTagName("postal").item(0).getFirstChild().getTextContent());
            System.out.println(letterElement.getElementsByTagName("postal").item(0).getLastChild().getTextContent());
            System.out.println(letterElement.getElementsByTagName("reference").item(0).getTextContent());
        }
    }
}
