package org.yarlithub.dia.repo;

import com.mysql.jdbc.Connection;
import org.yarlithub.dia.repo.object.Device;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/30/14.
 */
public class DataLayer {
    private static final Logger LOGGER = Logger.getLogger(DataLayer.class.getName());

    public static Device getDeviceByMask(String device_mask) {
        String sql = String.format("SELECT * FROM device WHERE device_mask=\"%s\"", device_mask);
        return DiaDBUtil.getDevice(sql);
    }

    public static Device getDeviceByName(String device_name) {
        String sql = String.format("SELECT * FROM device WHERE device_name=\"%s\"", device_name);
        return DiaDBUtil.getDevice(sql);
    }

    public static boolean isUser() {
        return false;
    }

    /**
     * get the maximum id of all the device and put a dummy value for new device to update.
     *
     * @return maximum id of all devices+1 , used to create new device name.
     */
    public static synchronized int reserveNewDevice() {
        int maxId = Integer.MAX_VALUE;

        Connection con = DiaDBConnector.getConnection();
        String sqlMaxId = "SELECT id FROM device ORDER BY id DESC LIMIT 1";
        try {
            ResultSet rs = DiaDBUtil.sqlQuery(con, sqlMaxId);
            if (rs.next()) {
                maxId = rs.getInt("id") + 1;
                String sqlIncrement =
                        String.format("INSERT INTO device (device_name, pin, device_mask) VALUES (\"%s\",\"%s\",\"%s\")"
                                , String.valueOf(maxId), "reserved", "reserved");
                DiaDBUtil.sqlUpdate(con, sqlIncrement);
            }
            rs.close();
            con.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "SQLException: " + se);
        }
        return maxId;
    }

    public static int updateNewDevice(Device device) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("UPDATE device "
                + "SET device_name = \"%s\", pin = \"%s\", device_mask = \"%s\" "
                + "WHERE id = \"%s\""
                , device.getDevice_name(), device.getPin(), device.getDevice_mask(), String.valueOf(device.getId()));

        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
            con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }

    public static int insertDeviceAccess(int device_id, String user_mask) {
        int result = 0;
        Connection con = DiaDBConnector.getConnection();
        String sql = String.format("INSERT INTO device_access (device_id, user_mask)VALUES (\"%s\",\"%s\") "
                , String.valueOf(device_id), user_mask);
        try {
            result = DiaDBUtil.sqlUpdate(con, sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
}
