/**
 * @ (#) AdditionalHospitalDetailVO.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : AdditionalHospitalDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import com.ttk.dto.preauth.PreAuthVO;

public class AdditionalHospitalDetailVO extends PreAuthVO{
	
	private String strRegnNbr = "";
	private Long lngAddHospDtlSeqID = null;
	private String strContactName = "";
	private String strPhoneNbr = "";
	private Integer intNbrOfBeds = null;
	private String strFullyEquippedYN = "";
	private Long lngHospAssocSeqID = null;
	private String strEditYN = "";
	
	/** Retrieve the Number Of Beds
	 * @return Returns the intNbrOfBeds.
	 */
	public Integer getNbrOfBeds() {
		return intNbrOfBeds;
	}//end of getNbrOfBeds()
	
	/** Sets the Number Of Beds
	 * @param intNbrOfBeds The intNbrOfBeds to set.
	 */
	public void setNbrOfBeds(Integer intNbrOfBeds) {
		this.intNbrOfBeds = intNbrOfBeds;
	}//end of setNbrOfBeds(Integer intNbrOfBeds)
	
	/** Retrieve the Additional Hospital Detail SeqID
	 * @return Returns the lngAddHospDtlSeqID.
	 */
	public Long getAddHospDtlSeqID() {
		return lngAddHospDtlSeqID;
	}//end of getAddHospDtlSeqID()
	
	/** Sets the Additional Hospital Detail SeqID
	 * @param lngAddHospDtlSeqID The lngAddHospDtlSeqID to set.
	 */
	public void setAddHospDtlSeqID(Long lngAddHospDtlSeqID) {
		this.lngAddHospDtlSeqID = lngAddHospDtlSeqID;
	}//end of setAddHospDtlSeqID(Long lngAddHospDtlSeqID)
	
	/** Retrieve the Hospital Associate SeqID
	 * @return Returns the lngHospAssocSeqID.
	 */
	public Long getHospAssocSeqID() {
		return lngHospAssocSeqID;
	}//end of getHospAssocSeqID()
	
	/** Sets the Hospital Associate SeqID
	 * @param lngHospAssocSeqID The lngHospAssocSeqID to set.
	 */
	public void setHospAssocSeqID(Long lngHospAssocSeqID) {
		this.lngHospAssocSeqID = lngHospAssocSeqID;
	}//end of setHospAssocSeqID(Long lngHospAssocSeqID)
	
	/** Retrieve the Contact Name
	 * @return Returns the strContactName.
	 */
	public String getContactName() {
		return strContactName;
	}//end of getContactName()
	
	/** Sets the Contact Name
	 * @param strContactName The strContactName to set.
	 */
	public void setContactName(String strContactName) {
		this.strContactName = strContactName;
	}//end of setContactName(String strContactName)
	
	/** Retrieve the Fully Equipped YN
	 * @return Returns the strFullyEquippedYN.
	 */
	public String getFullyEquippedYN() {
		return strFullyEquippedYN;
	}//end of getFullyEquippedYN()
	
	/** Sets the Fully Equipped YN
	 * @param strFullyEquippedYN The strFullyEquippedYN to set.
	 */
	public void setFullyEquippedYN(String strFullyEquippedYN) {
		this.strFullyEquippedYN = strFullyEquippedYN;
	}//end of setFullyEquippedYN(String strFullyEquippedYN)
	
	/** Retrieve the PhoneNbr
	 * @return Returns the strPhoneNbr.
	 */
	public String getPhoneNbr() {
		return strPhoneNbr;
	}//end of getPhoneNbr()
	
	/** Sets the PhoneNbr
	 * @param strPhoneNbr The strPhoneNbr to set.
	 */
	public void setPhoneNbr(String strPhoneNbr) {
		this.strPhoneNbr = strPhoneNbr;
	}//end of setPhoneNbr(String strPhoneNbr)
	
	/** Retrieve the Registration Number
	 * @return Returns the strRegnNbr.
	 */
	public String getRegnNbr() {
		return strRegnNbr;
	}//end of getRegnNbr()
	
	/** Sets the Registration Number
	 * @param strRegnNbr The strRegnNbr to set.
	 */
	public void setRegnNbr(String strRegnNbr) {
		this.strRegnNbr = strRegnNbr;
	}//end of setRegnNbr(String strRegnNbr)

	/** Retrieve the EditYN
	 * @return Returns the strEditYN.
	 */
	public String getEditYN() {
		return strEditYN;
	}//end of getEditYN()

	/** Sets the EditYN
	 * @param strEditYN The strEditYN to set.
	 */
	public void setEditYN(String strEditYN) {
		this.strEditYN = strEditYN;
	}//end of setEditYN(String strEditYN)
	
}//end of AdditionalHospitalDetailVO
