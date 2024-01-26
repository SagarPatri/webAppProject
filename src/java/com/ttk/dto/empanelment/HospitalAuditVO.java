/**
 * @ (#) HospitalAuditVO.java Sep 19, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalAuditVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 19, 2005
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.empanelment;

import java.util.Date;
import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of hospital audit information. 
 * The corresponding DB Table is TPA_HOSP_LOG.
 */
public class HospitalAuditVO extends BaseVO {
	
	// String fields declared private
	private Date dtRefDate = null;
	private String strRemarks = "";
	private String strRefNmbr = "";
	private String strModReson = "";
	
	/*
	 * Sets the reference date
	 * @param dtRefDate Date reference date
	 */
	public void setRefDate(Date dtRefDate) {
		this.dtRefDate = dtRefDate;
	}// end of setRefDate(Date dtRefDate)
	
	/*
	 * Retrieve the reference date
	 * @return dtRefDate Date reference date
	 */
	public Date getRefDate() {
		return dtRefDate;
	}// end of getRefDate()
	
	/*
	 * Sets the remarks
	 * @param strRemarks String remarks
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}// end of setRemarks(String strRemarks)
	
	/*
	 * Retrieve the remarks
	 * @return strRemarks String remarks
	 */
	public String getRemarks() {
		return strRemarks;
	}// end of getRemarks()
	
	/*
	 * Sets the reference number
	 * @param strRefNmbr String reference number
	 */
	public void setRefNmbr(String strRefNmbr) {
		this.strRefNmbr = strRefNmbr;
	}// end of setRefNmbr(String strRefNmbr)
	
	/*
	 * Retrieve the reference number
	 * @return strRefNmbr String strRefNmbr
	 */
	public String getRefNmbr() {
		return strRefNmbr;
	}// end of getrRefNmbr()
	
	/*
	 * Sets the modified reason
	 * @param strModReson String modified reson
	 */
	public void setModReson(String strModReson) {
		this.strModReson = strModReson;
	}// end of setModReson(String strModReson)
	
	/*
	 * Retrieve the modified reason type
	 * @return strModResonType String modified reson type
	 */
	public String getModReson() {
		return strModReson;
	}// end of getModReson()
}//end of HospitalAuditVO
