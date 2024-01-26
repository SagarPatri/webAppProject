/**
 * @ (#) PreAuthTariffVO.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthTariffVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PreAuthTariffVO extends PreAuthVO {
    
    
    private ArrayList<Object> alAilmentVOList = null;
    private String strRoomTypeID = "";
   
    private Integer intAdditionalStay = null;//Req_stay
    private Integer intOtherDaysStay = null;
    private Integer intPkgDaysStay = null;
    private ArrayList<Object> alTariffDetailVOList = null;
    private BigDecimal bdPkgRequestedAmt = null;
    private BigDecimal bdPkgApprovedAmt = null;
    private BigDecimal bdPkgMaxAmtAllowed = null;
    private String strRequestedPkgAmt = "";
    private String strApprovedPkgAmt = "";
    private String strMaxPkgAmtAllowed = "";
    private Long lngTariffHdrSeqID = null;
    private String strNotes = "";
    private BigDecimal bdRequestedAmt = null;
    private String strReqAmount = "";
    private String strClickSave = "";
    private String strAccountYN = "";
    private String strAvaSuminsured = "";
    private String strAvaBonus = "";
    private String strAvaBuffer = "";
    private String strTotalAmt = "";
    private String strVaccineTypeID = "";//added for maternity
    //start Changes as per KOC 1140 ChangeRequest({SumInsuredRestriction)
    private String familySIResAmt = "";
    private String familySIResAmtYN = "N";
    
    
    
    
    public String getVaccineTypeID() {
		return strVaccineTypeID;
	}

	public void setVaccineTypeID(String strVaccineTypeID) {
		this.strVaccineTypeID = strVaccineTypeID;
	}

	/** Retrieve the AvaBonus
   
        
	/**
	 * @param familySIResAmt the familySIResAmt to set
	 */
	public void setFamilySIResAmt(String familySIResAmt) {
		this.familySIResAmt = familySIResAmt;
	}

	/**
	 * @return the familySIResAmt
	 */
	public String getFamilySIResAmt() {
		return familySIResAmt;
	}

	/**
	 * @param familySIResAmtYN the familySIResAmtYN to set
	 */
	public void setFamilySIResAmtYN(String familySIResAmtYN) {
		this.familySIResAmtYN = familySIResAmtYN;
	}

	/**
	 * @return the familySIResAmtYN
	 */
	public String getFamilySIResAmtYN() {
		return familySIResAmtYN;
	}
 //end Changes as per KOC 1140 ChangeRequest({SumInsuredRestriction)
    /** Retrieve the AvaBonus
	 * @return Returns the strAvaBonus.
	 */
	public String getAvaBonus() {
		return strAvaBonus;
	}//end of getAvaBonus()

	/** Sets the AvaBonus
	 * @param strAvaBonus The strAvaBonus to set.
	 */
	public void setAvaBonus(String strAvaBonus) {
		this.strAvaBonus = strAvaBonus;
	}//end of setAvaBonus(String strAvaBonus)

	/** Retrieve the AvaBuffer
	 * @return Returns the strAvaBuffer.
	 */
	public String getAvaBuffer() {
		return strAvaBuffer;
	}//end of getAvaBuffer()

	/** Sets the AvaBuffer
	 * @param strAvaBuffer The strAvaBuffer to set.
	 */
	public void setAvaBuffer(String strAvaBuffer) {
		this.strAvaBuffer = strAvaBuffer;
	}//end of setAvaBuffer(String strAvaBuffer)

	/** Retrieve the AvaSuminsured
	 * @return Returns the strAvaSuminsured.
	 */
	public String getAvaSuminsured() {
		return strAvaSuminsured;
	}//end of getAvaSuminsured()

	/** Sets the AvaSuminsured
	 * @param strAvaSuminsured The strAvaSuminsured to set.
	 */
	public void setAvaSuminsured(String strAvaSuminsured) {
		this.strAvaSuminsured = strAvaSuminsured;
	}//end of setAvaSuminsured(String strAvaSuminsured)

	/** Retrieve the TotalAmt
	 * @return Returns the strTotalAmt.
	 */
	public String getTotalAmt() {
		return strTotalAmt;
	}//end of getTotalAmt()

	/** Sets the TotalAmt
	 * @param strTotalAmt The strTotalAmt to set.
	 */
	public void setTotalAmt(String strTotalAmt) {
		this.strTotalAmt = strTotalAmt;
	}//end of setTotalAmt(String strTotalAmt)

	/** Retrieve the AccountYN
	 * @return Returns the strAccountYN.
	 */
	public String getAccountYN() {
		return strAccountYN;
	}//end of getAccountYN()

	/** Sets the AccountYN
	 * @param strAccountYN The strAccountYN to set.
	 */
	public void setAccountYN(String strAccountYN) {
		this.strAccountYN = strAccountYN;
	}//end of setAccountYN(String strAccountYN)

	/** Retrieve the flag Whether Calculae/Save Button has Clicked.
	 * @return Returns the strClickSave.
	 */
	public String getClickSave() {
		return strClickSave;
	}//end of getClickSave()

	/** Sets the flag Whether Calculae/Save Button has Clicked. 
	 * @param strClickSave The strClickSave to set.
	 */
	public void setClickSave(String strClickSave) {
		this.strClickSave = strClickSave;
	}//end of setClickSave(String strClickSave)

	/** Retrieve the Requested Amount
	 * @return Returns the strReqAmount.
	 */
	public String getReqAmount() {
		return strReqAmount;
	}//end of getReqAmount()

	/** Sets the Requested Amount
	 * @param strReqAmount The strReqAmount to set.
	 */
	public void setReqAmount(String strReqAmount) {
		this.strReqAmount = strReqAmount;
	}//end of setReqAmount(String strReqAmount)

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

	/** Retrieve the ApprovedPkgAmt
	 * @return Returns the strApprovedPkgAmt.
	 */
	public String getApprovedPkgAmt() {
		return strApprovedPkgAmt;
	}//end of getApprovedPkgAmt()

	/** Sets the ApprovedPkgAmt
	 * @param strApprovedPkgAmt The strApprovedPkgAmt to set.
	 */
	public void setApprovedPkgAmt(String strApprovedPkgAmt) {
		this.strApprovedPkgAmt = strApprovedPkgAmt;
	}//end of setApprovedPkgAmt(String strApprovedPkgAmt)

	/** Retrieve the MaxPkgAmtAllowed
	 * @return Returns the strMaxPkgAmtAllowed.
	 */
	public String getMaxPkgAmtAllowed() {
		return strMaxPkgAmtAllowed;
	}//end of getMaxPkgAmtAllowed()

	/** Sets the MaxPkgAmtAllowed
	 * @param strMaxPkgAmtAllowed The strMaxPkgAmtAllowed to set.
	 */
	public void setMaxPkgAmtAllowed(String strMaxPkgAmtAllowed) {
		this.strMaxPkgAmtAllowed = strMaxPkgAmtAllowed;
	}//end of setMaxPkgAmtAllowed(String strMaxPkgAmtAllowed)

	/** Retrieve the RequestedPkgAmt
	 * @return Returns the strRequestedPkgAmt.
	 */
	public String getRequestedPkgAmt() {
		return strRequestedPkgAmt;
	}//end of getRequestedPkgAmt()

	/** Sets the RequestedPkgAmt
	 * @param strRequestedPkgAmt The strRequestedPkgAmt to set.
	 */
	public void setRequestedPkgAmt(String strRequestedPkgAmt) {
		this.strRequestedPkgAmt = strRequestedPkgAmt;
	}//end of setRequestedPkgAmt(String strRequestedPkgAmt)

	/** Retrieve the Notes
	 * @return Returns the strNotes.
	 */
	public String getNotes() {
		return strNotes;
	}//end of getNotes()

	/** Sets the Notes
	 * @param strNotes The strNotes to set.
	 */
	public void setNotes(String strNotes) {
		this.strNotes = strNotes;
	}//end of setNotes(String strNotes)
    
    /** Retrieve the TariffHdrSeqID
	 * @return Returns the lngTariffHdrSeqID.
	 */
	public Long getTariffHdrSeqID() {
		return lngTariffHdrSeqID;
	}//end of getTariffHdrSeqID()

	/** Sets the TariffHdrSeqID
	 * @param lngTariffHdrSeqID The lngTariffHdrSeqID to set.
	 */
	public void setTariffHdrSeqID(Long lngTariffHdrSeqID) {
		this.lngTariffHdrSeqID = lngTariffHdrSeqID;
	}//end of setTariffHdrSeqID(Long lngTariffHdrSeqID)

	/** This method returns the Package Approved Amount
     * @return Returns the bdPkgApprovedAmt.
     */
    public BigDecimal getPkgApprovedAmt() {
        return bdPkgApprovedAmt;
    }//end of getApprovedAmt()
    
    /** This method sets the Approved Amount
     * @param bdPkgApprovedAmt The bdPkgApprovedAmt to set.
     */
    public void setPkgApprovedAmt(BigDecimal bdPkgApprovedAmt) {
        this.bdPkgApprovedAmt = bdPkgApprovedAmt;
    }//end of setPkgApprovedAmt(BigDecimal bdPkgApprovedAmt)
    
    /** Retrieve the MaxAmtAllowed
	 * @return Returns the bdMaxAmtAllowed.
	 */
	public BigDecimal getPkgMaxAmtAllowed() {
		return bdPkgMaxAmtAllowed;
	}//end of getPkgMaxAmtAllowed()

	/** Sets the PkgMaxAmtAllowed
	 * @param bdPkgMaxAmtAllowed The bdPkgMaxAmtAllowed to set.
	 */
	public void setPkgMaxAmtAllowed(BigDecimal bdPkgMaxAmtAllowed) {
		this.bdPkgMaxAmtAllowed = bdPkgMaxAmtAllowed;
	}//end of setPkgMaxAmtAllowed(BigDecimal bdPkgMaxAmtAllowed)

	/** Retrieve the PkgRequestedAmt
	 * @return Returns the bdPkgRequestedAmt.
	 */
	public BigDecimal getPkgRequestedAmt() {
		return bdPkgRequestedAmt;
	}//end of getPkgRequestedAmt()

	/** Sets the PkgRequestedAmt
	 * @param bdPkgRequestedAmt The bdPkgRequestedAmt to set.
	 */
	public void setPkgRequestedAmt(BigDecimal bdPkgRequestedAmt) {
		this.bdPkgRequestedAmt = bdPkgRequestedAmt;
	}//end of setPkgRequestedAmt(BigDecimal bdPkgRequestedAmt)
    
    /** Retrieve the TariffDetailVOList
	 * @return Returns the alTariffDetailVOList.
	 */
	public ArrayList<Object> getTariffDetailVOList() {
		return alTariffDetailVOList;
	}//end of getTariffDetailVOList()

	/** Sets the TariffDetailVOList
	 * @param alTariffDetailVOList The alTariffDetailVOList to set.
	 */
	public void setTariffDetailVOList(ArrayList<Object> alTariffDetailVOList) {
		this.alTariffDetailVOList = alTariffDetailVOList;
	}//end of setTariffDetailVOList(ArrayList alTariffDetailVOList)

	/** Retrieve the Other Days Stay
	 * @return Returns the intOtherDaysStay.
	 */
	public Integer getOtherDaysStay() {
		return intOtherDaysStay;
	}//end of getOtherDaysStay()

	/** Sets the Other Days Stay
	 * @param intOtherDaysStay The intOtherDaysStay to set.
	 */
	public void setOtherDaysStay(Integer intOtherDaysStay) {
		this.intOtherDaysStay = intOtherDaysStay;
	}//end of setOtherDaysStay(Integer intOtherDaysStay)

	/** Retrieve the PkgDaysStay
	 * @return Returns the intPkgDaysStay.
	 */
	public Integer getPkgDaysStay() {
		return intPkgDaysStay;
	}//end of getPkgDaysStay()

	/** Sets the PkgDaysStay
	 * @param intPkgDaysStay The intPkgDaysStay to set.
	 */
	public void setPkgDaysStay(Integer intPkgDaysStay) {
		this.intPkgDaysStay = intPkgDaysStay;
	}//end of setPkgDaysStay(Integer intPkgDaysStay)

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

	/** Retrieve the Additional Stay Days
	 * @return Returns the intAdditionalStay.
	 */
	public Integer getAdditionalStay() {
		return intAdditionalStay;
	}//end of getAdditionalStay()

	/** Sets the Additional Stay Days
	 * @param intAdditionalStay The intAdditionalStay to set.
	 */
	public void setAdditionalStay(Integer intAdditionalStay) {
		this.intAdditionalStay = intAdditionalStay;
	}//end of setAdditionalStay(Integer intAdditionalStay)

	/** Retrieve the Tariff AilmentVO List
	 * @return Returns the alAilmentVOList.
	 */
	public ArrayList<Object> getAilmentVOList() {
		return alAilmentVOList;
	}//end of getAilmentVOList()

	/** Sets the Tariff AilmentVO List
	 * @param alAilmentVOList The alAilmentVOList to set.
	 */
	public void setAilmentVOList(ArrayList<Object> alAilmentVOList) {
		this.alAilmentVOList = alAilmentVOList;
	}//end of setAilmentVOList(ArrayList alAilmentVOList)

}//end of PreAuthTariffVO
