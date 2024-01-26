/**
 * @ (#) DependentDetailVO.java Jan 24, 2008
 * Project 	     : TTK HealthCare Services
 * File          : DependentDetailVO.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Jan 24, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :  Ramakrishna K M
 * Modified date :  Jan 24, 2008
 * Reason        :  Modified VO methods
 */

package com.ttk.dto.onlineforms;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class DependentDetailVO extends BaseVO {

	private Long lngMemberSeqID = null;
    private Long lngPolicySeqID = null;
    private Long lngPolicyGroupSeqID = null;
    private String strName = "";
    private Date dtDateOfBirth = null;
    private Integer intAge=null;
    private String strGenderTypeID = "";
    private String strGenderDescription="";
    private String strRelationTypeID = "";
    private String strRelationDesc = "";
    private String strMemberTypeID= "";
    private String strMemberType= "";
    private String strEnrollmentID = "";
    private Date dtInceptionDate = null;
    private BigDecimal bdTotalSumInsured = null;	//mem_tot_sum_insured
    private String strAllowAddYN ="";
    private String strAllowDeleteYN="";
    private String strLoginType = "";
    private String strImageName = "RatesIcon";
    private String strImageTitle = "Additional SumInsured";
    private String strDeleteImageName = "Blank";
    private String strDeleteImageTitle = "";
    private String strAllowAddSumYN = "";
    private String strFamilySumIconYN = ""; //show_family_sum_icon_yn
    private String strGenderYN = "";//'Y'-if gender defines in Database and 'N'-Gender has not defined in database
    private String strAssocGenderRel = "";
    private String strAbbrCode = "";
    private String strEnrollmentNbr = "";
    private String strCancelYN = "";
    private String strExpiredYN = "";
    private String strPreClaimExistYN = "";
    private BigDecimal bdFloaterSumInsured = null;	//floater_sum_insured
    private String strFloaterSumStatus = "";
    private String strPolicyStatusTypeID = "";//POLICY_STATUS_GENERAL_TYPE_ID
    private Long lngProdPlanSeqID = null;//PROD_PLAN_SEQ_ID
    private BigDecimal bdAddPremium = null;//ADDITIONAL_PREMIUM
    private BigDecimal bdPlanAmt=null;
    private String strWithinLimitYN = "";//within_limit_yn
    private String strLoginWindowPeriodAlert="";
	private String strDeclaration="";//added for IBM
	private Date dtDateOfMarriage=null;//added for IBM for Age CR

	// Added for Oracle alert Message on Deletion of Records
	private String strGroupId = "";

	public String getGroupId() {
		return strGroupId;
	}
	public void setGroupId(String strGroupId) {
		this.strGroupId = strGroupId;
	}
	//Ended
	//added for IBM for Age CR
	public Date getDateOfMarriage() {
			return dtDateOfMarriage;
	}
	public void setDateOfMarriage(Date dtDateOfMarriage) {
			this.dtDateOfMarriage = dtDateOfMarriage;
	}

	public String getDeclaration()
	{
	 return strDeclaration;
	 }
	public void setDeclaration(String strDeclaration)
	{
	this.strDeclaration = strDeclaration;
	 }

    //Changes as per KOC 1159 and 1160
    private String combinationMembersLimit="";
        /**
	 * @param combinationMembersLimit the combinationMembersLimit to set
	 */
	public void setCombinationMembersLimit(String combinationMembersLimit) {
		this.combinationMembersLimit = combinationMembersLimit;
	}

	/**
	 * @return the combinationMembersLimit
	 */
	public String getCombinationMembersLimit() {
		return combinationMembersLimit;
	}

	//Changes as per KOC 1159 and 1160

	//added by Praveen for not showing dependent details in online FOR KOC-1216
	private String strPolicy_Stop_YN="";

	public String getPolicy_Stop_YN()
	{
		return strPolicy_Stop_YN;
	}
	public void setPolicy_Stop_YN(String strPolicyStopYN) {
		strPolicy_Stop_YN = strPolicyStopYN;
	}

	//added by Praveen for not showing dependent details in online
	// Change added for CR KOC1227C
    private String strPremiumDeductionOption=""; //strPremiumDeductionOption

    /** This method returns the Premium Deduction Option Selected
     * @return Returns the strPremiumDeductionOption.
     */
    public String getPremiumDeductionOption() {
		return strPremiumDeductionOption;
	}

    /** This method sets the Premium Deduction Option
     * @param strPremiumDeductionOption The Premium Deduction Option selected.
     */
	public void setPremiumDeductionOption(String strPremiumDeductionOption) {
		this.strPremiumDeductionOption = strPremiumDeductionOption;
	}


    /** Retrieve the LoginWindowPeriodAlert
	 * @return the strLoginWindowPeriodAlert
	 */
	public String getLoginWindowPeriodAlert() {
		return strLoginWindowPeriodAlert;
	}//end of getLoginWindowPeriodAlert()

	/** Sets the LoginWindowPeriodAlert
	 * @param strLoginWindowPeriodAlert the strLoginWindowPeriodAlert to set
	 */
	public void setLoginWindowPeriodAlert(String strLoginWindowPeriodAlert) {
		this.strLoginWindowPeriodAlert = strLoginWindowPeriodAlert;
	}//end of setLoginWindowPeriodAlert(String strLoginWindowPeriodAlert)

	/** Retrieve the WithinLimitYN
	 * @return Returns the strWithinLimitYN.
	 */
	public String getWithinLimitYN() {
		return strWithinLimitYN;
	}//end of getWithinLimitYN()

	/** Sets the WithinLimitYN
	 * @param strWithinLimitYN The strWithinLimitYN to set.
	 */
	public void setWithinLimitYN(String strWithinLimitYN) {
		this.strWithinLimitYN = strWithinLimitYN;
	}//end of setWithinLimitYN(String strWithinLimitYN)

	/** Retrieve the Plan Amt
	 * @return Returns the bdPlanAmt.
	 */
	public BigDecimal getPlanAmt() {
		return bdPlanAmt;
	}//end of getPlanAmt()

	/** Sets the Plan Amount
	 * @param bdAddPremium The bdPlanAmt to set.
	 */
	public void setPlanAmt(BigDecimal bdPlanAmt) {
		this.bdPlanAmt = bdPlanAmt;
	}//end of setPlanAmt(BigDecimal bdPlanAmt)
    /** Retrieve the Additional Premium
	 * @return Returns the bdAddPremium.
	 */
	public BigDecimal getAddPremium() {
		return bdAddPremium;
	}//end of getAddPremium()

	/** Sets the Additional Premium
	 * @param bdAddPremium The bdAddPremium to set.
	 */
	public void setAddPremium(BigDecimal bdAddPremium) {
		this.bdAddPremium = bdAddPremium;
	}//end of setAddPremium(BigDecimal bdAddPremium)

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

	/** Retrieve the FloaterSumInsured
	 * @return Returns the bdFloaterSumInsured.
	 */
	public BigDecimal getFloaterSumInsured() {
		return bdFloaterSumInsured;
	}//end of getFloaterSumInsured()

	/** Sets the FloaterSumInsured
	 * @param bdFloaterSumInsured The bdFloaterSumInsured to set.
	 */
	public void setFloaterSumInsured(BigDecimal bdFloaterSumInsured) {
		this.bdFloaterSumInsured = bdFloaterSumInsured;
	}//end of setFloaterSumInsured(BigDecimal bdFloaterSumInsured)

	/** Retrieve the FloaterSumStatus
	 * @return Returns the strFloaterSumStatus.
	 */
	public String getFloaterSumStatus() {
		return strFloaterSumStatus;
	}//end of getFloaterSumStatus()

	/** Sets the FloaterSumStatus
	 * @param strFloaterSumStatus The strFloaterSumStatus to set.
	 */
	public void setFloaterSumStatus(String strFloaterSumStatus) {
		this.strFloaterSumStatus = strFloaterSumStatus;
	}//end of setFloaterSumStatus(String strFloaterSumStatus)

	/** Retrieve the PreClaimExistYN
	 * @return Returns the strPreClaimExistYN.
	 */
	public String getPreClaimExistYN() {
		return strPreClaimExistYN;
	}//end of getPreClaimExistYN()

	/** Sets the PreClaimExistYN
	 * @param strPreClaimExistYN The strPreClaimExistYN to set.
	 */
	public void setPreClaimExistYN(String strPreClaimExistYN) {
		this.strPreClaimExistYN = strPreClaimExistYN;
	}//end of setPreClaimExistYN(String strPreClaimExistYN)

	/** Retrieve the DeleteImageName
	 * @return Returns the strDeleteImageName.
	 */
	public String getDeleteImageName() {
		return strDeleteImageName;
	}//end of getDeleteImageName()

	/** Sets the DeleteImageName
	 * @param strDeleteImageName The strDeleteImageName to set.
	 */
	public void setDeleteImageName(String strDeleteImageName) {
		this.strDeleteImageName = strDeleteImageName;
	}//end of setDeleteImageName(String strDeleteImageName)

	/** Retrieve the DeleteImageTitle
	 * @return Returns the strDeleteImageTitle.
	 */
	public String getDeleteImageTitle() {
		return strDeleteImageTitle;
	}//end of getDeleteImageTitle()

	/** Sets the DeleteImageTitle
	 * @param strDeleteImageTitle The strDeleteImageTitle to set.
	 */
	public void setDeleteImageTitle(String strDeleteImageTitle) {
		this.strDeleteImageTitle = strDeleteImageTitle;
	}//end of setDeleteImageTitle(String strDeleteImageTitle)

	/** Retrieve the CancelYN
	 * @return Returns the strCancelYN.
	 */
	public String getCancelYN() {
		return strCancelYN;
	}//end of getCancelYN()

	/** Sets the CancelYN
	 * @param strCancelYN The strCancelYN to set.
	 */
	public void setCancelYN(String strCancelYN) {
		this.strCancelYN = strCancelYN;
	}//end of setCancelYN(String strCancelYN)

	/** Retrieve the ExpiredYN
	 * @return Returns the strCancelYN.
	 */
	public String getExpiredYN() {
		return strExpiredYN;
	}//end of getExpiredYN()

	/** Sets the ExpiredYN
	 * @param strCancelYN The strExpiredYN to set.
	 */
	public void setExpiredYN(String strExpiredYN) {
		this.strExpiredYN = strExpiredYN;
	}//end of setExpiredYN(String strExpiredYN)

	/** Retrieve the EnrollmentNbr
	 * @return Returns the strEnrollmentNbr.
	 */
	public String getEnrollmentNbr() {
		return strEnrollmentNbr;
	}//end of getEnrollmentNbr()

	/** Sets the EnrollmentNbr
	 * @param strEnrollmentNbr The strEnrollmentNbr to set.
	 */
	public void setEnrollmentNbr(String strEnrollmentNbr) {
		this.strEnrollmentNbr = strEnrollmentNbr;
	}//end of setEnrollmentNbr(String strEnrollmentNbr)

	/** Retrieve the Abbrevation Code
	 * @return Returns the strAbbrCode.
	 */
	public String getAbbrCode() {
		return strAbbrCode;
	}//end of getAbbrCode()

	/** Sets the Abbrevation Code
	 * @param strAbbrCode The strAbbrCode to set.
	 */
	public void setAbbrCode(String strAbbrCode) {
		this.strAbbrCode = strAbbrCode;
	}//end of setAbbrCode(String strAbbrCode)

    /** Retrieve the AssocGenderRel
	 * @return Returns the strAssocGenderRel.
	 */
	public String getAssocGenderRel() {
		return strAssocGenderRel;
	}//end of getAssocGenderRel()

	/** Sets the AssocGenderRel
	 * @param strAssocGenderRel The strAssocGenderRel to set.
	 */
	public void setAssocGenderRel(String strAssocGenderRel) {
		this.strAssocGenderRel = strAssocGenderRel;
	}//end of setAssocGenderRel(String strAssocGenderRel)

	/** Retrieve the Gender YN
	 * @return Returns the strGenderYN.
	 */
	public String getGenderYN() {
		return strGenderYN;
	}//end of getGenderYN()

	/** Sets the Gender YN
	 * @param strGenderYN The strGenderYN to set.
	 */
	public void setGenderYN(String strGenderYN) {
		this.strGenderYN = strGenderYN;
	}//end of setGenderYN(String strGenderYN)

    /** Retrieve the FamilySumIconYN
	 * @return Returns the strFamilySumIconYN.
	 */
	public String getFamilySumIconYN() {
		return strFamilySumIconYN;
	}//end of getFamilySumIconYN()

	/** Sets the FamilySumIconYN
	 * @param strFamilySumIconYN The strFamilySumIconYN to set.
	 */
	public void setFamilySumIconYN(String strFamilySumIconYN) {
		this.strFamilySumIconYN = strFamilySumIconYN;
	}//end of setFamilySumIconYN(String strFamilySumIconYN)

	/** Retrieve the AllowAddSumYN
	 * @return Returns the strAllowAddSumYN.
	 */
	public String getAllowAddSumYN() {
		return strAllowAddSumYN;
	}//end of getAllowAddSumYN()

	/** Sets the AllowAddSumYN
	 * @param strAllowAddSumYN The strAllowAddSumYN to set.
	 */
	public void setAllowAddSumYN(String strAllowAddSumYN) {
		this.strAllowAddSumYN = strAllowAddSumYN;
	}//end of setAllowAddSumYN(String strAllowAddSumYN)

    /** Retrieve the LoginType
	 * @return Returns the strLoginType.
	 */
	public String getLoginType() {
		return strLoginType;
	}//end of getLoginType()

	/** Sets the LoginType
	 * @param strLoginType The strLoginType to set.
	 */
	public void setLoginType(String strLoginType) {
		this.strLoginType = strLoginType;
	}//end of setLoginType(String strLoginType)

	/** Retrieve the AllowAddYN
	 * @return Returns the strAllowAddYN.
	 */
	public String getAllowAddYN() {
		return strAllowAddYN;
	}//end of getAllowAddYN()

	/** Sets the AllowAddYN
	 * @param strAllowAddYN The strAllowAddYN to set.
	 */
	public void setAllowAddYN(String strAllowAddYN) {
		this.strAllowAddYN = strAllowAddYN;
	}//end of setAllowAddYN(String strAllowAddYN)

	/** Retrieve the AllowDeleteYN
	 * @return Returns the strAllowDeleteYN.
	 */
	public String getAllowDeleteYN() {
		return strAllowDeleteYN;
	}//end of getAllowDeleteYN()

	/** Sets the AllowDeleteYN
	 * @param strAllowDeleteYN The strAllowDeleteYN to set.
	 */
	public void setAllowDeleteYN(String strAllowDeleteYN) {
		this.strAllowDeleteYN = strAllowDeleteYN;
	}//end of setAllowdeleteYN(String strAllowDeleteYN)

	/**
	 * This method returns the PolicyGroup Sequence ID
	 * @return Returns the lngPolicyGroupSeqID.
	 */
	public Long getPolicyGroupSeqID() {
		return lngPolicyGroupSeqID;
	}//end of getPolicyGroupSeqID()

	/**
	 * This method sets the PolicyGroup Sequence ID
	 * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
	 */
	public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
		this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
	}//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)

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

    /** This method returns the Member Sequence ID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()

	/** This method sets the Member Sequence ID
	 * @param lngMemberSeqID The Member Sequence ID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)

	/** This method returns the Member Name
	 * @return Returns the strName.
	 */
	public String getName() {
		return strName;
	}//end of getName()

	/** This method sets the Member Name
	 * @param strName The Member Name to set.
	 */
	public void setName(String strName) {
		this.strName = strName;
	}//end of setName(String strName)

	/** This method returns the Enrollment ID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()

	/** This method sets the Enrollment ID
	 * @param strEnrollmentID The Enrollment ID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)

	/** This method returns the GenderType ID
	 * @return Returns the strGenderTypeID.
	 */
	public String getGenderTypeID() {
		return strGenderTypeID;
	}//end of getGenderTypeID()

	/** This method sets the GenderType ID
	 * @param strGenderTypeID The GenderType ID to set.
	 */
	public void setGenderTypeID(String strGenderTypeID) {
		this.strGenderTypeID = strGenderTypeID;
	}//end of setGenderTypeID(String strGenderTypeID)

	/** This method returns the RelationType ID
	 * @return Returns the strRelationTypeID.
	 */
	public String getRelationTypeID() {
		return strRelationTypeID;
	}//end of getRelationTypeID()

	/** This method sets the RelationType ID
	 * @param strRelationTypeID The RelationType ID to set.
	 */
	public void setRelationTypeID(String strRelationTypeID) {
		this.strRelationTypeID = strRelationTypeID;
	}//end of setRelationTypeID(String strRelationTypeID)

	/** This method returns the Age
	 * @return Returns the intAge.
	 */
	public String getFormattedAge() {
		return intAge==null ? "":intAge.toString();
	}//end of getAge()

	/** This method returns the Age
	 * @return Returns the intAge.
	 */
	public Integer getAge() {
		return intAge;
	}//end of getAge()

	/** This method sets the Age
	 * @param intAge The Age to set.
	 */
	public void setAge(Integer intAge) {
		this.intAge = intAge;
	}//end of setAge(Integer intAge)

	/** This method returns the Date of Birth
	 * @return Returns the dtDateOfBirth.
	 */
	public Date getDateOfBirth() {
		return dtDateOfBirth;
	}//end of getDateOfBirth()

	/** This method returns the Date of Birth
	 * @return Returns the dtDateOfBirth.
	 */
	public String getWebDateOfBirth() {
		return TTKCommon.getFormattedDate(dtDateOfBirth);
	}//end of getDateOfBirth()

	/** This method sets the Date of Birth
	 * @param dtDateOfBirth The Date of Birth to set.
	 */
	public void setDateOfBirth(Date dtDateOfBirth) {
		this.dtDateOfBirth = dtDateOfBirth;
	}//end of setDateOfBirth(Date dtStartDate)

	/**
	 * This method retunrs Gender Description.
	 * @return strGenderDescription
	 */
	public String getGenderDescription() {
		return strGenderDescription;
	}//end of getGenderDescription()

	/**
	 * This method sets Gender Description
	 * @param strGenderDescription
	 */
	public void setGenderDescription(String strGenderDescription) {
		this.strGenderDescription = strGenderDescription;
	}//end of setGenderDescription(String strGenderDescription)

	/** This method returns the Member Relation Description
	 * @return Returns the strRelationDesc.
	 */
	public String getRelationDesc() {
		return strRelationDesc;
	}//End of getRelationDesc()

	/** This method sets the Member Relation Description
	 * @param strRelationDesc The strRelationDesc to set.
	 */
	public void setRelationDesc(String strRelationDesc) {
		this.strRelationDesc = strRelationDesc;
	}//End of setRelationDesc(String strRelationDesc)

	/**
     * This method returns the Inception Date
     * @return Returns the dtInceptionDate.
     */
    public Date getInceptionDate() {
        return dtInceptionDate;
    }//end of getInceptionDate()
    /**
     * This method sets the Inception Date
     * @param dtInceptionDate The dtInceptionDate to set.
     */
    public void setInceptionDate(Date dtInceptionDate) {
        this.dtInceptionDate = dtInceptionDate;
    }//end of setInceptionDate(Date dtInceptionDate)
    /**
     * This method returns the Member Type ID
     * @return Returns the strMemberTypeID.
     */
    public String getMemberTypeID() {
        return strMemberTypeID;
    }//end of getMemberTypeID()
    /**
     * This method sets the Member Type ID
     * @param strMemberTypeID The strMemberTypeID to set.
     */
    public void setMemberTypeID(String strMemberTypeID) {
        this.strMemberTypeID = strMemberTypeID;
    }//end of setMemberTypeID(String strMemberTypeID)

    /**
     * This method returns the Member Type
     * @return Returns the strMemberType.
     */
    public String getMemberType() {
        return strMemberType;
    }//end of getMemberType()
    /**
     * This method sets the Member Type
     * @param strMemberType The strMemberType to set.
     */
    public void setMemberType(String strMemberType) {
        this.strMemberType = strMemberType;
    }//end of setMemberType(String strMemberType)

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

	/** Retrive the Rates Icon Image
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}//end of getImageName()

	/** Sets the Rates Icon Image
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)

	/** Retrive the Additional Sum Insured
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle

	/** Sets the Additional Sum Insured
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)
	/** Retrieve the PolicyStatusTypeID
	 * @return Returns the strPolicyStatusTypeID.
	 */
	public String getPolicyStatusTypeID() {
		return strPolicyStatusTypeID;
	}//end of getPolicyStatusTypeID()

	/** Sets the PolicyStatusTypeID
	 * @param strPolicyStatusTypeID The strPolicyStatusTypeID to set.
	 */
	public void setPolicyStatusTypeID(String strPolicyStatusTypeID) {
		this.strPolicyStatusTypeID = strPolicyStatusTypeID;
	}//end of setPolicyStatusTypeID(String strPolicyStatusTypeID)
 }//end of DependentDetailVO
