package es.cex.dto;

/**
 * Request user paginated list of objects with additional pagination information.
 */
public class UserPaginatedRequestDto extends PaginationDto {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3053707084076782158L;

	private String data;
	private Long functionalGroupId;
	private Long roleId;
	private Boolean active;

	public UserPaginatedRequestDto() {
		super();
	}

	public UserPaginatedRequestDto(String data, Long functionalGroupId, Long roleId, Boolean active, Integer size, Integer page) {
		super();
		this.setData(data);
		this.setFunctionalGroupId(functionalGroupId);
		this.setRoleId(roleId);
		this.setActive(active);
		this.setPage(page);
		this.setSize(size);
	}

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
