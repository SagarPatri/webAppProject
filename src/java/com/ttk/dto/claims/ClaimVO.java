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

public class ClaimVO extends BaseVO{
	
	private Long lngClaimSeqID = null;
	private String strClaimNbr = "";
	private String strHospitalName="";
    private String strEnrollmentID="";
    private String strClaimantName="";
    private Long lngOfficeSeqID = null;
    private Long lngPolicySeqID = null;
    private Long lngMemberSeqID = null;
    private String strAssignImageName = "UserIcon";
    private String strAssignImageTitle = "Assign";
    private Long lngAssignUserSeqID = null;
    private String strBufferAllowedYN = "";
    private String strShortfallYN = "";
    private String strShortfallImageName="Blank";
    private String strShortfallImageTitle="";
    private String strClaimDmsRefID = "";
    private Date dtAdmissionDate = null;
	
	//koc 11 koc11
    private String strGetInvImageName= null;
    private String strgetInvImageTitle = null;
    private String strInvestigationImageName = "AddIcon"; //UserIcon1
    private String strInvestigationImageTitle = "Investigation";
    
    public String getInvestigationImageName() {
		return strInvestigationImageName;
	}//end of getAssignImageName()

	/** Sets the Assign Image Name
	 * @param strAssignImageName The strAssignImageName to set.
	 */
	public void setInvestigationImageName(String strInvestigationImageName) {
		this.strInvestigationImageName = strInvestigationImageName;
	}//end of setAssignImageName(String strAssignImageName)

	/** Retrieve the Assign Image Title
	 * @return Returns the strAssignImageTitle.
	 */
	public String getInvestigationImageTitle() {
		return strInvestigationImageTitle;
	}//end of getAssignImageTitle()

	/** Sets the Assign Image Title
	 * @param strAssignImageTitle The strAssignImageTitle to set.
	 */
	public void setInvestigationImageTitle(String strInvestigationImageTitle) {
		this.strInvestigationImageTitle = strInvestigationImageTitle;
	}//end of setAssignImageTitle(String strAssignImageTitle)
    
    public String getInvImageName() {
		return strGetInvImageName;
	}//end of getShortfallImageName()

	/** Sets the strGetInvImageName Image Name
	 * @param strShortfallImageName The strShortfallImageName to set.
	 */
	public void setInvImageName(String strGetInvImageName) {
		this.strGetInvImageName = strGetInvImageName;
	}//end of setShortfallImageName(String strShortfallImageName)

	/** Retrieve the getInvImageTitle Image Title
	 * @return Returns the strShortfallImageTitle.
	 */
	public String getInvImageTitle() {
		return strgetInvImageTitle;
	}//end of getShortfallImageTitle()

	/** Sets the Shortfall Image Title
	 * @param strShortfallImageTitle The strShortfallImageTitle to set.
	 */
	public void setInvImageTitle(String strgetInvImageTitle) {
		this.strgetInvImageTitle = strgetInvImageTitle;
	}//end of setInvImageTitle(String strShortfallImageTitle)
	
    /** Retrieve the Admission Date
	 * @return Returns the dtAdmissionDate.
	 */
	public Date getAdmissionDate() {
		return dtAdmissionDate;
	}//end of getAdmissionDate()
	
	/** Retrieve the Admission Date
	 * @return Returns the dtAdmissionDate.
	 */
	public String getClaimAdmissionDate() {
		return TTKCommon.getFormattedDate(dtAdmissionDate);
	}//end of getClaimAdmissionDate()
	
	/** Sets the Admission Date
	 * @param dtAdmissionDate The dtAdmissionDate to set.
	 */
	public void setAdmissionDate(Date dtAdmissionDate) {
		this.dtAdmissionDate = dtAdmissionDate;
	}//end of setAdmissionDate(Date dtAdmissionDate)
	
	/** Retrieve the AssignUserSeqID
	 * @return Returns the lngAssignUserSeqID.
	 */
	public Long getAssignUserSeqID() {
		return lngAssignUserSeqID;
	}//end of getAssignUserSeqID()
	
	/** Sets the AssignUserSeqID
	 * @param lngAssignUserSeqID The lngAssignUserSeqID to set.
	 */
	public void setAssignUserSeqID(Long lngAssignUserSeqID) {
		this.lngAssignUserSeqID = lngAssignUserSeqID;
	}//end of setAssignUserSeqID(Long lngAssignUserSeqID)
	
	/** Retrieve the Claim Seq ID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()
	
	/** Sets the Claim Seq ID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)
	
	/** Retrieve the Member Seq ID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()
	
	/** Sets the Member Seq ID
	 * @param lngMemberSeqID The lngMemberSeqID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
	/** Retrieve the Office Seq ID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()
	
	/** Sets the Office Seq ID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)
	
	/** Retrieve the Policy Seq ID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/** Sets the Policy Seq ID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)
	
	/** Retrieve the AssignImageName
	 * @return Returns the strAssignImageName.
	 */
	public String getAssignImageName() {
		return strAssignImageName;
	}//end of getAssignImageName()
	
