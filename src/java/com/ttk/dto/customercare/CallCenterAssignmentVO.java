/**
 * @ (#) CallCenterAssignmentVO.java Oct 6, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CallCenterAssignmentVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 6, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.customercare;

import com.ttk.dto.BaseVO;

public class CallCenterAssignmentVO extends BaseVO {
	
	private Long lngClarifySeqID = null;
	private Long lngLogSeqID = null;
	private String strLogNumber = "";
	private Long lngOfficeSeqID = null;
	private String strDeptTypeID = "";
	private String strRemarks = "";
	
	/** Retrieve the ClarifySeqID
	 * @return Returns the lngClarifySeqID.
	 */
	public Long getClarifySeqID() {
		return lngClarifySeqID;
	}//end of getClarifySeqID()
	
	/** Sets the ClarifySeqID
	 * @param lngClarifySeqID The lngClarifySeqID to set.
	 */
	public void setClarifySeqID(Long lngClarifySeqID) {
		this.lngClarifySeqID = lngClarifySeqID;
	}//end of setClarifySeqID(Long lngClarifySeqID)
	
	/** Retrieve the LogSeqID
	 * @return Returns the lngLogSeqID.
	 */
	public Long getLogSeqID() {
		return lngLogSeqID;
	}//end of getLogSeqID()
	
	/** Sets the LogSeqID
	 * @param lngLogSeqID The lngLogSeqID to set.
	 */
	public void setLogSeqID(Long lngLogSeqID) {
		this.lngLogSeqID = lngLogSeqID;
	}//end of setLogSeqID(Long lngLogSeqID)
	
	/** Retrieve the OfficeSeqID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()
	
	/** Sets the OfficeSeqID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)
	
	/** Retrieve the DeptTypeID
	 * @return Returns the strDeptTypeID.
	 */
	public String getDeptTypeID() {
		return strDeptTypeID;
	}//end of getDeptTypeID()
	
	/** Sets the DeptTypeID
	 * @param strDeptTypeID The strDeptTypeID to set.
	 */
	public void setDeptTypeID(String strDeptTypeID) {
		this.strDeptTypeID = strDeptTypeID;
	}//end of setDeptTypeID(String strDeptTypeID)
	
	/** Retrieve the LogNumber
	 * @return Returns the strLogNumber.
	 */
	public String getLogNumber() {
		return strLogNumber;
	}//end of getLogNumber()
	
	/** Sets the LogNumber
	 * @param strLogNumber The strLogNumber to set.
	 */
	public void setLogNumber(String strLogNumber) {
		this.strLogNumber = strLogNumber;
	}//end of setLogNumber(String strLogNumber)
	
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
	
}//end of CallCenterAssignmentVO
