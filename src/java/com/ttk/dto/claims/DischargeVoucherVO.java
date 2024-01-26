/**
 * @ (#) DischargeVoucherVO.java Jul 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : DischargeVoucherVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class DischargeVoucherVO extends ClaimVO {
	
	private String strStatusTypeID = "";
	private Long lngDchrgVoucherSeqID = null;
	private String strDchrgRemarks = "";
	private Date dtDchrgRcvdDate = null;
	private String strDchrgRcvdTime = "";
	private String strDchrgRcvdDay = "";
	private Date dtDchrgSentDate = null;
	private String strDchrgSentTime = "";
	private String strDchrgSentDay =  "";
	private String strRefNbr = "";
	
	/** Retrieve the RefNbr
	 * @return Returns the strRefNbr.
	 */
	public String getRefNbr() {
		return strRefNbr;
	}//end of getRefNbr()

	/** Sets the RefNbr
	 * @param strRefNbr The strRefNbr to set.
	 */
	public void setRefNbr(String strRefNbr) {
		this.strRefNbr = strRefNbr;
	}//end of setRefNbr(String strRefNbr)

	/** Retrieve the DchrgRcvdDate
	 * @return Returns the dtDchrgRcvdDate.
	 */
	public Date getDchrgRcvdDate() {
		return dtDchrgRcvdDate;
	}//end of getDchrgRcvdDate()
	
	/** Retrieve the DchrgRcvdDate
	 * @return Returns the dtDchrgRcvdDate.
	 */
	public String getClaimDchrgRcvdDate() {
		return TTKCommon.getFormattedDate(dtDchrgRcvdDate);
	}//end of getClaimDchrgRcvdDate()
	
	/** Sets the DchrgRcvdDate
	 * @param dtDchrgRcvdDate The dtDchrgRcvdDate to set.
	 */
	public void setDchrgRcvdDate(Date dtDchrgRcvdDate) {
		this.dtDchrgRcvdDate = dtDchrgRcvdDate;
	}//end of setDchrgRcvdDate(Date dtDchrgRcvdDate)
	
	/** Retrieve the DchrgSentDate
	 * @return Returns the dtDchrgSentDate.
	 */
	public Date getDchrgSentDate() {
		return dtDchrgSentDate;
	}//end of getDchrgSentDate()
	
	/** Retrieve the DchrgSentDate
	 * @return Returns the dtDchrgSentDate.
	 */
	public String getClaimDchrgSentDate() {
		return TTKCommon.getFormattedDate(dtDchrgSentDate);
	}//end of getClaimDchrgSentDate()
	
	/** Sets the DchrgSentDate
	 * @param dtDchrgSentDate The dtDchrgSentDate to set.
	 */
	public void setDchrgSentDate(Date dtDchrgSentDate) {
		this.dtDchrgSentDate = dtDchrgSentDate;
	}//end of setDchrgSentDate(Date dtDchrgSentDate)
	
	/** Retrieve the DchrgVoucherSeqID
	 * @return Returns the lngDchrgVoucherSeqID.
	 */
	public Long getDchrgVoucherSeqID() {
		return lngDchrgVoucherSeqID;
	}//end of getDchrgVoucherSeqID()
	
	/** Sets the DchrgVoucherSeqID
	 * @param lngDchrgVoucherSeqID The lngDchrgVoucherSeqID to set.
	 */
	public void setDchrgVoucherSeqID(Long lngDchrgVoucherSeqID) {
		this.lngDchrgVoucherSeqID = lngDchrgVoucherSeqID;
	}//end of setDchrgVoucherSeqID(Long lngDchrgVoucherSeqID)
	
	/** Retrieve the DchrgRcvdDay
	 * @return Returns the strDchrgRcvdDay.
	 */
	public String getDchrgRcvdDay() {
		return strDchrgRcvdDay;
	}//end of getDchrgRcvdDay()
	
	/** Sets the DchrgRcvdDay
	 * @param strDchrgRcvdDay The strDchrgRcvdDay to set.
	 */
	public void setDchrgRcvdDay(String strDchrgRcvdDay) {
		this.strDchrgRcvdDay = strDchrgRcvdDay;
	}//end of setDchrgRcvdDay(String strDchrgRcvdDay)
	
	/** Retrieve the DchrgRcvdTime
	 * @return Returns the strDchrgRcvdTime.
	 */
	public String getDchrgRcvdTime() {
		return strDchrgRcvdTime;
	}//end of getDchrgRcvdTime()
	
	/** Sets the DchrgRcvdTime
	 * @param strDchrgRcvdTime The strDchrgRcvdTime to set.
	 */
	public void setDchrgRcvdTime(String strDchrgRcvdTime) {
		this.strDchrgRcvdTime = strDchrgRcvdTime;
	}//end of setDchrgRcvdTime(String strDchrgRcvdTime)
	
	/** Retrieve the DchrgRemarks
	 * @return Returns the strDchrgRemarks.
	 */
	public String getDchrgRemarks() {
		return strDchrgRemarks;
	}//end of getDchrgRemarks()
	
	/** Sets the DchrgRemarks
	 * @param strDchrgRemarks The strDchrgRemarks to set.
	 */
	public void setDchrgRemarks(String strDchrgRemarks) {
		this.strDchrgRemarks = strDchrgRemarks;
	}//end of setDchrgRemarks(String strDchrgRemarks)
	
	/** Retrieve the DchrgSentDay
	 * @return Returns the strDchrgSentDay.
	 */
	public String getDchrgSentDay() {
		return strDchrgSentDay;
	}//end of getDchrgSentDay()
	
	/** Sets the DchrgSentDay
	 * @param strDchrgSentDay The strDchrgSentDay to set.
	 */
	public void setDchrgSentDay(String strDchrgSentDay) {
		this.strDchrgSentDay = strDchrgSentDay;
	}//end of setDchrgSentDay(String strDchrgSentDay)
	
	/** Retrieve the DchrgSentTime
	 * @return Returns the strDchrgSentTime.
	 */
	public String getDchrgSentTime() {
		return strDchrgSentTime;
	}//end of getDchrgSentTime()
	
	/** Sets the DchrgSentTime
	 * @param strDchrgSentTime The strDchrgSentTime to set.
	 */
	public void setDchrgSentTime(String strDchrgSentTime) {
		this.strDchrgSentTime = strDchrgSentTime;
	}//edn of setDchrgSentTime(String strDchrgSentTime)
	
	/** Retrieve the StatusTypeID
	 * @return Returns the strStatusTypeID.
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()
	
	/** Sets the StatusTypeID
	 * @param strStatusTypeID The strStatusTypeID to set.
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)
	
}//end of DischargeVoucherVO
