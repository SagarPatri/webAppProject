
/**
 * @ (#) PreRequisiteVO.java Sep 19, 2005
 * Project      : intX
 * File         : PreRequisiteVO.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : Jan 20 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any address. 
 * The corresponding DB Table is TPA_HOSP_ADDRESS.
 */
public class PreRequisiteVO extends BaseVO{
	

	//declaring private methods for VO
	private String strHospName	=	"";
	private String strLicenceNo	=	"";
	private String strHospMail	=	"";
	private String strUserId	=	"";
	private String strPassword	=	"";
	private String strContactSeqId	=	"";
	private Long   lMobileNo		=	null;
	
	//Dashboard getters
	private String strEmpanelEvent			=	"";
	private Long   lTotalProviders			=	null;
	private Long   lCompletedProviders		=	null;
	private Long   lPendingProviders		=	null;
	private String strRemarks				=	"";
	private String strHealthAuth			=	"";
	
	public String getHealthAuth() {
		return strHealthAuth;
	}
	public void setHealthAuth(String strHealthAuth) {
		this.strHealthAuth = strHealthAuth;
	}

	public String getRemarks() {
		return strRemarks;
	}
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}
	
	public String getEmpanelEvent() {
		return strEmpanelEvent;
	}
	public void setEmpanelEvent(String strEmpanelEvent) {
		this.strEmpanelEvent = strEmpanelEvent;
	}
	public Long getTotalProviders() {
		return lTotalProviders;
	}
	public void setTotalProviders(Long lTotalProviders) {
		this.lTotalProviders = lTotalProviders;
	}
	public Long getCompletedProviders() {
		return lCompletedProviders;
	}
	public void setCompletedProviders(Long lCompletedProviders) {
		this.lCompletedProviders = lCompletedProviders;
	}
	public Long getPendingProviders() {
		return lPendingProviders;
	}
	public void setPendingProviders(Long lPendingProviders) {
		this.lPendingProviders = lPendingProviders;
	}
	
	
	public String getContactSeqId() {
		return strContactSeqId;
	}
	public void setContactSeqId(String strContactSeqId) {
		this.strContactSeqId = strContactSeqId;
	}
	
	
	public Long getMobileNo() {
		return lMobileNo;
	}
	public void setMobileNo(Long lMobileNo) {
		this.lMobileNo = lMobileNo;
	}
	/*
	 * Get the hospital name
	 */
	public String getHospName() {
		return strHospName;
	}
	/*
	 * Set the hospital name
	 */
	public void setHospName(String strHospName) {
		this.strHospName = strHospName;
	}
	/*
	 * Get the Licence No
	 */
	public String getLicenceNo() {
		return strLicenceNo;
	}
	/*
	 * Set the Licence No
	 */
	public void setLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}
	/*
	 * Get the Hospital Mail
	 */
	public String getHospMail() {
		return strHospMail;
	}
	/*
	 * Set the Hospital Mail
	 */
	public void setHospMail(String strHospMail) {
		this.strHospMail = strHospMail;
	}
	/*
	 * Get the User ID
	 */
	public String getUserId() {
		return strUserId;
	}
	/*
	* Set the User ID
	 */
	public void setUserId(String strUserId) {
		this.strUserId = strUserId;
	}
	/*
	 * Get the Password
	 */
	public String getPassword() {
		return strPassword;
	}
	/*
	 * Set Password
	 */
	public void setPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	
	
	
}//end of PreRequisiteVO.java
