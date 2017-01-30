package ua.rozborsky.tollRoadServer.classes;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.sql2o.Sql2oException;
import ua.rozborsky.tollRoadServer.interfaces.DAO;
import ua.rozborsky.transmittedObjects.AnswerFromServer;
import ua.rozborsky.transmittedObjects.Client;
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
        SendEmail sendEmail = new SendEmail("roman.rozborsky@gmail.com");
        sendEmail.send();
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            RequestFromClient requestFromClient = (RequestFromClient)inStream.readObject();

            AnswerFromServer answerFromServer = checkId(requestFromClient);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(answerFromServer);
            out.flush();
        } catch (IOException | Sql2oException | ClassNotFoundException e) {
            log.error(e);//todo if throw this exception - client dosn't work
        }
    }

    private AnswerFromServer checkId(RequestFromClient requestFromClient) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
        DAO dao = (DAO) context.getBean("daoMySQL");

        String message = Properties.notRegistered();

        if (dao.isRegistered(requestFromClient.id())){
            message = Properties.blocked();

            Driver driver = dao.driver();
            if(driver.isActive()){
                if (requestFromClient.client().equals(Client.entrance)){//todo too big method
                    message = Properties.inChain();
                    if (!dao.isInChain(requestFromClient.id())) {
                        dao.addDriverInChain();
                        canRide = true;
                        message = Properties.ok();
                    }
                } else {
                    message = Properties.notInChain();
                    if (dao.isInChain(requestFromClient.id())) {
                        dao.removeDriverFromChain(requestFromClient.id());
                        canRide = true;
                        message = Properties.ok();
                    }
                }
            }
        }
        AnswerFromServer answerFromServer = (AnswerFromServer) context.getBean("answerFromServer");
        answerFromServer.setMessage(message);
        answerFromServer.setCanRide(canRide);

        return answerFromServer;
    }
}

