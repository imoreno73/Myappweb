package es.cex.controller.form;

import java.io.Serializable;
import java.util.List;

/**
 * Class with error fields
 */

public class ErrorForm implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 8139130287556335433L;

	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
