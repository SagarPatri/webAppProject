
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;


public class EscalationLimitVO extends BaseVO{

	
	private Long lngLimitSeqID = null;
    private Long lngProdPolicySeqID = null;
	private String strPatClmTypeID = "";
	private String strDesc = "";
	private String strFlag = "";
	private String strRemType = "";
	private Integer intEscalateDays = null;

	
	public Long getLimitSeqID() {
		return lngLimitSeqID;
	}
	public void setLimitSeqID(Long lngLimitSeqID) {
		this.lngLimitSeqID = lngLimitSeqID;
	}
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}
	public String getPatClmTypeID() {
		return strPatClmTypeID;
	}
	public void setPatClmTypeID(String strPatClmTypeID) {
		this.strPatClmTypeID = strPatClmTypeID;
	}
	public String getFlag() {
		return strFlag;
	}
	public void setFlag(String strFlag) {
		this.strFlag = strFlag;
	}
	public String getRemType() {
		return strRemType;
	}
	public void setRemType(String strRemType) {
		this.strRemType = strRemType;
	}
	public Integer getEscalateDays() {
		return intEscalateDays;
	}
	public void setEscalateDays(Integer intEscalateDays) {
		this.intEscalateDays = intEscalateDays;
	}
	public String getDesc() {
		return strDesc;
	}
	public void setDesc(String strDesc) {
		this.strDesc = strDesc;
	}
	

}
