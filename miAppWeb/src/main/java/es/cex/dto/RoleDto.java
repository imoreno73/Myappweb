package es.cex.dto;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class with user role DTO
 */

public class RoleDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -2286853293803904007L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("slug")
	private String slug;

	@JsonProperty("controlTotalMenu")
	private Boolean controlTotalMenu;

	@JsonProperty("active")
	private Boolean active;

	@JsonProperty("functionalGroup")
	private FunctionalGroupDto functionalGroup;

	@DateTimeFormat
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	@JsonProperty("lockedDate")
	private ZonedDateTime lockedDate;

	public RoleDto() {
		super();
	}

	public RoleDto(Long id) {
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

	public Boolean getControlTotalMenu() {
		return controlTotalMenu;
	}

	public void setControlTotalMenu(Boolean controlTotalMenu) {
		this.controlTotalMenu = controlTotalMenu;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ZonedDateTime getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(ZonedDateTime lockedDate) {
		this.lockedDate = lockedDate;
	}

	public FunctionalGroupDto getFunctionalGroup() {
		return functionalGroup;
	}

	public void setFunctionalGroup(FunctionalGroupDto functionalGroup) {
		this.functionalGroup = functionalGroup;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
