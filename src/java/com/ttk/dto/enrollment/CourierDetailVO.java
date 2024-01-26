/**
 * @ (#) CourierDetailVO.java May 26, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CourierDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 26, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class CourierDetailVO extends CourierVO {
	
	private Long lngOfficeSeqID = null;
	private String strContentTypeID = "";
	private Long lngHospSeqID = null;
	private String strHospName = "";
	private String strEmpanelmentNbr = "";
	private String strCardBatchNbr = "";
	private Date dtBatchDate = null;
	private Long lngCardBatchSeqID = null;
	private String strContentDesc = "";
	private String strStatusTypeID = "";
	private Date dtDeliveryDate = null;
	private String strDeliveryTime = "";
	private String strDeliveryDay = "";
	private String strProofDeliveryNbr = "";
	private String strRemarks = "";
	private String strCourierTypeID = "";
	private Integer intNbrOfClaims = null;
	private Integer intNbrOfPolicies = null;
	private String strDispatchTime = "";
	private String strDispatchDay = "";
	private Date dtReceivedDate = null;
	private String strReceivedDay = "";
	private String strReceivedTime = "";
	private Long lngCourierCompSeqID = null;
	private String strOtherDesc = "";
	private String strRcptRemarks = "";
	private String strDispatchRemarks = "";
	private String strSourceRcvdTypeId = "";
	private Long lngOfficeSeqID1 = null;
	
	//added for 2koc

	public Long getOfficeSeqID1() {
		return lngOfficeSeqID1;
	}

	public void setOfficeSeqID1(Long lngOfficeSeqID1) {
		this.lngOfficeSeqID1 = lngOfficeSeqID1;
	}

	private String strRctSen = "";
	private String strRctSenLoc= "";
	private String strRctRecName= "";
	private String strRctSignature= "";
	private String strRctClaimType= "";
	private String strRctdepartment= "";
	private String strRctPhoneNbr = "";
	private String strRctEmailID ="";
	private String strRctDocType ="";
	private String strRctCouAddressTo ="";
	private String strCouAddressTo ="";
	private Integer intNbrOfDocs = null;
	private String strDspToaddress ="";
	private String strDepartment= "";
	private String strStatusTypeID1 = "";
	private String strDocketPODNbr = "";
	private String strRctSen1 = "";
    private String strDocketPODNbr1 = "";
    public String  strSignature="";
    private String StrCourierDocType = "";
    
    
    public String getCourierDocType() {
		return StrCourierDocType;
	}

	public void setCourierDocType(String strCourierDocType) {
		StrCourierDocType = strCourierDocType;
	}

	public String getCouAddressTo() {
		return strCouAddressTo;
	}

	public void setCouAddressTo(String strCouAddressTo) {
		this.strCouAddressTo = strCouAddressTo;
	}

	
 
	public String getSignature() {
		return strSignature;
	}

	public void setSignature(String strSignature) {
		this.strSignature = strSignature;
	}

	public String getRctSen1() {
		return strRctSen1;
	}

	public void setRctSen1(String strRctSen1) {
		this.strRctSen1 = strRctSen1;
	}

	
	public String getDocketPODNbr1() {
		return strDocketPODNbr1;
	}

	public void setDocketPODNbr1(String strDocketPODNbr1) {
		this.strDocketPODNbr1 = strDocketPODNbr1;
	}

	public String getDocketPODNbr() {
		return strDocketPODNbr;
	}

	public void setDocketPODNbr(String strDocketPODNbr) {
		this.strDocketPODNbr = strDocketPODNbr;
	}
	
	public String getStatusTypeID1() {
		return strStatusTypeID1;
	}

	public void setStatusTypeID1(String strStatusTypeID1) {
		this.strStatusTypeID1 = strStatusTypeID1;
	}

	public String getDepartment() {
		return strDepartment;
	}

	public void setDepartment(String strDepartment) {
		this.strDepartment = strDepartment;
	}

	private Date dtPodReceDate = null;
	
	
	
	
	public Date getPodReceDate() {
		return dtPodReceDate;
	}

	public void setPodReceDate(Date dtPodReceDate) {
		this.dtPodReceDate = dtPodReceDate;
	}

	public String getDspToaddress() {
		return strDspToaddress;
	}

	public void setDspToaddress(String strDspToaddress) {
		this.strDspToaddress = strDspToaddress;
	}

	public Integer getNbrOfDocs() {
		return intNbrOfDocs;
	}

	public void setNbrOfDocs(Integer intNbrOfDocs) {
		this.intNbrOfDocs = intNbrOfDocs;
	}

	public String getRctCouAddressTo() {
		return strRctCouAddressTo;
	}

	public void setRctCouAddressTo(String strRctCouAddressTo) {
		this.strRctCouAddressTo = strRctCouAddressTo;
	}

	public String getRctDocType() {
		return strRctDocType;
	}

	public void setRctDocType(String strRctDocType) {
		this.strRctDocType = strRctDocType;
	}

	public String getRctClaimType() {
		return strRctClaimType;
	}

	public void setRctClaimType(String strRctClaimType) {
		this.strRctClaimType = strRctClaimType;
	}

	public String getRctSignature() {
		return strRctSignature;
	}

	public void setRctSignature(String strRctSignature) {
		this.strRctSignature = strRctSignature;
	}

	
	
	
	public String getRctRecName() {
		return strRctRecName;
	}

	public void setRctRecName(String strRctRecName) {
		this.strRctRecName = strRctRecName;
	}

	
	public String getRctEmailID() {
		return strRctEmailID;
	}

	public void setRctEmailID(String strRctEmailID) {
		this.strRctEmailID = strRctEmailID;
	}

	public String getRctPhoneNbr() {
		return strRctPhoneNbr;
	}

	public void setRctPhoneNbr(String strRctPhoneNbr) {
		this.strRctPhoneNbr = strRctPhoneNbr;
	}

	
	
	
	
	
	
	public String getRctdepartment() {
		return strRctdepartment;
	}

	public void setRctdepartment(String strRctdepartment) {
		this.strRctdepartment = strRctdepartment;
	}

	



	public String getRctSenLoc() {
		return strRctSenLoc;
	}

	public void setRctSenLoc(String strRctSenLoc) {
		this.strRctSenLoc = strRctSenLoc;
	}

	public String getRctSen() {
		return strRctSen;
	}

	public void setRctSen(String strRctSen) {
		this.strRctSen = strRctSen;
	}

	
	//end added for 2koc
	/** Retrieve the SourceRcvdTypeId
	 * @return Returns the strSourceRcvdTypeId.
	 */
	public String getSourceRcvdTypeId() {
		return strSourceRcvdTypeId;
	}//end of getSourceRcvdTypeId()

	/** Sets the SourceRcvdTypeId
	 * @param strSourceRcvdTypeId The strSourceRcvdTypeId to set.
	 */
	public void setSourceRcvdTypeId(String strSourceRcvdTypeId) {
		this.strSourceRcvdTypeId = strSourceRcvdTypeId;
	}//end of setSourceRcvdTypeId(String strSourceRcvdTypeId)

	/** Retrieve the Dispatch Remarks
	 * @return Returns the strDispatchRemarks.
	 */
	public String getDispatchRemarks() {
		return strDispatchRemarks;
	}//end of getDispatchRemarks()

	/** Sets the Dispatch Remarks
	 * @param strDispatchRemarks The strDispatchRemarks to set.
	 */
	public void setDispatchRemarks(String strDispatchRemarks) {
		this.strDispatchRemarks = strDispatchRemarks;
	}//end of setDispatchRemarks(String strDispatchRemarks)

	/** Retrieve the Receipt Remarks
	 * @return Returns the strRcptRemarks.
	 */
	public String getRcptRemarks() {
		return strRcptRemarks;
	}//end of getRcptRemarks()

	/** Sets the Receipt Remarks
	 * @param strRcptRemarks The strRcptRemarks to set.
	 */
	public void setRcptRemarks(String strRcptRemarks) {
		this.strRcptRemarks = strRcptRemarks;
	}//end of setRcptRemarks(String strRcptRemarks)

	/** Retrieve the OtherDesc
	 * @return Returns the strOtherDesc.
	 */
	public String getOtherDesc() {
		return strOtherDesc;
	}//end of getOtherDesc()

	/** Sets the OtherDesc
	 * @param strOtherDesc The strOtherDesc to set.
	 */
	public void setOtherDesc(String strOtherDesc) {
		this.strOtherDesc = strOtherDesc;
	}//end of setOtherDesc(String strOtherDesc)

	/** Retrieve the Courier Company Seq ID
	 * @return Returns the lngCourierCompSeqID.
	 */
	public Long getCourierCompSeqID() {
		return lngCourierCompSeqID;
	}//end of getCourierCompSeqID()

	/** Sets the Courier Company Seq ID
	 * @param lngCourierCompSeqID The lngCourierCompSeqID to set.
	 */
	public void setCourierCompSeqID(Long lngCourierCompSeqID) {
		this.lngCourierCompSeqID = lngCourierCompSeqID;
	}//end of setCourierCompSeqID(Long lngCourierCompSeqID)

	/** Retrieve the ReceivedDate
	 * @return Returns the dtReceivedDate.
	 */
	public Date getReceivedDate() {
		return dtReceivedDate;
	}//end of getReceivedDate()
	
	/** Retrieve the ReceivedDate
	 * @return Returns the dtReceivedDate.
	 */
	public String getReceivedDateTime() {
		return TTKCommon.getFormattedDate(dtReceivedDate);
	}//end of getReceivedDate()

	/** Sets the ReceivedDate
	 * @param dtReceivedDate The dtReceivedDate to set.
	 */
	public void setReceivedDate(Date dtReceivedDate) {
		this.dtReceivedDate = dtReceivedDate;
	}//end of setReceivedDate(Date dtReceivedDate)

	/** Retrieve the ReceivedDay
	 * @return Returns the strReceivedDay.
	 */
	public String getReceivedDay() {
		return strReceivedDay;
	}//end of getReceivedDay()

	/** Sets the ReceivedDay
	 * @param strReceivedDay The strReceivedDay to set.
	 */
	public void setReceivedDay(String strReceivedDay) {
		this.strReceivedDay = strReceivedDay;
	}//end of setReceivedDay(String strReceivedDay)

	/** Retrieve the ReceivedTime
	 * @return Returns the strReceivedTime.
	 */
	public String getReceivedTime() {
		return strReceivedTime;
	}//end of getReceivedTime()

	/** Sets the ReceivedTime
	 * @param strReceivedTime The strReceivedTime to set.
	 */
	public void setReceivedTime(String strReceivedTime) {
		this.strReceivedTime = strReceivedTime;
	}//end of setReceivedTime(String strReceivedTime)

	/** Retrieve the Dispatch Day
	 * @return Returns the strDispatchDay.
	 */
	public String getDispatchDay() {
		return strDispatchDay;
	}//end of getDispatchDay()

	/** Sets the Dispatch Day
	 * @param strDispatchDay The strDispatchDay to set.
	 */
	public void setDispatchDay(String strDispatchDay) {
		this.strDispatchDay = strDispatchDay;
	}//end of setDispatchDay(String strDispatchDay)

	/** Retrieve the Dispatch Time
	 * @return Returns the strDispatchTime.
	 */
	public String getDispatchTime() {
		return strDispatchTime;
	}//end of getDispatchTime()

	/** Sets the Dispatch Time
	 * @param strDispatchTime The strDispatchTime to set.
	 */
	public void setDispatchTime(String strDispatchTime) {
		this.strDispatchTime = strDispatchTime;
	}//end of setDispatchTime(String strDispatchTime)

	/** Retrieve the Number Of Claims
	 * @return Returns the intNbrOfClaims.
	 */
	public Integer getNbrOfClaims() {
		return intNbrOfClaims;
	}//end of getNbrOfClaims()

	/** Sets the Number Of Claims
	 * @param intNbrOfClaims The intNbrOfClaims to set.
	 */
	public void setNbrOfClaims(Integer intNbrOfClaims) {
		this.intNbrOfClaims = intNbrOfClaims;
	}//end of setNbrOfClaims(Integer intNbrOfClaims)

	/** Retrieve the Number Of Policies
	 * @return Returns the intNbrOfPolicies.
	 */
	public Integer getNbrOfPolicies() {
		return intNbrOfPolicies;
	}//end of getNbrOfPolicies()

	/** Sets the Number Of Policies
	 * @param intNbrOfPolicies The intNbrOfPolicies to set.
	 */
	public void setNbrOfPolicies(Integer intNbrOfPolicies) {
		this.intNbrOfPolicies = intNbrOfPolicies;
	}//end of setNbrOfPolicies(Integer intNbrOfPolicies)

	/** Retrieve the Courier Type ID
	 * @return Returns the strCourierTypeID.
	 */
	public String getCourierTypeID() {
		return strCourierTypeID;
	}//end of getCourierTypeID()

	/** Sets the Courier Type ID
	 * @param strCourierTypeID The strCourierTypeID to set.
	 */
	public void setCourierTypeID(String strCourierTypeID) {
		this.strCourierTypeID = strCourierTypeID;
	}//end of setCourierTypeID(String strCourierTypeID)

	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public Date getBatchDate() {
		return dtBatchDate;
	}//end of getBatchDate()
	
	/** Sets the BatchDate
	 * @param dtBatchDate The dtBatchDate to set.
	 */
	public void setBatchDate(Date dtBatchDate) {
		this.dtBatchDate = dtBatchDate;
	}//end of setBatchDate(Date dtBatchDate)
	
	/** Retrieve the Delivery Date
	 * @return Returns the dtDeliveryDate.
	 */
	public Date getDeliveryDate() {
		return dtDeliveryDate;
	}//end of getDeliveryDate()
	
	/** Retrieve the Delivery Date
	 * @return Returns the dtDeliveryDate.
	 */
	public String getDocDeliveryDate() {
		return TTKCommon.getFormattedDate(dtDeliveryDate);
	}//end of getDeliveryDate()
	
	/** Sets the Delivery Date
	 * @param dtDeliveryDate The dtDeliveryDate to set.
	 */
	public void setDeliveryDate(Date dtDeliveryDate) {
		this.dtDeliveryDate = dtDeliveryDate;
	}//end of setDeliveryDate(Date dtDeliveryDate)
	
	/** Retrieve the Card Batch Seq ID
	 * @return Returns the lngCardBatchSeqID.
	 */
	public Long getCardBatchSeqID() {
		return lngCardBatchSeqID;
	}//end of getCardBatchSeqID()
	
	/** Sets the Card Batch Seq ID
	 * @param lngCardBatchSeqID The lngCardBatchSeqID to set.
	 */
	public void setCardBatchSeqID(Long lngCardBatchSeqID) {
		this.lngCardBatchSeqID = lngCardBatchSeqID;
	}//end of setCardBatchSeqID(Long lngCardBatchSeqID)
	
	/** Retrieve the Hospital Seq ID
	 * @return Returns the lngHospSeqID.
	 */
	public Long getHospSeqID() {
		return lngHospSeqID;
	}//end of getHospSeqID()
	
	/** Sets the Hospital Seq ID
	 * @param lngHospSeqID The lngHospSeqID to set.
	 */
	public void setHospSeqID(Long lngHospSeqID) {
		this.lngHospSeqID = lngHospSeqID;
	}//end of setHospSeqID(Long lngHospSeqID)
	
	/** Retrieve the Office Seq ID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()
	
	/** Sets the Office Seq ID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)
	
	/** Retrieve the Card Batch Nbr
	 * @return Returns the strCardBatchNbr.
	 */
	public String getCardBatchNbr() {
		return strCardBatchNbr;
	}//end of getCardBatchNbr()
	
	/** Sets the Card Batch Nbr
	 * @param strCardBatchNbr The strCardBatchNbr to set.
	 */
	public void setCardBatchNbr(String strCardBatchNbr) {
		this.strCardBatchNbr = strCardBatchNbr;
	}//end of setCardBatchNbr(String strCardBatchNbr)
	
	/** Retrieve the Content Description
	 * @return Returns the strContentDesc.
	 */
	public String getContentDesc() {
		return strContentDesc;
	}//end of getContentDesc()
	
	/** Sets the Content Description
	 * @param strContentDesc The strContentDesc to set.
	 */
	public void setContentDesc(String strContentDesc) {
		this.strContentDesc = strContentDesc;
	}//end of setContentDesc(String strContentDesc)
	
	/** Retrieve the Content Type ID
	 * @return Returns the strContentTypeID.
	 */
	public String getContentTypeID() {
		return strContentTypeID;
	}//end of getContentTypeID()
	
	/** Sets the Content Type ID
	 * @param strContentTypeID The strContentTypeID to set.
	 */
	public void setContentTypeID(String strContentTypeID) {
		this.strContentTypeID = strContentTypeID;
	}//end of setContentTypeID(String strContentTypeID)
	
	/** Retrieve the Delivery Day
	 * @return Returns the strDeliveryDay.
	 */
	public String getDeliveryDay() {
		return strDeliveryDay;
	}//end of getDeliveryDay()
	
	/** Sets the Delivery Day
	 * @param strDeliveryDay The strDeliveryDay to set.
	 */
	public void setDeliveryDay(String strDeliveryDay) {
		this.strDeliveryDay = strDeliveryDay;
	}//end of setDeliveryDay(String strDeliveryDay)
	
	/** Retrieve the Delivery Time
	 * @return Returns the strDeliveryTime.
	 */
	public String getDeliveryTime() {
		return strDeliveryTime;
	}//end of getDeliveryTime()
	
	/** Sets the Delivery Time
	 * @param strDeliveryTime The strDeliveryTime to set.
	 */
	public void setDeliveryTime(String strDeliveryTime) {
		this.strDeliveryTime = strDeliveryTime;
	}//end of setDeliveryTime(String strDeliveryTime)
	
	/** Retrieve the Empanelment Nbr.
	 * @return Returns the strEmpanelmentNbr.
	 */
	public String getEmpanelmentNbr() {
		return strEmpanelmentNbr;
	}//end of getEmpanelmentNbr()
	
	/** Sets the Empanelment Nbr.
	 * @param strEmpanelmentNbr The strEmpanelmentNbr to set.
	 */
	public void setEmpanelmentNbr(String strEmpanelmentNbr) {
		this.strEmpanelmentNbr = strEmpanelmentNbr;
	}//end of setEmpanelmentNbr(String strEmpanelmentNbr)
	
	/** Retrieve the Hospital Name
	 * @return Returns the strHospName.
	 */
	public String getHospName() {
		return strHospName;
	}//end of getHospName()
	
	/** Sets the Hospital Name
	 * @param strHospName The strHospName to set.
	 */
	public void setHospName(String strHospName) {
		this.strHospName = strHospName;
	}//end of setHospName(String strHospName)
	
	/** Retrieve the Proof Delivery Nbr
	 * @return Returns the strProofDeliveryNbr.
	 */
	public String getProofDeliveryNbr() {
		return strProofDeliveryNbr;
	}//end of getProofDeliveryNbr()
	
	/** Sets the Proof Delivery Nbr
	 * @param strProofDeliveryNbr The strProofDeliveryNbr to set.
	 */
	public void setProofDeliveryNbr(String strProofDeliveryNbr) {
		this.strProofDeliveryNbr = strProofDeliveryNbr;
	}//end of setProofDeliveryNbr(String strProofDeliveryNbr)
	
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
	
}//end of CourierDetailVO
