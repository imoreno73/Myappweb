package es.cex.dto;

/**
 * Class with functional groups DTO
 */

public class FunctionalGroupDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 5427417224248907488L;

	private Long id;
	private String name;
	private String slug;

        public FunctionalGroupDto(Long id) {
            this.id = id;
        }

        public FunctionalGroupDto() {
            super();
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
