/**
 * @ (#) RuleVO.java Jul 6, 2006
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

package com.ttk.dto.administration;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.dom4j.Document;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO is used get the product, policy rules and save Product policy rules.
 * It accesses TPA_INS_PROD_POLICY_RULES table in database
 */
public class RuleVO extends BaseVO {

    private Long lngProdPolicySeqID=null;       //PROD_POLICY_SEQ_ID
    private Long lngProdPolicyRuleSeqID=null;   //PROD_POLICY_RULE_SEQ_ID
    private Document ruleDocument =null;        //PROD_POLICY_RULE
    private Date  dtFromDate=null;              //VALID_FROM
    private Date  dtEndDate=null;               //VALID_TO
    private Long lngSeqID = null;              //PROD_POLICY_SEQ_ID/PROD_POLICY_RULE_SEQ_ID/POLICY_SEQ_ID
    private Long lngRuleDataSeqID = null;
    private Integer iRuleExecutionFlag=null;
    private String strImageName = "HistoryIcon";
    private String strImageTitle = "Clause Details";
    private Long policySeqId=null;
    //changes on jan 11th koc 1099
    private String OverrideCompletedYN="N";
    
    
    private Writer providerCopayRulesWriter;
    private Reader providerCopayRulesReader;
    
    

    private String benefitID="";
    private String benefitRuleCompletedYN="";
    private String benefitRuleXml="";
    private Long benefitRuleSeqID;
    private String proPolAuthority="";
    
    
    
    public void setBenefitRuleSeqID(Long benefitRuleSeqID) {
		this.benefitRuleSeqID = benefitRuleSeqID;
	}
    public Long getBenefitRuleSeqID() {
		return benefitRuleSeqID;
	}
    
    public void setBenefitRuleXml(String benefitRuleXml) {
		this.benefitRuleXml = benefitRuleXml;
	}
    public String getBenefitRuleXml() {
		return benefitRuleXml;
	}
    public void setBenefitRuleCompletedYN(String benefitRuleCompletedYN) {
		this.benefitRuleCompletedYN = benefitRuleCompletedYN;
	}
    public String getBenefitRuleCompletedYN() {
		return benefitRuleCompletedYN;
	}
   
    public void setBenefitID(String benefitID) {
		this.benefitID = benefitID;
	}
    public String getBenefitID() {
		return benefitID;
	}
 
    public void setProviderCopayRulesReader(Reader providerCopayRulesReader) {
		this.providerCopayRulesReader = providerCopayRulesReader;
	}
    public Reader getProviderCopayRulesReader() {
		return providerCopayRulesReader;
	}
    
    public void setProviderCopayRulesWriter(Writer providerCopayRulesWriter) {
		this.providerCopayRulesWriter = providerCopayRulesWriter;
	}
    public Writer getProviderCopayRulesWriter() {
		return providerCopayRulesWriter;
	}
    
    public void setOverrideCompletedYN(String overrideCompletedYN) {
		OverrideCompletedYN = overrideCompletedYN;
	}

	public String getOverrideCompletedYN() {
		return OverrideCompletedYN;
	}
    //changes on jan 11th koc 1099
 /** Retrieve the ImageName
     * @return Returns the strImageName.
     */
    public String getImageName() {
		return strImageName;
	}//end of getImageName()
    
    /** Sets the ImageName
     * @param strImageName The strImageName to set.
     */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)
	
	/** Retrieve the ImageTitle
     * @return Returns the strImageTitle.
     */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle()
	
	/** Sets the ImageTitle
     * @param strImageTitle The strImageTitle to set.
     */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)

	/** Retrieve the RuleDataSeqID
     * @return Returns the lngRuleDataSeqID.
     */
    public Long getRuleDataSeqID() {
        return lngRuleDataSeqID;
    }//end of getRuleDataSeqID()

    /** Sets the RuleDataSeqID
     * @param lngRuleDataSeqID The lngRuleDataSeqID to set.
     */
    public void setRuleDataSeqID(Long lngRuleDataSeqID) {
        this.lngRuleDataSeqID = lngRuleDataSeqID;
    }//end of setRuleDataSeqID(Long lngRuleDataSeqID)

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
     * Retrieve the Rule Document
     * @return  ruleDocument Document
     */
    public Document getRuleDocument() {
        return ruleDocument;
    }//end of getRuleDocument()

    /**
     * Sets the Rule Document
     * @param  ruleDocument Document
     */
    public void setRuleDocument(Document ruleDocument) {
        this.ruleDocument = ruleDocument;
    }//end of setRuleDocument(Document ruleDocument)

    /**
     * Retrieve the status of Rule Execution Flag
     * @return  iRuleExecutionFlag Integer
     */
    public Integer getRuleExecutionFlag() {
        return iRuleExecutionFlag;
    }//end of getRuleExecutionFlag()

    /**
     * Sets the status of Rule Execution Flag
     * @param  iRuleExecutionFlag Integer
     */
    public void setRuleExecutionFlag(Integer iRuleExecutionFlag) {
        this.iRuleExecutionFlag = iRuleExecutionFlag;
    }//end of setRuleExecStatus(Integer ruleExecStatus)

	public Long getPolicySeqId() {
		return policySeqId;
	}

	public void setPolicySeqId(Long policySeqId) {
		this.policySeqId = policySeqId;
	}
	public String getProPolAuthority() {
		return proPolAuthority;
	}
	public void setProPolAuthority(String proPolAuthority) {
		this.proPolAuthority = proPolAuthority;
	}
}//end of RuleVO.java
