/**
 * @ (#) DocumentChecklistVO.java Jul 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : DocumentChecklistVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 17, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.claims;

import com.ttk.dto.preauth.PreAuthVO;

public class DocumentChecklistVO extends PreAuthVO{

	private String strDocumentName = "";
//	private Integer intSheetsCount = null;
	private String strSheetsCount = "";
	private String strDocumentTypeID = "";
	private String strDocumentListType = "";
	private String strReasonTypeID = "";
	private String strRemarks = "";
	private String strDocumentRcvdYN = "";
	private String strCategoryTypeID = "";
	private Long lngDocumentRcvdSeqID = null;
	private String strCategoryName = "";

	/** Retrieve the CategoryName
	 * @return Returns the strCategoryName.
	 */
	public String getCategoryName() {
		return strCategoryName;
	}//end of getCategoryName()

	/** Sets the CategoryName
	 * @param strCategoryName The strCategoryName to set.
	 */
	public void setCategoryName(String strCategoryName) {
		this.strCategoryName = strCategoryName;
	}//end of setCategoryName(String strCategoryName)

	/** Retrieve the Document Rcvd Seq ID
	 * @return Returns the lngDocumentRcvdSeqID.
	 */
	public Long getDocumentRcvdSeqID() {
		return lngDocumentRcvdSeqID;
	}//end of getDocumentRcvdSeqID()

	/** Sets the Document Rcvd Seq ID
	 * @param lngDocumentRcvdSeqID The lngDocumentRcvdSeqID to set.
	 */
	public void setDocumentRcvdSeqID(Long lngDocumentRcvdSeqID) {
		this.lngDocumentRcvdSeqID = lngDocumentRcvdSeqID;
	}//end of setDocumentRcvdSeqID(Long lngDocumentRcvdSeqID)

	/** Retrieve the Sheets Count
	 * @return Returns the strSheetsCount.
	 */
	public String getSheetsCount() {
		return strSheetsCount;
	}//end of getSheetsCount()

	/** Sets the Sheets Count
	 * @param strSheetsCount The strSheetsCount to set.
	 */
	public void setSheetsCount(String strSheetsCount) {
		this.strSheetsCount = strSheetsCount;
	}//end of setSheetsCount(String strSheetsCount)

	/** Sets the Sheets Count
	 * @param strSheetsCount The strSheetsCount to set.
	 *//*
	public void setStrSheetsCount(String strSheetsCount) {
		this.strSheetsCount = strSheetsCount;
	}//end of setStrSheetsCount(String strSheetsCount)

	*//** Retrieve the Sheets Count
	 * @return Returns the intSheetsCount.
	 *//*
	public String getStrSheetsCount() {
		return String.valueOf(intSheetsCount);
	}//end of getStrSheetsCount()
*/
	/** Retrieve the Category Type ID
	 * @return Returns the strCategoryTypeID.
	 */
	public String getCategoryTypeID() {
		return strCategoryTypeID;
	}//end of getCategoryTypeID()

	/** Sets the Category Type ID
	 * @param strCategoryTypeID The strCategoryTypeID to set.
	 */
	public void setCategoryTypeID(String strCategoryTypeID) {
		this.strCategoryTypeID = strCategoryTypeID;
	}//end of setCategoryTypeID(String strCategoryTypeID)

	/** Retrieve the Document List Type
	 * @return Returns the strDocumentListType.
	 */
	public String getDocumentListType() {
		return strDocumentListType;
	}//end of getDocumentListType()

	/** Sets the Document List Type
	 * @param strDocumentListType The strDocumentListType to set.
	 */
	public void setDocumentListType(String strDocumentListType) {
		this.strDocumentListType = strDocumentListType;
	}//end of setDocumentListType(String strDocumentListType)

	/** Retrieve the Document Name
	 * @return Returns the strDocumentName.
	 */
	public String getDocumentName() {
		return strDocumentName;
	}//end of getDocumentName()

	/** Sets the Document Name
	 * @param strDocumentName The strDocumentName to set.
	 */
	public void setDocumentName(String strDocumentName) {
		this.strDocumentName = strDocumentName;
	}//end of setDocumentName(String strDocumentName)

	/** Retrieve the Document Rcvd YN
	 * @return Returns the strDocumentRcvdYN.
	 */
	public String getDocumentRcvdYN() {
		return strDocumentRcvdYN;
	}//end of getDocumentRcvdYN()

	/** Sets the Document Rcvd YN
	 * @param strDocumentRcvdYN The strDocumentRcvdYN to set.
	 */
	public void setDocumentRcvdYN(String strDocumentRcvdYN) {
		this.strDocumentRcvdYN = strDocumentRcvdYN;
	}//end of setDocumentRcvdYN(String strDocumentRcvdYN)

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

	/** Retrieve the Reason Type ID
	 * @return Returns the strReasonTypeID.
	 */
	public String getReasonTypeID() {
		return strReasonTypeID;
	}//end of getReasonTypeID()

	/** Sets the Reason Type ID
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

}//end of DocumentChecklistVO
