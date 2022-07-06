package es.cex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.cex.common.thymeleaf.dateUtils.DateUtilsDialect;

@Configuration
public class ThymeleafConfig {

	@Bean
	public DateUtilsDialect conditionalCommentDialect() {
	    return new DateUtilsDialect();
	}

}