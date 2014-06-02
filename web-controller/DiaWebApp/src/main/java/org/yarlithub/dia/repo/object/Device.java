package org.yarlithub.dia.repo.object;

import org.yarlithub.dia.util.OperationMode;
import org.yarlithub.dia.util.OperationType;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class Device {
 private int id;
    private String deviceName;
    private String pin;
    private String deviceMask;
    private int gardenId;
    private int operationMode;
    private int operationType;

    public Device() {
        this.id = 0;
        this.operationMode= OperationMode.DEFAULT;
        this.operationType= OperationType.MANUAL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDeviceMask() {
        return deviceMask;
    }

    public void setDeviceMask(String deviceMask) {
        this.deviceMask = deviceMask;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(int operationMode) {
        this.operationMode = operationMode;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }
}
