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

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId_driver() {
        return id_driver;
    }

    public void setId_driver(int id_driver) {
        this.id_driver = id_driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
