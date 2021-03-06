package ua.rozborsky.tollRoadServer;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rozborsky.tollRoadServer.classes.SectionOfRoad;
import ua.rozborsky.tollRoadServer.classes.SendEmail;
import ua.rozborsky.tollRoadServer.classes.SocketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 21.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
        SocketManager socketManager = (SocketManager) context.getBean("socketManager");
        socketManager.start();
    }
}
