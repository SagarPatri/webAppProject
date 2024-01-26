/**
 * @ (#) PolicyVO.java Jan 31, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PolicyVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jan 31, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class PolicyVO extends BaseVO {
   
    private Long lngPolicySeqID = null;
    private Long lngPolicyGroupSeqID = null;
    private Long lngEndorsementSeqID = null;
    private String strPolicyNbr = "";
    private String strEnrollmentNbr = "";
    private String strEndorsementNbr = "";
    private String strCustEndorsementNbr = "";
    private String strMemberName = "";
    private String strOfficeCode = "";
    private String strBatchNbr = "";
    private String strGroupName = "";
    private String strGroupID = "";
    private String strGrpID = ""; // For Browsing the files in Webconfig
    private String strCorporateName = "";
    private Long lngBatchSeqID = null;
    private String strPolicyTypeID = "";
    private String strEnrollmentDesc = "";
    private String strPrevPolicyNbr = "";
    private String strWorkflow = "";
    private String strAgentCode = "";
    private String strReview = "";
    private String strTPAStatusTypeID = "";
    private String strImageName = "Blank";
    private String strImageTitle = "";
    private Long lngMemAssocSeqID = null;
    private Long lngMemberSeqID = null;
    private Long lngMemMatchSeqID = null;
    private String strAssociateYN = "";
    private String strPolicyYN = "";
    private String strCompanyName = "";
    private String strOfficeName = "";
    private String strStatus = "";
    private String strEnrollmentType = "";
    private String strFlag = "";//Used in Inward Entry - Batch Policies List
    private String strBatchEntryCompleteYN ="";
    private String strBatchCompleteYN = "";
    private Long lngProdPolicySeqID = null;
    private String strCompletedYN = "";
    private String strClausesImageName = "HistoryIcon";
    private String strClausesImageTitle = "Clause Details";
    private String strZoneCode="";
    private String strDevOffCode="";
		// Changes done for CR KOC1170
	private String strPolicyHolderCode="";

//Change as per KOC 1216B
	 private String  strEnrollmentID="";
	 private String  strMemberBuffer="";
	 private String  strInsuredName="";
	 private String  strTotalMemberBuffer="";
	 private String capitationflag = "";
     private String policyCategory="";
	 private String policyCategoryRemarks="";
	 private ArrayList<String> alprevPolicyCategory = null ;
	 private String previouspolicyCategoryRemarks = null;
	 private String previouspolicyCategory = null;
	 
	 private Long lngInsuranceSeqID = null;

	 private String vidalMemId="";
	 private String gnMemId="";
	 
	 //Change as Administration ->Schemes
	 private String portedPolicyStatus="";
	 
	 private String ProductName = "";
	 private String productAuthority = "";
	 private String sEmiratesID="";
	 private String dhpoMemberId;
	 
	
    public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductAuthority() {
		return productAuthority;
	}


	public void setProductAuthority(String productAuthority) {
		this.productAuthority = productAuthority;
	}

	 	public String getPreviouspolicyCategory() {
		return previouspolicyCategory;
	}
	 	
	public void setPreviouspolicyCategory(String previouspolicyCategory) {
		this.previouspolicyCategory = previouspolicyCategory;
	}


		public String getPreviouspolicyCategoryRemarks() {
			return previouspolicyCategoryRemarks;
		}


		public void setPreviouspolicyCategoryRemarks(
				String previouspolicyCategoryRemarks) {
			this.previouspolicyCategoryRemarks = previouspolicyCategoryRemarks;
		}


		public ArrayList<String> getAlprevPolicyCategory() {
			return alprevPolicyCategory;
		}


		public void setAlprevPolicyCategory(ArrayList<String> alprevPolicyCategory) {
			this.alprevPolicyCategory = alprevPolicyCategory;
		}


		public Long getLngInsuranceSeqID() {
			return lngInsuranceSeqID;
		}


		public void setLngInsuranceSeqID(Long lngInsuranceSeqID) {
			this.lngInsuranceSeqID = lngInsuranceSeqID;
		}


		public String getPolicyCategoryRemarks() {
			return policyCategoryRemarks;
		}


		public void setPolicyCategoryRemarks(String policyCategoryRemarks) {
			this.policyCategoryRemarks = policyCategoryRemarks;
		}


		public String getPolicyCategory() {
			return policyCategory;
		}


		public void setPolicyCategory(String policyCategory) {
			this.policyCategory = policyCategory;
		}




	
	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName) {
		this.strInsuredName = insuredName;
	}

	/**
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return strInsuredName;
	}

	/**
	 * @param memberBuffer the memberBuffer to set
	 */
	public void setMemberBuffer(String memberBuffer) {
		this.strMemberBuffer = memberBuffer;
	}

	/**
	 * @return the memberBuffer
	 */
	public String getMemberBuffer() {
		return strMemberBuffer;
	}

	/**
	 * @param enrollmentID the enrollmentID to set
	 */
	public void setEnrollmentID(String enrollmentID) {
		this.strEnrollmentID = enrollmentID;
	}

	/**
	 * @return the enrollmentID
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}
	
	/** This method returns the PolicyHolderCode
	 * @return the strPolicyHolderCode
	 */
	public String getPolicyHolderCode() {
		return strPolicyHolderCode;
	}//end of getPolicyHolderCode()

	/** This method sets the PolicyHolderCode
	 * @param strPolicyHolderCode the strPolicyHolderCode to set
	 */
	public void setPolicyHolderCode(String strPolicyHolderCode) {
		this.strPolicyHolderCode = strPolicyHolderCode;
	}//end of setPolicyHolderCode(String strPolicyHolderCode)
    //End changes done for CR KOC1170
    
    /** Retrieve the GrpID
	 * @return Returns the strGrpID.
	 */
	public String getGrpID() {
		return strGrpID;
	}//end of getGrpID()

	/** Sets the GrpID
	 * @param strGrpID The strGrpID to set.
	 */
	public void setGrpID(String strGrpID) {
		this.strGrpID = strGrpID;
	}//end of setGrpID(String strGrpID)

	/** Retrieve the ImageName
     * @return Returns the strClausesImageName.
     */
    public String getClausesImageName() {
		return strClausesImageName;
	}//end of getClausesImageName()
    
    /** Sets the ImageName
     * @param strClausesImageName The strClausesImageName to set.
     */
	public void setClausesImageName(String strClausesImageName) {
		this.strClausesImageName = strClausesImageName;
	}//end of setClausesImageName(String strClausesImageName)
	
	/** Retrieve the ImageTitle
     * @return Returns the strClausesImageTitle.
     */
	public String getClausesImageTitle() {
		return strClausesImageTitle;
	}//end of getClausesImageTitle()
	
	/** Sets the ImageTitle
     * @param strClausesImageTitle The strClausesImageTitle to set.
     */
	public void setClausesImageTitle(String strClausesImageTitle) {
		this.strClausesImageTitle = strClausesImageTitle;
	}//end of setClausesImageTitle(String strClausesImageTitle)
        
    /** Retrieve the CompletedYN
	 * @return Returns the strCompletedYN.
	 */
	public String getCompletedYN() {
		return strCompletedYN;
	}//end of getCompletedYN()

	/** Sets the CompletedYN
	 * @param strCompletedYN The strCompletedYN to set.
	 */
	public void setCompletedYN(String strCompletedYN) {
		this.strCompletedYN = strCompletedYN;
	}//end of setCompletedYN(String strCompletedYN)

	/** This method returns the Batch Sequence ID 
     * @return Returns the lngBatchSeqID.
     */
    public Long getBatchSeqID() {
        return lngBatchSeqID;
    }//end of getBatchSeqID()
    
    /** This method sets the Batch Sequence ID 
     * @param lngBatchSeqID The lngBatchSeqID to set.
     */
    public void setBatchSeqID(Long lngBatchSeqID) {
        this.lngBatchSeqID = lngBatchSeqID;
    }//end of setLngBatchSeqID(Long lngBatchSeqID)
    
    /** This method returns the Policy Sequence ID
     * @return Returns the lngPolicySeqID.
     */
    public Long getPolicySeqID() {
        return lngPolicySeqID;
    }//end of getPolicySeqID()
    
    /** This method sets the Policy Sequence ID
     * @param lngPolicySeqID The lngPolicySeqID to set.
     */
    public void setPolicySeqID(Long lngPolicySeqID) {
        this.lngPolicySeqID = lngPolicySeqID;
    }//end of setPolicySeqID(Long lngPolicySeqID)
    
    /** This method returns the Batch Number
     * @return Returns the strBatchNbr.
     */
    public String getBatchNbr() {
        return strBatchNbr;
    }//end of getBatchNbr()
    
    /** This method sets the Batch Number
     * @param strBatchNbr The strBatchNbr to set.
     */
    public void setBatchNbr(String strBatchNbr) {
        this.strBatchNbr = strBatchNbr;
    }//end of setBatchNbr(String strBatchNbr)
    
    /** This method returns the Corporate Name
     * @return Returns the strCorporateName.
     */
    public String getCorporateName() {
        return strCorporateName;
    }//end of getCorporateName()
    
    /** This method sets the Corporate Name
     * @param strCorporateName The strCorporateName to set.
     */
    public void setCorporateName(String strCorporateName) {
        this.strCorporateName = strCorporateName;
    }//end of setCorporateName(String strCorporateName)
    
    /** This method returns the Endorsement Number
     * @return Returns the strEndorsementNbr.
     */
    public String getEndorsementNbr() {
        return strEndorsementNbr;
    }//end of getEndorsementNbr()
    
    /** This method sets the Endorsement Number
     * @param strEndorsementNbr The strEndorsementNbr to set.
     */
    public void setEndorsementNbr(String strEndorsementNbr) {
        this.strEndorsementNbr = strEndorsementNbr;
    }//end of setEndorsementNbr(String strEndorsementNbr)
    
    /** This method returns the Enrollment Number
     * @return Returns the strEnrollmentNbr.
     */
    public String getEnrollmentNbr() {
        return strEnrollmentNbr;
    }//end of getEnrollmentNbr()
    
    /** This method sets the Enrollment Number
     * @param strEnrollmentNbr The strEnrollmentNbr to set.
     */
    public void setEnrollmentNbr(String strEnrollmentNbr) {
        this.strEnrollmentNbr = strEnrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)
    
    /** This method returns the Group ID
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()
    
    /** This method sets the Group ID
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)
    
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
    
    /** This method returns the Member Name
     * @return Returns the strMemberName.
     */
    public String getMemberName() {
        return strMemberName;
    }//end of getMemberName()
    
    /** This method sets the Member Name
     * @param strMemberName The strMemberName to set.
     */
    public void setMemberName(String strMemberName) {
        this.strMemberName = strMemberName;
    }//end of setMemberName(String strMemberName)
    
    /** This method returns the Office Code
     * @return Returns the strOfficeCode.
     */
    public String getOfficeCode() {
        return strOfficeCode;
    }//end of getOfficeCode()
    
    /** This method sets the Office Code
     * @param strOfficeCode The strOfficeCode to set.
     */
    public void setOfficeCode(String strOfficeCode) {
        this.strOfficeCode = strOfficeCode;
    }//end of setOfficeCode(String strOfficeCode)
    
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }//end of getPolicyNbr()
    
    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)
    
    /** This method returns the Policy Type
     * @return Returns the strPolicyTypeID.
     */
    public String getPolicyTypeID() {
        return strPolicyTypeID;
    }//end of getPolicyTypeID()
    
    /** This method sets the Policy Type
     * @param strPolicyTypeID The strPolicyTypeID to set.
     */
    public void setPolicyTypeID(String strPolicyTypeID) {
        this.strPolicyTypeID = strPolicyTypeID;
    }//end of setPolicyTypeID(String strPolicyTypeID)
    
    /** This method returns the Previous Policy Number
     * @return Returns the strPrevPolicyNbr.
     */
    public String getPrevPolicyNbr() {
        return strPrevPolicyNbr;
    }//end of getPrevPolicyNbr()
    
    /** This method sets the Previous Policy Number
     * @param strPrevPolicyNbr The strPrevPolicyNbr to set.
     */
    public void setPrevPolicyNbr(String strPrevPolicyNbr) {
        this.strPrevPolicyNbr = strPrevPolicyNbr;
    }//end of setPrevPolicyNbr(String strPrevPolicyNbr)
    
    /** This method returns the Workflow
     * @return Returns the strWorkflow.
     */
    public String getWorkflow() {
        return strWorkflow;
    }//end of getWorkflow()
    
    /** This method sets the Workflow
     * @param strWorkflow The strWorkflow to set.
     */
    public void setWorkflow(String strWorkflow) {
        this.strWorkflow = strWorkflow;
    }//end of setWorkflow(String strWorkflow)
    
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
    
    /** This method returns the Policy Group Seq ID
     * @return Returns the lngPolicyGroupSeqID.
     */
    public Long getPolicyGroupSeqID() {
        return lngPolicyGroupSeqID;
    }//end of getPolicyGroupSeqID()
    
    /** This method sets the Policy Group Seq ID
     * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
     */
    public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
        this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
    }//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)
    
    /** This method returns the Enrollment Description
     * @return Returns the strEnrollmentDesc.
     */
    public String getEnrollmentDesc() {
        return strEnrollmentDesc;
    }//end of getEnrollmentDesc()
    
    /** This method sets the Enrollment Description
     * @param strEnrollmentDesc The strEnrollmentDesc to set.
     */
    public void setEnrollmentDesc(String strEnrollmentDesc) {
        this.strEnrollmentDesc = strEnrollmentDesc;
    }//end of setEnrollmentDesc(String strEnrollmentDesc)
    
    /** This method returns the Agent Code
     * @return Returns the strAgentCode.
     */
    public String getAgentCode() {
        return strAgentCode;
    }//end of getAgentCode()
    
    /** This method sets the Agent Code
     * @param strAgentCode The strAgentCode to set.
     */
    public void setAgentCode(String strAgentCode) {
        this.strAgentCode = strAgentCode;
    }//end of setAgentCode(String strAgentCode)
    
    /** This method returns the Review
     * @return Returns the strReview.
     */
    public String getReview() {
        return strReview;
    }//end of getReview()
    
    /** This method sets the Review
     * @param strReview The strReview to set.
     */
    public void setReview(String strReview) {
        this.strReview = strReview;
    }//end of setReview(String strReview)
    
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
    
    /** This method returns the Customer Endorsement Number
     * @return Returns the strCustEndorsementNbr.
     */
    public String getCustEndorsementNbr() {
        return strCustEndorsementNbr;
    }//end of getCustEndorsementNbr()
    
    /** This method sets the Customer Endorsement Number
     * @param strCustEndorsementNbr The strCustEndorsementNbr to set.
     */
    public void setCustEndorsementNbr(String strCustEndorsementNbr) {
        this.strCustEndorsementNbr = strCustEndorsementNbr;
    }//end of setCustEndorsementNbr(String strCustEndorsementNbr)
    
    /** This method returns the Memeber Associate Seq ID
     * @return Returns the lngMemAssocSeqID.
     */
    public Long getMemAssocSeqID() {
        return lngMemAssocSeqID;
    }//end of getMemAssocSeqID()
    
    /** This method sets the Memeber Associate Seq ID
     * @param lngMemAssocSeqID The lngMemAssocSeqID to set.
     */
    public void setMemAssocSeqID(Long lngMemAssocSeqID) {
        this.lngMemAssocSeqID = lngMemAssocSeqID;
    }//end of setMemAssocSeqID(Long lngMemAssocSeqID)
    
    /** This method returns the Memeber Seq ID
     * @return Returns the lngMemberSeqID.
     */
    public Long getMemberSeqID() {
        return lngMemberSeqID;
    }//end of getMemberSeqID()
    
    /** This method sets the Memeber Seq ID
     * @param lngMemberSeqID The lngMemberSeqID to set.
     */
    public void setMemberSeqID(Long lngMemberSeqID) {
        this.lngMemberSeqID = lngMemberSeqID;
    }//end of setMemberSeqID(Long lngMemberSeqID)
    
    /** This method returns the Member match Seq ID
     * @return Returns the lngMemMatchSeqID.
     */
    public Long getMemMatchSeqID() {
        return lngMemMatchSeqID;
    }//end of getMemMatchSeqID()
    
    /** This method sets the Member Match Seq ID 
     * @param lngMemMatchSeqID The lngMemMatchSeqID to set.
     */
    public void setMemMatchSeqID(Long lngMemMatchSeqID) {
        this.lngMemMatchSeqID = lngMemMatchSeqID;
    }//end of setMemMatchSeqID(Long lngMemMatchSeqID)
    
    /** This method returns the Associate YN
     * @return Returns the strAssociateYN.
     */
    public String getAssociateYN() {
        return strAssociateYN;
    }//end of getAssociateYN()
    
    /** This method sets the Associate YN
     * @param strAssociateYN The strAssociateYN to set.
     */
    public void setAssociateYN(String strAssociateYN) {
        this.strAssociateYN = strAssociateYN;
    }//end of setAssociateYN(String strAssociateYN)
    
    /** This method returns the PolicyYN
     * @return Returns the strPolicyYN.
     */
    public String getPolicyYN() {
        return strPolicyYN;
    }//end of getPolicyYN()
    
    /** This method sets the PolicyYN
     * @param strPolicyYN The strPolicyYN to set.
     */
    public void setPolicyYN(String strPolicyYN) {
        this.strPolicyYN = strPolicyYN;
    }//end of setPolicyYN(String strPolicyYN)
    
    /** This method returns the Company Name
     * @return Returns the strCompanyName.
     */
    public String getCompanyName() {
        return strCompanyName;
    }//end of getCompanyName()
    
    /** This method sets the Company Name
     * @param strCompanyName The strCompanyName to set.
     */
    public void setCompanyName(String strCompanyName) {
        this.strCompanyName = strCompanyName;
    }//end of setCompanyName(String strCompanyName)
    
    /** This method returns the Office Name
     * @return Returns the strOfficeName.
     */
    public String getOfficeName() {
        return strOfficeName;
    }//end of getOfficeName()
    
    /** This method sets the Office Name
     * @param strOfficeName The strOfficeName to set.
     */
    public void setOfficeName(String strOfficeName) {
        this.strOfficeName = strOfficeName;
    }//end of setOfficeName(String strOfficeName)
    
    /** This method returns the Status
     * @return strStatus String Status 
     */
    public String getStatus() {
        return strStatus;
    }//end of getStatus()
    
    /** This method Sets the Status
     * @param strStatus String Status
     */
    public void setStatus(String strStatus) {
        this.strStatus = strStatus;
    }//end of setStatus(String strStatus)
    
    /** This method returns the the EnrollMent Type
     * @return strEnrollMentType String EnrollMent Type
     */
    public String getEnrollmentType() {
        return strEnrollmentType;
    }//end of getEnrollMentType()
    
    /** This method sets the EnrollMent Type
     * @param strEnrollMentType String EnrollMent Type
     */
    public void setEnrollmentType(String strEnrollmentType) {
        this.strEnrollmentType = strEnrollmentType;
    }//end of setEnrollMentType(String strEnrollmentType)
    
    /** This method returns the Flag
     * @return Returns the strFlag.
     */
    public String getFlag() {
        return strFlag;
    }//end of getFlag()
    
    /** This method sets the Flag
     * @param strFlag The strFlag to set.
     */
    public void setFlag(String strFlag) {
        this.strFlag = strFlag;
    }//end of setFlag(String strFlag)

	/** This method returns the Batch Entry Complete Yes or No 
	 * @return Returns the strBatchEntryCompleteYN.
	 */
	public String getBatchEntryCompleteYN() {
		return strBatchEntryCompleteYN;
	}// End of getBtachEntryCompleteYN()

	/** This method sets the Batch Entry Complete Yes or No 
	 * @param strBtachEntryCompleteYN The strBtachEntryCompleteYN to set.
	 */
	public void setBatchEntryCompleteYN(String strBatchEntryCompleteYN) {
		this.strBatchEntryCompleteYN = strBatchEntryCompleteYN;
	}// End of  setBtachEntryCompleteYN(String strBtachEntryCompleteYN)

	/** This method returns the Batch Completed Yes or No 
	 * @return Returns the strBatchCompleteYN.
	 */
	public String getBatchCompleteYN() {
		return strBatchCompleteYN;
	}// End of getBatchCompleteYN()

	/** This method sets the Batch Completed Yes or No 
	 * @param strBatchCompleteYN The strBatchCompleteYN to set.
	 */
	public void setBatchCompleteYN(String strBatchCompleteYN) {
		this.strBatchCompleteYN = strBatchCompleteYN;
	}//End of setBatchCompleteYN(String strBatchCompleteYN)
	
    /** This method returns the Product Policy Seq ID 
     * @return Returns the lngProdPolicySeqID.
     */
    public Long getProdPolicySeqID() {
        return lngProdPolicySeqID;
    }//end of getProdPolicySeqID()
    
    /** This method sets the Product Policy Seq ID
     * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
     */
    public void setProdPolicySeqID(Long lngProdPolicySeqID) {
        this.lngProdPolicySeqID = lngProdPolicySeqID;
    }//end of setProdPolicySeqID(Long lngProdPolicySeqID)

	/** This method returns the ZoneCode
	 * @return the strZoneCode
	 */
	public String getZoneCode() {
		return strZoneCode;
	}//end of getZoneCode()

	/** This method sets the ZoneCode
	 * @param strZoneCode the strZoneCode to set
	 */
	public void setZoneCode(String strZoneCode) {
		this.strZoneCode = strZoneCode;
	}//end of setZoneCode(String strZoneCode)

	/** This method returns the DevOffCode
	 * @return the strDevOffCode
	 */
	public String getDevOffCode() {
		return strDevOffCode;
	}//end of getDevOffCode()

	/** This method sets the DevOffCode
	 * @param strDevOffCode the strDevOffCode to set
	 */
	public void setDevOffCode(String strDevOffCode) {
		this.strDevOffCode = strDevOffCode;
	}//end of setDevOffCode(String strDevOffCode)

	public String getTotalMemberBuffer() {
		return strTotalMemberBuffer;
	}

	public void setTotalMemberBuffer(String strTotalMemberBuffer) {
		this.strTotalMemberBuffer = strTotalMemberBuffer;
	}
	public String getCapitationflag() {
		return capitationflag;
	}

	public void setCapitationflag(String capitationflag) {
		this.capitationflag = capitationflag;
	}


	public String getVidalMemId() {
		return vidalMemId;
	}


	public void setVidalMemId(String vidalMemId) {
		this.vidalMemId = vidalMemId;
	}


	public String getGnMemId() {
		return gnMemId;
	}


	public void setGnMemId(String gnMemId) {
		this.gnMemId = gnMemId;
	}


	public String getPortedPolicyStatus() {
		return portedPolicyStatus;
	}


	public void setPortedPolicyStatus(String portedPolicyStatus) {
		this.portedPolicyStatus = portedPolicyStatus;
	}
	public String getsEmiratesID() {
		return sEmiratesID;
	}
	public void setsEmiratesID(String sEmiratesID) {
		this.sEmiratesID = sEmiratesID;
	}
	public String getDhpoMemberId() {
		return dhpoMemberId;
	}
	public void setDhpoMemberId(String dhpoMemberId) {
		this.dhpoMemberId = dhpoMemberId;
	}
	
	
}//end of PolicyVO
