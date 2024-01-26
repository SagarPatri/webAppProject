package com.ttk.dto.displayOfBenefits;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class DisplayBenefitsVO extends BaseVO {

private String benefitName;
private ArrayList<Object> benefitsDetailsList= new ArrayList<Object>();

public String getBenefitName() {
	return benefitName;
}
public void setBenefitName(String benefitName) {
	this.benefitName = benefitName;
}
public ArrayList<Object> getBenefitsDetailsList() {
	return benefitsDetailsList;
}
public void setBenefitsDetailsList(ArrayList<Object> benefitsDetailsList) {
	this.benefitsDetailsList = benefitsDetailsList;
}

public void setBenefitsDetailsVO(int index, BenefitsDetailsVO value){
    this.benefitsDetailsList.add(value);
}
public BenefitsDetailsVO getBenefitsDetailsList(int index){
    return (BenefitsDetailsVO)this.benefitsDetailsList.get(index);
}

}
