package org.yarlithub.dia.sms;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/28/14.
 */


import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;
import org.yarlithub.dia.repo.DataLayer;
import org.yarlithub.dia.repo.object.Device;
import org.yarlithub.dia.repo.object.DeviceAccess;
import org.yarlithub.dia.util.DiaCommonUtil;
import org.yarlithub.dia.util.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmsHandler implements MoSmsListener {
    private final static Logger LOGGER = Logger.getLogger(SmsHandler.class.getName());
    Device device;
    SmsRequestSender smsMtSender;
    SmsRequestProcessor smsRequestProcessor;

    @Override
    public void init() {
        LOGGER.info("Initiating SMS Handler");

//        try {
//            smsMtSender = new SmsRequestSender(new URL(Property.getValue("sdp.sms.url")));
//        } catch (MalformedURLException e) {
//            LOGGER.info("MalformedURLException on initializing SmsHandler");
//        }
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        LOGGER.info("DIA Sms Received : " + moSmsReq);

        smsRequestProcessor = new SmsRequestProcessor();
        try {
            String message = DiaSmsUtil.removeDIA(moSmsReq.getMessage());
            MtSmsReq deviceMtSms, userMtSms;
            MtSmsResp deviceMtResp = null, userMtResp = null;

            if (message.startsWith("dd")) {
                //message received from device
                Device device = DataLayer.getDeviceByMask(moSmsReq.getSourceAddress());
                if (device.getId() > 0) {
                    String deviceName = device.getDeviceName();
                    List<DeviceAccess> deviceAccessList = DataLayer.getDeviceAccessListByDevice(String.valueOf(device.getId()));

                    List<String> addressList = new ArrayList<String>();
                    for (DeviceAccess deviceAccess : deviceAccessList) {
                        addressList.add(deviceAccess.getUserMask());
                    }
                    userMtSms = DiaSmsUtil.createUserReplyMtSms(moSmsReq);
                    userMtSms.setDestinationAddresses(addressList);

                    if (message.startsWith("dd on")) {
                        device.setCurrentStatus(1);
                        DataLayer.updateDevice(device);
                        message = message.substring(6);
                        String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                        String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));

                        //TODO: send sensor data to DIA intellegent and get message
                        userMtSms.setMessage(deviceName + " switched on, Moisture level " + moisture + "%, temperature " + temperature + " C.");
                    } else if (message.startsWith("dd off")) {
                        device.setCurrentStatus(0);
                        DataLayer.updateDevice(device);
                        message = message.substring(7);
                        String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                        String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));

                        //TODO: send sensor data to DIA intellegent and get message
                        userMtSms.setMessage(deviceName + " switched off, Moisture level " + moisture + "%, temperature " + temperature + " C.");
                    } else if (message.startsWith("dd shd on")) {
                        message = message.substring(10);
                        String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                        String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));

                        //TODO: send sensor data to DIA intellegent and get message
                        userMtSms.setMessage(deviceName + " switched on by your schedule, Moisture level " + moisture + "%, temperature " + temperature + " C.");
                    } else if (message.startsWith("dd shd off")) {
                        message = message.substring(11);
                        String temperature = String.valueOf(DiaCommonUtil.temperatureValue(message));
                        String moisture = String.valueOf(DiaCommonUtil.moistureValue(message));

                        //TODO: send sensor data to DIA intellegent and get message
                        userMtSms.setMessage(deviceName + " switched off by your schedule, Moisture level " + moisture + "%, temperature " + temperature + " C.");
                    } else if (message.startsWith("dd shd T")) {
                        userMtSms.setMessage("Schedule successfully loaded to " + deviceName);
                    } else if (message.startsWith("dd rst")) {
                        deviceMtSms = DiaSmsUtil.createDeviceReplyCommandMtSms(moSmsReq);
                        if (device.getOperationMode() == OperationType.MANUAL) {
                            deviceMtSms.setMessage("shd stop");
                            DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                            return;
                        } else {
                            String schedule = device.getSchedule();
                            schedule = "shd " + String.valueOf(DiaCommonUtil.getCurrentDay()) + ";" + schedule;
                            deviceMtSms.setMessage(schedule);
                            DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                            return;
                        }
                    }
                    DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                }

            } else {
                //message received from user
                deviceMtSms = DiaSmsUtil.createDeviceCommandMtSms(moSmsReq);
                userMtSms = DiaSmsUtil.createUserReplyMtSms(moSmsReq);
                if (message.equals("on")) {
                    // message is one of : on,off
                    deviceMtSms.setMessage(message);
                    LOGGER.info("Sending MT Sms message to device : " + deviceMtSms);
                    deviceMtResp = DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                    if (StatusCodes.SuccessK.equals(deviceMtResp.getStatusCode())) {
                        DeviceAccess deviceAccess = DataLayer.getDeviceAccessByMask(moSmsReq.getSourceAddress());
                        Device device1 = DataLayer.getDeviceById(deviceAccess.getDeviceId());
                        device1.setCurrentStatus(1);
                        DataLayer.updateDevice(device1);
                        LOGGER.info("MT SMS message successfully sent : " + deviceMtResp);
                    } else {
                        LOGGER.info("MT SMS message sending failed with status code [" + deviceMtResp.getStatusCode() + "] "
                                + deviceMtResp.getStatusDetail());
                    }

                } else if (message.equals("off")) {
                    // message is one of : on,off
                    deviceMtSms.setMessage(message);
                    LOGGER.info("Sending MT Sms message to device : " + deviceMtSms);
                    deviceMtResp = DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                    if (StatusCodes.SuccessK.equals(deviceMtResp.getStatusCode())) {
                        DeviceAccess deviceAccess = DataLayer.getDeviceAccessByMask(moSmsReq.getSourceAddress());
                        Device device1 = DataLayer.getDeviceById(deviceAccess.getDeviceId());
                        device1.setCurrentStatus(0);
                        DataLayer.updateDevice(device1);
                        LOGGER.info("MT SMS message successfully sent : " + deviceMtResp);
                    } else {
                        LOGGER.info("MT SMS message sending failed with status code [" + deviceMtResp.getStatusCode() + "] "
                                + deviceMtResp.getStatusDetail());
                    }

                } else if (message.startsWith("1") || message.startsWith("0")) {
                    //this is a scheduling message
                    //TODO:validate shd command
                    //boolean isValidSchedule = DiaSmsUtil.validateShdString(message);
                    //TODO: day should be device location based not server based.
                    deviceMtSms.setMessage("shd " + DiaCommonUtil.getCurrentDay() + ";" + message);
                    LOGGER.info("Sending MT Sms message to device : " + deviceMtSms);
                    deviceMtResp = DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);
                    if (StatusCodes.SuccessK.equals(deviceMtResp.getStatusCode())) {
                        LOGGER.info("MT SMS message successfully sent : " + deviceMtResp);
                    } else {
                        LOGGER.info("MT SMS message sending failed with status code [" + deviceMtResp.getStatusCode() + "] "
                                + deviceMtResp.getStatusDetail());
                    }

                } else if (message.startsWith("shd stop")) {
                    deviceMtSms.setMessage("shd stop");
                    DiaSmsUtil.sendCommand(smsMtSender, deviceMtSms);

                } else if (message.startsWith("mod")) {
                    //modes of operation change
                    //TODO:Mode change logic here
                    if (String.valueOf(message.charAt(4)).equals("d")) {
                        userMtSms.setMessage("Changing your operation mode to default");
                        DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                    } else if (String.valueOf(message.charAt(4)).equals("a")) {
                        userMtSms.setMessage("Changing your operation mode to alert, I'll check the situation and send you alerts");
                        DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                    }


                } else if (message.startsWith("rep")) {
                    //reporting change
                    //TODO:Rep change logic here
                    userMtSms.setMessage("Reporting facility is currently not available, I'll notify when it become available.");
                    DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                } else {
                    //error in user message format.
                    userMtSms.setMessage("Sorry, I can't understand what you say, please retry");
                    DiaSmsUtil.sendCommand(smsMtSender, userMtSms);
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred", e);
        }
        smsMtSender = null;
        smsRequestProcessor = null;

    }

}
