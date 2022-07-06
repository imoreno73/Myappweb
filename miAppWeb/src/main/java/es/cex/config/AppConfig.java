package es.cex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import es.cex.config.CexConfigurer.TipoAplicacion;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"es.cex.*"})
public class AppConfig {

	@Bean
  	public CexConfigurer cexConfigurer()  {
    	 final CexConfigurer configurer = new CexConfigurer();
    	 configurer.setTipoAplicacion(TipoAplicacion.WEB);
    	 configurer.setHasToken(true);
    	 configurer.setMetricsSecurity(false);
    	 return configurer;
  	}

}