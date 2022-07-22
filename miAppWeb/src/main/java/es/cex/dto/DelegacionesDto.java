package es.cex.dto;

import java.util.Date;

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
	private Date opendate;
	private String country;
	private String delegationorigin;
	private String delegationdestiny;
	private String namedelegation;
	private String adress;
	private String city;
	private int cp;
	private String province;
	private String dni;
	private double geolat;
	private double geolong;
	private String time;
	
	
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

	public String getNamedelegation() {
		return namedelegation;
	}

	public void setNamedelegation(String namedelegation) {
		this.namedelegation = namedelegation;
	}

}
