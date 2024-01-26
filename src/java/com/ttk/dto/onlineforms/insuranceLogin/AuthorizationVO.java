/**
 * @ (#) AuthorizationVO.java 27th August 2015
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

public class AuthorizationVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	

	
	//Below getters are using in Focus View
    private Date dtDateFrom 				= null;
    private Date dtDateTo 				= null;
    private BigDecimal bdClaimedAmt 		= null;
    private BigDecimal bdSettledAmt 		= null;
    private String strStatus 				=	"";
    private String strName;
    private String strRelation;
    
    public String getName() {
		return strName;
	}

	public void setName(String strName) {
		this.strName = strName;
	}

	public String getRelation() {
		return strRelation;
	}

	public void setRelation(String strRelation) {
		this.strRelation = strRelation;
	}

	public String getAuthNo() {
		return strAuthNo;
	}

	public void setAuthNo(String strAuthNo) {
		this.strAuthNo = strAuthNo;
	}

	private String strAuthNo;
    

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

	public String getStatus() {
		return strStatus;
	}

	public void setStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//Getters and setters for Global View
	//as on date
	private int preAuthReceivedAsOnDate;
	private BigDecimal amtPreAuthReceivedAsOnDate;

	private int preAuthApprovedAsOnDate;
	private BigDecimal amtPreAuthApprovedAsOnDate;	

	private int preAuthDeniedAsOnDate;
	private BigDecimal amtPreAuthDeniedAsOnDate;	

	private int preAuthShortfallAsOnDate;
	private BigDecimal amtPreAuthShortfallAsOnDate;	
	
	
	//for current month
	private int preAuthReceivedCurMonth;
	private BigDecimal amtPreAuthReceivedCurMonth;

	private int preAuthApprovedCurMonth;
	private BigDecimal amtPreAuthApprovedCurMonth;	

	private int preAuthDeniedCurMonth;
	private BigDecimal amtPreAuthDeniedCurMonth;	

	private int preAuthShortfallCurMonth;
	private BigDecimal amtPreAuthShortfallCurMonth;
	public int getPreAuthReceivedAsOnDate() {
		return preAuthReceivedAsOnDate;
	}
	public void setPreAuthReceivedAsOnDate(int preAuthReceivedAsOnDate) {
		this.preAuthReceivedAsOnDate = preAuthReceivedAsOnDate;
	}
	public BigDecimal getAmtPreAuthReceivedAsOnDate() {
		return amtPreAuthReceivedAsOnDate;
	}
	public void setAmtPreAuthReceivedAsOnDate(BigDecimal amtPreAuthReceivedAsOnDate) {
		this.amtPreAuthReceivedAsOnDate = amtPreAuthReceivedAsOnDate;
	}
	public int getPreAuthApprovedAsOnDate() {
		return preAuthApprovedAsOnDate;
	}
	public void setPreAuthApprovedAsOnDate(int preAuthApprovedAsOnDate) {
		this.preAuthApprovedAsOnDate = preAuthApprovedAsOnDate;
	}
	public BigDecimal getAmtPreAuthApprovedAsOnDate() {
		return amtPreAuthApprovedAsOnDate;
	}
	public void setAmtPreAuthApprovedAsOnDate(BigDecimal amtPreAuthApprovedAsOnDate) {
		this.amtPreAuthApprovedAsOnDate = amtPreAuthApprovedAsOnDate;
	}
	public int getPreAuthDeniedAsOnDate() {
		return preAuthDeniedAsOnDate;
	}
	public void setPreAuthDeniedAsOnDate(int preAuthDeniedAsOnDate) {
		this.preAuthDeniedAsOnDate = preAuthDeniedAsOnDate;
	}
	public BigDecimal getAmtPreAuthDeniedAsOnDate() {
		return amtPreAuthDeniedAsOnDate;
	}
	public void setAmtPreAuthDeniedAsOnDate(BigDecimal amtPreAuthDeniedAsOnDate) {
		this.amtPreAuthDeniedAsOnDate = amtPreAuthDeniedAsOnDate;
	}
	public int getPreAuthShortfallAsOnDate() {
		return preAuthShortfallAsOnDate;
	}
	public void setPreAuthShortfallAsOnDate(int preAuthShortfallAsOnDate) {
		this.preAuthShortfallAsOnDate = preAuthShortfallAsOnDate;
	}
	public BigDecimal getAmtPreAuthShortfallAsOnDate() {
		return amtPreAuthShortfallAsOnDate;
	}
	public void setAmtPreAuthShortfallAsOnDate(
			BigDecimal amtPreAuthShortfallAsOnDate) {
		this.amtPreAuthShortfallAsOnDate = amtPreAuthShortfallAsOnDate;
	}
	public int getPreAuthReceivedCurMonth() {
		return preAuthReceivedCurMonth;
	}
	public void setPreAuthReceivedCurMonth(int preAuthReceivedCurMonth) {
		this.preAuthReceivedCurMonth = preAuthReceivedCurMonth;
	}
	public BigDecimal getAmtPreAuthReceivedCurMonth() {
		return amtPreAuthReceivedCurMonth;
	}
	public void setAmtPreAuthReceivedCurMonth(BigDecimal amtPreAuthReceivedCurMonth) {
		this.amtPreAuthReceivedCurMonth = amtPreAuthReceivedCurMonth;
	}
	public int getPreAuthApprovedCurMonth() {
		return preAuthApprovedCurMonth;
	}
	public void setPreAuthApprovedCurMonth(int preAuthApprovedCurMonth) {
		this.preAuthApprovedCurMonth = preAuthApprovedCurMonth;
	}
	public BigDecimal getAmtPreAuthApprovedCurMonth() {
		return amtPreAuthApprovedCurMonth;
	}
	public void setAmtPreAuthApprovedCurMonth(BigDecimal amtPreAuthApprovedCurMonth) {
		this.amtPreAuthApprovedCurMonth = amtPreAuthApprovedCurMonth;
	}
	public int getPreAuthDeniedCurMonth() {
		return preAuthDeniedCurMonth;
	}
	public void setPreAuthDeniedCurMonth(int preAuthDeniedCurMonth) {
		this.preAuthDeniedCurMonth = preAuthDeniedCurMonth;
	}
	public BigDecimal getAmtPreAuthDeniedCurMonth() {
		return amtPreAuthDeniedCurMonth;
	}
	public void setAmtPreAuthDeniedCurMonth(BigDecimal amtPreAuthDeniedCurMonth) {
		this.amtPreAuthDeniedCurMonth = amtPreAuthDeniedCurMonth;
	}
	public int getPreAuthShortfallCurMonth() {
		return preAuthShortfallCurMonth;
	}
	public void setPreAuthShortfallCurMonth(int preAuthShortfallCurMonth) {
		this.preAuthShortfallCurMonth = preAuthShortfallCurMonth;
	}
	public BigDecimal getAmtPreAuthShortfallCurMonth() {
		return amtPreAuthShortfallCurMonth;
	}
	public void setAmtPreAuthShortfallCurMonth(
			BigDecimal amtPreAuthShortfallCurMonth) {
		this.amtPreAuthShortfallCurMonth = amtPreAuthShortfallCurMonth;
	}	
	
	
}
