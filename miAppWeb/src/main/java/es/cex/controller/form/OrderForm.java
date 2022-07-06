package es.cex.controller.form;

import javax.validation.constraints.NotNull;

public class OrderForm extends ErrorForm {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = -1575444742784346872L;

	@NotNull
	private Long id;

	@NotNull
	private Long idParent;

	@NotNull
	private Long orden;

	@NotNull
	private Long idParentOld;

	@NotNull
	private Long ordenOld;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	/**
	 * @return the idParentOld
	 */
	public Long getIdParentOld() {
		return idParentOld;
	}

	/**
	 * @param idParentOld the idParentOld to set
	 */
	public void setIdParentOld(Long idParentOld) {
		this.idParentOld = idParentOld;
	}

	/**
	 * @return the ordenOld
	 */
	public Long getOrdenOld() {
		return ordenOld;
	}

	/**
	 * @param ordenOld the ordenOld to set
	 */
	public void setOrdenOld(Long ordenOld) {
		this.ordenOld = ordenOld;
	}


}
