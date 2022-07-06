package es.cex.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.cex.arq.exceptions.ServiceException;
import es.cex.service.IRestClientService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link RestClientService}.
 */
@Service("restClientService")
public class RestClientServiceImpl implements IRestClientService {

	/** LOG. */
	private static final Log LOG = LogFactory.getLog(RestClientServiceImpl.class);

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public <T, K> K getRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException {
		return exchangeRestService(restServiceParameter, HttpMethod.GET);
	}

	@Override
	public <T, K> K postRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException {
		return exchangeRestService(restServiceParameter, HttpMethod.POST);

	}

	@Override
	public <T, K> K putRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException {
		return exchangeRestService(restServiceParameter, HttpMethod.PUT);

	}

	@Override
	public <T, K> K patchRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException {
		return exchangeRestService(restServiceParameter, HttpMethod.PATCH);
	}

	@Override
	public <T, K> K deleteRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException {
		return exchangeRestService(restServiceParameter, HttpMethod.DELETE);
	}

	/**
	 *
	 * @param <T>
	 * @param <K>
	 * @param parameterObject
	 * @param method
	 * @return
	 * @throws ServiceException
	 */
	private <T, K> K exchangeRestService(final RestServiceParameter<T, K> parameterObject, HttpMethod method)
			throws ServiceException {

		String auth = parameterObject.getAuth();
		Map<String, ?> uriVariables = parameterObject.getUriVariables();
		K response = null;
		final HttpHeaders headers = new HttpHeaders();

		if (auth != null) {
			if (auth.indexOf("Basic") == -1) {
				auth = "Basic " + auth;
			}
			LOG.debug("RestClientService auth: " + auth);
			headers.set("Authorization", auth);
		}
		headers.set("Content-Type", "application/json");

		if (parameterObject.getHeaders() != null) {
			headers.addAll(parameterObject.getHeaders());
		}

		if (uriVariables == null) {
			uriVariables = new HashMap<>();
		}

		final HttpEntity<T> entity = new HttpEntity<>(parameterObject.getRequest(), headers);
		ResponseEntity<K> responseEntity = null;
		try {
			responseEntity = this.restTemplate.exchange(parameterObject.getUrl(), method, entity,
					parameterObject.getResponseType(), uriVariables);

			if (parameterObject.getResponseHandler() != null) {
				parameterObject.getResponseHandler().accept(responseEntity);
			}

		} catch (RestClientException e) {
			response = (K) parameterObject.getExceptionHandler().accept(e);
		}

		if (responseEntity != null) {
			response = responseEntity.getBody();
		}
		return response;
	}

}
