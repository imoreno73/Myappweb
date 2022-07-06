package es.cex.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import es.cex.arq.exceptions.ControllerException;
import es.cex.arq.exceptions.ServiceException;
import es.cex.common.constant.Constant;
import es.cex.common.constant.I18Constant;
import es.cex.common.constant.MappingConstant;
import es.cex.common.constant.ViewsConstant;
import es.cex.common.utils.CommonUtils;
import es.cex.controller.form.MenuForm;
import es.cex.controller.form.MenuListFormDto;
import es.cex.controller.form.OrderForm;
import es.cex.controller.form.TranslateMenuRequestForm;
import es.cex.dto.PageDto;
import es.cex.dto.RoleDto;
import es.cex.service.ICacheIntranetService;
import es.cex.service.IFunctionalGroupService;
import es.cex.service.IPageService;
import es.cex.service.IRoleService;

/**
 * Controlador para páginas
 */
@Controller
@RequestMapping(MappingConstant.PAGE_ROOT)
public class PageController extends BaseController {

	private static final String ERROR_AL_ACTUALIZAR_EL_ORDER = "Error al actualizar el order.";

	private static final String ERROR_ROLES_REQUEST = "Error al recuperar la lista completa de roles para añadirlos a la request";

	private static final String ERROR_GRUPOS_FUNCIONALES_REQUEST = "Error al recuperar la lista completa de grupos funcionales para añadirlos a la request";

	private static final String MENU_NO_PUEDE_SER_NULL = "Menu no puede ser null";

	private static final String ERROR_AL_BORRAR_EL_CACHE = "Error al borrar el cache.";

	private static final String ERROR_AL_ELIMINAR_LA_PAGINA_CON_ID = "Error al eliminar la página con id: {}";

	private static final String ERROR_AL_GESTIONAR_LA_PAGINA = "Error al gestionar la página";

	private static final String ERROR_AL_RECUPERAR_LA_PAGINA = "Error al recuperar la página {}";

	private static final String ERROR_AL_RECUPERAR_LA_LISTA_DE_PAGINAS = "Error al recuperar la lista de páginas";

	private static final Logger LOG = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private IPageService pageService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private ICacheIntranetService cacheIntranetService;

	@Autowired
	private IFunctionalGroupService functionalGroupService;

	@Autowired
	private Map<String, String> localesAvailables;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * Carga la página principal de páginas
	 *
	 * @param request
	 *            request
	 * @param formUserSearch
	 *            formUserSearch
	 * @return view
	 * @throws ControllerException
	 *
	 */
	@GetMapping("")
	public String search(HttpServletRequest request) throws ControllerException {

		this.setHeadTitle(request, I18Constant.I18_VIEW_PAGE_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());
		request.setAttribute(Constant.CEX_LOCALES, localesAvailables);

		this.addFunctionalGroupsToRequest(request);
		this.addRolesToRequest(request);

		return ViewsConstant.VIEW_PAGE_MANAGE;
	}

