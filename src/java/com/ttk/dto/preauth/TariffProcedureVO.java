/**
 * @ (#) TariffProcedureVO.java Sep 7, 2007
 * Project 	     : TTK HealthCare Services
 * File          : TariffProcedureVO.java
 * Author        : Arun K N
 * Company       : Span Systems Corporation
 * Date Created  : Sep 7, 2007
 *
 * @author       :  Arun K N
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;

public class TariffProcedureVO extends BaseVO{
	
	private Long lngPatProcSeqID=null;		//PAT_PROC_SEQ_ID
	private Long lngProcSeqID = null;		//PROC_SEQ_ID
    private String strProcDesc = "";		//PROC_DESCRIPTION
    private String strProcedureAmt = "";	//PROCEDURE_AMOUNT
	
    /**
	 * Retrieve the PAT_PROC_SEQ_ID
	 * @return  lngPatProcSeqID Long
	 */
	public Long getPatProcSeqID() {
		return lngPatProcSeqID;
	}//end of getPatProcSeqID()
	
	/**
	 * Sets the PAT_PROC_SEQ_ID
	 * @param  lngPatProcSeqID Long 
	 */
	public void setPatProcSeqID(Long lngPatProcSeqID) {
		this.lngPatProcSeqID = lngPatProcSeqID;
	}//end of setPatProcSeqID(Long lngPatProcSeqID)
	
	/**
	 * Retrieve the PROC_SEQ_ID
	 * @return  lngProcSeqID Long
	 */
	public Long getProcSeqID() {
		return lngProcSeqID;
	}//end of getProcSeqID()
	
	/**
	 * Sets the PROC_SEQ_ID
	 * @param  lngProcSeqID Long 
	 */
	public void setProcSeqID(Long lngProcSeqID) {
		this.lngProcSeqID = lngProcSeqID;
	}//end of setProcSeqID(Long lngProcSeqID)
	
	/**
	 * Retrieve the PROC_DESCRIPTION
	 * @return  strProcDesc String
	 */
	public String getProcDesc() {
		return strProcDesc;
	}//end of getProcDesc()
	
	/**
	 * Sets the PROC_DESCRIPTION
	 * @param  strProcDesc String 
	 */
	public void setProcDesc(String strProcDesc) {
		this.strProcDesc = strProcDesc;
	}//end of setProcDesc(String strProcDesc)
	
	/**
	 * Retrieve the PROCEDURE_AMOUNT
	 * @return  strProcedureAmt String
	 */
	public String getProcedureAmt() {
		return strProcedureAmt;
	}//end of getProcedureAmt()
	
	/**
	 * Sets the PROCEDURE_AMOUNT
	 * @param  strProcedureAmt String 
	 */
	public void setProcedureAmt(String strProcedureAmt) {
		this.strProcedureAmt = strProcedureAmt;
	}//end of setProcedureAmt(String strProcedureAmt)
}//end of TariffProcedureVO.java
