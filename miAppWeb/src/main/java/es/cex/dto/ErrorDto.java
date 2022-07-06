package es.cex.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Class with error fields
 */

public class ErrorDto implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 8139130287556335433L;

	private Map<String,String> errors;

	public Map<String,String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String,String> errors) {
		this.errors = errors;
	}

}
