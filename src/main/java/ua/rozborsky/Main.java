package ua.rozborsky;

/**
 * Created by roman on 21.01.2017.
 */
public class Main {
    static SocketManager socketManager = new SocketManager();

    public static void main(String[] args) {
        socketManager.connect();
    }
}
