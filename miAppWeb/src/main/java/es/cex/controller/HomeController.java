package es.cex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.cex.app.arq.controller.CexController;
import es.cex.arq.exceptions.ControllerException;
import es.cex.common.constant.Constant;
import es.cex.common.constant.I18Constant;
import es.cex.common.constant.ViewsConstant;

/**
 * Purpose: Home page
 */
@Controller
@RequestMapping("")
public class HomeController extends CexController {

	/**
	 * Home
	 * @param request
	 * @return
	 * @throws ControllerException
	 */
	@RequestMapping("")
	public String showHome(HttpServletRequest request) throws ControllerException {
		this.setHeadTitle(request, I18Constant.I18_VIEW_HEAD_TITLE);
		request.setAttribute(Constant.CEX_MENU_KEY, this.getMenu());
		return ViewsConstant.VIEW_HOME_PAGE;
	}

}
