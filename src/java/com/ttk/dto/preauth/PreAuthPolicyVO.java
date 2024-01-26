/**
* @ (#) PreAuthPolicyVO.java Apr 10, 2006
* Project 		: TTK HealthCare Services
* File 			: PreAuthPolicyVO.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Apr 10, 2006
*
* @author 		: Pradeep R
* Modified by 	: 
* Modified date : 
* Reason 		: 
*/

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.BaseVO;

public class PreAuthPolicyVO extends BaseVO {
	private Long lngPolicySeqID = null;
    private Long lngProductSeqID = null;
    private String strProductDesc = "";
    private Long lngInsSeqID = null;
    private String strCompanyName = "";
    private String strCorporateName = "";
	private String strPolicyNo = "";
	private String strPolicyHolderName = "";
	private String strPhone = "";
	private String strTermStatusID = "";
    private String strTermStatusDesc = "";
	private String strPolicyTypeID = "";
    private String strPolicyTypeDesc = "";
	private String strPolicySubTypeID = "";
    private String strPolicySubTypeDesc = "";
	private Date dtStartDate = null;
	private Date dtEndDate = null;
    private BigDecimal bdTotSumInsured = null;
    private String strRemarks = "";
    private String strMobileNo = "";
	private BigDecimal bdAvblBufferAmount = null;
	private BigDecimal bdCumulativeBonus = null;
	
	/** Retrieve the Product Description
	 * @return Returns the strProductDesc.
	 */
	public String getProductDesc() {
		return strProductDesc;
	}//end of getProductDesc()

	/** Sets the Product Description
	 * @param strProductDesc The strProductDesc to set.
	 */
	public void setProductDesc(String strProductDesc) {
		this.strProductDesc = strProductDesc;
	}//end of setProductDesc(String strProductDesc)

	/** This method returns the Total Sum Insured
     * @return Returns the bdTotSumInsured.
     */
    public BigDecimal getTotSumInsured() {
        return bdTotSumInsured;
    }//end of getTotSumInsured()
    
    /** This method sets the Total Sum Insured
     * @param bdTotSumInsured The bdTotSumInsured to set.
     */
    public void setTotSuminsured(BigDecimal bdTotSumInsured) {
        this.bdTotSumInsured = bdTotSumInsured;
    }//end of setTotSuminsured(BigDecimal bdTotSumInsured)
    
    /** This method returns the Mobile No.
     * @return Returns the strMobileNo.
     */
    public String getMobileNo() {
        return strMobileNo;
    }//end of getMobileNo()
    
    /** This method sets the Mobile No.
     * @param strMobileNo The strMobileNo to set.
     */
    public void setMobileNo(String strMobileNo) {
        this.strMobileNo = strMobileNo;
    }//end of setMobileNo(String strMobileNo)
    
    /** This method returns the Policy SubType Description
     * @return Returns the strPolicySubTypeDesc.
     */
    public String getPolicySubTypeDesc() {
        return strPolicySubTypeDesc;
    }//end of getPolicySubTypeDesc()
    
    /** This method sets the Policy SubType Description
     * @param strPolicySubTypeDesc The strPolicySubTypeDesc to set.
     */
    public void setPolicySubTypeDesc(String strPolicySubTypeDesc) {
        this.strPolicySubTypeDesc = strPolicySubTypeDesc;
    }//end of setPolicySubTypeDesc(String strPolicySubTypeDesc)
    
    /** This method returns the Policy Type Description
     * @return Returns the strPolicyTypeDesc.
     */
    public String getPolicyTypeDesc() {
        return strPolicyTypeDesc;
    }//end of getPolicyTypeDesc()
    
    /** This method sets the Policy Type Description
     * @param strPolicyTypeDesc The strPolicyTypeDesc to set.
     */
    public void setPolicyTypeDesc(String strPolicyTypeDesc) {
        this.strPolicyTypeDesc = strPolicyTypeDesc;
    }//end of setPolicyTypeDesc(String strPolicyTypeDesc)
    
    /** This method returns the Remarks
     * @return Returns the strRemarks.
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()
    
    /** This method sets the Remarks
     * @param strRemarks The strRemarks to set.
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)
    
    /** This method returns the Term Status Description
     * @return Returns the strTermStatusDesc.
     */
    public String getTermStatusDesc() {
        return strTermStatusDesc;
    }//end of getTermStatusDesc()
    
    /** This method sets the Term Status Description
     * @param strTermStatusDesc The strTermStatusDesc to set.
     */
    public void setTermStatusDesc(String strTermStatusDesc) {
        this.strTermStatusDesc = strTermStatusDesc;
    }//end of setTermStatusDesc(String strTermStatusDesc)
    
    /** This method returns the Insurance Company Name
     * @return Returns the strCompanyName.
     */
    public String getCompanyName() {
        return strCompanyName;
    }//end of getCompanyName()
    
    /** This method sets the Insurance Company Name
     * @param strCompanyName The strCompanyName to set.
     */
    public void setCompanyName(String strCompanyName) {
        this.strCompanyName = strCompanyName;
    }//end of setCompanyName(String strCompanyName)
    
    /** This method returns the Corporate Name
     * @return Returns the strCorporateName.
     */
    public String getCorporateName() {
        return strCorporateName;
    }//end of getCorporateName()
    
    /** This method sets the Corporate Name
     * @param strCorporateName The strCorporateName to set.
     */
    public void setCorporateName(String strCorporateName) {
        this.strCorporateName = strCorporateName;
    }//end of setCorporateName(String strCorporateName)
    
