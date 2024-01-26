/**
 * @ (#) SupportVO.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : SupportVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class SupportVO extends PreAuthVO {
    
    private Long lngSeqID = null;
    private String strDocumentTypeID = "";
    private String strShortfallNo = "";
    private String strShortfallDesc = "";
    private String strStatusDesc = "";
    private String strInvestigationNo = "";
    private String strInvestigatedBy = "";
    private Date dtDocumentDate = null;
    private String strReasonTypeID="";
    private String strInvestAgencyDesc = "";
    private String strBranchName = "";
    private String strInvShortfallNo = "";
    private String strPreAuthClaimNo = "";
    private Date dtCompletedDate = null;
    private String strTypeDesc = "";
    private String strEditYN = "";//Investigation EditYN
    private String strRefNbr = "";
    
    /** Retrieve the RefNbr
	 * @return Returns the strRefNbr.
	 */
	public String getRefNbr() {
		return strRefNbr;
	}//end of getRefNbr()

	/** Sets the RefNbr
	 * @param strRefNbr The strRefNbr to set.
	 */
	public void setRefNbr(String strRefNbr) {
		this.strRefNbr = strRefNbr;
	}//end of setRefNbr(String strRefNbr)

	/** Retrieve the Edit YN
	 * @return Returns the strEditYN.
	 */
	public String getEditYN() {
		return strEditYN;
	}//end of getEditYN()

	/** Sets the Edit YN
	 * @param strEditYN The strEditYN to set.
	 */
	public void setEditYN(String strEditYN) {
		this.strEditYN = strEditYN;
	}//end of setEditYN(String strEditYN)

	/** Retrieve the Type Description
	 * @return Returns the strTypeDesc.
	 */
	public String getTypeDesc() {
		return strTypeDesc;
	}//end of getTypeDesc()

	/** Sets the Type Description
	 * @param strTypeDesc The strTypeDesc to set.
	 */
	public void setTypeDesc(String strTypeDesc) {
		this.strTypeDesc = strTypeDesc;
	}//end of setTypeDesc(String strTypeDesc)
    
    /** Retrieve the Completed Date
	 * @return Returns the dtCompletedDate.
	 */
	public Date getCompletedDate() {
		return dtCompletedDate;
	}//end of getCompletedDate()

	/** Sets the Completed Date
	 * @param dtCompletedDate The dtCompletedDate to set.
	 */
	public void setCompletedDate(Date dtCompletedDate) {
		this.dtCompletedDate = dtCompletedDate;
	}//end of setCompletedDate(Date dtCompletedDate)

	/** Retrieve the PreAuth/ClaimNo.
	 * @return Returns the strPreAuthClaimNo.
	 */
	public String getPreAuthClaimNo() {
		return strPreAuthClaimNo;
	}//end of getPreAuthClaimNo()

	/** Sets the PreAuth/ClaimNo.
	 * @param strPreAuthClaimNo The strPreAuthClaimNo to set.
	 */
	public void setPreAuthClaimNo(String strPreAuthClaimNo) {
		this.strPreAuthClaimNo = strPreAuthClaimNo;
	}//end of setPreAuthClaimNo(String strPreAuthClaimNo)

	/** Retrieve the Investigation/Shortfall No.
	 * @return Returns the strInvShortfallNo.
	 */
	public String getInvShortfallNo() {
		return strInvShortfallNo;
	}//end of getInvShortfallNo()

	/** Sets the Investigation/Shortfall No.
	 * @param strInvShortfallNo The strInvShortfallNo to set.
	 */
	public void setInvShortfallNo(String strInvShortfallNo) {
		this.strInvShortfallNo = strInvShortfallNo;
	}//end of setInvShortfallNo(String strInvShortfallNo)

	/** Retrieve the Branch Name
	 * @return Returns the strBranchName.
	 */
	public String getBranchName() {
		return strBranchName;
	}//end of getBranchName()

	/** Sets the Branch Name
	 * @param strBranchName The strBranchName to set.
	 */
	public void setBranchName(String strBranchName) {
		this.strBranchName = strBranchName;
	}//end of setBranchName(String strBranchName)

	/** Retrieve the Investigation Agency Desciption
	 * @return Returns the strInvestAgencyDesc.
	 */
	public String getInvestAgencyDesc() {
		return strInvestAgencyDesc;
	}//end of getInvestAgencyDesc()

	/** Sets the Investigation Agency Desciption
	 * @param strInvestAgencyDesc The strInvestAgencyDesc to set.
	 */
	public void setInvestAgencyDesc(String strInvestAgencyDesc) {
		this.strInvestAgencyDesc = strInvestAgencyDesc;
	}//end of setInvestAgencyDesc(String strInvestAgencyDesc)

	/**
     * Retrieve the Reason Type ID
     * @return  strReasonTypeID String
     */
    public String getReasonTypeID() {
        return strReasonTypeID;
    }//end of getReasonTypeID()
    
    /**
     * Sets the Reason Type ID
     * @param  strReasonTypeID String  
     */
    public void setReasonTypeID(String strReasonTypeID) {
        this.strReasonTypeID = strReasonTypeID;
    }//end  of setReasonTypeID(String strReasonTypeID)
    
    /** Retrieve the Document Date
     * @return Returns the dtDocumentDate.
     */
    public String getDocumentDateTime() {
        return TTKCommon.getFormattedDateHour(dtDocumentDate);
    }//end of getDocumentDateTime()
    
    /** Retrieve the Document Date
     * @return Returns the dtDocumentDate.
     */
    public Date getDocumentDate() {
    	return dtDocumentDate;
    }//end of getDocumentDate()
    
    /** Sets the Document Date
     * @param dtDocumentDate The dtDocumentDate to set.
     */
    public void setDocumentDate(Date dtDocumentDate) {
        this.dtDocumentDate = dtDocumentDate;
    }//end of setDocumentDate(Date dtDocumentDate)
    
    /** Retrieve the Seq ID 
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()
    
    /** Sets the Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)
    
    /** Retrieve the Document Type ID
     * @return Returns the strDocumentTypeID.
     */
    public String getDocumentTypeID() {
        return strDocumentTypeID;
    }//end of getDocumentTypeID()
    
    /** Sets the Document Type ID
     * @param strDocumentTypeID The strDocumentTypeID to set.
     */
    public void setDocumentTypeID(String strDocumentTypeID) {
        this.strDocumentTypeID = strDocumentTypeID;
    }//end of setDocumentTypeID(String strDocumentTypeID)
    
    /** Retrieve the Investigation No.
     * @return Returns the strInvestigationNo.
     */
    public String getInvestigationNo() {
        return strInvestigationNo;
    }//end of getInvestigationNo()
    
    /** Sets the Investigation No.
     * @param strInvestigationNo The strInvestigationNo to set.
     */
    public void setInvestigationNo(String strInvestigationNo) {
        this.strInvestigationNo = strInvestigationNo;
    }//end of setInvestigationNo(String strInvestigationNo)
    
    /** Retrieve the Shortfall Description
     * @return Returns the strShortfallDesc.
     */
    public String getShortfallDesc() {
        return strShortfallDesc;
    }//end of getShortfallDesc()
    
    /** Sets the Shortfall Description
     * @param strShortfallDesc The strShortfallDesc to set.
     */
    public void setShortfallDesc(String strShortfallDesc) {
        this.strShortfallDesc = strShortfallDesc;
    }//end of setShortfallDesc(String strShortfallDesc)
    
    /** Retrieve the Shortfall No.
     * @return Returns the strShortfallNo.
     */
    public String getShortfallNo() {
        return strShortfallNo;
    }//end of getShortfallNo()
    
    /** Sets the Shortfall No.
     * @param strShortfallNo The strShortfallNo to set.
     */
    public void setShortfallNo(String strShortfallNo) {
        this.strShortfallNo = strShortfallNo;
    }//end of setShortfallNo(String strShortfallNo)
    
    /** Retrieve the Status Description
     * @return Returns the strStatusDesc.
     */
    public String getStatusDesc() {
        return strStatusDesc;
    }//end of getStatusDesc()
    
    /** Sets the Status Description
     * @param strStatusDesc The strStatusDesc to set.
     */
    public void setStatusDesc(String strStatusDesc) {
        this.strStatusDesc = strStatusDesc;
    }//end of setStatusDesc(String strStatusDesc)
    
    /** Retrieve the Investigated By
     * @return Returns the strInvestigatedBy.
     */
    public String getInvestigatedBy() {
        return strInvestigatedBy;
    }//end of getValidatedBy()
    
    /** Sets the Investigated By
     * @param strInvestigatedBy The strInvestigatedBy to set.
     */
    public void setInvestigatedBy(String strInvestigatedBy) {
        this.strInvestigatedBy = strInvestigatedBy;
    }//end of setValidatedBy(String strInvestigatedBy)
}//end of SupportVO
