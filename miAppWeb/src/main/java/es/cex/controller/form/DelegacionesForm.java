package es.cex.controller.form;

import javax.validation.constraints.NotEmpty;

/**
 * Class with Delegaciones Form
 */

public class DelegacionesForm extends ErrorForm {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 2253013484270527631L;

	private Long id;

	@NotEmpty(message = "view.delegaciones.error.name.empty")
	private String name;

	public DelegacionesForm() {
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
