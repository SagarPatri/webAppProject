/**
 * @ (#) CriticalGroupVO.java Oct 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : CriticalGroupVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.maintenance;

import com.ttk.dto.BaseVO;

public class CriticalGroupVO extends BaseVO{
	
	private String strGroupID = "";
	private String strGroupName = "";
	private String strGroupDesc = "";
	private String strImageName = "HistoryIcon";
    private String strImageTitle = "Associate Procedures"; 
    //added for KOC-1273
	private String strCriticalImageName = "HospitalIcon";
	private String strCriticalImageTitle = "Associate ICD";
	
	
	/** Retrieve the ImageName
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}//end of getImageName()

	/** Sets the ImageName
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)

	/** Retrieve the ImageTitle
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle()

	/** Sets the ImageTitle
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)

	/** Retrieve the Group Description
	 * @return Returns the strGroupDesc.
	 */
	public String getGroupDesc() {
		return strGroupDesc;
	}//end of getGroupDesc()
	
	/** Sets the Group Description
	 * @param strGroupDesc The strGroupDesc to set.
	 */
	public void setGroupDesc(String strGroupDesc) {
		this.strGroupDesc = strGroupDesc;
	}//end of setGroupDesc(String strGroupDesc)
	
	/** Retrieve the GroupID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}//end of getGroupID()
	
	/** Sets the GroupID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}//end of setGroupID(String strGroupID)
	
	/** Retrieve the GroupName
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()
	
	/** Sets the GroupName
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)
	//start of KOC-1273
	public String getCriticalImageTitle() {
		return strCriticalImageTitle;
	}

	public void setCriticalImageTitle(String strCriticalImageTitle) {
		this.strCriticalImageTitle = strCriticalImageTitle;
	}

	public String getCriticalImageName() {
		return strCriticalImageName;
	}

	public void setCriticalImageName(String strCriticalImageName) {
		this.strCriticalImageName = strCriticalImageName;
	}
	//ended for KOC-1273
}//end of CriticalGroupVO
