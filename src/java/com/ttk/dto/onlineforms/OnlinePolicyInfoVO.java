/**
 * @ (#) OnlinePolicyInfoVO.java Apr 18, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlinePolicyInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 18, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class OnlinePolicyInfoVO extends BaseVO{

	private Long lngPolicySeqID = null;
	private String strPolicyNbr = "";
	private String strGroupName = "";
	private Date dtStartDate = null;
    private Date dtEndDate = null;
	
	private String strPolicyNbr1 = "";
    public String getPolicyNbr1() {
		return strPolicyNbr1;
	}

	public void setPolicyNbr1(String strPolicyNbr1) {
		this.strPolicyNbr1 = strPolicyNbr1;
	}
	
    /** Retrieve the EndDate
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()
	
	/** Retrieve the EndDate
	 * @return Returns the dtEndDate.
	 */
	public String getPolicyEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}//end of getPolicyEndDate()
	
	/** Sets the EndDate
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)
	
	/** Retrieve the StartDate
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()
	
	/** Retrieve the StartDate
	 * @return Returns the dtStartDate.
	 */
	public String getPolicyStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getPolicyStartDate()
	
	/** Sets the StartDate
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)
	
	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)
	
	/** Retrieve the GroupName
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()
	
	/** Sets the GroupName
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)
	
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
    
}//end of OnlinePolicyInfoVO
