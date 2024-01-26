/*
 * Added as per kOC 1285 Change Request  
 * 
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;
public class DomConfigVO extends BaseVO {
	
	private Long lngProdPolicySeqID = null;
	private String strSelectedType="";
	public void setSelectedType(String selectedType) {
		strSelectedType = selectedType;
	}
	public String getSelectedType() {
		return strSelectedType;
	}
	public void setProdPolicySeqID(Long prodPolicySeqID) {
		lngProdPolicySeqID = prodPolicySeqID;
	}
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}
}
