/**
 *   @ (#) BankAddressVO.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankAddressVO.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 07, 2006
 *
 *   @author       :  Balakrishna E
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dto.finance;

import com.ttk.dto.empanelment.AddressVO;

/**
 * This VO contains bank address information.
 */

public class BankAddressVO extends AddressVO{

	private String strContactPerson;
	private String strOffPhone1;
	private String strOffPhone2;
	private String strEmailID;
	private String strFax;
	private String strMobile;
	private String strHomePhone;


    /** Retrieve the Home Phone
     * @return Returns the strHomePhone.
     */
    public String getHomePhone() {
        return strHomePhone;
    }//end of getHomePhone()

    /** Sets the Home Phone
     * @param strHomePhone The strHomePhone to set.
     */
    public void setHomePhone(String strHomePhone) {
        this.strHomePhone = strHomePhone;
    }//end of setHomePhone(String strHomePhone)

	/** Retrieve the Contact Person
	 * @return Returns the strContactPerson.
	 */
	public String getContactPerson() {
		return strContactPerson;
	}//end of getContactPerson()

	/** Sets the Contact Person
	 * @param strContactPerson The strContactPerson to set.
	 */
	public void setContactPerson(String strContactPerson) {
		this.strContactPerson = strContactPerson;
	}//end of setContactPerson(String strContactPerson)

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

	/** Retrieve the Fax
	 * @return Returns the strFax.
	 */
	public String getFax() {
		return strFax;
	}//end of getFax()

	/** Sets the Fax
	 * @param strFax The strFax to set.
	 */
	public void setFax(String strFax) {
		this.strFax = strFax;
	}//end of setFax(String strFax)

	/** Retrieve the Mobile
	 * @return Returns the strMobile.
	 */
	public String getMobile() {
		return strMobile;
	}//end of getMobile()

	/** Sets the Mobile
	 * @param strMobile The strMobile to set.
	 */
	public void setMobile(String strMobile) {
		this.strMobile = strMobile;
	}// end of setMobile(String strMobile)

	/** Retrieve the Office Phone1
	 * @return Returns the strOffPhone1.
	 */
	public String getOffPhone1() {
		return strOffPhone1;
	}//end of getOffPhone1()

	/** Sets the Office Phone1
	 * @param strOffPhone1 The strOffPhone1 to set.
	 */
	public void setOffPhone1(String strOffPhone1) {
		this.strOffPhone1 = strOffPhone1;
	}//end of setOffPhone1(String strOffPhone1)

	/** Retrieve the Office Phone2
	 * @return Returns the strOffPhone2.
	 */
	public String getOffPhone2() {
		return strOffPhone2;
	}//end of getOffPhone2()

	/** Sets the Office Phone2
	 * @param strOffPhone2 The strOffPhone2 to set.
	 */
	public void setOffPhone2(String strOffPhone2) {
		this.strOffPhone2 = strOffPhone2;
	}//end of setOffPhone2(String strOffPhone2)

}//end of BankAddressVO