package es.cex.service;

import org.springframework.web.client.RestClientException;

import es.cex.arq.exceptions.ServiceException;

@FunctionalInterface
public interface RestClientExceptionHandler<T> {

    T accept(RestClientException e) throws ServiceException;

}
