package es.cex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@ConfigurationProperties
@Configuration
@ComponentScan(basePackages = {" es.cex.*"})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

	private static final Class<Application> applicationClass = Application.class;
	private static final Log LOG = LogFactory.getLog(applicationClass);

	public static void main(String[] args) {
		try {
		LOG.info("Arranca ...");
		SpringApplication.run(applicationClass, args);
		LOG.info("Arrancado ...");
		}catch (Exception e) {
			LOG.error("Error en la aplicación ...", e);
		}
	}

}