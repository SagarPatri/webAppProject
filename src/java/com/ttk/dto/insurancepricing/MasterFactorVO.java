/**
 * @ (#) AddressVO.java Sep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : AddressVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 19, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.insurancepricing;
// import java.util.Date;



import java.util.Date;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any address. 
 * The corresponding DB Table is TPA_HOSP_ADDRESS.
 */
public class MasterFactorVO extends BaseVO{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  declaration of the fields as private

	private String factId=null;
    private String benefitRule="";
    private String benefitDesc="";
    private String gender="";
    private String description="";
    private String factor="";
    private String averageAge="";
    private String rowColor="";
	private Date avgExpStartDate=null;   
	private Date avgExpendDate=null;   

	public String getRowColor() {
		return rowColor;
	}
	public void setRowColor(String rowColor) {
		this.rowColor = rowColor;
	}
	public String getFactId() {
		return factId;
	}
	public void setFactId(String factId) {
		this.factId = factId;
	}
	public String getBenefitRule() {
		return benefitRule;
	}
	public void setBenefitRule(String benefitRule) {
		this.benefitRule = benefitRule;
	}
	public String getBenefitDesc() {
		return benefitDesc;
	}
	public void setBenefitDesc(String benefitDesc) {
		this.benefitDesc = benefitDesc;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public String getAverageAge() {
		return averageAge;
	}
	public void setAverageAge(String averageAge) {
		this.averageAge = averageAge;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getAvgExpStartDate() {
		return avgExpStartDate;
	}
	public void setAvgExpStartDate(Date avgExpStartDate) {
		this.avgExpStartDate = avgExpStartDate;
	}
	public Date getAvgExpendDate() {
		return avgExpendDate;
	}
	public void setAvgExpendDate(Date avgExpendDate) {
		this.avgExpendDate = avgExpendDate;
	}

	
}//end of AddressVO.java
