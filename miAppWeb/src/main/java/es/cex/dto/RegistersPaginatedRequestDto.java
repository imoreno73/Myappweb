package es.cex.dto;

/**
 * Request functional group paginated list of objects with additional pagination information.
 */
public class RegistersPaginatedRequestDto extends PaginationDto {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7335812823141459782L;

	private String name;

	public RegistersPaginatedRequestDto() {
		super();
	}

	public RegistersPaginatedRequestDto(String name, Integer size, Integer page) {
		super();
		this.setName(name);
		this.setPage(page);
		this.setSize(size);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
