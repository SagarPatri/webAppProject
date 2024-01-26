/** 1216B
 * @ (#) EnrollBufferDetailVO.java Jun 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : EnrollBufferDetailVO.java
 * 
 */

package com.ttk.dto.maintenance;

import java.math.BigDecimal;
import com.ttk.dto.administration.BufferVO;
public class EnrollBufferDetailVO extends BufferVO{
	
	
	
	private static final long serialVersionUID = 1L;
	public String getModeTypeID() {
		return strModeTypeID;
	}
	public void setModeTypeID(String modeTypeID) {
		strModeTypeID = modeTypeID;
	}
	public String getAllocatedTypeID() {
		return strAllocatedTypeID;
	}
	public void setAllocatedTypeID(String allocatedTypeID) {
		strAllocatedTypeID = allocatedTypeID;
	}
	public BigDecimal getAllocatedAmt() {
		return bdAllocatedAmt;
	}
	public void setAllocatedAmt(BigDecimal allocatedAmt) {
		bdAllocatedAmt = allocatedAmt;
	}
	public String getAdmnAuthTypeID() {
		return strAdmnAuthTypeID;
	}
	public void setAdmnAuthTypeID(String admnAuthTypeID) {
		strAdmnAuthTypeID = admnAuthTypeID;
	}
	public String getRemarks() {
		return strRemarks;
	}
	public void setRemarks(String remarks) {
		strRemarks = remarks;
	}
	public String getAdmnAuthDesc() {
		return strAdmnAuthDesc;
	}
	public void setAdmnAuthDesc(String admnAuthDesc) {
		strAdmnAuthDesc = admnAuthDesc;
	}
	public String getAllocatedTypeDesc() {
		return strAllocatedTypeDesc;
	}
	public void setAllocatedTypeDesc(String allocatedTypeDesc) {
		strAllocatedTypeDesc = allocatedTypeDesc;
	}
	public String getEditYN() {
		return strEditYN;
	}
	public void setEditYN(String editYN) {
		strEditYN = editYN;
	}
	public String getBufferAmount() {
		return strBufferAmount;
	}
	public void setBufferAmount(String bufferAmount) {
		strBufferAmount = bufferAmount;
	}
	public String getCorpoateAmount() {
		return strCorpoateAmount;
	}
	public void setCorpoateAmount(String corpoateAmount) {
		strCorpoateAmount = corpoateAmount;
	}
	public String getFamilyAmount() {
		return strFamilyAmount;
	}
	public void setFamilyAmount(String familyAmount) {
		strFamilyAmount = familyAmount;
	}
	public String getPossibleBufferAmount() {
		return strPossibleBufferAmount;
	}
	public void setPossibleBufferAmount(String possibleBufferAmount) {
		strPossibleBufferAmount = possibleBufferAmount;
	}
	public String getApprovedBufferAmount() {
		return strApprovedBufferAmount;
	}
	public void setApprovedBufferAmount(String approvedBufferAmount) {
		strApprovedBufferAmount = approvedBufferAmount;
	}

	
	private String strModeTypeID = "";
	private String strAllocatedTypeID = "";
	private BigDecimal bdAllocatedAmt = null;
	private String strAdmnAuthTypeID = "";
	private String strRemarks = "";
	private String strAdmnAuthDesc = "";
	private String strAllocatedTypeDesc = "";
	private String strEditYN = "";
	private String strBufferAmount="";
	private String strCorpoateAmount="";
	private String strFamilyAmount="";
	private String strPossibleBufferAmount="";
	private String strApprovedBufferAmount="";
	
	
	
	
	
	
	
	
}//end of BufferDetailVO
