package com.ttk.dto.maintenance;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;
import java.util.Date;

public class InvestigationGroupVO extends BaseVO {
	private String groupID = "";
	private String groupName = "";
	private Date agencyEmpanelDate;private String agencyEmpanelTime;private String agencyEmpanelDay;
	
	private String address1;
	private String address2;
	private String address3;
	private String stateTypeId;
	private String cityTypeId;
	private String pin_code;
	private String countryId;
	private String officePhoneNo1;
	private String officePhoneNo2;
	private String faxNo;
	private String mobileNo;
	private String email1;
	private String email2;
	private String invActiveYN;
	
	public String getInvestStringDate() {
        return TTKCommon.getFormattedDate(agencyEmpanelDate);
    }//end of getInvestStringDate()
	public String getInvestDateTime() {
        return TTKCommon.getFormattedDateHour(agencyEmpanelDate);
    }//end of getInvestDateTime()
	
	public String getAgencyEmpanelTime() {
		return agencyEmpanelTime;
	}
	public void setAgencyEmpanelTime(String agencyEmpanelTime) {
		this.agencyEmpanelTime = agencyEmpanelTime;
	}
	public String getAgencyEmpanelDay() {
		return agencyEmpanelDay;
	}
	public void setAgencyEmpanelDay(String agencyEmpanelDay) {
		this.agencyEmpanelDay = agencyEmpanelDay;
	}
	public String getInvActiveYN() {
		return invActiveYN;
	}
	public void setInvActiveYN(String invActiveYN) {
		this.invActiveYN = invActiveYN;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getAgencyEmpanelDate() {
		return agencyEmpanelDate;
	}
	public void setAgencyEmpanelDate(Date agencyEmpanelDate) {
		this.agencyEmpanelDate = agencyEmpanelDate;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getStateTypeId() {
		return stateTypeId;
	}
	public void setStateTypeId(String stateTypeId) {
		this.stateTypeId = stateTypeId;
	}
	public String getCityTypeId() {
		return cityTypeId;
	}
	public void setCityTypeId(String cityTypeId) {
		this.cityTypeId = cityTypeId;
	}
	public String getPin_code() {
		return pin_code;
	}
	public void setPin_code(String pinCode) {
		pin_code = pinCode;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getOfficePhoneNo1() {
		return officePhoneNo1;
	}
	public void setOfficePhoneNo1(String officePhoneNo1) {
		this.officePhoneNo1 = officePhoneNo1;
	}
	public String getOfficePhoneNo2() {
		return officePhoneNo2;
	}
	public void setOfficePhoneNo2(String officePhoneNo2) {
		this.officePhoneNo2 = officePhoneNo2;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}