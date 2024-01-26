/**
 * @ (#) CertificateVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : CertificateVO.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : June 07, 2006
 *
 * @author       : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.finance;

import com.ttk.dto.BaseVO;

/**
 * This VO contains cheque information.
 */

public class CertificateVO extends BaseVO {

	private Long lngBatchSeqID=null;
	private String strFinanceYear="";
	private String strFinYearTo="";
	private String strTDSBatchTypeID="";//TDS_BATCH_TYPE_ID
	private String strProcessedYN="";//PROCESSED_YN
	private String strHospitalID="";//hosp_code
	private String strStartDate="";
	private String strEndDate="";
	private String strBatchTypeID="";
	private String strSearchCon="";//search_str
	private String strRegenerationImageName = "Reassociate";
    private String strRegenerationImageTitle = "Regenerate Batch";	
    private String strTdsBatchQuarter ="";
    private String strTdsBatchQtrDesc="";
    
	
	/** Retrieve the TdsBatchQtrDesc
	 * @return the strTdsBatchQtrDesc
	 */
	public String getTdsBatchQtrDesc() {
		return strTdsBatchQtrDesc;
	}//end of getTdsBatchQtrDesc()

	/** Sets the TdsBatchQtrDesc
	 * @param strTdsBatchQtrDesc the strTdsBatchQtrDesc to set
	 */
	public void setTdsBatchQtrDesc(String strTdsBatchQtrDesc) {
		this.strTdsBatchQtrDesc = strTdsBatchQtrDesc;
	}//end of setTdsBatchQtrDesc(String strTdsBatchQtrDesc)

	/** Retrieve the TdsBatchQuarter
	 * @return the strTdsBatchQuarter
	 */
	public String getTdsBatchQuarter() {
		return strTdsBatchQuarter;
	}//end of getTdsBatchQuarter()

	/** Sets the TdsBatchQuarter
	 * @param strTdsBatchQuarter the strTdsBatchQuarter to set
	 */
	public void setTdsBatchQuarter(String strTdsBatchQuarter) {
		this.strTdsBatchQuarter = strTdsBatchQuarter;
	}//end of setTdsBatchQuarter(String strTdsBatchQuarter)

	/** Retrieve the Regeneration ImageName
	 * @return the strRegenerationImageName
	 */
	public String getRegenerationImageName() {
		return strRegenerationImageName;
	}//end of getRegenerationImageName()

	/** Sets the Regeneration ImageName
	 * @param strRegenerationImageName the strRegenerationImageName to set
	 */
	public void setRegenerationImageName(String strRegenerationImageName) {
		this.strRegenerationImageName = strRegenerationImageName;
	}//end of setRegenerationImageName(String strRegenerationImageName)

	/** Retrieve the Regeneration ImageTitle
	 * @return the strRegenerationImageTitle
	 */
	public String getRegenerationImageTitle() {
		return strRegenerationImageTitle;
	}//end of getRegenerationImageTitle() 

	/** Sets the Regeneration ImageTitle
	 * @param strRegenerationImageTitle the strRegenerationImageTitle to set
	 */
	public void setRegenerationImageTitle(String strRegenerationImageTitle) {
		this.strRegenerationImageTitle = strRegenerationImageTitle;
	}//end of setRegenerationImageTitle(String strRegenerationImageTitle)

	/** Retrieve the Batch Type ID
	 * @return the strBatchTypeID
	 */
	public String getBatchTypeID() {
		return strBatchTypeID;
	}//end of getBatchTypeID()
	
	/** Sets the Batch Type ID
	 * @param strBatchTypeID the strBatchTypeID to set
	 */
	public void setBatchTypeID(String strBatchTypeID) {
		this.strBatchTypeID = strBatchTypeID;
	}//end of setBatchTypeID(String strBatchTypeID)
	
	/** Retrieve the SearchConcat string
	 * @return the strSearchCon
	 */
	public String getSearchCon() {
		return strSearchCon;
	}//end of getSearchCon()
	
