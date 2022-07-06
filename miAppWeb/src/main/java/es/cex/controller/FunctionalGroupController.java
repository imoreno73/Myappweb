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
import es.cex.controller.form.FunctionalGroupForm;
import es.cex.controller.form.FunctionalGroupSearchForm;
import es.cex.dto.FunctionalGroupDto;
import es.cex.dto.FunctionalGroupPaginatedRequestDto;
import es.cex.dto.PaginationDto;
import es.cex.service.IFunctionalGroupService;

/**
 * Controlador para usuarios
 */
@Controller
@RequestMapping(MappingConstant.FUNCTIONAL_GROUP_ROOT)
public class FunctionalGroupController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(FunctionalGroupController.class);

	@Autowired
	private IFunctionalGroupService functionalGroupService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Carga la página principal de grupos funcionales
	 *
	 * @param request
	 *            request
	 * @param formFunctionalGroupSearch
	 *            formFunctionalGroupSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@RequestMapping("")
	public String search(HttpServletRequest request,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_SEARCH_FORM_ATTRIBUTE_KEY) FunctionalGroupSearchForm formFunctionalGroupSearch)
			throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return ViewsConstant.VIEW_FUNCTIONAL_GROUP_SEARCH_PAGE;
	}

	/**
	 * Búsqueda de grupos funcionales
	 *
	 * @param formFunctionalGroupSearch
	 *            Criterio de búsqueda
	 * @return JSON con los grupos funcionales encontrados
	 *
	 *         Lista paginada de grupos funcionales
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.FUNCTIONAL_GROUP_SEARCH)
	public ResponseEntity<String> searchFunctionalGroups(FunctionalGroupSearchForm formFunctionalGroupSearch, HttpServletRequest request)
			throws ControllerException {

		DataTableResults<FunctionalGroupDto> dataTableResult = null;

		// Preparamos el DTO de envío a partir del formulario recibido
		PaginationDto paginationDto = null;

		try {
			paginationDto = this.commonUtils.getPaginationData(request);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos de la paginación", e);
			throw new ControllerException("Error al recuperar los datos de la paginación", e);
		}

		FunctionalGroupPaginatedRequestDto functionalGropPaginatedRequestDto = new FunctionalGroupPaginatedRequestDto(formFunctionalGroupSearch.getName(),
				paginationDto.getSize(), paginationDto.getPage());

		try {
			dataTableResult = this.commonUtils.prepareResponse(
					this.functionalGroupService.getPaginatedList(functionalGropPaginatedRequestDto),
					request.getParameter(Constant.PAGINATION_DRAW_KEY));
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista paginada de grupos funcionales", e);
			throw new ControllerException("Error al recuperar la lista paginada de grupos funcionales", e);
		}

		return ResponseEntity.ok(dataTableResult.getJson());
	}

	/**
	 * Carga el detalle de grupo funcional
	 *
	 * @param request
	 * @param slug
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.FUNCTIONAL_GROUP_DETAIL)
	public String showFunctionalGroup(HttpServletRequest request,
			@PathVariable(Constant.FUNCTIONAL_GROUP_SLUG_PATH_VARIABLE_KEY) String slug) throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los datos del grupo funcional
		FunctionalGroupDto functionalGroupDto = null;
		try {
			functionalGroupDto = this.functionalGroupService.getFunctionalGroupBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar el gruop funcional por slug: {}", slug, e);
			throw new ControllerException("Error al recuperar el grupo funcional por slug: " + slug, e);
		}

		// Enviamos los datos a la vista
		request.setAttribute(Constant.FUNCTIONAL_GROUP_DETAIL_ATTRIBUTE_KEY, functionalGroupDto);

		return ViewsConstant.VIEW_FUNCTIONAL_GROUP_DETAIL_PAGE;
	}

	/**
	 * Recupera la lista completa de grupos funcionales
	 * @param request request
	 * @return JSON con los grupos funcionales encontrados
	 *
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.FUNCTIONAL_GROUP_LIST)
	public ResponseEntity<List<FunctionalGroupDto>> list(HttpServletRequest request) throws ControllerException {

		List<FunctionalGroupDto> gruposFuncionales = null;

		try {
			gruposFuncionales = this.functionalGroupService.getFunctionalGroupList();
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de grupos funcionales", e);
			throw new ControllerException("Error al recuperar la lista de grupos funcionales", e);
		}

		return ResponseEntity.ok(gruposFuncionales);
	}

	/**
	 * Mostrar la página de grupos funcionales
	 *
	 * @param request
	 * @param formFunctionalGroup
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.FUNCTIONAL_GROUP_NEW)
	public String newFunctionalGroup(HttpServletRequest request,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY) FunctionalGroupForm formFunctionalGroup) throws ControllerException {

		// Añadimos el título de nuevo usuario
		this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_NEW_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_FUNCTIONAL_GROUP_NEW_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.FUNCTIONAL_GROUP_ROOT + MappingConstant.FUNCTIONAL_GROUP_CREATE);

		return ViewsConstant.VIEW_FUNCTIONAL_GROUP_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear un grupo funcional
	 *
	 * @param request
	 * @param formFunctionalGroup,
	 * formFunctionalGroupSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.FUNCTIONAL_GROUP_CREATE)
	public String create(HttpServletRequest request,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY) FunctionalGroupForm formFunctionalGroup,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_SEARCH_FORM_ATTRIBUTE_KEY) FunctionalGroupSearchForm formFunctionalGroupSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formFunctionalGroup, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el grupo funcional
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			FunctionalGroupDto functionalGroupDto = this.functionalGroupFormToDto(formFunctionalGroup);

			// Llamamos al servicio para guardar el grupo funcional
			try {
				functionalGroupDto = this.functionalGroupService.save(functionalGroupDto);
				if (functionalGroupDto.getErrors() != null) {
					errors = functionalGroupDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al almacenar el grupo funcional", e);
				throw new ControllerException("Error al almacenar el grupo funcional", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_FUNCTIONAL_GROUP_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.FUNCTIONAL_GROUP_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_FUNCTIONAL_GROUPS_TITLE,
					I18Constant.I18_NOTIFICATIONS_FUNCTIONAL_GROUPS_CREATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de grupos funcionales
			this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_HEAD_TITLE);
			formFunctionalGroupSearch.reset();

		} else if (formFunctionalGroup != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formFunctionalGroup.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_FUNCTIONAL_GROUP_NEW_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.FUNCTIONAL_GROUP_ROOT + MappingConstant.FUNCTIONAL_GROUP_CREATE);

			// Añadimos el título de nuevo grupo funcional
			this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_NEW_TITLE);

			view = ViewsConstant.VIEW_FUNCTIONAL_GROUP_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Mostrar la página de nuevo grupo funcional
	 *
	 * @param request
	 * @param slug
	 * @param formFunctionalGroup
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.FUNCTIONAL_GROUP_EDIT)
	public String editFunctionalGroup(HttpServletRequest request,
			@PathVariable(Constant.FUNCTIONAL_GROUP_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY) FunctionalGroupForm formFunctionalGroup) throws ControllerException {

		FunctionalGroupDto functionalGroupDto = null;

		// Añadimos el título de nuevo grupo funcional
		this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_EDIT_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_FUNCTIONAL_GROUP_EDIT_TITLE));

		// Recuperar los datos del grupo funcional a editar
		try {
			// Si el grupo funcional no existe, getFunctionalGroupBySlug lanzará una excepción
			functionalGroupDto = this.functionalGroupService.getFunctionalGroupBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos del grupo funcional: {}", slug, e);
			throw new ControllerException("Error al recuperar el grupo funcional: " + slug, e);
		}

		// Añadimos el destino del formulario
				request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_URL_ATTRIBUTE_KEY,
						MappingConstant.FUNCTIONAL_GROUP_ROOT + MappingConstant.FUNCTIONAL_GROUP_UPDATE.replace("{slug}", functionalGroupDto.getSlug()));

		// Preparamos el functionalGroupForm a partir del functionalGroupDto
		this.functionalGroupDtoToForm(functionalGroupDto, formFunctionalGroup);

		return ViewsConstant.VIEW_FUNCTIONAL_GROUP_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear un grupo funcional
	 *
	 * @param request
	 * @param slug
	 * @param formFunctionalGroup
	 * @param formFunctionalGroupSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.FUNCTIONAL_GROUP_UPDATE)
	public String update(HttpServletRequest request, @PathVariable(Constant.FUNCTIONAL_GROUP_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_FORM_ATTRIBUTE_KEY) FunctionalGroupForm formFunctionalGroup,
			@ModelAttribute(Constant.FUNCTIONAL_GROUP_SEARCH_FORM_ATTRIBUTE_KEY) FunctionalGroupSearchForm formFunctionalGroupSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formFunctionalGroup, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el usuario
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			FunctionalGroupDto functionalGroupDto = this.functionalGroupFormToDto(formFunctionalGroup);

			// Llamamos al servicio para editar el grupo funcional
			try {
				functionalGroupDto = this.functionalGroupService.update(functionalGroupDto);
				if (functionalGroupDto != null && functionalGroupDto.getErrors() != null) {
					errors = functionalGroupDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al guardar el grupo funcional", e);
				throw new ControllerException("Error al guardar el grupo funcional", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_FUNCTIONAL_GROUP_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.FUNCTIONAL_GROUP_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_FUNCTIONAL_GROUPS_TITLE,
					I18Constant.I18_NOTIFICATIONS_FUNCTIONAL_GROUPS_UPDATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de grupos funcionales
			this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_HEAD_TITLE);
			formFunctionalGroupSearch.reset();

		} else if (formFunctionalGroup != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formFunctionalGroup.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_FUNCTIONAL_GROUP_EDIT_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.FUNCTIONAL_GROUP_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.FUNCTIONAL_GROUP_ROOT + MappingConstant.FUNCTIONAL_GROUP_UPDATE.replace("{slug}", slug));

			// Añadimos el título de modificar grupo funcional
			this.setHeadTitle(request, I18Constant.I18_VIEW_FUNCTIONAL_GROUP_EDIT_TITLE);

			view = ViewsConstant.VIEW_FUNCTIONAL_GROUP_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Elimina un grupo funcional
	 *
	 * @param slug grupo funcional a eliminar
	 * @return Código HTTP 200 si se eliminó correctamente. Código HTTP 404 si no
	 *         existe el usuario a eliminar
	 *
	 * @throws ControllerException
	 */
	@DeleteMapping(MappingConstant.FUNCTIONAL_GROUP_DELETE)
	public ResponseEntity<Void> deleteFunctionalGroup(@PathVariable(Constant.FUNCTIONAL_GROUP_ID_PATH_VARIABLE_KEY) Long id) {

		boolean deleted = true;
		HttpStatus httpStatus = null;

		try {

			this.functionalGroupService.delete(id);
		} catch (ServiceException e) {
			LOG.error("Error al eliminar el grupo funcional con id: {}", id, e);
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
	 * Generamos un {@link FunctionalGroupDto} a partir de un {@link FunctionalGroupForm}
	 *
	 * @param formFunctionalGroup grupo funcional
	 * @return grupo funcional
	 */
	private FunctionalGroupDto functionalGroupFormToDto(FunctionalGroupForm formFunctionalGroup) {

		FunctionalGroupDto functionalGroupDto = null;

		if (formFunctionalGroup != null) {

			// Generamos el DTO a partir del formulario
			functionalGroupDto = new FunctionalGroupDto();
			functionalGroupDto.setName(formFunctionalGroup.getName());
			functionalGroupDto.setId(formFunctionalGroup.getId());

		}

		return functionalGroupDto;

	}

	/**
	 * Cargamos en el formulario {@link FunctionalGroupForm} los datos del grupo funcional {@link FunctionalGroupDto}
	 *
	 * @param functionalGroupDto grupo funcional
	 * @param functionalGroupForm Formulario
	 *
	 */
	private void functionalGroupDtoToForm(FunctionalGroupDto functionalGroupDto, FunctionalGroupForm functionalGroupForm) {

		if (functionalGroupDto != null && functionalGroupForm != null) {

			functionalGroupForm.setId(functionalGroupDto.getId());
			functionalGroupForm.setName(functionalGroupDto.getName());


		}

	}

}
