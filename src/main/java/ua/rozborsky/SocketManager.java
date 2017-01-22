package ua.rozborsky;

import ua.rozborsky.classes.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by roman on 21.01.2017.
 */
public class SocketManager {
    static final int PORT = 4444;

    public void connect() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();//todo------------------
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();//todo------------------
            }

            new ClientThread(socket).start();
        }
    }
}
