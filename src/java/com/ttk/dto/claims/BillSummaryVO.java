/**
 * @ (#) BillSummaryVO.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BillSummaryVO.java
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
import java.util.ArrayList;

import com.ttk.dto.preauth.PreAuthVO;

public class BillSummaryVO extends PreAuthVO{
	
	private ArrayList<Object> alAilmentVOList = null;
    private ArrayList<Object> alBillDetailVOList = null;
    private BigDecimal bdPreHospitalizationAmt = null;
	private BigDecimal bdHospitalizationAmt = null;
	private BigDecimal bdPostHospitalizationAmt = null;
	private String strPreHospitalization = "";
	private String strHospitalization = "";
	private String strPostHospitalization = "";
	private Integer intReqAmtMisMatch = null;
	
	/** Retrieve the ReqAmtMisMatch
	 * @return Returns the intReqAmtMisMatch.
	 */
	public Integer getReqAmtMisMatch() {
		return intReqAmtMisMatch;
	}//end of getReqAmtMisMatch()

	/** Sets the ReqAmtMisMatch
	 * @param intReqAmtMisMatch The intReqAmtMisMatch to set.
	 */
	public void setReqAmtMisMatch(Integer intReqAmtMisMatch) {
		this.intReqAmtMisMatch = intReqAmtMisMatch;
	}//end of setReqAmtMisMatch(Integer intReqAmtMisMatch)

	/** Retrieve the Hospitalization Amount
	 * @return Returns the bdHospitalizationAmt.
	 */
	public BigDecimal getHospitalizationAmt() {
		return bdHospitalizationAmt;
	}//end of getHospitalizationAmt()
	
	/** Sets the Hospitalization Amount
	 * @param bdHospitalizationAmt The bdHospitalizationAmt to set.
	 */
	public void setHospitalizationAmt(BigDecimal bdHospitalizationAmt) {
		this.bdHospitalizationAmt = bdHospitalizationAmt;
	}//end of setHospitalizationAmt(BigDecimal bdHospitalizationAmt)
	
	/** Retrieve the Post Hospitalization Amt
	 * @return Returns the bdPostHospitalizationAmt.
	 */
	public BigDecimal getPostHospitalizationAmt() {
		return bdPostHospitalizationAmt;
	}//end of getPostHospitalizationAmt()
	
	/** Sets the Post Hospitalization Amt
	 * @param bdPostHospitalizationAmt The bdPostHospitalizationAmt to set.
	 */
	public void setPostHospitalizationAmt(BigDecimal bdPostHospitalizationAmt) {
		this.bdPostHospitalizationAmt = bdPostHospitalizationAmt;
	}//end of setPostHospitalizationAmt(BigDecimal bdPostHospitalizationAmt)
	
	/** Retrieve the Pre Hospitalization Amount
	 * @return Returns the bdPreHospitalizationAmt.
	 */
	public BigDecimal getPreHospitalizationAmt() {
		return bdPreHospitalizationAmt;
	}//end of getPreHospitalizationAmt()
	
	/** Sets the Pre Hospitalization Amount
	 * @param bdPreHospitalizationAmt The bdPreHospitalizationAmt to set.
	 */
	public void setPreHospitalizationAmt(BigDecimal bdPreHospitalizationAmt) {
		this.bdPreHospitalizationAmt = bdPreHospitalizationAmt;
	}//end of setPreHospitalizationAmt(BigDecimal bdPreHospitalizationAmt)
	
	/** Retrieve the Hospitalization
	 * @return Returns the strHospitalization.
	 */
	public String getHospitalization() {
		return strHospitalization;
	}//end of getHospitalization()
	
	/** Sets the Hospitalization
	 * @param strHospitalization The strHospitalization to set.
	 */
	public void setHospitalization(String strHospitalization) {
		this.strHospitalization = strHospitalization;
	}//end of setHospitalization(String strHospitalization)
	
	/** Retrieve the Post Hospitalization
	 * @return Returns the strPostHospitalization.
	 */
	public String getPostHospitalization() {
		return strPostHospitalization;
	}//end of getPostHospitalization()
	
	/** Sets the Post Hospitalization
	 * @param strPostHospitalization The strPostHospitalization to set.
	 */
	public void setPostHospitalization(String strPostHospitalization) {
		this.strPostHospitalization = strPostHospitalization;
	}//end of setPostHospitalization(String strPostHospitalization)
	
	/** Retrieve the PreHospitalization
	 * @return Returns the strPreHospitalization.
	 */
	public String getPreHospitalization() {
		return strPreHospitalization;
	}//end of getPreHospitalization()
	
	/** Sets the PreHospitalization
	 * @param strPreHospitalization The strPreHospitalization to set.
	 */
	public void setPreHospitalization(String strPreHospitalization) {
		this.strPreHospitalization = strPreHospitalization;
	}//end of setPreHospitalization(String strPreHospitalization)
	
    /** Retrieve the AilmentVOList
	 * @return Returns the alAilmentVOList.
	 */
	public ArrayList<Object> getAilmentVOList() {
		return alAilmentVOList;
	}//end of getAilmentVOList()

	/** Sets the AilmentVOList
	 * @param alAilmentVOList The alAilmentVOList to set.
	 */
	public void setAilmentVOList(ArrayList<Object> alAilmentVOList) {
		this.alAilmentVOList = alAilmentVOList;
	}//end of setAilmentVOList(ArrayList alAilmentVOList)

	/** Retrieve the BillDetailVO
	 * @return Returns the alBillDetailVOList.
	 */
	public ArrayList<Object> getBillDetailVOList() {
		return alBillDetailVOList;
	}//end of getBillSplitupVOList()

	/** Sets the BillDetailVO
	 * @param alBillDetailVOList The alBillDetailVOList to set.
	 */
	public void setBillDetailVOList(ArrayList<Object> alBillDetailVOList) {
		this.alBillDetailVOList = alBillDetailVOList;
	}//end of setBillSplitupVOList(ArrayList alBillDetailVOList)

}//end of BillSummaryVO
