/**
 * @ (#) OnlineInsPolicyVO.java Mar 25, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineInsPolicyVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 25, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import com.ttk.dto.BaseVO;

public class OnlineInsPolicyVO extends BaseVO{

	private Long lngPolicySeqID = null;
	private Long lngMemberSeqID = null;
	private String strEnrollmentID = "";
	private String strMemberName = "";
	private String strPolicyNbr = "";
	private String strGroupName = "";
	private String strInsCompCodeNbr = "";
	private String strPolicyStatus = "";
	private Long lngPolicyGrpSeqID = null;
	private Long lngLogSeqID = null;
	private String strTemplateName = null;
	
	/** Retrieve the TemplateName
	 * @return Returns the strTemplateName.
	 */
	public String getTemplateName() {
		return strTemplateName;
	}//end of getTemplateName()

	/** Sets the TemplateName
	 * @param strTemplateName The strTemplateName to set.
	 */
	public void setTemplateName(String strTemplateName) {
		this.strTemplateName = strTemplateName;
	}//end of setTemplateName(String strTemplateName)
	
	/** Retrieve the LogSeqID
	 * @return Returns the lngLogSeqID.
	 */
	public Long getLogSeqID() {
		return lngLogSeqID;
	}//end of getLogSeqID()
	
	/** Sets the LogSeqID
	 * @param lngLogSeqID The lngLogSeqID to set.
	 */
	public void setLogSeqID(Long lngLogSeqID) {
		this.lngLogSeqID = lngLogSeqID;
	}//end of setLogSeqID(Long lngLogSeqID)
	
	/** Retrieve the Policy Group Seq ID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()

	/** Sets the Policy Group Seq ID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)
	
	/** Retrieve the MemberSeqID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()
	
	/** Sets the MemberSeqID
	 * @param lngMemberSeqID The lngMemberSeqID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
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
	
	/** Retrieve the EnrollmentID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()
	
	/** Sets the EnrollmentID
	 * @param strEnrollmentID The strEnrollmentID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)
	
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
	
	/** Retrieve the InsCompCodeNbr
	 * @return Returns the strInsCompCodeNbr.
	 */
	public String getInsCompCodeNbr() {
		return strInsCompCodeNbr;
	}//end of getInsCompCodeNbr()
	
	/** Sets the InsCompCodeNbr
	 * @param strInsCompCodeNbr The strInsCompCodeNbr to set.
	 */
	public void setInsCompCodeNbr(String strInsCompCodeNbr) {
		this.strInsCompCodeNbr = strInsCompCodeNbr;
	}//end of setInsCompCodeNbr(String strInsCompCodeNbr)
	
	/** Retrieve the MemberName
	 * @return Returns the strMemberName.
	 */
	public String getMemberName() {
		return strMemberName;
	}//end of getMemberName()
	
	/** Sets the MemberName
	 * @param strmemberName The strmemberName to set.
	 */
	public void setMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}//end of setMemberName(String strMemberName)
	
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
	
	/** Retrieve the PolicyStatus
	 * @return Returns the strPolicyStatus.
	 */
	public String getPolicyStatus() {
		return strPolicyStatus;
	}//end of getPolicyStatus()
	
	/** Sets the PolicyStatus
	 * @param strPolicyStatus The strPolicyStatus to set.
	 */
	public void setPolicyStatus(String strPolicyStatus) {
		this.strPolicyStatus = strPolicyStatus;
	}//end of setPolicyStatus(String strPolicyStatus)

}//end of OnlineInsPolicyVO
