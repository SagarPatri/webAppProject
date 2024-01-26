package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Providers {
	
	private ArrayList<ProviderCopayDetails>  providersCopayDetails;

	private String providerConfStatus;
	private ProviderSeqIDList providerSeqIDList;
	
	
	
	public void setProviderConfStatus(String providerConfStatus) {
		this.providerConfStatus = providerConfStatus;
	}
	
	@XmlAttribute
	public String getProviderConfStatus() {
		return providerConfStatus;
	}
	private String providerSeqIDs;
	public void setProviderSeqIDs(String providerSeqIDs) {
		this.providerSeqIDs = providerSeqIDs;
	}
	@XmlAttribute
	public String getProviderSeqIDs() {
		return providerSeqIDs;
	}
	
	public void setProvidersCopayDetails(
			ArrayList<ProviderCopayDetails> providersCopayDetails) {
		this.providersCopayDetails = providersCopayDetails;
	}
	@XmlElement
	public ArrayList<ProviderCopayDetails> getProvidersCopayDetails() {
		return providersCopayDetails;
	}
	
	public void setProviderSeqIDList(ProviderSeqIDList providerSeqIDList) {
		this.providerSeqIDList = providerSeqIDList;
	}
	@XmlElement
	public ProviderSeqIDList getProviderSeqIDList() {
		return providerSeqIDList;
	}
}
