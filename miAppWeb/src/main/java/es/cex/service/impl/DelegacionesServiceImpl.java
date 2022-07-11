package es.cex.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.DelegacionesDto;
import es.cex.dto.DelegacionesPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;
import es.cex.service.IAuthenticationService;
import es.cex.service.IDelegacionesService;
import es.cex.service.IRestClientService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IDelegacionesService}
 */
@Service("DelegacionesService")
public class DelegacionesServiceImpl /*implements IDelegacionesService */{
//
//	/** LOG */
//	private static final Logger LOG = LoggerFactory.getLogger(DelegacionesServiceImpl.class);
//
//	@Value("${ws.get.list.delegaciones.url}")
//	private String wsGetDelegacionessListUrl;
//
//	@Value("${ws.get.paginated.delegaciones.url}")
//	private String wsGetPaginatedDelegacionessUrl;
//
//	@Value("${ws.get.delegaciones.url}")
//	private String wsGetDelegacionesUrl;
//
//	@Value("${ws.post.delegaciones.url}")
//	private String wsPostDelegacionesUrl;
//
//	@Value("${ws.put.delegaciones.url}")
//	private String wsPutDelegacionesUrl;
//
//	@Value("${ws.delete.delegaciones.url}")
//	private String wsDeleteDelegacionesUrl;
//
//	@Value("${REST_AUTH}")
//	private String restAuth;
//
//	@Autowired
//	private IRestClientService restClientService;
//
//	@Autowired
//	private IAuthenticationService authenticationService;
//
//	@Override
//	public List<DelegacionesDto> getDelegacionesList() throws ServiceException {
//
//		List<DelegacionesDto> Delegacioness = null;
//
//		final RestServiceParameter<Object, List> restServiceParameter = RestServiceParameter
//				.create(this.wsGetDelegacionessListUrl, List.class).setAuth(this.restAuth);
//
//		this.authenticationService.addTokenToHeader(restServiceParameter);
//
//		try {
//			Delegacioness = this.restClientService.getRestService(restServiceParameter);
//		} catch (Exception e) {
//			LOG.error("Error al recuperar la lista completa de delegaciones", e);
//			throw new ServiceException("Error al recuperar la lista completa de delegaciones", e);
//		}
//
//		return Delegacioness;
//
//	}
//
//	@Override
//	public PaginatedListDto<DelegacionesDto> getPaginatedList(
//			DelegacionesPaginatedRequestDto DelegacionesPaginatedRequestDto) throws ServiceException {
//
//		PaginatedListDto<DelegacionesDto> paginatedListDto = null;
//		String urlFrmt = null;
//
//		try {
//			urlFrmt = MessageFormat
//					.format(wsGetPaginatedDelegacionessUrl, DelegacionesPaginatedRequestDto.getName(),
//							DelegacionesPaginatedRequestDto.getPage(), DelegacionesPaginatedRequestDto.getSize())
//					.replace("null", "");
//
//			final RestServiceParameter<Object, PaginatedListDto> restServiceParameter = RestServiceParameter
//					.create(urlFrmt, PaginatedListDto.class).setAuth(this.restAuth);
//
//			this.authenticationService.addTokenToHeader(restServiceParameter);
//
//			paginatedListDto = this.restClientService.getRestService(restServiceParameter);
//		} catch (Exception e) {
//			LOG.error("Error al recuperar la lista paginada de delegaciones", e);
//			throw new ServiceException("Error al recuperar la lista paginada de delgaciones", e);
//		}
//
//		return paginatedListDto;
//
//	}
//
//	@Override
//	public DelegacionesDto getDelegacionesBySlug(String slug) throws ServiceException {
//
//		DelegacionesDto DelegacionesDto = null;
//		String urlFrmt = null;
//
//		try {
//			urlFrmt = MessageFormat.format(this.wsGetDelegacionesUrl, slug);
//
//			final RestServiceParameter<Object, DelegacionesDto> restServiceParameter = RestServiceParameter
//					.create(urlFrmt, DelegacionesDto.class).setAuth(this.restAuth);
//
//			this.authenticationService.addTokenToHeader(restServiceParameter);
//
//			DelegacionesDto = this.restClientService.getRestService(restServiceParameter);
//		} catch (Exception e) {
//			LOG.error("Error al recuperar la delegacion por slug: {}", slug, e);
//			throw new ServiceException("Error al recuperar la delegacion por slug: " + slug, e);
//		}
//
//		return DelegacionesDto;
//
//	}
//
//	@Override
//	public DelegacionesDto save(DelegacionesDto DelegacionesDto) throws ServiceException {
//
//		DelegacionesDto newDelegacionesDto = null;
//
//		try {
//
//			final RestServiceParameter<DelegacionesDto, DelegacionesDto> restServiceParameter = RestServiceParameter.create(this.wsPostDelegacionesUrl, DelegacionesDto, DelegacionesDto.class)
//					.setAuth(this.restAuth);
//
//			this.authenticationService.addTokenToHeader(restServiceParameter);
//
//			newDelegacionesDto = this.restClientService.postRestService(restServiceParameter);
//
//		} catch (Exception e) {
//			LOG.error("Error al almacenar la delegacion: {}", DelegacionesDto.getName(), e);
//			throw new ServiceException("Error al almacenar la delegacion: " + DelegacionesDto.getName(), e);
//		}
//
//		return newDelegacionesDto;
//
//	}
//
//	@Override
//	public DelegacionesDto update(DelegacionesDto DelegacionesDto) throws ServiceException {
//
//		DelegacionesDto newDelegacionesDto = null;
//		String urlFrmt = null;
//
//		try {
//			urlFrmt = MessageFormat.format(this.wsPutDelegacionesUrl, DelegacionesDto.getId().toString());
//
//			final RestServiceParameter<DelegacionesDto, DelegacionesDto> restServiceParameter = RestServiceParameter.create(urlFrmt, DelegacionesDto, DelegacionesDto.class)
//					.setAuth(this.restAuth);
//
//			this.authenticationService.addTokenToHeader(restServiceParameter);
//
//			newDelegacionesDto = this.restClientService.putRestService(restServiceParameter);
//
//		} catch (Exception e) {
//			LOG.error("Error al actualizar la delegacion: {}", DelegacionesDto.getName(), e);
//			throw new ServiceException("Error al actualizar la delegacion: " + DelegacionesDto.getName(), e);
//		}
//
//		return newDelegacionesDto;
//
//	}
//
//	@Override
//	public void delete(Long id) throws ServiceException {
//
//		String urlFrmt = null;
//
//		try {
//
//			urlFrmt = MessageFormat.format(this.wsDeleteDelegacionesUrl, id.toString());
//
//			final RestServiceParameter<Object, DelegacionesDto> restServiceParameter = RestServiceParameter
//					.create(urlFrmt, DelegacionesDto.class).setAuth(this.restAuth);
//
//			this.authenticationService.addTokenToHeader(restServiceParameter);
//
//			this.restClientService.deleteRestService(restServiceParameter);
//
//		} catch (Exception e) {
//			LOG.error("Error al eliminar la delegacion: {}", id, e);
//			throw new ServiceException("Error al eliminar la delegacion: " + id, e);
//		}

//	}

}