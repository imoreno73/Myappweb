package es.cex.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.RoleDto;
import es.cex.dto.RoleListRequestDto;
import es.cex.dto.RolePaginatedRequestDto;
import es.cex.service.IAuthenticationService;
import es.cex.service.IRestClientService;
import es.cex.service.IRoleService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IRoleService}
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Value("${ws.get.list.roles.url:test}")
	private String wsGetListRolesUrl;

	@Value("${ws.get.paginated.roles.url:test}")
	private String wsGetPaginatedRolesUrl;

	@Value("${ws.get.role.url:test}")
	private String wsGetRoleUrl;

	@Value("${ws.post.role.url:test}")
	private String wsPostRoleUrl;

	@Value("${ws.put.role.url:test}")
	private String wsPutRoleUrl;

	@Value("${ws.delete.role.url:test}")
	private String wsDeleteRoleUrl;

	@Value("${REST_AUTH:test}")
	private String restAuth;

	@Autowired
	private IRestClientService restClientService;

	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	public List<RoleDto> getRoleList() throws ServiceException {

		return this.getRoleList(null);
	}

	@Override
	public List<RoleDto> getRoleList(RoleListRequestDto roleRequestDto) throws ServiceException {

		List<RoleDto> userRoles = null;
		String userIds = null;
		String functionalGroupId = null;

		if (roleRequestDto != null) {
			userIds = roleRequestDto.getUserIds();

			if(roleRequestDto.getFunctionalGroupId() != null) {
				functionalGroupId = roleRequestDto.getFunctionalGroupId().toString();
			}

		}

		String urlFrmt = MessageFormat.format(this.wsGetListRolesUrl, userIds, functionalGroupId).replace("null", "");

		final RestServiceParameter<Object, List> restServiceParameter = RestServiceParameter
				.create(urlFrmt, List.class).setAuth(this.restAuth);

		this.authenticationService.addTokenToHeader(restServiceParameter);

		try {
			userRoles = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista completa de roles de usuario", e);
			throw new ServiceException("Error al recuperar la lista completa de roles de usuario", e);
		}

		return userRoles;
	}

	@Override
	public PaginatedListDto<RoleDto> getPaginatedRoleList(RolePaginatedRequestDto rolePaginatedRequestDto)
			throws ServiceException {

		PaginatedListDto<RoleDto> paginatedListDto = null;
		String urlFrmt = null;
		String functionalGroupId = null;

		if(rolePaginatedRequestDto.getFunctionalGroup() != null) {
			functionalGroupId = rolePaginatedRequestDto.getFunctionalGroup().toString();
		}

		try {
			urlFrmt = MessageFormat.format(wsGetPaginatedRolesUrl, rolePaginatedRequestDto.getName(),
					rolePaginatedRequestDto.getActive(), rolePaginatedRequestDto.getControlTotalMenu(),
					functionalGroupId, rolePaginatedRequestDto.getPage(),
					rolePaginatedRequestDto.getSize()).replace("null", "");

			final RestServiceParameter<Object, PaginatedListDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PaginatedListDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			paginatedListDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar la lista paginada de roles", e);
			throw new ServiceException("Error al recuperar la lista paginada de roles", e);
		}

		return paginatedListDto;

	}

	@Override
	public RoleDto getRoleBySlug(String slug) throws ServiceException {

		RoleDto roleDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsGetRoleUrl, slug);

			final RestServiceParameter<Object, RoleDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, RoleDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			roleDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al recuperar el rol por slug: {}", slug, e);
			throw new ServiceException("Error al recuperar el rol por slug: " + slug, e);
		}

		return roleDto;

	}

	@Override
	public RoleDto save(RoleDto roleDto) throws ServiceException {

		RoleDto newRoleDto = null;

		try {
			final RestServiceParameter<RoleDto, RoleDto> restServiceParameter = RestServiceParameter.create(this.wsPostRoleUrl, roleDto, RoleDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newRoleDto = this.restClientService.postRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al almacenar el rol: {}", roleDto.getId().toString(), e);
			throw new ServiceException("Error al almacenar el rol: " + roleDto.getId().toString(), e);
		}

		return newRoleDto;

	}

	@Override
	public RoleDto update(RoleDto roleDto) throws ServiceException {

		RoleDto newRoleDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsPutRoleUrl, roleDto.getId().toString());

			final RestServiceParameter<RoleDto, RoleDto> restServiceParameter = RestServiceParameter.create(urlFrmt, roleDto, RoleDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newRoleDto = this.restClientService.putRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al actualizar el rol: {}", roleDto.getId().toString(), e);
			throw new ServiceException("Error al actualizar el rol: " + roleDto.getId().toString(), e);
		}

		return newRoleDto;

	}

	@Override
	public void delete(Long id) throws ServiceException {

		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsDeleteRoleUrl, id.toString());

			final RestServiceParameter<Object, RoleDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, RoleDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			this.restClientService.deleteRestService(restServiceParameter);
		} catch (Exception e) {
			LOG.error("Error al eliminar el rol: {}", id.toString(), e);
			throw new ServiceException("Error al eliminar el rol: " + id.toString(), e);
		}

	}

}