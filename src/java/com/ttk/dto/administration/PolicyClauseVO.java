/**
 * @ (#) PolicyClauseVO.java Jul 9, 2007
 * Project 	     : TTK HealthCare Services
 * File          : PolicyClauseVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 9, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class PolicyClauseVO extends BaseVO{
	
	private Long lngProdPolicySeqID=null;   //PROD_POLICY_SEQ_ID
	private Long lngClauseSeqID = null;     //CLAUSE_SEQ_ID
	private String strClauseNbr = "";       //CLAUSE_NUMBER
	private String strClauseDesc = "";      //CLAUSE_DESCRIPTION
	private String strSelectedYN = "";      //SELECTED_YN
	private String strClauseYN = "";        //CLAUSE_YN
	private String strActiveYN = "";        //ACTIVE_YN
	private String strClauseImageName="ICDIcon";
	private String strClauseImageTitle="Associate Disease";
		
	//added as per Shortfall cr
	private String strClauseSubType="";
	private String ClauseFor="";
private String strClauseSubTypeDesc="";
	private String ClauseForDesc="";
	
	
	/**
	 * @param clauseForDesc the clauseForDesc to set
	 */
	public void setClauseForDesc(String clauseForDesc) {
		this.ClauseForDesc = clauseForDesc;
	}

	/**
	 * @return the clauseForDesc
	 */
	public String getClauseForDesc() {
		return ClauseForDesc;
	}

	/**
	 * @param clauseSubTypeDesc the clauseSubTypeDesc to set
	 */
	public void setClauseSubTypeDesc(String clauseSubTypeDesc) {
		this.strClauseSubTypeDesc = clauseSubTypeDesc;
	}

	/**
	 * @return the clauseSubTypeDesc
	 */
	public String getClauseSubTypeDesc() {
		return strClauseSubTypeDesc;
	}

	/**
	 * @param clauseFor the clauseFor to set
	 */
	public void setClauseFor(String clauseFor) {
		this.ClauseFor = clauseFor;
	}

	/**
	 * @return the clauseFor
	 */
	public String getClauseFor() {
		return ClauseFor;
	}

	/**
	 * @param clauseSubType the clauseSubType to set
	 */
	public void setClauseSubType(String clauseSubType) {
		this.strClauseSubType = clauseSubType;
	}

	/**
	 * @return the clauseSubType
	 */
	public String getClauseSubType() {
		return strClauseSubType;
	}

	//added as per shortfall cr
	/** Retrieve the ActiveYN
	 * @return Returns the strActiveYN.
	 */
	public String getActiveYN() {
		return strActiveYN;
	}//end of getActiveYN()

	/** Sets the ActiveYN
	 * @param strActiveYN The strActiveYN to set.
	 */
	public void setActiveYN(String strActiveYN) {
		this.strActiveYN = strActiveYN;
	}//end of setActiveYN(String strActiveYN)

	/** Retrieve the ClauseYN
	 * @return Returns the strClauseYN.
	 */
	public String getClauseYN() {
		return strClauseYN;
	}//end of getClauseYN()

	/** Sets the ClauseYN
	 * @param strClauseYN The strClauseYN to set.
	 */
	public void setClauseYN(String strClauseYN) {
		this.strClauseYN = strClauseYN;
	}//end of setClauseYN(String strClauseYN)

	/** Retrieve the ClauseSeqID
	 * @return Returns the lngClauseSeqID.
	 */
	public Long getClauseSeqID() {
		return lngClauseSeqID;
	}//end of getClauseSeqID()
	
	/** Sets the ClauseSeqID
	 * @param lngClauseSeqID The lngClauseSeqID to set.
	 */
	public void setClauseSeqID(Long lngClauseSeqID) {
		this.lngClauseSeqID = lngClauseSeqID;
	}//end of setClauseSeqID(Long lngClauseSeqID)
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the Clause Description
	 * @return Returns the strClauseDesc.
	 */
	public String getClauseDesc() {
		return strClauseDesc;
	}//end of getClauseDesc()
	
	/** Sets the Clause Description
	 * @param strClauseDesc The strClauseDesc to set.
	 */
	public void setClauseDesc(String strClauseDesc) {
		this.strClauseDesc = strClauseDesc;
	}//end of setClauseDesc(String strClauseDesc)
	
	/** Retrieve the ClauseNbr
	 * @return Returns the strClauseNbr.
	 */
	public String getClauseNbr() {
		return strClauseNbr;
	}//end of getClauseNbr()
	
	/** Sets the ClauseNbr
	 * @param strClauseNbr The strClauseNbr to set.
	 */
	public void setClauseNbr(String strClauseNbr) {
		this.strClauseNbr = strClauseNbr;
	}//end of setClauseNbr(String strClauseNbr)

	/** Retrieve the SelectedYN
	 * @return Returns the strSelectedYN.
	 */
	public String getSelectedYN() {
		return strSelectedYN;
	}//end of getSelectedYN()

	/** Sets the SelectedYN
	 * @param strSelectedYN The strSelectedYN to set.
	 */
	public void setSelectedYN(String strSelectedYN) {
		this.strSelectedYN = strSelectedYN;
	}//end of setSelectedYN(String strSelectedYN)

	/** Retrieve the ClauseImageName
	 * @return the strClauseImageName
	 */
	public String getClauseImageName() {
		return strClauseImageName;
	}//end of getClauseImageName()

	/** Sets the ClauseImageName
	 * @param strClauseImageName the strClauseImageName to set
	 */
	public void setClauseImageName(String strClauseImageName) {
		this.strClauseImageName = strClauseImageName;
	}//end of setClauseImageName(String strClauseImageName)

	/** Retrieve the ClauseImageTitle
	 * @return the strClauseImageTitle
	 */
	public String getClauseImageTitle() {
		return strClauseImageTitle;
	}//end of getClauseImageTitle()

	/** Sets the ClauseImageTitle
	 * @param strClauseImageTitle the strClauseImageTitle to set
	 */
	public void setClauseImageTitle(String strClauseImageTitle) {
		this.strClauseImageTitle = strClauseImageTitle;
	}//end of setClauseImageTitle(String strClauseImageTitle)
	
}//end of PolicyClauseVO
