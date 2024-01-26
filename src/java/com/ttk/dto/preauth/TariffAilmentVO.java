/**
 * @ (#) TariffAilmentVO.java May 5, 2006
 * Project 	     : TTK HealthCare Services
 * File          : TariffAilmentVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 5, 2006
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

public class TariffAilmentVO extends BaseVO {
	
	private BigDecimal bdAilmentApprovedAmt = null;
    private BigDecimal bdAilmentMaxAllowedAmt = null;
    private String strApprovedAilmentAmt = "";
    private String strMaxAilmentAllowedAmt = "";
    private Long lngPEDCodeID = null;
    private String strDescription = "";
    private String strPrimaryAilmentYN = "";
    private String strAilmentNotes = "";
    private Long lngAilmentCapsSeqID = null;
    private Long lngICDPCSSeqID = null;
    private Long lngAilmentProcSeqID = null;
    private Long lngProcSeqID = null;//proc_seq_id
    private String strProcDesc = "";//proc_description
    private String strProcedureAmt = "";//procedure_amount
    private ArrayList<Object> alProcedureList=null;
    
    
    
    /** Retrieve the Procedure List
	 * @return Returns the alProcedureList.
	 */
    public ArrayList<Object> getProcedureList() {
		return alProcedureList;
	}//end of getProcedureList()
    
    /** Sets the Procedure List
	 * @param alProcedureList ArrayList to set.
	 */
	public void setProcedureList(ArrayList<Object> alProcedureList) {
		this.alProcedureList = alProcedureList;
	}//end of setProcedureList(ArrayList alProcedureList)

	/** Retrieve the Procedure Seq ID
	 * @return Returns the lngProcSeqID.
	 */
	public Long getProcSeqID() {
		return lngProcSeqID;
	}//end of getProcSeqID()

	/** Sets the Procedure Seq ID
	 * @param lngProcSeqID The lngProcSeqID to set.
	 */
	public void setProcSeqID(Long lngProcSeqID) {
		this.lngProcSeqID = lngProcSeqID;
	}//end of setProcSeqID(Long lngProcSeqID)

	/** Retrieve the Procedure Description
	 * @return Returns the strProcDesc.
	 */
	public String getProcDesc() {
		return strProcDesc;
	}//end of getProcDesc()

	/** Sets the Procedure Description
	 * @param strProcDesc The strProcDesc to set.
	 */
	public void setProcDesc(String strProcDesc) {
		this.strProcDesc = strProcDesc;
	}//end of setProcDesc(String strProcDesc)

	/** Retrieve the Procedure Amount
	 * @return Returns the strProcedureAmt.
	 */
	public String getProcedureAmt() {
		return strProcedureAmt;
	}//end of getProcedureAmt()

	/** Sets the Procedure Amount
	 * @param strProcedureAmt The strProcedureAmt to set.
	 */
	public void setProcedureAmt(String strProcedureAmt) {
		this.strProcedureAmt = strProcedureAmt;
	}//end of setProcedureAmt(String strProcedureAmt)

	/** Retrieve the PrimaryAilmentYN
	 * @return Returns the strPrimaryAilmentYN.
	 */
	public String getPrimaryAilmentYN() {
		return strPrimaryAilmentYN;
	}//end of getPrimaryAilmentYN()

	/** Sets the PrimaryAilmentYN
	 * @param strPrimaryAilmentYN The strPrimaryAilmentYN to set.
	 */
	public void setPrimaryAilmentYN(String strPrimaryAilmentYN) {
		this.strPrimaryAilmentYN = strPrimaryAilmentYN;
	}//end of setPrimaryAilmentYN(String strPrimaryAilmentYN)

	/** Retrieve the ApprovedAilmentAmt
	 * @return Returns the strApprovedAilmentAmt.
	 */
	public String getApprovedAilmentAmt() {
		return strApprovedAilmentAmt;
	}//end of getApprovedAilmentAmt()

	/** Sets the ApprovedAilmentAmt
	 * @param strApprovedAilmentAmt The strApprovedAilmentAmt to set.
	 */
	public void setApprovedAilmentAmt(String strApprovedAilmentAmt) {
		this.strApprovedAilmentAmt = strApprovedAilmentAmt;
	}//end of setApprovedAilmentAmt(String strApprovedAilmentAmt)

	/** Retrieve the MaxAilmentAllowedAmt
	 * @return Returns the strMaxAilmentAllowedAmt.
	 */
	public String getMaxAilmentAllowedAmt() {
		return strMaxAilmentAllowedAmt;
	}//end of getMaxAilmentAllowedAmt()

	/** Sets the MaxAilmentAllowedAmt
	 * @param strMaxAilmentAllowedAmt The strMaxAilmentAllowedAmt to set.
	 */
	public void setMaxAilmentAllowedAmt(String strMaxAilmentAllowedAmt) {
		this.strMaxAilmentAllowedAmt = strMaxAilmentAllowedAmt;
	}//end of setMaxAilmentAllowedAmt(String strMaxAilmentAllowedAmt)

	/** Retrieve the ICDPCSSeqID
	 * @return Returns the lngICDPCSSeqID.
	 */
	public Long getICDPCSSeqID() {
		return lngICDPCSSeqID;
	}//end of getICDPCSSeqID()

	/** Sets the ICDPCSSeqID
	 * @param lngICDPCSSeqID The lngICDPCSSeqID to set.
	 */
	public void setICDPCSSeqID(Long lngICDPCSSeqID) {
		this.lngICDPCSSeqID = lngICDPCSSeqID;
	}//end of setICDPCSSeqID(Long lngICDPCSSeqID)

	/** Retrieve the Ailment Caps Seq ID
	 * @return Returns the lngAilmentCapsSeqID.
	 */
	public Long getAilmentCapsSeqID() {
		return lngAilmentCapsSeqID;
	}//end of getAilmentCapsSeqID()

	/** Sets the Ailment Caps Seq ID
	 * @param lngAilmentCapsSeqID The lngAilmentCapsSeqID to set.
	 */
	public void setAilmentCapsSeqID(Long lngAilmentCapsSeqID) {
		this.lngAilmentCapsSeqID = lngAilmentCapsSeqID;
	}//end of setAilmentCapsSeqID(Long lngAilmentCapsSeqID)

	/** Retrieve the Notes
	 * @return Returns the strAilmentNotes.
	 */
	public String getAilmentNotes() {
		return strAilmentNotes;
	}//end of getAilmentNotes()

	/** Sets the Notes
	 * @param strAilmentNotes The strAilmentNotes to set.
	 */
	public void setAilmentNotes(String strAilmentNotes) {
		this.strAilmentNotes = strAilmentNotes;
	}//end of setAilmentNotes(String strAilmentNotes)

	/** Retrieve the PED Code ID
	 * @return Returns the lngPEDCodeID.
	 */
	public Long getPEDCodeID() {
		return lngPEDCodeID;
	}//end of getPEDCodeID()

	/** Sets the PED Code ID
	 * @param lngPEDCodeID The lngPEDCodeID to set.
	 */
	public void setPEDCodeID(Long lngPEDCodeID) {
		this.lngPEDCodeID = lngPEDCodeID;
	}//end of setPEDCodeID(Long lngPEDCodeID)

	/** Retrieve the Description
	 * @return Returns the strDescription.
	 */
	public String getDescription() {
		return strDescription;
	}//end of getDescription()

	/** Sets the Description
	 * @param strDescription The strDescription to set.
	 */
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}//end of setDescription(String strDescription)

	/** This method returns the Approved Amount
     * @return Returns the bdAilmentApprovedAmt.
     */
    public BigDecimal getAilmentApprovedAmt() {
        return bdAilmentApprovedAmt;
    }//end of getAilmentApprovedAmt()
    
    /** This method sets the Approved Amount
     * @param bdAilmentApprovedAmt The bdAilmentApprovedAmt to set.
     */
    public void setAilmentApprovedAmt(BigDecimal bdAilmentApprovedAmt) {
        this.bdAilmentApprovedAmt = bdAilmentApprovedAmt;
    }//end of setAilmentApprovedAmt(BigDecimal bdAilmentApprovedAmt)
    
    /** Retrieve the Max Allowed Amount
	 * @return Returns the bdAilmentMaxAllowedAmt.
	 */
	public BigDecimal getAilmentMaxAllowedAmt() {
		return bdAilmentMaxAllowedAmt;
	}//end of getAilmentMaxAllowedAmt()

	/** Sets the Max Allowed Amount
	 * @param bdAilmentMaxAllowedAmt The bdAilmentMaxAllowedAmt to set.
	 */
	public void setAilmentMaxAllowedAmt(BigDecimal bdAilmentMaxAllowedAmt) {
		this.bdAilmentMaxAllowedAmt = bdAilmentMaxAllowedAmt;
	}//end of setAilmentMaxAllowedAmt(BigDecimal bdAilmentMaxAllowedAmt)

	/** Retrieve the AilmentProcSeqID
	 * @return Returns the lngAilmentProcSeqID.
	 */
	public Long getAilmentProcSeqID() {
		return lngAilmentProcSeqID;
	}//end of getAilmentProcSeqID()

	/** Sets the AilmentProcSeqID
	 * @param lngAilmentProcSeqID The lngAilmentProcSeqID to set.
	 */
	public void setAilmentProcSeqID(Long lngAilmentProcSeqID) {
		this.lngAilmentProcSeqID = lngAilmentProcSeqID;
	}//end of setAilmentProcSeqID(Long lngAilmentProcSeqID)
    
}//end of TariffAilmentVO
