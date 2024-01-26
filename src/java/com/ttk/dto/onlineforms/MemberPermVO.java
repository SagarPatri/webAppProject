/**
 * @ (#) SumInsuredVO.java Jan 16, 2007
 * Project 	     : TTK HealthCare Services
 * File          : SumInsuredVO.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Jan 16, 2007
 *
 * @author       :  Balakrishna E
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;


public class MemberPermVO  {
    
    private String strPolMemFieldTypeID  =null; //pol_mem_field_type_id
    private String strFieldName =""; //field_name
    private String strMandatoryYN =""; //mandatory_yn
    private String strFieldStatus ="";	//field_status
    private String strAddSumIconYN = "";//add_sum_icon_yn
	
    /** Retrieve the AddSumIconYN
	 * @return Returns the strAddSumIconYN.
	 */
	public String getAddSumIconYN() {
		return strAddSumIconYN;
	}//end of getAddSumIconYN()
	
	/** Sets the AddSumIconYN
	 * @param strAddSumIconYN The strAddSumIconYN to set.
	 */
	public void setAddSumIconYN(String strAddSumIconYN) {
		this.strAddSumIconYN = strAddSumIconYN;
	}//end of setAddSumIconYN(String strAddSumIconYN)
	
	/** Retrive the Field Name
	 * @return Returns the strFieldName.
	 */
	public String getFieldName() {
		return strFieldName;
	}//end of getFieldName()
	/** Sets the Field Name
	 * @param strFieldName The strFieldName to set.
	 */
	public void setFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}//end of setFieldName(String strFieldName)
	/** Retrive the Field Status
	 * @return Returns the strFieldStatus.
	 */
	public String getFieldStatus() {
		return strFieldStatus;
	}//end of getFieldStatus()
	/** Sets the Field Status
	 * @param strFieldStatus The strFieldStatus to set.
	 */
	public void setFieldStatus(String strFieldStatus) {
		this.strFieldStatus = strFieldStatus;
	}//end of setFieldStatus(String strFieldStatus)
	/** Retrive the Mandatory YN
	 * @return Returns the strMandatoryYN.
	 */
	public String getMandatoryYN() {
		return strMandatoryYN;
	}//end of getMandatoryYN()
	/** Sets the Mandatory YN
	 * @param strMandatoryYN The strMandatoryYN to set.
	 */
	public void setMandatoryYN(String strMandatoryYN) {
		this.strMandatoryYN = strMandatoryYN;
	}//end of setMandatoryYN(String strMandatoryYN)
	/** Retrive the Policy Member Field Type ID
	 * @return Returns the strPolMemFieldTypeID.
	 */
	public String getPolMemFieldTypeID() {
		return strPolMemFieldTypeID;
	}//end of getPolMemFieldTypeID()
	/** Sets the Policy Member Field Type ID
	 * @param strPolMemFieldTypeID The strPolMemFieldTypeID to set.
	 */
	public void setPolMemFieldTypeID(String strPolMemFieldTypeID) {
		this.strPolMemFieldTypeID = strPolMemFieldTypeID;
	}//end of setPolMemFieldTypeID(String strPolMemFieldTypeID) 
	
}//end of MemberAddressVO
