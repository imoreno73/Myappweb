package es.cex.controller.form;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class with Delegaciones Form
 */

public class DelegacionesForm extends ErrorForm {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 2253013484270527631L;

	private Long id;
	@NotEmpty
	private Date opendate;
	@NotEmpty
	private String country;
	@NotEmpty
	private String delegationorigin;
	@NotEmpty
	private String delegationdestiny;
	@NotEmpty
	private String namedelegation;
	@NotEmpty
	private String adress;
	@NotEmpty
	private String city;
	@NotNull
	private int cp;
	@NotEmpty
	private String province;
	@NotEmpty
	private String dni;
	@NotNull
	private double geolat;
	@NotNull
	private double geolong;
	@NotEmpty
	private String time_open;
	@NotEmpty
	private String time_closed;

	public Date getOpendate() {
		return opendate;
	}

	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDelegationorigin() {
		return delegationorigin;
	}

	public void setDelegationorigin(String delegationorigin) {
		this.delegationorigin = delegationorigin;
	}

	public String getDelegationdestiny() {
		return delegationdestiny;
	}

	public void setDelegationdestiny(String delegationdestiny) {
		this.delegationdestiny = delegationdestiny;
	}

	public String getNamedelegation() {
		return namedelegation;
	}

	public void setNamedelegation(String namedelegation) {
		this.namedelegation = namedelegation;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public double getGeolat() {
		return geolat;
	}

	public void setGeolat(double geolat) {
		this.geolat = geolat;
	}

	public double getGeolong() {
		return geolong;
	}

	public void setGeolong(double geolong) {
		this.geolong = geolong;
	}

	public String getTime_open() {
		return time_open;
	}

	public void setTime_open(String time_open) {
		this.time_open = time_open;
	}

	public String getTime_closed() {
		return time_closed;
	}

	public void setTime_closed(String time_closed) {
		this.time_closed = time_closed;
	}

	@NotEmpty(message = "view.delegaciones.error.name.empty")
	private String name;

	public DelegacionesForm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
