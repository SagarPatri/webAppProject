/**
 * @ (#) AdditionalDetailVO.java Apr 18, 2006
 * Project 	     : TTK HealthCare Services
 * File          : AdditionalDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 18, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class AdditionalDetailVO extends BaseVO {
	
	private String strRelationshipTypeID = "";
	private String strEmployeeNbr = "";
	private String strEmployeeName = "";
	private Date dtJoiningDate = null;
	private Date dtAddReceivedDate=null;
	private String strAddReceivedTime="";
    private String strAddReceivedDay="";
    private String strReceivedFromType = "";
    private String strSourceTypeID = "";
    private String strReferenceNbr = "";
    private String strContactPerson = "";
    private String strAdditionalRemarks = "";
    private Long lngAdditionalDtlSeqID = null;
    private String strCertificateNo =""; //certificate_no
    private String strInsScheme =""; //ins_scheme
    
    /** Retrive the CertificateNo
	 * @return Returns the strCertificateNo.
	 */
	public String getCertificateNo() {
		return strCertificateNo;
	}//end of getCertificateNo()

	/** Sets the CertificateNo
	 * @param strCertificateNo The strCertificateNo to set.
	 */
	public void setCertificateNo(String strCertificateNo) {
		this.strCertificateNo = strCertificateNo;
	}//end of setCertificateNo(String strCertificateNo)
	
	/** Retrive the InsScheme
	 * @return Returns the strInsScheme.
	 */
	public String getInsScheme() {
		return strInsScheme;
	}//end of getInsScheme()

	/** Sets the InsScheme
	 * @param strInsScheme The strInsScheme to set.
	 */
	public void setInsScheme(String strInsScheme) {
		this.strInsScheme = strInsScheme;
	}//end of setInsScheme(String strInsScheme)
    
    /** Retrieve the Additional Dtl Seq ID
	 * @return Returns the lngAdditionalDtlSeqID.
	 */
	public Long getAdditionalDtlSeqID() {
		return lngAdditionalDtlSeqID;
	}//end of getAdditionalDtlSeqID()

	/** Sets the Additional Dtl Seq ID
	 * @param lngAdditionalDtlSeqID The lngAdditionalDtlSeqID to set.
	 */
	public void setAdditionalDtlSeqID(Long lngAdditionalDtlSeqID) {
		this.lngAdditionalDtlSeqID = lngAdditionalDtlSeqID;
	}//end of setAdditionalDtlSeqID(Long lngAdditionalDtlSeqID)

	/** Retrieve the Joining Date
	 * @return Returns the dtJoiningDate.
	 */
	public Date getJoiningDate() {
		return dtJoiningDate;
	}//end of getJoiningDate()

	/** Sets the Joining Date
	 * @param dtJoiningDate The dtJoiningDate to set.
	 */
	public void setJoiningDate(Date dtJoiningDate) {
		this.dtJoiningDate = dtJoiningDate;
	}//end of setJoiningDate(Date dtJoiningDate)

	/** Retrieve the Additional Remarks
	 * @return Returns the strAdditionalRemarks.
	 */
	public String getAdditionalRemarks() {
		return strAdditionalRemarks;
	}//end of getAdditionalRemarks()

	/** Sets the Additional Remarks
	 * @param strAdditionalRemarks The strAdditionalRemarks to set.
	 */
	public void setAdditionalRemarks(String strAdditionalRemarks) {
		this.strAdditionalRemarks = strAdditionalRemarks;
	}//end of setAdditionalRemarks(String strAdditionalRemarks)

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

	/** Retrieve the Employee Name
	 * @return Returns the strEmployeeName.
	 */
	public String getEmployeeName() {
		return strEmployeeName;
	}//end of getEmployeeName()

	/** Sets the Employee Name
	 * @param strEmployeeName The strEmployeeName to set.
	 */
	public void setEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}//end of setEmployeeName(String strEmployeeName)

	/** Retrieve the Employee No.
	 * @return Returns the strEmployeeNbr.
	 */
	public String getEmployeeNbr() {
		return strEmployeeNbr;
	}//end of getEmployeeNbr()

	/** Sets the Employee No.
	 * @param strEmployeeNbr The strEmployeeNbr to set.
	 */
	public void setEmployeeNbr(String strEmployeeNbr) {
		this.strEmployeeNbr = strEmployeeNbr;
	}//end of setEmployeeNbr(String strEmployeeNbr)

	/** Retrieve the Received From Type
	 * @return Returns the strReceivedFromType.
	 */
	public String getReceivedFromType() {
		return strReceivedFromType;
	}//end of getReceivedFromType()

	/** Sets the Received From Type
	 * @param strReceivedFromType The strReceivedFromType to set.
	 */
	public void setReceivedFromType(String strReceivedFromType) {
		this.strReceivedFromType = strReceivedFromType;
	}//end of setReceivedFromType(String strReceivedFromType)

	/** Retrieve the Reference No.
	 * @return Returns the strReferenceNbr.
	 */
	public String getReferenceNbr() {
		return strReferenceNbr;
	}//end of getReferenceNbr()

	/** Sets the Reference No.
	 * @param strReferenceNbr The strReferenceNbr to set.
	 */
	public void setReferenceNbr(String strReferenceNbr) {
		this.strReferenceNbr = strReferenceNbr;
	}//end of setReferenceNbr(String strReferenceNbr)

	/** Retrieve the Relationship Type ID
	 * @return Returns the strRelationshipTypeID.
	 */
	public String getRelationshipTypeID() {
		return strRelationshipTypeID;
	}//end of getRelationshipTypeID()

	/** Sets the Relationship Type ID
	 * @param strRelationshipTypeID The strRelationshipTypeID to set.
	 */
	public void setRelationshipTypeID(String strRelationshipTypeID) {
		this.strRelationshipTypeID = strRelationshipTypeID;
	}//end of setRelationshipTypeID(String strRelationshipTypeID)

	/** Retrieve the Source Type ID
	 * @return Returns the strSourceTypeID.
	 */
	public String getSourceTypeID() {
		return strSourceTypeID;
	}//end of getSourceTypeID()

	/** Sets the Source Type ID
	 * @param strSourceTypeID The strSourceTypeID to set.
	 */
	public void setSourceTypeID(String strSourceTypeID) {
		this.strSourceTypeID = strSourceTypeID;
	}//end of setSourceTypeID(String strSourceTypeID)

	/**
     * Retrieve the Received Date
     * @return  dtAddReceivedDate Date
     */
    public Date getAddReceivedDate() {
        return dtAddReceivedDate;
    }//end of getAddReceivedDate()
    
    /**
     * Retrieve the Received Date
     * @return  dtAddReceivedDate Date
     */
    public String getInfoReceivedDate() {
        return TTKCommon.getFormattedDate(dtAddReceivedDate);
    }//end of getInfoReceivedDate()
    
    /**
     * Sets the Received Date
     * @param  dtAddReceivedDate Date  
     */
    public void setAddReceivedDate(Date dtAddReceivedDate) {
        this.dtAddReceivedDate = dtAddReceivedDate;
    }//end of setAddReceivedDate(Date dtAddReceivedDate)
    
    /**
     * Retrieve the Received Time
     * @return  strAddReceivedTime String
     */
    public String getAddReceivedTime() {
        return strAddReceivedTime;
    }//end of getAddReceivedTime()
    
    /**
     * Sets the Received Time
     * @param  strAddReceivedTime String  
     */
    public void setAddReceivedTime(String strAddReceivedTime) {
        this.strAddReceivedTime = strAddReceivedTime;
    }//end of setAddReceivedTime(String strAddReceivedTime)
    
    /**
     * Retrieve the Received Day
     * @return  strAddReceivedDay String
     */
    public String getAddReceivedDay() {
        return strAddReceivedDay;
    }//end of getAddReceivedDay()
    
    /**
     * Sets the Received Day
     * @param  strAddReceivedDay String  
     */
    public void setAddReceivedDay(String strAddReceivedDay) {
        this.strAddReceivedDay = strAddReceivedDay;
    }//end of setAddReceivedDay(String strAddReceivedDay)

}//end of AdditionalDetailVO
