package es.cex.dto;

/**
 * Request role paginated list of objects with additional pagination information.
 */
public class RolePaginatedRequestDto extends PaginationDto {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7460340673766321055L;

	private String name;
	private Boolean active;
	private Boolean controlTotalMenu;
	private Long functionalGroup;

	public RolePaginatedRequestDto() {
		super();
	}

	public RolePaginatedRequestDto(String name, Boolean active, Boolean controlTotalMenu, Long functionalGroup, Integer size, Integer page) {
		super();
		this.setName(name);
		this.setControlTotalMenu(controlTotalMenu);
		this.setActive(active);
		this.setFunctionalGroup(functionalGroup);
		this.setPage(page);
		this.setSize(size);
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
