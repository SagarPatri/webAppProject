/**
 * @ (#) ClaimsVo.java 27th August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 27th August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 27th August 2015
 * Reason        :
 *
 */
package com.ttk.dto.onlineforms.insuranceLogin;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class ClaimsVO extends BaseVO{



	private static final long serialVersionUID = 1L;
	
	
    private Date dtDateFrom 				= null;
    private Date dtDateTo 				= null;
    private BigDecimal bdClaimedAmt 		= null;
    private BigDecimal bdSettledAmt 		= null;
    private String strClaimNbr 			=	"";
    private String strStatus 				=	"";

	public String getDateFrom() {
		return TTKCommon.getFormattedDate(dtDateFrom);
	}

	public void setDateFrom(Date dtDateFrom) {
		this.dtDateFrom = dtDateFrom;
	}

	public String getDateTo() {
		return TTKCommon.getFormattedDate(dtDateTo);
	}

	public void setDateTo(Date dtDateTo) {
		this.dtDateTo = dtDateTo;
	}

	public BigDecimal getClaimedAmt() {
		return bdClaimedAmt;
	}

	public void setClaimedAmt(BigDecimal bdClaimedAmt) {
		this.bdClaimedAmt = bdClaimedAmt;
	}

	public BigDecimal getSettledAmt() {
		return bdSettledAmt;
	}

	public void setSettledAmt(BigDecimal bdSettledAmt) {
		this.bdSettledAmt = bdSettledAmt;
	}

	public String getClaimNbr() {
		return strClaimNbr;
	}

	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}

	public String getStatus() {
		return strStatus;
	}

	public void setStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	//global view getters
	private int noOfClaimsReported;
	private BigDecimal reportedAmount;
	private int noOfClaimsPaid;
	private BigDecimal paidAmount;
	private int noOfClaimsOutstanding;
	private BigDecimal outstandingAmount;
	private int noOfClaimsApproved;
	private BigDecimal approvedAmount;
	private int noOfClaimsDenied;
	private BigDecimal deniedAmount;
	private int noOfClaimsShortfall;
	private BigDecimal shortfallAmount;
	private int noOfClmCurMonRptd;
	private BigDecimal curMonReportedAmount;
	private int noOfClmCurMonPaid;
	private BigDecimal curMonPaidAmount;
	private int noOfClmCurMonOutstanding;
	private BigDecimal curMonOutstandingAmount;
	private int noOfClmCurMonApproved;
	private BigDecimal curMonApprovedAmount;
	private int noOfClmCurMonDenied;
	private BigDecimal curMonDeniedAmount;
	private int noOfClmCurMonShortfall;
	private BigDecimal curMonShortfallAmount;
	public int getNoOfClaimsReported() {
		return noOfClaimsReported;
	}
	public void setNoOfClaimsReported(int noOfClaimsReported) {
		this.noOfClaimsReported = noOfClaimsReported;
	}
	public BigDecimal getReportedAmount() {
		return reportedAmount;
	}
	public void setReportedAmount(BigDecimal reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	public int getNoOfClaimsPaid() {
		return noOfClaimsPaid;
	}
	public void setNoOfClaimsPaid(int noOfClaimsPaid) {
		this.noOfClaimsPaid = noOfClaimsPaid;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public int getNoOfClaimsOutstanding() {
		return noOfClaimsOutstanding;
	}
	public void setNoOfClaimsOutstanding(int noOfClaimsOutstanding) {
		this.noOfClaimsOutstanding = noOfClaimsOutstanding;
	}
	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public int getNoOfClaimsApproved() {
		return noOfClaimsApproved;
	}
	public void setNoOfClaimsApproved(int noOfClaimsApproved) {
		this.noOfClaimsApproved = noOfClaimsApproved;
	}
	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}
	public int getNoOfClaimsDenied() {
		return noOfClaimsDenied;
	}
	public void setNoOfClaimsDenied(int noOfClaimsDenied) {
		this.noOfClaimsDenied = noOfClaimsDenied;
	}
	public BigDecimal getDeniedAmount() {
		return deniedAmount;
	}
	public void setDeniedAmount(BigDecimal deniedAmount) {
		this.deniedAmount = deniedAmount;
	}
	public int getNoOfClaimsShortfall() {
		return noOfClaimsShortfall;
	}
	public void setNoOfClaimsShortfall(int noOfClaimsShortfall) {
		this.noOfClaimsShortfall = noOfClaimsShortfall;
	}
	public BigDecimal getShortfallAmount() {
		return shortfallAmount;
	}
	public void setShortfallAmount(BigDecimal shortfallAmount) {
		this.shortfallAmount = shortfallAmount;
	}
	public int getNoOfClmCurMonRptd() {
		return noOfClmCurMonRptd;
	}
	public void setNoOfClmCurMonRptd(int noOfClmCurMonRptd) {
		this.noOfClmCurMonRptd = noOfClmCurMonRptd;
	}
	public BigDecimal getCurMonReportedAmount() {
		return curMonReportedAmount;
	}
	public void setCurMonReportedAmount(BigDecimal curMonReportedAmount) {
		this.curMonReportedAmount = curMonReportedAmount;
	}
	public int getNoOfClmCurMonPaid() {
		return noOfClmCurMonPaid;
	}
	public void setNoOfClmCurMonPaid(int noOfClmCurMonPaid) {
		this.noOfClmCurMonPaid = noOfClmCurMonPaid;
	}
	public BigDecimal getCurMonPaidAmount() {
		return curMonPaidAmount;
	}
	public void setCurMonPaidAmount(BigDecimal curMonPaidAmount) {
		this.curMonPaidAmount = curMonPaidAmount;
	}
	public int getNoOfClmCurMonOutstanding() {
		return noOfClmCurMonOutstanding;
	}
	public void setNoOfClmCurMonOutstanding(int noOfClmCurMonOutstanding) {
		this.noOfClmCurMonOutstanding = noOfClmCurMonOutstanding;
	}
	public BigDecimal getCurMonOutstandingAmount() {
		return curMonOutstandingAmount;
	}
	public void setCurMonOutstandingAmount(BigDecimal curMonOutstandingAmount) {
		this.curMonOutstandingAmount = curMonOutstandingAmount;
	}
	public int getNoOfClmCurMonApproved() {
		return noOfClmCurMonApproved;
	}
	public void setNoOfClmCurMonApproved(int noOfClmCurMonApproved) {
		this.noOfClmCurMonApproved = noOfClmCurMonApproved;
	}
	public BigDecimal getCurMonApprovedAmount() {
		return curMonApprovedAmount;
	}
	public void setCurMonApprovedAmount(BigDecimal curMonApprovedAmount) {
		this.curMonApprovedAmount = curMonApprovedAmount;
	}
	public int getNoOfClmCurMonDenied() {
		return noOfClmCurMonDenied;
	}
	public void setNoOfClmCurMonDenied(int noOfClmCurMonDenied) {
		this.noOfClmCurMonDenied = noOfClmCurMonDenied;
	}
	public BigDecimal getCurMonDeniedAmount() {
		return curMonDeniedAmount;
	}
	public void setCurMonDeniedAmount(BigDecimal curMonDeniedAmount) {
		this.curMonDeniedAmount = curMonDeniedAmount;
	}
	public int getNoOfClmCurMonShortfall() {
		return noOfClmCurMonShortfall;
	}
	public void setNoOfClmCurMonShortfall(int noOfClmCurMonShortfall) {
		this.noOfClmCurMonShortfall = noOfClmCurMonShortfall;
	}
	public BigDecimal getCurMonShortfallAmount() {
		return curMonShortfallAmount;
	}
	public void setCurMonShortfallAmount(BigDecimal curMonShortfallAmount) {
		this.curMonShortfallAmount = curMonShortfallAmount;
	}
	
	
	
}
