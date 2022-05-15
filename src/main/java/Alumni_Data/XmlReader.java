package Alumni_Data;
// This is a list of Interfaces in JAVA that helps us use some of the syntax's and operators
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.ArrayList;

public class XmlReader {                                    // as we can see by the name of this class, This class reads all the data in our given XML file given by us.
    public static ArrayList<User> readUsersFromXml(String filepath) {
        ArrayList<User> users = new ArrayList<>();
        try {                                            //document builder factory defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new FileInputStream(filepath));  //parse means converting data in the String format from a file, user input, or a certain network.

            Element rootElement = document.getDocumentElement();
            //Algorithm of reading data from XML file and then show it in display when asked.
            NodeList childsOfRootElement = rootElement.getChildNodes();
            for (int i = 0; i < childsOfRootElement.getLength(); i++) {
                Node childNode = childsOfRootElement.item(i);  // get a child node object
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childsOfUserTag = childNode.getChildNodes();  // get all child nodes of a user tag
                    String name = "";
                    int age = 0;
                    String email_address = "";
                    String profession = "";
                    Faculty faculty = Faculty.ARTS;
                    for (int j = 0; j < childsOfUserTag.getLength(); j++) {
                        Node childNodeOfUserTag = childsOfUserTag.item(j);
                        if (childNodeOfUserTag.getNodeType() == Node.ELEMENT_NODE) {
                            switch (childNodeOfUserTag.getNodeName()) {
                                case "name" -> name = childNodeOfUserTag.getTextContent();
                                case "age" -> age = Integer.parseInt(childNodeOfUserTag.getTextContent());
                                case "email_address" -> email_address = childNodeOfUserTag.getTextContent();
                                case "profession" -> profession = childNodeOfUserTag.getTextContent();
                                case "faculty" -> faculty = Faculty.valueOf(childNodeOfUserTag.getTextContent());
                            }
                        }
                    }
                    users.add(new User(name, age, email_address, profession, faculty));
                }
            }
        } catch (
                Exception e) {                          //try-catch statements are used in Java to handle unwanted errors during the execution of a program.
            e.printStackTrace();                         //a tool used to handle exceptions and errors.
        }
        return users;
    }
}


