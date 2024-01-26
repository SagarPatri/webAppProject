/**
 * @ (#)  RuleVO.java Apr 15, 2006
 * Project      : TTKPROJECT
 * File         : RuleVO.java
 * Author       : Unni V Mana
 * Company      : Span Systems Corporation
 * Date Created : Apr 15, 2006
 *
 * @author       :  Unni V Mana
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

	package com.ttk.dto.enrollment;

	import java.util.Date;

import org.dom4j.Document;
	import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;
	
public class MemberRuleVO extends BaseVO {
	
	private Date  dtFrom=null; 
	private Date  dtTo=null; 
	
	
	/*private String strDateFrom=null;
	private String strDateTo=null;*/
	
	private String strAction;
	private Long lngMemeberSeqID=null;
	private Document memberRule=null;
	
	
	
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
	public Long getMemeberSeqID() {
		return lngMemeberSeqID;
	}
	public void setMemeberSeqID(Long lngMemeberSeqID) {
		this.lngMemeberSeqID = lngMemeberSeqID;
	}
	public Document getMemberRule() {
		return memberRule;
	}
	public void setMemberRule(Document memberRule) {
		this.memberRule = memberRule;
	}
	public String getAction() {
		return strAction;
	}
	public void setAction(String strAction) {
		this.strAction = strAction;
	}


}
