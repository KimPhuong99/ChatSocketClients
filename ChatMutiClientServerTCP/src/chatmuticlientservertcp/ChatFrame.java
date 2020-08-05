/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmuticlientservertcp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorListener;

/**
 *
 * @author Admin
 */
public class ChatFrame extends JFrame implements Observer {

    private JPanel panelDau;
    private JPanel panelDK;
    private JPanel panelDN;
    private JPanel panelChat;
    private JButton bt_DK;
    private JButton bt_DN;
    private JButton bt_OK1;
    private JButton bt_OK2;
    private JButton bt_send;
    private JTextField text_ten;
    private JTextField text_pass;
    private user client = null;
    private Chat chat;
    private JTextArea textArea;
    private user receiveClient = null;
    private int coGuiFile = 0;
    private String path = "";
    private String fileName = "";
    private JTextField t1;
    private String nguoiNhan = "";

    public ChatFrame(Chat access) {
        this.chat = access;
        buildGUI();
    }

    public ChatFrame() {
        buildGUI();
    }

    public void buildGUI() {
        buildPanelDau();
    }

    public void buildPanelDau() {
        panelDau = new JPanel();
        panelDau.setLayout(new BorderLayout());
        JPanel headpanel = new JPanel();
        JLabel tt = new JLabel("Welcome to COWF", JLabel.CENTER);
        tt.setFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 18));
        headpanel.add(tt);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        bt_DK = new JButton("Sign in.");
        bt_DK.setAlignmentX(Component.CENTER_ALIGNMENT);
        ActionListener chuyenQuaDK = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(panelDau);
                buildDK();
                validate();
            }
        };
        ActionListener chuyenQuaDN = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(panelDau);
                buildDN();
                validate();
            }
        };
        bt_DK.addActionListener(chuyenQuaDK);

        panel.add(bt_DK);
        bt_DN = new JButton("Log in.");
        bt_DN.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(bt_DN);
        bt_DN.addActionListener(chuyenQuaDN);
        panelDau.add(headpanel, BorderLayout.NORTH);
        panelDau.add(panel, BorderLayout.CENTER);
        setContentPane(panelDau);

    }

    public void buildDK() {
        setSize(400, 250);
        panelDK = new JPanel();
        panelDK.setLayout(new BorderLayout());
        JPanel headpanel = new JPanel();
        JLabel tt = new JLabel("SIGN IN", JLabel.CENTER);
        tt.setFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 18));
        headpanel.add(tt);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        JLabel label_Ten = new JLabel("Username: ", JLabel.RIGHT);
        panel.add(label_Ten);
        text_ten = new JTextField(12);
        panel.add(text_ten);
        JLabel label_Pass = new JLabel("Password: ", JLabel.RIGHT);
        panel.add(label_Pass);
        text_pass = new JTextField(12);
        panel.add(text_pass);

        JPanel footPane = new JPanel();
        JButton button = new JButton("Sign in");
        footPane.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDK.add(headpanel, BorderLayout.NORTH);
        panelDK.add(panel, BorderLayout.CENTER);
        panelDK.add(footPane, BorderLayout.SOUTH);
        setContentPane(panelDK);

        ActionListener DangKy;
        DangKy = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = text_ten.getText().trim();
                String pass = text_pass.getText().trim();
                if (name.length() != 0 && pass.length() != 0) {
                    //đọc file rồi so sánh
                    DOMExample DOM = new DOMExample();
                    java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
                    System.out.println("Chiu dai cua ds:" + dsnd.size());
                    int i = 0;
                    for (user A : dsnd) {
                        if (A.getUsername().compareTo(name) == 0) {
                            i = 1;
                        }
                    }
                    if (i == 0) {
                        user A = new user(name, pass, 0, 0);
                        dsnd.add(A);
                        DOMCreateXMLExample DOME = new DOMCreateXMLExample();
                        if (DOME.writeListStudents(dsnd) == 1) {
                            JOptionPane.showMessageDialog(null, "Đăng ký thành công.");
                        }
                        remove(panelDK);
                        buildDN();
                        validate();
                    } else {
                        JOptionPane.showMessageDialog(null, "Trùng Username. Mời nhập lại");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username hoặc Password trống");
                }
            }
        };
        button.addActionListener(DangKy);

    }

    public void buildDN() {
        setSize(400, 250);
        panelDN = new JPanel();
        panelDN.setLayout(new BorderLayout());
        JPanel headpanel = new JPanel();
        JLabel tt = new JLabel("LOG IN", JLabel.CENTER);
        tt.setFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 18));
        headpanel.add(tt);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        JLabel label_Ten = new JLabel("Username: ", JLabel.RIGHT);
        panel.add(label_Ten);
        text_ten = new JTextField(12);
        panel.add(text_ten);
        JLabel label_Pass = new JLabel("Password: ", JLabel.RIGHT);
        panel.add(label_Pass);
        text_pass = new JTextField(12);
        panel.add(text_pass);

        JPanel footPane = new JPanel();
        JButton button = new JButton("Log in");
        footPane.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDN.add(headpanel, BorderLayout.NORTH);
        panelDN.add(panel, BorderLayout.CENTER);
        panelDN.add(footPane, BorderLayout.SOUTH);
        setContentPane(panelDN);
        ActionListener DangNhap;
        DangNhap = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = text_ten.getText().trim();
                String pass = text_pass.getText().trim();
                if (name.length() != 0 && pass.length() != 0) {
                    //đọc file rồi so sánh
                    DOMExample DOM = new DOMExample();
                    java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
                    int i = 0;
                    for (user A : dsnd) {
                        if (A.getUsername().compareTo(name) == 0 && A.getPassword().compareTo(pass) == 0) {
                            i = 1;
                            int max = 0;
                            for (user Q : dsnd) {
                                if (Q.getRun() > max) {
                                    max = Q.getRun();
                                }
                            }
                            A.setRun(max + 1);
                            client = A;
                            DOMCreateXMLExample DOME = new DOMCreateXMLExample();
                            DOME.writeListStudents(dsnd);
                            dsnd = DOM.readListStudents("dsnd.xml");
                            break;
                        }
                    }
                    if (i == 1) {
                        chat();
                        remove(panelDN);
                        buildChat();
                        validate();
                        try {
                            chat.InitSocket("localhost", 3200);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username hoặc Password sai.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username hoặc Password trống");
                }
            }
        };
        button.addActionListener(DangNhap);
    }

    public void buildChat() {
        setSize(600, 400);
        panelChat = new JPanel();
        panelChat.setLayout(new BorderLayout());

        JPanel jptitle = new JPanel();
        JPanel jpbotton = new JPanel();
        JPanel jpControl = new JPanel();
        JPanel jplist = new JPanel();
        jplist.setLayout(new BorderLayout());

        jpControl.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.RED)));
        jplist.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
        jptitle.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.CYAN)));
        jpbotton.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.ORANGE)));

        jpbotton.setLayout(new BorderLayout());

        JButton btSend = new JButton("Send");
        jptitle.add(new JLabel("Welcome " + client.getUsername() + " to Chat Online"));
        jpbotton.add(btSend, BorderLayout.CENTER);
        JButton btExit = new JButton("Exit");
        jpbotton.add(btExit, BorderLayout.EAST);
        JButton btFile = new JButton("Gui file");
        jpbotton.add(btFile, BorderLayout.WEST);

        DefaultListModel modelList = new DefaultListModel();
        DOMExample DOM = new DOMExample();
        java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
        for (user A : dsnd) {
            if (A.getUsername().compareTo(client.getUsername()) != 0) {
                modelList.addElement(A.getUsername());
                modelList.addElement("---------------");
            }
        }
        JList mJlist = new JList(modelList);
        mJlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jplist.add(new JScrollPane(mJlist));

        jpControl.setLayout(new BorderLayout());
        textArea = new JTextArea(20, 80);
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        jpControl.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel jpko = new JPanel(new BorderLayout());
        t1 = new JTextField();
        JTextField t2 = new JTextField();
        jpko.add(t1, BorderLayout.NORTH);
        jpko.add(t2, BorderLayout.SOUTH);
        jpControl.add(jpko, BorderLayout.NORTH);

        panelChat.add(jplist, BorderLayout.WEST);
        panelChat.add(jpControl, BorderLayout.CENTER);
        panelChat.add(jptitle, BorderLayout.NORTH);
        panelChat.add(jpbotton, BorderLayout.SOUTH);
        btFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jf = new JFileChooser();
                int returnVal = jf.showOpenDialog(null);
                if (returnVal == jf.APPROVE_OPTION) {
                    coGuiFile = 1;
                    path = jf.getSelectedFile().getAbsolutePath();
                    fileName = jf.getSelectedFile().getName();
                    t2.setText(path);
                }
            }

        });
        btSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Nhan = t1.getText().trim();
                String str = t2.getText().trim();
                DOMExample DOM = new DOMExample();
                java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
                int conNguoiDung = 0;
                for (user A : dsnd) {
                    if (A.getUsername().compareTo(Nhan) == 0 && A.getRun() != 0) {
                        conNguoiDung = 1;
                    }
                }
                if (nguoiNhan.length() == 0) {
                    nguoiNhan = Nhan;
                }
                if (nguoiNhan.compareTo(Nhan) != 0) {
                    textArea.setText("");
                    nguoiNhan = Nhan;
                }
                if (conNguoiDung == 1) {
                    if (coGuiFile == 1) {
                        coGuiFile = 0;
                        try {
                            chat.sendFile(fileName, str, client.getUsername(), Nhan);
                        } catch (IOException ex) {
                            Logger.getLogger(ChatFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        if (str != null && str.trim().length() > 0) {
                            chat.send("1", str, client.getUsername(), Nhan);
                        }
                    }
                    t2.selectAll();
                    t2.requestFocus();
                    t2.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Người dùng này không hoạt động hoặc không tồn tại");
                }
            }
        });
        btExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Nhan = t1.getText().trim();
                String str = t2.getText().trim();

                DOMExample DOM = new DOMExample();
                java.util.List<user> dsnd = DOM.readListStudents("dsnd.xml");
                for (user Q : dsnd) {
                    if (Q.getUsername().compareTo(client.getUsername()) == 0) {
                        Q.setRun(0);
                        DOMCreateXMLExample DOME = new DOMCreateXMLExample();
                        DOME.writeListStudents(dsnd);
                        break;
                    }
                }
                chat.send("1", "quit", client.getUsername(), Nhan);
                setVisible(false);
            }
        });
        setContentPane(panelChat);
    }

    public void chat() {
        chat.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        final Object finalArg = arg;
        String text = finalArg.toString();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (text.contains(t1.getText())) {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new ChatFrame();
        frame.setTitle("MyChatApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
