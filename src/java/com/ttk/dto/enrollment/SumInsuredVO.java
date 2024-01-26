/**
 * @ (#) SumInsuredVO.java Feb 16, 2006
 * Project 	     : TTK HealthCare Services
 * File          : SumInsuredVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 16, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;


public class SumInsuredVO extends BaseVO{
    
    private String strProductPlanSeqID = "";
    private Date dtEffectiveDate = null;
    private Double dblBonus = null;
    private BigDecimal bdBonusAmt = null;
    private BigDecimal bdCumulativeBonusAmt = null;
    private Long lngMemInsuredSeqID = null;
    private Long lngPolicyGroupSeqID = null;
    private Long lngSeqID = null;
    private Long lngMemberSeqID = null;
    private String strSumInsured = "";
    private BigDecimal bdMemSumInsured = null; 
    private String strType = "";
    private String strPlanTypeValue = "";
    private Date dtEffectDate = null;
    
    private String currency;
    private ArrayList alPlanList = new ArrayList();
    private HashMap<String,String> currencyList=new HashMap<String,String>();
    
    /** This method returns the Product Plan Seq ID 
     * @return Returns the strProductPlanSeqID.
     */
    public String getProductPlanSeqID() {
        return strProductPlanSeqID;
    }//end of getProductPlanSeqID()
    
    /** This method sets the Product Plan Seq ID
     * @param strProductPlanSeqID The strProductPlanSeqID to set.
     */
    public void setProductPlanSeqID(String strProductPlanSeqID) {
        this.strProductPlanSeqID = strProductPlanSeqID;
    }//end of setProductPlanSeqID(Long strProductPlanSeqID)
    
    /** This method returns the Bonus Amount
     * @return Returns the bdBonusAmt.
     */
    public BigDecimal getBonusAmt() {
        return bdBonusAmt;
    }//end of getBonusAmt()
    
    /** This method sets the Bonus Amount
     * @param bdBonusAmt The bdBonusAmt to set.
     */
    public void setBonusAmt(BigDecimal bdBonusAmt) {
        this.bdBonusAmt = bdBonusAmt;
    }//end of setBonusAmt(BigDecimal bdBonusAmt)
    
    /** This method returns the Cumulative Bonus Amount
     * @return Returns the bdCumulativeBonusAmt.
     */
    public BigDecimal getCumulativeBonusAmt() {
        return bdCumulativeBonusAmt;
    }//end of getCumulativeBonusAmt()
    
    /** This method sets the Cumulative Bonus Amount
     * @param bdCumulativeBonusAmt The bdCumulativeBonusAmt to set.
     */
    public void setCumulativeBonusAmt(BigDecimal bdCumulativeBonusAmt) {
        this.bdCumulativeBonusAmt = bdCumulativeBonusAmt;
    }//end of setCumulativeBonusAmt(BigDecimal bdCumulativeBonusAmt)
    
    /** This method returns the Effective Date
     * @return Returns the dtEffectiveDate.
     */
    public Date getEffectiveDate() {
        return dtEffectiveDate;
    }//end of getEffectiveDate()
    
    /** This method sets the Effective Date
     * @param dtEffectiveDate The dtEffectiveDate to set.
     */
    public void setEffectiveDate(Date dtEffectiveDate) {
        this.dtEffectiveDate = dtEffectiveDate;
    }//end of setEffectiveDate(Date dtEffectiveDate)
    
    /** This method returns the Bonus
     * @return Returns the dblBonus.
     */
    public Double getBonus() {
        return dblBonus;
    }//end of getBonus()
    
    /** This method sets the Bonus
     * @param dblBonus The dblBonus to set.
     */
    public void setBonus(Double dblBonus) {
        this.dblBonus = dblBonus;
    }//end of setBonus(Double dblBonus)
    
    /** This method returns the Member Insured Seq ID
     * @return Returns the lngMemInsuredSeqID.
     */
    public Long getMemInsuredSeqID() {
        return lngMemInsuredSeqID;
    }//end of getMemInsuredSeqID()
    
    /** This method sets the Member Insured Seq ID
     * @param lngMemInsuredSeqID The lngMemInsuredSeqID to set.
     */
    public void setMemInsuredSeqID(Long lngMemInsuredSeqID) {
        this.lngMemInsuredSeqID = lngMemInsuredSeqID;
    }//end of setMemInsuredSeqID(Long lngMemInsuredSeqID)
    
    /** This method returns the Policy Group Seq ID
     * @return Returns the lngPolicyGroupSeqID.
     */
    public Long getPolicyGroupSeqID() {
        return lngPolicyGroupSeqID;
    }//end of getPolicyGroupSeqID()
    
    /**  This method sets the Policy Group Seq ID
     * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
     */
    public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
        this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
    }//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)
    
    /** This method returns the Member Seq ID
     * @return Returns the lngMemberSeqID.
     */
    public Long getMemberSeqID() {
        return lngMemberSeqID;
    }//end of getMemberSeqID()
    
    /** This method sets the Member Seq ID
     * @param lngMemberSeqID The lngMemberSeqID to set.
     */
    public void setMemberSeqID(Long lngMemberSeqID) {
        this.lngMemberSeqID = lngMemberSeqID;
    }//end of setMemberSeqID(Long lngMemberSeqID)
    
    /** This method returns the Sum Insured
     * @return Returns the strSumInsured.
     */
    public String getSumInsured() {
        return strSumInsured;
    }//end of getSumInsured()
    
    /** This method sets the Sum Insured
     * @param strSumInsured The strSumInsured to set.
     */
    public void setSumInsured(String strSumInsured) {
        this.strSumInsured = strSumInsured;
    }//end of setSumInsured(BigDecimal strSumInsured)
    
    /** This method returns the Type as % or Amt or Any
     * @return Returns the strType.
     */
    public String getType() {
        return strType;
    }//end of getType()
    
    /** This method sets the Type as % or Amt or Any
     * @param strType The strType to set.
     */
    public void setType(String strType) {
        this.strType = strType;
    }//end of setType(String strType)
    
    /** This method returns the ProductPlanSeqID concatenated with SumInsured value
     * @return Returns the strPlanTypeValue.
     */
    public String getPlanTypeValue() {
        return strPlanTypeValue;
    }//end of getPlanTypeValue()
    
    /** This method sets the ProductPlanSeqID concatenated with SumInsured value
     * @param strPlanTypeValue The strPlanTypeValue to set.
     */
    public void setPlanTypeValue(String strPlanTypeValue) {
        this.strPlanTypeValue = strPlanTypeValue;
    }//end of setPlanTypeValue(String strPlanTypeValue)
    
    /** This method returns the Effective Date as a String format 
     * @return Returns the dtEffectDate.
     */
    public String getEffectDate() {
        return TTKCommon.getFormattedDate(dtEffectDate);
    }//end of getEffectDate()
    
    /** This method sets the effective date
     * @param dtEffectDate The dtEffectDate to set.
     */
    public void setEffectDate(Date dtEffectDate) {
        this.dtEffectDate = dtEffectDate;
    }//end of setEffectDate(Date dtEffectDate)
    
    /** This method returns the Plans With Amount
     * @return Returns the alPlanList.
     */
    public ArrayList getPlanList() {
        return alPlanList;
    }//end of getPlanList()
    
    /** This method sets the Plans With Amount
     * @param alPlanList The alPlanList to set.
     */
    public void setPlanList(ArrayList alPlanList) {
        this.alPlanList = alPlanList;
    }//end of setPlanList(ArrayList alPlanList)
    
    /** This method returns the MemSumInsured
     * @return Returns the bdMemSumInsured.
     */
    public BigDecimal getMemSumInsured() {
        return bdMemSumInsured;
    }//end of getMemSumInsured()
    
    /** This method sets the MemSumInsured
     * @param bdMemSumInsured The bdMemSumInsured to set.
     */
    public void setMemSumInsured(BigDecimal bdMemSumInsured) {
        this.bdMemSumInsured = bdMemSumInsured;
    }//end of setMemSumInsured(BigDecimal bdMemSumInsured)
    
    /** This method returns the Seq ID, in Enrollment flow - Policy Seq ID and in Endorsement flow-Endorsement Seq ID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()
    
    /** This method sets the Seq ID,in Enrollment flow - Policy Seq ID and in Endorsement flow-Endorsement Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)
    
    public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public HashMap<String,String> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(HashMap<String,String> currencyList) {
		this.currencyList = currencyList;
	}

	
}//end of SumInsuredVO
