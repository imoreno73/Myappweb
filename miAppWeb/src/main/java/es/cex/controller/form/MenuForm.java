package es.cex.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MenuForm extends ErrorForm {

	/**
	 * serial number
	 */
	private static final long serialVersionUID = -1575444742784346872L;

	private Long id;

	private Long idParent;

	@NotNull(message = "{view.page.error.iframe.empty}")
	private TranslateMenuRequestForm name;

	private TranslateMenuRequestForm slug;

	@Pattern(regexp= "(^$|((http|https)://)(www.)?[a-zA-Z0-9@:%.[-]_\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%.[-]_\\+~#?&//=]*))", message = "{view.page.error.iframe.format}")
	private String urlIframe;

	@NotEmpty(message = "{view.page.error.roles.empty}")
	private List<Long> roles;

	private Boolean navTab = Boolean.TRUE;

	private Long orden;

	public String getUrlIframe() {
		return urlIframe;
	}

	public void setUrlIframe(String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

	public TranslateMenuRequestForm getName() {
		return name;
	}

	public void setName(TranslateMenuRequestForm name) {
		this.name = name;
	}

	public TranslateMenuRequestForm getSlug() {
		return slug;
	}

	public void setSlug(TranslateMenuRequestForm slug) {
		this.slug = slug;
	}

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

	public Boolean getNavTab() {
		return navTab;
	}

	public void setNavTab(Boolean navTab) {
		this.navTab = navTab;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}


}
