package Alumni_Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class XmlWriter {                               // This class enters the new data that we insert through our program/application in the XML file.
    private static Scanner scanner = new Scanner(System.in);              //Scanner It is the easiest way to read input in a Java program

    public static void main(String[] args) {
        String filepath = "src/main/java/Alumni_Data/users.xml";                      //filepath is where our XML file exist in this program.
        ArrayList<User> users = XmlReader.readUsersFromXml(filepath);
        //System.out.println(users);
        // menu system of the application with create, read, update and delete options
        int choice = -1;
        while (choice != 0) {                                                    // Menu of the program
            System.out.println("\n\t\tWELCOME TO UNIVERSITY'S ALUMNI REGISTRATION\n\nEnter your data by selecting option\r\nMenu:\n1 - List users\r\n" +
                    "2 - Add new user\r\n3 - Modify user\r\n4 - Delete user\r\n0 - Save and Exit ");
            try {                                                          //Choosing option by the user
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || 4 < choice) {
                    System.out.println("Not valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Not valid option.");
                scanner.nextLine();
            }
            switch (choice) {                                      //Options of the user
                case 1 -> System.out.println(users);
                case 2 -> addNewUser(users);
                case 3 -> modifyUser(users);
                case 4 -> deleteUser(users);
            }
        }
        //users.add(new User("Kate", 2000, "Second Street", EyeColor.AMBER));
        saveUsersToXml(users, filepath);                                 //The data user will enter will save in XMl(using the filepath) by using saveUsersToXml syntax
    }

    private static void addNewUser(ArrayList<User> users) {               // Her we will work on our second option adding a new user or Alumni's Detail
        System.out.print("Enter Name of Alumnus: ");
        String name = scanner.nextLine().toUpperCase();
        int age = readAge();
        System.out.print("Enter Email address of Alumnus: ");
        String email_address = scanner.nextLine();
        System.out.print("Enter Current Profession of Alumnus: ");
        String profession = scanner.nextLine().toUpperCase();
        Faculty faculty = readFaculty();
        users.add(new User(name, age, email_address, profession, faculty));
    }

    private static void modifyUser(ArrayList<User> users) {                //This algorithm is written if user wants to modify the details given before.
        User user = findUserIn(users);
        int age = readAge();
        System.out.print("Enter New Email address of Alumnus/ Alumni: ");
        String email_address = scanner.nextLine();
        System.out.print("Enter New Alumnus's Current Profession: ");
        String profession = scanner.nextLine().toUpperCase();
        Faculty faculty = readFaculty();
        users.set(users.indexOf(user),
                new User(user.getName(), age, email_address, profession, faculty));
    }

    private static void deleteUser(ArrayList<User> users) {
        users.remove(findUserIn(users));
    }

    private static User findUserIn(ArrayList<User> users) {                          // this helps while updating or modifing the user's data since for that first the user have to mention
        User user = new User();                                                      //his\her details to find
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter Name of Alumnus/ Alumni: ");
            name = scanner.nextLine().toUpperCase();
            for (User userElement : users) {
                if (userElement.getName().equals(name)) {
                    return userElement;
                }
            }
            name = "";
        }
        return user;
    }

    private static int readAge() {                                        // This method describes that age of the user should be an integer not any letter
        int age = 0;
        while (age == 0) {
            try {
                System.out.print("Enter age of user: ");
                age = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("This is not a valid birth year. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return age;
    }

    private static Faculty readFaculty() {                            //This method describes that the user should provide the name of the faculty mentioned in enum.
        Faculty faculty = Faculty.ARTS;
        String rawInput = "";
        while (rawInput.isEmpty()) {
            try {
                System.out.print("Enter faculty of user: ");
                rawInput = scanner.nextLine();
                faculty = Faculty.valueOf(rawInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Not valid Faculty.");
                rawInput = "";
            }
        }
        return faculty;
    }

    private static void saveUsersToXml(ArrayList<User> users, String filepath) {             //This method saves all the data inputed by the user in XML upon pressing "0".
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = document.createElement("users");
            document.appendChild(rootElement);

            for (User user : users) {
                Element userElement = document.createElement("user");
                rootElement.appendChild(userElement);
                createChildElement(document, userElement, "name", user.getName());
                createChildElement(document, userElement, "age", String.valueOf(user.getAge()));
                createChildElement(document, userElement, "email_address", String.valueOf(user.getEmail_address()));
                createChildElement(document, userElement, "profession", String.valueOf(user.getProfession()));
                createChildElement(document, userElement, "faculty", user.getFaculty().toString());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(filepath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createChildElement(Document document, Element parent, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.setTextContent(value);
        parent.appendChild(element);
    }


}
