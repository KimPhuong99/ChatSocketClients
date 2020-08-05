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
public class user {

    private String Username;
    private String Password;
    private int ID;
    private int run = 0;

    public user() {

    }

    public user(String user, String pass, int id, int run) {
        Username = user;
        Password = pass;
        ID = id;
        this.run = run;
    }

    public String getUsername() {
        return Username;

    }

    public String getPassword() {
        return Password;
    }

    public void setUser(String user) {
        Username = user;
    }

    public void setPass(String pass) {
        Password = pass;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int r) {
        run = r;
    }
}
