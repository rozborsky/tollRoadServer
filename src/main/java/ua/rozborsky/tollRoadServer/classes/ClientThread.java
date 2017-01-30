package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.sql2o.Sql2oException;
import ua.rozborsky.tollRoadServer.interfaces.DAO;
import ua.rozborsky.transmittedObjects.AnswerFromServer;
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

            ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
            DAO dao = (DAO) context.getBean("daoMySQL");

            String message = Properties.notRegistered();

            if (dao.isRegistered(requestFromClient.id())){//todo refactor method
                message = Properties.blocked();

                Driver driver = dao.driver();
                if(driver.isActive()){
                    message = Properties.inChain();

                    if (!dao.isInChain(requestFromClient.id())) {
                        dao.addDriverInChain();
                        canRide = true;
                        message = Properties.ok();
                    }
                }
            }
            AnswerFromServer answerFromServer = (AnswerFromServer) context.getBean("answerFromServer");
            answerFromServer.setMessage(message);
            answerFromServer.setCanRide(canRide);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(answerFromServer);
            out.flush();
        } catch (IOException | Sql2oException e) {
            log.error(e);//todo if throw this exception - client dosn't work
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

