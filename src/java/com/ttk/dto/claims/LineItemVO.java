/**
 * @ (#) LineItemVO.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : LineItemVO.java
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

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class LineItemVO extends BaseVO{

	private Long lngLineItemSeqID = null;
    private Long lngBillSeqID = null;
	private String strLineItemNbr = "";
	private String strAccountHeadTypeID = "";
	private String strAccountHeadDesc = "";
	private String strAllowYN = "";
	private BigDecimal bdRequestedAmt = null;
	private BigDecimal bdAllowedAmt = null;
	private BigDecimal bdDisAllowedAmt = null;
	private BigDecimal bdTotalAmt = null;
	private String strRoomTypeID = "";
	private String strVaccineTypeID = "";//added for maternity
	
	private String strRoomTypeDesc = "";
	private Integer intNbrofDays = null;
    private String strRemarks = "";
	//added for KOC-Decoupling
    private Integer intRownum = null;	
    private Long lngClaimSeqID = null;
	private String strNextbillNo= "";
    private Long lngNextbillSeqID = null;
    private Long lngNextLineItemSeqId = null;
    //ended

    //physiotherapy cr 1320
    private Integer intNumberofVisits = null;
    private String strClaimSubTypeID = "";
    
    /**
	 * @return the strClaimSubTypeID
	 */
	public String getClaimSubTypeID() {
		return strClaimSubTypeID;
	}
    private String strImmuneTypeID = "";//added for koc 1315
    private String strWellchildTypeID = "";//added for koc 1316
    private String strRoutadultTypeID = "";//added for koc 1308

	/**
	 * @param strClaimSubTypeID the strClaimSubTypeID to set
	 */
	public void setClaimSubTypeID(String strClaimSubTypeID) {
		this.strClaimSubTypeID = strClaimSubTypeID;
	}
   
	/**
	 * @return the intNumberofVisits
	 */
	public Integer getNumberofVisits() {
		return intNumberofVisits;
	}

	/**
	 * @param intNumberofVisits the intNumberofVisits to set
	 */
	public void setNumberofVisits(Integer intNumberofVisits) {
		this.intNumberofVisits = intNumberofVisits;
	}
	//physiotherapy cr 1320
    
	public String getvaccineTypeID() {
		return strVaccineTypeID;
	}

	public void setvaccineTypeID(String strVaccineTypeID) {
		this.strVaccineTypeID = strVaccineTypeID;
	}

	/** Retrieve the NbrofDays
	 * @return Returns the intNbrofDays.
	 */
	public Integer getNbrofDays() {
		return intNbrofDays;
	}//end of getNbrofDays()

	/** Sets the NbrofDays
	 * @param intNbrofDays The intNbrofDays to set.
	 */
	public void setNbrofDays(Integer intNbrofDays) {
		this.intNbrofDays = intNbrofDays;
	}//end of setNbrofDays(Integer intNbrofDays)

	/** Retrieve the RoomTypeID
	 * @return Returns the strRoomTypeID.
	 */
	public String getRoomTypeID() {
		return strRoomTypeID;
	}//end of getRoomTypeID()

	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)

    /** Retrieve the Remarks
     * @return Returns the strRemarks.
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()

    /** Sets the RoomTypeID
     * @param strRoomTypeID The strRoomTypeID to set.
     */
    public void setRoomTypeID(String strRoomTypeID) {
        this.strRoomTypeID = strRoomTypeID;
    }//end of setRoomTypeID(String strRoomTypeID)

	/** Retrieve the Allowed Amount
	 * @return Returns the bdAllowedAmt.
	 */
	public BigDecimal getAllowedAmt() {
		return bdAllowedAmt;
	}//end of getAllowedAmt()

	/** Sets the Allowed Amount
	 * @param bdAllowedAmt The bdAllowedAmt to set.
	 */
	public void setAllowedAmt(BigDecimal bdAllowedAmt) {
		this.bdAllowedAmt = bdAllowedAmt;
	}//end of setAllowedAmt(BigDecimal bdAllowedAmt)

	/** Retrieve the DisAllowed Amount
	 * @return Returns the bdDisAllowedAmt.
	 */
	public BigDecimal getDisAllowedAmt() {
		return bdDisAllowedAmt;
	}//end of getDisAllowedAmt()

	/** Sets the DisAllowed Amount
	 * @param bdDisAllowedAmt The bdDisAllowedAmt to set.
	 */
	public void setDisAllowedAmt(BigDecimal bdDisAllowedAmt) {
		this.bdDisAllowedAmt = bdDisAllowedAmt;
	}//end of setDisAllowedAmt(BigDecimal bdDisAllowedAmt)

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

	/** Retrieve the Total Amount
	 * @return Returns the bdTotalAmt.
	 */
	public BigDecimal getTotalAmt() {
		return bdTotalAmt;
	}//end of getTotalAmt()

	/** Sets the Total Amount
	 * @param bdTotalAmt The bdTotalAmt to set.
	 */
	public void setTotalAmt(BigDecimal bdTotalAmt) {
		this.bdTotalAmt = bdTotalAmt;
	}//end of setTotalAmt(BigDecimal bdTotalAmt)

	/** Retrieve the LineItemSeqID
	 * @return Returns the lngLineItemSeqID.
	 */
	public Long getLineItemSeqID() {
		return lngLineItemSeqID;
	}//end of getLineItemSeqID()

	/** Sets the LineItemSeqID
	 * @param lngLineItemSeqID The lngLineItemSeqID to set.
	 */
	public void setLineItemSeqID(Long lngLineItemSeqID) {
		this.lngLineItemSeqID = lngLineItemSeqID;
	}//end of setLineItemSeqID(Long lngLineItemSeqID)

    /** Retrieve the BillSeqID
     * @return Returns the lngBillSeqID.
     */
    public Long getBillSeqID() {
        return lngBillSeqID;
    }//end of getBillSeqID()

    /** Sets the BillSeqID
     * @param lngBillSeqID The lngBillSeqID to set.
     */
    public void setBillSeqID(Long lngBillSeqID) {
        this.lngBillSeqID = lngBillSeqID;
    }//end of setBillSeqID(Long lngBillSeqID)

	/** Retrieve the Account Head Description
	 * @return Returns the strAccountHeadDesc.
	 */
	public String getAccountHeadDesc() {
		return strAccountHeadDesc;
	}//end of getAccountHeadDesc()

	/** Sets the Account Head Description
	 * @param strAccountHeadDesc The strAccountHeadDesc to set.
	 */
	public void setAccountHeadDesc(String strAccountHeadDesc) {
		this.strAccountHeadDesc = strAccountHeadDesc;
	}//end of setAccountHeadDesc(String strAccountHeadDesc)

	/** Retrieve the AccountHeadTypeID
	 * @return Returns the strAccountHeadTypeID.
	 */
	public String getAccountHeadTypeID() {
		return strAccountHeadTypeID;
	}//end of getAccountHeadTypeID()

	/** Sets the AccountHeadTypeID
	 * @param strAccountHeadTypeID The strAccountHeadTypeID to set.
	 */
	public void setAccountHeadTypeID(String strAccountHeadTypeID) {
		this.strAccountHeadTypeID = strAccountHeadTypeID;
	}//end of setAccountHeadTypeID(String strAccountHeadTypeID)

	/** Retrieve the AllowYN
	 * @return Returns the strAllowYN.
	 */
	public String getAllowYN() {
		return strAllowYN;
	}//end of getAllowYN()

	/** Sets the AllowYN
	 * @param strAllowYN The strAllowYN to set.
	 */
	public void setAllowYN(String strAllowYN) {
		this.strAllowYN = strAllowYN;
	}//en of setAllowYN(String strAllowYN)

	/** Retrieve the LineItemNbr
	 * @return Returns the strLineItemNbr.
	 */
	public String getLineItemNbr() {
		return strLineItemNbr;
	}//end of getLineItemNbr()

	/** Sets the LineItemNbr
	 * @param strLineItemNbr The strLineItemNbr to set.
	 */
	public void setLineItemNbr(String strLineItemNbr) {
		this.strLineItemNbr = strLineItemNbr;
	}//end of setLineItemNbr(String strLineItemNbr)

	/** Retrieve the Room Type Description
	 * @return Returns the strRoomTypeDesc.
	 */
	public String getRoomTypeDesc() {
		return strRoomTypeDesc;
	}//end of getRoomTypeDesc()

	/** Sets the Room Type Description
	 * @param strRoomTypeDesc The strRoomTypeDesc to set.
	 */
	public void setRoomTypeDesc(String strRoomTypeDesc) {
		this.strRoomTypeDesc = strRoomTypeDesc;
	}//end of setRoomTypeDesc(String strRoomTypeDesc)
	public void setRownum(Integer intRownum) {
		this.intRownum = intRownum;
	}
