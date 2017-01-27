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
    private boolean canRide = false;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String id = in.readLine();

            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
            DAO dao = (DAO) context.getBean("daoMySQL");

            if (dao.isRegistered(Integer.valueOf(id))){
                Driver driver = dao.driver();
                if(dao.isActive()) {
                    dao.addDriverInChain();
                    canRide = true;
                }
            }

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(canRide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

