/*
 * Copyright (c) 2017. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.digibp.mule;

import org.mule.api.MuleContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MuleStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MuleStarter.class);

    public static void main(String... args) {
        try {
            Properties prop = new Properties();
            prop.load(MuleStarter.class.getClassLoader().getResourceAsStream("mule-deploy.properties"));
            LOGGER.info("Starting Mule...");
            SpringXmlConfigurationBuilder configurationBuilder = new SpringXmlConfigurationBuilder(prop.getProperty("config.resources"));
            MuleContext muleContext = new DefaultMuleContextFactory().createMuleContext(configurationBuilder);
            muleContext.start();
            LOGGER.info("Mule has started!");
        } catch (Exception e) {
            LOGGER.info("Error starting Mule...");
            e.printStackTrace();
        }
    }
}
