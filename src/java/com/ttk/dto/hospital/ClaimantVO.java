/** 
 *  @ (#)ClaimantVO.java 
 *  Project       : TTK HealthCare Services
 *  File          : ClaimantVO.java
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.hospital;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.enrollment.MemberVO;

public class ClaimantVO extends MemberVO{
    
    private BigDecimal bdTotalSumInsured = null;
    private BigDecimal bdAvailSumInsured = null;
    private String strCategoryTypeID="";
    private String strCategoryDesc = "";
    private String strCompanyName = "";
    private String strGroupName = "";
    private String strSumEnhancedYN="";
    private String strSumEnhancedImageName ="Blank";
    private String strSumEnhancedImageTitle="";
    private String strGenderDescription = "";
    private Long lngProductSeqID = null;
    private String strProductDesc = "";
    private Long lngInsSeqID = null;
    private String strCompanyCode = "";
    private String strPolicyHolderName = "";
	private String strPhone = "";
	private String strTermStatusID = "";
    private String strTermStatusDesc = "";
	private String strPolicyTypeID = "";
    private String strPolicyTypeDesc = "";
	private String strPolicySubTypeID = "";
    private String strPolicySubTypeDesc = "";
	private Date dtDateOfInception = null;
	private Date dtDateOfExit = null;
    private BigDecimal bdAvblBufferAmount = null;
	private BigDecimal bdCumulativeBonus = null;
	private Long lngGroupRegnSeqID = null;
	private String strGroupID = "";
	private String strCloseProximityYN="";
    private String strProximityStatusTypeID = "";
    private Date dtProximityDate = null;
    private String strClaimantNameDisc = "";
    private String strGenderDiscrepancy = "";
    private String strAgeDiscrepancy = "";
    private String strPolicyNbrDisc = "";
    private String strPolicyHolderNameDisc = "";
    private Long lngBufferDtlSeqID = null;
    private String strBufferAllowedYN = "";
    private BigDecimal bdTotBufferAmt = null;
    private String strSchemeCertNbr = "";
    private String strEmployeeNbr = "";
    private String strEmployeeName = "";
    private String strRelshipTypeID = "";
    private String strClaimantPhoneNbr = "";
    private Long lngInsHeadOffSeqID = null;
    private Long lngClmSeqID = null;//For Inward Entry Claims->ShortfallList
    private String strClaimNbr = "";//For Inward Entry Claims->ShortfallList
    private String strNotifyPhoneNbr = "";
    private String strEmailID ="";
    private String strInsCustCode =""; //ins_customer_code
    private String strCertificateNo =""; //certificate_no
    private String strInsScheme =""; //ins_scheme
    private String strCreditCardNbr = "";
   //change on DEC 6th KOC 1136
    private String vipYN="";    
  //change on DEC 6th KOC 1136
    
    
  //added for koc insurance reference No
	private String strInsuranceRefNo = "";
	public String getInsuranceRefNo() {
		return strInsuranceRefNo;
	}

	public void setInsuranceRefNo(String strInsuranceRefNo) {
		this.strInsuranceRefNo = strInsuranceRefNo;
	}
	
	// end added for koc insurance reference No
    /** Retrieve the CreditCardNbr
	 * @return Returns the strCreditCardNbr.
	 */
	public String getCreditCardNbr() {
		return strCreditCardNbr;
	}//end of getCreditCardNbr()

	/** Sets the CreditCardNbr
	 * @param strCreditCardNbr The strCreditCardNbr to set.
	 */
	public void setCreditCardNbr(String strCreditCardNbr) {
		this.strCreditCardNbr = strCreditCardNbr;
	}//end of setCreditCardNbr(String strCreditCardNbr)
    
    /** Retrive the CertificateNo
	 * @return Returns the strCertificateNo.
	 */
	public String getCertificateNo() {
		return strCertificateNo;
	}//end of getCertificateNo()

	/** Sets the CertificateNo
	 * @param strCertificateNo The strCertificateNo to set.
	 */
	public void setCertificateNo(String strCertificateNo) {
		this.strCertificateNo = strCertificateNo;
	}//end of setCertificateNo(String strCertificateNo)

	/** Retrive the InsCustCode
	 * @return Returns the strInsCustCode.
	 */
	public String getInsCustCode() {
		return strInsCustCode;
	}//end of getInsCustCode()

	/** Sets the InsCustCode
	 * @param strInsCustCode The strInsCustCode to set.
	 */
	public void setInsCustCode(String strInsCustCode) {
		this.strInsCustCode = strInsCustCode;
	}//end of setInsCustCode(String strInsCustCode)

	/** Retrive the InsScheme
	 * @return Returns the strInsScheme.
	 */
	public String getInsScheme() {
		return strInsScheme;
	}//end of getInsScheme()

	/** Sets the InsScheme
	 * @param strInsScheme The strInsScheme to set.
	 */
	public void setInsScheme(String strInsScheme) {
		this.strInsScheme = strInsScheme;
	}//end of setInsScheme(String strInsScheme)

	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()

	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)
    
    /** Retrieve the Claim Seq ID
	 * @return Returns the lngClmSeqID.
	 */
	public Long getClmSeqID() {
		return lngClmSeqID;
	}//end of getClmSeqID()

	/** Sets the Claim Seq ID
	 * @param lngClmSeqID The lngClmSeqID to set.
	 */
	public void setClmSeqID(Long lngClmSeqID) {
		this.lngClmSeqID = lngClmSeqID;
	}//end of setClmSeqID(Long lngClmSeqID)

	/** Retrieve the ClaimNbr
	 * @return Returns the strClaimNbr.
	 */
	public String getClaimNbr() {
		return strClaimNbr;
	}//end of getClaimNbr()

	/** Sets the ClaimNbr
	 * @param strClaimNbr The strClaimNbr to set.
	 */
	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}//end of setClaimNbr(String strClaimNbr)

	/** Retrieve the InsHeadOffSeqID
	 * @return Returns the lngInsHeadOffSeqID.
	 */
	public Long getInsHeadOffSeqID() {
		return lngInsHeadOffSeqID;
	}//end of getInsHeadOffSeqID()

	/** Sets the InsHeadOffSeqID
	 * @param lngInsHeadOffSeqID The lngInsHeadOffSeqID to set.
	 */
	public void setInsHeadOffSeqID(Long lngInsHeadOffSeqID) {
		this.lngInsHeadOffSeqID = lngInsHeadOffSeqID;
	}//end of setInsHeadOffSeqID(Long lngInsHeadOffSeqID)

	/** Retrieve the ClaimantPhoneNbr
	 * @return Returns the strClaimantPhoneNbr.
	 */
	public String getClaimantPhoneNbr() {
		return strClaimantPhoneNbr;
	}//end of getClaimantPhoneNbr()

	/** Sets the ClaimantPhoneNbr
	 * @param strClaimantPhoneNbr The strClaimantPhoneNbr to set.
	 */
	public void setClaimantPhoneNbr(String strClaimantPhoneNbr) {
		this.strClaimantPhoneNbr = strClaimantPhoneNbr;
	}//end of setClaimantPhoneNbr(String strClaimantPhoneNbr)

	/** Retrieve the EmployeeName
	 * @return Returns the strEmployeeName.
	 */
	public String getEmployeeName() {
		return strEmployeeName;
	}//end of getEmployeeName()

	/** Sets the EmployeeName
	 * @param strEmployeeName The strEmployeeName to set.
	 */
	public void setEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}//end of setEmployeeName(String strEmployeeName)

	/** Retrieve the EmployeeNbr
	 * @return Returns the strEmployeeNbr.
	 */
	public String getEmployeeNbr() {
		return strEmployeeNbr;
	}//end of getEmployeeNbr()

	/** Sets the EmployeeNbr
	 * @param strEmployeeNbr The strEmployeeNbr to set.
	 */
	public void setEmployeeNbr(String strEmployeeNbr) {
		this.strEmployeeNbr = strEmployeeNbr;
	}//end of setEmployeeNbr(String strEmployeeNbr)

	/** Retrieve the Relationship Type ID
	 * @return Returns the strRelshipTypeID.
	 */
	public String getRelshipTypeID() {
		return strRelshipTypeID;
	}//end of getRelshipTypeID()

	/** Sets the Relationship Type ID
	 * @param strRelshipTypeID The strRelshipTypeID to set.
	 */
	public void setRelshipTypeID(String strRelshipTypeID) {
		this.strRelshipTypeID = strRelshipTypeID;
	}//end of setRelshipTypeID(String strRelshipTypeID)

	/** Retrieve the Total Buffer Amount
	 * @return Returns the bdTotBufferAmt.
	 */
	public BigDecimal getTotBufferAmt() {
		return bdTotBufferAmt;
	}//end of getTotBufferAmt()

	/** Sets the Total Buffer Amount
	 * @param bdTotBufferAmt The bdTotBufferAmt to set.
	 */
	public void setTotBufferAmt(BigDecimal bdTotBufferAmt) {
		this.bdTotBufferAmt = bdTotBufferAmt;
	}//end of setTotBufferAmt(BigDecimal bdTotBufferAmt)

	/** Retrieve the BufferAllowedYN
	 * @return Returns the strBufferAllowedYN.
	 */
	public String getBufferAllowedYN() {
		return strBufferAllowedYN;
	}//end of getBufferAllowedYN()

	/** Sets the BufferAllowedYN
	 * @param strBufferAllowedYN The strBufferAllowedYN to set.
	 */
	public void setBufferAllowedYN(String strBufferAllowedYN) {
		this.strBufferAllowedYN = strBufferAllowedYN;
	}//end of setBufferAllowedYN(String strBufferAllowedYN)

	/** Retrieve the Scheme or Certificate Number
	 * @return Returns the strSchemeCertNbr.
	 */
	public String getSchemeCertNbr() {
		return strSchemeCertNbr;
	}//end of getSchemeCertNbr()

	/** Sets the Scheme or Certificate Number
	 * @param strSchemeCertNbr The strSchemeCertNbr to set.
	 */
	public void setSchemeCertNbr(String strSchemeCertNbr) {
		this.strSchemeCertNbr = strSchemeCertNbr;
	}//end of setSchemeCertNbr(String strSchemeCertNbr)

	/** Retrieve the Buffer Dtl Seq ID
	 * @return Returns the lngBufferDtlSeqID.
	 */
	public Long getBufferDtlSeqID() {
		return lngBufferDtlSeqID;
	}//end of getBufferDtlSeqID()

	/** Sets the Buffer Dtl Seq ID
	 * @param lngBufferDtlSeqID The lngBufferDtlSeqID to set.
	 */
	public void setBufferDtlSeqID(Long lngBufferDtlSeqID) {
		this.lngBufferDtlSeqID = lngBufferDtlSeqID;
	}//end of setBufferDtlSeqID(Long lngBufferDtlSeqID)
    
    /** Retrieve the Age Discrepancy
	 * @return Returns the strAgeDiscrepancy.
	 */
	public String getAgeDiscrepancy() {
		return strAgeDiscrepancy;
	}//end of getAgeDiscrepancy()

	/** Sets the Age Discrepancy
	 * @param strAgeDiscrepancy The strAgeDiscrepancy to set.
	 */
	public void setAgeDiscrepancy(String strAgeDiscrepancy) {
		this.strAgeDiscrepancy = strAgeDiscrepancy;
	}//end of setAgeDiscrepancy(String strAgeDiscrepancy)

	/** Retrieve the Claimant Name Discrepancy
	 * @return Returns the strClaimantNameDisc.
	 */
	public String getClaimantNameDisc() {
		return strClaimantNameDisc;
	}//end of getClaimantNameDisc()

	/** Sets the Claimant Name Discrepancy
	 * @param strClaimantNameDisc The strClaimantNameDisc to set.
	 */
	public void setClaimantNameDisc(String strClaimantNameDisc) {
		this.strClaimantNameDisc = strClaimantNameDisc;
	}//end of setClaimantNameDisc(String strClaimantNameDisc)

	/** Retrieve the Gender Discrepancy
	 * @return Returns the strGenderDiscrepancy.
	 */
	public String getGenderDiscrepancy() {
		return strGenderDiscrepancy;
	}//end of getGenderDiscrepancy()

	/** Sets the Gender Discrepancy
	 * @param strGenderDiscrepancy The strGenderDiscrepancy to set.
	 */
	public void setGenderDiscrepancy(String strGenderDiscrepancy) {
		this.strGenderDiscrepancy = strGenderDiscrepancy;
	}//end of setGenderDiscrepancy(String strGenderDiscrepancy)

	/** Retrieve the Policy Holder Name Discrepancy
	 * @return Returns the strPolicyHolderNameDisc.
	 */
	public String getPolicyHolderNameDisc() {
		return strPolicyHolderNameDisc;
	}//end of getPolicyHolderNameDisc()

	/** Sets the Policy Holder Name Discrepancy
	 * @param strPolicyHolderNameDisc The strPolicyHolderNameDisc to set.
	 */
	public void setPolicyHolderNameDisc(String strPolicyHolderNameDisc) {
		this.strPolicyHolderNameDisc = strPolicyHolderNameDisc;
	}//end of setPolicyHolderNameDisc(String strPolicyHolderNameDisc)

	/** Retrieve the Policy Nbr Discrepancy
	 * @return Returns the strPolicyNbrDisc.
	 */
	public String getPolicyNbrDisc() {
		return strPolicyNbrDisc;
	}//end of getPolicyNbrDisc()

	/** Sets the Policy Nbr Discrepancy
	 * @param strPolicyNbrDisc The strPolicyNbrDisc to set.
	 */
	public void setPolicyNbrDisc(String strPolicyNbrDisc) {
		this.strPolicyNbrDisc = strPolicyNbrDisc;
	}//end of setPolicyNbrDisc(String strPolicyNbrDisc)

	/**
     * Retrieve the Close Proximity indicator
     * @return  strCloseProximityYN String
     */
    public String getCloseProximityYN() {
        return strCloseProximityYN;
    }//end of getCloseProximityYN()
    
    /**
     * Sets the Close Proximity indicator
     * @param  strCloseProximityYN String  
     */
    public void setCloseProximityYN(String strCloseProximityYN) {
        this.strCloseProximityYN = strCloseProximityYN;
    }//end of setCloseProximityYN(String strCloseProximityYN)
    
    /** Retrieve the Proximity Date
     * @return Returns the dtProximityDate.
     */
    public Date getProximityDate() {
        return dtProximityDate;
    }//end of getProximityDate()
    
    /** Sets the Proximity Date
     * @param dtProximityDate The dtProximityDate to set.
     */
    public void setProximityDate(Date dtProximityDate) {
        this.dtProximityDate = dtProximityDate;
    }//end of setProximityDate(Date dtProximityDate)
    
    /** Retrieve the Proximity Status Type ID
     * @return Returns the strProximityStatusTypeID.
     */
    public String getProximityStatusTypeID() {
        return strProximityStatusTypeID;
    }//end of getProximityStatusTypeID()
    
    /** Sets the Proximity Status Type ID
     * @param strProximityStatusTypeID The strProximityStatusTypeID to set.
     */
    public void setProximityStatusTypeID(String strProximityStatusTypeID) {
        this.strProximityStatusTypeID = strProximityStatusTypeID;
    }//end of setProximityStatusTypeID(String strProximityStatusTypeID)
	
	/** Retrieve the Group Registration Seq ID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegnSeqID() {
		return lngGroupRegnSeqID;
	}//end of getGroupRegSeqID()

	/** Sets the Group Registration Seq ID
	 * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
	 */
	public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
		this.lngGroupRegnSeqID = lngGroupRegnSeqID;
	}//end of setGroupRegSeqID(Long lngGroupRegnSeqID)

	/** Retrieve the Group ID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}//end of getGroupID()

	/** Sets the Group ID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}//end of setGroupID(String strGroupID)

	/** Retrieve the Company Code
     * @return Returns the strCompanyCode.
     */
    public String getCompanyCode() {
        return strCompanyCode;
    }//end of getCompanyCode()
    
    /** Sets the Company Code
     * @param strCompanyCode The strCompanyCode to set.
     */
    public void setCompanyCode(String strCompanyCode) {
        this.strCompanyCode = strCompanyCode;
    }//end of setCompanyCode(String strCompanyCode)
	
	/** Retrieve the Product Description
	 * @return Returns the strProductDesc.
	 */
	public String getProductDesc() {
		return strProductDesc;
	}//end of getProductDesc()

	/** Sets the Product Description
	 * @param strProductDesc The strProductDesc to set.
	 */
	public void setProductDesc(String strProductDesc) {
		this.strProductDesc = strProductDesc;
	}//end of setProductDesc(String strProductDesc)

	/** This method returns the Policy SubType Description
     * @return Returns the strPolicySubTypeDesc.
     */
    public String getPolicySubTypeDesc() {
        return strPolicySubTypeDesc;
    }//end of getPolicySubTypeDesc()
    
    /** This method sets the Policy SubType Description
     * @param strPolicySubTypeDesc The strPolicySubTypeDesc to set.
     */
    public void setPolicySubTypeDesc(String strPolicySubTypeDesc) {
        this.strPolicySubTypeDesc = strPolicySubTypeDesc;
    }//end of setPolicySubTypeDesc(String strPolicySubTypeDesc)
    
    /** This method returns the Policy Type Description
     * @return Returns the strPolicyTypeDesc.
     */
    public String getPolicyTypeDesc() {
        return strPolicyTypeDesc;
    }//end of getPolicyTypeDesc()
    
    /** This method sets the Policy Type Description
     * @param strPolicyTypeDesc The strPolicyTypeDesc to set.
     */
    public void setPolicyTypeDesc(String strPolicyTypeDesc) {
        this.strPolicyTypeDesc = strPolicyTypeDesc;
    }//end of setPolicyTypeDesc(String strPolicyTypeDesc)
    
    /** This method returns the Term Status Description
     * @return Returns the strTermStatusDesc.
     */
    public String getTermStatusDesc() {
        return strTermStatusDesc;
    }//end of getTermStatusDesc()
    
    /** This method sets the Term Status Description
     * @param strTermStatusDesc The strTermStatusDesc to set.
     */
    public void setTermStatusDesc(String strTermStatusDesc) {
        this.strTermStatusDesc = strTermStatusDesc;
    }//end of setTermStatusDesc(String strTermStatusDesc)
    
    /** This method returns the Insurance Seq ID
     * @return Returns the lngInsSeqID.
     */
    public Long getInsSeqID() {
        return lngInsSeqID;
    }//end of getInsSeqID()
    
    /** This method sets the Insurance Seq ID
     * @param lngInsSeqID The lngInsSeqID to set.
     */
    public void setInsSeqID(Long lngInsSeqID) {
        this.lngInsSeqID = lngInsSeqID;
    }//end of setInsSeqID(Long lngInsSeqID)
    
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
    
    /** This method returns the AvblBufferAmount
	 * @return Returns the bdAvblBufferAmount.
	 */
	public BigDecimal getAvblBufferAmount() {
		return bdAvblBufferAmount;
	}//End of getAvblBufferAmount() 
	
	/** This method sets AvblBufferAmount
	 * @param bdAvblBufferAmount The bdAvblBufferAmount to set.
	 */
	public void setAvblBufferAmount(BigDecimal bdAvblBufferAmount) {
		this.bdAvblBufferAmount = bdAvblBufferAmount;
	}//End of setAvblBufferAmount(BigDecimal bdAvblBufferAmount)
	
	/** This method returns the Cumulative Bonus
	 * @return Returns the bdCumulativeBonus.
	 */
	public BigDecimal getCumulativeBonus() {
		return bdCumulativeBonus;
	}//End of getCumulativeBonus() 
	
	/** This method sets Cumulative Bonus
	 * @param bdCumulativeBonus The bdCumulativeBonus to set.
	 */
	public void setCumulativeBonus(BigDecimal bdCumulativeBonus) {
		this.bdCumulativeBonus = bdCumulativeBonus;
	}//End of setCumulativeBonus(BigDecimal bdCumulativeBonus)
	
	/** This method returns the Date of Exit
	 * @return Returns the dtDateOfExit.
	 */
	public Date getDateOfExit() {
		return dtDateOfExit;
	}//End of getDateOfExit()
	
	/** This method sets Date of Exit
	 * @param dtDateOfExit The dtDateOfExit to set.
	 */
	public void setDateOfExit(Date dtDateOfExit) {
		this.dtDateOfExit = dtDateOfExit;
	}//End of setDateOfExit(Date dtDateOfExit)
	
	/** This method returns the Date of Inception
	 * @return Returns the dtDateOfInception.
	 */
	public Date getDateOfInception() {
		return dtDateOfInception;
	}//End of getDateOfInception()
	
	/** This method sets Date of Inception
	 * @param dtDateOfInception The dtDateOfInception to set.
	 */
	public void setDateOfInception(Date dtDateOfInception) {
		this.dtDateOfInception = dtDateOfInception;
	}//End of setDateOfInception(Date dtDateOfInception)
	
	/** This method returns the Phone
	 * @return Returns the strPhone.
	 */
	public String getPhone() {
		return strPhone;
	}//End of getPhone()
	
	/** This method sets Phone
	 * @param strPhone The strPhone to set.
	 */
	public void setPhone(String strPhone) {
		this.strPhone = strPhone;
	}//End of setPhone(String strPhone)
	
	/** This method returns the PolicyHolderName
	 * @return Returns the strPolicyHolderName.
	 */
	public String getPolicyHolderName() {
		return strPolicyHolderName;
	}//End of getPolicyHolderName() 
	
	/** This method sets PolicyHolderName
	 * @param strPolicyHolderName The strPolicyHolderName to set.
	 */
	public void setPolicyHolderName(String strPolicyHolderName) {
		this.strPolicyHolderName = strPolicyHolderName;
	}//End of setPolicyHolderName(String strPolicyHolderName)
	
	/** This method returns the PolicySubTypeID
	 * @return Returns the strPolicySubTypeID.
	 */
	public String getPolicySubTypeID() {
		return strPolicySubTypeID;
	}//End of getPolicySubTypeID()
	
	/** This method sets PolicySubTypeID
	 * @param strPolicySubTypeID The strPolicySubTypeID to set.
	 */
	public void setPolicySubTypeID(String strPolicySubTypeID) {
		this.strPolicySubTypeID = strPolicySubTypeID;
	}//End of setPolicySubTypeID(String strPolicySubTypeID)
	 
	/**  This method returns the PolicyTypeID
	 * @return Returns the strPolicyTypeID.
	 */
	public String getPolicyTypeID() {
		return strPolicyTypeID;
	}//End of getPolicyTypeID()
	
	/** This method sets PolicyTypeID
	 * @param strPolicyTypeID The strPolicyTypeID to set.
	 */
	public void setPolicyTypeID(String strPolicyTypeID) {
		this.strPolicyTypeID = strPolicyTypeID;
	}//End of setPolicyTypeID(String strPolicyTypeID)
	
	/** This method returns the TermStatusID
	 * @return Returns the strTermStatusID.
	 */
	public String getTermStatusID() {
		return strTermStatusID;
	}//End of getTermStatusID()
	
	/** This method sets TermStatusID
	 * @param strTermStatusID The strTermStatusID to set.
	 */
	public void setTermStatusID(String strTermStatusID) {
		this.strTermStatusID = strTermStatusID;
	}//End of setTermStatusID(String strTermStatusID)
    
    /** Retrieve the Category Description
     * @return Returns the strCategoryDesc.
     */
    public String getCategoryDesc() {
        return strCategoryDesc;
    }//end of getCategoryDesc()
    
    /** Sets the Category Description
     * @param strCategoryDesc The strCategoryDesc to set.
     */
    public void setCategoryDesc(String strCategoryDesc) {
        this.strCategoryDesc = strCategoryDesc;
    }//end of setCategoryDesc(String strCategoryDesc)
    
    /** Retrieve the Insurance Company Name 
     * @return Returns the strCompanyName.
     */
    public String getCompanyName() {
        return strCompanyName;
    }//end of getCompanyName()
    
    /** Sets the Insurance Company Name
     * @param strCompanyName The strCompanyName to set.
     */
    public void setCompanyName(String strCompanyName) {
        this.strCompanyName = strCompanyName;
    }//end of setCompName(String strCompanyName)
    
    /** Retrieve the Corporate Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()
    
    /** Sets the Corporate Name
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)
    
    /** Retrieve the Gender Description
     * @return Returns the strGenderDescription.
     */
    public String getGenderDescription() {
        return strGenderDescription;
    }//end of getGenderDesc()
    
    /** Sets the Gender Description
     * @param strGenderDescription The strGenderDescription to set.
     */
    public void setGenderDescription(String strGenderDescription) {
        this.strGenderDescription = strGenderDescription;
    }//end of setGenderDesc(String strGenderDescription)
    
    /** Retrieve the Gender Type ID
     * @return Returns the strGenderTypeID.
     */
    /*public String getGenderTypeID() {
        return strGenderTypeID;
    }//end of getGenderTypeID()*/
    
    /** Sets the Gender Type ID
     * @param strGenderTypeID The strGenderTypeID to set.
     */
    /*public void setGenderTypeID(String strGenderTypeID) {
        this.strGenderTypeID = strGenderTypeID;
    }//end of setGenderTypeID(String strGenderTypeID)*/
    
    /**
     * Retrieve the Available SumInsured
     * @return  bdAvailSumInsured BigDecimal
     */
    public BigDecimal getAvailSumInsured() {
        return bdAvailSumInsured;
    }//end of getAvailSumInsured()
    
    /**
     * Sets the Available SumInsured
     * @param  bdAvailSumInsured BigDecimal  
     */
    public void setAvailSumInsured(BigDecimal bdAvailSumInsured) {
        this.bdAvailSumInsured = bdAvailSumInsured;
    }//end of setAvailSumInsured(BigDecimal bdAvailSumInsured)
    
    /**
     * Retrieve the Total SumInsured
     * @return  bdTotalSumInsured BigDecimal
     */
    public BigDecimal getTotalSumInsured() {
        return bdTotalSumInsured;
    }//end of getTotalSumInsured()
    
    /**
     * Sets the Total SumInsured
     * @param  bdTotalSumInsured BigDecimal  
     */
    public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
        this.bdTotalSumInsured = bdTotalSumInsured;
    }//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)
    
    /**
     * Retrieve the Category Type ID
     * @return  strCategoryTypeID String
     */
    public String getCategoryTypeID() {
        return strCategoryTypeID;
    }//end of getCategoryTypeID()
    
    /**
     * Sets the Category Type ID
     * @param  strCategoryTypeID String  
     */
    public void setCategoryTypeID(String strCategoryTypeID) {
        this.strCategoryTypeID = strCategoryTypeID;
    }//end of setCategoryTypeID(String strCategoryTypeID)
    
    /**
     * Retrieve the SumEnhanced Image Name
     * @return  strSumEnhancedImageName String
     */
    public String getSumEnhancedImageName() {
        return strSumEnhancedImageName;
    }//end of getSumEnhancedImageName()
    
    /**
     * Sets the SumEnhanced Image Name
     * @param  strSumEnhancedImageName String  
     */
    public void setSumEnhancedImageName(String strSumEnhancedImageName) {
        this.strSumEnhancedImageName = strSumEnhancedImageName;
    }//end of setSumEnhancedImageName(String strSumEnhancedImageName)
    
    /**
     * Retrieve the SumEnhanced Image Title
     * @return  strSumEnhancedImageTitle String
     */
    public String getSumEnhancedImageTitle() {
        return strSumEnhancedImageTitle;
    }//end of getSumEnhancedImageTitle()
    
    /**
     * Sets the SumEnhanced Image Title
     * @param  strSumEnhancedImageTitle String  
     */
    public void setSumEnhancedImageTitle(String strSumEnhancedImageTitle) {
        this.strSumEnhancedImageTitle = strSumEnhancedImageTitle;
    }//end of setSumEnhancedImageTitle(String strSumEnhancedImageTitle)
    
    /**
     * Retrieve the SumEnhancedYN
     * @return  strSumEnhancedYN String
     */
    public String getSumEnhancedYN() {
        return strSumEnhancedYN;
    }//end of getSumEnhancedYN()
    
    /**
     * Sets the SumEnhancedYN
     * @param  strSumEnhancedYN String  
     */
    public void setSumEnhancedYN(String strSumEnhancedYN) {
        this.strSumEnhancedYN = strSumEnhancedYN;
    }//end of setSumEnhancedYN(String strSumEnhancedYN)

	/** Retrieve the Notification Phone Nbr
	 * @return Returns the strNotifyPhoneNbr.
	 */
	public String getNotifyPhoneNbr() {
		return strNotifyPhoneNbr;
	}//end of getNotifyPhoneNbr()

	/** Sets the Notification Phone Nbr
	 * @param strNotifyPhoneNbr The strNotifyPhoneNbr to set.
	 */
	public void setNotifyPhoneNbr(String strNotifyPhoneNbr) {
		this.strNotifyPhoneNbr = strNotifyPhoneNbr;
	}//end of setNotifyPhoneNbr(String strNotifyPhoneNbr)
	//change on DEC 6th KOC 1136
	public void setVipYN(String vipYN) {
		this.vipYN = vipYN;
	}

	public String getVipYN() {
		return vipYN;
	}//change on DEC 6th KOC 1136
}//end of ClaimantVO.java