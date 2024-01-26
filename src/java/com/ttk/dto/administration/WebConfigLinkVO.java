/**
 * @ (#) WebConfigLinkVO.java Dec 20,2007
 * Project 	     : TTK HealthCare Services
 * File          : WebConfigLinkVO.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Dec 20,2007
 *
 * @author       :  Krupa J
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class WebConfigLinkVO extends BaseVO
{
	private Long lngConfigLinkSeqID = null;
	private String strConfigLinkDesc="";
	private String strShowLinkYN = "";
	private String strLinkTypeID = "";
	private String strLinkTypeDesc = "";
	private Integer intOrderNumber = null;
	private String strPath = "";
	private String strReportID="";
	private Long lngProdPolicySeqID = null;
	
	
	/** Retrieve the ConfigLinkSeqID
	 * @return Returns the lngConfigLinkSeqID.
	 */
	public Long getConfigLinkSeqID() {
		return lngConfigLinkSeqID;
	}//end of getConfigLinkSeqID()
	
	/** Sets the ConfigLinkSeqID
	 * @param lngConfigLinkSeqID The lngConfigLinkSeqID to set.
	 */
	public void setConfigLinkSeqID(Long lngConfigLinkSeqID) {
		this.lngConfigLinkSeqID = lngConfigLinkSeqID;
	}//end of setConfigLinkSeqID(Long lngConfigLinkSeqID)
	
	/** Retrieve the ConfigLinkDesc
	 * @return Returns the strConfigLinkDesc.
	 */
	public String getConfigLinkDesc() {
		return strConfigLinkDesc;
	}//end of getConfigLinkDesc()
	
	/** Sets the ConfigLinkDesc
	 * @param strConfigLinkDesc The strConfigLinkDesc to set.
	 */
	public void setConfigLinkDesc(String strConfigLinkDesc) {
		this.strConfigLinkDesc = strConfigLinkDesc;
	}//end of setConfigLinkDesc(String strConfigLinkDesc)
	
	/** Retrives the Show link YN
	 * @return the strShowLinkYN
	 */
	public String getShowLinkYN() {
		return strShowLinkYN;
	}//end of getShowLinkYN()

	/** Sets the  Show link YN 
	 * @param strShowLinkYN the strShowLinkYN to set
	 */
	public void setShowLinkYN(String strShowLinkYN) {
		this.strShowLinkYN = strShowLinkYN;
	}//end of setShowLinkYN(String strShowLinkYN)
	
	/** This method returns the Link Type ID
     * @return Returns the strLinkTypeID.
     */
    public String getLinkTypeID() {
        return strLinkTypeID;
    }//end of getLinkTypeID()
    
    /** This method sets the Link Type ID
     * @param strLinkTypeID The strLinkTypeID to set.
     */
    public void setLinkTypeID(String strLinkTypeID) {
        this.strLinkTypeID = strLinkTypeID;
    }//end of setLinkTypeID(String strLinkTypeID)
    
    /** Retrieves the Link Type Description
	 * @return Returns the strLinkTypeDesc.
	 */
	public String getLinkTypeDesc() {
		return strLinkTypeDesc;
	}//end of getLinkTypeDesc()

	/** Sets the Link Type Description
	 * @param strLinkTypeDesc The strLinkTypeDesc to set.
	 */
	public void setLinkTypeDesc(String strLinkTypeDesc) {
		this.strLinkTypeDesc = strLinkTypeDesc;
	}//end of setLinkTypeDesc(String strLinkTypeDesc)
	
	/** Retrieve the Order Number
	 * @return Returns the intOrderNumber.
	 */
	public Integer getOrderNumber() {
		return intOrderNumber;
	}//end of getModTimeFrame()
	
	/** Sets the Order Number
	 * @param intOrderNumber The intOrderNumber to set.
	 */
	public void setOrderNumber(Integer intOrderNumber) {
		this.intOrderNumber = intOrderNumber;
	}//end of setOrderNumber(Integer intOrderNumber)
	
	/** This method returns the Path
     * @return Returns the strPath.
     */
    public String getPath() {
        return strPath;
    }//end of getPath()
    
    /** This method sets the Path
     * @param strPath The strPath to set.
     */
    public void setPath(String strPath) {
        this.strPath = strPath;
    }//end of setPath(String strPath)
    
    /** This method returns the Report ID
     * @return Returns the strReportID.
     */
    public String getReportID() {
        return strReportID;
    }//end of getPath()
    
    /** This method sets the Report ID
     * @param strReportID The strReportID to set.
     */
    public void setReportID(String strReportID) {
        this.strReportID = strReportID;
    }//end of setReportID(String strReportID)
    
    /** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)

}
