package es.cex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

import es.cex.app.arq.controller.CexController;
import es.cex.common.constant.Constant;
import es.cex.common.types.NotificationTypes;
import es.cex.controller.form.ErrorForm;
import es.cex.dto.NotificationDto;

/**
 * Base controller
 */
public abstract class BaseController extends CexController {

	@Autowired
	SmartValidator validator;

	protected void changeUrl(HttpServletRequest request, String url) {

		request.setAttribute(Constant.CEX_CHANGE_URL_KEY, url);

	}

	protected void setNotification(HttpServletRequest request, String titleKey, String textKey, NotificationTypes type) {

		NotificationDto notificationDto = new NotificationDto();

		if(titleKey != null) {
			notificationDto.setTitle(this.getMessage(titleKey));
		}

		if(textKey != null) {
			notificationDto.setText(this.getMessage(textKey));
		}

		if(type != null) {
			notificationDto.setType(type.getNotificationType());
		} else {
			notificationDto.setType(NotificationTypes.INFO.getNotificationType());
		}

		request.setAttribute(Constant.CEX_NOTIFICATION_KEY, notificationDto);

	}

	protected List<String> validateForm(ErrorForm form, BindingResult bindingResult) {

		List<String> errors = null;

		// Validamos el formulario
		validator.validate(form, bindingResult);

		if (bindingResult.hasErrors()) {

			errors = new ArrayList<>();
			for(ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}

			form.setErrors(errors);

		}

		return errors;

	}
}
