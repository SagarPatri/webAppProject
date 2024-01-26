/**
 * @ (#) OnlineIntimationVO.java Mar 12, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineIntimationVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 12, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class OnlineIntimationVO extends BaseVO{
	
	private Long lngIntimationSeqID = null;
	private String strIntimationNbr = "";
	private String strEnrollmentID = "";
	private Long lngPolicyGrpSeqID = null;
	private Long lngMemberSeqID = null;
	private String strMemName = "";
	private String strGenderDesc = "";
	private Date dtIntGenDate = null;
	private String strIntGenTime="";
    private String strIntGenDay="";
	private Date dtExpectedDOA = null;
	private Date dtExpectedDOD = null;
	private String strAilmentDesc = "";
	private String strProvisionalDiagnosis = "";
	private String strPhoneNbr = "";
	private String strMobileNbr = "";
	private String strEmailID = "";
	private String strRemarks = "";
	private String strTTKRemarks = "";
	private Date dtTTKRespondedDate = null; 
	private String strTTKRespondedTime="";
    private String strTTKRespondedDay="";
	private String strSubmittedYN = "";
	private OnlineHospitalVO onlineHospitalVO = null;
	private String strPolicyNbr = "";
	private String strStatusTypeID = "";
	private String strStatusDesc = "";
	private String strHospitalName=""; //Hospital name
	private String strHospAddress="";// Address
	private BigDecimal bdEstimatedCost = null;
	private String strTTKNarrative = "";//TPA_NARRATIVE
	
	/** Retrieve the TTKNarrative
	 * @return Returns the strTTKNarrative.
	 */
	public String getTTKNarrative() {
		return strTTKNarrative;
	}//end of getTTKNarrative()

	/** Sets the TTKNarrative
	 * @param strTTKNarrative The strTTKNarrative to set.
	 */
	public void setTTKNarrative(String strTTKNarrative) {
		this.strTTKNarrative = strTTKNarrative;
	}//end of setTTKNarrative(String strTTKNarrative)

	/** Retrieve the EstimatedCost
	 * @return Returns the bdEstimatedCost.
	 */
	public BigDecimal getEstimatedCost() {
		return bdEstimatedCost;
	}//end of getEstimatedCost()

	/** Sets the EstimatedCost
	 * @param bdEstimatedCost The bdEstimatedCost to set.
	 */
	public void setEstimatedCost(BigDecimal bdEstimatedCost) {
		this.bdEstimatedCost = bdEstimatedCost;
	}//end of setEstimatedCost(BigDecimal bdEstimatedCost)

	/** Retrieve the HospAddress
	 * @return Returns the strHospAddress.
	 */
	public String getHospAddress() {
		return strHospAddress;
	}//end of getHospAddress()

	/** Sets the HospAddress
	 * @param strHospAddress The strHospAddress to set.
	 */
	public void setHospAddress(String strHospAddress) {
		this.strHospAddress = strHospAddress;
	}//end of setHospAddress(String strHospAddress)

	/** Retrieve the HospitalName
	 * @return Returns the strHospitalName.
	 */
	public String getHospitalName() {
		return strHospitalName;
	}//end of getHospitalName()

	/** Sets the HospitalName
	 * @param strHospitalName The strHospitalName to set.
	 */
	public void setHospitalName(String strHospitalName) {
		this.strHospitalName = strHospitalName;
	}//end of setHospitalName(String strHospitalName)

	/** Retrieve the IntGenDay
	 * @return Returns the strIntGenDay.
	 */
	public String getIntGenDay() {
		return strIntGenDay;
	}//end of getIntGenDay()

	/** Sets the IntGenDay
	 * @param strIntGenDay The strIntGenDay to set.
	 */
	public void setIntGenDay(String strIntGenDay) {
		this.strIntGenDay = strIntGenDay;
	}//end of setIntGenDay(String strIntGenDay)

	/** Retrieve the IntGenTime
	 * @return Returns the strIntGenTime.
	 */
	public String getIntGenTime() {
		return strIntGenTime;
	}//end of getIntGenTime()

	/** Sets the IntGenTime
	 * @param strIntGenTime The strIntGenTime to set.
	 */
	public void setIntGenTime(String strIntGenTime) {
		this.strIntGenTime = strIntGenTime;
	}//end of setIntGenTime(String strIntGenTime)

	/** Retrieve the TTKRespondedDay
	 * @return Returns the strTTKRespondedDay.
	 */
	public String getTTKRespondedDay() {
		return strTTKRespondedDay;
	}//end of getTTKRespondedDay()

	/** Sets the TTKRespondedDay
	 * @param strTTKRespondedDay The strTTKRespondedDay to set.
	 */
	public void setTTKRespondedDay(String strTTKRespondedDay) {
		this.strTTKRespondedDay = strTTKRespondedDay;
	}//end of setTTKRespondedDay(String strTTKRespondedDay)

	/** Retrieve the TTKRespondedTime
	 * @return Returns the strTTKRespondedTime.
	 */
	public String getTTKRespondedTime() {
		return strTTKRespondedTime;
	}//end of getTTKRespondedTime()

	/** Sets the TTKRespondedTime
	 * @param strTTKRespondedTime The strTTKRespondedTime to set.
	 */
	public void setTTKRespondedTime(String strTTKRespondedTime) {
		this.strTTKRespondedTime = strTTKRespondedTime;
	}//end of setTTKRespondedTime(String strTTKRespondedTime)

	/** Retrieve the StatusTypeID
	 * @return Returns the strStatusTypeID.
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()

	/** Sets the StatusTypeID
	 * @param strStatusTypeID The strStatusTypeID to set.
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)

	/** Retrieve the PolicyNbr
	 * @return Returns the strPolicyNbr.
	 */
	public String getPolicyNbr() {
		return strPolicyNbr;
	}//end of getPolicyNbr()

	/** Sets the PolicyNbr
	 * @param strPolicyNbr The strPolicyNbr to set.
	 */
	public void setPolicyNbr(String strPolicyNbr) {
		this.strPolicyNbr = strPolicyNbr;
	}//end of setPolicyNbr(String strPolicyNbr)

	/** Retrieve the StatusDesc
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/** Sets the StatusDesc
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/** Retrieve the TTKRespondedDate
	 * @return Returns the dtTTKRespondedDate.
	 */
	public Date getTTKRespondedDate() {
		return dtTTKRespondedDate;
	}//end of getTTKRespondedDate()
	
	/** Retrieve the TTKRespondedDate
	 * @return Returns the dtTTKRespondedDate.
	 */
	public String getTTKRespondDate() {
		return TTKCommon.getFormattedDateHour(dtTTKRespondedDate);
	}//end of getTTKRespondDate()

	/** Sets the TTKRespondedDate
	 * @param dtTTKRespondedDate The dtTTKRespondedDate to set.
	 */
	public void setTTKRespondedDate(Date dtTTKRespondedDate) {
		this.dtTTKRespondedDate = dtTTKRespondedDate;
	}//end of setTTKRespondedDate(Date dtTTKRespondedDate)

	/** Retrieve the TTKRemarks
	 * @return Returns the strTTKRemarks.
	 */
	public String getTTKRemarks() {
		return strTTKRemarks;
	}//end of getTTKRemarks()

	/** Sets the TTKRemarks
	 * @param strTTKRemarks The strTTKRemarks to set.
	 */
	public void setTTKRemarks(String strTTKRemarks) {
		this.strTTKRemarks = strTTKRemarks;
	}//end of setTTKRemarks(String strTTKRemarks)

	/** Retrieve the onlineHospitalVO
	 * @return Returns the onlineHospitalVO.
	 */
	public OnlineHospitalVO getOnlineHospitalVO() {
		return onlineHospitalVO;
	}//end of getOnlineHospitalVO()

	/** Sets the onlineHospitalVO
	 * @param onlineHospitalVO The onlineHospitalVO to set.
	 */
	public void setOnlineHospitalVO(OnlineHospitalVO onlineHospitalVO) {
		this.onlineHospitalVO = onlineHospitalVO;
	}//end of setOnlineHospitalVO(OnlineHospitalVO onlineHospitalVO)

	/** Retrieve the Expected DOA
	 * @return Returns the dtExpectedDOA.
	 */
	public Date getExpectedDOA() {
		return dtExpectedDOA;
	}//end of getExpectedDOA
	
	/** Sets the Expected DOA
	 * @param dtExpectedDOA The dtExpectedDOA to set.
	 */
	public void setExpectedDOA(Date dtExpectedDOA) {
		this.dtExpectedDOA = dtExpectedDOA;
	}//end of setExpectedDOA(Date dtExpectedDOA)
	
	/** Retrieve the Expected DOD
	 * @return Returns the dtExpectedDOD.
	 */
	public Date getExpectedDOD() {
		return dtExpectedDOD;
	}//end of getExpectedDOD()
	
	/** Sets the Expected DOD
	 * @param dtExpectedDOD The dtExpectedDOD to set.
	 */
	public void setExpectedDOD(Date dtExpectedDOD) {
		this.dtExpectedDOD = dtExpectedDOD;
	}//end of setExpectedDOD(Date dtExpectedDOD) 
	
	/** This method returns the Intimate Generated Date
     * @return Returns the dtIntGenDate.
     */
    public String getIntimationDate() {
        return TTKCommon.getFormattedDateHour(dtIntGenDate);
    }//end of getIntimationDate()
	
	/** Retrieve the Intimate Generated Date
	 * @return Returns the dtIntGenDate.
	 */
	public Date getIntGenDate() {
		return dtIntGenDate;
	}//end of getIntGenDate() 
	
	/** Sets the Intimate Generated Date
	 * @param dtIntGenDate The dtIntGenDate to set.
	 */
	public void setIntGenDate(Date dtIntGenDate) {
		this.dtIntGenDate = dtIntGenDate;
	}//end of setIntGenDate(Date dtIntGenDate)
	
	/** Retrieve the Intimation SeqID.
	 * @return Returns the lngIntimationSeqID.
	 */
	public Long getIntimationSeqID() {
		return lngIntimationSeqID;
	}//end of getIntimationSeqID()
	
	/** Sets the Intimation SeqID.
	 * @param lngIntimationSeqID The lngIntimationSeqID to set.
	 */
	public void setIntimationSeqID(Long lngIntimationSeqID) {
		this.lngIntimationSeqID = lngIntimationSeqID;
	}//end of setIntimationSeqID(Long lngIntimationSeqID)
	
	/** Retrieve the Member SeqID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID() 
	
	/** Sets the Member SeqID
	 * @param lngMemberSeqID The lngMemberSeqID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
	/** Retrieve the Policy Group SeqID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID() 
	
	/** Sets the Policy Group SeqID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID) 
	
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
	
	/** Retrieve the Email ID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()
	
	/** Sets the Email ID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)
	
	/** Retrieve the Enrollment ID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID() 
	
	/** Sets the Enrollment ID
	 * @param strEnrollmentID The strEnrollmentID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID) 
	
	/** Retrieve the Gender Description
	 * @return Returns the strGenderDesc.
	 */
	public String getGenderDesc() {
		return strGenderDesc;
	}//end of getGenderDesc() 
	
	/** Sets the Gender Description
	 * @param strGenderDesc The strGenderDesc to set.
	 */
	public void setGenderDesc(String strGenderDesc) {
		this.strGenderDesc = strGenderDesc;
	}//end of setGenderDesc(String strGenderDesc)
	
	/** Retrieve the Intimation Number
	 * @return Returns the strIntimationNbr.
	 */
	public String getIntimationNbr() {
		return strIntimationNbr;
	}//end of getIntimationNbr()
	
	/** Sets the Intimation Number
	 * @param strIntimationNbr The strIntimationNbr to set.
	 */
	public void setIntimationNbr(String strIntimationNbr) {
		this.strIntimationNbr = strIntimationNbr;
	}//end of setIntimationNbr(String strIntimationNbr) 
	
	/** Retrieve the Member Name
	 * @return Returns the strMemName.
	 */
	public String getMemName() {
		return strMemName;
	}//end of getMemName
	
	/** Sets the Member Name
	 * @param strMemName The strMemName to set.
	 */
	public void setMemName(String strMemName) {
		this.strMemName = strMemName;
	}//end of setMemName(String strMemName) 
	
	/** Retrieve the Mobile Number
	 * @return Returns the strMobileNbr.
	 */
	public String getMobileNbr() {
		return strMobileNbr;
	}//end of getMobileNbr()
	
	/** Sets the Mobile Number
	 * @param strMobileNbr The strMobileNbr to set.
	 */
	public void setMobileNbr(String strMobileNbr) {
		this.strMobileNbr = strMobileNbr;
	}//end of setMobileNbr(String strMobileNbr) 
	
	/** Retrieve the Phone Number
	 * @return Returns the strPhoneNbr.
	 */
	public String getPhoneNbr() {
		return strPhoneNbr;
	}//end of getPhoneNbr()
	
	/** Sets the Phone Number
	 * @param strPhoneNbr The strPhoneNbr to set.
	 */
	public void setPhoneNbr(String strPhoneNbr) {
		this.strPhoneNbr = strPhoneNbr;
	}//end of setPhoneNbr(String strPhoneNbr)
	
	/** Retrieve the Provisional Diagnosis
	 * @return Returns the strProvisionalDiagnosis.
	 */
	public String getProvisionalDiagnosis() {
		return strProvisionalDiagnosis;
	}//end of getProvisionalDiagnosis()
	
	/** Sets the Provisional Diagnosis
	 * @param strProvisionalDiagnosis The strProvisionalDiagnosis to set.
	 */
	public void setProvisionalDiagnosis(String strProvisionalDiagnosis) {
		this.strProvisionalDiagnosis = strProvisionalDiagnosis;
	}//end of setProvisionalDiagnosis(String strProvisionalDiagnosis)
	
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
	
	/** Retrieve the Submitted YN.
	 * @return Returns the strSubmittedYN.
	 */
	public String getSubmittedYN() {
		return strSubmittedYN;
	}//end of getSubmittedYN()
	
	/** Sets the Submitted YN.
	 * @param strSubmittedYN The strSubmittedYN to set.
	 */
	public void setSubmittedYN(String strSubmittedYN) {
		this.strSubmittedYN = strSubmittedYN;
	}//end of setSubmittedYN(String strSubmittedYN)	

}//end of OnlineIntimationVO
