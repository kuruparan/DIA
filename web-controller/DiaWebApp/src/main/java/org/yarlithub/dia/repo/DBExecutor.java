package org.yarlithub.dia.repo;

import org.yarlithub.dia.repo.object.Device;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DBExecutor {
    private static final Logger LOGGER = Logger.getLogger(DBExecutor.class.getName());

    public static Device getDeviceByMask(String device_mask) {
        Device device = new Device();
        try {
            String sql = String.format("SELECT * FROM device WHERE device_mask=\"%s\"", device_mask);
            ResultSet rs = DiaDBConnector.safelyQuery(sql);
            if (rs.next()) {
                device.setId(rs.getInt("id"));
                device.setDevice_name(rs.getString("device_name"));
                device.setPin(rs.getString("pin"));
                device.setDevice_mask(rs.getString("device_mask"));
                device.setGarden_id(rs.getInt("garden_id"));
            }
            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "SQLException: " + se);
        }
        return device;
    }

    public static Device getDeviceByName(String device_name) {
        Device device = new Device();
        try {
            String sql = String.format("SELECT * FROM device WHERE device_name=\"%s\"", device_name);
            ResultSet rs = DiaDBConnector.safelyQuery(sql);
            if (rs.next()) {
                device.setId(rs.getInt("id"));
                device.setDevice_name(rs.getString("device_name"));
                device.setPin(rs.getString("pin"));
                device.setDevice_mask(rs.getString("device_mask"));
                device.setGarden_id(rs.getInt("garden_id"));
            }
            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "SQLException: " + se);
        }
        return device;
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
        try {
            String sql = "SELECT id FROM device ORDER BY id DESC LIMIT 1";
            ResultSet rs = DiaDBConnector.safelyQuery(sql);
            if (rs.next()) {
                maxId = rs.getInt("id") + 1;
                String sqlIncrement =
                        String.format("INSERT INTO device (device_name, pin, device_mask) VALUES (\"%s\",\"%s\",\"%s\")"
                                , String.valueOf(maxId), "reserved", "reserved");

                DiaDBConnector.safelyUpdate(sqlIncrement);
            }
            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "SQLException: " + se);
        }
        return maxId;
    }

    public static int updateNewDevice(Device device) {
        int result = 0;
        String sql = String.format("UPDATE device "
                + "SET device_name = \"%s\", pin = \"%s\", device_mask = \"%s\" "
                + "WHERE id = \"%s\""
                , device.getDevice_name(), device.getPin(), device.getDevice_mask(), String.valueOf(device.getId()));

        try {
            result = DiaDBConnector.safelyUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }

    public static int insertDeviceAccess(int device_id, String user_mask) {
        int result = 0;
        String sql = String.format("INSERT INTO device_access (device_id, user_mask)VALUES (\"%s\",\"%s\") "
                , String.valueOf(device_id), user_mask);

        try {
            result = DiaDBConnector.safelyUpdate(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException: " + e);
        }
        return result;
    }
}
