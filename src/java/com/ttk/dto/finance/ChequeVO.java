/**
 * @ (#) ChequeVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : ChequeVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : June 07, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains cheque information.
 */

public class ChequeVO extends BaseVO {

	private Long lngSeqID;// Claims Cheque SeqID or Payment Seq ID
	private String strChequeNo;//Cheque No.
	private String strStartNo;//Starting Number
	private String strEndNo;//Ending Number
	private String strFloatAcctNo;//Float Account No.
	private String strStatusDesc;//Status
	private Date dtChequeDate;//Cheque Date
	private String strClaimSettNo;//Claim Settlement No.
	private String strClaimTypeDesc;//Claim Type Description
	private String strInsCompName;//Insurance Company
	private String strEnrollID;//Enrollment ID
	private String strClaimantName;//Claimant Name
	private Date dtApprDate;     //Approved Date
	private String strInFavorOf; //payee
	private BigDecimal bdClaimAmt;//Claim Amount
	
	
	private String approvedAmtClm;
	
	private Long lngAccountSeqID;//Bank SeqID or Float SeqID.
	private BigDecimal bdAvblFloatBalance = null;
	private String strFileName;//JRXML FileName.
	private Date dtBatchDate = null;//BATCH_DATE
	private String strBatchNumber = "";//FILE_NAME(Database Column)
	private String strBankName = "";//BANK_NAME
	private String strRemarks = "";//REMARKS
	private String strOfficeName = "";
	private String strNewChequeNo;//New Cheque No.
	private String strAbbrevationCode;//Abbrevation Code
	private Long lngPaymentSeqId; // Claim Settlement SeqID
	//added for Mail-SMS Template for Cigna
	private String strCigna_YN = "";
	private String incuredCurencyFormat;
	private BigDecimal convertedApprovedAmount;
    private String paymethod="";
    private String ChequeVO="";
    
	
    private Integer totalClaimRecordUploaded;
    private Integer totalClaimRecordSuccessful;
    private Integer totalClaimRecordFailed;
    
    private String dhpoMemberId;
	
    
    
	
	
	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	/** Retrieve the Abbrevation Code
	 * @return the strAbbrevationCode
	 */
	public String getAbbrevationCode() {
		return strAbbrevationCode;
	}//end of getAbbrevationCode()

	/** Sets the Abbrevation Code
	 * @param strAbbrevationCode the strAbbrevationCode to set
	 */
	public void setAbbrevationCode(String strAbbrevationCode) {
		this.strAbbrevationCode = strAbbrevationCode;
	}//end of setAbbrevationCode(String strAbbrevationCode) 

	/** Retrieve the OfficeName
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()

	/** Sets the OfficeName
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)

	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()

	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)

	/** Retrieve the Batch Date
	 * @return Returns the dtBatchDate.
	 */
	public Date getBatchDate() {
		return dtBatchDate;
	}//end of getBatchDate()
	
	/** Retrieve the Batch Date
	 * @return Returns the dtBatchDate.
	 */
	public String getChequeBatchDate() {
		return TTKCommon.getFormattedDateHour(dtBatchDate);
	}//end of getChequeBatchDate()

	/** Sets the Batch Date
	 * @param dtBatchDate The dtBatchDate to set.
	 */
	public void setBatchDate(Date dtBatchDate) {
		this.dtBatchDate = dtBatchDate;
	}//end of setBatchDate(Date dtBatchDate)

	/** Retrieve the BankName
	 * @return Returns the strBankName.
	 */
	public String getBankName() {
		return strBankName;
	}//end of getBankName()

	/** Sets the BankName
	 * @param strBankName The strBankName to set.
	 */
	public void setBankName(String strBankName) {
		this.strBankName = strBankName;
	}//end of setBankName(String strBankName)

	/** Retrieve the BatchNumber
	 * @return Returns the strBatchNumber.
	 */
	public String getBatchNumber() {
		return strBatchNumber;
	}//end of getBatchNumber()

	/** Sets the BatchNumber
	 * @param strBatchNumber The strBatchNumber to set.
	 */
	public void setBatchNumber(String strBatchNumber) {
		this.strBatchNumber = strBatchNumber;
	}//end of setBatchNumber(String strBatchNumber)

	/**Retrieve the FileName.
	 * @return Returns the strFileName.
	 */
	public String getFileName() {
		return strFileName;
	}//end of getFileName

	/**Sets the FileName.
	 * @param strFileName The strFileName to set.
	 */
	public void setFileName(String strFileName) {
		this.strFileName = strFileName;
	}//end of setFileName

	/** Retrieve the Available Float Balance
	 * @return Returns the bdAvblFloatBalance.
	 */
	public BigDecimal getAvblFloatBalance() {
		return bdAvblFloatBalance;
	}//end of getAvblFloatBalance()

	/** Sets the Available Float Balance
	 * @param bdAvblFloatBalance The bdAvblFloatBalance to set.
	 */
	public void setAvblFloatBalance(BigDecimal bdAvblFloatBalance) {
		this.bdAvblFloatBalance = bdAvblFloatBalance;
	}//end of setAvblFloatBalance(BigDecimal bdAvblFloatBalance)

