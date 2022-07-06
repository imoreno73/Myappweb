package es.cex.controller.form;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Class with user DTO
 */

public class UserForm extends ErrorForm {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -5159811243035124502L;

	private Long id;

	@NotEmpty(message = "view.user.error.login.empty")
	private String login;

	private String name;

	private String surname;

	private Boolean active;

	@NotEmpty(message = "view.user.error.email.empty")
	@Email(message = "view.user.error.email.format")
	private String email;

	//@NotEmpty(message = "view.user.error.roles.empty")
	private List<Long> roles;

	//@NotEmpty(message = "view.user.error.roles.empty")
	private List<Long> functionalGroups;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

        public List<Long> getFunctionalGroups() {
            return functionalGroups;
        }

        public void setFunctionalGroups(List<Long> functionalGroups) {
            this.functionalGroups = functionalGroups;
        }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
