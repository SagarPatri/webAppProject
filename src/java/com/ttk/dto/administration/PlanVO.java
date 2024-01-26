/**
 * @ (#) PlanVO.java Jun 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : PlanVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 26, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class PlanVO extends BaseVO{
	
	private Long lngProdPolicySeqID = null;
	private Long lngProdPlanSeqID = null;
	private String strProdPlanName = "";
	private String strPlanCode = "";
	private Integer intFromAge = null;
	private Integer intToAge = null;
	private BigDecimal bdPlanAmount = null;
	private Long lngProdSeqID =null;
	private Long lngPolicySeqID = null;
	private BigDecimal bdPlanPremium = null;
	
	/** Retrieve the PlanPremium
	 * @return Returns the bdPlanPremium.
	 */
	public BigDecimal getPlanPremium() {
		return bdPlanPremium;
	}//end of getPlanPremium()
	
	/** Sets the PlanPremium
	 * @param bdPlanPremium The bdPlanPremium to set.
	 */
	public void setPlanPremium(BigDecimal bdPlanPremium) {
		this.bdPlanPremium = bdPlanPremium;
	}//end of setPlanPremium(BigDecimal bdPlanPremium)
	
	/** Retrieve the ProdPlanSeqID
	 * @return Returns the lngProdPlanSeqID.
	 */
	public Long getProdPlanSeqID() {
		return lngProdPlanSeqID;
	}//end of getProdPlanSeqID()

	/** Sets the ProdPlanSeqID
	 * @param lngProdPlanSeqID The lngProdPlanSeqID to set.
	 */
	public void setProdPlanSeqID(Long lngProdPlanSeqID) {
		this.lngProdPlanSeqID = lngProdPlanSeqID;
	}//end of setProdPlanSeqID(Long lngProdPlanSeqID)

	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the ProdSeqID
	 * @return Returns the lngProdSeqID.
	 */
	public Long getProdSeqID() {
		return lngProdSeqID;
	}//end of getProdSeqID()

	/** Sets the ProdSeqID
	 * @param lngProdSeqID The lngProdSeqID to set.
	 */
	public void setProdSeqID(Long lngProdSeqID) {
		this.lngProdSeqID = lngProdSeqID;
	}//end of setProdSeqID(Long lngProdSeqID)

	/** Retrieve the PlanAmount
	 * @return Returns the bdPlanAmount.
	 */
	public BigDecimal getPlanAmount() {
		return bdPlanAmount;
	}//end of getPlanAmount()
	
	/** Sets the PlanAmount
	 * @param bdPlanAmount The bdPlanAmount to set.
	 */
	public void setPlanAmount(BigDecimal bdPlanAmount) {
		this.bdPlanAmount = bdPlanAmount;
	}//end of setPlanAmount(BigDecimal bdPlanAmount)
	
	/** This method returns the From Age
	 * @return Returns the intFromAge.
	 */
	public String getFormattedFromAge() {
		return intFromAge==null ? "":intFromAge.toString();
	}//end of getFormattedFromAge()
	
	/** Retrieve the FromAge
	 * @return Returns the intFromAge.
	 */
	public Integer getFromAge() {
		return intFromAge;
	}//end of getFromAge()
	
	/** Sets the FromAge
	 * @param intFromAge The intFromAge to set.
	 */
	public void setFromAge(Integer intFromAge) {
		this.intFromAge = intFromAge;
	}//end of setFromAge(Integer intFromAge)
	
	/** This method returns the To Age
	 * @return Returns the intToAge.
	 */
	public String getFormattedToAge() {
		return intToAge==null ? "":intToAge.toString();
	}//end of getFormattedToAge()
	
	/** Retrieve the ToAge
	 * @return Returns the intToAge.
	 */
	public Integer getToAge() {
		return intToAge;
	}//end of getToAge()
	
	/** Sets the ToAge
	 * @param intToAge The intToAge to set.
	 */
	public void setToAge(Integer intToAge) {
		this.intToAge = intToAge;
	}//end of setToAge(Integer intToAge)
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the PlanCode
	 * @return Returns the strPlanCode.
	 */
	public String getPlanCode() {
		return strPlanCode;
	}//end of getPlanCode()
	
	/** Sets the PlanCode
	 * @param strPlanCode The strPlanCode to set.
	 */
	public void setPlanCode(String strPlanCode) {
		this.strPlanCode = strPlanCode;
	}//end of setPlanCode(String strPlanCode)
	
	/** Retrieve the ProdPlanName
	 * @return Returns the strProdPlanName.
	 */
	public String getProdPlanName() {
		return strProdPlanName;
	}//end of getProdPlanName()
	
	/** Sets the ProdPlanName
	 * @param strProdPlanName The strProdPlanName to set.
	 */
	public void setProdPlanName(String strProdPlanName) {
		this.strProdPlanName = strProdPlanName;
	}//end of setProdPlanName(String strProdPlanName)
	
}//end of PlanVO
