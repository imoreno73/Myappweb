package es.cex.controller.form;

import javax.validation.constraints.NotEmpty;

/**
 * Class with Functional Group Form
 */

public class FunctionalGroupForm extends ErrorForm {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 2253013484270527631L;

	private Long id;

	@NotEmpty(message = "view.functional.group.error.name.empty")
	private String name;

	public FunctionalGroupForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
