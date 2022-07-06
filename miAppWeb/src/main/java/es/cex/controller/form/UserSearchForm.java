package es.cex.controller.form;

import java.io.Serializable;

/**
 * User Search Form
 */
public class UserSearchForm implements Serializable {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -6679073879386114720L;

	private String data;
	private Long functionalGroupId;
	private Long roleId;
	private Boolean active;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getFunctionalGroupId() {
		return functionalGroupId;
	}

	public void setFunctionalGroupId(Long functionalGroupId) {
		this.functionalGroupId = functionalGroupId;
	}

}
