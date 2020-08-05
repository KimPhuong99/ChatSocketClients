/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmuticlientservertcp;

/**
 *
 * @author Admin
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DOMExample {

    public DOMExample() {
    }

    public static List<user> readListStudents(String s) {
        List<user> listStudents = new ArrayList<>();
        user student = null;

        try {//System.out.print("done1");
            // đọc file input.xml
            File inputFile = new File("dsnd.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // in phần tử gốc ra màn hình
            //System.out.println("Phần tử gốc:"
            //      + doc.getDocumentElement().getNodeName());
            // đọc tất cả các phần tử có tên thẻ là "student"
            NodeList nodeListStudent = doc.getElementsByTagName("user");

            // duyệt các phần tử student
            for (int i = 0; i < nodeListStudent.getLength(); i++) {
                // tạo đối tượng student
                student = new user();
                // đọc các thuộc tính của student
                Node nNode = nodeListStudent.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    student.setUser(eElement.getElementsByTagName("name").item(0).getTextContent());
                    student.setPass(eElement.getElementsByTagName("pass").item(0).getTextContent());
                    String ID = eElement.getElementsByTagName("ID").item(0).getTextContent();
                    student.setID(Integer.parseInt(ID));
                    String run = eElement.getElementsByTagName("run").item(0).getTextContent();
                    student.setRun(Integer.parseInt(run));
                }
                // add đối tượng student vào listStudents
                listStudents.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }
}
