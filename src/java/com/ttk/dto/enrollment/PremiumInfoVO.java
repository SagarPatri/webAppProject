/**
 * @ (#) PremiumInfoVO.java Feb 9, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PremiumInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 9, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.BaseVO;


public class PremiumInfoVO extends BaseVO{
    
    private Long lngMemberSeqID = null;
    private Long lngPolicyGroupSeqID = null;
    private String strName = "";
    private String strPolicySubTypeID = "";
    private String strPolicySubTypeDesc = "";
    private String strMemberPolicyTypeID = "";
    private BigDecimal bdCalcPremium = null;
    private BigDecimal bdTotalSumInsured = null;
    private String strTotSumInsured = "";
    private BigDecimal bdTotalPremium = null;
    private BigDecimal bdFloaterSumInsured = null;
    private BigDecimal bdFloaterPremium = null;
    private String strFloatPremium = "";
    private BigDecimal bdTotalBonus = null;
    private String strPremiumPaid = "";
    private BigDecimal bdCumulativeBonusAmt = null;
    private BigDecimal bdTotalFlySumInsured = null;
    private BigDecimal bdTotalFlyPremium = null;
    private String strTotalFamilyPremium = "";
    private String strImageName = "RatesIcon";
    private String strImageTitle = "Sum Insured";
    private Long lngSeqID = null;
    private Long lngProductSeqID = null;
    private Long lngPolicySeqID = null;
    private String strCancelYN = "";
    private Date dtPolicyStartDate = null;
    
  //addedd as per KOC 1284 Change Request
    private String strSelectregion = null;
	private String strSelectregionYN = "N";
	private BigDecimal grossPremium;
	private BigDecimal proRataPremium;
	private BigDecimal netPremium;
	public void setSelectregionYN(String selectregionYN) {
		strSelectregionYN = selectregionYN;
	}

	public String getSelectregionYN() {
		return strSelectregionYN;
	}
	public void setSelectregion(String selectregion) {
		strSelectregion = selectregion;
	}

	public String getSelectregion() {
		return strSelectregion;
	}
	//addedd as per KOC 1284 Change Request
    /** Retrieve the PolicyStartDate
	 * @return Returns the dtPolicyStartDate.
	 */
	public Date getPolicyStartDate() {
		return dtPolicyStartDate;
	}//end of getPolicyStartDate()

	/** Sets the PolicyStartDate
	 * @param dtPolicyStartDate The dtPolicyStartDate to set.
	 */
	public void setPolicyStartDate(Date dtPolicyStartDate) {
		this.dtPolicyStartDate = dtPolicyStartDate;
	}//end of setPolicyStartDate(Date dtPolicyStartDate)

	/** Retrieve the Cancel YN
	 * @return Returns the strCancelYN.
	 */
	public String getCancelYN() {
		return strCancelYN;
	}//end of getCancelYN()

	/** Sets the Cancel YN
	 * @param strCancelYN The strCancelYN to set.
	 */
	public void setCancelYN(String strCancelYN) {
		this.strCancelYN = strCancelYN;
	}//end of setCancelYN(String strCancelYN)

	/** This method returns the Calculated Premium 
     * @return Returns the bdCalcPremium.
     */
    public BigDecimal getCalcPremium() {
        return bdCalcPremium;
    }//end of getCalcPremium()
    
    /** This method sets the Calculated Premium
     * @param bdCalcPremium The bdCalcPremium to set.
     */
    public void setCalcPremium(BigDecimal bdCalcPremium) {
        this.bdCalcPremium = bdCalcPremium;
    }//end of setCalcPremium(BigDecimal bdCalcPremium)
    
    /** This method returns the Floater Premium
     * @return Returns the bdFloaterPremium.
     */
    public BigDecimal getFloaterPremium() {
        return bdFloaterPremium;
    }//end of getFloaterPremium()
    
    /** This method sets the Floater Premium
     * @param bdFloaterPremium The bdFloaterPremium to set.
     */
    public void setFloaterPremium(BigDecimal bdFloaterPremium) {
        this.bdFloaterPremium = bdFloaterPremium;
    }//end of setFloaterPremium(BigDecimal bdFloaterPremium)
    
    /** This method returns the Floater Sum Insured
     * @return Returns the bdFloaterSumInsured.
     */
    public BigDecimal getFloaterSumInsured() {
        return bdFloaterSumInsured;
    }//end of getFloaterSumInsured()
    
    /** This method sets the Floater Sum Insured
     * @param bdFloaterSumInsured The bdFloaterSumInsured to set.
     */
    public void setFloaterSumInsured(BigDecimal bdFloaterSumInsured) {
        this.bdFloaterSumInsured = bdFloaterSumInsured;
    }//end of setFloaterSumInsured(BigDecimal bdFloaterSumInsured)
    
    /** This method returns the Total Bonus
     * @return Returns the bdTotalBonus.
     */
    public BigDecimal getTotalBonus() {
        return bdTotalBonus;
    }//end of getTotalBonus()
    
    /** This method sets the Total Bonus
     * @param bdTotalBonus The bdTotalBonus to set.
     */
    public void setTotalBonus(BigDecimal bdTotalBonus) {
        this.bdTotalBonus = bdTotalBonus;
    }//end of setTotalBonus(BigDecimal bdTotalBonus)
    
    /** This method returns the Total Premium
     * @return Returns the bdTotalPremium.
     */
    public BigDecimal getTotalPremium() {
        return bdTotalPremium;
    }//end of getTotalPremium()
    
    /** This method sets the Total Premium
     * @param bdTotalPremium The bdTotalPremium to set.
     */
    public void setTotalPremium(BigDecimal bdTotalPremium) {
        this.bdTotalPremium = bdTotalPremium;
    }//end of setTotalPremium(BigDecimal bdTotalPremium)
    
    /** This method returns the Total Sum Insured
     * @return Returns the bdTotalSumInsured.
     */
    public BigDecimal getTotalSumInsured() {
        return bdTotalSumInsured;
    }//end of getTotalSumInsured()
    
    /** This method sets the Total Sum Insured
     * @param bdTotalSumInsured The bdTotalSumInsured to set.
     */
    public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
        this.bdTotalSumInsured = bdTotalSumInsured;
    }//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)
    
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
    
    /** This method returns the Member Policy Type ID
     * @return Returns the strMemberPolicyTypeID.
     */
    public String getMemberPolicyTypeID() {
        return strMemberPolicyTypeID;
    }//end of getMemberPolicyTypeID()
    
    /** This method sets the Member Policy Type ID
     * @param strMemberPolicyTypeID The strMemberPolicyTypeID to set.
     */
    public void setMemberPolicyTypeID(String strMemberPolicyTypeID) {
        this.strMemberPolicyTypeID = strMemberPolicyTypeID;
    }//end of setMemberPolicyTypeID(String strMemberPolicyTypeID)
    
    /** This method returns the Name
     * @return Returns the strName.
     */
    public String getName() {
        return strName;
    }//end of getName()
    
    /** This method sets the Name
     * @param strName The strName to set.
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
    
    /** This method returns the Policy Sub Type Description
     * @return Returns the strPolicySubTypeDesc.
     */
    public String getPolicySubTypeDesc() {
        return strPolicySubTypeDesc;
    }//end of getPolicySubTypeDesc()
    
    /** This method sets the Policy Sub Type Description
     * @param strPolicySubTypeDesc The strPolicySubTypeDesc to set.
     */
    public void setPolicySubTypeDesc(String strPolicySubTypeDesc) {
        this.strPolicySubTypeDesc = strPolicySubTypeDesc;
    }//end of setPolicySubTypeDesc(String strPolicySubTypeDesc)
    
    /** This method returns the Policy SubType ID
     * @return Returns the strPolicySubTypeID.
     */
    public String getPolicySubTypeID() {
        return strPolicySubTypeID;
    }//end of getPolicySubTypeID()
    
    /** This method sets the Policy SubType ID
     * @param strPolicySubTypeID The strPolicySubTypeID to set.
     */
    public void setPolicySubTypeID(String strPolicySubTypeID) {
        this.strPolicySubTypeID = strPolicySubTypeID;
    }//end of setPolicySubTypeID(String strPolicySubTypeID)
    
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
    
    /** This method returns the Total Family Premium
     * @return Returns the bdTotalFlyPremium.
     */
    public BigDecimal getTotalFlyPremium() {
        return bdTotalFlyPremium;
    }//end of getTotalFlyPremium()
    
    /** This method sets the Total Family Premium
     * @param bdTotalFlyPremium The bdTotalFlyPremium to set.
     */
    public void setTotalFlyPremium(BigDecimal bdTotalFlyPremium) {
        this.bdTotalFlyPremium = bdTotalFlyPremium;
    }//end of setTotalFlyPremium(BigDecimal bdTotalFlyPremium)
    
    /** This method returns the Total Family Sum Insured
     * @return Returns the bdTotalFlySumInsured.
     */
    public BigDecimal getTotalFlySumInsured() {
        return bdTotalFlySumInsured;
    }//end of getTotalFlySumInsured()
    
    /** This method sets the Total Family Sum Insured
     * @param bdTotalFlySumInsured The bdTotalFlySumInsured to set.
     */
    public void setTotalFlySumInsured(BigDecimal bdTotalFlySumInsured) {
        this.bdTotalFlySumInsured = bdTotalFlySumInsured;
    }//end of setTotalFlySumInsured(BigDecimal bdTotalFlySumInsured)
    
    /** This method returns the Image Name
     * @return Returns the strImageName.
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /** This method sets the Image Name 
     * @param strImageName The strImageName to set.
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /** This method returns the Image Title
     * @return Returns the strImageTitle.
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /** This method sets the Image Title
     * @param strImageTitle The strImageTitle to set.
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)
    
    /** This method returns the Total Sum Insured
     * @return Returns the strTotSumInsured.
     */
    public String getTotSumInsured() {
        return strTotSumInsured;
    }//end of getTotSumInsured()
    
    /** This method sets the Total Sum Insured
     * @param strTotSumInsured The strTotSumInsured to set.
     */
    public void setTotSumInsured(String strTotSumInsured) {
        this.strTotSumInsured = strTotSumInsured;
    }//end of setTotSumInsured(String strTotSumInsured)
    
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
    
    /** This method returns the PolicySeqID/EndorsementSeqID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()
    
    /** This method sets the PolicySeqID/EndorsementSeqID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)
    
    /** This method returns the Product Seq ID
     * @return Returns the lngProductSeqID.
     */
    public Long getProductSeqID() {
        return lngProductSeqID;
    }//end of getProductSeqID()
    
    /** This method sets the Product Seq ID
     * @param lngProductSeqID The lngProductSeqID to set.
     */
    public void setProductSeqID(Long lngProductSeqID) {
        this.lngProductSeqID = lngProductSeqID;
    }//end of setProductSeqID(Long lngProductSeqID)
    
    /** This method returns the Policy Seq ID
     * @return Returns the lngPolicySeqID.
     */
    public Long getPolicySeqID() {
        return lngPolicySeqID;
    }//end of getPolicySeqID()
    
    /** This method sets the Policy Seq ID
     * @param lngPolicySeqID The lngPolicySeqID to set.
     */
    public void setPolicySeqID(Long lngPolicySeqID) {
        this.lngPolicySeqID = lngPolicySeqID;
    }//end of setPolicySeqID(Long lngPolicySeqID)
    
    /** This method returns the Float Premium
     * @return Returns the strFloatPremium.
     */
    public String getFloatPremium() {
        return strFloatPremium;
    }//end of getFloatPremium()
    
    /** This method sets the Float Premium
     * @param strFloatPremium The strFloatPremium to set.
     */
    public void setFloatPremium(String strFloatPremium) {
        this.strFloatPremium = strFloatPremium;
    }//end of setFloatPremium(String strFloatPremium)
    
    /** This method returns the Premium Paid
     * @return Returns the strPremiumPaid.
     */
    public String getPremiumPaid() {
        return strPremiumPaid;
    }//end of getPremiumPaid()
    
    /** This method sets the Premium Paid
     * @param strPremiumPaid The strPremiumPaid to set.
     */
    public void setPremiumPaid(String strPremiumPaid) {
        this.strPremiumPaid = strPremiumPaid;
    }//end of setPremiumPaid(String strPremiumPaid)
    
    /** This method returns the Total Family Premium
     * @return Returns the strTotalFamilyPremium.
     */
    public String getTotalFamilyPremium() {
        return strTotalFamilyPremium;
    }//end of getTotalFamilyPremium()
    
    /** This method sets the Total Family Premium
     * @param strTotalFamilyPremium The strTotalFamilyPremium to set.
     */
    public void setTotalFamilyPremium(String strTotalFamilyPremium) {
        this.strTotalFamilyPremium = strTotalFamilyPremium;
    }//end of setTotalFamilyPremium(String strTotalFamilyPremium)

	public BigDecimal getGrossPremium() {
		return grossPremium;
	}

	public void setGrossPremium(BigDecimal grossPremium) {
		this.grossPremium = grossPremium;
	}

	public BigDecimal getProRataPremium() {
		return proRataPremium;
	}

	public void setProRataPremium(BigDecimal proRataPremium) {
		this.proRataPremium = proRataPremium;
	}

	public BigDecimal getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(BigDecimal netPremium) {
		this.netPremium = netPremium;
	}
}//end of PremiumInfoVO
