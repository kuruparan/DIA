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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmsHandler implements MoSmsListener {
    private final static Logger LOGGER = Logger.getLogger(SmsHandler.class.getName());
    Device device;

    @Override
    public void init() {
        LOGGER.info("Initiating SMS Handler");
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        try {
            LOGGER.info("Sms Received for generate request : " + moSmsReq);
            SmsRequestSender smsMtSender = new SmsRequestSender(new URL(Property.getValue("sdp.sms.url")));

            MtSmsReq mtSmsReq;

            //  mtSmsReq = createSimpleMtSms(moSmsReq);
            mtSmsReq = createRoutedSms(moSmsReq);

            mtSmsReq.setApplicationId(moSmsReq.getApplicationId());
            mtSmsReq.setPassword("664f195dbd79a85d0e0f2194e8ff4a2e");
            mtSmsReq.setVersion(moSmsReq.getVersion());

            String deliveryReq = moSmsReq.getDeliveryStatusRequest();
            if (deliveryReq != null) {
                if (deliveryReq.equals("1")) {
                    mtSmsReq.setDeliveryStatusRequest("1");
                }
            } else {
                mtSmsReq.setDeliveryStatusRequest("0");
            }

            MtSmsResp mtSmsResp = smsMtSender.sendSmsRequest(mtSmsReq);
            String statusCode = mtSmsResp.getStatusCode();
            String statusDetails = mtSmsResp.getStatusDetail();
            if (StatusCodes.SuccessK.equals(statusCode)) {
                LOGGER.info("MT SMS message successfully sent : "+mtSmsReq);
                MtSmsReq mtReplySmsReq = createReplyMtSms(moSmsReq, mtSmsReq.getMessage() + " command sent to your device " + device.getDevice_name());

                mtReplySmsReq.setApplicationId(moSmsReq.getApplicationId());
                mtReplySmsReq.setPassword("664f195dbd79a85d0e0f2194e8ff4a2e");
                mtReplySmsReq.setVersion(moSmsReq.getVersion());
                smsMtSender.sendSmsRequest(mtReplySmsReq);
            } else {
                LOGGER.info("MT SMS message sending failed with status code [" + statusCode + "] "+statusDetails);
            }


        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred", e);
        }
    }

    private MtSmsReq createReplyMtSms(MoSmsReq moSmsReq, String message) {
        MtSmsReq mtSmsReq = new MtSmsReq();

        mtSmsReq.setMessage(message);
        List<String> addressList = new ArrayList<String>();
        addressList.add(moSmsReq.getSourceAddress());
        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }

    /**
     * get the users on/off sms and prepare with destination address to his controlling device
     *
     * @param moSmsReq
     * @return
     */
    private MtSmsReq createRoutedSms(MoSmsReq moSmsReq) {
        MtSmsReq mtSmsReq = new MtSmsReq();

        mtSmsReq.setMessage(moSmsReq.getMessage());
        List<String> addressList = new ArrayList<String>();
        DeviceAccess deviceAccess = DataLayer.getDeviceAccessByMask(moSmsReq.getSourceAddress());
        LOGGER.info("Device access check : deviceId " + deviceAccess.getDevice_id());
        if (deviceAccess.getId() > 0) {
            device = DataLayer.getDeviceById(deviceAccess.getDevice_id());
            addressList.add(device.getDevice_mask());
            LOGGER.info("Detected user's device : " + device.getDevice_name());
        }
        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }

}
