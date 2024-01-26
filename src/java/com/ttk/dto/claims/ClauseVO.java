/**
 * @ (#) ClauseVO.java Jul 9, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ClauseVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 9, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class ClauseVO extends BaseVO{
	
	private Long lngClaimSeqID = null;
	private String strRejHeaderInfo = "";//REJ_HEADER_INFO
	private String strRejFooterInfo = "";//REJ_FOOTER_INFO
	private ArrayList alPolicyClauseVO = null;
	private String strLetterTypeID = "";//LTR_GENERAL_TYPE_ID
	private String strClaimTypeID = "";//CLAIM_GENERAL_TYPE_ID
	private Long lngInsSeqID = null;//INS_SEQ_ID
	
	/** Retrieve the Ins Seq ID
	 * @return Returns the lngInsSeqID.
	 */
	public Long getInsSeqID() {
		return lngInsSeqID;
	}//end of getInsSeqID()

	/** Sets the Ins Seq ID
	 * @param lngInsSeqID The lngInsSeqID to set.
	 */
	public void setInsSeqID(Long lngInsSeqID) {
		this.lngInsSeqID = lngInsSeqID;
	}//end of setInsSeqID(Long lngInsSeqID)

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

	/** Retrieve the LetterTypeID
	 * @return Returns the strLetterTypeID.
	 */
	public String getLetterTypeID() {
		return strLetterTypeID;
	}//end of getLetterTypeID()

	/** Sets the LetterTypeID
	 * @param strLetterTypeID The strLetterTypeID to set.
	 */
	public void setLetterTypeID(String strLetterTypeID) {
		this.strLetterTypeID = strLetterTypeID;
	}//end of setLetterTypeID(String strLetterTypeID)

	/** Retrieve the ArrayList of PolicyClauseVO
	 * @return Returns the alPolicyClauseVO.
	 */
	public ArrayList getPolicyClauseVO() {
		return alPolicyClauseVO;
	}//end of getPolicyClauseVO()
	
	/** Sets the ArrayList of PolicyClauseVO
	 * @param alPolicyClauseVO The alPolicyClauseVO to set.
	 */
	public void setPolicyClauseVO(ArrayList alPolicyClauseVO) {
		this.alPolicyClauseVO = alPolicyClauseVO;
	}//end of setPolicyClauseVO(ArrayList alPolicyClauseVO)
	
	/** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()
	
	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)
	
	/** Retrieve the RejFooterInfo
	 * @return Returns the strRejFooterInfo.
	 */
	public String getRejFooterInfo() {
		return strRejFooterInfo;
	}//end of getRejFooterInfo()
	
	/** Sets the RejFooterInfo
	 * @param strRejFooterInfo The strRejFooterInfo to set.
	 */
	public void setRejFooterInfo(String strRejFooterInfo) {
		this.strRejFooterInfo = strRejFooterInfo;
	}//end of setRejFooterInfo(String strRejFooterInfo)
	
	/** Retrieve the RejHeaderInfo
	 * @return Returns the strRejHeaderInfo.
	 */
	public String getRejHeaderInfo() {
		return strRejHeaderInfo;
	}//end of getRejHeaderInfo()
	
	/** Sets the RejHeaderInfo
	 * @param strRejHeaderInfo The strRejHeaderInfo to set.
	 */
	public void setRejHeaderInfo(String strRejHeaderInfo) {
		this.strRejHeaderInfo = strRejHeaderInfo;
	}//end of setRejHeaderInfo(String strRejHeaderInfo)

}//end of ClauseVO
