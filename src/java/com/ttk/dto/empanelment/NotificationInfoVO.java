/**
 * @ (#) GroupRegNotificationVO.java May 15, 2008
 * Project       : TTK HealthCare Services
 * File          : GroupRegNotificationVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : May 15, 2008
 * @author       : Balakrishna Erram
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import java.util.ArrayList;
import com.ttk.dto.BaseVO;

public class NotificationInfoVO extends BaseVO{
	
	private Long lngGroupRegSeqID = null ;  
	private String strMsgID = null; 
	private ArrayList alAssocNotifyList=null;
	private ArrayList alUnAssocNotifyList=null;
	//Added for CR Mail-SMS Notification for Cigna
	private Long lngInsuranceSeqID = null;
	
	/** Retrieve the Message ID
	 * @return Returns the strMsgID.
	 */
	public String getMsgID() {
		return strMsgID;
	}//end of getMsgID()

	/** Sets the Message ID
	 * @param strMsgID The strMsgID to set.
	 */
	public void setMsgID(String strMsgID) {
		this.strMsgID = strMsgID;
	}//end of setMsgID(String strMsgID)

	/** This method returns the Group Registration Sequence ID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}// End of getGroupRegSeqID()
	
	/** This method sets the Group Registration Sequence ID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}// End of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/**This method returns the Role ArrayList
	 * @return Returns the alRole.
	 */
	public ArrayList getAssocNotifyList() {
		return alAssocNotifyList;
	}//end of  getAssocNotifyList()
	
	/**This method sets the Role ArrayList
	 * @param alRole The alRole to set.
	 */
	public void setAssocNotifyList(ArrayList alAssocNotifyList) {
		this.alAssocNotifyList = alAssocNotifyList;
	}//end of setAssocNotifyList(ArrayList alAssocNotifyList)
	
	/**
	 * This method returns the UnAssociated Role ArrayList
	 * @return Returns the alUnAssocNotifyList.
	 */
	public ArrayList getUnAssocNotifyList() {
		return alUnAssocNotifyList;
	}//end of getUnAssocNotifyList() 
	
	/**
	 * This method Sets the UnAssociated NotifyList ArrayList
	 * @param alUnAssociatedRole The alUnAssocNotifyList to set.
	 */
	public void setUnAssocNotifyList(ArrayList alUnAssocNotifyList) {
		this.alUnAssocNotifyList = alUnAssocNotifyList;
	}//end of setUnAssocNotifyList(ArrayList alUnAssocNotifyList)
	//added for Mail-SMS Template for Cigna
	public void setInsuranceSeqID(Long lngInsuranceSeqID) {
		this.lngInsuranceSeqID = lngInsuranceSeqID;
	}

	public Long getInsuranceSeqID() {
		return lngInsuranceSeqID;
	}
}//End of NotificationInfoVO
