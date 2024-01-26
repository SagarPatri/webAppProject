package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Emirates {
	private String emirateIDs;
	private String confStatus;
	private ArrayList<EmirateCopayDetails>  emirateCopayDetails;	
	
	
	@XmlAttribute
	public String getEmirateIDs() {
		return emirateIDs;
	}
	public void setEmirateIDs(String emirateIDs) {
		this.emirateIDs = emirateIDs;
	}
	@XmlElement
	public ArrayList<EmirateCopayDetails> getEmirateCopayDetails() {
		return emirateCopayDetails;
	}
	public void setEmirateCopayDetails(
			ArrayList<EmirateCopayDetails> emirateCopayDetails) {
		this.emirateCopayDetails = emirateCopayDetails;
	}
	@XmlAttribute
	public String getConfStatus() {
		return confStatus;
	}
	public void setConfStatus(String confStatus) {
		this.confStatus = confStatus;
	}
	
}
