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

    private static List<SectionOfRoad> sections = new ArrayList<>();


    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationConfig.xml");
        SocketManager socketManager = (SocketManager) context.getBean("socketManager");
        socketManager.start();
    }

    private static void initSections() {
        sections.add(new SectionOfRoad(1, 2, 10));
        sections.add(new SectionOfRoad(2, 3, 15.5));
        sections.add(new SectionOfRoad(3, 4, 30));
        sections.add(new SectionOfRoad(4, 5, 28.3));
        sections.add(new SectionOfRoad(5, 6, 20));
        sections.add(new SectionOfRoad(6, 7, 41.6));
        sections.add(new SectionOfRoad(7, 8, 18));
        sections.add(new SectionOfRoad(8, 9, 28));
    }
}
