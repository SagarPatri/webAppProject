/**
 * @ (#) RuleSynchronizationVO.java Aug 3, 2007
 * Project 	     : TTK HealthCare Services
 * File          : RuleSynchronizationVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 3, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class RuleSynchronizationVO extends BaseVO{
	

	private Long lngPolicySeqID = null;
	private Long lngProdPolicySeqId = null;
	private String strPolicyNbr = "";
	private String strGroupID = "";
    private String strCorporateName = "";
    private String strPrevPolicyNbr = "";
    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private Date dtSynchDate = null;
    private Date dtInsurerSynchDate = null;//denial process
    private String policyStatusID = null;
    
  //denial process
	public Date getInsurerSynchDate() {
		return dtInsurerSynchDate;
	}

	public void setInsurerSynchDate(Date dtInsurerSynchDate) {
		this.dtInsurerSynchDate = dtInsurerSynchDate;
	}
	public String getInsSynchDate() {
		return TTKCommon.getFormattedDate(dtInsurerSynchDate);
	}//end of getRuleSynchDate()
	//denial process
	
	/** Retrieve the EndDate
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()
	
	/** Retrieve the EndDate
	 * @return Returns the dtEndDate.
	 */
	public String getPolicyEndDate() {
		return  TTKCommon.getFormattedDate(dtEndDate);
	}//end of getPolicyEndDate()
	
	/** Sets the EndDate
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)
	
	/** Retrieve the StartDate
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()
	
	/** Retrieve the StartDate
	 * @return Returns the dtStartDate.
	 */
	public String getPolicyStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getPolicyStartDate()
	
	/** Sets the StartDate
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)
	
	/** Retrieve the SynchDate
	 * @return Returns the dtSynchDate.
	 */
	public Date getSynchDate() {
		return dtSynchDate;
	}//end of getSynchDate()
	
	/** Retrieve the SynchDate
	 * @return Returns the dtSynchDate.
	 */
	public String getRuleSynchDate() {
		return TTKCommon.getFormattedDate(dtSynchDate);
	}//end of getRuleSynchDate()
	
	/** Sets the SynchDate
	 * @param dtSynchDate The dtSynchDate to set.
	 */
	public void setSynchDate(Date dtSynchDate) {
		this.dtSynchDate = dtSynchDate;
	}//end of setSynchDate(Date dtSynchDate)
	
	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)
	
	/** Retrieve the ProdPolicySeqId
	 * @return Returns the lngProdPolicySeqId.
	 */
	public Long getProdPolicySeqId() {
		return lngProdPolicySeqId;
	}//end of getProdSeqId()
	
	/** Sets the ProdSeqId
	 * @param lngProdPolicySeqId The lngProdPolicySeqId to set.
	 */
	public void setProdpolicySeqId(Long lngProdPolicySeqId) {
		this.lngProdPolicySeqId = lngProdPolicySeqId;
	}//end of setProdSeqId(Long lngProdPolicySeqId)
	
	/** Retrieve the CorporateName
	 * @return Returns the strCorporateName.
	 */
	public String getCorporateName() {
		return strCorporateName;
	}//end of getCorporateName()
	
	/** Sets the CorporateName
	 * @param strCorporateName The strCorporateName to set.
	 */
	public void setCorporateName(String strCorporateName) {
		this.strCorporateName = strCorporateName;
	}//end of setCorporateName(String strCorporateName)
	
	/** Retrieve the GroupID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}//end of getGroupID()
	
	/** Sets the GroupID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}//end of setGroupID(String strGroupID)
	
	/** Retrieve the PolicyNbr
	 * @return Returns the strPolicyNbr.
	 */
	public String getPolicyNbr() {
		return strPolicyNbr;
	}//end of getPolicyNbr()
	
	/** Sets the PolicyNbr
	 * @param strPolicyNbr The strPolicyNbr to set.
	 */
	public void setPolicyNbr(String strPolicyNbr) {
		this.strPolicyNbr = strPolicyNbr;
	}//end of setPolicyNbr(String strPolicyNbr)
	
	/** Retrieve the PrevPolicyNbr
	 * @return Returns the strPrevPolicyNbr.
	 */
	public String getPrevPolicyNbr() {
		return strPrevPolicyNbr;
	}//end of getPrevPolicyNbr()
	
	/** Sets the PrevPolicyNbr
	 * @param strPrevPolicyNbr The strPrevPolicyNbr to set.
	 */
	public void setPrevPolicyNbr(String strPrevPolicyNbr) {
		this.strPrevPolicyNbr = strPrevPolicyNbr;
	}//end of setPrevPolicyNbr(String strPrevPolicyNbr)

	public String getPolicyStatusID() {
		return policyStatusID;
	}

	public void setPolicyStatusID(String policyStatusID) {
		this.policyStatusID = policyStatusID;
	}
    
}//end of RuleSynchronizationVO
