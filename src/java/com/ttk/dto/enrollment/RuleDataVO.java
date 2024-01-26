package com.ttk.dto.enrollment;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;
import java.util.Date;
import org.dom4j.Document;

public class RuleDataVO extends BaseVO {

	private Long lPolicyRuleSeqID= null;  
	private Long lPolicySeqID = null;
	private Document policyRule =null; 
	private Date  dtFrom=null; 
	private Date  dtTo=null; 
	private String strAddedBy="";
	private Long lngPrevProdPolicyRuleSeqId;
	
	/*private String strDateFrom=null;
	private String strDateTo=null;*/
	
	public Date getDtFrom() {
		return dtFrom;
	}
	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	public Date getDtTo() {
		return dtTo;
	}
	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
	}
	public Long getPrevProdPolicyRuleSeqId() {
		return lngPrevProdPolicyRuleSeqId;
	}
	public void setPrevProdPolicyRuleSeqId(Long lngPrevProdPolicyRuleSeqId) {
		this.lngPrevProdPolicyRuleSeqId = lngPrevProdPolicyRuleSeqId;
	}
	public Long getPolicyRuleSeqID() {
		return lPolicyRuleSeqID;
	}
	public void setPolicyRuleSeqID(Long policyRuleSeqID) {
		lPolicyRuleSeqID = policyRuleSeqID;
	}
	public Long getPolicySeqID() {
		return lPolicySeqID;
	}
	public void setPolicySeqID(Long policySeqID) {
		lPolicySeqID = policySeqID;
	}
	public Document getPolicyRule() {
		return policyRule;
	}
	public void setPolicyRule(Document policyRule) {
		this.policyRule = policyRule;
	}
	public String getStrAddedBy() {
		return strAddedBy;
	}
	public void setStrAddedBy(String strAddedBy) {
		this.strAddedBy = strAddedBy;
	}
	
	public String getStrDateFrom() {
		return TTKCommon.getFormattedDate(dtFrom);
	}
	public void setStrDateFrom(Date strDateFrom) {
		this.dtFrom = strDateFrom;
	}
	public String getStrDateTo() {
		return TTKCommon.getFormattedDate(dtTo);
	}
	public void setStrDateTo(Date strDateTo) {
		this.dtTo = strDateTo;
	}

}
