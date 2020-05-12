package com.nscooper.mizuho.exceptions;

public class VendorNotFoundByName extends Exception {

	private static final long serialVersionUID = 4402395968480047099L;
	
	public VendorNotFoundByName(String string) {
		super(string);
	}
}
