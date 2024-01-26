package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class InsuranceApproveVO  extends BaseVO {  

	
	


	private Long lngProdPolicySeqID=null;

    private String strApprovalYN=""; 
	private String strRejectionYN=""; 
	private String strOnlineMailsYN=""; 
	private String strAdobeYN="";
	private String strTimeInHrs=""; 
	private String strTimeInMins="";
	
	private String strNotificationFlag="";
	
	//added for bajaj enhanc1
	private String strAllowedPatYN="";
	private String strAllowedClmYN="";
	private String strPatOperator="";
	private String strClmOperator="";
	private BigDecimal bdPatApproveAmountLimit=null;
	private BigDecimal bdClmApproveAmountLimit=null;
	
	private String strInsClmYN="";
	private String strInsPatYN="";
	private String strRejectionClmYN=""; //denial process
	private String strNotificationClmFlag="";//denial process
	private String strInsClmRemYN="";//denial process
	private String strAllowedClmRemYN="";//denial process
	private String strRejectionClmRemYN=""; //denial process
	private String strNotificationClmRemFlag="";//denial process
	private String strClmOperatorRem="";//denial process
	private BigDecimal bdClmApproveAmountLimitRem=null;//denial process
	private String strTimeInHrsClm=""; //denial process
	private String strTimeInMinsClm="";//denial process
	//denial process
	

	public String getTimeInHrsClm() {
		return strTimeInHrsClm;
	}
	public void setTimeInHrsClm(String strTimeInHrsClm) {
		this.strTimeInHrsClm = strTimeInHrsClm;
	}
	public String getTimeInMinsClm() {
		return strTimeInMinsClm;
	}
	public void setTimeInMinsClm(String strTimeInMinsClm) {
		this.strTimeInMinsClm = strTimeInMinsClm;
	}
	public String getClmOperatorRem() {
		return strClmOperatorRem;
	}
	public void setClmOperatorRem(String strClmOperatorRem) {
		this.strClmOperatorRem = strClmOperatorRem;
	}
	public BigDecimal getClmApproveAmountLimitRem() {
		return bdClmApproveAmountLimitRem;
	}
	public void setClmApproveAmountLimitRem(BigDecimal bdClmApproveAmountLimitRem) {
		this.bdClmApproveAmountLimitRem = bdClmApproveAmountLimitRem;
	}
	public String getNotificationClmRemFlag() {
		return strNotificationClmRemFlag;
	}
	public void setNotificationClmRemFlag(String strNotificationClmRemFlag) {
		this.strNotificationClmRemFlag = strNotificationClmRemFlag;
	}
	public String getAllowedClmRemYN() {
		return strAllowedClmRemYN;
	}
	public void setAllowedClmRemYN(String strAllowedClmRemYN) {
		this.strAllowedClmRemYN = strAllowedClmRemYN;
	}
	public String getRejectionClmRemYN() {
		return strRejectionClmRemYN;
	}
	public void setRejectionClmRemYN(String strRejectionClmRemYN) {
		this.strRejectionClmRemYN = strRejectionClmRemYN;
	}
	public String getInsClmRemYN() {
		return strInsClmRemYN;
	}
	public void setInsClmRemYN(String strInsClmRemYN) {
		this.strInsClmRemYN = strInsClmRemYN;
	}
	public String getRejectionClmYN() {
		return strRejectionClmYN;
	}
	public void setRejectionClmYN(String strRejectionClmYN) {
		this.strRejectionClmYN = strRejectionClmYN;
	}
	public String getNotificationClmFlag() {
		return strNotificationClmFlag;
	}
	public void setNotificationClmFlag(String strNotificationClmFlag) {
		this.strNotificationClmFlag = strNotificationClmFlag;
	}
	//denial process
	
	public void setTimeInMins(String timeInMins) {
		strTimeInMins = timeInMins;
	}
	public String getTimeInMins() {
		return strTimeInMins;
	}
	public String getTimeInHrs() {
		return strTimeInHrs;
	}
	public void setTimeInHrs(String timeInHrs) {
		strTimeInHrs = timeInHrs;
	}
	
	public void setAdobeYN(String adobeYN) {
		strAdobeYN = adobeYN;
	}
	public String getAdobeYN() {
		return strAdobeYN;
	}
	//1274A  
	public String getRejectionYN() {
		return strRejectionYN;
	}
	public void setRejectionYN(String rejectionYN) {
		strRejectionYN = rejectionYN;
	}
	public String getOnlineMailsYN() {
		return strOnlineMailsYN;
	}
	public void setOnlineMailsYN(String onlineMailsYN) {
		strOnlineMailsYN = onlineMailsYN;
	}
	public void setApprovalYN(String approvalYN) {
		strApprovalYN = approvalYN;
	}
	public String getApprovalYN() {
		return strApprovalYN;
	}
	public void setNotificationFlag(String notificationFlag) {
		strNotificationFlag = notificationFlag;
	}
	public String getNotificationFlag() {
		return strNotificationFlag;
	}

	/**
	 * @param operator the operator to set
	 */
	
	/**
	 * @param approveAmountLimit the approveAmountLimit to set
	 */
	
	/**
	 * @param prodPolicySeqID the prodPolicySeqID to set
	 */
	public void setProdPolicySeqID(Long prodPolicySeqID) {
		lngProdPolicySeqID = prodPolicySeqID;
	}
	/**
	 * @return the prodPolicySeqID
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}
	/**
	 * @param allowedYN the allowedYN to set
	 */
	//added for bajaj enhan1
	public void setAllowedPatYN(String allowedPatYN) {
		strAllowedPatYN = allowedPatYN;
	}
	/**
	 * @return the allowedYN
	 */
	public String getAllowedPatYN() {
		return strAllowedPatYN;
	}
	
	public void setAllowedClmYN(String allowedClmYN) {
		strAllowedClmYN = allowedClmYN;
	}
	/**
	 * @return the allowedYN
	 */
	public String getAllowedClmYN() {
		return strAllowedClmYN;
	}
	
	public void setPatOperator(String PatOperator) {
		strPatOperator = PatOperator;
	}
	/**
	 * @return the operator
	 */
	public String getPatOperator() {
		return strPatOperator;
	}
	
	public void setClmOperator(String ClmOperator) {
		strClmOperator = ClmOperator;
	}
	/**
	 * @return the operator
	 */
	public String getClmOperator() {
		return strClmOperator;
	}
	
	public void setPatApproveAmountLimit(BigDecimal PatapproveAmountLimit) {
		bdPatApproveAmountLimit = PatapproveAmountLimit;
	}
	/**
	 * @return the approveAmountLimit
	 */
	public BigDecimal getPatApproveAmountLimit() {
		return bdPatApproveAmountLimit;
	}
	public void setClmApproveAmountLimit(BigDecimal ClmapproveAmountLimit) {
		bdClmApproveAmountLimit = ClmapproveAmountLimit;
	}
	/**
	 * @return the approveAmountLimit
	 */
	public BigDecimal getClmApproveAmountLimit() {
		return bdClmApproveAmountLimit;
	}
	
	public String getInsClmYN() {
		return strInsClmYN;
	}
	public void setInsClmYN(String strInsClmYN) {
		this.strInsClmYN = strInsClmYN;
	}
	public String getInsPatYN() {
		return strInsPatYN;
	}
	public void setInsPatYN(String strInsPatYN) {
		this.strInsPatYN = strInsPatYN;
	}
	// end added for bajaj enhan1
}
	
	
	


