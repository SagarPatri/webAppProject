/**
 * @ (#) Bajaj Jul 6, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 6, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.onlineforms;

import java.util.Date;

import org.dom4j.Document;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO is used get the product, policy rules and save Product policy rules.
 * It accesses TPA_INS_PROD_POLICY_RULES table in database
 */
public class ClaimFormVO extends BaseVO {

    private Long lngProdPolicySeqID=null;       //PROD_POLICY_SEQ_ID
    private Long lngProdPolicyRuleSeqID=null;   //PROD_POLICY_RULE_SEQ_ID
    private Document claimPreauthDocument =null;        //PROD_POLICY_RULE
    private Date  dtFromDate=null;              //VALID_FROM
    private Date  dtEndDate=null;               //VALID_TO
    private Long lngSeqID = null;    
    private String strAllowYN="";
    private String strClaimApprovlStatus="";
    private String  strSwitchType="";
    private String insApprovalStatus="";
    private String strInpStatus="";
    
    private String StrInsurerRemarks="";
    public String getInsurerRemarks() {
		return StrInsurerRemarks;
	}

	public void setInsurerRemarks(String strInsurerRemarks) {
		StrInsurerRemarks = strInsurerRemarks;
	}

    //PROD_POLICY_SEQ_ID/PROD_POLICY_RULE_SEQ_ID/POLICY_SEQ_ID
   /* private Long lngRuleDataSeqID = null;
    private Integer iRuleExecutionFlag=null;
    private String strImageName = "HistoryIcon";
    private String strImageTitle = "Clause Details";
    */
  

   

	

	/**
	 * @param inpStatus the inpStatus to set
	 */
	public void setInpStatus(String inpStatus) {
		strInpStatus = inpStatus;
	}

	/**
	 * @return the inpStatus
	 */
	public String getInpStatus() {
		return strInpStatus;
	}

	/**
	 * @param insApprovalStatus the insApprovalStatus to set
	 */
	public void setInsApprovalStatus(String insApprovalStatus) {
		this.insApprovalStatus = insApprovalStatus;
	}

	/**
	 * @return the insApprovalStatus
	 */
	public String getInsApprovalStatus() {
		return insApprovalStatus;
	}

	/**
	 * @param switchType the switchType to set
	 */
	public void setSwitchType(String switchType) {
		strSwitchType = switchType;
	}

	/**
	 * @return the switchType
	 */
	public String getSwitchType() {
		return strSwitchType;
	}

	/** Retrieve the Seq ID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()

    /** Sets the Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)

    /**
     * Retrieve the Rule's End Date
     * @return  dtendDate Date
     */
    public Date getEndDate() {
        return dtEndDate;
    }//end of getEndDate()

    /**
     * Retrieve the Rule's End Date in dd/mm/yy format
     * @return dtFromDate String
     */
    public String getStrEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//end of getStrEndDate()
    /**
     * Sets the Rule's End Date
     * @param  dtEndDate Date
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)

    /**
     * Retrieve the Rule's Start Date in dd/mm/yy format
     * @return dtFromDate String
     */
    public String getStrFromDate() {
        return TTKCommon.getFormattedDate(dtFromDate);
    }//end of getStrFromDate()

    /**
     * Retrieve the Rule's Start Date
     * @return  dtFromDate Date
     */
    public Date getFromDate() {
        return dtFromDate;
    }//end of getFromDate()

    /**
     * Sets the Rule's Start Date
     * @param  dtFromDate Date
     */
    public void setFromDate(Date dtFromDate) {
        this.dtFromDate = dtFromDate;
    }//end of setFromDate(Date dtFromDate)

    /**
     * Retrieve the Product Policy Rule SeqID
     * @return  lngProdPolicyRuleSeqID Long
     */
    public Long getProdPolicyRuleSeqID() {
        return lngProdPolicyRuleSeqID;
    }//end of getProdPolicyRuleSeqID()

    /**
     * Sets the Product Policy Rule SeqID
     * @param  lngProdPolicyRuleSeqID Long
     */
    public void setProdPolicyRuleSeqID(Long lngProdPolicyRuleSeqID) {
        this.lngProdPolicyRuleSeqID = lngProdPolicyRuleSeqID;
    }//end of setProdPolicyRuleSeqID(Long lngProdPolicyRuleSeqID)

    /**
     * Retrieve the Product Policy SeqID
     * @return  lngProdPolicySeqID Long
     */
    public Long getProdPolicySeqID() {
        return lngProdPolicySeqID;
    }//end of getProdPolicySeqID()

    /**
     * Sets the Product Policy SeqID
     * @param  lngProdPolicySeqID Long
     */
    public void setProdPolicySeqID(Long lngProdPolicySeqID) {
        this.lngProdPolicySeqID = lngProdPolicySeqID;
    }//end of setProdPolicySeqID(Long lngProdPolicySeqID)

	/**
	 * @param claimPreauthDocument the claimPreauthDocument to set
	 */
	public void setClaimPreauthDocument(Document claimPreauthDocument) {
		this.claimPreauthDocument = claimPreauthDocument;
	}

	/**
	 * @return the claimPreauthDocument
	 */
	public Document getClaimPreauthDocument() {
		return claimPreauthDocument;
	}

	/**
	 * @param allowYN the allowYN to set
	 */
	public void setAllowYN(String allowYN) {
		strAllowYN = allowYN;
	}

	/**
	 * @return the allowYN
	 */
	public String getAllowYN() {
		return strAllowYN;
	}

	
 
}//end of RuleVO.java
