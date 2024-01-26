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

import com.ttk.dto.BaseVO;

public class MailNotificationVO extends BaseVO {
	    
    private String strNotiEmailId  = "" ;//notify_email_id 
    private String strNotiTypeId  = "" ;//notify_type_id
    private String strCcEmailId = "" ;//cc_email_id
    private String strEmailId = "" ;//email_id
    private String strPpnYN = ""; //added for koc 1346
    //change for hr mail id
    private String strHrEmailId="";
    
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
	
}//End of GroupRegistrationVO
