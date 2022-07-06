package es.cex.service;


import es.cex.arq.exceptions.ServiceException;

/**
 * Interface for Authentication service
 */
public interface IAuthenticationService {

	public String getToken() throws ServiceException;

	public <T,K> void addTokenToHeader(RestServiceParameter<T,K> restServiceParameter) throws ServiceException;

}
