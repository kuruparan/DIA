package org.yarlithub.dia.repo;

import com.mysql.jdbc.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DBExecutor {
    private static final Logger LOGGER = Logger.getLogger(DBExecutor.class.getName());

    public static boolean isDevice() {
        Boolean result = false;
        Connection con = Connector.getConnection("", "", "");
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql;
            sql = "SELECT device_mask FROM device";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result = true;
            }
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "SQLException: " + se);
        } catch (Exception e) {
            //Handle errors for Class.forName
            LOGGER.log(Level.SEVERE, "Exception: " + e);
        }
        return result;
    }
}
