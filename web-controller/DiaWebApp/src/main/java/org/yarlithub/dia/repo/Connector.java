package org.yarlithub.dia.repo;


import com.mysql.jdbc.Connection;
import org.yarlithub.dia.util.DBConfig;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class Connector {
    private static volatile Connection connection;

    /**
     * Get a singleton connection.
     * @return
     */
    public static Connection getConnection(String url, String userName, String password) {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager
                        .getConnection(DBConfig.mySqlUrl, DBConfig.mySqlUserName, DBConfig.mySqlPassword);

            } catch (ClassNotFoundException e) {
                System.out.println("Where is your MySQL JDBC Driver?");
                e.printStackTrace();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                connection = null;
            }
            return connection;
        }
    }
}
