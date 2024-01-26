/**
 * @ (#) WebConfigMemberVO.java Jan 7,2008
 * Project 	     : TTK HealthCare Services
 * File          : WebConfigMemberVO.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Jan 7,2008
 *
 * @author       : Krupa J
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class WebConfigMemberVO extends BaseVO
{
	private Long lngProdPolicySeqID = null;
	private Long lngMemberConfigSeqID = null;
	private Long lngPolicySeqID=null;
	private String strPolicyMemFieldTypeID="";
	private String strFieldName="";
	private String strFieldDesc="";
	private String strMandatoryYN = "";
	private String strFieldStatusGenTypeID="";
	private String strAllowDeselectYN  = "";
	
	/** Retrieve the AllowDeselectYN
	 * @return Returns the strAllowDeselectYN.
	 */
	public String getAllowDeselectYN() {
		return strAllowDeselectYN;
	}//end of getAllowDeselectYN() 

	/** Sets the AllowDeselectYN
	 * @param strAllowDeselectYN The strAllowDeselectYN to set.
	 */
	public void setAllowDeselectYN(String strAllowDeselectYN) {
		this.strAllowDeselectYN = strAllowDeselectYN;
	}//end of setAllowDeselectYN(String strAllowDeselectYN)

	/** Retrieve the PolicySeqID
	 * @return Returns the strPolicySeqID
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}

	/** Retrieve the FieldDesc
	 * @return Returns the strFieldDesc.
	 */
	public String getFieldDesc() {
		return strFieldDesc;
	}

	/** Sets the FieldDesc
	 * @param strFieldDesc The strFieldDesc to set.
	 */
	public void setFieldDesc(String strFieldDesc) {
		this.strFieldDesc = strFieldDesc;
	}

	/** Retrieve the MemberConfigSeqID
	 * @return Returns the lngConfigMemberSeqID.
	 */
	public Long getMemberConfigSeqID() {
		return lngMemberConfigSeqID;
	}
	
	/** Sets the MemberConfigSeqID
	 * @param lngMemberConfigSeqID The lngMemberConfigSeqID to set.
	 */
	public void setMemberConfigSeqID(Long lngMemberConfigSeqID) {
		this.lngMemberConfigSeqID = lngMemberConfigSeqID;
	}
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}
	
	/** Retrieve the FieldName
	 * @return Returns the strFieldName.
	 */
	public String getFieldName() {
		return strFieldName;
	}
	
	/** Sets the FieldName
	 * @param strFieldName The strFieldName to set.
	 */
	public void setFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}
	
	/** Retrieve the FieldStatusGenTypeID
	 * @return Returns the strFieldStatusGenTypeID.
	 */
	public String getFieldStatusGenTypeID() {
		return strFieldStatusGenTypeID;
	}
	
	/** Sets the FieldStatusGenTypeID
	 * @param strFieldStatusGenTypeID The strFieldStatusGenTypeID to set.
	 */
	public void setFieldStatusGenTypeID(String strFieldStatusGenTypeID) {
		this.strFieldStatusGenTypeID = strFieldStatusGenTypeID;
	}
	
	/** Retrieve the MandatoryYN
	 * @return Returns the strMandatoryYN.
	 */
	public String getMandatoryYN() {
		return strMandatoryYN;
	}
	
	/** Sets the MandatoryYN
	 * @param strMandatoryYN The strMandatoryYN to set.
	 */
	public void setMandatoryYN(String strMandatoryYN) {
		this.strMandatoryYN = strMandatoryYN;
	}
	
	/** Retrieve the PolicyMemFieldTypeID
	 * @return Returns the strPolicyMemFieldTypeID.
	 */
	public String getPolicyMemFieldTypeID() {
		return strPolicyMemFieldTypeID;
	}
	
	/** Sets the PolicyMemFieldTypeID
	 * @param strPolicyMemFieldTypeID The strPolicyMemFieldTypeID to set.
	 */
	public void setPolicyMemFieldTypeID(String strPolicyMemFieldTypeID) {
		this.strPolicyMemFieldTypeID = strPolicyMemFieldTypeID;
	}
	
}//end of WebConfigMemberVO
