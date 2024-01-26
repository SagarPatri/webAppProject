/**
 * @ (#) ClaimInwardDetailVO.java Jul 14, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimInwardDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 14, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ttk.dto.preauth.ClaimantVO;

public class ClaimInwardDetailVO extends ClaimInwardVO{
	
	private String strBarCodeNbr = "";
	private String strDocumentTypeDesc = "";
	private String strReceivedTime = "";
	private String strReceivedDay = "";
	private String strSourceTypeID = "";
	private String strClaimTypeID = "";
	private BigDecimal bdRequestedAmt = null;
	private Long lngOfficeSeqID = null;
	private String strOfficeName = "";
	private String strStatusTypeID = "";
	private String strRemarks = "";
	private Long lngCourierSeqID = null;
	private String strCourierNbr = "";
	private ArrayList<Object> alClaimDocumentVOList = null;
	private ClaimantVO claimantVO = null;
	private String strClaimNbr = "";
	private String strShortfallID = "";
	private Long lngShortfallSeqID = null;
	private ArrayList alPrevClaimList = new ArrayList();
	private Long lngParentClmSeqID = null;
	private String strCurrentClaimNbr = "";
	private ArrayList alShortfallVO = null;//For Inward Entry Claims->ShortfallList
	
	/** Retrieve the ArrayList of ShortfallVO
	 * @return Returns the alShortfallVO.
	 */
	public ArrayList getShortfallVO() {
		return alShortfallVO;
	}//end of getShortfallVO()

	/** Sets the ArrayList of ShortfallVO
	 * @param alShortfallVO The alShortfallVO to set.
	 */
	public void setShortfallVO(ArrayList alShortfallVO) {
		this.alShortfallVO = alShortfallVO;
	}//end of setShortfallVO(ArrayList alShortfallVO)
	
	/** Retrieve the CurrentClaimNbr
	 * @return Returns the strCurrentClaimNbr.
	 */
	public String getCurrentClaimNbr() {
		return strCurrentClaimNbr;
	}//end of getCurrentClaimNbr()

	/** Sets the CurrentClaimNbr
	 * @param strCurrentClaimNbr The strCurrentClaimNbr to set.
	 */
	public void setCurrentClaimNbr(String strCurrentClaimNbr) {
		this.strCurrentClaimNbr = strCurrentClaimNbr;
	}//end of setCurrentClaimNbr(String strCurrentClaimNbr)

	/** Retrieve the ParentClmSeqID
	 * @return Returns the lngParentClmSeqID.
	 */
	public Long getParentClmSeqID() {
		return lngParentClmSeqID;
	}//end of getParentClmSeqID()

	/** Sets the ParentClmSeqID
	 * @param lngParentClmSeqID The lngParentClmSeqID to set.
	 */
	public void setParentClmSeqID(Long lngParentClmSeqID) {
		this.lngParentClmSeqID = lngParentClmSeqID;
	}//end of setParentClmSeqID(Long lngParentClmSeqID)

	/** Retrieve the PrevClaimList
	 * @return Returns the alPrevClaimList.
	 */
	public ArrayList getPrevClaimList() {
		return alPrevClaimList;
	}//end of getPrevClaimList()

	/** Sets the PrevClaimList
	 * @param alPrevClaimList The alPrevClaimList to set.
	 */
	public void setPrevClaimList(ArrayList alPrevClaimList) {
		this.alPrevClaimList = alPrevClaimList;
	}//end of setPrevClaimList(ArrayList alPrevClaimList)

	/** Retrieve the ShortfallID
	 * @return Returns the strShortfallID.
	 */
	public String getShortfallID() {
		return strShortfallID;
	}//end of getShortfallID()

	/** Sets the ShortfallID
	 * @param strShortfallID The strShortfallID to set.
	 */
	public void setShortfallID(String strShortfallID) {
		this.strShortfallID = strShortfallID;
	}//end of setShortfallID(String strShortfallID)

	/** Retrieve the ClaimNbr
	 * @return Returns the strClaimNbr.
	 */
	public String getClaimNbr() {
		return strClaimNbr;
	}//end of getClaimNbr()

	/** Sets the ClaimNbr
	 * @param strClaimNbr The strClaimNbr to set.
	 */
	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}//end of setClaimNbr(String strClaimNbr)

	/** Retrieve the ClaimantVO
	 * @return Returns the claimantVO.
	 */
	public ClaimantVO getClaimantVO() {
		return claimantVO;
	}//end of getClaimantVO()

	/** Sets the ClaimantVO
	 * @param claimantVO The claimantVO to set.
	 */
	public void setClaimantVO(ClaimantVO claimantVO) {
		this.claimantVO = claimantVO;
	}//end of setClaimantVO(ClaimantVO claimantVO)

	/** Retrieve the DocumentVOList
	 * @return Returns the alClaimDocumentVOList.
	 */
	public ArrayList<Object> getClaimDocumentVOList() {
		return alClaimDocumentVOList;
	}//end of getClaimDocumentVOList()

	/** Sets the DocumentVOList
	 * @param alClaimDocumentVOList The alClaimDocumentVOList to set.
	 */
	public void setClaimDocumentVOList(ArrayList<Object> alClaimDocumentVOList) {
		this.alClaimDocumentVOList = alClaimDocumentVOList;
	}//end of setClaimDocumentVOList(ArrayList alClaimDocumentVOList)

	/** Retrieve the CourierSeqID
	 * @return Returns the lngCourierSeqID.
	 */
	public Long getCourierSeqID() {
		return lngCourierSeqID;
	}//end of getCourierSeqID()
	
	/** Sets the CourierSeqID
	 * @param lngCourierSeqID The lngCourierSeqID to set.
	 */
	public void setCourierSeqID(Long lngCourierSeqID) {
		this.lngCourierSeqID = lngCourierSeqID;
	}//end of setCourierSeqID(Long lngCourierSeqID)
	
	/** Retrieve the CourierNbr
	 * @return Returns the strCourierNbr.
	 */
	public String getCourierNbr() {
		return strCourierNbr;
	}//end of getCourierNbr()
	
	/** Sets the CourierNbr
	 * @param strCourierNbr The strCourierNbr to set.
	 */
	public void setCourierNbr(String strCourierNbr) {
		this.strCourierNbr = strCourierNbr;
	}//end of setCourierNbr(String strCourierNbr)
	
	/** Retrieve the Requested Amount
	 * @return Returns the bdRequestedAmt.
	 */
	public BigDecimal getRequestedAmt() {
		return bdRequestedAmt;
	}//end of getRequestedAmt()
	
	/** Sets the Requested Amount
	 * @param bdRequestedAmt The bdRequestedAmt to set.
	 */
	public void setRequestedAmt(BigDecimal bdRequestedAmt) {
		this.bdRequestedAmt = bdRequestedAmt;
	}//end of setRequestedAmt(BigDecimal bdRequestedAmt)
	
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
	
	/** Retrieve the BarCodeNbr
	 * @return Returns the strBarCodeNbr.
	 */
	public String getBarCodeNbr() {
		return strBarCodeNbr;
	}//end of getBarCodeNbr()
	
	/** Sets the BarCodeNbr
	 * @param strBarCodeNbr The strBarCodeNbr to set.
	 */
	public void setBarCodeNbr(String strBarCodeNbr) {
		this.strBarCodeNbr = strBarCodeNbr;
	}//end of setBarCodeNbr(String strBarCodeNbr)
	
	/** Retrieve the ClaimTypeID
	 * @return Returns the strClaimTypeID.
	 */
	public String getClaimTypeID() {
		return strClaimTypeID;
	}//end of getClaimTypeID()
	
	/** Sets the ClaimTypeID
	 * @param strClaimTypeID The strClaimTypeID to set.
	 */
	public void setClaimTypeID(String strClaimTypeID) {
		this.strClaimTypeID = strClaimTypeID;
	}//end of setClaimTypeID(String strClaimTypeID)
	
	/** Retrieve the Document Type Description
	 * @return Returns the strDocumentTypeDesc.
	 */
	public String getDocumentTypeDesc() {
		return strDocumentTypeDesc;
	}//end of getDocumentTypeDesc()
	
	/** Sets the Document Type Description
	 * @param strDocumentTypeDesc The strDocumentTypeDesc to set.
	 */
	public void setDocumentTypeDesc(String strDocumentTypeDesc) {
		this.strDocumentTypeDesc = strDocumentTypeDesc;
	}//end of setDocumentTypeDesc(String strDocumentTypeDesc)
	
	/** Retrieve the Office Name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()
	
	/** Sets the Office Name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)
	
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
	
	/** Retrieve the SourceTypeID
	 * @return Returns the strSourceTypeID.
	 */
	public String getSourceTypeID() {
		return strSourceTypeID;
	}//end of getSourceTypeID()
	
	/** Sets the SourceTypeID
	 * @param strSourceTypeID The strSourceTypeID to set.
	 */
	public void setSourceTypeID(String strSourceTypeID) {
		this.strSourceTypeID = strSourceTypeID;
	}//end of setSourceTypeID(String strSourceTypeID)
	
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

	/** Retrieve the ShortfallSeqID
	 * @return Returns the lngShortfallSeqID.
	 */
	public Long getShortfallSeqID() {
		return lngShortfallSeqID;
	}//end of getShortfallSeqID()

	/** Sets the ShortfallSeqID
	 * @param lngShortfallSeqID The lngShortfallSeqID to set.
	 */
	public void setShortfallSeqID(Long lngShortfallSeqID) {
		this.lngShortfallSeqID = lngShortfallSeqID;
	}//end of setShortfallSeqID(Long lngShortfallSeqID)
	
}//end of ClaimInwardDetailVO
