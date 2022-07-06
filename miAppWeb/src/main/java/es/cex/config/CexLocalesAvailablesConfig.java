package es.cex.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cex.arq.exceptions.ConfigException;

@Configuration
public class CexLocalesAvailablesConfig {

	@Value("${cex.locales.availables:{\"es\": \"Español\", \"pt\": \"Português\"}:test}")
	private String cexLocalesAvailables;

    @Bean("localesAvailables")
    public Map<String, String> getLocalesAvailables() throws ConfigException {
    	Map<String, String> result = null;
		try {
			result = new ObjectMapper().readValue(this.cexLocalesAvailables, HashMap.class);
		} catch (IOException e) {
			throw new ConfigException("Error al cargar los idiomas disponibles");
		}
        return result;
    }

}