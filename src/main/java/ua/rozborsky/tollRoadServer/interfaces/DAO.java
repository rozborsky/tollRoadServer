package ua.rozborsky.tollRoadServer.interfaces;

import ua.rozborsky.tollRoadServer.classes.Driver;

/**
 * Created by roman on 22.01.2017.
 */
public interface DAO {
    boolean isRegistered(int id);

    Driver driver();

    void addDriverInChain(int entranceCheckPoint);

    void removeDriverFromChain(int id);

    boolean isInChain(int id);

    int entranceCheckPoint(int id);
}
