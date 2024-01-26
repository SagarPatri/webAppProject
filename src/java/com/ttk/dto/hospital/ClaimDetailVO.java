/**
 * @ (#) ClaimDetailVO.java 
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDetailVO.java
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.hospital;
import java.util.ArrayList;
import java.util.Date;
import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class ClaimDetailVO extends BaseVO {

	private String strClaimFileNbr = "";
	private String strRequestTypeID = "";
	private String strRequestTypeDesc = "";
	private String strClaimTypeID = "";
	private String strClaimTypeDesc = "";
	private String strClaimSubTypeID = "";
	private Date dtIntimationDate = null;
	private String strModeTypeID = "";
	private String strModeTypeDesc = "";
	private String strInPatientNbr = "";
	private String strSourceDesc = "";
	private Date dtDischargeDate = null;
	private String strDischargeTime = "";
	private String strDischargeDay = "";
	private Date dtClaimReceivedDate = null;
	private String strClaimReceivedTime = "";
	private String strClaimReceivedDay = "";
	private String strClaimRemarks = "";
	private String strReopenTypeID = "";
	private String strReopenTypeDesc = "";
	private String strDoctorRegnNbr = "";
	/*private BigDecimal bdRequestedAmt = null;
	private String strTreatingDrName = "";
	private String strOfficeName = "";
	private String strAssignedTo = "";
	private String strProcessingBranch = "";
	*/
	//..........................................Adedd as per KOC 1285 Change Request....................................
	private String strDomicilaryReason = "";
	private ArrayList alDomicilaryReasonList=null;
	private String strDoctorCertificateYN="";
	private String strDoctorCertificateYNFlag = "";
	
	public void setDoctorCertificateYN(String doctorCertificateYN) {
		strDoctorCertificateYN = doctorCertificateYN;
	}

	public String getDoctorCertificateYN() {
		return strDoctorCertificateYN;
	}

	public void setDoctorCertificateYNFlag(String doctorCertificateYNFlag) {
		strDoctorCertificateYNFlag = doctorCertificateYNFlag;
	}

	public String getDoctorCertificateYNFlag() {
		return strDoctorCertificateYNFlag;
	}

	public void setDomicilaryReasonList(ArrayList domicilaryReasonList) {
		alDomicilaryReasonList = domicilaryReasonList;
	}

	public ArrayList getDomicilaryReasonList() {
		return alDomicilaryReasonList;
	}
		public void setDomicilaryReason(String domicilaryReason) {
		strDomicilaryReason = domicilaryReason;
	}

	public String getDomicilaryReason() {
		return strDomicilaryReason;
	}

	//......................................Adedd as per KOC 1285 Change Request.........................................
	
	/** Retrieve the Intimation Date
	 * @return Returns the dtIntimationDate.
	 */
	public Date getIntimationDate() {
		return dtIntimationDate;
	}//end of getIntimationDate()
	
	/** Sets the Intimation Date
	 * @param dtIntimationDate The dtIntimationDate to set.
	 */
	public void setIntimationDate(Date dtIntimationDate) {
		this.dtIntimationDate = dtIntimationDate;
	}//end of setIntimationDate(Date dtIntimationDate)
	
	/** Retrieve the Claim File Number
	 * @return Returns the strClaimFileNbr.
	 */
	public String getClaimFileNbr() {
		return strClaimFileNbr;
	}//end of getClaimFileNbr()
	
	/** Sets the Claim File Number
	 * @param strClaimFileNbr The strClaimFileNbr to set.
	 */
	public void setClaimFileNbr(String strClaimFileNbr) {
		this.strClaimFileNbr = strClaimFileNbr;
	}//end of setClaimFileNbr(String strClaimFileNbr)
	
	/** Retrieve the ClaimSubTypeID
	 * @return Returns the strClaimSubTypeID.
	 */
	public String getClaimSubTypeID() {
		return strClaimSubTypeID;
	}//end of getClaimSubTypeID()
	
	/** Sets the ClaimSubTypeID
	 * @param strClaimSubTypeID The strClaimSubTypeID to set.
	 */
	public void setClaimSubTypeID(String strClaimSubTypeID) {
		this.strClaimSubTypeID = strClaimSubTypeID;
	}//end of setClaimSubTypeID(String strClaimSubTypeID)
	
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
	
	/** Retrieve the InPatient Number
	 * @return Returns the strInPatientNbr.
	 */
	public String getInPatientNbr() {
		return strInPatientNbr;
	}//end of getInPatientNbr()
	
	/** Sets the InPatient Number
	 * @param strInPatientNbr The strInPatientNbr to set.
	 */
	public void setInPatientNbr(String strInPatientNbr) {
		this.strInPatientNbr = strInPatientNbr;
	}//end of setInPatientNbr(String strInPatientNbr)
	
	/** Retrieve the ModeTypeID
	 * @return Returns the strModeTypeID.
	 */
	public String getModeTypeID() {
		return strModeTypeID;
	}//end of getModeTypeID()
	
	/** Sets the ModeTypeID
	 * @param strModeTypeID The strModeTypeID to set.
	 */
	public void setModeTypeID(String strModeTypeID) {
		this.strModeTypeID = strModeTypeID;
	}//end of setModeTypeID(String strModeTypeID)
	
	/** Retrieve the RequestTypeID
	 * @return Returns the strRequestTypeID.
	 */
	public String getRequestTypeID() {
		return strRequestTypeID;
	}//end of getRequestTypeID()
	
	/** Sets the RequestTypeID
	 * @param strRequestTypeID The strRequestTypeID to set.
	 */
	public void setRequestTypeID(String strRequestTypeID) {
		this.strRequestTypeID = strRequestTypeID;
	}//end of setRequestTypeID(String strRequestTypeID)
	
	/** Retrieve the Source Description
	 * @return Returns the strSourceDesc.
	 */
	public String getSourceDesc() {
		return strSourceDesc;
	}//end of getSourceDesc()
	
	/** Sets the Source Description
	 * @param strSourceDesc The strSourceDesc to set.
	 */
	public void setSourceDesc(String strSourceDesc) {
		this.strSourceDesc = strSourceDesc;
	}//end of setSourceDesc(String strSourceDesc)

	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	public Date getDischargeDate() {
		return dtDischargeDate;
	}//end of getDischargeDate()
	
	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	public String getClaimDischargeDate() {
		return TTKCommon.getFormattedDate(dtDischargeDate);
	}//end of getClaimDischargeDate()

	/** Sets the Discharge Date
	 * @param dtDischargeDate The dtDischargeDate to set.
	 */
	public void setDischargeDate(Date dtDischargeDate) {
		this.dtDischargeDate = dtDischargeDate;
	}//end of setDischargeDate(Date dtDischargeDate)

	/** Retrieve the Discharge Day
	 * @return Returns the strDischargeDay.
	 */
	public String getDischargeDay() {
		return strDischargeDay;
	}//end of getDischargeDay()

	/** Sets the Discharge Day
	 * @param strDischargeDay The strDischargeDay to set.
	 */
	public void setDischargeDay(String strDischargeDay) {
		this.strDischargeDay = strDischargeDay;
	}//end of setDischargeDay(String strDischargeDay)

	/** Retrieve the Discharge Time
	 * @return Returns the strDischargeTime.
	 */
	public String getDischargeTime() {
		return strDischargeTime;
	}//end of getDischargeTime()

	/** Sets the Discharge Time
	 * @param strDischargeTime The strDischargeTime to set.
	 */
	public void setDischargeTime(String strDischargeTime) {
		this.strDischargeTime = strDischargeTime;
	}//end of setDischargeTime(String strDischargeTime)

	/** Retrieve the ClaimReceivedDate
	 * @return Returns the dtClaimReceivedDate.
	 */
	public Date getClaimReceivedDate() {
		return dtClaimReceivedDate;
	}//end of getClaimReceivedDate()
	
	/** Retrieve the ClaimReceivedDate
	 * @return Returns the dtClaimReceivedDate.
	 */
	public String getClaimRcvdDate() {
		return TTKCommon.getFormattedDate(dtClaimReceivedDate);
	}//end of getClaimRcvdDate()

	/** Sets the ClaimReceivedDate
	 * @param dtClaimReceivedDate The dtClaimReceivedDate to set.
	 */
	public void setClaimReceivedDate(Date dtClaimReceivedDate) {
		this.dtClaimReceivedDate = dtClaimReceivedDate;
	}//end of setClaimReceivedDate(Date dtClaimReceivedDate)

	/** Retrieve the ClaimReceivedDay
	 * @return Returns the strClaimReceivedDay.
	 */
	public String getClaimReceivedDay() {
		return strClaimReceivedDay;
	}//end of getClaimReceivedDay()

	/** Sets the ClaimReceivedDay
	 * @param strClaimReceivedDay The strClaimReceivedDay to set.
	 */
	public void setClaimReceivedDay(String strClaimReceivedDay) {
		this.strClaimReceivedDay = strClaimReceivedDay;
	}//end of setClaimReceivedDay(String strClaimReceivedDay)

	/** Retrieve the ClaimReceivedTime
	 * @return Returns the strClaimReceivedTime.
	 */
	public String getClaimReceivedTime() {
		return strClaimReceivedTime;
	}//end of getClaimReceivedTime()

	/** Sets the ClaimReceivedTime
	 * @param strClaimReceivedTime The strClaimReceivedTime to set.
	 */
	public void setClaimReceivedTime(String strClaimReceivedTime) {
		this.strClaimReceivedTime = strClaimReceivedTime;
	}//end of setClaimReceivedTime(String strClaimReceivedTime)

	/** Retrieve the ClaimTypeDesc
	 * @return Returns the strClaimTypeDesc.
	 */
	public String getClaimTypeDesc() {
		return strClaimTypeDesc;
	}//end of getClaimTypeDesc()

	/** Sets the ClaimTypeDesc
	 * @param strClaimTypeDesc The strClaimTypeDesc to set.
	 */
	public void setClaimTypeDesc(String strClaimTypeDesc) {
		this.strClaimTypeDesc = strClaimTypeDesc;
	}//end of setClaimTypeDesc(String strClaimTypeDesc)

	/** Retrieve the ModeTypeDesc
	 * @return Returns the strModeTypeDesc.
	 */
	public String getModeTypeDesc() {
		return strModeTypeDesc;
	}//end of getModeTypeDesc()

	/** Sets the ModeTypeDesc
	 * @param strModeTypeDesc The strModeTypeDesc to set.
	 */
	public void setModeTypeDesc(String strModeTypeDesc) {
		this.strModeTypeDesc = strModeTypeDesc;
	}//end of setModeTypeDesc(String strModeTypeDesc)

	/** Retrieve the RequestTypeDesc
	 * @return Returns the strRequestTypeDesc.
	 */
	public String getRequestTypeDesc() {
		return strRequestTypeDesc;
	}//end of getRequestTypeDesc()

	/** Sets the RequestTypeDesc
	 * @param strRequestTypeDesc The strRequestTypeDesc to set.
	 */
	public void setRequestTypeDesc(String strRequestTypeDesc) {
		this.strRequestTypeDesc = strRequestTypeDesc;
	}//end of setRequestTypeDesc(String strRequestTypeDesc)

	/** Retrieve the ClaimRemarks
	 * @return Returns the strClaimRemarks.
	 */
	public String getClaimRemarks() {
		return strClaimRemarks;
	}//end of getClaimRemarks()

	/** Sets the ClaimRemarks
	 * @param strClaimRemarks The strClaimRemarks to set.
	 */
	public void setClaimRemarks(String strClaimRemarks) {
		this.strClaimRemarks = strClaimRemarks;
	}//end of setClaimRemarks(String strClaimRemarks)

	/** Retrieve the Reopen Type Description
	 * @return Returns the strReopenTypeDesc.
	 */
	public String getReopenTypeDesc() {
		return strReopenTypeDesc;
	}//end of getReopenTypeDesc()

	/** Sets the Reopen Type Description
	 * @param strReopenTypeDesc The strReopenTypeDesc to set.
	 */
	public void setReopenTypeDesc(String strReopenTypeDesc) {
		this.strReopenTypeDesc = strReopenTypeDesc;
	}//end of setReopenTypeDesc(String strReopenTypeDesc)

	/** Retrieve the Reopen Type ID
	 * @return Returns the strReopenTypeID.
	 */
	public String getReopenTypeID() {
		return strReopenTypeID;
	}//end of getReopenTypeID()

	/** Sets the Reopen Type ID
	 * @param strReopenTypeID The strReopenTypeID to set.
	 */
	public void setReopenTypeID(String strReopenTypeID) {
		this.strReopenTypeID = strReopenTypeID;
	}//end of setReopenTypeID(String strReopenTypeID)

	/** Retrieve the Doctor Regn Nbr
	 * @return Returns the strDoctorRegnNbr.
	 */
	public String getDoctorRegnNbr() {
		return strDoctorRegnNbr;
	}//end of getDoctorRegnNbr()

	/** Sets the Doctor Regn Nbr
	 * @param strDoctorRegnNbr The strDoctorRegnNbr to set.
	 */
	public void setDoctorRegnNbr(String strDoctorRegnNbr) {
		this.strDoctorRegnNbr = strDoctorRegnNbr;
	}//end of setDoctorRegnNbr(String strDoctorRegnNbr)
	
}//end of ClaimDetailVO
