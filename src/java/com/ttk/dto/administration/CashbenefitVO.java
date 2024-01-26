/**
 * @ (#) CashbenefitVO.java Jun 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : CashbenefitVO.java
 * Author        : 
 * Company       : 
 * Date Created  : 
 *
 * @author       :  
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
public class CashbenefitVO extends BaseVO{
	
	//private Long lngProdPolicySeqID = null;
	//private Long lngProdPlanSeqID = null;
	private Long lnginsCashBenefitSeqID = null; 
	private Long lnginsConvBenfSeqID = null; 
	private String strProdPlanName = "";
	private Long lngProdSeqID =null;
	private String strPlanCode = "";
	private Integer intFromAge = null;
	private Integer intToAge = null;
	private Long lngGroupRegSeqID = null;
	private String strSchemeID = "";
	//private BigDecimal bdPlanAmount = null;	
	private Long lngPolicySeqID = null;
	private BigDecimal bdPlanPremium = null;
	private BigDecimal bdroom = null;
	private BigDecimal bdaccident = null;
	private BigDecimal bdicu = null;
	private BigDecimal bdconveyance = null;
	//private BigDecimal bdor = null;
	private Integer introomDays = null;
	private Integer intaccidentDays = null;
	private Integer inticuDays = null;
	private Integer intconveyanceDays = null;
	private Integer intpolicyDays = null;
	private Integer introomPercentage = null;
	private Integer intaccidentPercentage = null;
	private Integer inticuPercentage = null;
	private Integer intconveyancePercentage = null;
	private String strProdPlanConvName = "";
	private String strPlanConvCode = "";
	private String strconvBenefit = "HCB"; 
	private String strconv = "CONV_BENEF";
	
	//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
	private Long lnginsCriticalBenefitSeqID = null; 
	private String strcriticalBenefit = "CRITICAL_BENEFIT";
	private BigDecimal bdCriticalBenefitAmount = null;
    private String strCriticalTypeID = "";
    private Integer strNoOfTimes = null;
    private Integer strSurvivalPeriod = null;
    private Integer strWaitingPeriod = null;
    private BigDecimal bdSumInsuredPer  = null;  
    private String strShowSurvivalPeriod = "";
	
	/**
	 * @param strPlanConvCode the strPlanConvCode to set
	 */
	public void setPlanConvCode(String strPlanConvCode) {
		this.strPlanConvCode = strPlanConvCode;
	}

	/**
	 * @return the strPlanConvCode
	 */
	public String getPlanConvCode() {
		return strPlanConvCode;
	}

	/**
	 * @param strProdPlanConvName the strProdPlanConvName to set
	 */
	public void setProdPlanConvName(String strProdPlanConvName) {
		this.strProdPlanConvName = strProdPlanConvName;
	}

	/**
	 * @return the strProdPlanConvName
	 */
	public String getProdPlanConvName() {
		return strProdPlanConvName;
	}

	/**
	 * @param lnginsConvBenfSeqID the lnginsConvBenfSeqID to set
	 */
	public void setinsConvBenfSeqID(Long lnginsConvBenfSeqID) {
		this.lnginsConvBenfSeqID = lnginsConvBenfSeqID;
	}

	/**
	 * @return the lnginsConvBenfSeqID
	 */
	public Long getinsConvBenfSeqID() {
		return lnginsConvBenfSeqID;
	}

	/**
	 * @param strconv the strconv to set
	 */
	public void setconv(String strconv) {
		this.strconv = strconv;
	}

	/**
	 * @return the strconv
	 */
	public String getconv() {
		return strconv;
	}

	/**
	 * @param lngconvBenefit the lngconvBenefit to set
	 */
	public void setConvBenefit(String strconvBenefit) {
		this.strconvBenefit = strconvBenefit;
	}

	/**
	 * @return the lngconvBenefit
	 */
	public String getConvBenefit() {
		return strconvBenefit;
	}

	/**
	 * @param conveyancePercentage the conveyancePercentage to set
	 */
	public void setConveyancePercentage(Integer intconveyancePercentage) {
		this.intconveyancePercentage = intconveyancePercentage;
	}

	/**
	 * @return the conveyancePercentage
	 * 
	 * */
	
	public Integer getConveyancePercentage() {
		return intconveyancePercentage;
	} 

	/**
	 * @param icuPercentage the icuPercentage to set
	 */
	public void setIcuPercentage(Integer inticuPercentage) {
		this.inticuPercentage = inticuPercentage;
	}

	/**
	 * @return the icuPercentage
	 */
	public Integer getIcuPercentage() {
		return inticuPercentage;
	}

	/**
	 * @param accidentPercentage the accidentPercentage to set
	 */
	public void setAccidentPercentage(Integer intaccidentPercentage) {
		this.intaccidentPercentage = intaccidentPercentage;
	}

	/**
	 * @return the accidentPercentage
	 */
	public Integer getAccidentPercentage() {
		return intaccidentPercentage;
	}

	/**
	 * @param roomPercentage the roomPercentage to set
	 */
	public void setRoomPercentage(Integer introomPercentage) {
		this.introomPercentage = introomPercentage;
	}

	/**
	 * @return the roomPercentage
	 */
	public Integer getRoomPercentage() {
		return introomPercentage;
	}

	/**
	 * @param policyDays the policyDays to set
	 */
	public void setPolicyDays(Integer intpolicyDays) {
		this.intpolicyDays = intpolicyDays;
	}

	/**
	 * @return the policyDays
	*/
	public Integer getPolicyDays() {
		return intpolicyDays;
	} 

	/**
	 * @param conveanceDays the conveanceDays to set
	 */
	public void setConveyanceDays(Integer intconveyanceDays) {
		this.intconveyanceDays = intconveyanceDays;
	} 

	/**
	 * @return the conveanceDays
	 */
	public Integer getConveyanceDays() {
		return intconveyanceDays;
	} 

	/**
	 * @param icuDays the icuDays to set
	 */
	public void setIcuDays(Integer inticuDays) {
		this.inticuDays = inticuDays;
	}

	/**
	 * @return the icuDays
	 */ 
	public Integer getIcuDays() {
		return inticuDays;
	} 

	/**
	 * @param accidentDays the accidentDays to set
	 */
	public void setAccidentDays(Integer intaccidentDays) {
		this.intaccidentDays = intaccidentDays;
	}

	/**
	 * @return the accidentDays
	 */
	public Integer getAccidentDays() {
		return intaccidentDays;
	}

	/**
	 * @param roomDays the roomDays to set
	 */
	public void setRoomDays(Integer introomDays) {
		this.introomDays = introomDays;
	}

	/**
	 * @return the roomDays
	 */
	public Integer getRoomDays() {
		return introomDays;
	}

	/**
	 * @param or the or to set
	
	public void setOr(BigDecimal bdor) {
		this.bdor = bdor;
	} */

	/**
	 * @return the or
	
	public BigDecimal getOr() {
		return bdor;
	} */

	/**
	 * @param conveyance the conveyance to set
	 */
	public void setConveyance(BigDecimal bdconveyance) {
		this.bdconveyance = bdconveyance;
	}

	/**
	 * @return the conveyance
	 */
	public BigDecimal getConveyance() {
		return bdconveyance;
	}

	/**
	 * @param icu the icu to set
	 */
	public void setIcu(BigDecimal bdicu) {
		this.bdicu = bdicu;
	}

	/**
	 * @return the icu
	 */
	public BigDecimal getIcu() {
		return bdicu;
	}
	
	/**
	 * @param accident the accident to set
	 */
	public void setAccident(BigDecimal bdaccident) {
		this.bdaccident = bdaccident;
	}

	/**
	 * @return the accident
	 */
	public BigDecimal getAccident() {
		return bdaccident;
	}
	
	/**
	 * @param room the room to set
	 */
	public void setRoom(BigDecimal bdroom) {
		this.bdroom = bdroom;
	}

	/**
	 * @return the room
	 */
	public BigDecimal getRoom() {
		return bdroom;
	}
	
	//koc for 1270 hospital cash benefit

	
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
	
	public Long getProdPlanSeqID() {
		return lngProdPlanSeqID;
	}//end of getProdPlanSeqID()
    */
	/** Sets the ProdPlanSeqID
	 * @param lngProdPlanSeqID The lngProdPlanSeqID to set.
	 
	public void setProdPlanSeqID(Long lngProdPlanSeqID) {
		this.lngProdPlanSeqID = lngProdPlanSeqID;
	}//end of setProdPlanSeqID(Long lngProdPlanSeqID)
	*/
	/** Retrieve the CashBenefitSeqID
	 * @return Returns the lngCashBenefitSeqID.
	 */
	public Long getinsCashBenefitSeqID() {
		return lnginsCashBenefitSeqID;
	}//end of getinsCashBenefitSeqID()

	/** Sets the insCashBenefitSeqID
	 * @param lnginsCashBenefitSeqID The lnginsCashBenefitSeqID to set.
	 */
	public void setinsCashBenefitSeqID(Long lnginsCashBenefitSeqID) {
		this.lnginsCashBenefitSeqID = lnginsCashBenefitSeqID;
	}//end of setinsCashBenefitSeqID(Long lnginsCashBenefitSeqID)


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
	
	/** Retrieve the GroupRegSeqID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}//end of getGroupRegSeqID()
	
	/** Sets the GroupRegSeqID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}//end of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/** Retrieve the SchemeID
	 * @return Returns the strSchemeID.
	 */
	public String getSchemeID() {
		return strSchemeID;
	}//end of getSchemeID()
	
	/** Sets the SchemeID
	 * @param strSchemeID The strSchemeID to set.
	 */
	public void setSchemeID(String strSchemeID) {
		this.strSchemeID = strSchemeID;
	}//end of setSchemeID(String strSchemeID)

	/** Retrieve the PlanAmount
	 * @return Returns the bdPlanAmount.
	 
	public BigDecimal getPlanAmount() {
		return bdPlanAmount;
	}//end of getPlanAmount()
	
	/** Sets the PlanAmount
	 * @param bdPlanAmount The bdPlanAmount to set.
	 
	public void setPlanAmount(BigDecimal bdPlanAmount) {
		this.bdPlanAmount = bdPlanAmount;
	}//end of setPlanAmount(BigDecimal bdPlanAmount)*/
	
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
	
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	 */
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	*/
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
	//KOC FOR PRAVEEN CRITICAL BENEFIT
	/**
	 * @param lnginsCriticalBenefitSeqID the lnginsCriticalBenefitSeqID to set
	 */
	public void setinsCriticalBenefitSeqID(Long lnginsCriticalBenefitSeqID) {
		this.lnginsCriticalBenefitSeqID = lnginsCriticalBenefitSeqID;
	}

	/**
	 * @return the lnginsCriticalBenefitSeqID
	 */
	public Long getinsCriticalBenefitSeqID() {
		return lnginsCriticalBenefitSeqID;
	}
	//KOC FOR PRAVEEN CRITICAL BENEFIT
	/**
	 * @param strcriticalBenefit the strcriticalBenefit to set
	 */
	public void setcriticalBenefit(String strcriticalBenefit) {
		this.strcriticalBenefit = strcriticalBenefit;
	}

	/**
	 * @return the strcriticalBenefit
	 */
	public String getcriticalBenefit() {
		return strcriticalBenefit;
	}

	public void setCriticalBenefitAmount(BigDecimal bdCriticalBenefitAmount) {
		this.bdCriticalBenefitAmount = bdCriticalBenefitAmount;
	}

	public BigDecimal getCriticalBenefitAmount() {
		return bdCriticalBenefitAmount;
	}

	public void setCriticalTypeID(String strCriticalTypeID) {
		this.strCriticalTypeID = strCriticalTypeID;
	}

	public String getCriticalTypeID() {
		return strCriticalTypeID;
	}

	public void setNoOfTimes(Integer strNoOfTimes) {
		this.strNoOfTimes = strNoOfTimes;
	}

	public Integer getNoOfTimes() {
		return strNoOfTimes;
	}

	public void setSurvivalPeriod(Integer strSurvivalPeriod) {
		this.strSurvivalPeriod = strSurvivalPeriod;
	}

	public Integer getSurvivalPeriod() {
		return strSurvivalPeriod;
	}

	public void setWaitingPeriod(Integer strWaitingPeriod) {
		this.strWaitingPeriod = strWaitingPeriod;
	}

	public Integer getWaitingPeriod() {
		return strWaitingPeriod;
	}

	public void setSumInsuredPer(BigDecimal bdSumInsuredPer) {
		this.bdSumInsuredPer = bdSumInsuredPer;
	}

	public BigDecimal getSumInsuredPer() {
		return bdSumInsuredPer;
	}

	public void setShowSurvivalPeriod(String strShowSurvivalPeriod) {
		this.strShowSurvivalPeriod = strShowSurvivalPeriod;
	}

	public String getShowSurvivalPeriod() {
		return strShowSurvivalPeriod;
	}
}//end of PlanVO
