package es.cex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.cex.config.interceptor.ThemeInterceptor;

@Configuration
public class ThemeConfig {

	@Bean
  	public ThemeInterceptor themeInterceptor()  {
    	 return new ThemeInterceptor();
  	}

}