package es.cex.dto;

import java.io.Serializable;

/**
 * Class with notification fields
 */

public class NotificationDto implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 3254950246413557616L;

	private String title;
	private String text;
	private String type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
