package ua.rozborsky.classes;

import java.io.*;
import java.net.Socket;

/**
 * Created by roman on 22.01.2017.
 */
public class ClientThread extends Thread {
        private Socket socket;
        public ClientThread(Socket clientSocket) {
            this.socket = clientSocket;
        }

    public void run() {
        BufferedReader bufferedReader = null;

        try {
            System.out.println("connect");
            InputStream inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String line;

        try {
            line = bufferedReader.readLine();
            System.out.println(line);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
