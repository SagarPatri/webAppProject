/** 
 *  @ (#)ClaimantVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : ClaimantVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */

package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;




public class ProviderDetailsVO extends BaseVO{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
private Long providerSeqId;
private String providerId;
private String providerName;
private String authority;
private String providerSpecificRemarks;
private String providerType;
private String curencyType;
private String providerAuthority;

private String emirate;
private String area;
private String providerPhone;
private String vatTrnCode;

public String getProviderAuthority() {
	return providerAuthority;
}
public void setProviderAuthority(String providerAuthority) {
	this.providerAuthority = providerAuthority;
}
public String getProviderType() {
	return providerType;
}
public void setProviderType(String providerType) {
	this.providerType = providerType;
}
public String getProviderSpecificRemarks() {
	return providerSpecificRemarks;
}
public void setProviderSpecificRemarks(String providerSpecificRemarks) {
	this.providerSpecificRemarks = providerSpecificRemarks;
}
public Long getProviderSeqId() {
	return providerSeqId;
}
public void setProviderSeqId(Long providerSeqId) {
	this.providerSeqId = providerSeqId;
}
public String getProviderId() {
	return providerId;
}
public void setProviderId(String providerId) {
	this.providerId = providerId;
}
public String getProviderName() {
	return providerName;
}
public void setProviderName(String providerName) {
	this.providerName = providerName;
}
public String getAuthority() {
	return authority;
}
public void setAuthority(String authority) {
	this.authority = authority;
}
public String getCurencyType() {
	return curencyType;
}
public void setCurencyType(String curencyType) {
	this.curencyType = curencyType;
}
public String getEmirate() {
	return emirate;
}
public void setEmirate(String emirate) {
	this.emirate = emirate;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getProviderPhone() {
	return providerPhone;
}
public void setProviderPhone(String prividerPhone) {
	this.providerPhone = prividerPhone;
}
public String getVatTrnCode() {
	return vatTrnCode;
}
public void setVatTrnCode(String vatTrnCode) {
	this.vatTrnCode = vatTrnCode;
}

}//end of ProviderDetailsVO.java