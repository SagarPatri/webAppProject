/**
 * @ (#) DomiciliaryVO.java Mar 7, 2006
 * Project 	     : TTK HealthCare Services
 * File          : DomiciliaryVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 7, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;


public class DomiciliaryVO extends BaseVO{
    
    private Long lngMemberSeqID = null;
    private Long lngPolicyGroupSeqID = null;
    private String strName = "";
    private String strDomiciliaryTypeID = "";
    private String strDomiciliaryTypeDesc = "";
    private BigDecimal bdLimit = null;
    private BigDecimal bdFamilyLimit = null;
    private BigDecimal bdHospAmt = null;
    private String strDomHospAmt = "";
    private String strDomiciliaryLimit = "";
    private String strOverallfamilyLimit = "";
    //added for Policy Deductable - KOC-1277
    private String strPolicyDeductableTypeId = "";//New Flag given for Deductable PFL/PNF
    private String strPolicyDeductableLimit = ""; //
    
    private String strOverallfamilyDeductableLimit = "";
    
    private String strMemberDeductableYN = ""; //selected checkbox value for each member
    private String strOverallFamilyCheckYN = ""; //
    private String strMemberDeductable = "";
    private String strOverallFamilyCheck = "";
    private BigDecimal bdDedLimit = null;
    
    private String strMemberYn = "";
    



 /** Retrieve the HospAmt
	 * @return Returns the bdHospAmt.
	 */
	public BigDecimal getHospAmt() {
		return bdHospAmt;
	}//end of getHospAmt()

	/** Sets the HospAmt
	 * @param bdHospAmt The bdHospAmt to set.
	 */
	public void setHospAmt(BigDecimal bdHospAmt) {
		this.bdHospAmt = bdHospAmt;
	}//end of setHospAmt(BigDecimal bdHospAmt)

	/** Retrieve the DomHospAmt
	 * @return Returns the strDomHospAmt.
	 */
	public String getDomHospAmt() {
		return strDomHospAmt;
	}//end of getDomHospAmt()

	/** Sets the DomHospAmt
	 * @param strDomHospAmt The strDomHospAmt to set.
	 */
	public void setDomHospAmt(String strDomHospAmt) {
		this.strDomHospAmt = strDomHospAmt;
	}//end of setDomHospAmt(String strDomHospAmt)

	/** This method returns the Overall Family Limit
     * @return Returns the bdFamilyLimit.
     */
    public BigDecimal getFamilyLimit() {
        return bdFamilyLimit;
    }//end of getFamilyLimit()
    
    /** This method sets the Overall Family Limit
     * @param bdFamilyLimit The bdFamilyLimit to set.
     */
    public void setFamilyLimit(BigDecimal bdFamilyLimit) {
        this.bdFamilyLimit = bdFamilyLimit;
    }//end of setFamilyLimit(BigDecimal bdFamilyLimit)
    
    /** This method returns the Limit
     * @return Returns the bdLimit.
     */
    public BigDecimal getLimit() {
        return bdLimit;
    }//end of getLimit()
    
    /** This method sets the Limit 
     * @param bdLimit The bdLimit to set.
     */
    public void setLimit(BigDecimal bdLimit) {
        this.bdLimit = bdLimit;
    }//end of setLimit(BigDecimal bdLimit)
    
    /** This method returns the Member Seq ID
     * @return Returns the lngMemberSeqID.
     */
    public Long getMemberSeqID() {
        return lngMemberSeqID;
    }//end of getMemberSeqID()
    
    /** This method sets the Member Seq ID
     * @param lngMemberSeqID The lngMemberSeqID to set.
     */
    public void setMemberSeqID(Long lngMemberSeqID) {
        this.lngMemberSeqID = lngMemberSeqID;
    }//end of setMemberSeqID(Long lngMemberSeqID)
    
    /** This method returns the Policy Group Seq ID
     * @return Returns the lngPolicyGroupSeqID.
     */
    public Long getPolicyGroupSeqID() {
        return lngPolicyGroupSeqID;
    }//end of getPolicyGroupSeqID()
    
    /** This method sets the Policy Group Seq ID
     * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
     */
    public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
        this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
    }//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)
    
    /** This method returns the Member Name
     * @return Returns the strName.
     */
    public String getName() {
        return strName;
    }//end of getName()
    
    /** This method sets the Member Name
     * @param strName The strName to set.
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
    
    /** This method returns the Domiciliary General Type Description
     * @return Returns the strDomiciliaryTypeDesc.
     */
    public String getDomiciliaryTypeDesc() {
        return strDomiciliaryTypeDesc;
    }//end of getDomiciliaryTypeDesc()
    
    /** This method sets the Domiciliary General Type Description
     * @param strDomiciliaryTypeDesc The strDomiciliaryTypeDesc to set.
     */
    public void setDomiciliaryTypeDesc(String strDomiciliaryTypeDesc) {
        this.strDomiciliaryTypeDesc = strDomiciliaryTypeDesc;
    }//end of setDomiciliaryTypeDesc(String strDomiciliaryTypeDesc)
    
    /** This method returns the Domiciliary General Type ID
     * @return Returns the strDomiciliaryTypeID.
     */
    public String getDomiciliaryTypeID() {
        return strDomiciliaryTypeID;
    }//end of getDomiciliaryTypeID()
    
    /** This method sets the Domiciliary General Type ID
     * @param strDomiciliaryTypeID The strDomiciliaryTypeID to set.
     */
    public void setDomiciliaryTypeID(String strDomiciliaryTypeID) {
        this.strDomiciliaryTypeID = strDomiciliaryTypeID;
    }//end of setDomiciliaryTypeID(String strDomiciliaryTypeID)
    
    /** This method returns the Domiciliary Limit
     * @return Returns the strDomiciliaryLimit.
     */
    public String getDomiciliaryLimit() {
        return strDomiciliaryLimit;
    }//end of getDomiciliaryLimit()
    
    /** This method sets the Domiciliary Limit
     * @param strDomiciliaryLimit The strDomiciliaryLimit to set.
     */
    public void setDomiciliaryLimit(String strDomiciliaryLimit) {
        this.strDomiciliaryLimit = strDomiciliaryLimit;
    }//end of setDomiciliaryLimit(String strDomiciliaryLimit)
    
    /** This method returns the Over All Family Limit
     * @return Returns the strOverallfamilyLimit.
     */
    public String getOverallfamilyLimit() {
        return strOverallfamilyLimit;
    }//end of getOverallfamilyLimit()
    
    /** This method sets the Over All Family Limit
     * @param strOverallfamilyLimit The strOverallfamilyLimit to set.
     */
    public void setOverallfamilyLimit(String strOverallfamilyLimit) {
        this.strOverallfamilyLimit = strOverallfamilyLimit;
    }//end of setOverallfamilyLimit(String strOverallfamilyLimit)
	 //added for Policy Deductable - KOC-1277
    public String getPolicyDeductableTypeId() {
		return strPolicyDeductableTypeId;
	}

	public void setPolicyDeductableTypeId(String strPolicyDeductableTypeId) {
		this.strPolicyDeductableTypeId = strPolicyDeductableTypeId;
	}

	public String getPolicyDeductableLimit() {
		return strPolicyDeductableLimit;
	}

	public void setPolicyDeductableLimit(String strPolicyDeductableLimit) {
		this.strPolicyDeductableLimit = strPolicyDeductableLimit;
	}

	/*public String getPolicyDeductablePercLimit() {
		return strPolicyDeductablePercLimit;
	}

	public void setPolicyDeductablePercLimit(String strPolicyDeductablePercLimit) {
		this.strPolicyDeductablePercLimit = strPolicyDeductablePercLimit;
	}*/

	public String getOverallfamilyDeductableLimit() {
		return strOverallfamilyDeductableLimit;
	}

	public void setOverallfamilyDeductableLimit(String strOverallfamilyDeductableLimit) {
		this.strOverallfamilyDeductableLimit = strOverallfamilyDeductableLimit;
	}

	/*public String getOverallfamilyDeductablePercLimit() {
		return strOverallfamilyDeductablePercLimit;
	}

	public void setOverallfamilyDeductablePercLimit(String strOverallfamilyDeductablePercLimit) {
		this.strOverallfamilyDeductablePercLimit = strOverallfamilyDeductablePercLimit;
	}*/
	public String getMemberDeductableYN() {
		return strMemberDeductableYN;
	}

	public void setMemberDeductableYN(String strMemberDeductableYN) {
		this.strMemberDeductableYN = strMemberDeductableYN;
	}
	public String getOverallFamilyCheckYN() {
		return strOverallFamilyCheckYN;
	}

	public void setOverallFamilyCheckYN(String strOverallFamilyCheckYN) {
		this.strOverallFamilyCheckYN = strOverallFamilyCheckYN;
	}

	public void setMemberDeductable(String strMemberDeductable) {
		this.strMemberDeductable = strMemberDeductable;
	}

	public String getMemberDeductable() {
		return strMemberDeductable;
	}

	public void setOverallFamilyCheck(String strOverallFamilyCheck) {
		this.strOverallFamilyCheck = strOverallFamilyCheck;
	}

	public String getOverallFamilyCheck() {
		return strOverallFamilyCheck;
	}
	public BigDecimal getDedLimit() {
		return bdDedLimit;
	}

	public void setDedLimit(BigDecimal bdDedLimit) {
		this.bdDedLimit = bdDedLimit;
	}
	/*public BigDecimal getDedPerLimit() {
		return bdDedPerLimit;
	}

	public void setDedPerLimit(BigDecimal bdDedPerLimit) {
		this.bdDedPerLimit = bdDedPerLimit;
	}*/
	public String getMemberYn() {
		return strMemberYn;
	}
	public void setMemberYn(String strMemberYn) {
		this.strMemberYn = strMemberYn;
	}

}//end of DomiciliaryVO
