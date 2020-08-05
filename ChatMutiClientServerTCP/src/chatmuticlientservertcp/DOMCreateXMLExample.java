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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.List;

public class DOMCreateXMLExample {

    public DOMCreateXMLExample() {
    }

    public static int writeListStudents(List<user> listStudents) {

        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // tạo phần tử gốc có tên class
            Element rootElement = doc.createElement("ds_user");
            // thêm thuộc tính totalStudents vào thẻ class
            doc.appendChild(rootElement);
            for (user student : listStudents) {
                Element user1 = doc.createElement("user");
                rootElement.appendChild(user1);
                Element ten = doc.createElement("name");
                ten.appendChild(doc.createTextNode(student.getUsername()));
                user1.appendChild(ten);
                Element pass = doc.createElement("pass");
                pass.appendChild(doc.createTextNode(student.getPassword()));
                user1.appendChild(pass);
                Element ID = doc.createElement("ID");
                ID.appendChild(doc.createTextNode(String.valueOf(student.getID())));
                user1.appendChild(ID);
                Element run = doc.createElement("run");
                run.appendChild(doc.createTextNode(String.valueOf(student.getRun())));
                user1.appendChild(run);
            }
            // ghi nội dung vào file XML
            TransformerFactory transformerFactory
                    = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(
                    new File("dsnd.xml"));
            transformer.transform(source, result);
            return 1;
            // ghi kết quả ra console để kiểm tra
            //StreamResult consoleResult = new StreamResult(System.out);
            // transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
