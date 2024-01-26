/**
 * @ (#) AuthAcctheadVO.java Aug 12, 2008
 * Project 	     : TTK HealthCare Services
 * File          : AuthAcctheadVO.java
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

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class AuthAcctheadVO extends BaseVO{
	
	private String strAuthGrpSeqID = null;
	private String strWardTypeID = "";
	private String strIncAcctheadYN = "";
	private String strShowAcctheadYN = "";
	private String strWardDesc = "";
	private ArrayList alGroupList = new ArrayList(); // Group List
	
	/** Retrieve the GroupList
	 * @return Returns the alGroupList.
	 */
	public ArrayList getGroupList() {
		return alGroupList;
	}//end of getGroupList()

	/** Sets the GroupList
	 * @param alGroupList The alGroupList to set.
	 */
	public void setGroupList(ArrayList alGroupList) {
		this.alGroupList = alGroupList;
	}//end of setGroupList(ArrayList alGroupList)
	
	/** Retrieve the WardDesc
	 * @return Returns the strWardDesc.
	 */
	public String getWardDesc() {
		return strWardDesc;
	}//end of getWardDesc()

	/** Sets the WardDesc
	 * @param strWardDesc The strWardDesc to set.
	 */
	public void setWardDesc(String strWardDesc) {
		this.strWardDesc = strWardDesc;
	}//end of setWardDesc(String strWardDesc)

	/** Retrieve the AuthGrpSeqID
	 * @return Returns the strAuthGrpSeqID.
	 */
	public String getAuthGrpSeqID() {
		return strAuthGrpSeqID;
	}//end of getAuthGrpSeqID()
	
	/** Sets the AuthGrpSeqID
	 * @param strAuthGrpSeqID The strAuthGrpSeqID to set.
	 */
	public void setAuthGrpSeqID(String strAuthGrpSeqID) {
		this.strAuthGrpSeqID = strAuthGrpSeqID;
	}//end of setAuthGrpSeqID(String strAuthGrpSeqID)
	
	/** Retrieve the Include Accthead YN
	 * @return Returns the strIncAcctheadYN.
	 */
	public String getIncAcctheadYN() {
		return strIncAcctheadYN;
	}//end of getIncAcctheadYN()
	
	/** Sets the Include Accthead YN
	 * @param strIncAcctheadYN The strIncAcctheadYN to set.
	 */
	public void setIncAcctheadYN(String strIncAcctheadYN) {
		this.strIncAcctheadYN = strIncAcctheadYN;
	}//end of setIncAcctheadYN(String strIncAcctheadYN)
	
	/** Retrieve the ShowAcctheadYN
	 * @return Returns the strShowAcctheadYN.
	 */
	public String getShowAcctheadYN() {
		return strShowAcctheadYN;
	}//end of getShowAcctheadYN()
	
	/** Sets the ShowAcctheadYN
	 * @param strShowAcctheadYN The strShowAcctheadYN to set.
	 */
	public void setShowAcctheadYN(String strShowAcctheadYN) {
		this.strShowAcctheadYN = strShowAcctheadYN;
	}//end of setShowAcctheadYN(String strShowAcctheadYN)
	
	/** Retrieve the WardTypeID
	 * @return Returns the strWardTypeID.
	 */
	public String getWardTypeID() {
		return strWardTypeID;
	}//end of getWardTypeID()
	
	/** Sets the WardTypeID
	 * @param strWardTypeID The strWardTypeID to set.
	 */
	public void setWardTypeID(String strWardTypeID) {
		this.strWardTypeID = strWardTypeID;
	}//end of setWardTypeID(String strWardTypeID)
	
	
	

}//end of AuthAcctheadVO