    /** This method returns the Insurance Seq ID
     * @return Returns the lngInsSeqID.
     */
    public Long getInsSeqID() {
        return lngInsSeqID;
    }//end of getInsSeqID()
    
    /** This method sets the Insurance Seq ID
     * @param lngInsSeqID The lngInsSeqID to set.
     */
    public void setInsSeqID(Long lngInsSeqID) {
        this.lngInsSeqID = lngInsSeqID;
    }//end of setInsSeqID(Long lngInsSeqID)
    
    /** This method returns the Product Seq ID
     * @return Returns the lngProductSeqID.
     */
    public Long getProductSeqID() {
        return lngProductSeqID;
    }//end of getProductSeqID()
    
    /** This method sets the Product Seq ID
     * @param lngProductSeqID The lngProductSeqID to set.
     */
    public void setProductSeqID(Long lngProductSeqID) {
        this.lngProductSeqID = lngProductSeqID;
    }//end of setProductSeqID(Long lngProductSeqID)
    
    /** This method returns the AvblBufferAmount
	 * @return Returns the bdAvblBufferAmount.
	 */
	public BigDecimal getAvblBufferAmount() {
		return bdAvblBufferAmount;
	}//End of getAvblBufferAmount() 
	
	/** This method sets AvblBufferAmount
	 * @param bdAvblBufferAmount The bdAvblBufferAmount to set.
	 */
	public void setAvblBufferAmount(BigDecimal bdAvblBufferAmount) {
		this.bdAvblBufferAmount = bdAvblBufferAmount;
	}//End of setAvblBufferAmount(BigDecimal bdAvblBufferAmount)
	
	/** This method returns the Cumulative Bonus
	 * @return Returns the bdCumulativeBonus.
	 */
	public BigDecimal getCumulativeBonus() {
		return bdCumulativeBonus;
	}//End of getCumulativeBonus() 
	
	/** This method sets Cumulative Bonus
	 * @param bdCumulativeBonus The bdCumulativeBonus to set.
	 */
	public void setCumulativeBonus(BigDecimal bdCumulativeBonus) {
		this.bdCumulativeBonus = bdCumulativeBonus;
	}//End of setCumulativeBonus(BigDecimal bdCumulativeBonus)
	
	/** This method returns the EndDate
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//End of getEndDate()
	
	/** This method sets EndDate
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//End of setEndDate(Date dtEndDate)
	
	/** This method returns the StartDate
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//End of getStartDate()
	
	/** This method sets StartDate
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//End of setStartDate(Date dtStartDate)
	
	/** This method returns the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//End of getPolicySeqID()
	
	/**   This method sets PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//End of setPolicySeqID(Long lngPolicySeqID)
	
	/** This method returns the Phone
	 * @return Returns the strPhone.
	 */
	public String getPhone() {
		return strPhone;
	}//End of getPhone()
	
	/** This method sets Phone
	 * @param strPhone The strPhone to set.
	 */
	public void setPhone(String strPhone) {
		this.strPhone = strPhone;
	}//End of setPhone(String strPhone)
	
	/** This method returns the PolicyHolderName
	 * @return Returns the strPolicyHolderName.
	 */
	public String getPolicyHolderName() {
		return strPolicyHolderName;
	}//End of getPolicyHolderName() 
	
	/** This method sets PolicyHolderName
	 * @param strPolicyHolderName The strPolicyHolderName to set.
	 */
	public void setPolicyHolderName(String strPolicyHolderName) {
		this.strPolicyHolderName = strPolicyHolderName;
	}//End of setPolicyHolderName(String strPolicyHolderName)
	
	/** This method returns the PolicyNo
	 * @return Returns the strPolicyNo.
	 */
	public String getPolicyNo() {
		return strPolicyNo;
	}//End of getPolicyNo()
	
	/** This method sets PolicyNo
	 * @param strPolicyNo The strPolicyNo to set.
	 */
	public void setPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}//End of setPolicyNo(String strPolicyNo)
	 
	/** This method returns the PolicySubTypeID
	 * @return Returns the strPolicySubTypeID.
	 */
	public String getPolicySubTypeID() {
		return strPolicySubTypeID;
	}//End of getPolicySubTypeID()
	
	/** This method sets PolicySubTypeID
	 * @param strPolicySubTypeID The strPolicySubTypeID to set.
	 */
	public void setPolicySubTypeID(String strPolicySubTypeID) {
		this.strPolicySubTypeID = strPolicySubTypeID;
	}//End of setPolicySubTypeID(String strPolicySubTypeID)
	 
	/**  This method returns the PolicyTypeID
	 * @return Returns the strPolicyTypeID.
	 */
	public String getPolicyTypeID() {
		return strPolicyTypeID;
	}//End of getPolicyTypeID()
	
	/** This method sets PolicyTypeID
	 * @param strPolicyTypeID The strPolicyTypeID to set.
	 */
	public void setPolicyTypeID(String strPolicyTypeID) {
		this.strPolicyTypeID = strPolicyTypeID;
	}//End of setPolicyTypeID(String strPolicyTypeID)
	
	/** This method returns the TermStatusID
	 * @return Returns the strTermStatusID.
	 */
	public String getTermStatusID() {
		return strTermStatusID;
	}//End of getTermStatusID()
	
	/** This method sets TermStatusID
	 * @param strTermStatusID The strTermStatusID to set.
	 */
	public void setTermStatusID(String strTermStatusID) {
		this.strTermStatusID = strTermStatusID;
	}//End of setTermStatusID(String strTermStatusID)
}


