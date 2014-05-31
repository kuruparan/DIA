package org.yarlithub.dia.repo.object;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class Device {
 private int id;
    private String device_name;
    private String pin;
    private String device_mask;
    private int garden_id;

    public Device() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDevice_mask() {
        return device_mask;
    }

    public void setDevice_mask(String device_mask) {
        this.device_mask = device_mask;
    }

    public int getGarden_id() {
        return garden_id;
    }

    public void setGarden_id(int garden_id) {
        this.garden_id = garden_id;
    }
}
