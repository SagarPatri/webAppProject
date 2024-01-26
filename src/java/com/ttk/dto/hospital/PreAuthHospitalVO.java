/**
* @ (#) PreAuthHospitalVO.java
* Project 		: TTK HealthCare Services
* File 			: PreAuthHospitalVO.java
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

import com.ttk.dto.empanelment.HospitalVO;

public class PreAuthHospitalVO extends HospitalVO {
	private String strRating = "";
	private String strRatingImageName = "";
	private String strRatingImageTitle = "";
	private String strAddress1="";// Address1
    private String strAddress2="";// Address2
    private String strAddress3 = "";
    private String strStateName = "";
    private String strCountryName = "";
    private String strPincode = "";
    private String strEmailID ="";
    private String strPhoneNbr1 ="";
    private String strPhoneNbr2 ="";
    private String strMobileNbr ="";
    private String strFaxNbr ="";
    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private String strHospRemarks = "";
    private String strHospStatus = "";
    private String strEmpanelStatusTypeID = "";
    private String strStatusDisYN = "";//for discrepancy for status.
    private String strHospServiceTaxRegnNbr="";
    
    
   

	/** Retrieve the Hospital Status discrepancy
     * @return Returns the strStatusDisYN.
     */
    public String getStatusDisYN() {
        return strStatusDisYN;
    }//end of getStatusDisYN()
    
    /** Sets the Hospital Status discrepancy
     * @param strStatusDisYN The strStatusDisYN to set.
     */
    public void setStatusDisYN(String strStatusDisYN) {
        this.strStatusDisYN = strStatusDisYN;
    }//end of setStatusDisYN(String strStatusDisYN)
    
    /** Retrieve the Empanel Status
	 * @return Returns the strEmpanelStatusTypeID.
	 */
	public String getEmpanelStatusTypeID() {
		return strEmpanelStatusTypeID;
	}//end of getEmpanelStatusTypeID()

	/** Sets the Empanel Status
	 * @param strEmpanelStatus The strEmpanelStatus to set.
	 */
	public void setEmpanelStatusTypeID(String strEmpanelStatusTypeID) {
		this.strEmpanelStatusTypeID = strEmpanelStatusTypeID;
	}//end of setEmpanelStatusTypeID(String strEmpanelStatusTypeID)

	/** Retrieve the Address3
	 * @return Returns the strAddress3.
	 */
	public String getAddress3() {
		return strAddress3;
	}//end of getAddress3()

	/** Sets the Address3
	 * @param strAddress3 The strAddress3 to set.
	 */
	public void setAddress3(String strAddress3) {
		this.strAddress3 = strAddress3;
	}//end of setAddress3(String strAddress3)

	/** Retrieve the Pincode
	 * @return Returns the strPincode.
	 */
	public String getPincode() {
		return strPincode;
	}//end of getPincode()

	/** Sets the Pincode
	 * @param strPincode The strPincode to set.
	 */
	public void setPincode(String strPincode) {
		this.strPincode = strPincode;
	}//end of setPincode(String strPincode)

	/** Retrieve the Hospital Status 
	 * @return Returns the strHospStatus.
	 */
	public String getHospStatus() {
		return strHospStatus;
	}//end of getHospStatus()

	/** Sets the Hospital Status
	 * @param strHospStatus The strHospStatus to set.
	 */
	public void setHospStatus(String strHospStatus) {
		this.strHospStatus = strHospStatus;
	}//end of setHospStatus(String strHospStatus)

	/** Retrieve the Remarks
	 * @return Returns the strHospRemarks.
	 */
	public String getHospRemarks() {
		return strHospRemarks;
	}//end of getHospRemarks()

	/** Sets the Remarks
	 * @param strHospRemarks The strHospRemarks to set.
	 */
	public void setHospRemarks(String strHospRemarks) {
		this.strHospRemarks = strHospRemarks;
	}//end of setHospRemarks(String strHospRemarks)

	/** Retrieve the End Date 
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()

	/** Sets the End Date
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)

	/** Retrieve the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()

	/** Sets the Start Date
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)

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
    
    /** This method returns the Country Name
     * @return Returns the strCountryName.
     */
    public String getCountryName() {
        return strCountryName;
    }//end of getCountryName()
    
    /** This method sets the Country Name
     * @param strCountryName The strCountryName to set.
     */
    public void setCountryName(String strCountryName) {
        this.strCountryName = strCountryName;
    }//end of setCountryName(String strCountryName)
    
    /** This method returns the State Name
     * @return Returns the strStateName.
     */
    public String getStateName() {
        return strStateName;
    }//end of getStateName()
    
    /** This method sets the State Name
     * @param strStateName The strStateName to set.
     */
    public void setStateName(String strStateName) {
        this.strStateName = strStateName;
    }//end of setStateName(String strStateName)
    
    /**
     * Retrieve the Address1
     * 
     * @return  strAddress1 String Address1
     */
    public String getAddress1() {
        return strAddress1;
    }//end of getAddress1()
    
    /**
     * Sets the Address1
     * 
     * @param  strAddress1 String Address1
     */
    public void setAddress1(String strAddress1) {
        this.strAddress1 = strAddress1;
    }//end of setAddress1(String strAddress1)
    
    /**
     * Retrieve the Address2
     * 
     * @return  strAddress2 String Address2
     */
    public String getAddress2() {
        return strAddress2;
    }//end of getAddress2()
    
    /**
     * Sets the Address2
     * 
     * @param  strAddress2 String Address2 
     */
    public void setAddress2(String strAddress2) {
        this.strAddress2 = strAddress2;
    }//end of setAddress2(String strAddress2)
    
    /** This method returns Rating
	 * @return Returns the strRating.
	 */
	public String getRating() {
		return strRating;
	}//End of getRating() 
	
	/** This method sets the Rating
	 * @param strRating The strRating to set.
	 */
	public void setRating(String strRating) {
		this.strRating = strRating;
	}//End of setRating(String strRating)
	
	/** This method returns RatingImageName
	 * @return Returns the strRatingImageName.
	 */
	public String getRatingImageName() {
		return strRatingImageName;
	}//End of getRatingImageName() 
	
	/** This method sets the RatingImageName
	 * @param strRatingImageName The strRatingImageName to set.
	 */
	public void setRatingImageName(String strRatingImageName) {
		this.strRatingImageName = strRatingImageName;
	}//End of setRatingImageName(String strRatingImageName)
	
	/** This method returns RatingImageTitle
	 * @return Returns the strRatingImageTitle.
	 */
	public String getRatingImageTitle() {
		return strRatingImageTitle;
	}//End of RatingImageTitle()
	
	/** This method sets the setRatingImageTitle
	 * @param strRatingImageTitle The strRatingImageTitle to set.
	 */
	public void setRatingImageTitle(String strRatingImageTitle) {
		this.strRatingImageTitle = strRatingImageTitle;
	}//End of setRatingImageTitle(String strRatingImageTitle)
	
	/** This method returns ServiceTaxRegnNbr
	 * @return Returns the strRatingImageTitle.
	 */
	public String getHospServiceTaxRegnNbr() 
	 {
			return strHospServiceTaxRegnNbr;
	 }//end of getServiceTaxRegnNbr()

	/** This method sets the ServiceTaxRegnNbr
	 * @param strRatingImageTitle The strServiceTaxRegnNbr to set.
	 */
	public void setHospServiceTaxRegnNbr(String strHospServiceTaxRegnNbr) 
	{
			this.strHospServiceTaxRegnNbr = strHospServiceTaxRegnNbr;
	}//end of setServiceTaxRegnNbr(String strServiceTaxRegnNbr) 
}


