package ua.rozborsky.transmittedObjects;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by roman on 30.01.2017.
 */
@Service("answerFromServer")
public class AnswerFromServer   implements Serializable {
    private boolean canRide;
    private String message;

    public boolean canRide() {
        return canRide;
    }

    public void setCanRide(boolean canRide) {
        this.canRide = canRide;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
