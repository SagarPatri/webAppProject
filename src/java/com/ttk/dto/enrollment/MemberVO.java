/**
 * @ (#) MemberVO.java Feb 02, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MemberVO.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Feb 02, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.enrollment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;


public class MemberVO extends BaseVO {

	private Long lngMemberSeqID = null;
    private Long lngPolicySeqID = null;
	private String strName = "";
	private String strEnrollmentID = "";
    private String strEnrollmentNbr="";
	private String strGenderTypeID = "";
	private String strRelationTypeID = "";
	private Integer intAge=null;
	private Date dtDateOfBirth = null;
	private Date dtStartDate = null;
	private Date dtEndDate = null;
	private Date dtProposalDate=null;
	private ArrayList alMember=null;
	private Long lngSupensionSeqID = null;
	private String strRelationDesc = "";
	private String strRenewalYn = "";
    private String strPolicyNbr = "";
	private Long lngPolicyGroupSeqID=null;
    private Date dtDOB = null;
    private String strCancelYN="";
    private String strExpiredYN="";
    private Long lngEndorsementSeqID = null;
    private String strTPAStatusTypeID = "";
    private Date dtPolicyEndDate = null;    //policy expiry Date
    private Date dtPolicyStartDate = null;    //policy start Date
    private Date dtEffectiveDate = null;    //Effective Date of the Current endorsement
    private String strAbbrCode = "";
    private String strDMSRefID = "";
    private String strValidationStatus = "";
    private String strGenderDescription="";
    private String strTemplateID = "";
    private String strTemplateName = "";

    //newly added fields

    private String strChangeAldYN =""; //change_allowed_yn
    private String strEmpAddYN ="";	//hr_employee_add_yn
    private String strDependentAddYN ="";//hr_dependent_add_yn
    private String strAllowCancYN =""; //hr_allow_cancellation_yn
    private String strAllowModiYN ="";	//hr_allow_modification_yn
    private String strMemberTypeID = "";//POLICY_SUB_GENERAL_TYPE_ID   Floater/Non-Floater
    private String strGroupName = "";
    private String strInsuredName = "";
    private String strAllowAddSumYN = "";
    private BigDecimal bdPolicySumInsured=null;
    private String strEmpStatusTypeID = "";
    private String strEmpStatusDesc = "";
    private String strPolicyStatusTypeID = "";//POLICY_STATUS_GENERAL_TYPE_ID
    private String strPolicyOpted="";
	private String strReductWaitingPeriodYN = ""; //KOC 1270 hospital cash benefit
    private String strSumInsuredBenefitYN = ""; //KOC 1270 policy restoration
    private String strCriticalBenefitYN = ""; //KOC 1273 critical cash benefit
    
    private String sMemberID = "";
    private String sEmiratesID = "";
    
    
	public String getSumInsuredBenefitYN() {
		return strSumInsuredBenefitYN;
	}
     //added for koc 1278 & KOC 1270 for hospital cash benefit 
    private String personalWaitingPeriodYN = "";    //koc 1278 personal waiting period
	//KOC 1270 for hospital cash benefit
    private String strHospCashBenefitsYN = ""; //KOC 1270 hospital cash benefit
    private String strConvCashBenefitsYN = ""; //KOC 1270 hospital cash benefit
     
    private String strUploadYN = "";  //1352
    private String strFirstName="";
    private String strSecondName="";
    private String strFamilyName="";
    private String eventCompleteYN="";
    private String capitaionFlag="";
    private String memeligFlag ="";
    private int preauthClaimCount;
    private String dhpoMemberId;
    
    private String benefitType;
    private String memberId;
    private String preMemInceptionDt;
    private String policyNumber;
    private String policyEndDate;
    private String policyStartDate;
    private String sumInsured;
    private String availableSumInsured;
    private String utilizeSuminsured;
    private String mode;
    
    public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	
	
	public String getUtilizeSuminsured() {
		return utilizeSuminsured;
	}
	public void setUtilizeSuminsured(String utilizeSuminsured) {
		this.utilizeSuminsured = utilizeSuminsured;
	}
	public void setPolicyEndDate(String policyEndDate) {
		this.policyEndDate = policyEndDate;
	}
	public void setPolicyStartDate(String policyStartDate) {
		this.policyStartDate = policyStartDate;
	}
	public String getPreMemInceptionDt() {
		return preMemInceptionDt;
	}
	public void setPreMemInceptionDt(String preMemInceptionDt) {
		this.preMemInceptionDt = preMemInceptionDt;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
    
    
    
    
	
	public String getMemeligFlag() {
		return memeligFlag;
	}
	public void setMemeligFlag(String memeligFlag) {
		this.memeligFlag = memeligFlag;
	}
	/**
	 * @param uploadYN the uploadYN to set
	 */
	public void setUploadYN(String uploadYN) {
		strUploadYN = uploadYN;
	}
	/**
	 * @return the uploadYN
	 */
	public String getUploadYN() {
		return strUploadYN;
	}
	
	/**
	 * @param strConvCashBenefitsYN the strConvCashBenefitsYN to set
	 */
	public void setConvCashBenefitsYN(String strConvCashBenefitsYN) {
		this.strConvCashBenefitsYN = strConvCashBenefitsYN;
	}
    
  //added for KOC-1273
	public String getCriticalBenefitYN() {
		return strCriticalBenefitYN;
	}

	public void setSumInsuredBenefitYN(String strSumInsuredBenefitYN) {
		this.strSumInsuredBenefitYN = strSumInsuredBenefitYN;
	}
	// end KOC 1270 policy restoration
    /**
	 * @param personalWaitingPeriodYN the personalWaitingPeriodYN to set
	 */
	public void setPersonalWaitingPeriodYN(String personalWaitingPeriodYN) {
		this.personalWaitingPeriodYN = personalWaitingPeriodYN;
	}

	/**
	 * @return the personalWaitingPeriodYN
	 */
	public String getPersonalWaitingPeriodYN() {
		return personalWaitingPeriodYN;
	}

    
	/**
	 * @return the strConvCashBenefitsYN
	 */
	public String getConvCashBenefitsYN() {
		return strConvCashBenefitsYN;
	}

    
    
    
	/**
	 * @param strHospCashBenefitsYN the strHospCashBenefitsYN to set
	 */
	public void setHospCashBenefitsYN(String strHospCashBenefitsYN) {
		this.strHospCashBenefitsYN = strHospCashBenefitsYN;
	}

	/**
	 * @return the strHospCashBenefitsYN
	 */
	public String getHospCashBenefitsYN() {
		return strHospCashBenefitsYN;
	}
