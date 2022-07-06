package es.cex.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.FunctionalGroupPaginatedRequestDto;
import es.cex.dto.PaginatedListDto;
import es.cex.service.IAuthenticationService;
import es.cex.service.IFunctionalGroupService;
import es.cex.service.IRestClientService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IFunctionalGroupService}
 */
@Service("functionalGroupService")
public class FunctionalGroupServiceImpl implements IFunctionalGroupService {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(FunctionalGroupServiceImpl.class);

	@Value("${ws.get.list.functional.groups.url}")
	private String wsGetFunctionalGroupsListUrl;

	@Value("${ws.get.paginated.functional.groups.url}")
	private String wsGetPaginatedFunctionalGroupsUrl;

	@Value("${ws.get.functional.group.url}")
	private String wsGetFunctionalGroupUrl;

	@Value("${ws.post.functional.group.url}")
	private String wsPostFunctionalGroupUrl;

	@Value("${ws.put.functional.group.url}")
	private String wsPutFunctionalGroupUrl;

	@Value("${ws.delete.functional.group.url}")
	private String wsDeleteFunctionalGroupUrl;

	@Value("${REST_AUTH}")
	private String restAuth;

	@Autowired
	private IRestClientService restClientService;

	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	public List<FunctionalGroupDto> getFunctionalGroupList() throws ServiceException {

		List<FunctionalGroupDto> functionalGroups = null;

		final RestServiceParameter<Object, List> restServiceParameter = RestServiceParameter
				.create(this.wsGetFunctionalGroupsListUrl, List.class).setAuth(this.restAuth);

		this.authenticationService.addTokenToHeader(restServiceParameter);

		try {
			functionalGroups = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista completa de grupos funcionales", e);
			throw new ServiceException("Error al recuperar la lista completa de grupos funcionales", e);
		}

		return functionalGroups;

	}

	@Override
	public PaginatedListDto<FunctionalGroupDto> getPaginatedList(
			FunctionalGroupPaginatedRequestDto functionalGroupPaginatedRequestDto) throws ServiceException {

		PaginatedListDto<FunctionalGroupDto> paginatedListDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat
					.format(wsGetPaginatedFunctionalGroupsUrl, functionalGroupPaginatedRequestDto.getName(),
							functionalGroupPaginatedRequestDto.getPage(), functionalGroupPaginatedRequestDto.getSize())
					.replace("null", "");

			final RestServiceParameter<Object, PaginatedListDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PaginatedListDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			paginatedListDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista paginada de grupos funcionales", e);
			throw new ServiceException("Error al recuperar la lista paginada de grupos funcionales", e);
		}

		return paginatedListDto;

	}

	@Override
	public FunctionalGroupDto getFunctionalGroupBySlug(String slug) throws ServiceException {

		FunctionalGroupDto functionalGroupDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsGetFunctionalGroupUrl, slug);

			final RestServiceParameter<Object, FunctionalGroupDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, FunctionalGroupDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			functionalGroupDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar el grupo funcional por slug: {}", slug, e);
			throw new ServiceException("Error al recuperar el grupo funcional por slug: " + slug, e);
		}

		return functionalGroupDto;

	}

	@Override
	public FunctionalGroupDto save(FunctionalGroupDto functionalGroupDto) throws ServiceException {

		FunctionalGroupDto newFunctionalGroupDto = null;

		try {

			final RestServiceParameter<FunctionalGroupDto, FunctionalGroupDto> restServiceParameter = RestServiceParameter.create(this.wsPostFunctionalGroupUrl, functionalGroupDto, FunctionalGroupDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newFunctionalGroupDto = this.restClientService.postRestService(restServiceParameter);

		} catch (Exception e) {
			LOG.error("Error al almacenar el grupo funcional: {}", functionalGroupDto.getName(), e);
			throw new ServiceException("Error al almacenar el grupo funcional: " + functionalGroupDto.getName(), e);
		}

		return newFunctionalGroupDto;

	}

	@Override
	public FunctionalGroupDto update(FunctionalGroupDto functionalGroupDto) throws ServiceException {

		FunctionalGroupDto newFunctionalGroupDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsPutFunctionalGroupUrl, functionalGroupDto.getId().toString());

			final RestServiceParameter<FunctionalGroupDto, FunctionalGroupDto> restServiceParameter = RestServiceParameter.create(urlFrmt, functionalGroupDto, FunctionalGroupDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newFunctionalGroupDto = this.restClientService.putRestService(restServiceParameter);

		} catch (Exception e) {
			LOG.error("Error al actualizar el grupo funcional: {}", functionalGroupDto.getName(), e);
			throw new ServiceException("Error al actualizar el grupo funcional: " + functionalGroupDto.getName(), e);
		}

		return newFunctionalGroupDto;

	}

	@Override
	public void delete(Long id) throws ServiceException {

		String urlFrmt = null;

		try {

			urlFrmt = MessageFormat.format(this.wsDeleteFunctionalGroupUrl, id.toString());

			final RestServiceParameter<Object, FunctionalGroupDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, FunctionalGroupDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			this.restClientService.deleteRestService(restServiceParameter);

		} catch (Exception e) {
			LOG.error("Error al eliminar el grupo funcional: {}", id, e);
			throw new ServiceException("Error al eliminar el grupo funcional: " + id, e);
		}

	}

}