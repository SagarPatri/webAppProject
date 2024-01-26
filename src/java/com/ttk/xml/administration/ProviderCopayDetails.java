package com.ttk.xml.administration;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class ProviderCopayDetails implements Serializable{
	private String providerSeqID;
	
	private String copay="";
	private String flatamount="";
	private String currency;
	private String minmax;
	
	@XmlAttribute
	public String getProviderSeqID() {
		return providerSeqID;
	}
	public void setProviderSeqID(String providerSeqID) {
		this.providerSeqID = providerSeqID;
	}
	@XmlAttribute
	public String getCopay() {
		return copay;
	}
	public void setCopay(String copay) {
		this.copay = copay;
	}
	@XmlAttribute
	public String getFlatamount() {
		return flatamount;
	}
	public void setFlatamount(String flatamount) {
		this.flatamount = flatamount;
	}
	@XmlAttribute
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@XmlAttribute
	public String getMinmax() {
		return minmax;
	}
	public void setMinmax(String minmax) {
		this.minmax = minmax;
	}

}