//KOC 1270 for hospital cash benefit
	public void setCriticalBenefitYN(String strCriticalBenefitYN) {
		this.strCriticalBenefitYN = strCriticalBenefitYN;
	}	

	public String getPolicyOpted() {
			return strPolicyOpted;
	}

	public void setPolicyOpted(String strPolicyOpted) {
			this.strPolicyOpted = strPolicyOpted;
	}

	private Date dtDateOfJoining=null;//added for IBM for Age CR

	public Date getDateOfJoining() {
			return dtDateOfJoining;
	}
	public void setDateOfJoining(Date dtDateOfJoining) {
			this.dtDateOfJoining = dtDateOfJoining;
	}

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

    /** Retrieve the EmpStatusDesc
	 * @return Returns the strEmpStatusDesc.
	 */
	public String getEmpStatusDesc() {
		return strEmpStatusDesc;
	}//end of getEmpStatusDesc()

	/** Sets the EmpStatusDesc
	 * @param strEmpStatusDesc The strEmpStatusDesc to set.
	 */
	public void setEmpStatusDesc(String strEmpStatusDesc) {
		this.strEmpStatusDesc = strEmpStatusDesc;
	}//end of setEmpStatusDesc(String strEmpStatusDesc)

	/** Retrieve the EmpStatusTypeID
	 * @return Returns the strEmpStatusTypeID.
	 */
	public String getEmpStatusTypeID() {
		return strEmpStatusTypeID;
	}//end of getEmpStatusTypeID()

	/** Sets the EmpStatusTypeID
	 * @param strEmpStatusTypeID The strEmpStatusTypeID to set.
	 */
	public void setEmpStatusTypeID(String strEmpStatusTypeID) {
		this.strEmpStatusTypeID = strEmpStatusTypeID;
	}//end of setEmpStatusTypeID(String strEmpStatusTypeID)

	/** Retrieve the PolicySumInsured
	 * @return Returns the bdPolicySumInsured.
	 */
	public BigDecimal getPolicySumInsured() {
		return bdPolicySumInsured;
	}//end of getPolicySumInsured()

	/** Sets the PolicySumInsured
	 * @param bdPolicySumInsured The bdPolicySumInsured to set.
	 */
	public void setPolicySumInsured(BigDecimal bdPolicySumInsured) {
		this.bdPolicySumInsured = bdPolicySumInsured;
	}//end of setPolicySumInsured()

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

	/** Retrieve the InsuredName
	 * @return Returns the strInsuredName.
	 */
	public String getInsuredName() {
		return strInsuredName;
	}//end of getInsuredName()

	/** Sets the InsuredName
	 * @param strInsuredName The strInsuredName to set.
	 */
	public void setInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
	}//end of setInsuredName(String strInsuredName)

    /** Retrieve the MemberTypeID
	 * @return Returns the strMemberTypeID.
	 */
	public String getMemberTypeID() {
		return strMemberTypeID;
	}//end of getMemberTypeID()

	/** Sets the MemberTypeID
	 * @param strMemberTypeID The strMemberTypeID to set.
	 */
	public void setMemberTypeID(String strMemberTypeID) {
		this.strMemberTypeID = strMemberTypeID;
	}//end of setMemberTypeID(String strMemberTypeID)

	/** This method returns the Group Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()

    /** This method sets the Group Name
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)

    /**
	 * This method retunrs Gender Description.
	 * @return strGenderDescription
	 */
	public String getGenderDescription() {
		return strGenderDescription;
	}//end of

	/**
	 * This method sets Gender Description
	 * @param strGenderDescription
	 */
	public void setGenderDescription(String strGenderDescription) {
		this.strGenderDescription = strGenderDescription;
	}//end of setGenderDescription(String strGenderDescription)


    /**
     * This method returns the Validation Status
     * @return strValidationStatus.
     */
    public String getValidationStatus() {
        return strValidationStatus;
    }//end of getValidationStatus()
    /**
     * This method sets the Validation Status
     * @param strValidationStatus The strValidationStatus to set.
     */
    public void setValidationStatus(String strValidationStatus) {
        this.strValidationStatus = strValidationStatus;
    }//end of setValidationStatus(String strValidationStatus)

    /**
     * This method returns the Enrollment Number
     * @return Returns the strEnrollmentNbr.
     */
    public String getEnrollmentNbr() {
        return strEnrollmentNbr;
    }//end of getEnrollmentNbr()


    /**
     * This method sets the Enrollment Number
     * @param strEnrollmentNbr The strEnrollmentNbr to set.
     */
    public void setEnrollmentNbr(String strEnrollmentNbr) {
        this.strEnrollmentNbr = strEnrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)

    /** Retrieve the DMS Ref ID
	 * @return Returns the strDMSRefID.
	 */
	public String getDMSRefID() {
		return strDMSRefID;
	}//end of getDMSRefID()

	/** Sets the DMS Ref ID
	 * @param strDMSRefID The strDMSRefID to set.
	 */
	public void setDMSRefID(String strDMSRefID) {
		this.strDMSRefID = strDMSRefID;
	}//end of setDMSRefID(String strDMSRefID)

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

	/**
     * Retrieve the Endorsement Effective Date of the current endorsement
     * @return  dtEffectiveDate Date
     */
    public Date getEffectiveDate() {
        return dtEffectiveDate;
    }//end of getEffectiveDate()

    /**
     * Sets the Endorsement Effective Date of the current endorsement
     * @param  dtEffectiveDate Date
     */
    public void setEffectiveDate(Date dtEffectiveDate) {
        this.dtEffectiveDate = dtEffectiveDate;
    }//end of setEffectiveDate(Date dtEffectiveDate)

    /**
     * Retrieve the Policy Start Date
     * @return  dtPolicyStartDate Date
     */
    public Date getPolicyStartDate() {
        return dtPolicyStartDate;
    }//end of getPolicyStartDate()

    /**
     * Sets the Policy Start Date
     * @param  dtPolicyStartDate Date
     */
    public void setPolicyStartDate(Date dtPolicyStartDate) {
        this.dtPolicyStartDate = dtPolicyStartDate;
    }//end of setPolicyStartDate(Date dtPolicyEndDate)
    /**
     * Retrieve the Policy End Date
     * @return  dtPolicyEndDate Date
     */
    public Date getPolicyEndDate() {
        return dtPolicyEndDate;
    }//end of getPolicyEndDate()

    /**
     * Sets the Policy End Date
     * @param  dtPolicyEndDate Date
     */
    public void setPolicyEndDate(Date dtPolicyEndDate) {
        this.dtPolicyEndDate = dtPolicyEndDate;
    }//end of setPolicyEndDate(Date dtPolicyEndDate)

	/** This method returns the TPA Status General Type ID
     * @return Returns the strTPAStatusTypeID.
     */
    public String getTPAStatusTypeID() {
        return strTPAStatusTypeID;
    }//end of getTPAStatusTypeID()

    /** This method sets the TPA Status General Type ID
     * @param strTPAStatusTypeID The strTPAStatusTypeID to set.
     */
    public void setTPAStatusTypeID(String strTPAStatusTypeID) {
        this.strTPAStatusTypeID = strTPAStatusTypeID;
    }//end of setTPAStatusTypeID(String strTPAStatusTypeID)

    /** This method returns the Endorsement Seq ID
     * @return Returns the lngEndorsementSeqID.
     */
    public Long getEndorsementSeqID() {
        return lngEndorsementSeqID;
    }//end of getEndorsementSeqID()

    /** This method sets the Endorsement Seq ID
     * @param lngEndorsementSeqID The lngEndorsementSeqID to set.
     */
    public void setEndorsementSeqID(Long lngEndorsementSeqID) {
        this.lngEndorsementSeqID = lngEndorsementSeqID;
    }//end of setEndorsementSeqID(Long lngEndorsementSeqID)

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

	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()

	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getListStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getStartDate()

	/** This method sets the Start Date
	 * @param dtStartDate The Start Date to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getListEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}//end of getEndDate()

	/** This method sets the End Date
	 * @param dtEndDate The End Date to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)

	/** This method returns the List of member
	 * @return Returns the alMember.
	 */
	public ArrayList getMemberList() {
		return alMember;
	}//end of getMemberList()

	/** This method sets the Member list
	 * @param dtEndDate The Member list.
	 */
	public void setMemberList(ArrayList alMember) {
		this.alMember = alMember;
	}//end of setMemberList(ArrayList alMember)

	/** This method returns the Member Suspension Sequence ID
	 * @return Returns the lngSupensionSeqID.
	 */
	public Long getSupensionSeqID() {
		return lngSupensionSeqID;
	}// End of getSupensionSeqID()

	/** This method sets the Member Suspension Sequence ID
	 * @param lngSupensionSeqID The lngSupensionSeqID to set.
	 */
	public void setSupensionSeqID(Long lngSupensionSeqID) {
		this.lngSupensionSeqID = lngSupensionSeqID;
	}// End of setSupensionSeqID(Long lngSupensionSeqID)

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

	/** This method returns the Renewal Yes or NO
	 * @return Returns the strRenewalYn.
	 */
	public String getRenewalYn() {
		return strRenewalYn;
	}// End of getRenewalYn()

	/** This method sets the Renewal Yes or NO
     * @param strRenewalYn The strRenewalYn to set.
     */
    public void setRenewalYn(String strRenewalYn) {
        this.strRenewalYn = strRenewalYn;
    }// End of setRenewalYn(String strRenewalYn)

    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }// End of setPolicyNbr(String strPolicyNbr)
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }// End of getPolicyNbr()
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

    /** This method returns cancelYN
     * @return Returns the strCancelYN.
     */
    public String getCancleYN() {
        return this.strCancelYN;
    }//end of getCancleYN()

    /**
     * @param strCancelYN The strCancelYN to set.
     */
    public void setCancelYN(String strCancelYN) {
        this.strCancelYN = strCancelYN;
    }//end of setCancelYN(String strCancelYN)

    /** This method returns the expiredYN
     * @return Returns the strCancelYN.
     */
    public String getExpiredYN() {
        return this.strExpiredYN;
    }//end of getExpiredYN()
    /**
     * @param strExpiredYN The strExpiredYN to set.
     */
    public void setExpiredYN(String strExpiredYN) {
        this.strExpiredYN = strExpiredYN;
    }//end of setExpiredYN(String strExpiredYN)

    /** This method returns the Date Of Birth
     * @return Returns the dtDOB.
     */
    public String getDOB() {
        return TTKCommon.getFormattedDate(dtDOB);
    }//end of getDOB()
    /**
     * @param dtDOB The dtDOB to set.
     */
    public void setDOB(Date dtDOB) {
        this.dtDOB = dtDOB;
    }//end of setDOB(Date dtDOB)

	/** Retrieve the TemplateID
	 * @return Returns the strTemplateID.
	 */
	public String getTemplateID() {
		return strTemplateID;
	}//end of getTemplateID()

	/** Sets the TemplateID
	 * @param strTemplateID The strTemplateID to set.
	 */
	public void setTemplateID(String strTemplateID) {
		this.strTemplateID = strTemplateID;
	}//end of setTemplateID(String strTemplateID)

	/** Retrieve the TemplateName
	 * @return Returns the strTemplateName.
	 */
	public String getTemplateName() {
		return strTemplateName;
	}//end of getTemplateName()

	/** Sets the TemplateName
	 * @param strTemplateName The strTemplateName to set.
	 */
	public void setTemplateName(String strTemplateName) {
		this.strTemplateName = strTemplateName;
	}//end of setTemplateName(String strTemplateName)

	/** Retrieve the AllowCancYN
	 * @return Returns the strAllowCancYN.
	 */
	public String getAllowCancYN() {
		return strAllowCancYN;
	}

	/** Sets the AllowCancYN
	 * @param strAllowCancYN The strAllowCancYN to set.
	 */
	public void setAllowCancYN(String strAllowCancYN) {
		this.strAllowCancYN = strAllowCancYN;
	}

	/** Retrieve the AllowModiYN
	 * @return Returns the strAllowModiYN.
	 */
	public String getAllowModiYN() {
		return strAllowModiYN;
	}

	/** Sets the strAllowModiYN
	 * @param strAllowModiYN The strAllowModiYN to set.
	 */
	public void setAllowModiYN(String strAllowModiYN) {
		this.strAllowModiYN = strAllowModiYN;
	}

	/** Retrieve the ChangeAldYN
	 * @return Returns the strChangeAldYN.
	 */
	public String getChangeAldYN() {
		return strChangeAldYN;
	}

	/** Sets the ChangeAldYN
	 * @param strChangeAldYN The strChangeAldYN to set.
	 */
	public void setChangeAldYN(String strChangeAldYN) {
		this.strChangeAldYN = strChangeAldYN;
	}

	/** Retrive the DependentAddYN
	 * @return Returns the strDependentAddYN.
	 */
	public String getDependentAddYN() {
		return strDependentAddYN;
	}

	/** Sets the DependentAddYN
	 * @param strDependentAddYN The strDependentAddYN to set.
	 */
	public void setDependentAddYN(String strDependentAddYN) {
		this.strDependentAddYN = strDependentAddYN;
	}

	/** Retrive the EmpAddYN
	 * @return Returns the strEmpAddYN.
	 */
	public String getEmpAddYN() {
		return strEmpAddYN;
	}

	/** Sets the EmpAddYN
	 * @param strEmpAddYN The strEmpAddYN to set.
	 */
	public void setEmpAddYN(String strEmpAddYN) {
		this.strEmpAddYN = strEmpAddYN;
	}

	/**
	 * @return Returns the dtProposalDate.
	 */
	public Date getProposalDate() {
		return dtProposalDate;
	}

	/**
	 * @param dtProposalDate The dtProposalDate to set.
	 */
	public void setProposalDate(Date dtProposalDate) {
		this.dtProposalDate = dtProposalDate;
	}
	public String getreductWaitingPeriodYN() {
		return strReductWaitingPeriodYN;
	}

	public void setreductWaitingPeriodYN(String strReductWaitingPeriodYN) {
		this.strReductWaitingPeriodYN = strReductWaitingPeriodYN;
	}
	public String getStrFirstName() {
		return strFirstName;
	}
	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}
	public String getStrSecondName() {
		return strSecondName;
	}
	public void setStrSecondName(String strSecondName) {
		this.strSecondName = strSecondName;
	}
	public String getStrFamilyName() {
		return strFamilyName;
	}
	public void setStrFamilyName(String strFamilyName) {
		this.strFamilyName = strFamilyName;
	}
	public String getEventCompleteYN() {
		return eventCompleteYN;
	}
	public void setEventCompleteYN(String eventCompleteYN) {
		this.eventCompleteYN = eventCompleteYN;
	}
	public String getCapitaionFlag() {
		return capitaionFlag;
	}
	public void setCapitaionFlag(String capitaionFlag) {
		this.capitaionFlag = capitaionFlag;
	}
	public String getsMemberID() {
		return sMemberID;
	}
	public void setsMemberID(String sMemberID) {
		this.sMemberID = sMemberID;
	}
	public String getsEmiratesID() {
		return sEmiratesID;
	}
	public void setsEmiratesID(String sEmiratesID) {
		this.sEmiratesID = sEmiratesID;
	}
	public int getPreauthClaimCount() {
		return preauthClaimCount;
	}
	public void setPreauthClaimCount(int preauthClaimCount) {
		this.preauthClaimCount = preauthClaimCount;
	}
	public String getDhpoMemberId() {
		return dhpoMemberId;
	}
	public void setDhpoMemberId(String dhpoMemberId) {
		this.dhpoMemberId = dhpoMemberId;
	}
	

}//end of MemberVO
