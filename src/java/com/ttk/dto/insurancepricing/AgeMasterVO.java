/**
 * @ (#) InsuranceFeedbackVO.java Oct 24, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceFeedbackVO.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 24, 2005
 *
 * @author       :  Ravindra

 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.insurancepricing;

import com.ttk.dto.BaseVO;
public class AgeMasterVO extends BaseVO{
	
	@Override
	public String toString() {
		return "AgeMasterVO [groupProfileSeqID=" + groupProfileSeqID
				+ ", inpS2SeqID=" + inpS2SeqID + ", val=" + val + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String column5;
	private String column6;
	private Long groupProfileSeqID= null;
	private Long inpS2SeqID= null;
	private String val;
	private String ovrlPofloDist;
	private String incmFlag;
	
	
	public String getIncmFlag() {
		return incmFlag;
	}
	public void setIncmFlag(String incmFlag) {
		this.incmFlag = incmFlag;
	}
	public String getOvrlPofloDist() {
		return ovrlPofloDist;
	}
	public void setOvrlPofloDist(String ovrlPofloDist) {
		this.ovrlPofloDist = ovrlPofloDist;
	}
	public Long getInpS2SeqID() {
		return inpS2SeqID;
	}
	public void setInpS2SeqID(Long inpS2SeqID) {
		this.inpS2SeqID = inpS2SeqID;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String pricingNumberAlert = "";
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	public String getColumn2() {
		return column2;
	}
	public void setColumn2(String column2) {
		this.column2 = column2;
	}
	public String getColumn3() {
		return column3;
	}
	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	public String getColumn4() {
		return column4;
	}
	public void setColumn4(String column4) {
		this.column4 = column4;
	}
	public String getColumn5() {
		return column5;
	}
	public void setColumn5(String column5) {
		this.column5 = column5;
	}
	public String getColumn6() {
		return column6;
	}
	public void setColumn6(String column6) {
		this.column6 = column6;
	}
	public Long getGroupProfileSeqID() {
		return groupProfileSeqID;
	}
	public void setGroupProfileSeqID(Long groupProfileSeqID) {
		this.groupProfileSeqID = groupProfileSeqID;
	}
	public String getPricingNumberAlert() {
		return pricingNumberAlert;
	}
	public void setPricingNumberAlert(String pricingNumberAlert) {
		this.pricingNumberAlert = pricingNumberAlert;
	}
	
	
}// End of InsuranceFeedbackVO

