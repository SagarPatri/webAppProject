/**
 * @ (#) OnlineChangePasswordVO.java Oct 14, 2010
 * Project      : TTK HealthCare Services
 * File         :OnlineChangePasswordVO
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : Oct 14, 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.onlineforms;

import com.ttk.dto.BaseVO;

/**
 * @author manikanta_k 
 * 
 */
public class OnlineChangePasswordVO extends BaseVO {
	
	private String strOldPassword = "";
	private String strNewPassword = "";
	private String strConfirmPassword = "";
	private String strUserID = "";
	private Long lngContactSeqID = null;
	
	/** Retrieve the ContactSeqID
	 * @return the lngContactSeqID
	 */
	public Long getContactSeqID() {
		return lngContactSeqID;
	}//end of getContactSeqID()

	/** Sets the ContactSeqID
	 * @param lngContactSeqID the lngContactSeqID to set
	 */
	public void setContactSeqID(Long lngContactSeqID) {
		this.lngContactSeqID = lngContactSeqID;
	}//end of setContactSeqID(Long lngContactSeqID)

	/** Retrieve the OldPassword
	 * @return the strOldPassword
	 */
	public String getOldPassword() {
		return strOldPassword;
	}//end of getOldPassword()
	
	/** Sets the OldPassword
	 * @param strOldPassword the strOldPassword to set
	 */
	public void setOldPassword(String strOldPassword) {
		this.strOldPassword = strOldPassword;
	}//end of setOldPassword(String strOldPassword)
	
	/** Retrieve the NewPassword
	 * @return the strNewPassword
	 */
	public String getNewPassword() {
		return strNewPassword;
	}//end of getNewPassword()
	
	/** Sets the NewPassword
	 * @param strNewPassword the strNewPassword to set
	 */
	public void setNewPassword(String strNewPassword) {
		this.strNewPassword = strNewPassword;
	}//end of setNewPassword(String strNewPassword)
	
	/**Retrieve the ConfirmPassword
	 * @return the strConfirmPassword
	 */
	public String getConfirmPassword() {
		return strConfirmPassword;
	}//end of getConfirmPassword()
	
	/** Sets the ConfirmPassword
	 * @param strConfirmPassword the strConfirmPassword to set
	 */
	public void setConfirmPassword(String strConfirmPassword) {
		this.strConfirmPassword = strConfirmPassword;
	}//end of setConfirmPassword(String strConfirmPassword) 
	
	/** Retrieve the UserID
	 * @return the strUserID
	 */
	public String getUserID() {
		return strUserID;
	}//end of getUserID()
	
	/** Sets the UserID
	 * @param strUserID the strUserID to set
	 */
	public void setUserID(String strUserID) {
		this.strUserID = strUserID;
	}//end of setUserID(String strUserID)

}//end of OnlineChangePasswordVO
