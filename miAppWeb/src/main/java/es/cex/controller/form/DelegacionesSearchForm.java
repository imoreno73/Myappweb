package es.cex.controller.form;

import java.io.Serializable;

/**
 * Delegaciones Search Form
 */
public class DelegacionesSearchForm implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 4840230779780431100L;

	private String name;

	public DelegacionesSearchForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void reset() {
		this.name = null;
	}

}
