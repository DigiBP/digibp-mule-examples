package ch.fhnw.digibp.mule;

import org.mule.api.MuleContext;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.StaticApplicationContext;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,DispatcherServletAutoConfiguration.class,
        WebMvcAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@PropertySource("classpath:mule-deploy.properties")
public class MuleBoot implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MuleBoot.class);

    @Autowired
    private ApplicationContext context;

    @Value("${config.resources}")
    String muleConfigResources;

    @Override
    public void run(String... strings) {
        DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = null;
        try {
            StaticApplicationContext staticApplicationContext = new StaticApplicationContext(context);
            configBuilder = new SpringXmlConfigurationBuilder(muleConfigResources);
            staticApplicationContext.refresh();
            configBuilder.setParentContext(staticApplicationContext);
            MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
            muleContext.start();
            log.info("Started Mule!");
        } catch (Exception e) {
            log.error("Error starting Mule...", e);
        }
    }

    public static void main(String... args) {
        log.info("Starting SpringApplication...");
        SpringApplication app = new SpringApplication(MuleBoot.class);
        app.setWebEnvironment(false);
        app.run(args);
        log.info("SpringApplication has started...");
    }
}