	/** Sets the SearchConcat string
	 * @param strSearchCon the strSearchCon to set
	 */
	public void setSearchCon(String strSearchCon) {
		this.strSearchCon = strSearchCon;
	}//end of setSearchCon(String strSearchCon)
	
	/** Retrieve the Hospital ID
	 * @return the strHospitalID
	 */
	public String getHospitalID() {
		return strHospitalID;
	}//end of getHospitalID()
	
	/** Sets the Hospital ID
	 * @param strHospitalID the strHospitalID to set
	 */
	public void setHospitalID(String strHospitalID) {
		this.strHospitalID = strHospitalID;
	}//end of setHospitalID(String strHospitalID) 
	
	/** Retrieve the Start Date
	 * @return the strStartDate
	 */
	public String getStartDate() {
		return strStartDate;
	}//end of getStartDate()
	
	/** Sets the Start Date
	 * @param strStartDate the strStartDate to set
	 */
	public void setStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}//end of setStartDate(String strStartDate)
	
	/** Retrieve the End Date
	 * @return the strEndDate
	 */
	public String getEndDate() {
		return strEndDate;
	}//end of getEndDate()
	
	/** Sets the End Date
	 * @param strEndDate the strEndDate to set
	 */
	public void setEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}//end of setEndDate(String strEndDate)
	
	/** Retrieve the TDS Batch Type ID
	 * @return the strTDSBatchTypeID
	 */
	public String getTDSBatchTypeID() {
		return strTDSBatchTypeID;
	}//end of getTDSBatchTypeID()
	
	/** Sets the TDS Batch Type ID
	 * @param strTDSBatchTypeID the strTDSBatchTypeID to set
	 */
	public void setTDSBatchTypeID(String strTDSBatchTypeID) {
		this.strTDSBatchTypeID = strTDSBatchTypeID;
	}//end of setTDSBatchTypeID(String strTDSBatchTypeID)
	
	/** Retrieve the ProcessedYN
	 * @return the strProcessedYN
	 */
	public String getProcessedYN() {
		return strProcessedYN;
	}//end of getProcessedYN() 
	
	/** Sets the ProcessedYN
	 * @param strProcessedYN the strProcessedYN to set
	 */
	public void setProcessedYN(String strProcessedYN) {
		this.strProcessedYN = strProcessedYN;
	}//end of setProcessedYN(String strProcessedYN)
	
	/** Retrieve the Batch Seq ID
	 * @return the lngBatchSeqID
	 */
	public Long getBatchSeqID() {
		return lngBatchSeqID;
	}//end of getBatchSeqID()
	
	/** Sets the Batch Seq ID
	 * @param lngBatchSeqID the lngBatchSeqID to set
	 */
	public void setBatchSeqID(Long lngBatchSeqID) {
		this.lngBatchSeqID = lngBatchSeqID;
	}//end of setBatchSeqID(Long lngBatchSeqID)
	
	/** Retrieve the Finance Year
	 * @return the strFinanceYear
	 */
	public String getFinanceYear() {
		return strFinanceYear;
	}//end of getFinanceYear() 
	
	/** Sets the Finance Year
	 * @param strFinanceYear the strFinanceYear to set
	 */
	public void setFinanceYear(String strFinanceYear) {
		this.strFinanceYear = strFinanceYear;
	}//end of setFinanceYear(String strFinanceYear)
	
	/** Retrieve the Fin Year To
	 * @return the strFinYearTo
	 */
	public String getFinYearTo() {
		return strFinYearTo;
	}//end of getFinYearTo() 
	
	/** Sets the Fin Year To
	 * @param strFinYearTo the strFinYearTo to set
	 */
	public void setFinYearTo(String strFinYearTo) {
		this.strFinYearTo = strFinYearTo;
	}//end of setFinYearTo(String strFinYearTo) 	
	
}//end of ChequeVO