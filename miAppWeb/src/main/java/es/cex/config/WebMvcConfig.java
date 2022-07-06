package es.cex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import es.cex.app.arq.config.CexWebMvcConfig;
import es.cex.config.interceptor.ThemeInterceptor;

@Configuration
public class WebMvcConfig extends CexWebMvcConfig {

	@Autowired
	private ThemeInterceptor themeInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(themeInterceptor);
	}

}