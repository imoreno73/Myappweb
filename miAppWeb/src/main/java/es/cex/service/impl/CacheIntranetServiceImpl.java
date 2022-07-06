package es.cex.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.service.ICacheIntranetService;
import es.cex.service.IRestClientService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link ICacheIntranetService}
 */
@Service("cacheIntranetService")
public class CacheIntranetServiceImpl implements ICacheIntranetService {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(CacheIntranetServiceImpl.class);

	@Value("${cex.intranet.host:test}")
	private String intranetHostUrl;

	@Autowired
	private IRestClientService restClientService;


	@Override
	public void flush() throws ServiceException {

		String urlFrmt = intranetHostUrl+"/cache/flush";

		try {

			final RestServiceParameter<Object, String> restServiceParameter = RestServiceParameter
					.create(urlFrmt, String.class);

			this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al eliminar el cache: {}", e);
			throw new ServiceException("Error al eliminar el cache. ", e);
		}

	}

}