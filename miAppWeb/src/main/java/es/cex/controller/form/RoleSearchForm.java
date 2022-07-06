package es.cex.controller.form;

import java.io.Serializable;

/**
 * Role Search Form
 */
public class RoleSearchForm implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -775475607600895411L;

	private String name;
	private Boolean active;
	private Boolean controlTotalMenu;
	private Long functionalGroup;

	public RoleSearchForm() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFunctionalGroup() {
		return functionalGroup;
	}

	public void setFunctionalGroup(Long functionalGroup) {
		this.functionalGroup = functionalGroup;
	}

	public void reset() {
		this.name = null;
		this.active = null;
		this.controlTotalMenu = null;
		this.functionalGroup = null;
	}

}
