package es.cex.config.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import es.cex.common.constant.Constant;

public class ThemeInterceptor extends HandlerInterceptorAdapter {

	@Value("#{'${authentication.urls.skip:/login,/css/,/js/,/jquery/,/images/,/script/}'.split(',')}")
	private List<String> skipUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// Exluir recursos estaticos
		if (!this.isUrlSkiped(request.getServletPath())) {

			// Recogemos el atributo del tema y lo cargamos en la sesión del usuario
			String appTheme = request.getParameter(Constant.APP_THEME);

			if (appTheme != null) {
				// Si recibimos un tema lo actualizamos
				request.getSession().setAttribute(Constant.APP_THEME, appTheme);
			} else if (request.getSession().getAttribute(Constant.APP_THEME) == null) {
				// Si NO recibimos un tema pero no teníamos ninguno registrado, indicamos un
				// tema por defecto
				request.getSession().setAttribute(Constant.APP_THEME, Constant.CEX_THEME);
			}
		}

		return true;
	}

	private boolean isUrlSkiped(final String url) {

		return skipUrls.stream().map(e -> e.trim()).filter(e -> !e.isEmpty()).anyMatch(url::startsWith);

	}

}
