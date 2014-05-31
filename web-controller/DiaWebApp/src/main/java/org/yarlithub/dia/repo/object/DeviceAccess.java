package org.yarlithub.dia.repo.object;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DeviceAccess {
    private int id;
    private String user_name;
    private String user_mask;
    private int device_id;

    public DeviceAccess() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mask() {
        return user_mask;
    }

    public void setUser_mask(String user_mask) {
        this.user_mask = user_mask;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }
}
