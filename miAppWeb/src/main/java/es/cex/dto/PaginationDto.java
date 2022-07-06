package es.cex.dto;

import java.io.Serializable;
/**
 *  Containing criteria for paginating queries
 */

public class PaginationDto implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -371338544337787485L;

	/**
	 * size
	 */
	private Integer size;

	/**
	 * page
	 */
	private Integer page;

	/**
	 * The constructor.
	 */
	public PaginationDto() {

		super();
	}

	/**
	 * Constructor expecting an existing the size, the page and the total number of
	 * results found.
	 *
	 * @param size
	 *            is the size of the page.
	 * @param page
	 *            is the current page.
	 * @param total
	 *            is the total number of results found without pagination.
	 */
	public PaginationDto(Integer size, int page, Long total) {

		super();

		setPage(page);
		setSize(size);
	}

	/**
	 * @return size the size of a page.
	 */
	public Integer getSize() {

		return this.size;
	}

	/**
	 * @param size
	 *            the size of a page.
	 */
	public void setSize(Integer size) {

		this.size = size;
	}

	/**
	 * @return page the current page.
	 */
	public Integer getPage() {

		return this.page;
	}

	/**
	 * @param page
	 *            the current page. Must be greater than 0.
	 */
	public void setPage(Integer page) {

		this.page = page;
	}
}