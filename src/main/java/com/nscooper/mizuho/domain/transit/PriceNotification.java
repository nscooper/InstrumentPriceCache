package com.nscooper.mizuho.domain.transit;

public class PriceNotification {

	private String vendorName;
	private String instrumentIsin;
	private String instrumentName;
	private Double bidPrice;
	private Double offerPrice;

	public PriceNotification() {
		super();
	}

	/**
	 * @param vendorName
	 * @param instrumentIsin
	 * @param instrumentName
	 * @param bidPrice
	 * @param offerPrice
	 */
	public PriceNotification(String vendorName, String instrumentIsin, String instrumentName, Double bidPrice,
			Double offerPrice) {
		super();
		this.vendorName = vendorName;
		this.instrumentIsin = instrumentIsin;
		this.instrumentName = instrumentName;
		this.bidPrice = bidPrice;
		this.offerPrice = offerPrice;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getInstrumentIsin() {
		return instrumentIsin;
	}

	public void setInstrumentIsin(String instrumentIsin) {
		this.instrumentIsin = instrumentIsin;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
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

}
