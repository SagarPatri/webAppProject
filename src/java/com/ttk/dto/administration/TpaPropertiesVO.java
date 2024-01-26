/**
 * @ (#) TpaPropertiesVO.java Mar 18, 2006
 * Project       : TTK HealthCare Services
 * File          : TpaPropertiesVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Mar 18, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class TpaPropertiesVO extends BaseVO{
	
	private Long lngOfficeSequenceID = null ;
	private String strPaAllowedYN = "";
	private BigDecimal bdPaLimit = null;
	private String strClaimAllowedYN = "";
	private BigDecimal bdClaimLimit = null;
	private String strCardPrintAllowedYN = "";
	private String strEnrolProcessYN = "";
	
	/** This method returns the TPA office sequence id
	 * @return Returns the lngOfficeSequenceID.
	 */
	public Long getOfficeSequenceID() {
		return lngOfficeSequenceID;
	}// end of getOfficeSequenceID() 
	
	/** This method sets the TPA ofice sequence ID
	 * @param lngOfficeSequenceID The lngOfficeSequenceID to set.
	 */
	public void setOfficeSequenceID(Long lngOfficeSequenceID) {
		this.lngOfficeSequenceID = lngOfficeSequenceID;
	}// End of setOfficeSequenceID(Long lngOfficeSequenceID)
	
	/** This method returns the Claim Limit
	 * @return Returns the bdClaimLimit.
	 */
	public BigDecimal getClaimLimit() {
		return bdClaimLimit;
	}//End of getClaimLimit()
	
	/** This method sets the Claim Limit
	 * @param bdClaimLimit The bdClaimLimit to set.
	 */
	public void setClaimLimit(BigDecimal bdClaimLimit) {
		this.bdClaimLimit = bdClaimLimit;
	}// End of setClaimLimit(BigDecimal bdClaimLimit)
	
	/** This method returns the Pre Authorization Limit
	 * @return Returns the bdPaLimit.
	 */
	public BigDecimal getPaLimit() {
		return bdPaLimit;
	}//getPaLimit()
	
	/** This method sets the Pre Authorization Limit
	 * @param bdPaLimit The bdPaLimit to set.
	 */
	public void setPaLimit(BigDecimal bdPaLimit) {
		this.bdPaLimit = bdPaLimit;
	}//End of setPaLimit(BigDecimal bdPaLimit)
	
	/** This method returns the Card Print Allowed Yes or No
	 * @return Returns the strCardPrintAllowedYN.
	 */
	public String getCardPrintAllowedYN() {
		return strCardPrintAllowedYN;
	}//getCardPrintAllowedYN()
	
	/** This method sets the card print Allowed Yes or No
	 * @param strCardPrintAllowedYN The strCardPrintAllowedYN to set.
	 */
	public void setCardPrintAllowedYN(String strCardPrintAllowedYN) {
		this.strCardPrintAllowedYN = strCardPrintAllowedYN;
	}// end of setCardPrintAllowedYN(String strCardPrintAllowedYN)
	
	/** This method returns the Calim Allowed Yes or Nos
	 * @return Returns the strClaimAllowedYN.
	 */
	public String getClaimAllowedYN() {
		return strClaimAllowedYN;
	}// End of getClaimAllowedYN()
	
	/** This method sets the Calim Alowed Yes or No
	 * @param strClaimAllowedYN The strClaimAllowedYN to set.
	 */
	public void setClaimAllowedYN(String strClaimAllowedYN) {
		this.strClaimAllowedYN = strClaimAllowedYN;
	}// End of setClaimAllowedYN(String strClaimAllowedYN) 
	
	/** This method returns the pre authorization allowed Yes or No
	 * @return Returns the strPaAllowedYN.
	 */
	public String getPaAllowedYN() {
		return strPaAllowedYN;
	}// End of getPaAllowedYN()
	
	/** This method sets the pre authorization allowed Yes or No
	 * @param strPaAllowedYN The strPaAllowedYN to set.
	 */
	public void setPaAllowedYN(String strPaAllowedYN) {
		this.strPaAllowedYN = strPaAllowedYN;
	}//end of setPaAllowedYN(String strPaAllowedYN)

	/** This method returns the Enrollment process Required Yes or No
	 * @return Returns the strEnrolProcessYN.
	 */
	public String getEnrolProcessYN() {
		return strEnrolProcessYN;
	}// end of getEnrolProcessYN()

	/** This method sets the Enrollment process Required Yes or No
	 * @param strEnrolProcessYN The strEnrolProcessYN to set.
	 */
	public void setEnrolProcessYN(String strEnrolProcessYN) {
		this.strEnrolProcessYN = strEnrolProcessYN;
	}// End of setEnrolProcessYN(String strEnrolProcessYN)
	

}//end of TpaPropertiesVO
