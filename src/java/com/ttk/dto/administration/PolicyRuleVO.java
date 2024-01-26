/**
 * @ (#)  PolicyRuleVO.java Mar 24, 2006
 * Project      : TTKPROJECT
 * File         : PolicyRuleVO.java
 * Author       : Unni V Mana
 * Company      : Span Systems Corporation
 * Date Created : Mar 24, 2006
 *
 * @author       :  Unni V Mana
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.administration;


import java.util.Date;

import org.dom4j.Document;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class PolicyRuleVO extends BaseVO {


	
	private Long lngPolicySeqId;
	private Long lngProdPolicyRuleSeqId;
	private Long lngProdPolicySeqId;
	private Document docProdPolicyRule;
	private Long lngPrevProdPolicyRuleSeqId; 
	
	private Date  dtFrom=null; 
	private Date  dtTo=null;
	
	public Document getProdPolicyRule() {
		return docProdPolicyRule;
	}
	public void setProdPolicyRule(Document docProdPolicyRule) {
		this.docProdPolicyRule = docProdPolicyRule;
	}
	public Long getPolicySeqId() {
		return lngPolicySeqId;
	}
	public void setPolicySeqId(Long lngPolicySeqId) {
		this.lngPolicySeqId = lngPolicySeqId;
	}
	public Long getProdPolicyRuleSeqId() {
		return lngProdPolicyRuleSeqId;
	}
	public void setProdPolicyRuleSeqId(Long lngProdPolicyRuleSeqId) {
		this.lngProdPolicyRuleSeqId = lngProdPolicyRuleSeqId;
	}
	public Long getProdPolicySeqId() {
		return lngProdPolicySeqId;
	}
	public void setProdPolicySeqId(Long lngProdPolicySeqId) {
		this.lngProdPolicySeqId = lngProdPolicySeqId;
	}
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
	public String getStrDateFrom() {
		return TTKCommon.getFormattedDate(dtFrom); 
	}
	public void setStrDateFrom(Date strDateFrom) {
		this.dtFrom = strDateFrom;;
	}
	public String getStrDateTo() {
		return TTKCommon.getFormattedDate(dtTo);
	}
	public void setStrDateTo(Date strDateTo) {
		this.dtTo = strDateTo;
	}
}
