package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Countries {
	private String countryIDs;
	private ArrayList<CountryCopayDetails>  countriesCopayDetails;	
	
	public void setCountryIDs(String countryIDs) {
		this.countryIDs = countryIDs;
	}
	@XmlAttribute
	public String getCountryIDs() {
		return countryIDs;
	}
	public void setCountriesCopayDetails(ArrayList<CountryCopayDetails> countriesCopayDetails) {
		this.countriesCopayDetails = countriesCopayDetails;
	}
	@XmlElement
	public ArrayList<CountryCopayDetails> getCountriesCopayDetails() {
		return countriesCopayDetails;
	}
}
