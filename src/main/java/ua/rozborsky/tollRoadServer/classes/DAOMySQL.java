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
    public boolean isRegistered = false;

    static{
        sql2o = new Sql2o("jdbc:mysql://localhost:3306/toll_road?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
    }


    @Override
    public boolean isRegistered(int id) {
        String sql = "SELECT * FROM drivers WHERE id_driver=" + id;
        try(Connection con = sql2o.open()) {
            List<Driver> drivers =  con.createQuery(sql).executeAndFetch(Driver.class);
            if (drivers.size() != 0){
                this.isRegistered = true;
                this.driver = drivers.get(0);

                return drivers.get(0).isActive();
            }

            return false;
        }
    }

    @Override
    public Driver driver() {
        return this.driver;
    }


    public boolean isActive() {
        return driver.isActive() == true;
    }

    @Override
    public void addDriverInChain() {
        String insertSql =
                "INSERT into drivers_on_roads (iddrivers_on_roads) VALUES (:id)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                .addParameter("id", driver.getId_driver())
                .executeUpdate();
        }
    }
}
