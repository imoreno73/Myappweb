package es.cex.dto;

/**
 * Class with delegaciones DTO
 */

public class DelegacionesDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 5427417224248907488L;

	private Long id;
	private String name;
	private String slug;

        public DelegacionesDto(Long id) {
            this.id = id;
        }

        public DelegacionesDto() {
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
