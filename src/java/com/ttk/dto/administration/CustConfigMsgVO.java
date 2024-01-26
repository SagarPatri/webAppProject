/**
 * @ (#) ConfigCustomMessageAction.java Oct 12, 2010
 * Project      : TTK HealthCare Services
 * File         : ConfigCustomMessageAction.java
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : Oct 12, 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;


public class CustConfigMsgVO extends BaseVO{
	
	private String strConfigYN="";
	private String strMessage="";
	private Long lngProdPolicySeqID = null;
	
	/** Retrieve the ConfigYN
	 * @return the strConfigYN
	 */
	public String getConfigYN() {
		return strConfigYN;
	}// end of getConfigYN()
	/** Sets the ConfigYN
	 * @param strConfigYN the strConfigYN to set
	 */
	public void setConfigYN(String strConfigYN) {
		this.strConfigYN = strConfigYN;
	}//end of setConfigYN(String strConfigYN)
	/** Retrieve the Message
	 * @return the strMessage
	 */
	public String getMessage() {
		return strMessage;
	}//end of getMessage()
	/** Sets the Message
	 * @param strMessage the strMessage to set
	 */
	public void setMessage(String strMessage) {
		this.strMessage = strMessage;
	}//end of setMessage(String strMessage)
	
	/** Retrieve the ProdPolicySeqID
	 * @return the lngProdPolicySeqID
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID the lngProdPolicySeqID to set
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)

}// end of CustConfigMsgVO
