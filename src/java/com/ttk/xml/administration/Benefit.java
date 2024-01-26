package com.ttk.xml.administration;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Benefit {

	private String benefitType;
	private ArrayList<Condition> conditions;
	
		
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	@XmlAttribute
	public String getBenefitType() {
		return benefitType;
	}
	public void setConditions(ArrayList<Condition> conditions) {
		this.conditions = conditions;
	}
	@XmlElement(name="Condition")
	public ArrayList<Condition> getConditions() {
		return conditions;
	}
}
