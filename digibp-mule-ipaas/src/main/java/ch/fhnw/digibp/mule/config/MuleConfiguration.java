/*
 * Copyright (c) 2017. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.digibp.mule.config;

import org.mule.api.MuleContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mule-deploy.properties")
public class MuleConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuleConfiguration.class);

    @Value("${config.resources}")
    String configResources;

    @Autowired
    private ApplicationContext context;

    @Bean
    public MuleConfiguration init() {
        try {
            LOGGER.info("Starting Mule...");
            SpringXmlConfigurationBuilder configurationBuilder = new SpringXmlConfigurationBuilder(configResources);
            configurationBuilder.setParentContext(context);

            MuleContext muleContext = new DefaultMuleContextFactory().createMuleContext(configurationBuilder);
            muleContext.start();
            LOGGER.info("Mule has started!");
        } catch (Exception e) {
            LOGGER.info("Error starting Mule...");
            e.printStackTrace();
        }
        return this;
    }
}
