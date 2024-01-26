/**
 * @ (#) TransactionVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : TransactionVO.java
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

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of of the transaction information.
 */

public class ReportsVO extends BaseVO {

	private String strReportName="";
	private String strInvNumber="";
	private String strGroupId="";
    private String strGroupName="";
    private String strFloatAccNo="";
    private String strBatchNo="";
    private String strClaimTypeID = "";
    private Date dtBatchDate = null;
    private String strSelectRptType = "";
    private String strPayMode = "";
    private String strSelectBatch = "";
    private String strBankAccountNo = "";
    private String strInsInfo = "";
    private String strFileName;//JRXML FileName.
    private String ibanNo = "";
    private String dhaLicenseNo = "";
    private String benefitTypes = "";
    
    
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
    
    /** Retrieve the Insurance Info
	 * @return the strInsInfo
	 */
	public String getInsInfo() {
		return strInsInfo;
	}//end of getInsInfo()

	/** Sets the Insurance Info
	 * @param strInsInfo the strInsInfo to set
	 */
	public void setInsInfo(String strInsInfo) {
		this.strInsInfo = strInsInfo;
	}//end of setInsInfo(String strInsInfo)

	/** Retrieve the Bank account No.
	 * @return the strBankAccountNo
	 */
	public String getBankAccountNo() {
		return strBankAccountNo;
	}//end of getBankAccountNo()

	/** Sets the Bank account No.
	 * @param strBankAccountNo the strBankAccountNo to set
	 */
	public void setBankAccountNo(String strBankAccountNo) {
		this.strBankAccountNo = strBankAccountNo;
	}//end of setBankAccountNo(String strBankAccountNo)

	/** Retrieve the SelectBatch
	 * @return Returns the strSelectBatch.
	 */
	public String getSelectBatch() {
		return strSelectBatch;
	}//end of getSelectBatch()

	/** Sets the SelectBatch
	 * @param strSelectRptType The strSelectBatch to set.
	 */
	public void setSelectBatch(String strSelectBatch) {
		this.strSelectBatch = strSelectBatch;
	}//end of setSelectBatch(String strSelectBatch)
    
    /** Retrieve the SelectRptType
	 * @return Returns the strSelectRptType.
	 */
	public String getSelectRptType() {
		return strSelectRptType;
	}//end of getSelectRptType()

	/** Sets the strSelectRptType
	 * @param strSelectRptType The strSelectRptType to set.
	 */
	public void setSelectRptType(String strSelectRptType) {
		this.strSelectRptType = strSelectRptType;
	}//end of setSelectRptType(String strSelectRptType)

	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public Date getBatchDate() {
		return dtBatchDate;
	}//end of getBatchDate()
	
	/**
     * Retrieve the Received Date
     * @return  dtReceivedDate Date
     */
    public String getEFTBatchDate() {
        return TTKCommon.getFormattedDateHour(dtBatchDate);
    }//end of getEFTBatchDate()

	/** Sets the BatchDate
	 * @param dtBatchDate The dtBatchDate to set.
	 */
	public void setBatchDate(Date dtBatchDate) {
		this.dtBatchDate = dtBatchDate;
	}//end of setBatchDate(Date dtBatchDate)

	/** Retrieve the ClaimTypeID
	 * @return Returns the strClaimTypeID.
	 */
	public String getClaimTypeID() {
		return strClaimTypeID;
	}//end of getClaimTypeID()
	
	/** Sets the ClaimTypeID
	 * @param strClaimTypeID The strClaimTypeID to set.
	 */
	public void setClaimTypeID(String strClaimTypeID) {
		this.strClaimTypeID = strClaimTypeID;
	}//end of setClaimTypeID(String strClaimTypeID)
	
	/**Retrieve the Batch Number
	 * @return Returns the strBatchNo.
	 */
	public String getBatchNo() {
		return strBatchNo;
	}//end of getBatchNo() 
	
	/** Sets the Batch Nubmer 
	 * @param strBatchNo The strBatchNo to set.
	 */
	public void setBatchNo(String strBatchNo) {
		this.strBatchNo = strBatchNo;
	}//end of setBatchNo(String strBatchNo) 
	
	/** Retrieve the float account number
	 * @return Returns the strFloatAccNo.
	 */
	public String getFloatAccNo() {
		return strFloatAccNo;
	}//end of getFloatAccNo() 
	
	/** Sets the float account number
	 * @param strFloatAccNo The strFloatAccNo to set.
	 */
	public void setFloatAccNo(String strFloatAccNo) {
		this.strFloatAccNo = strFloatAccNo;
	}//end of setFloatAccNo(String strFloatAccNo)
	
	/** Retrieve the Group ID
	 * @return Returns the strGroupId.
	 */
	public String getGroupId() {
		return strGroupId;
	}//end of getGroupId() 
	
	/** Sets the Group ID
	 * @param strGroupId The strGroupId to set.
	 */
	public void setGroupId(String strGroupId) {
		this.strGroupId = strGroupId;
	}//end of setGroupId(String strGroupId)
	
	/** Retrieve the Group Name
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()
	
	/** Sets the Group Name
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)
	
	/** Retrive the Invoice number
	 * @return the strInvNumber
	 */
	public String getInvNumber() {
		return strInvNumber;
	}//end of getInvNumber
	
	/**
	 * @param strInvNumber the strInvNumber to set
	 */
	public void setInvNumber(String strInvNumber) {
		this.strInvNumber = strInvNumber;
	}//end of setInvNumber(String strInvNumber)
	
	/** Retrieve the Report Name
	 * @return the strReportName
	 */
	public String getReportName() {
		return strReportName;
	}//end of getReportName()
	
	/** Sets the Report Name
	 * @param strReportName the strReportName to set
	 */
	public void setReportName(String strReportName) {
		this.strReportName = strReportName;
	}//end of setReportName(String strReportName)

	/** Retrive the Payment Mode
	 * @return Returns the strPayMode.
	 */
	public String getPayMode() {
		return strPayMode;
	}//end of getPayMode()

	/** Sets the Payment Mode
	 * @param strPayMode The strPayMode to set.
	 */
	public void setPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}//end of setPayMode(String strPayMode)

	public String getIbanNo() {
		return ibanNo;
	}

	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	public String getDhaLicenseNo() {
		return dhaLicenseNo;
	}

	public void setDhaLicenseNo(String dhaLicenseNo) {
		this.dhaLicenseNo = dhaLicenseNo;
	}

	public String getBenefitTypes() {
		return benefitTypes;
	}

	public void setBenefitTypes(String benefitTypes) {
		this.benefitTypes = benefitTypes;
	}
    
}//end of ReportsVO
