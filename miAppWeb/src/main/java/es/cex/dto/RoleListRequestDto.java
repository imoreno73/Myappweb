package es.cex.dto;

/**
 * Request role list of objects
 */
public class RoleListRequestDto extends PaginationDto {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4836766369576925788L;

	private String userIds;
	private Long functionalGroupId;

	public RoleListRequestDto() {
		super();
	}

	public RoleListRequestDto(String userIds, Long functionalGroupId) {
		super();
		this.userIds = userIds;
		this.functionalGroupId = functionalGroupId;
	}

	public RoleListRequestDto(Long functionalGroupId) {
		super();
		this.functionalGroupId = functionalGroupId;
	}

	public RoleListRequestDto(String userIds) {
		super();
		this.userIds = userIds;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Long getFunctionalGroupId() {
		return functionalGroupId;
	}

	public void setFunctionalGroupId(Long functionalGroupId) {
		this.functionalGroupId = functionalGroupId;
	}

}
