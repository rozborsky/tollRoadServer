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
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;

/**
 * Created by roman on 22.01.2017.
 */
public class ClientThread extends Thread {
    private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
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

            AnswerFromServer answerFromServer = checkId(requestFromClient);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(answerFromServer);
            out.flush();
        } catch (IOException | Sql2oException | ClassNotFoundException e) {
            log.error(e);
            try {
                socket.close();
            } catch (IOException er) {
                log.error(er);
            }
        }
    }

    private AnswerFromServer checkId(RequestFromClient requestFromClient) {
        DAO dao = (DAO) context.getBean("daoMySQL");
        String message = Properties.notRegistered();
        int id = requestFromClient.id();

        if (dao.isRegistered(id)){
            message = Properties.blocked();

            Driver driver = dao.driver();
            if(driver.isActive()){
                if (requestFromClient.client().equals(Client.entrance)){
                    message = Properties.inChain();
                    if (!dao.isInChain(id)) {
                        dao.addDriverInChain(requestFromClient.checkPoint());
                        canRide = true;
                        message = Properties.ok();
                    }
                } else {
                    message = Properties.notInChain();
                    if (dao.isInChain(id)) {
                        double distance = calculateDistance(dao.entranceCheckPoint(id), requestFromClient.checkPoint());
                        BigDecimal price = calculatePrice(distance, Properties.price());

                        dao.removeDriverFromChain(id);
                        canRide = true;
                        message = Properties.ok();

                        sendEmail(driver.getE_mail(), distance, price);
                    }
                }
            }
        }
        AnswerFromServer answerFromServer = (AnswerFromServer) context.getBean("answerFromServer");
        answerFromServer.setMessage(message);
        answerFromServer.setCanRide(canRide);

        return answerFromServer;
    }

    private void sendEmail(String email, double distance, BigDecimal price) {
        //SendEmail sendEmail = new SendEmail("roman.rozborsky@gmail.com");
        SendEmail sendEmail = new SendEmail(email);
        String eMail = emailMessage(distance, price);
        sendEmail.setMessage(eMail);
        sendEmail.send();
    }

    private String emailMessage(double distance, BigDecimal price) {
        return "Good day. You use the toll road system. You drove " + distance + " kilometers. Payment is " + price + " dollars";
    }

    private BigDecimal calculatePrice(double dst, double prc){
        BigDecimal distance = new BigDecimal(String.valueOf(dst));
        BigDecimal price = new BigDecimal(String.valueOf(prc));

        return distance.multiply(price);
    }

    private double calculateDistance(int entranceCheckPoint, int exitCheckPoint) {
        Road road = (Road) context.getBean("road");
        List<SectionOfRoad> sections = road.sectionsOfRoad();
        int checkPointFirst;
        int checkPointSecond;

        if (entranceCheckPoint < exitCheckPoint) {
            checkPointFirst = entranceCheckPoint;
            checkPointSecond = exitCheckPoint;
        } else {
            checkPointFirst = exitCheckPoint;
            checkPointSecond = entranceCheckPoint;
        }

        double distance = 0;
        for (int i = checkPointFirst; i < checkPointSecond; i++) {
            distance += sections.get(i - 1).getLength();
        }

        return distance;
    }
}

