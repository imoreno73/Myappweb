package es.cex.service;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cex.arq.exceptions.ServiceException;
import es.cex.arq.service.impl.RestClientServiceImpl;

public class RestServiceParameter<T, K> {

	/** LOG. */
	private static final Log LOG_CLIENT = LogFactory.getLog(RestClientServiceImpl.class);

	private String url;
	private T request;
	private Class<K> responseType;
	private Map<String, ?> uriVariables;
	private String auth;
	private @NotNull RestClientExceptionHandler<K> exceptionHandler;
	private @NotNull RestClientResponseHandler<K> responseHandler;
	private HttpHeaders headers;

	private RestServiceParameter(String url, T request, Class<K> responseType) {
		this.exceptionHandler = this::defaultExceptionHandler;
		this.responseHandler = response -> 	LOG_CLIENT.debug("Respuesta obtenida ");
		this.url = url;
		this.request = request;
		this.responseType = responseType;
	}


	private K defaultExceptionHandler(RestClientException e) throws ServiceException {
		K response = null;
		if (e instanceof BadRequest) {
			final ObjectMapper mapper = new ObjectMapper();
			try {
				String responseBody = ((BadRequest)e).getResponseBodyAsString();
				if (responseBody != null) {
					response = mapper.readValue(((BadRequest)e).getResponseBodyAsString(), responseType);
				}
			} catch (Exception ex) {
				throw new ServiceException("Error en la llamada. " + ex.getMessage(), ex);
			}
		} else {
			throw new ServiceException("Error en la llamada " + this.getUrl() + ": " + e.getMessage());
		}
		return response;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public RestServiceParameter<T, K> setHeaders(HttpHeaders headers) {
		this.headers = headers;
		return this;
	}

	public static <T, K> RestServiceParameter<T, K> create(String url) {
		return new RestServiceParameter<>(url, null, null);
	}

	public static <T, K> RestServiceParameter<T, K> create(String url, T request, Class<K> responseType) {
		return new RestServiceParameter<>(url, request, responseType);
	}

	public static <T, K> RestServiceParameter<T, K> create(String url, T request) {
		return new RestServiceParameter<>(url, request, null);
	}

	public static <T, K> RestServiceParameter<T, K> create(String url, Class<K> responseType) {
		return new RestServiceParameter<>(url, null, responseType);
	}

	public String getUrl() {
		return url;
	}


	public T getRequest() {
		return request;
	}


	public Class<K> getResponseType() {
		return responseType;
	}

	public Map<String, ?> getUriVariables() {
		return uriVariables;
	}

	public RestServiceParameter<T, K> setUriVariables(Map<String, ?> uriVariables) {
		this.uriVariables = uriVariables;
		return this;
	}

	public String getAuth() {
		return auth;
	}

	public RestServiceParameter<T, K> setAuth(String auth) {
		this.auth = auth;
		return this;
	}

	public RestClientExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public RestServiceParameter<T, K> setExceptionHandler(RestClientExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public RestClientResponseHandler<K> getResponseHandler() {
		return responseHandler;
	}

	public RestServiceParameter<T, K> setResponseHandler(RestClientResponseHandler<K> invalidStatusHandler) {
		this.responseHandler = invalidStatusHandler;
		return this;
	}

}
