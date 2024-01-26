/**
 * @ (#) AssocGroupVO.java October 21, 2009
 * Project 	     : TTK HealthCare Services
 * File          : AssocGroupVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : October 21, 2009
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import com.ttk.dto.BaseVO;

/** Author Information.
 * @author ramakrishna_km
 *
 */
public class AssocGroupVO extends BaseVO{

	/** Serial ID.
	 * 
	 */
	private static final long serialVersionUID = -7603059989337453534L;
	
	private Long lngFloatGrpAssSeqID = null;
	private Long lngGroupRegSeqID = null;
	private String strGroupID = "";
	private String strGroupName = "";
	private String strPolicyNo = "";

	private Long lngFloatSeqID = null;
    private String strDeleteImageName = "DeleteIcon";
    private String strDeleteImageTitle = "Delete";
    private String policyCategory="";
    
    public String getPolicyCategory() {
		return policyCategory;
	}

	public void setPolicyCategory(String policyCategory) {
		this.policyCategory = policyCategory;
	}

	public String getPolicyNo() {
		return strPolicyNo;
	}

	public void setPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}
	/** Retrieve the FloatSeqID.
	 * @return Returns the lngFloatSeqID.
	 */
	public Long getFloatSeqID() {
		return lngFloatSeqID;
	}//end of getFloatSeqID()

	/** Sets the FloatSeqID.
	 * @param lngFloatSeqID The lngFloatSeqID to set.
	 */
	public void setFloatSeqID(Long lngFloatSeqID) {
		this.lngFloatSeqID = lngFloatSeqID;
	}//end of setFloatSeqID(Long lngFloatSeqID)

	/** Retrieve the FloatGrpAssSeqID.
	 * @return Returns the lngFloatGrpAssSeqID.
	 */
	public Long getFloatGrpAssSeqID() {
		return lngFloatGrpAssSeqID;
	}//end of getFloatGrpAssSeqID()
	
	/** Sets the FloatGrpAssSeqID.
	 * @param lngFloatGrpAssSeqID The lngFloatGrpAssSeqID to set.
	 */
	public void setFloatGrpAssSeqID(Long lngFloatGrpAssSeqID) {
		this.lngFloatGrpAssSeqID = lngFloatGrpAssSeqID;
	}//end of setFloatGrpAssSeqID(Long lngFloatGrpAssSeqID)
	
	/** Retrieve the GroupRegSeqID.
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}//end of getGroupRegSeqID()
	
	/** Sets the GroupRegSeqID.
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}//end of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/** Retrieve the GroupID.
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}//end of getGroupID()
	
	/** Sets the GroupID.
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}//end of setGroupID(String strGroupID)
	
	/** Retrieve the GroupName.
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()
	
	/** Sets the GroupName.
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)

	/** Retrieve the DeleteImageName
	 * @return Returns the strDeleteImageName.
	 */
	public String getDeleteImageName() {
		return strDeleteImageName;
	}//end of getDeleteImageName()
	
	/** Sets the DeleteImageName
	 * @param strDeleteImageName The strDeleteImageName to set.
	 */
	public void setDeleteImageName(String strDeleteImageName) {
		this.strDeleteImageName = strDeleteImageName;
	}//end of setDeleteImageName(String strDeleteImageName)
	
	/** Retrieve the DeleteImageTitle
	 * @return Returns the strDeleteImageTitle.
	 */
	public String getDeleteImageTitle() {
		return strDeleteImageTitle;
	}//end of getDeleteImageTitle()
	
	/** Sets the DeleteImageTitle
	 * @param strDeleteImageTitle The strDeleteImageTitle to set.
	 */
	public void setDeleteImageTitle(String strDeleteImageTitle) {
		this.strDeleteImageTitle = strDeleteImageTitle;
	}//end of setDeleteImageTitle(String strDeleteImageTitle)
}//end of AssocGroupVO
