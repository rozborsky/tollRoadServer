package ua.rozborsky.tollRoadServer.classes;

import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ua.rozborsky.tollRoadServer.interfaces.DAO;

import java.util.List;

/**
 * Created by roman on 22.01.2017.
 */
@Service("daoMySQL")
public class DAOMySQL implements DAO {
    private static Sql2o sql2o;
    private Driver driver;

    static{
        sql2o = new Sql2o(Properties.url(), Properties.user(), Properties.password());
    }


    @Override
    public boolean isRegistered(int id) {
        String sql = "SELECT * FROM drivers WHERE id_driver=" + id;
        try(Connection con = sql2o.open()) {
            List<Driver> drivers =  con.createQuery(sql).executeAndFetch(Driver.class);
            if(drivers.size() != 0) {
                driver = drivers.get(0);

                return true;
            }
            return false;
        }
    }

    @Override
    public Driver driver() {
        return this.driver;
    }


    @Override
    public void addDriverInChain() {
        String insertSql = "INSERT into drivers_on_roads (iddrivers_on_roads) VALUES (:id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("id", driver.getId_driver())
                    .executeUpdate();
        }
    }

    @Override
    public void removeDriverFromChain(int id) {
        String deleteSql = "DELETE FROM drivers_on_roads WHERE iddrivers_on_roads = :id;";

        try(Connection con = sql2o.open()) {
            con.createQuery(deleteSql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public boolean isInChain(int id) {
        String sql = "SELECT * FROM drivers_on_roads WHERE iddrivers_on_roads = :id;";

        try (Connection con = sql2o.open()) {
            List<Integer> idDriver =  con.createQuery(sql)
                    .addParameter("id", id)
                    .executeScalarList(Integer.class);

            return idDriver.size() != 0;
        }
    }
}
