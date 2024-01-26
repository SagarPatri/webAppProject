/**
 * @ (#) BufferVO.java Jun 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BufferVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 17, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class BufferVO extends BaseVO{
	
	private Long lngProdPolicySeqID = null;
	private Long lngBufferSeqID = null;
	private String strRefNbr = "";
	private Date dtBufferDate = null;
	private BigDecimal bdBufferAmt = null;//this is for normal corpus fund
	private String strModeDesc = "";
	private BigDecimal bdTotBufferAmt = null;
	
	private Long lngPolicySeqID = null;
	//<!-- added for hyundai buffer by rekha on 19.06.2014 -->
	private BigDecimal bdNormMedAmt = null;
	private BigDecimal bdCritiCorpAmt = null;
	private BigDecimal bdCritiMedAmt = null;
	private BigDecimal bdCriIllBufferAmt = null;
    private BigDecimal bdNrmMedLimit = null;
	private BigDecimal bdCriCorpLimit = null;
	private BigDecimal bdCriMedLimit = null;
	private BigDecimal bdCriIllBufferLimit = null;
	private BigDecimal bdTotNorMedBufferAmt = null;
	private BigDecimal bdTotCriCorBufferAmt = null;
	private BigDecimal bdTotCriMedBufferAmt = null;
	private BigDecimal bdTotCriIllnessBufferAmt = null;

	
	public BigDecimal getNormMedAmt() {
		return bdNormMedAmt;
	}

	public void setNormMedAmt(BigDecimal bdNormMedAmt) {
		this.bdNormMedAmt = bdNormMedAmt;
	}
	public BigDecimal getCritiCorpAmt() {
		return bdCritiCorpAmt;
	}

	public void setCritiCorpAmt(BigDecimal bdCritiCorpAmt) {
		this.bdCritiCorpAmt = bdCritiCorpAmt;
	}
	public BigDecimal getCritiMedAmt() {
		return bdCritiMedAmt;
	}

	public void setCritiMedAmt(BigDecimal bdCritiMedAmt) {
		this.bdCritiMedAmt = bdCritiMedAmt;
	}

	//<!-end - added for hyundai buffer by rekha on 19.06.2014 -->
	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the Total Buffer Amount
	 * @return Returns the bdTotBufferAmt.
	 */
	public BigDecimal getTotBufferAmt() {
		return bdTotBufferAmt;
	}//end of getTotBufferAmt()
	
	/** Sets the Total Buffer Amount
	 * @param bdTotBufferAmt The bdTotBufferAmt to set.
	 */
	public void setTotBufferAmt(BigDecimal bdTotBufferAmt) {
		this.bdTotBufferAmt = bdTotBufferAmt;
	}//end of setTotBufferAmt(BigDecimal bdTotBufferAmt)
	
	/** Retrieve the Buffer Amount
	 * @return Returns the bdBufferAmt.
	 */
	public BigDecimal getBufferAmt() {
		return bdBufferAmt;
	}//end of getBufferAmt()
	
	/** Sets the BufferAmt
	 * @param bdBufferAmt The bdBufferAmt to set.
	 */
	public void setBufferAmt(BigDecimal bdBufferAmt) {
		this.bdBufferAmt = bdBufferAmt;
	}//end of setBufferAmt(BigDecimal bdBufferAmt)
	
	/** Retrieve the Buffer Date
	 * @return Returns the dtBufferDate.
	 */
	public Date getBufferDate() {
		return dtBufferDate;
	}//end of getBufferDate()
	
	/** Retrieve the Buffer Date
	 * @return Returns the dtBufferDate.
	 */
	public String getBufferAddedDate() {
		return TTKCommon.getFormattedDate(dtBufferDate);
	}//end of getBufferAddedDate()
	
	/** Sets the Buffer Date
	 * @param dtBufferDate The dtBufferDate to set.
	 */
	public void setBufferDate(Date dtBufferDate) {
		this.dtBufferDate = dtBufferDate;
	}//end of setBufferDate(Date dtBufferDate)
	
	/** Retrieve the Buffer Seq ID
	 * @return Returns the lngBufferSeqID.
	 */
	public Long getBufferSeqID() {
		return lngBufferSeqID;
	}//end of getBufferSeqID()
	
	/** Sets the Buffer Seq ID
	 * @param lngBufferSeqID The lngBufferSeqID to set.
	 */
	public void setBufferSeqID(Long lngBufferSeqID) {
		this.lngBufferSeqID = lngBufferSeqID;
	}//end of setBufferSeqID(Long lngBufferSeqID)
	
	/** Retrieve the Policy Seq ID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the Policy Seq ID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the Mode Description
	 * @return Returns the strModeDesc.
	 */
	public String getModeDesc() {
		return strModeDesc;
	}//end of getModeDesc()
	
	/** Sets the Mode Description
	 * @param strModeDesc The strModeDesc to set.
	 */
	public void setModeDesc(String strModeDesc) {
		this.strModeDesc = strModeDesc;
	}//end of setModeDesc(String strModeDesc)
	
	/** Retrieve the Reference Number
	 * @return Returns the strRefNbr.
	 */
	public String getRefNbr() {
		return strRefNbr;
	}//end of getRefNbr()
	
	/** Sets the Reference Number
	 * @param strRefNbr The strRefNbr to set.
	 */
	public void setRefNbr(String strRefNbr) {
		this.strRefNbr = strRefNbr;
	}//end of setRefNbr(String strRefNbr)

	public BigDecimal getNrmMedLimit() {
		return bdNrmMedLimit;
	}

	public void setNrmMedLimit(BigDecimal bdNrmMedLimit) {
		this.bdNrmMedLimit = bdNrmMedLimit;
	}

	public BigDecimal getCriCorpLimit() {
		return bdCriCorpLimit;
	}

	public void setCriCorpLimit(BigDecimal bdCriCorpLimit) {
		this.bdCriCorpLimit = bdCriCorpLimit;
	}

	public BigDecimal getCriMedLimit() {
		return bdCriMedLimit;
	}

	public void setCriMedLimit(BigDecimal bdCriMedLimit) {
		this.bdCriMedLimit = bdCriMedLimit;
	}

	public BigDecimal getTotNorMedBufferAmt() {
		return bdTotNorMedBufferAmt;
	}

	public void setTotNorMedBufferAmt(BigDecimal bdTotNorMedBufferAmt) {
		this.bdTotNorMedBufferAmt = bdTotNorMedBufferAmt;
	}

	public BigDecimal getTotCriCorBufferAmt() {
		return bdTotCriCorBufferAmt;
	}

	public void setTotCriCorBufferAmt(BigDecimal bdTotCriCorBufferAmt) {
		this.bdTotCriCorBufferAmt = bdTotCriCorBufferAmt;
	}

	public BigDecimal getTotCriMedBufferAmt() {
		return bdTotCriMedBufferAmt;
	}

	public void setTotCriMedBufferAmt(BigDecimal bdTotCriMedBufferAmt) {
		this.bdTotCriMedBufferAmt = bdTotCriMedBufferAmt;
	}

	public BigDecimal getCriIllBufferAmt() {
		return bdCriIllBufferAmt;
	}

	public void setCriIllBufferAmt(BigDecimal bdCriIllBufferAmt) {
		this.bdCriIllBufferAmt = bdCriIllBufferAmt;
	}

	public BigDecimal getCriIllBufferLimit() {
		return bdCriIllBufferLimit;
	}

	public void setCriIllBufferLimit(BigDecimal bdCriIllBufferLimit) {
		this.bdCriIllBufferLimit = bdCriIllBufferLimit;
	}

	public BigDecimal getTotCriIllnessBufferAmt() {
		return bdTotCriIllnessBufferAmt;
	}

	public void setTotCriIllnessBufferAmt(BigDecimal bdTotCriIllnessBufferAmt) {
		this.bdTotCriIllnessBufferAmt = bdTotCriIllnessBufferAmt;
	}
	

	
	
}//end of BufferVO
