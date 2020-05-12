package com.nscooper.mizuho.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

@Entity
@Table(name = "price")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Price implements Serializable {

	private static final long serialVersionUID = -495333724045182233L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false, name = "id")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
	private Vendor vendor;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
	private Instrument instrument;
	
	@Column(nullable=false, name = "bid_price")
	private Double bidPrice;
	
	@Column(nullable=false, name = "offer_price")
	private Double offerPrice;

	@Column(nullable=false, name = "created")
	private Timestamp created;
	
	
	public Price() {
		super();
	}
	
	/**
	 * @param vendor
	 * @param instrument
	 * @param bidPrice
	 * @param offerPrice
	 * @param created
	 */
	public Price(Vendor vendor, Instrument instrument, Double bidPrice, Double offerPrice, Timestamp created) {
		super();
		this.vendor = vendor;
		this.instrument = instrument;
		this.bidPrice = bidPrice;
		this.offerPrice = offerPrice;
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Double getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(Double offerPrice) {
		this.offerPrice = offerPrice;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidPrice == null) ? 0 : bidPrice.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + ((offerPrice == null) ? 0 : offerPrice.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (bidPrice == null) {
			if (other.bidPrice != null)
				return false;
		} else if (!bidPrice.equals(other.bidPrice))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (offerPrice == null) {
			if (other.offerPrice != null)
				return false;
		} else if (!offerPrice.equals(other.offerPrice))
			return false;

		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Price [ id=" + id + ", vendor=" + vendor + ", instrument=" + instrument + ", bidPrice=" + bidPrice
				+ ", offerPrice=" + offerPrice + ", created=" + created + " ]";
	}
	
	public final String toJson() {
		JSONObject json =  new JSONObject();
		json.put("id",getId());
		json.put("instrument",getInstrument());
		json.put("vendor",getVendor());
		json.put("bidPrice",getBidPrice());
		json.put("offerPrice",getOfferPrice());
		json.put("created",getCreated());
		return json.toString(5);
	}

}
