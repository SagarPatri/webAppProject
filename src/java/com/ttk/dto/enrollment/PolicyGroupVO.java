/**
 * @ (#) PolicyGroupVO.java Feb 2, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PolicyGroupVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 2, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import com.ttk.dto.BaseVO;


public class PolicyGroupVO extends BaseVO {
    
    private String strGroupID = "";
    private String strGroupName = "";
    private String strBranchName = "";
    private String strCompanyName = "";
    private String strPolicyNo="";
    private String strEffFromDate="";
    private String strEffToDate="";
    //private String strExpiryDate="";
    private Long lngParentGrpSeqID=null;
    private Long lngGroupRegnSeqID = null;
    private String policyCategory="";
    
    

    public String getPolicyCategory() {
		return policyCategory;
	}

	public void setPolicyCategory(String policyCategory) {
		this.policyCategory = policyCategory;
	}

	/** This method returns the Group Reg Seq ID
     * @return Returns the lngGroupRegnSeqID.
     */
    public Long getGroupRegnSeqID() {
        return lngGroupRegnSeqID;
    }//end of getGroupRegnSeqID()
    
    /** This method sets the Group Reg Seq ID
     * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
     */
    public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
        this.lngGroupRegnSeqID = lngGroupRegnSeqID;
    }//end of setGroupRegnSeqID(Long lngGroupRegnSeqID)
    
    /** This method returns the Branch Name
     * @return Returns the strBranchName.
     */
    public String getBranchName() {
        return strBranchName;
    }//end of getBranchName()
    
    /** This method sets the Branch Name
     * @param strBranchName The strBranchName to set.
     */
    public void setBranchName(String strBranchName) {
        this.strBranchName = strBranchName;
    }//end of setBranchName(String strBranchName)
    
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
    
    /** This method returns the Group ID
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()
    
    /** This method sets the Group ID
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)
    
    /** This method returns the Group Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()
    
    /** This method sets the Group Name
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)

	/**
	 * @return Returns the lngParentGrpSeqID.
	 */
	public Long getParentGroupSeqID() {
		return lngParentGrpSeqID;
	}

	/**
	 * @param lngParentGrpSeqID The lngParentGrpSeqID to set.
	 */
	public void setParentGroupSeqID(Long lngParentGrpSeqID) {
		this.lngParentGrpSeqID = lngParentGrpSeqID;
	}

	/**
	 * @return Returns the strEffFromDate.
	 */
	public String getEffFromDate() {
		return strEffFromDate;
	}

	/**
	 * @param strEffFromDate The strEffFromDate to set.
	 */
	public void setEffFromDate(String strEffFromDate) {
		this.strEffFromDate = strEffFromDate;
	}

	/**
	 * @return Returns the strEffToDate.
	 */
	public String getEffToDate() {
		return strEffToDate;
	}

	/**
	 * @param strEffToDate The strEffToDate to set.
	 */
	public void setEffToDate(String strEffToDate) {
		this.strEffToDate = strEffToDate;
	}

	/**
	 * @return Returns the strPolicyNo.
	 */
	public String getPolicyNo() {
		return strPolicyNo;
	}

	/**
	 * @param strPolicyNo The strPolicyNo to set.
	 */
	public void setPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	/**
	 * @return Returns the strExpiryDate.
	 *//*
	public String getExpiryDate() {
		return strExpiryDate;
	}

	*//**
	 * @param strExpiryDate The strExpiryDate to set.
	 *//*
	public void setExpiryDate(String strExpiryDate) {
		this.strExpiryDate = strExpiryDate;
	}*/
}//end of PolicyGroupVO
