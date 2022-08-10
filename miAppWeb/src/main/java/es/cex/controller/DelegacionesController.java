package es.cex.controller;

import java.util.Arrays;

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
import org.springframework.ui.Model;

/**
 * Controlador para usuarios
 */
@Controller


@RequestMapping(MappingConstant.DELEGACIONES_ROOT)
public class DelegacionesController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(DelegacionesController.class);

//	@Autowired
//	private IDelegacionesService delegacionesService;

//	@Autowired
//	private CommonUtils commonUtils;

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
		//this.setHeadTitle(request, I18Constant.I18_VIEW_DELEGACIONES_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());

		return ViewsConstant.VIEW_DELEGACIONES_SEARCH_PAGE;
	}
	
	//controlador para submit form?
	
	/*
	 * @GetMapping("/delegaciones") public String delegacionesForm(Model model) {
	 * model.addAttribute("delegaciones", new Delegaciones()); return
	 * "delegaciones"; }
	 * 
	 * @PostMapping("/delegaciones") public String
	 * delegacionesSubmit(@ModelAttribute delegaciones Delegaciones, Model model) {
	 * model.addAttribute("delegaciones", delegaciones); return "result"; }
	 */
	
	
	     
	    @GetMapping("/register")
	    public String showForm(Model model) {
	        DelegacionesForm delegacionesform = new DelegacionesForm();
	        model.addAttribute("user", delegacionesform);
	         
	        List<String> listDelegacion = Arrays.asList("opendate", "country", "delegationorigin", "delegationdestiny", "namedelegation", "delegationadress",
	        	"city", "cp", "province", "dni", "time_open", "time_closed", "geolat", "geolong", "forward", "pickup", "highuser", "highdrivers", 
	        	"highusersgp", "highusersupervisorsgp", "highroutessgp", "cptomigrate", "routestomigrate", "clientstomigrate", "equivalence");
	        model.addAttribute("listDelegacion", listDelegacion);
	         
	        return "layout-submit";
	    }
	    	       
	    @PostMapping("/register")
	    public String submitForm(@ModelAttribute("delegacionesform") DelegacionesForm delegacionesform) {
	        System.out.println(delegacionesform);
	        return "layout-submit";
	    }
//	}
}


