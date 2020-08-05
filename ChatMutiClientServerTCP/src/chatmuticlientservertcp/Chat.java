/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmuticlientservertcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
class Chat extends Observable {

    private Socket socket;
    private DataOutputStream outputStream;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    public void InitSocket(String client, int port) throws IOException {

        socket = new Socket(client, port);
        outputStream = new DataOutputStream(socket.getOutputStream());

        Thread NhanThread = new Thread() {
            @Override
            public void run() {
                try {
                    DataInputStream reader = new DataInputStream(socket.getInputStream());
                    String line;
                    while ((line = reader.readLine()) != null) {
                        ois = new ObjectInputStream(socket.getInputStream());
                        FileTranfer FileReceive = (FileTranfer) ois.readObject();
                        if (FileReceive != null) {
                            createFile(FileReceive);
                        }
                        notifyObservers(line);
                        reader = new DataInputStream(socket.getInputStream());
                    }
                } catch (IOException ex) {
                    notifyObservers(ex);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        NhanThread.start();
    }
    private static final String CRLF = "\r\n";

    public void sendFile(String fileName, String filePath, String client1, String client2) throws IOException {
        send("2", "file", client1, client2);
        FileTranfer fileInfo = getFile(filePath);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(fileInfo);

    }

    public FileTranfer getFile(String filePath) throws IOException {
        FileTranfer fileinfo = null;
        BufferedInputStream bis = null;
        try {
            File file = new File(filePath);
            bis = new BufferedInputStream(new FileInputStream(file));
            fileinfo = new FileTranfer();
            byte[] fileBytes = new byte[(int) file.length()];
            bis.read(fileBytes, 0, fileBytes.length);
            fileinfo.setDataBytes(fileBytes);
            fileinfo.setDestinationDirectory("D:\\server\\");
            fileinfo.setFilename(file.getName());
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            bis.close();
        }
        return fileinfo;
    }

    public boolean createFile(FileTranfer fileInfo) throws IOException {
        BufferedOutputStream bos = null;

        try {
            if (fileInfo != null) {
                File fileReceive = new File(fileInfo.getDestinationDirectory()
                        + fileInfo.getFilename());
                bos = new BufferedOutputStream(
                        new FileOutputStream(fileReceive));
                // write file content
                bos.write(fileInfo.getDataBytes());
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            bos.close();
        }
        return true;
    }

    public void send(String loai, String text, String client1, String client2) {
        try {
            outputStream.write((loai + "." + client1 + "." + client2 + "." + text + CRLF).getBytes("UTF-8"));
            outputStream.flush();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}
