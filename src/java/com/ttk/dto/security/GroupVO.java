/**
 * @ (#) GroupVO.java Dec 19, 2005
 * Project       : TTK HealthCare Services
 * File          : GroupVO.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Dec 19, 2005
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.security;

import com.ttk.dto.BaseVO;

public class GroupVO extends BaseVO {

	private Long lngGroupSeqID = null; //GROUP_SEQ_ID
	private String strGroupName = ""; //GROUP_NAME
    private String strDisplayGroupName = ""; //GROUP_NAME for display is Table
	private String strGroupDesc = ""; //GROUP_DESCRIPTION
	private Long lngOfficeSeqID = null;//TPA_OFFICE_SEQ_ID
	private String strOfficeName = "";//OFFICE_NAME
	private String strOfficeCode = ""; //OFFICE_CODE
	private String strZoneName = "" ; //ZONE_NAME
	private Long lngGroupBranchSeqID = null; //GROUP_BRANCH_SEQ_ID
	private Long lngProductSeqID = null; //PRODUCT_SEQ_ID
	private String strDefaultGroupYN = ""; //
	private String strImageName = "ContactsIcon";
	private String strImageTitle = "Users";
    private String strInsCompName ="";
	private String strUserTypeID ="";
    //KOC Cigna_insurance_resriction
    private String struserRestriction = ""; 
    
	public void setuserRestriction(String struserRestriction) {
		this.struserRestriction = struserRestriction;
	}

	public String getuserRestriction() {
		return struserRestriction;
	}
	//KOC Cigna_insurance_resriction

    /** This method returns the Insurance Company Name
     * @return Returns the strInsCompName.
     */
    public String getInsCompName() {
        return strInsCompName;
    }// End of getInsCompName()

    /** This method sets the Insurance Company Name Name
     * @param strInsCompName The strInsCompName to set.
     */
    public void setInsCompName(String strInsCompName) {
        this.strInsCompName = strInsCompName;
    }// End of setGroupName(String strGroupName)

	/** This method returns the GROUP SEQUENCE ID
	 * @return Returns the lngGroupSeqID.
	 */
	public Long getGroupSeqID() {
		return lngGroupSeqID;
	}//End of getGroupSeqID()

	/** This method sets the GROUP SEQUENCE ID
	 * @param grpSeqId The lngGroupSeqID to set.
	 */
	public void setGroupSeqID(Long lngGroupSeqID) {
		this.lngGroupSeqID = lngGroupSeqID;
	}// End of setGroupSeqID(Long lngGroupSeqID)

	/** This method returns the Group Name
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}// End of getGroupName()

	/** This method sets the Group Name
	 * @param strGrpname The strGrpname to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}// End of setGroupName(String strGroupName)

    /** This method returns the Group Name with branch code concatenated
     * @return Returns the strGroupName.
     */
    public String getDisplayGroupName() {
        return strDisplayGroupName;
    }// End of getDisplayGroupName()

    /** This method sets the Group Name
     * @param strDisplayGroupName The strDisplayGroupName to set.
     */
    
    
    
    
    public String getUserTypeID() {
		return strUserTypeID;
	}

	public void setUserTypeID(String strUserTypeID) {
		this.strUserTypeID = strUserTypeID;
	}

	
	
	
    public void setDisplayGroupName(String strDisplayGroupName) {
        this.strDisplayGroupName = strDisplayGroupName;
    }// End of setDisplayGroupName(String strDisplayGroupName)

	/** This method returns the Group Description
	 * @return Returns the strGrpDesc.
	 */
	public String getGroupDesc() {
		return strGroupDesc;
	} // End of getGroupDesc()

	/** This method sets the Group Description
	 * @param strGrpDesc The strGroupDesc to set.
	 */
	public void setGroupDesc(String strGroupDesc) {
		this.strGroupDesc = strGroupDesc;
	}// End of setGrpDesc(String strGrpDesc)

	/** This method returns the TPA Office Sequence Id
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()

	/** This method sets the TPA Office Sequence Id
	 * @param tpaOfficeSeqId The lTpaOfficeSeqId to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)

	/** This method returns the Default Group YN
	 * @return Returns the strDefaultGroupYN.
	 */
	public String getDefaultGroupYN() {
		return strDefaultGroupYN;
	}//end of getDefaultGroupYN()

	/** This method sets the Default Group YN
	 * @param strDefaultGroupYN The strDefaultGroupYN to set.
	 */
	public void setDefaultGroupYN(String strDefaultGroupYN) {
		this.strDefaultGroupYN = strDefaultGroupYN;
	}//end of setDefaultGroupYN(String strDefaultGroupYN)

	/** This method returns the Office Name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()

	/** This method sets the Office Name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)

	/**
	 * This method gets the image title
	 *
	 * @return strImageTitle String image title
	 */
	public String getImageTitle()
	{
		return this.strImageTitle;
	}//end of getImageTitle() method

	/**
	 * This method sets the image title
	 *
	 * @param strImageTitle String image name
	 */
	public void setImageTitle(String strImageTitle)
	{
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle) method

	/**
	 * This method sets the image name
	 *
	 * @param strImageName String image name
	 */
	public void setImageName(String strImageName)
	{
		this.strImageName = strImageName;
	}//end of setImageName

	/**
	 * This method gets the image name
	 *
	 * @return strImageName String image name
	 */
	public String getImageName()
	{
		return this.strImageName;
	}//end of getImageName

	/** This method returns the Group Branch Sequence ID
	 * @return Returns the lngGroupBranchSeqID.
	 */
	public Long getGroupBranchSeqID() {
		return lngGroupBranchSeqID;
	}//End of getGroupBranchSeqID()

	/** This method sets the Group Branch Sequence ID
	 * @param lngGroupBranchSeqID The lngGroupBranchSeqID to set.
	 */
	public void setGroupBranchSeqID(Long lngGroupBranchSeqID) {
		this.lngGroupBranchSeqID = lngGroupBranchSeqID;
	}//End of setGroupBranchSeqID(Long lngGroupBranchSeqID)

	/** This method returns the Product Sequence ID
	 * @return Returns the lngProductSeqID.
	 */
	public Long getProductSeqID() {
		return lngProductSeqID;
	}//End of getProductSeqID()

	/** This method sets the Product Sequence ID
	 * @param lngProductSeqID The lngProductSeqID to set.
	 */
	public void setProductSeqID(Long lngProductSeqID) {
		this.lngProductSeqID = lngProductSeqID;
	}// End of setProductSeqID(Long lngProductSeqID)

	/** This method returns the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}// Edn of getOfficeCode()

	/** This method sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}// Edn of setOfficeCode(String strOfficeCode)

	/** This method returns the Zone Name
	 * @return Returns the strZoneName.
	 */
	public String getZoneName() {
		return strZoneName;
	}// End of getZoneName()

	/** This method sets the Zone name
	 * @param strZoneName The strZoneName to set.
	 */
	public void setZoneName(String strZoneName) {
		this.strZoneName = strZoneName;
	}// End of setZoneName(String strZoneName)

}// End of GroupVO
