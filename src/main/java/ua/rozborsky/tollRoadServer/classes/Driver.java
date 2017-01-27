package ua.rozborsky.tollRoadServer.classes;

/**
 * Created by roman on 25.01.2017.
 */
public class Driver {
    private int id_driver;
    private String name;
    private String surname;
    private boolean active;
    private String e_mail;

    public boolean isActive() {
        return active;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getSurname() {
        return surname;
    }

    public int getId_driver() {
        return id_driver;
    }

    public String getName() {
        return name;
    }

}