	/** Sets the AssignImageName
	 * @param strAssignImageName The strAssignImageName to set.
	 */
	public void setAssignImageName(String strAssignImageName) {
		this.strAssignImageName = strAssignImageName;
	}//end of setAssignImageName(String strAssignImageName)
	
	/** Retrieve the AssignImageTitle
	 * @return Returns the strAssignImageTitle.
	 */
	public String getAssignImageTitle() {
		return strAssignImageTitle;
	}//end of getAssignImageTitle()
	
	/** Sets the AssignImageTitle
	 * @param strAssignImageTitle The strAssignImageTitle to set.
	 */
	public void setAssignImageTitle(String strAssignImageTitle) {
		this.strAssignImageTitle = strAssignImageTitle;
	}//end of setAssignImageTitle(String strAssignImageTitle)
	
	/** Retrieve the BufferAllowedYN
	 * @return Returns the strBufferAllowedYN.
	 */
	public String getBufferAllowedYN() {
		return strBufferAllowedYN;
	}//end of getBufferAllowedYN()
	
	/** Sets the BufferAllowedYN
	 * @param strBufferAllowedYN The strBufferAllowedYN to set.
	 */
	public void setBufferAllowedYN(String strBufferAllowedYN) {
		this.strBufferAllowedYN = strBufferAllowedYN;
	}//end of setBufferAllowedYN(String strBufferAllowedYN)
	
	/** Retrieve the ClaimantName
	 * @return Returns the strClaimantName.
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getClaimantName()
	
	/** Sets the ClaimantName
	 * @param strClaimantName The strClaimantName to set.
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setClaimantName(String strClaimantName)
	
	/** Retrieve the ClaimDmsRefID
	 * @return Returns the strClaimDmsRefID.
	 */
	public String getClaimDmsRefID() {
		return strClaimDmsRefID;
	}//end of getClaimDmsRefID()
	
	/** Sets the ClaimDmsRefID
	 * @param strClaimDmsRefID The strClaimDmsRefID to set.
	 */
	public void setClaimDmsRefID(String strClaimDmsRefID) {
		this.strClaimDmsRefID = strClaimDmsRefID;
	}//end of setClaimDmsRefID(String strClaimDmsRefID)
	
	/** Retrieve the ClaimNbr
	 * @return Returns the strClaimNbr.
	 */
	public String getClaimNbr() {
		return strClaimNbr;
	}//end of getClaimNbr()
	
	/** Sets the ClaimNbr
	 * @param strClaimNbr The strClaimNbr to set.
	 */
	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}//end of setClaimNbr(String strClaimNbr)
	
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
	
	/** Retrieve the HospitalName
	 * @return Returns the strHospitalName.
	 */
	public String getHospitalName() {
		return strHospitalName;
	}//end of getHospitalName()
	
	/** Sets the HospitalName
	 * @param strHospitalName The strHospitalName to set.
	 */
	public void setHospitalName(String strHospitalName) {
		this.strHospitalName = strHospitalName;
	}//end of setHospitalName(String strHospitalName)
	
	/** Retrieve the ShortfallImageName
	 * @return Returns the strShortfallImageName.
	 */
	public String getShortfallImageName() {
		return strShortfallImageName;
	}//end of getShortfallImageName()
	
	/** Sets the ShortfallImageName
	 * @param strShortfallImageName The strShortfallImageName to set.
	 */
	public void setShortfallImageName(String strShortfallImageName) {
		this.strShortfallImageName = strShortfallImageName;
	}//end of setShortfallImageName(String strShortfallImageName)
	
	/** Retrieve the ShortfallImageTitle
	 * @return Returns the strShortfallImageTitle.
	 */
	public String getShortfallImageTitle() {
		return strShortfallImageTitle;
	}//end of getShortfallImageTitle()
	
	/** Sets the ShortfallImageTitle
	 * @param strShortfallImageTitle The strShortfallImageTitle to set.
	 */
	public void setShortfallImageTitle(String strShortfallImageTitle) {
		this.strShortfallImageTitle = strShortfallImageTitle;
	}//end of setShortfallImageTitle(String strShortfallImageTitle)
	
	/** Retrieve the ShortfallYN
	 * @return Returns the strShortfallYN.
	 */
	public String getShortfallYN() {
		return strShortfallYN;
	}//end of getShortfallYN()
	
	/** Sets the ShortfallYN
	 * @param strShortfallYN The strShortfallYN to set.
	 */
	public void setShortfallYN(String strShortfallYN) {
		this.strShortfallYN = strShortfallYN;
	}//end of setShortfallYN(String strShortfallYN)
    
}//end of ClaimVO
