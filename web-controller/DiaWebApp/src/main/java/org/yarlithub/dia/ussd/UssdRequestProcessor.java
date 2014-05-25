package org.yarlithub.dia.ussd;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdResp;
import org.yarlithub.dia.util.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/24/14.
 */
public class UssdRequestProcessor {

    private final static Logger LOGGER = Logger.getLogger(UssdHandler.class.getName());

    // hardcoded values
    private static final String EXIT_SERVICE_CODE = "000";
    private static final String BACK_SERVICE_CODE = "999";
    private static final String PROPERTY_KEY_PREFIX = "menu.level.";
    private static final String USSD_OP_MO_INIT = "mo-init";
    private static final String USSD_OP_MT_CONT = "mt-cont";
    private static final String USSD_OP_MT_FIN = "mt-fin";

    // menu state saving for back button
    private List<String> menuState = new ArrayList<>();

    // service to send the request
    private UssdRequestSender ussdMtSender;

    public UssdRequestProcessor(UssdRequestSender ussdMtSender) {
        this.ussdMtSender = ussdMtSender;
    }

    /**
     * Build the response based on the requested service code
     *
     * @param moUssdReq MO Ussd request.
     */
    public void processRequest(final MoUssdReq moUssdReq) throws SdpException {

        LOGGER.log(Level.INFO, "**************menuState array is now: "+menuState.toString());

        // exit request - session destroy
        if (moUssdReq.getMessage().equals(EXIT_SERVICE_CODE)) {
            terminateSession(moUssdReq);
            return;// completed work and return
        }

        // back button handling
        if (moUssdReq.getMessage().equals(BACK_SERVICE_CODE)) {
            backButtonHandle(moUssdReq);
            return;// completed work and return
        }

        // get current service code
        String serviceCode;
        if (USSD_OP_MO_INIT.equals(moUssdReq.getUssdOperation())) {
            serviceCode = "0";
            clearMenuState();
        } else {
            serviceCode = getServiceCode(moUssdReq);
            LOGGER.log(Level.INFO, "**************MoUssdRequest message: "+moUssdReq.getMessage());
            LOGGER.log(Level.INFO, "**************Service code: "+serviceCode);
        }
        // create request to display user
        final MtUssdReq request = createRequest(moUssdReq, serviceCode, USSD_OP_MT_CONT);
        sendRequest(request);
        // record menu state
        menuState.add(serviceCode);
    }

    /**
     * Build request object
     *
     * @param moUssdReq     - Receive request object
     * @param menuContent   - menu to display next
     * @param ussdOperation - operation
     * @return MtUssdReq - filled request object
     */
    private MtUssdReq createRequest(final MoUssdReq moUssdReq, final String menuContent, final String ussdOperation) {
        final MtUssdReq request = new MtUssdReq();
        request.setApplicationId(moUssdReq.getApplicationId());
        request.setEncoding(moUssdReq.getEncoding());
        request.setMessage(menuContent);
        request.setPassword(Messages.getMessage(moUssdReq.getApplicationId()));
        request.setSessionId(moUssdReq.getSessionId());
        request.setUssdOperation(Messages.getMessage("operation"));
        request.setVersion(moUssdReq.getVersion());
        request.setDestinationAddress(moUssdReq.getSourceAddress());
        return request;
    }

    /**
     * load a property from ussdmenu.properties
     *
     * @param key Key
     * @return value
     */
    private String getText(final String key) {
        return getPropertyValue(PROPERTY_KEY_PREFIX + key);
    }

    private String getPropertyValue(final String key) {
        //return key;
        return Messages.getMessage(key);
    }

    /**
     * Request sender
     *
     * @param request
     * @return MtUssdResp
     */
    private MtUssdResp sendRequest(final MtUssdReq request) throws SdpException {
        // sending request to service
        MtUssdResp response = null;
        try {
            response = ussdMtSender.sendUssdRequest(request);
        } catch (SdpException e) {
            LOGGER.log(Level.INFO, "Unable to send request", e);
            throw e;
        }

        // response status
        String statusCode = response.getStatusCode();
        String statusDetails = response.getStatusDetail();
        if (StatusCodes.SuccessK.equals(statusCode)) {
            LOGGER.info("MT USSD message successfully sent");
        } else {
            LOGGER.info("MT USSD message sending failed with status code [" + statusCode + "] " + statusDetails);
        }
        return response;
    }

    /**
     * Clear history list
     */
    private void clearMenuState() {
        LOGGER.info("clear history List");
        menuState.clear();
    }

    /**
     * Terminate session
     *
     * @param moUssdReq MO Ussd request.
     * @throws hms.kite.samples.api.SdpException
     */
    private void terminateSession(final MoUssdReq moUssdReq) throws SdpException {
        final MtUssdReq request = createRequest(moUssdReq, "", USSD_OP_MT_FIN);
        sendRequest(request);
    }

    /**
     * Handlling back button with menu state
     *
     * @param moUssdReq MO Ussd request.
     * @throws hms.kite.samples.api.SdpException
     */
    private void backButtonHandle(final MoUssdReq moUssdReq) throws SdpException {
        String lastMenuVisited = "0";

        // remove last menu when back
        if (menuState.size() > 0) {
            menuState.remove(menuState.size() - 1);
            lastMenuVisited = menuState.get(menuState.size() - 1);
        }

        // create request and send
        final MtUssdReq request = createRequest(moUssdReq, buildMenuContent(lastMenuVisited), USSD_OP_MT_CONT);
        sendRequest(request);

        // clear menu status
        if (lastMenuVisited.equals("0")) {
            clearMenuState();
            // add 0 to menu state ,finally its in main menu
            menuState.add("0");
        }

    }

    /**
     * Create service code to navigate through menu and for property loading
     *
     * @param moUssdReq MO Ussd request.
     * @return serviceCode
     */
    private String getServiceCode(final MoUssdReq moUssdReq) {
        String serviceCode = "0";
        try {
            serviceCode = moUssdReq.getMessage();
        } catch (NumberFormatException e) {
            return serviceCode;
        }

        // create service codes for child menus based on the main menu codes
        if (menuState.size() > 0 && !menuState.get(menuState.size() - 1).equals("0")) {
            String generatedChildServiceCode = "" + menuState.get(menuState.size() - 1) + serviceCode;
            serviceCode = generatedChildServiceCode;
        }

        return serviceCode;
    }

    /**
     * Build menu based on the service code
     *
     * @param selection
     * @return menuContent
     */
    private String buildMenuContent(final String selection) {
        String menuContent;
        try {
            // build menu contents
            menuContent = getText(selection);
        } catch (MissingResourceException e) {
            // back to main menu
            menuContent = getText("0");
        }
        return menuContent;
    }

}
