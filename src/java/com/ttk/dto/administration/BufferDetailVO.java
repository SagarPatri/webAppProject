/**
 * @ (#) BufferDetailVO.java Jun 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BufferDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 17, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

public class BufferDetailVO extends BufferVO{
	
	private String strModeTypeID = "";
	private String strAllocatedTypeID = "";
	private BigDecimal bdAllocatedAmt = null;
	private String strAdmnAuthTypeID = "";
	private String strRemarks = "";
	private String strAdmnAuthDesc = "";
	private String strAllocatedTypeDesc = "";
	private String strEditYN = "";
	
	
	//<!-end  - added for hyundai buffer by rekha on 19.06.2014 -->
	/** Retrieve the EditYN
	 * @return Returns the strEditYN.
	 */
	public String getEditYN() {
		return strEditYN;
	}//end of getEditYN()

	/** Sets the EditYN
	 * @param strEditYN The strEditYN to set.
	 */
	public void setEditYN(String strEditYN) {
		this.strEditYN = strEditYN;
	}//end of setEditYN(String strEditYN)
	
	/** Retrieve the Admin Authority Description
	 * @return Returns the strAdmnAuthDesc.
	 */
	public String getAdmnAuthDesc() {
		return strAdmnAuthDesc;
	}//end of getAdmnAuthDesc()

	/** Sets the Admin Authority Description
	 * @param strAdmnAuthDesc The strAdmnAuthDesc to set.
	 */
	public void setAdmnAuthDesc(String strAdmnAuthDesc) {
		this.strAdmnAuthDesc = strAdmnAuthDesc;
	}//end of setAdmnAuthDesc(String strAdmnAuthDesc)

	/** Retrieve the Allocated Type Description
	 * @return Returns the strAllocatedTypeDesc.
	 */
	public String getAllocatedTypeDesc() {
		return strAllocatedTypeDesc;
	}//end of getAllocatedTypeDesc()

	/** Sets the Allocated Type Description
	 * @param strAllocatedTypeDesc The strAllocatedTypeDesc to set.
	 */
	public void setAllocatedTypeDesc(String strAllocatedTypeDesc) {
		this.strAllocatedTypeDesc = strAllocatedTypeDesc;
	}//end of setAllocatedTypeDesc(String strAllocatedTypeDesc)

	/** Retrieve the Allocated Amount
	 * @return Returns the bdAllocatedAmt.
	 */
	public BigDecimal getAllocatedAmt() {
		return bdAllocatedAmt;
	}//end of getAllocatedAmt()
	
	/** Sets the Allocated Amount
	 * @param bdAllocatedAmt The bdAllocatedAmt to set.
	 */
	public void setAllocatedAmt(BigDecimal bdAllocatedAmt) {
		this.bdAllocatedAmt = bdAllocatedAmt;
	}//end of setAllocatedAmt(BigDecimal bdAllocatedAmt)
	
	/** Retrieve the Mode Type ID
	 * @return Returns the strModeTypeID.
	 */
	public String getModeTypeID() {
		return strModeTypeID;
	}//end of getModeTypeID()
	
	/** Sets the Mode Type ID
	 * @param strModeTypeID The strModeTypeID to set.
	 */
	public void setModeTypeID(String strModeTypeID) {
		this.strModeTypeID = strModeTypeID;
	}//end of setModeTypeID(String strModeTypeID)
	
	/** Retrieve the Administering Authority Type ID
	 * @return Returns the strAdmnAuthTypeID.
	 */
	public String getAdmnAuthTypeID() {
		return strAdmnAuthTypeID;
	}//end of getAdmnAuthTypeID()
	
	/** Sets the Administering Authority Type ID
	 * @param strAdmnAuthTypeID The strAdmnAuthTypeID to set.
	 */
	public void setAdmnAuthTypeID(String strAdmnAuthTypeID) {
		this.strAdmnAuthTypeID = strAdmnAuthTypeID;
	}//end of setAdmnAuthTypeID(String strAdmnAuthTypeID)
	
	/** Retrieve the Allocated Type ID
	 * @return Returns the strAllocatedTypeID.
	 */
	public String getAllocatedTypeID() {
		return strAllocatedTypeID;
	}//end of getAllocatedTypeID()
	
	/** Sets the Allocated Type ID
	 * @param strAllocatedTypeID The strAllocatedTypeID to set.
	 */
	public void setAllocatedTypeID(String strAllocatedTypeID) {
		this.strAllocatedTypeID = strAllocatedTypeID;
	}//end of setAllocatedTypeID(String strAllocatedTypeID)
	
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


	
}//end of BufferDetailVO
