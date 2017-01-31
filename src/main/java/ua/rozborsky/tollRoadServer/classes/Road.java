package ua.rozborsky.tollRoadServer.classes;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by roman on 31.01.2017.
 */
@Service("road")
public class Road {
    private List<SectionOfRoad> sections = Arrays.asList(
            new SectionOfRoad(1, 2, 10),
            new SectionOfRoad(2, 3, 15.5),
            new SectionOfRoad(3, 4, 30),
            new SectionOfRoad(4, 5, 28.3),
            new SectionOfRoad(5, 6, 20),
            new SectionOfRoad(6, 7, 41.6),
            new SectionOfRoad(7, 8, 18),
            new SectionOfRoad(8, 9, 28)
    );

    public List<SectionOfRoad> sectionsOfRoad() {
        return this.sections;
    }
}
