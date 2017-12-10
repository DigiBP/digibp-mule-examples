/*
 * Copyright (c) 2017. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.digibp.mule;

import ch.fhnw.digibp.mule.config.EnableMuleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,DispatcherServletAutoConfiguration.class,
        WebMvcAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableMuleApplication
public class MuleApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MuleApplication.class);

    public static void main(String... args) {
        LOGGER.info("Starting Mule application...");
        new SpringApplicationBuilder(MuleApplication.class).web(false).run(args);
        LOGGER.info("Mule application has started!");
    }
}
