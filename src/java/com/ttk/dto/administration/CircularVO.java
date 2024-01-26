/**
 * @ (#)  CircularVO.java Nov 4, 2005
 * Project      : TTKPROJECT
 * File         : CircularVO.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 4, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class CircularVO extends BaseVO{
	private Long lCircularSeqId = null ;     //CIRCULAR_SEQ_ID
	private Long lProductSeqId = null;       //PRODUCT_SEQ_ID
	private Long lPolicySeqId = null;        //POLICY_SEQ_ID  
	private String strCircularNumber = "";   //CIRCULAR_NUMBER
	private Date dtIssuedDate = null;        //CIRCULAR_ISSUED_DATE
	private Date dtRecievedDate= null;       //CIRCULAR_RECEIVED_DATE
	private String strCirclarTitle = "";     //TITLE
	private String strCirclarDesc = "";      //DESCRIPTION
    private Long lInsSeqId = null;
    private Date dtIssueDate = null;
    private Date dtReceiveDate = null;

	/** This method returns the dtIssuedDate
	 * @return Returns the dtIssuedDate.
	 */
	public String getIssuedDate() {
		return TTKCommon.getFormattedDate(dtIssuedDate);
	}// End of getIssuedDate()
	
	/** This method sets the dtIssuedDate
	 * @param dtIssuedDate The dtIssuedDate to set.
	 */
	public void setIssuedDate(Date dtIssuedDate) {
		this.dtIssuedDate = dtIssuedDate;
	}// End of setIssuedDate(Date dtIssuedDate)
	
	/** This method returns dtRecievedDate
	 * @return Returns the dtRecievedDate.
	 */
	public String getRecievedDate() {
        return TTKCommon.getFormattedDate(dtRecievedDate);
	}// End of getRecievedDate()
	
	/** This method sets the dtRecievedDate
	 * @param dtRecievedDate The dtRecievedDate to set.
	 */
	public void setRecievedDate(Date dtRecievedDate) {
		this.dtRecievedDate = dtRecievedDate;
	}// End of setRecievedDate(Date dtRecievedDate)
	
	/** This method returns lCircularSeqId
	 * @return Returns the lCircularSeqId.
	 */
	public Long getCircularSeqId() {
		return lCircularSeqId;
	}// End of getCircularSeqId()
	
	/** This method sets the lCircularSeqId
	 * @param circularSeqId The lCircularSeqId to set.
	 */
	public void setCircularSeqId(Long circularSeqId) {
		lCircularSeqId = circularSeqId;
	}// End of setCircularSeqId(Long circularSeqId)
	
	/** This method returns lPolicySeqId
	 * @return Returns the lPolicySeqId.
	 */
	public Long getPolicySeqId() {
		return lPolicySeqId;
	}// End of getPolicySeqId()
	
	/** This method returns lPolicySeqId
	 * @param policySeqId The lPolicySeqId to set.
	 */
	public void setPolicySeqId(Long policySeqId) {
		lPolicySeqId = policySeqId;
	}// End of setPolicySeqId(Long policySeqId)
	
	/** This method returns lProductSeqId
	 * @return Returns the lProductSeqId.
	 */
	public Long getProductSeqId() {
		return lProductSeqId;
	}// End of getProductSeqId()
	
	/** This method sets the lProductSeqId
	 * @param productSeqId The lProductSeqId to set.
	 */
	public void setProductSeqId(Long productSeqId) {
		lProductSeqId = productSeqId;
	}// End of  setProductSeqId(Long productSeqId)
	
	/** This method returns the strCirclarDesc
	 * @return Returns the strCirclarDesc.
	 */
	public String getCirclarDesc() {
		return strCirclarDesc;
	}// End of getCirclarDesc()
	
	/** This method sets the strCirclarDesc
	 * @param strCirclarDesc The strCirclarDesc to set.
	 */
	public void setCirclarDesc(String strCirclarDesc) {
		this.strCirclarDesc = strCirclarDesc;
	}// end of setCirclarDesc(String strCirclarDesc)
	
	/** This method returns the strCirclarTitle
	 * @return Returns the strCirclarTitle.
	 */
	public String getCirclarTitle() {
		return strCirclarTitle;
	} // end of getCirclarTitle()
	
	/** This method sets the strCirclarTitle
	 * @param strCirclarTitle The strCirclarTitle to set.
	 */
	public void setCirclarTitle(String strCirclarTitle) {
		this.strCirclarTitle = strCirclarTitle;
	}// End of setCirclarTitle
	
	/** This method returns strCircularNumber
	 * @return Returns the strCircularNumber.
	 */
	public String getCircularNumber() {
		return strCircularNumber;
	}// End of getCircularNumber()
	
	/** this method sets the strCircularNumber
	 * @param strCircularNumber The strCircularNumber to set.
	 */
	public void setCircularNumber(String strCircularNumber) {
		this.strCircularNumber = strCircularNumber;
	}// End of setCircularNumber(String strCircularNumber)
	
    /** Retrieve the Insurance Seq Id
     * @return lInsSeqId Long Insurance Seq Id
     */
    public Long getInsSeqId() {
        return lInsSeqId;
    }//end of getInsSeqId()
    
    /** Sets the Insurance Seq Id
     * @param insSeqId The lInsSeqId to set.
     */
    public void setInsSeqId(Long insSeqId) {
        lInsSeqId = insSeqId;
    }//end of setInsSeqId(Long insSeqId)
    
    /** This method returns the Issue Date
     * @return Returns the dtIssueDate.
     */
    public Date getIssueDate() {
        return dtIssueDate;
    }//end of getIssueDate()
    
    /** This method sets the Issue Date
     * @param dtIssueDate The dtIssueDate to set.
     */
    public void setIssueDate(Date dtIssueDate) {
        this.dtIssueDate = dtIssueDate;
    }//end of setIssueDate(Date dtIssueDate)
    
    /** This method returns the Receive Date
     * @return Returns the dtReceiveDate.
     */
    public Date getReceiveDate() {
        return dtReceiveDate;
    }//end of getReceiveDate()
    
    /** This method sets the Receive Date
     * @param dtReceiveDate The dtReceiveDate to set.
     */
    public void setReceiveDate(Date dtReceiveDate) {
        this.dtReceiveDate = dtReceiveDate;
    }//end of setReceiveDate(Date dtReceiveDate)
}// End of CircularVO
