package ua.rozborsky.tollRoadServer.classes;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rozborsky.tollRoadServer.interfaces.DAO;

import java.io.*;
import java.net.Socket;

/**
 * Created by roman on 22.01.2017.
 */
public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String id = in.readLine();

            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
            DAO socketManager = (DAO) context.getBean("daoMySQL");
            boolean isActive = socketManager.isRegisteredUser(Integer.valueOf(id));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(isActive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

