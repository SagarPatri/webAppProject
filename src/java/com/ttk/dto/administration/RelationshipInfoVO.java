/**
 * @ (#) RelationshipInfoVO.java Mar 10, 2008
 * Project 	     : TTK HealthCare Services
 * File          : RelationshipInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 10, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class RelationshipInfoVO extends BaseVO{
	
	private String strRelshipTypeID = "";//RELSHIP_TYPE_ID
	private String strRelshipDesc = "";//RELSHIP_DESCRIPTION
	private String strSelectedYN = "";//SELECTED_YN
	private String strWindowPeriodResYN = "";//WP_RESTRICTION_YN
	private Integer intRelshipWindowPeriod = null;
	private Integer intPolicyWindowPeriod = null;
	private String strFromDateGenTypeID = "";
	
	/** Retrieve the PolicyWindowPeriod
	 * @return Returns the intPolicyWindowPeriod.
	 */
	public Integer getPolicyWindowPeriod() {
		return intPolicyWindowPeriod;
	}//end of getPolicyWindowPeriod()

	/** Sets the PolicyWindowPeriod
	 * @param intPolicyWindowPeriod The intPolicyWindowPeriod to set.
	 */
	public void setPolicyWindowPeriod(Integer intPolicyWindowPeriod) {
		this.intPolicyWindowPeriod = intPolicyWindowPeriod;
	}//end of setPolicyWindowPeriod(Integer intPolicyWindowPeriod)

	/** Retrieve the RelshipWindowPeriod
	 * @return Returns the intRelshipWindowPeriod.
	 */
	public Integer getRelshipWindowPeriod() {
		return intRelshipWindowPeriod;
	}//end of getRelshipWindowPeriod()

	/** Sets the RelshipWindowPeriod
	 * @param intRelshipWindowPeriod The intRelshipWindowPeriod to set.
	 */
	public void setRelshipWindowPeriod(Integer intRelshipWindowPeriod) {
		this.intRelshipWindowPeriod = intRelshipWindowPeriod;
	}//end of setRelshipWindowPeriod(Integer intRelshipWindowPeriod)

	/** Retrieve the FromDateGenTypeID
	 * @return Returns the strFromDateGenTypeID.
	 */
	public String getFromDateGenTypeID() {
		return strFromDateGenTypeID;
	}//end of getFromDateGenTypeID()

	/** Sets the FromDateGenTypeID
	 * @param strFromDateGenTypeID The strFromDateGenTypeID to set.
	 */
	public void setFromDateGenTypeID(String strFromDateGenTypeID) {
		this.strFromDateGenTypeID = strFromDateGenTypeID;
	}//end of setFromDateGenTypeID(String strFromDateGenTypeID)

	/** Retrieve the Relationship Description
	 * @return Returns the strRelshipDesc.
	 */
	public String getRelshipDesc() {
		return strRelshipDesc;
	}//end of getRelshipDesc()
	
	/** Sets the Relationship Description
	 * @param strRelshipDesc The strRelshipDesc to set.
	 */
	public void setRelshipDesc(String strRelshipDesc) {
		this.strRelshipDesc = strRelshipDesc;
	}//end of setRelshipDesc(String strRelshipDesc)
	
	/** Retrieve the Relationship Type ID
	 * @return Returns the strRelshipTypeID.
	 */
	public String getRelshipTypeID() {
		return strRelshipTypeID;
	}//end of getRelshipTypeID()
	
	/** Sets the Relationship Type ID
	 * @param strRelshipTypeID The strRelshipTypeID to set.
	 */
	public void setRelshipTypeID(String strRelshipTypeID) {
		this.strRelshipTypeID = strRelshipTypeID;
	}//end of setRelshipTypeID(String strRelshipTypeID)
	
	/** Retrieve the SelectedYN
	 * @return Returns the strSelectedYN.
	 */
	public String getSelectedYN() {
		return strSelectedYN;
	}//end of getSelectedYN()
	
	/** Sets the SelectedYN
	 * @param strSelectedYN The strSelectedYN to set.
	 */
	public void setSelectedYN(String strSelectedYN) {
		this.strSelectedYN = strSelectedYN;
	}//end of setSelectedYN(String strSelectedYN)
	
	/** Retrieve the Window Period Restrict YN
	 * @return Returns the strWindowPeriodResYN.
	 */
	public String getWindowPeriodResYN() {
		return strWindowPeriodResYN;
	}//end of getWindowPeriodResYN()
	
	/** Sets the Window Period Restrict YN
	 * @param strWindowPeriodResYN The strWindowPeriodResYN to set.
	 */
	public void setWindowPeriodResYN(String strWindowPeriodResYN) {
		this.strWindowPeriodResYN = strWindowPeriodResYN;
	}//end of setWindowPeriodResYN(String strWindowPeriodResYN)
	
	

}//end of RelationshipInfoVO