	/**Retrieve the AccountSeqID.
	 * @return Returns the lngAccountSeqID.
	 */
	public Long getAccountSeqID() {
		return lngAccountSeqID;
	}//end of getAccountSeqID()

	/**Sets the AccountSeqID.
	 * @param lngAccountSeqID The lngAccountSeqID to set.
	 */
	public void setAccountSeqID(Long lngAccountSeqID) {
		this.lngAccountSeqID = lngAccountSeqID;
	}//end of setAccountSeqID(Long lngBankAccSeqID)


	/**Retrieve the Claim Amount.
	 * @return Returns the bdClaimAmt.
	 */
	public BigDecimal getClaimAmt() {
		return bdClaimAmt;
	}//end of getClaimAmt()

	/**Sets the Claim Amount.
	 * @param bdClaimAmt The bdClaimAmt to set.
	 */
	public void setClaimAmt(BigDecimal bdClaimAmt) {
		this.bdClaimAmt = bdClaimAmt;
	}//end of setClaimAmt(BigDecimal bdClaimAmt)

	 /**Retrieve the Approved Date.
     * @return Returns the dtApprDate.
     */
    public Date getApprDate() {
        return dtApprDate;
    }//end of getApprDate()

    /**Retrieve the Approved Date.
     * @return Returns the dtApprDate.
     */
    public String getApprovedDate() {
        return TTKCommon.getFormattedDate(dtApprDate);
    }//end of getApprovedDate()

    /**Sets the Approved Date.
     * @param dtApprDate The dtApprDate to set.
     */
    public void setApprDate(Date dtApprDate) {
        this.dtApprDate = dtApprDate;
    }//end of setApprDate(Date dtApprDate)

	/**Retrieve the In favor of.
	 * @return Returns the strInFavorOf.
	 */
	public String getInFavorOf() {
		return strInFavorOf;
	}//end of getInFavorOf()

	/**Sets the In favor of.
	 * @param strInFavorOf The strInFavorOf to set.
	 */
	public void setInFavorOf(String strInFavorOf) {
		this.strInFavorOf = strInFavorOf;
	}//end of setInFavorOf(String strInFavorOf)

	/**Retrieve the claimant name.
	 * @return Returns the strclaimantName.
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getclaimantName()

	/**Sets the claimant name.
	 * @param strClaimantName The strClaimantName to set.
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setclaimantName(String strClaimantName)

	/**Retrieve the EnrollID.
	 * @return Returns the strEnrollID.
	 */
	public String getEnrollID() {
		return strEnrollID;
	}//end of getEnrollID()

	/**Sets the EnrollID.
	 * @param strEnrollID The strEnrollID to set.
	 */
	public void setEnrollID(String strEnrollID) {
		this.strEnrollID = strEnrollID;
	}//end of setEnrollID(String strEnrollID)

	/**Retrieve the Cheque Date.
	 * @return Returns the dtChequeDate.
	 */
	public Date getChequeDate() {
		return dtChequeDate;
	}//end of getChequeDate()

	/**Retrieve the Cheque Date.
	 * @return Returns the dtChequeDate.
	 */
	public String getChequeInfoDate() {
		return TTKCommon.getFormattedDate(dtChequeDate);
	}//end of getChequeDate()
	/** This method returns the StartDate
     * @return Returns the dtStartDate.
     */
    public String getFormattedChequeDate() {
        return TTKCommon.getFormattedDate(dtChequeDate);
    }// End of getFormattedStartDate()
	/**Sets the Cheque Date.
	 * @param dtChequeDate The dtChequeDate to set.
	 */
	public void setChequeDate(Date dtChequeDate) {
		this.dtChequeDate = dtChequeDate;
	}//end of setChequeDate(Date dtChequeDate)

	/**Retrieve the lngSeqID.
	 * @return Returns the lngSeqID.
	 */
	public Long getSeqID() {
		return lngSeqID;
	}//end of getChequeSeqID()

	/**Sets the lngSeqID.
	 * @param lngSeqID The lngSeqID to set.
	 */
	public void setSeqID(Long lngSeqID) {
		this.lngSeqID = lngSeqID;
	}//end of setSeqID(Long lngSeqID)

	/**Retrieve the ChequeNo.
	 * @return Returns the strChequeNo.
	 */
	public String getChequeNo() {
		return strChequeNo;
	}//end of getChequeNo()

	/**Sets the ChequeNo.
	 * @param strChequeNo The strChequeNo to set.
	 */
	public void setChequeNo(String strChequeNo) {
		this.strChequeNo = strChequeNo;
	}//end of setChequeNo(String strChequeNo)

	/**Retrieve the ClaimSettNo.
	 * @return Returns the strClaimSettNo.
	 */
	public String getClaimSettNo() {
		return strClaimSettNo;
	}//end of getClaimSettNo()

