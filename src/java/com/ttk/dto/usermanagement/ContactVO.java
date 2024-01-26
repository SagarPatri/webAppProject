/**
 * @ (#) ContactVO.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : ContactVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.usermanagement;

import com.ttk.dto.BaseVO;

public class ContactVO extends BaseVO {
	
	private PersonalInfoVO personalInfo = null;
	private UserAccessVO userAccessInfo = null;
	private AdditionalInfoVO additionalInfo = null;
	private Long lngContactSeqID = null;
	private Long lngHospitalSeqID = null;
	private Long lngPartnerSeqID = null;
	private Long lngInsuranceSeqID = null;
	private Long lngGroupRegSeqID = null;
	private Long lngBankSeqID = null;
	private Long intOfficeSeqID = null;
	
	//added for broker login kocb
	private Long lngBrokerSeqID = null;
	
	
	public Long getPartnerSeqID() {
		return lngPartnerSeqID;
	}// End of getHospitalSeqID()

	
	public void setPartnerSeqID(Long lngPartnerSeqID) {
		this.lngPartnerSeqID = lngPartnerSeqID;
	}// End of setHospitalSeqID(Long lngHospitalSeqID)

	
	public Long getBrokerSeqID() {
		return lngBrokerSeqID;
	}

	public void setBrokerSeqID(Long lngBrokerSeqID) {
		this.lngBrokerSeqID = lngBrokerSeqID;
	}
	
	
	/** This method returns the Office Sequence ID
	 * @return Returns the intOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return intOfficeSeqID;
	}// End of getOfficeSeqID()
	
	/** This method sets the Office Sequence ID 
	 * @param intBranchID The intOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long intOfficeSeqID) {
		this.intOfficeSeqID = intOfficeSeqID;
	}// End of setOfficeSeqID(Integer intOfficeSeqID)

	//added for broker login kocb
	/** Retrieve the Bank Seq ID
	 * @return Returns the lngBankSeqID.
	 */
	public Long getBankSeqID() {
		return lngBankSeqID;
	}//end of getBankSeqID()

	/** Sets the Bank Seq ID
	 * @param lngBankSeqID The lngBankSeqID to set.
	 */
	public void setBankSeqID(Long lngBankSeqID) {
		this.lngBankSeqID = lngBankSeqID;
	}//end of setBankSeqID(Long lngBankSeqID)

	/** This method returns the Additional Information
	 * @return Returns the additionalInfo.
	 */
	public AdditionalInfoVO getAdditionalInfo() {
		return additionalInfo;
	}// End of AdditionalInfoVO getAdditionalInfo(
	
	/** This method sets Additional Information
	 * @param additionalInfo The additionalInfo to set.
	 */
	public void setAdditionalInfo(AdditionalInfoVO additionalInfo) {
		this.additionalInfo = additionalInfo;
	}// End of setAdditionalInfo(AdditionalInfoVO additionalInfo)
	
	/** This method returns the Contact Sequence ID
	 * @return Returns the lngContactSeqID.
	 */
	public Long getContactSeqID() {
		return lngContactSeqID;
	}// End of getContactSeqID()
	
	/** This method sets the Contact Sequence Id
	 * @param lngContactSeqID The lngContactSeqID to set.
	 */
	public void setContactSeqID(Long lngContactSeqID) {
		this.lngContactSeqID = lngContactSeqID;
	}// End of setContactSeqID(Long lngContactSeqID)
	
	/** This method returns the Personal Information
	 * @return Returns the personalInfo.
	 */
	public PersonalInfoVO getPersonalInfo() {
		return personalInfo;
	}// End of PersonalInfoVO getPersonalInfo()
	
	/** This method sets the Personal Inforamtion 
	 * @param personalInfo The personalInfo to set.
	 */
	public void setPersonalInfo(PersonalInfoVO personalInfo) {
		this.personalInfo = personalInfo;
	}// End of setPersonalInfo(PersonalInfoVO personalInfo)
	
	/** This method returns the User Access Information
	 * @return Returns the userAccessInfo.
	 */
	public UserAccessVO getUserAccessInfo() {
		return userAccessInfo;
	}// End of getUserAccessInfo()
	
	/** This method sets the User Access Information
	 * @param userAccessInfo The userAccessInfo to set.
	 */
	public void setUserAccessInfo(UserAccessVO userAccessInfo) {
		this.userAccessInfo = userAccessInfo;
	}// End of setUserAccessInfo(UserAccessVO userAccessInfo)

	/** This method returns the Hospital Sequence ID
	 * @return Returns the lngHospitalSeqID.
	 */
	public Long getHospitalSeqID() {
		return lngHospitalSeqID;
	}// End of getHospitalSeqID()

	/** This method sets the Hospital Sequence ID
	 * @param lngHospitalSeqID The lngHospitalSeqID to set.
	 */
	public void setHospitalSeqID(Long lngHospitalSeqID) {
		this.lngHospitalSeqID = lngHospitalSeqID;
	}// End of setHospitalSeqID(Long lngHospitalSeqID)

	/** This method returns the Insurance Sequence ID 
	 * @return Returns the lngInsuranceSeqID.
	 */
	public Long getInsuranceSeqID() {
		return lngInsuranceSeqID;
	}// End of getInsuranceSeqID()

	/** This method sets the Insurance Sequence ID
	 * @param lngInsuranceSeqID The lngInsuranceSeqID to set.
	 */
	public void setInsuranceSeqID(Long lngInsuranceSeqID) {
		this.lngInsuranceSeqID = lngInsuranceSeqID;
	}// End of setInsuranceSeqID(Long lngInsuranceSeqID)

	/** This method returns the Group Registration Sequence ID 
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}// End of getGroupRegSeqID()

	/** This method sets the Group Registration Sequence ID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}// End of setGroupRegSeqID(Long lngGroupRegSeqID)
	
}// End of  ContactVO
