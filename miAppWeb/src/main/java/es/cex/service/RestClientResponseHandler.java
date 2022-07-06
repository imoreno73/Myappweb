package es.cex.service;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import es.cex.arq.exceptions.ServiceException;

@FunctionalInterface
public interface RestClientResponseHandler<T> {

	/**
	 *
	 * @param status
	 * @throws ServiceException
	 */
    void accept(@Nullable ResponseEntity<T> response) throws ServiceException;

}