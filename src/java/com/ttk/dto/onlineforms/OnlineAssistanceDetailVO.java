/**
 * @ (#) OnlineAssistanceDetailVO.java Oct 22, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAssistanceDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import java.util.ArrayList;

/**
 * @author ramakrishna_km
 *
 */
public class OnlineAssistanceDetailVO extends OnlineAssistanceVO{
	
	private Long lngPolicyGrpSeqID = null;
	private Long lngPolicySeqID = null;
	private String strEmailID = "";
	private String strPhoneNbr = "";
	private String strMobileNbr = "";
	private ArrayList alOnlineQueryVO = null;
	private OnlineQueryVO onlineQueryVO = null;
	private String strFeedbackAllowedYN="";

	/** Retrieve the FeedbackAllowedYN
	 * @return the strFeedbackAllowedYN
	 */
	public String getFeedbackAllowedYN() {
		return strFeedbackAllowedYN;
	}//end of getFeedbackAllowedYN()

	/** Sets the FeedbackAllowedYN
	 * @param strFeedbackAllowedYN the strFeedbackAllowedYN to set
	 */
	public void setFeedbackAllowedYN(String strFeedbackAllowedYN) {
		this.strFeedbackAllowedYN = strFeedbackAllowedYN;
	}//end of setFeedbackAllowedYN(String strFeedbackAllowedYN)

	/** Sets the OnlineQueryVO
	 * @param onlineQueryVO The onlineQueryVO to set.
	 */
	public void setOnlineQueryVO(OnlineQueryVO onlineQueryVO) {
		this.onlineQueryVO = onlineQueryVO;
	}//end of setOnlineQueryVO(OnlineQueryVO onlineQueryVO)
	
	/** Retrieve the OnlineQueryVO
	 * @return Returns the onlineQueryVO.
	 */
	public OnlineQueryVO getOnlineQueryVO() {
		return onlineQueryVO;
	}//end of getOnlineQueryVO()
	
	/** Retrieve the OnlineQueryVO
	 * @return Returns the alOnlineQueryVO.
	 */
	public ArrayList getQueryVO() {
		return alOnlineQueryVO;
	}//end of getQueryVO()
	
	/** Sets the OnlineQueryVO
	 * @param alOnlineQueryVO The alOnlineQueryVO to set.
	 */
	public void setQueryVO(ArrayList alOnlineQueryVO) {
		this.alOnlineQueryVO = alOnlineQueryVO;
	}//end of setQueryVO(ArrayList alOnlineQueryVO)
	
	/** Retrieve the PolicyGrpSeqID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()
	
	/** Sets the PolicyGrpSeqID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)
	
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
	
	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()
	
	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)
	
	/** Retrieve the MobileNbr
	 * @return Returns the strMobileNbr.
	 */
	public String getMobileNbr() {
		return strMobileNbr;
	}//end of getMobileNbr()
	
	/** Sets the MobileNbr
	 * @param strMobileNbr The strMobileNbr to set.
	 */
	public void setMobileNbr(String strMobileNbr) {
		this.strMobileNbr = strMobileNbr;
	}//end of setMobileNbr(String strMobileNbr)
	
	/** Retrieve the PhoneNbr
	 * @return Returns the strPhoneNbr.
	 */
	public String getPhoneNbr() {
		return strPhoneNbr;
	}//end of getPhoneNbr()
	
	/** Sets the PhoneNbr
	 * @param strPhoneNbr The strPhoneNbr to set.
	 */
	public void setPhoneNbr(String strPhoneNbr) {
		this.strPhoneNbr = strPhoneNbr;
	}//end of setPhoneNbr(String strPhoneNbr)
	
}//end of OnlineAssistanceDetailVO