// added for koc 1315
	public String getImmuneTypeID() {
		return strImmuneTypeID;
	}

	public Integer getRownum() {
		return intRownum;
	}

	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}

	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}

	public void setNextbillNo(String strNextbillNo) {
		this.strNextbillNo = strNextbillNo;
	}

	public String getNextbillNo() {
		return strNextbillNo;
	}

	public void setNextbillSeqID(Long lngNextbillSeqID) {
		this.lngNextbillSeqID = lngNextbillSeqID;
	}

	public Long getNextbillSeqID() {
		return lngNextbillSeqID;
	}

	public void setNextLineItemSeqId(Long lngNextLineItemSeqId) {
		this.lngNextLineItemSeqId = lngNextLineItemSeqId;
	}

	public Long getNextLineItemSeqId() {
		return lngNextLineItemSeqId;
	}

	public void setImmuneTypeID(String strImmuneTypeID) {
		this.strImmuneTypeID = strImmuneTypeID;
	}
	//end  added for koc 1315
	// added for koc 1316
	public String getWellchildTypeID() {
		return strWellchildTypeID;
	}

	public void setWellchildTypeID(String strWellchildTypeID) {
		this.strWellchildTypeID = strWellchildTypeID;
	}
	//end  added for koc 1316
	// added for koc 1308
	public String getRoutadultTypeID() {
		return strRoutadultTypeID;
	}

	public void setRoutadultTypeID(String strRoutadultTypeID) {
		this.strRoutadultTypeID = strRoutadultTypeID;
	}
	//end  added for koc 1308
}//end of LineItemVO
