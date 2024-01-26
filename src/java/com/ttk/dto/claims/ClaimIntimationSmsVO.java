/**
 * @ (#) ClaimVO.java Jul 14, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 14, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class ClaimIntimationSmsVO extends BaseVO{
	
	private String strFromMobile = "";
	private String strFromContent = "";

	/** Sets the Claim Seq ID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setFromMobile(String strFromMobile) {
		this.strFromMobile = strFromMobile;
	}//end of setClaimSeqID(Long lngClaimSeqID)
	
	/** Retrieve the Member Seq ID
	 * @return Returns the lngMemberSeqID.
	 */
	public String getFromMobile() {
		return strFromMobile;
	}//end of getMemberSeqID()
	/** Retrieve the AssignImageName
	 * @return Returns the strAssignImageName.
	 */
	public String getFromContent() {
		return strFromContent;
	}//end of getAssignImageName()
	
	/** Sets the AssignImageName
	 * @param strAssignImageName The strAssignImageName to set.
	 */
	public void setFromContent(String strFromContent) {
		this.strFromContent = strFromContent;
	}//end of setAssignImageName(String strAssignImageName)
 
}//end of ClaimVO
