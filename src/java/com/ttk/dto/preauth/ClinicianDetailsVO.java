/** 
 *  @ (#)ClaimantVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : ClaimantVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */

package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;




public class ClinicianDetailsVO extends BaseVO{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */

private Long clinicianSeqId;
private String clinicianId;
private String clinicianName;

//for provider login
private String clinicianSpeciality;
private String clinicianConsultation;

public Long getClinicianSeqId() {
	return clinicianSeqId;
}
public void setClinicianSeqId(Long clinicianSeqId) {
	this.clinicianSeqId = clinicianSeqId;
}
public String getClinicianId() {
	return clinicianId;
}
public void setClinicianId(String clinicianId) {
	this.clinicianId = clinicianId;
}
public String getClinicianName() {
	return clinicianName;
}
public void setClinicianName(String clinicianName) {
	this.clinicianName = clinicianName;
}
/**
 * @return the clinicianSpeciality
 */
public String getClinicianSpeciality() {
	return clinicianSpeciality;
}
/**
 * @param clinicianSpeciality the clinicianSpeciality to set
 */
public void setClinicianSpeciality(String clinicianSpeciality) {
	this.clinicianSpeciality = clinicianSpeciality;
}
/**
 * @return the clinicianConsultation
 */
public String getClinicianConsultation() {
	return clinicianConsultation;
}
/**
 * @param clinicianConsultation the clinicianConsultation to set
 */
public void setClinicianConsultation(String clinicianConsultation) {
	this.clinicianConsultation = clinicianConsultation;
}
}//end of ClinicianDetailsVO.java