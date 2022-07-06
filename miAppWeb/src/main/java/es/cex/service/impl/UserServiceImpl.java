package es.cex.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.cex.arq.exceptions.ServiceException;
import es.cex.dto.PaginatedListDto;
import es.cex.dto.UserDto;
import es.cex.dto.UserPaginatedDto;
import es.cex.dto.UserPaginatedRequestDto;
import es.cex.service.IAuthenticationService;
import es.cex.service.IRestClientService;
import es.cex.service.IUserService;
import es.cex.service.RestServiceParameter;

/**
 * Implementation of {@link IUserService}
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Value("${ws.get.users.paginated.url:test}")
	private String wsGetUsersPaginatedUrl;

	@Value("${ws.get.users.list.url:test}")
	private String wsGetUsersListUrl;

	@Value("${ws.get.user.url:test}")
	private String wsGetUserUrl;

	@Value("${ws.post.user.url:test}")
	private String wsPostUserUrl;

	@Value("${ws.put.user.url:test}")
	private String wsPutUserUrl;

	@Value("${ws.delete.user.url:test}")
	private String wsDeleteUserUrl;

	@Value("${REST_AUTH:test}")
	private String restAuth;

	@Autowired
	private IRestClientService restClientService;

	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	public PaginatedListDto<UserPaginatedDto> getPaginatedUserList(UserPaginatedRequestDto userPaginatedRequestDto)
			throws ServiceException {

		PaginatedListDto<UserPaginatedDto> paginatedListDto = null;
		String urlFrmt = null;

		String functionalGroupId = null;
		String roleId = null;

		if(userPaginatedRequestDto.getFunctionalGroupId() != null) {
			functionalGroupId = userPaginatedRequestDto.getFunctionalGroupId().toString();
		}

		if(userPaginatedRequestDto.getRoleId() != null) {
			roleId = userPaginatedRequestDto.getRoleId().toString();
		}


		try {
			urlFrmt = MessageFormat.format(wsGetUsersPaginatedUrl, userPaginatedRequestDto.getData(),
					userPaginatedRequestDto.getActive(), functionalGroupId, roleId,
					userPaginatedRequestDto.getPage(), userPaginatedRequestDto.getSize()).replace("null", "");

			final RestServiceParameter<Object, PaginatedListDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, PaginatedListDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			paginatedListDto = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			throw new ServiceException("Error al recuperar la lista de usuarios", e);
		}

		return paginatedListDto;

	}

	@Override
	public UserDto getUserByLogin(String login) throws ServiceException {

		UserDto userDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsGetUserUrl, login);

			final RestServiceParameter<Object, UserDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, UserDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			userDto = this.restClientService.getRestService(restServiceParameter);

		} catch (Exception e) {
			throw new ServiceException("Error al recuperar el usuario por login: " + login, e);
		}

		return userDto;
	}

	@Override
	public UserDto save(UserDto userDto) throws ServiceException {

		UserDto newUserDto = null;

		try {

			final RestServiceParameter<UserDto, UserDto> restServiceParameter = RestServiceParameter.create(this.wsPostUserUrl, userDto, UserDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newUserDto = this.restClientService.postRestService(restServiceParameter);
		} catch (Exception e) {
			throw new ServiceException("Error al almacenar el usuario: " + userDto.getLogin(), e);
		}

		return newUserDto;
	}

	@Override
	public void delete(Long id) throws ServiceException {

		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsDeleteUserUrl, id.toString());

			final RestServiceParameter<Object, UserDto> restServiceParameter = RestServiceParameter
					.create(urlFrmt, UserDto.class).setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			this.restClientService.deleteRestService(restServiceParameter);

		} catch (Exception e) {
			throw new ServiceException("Error al eliminar el usuario: " + id, e);
		}

	}

	@Override
	public UserDto update(UserDto userDto) throws ServiceException {

		UserDto newUserDto = null;
		String urlFrmt = null;

		try {
			urlFrmt = MessageFormat.format(this.wsPutUserUrl, userDto.getId().toString());

			final RestServiceParameter<UserDto, UserDto> restServiceParameter = RestServiceParameter.create(urlFrmt, userDto, UserDto.class)
					.setAuth(this.restAuth);

			this.authenticationService.addTokenToHeader(restServiceParameter);

			newUserDto = this.restClientService.putRestService(restServiceParameter);
		} catch (Exception e) {
			throw new ServiceException("Error al actualizar el usuario: " + userDto.getLogin(), e);
		}

		return newUserDto;

	}

	@Override
	public List<UserDto> getUserList() throws ServiceException {

		List<UserDto> users = null;

		final RestServiceParameter<Object, List> restServiceParameter = RestServiceParameter
				.create(this.wsGetUsersListUrl, List.class).setAuth(this.restAuth);

		this.authenticationService.addTokenToHeader(restServiceParameter);

		try {
			users = this.restClientService.getRestService(restServiceParameter);
		} catch (Exception e) {
			throw new ServiceException("Error al recuperar la lista completa de usuarios", e);
		}

		return users;

	}

}