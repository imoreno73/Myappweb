package es.cex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.authentication.jwt.utils.AuthenticationUtils;
import es.cex.common.constant.Constant;
import es.cex.common.utils.CommonUtils;
import es.cex.service.IAuthenticationService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IAuthenticationService}
 */
@Service("authentiationService")
public class AuthentiationServiceImpl implements IAuthenticationService {

	@Autowired
	private CommonUtils commonUtils;

	@Override
	public String getToken() throws ServiceException {

		return AuthenticationUtils.getToken();
	}

	@Override
	public <T,K> void addTokenToHeader(RestServiceParameter<T,K> restServiceParameter) throws ServiceException {

		String token = this.getToken();

		// Si tenemos token, lo añadimos
		if(this.commonUtils.isAvailable(token)) {
			final HttpHeaders headers = new HttpHeaders();
			headers.add(Constant.CEX_AUTH, token);
			restServiceParameter.setHeaders(headers);
		}
	}

}