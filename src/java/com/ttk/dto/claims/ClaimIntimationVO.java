/**
 * @ (#) ClaimIntimationVO.java Jul 16, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimIntimationVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 16, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.claims;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.preauth.PreAuthVO;

public class ClaimIntimationVO extends PreAuthVO{

	private Long lngIntimationSeqID = null;
    private Long lngCallLogSeqID = null;
    private Long lngHospitalSeqID = null;
    private Date dtHospitalizationDate = null;

	private String strHospitalizationTime = "";
	private String strHospitalizationDay = "";
	private BigDecimal bdEstimatedAmt = null;
	private String strAilmentDesc = "";
	private Date dtIntimationDate = null;
    private Date dtLikelyDateOfHosp = null;
	private String strHospitalAaddress = null;
	//koc 1339 for mail
	private String strPatientName = null;
	private String strSource = null;
	
	/**
	 * @param strSource the strSource to set
	 */
	public void setSource(String strSource) {
		this.strSource = strSource;
	}

	/**
	 * @return the strSource
	 */
	public String getSource() {
		return strSource;
	}

	/**
	 * @param strPatientName the strPatientName to set
	 */
	public void setPatientName(String strPatientName) {
		this.strPatientName = strPatientName;
	}

	/**
	 * @return the strPatientName
	 */
	public String getPatientName() {
		return strPatientName;
	}

	/*private String strTtkid = null;
	private String strPolicyNumber = null;

    *//**
	 * @param strPolicyNumber the strPolicyNumber to set
	 *//*
	public void setPolicyNumber(String strPolicyNumber) {
		this.strPolicyNumber = strPolicyNumber;
	}

	*//**
	 * @return the strPolicyNumber
	 *//*
	public String getPolicyNumber() {
		return strPolicyNumber;
	}

	*//**
	 * @param strTtkid the strTtkid to set
	 *//*
	public void setTtkid(String strTtkid) {
		this.strTtkid = strTtkid;
	}

	*//**
	 * @return the strTtkid
	 *//*
	public String getTtkid() {
		return strTtkid;
	}*/
	//koc 1339 for mail
	/** Retrieve the Address
     * @return Returns the strHospitalAaddress.
     */
    public String getHospitalAaddress() {
        return strHospitalAaddress;
    }//end of getHospitalAaddress()

    /** Sets the Address
     * @return Returns the strHospitalAaddress.
     */
    public void setHospitalAaddress(String strAaddress) {
        this.strHospitalAaddress =strAaddress;
    }//end of setHospitalAaddress()

	/** Retrieve the HospitalizationDate
	 * @return Returns the dtHospitalizationDate.
	 */
	public Date getHospitalizationDate() {
		return dtHospitalizationDate;
	}//end of getHospitalizationDate()

	/** Retrieve the HospitalizationDate
	 * @return Returns the dtHospitalizationDate.
	 */
	public String getDateOfHospitalization() {
		return TTKCommon.getFormattedDate(dtHospitalizationDate);
	}//end of getDateOfHospitalization()

	/** Retrieve the HospitalizationDate
	 * @return Returns the dtHospitalizationDate.
	 */
	public String getLikelyDateOfHospitalization() {
		return TTKCommon.getFormattedDateHour(dtHospitalizationDate);
	}//end of getLikelyDateOfHospitalization()

	/** Sets the HospitalizationDate
	 * @param dtHospitalizationDate The dtHospitalizationDate to set.
	 */
	public void setHospitalizationDate(Date dtHospitalizationDate) {
		this.dtHospitalizationDate = dtHospitalizationDate;
	}//end of setHospitalizationDate(Date dtHospitalizationDate)

	/** Retrieve the HospitalizationDay
	 * @return Returns the strHospitalizationDay.
	 */
	public String getHospitalizationDay() {
		return strHospitalizationDay;
	}//end of getHospitalizationDay()

	/** Sets the HospitalizationDay
	 * @param strHospitalizationDay The strHospitalizationDay to set.
	 */
	public void setHospitalizationDay(String strHospitalizationDay) {
		this.strHospitalizationDay = strHospitalizationDay;
	}//end of setHospitalizationDay(String strHospitalizationDay)

	/** Retrieve the HospitalizationTime
	 * @return Returns the strHospitalizationTime.
	 */
	public String getHospitalizationTime() {
		return strHospitalizationTime;
	}//end of getHospitalizationTime()

	/** Sets the HospitalizationTime
	 * @param strHospitalizationTime The strHospitalizationTime to set.
	 */
	public void setHospitalizationTime(String strHospitalizationTime) {
		this.strHospitalizationTime = strHospitalizationTime;
	}//end of setHospitalizationTime(String strHospitalizationTime)

	/** Retrieve the Estimated Amount
	 * @return Returns the bdEstimatedAmt.
	 */
	public BigDecimal getEstimatedAmt() {
		return bdEstimatedAmt;
	}//end of getEstimatedAmt()

	/** Sets the Estimated Amount
	 * @param bdEstimatedAmt The bdEstimatedAmt to set.
	 */
	public void setEstimatedAmt(BigDecimal bdEstimatedAmt) {
		this.bdEstimatedAmt = bdEstimatedAmt;
	}//end of setEstimatedAmt(BigDecimal bdEstimatedAmt)

    /** Retrieve the Likely Date Of hospitalisation
     * @return Returns the dtLikelyDateOfHosp.
     */
    public Date getLikelyDateOfHosp() {
        return dtLikelyDateOfHosp;
    }//end of getLikelyDateOfHosp()

    /** Retrieve the Likely Date Of hospitalisation
     * @return Returns the dtLikelyDateOfHosp.
     */
    public String getClaimLikelyDateOfHosp() {
        return TTKCommon.getFormattedDateHour(dtLikelyDateOfHosp);
    }//end of getClaimLikelyDateOfHosp()
    
    /** Retrieve the Likely Date Of hospitalisation
     * @return Returns the dtLikelyDateOfHosp.
     */
    public void setClaimLikelyDateOfHosp(String strLikelyDateOfHosp) {
        
    }//end of getClaimLikelyDateOfHosp()

    /** Sets the Likely Date Of hospitalisation
     * @param dtLikelyDateOfHosp The dtLikelyDateOfHosp to set.
     */
    public void setLikelyDateOfHosp(Date dtLikelyDateOfHosp) {
        this.dtLikelyDateOfHosp = dtLikelyDateOfHosp;
    }//end of setLikelyDateOfHosp(Date dtLikelyDateOfHosp)

	/** Retrieve the Intimation Date
	 * @return Returns the dtIntimationDate.
	 */
	public Date getIntimationDate() {
		return dtIntimationDate;
	}//end of getIntimationDate()

	/** Retrieve the Intimation Date
	 * @return Returns the dtIntimationDate.
	 */
	public String getClaimIntimationDate() {
		return TTKCommon.getFormattedDateHour(dtIntimationDate);
	}//end of getClaimIntimationDate()
	
	/** Retrieve the Intimation Date
	 * @return Returns the dtIntimationDate.
	 */
	public void setClaimIntimationDate(String strIntimationDate) {
		
	}//end of getClaimIntimationDate()
	
	/** Sets the Intimation Date
	 * @param dtIntimationDate The dtIntimationDate to set.
	 */
	public void setIntimationDate(Date dtIntimationDate) {
		this.dtIntimationDate = dtIntimationDate;
	}//end of setIntimationDate(Date dtIntimationDate)

    /** Retrieve the Hospital Seq ID
     * @return Returns the lngCallLogSeqID.
     */
    public Long getHospitalSeqID() {
        return lngHospitalSeqID;
    }//end of getHospitalSeqID()

    /** Sets the Hospital Seq ID
     * @param lngHospitalSeqID The lngHospitalSeqID to set.
     */

    public void setHospitalSeqID(Long lngHospitalSeqID) {
        this.lngHospitalSeqID = lngHospitalSeqID;
    }//end of setHospitalSeqID(Long lngHospitalSeqID)

    /** Retrieve the Call Log Seq ID
     * @return Returns the lngCallLogSeqID.
     */
    public Long getCallLogSeqID() {
        return lngCallLogSeqID;
    }//end of getCallLogSeqID()

    /** Sets the CallLog Seq ID
     * @param lngCallLogSeqID The lngCallLogSeqID to set.
     */
    public void setCallLogSeqID(Long lngCallLogSeqID) {
        this.lngCallLogSeqID = lngCallLogSeqID;
    }//end of setCallLogSeqID(Long lngCallLogSeqID)
	/** Retrieve the IntimationSeqID
	 * @return Returns the lngIntimationSeqID.
	 */
	public Long getIntimationSeqID() {
		return lngIntimationSeqID;
	}//end of getIntimationSeqID()

	/** Sets the IntimationSeqID
	 * @param lngIntimationSeqID The lngIntimationSeqID to set.
	 */
	public void setIntimationSeqID(Long lngIntimationSeqID) {
		this.lngIntimationSeqID = lngIntimationSeqID;
	}//end of setIntimationSeqID(Long lngIntimationSeqID)

	/** Retrieve the Ailment Description
	 * @return Returns the strAilmentDesc.
	 */
	public String getAilmentDesc() {
		return strAilmentDesc;
	}//end of getAilmentDesc()

	/** Sets the Ailment Description
	 * @param strAilmentDesc The strAilmentDesc to set.
	 */
	public void setAilmentDesc(String strAilmentDesc) {
		this.strAilmentDesc = strAilmentDesc;
	}//end of setAilmentDesc(String strAilmentDesc)

}//end of ClaimIntimationVO
