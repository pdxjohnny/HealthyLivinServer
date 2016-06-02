package net.carpoolme.storage;

import net.carpoolme.utils.BasicParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by John Andersen on 5/29/16.
 */
public class XML extends BasicParser implements Serializer {
    public InputStream toInputStream(Object object) {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("root");
            doc.appendChild(root);

            for (Object[] subObjects : (Object[][]) object) {
                Element name = doc.createElement(subObjects[0].toString());
                name.appendChild(doc.createTextNode(subObjects[1].toString()));
                root.appendChild(name);
            }

            // Save the document to the disk file
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            // format the XML nicely
            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                // location and name of XML file you can change as per need
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                StreamResult result = new StreamResult(byteArrayOutputStream);
                aTransformer.transform(source, result);
                return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            } catch (TransformerException e) {
                System.out.println("ERROR: failed to transform into xml");
                e.printStackTrace();
            }
        } catch (DOMException | ParserConfigurationException | TransformerConfigurationException e) {
            System.out.println("ERROR: failed to create xml");
            e.printStackTrace();
        }
        return null;
    }

    public Object toObject(InputStream in) {
        Object[][] result = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(in);

            NodeList childNodes = document.getDocumentElement().getChildNodes();

            Node node;
            for (int i = 0; i < childNodes.getLength(); ++i) {
                node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    result = setKey(result, node.getNodeName(), node.getChildNodes().item(0).getNodeValue());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("ERROR: failed to parse xml");
            e.printStackTrace();
        }
        return result;
    }
}
