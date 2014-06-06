package org.yarlithub.dia.sms;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;
import org.yarlithub.dia.util.Property;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 6/1/14.
 */
public class SmsRequestProcessor {
    private final static Logger LOGGER = Logger.getLogger(SmsHandler.class.getName());

    public static void sendWebDeviceCommand(String deviceMask, String command) {
        MtSmsReq mtSmsReq = new MtSmsReq();
        mtSmsReq.setApplicationId(Property.getValue("APP_ID"));
        mtSmsReq.setPassword("APP_PASS");
        List<String> addressList = new ArrayList<String>();
        addressList.add(deviceMask);
        mtSmsReq.setDestinationAddresses(addressList);
        mtSmsReq.setMessage(command);


        SmsRequestSender smsMtSender;
        try {
            smsMtSender = new SmsRequestSender(new URL(Property.getValue("sdp.sms.url")));

            MtSmsResp mtSmsResp = null;
            mtSmsResp = smsMtSender.sendSmsRequest(mtSmsReq);
        } catch (SdpException e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred while sending SMS", e);
        } catch (MalformedURLException e) {
            LOGGER.info("MalformedURLException on initializing SmsHandler");
        }
    }

    public MtSmsResp sendCommand(SmsRequestSender smsMtSender, MtSmsReq mtSmsReq) {
        MtSmsResp mtSmsResp = null;
        try {
            mtSmsResp = smsMtSender.sendSmsRequest(mtSmsReq);
        } catch (SdpException e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred while sending SMS", e);
        }
        return mtSmsResp;
    }
}
