/**
 * @ (#) HospitalCopayVO.java Nov 3, 2008
 * Project 	     : TTK HealthCare Services
 * File          : HospitalCopayVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Nov 3, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class HospitalCopayVO extends BaseVO{
	
	private Long lngProdHospSeqID = null;
	private Long lngHospSeqID = null;
	private Long lngProdPolicySeqID = null;
	private BigDecimal bdCopayAmt = null;
	private BigDecimal bdCopayPerc = null;
	
	/** Retrieve the CopayAmt
	 * @return Returns the bdCopayAmt.
	 */
	public BigDecimal getCopayAmt() {
		return bdCopayAmt;
	}//end of getCopayAmt()
	
	/** Sets the CopayAmt
	 * @param bdCopayAmt The bdCopayAmt to set.
	 */
	public void setCopayAmt(BigDecimal bdCopayAmt) {
		this.bdCopayAmt = bdCopayAmt;
	}//end of setCopayAmt(BigDecimal bdCopayAmt)
	
	/** Retrieve the CopayPerc
	 * @return Returns the bdCopayPerc.
	 */
	public BigDecimal getCopayPerc() {
		return bdCopayPerc;
	}//end of getCopayPerc()
	
	/** Sets the CopayPerc
	 * @param bdCopayPerc The bdCopayPerc to set.
	 */
	public void setCopayPerc(BigDecimal bdCopayPerc) {
		this.bdCopayPerc = bdCopayPerc;
	}//end of setCopayPerc(BigDecimal bdCopayPerc)
	
	/** Retrieve the ProdHospSeqID
	 * @return Returns the lngProdHospSeqID.
	 */
	public Long getProdHospSeqID() {
		return lngProdHospSeqID;
	}//end of getProdHospSeqID()
	
	/** Sets the ProdHospSeqID
	 * @param lngProdHospSeqID The lngProdHospSeqID to set.
	 */
	public void setProdHospSeqID(Long lngProdHospSeqID) {
		this.lngProdHospSeqID = lngProdHospSeqID;
	}//end of setProdHospSeqID(Long lngProdHospSeqID)
	
	/** Retrieve the hospSeqID
	 * @return Returns the lngHospSeqID.
	 */
	public Long getHospSeqID() {
		return lngHospSeqID;
	}//end of getHospSeqID()
	
	/** Sets the hospSeqID
	 * @param lngHospSeqID The lngHospSeqID to set.
	 */
	public void setHospSeqID(Long lngHospSeqID) {
		this.lngHospSeqID = lngHospSeqID;
	}//end of setHospSeqID(Long lngHospSeqID)
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)

}//end of HospitalCopayVO
