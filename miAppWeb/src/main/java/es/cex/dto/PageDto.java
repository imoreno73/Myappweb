package es.cex.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class with page DTO
 */

public class PageDto extends ErrorDto {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 715863116403890194L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("parent")
	private Long parent;

	@JsonProperty("name")
	private Map<String, String> name;

	@JsonProperty("slug")
	private Map<String, String> slug;

	@JsonProperty("urlIframe")
	private String urlIframe;

	@JsonProperty("roles")
	private List<RoleDto> roles;

	@JsonProperty("navTab")
	private boolean navTab;

	@JsonProperty("orden")
	private Long orden;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public Map<String, String> getSlug() {
		return slug;
	}

	public void setSlug(Map<String, String> slug) {
		this.slug = slug;
	}

	public String getUrlIframe() {
		return urlIframe;
	}

	public void setUrlIframe(String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	/**
	 * @return the navTab
	 */
	public boolean isNavTab() {
		return navTab;
	}

	/**
	 * @param navTab the navTab to set
	 */
	public void setNavTab(boolean navTab) {
		this.navTab = navTab;
	}

	/**
	 * @return the orden
	 */
	public Long getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Long orden) {
		this.orden = orden;
	}
}
