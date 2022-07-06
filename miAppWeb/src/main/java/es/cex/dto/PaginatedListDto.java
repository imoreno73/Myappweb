package es.cex.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A paginated list of objects with additional pagination information.
 *
 * @param <E>
 */
public class PaginatedListDto<E> implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -5431484260682355735L;

	/** @see #getPagination() */
	private PaginationDto pagination;

	/**
	 * result
	 */
	private List<E> result;

	/**
	 * total
	 */
	private Integer total;

	/**
	 * The constructor.
	 */
	public PaginatedListDto() {

		super();
	}

	/**
	 * A convenience constructor which accepts a paginated list and
	 * {@link PaginationDto pagination information}.
	 *
	 * @param result
	 *            is the list of objects.
	 * @param pagination
	 *            is the {@link PaginationDto pagination information}.
	 * @param total
	 *            Total number of targets
	 */
	public PaginatedListDto(List<E> result, PaginationDto pagination, Integer total) {

		this.result = result;
		this.pagination = pagination;
		this.total = total;
	}

	/**
	 * @return the list of objects.
	 */
	public List<E> getResult() {

		return this.result;
	}

	/**
	 * @return pagination is the {@link PaginationDto pagination information}.
	 */
	public PaginationDto getPagination() {

		return this.pagination;
	}

	/**
	 * @return total
	 */
	public Integer getTotal() {

		return this.total;
	}

	/**
	 * @param total
	 *            new value of {@link #getTotal}.
	 */
	public void setTotal(Integer total) {

		this.total = total;
	}

}
