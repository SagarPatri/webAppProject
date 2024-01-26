/**
 * @ (#) AccountHeadVO.java Jun 27, 2007
 * Project 	     : TTK HealthCare Services
 * File          : AccountHeadVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 27, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;

public class AccountHeadVO extends BaseVO {
	
	private String strWardTypeID = "";//ward_type_id
	private String strWardDesc = "";//ward_description
	private String strSelectedYN = "";//already_selected_yn
	private String strCommonAcctHeadYN = "";//common_accont_head_yn
	private String strCaptureNbrOfDaysYN = "";//capture_number_of_days_yn
	
	/** Retrieve the Capture Nbr Of Days YN
	 * @return Returns the strCaptureNbrOfDaysYN.
	 */
	public String getCaptureNbrOfDaysYN() {
		return strCaptureNbrOfDaysYN;
	}//end of getCaptureNbrOfDaysYN()
	
	/** Sets the Capture Nbr Of Days YN
	 * @param strCaptureNbrOfDaysYN The strCaptureNbrOfDaysYN to set.
	 */
	public void setCaptureNbrOfDaysYN(String strCaptureNbrOfDaysYN) {
		this.strCaptureNbrOfDaysYN = strCaptureNbrOfDaysYN;
	}//end of setCaptureNbrOfDaysYN(String strCaptureNbrOfDaysYN)
	
	/** Retrieve the Common AccounttHead YN
	 * @return Returns the strCommonAcctHeadYN.
	 */
	public String getCommonAcctHeadYN() {
		return strCommonAcctHeadYN;
	}//end of getCommonAcctHeadYN()
	
	/** Sets the Common AccounttHead YN
	 * @param strCommonAcctHeadYN The strCommonAcctHeadYN to set.
	 */
	public void setCommonAcctHeadYN(String strCommonAcctHeadYN) {
		this.strCommonAcctHeadYN = strCommonAcctHeadYN;
	}//end of setCommonAcctHeadYN(String strCommonAcctHeadYN)
	
	/** Retrieve the Selected YN
	 * @return Returns the strSelectedYN.
	 */
	public String getSelectedYN() {
		return strSelectedYN;
	}//end of getSelectedYN()
	
	/** Sets the Selected YN
	 * @param strSelectedYN The strSelectedYN to set.
	 */
	public void setSelectedYN(String strSelectedYN) {
		this.strSelectedYN = strSelectedYN;
	}//end of setSelectedYN(String strSelectedYN)
	
	/** Retrieve the Ward Description
	 * @return Returns the strWardDesc.
	 */
	public String getWardDesc() {
		return strWardDesc;
	}//end of getWardDesc()
	
	/** Sets the Ward Description
	 * @param strWardDesc The strWardDesc to set.
	 */
	public void setWardDesc(String strWardDesc) {
		this.strWardDesc = strWardDesc;
	}//end of setWardDesc(String strWardDesc)
	
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

}//end of AccountHeadVO
