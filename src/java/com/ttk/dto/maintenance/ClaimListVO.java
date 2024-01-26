/**
 * @ (#) ClaimListVO.java
 * Project 	     : TTK HealthCare Services
 * File          : ClaimListVO.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : 10th August, 2010
 *
 * @author       :
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.maintenance;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class ClaimListVO extends BaseVO {
    
    private Long lngClaimSeqID = null;  //CLAIM_SEQ_ID  
    private String strClaimNbr = null;  //CLAIM_NUMBER
    private String strPolicyNbr = "";//POLICY_NUMBER
    private String strClaimantName = "";//CLAIMANT NAME
    private String strEnrollmentID = "";//Enrollment ID
    private BigDecimal bdPreClmReqAmt = null;// Present Claim Request Amount
    private BigDecimal bgNewClmReqAmt = null;// New Claim Request Amount
    private String strRemarks = "";//Remarks
        
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }//end of getPolicyNbr()
    
    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)

	/** Retrieve the Claim Seq ID
	 * @return the lngClaimSeqID
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()

	/** Sets the Claim Seq ID
	 * @param lngClaimSeqID the lngClaimSeqID to set
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID) 

	/** Retrieve the Claim Number
	 * @return the strClaimNbr
	 */
	public String getClaimNbr() {
		return strClaimNbr;
	}//end of getClaimNbr() 

	/** Sets the Claim Nubmer
	 * @param strClaimNbr the strClaimNbr to set
	 */
	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}//end of setClaimNbr(String strClaimNbr) 

	/** Retrieve the Claimant Name
	 * @return the strClaimantName
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getClaimantName()

	/** Sets the Claimant Name
	 * @param strClaimantName the strClaimantName to set
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setClaimantName(String strClaimantName) 

	/** Retrieve the Enrollment ID
	 * @return the strEnrollmentID
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()

	/** Sets the Enrollment ID
	 * @param strEnrollmentID the strEnrollmentID to set
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)

	/** Retrieve the Pre Claim Request Amount
	 * @return the bdPreClmReqAmt
	 */
	public BigDecimal getPreClmReqAmt() {
		return bdPreClmReqAmt;
	}//end of getPreClmReqAmt() 

	/** Sets the Pre Claim Request Amount
	 * @param bdPreClmReqAmt the bdPreClmReqAmt to set
	 */
	public void setPreClmReqAmt(BigDecimal bdPreClmReqAmt) {
		this.bdPreClmReqAmt = bdPreClmReqAmt;
	}//end of setPreClmReqAmt(BigDecimal bdPreClmReqAmt)

	/** Retrieve the New Claim Request Amount
	 * @return the bgNewClmReqAmt
	 */
	public BigDecimal getNewClmReqAmt() {
		return bgNewClmReqAmt;
	}//end of getNewClmReqAmt() 

	/** Sets teh New Claim Request Amount
	 * @param bgNewClmReqAmt the bgNewClmReqAmt to set
	 */
	public void setNewClmReqAmt(BigDecimal bgNewClmReqAmt) {
		this.bgNewClmReqAmt = bgNewClmReqAmt;
	}//end of setNewClmReqAmt(BigDecimal bdPreClmReqAmt) 

	/** Retrieve the Remarks
	 * @return the strRemarks
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks() 

	/** Retrieve the Remarks
	 * @param strRemarks the strRemarks to set
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)
    
 }//end of ClaimListVO