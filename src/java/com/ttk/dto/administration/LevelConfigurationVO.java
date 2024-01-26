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

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class LevelConfigurationVO extends BaseVO{
	
	private Long lngBufferSeqId = null;   
	private Long lngProdPolicySeqID=null;   //PROD_POLICY_SEQ_ID
	private Long lngPolicy_seq_id = null;     //CLAUSE_SEQ_ID
	private String strBufferType="";
	private String strGeneralType="";
    private String  strLevelType = "";       //levels
	private BigDecimal bdLevelsLimit = null;
	private String strRemarks = "";      //CLAUSE_DESCRIPTION
	private String  strLevel = ""; 
	private String  strLevelDesc = ""; 
	
	
	
	public Long getBufferSeqId() {
		return lngBufferSeqId;
	}

	public void setBufferSeqId(Long lngBufferSeqId) {
		this.lngBufferSeqId = lngBufferSeqId;
	}
	

	
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
	




	/** Sets the ClauseImageTitle
	 * @param strClauseImageTitle the strClauseImageTitle to set
	 */


	public BigDecimal getLevelsLimit() {
		return bdLevelsLimit;
	}

	public void setLevelsLimit(BigDecimal bdLevelsLimit) {
		this.bdLevelsLimit = bdLevelsLimit;
	}

	public String getRemarks() {
		return strRemarks;
	}

	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public Long getPolicy_seq_id() {
		return lngPolicy_seq_id;
	}

	public void setPolicy_seq_id(Long lngPolicy_seq_id) {
		this.lngPolicy_seq_id = lngPolicy_seq_id;
	}

	
	public String getBufferType() {
		return strBufferType;
	}

	public void setBufferType(String strBufferType) {
		this.strBufferType = strBufferType;
	}

	public String getLevelType() {
		return strLevelType;
	}

	public void setLevelType(String strLevelType) {
		this.strLevelType = strLevelType;
	}

	public String getLevel() {
		return strLevel;
	}

	public void setLevel(String strLevel) {
		this.strLevel = strLevel;
	}

	public String getGeneralType() {
		return strGeneralType;
	}

	public void setGeneralType(String strGeneralType) {
		this.strGeneralType = strGeneralType;
	}

	public String getLevelDesc() {
		return strLevelDesc;
	}

	public void setLevelDesc(String strLevelDesc) {
		this.strLevelDesc = strLevelDesc;
	}


	
}//end of PolicyClauseVO
