
/**
 * @ (#)ShortfallDaysConfigVO.java Dec 05, 2012
 * Project       : TTK HealthCare Services
 * File          : ShortfallDaysConfigVO.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Dec 05, 2012
 *
 * @author       :  Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;


public class ShortfallDaysConfigVO extends BaseVO
{
	
	private String strRemainderReqDays="";
	private String strClosureNoticeDays="" ;
	private String strApprClosureNoticeDays="";
	private String strClaimClosureDays="";	
	private String strClaimSubmissionDays="";
	private String strRegretLetterDays="";
	private Long lngProdPolicySeqID=null;
	private Long lngShortfallConfigSeqID = null;
	private String strIntimationReqDays="";//added by satya for 1179 Shortfall CR
	
	private String strPostHospitalizationDays="";//shortfall phase1
	
	
	//shortfall phase1
	public void setPostHospitalizationDays(String strPostHospitalizationDays) {
		this.strPostHospitalizationDays = strPostHospitalizationDays;
	}
	
	public String getPostHospitalizationDays() {
		return strPostHospitalizationDays;
	}
	
	//shortfall phase1
	/**
	 * @return the remainderReqDays
	 */
	public String getRemainderReqDays() {
		return this.strRemainderReqDays;
	}
	/**
	 * @return the closureNoticeDays
	 */
	public String getClosureNoticeDays() {
		return this.strClosureNoticeDays;
	}
	/**
	 * @return the apprClosureNoticeDays
	 */
	public String getApprClosureNoticeDays() {
		return this.strApprClosureNoticeDays;
	}
	/**
	 * @return the claimClosureDays
	 */
	public String getClaimClosureDays() {
		return this.strClaimClosureDays;
	}
	/**
	 * @return the claimSubmissionDays
	 */
	public String getClaimSubmissionDays() {
		return this.strClaimSubmissionDays;
	}
	/**
	 * @return the regretLetterDays
	 */
	public String getRegretLetterDays() {
		return this.strRegretLetterDays;
	}
	/**
	 * @return the prodPolicySeqID
	 */
	public Long getProdPolicySeqID() {
		return this.lngProdPolicySeqID;
	}
	/**
	 * @return the shortfallConfigSeqID
	 */
	public Long getShortfallConfigSeqID() {
		return this.lngShortfallConfigSeqID;
	}
	/**
	 * @return the intimationReqDays
	 */
	public String getIntimationReqDays() {
		return this.strIntimationReqDays;
	}
	/**
	 * @param remainderReqDays the remainderReqDays to set
	 */
	public void setRemainderReqDays(String remainderReqDays) {
		this.strRemainderReqDays = remainderReqDays;
	}
	/**
	 * @param closureNoticeDays the closureNoticeDays to set
	 */
	public void setClosureNoticeDays(String closureNoticeDays) {
		this.strClosureNoticeDays = closureNoticeDays;
	}
	/**
	 * @param apprClosureNoticeDays the apprClosureNoticeDays to set
	 */
	public void setApprClosureNoticeDays(String apprClosureNoticeDays) {
		this.strApprClosureNoticeDays = apprClosureNoticeDays;
	}
	/**
	 * @param claimClosureDays the claimClosureDays to set
	 */
	public void setClaimClosureDays(String claimClosureDays) {
		this.strClaimClosureDays = claimClosureDays;
	}
	/**
	 * @param claimSubmissionDays the claimSubmissionDays to set
	 */
	public void setClaimSubmissionDays(String claimSubmissionDays) {
		this.strClaimSubmissionDays = claimSubmissionDays;
	}
	/**
	 * @param regretLetterDays the regretLetterDays to set
	 */
	public void setRegretLetterDays(String regretLetterDays) {
		this.strRegretLetterDays = regretLetterDays;
	}
	/**
	 * @param prodPolicySeqID the prodPolicySeqID to set
	 */
	public void setProdPolicySeqID(Long prodPolicySeqID) {
		this.lngProdPolicySeqID = prodPolicySeqID;
	}
	/**
	 * @param shortfallConfigSeqID the shortfallConfigSeqID to set
	 */
	public void setShortfallConfigSeqID(Long shortfallConfigSeqID) {
		this.lngShortfallConfigSeqID = shortfallConfigSeqID;
	}
	/**
	 * @param intimationReqDays the intimationReqDays to set
	 */
	public void setIntimationReqDays(String intimationReqDays) {
		this.strIntimationReqDays = intimationReqDays;
	}
  }//end of ShortfallDaysConfigVO
