/**
 * @ (#) PreAuthPEDVO.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthPEDVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.enrollment.PEDVO;

public class ICDPCSVO extends PEDVO{
    
    private String strPrimaryAilmentYN = "";
    private Long lngPreAuthClaimSeqID = null;
    private String strHospitalTypeID = "";
    private Integer intNoofVisits = null;
    private Integer intFrequencyVisit = null;
    private String strFrequencyVisitType = "";
    private TariffItemVO tariffItemVO = null;
    private String strTreatmentPlanTypeID="";
    private String strTreatmentPlanDesc = "";
    private String strPackageDescription = "";
    private BigDecimal bdValidatedAmt = null;
    private BigDecimal bdApprovedAmt = null;
    private BigDecimal bdAilmentPackageRate = null;
    private String strDiagnosis = "";
    private String strProcCodes = "";
    private String strDeleteName="Blank";
    private String strDeleteTitle="";
	//Koc Decoupling
    private String strCodCompleteYN=""; 
	private String strAilmentType = "";
    private String strDiagnType = "";    
    //added for KOC-Decoupling
    private Long lngDiagSeqId = null;
    private String strDiagnosisType = "";
    
    /** Retrieve the ProcCodes
	 * @return Returns the strProcCodes.
	 */
	public String getProcCodes() {
		return strProcCodes;
	}//end of getProcCodes()

	/** Sets the ProcCodes
	 * @param strProcCodes The strProcCodes to set.
	 */
	public void setProcCodes(String strProcCodes) {
		this.strProcCodes = strProcCodes;
	}//end of setProcCodes(String strProcCodes)

	/** Retrieve the Diagnosis
	 * @return Returns the strDiagnosis.
	 */
	public String getDiagnosis() {
		return strDiagnosis;
	}//end of getDiagnosis()

	/** Sets the Diagnosis
	 * @param strDiagnosis The strDiagnosis to set.
	 */
	public void setDiagnosis(String strDiagnosis) {
		this.strDiagnosis = strDiagnosis;
	}//end of setDiagnosis(String strDiagnosis)

	/** Retrieve the Ailment Package Rate
	 * @return Returns the bdAilmentPackageRate.
	 */
	public BigDecimal getAilmentPackageRate() {
		return bdAilmentPackageRate;
	}//end of getAilmentPackageRate()

	/** Sets the Ailment Package Rate
	 * @param bdAilmentPackageRate The bdAilmentPackageRate to set.
	 */
	public void setAilmentPackageRate(BigDecimal bdAilmentPackageRate) {
		this.bdAilmentPackageRate = bdAilmentPackageRate;
	}//end of setAilmentPackageRate(BigDecimal bdAilmentPackageRate)

	/** Retrieve the Approved Amount
	 * @return Returns the bdApprovedAmt.
	 */
	public BigDecimal getApprovedAmt() {
		return bdApprovedAmt;
	}//end of getApprovedAmt()

	/** Sets the Approved Amount
	 * @param bdApprovedAmt The bdApprovedAmt to set.
	 */
	public void setApprovedAmt(BigDecimal bdApprovedAmt) {
		this.bdApprovedAmt = bdApprovedAmt;
	}//end of setApprovedAmt(BigDecimal bdApprovedAmt)

	/** Retrieve the Validated Amount
	 * @return Returns the bdValidatedAmt.
	 */
	public BigDecimal getValidatedAmt() {
		return bdValidatedAmt;
	}//end of getValidatedAmt()

	/** Sets the Validated Amount
	 * @param bdValidatedAmt The bdValidatedAmt to set.
	 */
	public void setValidatedAmt(BigDecimal bdValidatedAmt) {
		this.bdValidatedAmt = bdValidatedAmt;
	}//end of setValidatedAmt(BigDecimal bdValidatedAmt)

	/** Retrieve the Package Description
	 * @return Returns the strPackageDescription.
	 */
	public String getPackageDescription() {
		return strPackageDescription;
	}//end of getPackageDescription()

	/** Sets the Package Description
	 * @param strPackageDescription The strPackageDescription to set.
	 */
	public void setPackageDescription(String strPackageDescription) {
		this.strPackageDescription = strPackageDescription;
	}//end of setPackageDescription(String strPackageDescription)

	/**
     * Retrieve the Treatment Plan Type ID
     * @return  strTreatmentPlanTypeID String
     */
    public String getTreatmentPlanTypeID() {
        return strTreatmentPlanTypeID;
    }//end of getTreatmentPlanTypeID()
    
    /**
     * Sets the Treatment Plan Type ID
     * @param  strTreatmentPlanTypeID String  
     */
    public void setTreatmentPlanTypeID(String strTreatmentPlanTypeID) {
        this.strTreatmentPlanTypeID = strTreatmentPlanTypeID;
    }//end of setTreatmentPlanTypeID(String strTreatmentPlanTypeID)
    
    /** Retrieve the Treatment Plan Description
     * @return Returns the strTreatmentPlanDesc.
     */
    public String getTreatmentPlanDesc() {
        return strTreatmentPlanDesc;
    }//end of getTreatmentPlanDesc()
    
    /** Sets the Treatment Plan Description
     * @param strTreatmentPlanDesc The strTreatmentPlanDesc to set.
     */
    public void setTreatmentPlanDesc(String strTreatmentPlanDesc) {
        this.strTreatmentPlanDesc = strTreatmentPlanDesc;
    }//end of setTreatmentPlanDesc(String strTreatmentPlanDesc)
    
    /** Retrieve the TariffItemVO
	 * @return Returns the tariffItemVO.
	 */
	public TariffItemVO getTariffItemVO() {
		return tariffItemVO;
	}//end of getTariffItemVO()

	/** Sets the TariffItemVO
	 * @param tariffItemVO The tariffItemVO to set.
	 */
	public void setTariffItemVO(TariffItemVO tariffItemVO) {
		this.tariffItemVO = tariffItemVO;
	}//end of setTariffItemVO(TariffItemVO tariffItemVO)

	/** Retrieve the Frequency of Visit
	 * @return Returns the intFrequencyVisit.
	 */
	public Integer getFrequencyVisit() {
		return intFrequencyVisit;
	}//end of getFrequencyVisit()

	/** Sets the Frequency of Visit
	 * @param intFrequencyVisit The intFrequencyVisit to set.
	 */
	public void setFrequencyVisit(Integer intFrequencyVisit) {
		this.intFrequencyVisit = intFrequencyVisit;
	}//end of setFrequencyVisit(Integer intFrequencyVisit)

	/** Retrieve the No of Visits
	 * @return Returns the intNoofVisits.
	 */
	public Integer getNoofVisits() {
		return intNoofVisits;
	}//end of getNoofVisits()

	/** Sets the No of Visits
	 * @param intNoofVisits The intNoofVisits to set.
	 */
	public void setNoofVisits(Integer intNoofVisits) {
		this.intNoofVisits = intNoofVisits;
	}//end of setNoofVisits(Integer intNoofVisits)

	/** Retrieve the Frequency Visit Type
	 * @return Returns the strFrequencyVisitType.
	 */
	public String getFrequencyVisitType() {
		return strFrequencyVisitType;
	}//end of getFrequencyVisitType()

	/** Sets the Frequency Visit Type
	 * @param strFrequencyVisitType The strFrequencyVisitType to set.
	 */
	public void setFrequencyVisitType(String strFrequencyVisitType) {
		this.strFrequencyVisitType = strFrequencyVisitType;
	}//end of setFrequencyVisitType(String strFrequencyVisitType)

	/** Retreive the PreAuth Seq ID/Claim Seq ID
     * @return Returns the lngPreAuthClaimSeqID.
     */
    public Long getPreAuthClaimSeqID() {
        return lngPreAuthClaimSeqID;
    }//end of getPreAuthClaimSeqID()
    
    /** Sets the PreAuthSeqID/Claim Seq ID
     * @param lngPreAuthClaimSeqID The lngPreAuthClaimSeqID to set.
     */
    public void setPreAuthClaimSeqID(Long lngPreAuthClaimSeqID) {
        this.lngPreAuthClaimSeqID = lngPreAuthClaimSeqID;
    }//end of setPreAuthClaimSeqID(Long lngPreAuthClaimSeqID)
    
    /** This method returns the Hospital Type ID
     * @return Returns the strHospitalTypeID.
     */
    public String getHospitalTypeID() {
        return strHospitalTypeID;
    }//end of getHospitalTypeID()
    
    /** This method sets the Hospital Type ID
     * @param strHospitalTypeID The strHospitalTypeID to set.
     */
    public void setHospitalTypeID(String strHospitalTypeID) {
        this.strHospitalTypeID = strHospitalTypeID;
    }//end of setHospitalTypeID(String strHospitalTypeID)
    
    /** This method returns the Primary Ailment YN
     * @return Returns the strPrimaryAilmentYN.
     */
    public String getPrimaryAilmentYN() {
        return strPrimaryAilmentYN;
    }//end of getPrimaryAilmentYN()
    
    /** This method sets the Primary Ailment YN
     * @param strPrimaryAilmentYN The strPrimaryAilmentYN to set.
     */
    public void setPrimaryAilmentYN(String strPrimaryAilmentYN) {
        this.strPrimaryAilmentYN = strPrimaryAilmentYN;
    }//end of setPrimaryAilmentYN(String strPrimaryAilmentYN)

	/**This method returns the
	 * @return Returns the strDeleteName.
	 */
	public String getDeleteName() {
		return strDeleteName;
	}//end of getDeleteName()

	/**
	 * @param strDeleteName The strDeleteName to set.
	 */
	public void setDeleteName(String strDeleteName) {
		this.strDeleteName = strDeleteName;
	}

	/**This method returns the
	 * @return Returns the strDeleteTitle.
	 */
	public String getDeleteTitle() {
		return strDeleteTitle;
	}

	/**
	 * @param strDeleteTitle The strDeleteTitle to set.
	 */
	public void setDeleteTitle(String strDeleteTitle) {
		this.strDeleteTitle = strDeleteTitle;
	}
	public void setAilmentType(String strAilmentType) {
		this.strAilmentType = strAilmentType;
	}

	public String getAilmentType() {
		return strAilmentType;
	}
    //added for CR KOC-Decoupling
	public void setCodCompleteYN(String strCodCompleteYN) {
		this.strCodCompleteYN = strCodCompleteYN;
	}
	public String getCodCompleteYN() {
		return strCodCompleteYN;
	}
	public void setDiagSeqId(Long lngDiagSeqId) {
		this.lngDiagSeqId = lngDiagSeqId;
	}

	public Long getDiagSeqId() {
		return lngDiagSeqId;
	}

	public void setDiagnosisType(String strDiagnosisType) {
		this.strDiagnosisType = strDiagnosisType;
	}

	public String getDiagnosisType() {
		return strDiagnosisType;
	}

	public void setDiagnType(String strDiagnType) {
		this.strDiagnType = strDiagnType;
	}

	public String getDiagnType() {
		return strDiagnType;
	}
}//end of PreAuthPEDVO
