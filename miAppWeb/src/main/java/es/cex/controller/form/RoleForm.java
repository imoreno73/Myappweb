package es.cex.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class with user role form
 */

public class RoleForm extends ErrorForm {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 2132334254063668715L;

	private Long id;

	@NotEmpty(message = "view.role.error.name.empty")
	private String name;

	@NotNull(message = "view.role.error.functional.group.empty")
	private Long functionalGroup;

	private Boolean active;

	private Boolean controlTotalMenu;

	public RoleForm() {

	}

	public RoleForm(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getControlTotalMenu() {
		return controlTotalMenu;
	}

	public void setControlTotalMenu(Boolean controlTotalMenu) {
		this.controlTotalMenu = controlTotalMenu;
	}

	public Long getFunctionalGroup() {
		return functionalGroup;
	}

	public void setFunctionalGroup(Long functionalGroup) {
		this.functionalGroup = functionalGroup;
	}

}
