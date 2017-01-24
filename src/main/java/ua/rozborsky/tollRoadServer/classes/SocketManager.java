package ua.rozborsky.tollRoadServer.classes;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by roman on 21.01.2017.
 */
@Service("socketManager")
public class SocketManager {
    static final int PORT = 4444;

    public void connect() {
        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                System.out.println("wait for client");
                socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();//todo------------------
        }
    }
}
