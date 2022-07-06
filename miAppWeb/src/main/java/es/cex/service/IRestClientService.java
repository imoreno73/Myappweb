package es.cex.service;

import es.cex.arq.exceptions.ServiceException;


/**
 * Interface for Rest request.
 */
public interface IRestClientService {



	/**
	 *
	 * @param <T>
	 * @param restServiceParameter
	 * @return
	 * @throws ServiceException
	 */
	public <T, K> K getRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException;

	/**
	 * Post rest service.
	 *
	 * @param <T>          Tipo parametrizado para la respuesta
	 * @param <K>          the key type
	 * @param url          Path del servicio Rest
	 * @param request      Datos para buscar la información del seguimiento
	 * @param responseType Tipo de la respuesta
	 * @return Rest service response
	 * @throws ServiceException the service exception
	 */
	public <T, K> K postRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException;




	/**
	 * Put rest service.
	 *
	 * @param <T>          Tipo parametrizado para la respuesta
	 * @param <K>          the key type
	 * @param url          Path del servicio Rest
	 * @param request      Datos para buscar la información del seguimiento
	 * @param responseType Tipo de la respuesta
	 * @param uriVariables the uri variables
	 * @param validStatus  List of valid HTTP codes
	 * @return Rest service response
	 * @throws ServiceException the service exception
	 */
	public <T, K> K putRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException;



	/**
	 * Patch rest service.
	 *
	 * @param <T>          Tipo parametrizado para la respuesta
	 * @param <K>          the key type
	 * @param url          Path del servicio Rest
	 * @param request      Datos para buscar la información del seguimiento
	 * @param responseType Tipo de la respuesta
	 * @param uriVariables the uri variables
	 * @param validStatus  List of valid HTTP codes
	 * @return Rest service response
	 * @throws ServiceException the service exception
	 */
	public <T, K> K patchRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException;


	/**
	 * Delete rest service.
	 *
	 * @param url          Path del servicio Rest
	 * @param uriVariables the uri variables
	 * @param validStatus  List of valid HTTP codes
	 * @throws ServiceException the service exception
	 */
	public <T, K> K deleteRestService(RestServiceParameter<T, K> restServiceParameter) throws ServiceException;

}
