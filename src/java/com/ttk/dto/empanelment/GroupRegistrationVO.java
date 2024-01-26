/**
 * @ (#) GroupRegistrationVO.java Jan 11, 2006
 * Project       : TTK HealthCare Services
 * File          : GroupRegistrationVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 11, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.empanelment;

import java.util.ArrayList;

import com.ttk.dto.security.GroupVO;

public class GroupRegistrationVO extends GroupVO {
	private Long lngGroupRegSeqID = null ;  
	private Long lngParentGroupSeqID = null; 
	private String strGroupID = null; 
	private String strGroupGenTypeID  = "";
	private AddressVO address = null;
    private ArrayList alBranch = null;
    private String strLocationCode = "";
    private String strAccntManagerName="";
    private Long lngAccntManagerSeqID = null;
    
//  Adding notification legend in Empanelment Group registration screen --15/05/2008
    private String strNotiEmailId  = "" ;//notify_email_id 
    private String strNotiTypeId  = "" ;//notify_type_id
    private String strCcEmailId = "" ;//cc_email_id
    private String strEmailId = "" ;//email_id
    private String strPpnYN = ""; //added for koc 1346
    //change for hr mail id
    private String strHrEmailId="";
    
    private String tradeLicenseNo="";
    
    	/** Retrive the HR Email ID
    	 * @return Returns the strCcEmailId.
    	 */
    	public String getHrEmailId() {
    		return strHrEmailId;
    	}//end of getCcEmailId() 

    	/** Sets the CC Email ID
    	 * @param strCcEmailId The strCcEmailId to set.
    	 */
    	public void setHrEmailId(String strHrEmailId) {
    		this.strHrEmailId = strHrEmailId;
    	}//end of setCcEmailId(String strCcEmailId) 
    	
    
    /** Retrive the CC Email ID
	 * @return Returns the strCcEmailId.
	 */
	public String getCcEmailId() {
		return strCcEmailId;
	}//end of getCcEmailId() 

	/** Sets the CC Email ID
	 * @param strCcEmailId The strCcEmailId to set.
	 */
	public void setCcEmailId(String strCcEmailId) {
		this.strCcEmailId = strCcEmailId;
	}//end of setCcEmailId(String strCcEmailId) 
	
	/** Retrive the Email ID
	 * @return Returns the strCcEmailId.
	 */
	public String getEmailId() {
		return strEmailId;
	}//end of getEmailId() 

	/** Sets the Email ID
	 * @param strCcEmailId The strCcEmailId to set.
	 */
	public void setEmailId(String strEmailId) {
		this.strEmailId = strEmailId;
	}//end of setEmailId(String strEmailId) 

	/** Retrieve the AccntManagerSeqID
	 * @return Returns the lngAccntManagerSeqID.
	 */
    public Long getAccntManagerSeqID() {
		return lngAccntManagerSeqID;
	}//end of getAccntManagerSeqID()
    
    /** Sets the AccntManagerSeqID
	 * @param lngAccntManagerSeqID The lngAccntManagerSeqID to set.
	 */
	public void setAccntManagerSeqID(Long lngAccntManagerSeqID) {
		this.lngAccntManagerSeqID = lngAccntManagerSeqID;
	}//end of setAccntManagerSeqID(Long lngAccntManagerSeqID)

	/** Retrieve the AccntManagerName
	 * @return Returns the strAccntManagerName.
	 */
    public String getAccntManagerName() {
		return strAccntManagerName;
	}//end of getAccntManagerName()
    
    /** Sets the AccntManagerName
	 * @param strAccntManagerName The strAccntManagerName to set.
	 */
	public void setAccntManagerName(String strAccntManagerName) {
		this.strAccntManagerName = strAccntManagerName;
	}//end of setAccntManagerName(String strAccntManagerName)

	/** Retrieve the LocationCode
	 * @return Returns the strLocationCode.
	 */
	public String getLocationCode() {
		return strLocationCode;
	}//end of getLocationCode()

	/** Sets the LocationCode
	 * @param strLocationCode The strLocationCode to set.
	 */
	public void setLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}//end of setLocationCode(String strLocationCode)

	/** This method returns the address details
	 * @return Returns the address.
	 */
	public AddressVO getAddress() {
		return address;
	}//End of getAddress()
	
	/** This method sets the address details
	 * @param address The address to set.
	 */
	public void setAddress(AddressVO address) {
		this.address = address;
	}// End of setAddress(AddressVO address)
	
	/** This method returns the Group Registration Sequence ID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}// End of getGroupRegSeqID()
	
	/** This method sets the Group Registration Sequence ID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}// End of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/** This method returns the Group General Type ID
	 * @return Returns the strGroupGenTypeID.
	 */
	public String getGroupGenTypeID() {
		return strGroupGenTypeID;
	}// End of getGroupGenTypeID()
	
	/** This method sets the Group General Type ID
	 * @param strGroupGenTypeID The strGroupGenTypeID to set.
	 */
	public void setGroupGenTypeID(String strGroupGenTypeID) {
		this.strGroupGenTypeID = strGroupGenTypeID;
	}//End of setGroupGenTypeID(String strGroupGenTypeID)
	
	/** This method returns the Group ID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}// End of getGroupID() 
	
	/** This method sets the Group ID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}// End of setGroupID(String strGroupID)
	
	/** This method returns the Parent Group Sequence ID
	 * @return Returns the lngParentGroupSeqID.
	 */
	public Long getParentGroupSeqID() {
		return lngParentGroupSeqID;
	}// End of getParentGroupSeqID()
	
	/** This method sets the Parent Group Sequence ID
	 * @param lngParentGroupSeqID The lngParentGroupSeqID to set.
	 */
	public void setParentGroupSeqID(Long lngParentGroupSeqID) {
		this.lngParentGroupSeqID = lngParentGroupSeqID;
	}// End of setParentGroupSeqID(Long lngParentGroupSeqID)
    
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

	/** Retrieve the Notify Email Id
	 * @return Returns the strNotiEmailId.
	 */
	public String getNotiEmailId() {
		return strNotiEmailId;
	}//end of getNotiEmailId

	/** Sets the Notify Email Id
	 * @param strNotiEmailId The strNotiEmailId to set.
	 */
	public void setNotiEmailId(String strNotiEmailId) {
		this.strNotiEmailId = strNotiEmailId;
	}//end of setNotiEmailId(String strNotiEmailId)

	/** Retrieve the Notify Type Id
	 * @return Returns the strNotiTypeId.
	 */
	public String getNotiTypeId() {
		return strNotiTypeId;
	}//end of getNotiTypeId()

	/** Sets the Notify Type Id
	 * @param strNotiTypeId The strNotiTypeId to set.
	 */
	public void setNotiTypeId(String strNotiTypeId) {
		this.strNotiTypeId = strNotiTypeId;
	}//end of setNotiTypeId(String strNotiTypeId)
	 //added for koc 1346
    public String getPpnYN() {
		return strPpnYN;
	}

	public void setPpnYN(String strPpnYN) {
		this.strPpnYN = strPpnYN;
	}
	 // end added for koc 1346

	public String getTradeLicenseNo() {
		return tradeLicenseNo;
	}

	public void setTradeLicenseNo(String tradeLicenseNo) {
		this.tradeLicenseNo = tradeLicenseNo;
	}
	
}//End of GroupRegistrationVO
