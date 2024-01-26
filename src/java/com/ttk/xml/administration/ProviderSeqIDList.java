package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class ProviderSeqIDList {
	private ArrayList<String>  providerSeqIDs;
	
	public void setProviderSeqIDs(ArrayList<String> providerSeqIDs) {
		this.providerSeqIDs = providerSeqIDs;
	}
	@XmlElement
	public ArrayList<String> getProviderSeqIDs() {
		return providerSeqIDs;
	}

}
