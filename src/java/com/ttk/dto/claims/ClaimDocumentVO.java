/**
 * @ (#) ClaimDocumentVO.java Jul 14, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDocumentVO.java
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

import com.ttk.dto.BaseVO;

public class ClaimDocumentVO extends BaseVO {
	
	private Integer intSheetsCount = null;
	private String strDocumentTypeID = "";
	private String strReasonTypeID = "";
	private String strRemarks = "";
	private String strDocumentName = "";
	private String strDocumentListType = "";
	
	/** Retrieve the Document List Type ID
	 * @return Returns the strDocumentListType.
	 */
	public String getDocumentListType() {
		return strDocumentListType;
	}//end of getDocumentListType()

	/** Sets the Document List Type ID
	 * @param strDocumentListType The strDocumentListType to set.
	 */
	public void setDocumentListType(String strDocumentListType) {
		this.strDocumentListType = strDocumentListType;
	}//end of setDocumentListType(String strDocumentListType)

	/** Retrieve the Sheets Count
	 * @return Returns the intSheetsCount.
	 */
	public Integer getSheetsCount() {
		return intSheetsCount;
	}//end of getSheetsCount()
	
	/** Sets the Sheets Count
	 * @param intSheetsCount The intSheetsCount to set.
	 */
	public void setSheetsCount(Integer intSheetsCount) {
		this.intSheetsCount = intSheetsCount;
	}//end of setSheetsCount(Integer intSheetsintSheetsCount)
	
	/** Retrieve the DocumentName
	 * @return Returns the strDocumentName.
	 */
	public String getDocumentName() {
		return strDocumentName;
	}//end of getDocumentName()
	
	/** Sets the DocumentName
	 * @param strDocumentName The strDocumentName to set.
	 */
	public void setDocumentName(String strDocumentName) {
		this.strDocumentName = strDocumentName;
	}//end of setDocumentName(String strDocumentName)
	
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
	
	/** Retrieve the ReasonTypeID
	 * @return Returns the strReasonTypeID.
	 */
	public String getReasonTypeID() {
		return strReasonTypeID;
	}//end of getReasonTypeID()
	
	/** Sets the ReasonTypeID
	 * @param strReasonTypeID The strReasonTypeID to set.
	 */
	public void setReasonTypeID(String strReasonTypeID) {
		this.strReasonTypeID = strReasonTypeID;
	}//end of setReasonTypeID(String strReasonTypeID)
	
	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()
	
	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)
	
}//end of ClaimDocumentVO
