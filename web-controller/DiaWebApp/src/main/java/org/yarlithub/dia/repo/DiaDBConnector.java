package org.yarlithub.dia.repo;


import com.mysql.jdbc.Connection;
import org.yarlithub.dia.util.DBConfig;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DiaDBConnector {
    private static final Logger LOGGER = Logger.getLogger(DiaDBConnector.class.getName());
    private static volatile Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager
                    .getConnection(DBConfig.mySqlUrl, DBConfig.mySqlUserName, DBConfig.mySqlPassword);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Where is your MySQL JDBC Driver?" + e);
            connection = null;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection Failed!" + e);
            connection = null;
        }
    }

    /**
     * Get a singleton connection.
     * @return
     */
    public static synchronized Connection getConnection(String url, String userName, String password) {
        return connection;

    }

    public static ResultSet safelyQuery(String sql) throws SQLException {

        Connection con = DiaDBConnector.getConnection("", "", "");
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sql);
    }

    public static int safelyUpdate(String sql) throws SQLException {

        Connection con = DiaDBConnector.getConnection("", "", "");
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(sql);
    }

}
