package es.cex.controller;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.cex.arq.exceptions.ControllerException;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.constant.I18Constant;
import es.cex.common.constant.MappingConstant;
import es.cex.common.constant.ViewsConstant;
import es.cex.common.entity.DataTableResults;
import es.cex.common.types.NotificationTypes;
import es.cex.common.utils.CommonUtils;
import es.cex.controller.form.UserForm;
import es.cex.controller.form.UserSearchForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PaginationDto;
import es.cex.dto.RoleDto;
import es.cex.dto.UserDto;
import es.cex.dto.UserPaginatedDto;
import es.cex.dto.UserPaginatedRequestDto;
import es.cex.service.IFunctionalGroupService;
import es.cex.service.IRoleService;
import es.cex.service.IUserService;

/**
 * Controlador para usuarios
 */
@Controller
@RequestMapping(MappingConstant.USER_ROOT)
public class UserController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IFunctionalGroupService functionalGroupService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Carga la página principal de usuarios
	 *
	 * @param request
	 *            request
	 * @param formUserSearch
	 *            formUserSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@RequestMapping("")
	public String search(HttpServletRequest request,
			@ModelAttribute(Constant.USER_SEARCH_FORM_ATTRIBUTE_KEY) UserSearchForm formUserSearch)
			throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_USER_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		this.addFunctionalGroupsToRequest(request);
		this.addRolesToRequest(request);

		return ViewsConstant.VIEW_USER_SEARCH_PAGE;
	}

	/**
	 * Búsqueda de usuarios
	 *
	 * @param formUserSearch
	 *            Criterio de búsqueda
	 * @return JSON con los usuarios encontrados
	 *
	 *         Lista paginada de usuarios
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.USER_SEARCH)
	public ResponseEntity<String> searchUsers(UserSearchForm formUserSearch, HttpServletRequest request)
			throws ControllerException {

		DataTableResults<UserPaginatedDto> dataTableResult = null;

		// Preparamos el DTO de envío a partir del formulario recibido
		PaginationDto paginationDto = null;

		try {
			paginationDto = this.commonUtils.getPaginationData(request);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos de la paginación", e);
			throw new ControllerException("Error al recuperar los datos de la paginación", e);
		}

		UserPaginatedRequestDto userPaginatedRequestDto = new UserPaginatedRequestDto(formUserSearch.getData(),
				formUserSearch.getFunctionalGroupId(), formUserSearch.getRoleId(), formUserSearch.getActive(), paginationDto.getSize(),
				paginationDto.getPage());

		try {
			dataTableResult = this.commonUtils.prepareResponse(
					this.userService.getPaginatedUserList(userPaginatedRequestDto),
					request.getParameter(Constant.PAGINATION_DRAW_KEY));
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de usuarios", e);
			throw new ControllerException("Error al ercuperar la lista de usuarios", e);
		}

		return ResponseEntity.ok(dataTableResult.getJson());
	}

	/**
	 * Carga el detalle del usuario
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.USER_DETAIL)
	public String showUser(HttpServletRequest request,
			@PathVariable(Constant.USER_LOGIN_PATH_VARIABLE_KEY) String login) throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_USER_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los datos del usuario
		UserDto userDto = null;
		try {
			userDto = this.userService.getUserByLogin(login);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar el usuario por login: {}", login, e);
			throw new ControllerException("Error al recuperar el usuario por login: " + login, e);
		}

		// Enviamos los datos a la vista
		request.setAttribute(Constant.USER_DETAIL_ATTRIBUTE_KEY, userDto);

		return ViewsConstant.VIEW_USER_DETAIL_PAGE;
	}

	/**
	 * Devolver datos usuario para importar permisos
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.USER_IMPORT)
	public ResponseEntity<UserDto> importPermUser(HttpServletRequest request,
			@PathVariable(Constant.USER_LOGIN_PATH_VARIABLE_KEY) String login) throws ControllerException {
		// Recuperar los datos del usuario
		UserDto userDto = null;
		try {
			userDto = this.userService.getUserByLogin(login);
		} catch (ServiceException e) {
			throw new ControllerException("Error al recuperar el usuario por login: " + login, e);
		}


		return ResponseEntity.ok(userDto);
	}

	/**
	 * Mostrar la página de nuevo usuario
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.USER_NEW)
	public String newUser(HttpServletRequest request,
			@ModelAttribute(Constant.USER_FORM_ATTRIBUTE_KEY) UserForm formUser) throws ControllerException {

		// Añadimos el título de nuevo usuario
		this.setHeadTitle(request, I18Constant.I18_VIEW_USER_NEW_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.USER_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_USER_NEW_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.USER_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.USER_ROOT + MappingConstant.USER_CREATE);

		// Añadimos roles, usuarios y grupos funcionales a la request
		this.addRolesToRequest(request);
		this.addUsersToRequest(request);
		this.addFunctionalGroupsToRequest(request);

		return ViewsConstant.VIEW_USER_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear un usuario
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.USER_CREATE)
	public String create(HttpServletRequest request,
			@ModelAttribute(Constant.USER_FORM_ATTRIBUTE_KEY) UserForm formUser,
			@ModelAttribute(Constant.USER_SEARCH_FORM_ATTRIBUTE_KEY) UserSearchForm formUserSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formUser, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el usuario
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			UserDto userDto = this.userFormToDto(formUser);

			// Llamamos al servicio para guardar el usuario
			try {
				userDto = this.userService.save(userDto);
				if (userDto != null && userDto.getErrors() != null) {
					errors = userDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al almacenar el usuario", e);
				throw new ControllerException("Error al almacenar el usuario", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_USER_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.USER_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_USERS_TITLE,
					I18Constant.I18_NOTIFICATIONS_USERS_CREATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de usuarios
			this.setHeadTitle(request, I18Constant.I18_VIEW_USER_HEAD_TITLE);

		} else if (formUser != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formUser.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.USER_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_USER_NEW_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.USER_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.USER_ROOT + MappingConstant.USER_CREATE);

			// Añadimos usuarios a la request
			this.addUsersToRequest(request);

			// Añadimos el título de nuevo usuario
			this.setHeadTitle(request, I18Constant.I18_VIEW_USER_NEW_TITLE);

			view = ViewsConstant.VIEW_USER_NEW_OR_EDIT_PAGE;
		}

		// Añadimos roles y grupos funcionales a la request
		this.addRolesToRequest(request);
		this.addFunctionalGroupsToRequest(request);

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Mostrar la página de nuevo usuario
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.USER_EDIT)
	public String editUser(HttpServletRequest request,
			@PathVariable(Constant.USER_LOGIN_PATH_VARIABLE_KEY) String login,
			@ModelAttribute(Constant.USER_FORM_ATTRIBUTE_KEY) UserForm formUser) throws ControllerException {

		UserDto userDto = null;

		// Añadimos el título de nuevo usuario
		this.setHeadTitle(request, I18Constant.I18_VIEW_USER_EDIT_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.USER_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_USER_EDIT_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.USER_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.USER_ROOT + MappingConstant.USER_UPDATE.replace("{login}", login));

		// Recuperar los datos del usuario a editar
		try {
			// Si el usuario no existe, getUserByLogin lanzará una excepción
			userDto = this.userService.getUserByLogin(login);
		} catch (ServiceException e) {
			throw new ControllerException("Error al recuperar el usuario: " + login, e);
		}

		// Preparamos el userForm a partir del userDto
		this.userDtoToForm(userDto, formUser);

		// Añadimos roles y usuarios a la request
		this.addRolesToRequest(request);
		this.addUsersToRequest(request);
		this.addFunctionalGroupsToRequest(request);

		return ViewsConstant.VIEW_USER_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear un usuario
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.USER_UPDATE)
	public String update(HttpServletRequest request, @PathVariable(Constant.USER_LOGIN_PATH_VARIABLE_KEY) String login,
			@ModelAttribute(Constant.USER_FORM_ATTRIBUTE_KEY) UserForm formUser,
			@ModelAttribute(Constant.USER_SEARCH_FORM_ATTRIBUTE_KEY) UserSearchForm formUserSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formUser, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el usuario
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			UserDto userDto = this.userFormToDto(formUser);

			// Llamamos al servicio para editar el usuario
			try {
				userDto = this.userService.update(userDto);
				if (userDto != null && userDto.getErrors() != null) {
					errors = userDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				throw new ControllerException("Error al guardar el usuario", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_USER_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.USER_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_USERS_TITLE,
					I18Constant.I18_NOTIFICATIONS_USERS_UPDATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de usuarios
			this.setHeadTitle(request, I18Constant.I18_VIEW_USER_HEAD_TITLE);
		} else if (formUser != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formUser.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.USER_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_USER_EDIT_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.USER_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.USER_ROOT + MappingConstant.USER_UPDATE.replace("{login}", login));

			// Añadimos usuarios a la request
			this.addUsersToRequest(request);

			// Añadimos el título de modificar usuario
			this.setHeadTitle(request, I18Constant.I18_VIEW_USER_EDIT_TITLE);

			view = ViewsConstant.VIEW_USER_NEW_OR_EDIT_PAGE;
		}

		// Añadimos roles y grupos funcionales a la request
		this.addRolesToRequest(request);
		this.addFunctionalGroupsToRequest(request);

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Elimina un usuario
	 *
	 * @param id
	 *            Usuario a eliminar
	 * @return Código HTTP 200 si se eliminó correctamente. Código HTTP 404 si no
	 *         existe el usuario a eliminar
	 *
	 * @throws ControllerException
	 */
	@DeleteMapping(MappingConstant.USER_DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable(Constant.USER_ID_PATH_VARIABLE_KEY) Long id) {

		boolean deleted = true;
		HttpStatus httpStatus = null;

		try {
			this.userService.delete(id);
		} catch (ServiceException e) {
			LOG.error("Error al eliminar el usuario con id: {}", id, e);
			deleted = false;
		}

		if (deleted) {
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}

		return ResponseEntity.status(httpStatus).build();
	}

	/**
	 * Generamos un {@link UserDto} a partir de un {@link UserForm}
	 *
	 * @param formUser
	 *            formUser
	 * @return Usuario
	 */
	private UserDto userFormToDto(UserForm formUser) {

		UserDto userDto = null;

		if (formUser != null) {

			// Generamos el DTO a partir del formulario
			userDto = new UserDto();
			userDto.setId(formUser.getId());
			userDto.setLogin(formUser.getLogin());
			userDto.setName(formUser.getName());
			userDto.setSurname(formUser.getSurname());

			if (formUser.getActive() == null) {
				formUser.setActive(true);
			}

			userDto.setActive(formUser.getActive());
			userDto.setEmail(formUser.getEmail());

			if (commonUtils.isAvailable(formUser.getRoles())) {
				// mapear roles
				userDto.setRoles(formUser.getRoles().stream().map(RoleDto::new).collect(Collectors.toList()));
			}

			if (commonUtils.isAvailable(formUser.getFunctionalGroups())) {
				// mapear roles
				userDto.setFunctionalGroups(formUser.getFunctionalGroups().stream().map(FunctionalGroupDto::new).collect(Collectors.toList()));
			}

		}

		return userDto;

	}

	/**
	 * Cargamos en el formulario {@link UserForm} los datos del usuario
	 * {@link UserDto}
	 *
	 * @param userDto
	 *            Usuario
	 * @param userForm
	 *            Formulario
	 *
	 */
	private void userDtoToForm(UserDto userDto, UserForm userForm) {

		if (userDto != null && userForm != null) {

			userForm.setId(userDto.getId());
			userForm.setLogin(userDto.getLogin());
			userForm.setName(userDto.getName());
			userForm.setSurname(userDto.getSurname());
			userForm.setActive(userDto.getActive());
			userForm.setEmail(userDto.getEmail());

			if (commonUtils.isAvailable(userDto.getRoles())) {

				List<Long> roles = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());

				userForm.setRoles(roles);

			}

			if (commonUtils.isAvailable(userDto.getFunctionalGroups())) {
				List<Long> functionalGroups = userDto.getFunctionalGroups().stream().map(FunctionalGroupDto::getId).collect(Collectors.toList());
				userForm.setFunctionalGroups(functionalGroups);
			}

		}

	}

	private void addUsersToRequest(HttpServletRequest request) throws ControllerException {

		// Enviamos a la vista la lista de usuarios
		try {
			request.setAttribute(Constant.ROLE_USERS_LIST_ATTRIBUTE_KEY, this.userService.getUserList());
		} catch (ServiceException e) {
			throw new ControllerException(
					"Error al recuperar la lista completa de usuarios para añadirlos a la request", e);
		}
	}

	private void addFunctionalGroupsToRequest(HttpServletRequest request) throws ControllerException {

		// Enviamos a la vista la lista de usuarios
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY, this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			throw new ControllerException(
					"Error al recuperar la lista completa de grupos funcionales para añadirlos a la request", e);
		}
	}

	private void addRolesToRequest(HttpServletRequest request) throws ControllerException {

		// Recuperar los roles de usuario para enviarlos a la vista
		try {
			request.setAttribute(Constant.USER_ROLES_LIST_ATTRIBUTE_KEY, this.roleService.getRoleList());
		} catch (ServiceException e) {
			throw new ControllerException("Error al recuperar la lista completa de roles para añadirlos a la request",
					e);
		}

	}

}
