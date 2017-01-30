package ua.rozborsky.transmittedObjects;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by roman on 29.01.2017.
 */
@Service("requestFromClient")
public class RequestFromClient implements Serializable {
    private int checkPoint;
    private Client client;
    private int id;

    public void setValues(int cpt, Client clt, int id){
        this.checkPoint = cpt;
        this.client = clt;
        this.id = id;
    }

    public Client client(){
        return client;
    }

    public int id() {
        return id;
    }

    public int checkPoint() {
        return checkPoint;
    }
}
