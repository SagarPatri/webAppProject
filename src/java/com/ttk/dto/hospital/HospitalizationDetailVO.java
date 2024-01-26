/**
 * @ (#) HospitalizationDetailVO.java Jul 31, 2006
 * Project 	     : TTK HealthCare Services
 * File          : HospitalizationDetailVO.java
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

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;
import com.ttk.dto.hospital.PreAuthHospitalVO;

public class HospitalizationDetailVO extends BaseVO{
	
	private Long lngPrevHospClaimSeqID = null;
	private Date dtAdmissionDate = null;
	private String strAdmissionTime = "";
	private String strAdmissionDay = "";
	private String strPrevHospDesc = "";
	private Date dtDischargeDate = null;
	private String strDischargeTime = "";
	private String strDischargeDay = "";
	private PreAuthHospitalVO preauthHospitalVO = null;
	
	/** Retrieve the AdmissionDate
	 * @return Returns the dtAdmissionDate.
	 */
	public Date getAdmissionDate() {
		return dtAdmissionDate;
	}//end of getAdmissionDate()
	
	/** Retrieve the AdmissionDate
	 * @return Returns the dtAdmissionDate.
	 */
	public String getHospAdmissionDate() {
		return TTKCommon.getFormattedDate(dtAdmissionDate);
	}//end of getHospAdmissionDate()
	
	/** Sets the AdmissionDate
	 * @param dtAdmissionDate The dtAdmissionDate to set.
	 */
	public void setAdmissionDate(Date dtAdmissionDate) {
		this.dtAdmissionDate = dtAdmissionDate;
	}//end of setAdmissionDate(Date dtAdmissionDate)
	
	/** Retrieve the DischargeDate
	 * @return Returns the dtDischargeDate.
	 */
	public Date getDischargeDate() {
		return dtDischargeDate;
	}//end of getDischargeDate()
	
	/** Retrieve the DischargeDate
	 * @return Returns the dtDischargeDate.
	 */
	public String getHospDischargeDate() {
		return TTKCommon.getFormattedDate(dtDischargeDate);
	}//end of getHospDischargeDate()
	
	/** Sets the DischargeDate
	 * @param dtDischargeDate The dtDischargeDate to set.
	 */
	public void setDischargeDate(Date dtDischargeDate) {
		this.dtDischargeDate = dtDischargeDate;
	}//end of setDischargeDate(Date dtDischargeDate)
	
	/** Retrieve the PrevHospClaimSeqID
	 * @return Returns the lngPrevHospClaimSeqID.
	 */
	public Long getPrevHospClaimSeqID() {
		return lngPrevHospClaimSeqID;
	}//end of getPrevHospClaimSeqID()
	
	/** Sets the PrevHospClaimSeqID
	 * @param lngPrevHospClaimSeqID The lngPrevHospClaimSeqID to set.
	 */
	public void setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID) {
		this.lngPrevHospClaimSeqID = lngPrevHospClaimSeqID;
	}//end of setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID)
	
	/** Retrieve the PreAuthHospitalVO
	 * @return Returns the preauthHospitalVO.
	 */
	public PreAuthHospitalVO getPreauthHospitalVO() {
		return preauthHospitalVO;
	}//end of getPreauthHospitalVO()
	
	/** Sets the PreAuthHospitalVO
	 * @param preauthHospitalVO The preauthHospitalVO to set.
	 */
	public void setPreauthHospitalVO(PreAuthHospitalVO preauthHospitalVO) {
		this.preauthHospitalVO = preauthHospitalVO;
	}//end of setPreauthHospitalVO(PreAuthHospitalVO preauthHospitalVO)
	
	/** Retrieve the AdmissionDay
	 * @return Returns the strAdmissionDay.
	 */
	public String getAdmissionDay() {
		return strAdmissionDay;
	}//end of getAdmissionDay()
	
	/** Sets the AdmissionDay
	 * @param strAdmissionDay The strAdmissionDay to set.
	 */
	public void setAdmissionDay(String strAdmissionDay) {
		this.strAdmissionDay = strAdmissionDay;
	}//end of setAdmissionDay(String strAdmissionDay)
	
	/** Retrieve the AdmissionTime
	 * @return Returns the strAdmissionTime.
	 */
	public String getAdmissionTime() {
		return strAdmissionTime;
	}//end of getAdmissionTime()
	
	/** Sets the AdmissionTime
	 * @param strAdmissionTime The strAdmissionTime to set.
	 */
	public void setAdmissionTime(String strAdmissionTime) {
		this.strAdmissionTime = strAdmissionTime;
	}//end of setAdmissionTime(String strAdmissionTime)
	
	/** Retrieve the DischargeDay
	 * @return Returns the strDischargeDay.
	 */
	public String getDischargeDay() {
		return strDischargeDay;
	}//end of getDischargeDay()
	
	/** Sets the DischargeDay
	 * @param strDischargeDay The strDischargeDay to set.
	 */
	public void setDischargeDay(String strDischargeDay) {
		this.strDischargeDay = strDischargeDay;
	}//end of setDischargeDay(String strDischargeDay)
	
	/** Retrieve the DischargeTime
	 * @return Returns the strDischargeTime.
	 */
	public String getDischargeTime() {
		return strDischargeTime;
	}//end of getDischargeTime()
	
	/** Sets the DischargeTime
	 * @param strDischargeTime The strDischargeTime to set.
	 */
	public void setDischargeTime(String strDischargeTime) {
		this.strDischargeTime = strDischargeTime;
	}//end of setDischargeTime(String strDischargeTime)
	
	/** Retrieve the PrevHospDesc
	 * @return Returns the strPrevHospDesc.
	 */
	public String getPrevHospDesc() {
		return strPrevHospDesc;
	}//end of getPrevHospDesc()
	
	/** Sets the PrevHospDesc
	 * @param strPrevHospDesc The strPrevHospDesc to set.
	 */
	public void setPrevHospDesc(String strPrevHospDesc) {
		this.strPrevHospDesc = strPrevHospDesc;
	}//end of setPrevHospDesc(String strPrevHospDesc)
	
}//end of HospitalizationDetailVO
