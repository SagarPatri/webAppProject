/** 
 *   @ (#) BankVO.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankVO.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 07, 2006
 *  
 *   @author       :  Balakrishna E
 *   Modified by   : 
 *   Modified date : 
 *   Reason        : 
 */

package com.ttk.dto.finance;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

/**
 * This VO contains bank information.
 */

public class BankVO extends BaseVO{
	
	private Long lngBankSeqID;
	private String strBankName;
	private String strCityDesc;
	private String strOfficeTypeDesc;
    private String strAddress;
    private String strBranchName = "";
    private ArrayList alBranch = null;
    
    /** This method returns the child objects for the tree
     * @return Returns the alBranch loaded with child objects.
     */
    public ArrayList getBranch() {
        return alBranch;
    }// End of getBranch()
    
    /** This method sets the ArrayList loaded with child objects.
     * @param alBranch The ArrayList containing the child objects.
     */
    public void setBranch(ArrayList alBranch) {
        this.alBranch = alBranch;
    }// End of setBranch(ArrayList alBranch)
    
	/** Retrieve the Branch Name
	 * @return Returns the strBranchName.
	 */
	public String getBranchName() {
		return strBranchName;
	}//end of getBranchName()

	/** Sets the Branch Name
	 * @param strBranchName The strBranchName to set.
	 */
	public void setBranchName(String strBranchName) {
		this.strBranchName = strBranchName;
	}//end of setBranchName(String strBranchName)

	/** Retrieve the Bank Seq ID
	 * @return Returns the lngBankSeqID.
	 */
	public Long getBankSeqID() {
		return lngBankSeqID;
	}//end of getBankSeqID()
	
	/** Sets the Bank Seq ID
	 * @param lngBankSeqID The lngBankSeqID to set.
	 */
	public void setBankSeqID(Long lngBankSeqID) {
		this.lngBankSeqID = lngBankSeqID;
	}//end of setBankSeqID(Long lngBankSeqID)
	
	/** Retrieve the Bank name
	 * @return Returns the strBankName.
	 */
	public String getBankName() {
		return strBankName;
	}//end of getBankName()
	
	/** Sets the Bank name
	 * @param strBankName The strBankName to set.
	 */
	public void setBankName(String strBankName) {
		this.strBankName = strBankName;
	}//end of setBankName(String strBankName)
	
	/** Retrieve the City Description
	 * @return Returns the strCityDesc.
	 */
	public String getCityDesc() {
		return strCityDesc;
	}//end of getCityDesc()
	
	/** Sets the City Description
	 * @param strCityDesc The strCityDesc to set.
	 */
	public void setCityDesc(String strCityDesc) {
		this.strCityDesc = strCityDesc;
	}//end of setCityDesc(String strCityDesc)
	
    /**Retrieve the Address.
     * @return Returns the strAddress.
     */
    public String getAddress() {
        return strAddress;
    }//end of getAddress()
    
    /**Sets the Address.
     * @param strAddress The strAddress to set.
     */
    public void setAddress(String strAddress) {
        this.strAddress = strAddress;
    }//end of setAddress(String strAddress)
    
    /**Sets the OfficeType Description.
     * @return Returns the strOfficeTypeDesc.
     */
    public String getOfficeTypeDesc() {
        return strOfficeTypeDesc;
    }//end of getOfficeTypeDesc()
    
    /**Retrieve the OfficeType Description.
     * @param strOfficeTypeDesc The strOfficeTypeDesc to set.
     */
    public void setOfficeTypeDesc(String strOfficeTypeDesc) {
        this.strOfficeTypeDesc = strOfficeTypeDesc;
    }//end of setOfficeTypeDesc(String strOfficeTypeDesc)
    
}//end of BankVO