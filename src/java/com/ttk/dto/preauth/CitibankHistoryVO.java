/**
 * @ (#) CitibankHistoryVO.java Sep 8, 2008
 * Project 	     : TTK HealthCare Services
 * File          : CitibankHistoryVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 8, 2008
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

/**
 * @author ramakrishna_km
 *
 */
public class CitibankHistoryVO extends BaseVO{
	
	private Long lngCitiEnrolSeqID = null;//CITIBANK_ENR_SEQ_ID
	private Long lngCitiClmSeqID = null;//CITIBANK_CLM_SEQ_ID
	private String strOrderNbr = "";//ORD_NUM
	private String strCustCode = "";//CUSTCODE
	private String strClaimNbr = "";//CLAIMNO
	private String strClaimYear = null;//CLAIMYEAR
		
	/** Retrieve the ClaimYear
	 * @return Returns the strClaimYear.
	 */
	public String getClaimYear() {
		return strClaimYear;
	}//end of getClaimYear()
	
	/** Sets the ClaimYear
	 * @param dtClaimYear The dtClaimYear to set.
	 */
	public void setClaimYear(String strClaimYear) {
		this.strClaimYear = strClaimYear;
	}//end of setClaimYear(String strClaimYear)
	
	/** Retrieve the CitiClmSeqID
	 * @return Returns the lngCitiClmSeqID.
	 */
	public Long getCitiClmSeqID() {
		return lngCitiClmSeqID;
	}//end of getCitiClmSeqID()
	
	/** Sets the CitiClmSeqID
	 * @param lngCitiClmSeqID The lngCitiClmSeqID to set.
	 */
	public void setCitiClmSeqID(Long lngCitiClmSeqID) {
		this.lngCitiClmSeqID = lngCitiClmSeqID;
	}//end of setCitiClmSeqID(Long lngCitiClmSeqID)
	
	/** Retrieve the CitiEnrolSeqID
	 * @return Returns the lngCitiEnrolSeqID.
	 */
	public Long getCitiEnrolSeqID() {
		return lngCitiEnrolSeqID;
	}//end of getCitiEnrolSeqID()
	
	/** Sets the CitiEnrolSeqID
	 * @param lngCitiEnrolSeqID The lngCitiEnrolSeqID to set.
	 */
	public void setCitiEnrolSeqID(Long lngCitiEnrolSeqID) {
		this.lngCitiEnrolSeqID = lngCitiEnrolSeqID;
	}//end of setCitiEnrolSeqID(Long lngCitiEnrolSeqID)
	
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
	
	/** Retrieve the CustCode
	 * @return Returns the strCustCode.
	 */
	public String getCustCode() {
		return strCustCode;
	}//end of getCustCode()
	
	/** Sets the CustCode
	 * @param strCustCode The strCustCode to set.
	 */
	public void setCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}//end of setCustCode(String strCustCode)
	
	/** Retrieve the OrderNbr
	 * @return Returns the strOrderNbr.
	 */
	public String getOrderNbr() {
		return strOrderNbr;
	}//end of getOrderNbr()
	
	/** Sets the OrderNbr
	 * @param strOrderNbr The strOrderNbr to set.
	 */
	public void setOrderNbr(String strOrderNbr) {
		this.strOrderNbr = strOrderNbr;
	}//end of setOrderNbr(String strOrderNbr)
	
}//end of CitibankHistoryVO
