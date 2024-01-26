/**
 * @ (#) 1352 november 2013
 * Project       : TTK HealthCare Services
 * File          : EmployeeFileUpoadVO.java
 * author 		 : satya
 * Reason        : File Upload console in Employee Login
 */
package com.ttk.dto.onlineforms;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class EmployeeFileUplodedVO extends BaseVO{
	private String strReferenceNbr="";
	private String strFileName="";	
	private String strRemarks="";
	
	
	public void setReferenceNbr(String referenceNbr) {
		strReferenceNbr = referenceNbr;
	}
	public String getReferenceNbr() {
		return strReferenceNbr;
	}
	
	
	public void setRemarks(String remarks) {
		strRemarks = remarks;
	}
	public String getRemarks() {
		return strRemarks;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		strFileName = fileName;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return strFileName;
	}	
	
}
