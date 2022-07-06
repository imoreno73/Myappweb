package es.cex.controller.form;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

public class TranslateMenuRequestForm implements Serializable {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = 6212285536861778755L;

	@NotEmpty
	private Map<String, String> translate;

	public Map<String, String> getTranslate() {
		return translate;
	}

	public void setTranslate(Map<String, String> translate) {
		this.translate = translate;
	}

}
