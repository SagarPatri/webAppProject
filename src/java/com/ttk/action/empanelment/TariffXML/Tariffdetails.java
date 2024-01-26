package com.ttk.action.empanelment.TariffXML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tariffdetails {

	private Pricerefdetails pricerefdetails;

	public Pricerefdetails getPricerefdetails() {
		return pricerefdetails;
	}

	@XmlElement
	public void setPricerefdetails(Pricerefdetails pricerefdetails) {
		this.pricerefdetails = pricerefdetails;
	}
	
}
