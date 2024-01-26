/**
 * @ (#) WebloginInsCompInfo.java Feb 25, 2008
 * Project 	     : TTK HealthCare Services
 * File          : WebloginInsCompInfo.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 25, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;
import org.apache.struts.upload.FormFile;

public class WebconfigInsCompInfoVO extends BaseVO{
	
	private Long lngInsInfoSeqID = null;
	private Long lngProdPolicySeqID = null;
	private Long lngPolicySeqID=null;
	private String strInsInfo = "";

	private Long lngHospInfoSeqID = null;//kocnewhosp1
	private String strHospInfo = "";
	private String strConfigLinkDesc="";
	private String strShowLinkYN = "";
	private Long lngConfigLinkSeqID = null;
	private String strLinkTypeID = "";
	private Integer intOrderNumber = null;
	private String strPath = "";
	private String strReportID="";
	private String strFile="";

	private	FormFile file=null;
	
	//kocnewhosp1
	
	
	/**
	 * @param file the file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}
	/**
	 * @return the file
	 */
	public FormFile getFile() {
		return file;
	}
	
	
	/** This method returns the File
     * @return Returns the strFile.
     */
    public String getFile1() {
        return strFile;
    }//end of getPath()
    
    /** This method sets the File
     * @param strFile The strFile to set.
     */
    public void setFile(String strFile) {
        this.strFile = strFile;
    }//end of setFile(String strFile)
    
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
	/** Retrieve the HospInfoSeqID
	 * @return Returns the lngHospInfoSeqID.
	 */
	public Long getHospInfoSeqID() {
		return lngHospInfoSeqID;
	}//end of getInsInfoSeqID()
	
	/** Sets the HospInfoSeqID
	 * @param lngHospInfoSeqID The lngInsInfoSeqID to set.
	 */
	
	/** Retrieve the strInsInfo
	 * @return Returns the strInsInfo.
	 */
	public String getHospInfo() {
		return strHospInfo;
	}//end of getHospInfo()
	
	/** Sets the strHospInfo
	 * @param strIHospInfo The strHospInfo to set.
	 */
	public void setHospInfo(String strHospInfo) {
		this.strHospInfo = strHospInfo;
	}//end of setHospInfo(String strHospInfo)
	
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
	
	
	public void setHospInfoSeqID(Long lngHospInfoSeqID) {
		this.lngHospInfoSeqID = lngHospInfoSeqID;
	}//end of setInsInfoSeqID(Long lngInsInfoSeqID)
	
	
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
	
	/** Retrieve the InsInfoSeqID
	 * @return Returns the lngInsInfoSeqID.
	 */
	public Long getInsInfoSeqID() {
		return lngInsInfoSeqID;
	}//end of getInsInfoSeqID()
	
	/** Sets the InsInfoSeqID
	 * @param lngInsInfoSeqID The lngInsInfoSeqID to set.
	 */
	public void setInsInfoSeqID(Long lngInsInfoSeqID) {
		this.lngInsInfoSeqID = lngInsInfoSeqID;
	}//end of setInsInfoSeqID(Long lngInsInfoSeqID)
	
	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)
	
	/** Retrieve the lngProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the lngProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the strInsInfo
	 * @return Returns the strInsInfo.
	 */
	public String getInsInfo() {
		return strInsInfo;
	}//end of getInsInfo()
	
	/** Sets the strInsInfo
	 * @param strInsInfo The strInsInfo to set.
	 */
	public void setInsInfo(String strInsInfo) {
		this.strInsInfo = strInsInfo;
	}//end of setInsInfo(String strInsInfo)
	
}//end of WebloginInsCompInfo
