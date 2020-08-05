/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmuticlientservertcp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class TCPServer {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static int maxSocket = 20;
    private static final clientThread[] threads = new clientThread[20];
    
    static class TCPGUI extends JFrame{
        private JPanel panelDau;
        public TCPGUI(){
            buildGUI();
        }
        public void buildGUI(){
            
            panelDau = new JPanel();
            panelDau.setLayout(new BorderLayout());
            JLabel t= new JLabel("Server is running in port 3200");
            panelDau.add(t,BorderLayout.CENTER);
            setContentPane(panelDau);
        }
    }

    public static void main(String args[]) {
        int portServer = 3200;
        TCPGUI frame= new TCPGUI();
        frame.setTitle("MyChatApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(200,200);
        frame.setResizable(false);
        frame.setVisible(true);
        
        try {
            serverSocket = new ServerSocket(portServer);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                DOMExample DOM = new DOMExample();
                java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
                int max = 0;
                int i = 0;
                user B = new user();
                for (user A : dsnd) {
                    if (A.getRun() > max) {
                        i = A.getID();
                        max = A.getRun();
                        B = A;
                    }
                }
                System.out.println(" i= " + i + " max= " + max + "kkkkk");
                for (i = 0; i < 10; i++) {
                    if (threads[i] == null) {
                        B.setID(i);
                        (threads[i] = new clientThread(clientSocket, threads, B)).start();
                        break;
                    }
                }

            } catch (IOException ex) {
                System.out.print("kkkkk");
                System.err.print(ex);
            }
        }
    }
}
