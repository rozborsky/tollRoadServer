package ua.rozborsky.tollRoadServer.classes;

import ua.rozborsky.tollRoadServer.interfaces.DAO;

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
            InputStream inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            System.out.println(line);

        } catch (IOException e) {
            e.printStackTrace();//todo---------------------------------------------
            return;
        }

        sendResult();
    }

    private void sendResult() {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();//todo---------------------------------------------
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        DAO dao = new DAOMySQL();
        dao.createConnection();

        try {
            writer.write(dao.isRegisteredUser() + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();//todo---------------------------------------------
        }
    }
}
