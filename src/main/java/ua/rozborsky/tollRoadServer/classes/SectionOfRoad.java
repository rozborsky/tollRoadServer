package ua.rozborsky.tollRoadServer.classes;

/**
 * Created by roman on 31.01.2017.
 */
public class SectionOfRoad {
    private int EntranceCheckPoint;
    private int ExitCheckPoint;
    private double length;

    public SectionOfRoad(int entrCP, int exitCP, double len) {
        EntranceCheckPoint = entrCP;
        ExitCheckPoint = exitCP;
        this.length = len;
    }

    public int getEntranceCheckPoint() {
        return EntranceCheckPoint;
    }

    public int getExitCheckPoint() {
        return ExitCheckPoint;
    }

    public double getLength() {
        return length;
    }
}
