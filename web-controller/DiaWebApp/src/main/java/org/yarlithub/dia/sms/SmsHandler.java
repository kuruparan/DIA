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
import org.yarlithub.dia.util.Property;

import java.net.MalformedURLException;
import java.net.URL;
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
        smsRequestProcessor = new SmsRequestProcessor();
        try {
            smsMtSender = new SmsRequestSender(new URL(Property.getValue("sdp.sms.url")));
        } catch (MalformedURLException e) {
            LOGGER.info("MalformedURLException on initializing SmsHandler");
        }
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        init();
        try {
            LOGGER.info("Sms Received for generate request : " + moSmsReq);
            String message = DiaSmsUtil.removeDIA(moSmsReq.getMessage());
            MtSmsReq deviceMtSms, userMtSms;
            MtSmsResp deviceMtResp = null, userMtResp = null;

            if (message.startsWith("dd")) {
                //message received from device
                Device device = DataLayer.getDeviceByMask(moSmsReq.getSourceAddress());
                if (device.getId() > 0) {
                    DeviceAccess deviceAccess = DataLayer.getDeviceAccessByDevice(String.valueOf(device.getId()));

                    List<String> addressList = new ArrayList<String>();
                    addressList.add(deviceAccess.getUserMask());
                    userMtSms = DiaSmsUtil.createUserReplyMtSms(moSmsReq);
                    userMtSms.setDestinationAddresses(addressList);

                    if (message.startsWith("dd on")) {
                        message = message.substring(6);
                        String temperature = message.substring(8,10);
                        String moisture = message.substring(13);

                        //TODO: send sensor data to DIA intellegent and get message
                        userMtSms.setMessage("Device switched on, Moisture level " +moisture+"%, temperature "+temperature +" C." );
                    }
                }

            } else {
                //message received from user
                deviceMtSms = DiaSmsUtil.createDeviceCommandMtSms(moSmsReq);
                userMtSms = DiaSmsUtil.createUserReplyMtSms(moSmsReq);
                if (message.equals("on") || message.equals("off")) {
                    // message is one of : on,off
                    deviceMtSms.setMessage(message);
                    deviceMtResp = smsRequestProcessor.sendCommand(smsMtSender, deviceMtSms);
                    if (StatusCodes.SuccessK.equals(deviceMtResp.getStatusCode())) {
                        LOGGER.info("MT SMS message successfully sent : " + deviceMtResp);
                        userMtSms.setMessage(message + " command successfully sent to your device");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                        //TODO:Dummy alert - remove after TadHack
                        userMtSms.setMessage("Moisture level 80% temperature 29C. I prefer you can wait another 6 hours to water, send dia off if you decided to stop");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    } else {
                        LOGGER.info("MT SMS message sending failed with status code [" + deviceMtResp.getStatusCode() + "] "
                                + deviceMtResp.getStatusDetail());
                        userMtSms.setMessage("There was a problem in sending your command, may be your device not active?");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    }

                } else if (message.startsWith("1") || message.startsWith("0")) {
                    //this is a scheduling message
                    //TODO:validate shd command
                    //boolean isValidSchedule = DiaSmsUtil.validateShdString(message);
                    deviceMtSms.setMessage("shd " + message);
                    deviceMtResp = smsRequestProcessor.sendCommand(smsMtSender, deviceMtSms);
                    if (StatusCodes.SuccessK.equals(deviceMtResp.getStatusCode())) {
                        LOGGER.info("MT SMS message successfully sent : " + deviceMtResp);
                        userMtSms.setMessage("Schedule " + message + " successfully sent to your device");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    } else {
                        LOGGER.info("MT SMS message sending failed with status code [" + deviceMtResp.getStatusCode() + "] "
                                + deviceMtResp.getStatusDetail());
                        userMtSms.setMessage("There was a problem in sending your command, may be your device not active?");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    }

                } else if (message.startsWith("mod")) {
                    //modes of operation change
                    if(String.valueOf(message.charAt(4)).equals("d")){
                        userMtSms.setMessage("Changing your operation mode to default");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    } else if(String.valueOf(message.charAt(4)).equals("a")){
                        userMtSms.setMessage("Changing your operation mode to alert, I'll check the situation and send you alerts");
                        smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                    }


                } else if (message.startsWith("rep")) {
                    //reporting change
                    userMtSms.setMessage("Reporting facility is currently not available, I'll notify when it available.");
                    smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                } else {
                    //error in user message format.
                    userMtSms.setMessage("Sorry, I can't understand what you say, please retry");
                    smsRequestProcessor.sendCommand(smsMtSender, userMtSms);
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred", e);
        }
        smsMtSender=null;
        smsRequestProcessor=null;

    }

}
