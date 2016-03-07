package com.tamtran.myreceipt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Tam on 2/27/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceiptControllerTest {
    private static final Logger logger = LogManager.getLogger();
    private static String OS = System.getProperty("os.name").toLowerCase();
    @Test
    public void testEnv() throws Exception {
        logger.error(OS);
        logger.debug(OS);
        logger.info(OS);
    }
}