	/**Sets the ClaimSettNo.
	 * @param strClaimSettNo The strClaimSettNo to set.
	 */
	public void setClaimSettNo(String strClaimSettNo) {
		this.strClaimSettNo = strClaimSettNo;
	}//end of setClaimSettNo(String strClaimSettNo)

	/**Retrieve the Claim Type Description.
	 * @return Returns the strClaimTypeDesc.
	 */
	public String getClaimTypeDesc() {
		return strClaimTypeDesc;
	}//end of getClaimTypeDesc()

	/**Sets the Claim Type Description.
	 * @param strClaimTypeDesc The strClaimTypeDesc to set.
	 */
	public void setClaimTypeDesc(String strClaimTypeDesc) {
		this.strClaimTypeDesc = strClaimTypeDesc;
	}//end of setClaimTypeDesc(String strClaimTypeDesc)

	/**Retrieve the Cheque EndNo.
	 * @return Returns the strEndNo.
	 */
	public String getEndNo() {
		return strEndNo;
	}//end of getEndNo()

	/**Sets the Cheque EndNo.
	 * @param strEndNo The strEndNo to set.
	 */
	public void setEndNo(String strEndNo) {
		this.strEndNo = strEndNo;
	}//end of setEndNo(String strEndNo)

	/**Retrieve the FloatAccNo.
	 * @return Returns the strFloatAcctNo.
	 */
	public String getFloatAcctNo() {
		return strFloatAcctNo;
	}//end of getFloatAcctNo()

	/**Sets the FloatAccNo.
	 * @param strFloatAcctNo The strFloatAcctNo to set.
	 */
	public void setFloatAcctNo(String strFloatAcctNo) {
		this.strFloatAcctNo = strFloatAcctNo;
	}//end of setFloatAcctNo(String strFloatAcctNo)

	/**Retrieve the Insurance Company.
	 * @return Returns the strInsCompName.
	 */
	public String getInsCompName() {
		return strInsCompName;
	}//end of getInsCompName()

	/**Sets the Insurance Company.
	 * @param strInsCompName The strInsCompName to set.
	 */
	public void setInsCompName(String strInsCompName) {
		this.strInsCompName = strInsCompName;
	}//end of setInsCompName(String strInsCompName)

	/**Retrieve the Status Description.
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/**Sets the Status Description.
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/**Retrieve the Start No.
	 * @return Returns the strStartNo.
	 */
	public String getStartNo() {
		return strStartNo;
	}//end of getStartNo()

	/**Sets the Start No.
	 * @param strStartNo The strStartNo to set.
	 */
	public void setStartNo(String strStartNo) {
		this.strStartNo = strStartNo;
	}//end of setStartNo(String strStartNo)

	/**Retrieve the NewChequeNo.
	 * @return Returns the strNewChequeNo.
	 */
	public String getNewChequeNo() {
		return strNewChequeNo;
	}//end of getNewChequeNo()

	/**Sets the NewChequeNo.
	 * @param strNewChequeNo The strNewChequeNo to set.
	 */
	public void setNewChequeNo(String strNewChequeNo) {
		this.strNewChequeNo = strNewChequeNo;
	}//end of setNewChequeNo(String strNewChequeNo)
	
	/**Retrieve the lngPaymentSeqId.
	 * @return Returns the lngPaymentSeqId.
	 */
	public Long getPaymentSeqId() {
		return lngPaymentSeqId;
	}//end of getPaymentSeqId()

	/**Sets the lngPaymentSeqId.
	 * @param lngPaymentSeqId The lngPaymentSeqId to set.
	 */
	public void setPaymentSeqId(Long lngPaymentSeqId) {
		this.lngPaymentSeqId = lngPaymentSeqId;
	}//end of setPaymentSeqId(Long lngPaymentSeqId)
	//added for Cigna Mail-SMS Template 
	public void setCigna_YN(String strCigna_YN) {
		this.strCigna_YN = strCigna_YN;
	}

	public String getCigna_YN() {
		return strCigna_YN;
	}
	
	public String getIncuredCurencyFormat() {
		return incuredCurencyFormat;
	}

	public void setIncuredCurencyFormat(String incuredCurencyFormat) {
		this.incuredCurencyFormat = incuredCurencyFormat;
	}

	public BigDecimal getConvertedApprovedAmount() {
		return convertedApprovedAmount;
	}

	public void setConvertedApprovedAmount(BigDecimal convertedApprovedAmount) {
		this.convertedApprovedAmount = convertedApprovedAmount;
	}

	public String getChequeVO() {
		return ChequeVO;
	}

	public void setChequeVO(String chequeVO) {
		ChequeVO = chequeVO;
	}

	public String getApprovedAmtClm() {
		return approvedAmtClm;
	}

	public void setApprovedAmtClm(String approvedAmtClm) {
		this.approvedAmtClm = approvedAmtClm;
	}

	public String getDhpoMemberId() {
		return dhpoMemberId;
	}

	public void setDhpoMemberId(String dhpoMemberId) {
		this.dhpoMemberId = dhpoMemberId;
	}


}//end of ChequeVO