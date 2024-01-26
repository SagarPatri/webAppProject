/**
 * @ (#)  CriticalICDDetailVO.java Oct 5, 2005
 * Project      : TTKPROJECT
 * File         : CriticalICDDetailVO.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 5, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class CriticalICDDetailVO extends BaseVO{

	 private Long lProcedureID = null;
	 private String strProcedureCode = null;
	 private String strProcedureDescription = null;
	 private Long lngPATProcSeqID = null;
	 private Integer intProcCount = null;
	 private Long lngProcMapSeqID = null;
	 private String strMasterProcCode = "";
	 private String strShortDesc = "";
	 
	 /** Retrieve the ShortDesc
	 * @return Returns the strShortDesc.
	 */
	public String getShortDesc() {
		return strShortDesc;
	}//end of getShortDesc()

	/** Sets the ShortDesc
	 * @param strShortDesc The strShortDesc to set.
	 */
	public void setShortDesc(String strShortDesc) {
		this.strShortDesc = strShortDesc;
	}//end of setShortDesc(String strShortDesc)

	/** Retrieve the ProcMapSeqID
	 * @return Returns the lngProcMapSeqID.
	 */
	public Long getProcMapSeqID() {
		return lngProcMapSeqID;
	}//end of getProcMapSeqID()

	/** Sets the ProcMapSeqID
	 * @param lngProcMapSeqID The lngProcMapSeqID to set.
	 */
	public void setProcMapSeqID(Long lngProcMapSeqID) {
		this.lngProcMapSeqID = lngProcMapSeqID;
	}//end of setProcMapSeqID(Long lngProcMapSeqID)

	/** Retrieve the MasterProcCode
	 * @return Returns the strMasterProcCode.
	 */
	public String getMasterProcCode() {
		return strMasterProcCode;
	}//end of getMasterProcCode()

	/** Sets the MasterProcCode
	 * @param strMasterProcCode The strMasterProcCode to set.
	 */
	public void setMasterProcCode(String strMasterProcCode) {
		this.strMasterProcCode = strMasterProcCode;
	}//end of setMasterProcCode(String strMasterProcCode)

	/** Retrieve the ProcCount
	 * @return Returns the intProcCount.
	 */
	public Integer getProcCount() {
		return intProcCount;
	}//end of getProcCount()

	/** Sets the ProcCount
	 * @param intProcCount The intProcCount to set.
	 */
	public void setProcCount(Integer intProcCount) {
		this.intProcCount = intProcCount;
	}//end of setProcCount(Integer intProcCount)

	/** Retrieve the PATProcSeqID
	 * @return Returns the lngPATProcSeqID.
	 */
	public Long getPATProcSeqID() {
		return lngPATProcSeqID;
	}//end of getPATProcSeqID()

	/** Sets the PATProcSeqID
	 * @param lngPATProcSeqID The lngPATProcSeqID to set.
	 */
	public void setPATProcSeqID(Long lngPATProcSeqID) {
		this.lngPATProcSeqID = lngPATProcSeqID;
	}//end of setPATProcSeqID(Long lngPATProcSeqID)

	/** This method returns the lProcedureID
	 * @return Returns the lProcedureID.
	 */
	public Long getProcedureID() {
		return lProcedureID;
	}// end of getProcedureID()
	
	/** This method sets the setProcedureID
	 * @param procedureID The lProcedureID to set.
	 */
	public void setProcedureID(Long procedureID) {
		lProcedureID = procedureID;
	}// end of setProcedureID(Long procedureID)
	
	/** This method returns the strProcedureCode
	 * @return Returns the strProcedureCode.
	 */
	public String getProcedureCode() {
		return strProcedureCode;
	}//end of getProcedureCode()
	
	/** This method sets the  strProcedureCode
	 * @param strProcedureCode The strProcedureCode to set.
	 */
	public void setProcedureCode(String strProcedureCode) {
		this.strProcedureCode = strProcedureCode;
	}//end of setProcedureCode(String strProcedureCode)
	
	/** This method returns the strProcedureDescription
	 * @return Returns the strProcedureDescription.
	 */
	public String getProcedureDescription() {
		return strProcedureDescription;
	}// end of getProcedureDescription()
	
	/** This method sets the strProcedureDescription
	 * @param strProcedureDescription The strProcedureDescription to set.
	 */
	public void setProcedureDescription(String strProcedureDescription) {
		this.strProcedureDescription = strProcedureDescription;
	}//end of setProcedureDescription(String strProcedureDescription)
	
}// end of class CriticalICDDetailVO
