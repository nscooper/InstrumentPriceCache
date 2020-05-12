package com.nscooper.mizuho.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

@Entity
@Table(name = "INSTRUMENT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Instrument implements Serializable {

	private static final long serialVersionUID = 1586479202338599406L;

	public Instrument() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name="isin")
	private String isin;
	
	@Column(name="name")
	private String name;

	/**
	 * @param isin
	 * @param name
	 */
	public Instrument(String isin, String name) {
		super();
		this.isin = isin;
		this.name = name;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((isin == null) ? 0 : isin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		if (id != other.id)
			return false;
		if (isin == null) {
			if (other.isin != null)
				return false;
		} else if (!isin.equals(other.isin))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Instrument [id=" + id + ", isin=" + isin + ", name=" + name + "]";
	}
	
	public final String toJson() {
		JSONObject json =  new JSONObject();
		json.put("id",getId());
		json.put("isin",getIsin());
		json.put("name",getName());
		return json.toString(5);
	}

}
