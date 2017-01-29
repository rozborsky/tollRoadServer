package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.sql2o.Sql2oException;
import ua.rozborsky.tollRoadServer.interfaces.DAO;
import ua.rozborsky.transmittedObjects.RequestFromClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by roman on 22.01.2017.
 */
public class ClientThread extends Thread {
    private static final Logger log = Logger.getLogger(ClientThread.class);
    private Socket socket;
    private boolean canRide = false;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            RequestFromClient requestFromClient = (RequestFromClient)inStream.readObject();
            System.out.println(requestFromClient.id());
            System.out.println(requestFromClient.client());

            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
            DAO dao = (DAO) context.getBean("daoMySQL");

            if (dao.isRegistered(requestFromClient.id())){
                Driver driver = dao.driver();
                if(driver.isActive()){
                    if (dao.isInChain(requestFromClient.id())) {
                        //todo вивести повідомлення що водій вже в мережі
                    } else {
                        dao.addDriverInChain();
                        canRide = true;
                    }
                }
            }

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(canRide);
        } catch (IOException | Sql2oException e) {
            log.error(e);//todo if throw this exception - client dosn't work
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

