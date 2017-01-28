package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by roman on 21.01.2017.
 */
@Service("socketManager")
public class SocketManager {
    private static final Logger log = Logger.getLogger(SocketManager.class);

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(Properties.port())) {

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
