/**
 * @ (#) ClaimInwardVO.java Jul 14, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimInwardVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 14, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class ClaimInwardVO extends BaseVO {
	
	private Long lngInwardSeqID = null;
	private Long lngClaimSeqID = null;
	private String strInwardNbr = "";
	private String strEnrollmentID="";
	private String strClaimantName="";
	private String strGroupName = "";
	private String strClaimTypeDesc = "";
	private Date dtReceivedDate = null;
	private Long lngClmEnrollDtlSeqID = null;
	private String strClaimTypeID = "";
	private String strDocumentTypeID = "";
	
	/** Retrieve the DocumentTypeID
	 * @return Returns the strDocumentTypeID.
	 */
	public String getDocumentTypeID() {
		return strDocumentTypeID;
	}//end of getDocumentTypeID()
	
	/** Sets the DocumentTypeID
	 * @param strDocumentTypeID The strDocumentTypeID to set.
	 */
	public void setDocumentTypeID(String strDocumentTypeID) {
		this.strDocumentTypeID = strDocumentTypeID;
	}//end of setDocumentTypeID(String strDocumentTypeID)
	
	/** Retrieve the ClaimTypeID
	 * @return Returns the strClaimTypeID.
	 */
	public String getClaimTypeID() {
		return strClaimTypeID;
	}//end of getClaimTypeID()

	/** Sets the ClaimTypeID
	 * @param strClaimType The strClaimType to set.
	 */
	public void setClaimTypeID(String strClaimTypeID) {
		this.strClaimTypeID = strClaimTypeID;
	}//end of setClaimTypeID(String strClaimTypeID)

	/** Retrieve the ClmEnrollDtlSeqID
	 * @return Returns the lngClmEnrollDtlSeqID.
	 */
	public Long getClmEnrollDtlSeqID() {
		return lngClmEnrollDtlSeqID;
	}//end of getClmEnrollDtlSeqID()

	/** Sets the ClmEnrollDtlSeqID
	 * @param lngClmEnrollDtlSeqID The lngClmEnrollDtlSeqID to set.
	 */
	public void setClmEnrollDtlSeqID(Long lngClmEnrollDtlSeqID) {
		this.lngClmEnrollDtlSeqID = lngClmEnrollDtlSeqID;
	}//end of setClmEnrollDtlSeqID(Long lngClmEnrollDtlSeqID)

	/** Retrieve the ReceivedDate
	 * @return Returns the dtReceivedDate.
	 */
	public Date getReceivedDate() {
		return dtReceivedDate;
	}//end of getReceivedDate()
	
	/** Retrieve the ReceivedDate
	 * @return Returns the dtReceivedDate.
	 */
	public String getInwardReceivedDate() {
		return TTKCommon.getFormattedDate(dtReceivedDate);
	}//end of getInwardReceivedDate()
	
	/** Sets the ReceivedDate
	 * @param dtReceivedDate The dtReceivedDate to set.
	 */
	public void setReceivedDate(Date dtReceivedDate) {
		this.dtReceivedDate = dtReceivedDate;
	}//end of setReceivedDate(Date dtReceivedDate)
	
	/** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()
	
	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)
	
	/** Retrieve the InwardSeqID
	 * @return Returns the lngInwardSeqID.
	 */
	public Long getInwardSeqID() {
		return lngInwardSeqID;
	}//end of getInwardSeqID()
	
	/** Sets the InwardSeqID
	 * @param lngInwardSeqID The lngInwardSeqID to set.
	 */
	public void setInwardSeqID(Long lngInwardSeqID) {
		this.lngInwardSeqID = lngInwardSeqID;
	}//end of setInwardSeqID(Long lngInwardSeqID)
	
	/** Retrieve the ClaimantName
	 * @return Returns the strClaimantName.
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getClaimantName()
	
	/** Sets the ClaimantName
	 * @param strClaimantName The strClaimantName to set.
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setClaimantName(String strClaimantName)
	
	/** Retrieve the Claim Type Description
	 * @return Returns the strClaimTypeDesc.
	 */
	public String getClaimTypeDesc() {
		return strClaimTypeDesc;
	}//end of getClaimTypeDesc()
	
	/** Sets the Claim Type Description
	 * @param strClaimTypeDesc The strClaimTypeDesc to set.
	 */
	public void setClaimTypeDesc(String strClaimTypeDesc) {
		this.strClaimTypeDesc = strClaimTypeDesc;
	}//end of setClaimTypeDesc(String strClaimTypeDesc)
	
	/** Retrieve the EnrollmentID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()
	
	/** Sets the EnrollmentID
	 * @param strEnrollmentID The strEnrollmentID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)
	
	/** Retrieve the GroupName
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()
	
	/** Sets the GroupName
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)
	
	/** Retrieve the Inward Number
	 * @return Returns the strInwardNbr.
	 */
	public String getInwardNbr() {
		return strInwardNbr;
	}//end of getInwardNbr()
	
	/** Sets the Inward Number
	 * @param strInwardNbr The strInwardNbr to set.
	 */
	public void setInwardNbr(String strInwardNbr) {
		this.strInwardNbr = strInwardNbr;
	}//end of setInwardNbr(String strInwardNbr)
	
}//end of ClaimInwardVO
