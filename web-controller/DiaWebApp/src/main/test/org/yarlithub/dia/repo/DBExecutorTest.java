package org.yarlithub.dia.repo;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class DBExecutorTest {

    @Test
    public void isDeviceTest() {
        assertTrue(DBExecutor.isDevice());
    }
}
