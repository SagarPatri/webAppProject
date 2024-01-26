/**
 * @ (#) EndorsementVO.java Feb 6, 2006
 * Project       : TTK HealthCare Services
 * File          : EndorsementVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Feb 6, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.enrollment;

import java.math.BigDecimal;
import java.util.Date;

public class EndorsementVO extends PolicyDetailVO {
	private Long lngEndorsementSeqID = null;
	private Long lngEnrollBatchSeqID = null;
	private String strCustEndorsementNbr = "";
	private Date dtEffectiveDate = null;
	private Integer intAddMemberCnt = null;
	private Integer intAddMemberCntStatus = null;
	private BigDecimal bdAddPremium = null;
	private BigDecimal bdAddPremiumStatus = null;
	private BigDecimal bdAddPremiumInsured = null;
	private BigDecimal bdAddPremiumInsuredStatus = null;
	private Integer intDeletedMemberCnt = null;
	private Integer intDeletedMemberCntStatus = null;
	private BigDecimal bdDeductPremium = null;
	private BigDecimal bdDeductPremiumStatus = null;
	private BigDecimal bdDeductSumInsured = null;
	private BigDecimal bdDeductSumInsuredStatus = null;
	private String strEndorsementCompletedYN = "";
	private Integer intUpdateMemberCnt = null;
	private Integer intUpdateMemberCntStatus = null;
	private BigDecimal bdUpdatePremium = null;
	private BigDecimal bdUpdatePremiumStatus = null;
	private BigDecimal bdUpdatePremiumInsured = null;
	private BigDecimal bdUpdatePremiumInsuredStatus = null;
	private BigDecimal bdUpdateDeductPremium = null;
	private BigDecimal bdUpdateDeductPremiumStatus = null;
	private BigDecimal bdUpdateDeductSumInsured = null;
	private BigDecimal bdUpdateDeductSumInsuredStatus = null;
	private String strPolicyType = "";
	private Integer intAddEmployeeCnt = null;
	private Integer intAddEmployeeCntStatus = null;
	private Integer intDeletedEmployeeCnt = null;
	private Integer intDeletedEmployeeCntStatus = null;
	private String strPolicyCancelYN = "";
	private String strModPolicyYN = "";
	private BigDecimal bdAddDomiciliary = null;
	private BigDecimal bdAddDomiciliaryStatus = null;
	private BigDecimal bdDeductDomiciliary = null;
	private BigDecimal bdDeductDomiciliaryStatus = null;
	private BigDecimal bdCancelDomiciliary = null;
	private BigDecimal bdCancelDomiciliaryStatus = null;
	private String uploadMemberDataToWebPortalYN=null;
	private String strPolicySuspensionYN = "";
	
	
	
	/** Retrieve the AddDomiciliaryStatus
	 * @return Returns the bdAddDomiciliaryStatus.
	 */
	public BigDecimal getAddDomiciliaryStatus() {
		return bdAddDomiciliaryStatus;
	}//end of getAddDomiciliaryStatus()

	/** Sets the AddDomiciliaryStatus
	 * @param bdAddDomiciliaryStatus The bdAddDomiciliaryStatus to set.
	 */
	public void setAddDomiciliaryStatus(BigDecimal bdAddDomiciliaryStatus) {
		this.bdAddDomiciliaryStatus = bdAddDomiciliaryStatus;
	}//end of setAddDomiciliaryStatus(BigDecimal bdAddDomiciliaryStatus)

	/** Retrieve the CancelDomiciliaryStatus
	 * @return Returns the bdCancelDomiciliaryStatus.
	 */
	public BigDecimal getCancelDomiciliaryStatus() {
		return bdCancelDomiciliaryStatus;
	}//end of getCancelDomiciliaryStatus()

	/** Sets the CancelDomiciliaryStatus
	 * @param bdCancelDomiciliaryStatus The bdCancelDomiciliaryStatus to set.
	 */
	public void setCancelDomiciliaryStatus(BigDecimal bdCancelDomiciliaryStatus) {
		this.bdCancelDomiciliaryStatus = bdCancelDomiciliaryStatus;
	}//end of setCancelDomiciliaryStatus(BigDecimal bdCancelDomiciliaryStatus)

	/** Retrieve the DeductDomiciliaryStatus
	 * @return Returns the bdDeductDomiciliaryStatus.
	 */
	public BigDecimal getDeductDomiciliaryStatus() {
		return bdDeductDomiciliaryStatus;
	}//end of getDeductDomiciliaryStatus()

	/** Sets the DeductDomiciliaryStatus
	 * @param bdDeductDomiciliaryStatus The bdDeductDomiciliaryStatus to set.
	 */
	public void setDeductDomiciliaryStatus(BigDecimal bdDeductDomiciliaryStatus) {
		this.bdDeductDomiciliaryStatus = bdDeductDomiciliaryStatus;
	}//end of setDeductDomiciliaryStatus(BigDecimal bdDeductDomiciliaryStatus)

	/** Retrieve the AddDomiciliary
	 * @return Returns the bdAddDomiciliary.
	 */
	public BigDecimal getAddDomiciliary() {
		return bdAddDomiciliary;
	}//end of getAddDomiciliary()

	/** Sets the AddDomiciliary
	 * @param bdAddDomiciliary The bdAddDomiciliary to set.
	 */
	public void setAddDomiciliary(BigDecimal bdAddDomiciliary) {
		this.bdAddDomiciliary = bdAddDomiciliary;
	}//end of setAddDomiciliary(BigDecimal bdAddDomiciliary)

	/** Retrieve the CancelDomiciliary
	 * @return Returns the bdCancelDomiciliary.
	 */
	public BigDecimal getCancelDomiciliary() {
		return bdCancelDomiciliary;
	}//end of getCancelDomiciliary()

	/** Sets the CancelDomiciliary
	 * @param bdCancelDomiciliary The bdCancelDomiciliary to set.
	 */
	public void setCancelDomiciliary(BigDecimal bdCancelDomiciliary) {
		this.bdCancelDomiciliary = bdCancelDomiciliary;
	}//end of setCancelDomiciliary(BigDecimal bdCancelDomiciliary)

	/** Retrieve the DeductDomiciliary
	 * @return Returns the bdDeductDomiciliary.
	 */
	public BigDecimal getDeductDomiciliary() {
		return bdDeductDomiciliary;
	}//end of getDeductDomiciliary()

	/** Sets the DeductDomiciliary
	 * @param bdDeductDomiciliary The bdDeductDomiciliary to set.
	 */
	public void setDeductDomiciliary(BigDecimal bdDeductDomiciliary) {
		this.bdDeductDomiciliary = bdDeductDomiciliary;
	}//end of setDeductDomiciliary(BigDecimal bdDeductDomiciliary)

	/** Retrieve the Mod Policy YN
	 * @return Returns the strModPolicyYN.
	 */
	public String getModPolicyYN() {
		return strModPolicyYN;
	}//end of getModPolicyYN()

	/** Sets the Mod Policy YN
	 * @param strModPolicyYN The strModPolicyYN to set.
	 */
	public void setModPolicyYN(String strModPolicyYN) {
		this.strModPolicyYN = strModPolicyYN;
	}//end of setModPolicyYN(String strModPolicyYN)

	/** Retrieve the Add Employee Count
	 * @return Returns the intAddEmployeeCnt.
	 */
	public Integer getAddEmployeeCnt() {
		return intAddEmployeeCnt;
	}//end of getAddEmployeeCnt()

	/** Sets the Add Employee Count
	 * @param intAddEmployeeCnt The intAddEmployeeCnt to set.
	 */
	public void setAddEmployeeCnt(Integer intAddEmployeeCnt) {
		this.intAddEmployeeCnt = intAddEmployeeCnt;
	}//end of setAddEmployeeCnt(Integer intAddEmployeeCnt)

	/** Retrieve the Add Employee Count Status
	 * @return Returns the intAddEmployeeCntStatus.
	 */
	public Integer getAddEmployeeCntStatus() {
		return intAddEmployeeCntStatus;
	}//end of getAddEmployeeCntStatus()

	/** Sets the Add Employee Count Status
	 * @param intAddEmployeeCntStatus The intAddEmployeeCntStatus to set.
	 */
	public void setAddEmployeeCntStatus(Integer intAddEmployeeCntStatus) {
		this.intAddEmployeeCntStatus = intAddEmployeeCntStatus;
	}//end of setAddEmployeeCntStatus(Integer intAddEmployeeCntStatus)

	/** Retrieve the Deleted Employee Count
	 * @return Returns the intDeletedEmployeeCnt.
	 */
	public Integer getDeletedEmployeeCnt() {
		return intDeletedEmployeeCnt;
	}//end of getDeletedEmployeeCnt()

	/** Sets the Deleted Employee Count
	 * @param intDeletedEmployeeCnt The intDeletedEmployeeCnt to set.
	 */
	public void setDeletedEmployeeCnt(Integer intDeletedEmployeeCnt) {
		this.intDeletedEmployeeCnt = intDeletedEmployeeCnt;
	}//end of setDeletedEmployeeCnt(Integer intDeletedEmployeeCnt)

	/** Retrieve the Deleted Employee Count Status
	 * @return Returns the intDeletedEmployeeCntStatus.
	 */
	public Integer getDeletedEmployeeCntStatus() {
		return intDeletedEmployeeCntStatus;
	}//end of getDeletedEmployeeCntStatus()

	/** Sets the Deleted Employee Count Status
	 * @param intDeletedEmployeeCntStatus The intDeletedEmployeeCntStatus to set.
	 */
	public void setDeletedEmployeeCntStatus(Integer intDeletedEmployeeCntStatus) {
		this.intDeletedEmployeeCntStatus = intDeletedEmployeeCntStatus;
	}//end of setDeletedEmployeeCntStatus(Integer intDeletedEmployeeCntStatus)

	/** Retrieve the Policy Cancel YN 
	 * @return Returns the strPolicyCancelYN.
	 */
	public String getPolicyCancelYN() {
		return strPolicyCancelYN;
	}//end of getPolicyCancelYN()

	/** Sets the Policy Cancel YN
	 * @param strPolicyCancelYN The strPolicyCancelYN to set.
	 */
	public void setPolicyCancelYN(String strPolicyCancelYN) {
		this.strPolicyCancelYN = strPolicyCancelYN;
	}//end of setPolicyCancelYN(String strPolicyCancelYN)

	/** This method returns the Added Premium
	 * @return Returns the bdAddPremium.
	 */
	public BigDecimal getAddPremium() {
		return bdAddPremium;
	}// End of getAddPremium()
	
	/** This method sets the Added Premium
	 * @param bdAddPremium The bdAddPremium to set.
	 */
	public void setAddPremium(BigDecimal bdAddPremium) {
		this.bdAddPremium = bdAddPremium;
	}// End of setAddPremium(BigDecimal bdAddPremium)
	
	/** This method returns the Added Premium Insured
	 * @return Returns the bdAddPremiumInsured.
	 */
	public BigDecimal getAddPremiumInsured() {
		return bdAddPremiumInsured;
	}// End of getAddPremiumInsured()
	
	/** This method sets the Added Premium Insured
	 * @param bdAddPremiumInsured The bdAddPremiumInsured to set.
	 */
	public void setAddPremiumInsured(BigDecimal bdAddPremiumInsured) {
		this.bdAddPremiumInsured = bdAddPremiumInsured;
	}// Edn of setAddPremiumInsured(BigDecimal bdAddPremiumInsured)
	
	/** This method returns the Added Premium Insured Status
	 * @return Returns the bdAddPremiumInsuredStatus.
	 */
	public BigDecimal getAddPremiumInsuredStatus() {
		return bdAddPremiumInsuredStatus;
	}// End of getAddPremiumInsuredStatus()
	
	/** This method sets the Added Premium Insured Status
	 * @param bdAddPremiumInsuredStatus The bdAddPremiumInsuredStatus to set.
	 */
	public void setAddPremiumInsuredStatus(BigDecimal bdAddPremiumInsuredStatus) {
		this.bdAddPremiumInsuredStatus = bdAddPremiumInsuredStatus;
	}//End of setAddPremiumInsuredStatus(BigDecimal bdAddPremiumInsuredStatus)
	
	/** This method returns the Added Premium Status
	 * @return Returns the bdAddPremiumStatus.
	 */
	public BigDecimal getAddPremiumStatus() {
		return bdAddPremiumStatus;
	}//End of getAddPremiumStatus()
	
	/** This method sets the Added premium status
	 * @param bdAddPremiumStatus The bdAddPremiumStatus to set.
	 */
	public void setAddPremiumStatus(BigDecimal bdAddPremiumStatus) {
		this.bdAddPremiumStatus = bdAddPremiumStatus;
	}// End of setAddPremiumStatus(BigDecimal bdAddPremiumStatus)
	
	/** This method returns the Deducted Premium
	 * @return Returns the bdDeductPremium.
	 */
	public BigDecimal getDeductPremium() {
		return bdDeductPremium;
	}// End of getDeductPremium()
	
	/** This method sets the Deducted premium
	 * @param bdDeductPremium The bdDeductPremium to set.
	 */
	public void setDeductPremium(BigDecimal bdDeductPremium) {
		this.bdDeductPremium = bdDeductPremium;
	}// End of setDeductPremium(BigDecimal bdDeductPremium)
	
	/** This method returns the Deducted Premium Status
	 * @return Returns the bdDeductPremiumStatus.
	 */
	public BigDecimal getDeductPremiumStatus() {
		return bdDeductPremiumStatus;
	}// End of getDeductPremiumStatus()
	
	/** This method sets the Deducted Premium Status 
	 * @param bdDeductPremiumStatus The bdDeductPremiumStatus to set.
	 */
	public void setDeductPremiumStatus(BigDecimal bdDeductPremiumStatus) {
		this.bdDeductPremiumStatus = bdDeductPremiumStatus;
	}// End of setDeductPremiumStatus(BigDecimal bdDeductPremiumStatus)
	
	/** This method returns the Deducted Sum Insured
	 * @return Returns the bdDeductSumInsured.
	 */
	public BigDecimal getDeductSumInsured() {
		return bdDeductSumInsured;
	}// End of getDeductSumInsured()
	
	/** This method sets the Deducted sum insured
	 * @param bdDeductSumInsured The bdDeductSumInsured to set.
	 */
	public void setDeductSumInsured(BigDecimal bdDeductSumInsured) {
		this.bdDeductSumInsured = bdDeductSumInsured;
	}// End of setDeductSumInsured(BigDecimal bdDeductSumInsured)
	
	/** This method returns the Effective Date
	 * @return Returns the dtEffectiveDate.
	 */
	public Date getEffectiveDate() {
		return dtEffectiveDate;
	}//End of getEffectiveDate()
	
	/** This method sets the Effective Date
	 * @param dtEffectiveDate The dtEffectiveDate to set.
	 */
	public void setEffectiveDate(Date dtEffectiveDate) {
		this.dtEffectiveDate = dtEffectiveDate;
	}//End of setEffectiveDate(Date dtEffectiveDate)
	
	/** This method returns the Added member count
	 * @return Returns the intAddMemberCnt.
	 */
	public Integer getAddMemberCnt() {
		return intAddMemberCnt;
	}// End of getAddMemberCnt()
	
	/** This method sets the Added member count 
	 * @param intAddMemberCnt The intAddMemberCnt to set.
	 */
	public void setAddMemberCnt(Integer intAddMemberCnt) {
		this.intAddMemberCnt = intAddMemberCnt;
	}// End of setAddMemberCnt(Integer intAddMemberCnt)
	
	/** This method returns the Added member count status
	 * @return Returns the intAddMemberCntStatus.
	 */
	public Integer getAddMemberCntStatus() {
		return intAddMemberCntStatus;
	}// End of getAddMemberCntStatus()
	
	/** This method sets the Added Member Count status
	 * @param intAddmemberCntStatus The intAddmemberCntStatus to set.
	 */
	public void setAddMemberCntStatus(Integer intAddMemberCntStatus) {
		this.intAddMemberCntStatus = intAddMemberCntStatus;
	}//End of setAddMemberCntStatus(Integer intAddMemberCntStatus)
	
	/** This method returns the Deleted member count
	 * @return Returns the intDeletedMemberCnt.
	 */
	public Integer getDeletedMemberCnt() {
		return intDeletedMemberCnt;
	}// End of getDeletedMemberCnt()
	
	/** This method sets the Deleted member Count
	 * @param intDeletedMemberCnt The intDeletedMemberCnt to set.
	 */
	public void setDeletedMemberCnt(Integer intDeletedMemberCnt) {
		this.intDeletedMemberCnt = intDeletedMemberCnt;
	}// End of setDeletedMemberCnt(Integer intDeletedMemberCnt)
	
	/** This method returns the deleted member count status
	 * @return Returns the intDeletedMemberCntStatus.
	 */
	public Integer getDeletedMemberCntStatus() {
		return intDeletedMemberCntStatus;
	}// End of getDeletedMemberCntStatus()
	
	/** This method sets the Delete Member Count Status
	 * @param intDeletedMemberCntStatus The intDeletedMemberCntStatus to set.
	 */
	public void setDeletedMemberCntStatus(Integer intDeletedMemberCntStatus) {
		this.intDeletedMemberCntStatus = intDeletedMemberCntStatus;
	}// End of setDeletedMemberCntStatus(Integer intDeletedMemberCntStatus)
	
	/** This method returns the Endorsement Sequence ID
	 * @return Returns the lngEndorsementSeqID.
	 */
	public Long getEndorsementSeqID() {
		return lngEndorsementSeqID;
	}// End of getEndorsementSeqID()
	
	/** This method sets the Endorsement Sequence ID 
	 * @param lngEndorsementSeqID The lngEndorsementSeqID to set.
	 */
	public void setEndorsementSeqID(Long lngEndorsementSeqID) {
		this.lngEndorsementSeqID = lngEndorsementSeqID;
	}//End of setEndorsementSeqID(Long lngEndorsementSeqID)
	
	/** This method returns the Enrollment Sequence ID 
	 * @return Returns the lngEnrollBatchSeqID.
	 */
	public Long getEnrollBatchSeqID() {
		return lngEnrollBatchSeqID;
	}// End of getEnrollBatchSeqID()
	
	/** This method sets the Enrollment Batch Sequence ID
	 * @param lngEnrollBatchSeqID The lngEnrollBatchSeqID to set.
	 */
	public void setEnrollBatchSeqID(Long lngEnrollBatchSeqID) {
		this.lngEnrollBatchSeqID = lngEnrollBatchSeqID;
	}// End of setEnrollBatchSeqID(Long lngEnrollBatchSeqID)
	
	/** This method returns the Customer Endorsement Number
	 * @return Returns the strCustEndorsementNbr.
	 */
	public String getCustEndorsementNbr() {
		return strCustEndorsementNbr;
	}// End of getCustEndorsementNbr()
	
	/** This method sets the Customer Endorsement Nmber
	 * @param strCustEndorsementNbr The strCustEndorsementNbr to set.
	 */
	public void setCustEndorsementNbr(String strCustEndorsementNbr) {
		this.strCustEndorsementNbr = strCustEndorsementNbr;
	}// End of setCustEndorsementNbr(String strCustEndorsementNbr)

	/** This method returns the Status of the Deducted Sum Insured
	 * @return Returns the bdDeductSumInsuredStatus.
	 */
	public BigDecimal getDeductSumInsuredStatus() {
		return bdDeductSumInsuredStatus;
	}//Edn of getDeductSumInsuredStatus()

	/** This method sets the Status of the Deducted Sum Insured
	 * @param bdDeductSumInsuredStatus The bdDeductSumInsuredStatus to set.
	 */
	public void setDeductSumInsuredStatus(BigDecimal bdDeductSumInsuredStatus) {
		this.bdDeductSumInsuredStatus = bdDeductSumInsuredStatus;
	}//End of setDeductSumInsuredStatus(BigDecimal bdDeductSumInsuredStatus)

	/** This method returns the Endorsement Complete Yes or No
	 * @return Returns the strEndorsementCompletedYn.
	 */
	public String getEndorsementCompletedYN() {
		return strEndorsementCompletedYN;
	}// End of getEndorsementCompletedYN()

	/** This method sets the Endorsement Complete Yes or No
	 * @param strEndorsementCompletedYn The strEndorsementCompletedYN to set.
	 */
	public void setEndorsementCompletedYN(String strEndorsementCompletedYN) {
		this.strEndorsementCompletedYN = strEndorsementCompletedYN;
	}// End of setEndorsementCompletedYN(String strEndorsementCompletedYN)

	/** This method returns the to update the member count
	 * @return Returns the intUpdateMemberCnt.
	 */
	public Integer getUpdateMemberCnt() {
		return intUpdateMemberCnt;
	}//End of getUpdateMemberCnt()

	/** This method sets the to update member count
	 * @param intUpdateMemberCnt The intUpdateMemberCnt to set.
	 */
	public void setUpdateMemberCnt(Integer intUpdateMemberCnt) {
		this.intUpdateMemberCnt = intUpdateMemberCnt;
	}//End of setUpdateMemberCnt(Integer intUpdateMemberCnt)

	/** This method returns the updated member count
	 * @return Returns the intUpdateMemberCntStatus.
	 */
	public Integer getUpdateMemberCntStatus() {
		return intUpdateMemberCntStatus;
	}//End of getUpdateMemberCntStatus()

	/** This method sets the updated member count
	 * @param intUpdateMemberCntStatus The intUpdateMemberCntStatus to set.
	 */
	public void setUpdateMemberCntStatus(Integer intUpdateMemberCntStatus) {
		this.intUpdateMemberCntStatus = intUpdateMemberCntStatus;
	}//End of setUpdateMemberCntStatus(Integer intUpdateMemberCntStatus)

	/** This method returns the to update the premium
	 * @return Returns the bdUpdatePremium.
	 */
	public BigDecimal getUpdatePremium() {
		return bdUpdatePremium;
	}//end of getUpdatePremium()

	/** This method sets the to update the premium
	 * @param bdUpdatePremium The bdUpdatePremium to set.
	 */
	public void setUpdatePremium(BigDecimal bdUpdatePremium) {
		this.bdUpdatePremium = bdUpdatePremium;
	}//end of setUpdatePremium(BigDecimal bdUpdatePremium)

	/** This method returns the to update the premium status
	 * @return Returns the bdUpdatePremiumStatus.
	 */
	public BigDecimal getUpdatePremiumStatus() {
		return bdUpdatePremiumStatus;
	}//End of  getUpdatePremiumStatus()

	/** This method sets the to update the premium status
	 * @param bdUpdatePremiumStatus The bdUpdatePremiumStatus to set.
	 */
	public void setUpdatePremiumStatus(BigDecimal bdUpdatePremiumStatus) {
		this.bdUpdatePremiumStatus = bdUpdatePremiumStatus;
	}//End of setUpdatePremiumStatus(BigDecimal bdUpdatePremiumStatus)

	/** This method returns the to update premium insured
	 * @return Returns the bdUpdatePremiumInsured.
	 */
	public BigDecimal getUpdatePremiumInsured() {
		return bdUpdatePremiumInsured;
	}//End of getUpdatePremiumInsured()

	/** This method sets the to update premium insured
	 * @param bdUpdatePremiumInsured The bdUpdatePremiumInsured to set.
	 */
	public void setUpdatePremiumInsured(BigDecimal bdUpdatePremiumInsured) {
		this.bdUpdatePremiumInsured = bdUpdatePremiumInsured;
	}//end of setUpdatePremiumInsured(BigDecimal bdUpdatePremiumInsured)

	/** This method returns the updated premium insured status
	 * @return Returns the bdUpdatePremiumInsuredStatus.
	 */
	public BigDecimal getUpdatePremiumInsuredStatus() {
		return bdUpdatePremiumInsuredStatus;
	}//End of getUpdatePremiumInsuredStatus()

	/** This method sets the updated premium insured status
	 * @param bdUpdatePremiumInsuredStatus The bdUpdatePremiumInsuredStatus to set.
	 */
	public void setUpdatePremiumInsuredStatus(BigDecimal bdUpdatePremiumInsuredStatus) {
		this.bdUpdatePremiumInsuredStatus = bdUpdatePremiumInsuredStatus;
	}//End of setUpdatePremiumInsuredStatus(BigDecimal bdUpdatePremiumInsuredStatus)

	/** This method returns the updated deducted premium
	 * @return Returns the bdUpdateDeductPremium.
	 */
	public BigDecimal getUpdateDeductPremium() {
		return bdUpdateDeductPremium;
	}//End of getUpdateDeductPremium() 

	/** This method sets the updated deducted premium
	 * @param bdUpdateDeductPremium The bdUpdateDeductPremium to set.
	 */
	public void setUpdateDeductPremium(BigDecimal bdUpdateDeductPremium) {
		this.bdUpdateDeductPremium = bdUpdateDeductPremium;
	}//end of setUpdateDeductPremium(BigDecimal bdUpdateDeductPremium)

	/** This method returns the updated deducted premium
	 * @return Returns the bdUpdateDeductPremiumStatus.
	 */
	public BigDecimal getUpdateDeductPremiumStatus() {
		return bdUpdateDeductPremiumStatus;
	}//End of getUpdateDeductPremiumStatus() 

	/** This method sets the to update deducted sum insurred
	 * @param bdUpdateDeductPremiumStatus The bdUpdateDeductPremiumStatus to set.
	 */
	public void setUpdateDeductPremiumStatus(BigDecimal bdUpdateDeductPremiumStatus) {
		this.bdUpdateDeductPremiumStatus = bdUpdateDeductPremiumStatus;
	}//Edn of setUpdateDeductPremiumStatus(BigDecimal bdUpdateDeductPremiumStatus)

	/** This method returns the to update deducted sum insurred
	 * @return Returns the bdUpdateDeductSumInsured.
	 */
	public BigDecimal getUpdateDeductSumInsured() {
		return bdUpdateDeductSumInsured;
	}//end of getUpdateDeductSumInsured()

	/** This method sets the to update deducted sum insurred
	 * @param bdUpdateDeductSumInsured The bdUpdateDeductSumInsured to set.
	 */
	public void setUpdateDeductSumInsured(BigDecimal bdUpdateDeductSumInsured) {
		this.bdUpdateDeductSumInsured = bdUpdateDeductSumInsured;
	}//End of setUpdateDeductSumInsured(BigDecimal bdUpdateDeductSumInsured)

	/** This method returns the updated deducted sum insurred
	 * @return Returns the bdUpdateDeductSumInsuredStatus.
	 */
	public BigDecimal getUpdateDeductSumInsuredStatus() {
		return bdUpdateDeductSumInsuredStatus;
	}//End of getUpdateDeductSumInsuredStatus()

	/** This method sets the updated deducted sum insurred
	 * @param bdUpdateDeductSumInsuredStatus The bdUpdateDeductSumInsuredStatus to set.
	 */
	public void setUpdateDeductSumInsuredStatus(BigDecimal bdUpdateDeductSumInsuredStatus) {
		this.bdUpdateDeductSumInsuredStatus = bdUpdateDeductSumInsuredStatus;
	}//End of setUpdateDeductSumInsuredStatus(BigDecimal bdUpdateDeductSumInsuredStatus)

	/** This method returns the Policy Type
	 * @return Returns the strPolicyType.
	 */
	public String getPolicyType() {
		return strPolicyType;
	}//End of getPolicyType()

	/** This method sets the Policy Type
	 * @param strPolicyType The strPolicyType to set.
	 */
	public void setPolicyType(String strPolicyType) {
		this.strPolicyType = strPolicyType;
	}// End of setPolicyType(String strPolicyType)

	public String getUploadMemberDataToWebPortalYN() {
		return uploadMemberDataToWebPortalYN;
	}

	public void setUploadMemberDataToWebPortalYN(
			String uploadMemberDataToWebPortalYN) {
		this.uploadMemberDataToWebPortalYN = uploadMemberDataToWebPortalYN;
	}

	public String getPolicySuspensionYN() {
		return strPolicySuspensionYN;
	}

	public void setPolicySuspensionYN(String strPolicySuspensionYN) {
		this.strPolicySuspensionYN = strPolicySuspensionYN;
	}

}//End of EndorsementVO
