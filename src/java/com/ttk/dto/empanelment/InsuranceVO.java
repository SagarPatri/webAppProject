/**
 * @ (#)  InsuranceVO.java Oct 22, 2005
 * Project      : TTKPROJECT
 * File         : InsuranceVO.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 22, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : Nagaraj D V
 * Modified date : Nov 11, 2005
 * Reason        : To add set/get AssociatedBranchList method
 */
package com.ttk.dto.empanelment;

import java.util.ArrayList;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class InsuranceVO extends BaseVO{               
	
	private String strCompanyName = ""; //Insurance Company Name
    private String strOfficeType="";   // Office Type
	private String strProdPolicyNo=""; // product/Policy Number
	private Long lngProdHospSeqId = null; //PRODUCT_HOSP_SEQ_ID
	private String strImageName ="HistoryIcon";//ImageName
	private String strImageTitle="Revision History"; //ImageTitle
	private Long lngInsuranceSeqID = null;//INS_SEQ_ID
    private Long lngAssociatedSeqId = null; //ASSOCIATED_SEQ_ID
    private Long lngParentSeqID = null; //INSURANCE_PARENT_SEQ_ID
    private String strCompanyCodeNbr = ""; //INS_COMP_CODE_NUMBER
	private Long lngTTKBranchCode=null;
    private String strCompanyAbbreviation="";
    private String strSectorTypeCode="";
    private String strSectorTypeDesc = "";
    private String strTTKBranch = "";
 	private String  strCompanyStatus="";
 	private String strActiveInactive = "";
	private Date dtEmpanelment=null;
	private String strGenTypeID = "";
    private ArrayList alBranches = null;
    private String strBranchName = "";
    private String  strAuthority="";
    private String regAuthority ="";
    private String sCompanyCode ="";
    private String payerAuthoriy = "";
	public String getPayerAuthoriy() {
		return payerAuthoriy;
	}
	public void setPayerAuthoriy(String payerAuthoriy) {
		this.payerAuthoriy = payerAuthoriy;
	}
    
    
    public String getStrAuthority() {
		return strAuthority;
	}
	public void setStrAuthority(String strAuthority) {
		this.strAuthority = strAuthority;
	}

	//Shortfall CR KOC1179
    private String strCompanyEmailID=""; // Insurance Company Email ID
    
    
    private String commonAuthorityCode = "";
    
    
    /** Retrieve the  Company Email ID
	 * @return the strCompanyEmailID
	 */
    public String getCompanyEmailID() {
		return strCompanyEmailID;
	}
    /** Sets the Company EmailID
	 * @param strCompanyEmailID the strCompanyEmailID to set
	 */ 
	public void setCompanyEmailID(String strCompanyEmailID) {
		this.strCompanyEmailID = strCompanyEmailID;
	}
	// End Shortfall CR KOC1179
    /** This method returns the Insurance Company Abbreviation
     * @return Returns the strCompanyAbberviation.
     */
    public String getCompanyAbbreviation() {
        return strCompanyAbbreviation;
    }// End of getCompanyAbbreviation()
    
    /** This method sets the Insurance Company Abbreviation
     * @param strCompanyAbberviation The strCompanyAbberviation to set.
     */
    public void setCompanyAbbreviation(String strCompanyAbberviation) {
        this.strCompanyAbbreviation = strCompanyAbberviation;
    }// End of setCompanyAbbreviation(String strCompanyAbberviation)
    
    /** This method returns the Insurance Sector Type ID
     * @return Returns the strSectorTypeCode.
     */
    public String getSectorTypeCode() {
        return strSectorTypeCode;
    }// End of getSectorTypeCode()
    
    /** This method sets the Insurance Sector Type ID
     * @param strSectorTypeCode The strSectorTypeCode to set.
     */
    public void setSectorTypeCode(String strSectorTypeCode) {
        this.strSectorTypeCode = strSectorTypeCode;
    }//End of setSectorTypeCode(String strSectorTypeCode) 
    
    /** This method returns the TTK Branch Name
     * @return Returns the strTTKBranch.
     */
    public String getTTKBranch() {
        return strTTKBranch;
    }// End of getTTKBranch()
    
    /** This method sets the TTK Branch Name
     * @param strTTKBranch The strTTKBranch to set.
     */
    public void setTTKBranch(String strTTKBranch) {
        this.strTTKBranch = strTTKBranch;
    }// end of setTTKBranch(String strTTKBranch)
    
    /** This method returns the Empanelment Date
     * @return Returns the dtEmpanelment.
     */
	public Date getEmpanelmentDate() {
		return dtEmpanelment;
	}// End of getEmpanelment()
	
	/** This method returns the Empanelment Date
     * @return Returns the dtEmpanelment.
     */
	public String getFormattedEmpanelmentDate() {
		return TTKCommon.getFormattedDate(dtEmpanelment);
	}// End of getEmpanelment()

	/** This method sets the Empanelment Date
	 * @param dtEmpanelmentDate The dtEmpanelment to set.
	 */
	public void setEmpanelmentDate(Date dtEmpanelment) {
		this.dtEmpanelment = dtEmpanelment;
	}// End of setEmpanelmentDate(Date dtEmpanelment)
   
	/**
     * Sets the product/policy number
     * 
     * @param strProdPolicyNo String 
     */
    public void setProdPolicyNumber(String strProdPolicyNo){
        this.strProdPolicyNo = strProdPolicyNo;
    }//end of setProdPolicyNumber(String strProdPolicyNo)
    
    /**
     * Retrieve the product/policy number
     * 
     * @return strProdPolicyNo String 
     */
    public String getProdPolicyNumber()
    {
        return strProdPolicyNo; 
    }//end of getProdPolicyNumber()
    
	/** This method returns the Insurance Company name
	 * @return Returns the strCompanyName.
	 */
	public String getCompanyName() {
		return strCompanyName;
	}//End of getCompanyName()
	
	/**This Method sets the Insurance Comapany name
	 * @param strCompanyName The strCompanyName to set.
	 */
	public void setCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}//End of setCompanyName(String strCompanyName)

	/**
	 * This method returns the Product Hospital Sequence ID
	 * @return Returns the lProdHospSeqId.
	 */
	public Long getProdHospSeqId() {
		return lngProdHospSeqId;
	}// End of getProdHospSeqId(
	
	/**
	 * This method sets the Product Hospital Sequence ID
	 * @param lProdHospSeqId The lProdHospSeqId to set.
	 */
	public void setProdHospSeqId(Long lProdHospSeqId) {
		this.lngProdHospSeqId = lProdHospSeqId;
	}// End of setProdHospSeqId(Long lPprodHospSeqId)
	
	/**
	 * This method returns the ImageName
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}// End of getImageName()
	
	/**
	 * This method sets the Imagename
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}// End of setImageName(String strImageName) 
	
	/**
	 * This method returns the image name
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}// End of getImageTitle()
	
	/**
	 * This method sets the Image Title
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}// End of setImageTitle(String strImageTitle)
	
	/** This method returns the Insurance Sequence ID
	 * @return Returns the lInsuranceSeqID.
	 */
	public Long getInsuranceSeqID() {
		return lngInsuranceSeqID;
	}// End of getInsuranceSeqID()
	
	/** This method sets the Insurance Sequence ID
	 * @param lInsuranceSeqID The lInsuranceSeqID to set.
	 */
	public void setInsuranceSeqID(Long lInsuranceSeqID) {
        this.lngInsuranceSeqID = lInsuranceSeqID;
	}// End of setInsuranceSeqID(Long lInsuranceSeqID)
	
	/** This method returns the TTK Branch Code
	 * @return Returns the lTTKBranchCode.
	 */
	public Long getTTKBranchCode() {
		return lngTTKBranchCode;
	}// End of getTTKBranchCode()
	
	/** This method sets the TTK Branch Code
	 * @param strTtkBranchCode The lTTKBranchCode to set.
	 */
	public void setTTKBranchCode(Long lTTKBranchCode) {
		this.lngTTKBranchCode = lTTKBranchCode;
	}// End of setTTKBranchCode(String TTKBranchCode)
	
	/** This method returns the Status of the Insurance Comapny
	 * @return Returns the strCompanyStatus.
	 */
	public String getCompanyStatus() {
		return strCompanyStatus;
	}// End of getCompanyStatus()
	
	/** This method sets the Status of the Insurance Company
	 * @param strCompanyStatus The strCompanyStatus to set.
	 */
	public void setCompanyStatus(String strCompanyStatus) {
		this.strCompanyStatus = strCompanyStatus;
	}// End of setStrCompanyStatus(String strCompanyStatus)
	
	/** This method returns the General Type ID
	 * @return Returns the strGenTypeID.
	 */
	public String getGenTypeID() {
		return strGenTypeID;
	}// End of getGenTypeID()
	
	/** This method sets the Generel Type ID
	 * @param strGenTypeID The strGenTypeID to set.
	 */
	public void setGenTypeID(String strGenTypeID) {
		this.strGenTypeID = strGenTypeID;
	}// End of setGenTypeID(String strGenTypeID)
    
    /** This method returns the ArrayList of Branches
     * @return Returns the alBranches which contains associated insurance vo's.
     */
    public ArrayList getBranchList() {
        return alBranches;
    }// End of getBranchList()
    
    /** This method sets the ArraList Of Branches which contains associated insurance vo's
     * @param alBranches The alBranches to set.
     */
    public void setBranchList(ArrayList alBranches) {
        this.alBranches = alBranches;
    }// End of setBranchList(ArrayList alBranches)
    
    /** This method sets the Insurance Offfice Type
     * @param strPnsuranceOfficeType The strInsOfficeType to set.
     */
    public void setOfficeType(String strOfficeType) {
        this.strOfficeType = strOfficeType;
    }// End of setOfficeType(String strOfficeType)
    
    /** This method returns the  Insurance Office Type
     * @return Returns the strInsOfficeType.
     */
    public String getOfficeType() {
        return strOfficeType;
    }// End of getOfficeType()
    
    /**
     * This method returns  the Associated Sequence ID
     * @return Returns the lAssociatedSeqId.
     */
    public Long getAssociatedSeqId() {
        return lngAssociatedSeqId;
    }// End of getAssociatedSeqId()
    
    /**
     * This method sets the Associated Sequence ID
     * @param associatedSeqId The lAssociatedSeqId to set.
     */
    public void setAssociatedSeqId(Long lAssociatedSeqId) {
        this.lngAssociatedSeqId = lAssociatedSeqId;
    }// end of setAssociatedSeqId(Long associatedSeqId)

	/** This method returns the insurance company is Active / Inactive
	 * @return Returns the strActiveInactive.
	 */
	public String getActiveInactive() {
		return strActiveInactive;
	}// End of getActiveInactive()

	/** This method sets the insurance company is Active / Inactive
	 * @param strActiveInactive The strActiveInactive to set.
	 */
	public void setActiveInactive(String strActiveInactive) {
		this.strActiveInactive = strActiveInactive;
	}// End of setActiveInactive(String strActiveInactive)

	/** This method returns the Sector Type Description
	 * @return Returns the strSectorTypeDesc.
	 */
	public String getSectorTypeDesc() {
		return strSectorTypeDesc;
	}// End of getSectorTypeDesc()

	/** This method sets the Sector Type Description
	 * @param strSectorTypeDesc The strSectorTypeDesc to set.
	 */
	public void setSectorTypeDesc(String strSectorTypeDesc) {
		this.strSectorTypeDesc = strSectorTypeDesc;
	}//End of setSectorTypeDesc(String strSectorTypeDesc)

	/** This method returns the Insurance Parent Sequence ID
	 * @return Returns the lngParentSeqID.
	 */
	public Long getParentSeqID() {
		return lngParentSeqID;
	}// End of getParentSeqID()

	/** This method sets the Insurance Parent Sequence ID
	 * @param lngParentSeqID The lngParentSeqID to set.
	 */
	public void setParentSeqID(Long lngParentSeqID) {
		this.lngParentSeqID = lngParentSeqID;
	}// End of setParentSeqID(Long lngParentSeqID)

	/** This method returns the Insurance Company Code Number
	 * @return Returns the strCompanyCodeNbr.
	 */
	public String getCompanyCodeNbr() {
		return strCompanyCodeNbr;
	}// End of getCompanyCodeNbr()

	/** This method sets the Insurance Company Code NUmber
	 * @param strCompanyCodeNbr The strCompanyCodeNbr to set.
	 */
	public void setCompanyCodeNbr(String strCompanyCodeNbr) {
		this.strCompanyCodeNbr = strCompanyCodeNbr;
	}//End of setCompanyCodeNbr(String strCompanyCodeNbr)

	/** This method returns the Branch Company Name
	 * @return Returns the strBranchName.
	 */
	public String getBranchName() {
		return strBranchName;
	}//End of getBranchName()

	/** This method sets the Branch Company Name
	 * @param strBranchName The strBranchName to set.
	 */
	public void setBranchName(String strBranchName) {
		this.strBranchName = strBranchName;
	}//end of setBranchName(String strBranchName)
	public String getCommonAuthorityCode() {
		return commonAuthorityCode;
	}
	public void setCommonAuthorityCode(String commonAuthorityCode) {
		this.commonAuthorityCode = commonAuthorityCode;
	}
	/**
	 * @return the regAuthority
	 */
	public String getRegAuthority() {
		return regAuthority;
	}
	/**
	 * @param regAuthority the regAuthority to set
	 */
	public void setRegAuthority(String regAuthority) {
		this.regAuthority = regAuthority;
	}
	/**
	 * @return the sCompanyCode
	 */
	public String getsCompanyCode() {
		return sCompanyCode;
	}
	/**
	 * @param sCompanyCode the sCompanyCode to set
	 */
	public void setsCompanyCode(String sCompanyCode) {
		this.sCompanyCode = sCompanyCode;
	}
  	
}//end of class InsuranceVO
