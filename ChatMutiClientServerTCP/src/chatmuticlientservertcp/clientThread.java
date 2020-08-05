/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmuticlientservertcp;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
class clientThread extends Thread {

    private List<user> listuser;
    private user clientUser = null;
    private DataInputStream is = null;

    private DataOutputStream os1 = null;
    private BufferedWriter bw;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private int id;
    private List<user> dsnd = null;

    public clientThread(Socket clientSocket, clientThread[] threads, user B) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        DOMExample DOM = new DOMExample();
        dsnd = DOM.readListStudents("dsnd.xml");
        maxClientsCount = dsnd.size();
        this.clientUser = B;
    }

    public void run() {
        int maxClient = this.maxClientsCount;
        clientThread[] threads = this.threads;
        try {
            int i = 0;
            DOMExample DOM = new DOMExample();
            List<user> dsnd1 = DOM.readListStudents("dsnd.xml");
            while (true) {
                os1 = new DataOutputStream(clientSocket.getOutputStream());
                is = new DataInputStream(clientSocket.getInputStream());
                String line = is.readLine();
                String[] arStr = line.split("\\.");
                FileTranfer fileInfo = null;
                if (arStr[0].compareTo("2") == 0) {
                    ois = new ObjectInputStream(clientSocket.getInputStream());
                    fileInfo = (FileTranfer) ois.readObject();
                    System.out.println(fileInfo.getDestinationDirectory());
                }
                if (line.startsWith("quit")) {
                    break;
                }
                if (line.startsWith("@")) {
                } else {
                    synchronized (this) {
                        System.out.println(line);
                        for (i = 0; i < 10; i++) {
                            System.out.println(threads[i]);
                            if (Integer.parseInt(arStr[0]) == 2) {
                                if (this.clientUser.getUsername().compareTo(arStr[1]) == 0 && threads[i] == this) {
                                    threads[i].os1.write(("< you " + " to " + arStr[2] + "> sen a " + arStr[3] + "\r\n").getBytes("UTF-8"));
                                    threads[i].os1.flush();
                                    threads[i].oos = new ObjectOutputStream(threads[i].clientSocket.getOutputStream());
                                    threads[i].oos.writeObject(null);
                                    System.out.println(threads[i].clientUser.getUsername() + " - " + arStr[2]);
                                }
                                if (threads[i] != null && threads[i].clientUser.getUsername().compareTo(arStr[2]) == 0) {

                                    threads[i].os1.write(("<" + arStr[1] + " to " + " you > a file in " + fileInfo.getDestinationDirectory() + fileInfo.getFilename() + "\r\n").getBytes("UTF-8"));
                                    threads[i].os1.flush();
                                    threads[i].oos = new ObjectOutputStream(threads[i].clientSocket.getOutputStream());
                                    threads[i].oos.writeObject(fileInfo);
                                }
                            }
                            if (Integer.parseInt(arStr[0]) == 1) {
                                if (this.clientUser.getUsername().compareTo(arStr[1]) == 0 && threads[i] == this) {
                                    threads[i].os1.write(("< you" + " to " + arStr[2] + ">" + arStr[3] + "\r\n").getBytes("UTF-8"));
                                    threads[i].os1.flush();
                                    threads[i].oos = new ObjectOutputStream(threads[i].clientSocket.getOutputStream());
                                    threads[i].oos.writeObject(null);
                                }
                                if (threads[i] != null && threads[i].clientUser.getUsername().compareTo(arStr[2]) == 0) {
                                    threads[i].os1.write(("<" + arStr[1] + " to " + "you >" + arStr[3] + "\r\n").getBytes("UTF-8"));
                                    threads[i].os1.flush();
                                    threads[i].oos = new ObjectOutputStream(threads[i].clientSocket.getOutputStream());
                                    threads[i].oos.writeObject(null);
                                }
                            }
                        }
                    }
                }
            }
            synchronized (this) {
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
