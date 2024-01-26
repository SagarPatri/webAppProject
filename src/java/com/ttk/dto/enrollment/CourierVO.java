/**
 * @ (#) CourierVO.java May 26, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CourierVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 26, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class CourierVO extends BaseVO {
	
	private String strCourierNbr = "";
	private Long lngCourierSeqID = null;
	private String strCourierName = "";
	private Date dtDispatchDate = null;
	private String strOfficeName = "";
	private String strStatusDesc = "";
	private String strDocketPODNbr = "";
	
	/** Retrieve the Docket/POD Number
	 * @return Returns the strDocketPODNbr.
	 */
	public String getDocketPODNbr() {
		return strDocketPODNbr;
	}//end of getDocketPODNbr()

	/** Sets the Docket/POD Number
	 * @param strDocketPODNbr The strDocketPODNbr to set.
	 */
	public void setDocketPODNbr(String strDocketPODNbr) {
		this.strDocketPODNbr = strDocketPODNbr;
	}//end of setDocketPODNbr(String strDocketPODNbr)

	/** Retrieve the Dispatch Date
	 * @return Returns the dtDispatchDate.
	 */
	public Date getDispatchDate() {
		return dtDispatchDate;
	}//end of getDispatchDate()
	
	/** Retrieve the Dispatch Date
	 * @return Returns the dtDispatchDate.
	 */
	public String  getDocDispatchDate() {
		return TTKCommon.getFormattedDate(dtDispatchDate);
	}//end of getDispatchDate()
	
	/** Sets the Dispatch Date
	 * @param dtDispatchDate The dtDispatchDate to set.
	 */
	public void setDispatchDate(Date dtDispatchDate) {
		this.dtDispatchDate = dtDispatchDate;
	}//end of setDispatchDate(Date dtDispatchDate)
	
	/** Retrieve the Courier Seq ID
	 * @return Returns the lngCourierSeqID.
	 */
	public Long getCourierSeqID() {
		return lngCourierSeqID;
	}//end of getCourierSeqID()
	
	/** Sets the Courier Seq ID
	 * @param lngCourierSeqID The lngCourierSeqID to set.
	 */
	public void setCourierSeqID(Long lngCourierSeqID) {
		this.lngCourierSeqID = lngCourierSeqID;
	}//end of setCourierSeqID(Long lngCourierSeqID)
	
	/** Retrieve the Courier Name
	 * @return Returns the strCourierName.
	 */
	public String getCourierName() {
		return strCourierName;
	}//end of getCourierName()
	
	/** Sets the Courier Name
	 * @param strCourierName The strCourierName to set.
	 */
	public void setCourierName(String strCourierName) {
		this.strCourierName = strCourierName;
	}//end of setCourierName(String strCourierName)
	
	/** Retrieve the Courier Nbr
	 * @return Returns the strCourierNbr.
	 */
	public String getCourierNbr() {
		return strCourierNbr;
	}//end of getCourierNbr()
	
	/** Sets the Courier Nbr
	 * @param strCourierNbr The strCourierNbr to set.
	 */
	public void setCourierNbr(String strCourierNbr) {
		this.strCourierNbr = strCourierNbr;
	}//end of setCourierNbr(String strCourierNbr)
	
	/** Retrieve the OfficeName
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()
	
	/** Sets the OfficeName
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)
	
	/** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()
	
	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)
	
}//end of CourierVO
