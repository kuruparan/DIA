package org.yarlithub.dia.sms;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 6/1/14.
 */
public class SmsRequestProcessor {
    private final static Logger LOGGER = Logger.getLogger(SmsHandler.class.getName());

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
