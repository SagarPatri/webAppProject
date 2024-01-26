/**
 * @ (#) TdsCertificateVO.java May 06, 2010
 * Project      : TTK HealthCare Services
 * File         :TdsCertificateVO.java
 * Author       : Swaroop Kaushik D.S
 * Company      : Span Systems Corporation
 * Date Created : May 06, 2010
 *
 * @author       : Swaroop Kaushik D.S
 * Modified by   : Manikanta Kumar G.G
 * Modified date : May 06, 2010
 * Reason        : To Add additional Fields (CertPath)
 */
package com.ttk.dto.empanelment;

import com.ttk.dto.BaseVO;

public class TdsCertificateVO extends BaseVO{
	// fields declared private
	private Long lngCertSeqId = null;
	private String strEmpanelNumber = "";
	private String strDescription = "";
	private String strCertPath = "";
	private String strImageName = "HistoryIcon";
	private String strImageTitle = "Certificate Details";
	private Long lngFinancialYear = null;
	private Long lngFinancialYearTo = null;
	private String strFinancialYear = null;
	private Long lngHospSeqId  = null;
	
	/** Retrieve the ImageName
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}//end of getStrImageName()

	/** Sets the ImageName
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)

	/** Retrieve the ImageTitle
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle()

	/** Sets the ImageTitle
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)
	
	/** Retrieve the FinancialYearTo
	 * @return Returns the lngFinancialYearTo.
	 */
	public Long getFinancialYearTo() {
		return lngFinancialYearTo;
	}//end of getFinancialYearTo()
	
	/** Sets the FinancialYearTo
	 * @param lngFinancialYearTo The lngFinancialYearTo to set.
	 */
	public void setFinancialYearTo(Long lngFinancialYearTo) {
		this.lngFinancialYearTo = lngFinancialYearTo;
	}//end of setFinancialYearTo(Long lngFinancialYearTo)
	
	/** Retrieve the strFinancialYear
	 * @return Returns the strFinancialYear.
	 */
	public String getStrFinancialYear() {
		return strFinancialYear;
	}//end of getStrFinancialYear

	/** Sets the StrFinancialYear
	 * @param strFinancialYear The strFinancialYear to set.
	 */
	public void setStrFinancialYear(String strFinancialYear) {
		this.strFinancialYear = strFinancialYear;
	}//end of setStrFinancialYear(String strFinancialYear) 
	
	/** Retrieve the CertificatePath
	 * @return Returns the strCertPath.
	 */
	public String getCertPath() {
		return strCertPath;
	}//end of getCertPath()

	/** Sets the CertificatePath
	 * @param strCertPath The strCertPath to set.
	 */
	public void setCertPath(String strCertPath) {
		this.strCertPath = strCertPath;
	}//end of setCertPath(String strCertPath) 

	/** Retrieve the HospSeqId
	 * @return Returns the lngHospSeqId.
	 */
	public Long getHospSeqId() {
		return lngHospSeqId;
	}//end of getHospSeqId() 
	
	/** Sets the HospSeqId
	 * @param lngHospSeqId The lngHospSeqId to set.
	 */
	public void setHospSeqId(Long lngHospSeqId) {
		this.lngHospSeqId = lngHospSeqId;
	}//end of setHospSeqId(Long lngHospSeqId)
	
	/** Retrieve the CertificateSequenceid
	 * @return Returns the lngCertSeqId.
	 */
	public Long getCertSeqId() {
		return lngCertSeqId;
	}//end of getCertSeqId()
	
	/** Sets the CertificateSequenceid
	 * @param lngCertSeqId The lngCertSeqId to set.
	 */
	public void setCertSeqId(Long lngCertSeqId) {
		this.lngCertSeqId = lngCertSeqId;
	}//end of setCertSeqId(Long lngCertSeqId)
	
	/** Retrieve the Empanelment number of hospital
	 * @return Returns the strEmpanelNumber.
	 */
	public String getEmpanelNumber() {
		return strEmpanelNumber;
	}//end of getEmpanelNumber
	
	/** Sets the Empanelment number of hospital
	 * @param strEmpanelNumber The strEmpanelNumber to set.
	 */
	public void setEmpanelNumber(String strEmpanelNumber) {
		this.strEmpanelNumber = strEmpanelNumber;
	}//end of setEmpanelNumber(String strEmpanelNumber)
	
	/** Retrieve the Description
	 * @return Returns the strDescription.
	 */
	public String getDescription() {
		return strDescription;
	}//end of getDescription()
	
	/** Sets the Description
	 * @param strDescription The strDescription to set.
	 */
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}//end of setDescription(String strDescription)
	
	/** Retrieve the Financialyear
	 * @return Returns the lngFinancialYear.
	 */
	public Long getFinancialYear() {
		return lngFinancialYear;
	}//end of getFinancialYear()
	
	/** Sets the Financialyear
	 * @param lngFinancialYear The lngFinancialYear to set.
	 */
	public void setFinancialYear(Long lngFinancialYear) {
		this.lngFinancialYear = lngFinancialYear;
	}//end of setFinancialYear(Long lngFinancialYear)
	
}//end of TdsCertificateVO
