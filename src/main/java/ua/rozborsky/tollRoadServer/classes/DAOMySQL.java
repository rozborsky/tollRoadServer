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

    static{
        sql2o = new Sql2o("jdbc:mysql://localhost:3306/toll_road?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
    }

    public boolean isRegisteredUser(int id) {
        String sql = "SELECT * FROM drivers WHERE id_driver=" + id;
        try(Connection con = sql2o.open()) {
            List<Driver> drivers =  con.createQuery(sql).executeAndFetch(Driver.class);
            System.out.println(drivers.size());

        }
        return false;
    }
}
