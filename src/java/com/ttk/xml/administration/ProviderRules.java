package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ProviderRules")
public class ProviderRules {

	private ArrayList<Benefit> benefits;
	
	
	public void setBenefits(ArrayList<Benefit> benefits) {
		this.benefits = benefits;
	}
	@XmlElement(name="Benefit")
	public ArrayList<Benefit> getBenefits() {
		return benefits;
	}
	
}
