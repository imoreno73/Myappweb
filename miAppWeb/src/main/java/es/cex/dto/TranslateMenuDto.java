package es.cex.dto;

import java.io.Serializable;
import java.util.Map;

public class TranslateMenuDto implements Serializable {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = 2842234178469449766L;

	private Map<String, String> translate;

	public Map<String, String> getTranslate() {
		return translate;
	}

	public void setTranslate(Map<String, String> translate) {
		this.translate = translate;
	}

}
