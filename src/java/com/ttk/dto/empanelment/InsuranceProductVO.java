/**
 * @ (#) InsuranceCompanyProductVO.java Oct 19, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceCompanyProductVO.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 19, 2005
 *
 * @author       :  Ravindra

 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;

public class InsuranceProductVO extends InsuranceVO {
	
	private Long lngProductID=null;
	private Long lngAssocOffSeqId = null;
	private String strProductName="";
	private BigDecimal bdCommission =null;
	private String strEnrollmentType="";
	private String strEnrollmentDesc="";
	private Date dtStartDate=null;
	private Date dtEndDate=null;

	/** This method returns the Commission to TPA
	 * @return Returns the bdCommission.
	 */
	public BigDecimal getCommissionPercentage() {
		return bdCommission;
	}// End of getCommissionPercentage()
    
	/** This method sets the Commission to TPA
	 * @param bdCommission The bdCommission to set.
	 */
	public void setCommissionPercentage(BigDecimal bdCommission) {
	    this.bdCommission = bdCommission;
	}// End of setCommissionPercentage(BigDecimal bdCommission)
    
	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getFormattedEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}// End of getEndDate()
	
	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}// End of getEndDate()
    
	/** This method sets the End Date
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}// End of setEndDate(Date dtEndDate)
    
	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}// End of getStartDate()
	
	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getFormattedStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}// End of getStartDate()
    
	/** This method sets the StartDate
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}// End of setStartDate
    
	/** This method returns the Product ID
	 * @return Returns the lProductID.
	 */
	public Long getProductID() {
		return lngProductID;
	}// End of getProductID()
    
	/** This method sets the Product ID
	 * @param  The lngProductID to set.
	 */
	public void setProductID(Long lngProductID) {
		this.lngProductID = lngProductID;
	}// End of setProductID(Long lngProductID)
    
	/** This method returns the Enrollment Type
	 * @return Returns the strEnrollmentType.
	 */
	public String getEnrollmentType() {
		return strEnrollmentType;
	}// End of getEnrollmentType()
    
	/** This method sets the EnrollmentType
	 * @param strEnrollmentType The strEnrollmentType to set.
	 */
	public void setEnrollmentType(String strEnrollmentType) {
		this.strEnrollmentType = strEnrollmentType;
	}// End of setEnrollmentType(String strEnrollmentType)
    
	/** This method returns the Product Name
	 * @return Returns the strProductName.
	 */
	public String getProductName() {
		return strProductName;
	}// End of getProductName()
    
	/** This method sets the Product Name
	 * @param strProductName The strProductName to set.
	 */
	public void setProductName(String strProductName) {
		this.strProductName = strProductName;
	}// End of setProductName(String strProductName)
	
	/** THis method returns the Associated Office Sequence ID
	 * @return Returns the lngAssocOffSeqId.
	 */
	public Long getAssocOffSeqId() {
		return lngAssocOffSeqId;
	}// End of getAssocOffSeqId()
    
	/** This method sets the Associated Office Sequence ID
	 * @param assocOffSeqId The lngAssocOffSeqId to set.
	 */
	public void setAssocOffSeqId(Long lngAssocOffSeqId) {
		this.lngAssocOffSeqId = lngAssocOffSeqId;
	}// End of setAssocOffSeqId(Long lngAssocOffSeqId)
    
	/** This method returns the Enrollment Description
	 * @return Returns the strEnrollmentDesc.
	 */
	public String getEnrollmentDesc() {
		return strEnrollmentDesc;
	}// End of getEnrollmentDesc() 
    
	/** This method sets the EnrollMentDescription
	 * @param strEnrollmentDesc The strEnrollmentDesc to set.
	 */
	public void setEnrollmentDesc(String strEnrollmentDesc) {
		this.strEnrollmentDesc = strEnrollmentDesc;
	}// End of setEnrollmentDesc(String strEnrollmentDesc) 
}// End of InsuranceProductVO
