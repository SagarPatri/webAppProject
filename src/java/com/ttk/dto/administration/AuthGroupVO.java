/**
 * @ (#) AuthGroupVO.java Aug 12, 2008
 * Project 	     : TTK HealthCare Services
 * File          : AuthGroupVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 12, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class AuthGroupVO extends BaseVO{
	
	private Long lngAuthGroupSeqID = null;//AUTH_GRP_SEQ_ID
	private Long lngProdPolicySeqID = null;//PROD_POLICY_SEQ_ID
	private String strAuthGrpName = "";//GROUP_NAME
	private String strAuthGroupDesc = "";//GROUP_DESCRIPTION
	
	/** Retrieve the AuthGroupSeqID
	 * @return Returns the lngAuthGroupSeqID.
	 */
	public Long getAuthGroupSeqID() {
		return lngAuthGroupSeqID;
	}//end of getAuthGroupSeqID()
	
	/** Sets the AuthGroupSeqID
	 * @param lngAuthGroupSeqID The lngAuthGroupSeqID to set.
	 */
	public void setAuthGroupSeqID(Long lngAuthGroupSeqID) {
		this.lngAuthGroupSeqID = lngAuthGroupSeqID;
	}//end of setAuthGroupSeqID(Long lngAuthGroupSeqID)
	
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
	
	/** Retrieve the AuthGroupDesc
	 * @return Returns the strAuthGroupDesc.
	 */
	public String getAuthGroupDesc() {
		return strAuthGroupDesc;
	}//end of getAuthGroupDesc()
	
	/** Sets the AuthGroupDesc
	 * @param strAuthGroupDesc The strAuthGroupDesc to set.
	 */
	public void setAuthGroupDesc(String strAuthGroupDesc) {
		this.strAuthGroupDesc = strAuthGroupDesc;
	}//end of setAuthGroupDesc(String strAuthGroupDesc)
	
	/** Retrieve the AuthGrpName
	 * @return Returns the strAuthGrpName.
	 */
	public String getAuthGrpName() {
		return strAuthGrpName;
	}//end of getAuthGrpName()
	
	/** Sets the AuthGrpName
	 * @param strAuthGrpName The strAuthGrpName to set.
	 */
	public void setAuthGrpName(String strAuthGrpName) {
		this.strAuthGrpName = strAuthGrpName;
	}//end of setAuthGrpName(String strAuthGrpName)
	
}//end of AuthGroupVO
