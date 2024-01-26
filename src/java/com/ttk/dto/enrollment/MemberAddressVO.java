/**
 * @ (#) MemberAddressVO.java Feb 03, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MemberAddressVO.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Feb 03, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.enrollment;

import com.ttk.dto.empanelment.AddressVO;


public class MemberAddressVO extends AddressVO {

    private Long lngMemberSeqID  =null;
    private String strEmailID ="";
    private String strPhoneNbr1 ="";
    private String strPhoneNbr2 ="";
    private String strHomePhoneNbr ="";
    private String strMobileNbr ="";
    private String strFaxNbr ="";
    private String strEmailID2="";//added for EmailID2-KOC-1216
    private String stateDesc;
    private String contactNumber;
    private String residentialLocation;
    private String workLocation;
    
     public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
     public String getWorkLocation() {
		return workLocation;
	}

     public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
     public String getContactNumber() {
		return contactNumber;
	}
     public void setResidentialLocation(String residentialLocation) {
		this.residentialLocation = residentialLocation;
	}
     public String getResidentialLocation() {
		return residentialLocation;
	}
	public String getEmailID2() {
		return strEmailID2;
	    }

	public void setEmailID2(String strEmailID2) {
		this.strEmailID2 = strEmailID2;
	    }

    /** This method returns the Member Sequence ID
     * @return Returns the lngMemberSeqID.
     */
    public Long getMemberSeqID() {
        return lngMemberSeqID;
    }//end of getMemberSeqID()

    /** This method sets the Member Sequence ID
     * @param lngMemberSeqID The Member Sequence ID to set.
     */
    public void setMemberSeqID(Long lngMemberSeqID) {
        this.lngMemberSeqID = lngMemberSeqID;
    }//end of setMemberSeqID(Long lngMemberSeqID)

    /** This method returns the Email ID
     * @return Returns the strEmailID.
     */
    public String getEmailID() {
        return strEmailID;
    }//end of getEmailID()

    /** This method sets the Email ID
     * @param strEmailID The Email ID to set.
     */
    public void setEmailID(String strEmailID) {
        this.strEmailID = strEmailID;
    }//end of setEmailID(String strEmailID)

    /** This method returns the Phone Number 1
     * @return Returns the strPhoneNbr1.
     */
    public String getPhoneNbr1() {
        return strPhoneNbr1;
    }//end of getPhoneNbr1()

    /** This method sets the Phone Number 1
     * @param strPhoneNbr1 The Phone Number 1 to set.
     */
    public void setPhoneNbr1(String strPhoneNbr1) {
        this.strPhoneNbr1 = strPhoneNbr1;
    }//end of setPhoneNbr1(String strPhoneNbr1)

    /** This method returns the Phone Number 2
     * @return Returns the strPhoneNbr2.
     */
    public String getPhoneNbr2() {
        return strPhoneNbr2;
    }//end of getPhoneNbr2()

    /** This method sets the Phone Number 2
     * @param strPhoneNbr2 The Phone Number 2 to set.
     */
    public void setPhoneNbr2(String strPhoneNbr2) {
        this.strPhoneNbr2 = strPhoneNbr2;
    }//end of setPhoneNbr2(String strPhoneNbr2)

     /** This method returns the Home Phone Number
     * @return Returns the strHomePhoneNbr.
     */
    public String getHomePhoneNbr() {
        return strHomePhoneNbr;
    }//end of getHomePhoneNbr()

    /** This method sets the Home Phone Number
     * @param strHomePhoneNbr The Home Phone Number to set.
     */
    public void setHomePhoneNbr(String strHomePhoneNbr) {
        this.strHomePhoneNbr = strHomePhoneNbr;
    }//end of setHomePhoneNbr(String strHomePhoneNbr)

    /** This method returns the Mobile Number
     * @return Returns the strMobileNbr.
     */
    public String getMobileNbr() {
        return strMobileNbr;
    }//end of getMobileNbr()

    /** This method sets the Mobile Number
     * @param strMobileNbr The Mobile Number to set.
     */
    public void setMobileNbr(String strMobileNbr) {
        this.strMobileNbr = strMobileNbr;
    }//end of setMobileNbr(String strMobileNbr)

    /** This method returns the Fax Number
     * @return Returns the strFaxNbr.
     */
    public String getFaxNbr() {
        return strFaxNbr;
    }//end of String getFaxNbr()

    /** This method sets the Fax Number
     * @param strFaxNbr The Fax Number to set.
     */
    public void setFaxNbr(String strFaxNbr) {
        this.strFaxNbr = strFaxNbr;
    }//end of setFaxNbr(String strFaxNbr)

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

 }//end of MemberAddressVO
