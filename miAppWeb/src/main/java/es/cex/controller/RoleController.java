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
import org.springframework.web.bind.annotation.RequestParam;

import es.cex.arq.exceptions.ControllerException;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.constant.I18Constant;
import es.cex.common.constant.MappingConstant;
import es.cex.common.constant.ViewsConstant;
import es.cex.common.entity.DataTableResults;
import es.cex.common.types.NotificationTypes;
import es.cex.common.utils.CommonUtils;
import es.cex.controller.form.RoleForm;
import es.cex.controller.form.RoleSearchForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.PaginationDto;
import es.cex.dto.RoleDto;
import es.cex.dto.RoleListRequestDto;
import es.cex.dto.RolePaginatedRequestDto;
import es.cex.service.IFunctionalGroupService;
import es.cex.service.IRoleService;

/**
 * Controlador para roles
 */
@Controller
@RequestMapping(MappingConstant.ROLE_ROOT)
public class RoleController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IFunctionalGroupService functionalGroupService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Carga la página principal de roles
	 *
	 * @param request
	 *            request
	 * @param formRoleSearch
	 *            formRoleSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@RequestMapping("")
	public String search(HttpServletRequest request,
			@ModelAttribute(Constant.ROLE_SEARCH_FORM_ATTRIBUTE_KEY) RoleSearchForm formRoleSearch)
			throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles antes de mostrar el formulario de búsqueda", e);
			throw new ControllerException("Error al recuperar los roles antes de mostrar el formulario de búsqueda", e);
		}

		return ViewsConstant.VIEW_ROLE_SEARCH_PAGE;
	}

	/**
	 * Búsqueda de roles
	 *
	 * @param formRoleSearch
	 *            Criterio de búsqueda
	 * @return JSON con los roles encontrados
	 *
	 *         Lista paginada de roles
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.ROLE_SEARCH)
	public ResponseEntity<String> searchRoles(RoleSearchForm formRoleSearch, HttpServletRequest request)
			throws ControllerException {

		DataTableResults<RoleDto> dataTableResult = null;

		// Preparamos el DTO de envío a partir del formulario recibido
		PaginationDto paginationDto = null;

		try {
			paginationDto = this.commonUtils.getPaginationData(request);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos de la paginación", e);
			throw new ControllerException("Error al recuperar los datos de la paginación", e);
		}

		RolePaginatedRequestDto rolePaginatedRequestDto = new RolePaginatedRequestDto(formRoleSearch.getName(),
				formRoleSearch.getActive(), formRoleSearch.getControlTotalMenu(), formRoleSearch.getFunctionalGroup(),
				paginationDto.getSize(), paginationDto.getPage());

		try {
			dataTableResult = this.commonUtils.prepareResponse(
					this.roleService.getPaginatedRoleList(rolePaginatedRequestDto),
					request.getParameter(Constant.PAGINATION_DRAW_KEY));
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de roles", e);
			throw new ControllerException("Error al ercuperar la lista de roles", e);
		}

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles después de mostrar el formulario de busqueda", e);
			throw new ControllerException("Error al recuperar los roles después de mostrar el formulario de búsqueda",
					e);
		}

		return ResponseEntity.ok(dataTableResult.getJson());
	}

	/**
	 * Búsqueda de roles
	 *
	 * @param formRoleSearch
	 *            Criterio de búsqueda
	 * @return JSON con los roles encontrados
	 *
	 *         Lista paginada de roles
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.ROLE_SEARCH_BY_FUNCTIONAL_GROUP)
	public ResponseEntity<List<RoleDto>> searchRolesByFunctionalGroup(
			@RequestParam(Constant.ROLE_FUNCTIONAL_GROUP_ID_VARIABLE_KEY) Long functionalGroupId, HttpServletRequest request)
			throws ControllerException {

		// Preparamos el DTO de envío a partir de los iedntificadores de usuario
		List<RoleDto> roles = null;
		RoleListRequestDto roleListRequestDto = new RoleListRequestDto(functionalGroupId);

		try {
			roles = this.roleService.getRoleList(roleListRequestDto);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de roles por grupo funcional", e);
			throw new ControllerException("Error al recuperar la lista de roles por grupo funcional", e);
		}

		return ResponseEntity.ok(roles);
	}

	/**
	 * Búsqueda de roles
	 *
	 * @param usersId
	 *            Identificdores de usuarios
	 * @return JSON con los roles encontrados
	 *
	 *         Lista paginada de roles
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.ROLE_SEARCH_BY_USERS_ID)
	public ResponseEntity<List<RoleDto>> searchRolesByUsersId(
			@RequestParam(Constant.ROLE_USERS_ID_VARIABLE_KEY) String usersId, HttpServletRequest request)
			throws ControllerException {

		// Preparamos el DTO de envío a partir de los iedntificadores de usuario
		List<RoleDto> roles = null;
		RoleListRequestDto roleListRequestDto = new RoleListRequestDto(usersId);

		try {
			roles = this.roleService.getRoleList(roleListRequestDto);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de roles por usuarios", e);
			throw new ControllerException("Error al recuperar la lista de roles por usuarios", e);
		}

		return ResponseEntity.ok(roles);
	}

	/**
	 * Carga el detalle del rol
	 *
	 * @param request
	 *            request
	 * @param slug
	 *            slug
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.ROLE_DETAIL)
	public String showRole(HttpServletRequest request, @PathVariable(Constant.ROLE_SLUG_PATH_VARIABLE_KEY) String slug)
			throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los datos del rol
		RoleDto roleDto = null;
		try {
			roleDto = this.roleService.getRoleBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar el role por slug: {}", slug, e);
			throw new ControllerException("Error al recuperar el rol por login: " + slug, e);
		}

		// Enviamos los datos a la vista
		request.setAttribute(Constant.ROLE_DETAIL_ATTRIBUTE_KEY, roleDto);

		return ViewsConstant.VIEW_ROLE_DETAIL_PAGE;
	}

	/**
	 * Mostrar la página de nuevo rol
	 *
	 * @param request
	 *            request
	 * @param formRole
	 *            formRole
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.ROLE_NEW)
	public String newRole(HttpServletRequest request,
			@ModelAttribute(Constant.ROLE_FORM_ATTRIBUTE_KEY) RoleForm formRole) throws ControllerException {

		// Añadimos el título de nuevo rol
		this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_NEW_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.ROLE_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_ROLE_NEW_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.ROLE_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.ROLE_ROOT + MappingConstant.ROLE_CREATE);

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles antes de mostrar el formulario de creación", e);
			throw new ControllerException("Error al recuperar los roles antes de mostrar el formulario de creación", e);
		}

		return ViewsConstant.VIEW_ROLE_NEW_OR_EDIT_PAGE;
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
	@PostMapping(MappingConstant.ROLE_CREATE)
	public String create(HttpServletRequest request,
			@ModelAttribute(Constant.ROLE_FORM_ATTRIBUTE_KEY) RoleForm formRole,
			@ModelAttribute(Constant.ROLE_SEARCH_FORM_ATTRIBUTE_KEY) RoleSearchForm formRoleSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formRole, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el rol
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			RoleDto roleDto = this.roleFormToDto(formRole);

			// Llamamos al servicio para guardar el usuario
			try {

				roleDto = this.roleService.save(roleDto);
				if (roleDto != null && roleDto.getErrors() != null) {
					errors = roleDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al almacenar el rol", e);
				throw new ControllerException("Error al almacenar el rol", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_ROLE_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.ROLE_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_ROLES_TITLE,
					I18Constant.I18_NOTIFICATIONS_ROLES_CREATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de roles
			this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_HEAD_TITLE);
			formRoleSearch.reset();

		} else if (formRole != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formRole.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.ROLE_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_ROLE_NEW_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.ROLE_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.ROLE_ROOT + MappingConstant.ROLE_CREATE);

			// Añadimos el título de nuevo rol
			this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_NEW_TITLE);

			view = ViewsConstant.VIEW_ROLE_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles después de mostrar el formulario de creación", e);
			throw new ControllerException("Error al recuperar los roles después de mostrar el formulario de creación",
					e);
		}

		return view;

	}

	/**
	 * Mostrar la página de nuevo rol
	 *
	 * @param slug
	 *            slug
	 * @param formRole
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.ROLE_EDIT)
	public String editRole(HttpServletRequest request, @PathVariable(Constant.ROLE_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.ROLE_FORM_ATTRIBUTE_KEY) RoleForm formRole) throws ControllerException {

		RoleDto roleDto = null;

		// Añadimos el título de nuevo rol
		this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_EDIT_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.ROLE_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_ROLE_EDIT_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.ROLE_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.ROLE_ROOT + MappingConstant.ROLE_UPDATE.replace("{slug}", slug));

		// Recuperar los datos del rol a editar
		try {
			// Si el rol no existe, getRolBySlug lanzará una excepción
			roleDto = this.roleService.getRoleBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos del rol: {}", slug, e);
			throw new ControllerException("Error al recuperar el rol: " + slug, e);
		}

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles antes de mostrar el formulario de edición", e);
			throw new ControllerException("Error al recuperar los roles antes de mostrar el formulario de edición", e);
		}

		// Preparamos el roleForm a partir del roleDto
		this.roleDtoToForm(roleDto, formRole);

		return ViewsConstant.VIEW_ROLE_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear un rol
	 *
	 * @param request
	 * @param formEmailSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.ROLE_UPDATE)
	public String update(HttpServletRequest request, @PathVariable(Constant.ROLE_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.ROLE_FORM_ATTRIBUTE_KEY) RoleForm formRole,
			@ModelAttribute(Constant.ROLE_SEARCH_FORM_ATTRIBUTE_KEY) RoleSearchForm formRoleSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formRole, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el rol
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			RoleDto roleDto = this.roleFormToDto(formRole);

			// Llamamos al servicio para editar el rol
			try {
				roleDto = this.roleService.update(roleDto);
				if (roleDto != null && roleDto.getErrors() != null) {
					errors = roleDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al guardar el rol", e);
				throw new ControllerException("Error al guardar el rol", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_ROLE_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.ROLE_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_ROLES_TITLE,
					I18Constant.I18_NOTIFICATIONS_ROLES_UPDATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de roles
			this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_HEAD_TITLE);
			formRoleSearch.reset();

		} else if (formRole != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formRole.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.ROLE_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_ROLE_EDIT_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.ROLE_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.ROLE_ROOT + MappingConstant.ROLE_UPDATE.replace("{slug}", slug));

			// Añadimos el título de modificar usuario
			this.setHeadTitle(request, I18Constant.I18_VIEW_ROLE_EDIT_TITLE);

			view = ViewsConstant.VIEW_ROLE_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los grupos funcionales para enviarlos a la vista
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los roles después de mostrar el formulario de edición", e);
			throw new ControllerException("Error al recuperar los roles después de mostrar el formulario de edición",
					e);
		}

		return view;

	}

	/**
	 * Elimina un rol
	 *
	 * @param slug
	 *            Rol a eliminar
	 * @return Código HTTP 200 si se eliminó correctamente. Código HTTP 404 si no
	 *         existe el usuario a eliminar
	 *
	 * @throws ControllerException
	 */
	@DeleteMapping(MappingConstant.ROLE_DELETE)
	public ResponseEntity<Void> deleteRole(@PathVariable(Constant.ROLE_ID_PATH_VARIABLE_KEY) Long id) {

		boolean deleted = true;
		HttpStatus httpStatus = null;

		try {
			this.roleService.delete(id);
		} catch (ServiceException e) {
			LOG.error("Error al eliminar el rol con id: {}", id, e);
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
	 * Generamos un {@link RoleDto} a partir de un {@link RoleForm}
	 *
	 * @param formRole
	 *            formRole
	 * @return Rol
	 */
	private RoleDto roleFormToDto(RoleForm formRole) {

		RoleDto roleDto = null;

		if (formRole != null) {

			// Generamos el DTO a partir del formulario
			roleDto = new RoleDto();
			roleDto.setId(formRole.getId());
			roleDto.setName(formRole.getName());

			if (formRole.getActive() == null) {
				formRole.setActive(true);
			}

			roleDto.setActive(formRole.getActive());

			if (formRole.getControlTotalMenu() == null) {
				formRole.setControlTotalMenu(false);
			}

			roleDto.setControlTotalMenu(formRole.getControlTotalMenu());

			if (formRole.getFunctionalGroup() != null) {
				FunctionalGroupDto functionalGroupDto = new FunctionalGroupDto();
				functionalGroupDto.setId(formRole.getFunctionalGroup());
				roleDto.setFunctionalGroup(functionalGroupDto);
			}

		}

		return roleDto;

	}

	/**
	 * Cargamos en el formulario {@link RoleForm} los datos del usuario
	 * {@link RoleDto}
	 *
	 * @param roleDto
	 *            Rol
	 * @param roleForm
	 *            Formulario
	 *
	 */
	private void roleDtoToForm(RoleDto roleDto, RoleForm roleForm) {

		if (roleDto != null && roleForm != null) {

			roleForm.setId(roleDto.getId());
			roleForm.setName(roleDto.getName());
			roleForm.setActive(roleDto.getActive());
			roleForm.setControlTotalMenu(roleDto.getControlTotalMenu());

			if (roleDto.getFunctionalGroup() != null) {
				roleForm.setFunctionalGroup(roleDto.getFunctionalGroup().getId());
			}

		}

	}

}
