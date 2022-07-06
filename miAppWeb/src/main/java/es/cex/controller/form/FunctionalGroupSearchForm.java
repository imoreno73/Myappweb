package es.cex.controller.form;

import java.io.Serializable;

/**
 * Functional Group Search Form
 */
public class FunctionalGroupSearchForm implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 4840230779780431100L;

	private String name;

	public FunctionalGroupSearchForm() {
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
