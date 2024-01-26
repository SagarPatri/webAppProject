
/**
 * @ (#) HospitalDetailsVo Jul 31, 2006
 * Project 	     : TTK HealthCare Services
 * File          : HospitalizationDetailVO.java
  *  Author       :Kishor Kumar
 * Company      : Rcs Technologies
 * Date Created : May 06 ,2014
 *
 * @author       :Kishor Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.hospital;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class HospitalDetailsVo extends BaseVO{
	
	private Long lngPrevHospClaimSeqID = null;
	private Date dtAdmissionDate = null;
	private String strHospName = "";
	private String strEmpanelNo = "";
	private Date strRegDate = null;
	private String strCity = "";
	private String strState = "";
	private String strAddress = "";
	private String strOffPhone = "";
	private String strPinCode = "";
	private String strCountry	=	"";
	
	private String netWorkTypes	=	"";
	private String hospMailId	=	"";
	private String approvalLimit = "";
	private String strCurrentLicenceID = "";
	private String strProvideRegulatoryAuthority="";
	
	
	
	
	
	public String getCurrentLicenceID() {
		return strCurrentLicenceID;
	}
	public void setCurrentLicenceID(String strCurrentLicenceID) {
		this.strCurrentLicenceID = strCurrentLicenceID;
	}
	public String getProvideRegulatoryAuthority() {
		return strProvideRegulatoryAuthority;
	}
	public void setProvideRegulatoryAuthority(
			String strProvideRegulatoryAuthority) {
		this.strProvideRegulatoryAuthority = strProvideRegulatoryAuthority;
	}
	public String getApprovalLimit() {
		return approvalLimit;
	}
	public void setApprovalLimit(String approvalLimit) {
		this.approvalLimit = approvalLimit;
	}
	
	public String getHospMailId() {
		return hospMailId;
	}
	public void setHospMailId(String hospMailId) {
		this.hospMailId = hospMailId;
	}
	
	public String getOffPhone() {
		return strOffPhone;
	}
	public void setOffPhone(String offPhone) {
		strOffPhone = offPhone;
	}
	public String getPinCode() {
		return strPinCode;
	}
	public void setPinCode(String pinCode) {
		strPinCode = pinCode;
	}
	
	
	public String getHospName() {
		return strHospName;
	}
	public void setHospName(String hospName) {
		strHospName = hospName;
	}
	public String getEmpanelNo() {
		return strEmpanelNo;
	}
	public void setEmpanelNo(String empanelNo) {
		strEmpanelNo = empanelNo;
	}
	public Date getRegDate() {
		return strRegDate;
	}
	public void setRegDate(Date regDate) {
		strRegDate = regDate;
	}
	public String getCity() {
		return strCity;
	}
	public void setCity(String city) {
		strCity = city;
	}
	public String getState() {
		return strState;
	}
	public void setState(String state) {
		strState = state;
	}
	public String getAddress() {
		return strAddress;
	}
	public void setAddress(String address) {
		strAddress = address;
	}
	public String getCountry() {
		return strCountry;
	}
	public void setCountry(String strCountry) {
		this.strCountry = strCountry;
	}
	public String getNetWorkTypes() {
		return netWorkTypes;
	}
	public void setNetWorkTypes(String netWorkTypes) {
		this.netWorkTypes = netWorkTypes;
	}
	
	
	
}//end of HospitalDetailsVo
