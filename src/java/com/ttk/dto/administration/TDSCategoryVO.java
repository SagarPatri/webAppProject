/**
 * @ (#)  TDSCategoryVO.java July 27, 2009
 * Project      : TTKPROJECT
 * File         : TDSCategoryVO.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 27, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class TDSCategoryVO extends BaseVO{

	private String strTdsCatTypeID =""; //tds_cat_type_id
	private String strTdsCatName =""; //tds_cat_name
	private String strTdsDesc =""; //description
	private ArrayList<Object> alTDSConfList=null;
	
	/** Retrieve the  TdsCatType
	 * @return the strTdsCatTypeID
	 */
	public String getTdsCatTypeID() {
		return strTdsCatTypeID;
	}//end of getTdsCatType()
	
	/** sets the TdsCatType
	 * @param strTdsCatTypeID the strTdsCatTypeID to set
	 */
	public void setTdsCatTypeID(String strTdsCatTypeID) {
		this.strTdsCatTypeID = strTdsCatTypeID;
	}//end of stTdsCatType(String strTdsCatTypeID)
	
	/** Retrieve the TdsCatName
	 * @return the strTdsCatName
	 */
	public String getTdsCatName() {
		return strTdsCatName;
	}//end of getTdsCatName()
	
	/** Sets the TdsCatName
	 * @param strTdsCatName the strTdsCatName to set
	 */
	public void setTdsCatName(String strTdsCatName) {
		this.strTdsCatName = strTdsCatName;
	}//end of setTdsCatName(String strTdsCatName)
	
	/** Retrieve the TDS Description
	 * @return the strTdsDesc
	 */
	public String getTdsDesc() {
		return strTdsDesc;
	}//end of getTdsDesc()
	
	/** Sets the TDS Description
	 * @param strTdsDesc the strTdsDesc to set
	 */
	public void setTdsDesc(String strTdsDesc) {
		this.strTdsDesc = strTdsDesc;
	}//end of setTdsDesc(String strTdsDesc) 	

	/** Retrieve the TDS configuration List
	 * @return the alTDSConfList
	 */
	public ArrayList<Object> getTDSConfList() {
		return alTDSConfList;
	}//end of getTDSConfList()

	/** Sets the TDS configuration List
	 * @param alTDSConfList the alTDSConfList to set
	 */
	public void setTDSConfList(ArrayList<Object> alTDSConfList) {
		this.alTDSConfList = alTDSConfList;
	}//end of setTDSConfList(ArrayList<Object> alTDSConfList)	
	
}//end of TDSCategoryVO