	/**
	 * Recuperar el menu completo
	 *
	 * @param request
	 * @return JSON con el menu
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.PAGE_LIST)
	public ResponseEntity<List<MenuListFormDto>> getMenu(HttpServletRequest request) throws ControllerException {

		// Preparamos el DTO de envío a partir de los iedntificadores de usuario
		List<MenuListFormDto> menu = null;
		List<PageDto> pages = null;

		try {
			pages = this.pageService.getPageList();
		} catch (ServiceException e) {
			LOG.error(ERROR_AL_RECUPERAR_LA_LISTA_DE_PAGINAS, e);
			throw new ControllerException(ERROR_AL_RECUPERAR_LA_LISTA_DE_PAGINAS, e);
		}

		menu = this.pagesListDtoToMenuListForm(pages);

		return ResponseEntity.ok(menu);

	}

	/**
	 * Recuperar un elemento del menú
	 *
	 * @param request
	 * @return JSON con el menu
	 * @throws ControllerException
	 */
	@GetMapping(MappingConstant.PAGE_DETAIL)
	public ResponseEntity<MenuForm> getMenuItem(
			@PathVariable(Constant.PAGE_ID_PATH_VARIABLE_KEY) Long id,
			HttpServletRequest request) {

		HttpStatus response = null;
		MenuForm menuForm = null;
		PageDto pageDto = null;

		// Llamamos al servicio para recuperar el detalle de la página
		try {
			pageDto = this.pageService.getPageById(id);
			menuForm = this.pageDtoToMenuForm(pageDto);
			response = HttpStatus.OK;
		} catch (ServiceException e) {
			LOG.error(ERROR_AL_RECUPERAR_LA_PAGINA, id, e);
			response = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(menuForm, response);

	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuForm> createOrUpdateMenuItem(@RequestBody MenuForm menuForm, BindingResult bindingResult) {

		List<String> errors = this.validateForm(menuForm, bindingResult);
		HttpStatus response = null;

		if (!commonUtils.isAvailable(errors)) {

			// Convertir form en DTO
			PageDto pageRequestDto = null;
			PageDto pageResponseDto = null;

			try {

				pageRequestDto = this.pageFormToDto(menuForm);

				// llamar al service para actualizar o crear una nueva
				// Segun recibamos id o no de página
				if (pageRequestDto.getId() != null) {
					pageResponseDto = this.pageService.update(pageRequestDto);
				} else {
					pageResponseDto = this.pageService.save(pageRequestDto);
					menuForm.setId(pageResponseDto.getId());
				}

				if (pageResponseDto.getErrors() != null) {
					errors = pageResponseDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}

				if (!this.commonUtils.isAvailable(errors)) {
					response = HttpStatus.OK;
					cacheIntranetService.flush();
				} else {
					response = HttpStatus.BAD_REQUEST;
				}



			} catch (ServiceException e) {
				LOG.error(ERROR_AL_GESTIONAR_LA_PAGINA, e);
				response = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} else {
			response = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(menuForm, response);
	}

	/**
	 * Elimina una página
	 *
	 * @param login
	 *            Página a eliminar
	 * @return Código HTTP 200 si se eliminó correctamente. Código HTTP 404 si no
	 *         existe la página a eliminar
	 *
	 * @throws ControllerException
	 */
	@DeleteMapping(MappingConstant.PAGE_DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable(Constant.PAGE_ID_PATH_VARIABLE_KEY) Long id) {

		boolean deleted = true;
		HttpStatus httpStatus = null;

		try {
			this.pageService.delete(id);
		} catch (ServiceException e) {
			LOG.error(ERROR_AL_ELIMINAR_LA_PAGINA_CON_ID, id, e);
			deleted = false;
		}

		if (deleted) {
			httpStatus = HttpStatus.OK;
			try {
				cacheIntranetService.flush();
			}catch(ServiceException e) {
				LOG.error(ERROR_AL_BORRAR_EL_CACHE, e);
			}
		} else {
			httpStatus = HttpStatus.NOT_FOUND;
		}

		return ResponseEntity.status(httpStatus).build();
	}


	@PatchMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderForm> orderMenuItem(@RequestBody OrderForm orderForm, BindingResult bindingResult) {

		List<String> errors = this.validateForm(orderForm, bindingResult);
		HttpStatus response = null;

		if (!commonUtils.isAvailable(errors)) {

			// Convertir form en DTO
			PageDto pageResponseDto = null;

			try {

				pageResponseDto = this.pageService.getPageById(orderForm.getId());

				//reordenar
				List<PageDto> pages = this.pageService.getPageListParent(orderForm.getIdParent());

				if (pages != null) {
					pages.stream().
					forEach(e -> this.updateOrder(e, orderForm.getIdParentOld(), orderForm.getOrdenOld(),
							orderForm.getIdParent(), orderForm.getOrden(), orderForm.getId()));
				}

				//Actualiza si es de otro padre
				updateOldParent(orderForm);

				pageResponseDto.setParent(orderForm.getIdParent());
				pageResponseDto.setOrden(orderForm.getOrden());

				pageResponseDto = this.pageService.updateParentOrden(pageResponseDto);

				if (pageResponseDto.getErrors() != null) {
					errors = pageResponseDto.getErrors().entrySet()
                            .stream()
                            .map(Entry::getKey)
                            .sorted()
                            .collect(Collectors.toList());
				}

				if (!this.commonUtils.isAvailable(errors)) {
					response = HttpStatus.OK;
					cacheIntranetService.flush();
				} else {
					response = HttpStatus.BAD_REQUEST;
				}



			} catch (ServiceException e) {
				LOG.error(ERROR_AL_GESTIONAR_LA_PAGINA, e);
				response = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} else {
			response = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(orderForm, response);
	}

	// /**
	// * Generamos un {@link PageDto} a partir de un {@link PageForm}
	// *
	// * @param pageForm
	// * pageForm
	// * @return Página
	// */
	private PageDto pageFormToDto(MenuForm menuForm) throws ServiceException {

		PageDto pageDto = null;

		if (menuForm == null) {
			throw new ServiceException(MENU_NO_PUEDE_SER_NULL);
		}

		// Generamos el DTO a partir del formulario
		pageDto = new PageDto();
		pageDto.setId(menuForm.getId());
		pageDto.setParent(menuForm.getIdParent());
		pageDto.setName(menuForm.getName().getTranslate());
		pageDto.setSlug(menuForm.getSlug().getTranslate());
		pageDto.setUrlIframe(menuForm.getUrlIframe());
		pageDto.setNavTab(menuForm.getNavTab());
		pageDto.setOrden(menuForm.getOrden());

		if (commonUtils.isAvailable(menuForm.getRoles())) {
			// mapear roles
			pageDto.setRoles(menuForm.getRoles().stream().map(RoleDto::new).collect(Collectors.toList()));
		}

		return pageDto;

	}

	/**
	 * Generamos el menu {@link MenuListFormDto} a partir de las páginas
	 * {@link PageDto}
	 *
	 * @param pageDto
	 *            Página
	 * @param MenuListFormDto
	 *            Formulario
	 *
	 */
	private List<MenuListFormDto> pagesListDtoToMenuListForm(List<PageDto> pages) {

		List<MenuListFormDto> menuList = null;
		MenuListFormDto menuPage;
		Map<String, String> attr = null;

		if (commonUtils.isAvailable(pages)) {

			menuList = new ArrayList<>();

			for (PageDto page : pages) {

				menuPage = new MenuListFormDto();
				menuPage.setId(page.getId().toString());
				menuPage.setParent(page.getParent() != null ? page.getParent().toString() : "#");
				menuPage.setIcon("jstree-file");

				if (page.getName() != null) {
					menuPage.setText(page.getName().get("es"));
				}

				attr = new HashMap<>();
				attr.put("data-id", page.getId().toString());
				attr.put("data-tag", this.getPageTags(page));
				menuPage.setAttr(attr);

				menuList.add(menuPage);

			}

		}

		return menuList;

	}

	private String getPageTags(PageDto page) {

		List<String> values = new ArrayList<>();

		// Añadimos los names
		if(page.getName() != null) {
			values.addAll(page.getName().values());
		}

		// Añadimos los slugs
		if(page.getSlug() != null) {
			values.addAll(page.getSlug().values());
		}

		// Añadimos la url del iframe
		if(page.getUrlIframe() != null) {
			values.add(page.getUrlIframe());
		}

		return String.join(";", values);
	}

	private MenuForm pageDtoToMenuForm(PageDto pageDto) {

		MenuForm menuForm = null;

		if (pageDto != null) {
			menuForm = new MenuForm();
			menuForm.setId(pageDto.getId());
			menuForm.setIdParent(pageDto.getParent());

			TranslateMenuRequestForm name = new TranslateMenuRequestForm();
			name.setTranslate(pageDto.getName());
			menuForm.setName(name);

			TranslateMenuRequestForm slug = new TranslateMenuRequestForm();
			slug.setTranslate(pageDto.getSlug());
			menuForm.setSlug(slug);

			menuForm.setUrlIframe(pageDto.getUrlIframe());

			menuForm.setNavTab(pageDto.isNavTab());

			menuForm.setOrden(pageDto.getOrden());

			if (commonUtils.isAvailable(pageDto.getRoles())) {
				// mapear roles
				menuForm.setRoles(pageDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList()));
			}

		}

		return menuForm;
	}

	private void addFunctionalGroupsToRequest(HttpServletRequest request) throws ControllerException {

		// Enviamos a la vista la lista de usuarios
		try {
			request.setAttribute(Constant.FUNCTIONAL_GROUPS_LIST_ATTRIBUTE_KEY,
					this.functionalGroupService.getFunctionalGroupList());
		} catch (ServiceException e) {
			LOG.error(ERROR_GRUPOS_FUNCIONALES_REQUEST, e);
			throw new ControllerException(
					ERROR_GRUPOS_FUNCIONALES_REQUEST, e);
		}
	}

	private void addRolesToRequest(HttpServletRequest request) throws ControllerException {

		// Recuperar los roles de usuario para enviarlos a la vista
		try {
			request.setAttribute(Constant.PAGE_ROLES_LIST_ATTRIBUTE_KEY, this.roleService.getRoleList());
		} catch (ServiceException e) {
			LOG.error(ERROR_ROLES_REQUEST, e);
			throw new ControllerException(ERROR_ROLES_REQUEST,
					e);
		}

	}

	private void updateOrder(PageDto pageDto, Long oldParent, Long oldOrden, Long newParent, Long newOrden, Long id) {
		try {
			if(oldParent.equals(newParent)) {
				if(pageDto.getOrden() >= newOrden && !pageDto.getId().equals(id) && pageDto.getOrden() <  oldOrden) {

					pageDto.setOrden(pageDto.getOrden()+1);
					this.pageService.updateParentOrden(pageDto);

				} else if(pageDto.getOrden() >  oldOrden && pageDto.getOrden() <= newOrden && !pageDto.getId().equals(id)) {
					pageDto.setOrden(pageDto.getOrden()-1);
					this.pageService.updateParentOrden(pageDto);

				}
			} else {
				if(pageDto.getOrden() >= newOrden && !pageDto.getId().equals(id)) {
					pageDto.setOrden(pageDto.getOrden()+1);
					this.pageService.updateParentOrden(pageDto);

				}
			}
		} catch (ServiceException e) {
			LOG.error(ERROR_AL_ACTUALIZAR_EL_ORDER, e);
		}

	}

	private void updateOldParent(OrderForm orderForm) throws ServiceException{
		if (!orderForm.getIdParent().equals(orderForm.getIdParentOld())) {
			//reordenar hueco anterior en otro padre
			List<PageDto> pages = this.pageService.getPageListParent(orderForm.getIdParentOld());
			if (pages != null) {
				pages.stream().
				forEach(e -> this.updateOrderOldParent(e, orderForm.getOrdenOld()));
			}
		}
	}

	private void updateOrderOldParent(PageDto pageDto, Long oldOrden) {
		if(pageDto.getOrden() > oldOrden) {
			try {
				pageDto.setOrden(pageDto.getOrden()-1);
				this.pageService.updateParentOrden(pageDto);
			} catch (ServiceException e) {
				LOG.error(ERROR_AL_ACTUALIZAR_EL_ORDER, e);
			}
		}
	}

}
