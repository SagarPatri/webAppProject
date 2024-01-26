/**
 * @ (#)  CardRuleVO.java Nov 4, 2005
 * Project      : TTKPROJECT
 * File         : CardRuleVO.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 4, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class CardRuleVO extends BaseVO {
	private Long lProdPolicySeqId= null;  //PROD_POLICY_SEQ_ID
	private Long lCardRulesSeqId = null;//CARD_RULES_SEQ_ID
	private String strEnrollTypeId ="";  //ENROL_TYPE_ID
	private String strCardRuleTypeId=""; //CARD_RULE_TYPE_ID
	private String strGeneralTypeId="";  //GENERAL_TYPE_ID
    private String[] strGeneralTypeIdList = null;//GENERAL_TYPE_ID
    private ArrayList alGnrlTypeIdList = null;
    private String[] strCardRuleTypeIdList = null;//CARD_RULE_TYPE_ID
    private Long[] lCardRulesSeqIdList = null;
    private String strDescription = "";//DESCRIPTION
    private String strAnsHeaderType = ""; //ANSWER_HEADER_TYPE
    private String strOptions = "";
    private Long lngInsSeqID = null;
    private String strShortRemarks = "";
    private String strShowRemarks = "";
    private String[] strShortRemarksList = null;
    
    /** Retrieve the Short Remarks List
	 * @return Returns the strShortRemarksList.
	 */
	public String[] getShortRemarksList() {
		return strShortRemarksList;
	}//end of getShortRemarksList()

	/** Sets the Short Remarks List
	 * @param strShortRemarksList The strShortRemarksList to set.
	 */
	public void setShortRemarksList(String[] strShortRemarksList) {
		this.strShortRemarksList = strShortRemarksList;
	}//end of setShortRemarksList(String[] strShortRemarksList)

	/** Retrieve the Show Remarks
	 * @return Returns the strShowRemarks.
	 */
	public String getShowRemarks() {
		return strShowRemarks;
	}//end of getShowRemarks()

	/** Sets the Show Remarks
	 * @param strShowRemarks The strShowRemarks to set.
	 */
	public void setShowRemarks(String strShowRemarks) {
		this.strShowRemarks = strShowRemarks;
	}//end of setShowRemarks(String strShowRemarks)

	/** Retrieve the Short Remarks
	 * @return Returns the strShortRemarks.
	 */
	public String getShortRemarks() {
		return strShortRemarks;
	}//end of getShortRemarks()

	/** Sets the ShortRemarks
	 * @param strShortRemarks The strShortRemarks to set.
	 */
	public void setShortRemarks(String strShortRemarks) {
		this.strShortRemarks = strShortRemarks;
	}//end of setShortRemarks(String strShortRemarks)

	/** Retrieve the Insurance Seq ID
	 * @return Returns the lngInsSeqID.
	 */
	public Long getInsSeqID() {
		return lngInsSeqID;
	}//end of getInsSeqID()

	/** Sets the Insurance Seq ID
	 * @param lngInsSeqID The lngInsSeqID to set.
	 */
	public void setInsSeqID(Long lngInsSeqID) {
		this.lngInsSeqID = lngInsSeqID;
	}//end of setInsSeqID(Long lngInsSeqID)

	/** This method returns the lCardRulesSeqId
	 * @return Returns the lCardRulesSeqId.
	 */
	public Long getCardRulesSeqId() {
		return lCardRulesSeqId;
	}// End of getCardRulesSeqId()
	
	/** This method sets the lCardRulesSeqId
	 * @param cardRulesSeqId The lCardRulesSeqId to set.
	 */
	public void setCardRulesSeqId(Long cardRulesSeqId) {
        lCardRulesSeqId = cardRulesSeqId;
	}// End of setCardRulesSeqId(Long cardRulesSeqId)
	
	/** This method returns the lProdPolicySeqId
	 * @return Returns the lProdPolicySeqId.
	 */
	public Long getProdPolicySeqId() {
		return lProdPolicySeqId;
	}// End of getProdPolicySeqId()
	
	/** This method sets the lProdPolicySeqId
	 * @param prodPolicySeqId The lProdPolicySeqId to set.
	 */
	public void setProdPolicySeqId(Long prodPolicySeqId) {
        lProdPolicySeqId = prodPolicySeqId;
	}// End of setProdPolicySeqId(Long prodPolicySeqId)
	
	/** This method returns the strCardRuleTypeId
	 * @return Returns the strCardRuleTypeId.
	 */
	public String getCardRuleTypeId() {
		return strCardRuleTypeId;
	}// End of getCardRuleTypeId()
	
	/** This method sets the strCardRuleTypeId
	 * @param strCardRuleTypeId The strCardRuleTypeId to set.
	 */
	public void setCardRuleTypeId(String strCardRuleTypeId) {
		this.strCardRuleTypeId = strCardRuleTypeId;
	}// End of setCardRuleTypeId(String strCardRuleTypeId)
	
	/** This method returns the strEnrollTypeId
	 * @return Returns the strEnrollTypeId.
	 */
	public String getEnrollTypeId() {
		return strEnrollTypeId;
	}//End of getEnrollTypeId()
	
	/** This method sets the strEnrollTypeId
	 * @param strEnrollTypeId The strEnrollTypeId to set.
	 */
	public void setEnrollTypeId(String strEnrollTypeId) {
		this.strEnrollTypeId = strEnrollTypeId;
	}// End of setEnrollTypeId(String strEnrollTypeId)
	
	/** This method returns the strGeneralTypeId
	 * @return Returns the strGeneralTypeId.
	 */
	public String getGeneralTypeId() {
		return strGeneralTypeId;
	}// End of getGeneralTypeId()
	
	/** This method sets the strGeneralTypeId
	 * @param strGeneralTypeId The strGeneralTypeId to set.
	 */
	public void setGeneralTypeId(String strGeneralTypeId) {
		this.strGeneralTypeId = strGeneralTypeId;
	}// End of setGeneralTypeId(String strGeneralTypeId)
	
    /** This method returns the strCardRuleTypeIdList
     * @return Returns the strCardRuleTypeIdList.
     */
    public String[] getCardRuleTypeIdList() {
        return strCardRuleTypeIdList;
    }//end of getCardRuleTypeIdList()
    
    /** Sets the strCardRuleTypeIdList
     * @param strCardRuleTypeIdList The strCardRuleTypeIdList to set.
     */
    public void setCardRuleTypeIdList(String[] strCardRuleTypeIdList) {
        this.strCardRuleTypeIdList = strCardRuleTypeIdList;
    }//end of setCardRuleTypeIdList(String[] strCardRuleTypeIdList)
    
    /** this method returns the strGeneralTypeIdList
     * @return Returns the strGeneralTypeIdList.
     */
    public String[] getGeneralTypeIdList() {
        return strGeneralTypeIdList;
    }//end of getGeneralTypeIdList()
    
    /** this method sets the strGeneralTypeIdList
     * @param strGeneralTypeIdList The strGeneralTypeIdList to set.
     */
    public void setGeneralTypeIdList(String[] strGeneralTypeIdList) {
        this.strGeneralTypeIdList = strGeneralTypeIdList;
    }//end of setGeneralTypeIdList(String[] strGeneralTypeIdList)
    
    /** This method returns the Card Rules Sequence Id List
     * @return Returns the lCardRulesSeqIdList.
     */
    public Long[] getCardRulesSeqIdList() {
        return lCardRulesSeqIdList;
    }//end of getCardRulesSeqIdList() 
    
    /** This method sets the Card Rules Sequence Id List
     * @param cardRulesSeqIdList The lCardRulesSeqIdList to set.
     */
    public void setCardRulesSeqIdList(Long[] cardRulesSeqIdList) {
        lCardRulesSeqIdList = cardRulesSeqIdList;
    }//end of setCardRulesSeqIdList(Long[] cardRulesSeqIdList)
    
    /** This method returns the Answer Header Type
     * @return Returns the strAnsHeaderType.
     */
    public String getAnsHeaderType() {
        return strAnsHeaderType;
    }//end of getAnsHeaderType()
    
    /** This method sets the Answer Header Type
     * @param strAnsHeaderType The strAnsHeaderType to set.
     */
    public void setAnsHeaderType(String strAnsHeaderType) {
        this.strAnsHeaderType = strAnsHeaderType;
    }//end of setAnsHeaderType(String strAnsHeaderType)
    
    /** This method returns the Description
     * @return Returns the strDescription.
     */
    public String getDescription() {
        return strDescription;
    }//end of getDescription()
    
    /** This method sets the Description
     * @param strDescription The strDescription to set.
     */
    public void setDescription(String strDescription) {
        this.strDescription = strDescription;
    }//end of setDescription(String strDescription)
    
    /** This method returns the General Type Id List
     * @return Returns the alGnrlTypeIdList.
     */
    public ArrayList getGnrlTypeIdList() {
        return alGnrlTypeIdList;
    }//end of getGnrlTypeIdList()
    
    /** This method sets the General Type Id List
     * @param alGnrlTypeIdList The alGnrlTypeIdList to set.
     */
    public void setGnrlTypeIdList(ArrayList alGnrlTypeIdList) {
        this.alGnrlTypeIdList = alGnrlTypeIdList;
    }//end of setGnrlTypeIdList(ArrayList alGnrlTypeIdList)
    
    /** This method returns the Options
     * @return Returns the strOptions.
     */
    public String getOptions() {
        return strOptions;
    }//end of getOptions()
    
    /** This method sets the Options
     * @param strOptions The strOptions to set.
     */
    public void setOptions(String strOptions) {
        this.strOptions = strOptions;
    }//end of setOptions(String strOptions)
}//End of CardRuleVO
