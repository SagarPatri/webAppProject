/**
 * @ (#) CardBatchVO.java Jul 23, 2008
 * Project 	     : TTK HealthCare Services
 * File          : CardBatchVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 23, 2008
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

public class CardBatchVO extends BaseVO{
	
	private Long lngBatchSeqID = null;
	private String strBatchNbr = "";
	private Date dtBatchDate = null;
	private String strPolicyNbr = "";
	private String strEnrollmentNbr = "";
	private Long lngPolicyGroupSeqID=null; //policy_group_seq_id
	
	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public Date getBatchDate() {
		return dtBatchDate;
	}//end of getBatchDate()
	
	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public String getCardBatchDate() {
		return TTKCommon.getFormattedDateHour(dtBatchDate);
	}//end of getCardBatchDate()
	
	/** Sets the BatchDate
	 * @param dtBatchDate The dtBatchDate to set.
	 */
	public void setBatchDate(Date dtBatchDate) {
		this.dtBatchDate = dtBatchDate;
	}//end of setBatchDate(Date dtBatchDate)
	
	/** Retrieve the BatchSeqID
	 * @return Returns the lngBatchSeqID.
	 */
	public Long getBatchSeqID() {
		return lngBatchSeqID;
	}//end of getBatchSeqID()
	
	/** Sets the BatchSeqID
	 * @param lngBatchSeqID The lngBatchSeqID to set.
	 */
	public void setBatchSeqID(Long lngBatchSeqID) {
		this.lngBatchSeqID = lngBatchSeqID;
	}//end of setBatchSeqID(Long lngBatchSeqID)
	
	/** Retrieve the BatchNbr
	 * @return Returns the strBatchNbr.
	 */
	public String getBatchNbr() {
		return strBatchNbr;
	}//end of getBatchNbr()
	
	/** Sets the BatchNbr
	 * @param strBatchNbr The strBatchNbr to set.
	 */
	public void setBatchNbr(String strBatchNbr) {
		this.strBatchNbr = strBatchNbr;
	}//end of setBatchNbr(String strBatchNbr)
	
	/** Retrieve the EnrollmentNbr
	 * @return Returns the strEnrollmentNbr.
	 */
	public String getEnrollmentNbr() {
		return strEnrollmentNbr;
	}//end of getEnrollmentNbr()
	
	/** Sets the EnrollmentNbr
	 * @param strEnrollmentNbr The strEnrollmentNbr to set.
	 */
	public void setEnrollmentNbr(String strEnrollmentNbr) {
		this.strEnrollmentNbr = strEnrollmentNbr;
	}//end of setEnrollmentNbr(String strEnrollmentNbr)
	
	/** Retrieve the PolicyNbr
	 * @return Returns the strPolicyNbr.
	 */
	public String getPolicyNbr() {
		return strPolicyNbr;
	}//end of getPolicyNbr()
	
	/** Sets the PolicyNbr
	 * @param strPolicyNbr The strPolicyNbr to set.
	 */
	public void setPolicyNbr(String strPolicyNbr) {
		this.strPolicyNbr = strPolicyNbr;
	}//end of setPolicyNbr(String strPolicyNbr)
	
	/**
	 * This method returns the PolicyGroup Sequence ID
	 * @return Returns the lngPolicyGroupSeqID.
	 */
	public Long getPolicyGroupSeqID() {
		return lngPolicyGroupSeqID;
	}//end of getPolicyGroupSeqID()
	/**
	 * This method sets the PolicyGroup Sequence ID
	 * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
	 */
	public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
		this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
	}//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)
}//end of CardBatchVO
