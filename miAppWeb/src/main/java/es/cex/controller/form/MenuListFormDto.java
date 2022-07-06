package es.cex.controller.form;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.cex.dto.ErrorDto;

/**
 * Class with page DTO
 */

public class MenuListFormDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -1195084377434915095L;

	private String id;

	private String parent;

	private String text;

	private String icon;

	@JsonProperty("a_attr")
	private Map<String, String> attr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

}
