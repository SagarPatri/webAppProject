/**
 * @ (#) TariffDetailVO.java Jul 4, 2006
 * Project 	     : TTK HealthCare Services
 * File          : TariffDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 4, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class TariffDetailVO extends BaseVO{
	
	private String strTariffNotes = "";
	private BigDecimal bdReqSplitTariffAmt = null;
    private BigDecimal bdRequestedAmt = null;
    private BigDecimal bdApprovedAmt = null;
    private BigDecimal bdMaxAmtAllowed = null;
    private String strTariffRequestedAmt = "";
    private String strTariffApprovedAmt = "";
    private String strTariffMaxAmtAllowed = "";
    private Long lngTariffSeqID = null;
    private BigDecimal bdPackageRate = null;
    private Long lngWardAccGroupSeqID = null;
    private Long lngWardGrpSeqID = null;
    private String strAccGroupDesc = "";
    private String strWardTypeID = "";
    private String strWardDesc = "";
    private String strRoomTypeID = "";
    private String strDaysOfStay = null;
    private String strCaptureNbrOfDaysYN = "";//capture_number_of_days_yn
    private String strVaccineTypeID = "";// added for maternity
    private String strImmuneTypeID = "";// added for koc 1315
    private String strWellchildTypeID = "";// added for koc 1316
    private String strRoutadultTypeID = "";// added for koc 1308
    private  ArrayList vaccineType=null;
   
      
    
    public void setVaccineType(ArrayList vaccineType) {
		this.vaccineType = vaccineType;
	}

	public ArrayList getVaccineType() {
		return vaccineType;
	}

	public String getVaccineTypeID() {
		return strVaccineTypeID;
	}

	public void setVaccineTypeID(String strVaccineTypeID) {
		this.strVaccineTypeID = strVaccineTypeID;
	}

	/** Retrieve the DaysOfStay
	 * @return Returns the strDaysOfStay.
	 */
	public String getDaysOfStay() {
		return strDaysOfStay;
	}//end of getDaysOfStay()

	/** Sets the DaysOfStay
	 * @param strDaysOfStay The strDaysOfStay to set.
	 */
	public void setDaysOfStay(String strDaysOfStay) {
		this.strDaysOfStay = strDaysOfStay;
	}//end of setDaysOfStay(String strDaysOfStay)

	/** Retrieve the CaptureNbrOfDaysYN
	 * @return Returns the strCaptureNbrOfDaysYN.
	 */
	public String getCaptureNbrOfDaysYN() {
		return strCaptureNbrOfDaysYN;
	}//end of getCaptureNbrOfDaysYN()

	/** Sets the CaptureNbrOfDaysYN
	 * @param strCaptureNbrOfDaysYN The strCaptureNbrOfDaysYN to set.
	 */
	public void setCaptureNbrOfDaysYN(String strCaptureNbrOfDaysYN) {
		this.strCaptureNbrOfDaysYN = strCaptureNbrOfDaysYN;
	}//end of setCaptureNbrOfDaysYN(String strCaptureNbrOfDaysYN)

	/** Retrieve the RoomTypeID
	 * @return Returns the strRoomTypeID.
	 */
	public String getRoomTypeID() {
		return strRoomTypeID;
	}//end of getRoomTypeID()

	/** Sets the RoomTypeID
	 * @param strRoomTypeID The strRoomTypeID to set.
	 */
	public void setRoomTypeID(String strRoomTypeID) {
		this.strRoomTypeID = strRoomTypeID;
	}//end of setRoomTypeID(String strRoomTypeID)

	/** Retrieve the WardTypeID
	 * @return Returns the strWardTypeID.
	 */
	public String getWardTypeID() {
		return strWardTypeID;
	}//end of getWardTypeID()

	/** Sets the WardTypeID
	 * @param strWardTypeID The strWardTypeID to set.
	 */
	public void setWardTypeID(String strWardTypeID) {
		this.strWardTypeID = strWardTypeID;
	}//end of setWardTypeID(String strWardTypeID)

	/** Retrieve the WardGrpSeqID
	 * @return Returns the lngWardGrpSeqID.
	 */
	public Long getWardGrpSeqID() {
		return lngWardGrpSeqID;
	}//end of getWardGrpSeqID()

	/** Sets the WardGrpSeqID
	 * @param lngWardGrpSeqID The lngWardGrpSeqID to set.
	 */
	public void setWardGrpSeqID(Long lngWardGrpSeqID) {
		this.lngWardGrpSeqID = lngWardGrpSeqID;
	}//end of setWardGrpSeqID(Long lngWardGrpSeqID)

	/** Retrieve the Ward Description
	 * @return Returns the strWardDesc.
	 */
	public String getWardDesc() {
		return strWardDesc;
	}//end of getWardDesc()

	/** Sets the Ward Description
	 * @param strWardDesc The strWardDesc to set.
	 */
	public void setWardDesc(String strWardDesc) {
		this.strWardDesc = strWardDesc;
	}//end of setWardDesc(String strWardDesc)

	/** Retrieve the TariffApprovedAmt
	 * @return Returns the strTariffApprovedAmt.
	 */
	public String getTariffApprovedAmt() {
		return strTariffApprovedAmt;
	}//end of getTariffApprovedAmt()

	/** Sets the TariffApprovedAmt
	 * @param strTariffApprovedAmt The strTariffApprovedAmt to set.
	 */
	public void setTariffApprovedAmt(String strTariffApprovedAmt) {
		this.strTariffApprovedAmt = strTariffApprovedAmt;
	}//end of setTariffApprovedAmt(String strTariffApprovedAmt)

	/** Retrieve the TariffMaxAmtAllowed
	 * @return Returns the strTariffMaxAmtAllowed.
	 */
	public String getTariffMaxAmtAllowed() {
		return strTariffMaxAmtAllowed;
	}//end of getTariffMaxAmtAllowed()

	/** Sets the TariffMaxAmtAllowed
	 * @param strTariffMaxAmtAllowed The strTariffMaxAmtAllowed to set.
	 */
	public void setTariffMaxAmtAllowed(String strTariffMaxAmtAllowed) {
		this.strTariffMaxAmtAllowed = strTariffMaxAmtAllowed;
	}//end of setTariffMaxAmtAllowed(String strTariffMaxAmtAllowed)

	/** Retrieve the TariffRequestedAmt
	 * @return Returns the strTariffRequestedAmt.
	 */
	public String getTariffRequestedAmt() {
		return strTariffRequestedAmt;
	}//end of getTariffRequestedAmt()

	/** Sets the TariffRequestedAmt
	 * @param strTariffRequestedAmt The strTariffRequestedAmt to set.
	 */
	public void setTariffRequestedAmt(String strTariffRequestedAmt) {
		this.strTariffRequestedAmt = strTariffRequestedAmt;
	}//end of setTariffRequestedAmt(String strTariffRequestedAmt)

	/** Retrieve the MaxAmtAllowed
	 * @return Returns the bdMaxAmtAllowed.
	 */
	public BigDecimal getMaxAmtAllowed() {
		return bdMaxAmtAllowed;
	}//end of getMaxAmtAllowed()

	/** Sets the MaxAmtAllowed
	 * @param bdMaxAmtAllowed The bdMaxAmtAllowed to set.
	 */
	public void setMaxAmtAllowed(BigDecimal bdMaxAmtAllowed) {
		this.bdMaxAmtAllowed = bdMaxAmtAllowed;
	}//end of setMaxAmtAllowed(BigDecimal bdMaxAmtAllowed)

	/** Retrieve the RequestedAmt
	 * @return Returns the bdRequestedAmt.
	 */
	public BigDecimal getRequestedAmt() {
		return bdRequestedAmt;
	}//end of getRequestedAmt()

	/** Sets the RequestedAmt
	 * @param bdRequestedAmt The bdRequestedAmt to set.
	 */
	public void setRequestedAmt(BigDecimal bdRequestedAmt) {
		this.bdRequestedAmt = bdRequestedAmt;
	}//end of setRequestedAmt(BigDecimal bdRequestedAmt)

	/** Retrieve the TariffSeqID
	 * @return Returns the lngTariffSeqID.
	 */
	public Long getTariffSeqID() {
		return lngTariffSeqID;
	}//end of getTariffSeqID()

	/** Sets the TariffSeqID
	 * @param lngTariffSeqID The lngTariffSeqID to set.
	 */
	public void setTariffSeqID(Long lngTariffSeqID) {
		this.lngTariffSeqID = lngTariffSeqID;
	}//end of setTariffSeqID(Long lngTariffSeqID)

	/** Retrieve the Notes
	 * @return Returns the strTariffNotes.
	 */
	public String getTariffNotes() {
		return strTariffNotes;
	}//end of getTariffNotes()

	/** Sets the Notes
	 * @param strTariffNotes The strTariffNotes to set.
	 */
	public void setTariffNotes(String strTariffNotes) {
		this.strTariffNotes = strTariffNotes;
	}//end of setTariffNotes(String strTariffNotes)

	/** Retrieve the Request Split Tariff Amount
	 * @return Returns the bdReqSplitTariffAmt.
	 */
	public BigDecimal getReqSplitTariffAmt() {
		return bdReqSplitTariffAmt;
	}//end of getReqSplitTariffAmt()

	/** Sets the Request Split Tariff Amount
	 * @param bdReqSplitTariffAmt The bdReqSplitTariffAmt to set.
	 */
	public void setReqSplitTariffAmt(BigDecimal bdReqSplitTariffAmt) {
		this.bdReqSplitTariffAmt = bdReqSplitTariffAmt;
	}//end of setReqSplitTariffAmt(BigDecimal bdReqSplitTariffAmt)

	/** Retrieve the Ward Acc Group Seq ID
	 * @return Returns the lngWardAccGroupSeqID.
	 */
	public Long getWardAccGroupSeqID() {
		return lngWardAccGroupSeqID;
	}//end of getWardAccGroupSeqID()

	/** Sets the Ward Acc Group Seq ID
	 * @param lngWardAccGroupSeqID The lngWardAccGroupSeqID to set.
	 */
	public void setWardAccGroupSeqID(Long lngWardAccGroupSeqID) {
		this.lngWardAccGroupSeqID = lngWardAccGroupSeqID;
	}//end of setWardAccGroupSeqID(Long lngWardAccGroupSeqID)

	/** Retrieve the Acc Group Description
	 * @return Returns the strAccGroupDesc.
	 */
	public String getAccGroupDesc() {
		return strAccGroupDesc;
	}//end of getAccGroupDesc()

	/** Sets the Acc Group Description
	 * @param strAccGroupDesc The strAccGroupDesc to set.
	 */
	public void setAccGroupDesc(String strAccGroupDesc) {
		this.strAccGroupDesc = strAccGroupDesc;
	}//end of setAccGroupDesc(String strAccGroupDesc)
	
	/** Retrieve the Package Rate
	 * @return Returns the bdPackageRate.
	 */
	public BigDecimal getPackageRate() {
		return bdPackageRate;
	}//end of getPackageRate()

	/** Sets the Package Rate
	 * @param bdPackageRate The bdPackageRate to set.
	 */
	public void setPackageRate(BigDecimal bdPackageRate) {
		this.bdPackageRate = bdPackageRate;
	}//end of setPackageRate(BigDecimal bdPackageRate)
	
	/** This method returns the Approved Amount
     * @return Returns the bdApprovedAmt.
     */
    public BigDecimal getApprovedAmt() {
        return bdApprovedAmt;
    }//end of getApprovedAmt()
    
    /** This method sets the Approved Amount
     * @param bdApprovedAmt The bdApprovedAmt to set.
     */
    public void setApprovedAmt(BigDecimal bdApprovedAmt) {
        this.bdApprovedAmt = bdApprovedAmt;
    }//end of setApprovedAmt(BigDecimal bdApprovedAmt)
    
    
    //added for koc 1315
  	public String getImmuneTypeID() {
		return strImmuneTypeID;
	}

	public void setImmuneTypeID(String strImmuneTypeID) {
		this.strImmuneTypeID = strImmuneTypeID;
	}
    //end for koc 1315
	 //added for koc 1316
  	public String getWellchildTypeID() {
		return strWellchildTypeID;
	}

	public void setWellchildTypeID(String strWellchildTypeID) {
		this.strWellchildTypeID = strWellchildTypeID;
	}
    //end for koc 1316
	 //added for koc 1308
  	public String getRoutadultTypeID() {
		return strRoutadultTypeID;
	}

	public void setRoutadultTypeID(String strRoutadultTypeID) {
		this.strRoutadultTypeID = strRoutadultTypeID;
	}
    //end for koc 1308

	
}//end of TariffDetailVO
