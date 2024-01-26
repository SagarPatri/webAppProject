package com.ttk.dto.displayOfBenefits;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class ListDisplayBenefitsVO extends BaseVO {

private ArrayList<Object> displayBenefitsList = new ArrayList<Object>();
private String otherRemarks;

public ArrayList<Object> getDisplayBenefitsList() {
	return displayBenefitsList;
}

public void setDisplayBenefitsList(ArrayList<Object> displayBenefitsList) {
	this.displayBenefitsList = displayBenefitsList;
}

public void setDisplayBenefitsList(int index, DisplayBenefitsVO value){
    this.displayBenefitsList.add(value);
}
public DisplayBenefitsVO getDisplayBenefitsList(int index){
    return (DisplayBenefitsVO)this.displayBenefitsList.get(index);
}

public String getOtherRemarks() {
	return otherRemarks;
}

public void setOtherRemarks(String otherRemarks) {
	this.otherRemarks = otherRemarks;
}

}
