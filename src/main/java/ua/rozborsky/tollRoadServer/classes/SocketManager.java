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
    private static final int PORT = 4444;

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            //todo lo4J-----------------------------
            e.printStackTrace();
        }
    }
}
