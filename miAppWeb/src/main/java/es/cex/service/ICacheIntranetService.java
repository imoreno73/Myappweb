package es.cex.service;


import es.cex.arq.exceptions.ServiceException;

/**
 * Interface for Cache Intranet service
 */
public interface ICacheIntranetService {

	public void flush()throws ServiceException;

}
