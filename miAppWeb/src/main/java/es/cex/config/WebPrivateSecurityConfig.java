package es.cex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import es.cex.authentication.jwt.config.CexWebSecurityConfig;

/**
 * This type serves as a base class for extensions of the
 * {@code WebSecurityConfigurerAdapter} and provides a default configuration.
 * <br/>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true,
		  securedEnabled = true,
		  jsr250Enabled = true)
public class WebPrivateSecurityConfig extends CexWebSecurityConfig {

	public WebPrivateSecurityConfig() {
		super();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

}
