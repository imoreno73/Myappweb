package es.cex.dto;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class with user DTO
 */
public class UserDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -24595871407998053L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("login")
	private String login;

	@JsonProperty("name")
	private String name;

	@JsonProperty("surname")
	private String surname;

	@JsonProperty("email")
	private String email;

	@JsonProperty("roles")
	private List<RoleDto> roles;

	@JsonProperty("functionalGroups")
	private List<FunctionalGroupDto> functionalGroups;

	@JsonProperty("active")
	private Boolean active;

	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	@JsonProperty("lockedDate")
	private ZonedDateTime  lockedDate;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

        public List<FunctionalGroupDto> getFunctionalGroups() {
            return functionalGroups;
        }

        public void setFunctionalGroups(List<FunctionalGroupDto> functionalGroups) {
            this.functionalGroups = functionalGroups;
        }

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ZonedDateTime  getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(ZonedDateTime lockedDate) {
		this.lockedDate = lockedDate;
	}

}
