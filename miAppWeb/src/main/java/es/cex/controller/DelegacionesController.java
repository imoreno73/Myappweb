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
import es.cex.controller.form.DelegacionesForm;
import es.cex.controller.form.DelegacionesSearchForm;
import es.cex.dto.DelegacionesDto;
import es.cex.dto.DelegacionesPaginatedRequestDto;
import es.cex.dto.PaginationDto;
import es.cex.service.IDelegacionesService;

/**
 * Controlador para usuarios
 */
@Controller
@RequestMapping(MappingConstant.DELEGACIONES.ROOT)
public class DelegacionesController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(DelegacionesController.class);

	@Autowired
	private IDelegacionesService delegacionesService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Carga la página principal de delegaciones
	 *
	 * @param request
	 *            request
	 * @param formDelegacionesSearch
	 *            formDelegacionesSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@RequestMapping("")
	public String search(HttpServletRequest request,
			@ModelAttribute(Constant.DELEGACIONES_SEARCH_FORM_ATTRIBUTE_KEY) DelegacionesSearchForm formDelegacionesSearch)
			throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return ViewsConstant.VIEW_DELEGACIONES_SEARCH_PAGE;
	}

	/**
	 * Búsqueda de delegaciones
	 *
	 * @param formDelegacionesSearch
	 *            Criterio de búsqueda
	 * @return JSON con los delegaciones encontrados
	 *
	 *         Lista paginada de delegaciones
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.DELEGACIONES_SEARCH)
	public ResponseEntity<String> searchDelegacioness(DelegacionesSearchForm formDelegacionesSearch, HttpServletRequest request)
			throws ControllerException {

		DataTableResults<DelegacionesDto> dataTableResult = null;

		// Preparamos el DTO de envío a partir del formulario recibido
		PaginationDto paginationDto = null;

		try {
			paginationDto = this.commonUtils.getPaginationData(request);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos de la paginación", e);
			throw new ControllerException("Error al recuperar los datos de la paginación", e);
		}

		DelegacionesPaginatedRequestDto delegacionesPaginatedRequestDto = new DelegacionesPaginatedRequestDto(formDelegacionesSearch.getName(),
				paginationDto.getSize(), paginationDto.getPage());

		try {
			dataTableResult = this.commonUtils.prepareResponse(
					this.delegacionesService.getPaginatedList(delegacionesPaginatedRequestDto),
					request.getParameter(Constant.PAGINATION_DRAW_KEY));
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista paginada de delegaciones", e);
			throw new ControllerException("Error al recuperar la lista paginada de delegaciones", e);
		}

		return ResponseEntity.ok(dataTableResult.getJson());
	}

	/**
	 * Carga el detalle de delegacion
	 *
	 * @param request
	 * @param slug
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.DELEGACIONES_DETAIL)
	public String showDelegaciones(HttpServletRequest request,
			@PathVariable(Constant.DELEGACIONES_SLUG_PATH_VARIABLE_KEY) String slug) throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Recuperar los datos del delegacion
		DelegacionesDto delegacionesDto = null;
		try {
			delegacionesDto = this.delegacionesService.getDelegacionesBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar el gruop funcional por slug: {}", slug, e);
			throw new ControllerException("Error al recuperar el delegacion por slug: " + slug, e);
		}

		// Enviamos los datos a la vista
		request.setAttribute(Constant.DELEGACIONES_DETAIL_ATTRIBUTE_KEY, delegacionesDto);

		return ViewsConstant.VIEW_DELEGACIONES_DETAIL_PAGE;
	}

	/**
	 * Recupera la lista completa de delegaciones
	 * @param request request
	 * @return JSON con los delegaciones encontrados
	 *
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.DELEGACIONES_LIST)
	public ResponseEntity<List<DelegacionesDto>> list(HttpServletRequest request) throws ControllerException {

		List<DelegacionesDto> delegaciones = null;

		try {
			delegaciones = this.delegacionesService.getDelegacionesList();
		} catch (ServiceException e) {
			LOG.error("Error al recuperar la lista de delegaciones", e);
			throw new ControllerException("Error al recuperar la lista de delegaciones", e);
		}

		return ResponseEntity.ok(delegaciones);
	}

	/**
	 * Mostrar la página de delegaciones
	 *
	 * @param request
	 * @param formDelegaciones
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.DELEGACIONES_NEW)
	public String newDelegaciones(HttpServletRequest request,
			@ModelAttribute(Constant.DELEGACIONES_FORM_ATTRIBUTE_KEY) DelegacionesForm formDelegaciones) throws ControllerException {

		// Añadimos el título de nuevo usuario
		this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_NEW_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.DELEGACIONES_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_DELEGACIONES_NEW_TITLE));

		// Añadimos el destino del formulario
		request.setAttribute(Constant.DELEGACIONES_FORM_URL_ATTRIBUTE_KEY,
				MappingConstant.DELEGACIONES_ROOT + MappingConstant.DELEGACIONES_CREATE);

		return ViewsConstant.VIEW_DELEGACIONES_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear una delegacion
	 *
	 * @param request
	 * @param formDelegaciones,
	 * formDelegacionesSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.DELEGACIONES_CREATE)
	public String create(HttpServletRequest request,
			@ModelAttribute(Constant.DELEGACIONES_FORM_ATTRIBUTE_KEY) DelegacionesForm formDelegaciones,
			@ModelAttribute(Constant.DELEGACIONES_SEARCH_FORM_ATTRIBUTE_KEY) DelegacionesSearchForm formDelegacionesSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formDelegaciones, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el delegacion
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			DelegacionesDto delegacionesDto = this.delegacionesFormToDto(formDelegaciones);

			// Llamamos al servicio para guardar el delegacion
			try {
				delegacionesDto = this.delegacionesService.save(delegacionesDto);
				if (delegacionesDto.getErrors() != null) {
					errors = delegacionesDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al almacenar el delegacion", e);
				throw new ControllerException("Error al almacenar el delegacion", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_DELEGACIONES_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.DELEGACIONES_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_DELEGACIONESS_TITLE,
					I18Constant.I18_NOTIFICATIONS_DELEGACIONESS_CREATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de delegaciones
			this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_HEAD_TITLE);
			formDelegacionesSearch.reset();

		} else if (formDelegaciones != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formDelegaciones.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.DELEGACIONES_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_DELEGACIONES_NEW_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.DELEGACIONES_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.DELEGACIONES_ROOT + MappingConstant.DELEGACIONES_CREATE);

			// Añadimos el título de nuevo delegacion
			this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_NEW_TITLE);

			view = ViewsConstant.VIEW_DELEGACIONES_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Mostrar la página de nuevo delegacion
	 *
	 * @param request
	 * @param slug
	 * @param formDelegaciones
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping(MappingConstant.DELEGACIONES_EDIT)
	public String editDelegaciones(HttpServletRequest request,
			@PathVariable(Constant.DELEGACIONES_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.DELEGACIONES_FORM_ATTRIBUTE_KEY) DelegacionesForm formDelegaciones) throws ControllerException {

		DelegacionesDto delegacionesDto = null;

		// Añadimos el título de nuevo delegacion
		this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_EDIT_TITLE);

		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		// Añadimos el título personalizado por compartir formulario para crear y editar
		request.setAttribute(Constant.DELEGACIONES_FORM_TITLE_ATTRIBUTE_KEY,
				this.getMessage(I18Constant.I18_VIEW_DELEGACIONES_EDIT_TITLE));

		// Recuperar los datos del delegacion a editar
		try {
			// Si el delegacion no existe, getDelegacionesBySlug lanzará una excepción
			delegacionesDto = this.delegacionesService.getDelegacionesBySlug(slug);
		} catch (ServiceException e) {
			LOG.error("Error al recuperar los datos del delegacion: {}", slug, e);
			throw new ControllerException("Error al recuperar el delegacion: " + slug, e);
		}

		// Añadimos el destino del formulario
				request.setAttribute(Constant.DELEGACIONES_FORM_URL_ATTRIBUTE_KEY,
						MappingConstant.DELEGACIONES_ROOT + MappingConstant.DELEGACIONES_UPDATE.replace("{slug}", delegacionesDto.getSlug()));

		// Preparamos el delegacionesForm a partir del delegacionesDto
		this.delegacionesDtoToForm(delegacionesDto, formDelegaciones);

		return ViewsConstant.VIEW_DELEGACIONES_NEW_OR_EDIT_PAGE;
	}

	/**
	 * Crear una delegacion
	 *
	 * @param request
	 * @param slug
	 * @param formDelegaciones
	 * @param formDelegacionesSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@PostMapping(MappingConstant.DELEGACIONES_UPDATE)
	public String update(HttpServletRequest request, @PathVariable(Constant.DELEGACIONES_SLUG_PATH_VARIABLE_KEY) String slug,
			@ModelAttribute(Constant.DELEGACIONES_FORM_ATTRIBUTE_KEY) DelegacionesForm formDelegaciones,
			@ModelAttribute(Constant.DELEGACIONES_SEARCH_FORM_ATTRIBUTE_KEY) DelegacionesSearchForm formDelegacionesSearch,
			BindingResult bindingResult) throws ControllerException {

		// Vista a cargar
		String view = null;

		// Validamos el formulario
		List<String> errors = this.validateForm(formDelegaciones, bindingResult);

		// Si no hubo errores, llamaremos al servicio para crear el usuario
		if (!this.commonUtils.isAvailable(errors)) {

			// Generamos el DTO a partir del formulario
			DelegacionesDto delegacionesDto = this.delegacionesFormToDto(formDelegaciones);

			// Llamamos al servicio para editar el delegacion
			try {
				delegacionesDto = this.delegacionesService.update(delegacionesDto);
				if (delegacionesDto != null && delegacionesDto.getErrors() != null) {
					errors = delegacionesDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}
			} catch (ServiceException e) {
				LOG.error("Error al guardar el delegacion", e);
				throw new ControllerException("Error al guardar el delegacion", e);
			}

		}

		// Si fue correcto, redirigimos al listado
		if (!this.commonUtils.isAvailable(errors)) {
			view = ViewsConstant.VIEW_DELEGACIONES_SEARCH_PAGE;
			this.changeUrl(request, MappingConstant.DELEGACIONES_ROOT);
			this.setNotification(request, I18Constant.I18_NOTIFICATIONS_DELEGACIONESS_TITLE,
					I18Constant.I18_NOTIFICATIONS_DELEGACIONESS_UPDATE_SUCCESS, NotificationTypes.SUCCESS);

			// Añadimos el título para el listado de delegaciones
			this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_HEAD_TITLE);
			formDelegacionesSearch.reset();

		} else if (formDelegaciones != null) {
			// Si hubo problemas, volvemos al formulario de creación
			formDelegaciones.setErrors(errors);

			// Añadimos el título personalizado por compartir formulario para crear y editar
			request.setAttribute(Constant.DELEGACIONES_FORM_TITLE_ATTRIBUTE_KEY,
					this.getMessage(I18Constant.I18_VIEW_DELEGACIONES_EDIT_TITLE));

			// Añadimos el destino del formulario
			request.setAttribute(Constant.DELEGACIONES_FORM_URL_ATTRIBUTE_KEY,
					MappingConstant.DELEGACIONES_ROOT + MappingConstant.DELEGACIONES_UPDATE.replace("{slug}", slug));

			// Añadimos el título de modificar delegacion
			this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_EDIT_TITLE);

			view = ViewsConstant.VIEW_DELEGACIONES_NEW_OR_EDIT_PAGE;
		}

		// Añadimos el menu
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return view;

	}

	/**
	 * Elimina una delegacion
	 *
	 * @param slug delegacion a eliminar
	 * @return Código HTTP 200 si se eliminó correctamente. Código HTTP 404 si no
	 *         existe el usuario a eliminar
	 *
	 * @throws ControllerException
	 */
	@DeleteMapping(MappingConstant.DELEGACIONES_DELETE)
	public ResponseEntity<Void> deleteDelegaciones(@PathVariable(Constant.DELEGACIONES_ID_PATH_VARIABLE_KEY) Long id) {

		boolean deleted = true;
		HttpStatus httpStatus = null;

		try {

			this.delegacionesService.delete(id);
		} catch (ServiceException e) {
			LOG.error("Error al eliminar el delegacion con id: {}", id, e);
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
	 * Generamos un {@link DelegacionesDto} a partir de un {@link DelegacionesForm}
	 *
	 * @param formDelegaciones delegacion
	 * @return delegacion
	 */
	private DelegacionesDto delegacionesFormToDto(DelegacionesForm formDelegaciones) {

		DelegacionesDto delegacionesDto = null;

		if (formDelegaciones != null) {

			// Generamos el DTO a partir del formulario
			delegacionesDto = new DelegacionesDto();
			delegacionesDto.setName(formDelegaciones.getName());
			delegacionesDto.setId(formDelegaciones.getId());

		}

		return delegacionesDto;

	}

	/**
	 * Cargamos en el formulario {@link DelegacionesForm} los datos del delegacion {@link DelegacionesDto}
	 *
	 * @param delegacionesDto delegacion
	 * @param delegacionesForm Formulario
	 *
	 */
	private void delegacionesDtoToForm(DelegacionesDto delegacionesDto, DelegacionesForm delegacionesForm) {

		if (delegacionesDto != null && delegacionesForm != null) {

			delegacionesForm.setId(delegacionesDto.getId());
			delegacionesForm.setName(delegacionesDto.getName());


		}

	}

}
