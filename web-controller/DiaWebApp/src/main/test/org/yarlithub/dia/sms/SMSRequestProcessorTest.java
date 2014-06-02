package org.yarlithub.dia.sms;

import org.junit.Test;
import org.yarlithub.dia.util.OperationMode;

import static org.junit.Assert.assertEquals;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/31/14.
 */
public class SMSRequestProcessorTest {

    @Test
    public void TrimTest() {
        // assertTrue(DiaDBUtil.isDevice());
        String message = "DIA on";
        message = message.toLowerCase();
        if (message.startsWith("dia ")) {
            message = message.substring(4);
            assertEquals("Message not extracted!", message, "on");
        }
    }
}
