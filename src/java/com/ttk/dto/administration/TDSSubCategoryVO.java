/**
 * @ (#)  TDSSubCategoryVO.java July 28, 2009
 * Project      : TTKPROJECT
 * File         : TDSSubCategoryVO.java
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

public class TDSSubCategoryVO extends BaseVO{

	private static final long serialVersionUID = -1096243467125786701L;
	
	private String strTdsSubCatTypeID; //tds_subcat_type_id
	private String strTdsSubCatName =""; //tds_subcat_name
	private String strTdsCatTypeID =""; //tds_category_type_id
	private String strTdsSubCatDesc =""; //sub_category_description	
	private ArrayList<Object> alTDSConfList=null;
	
	/** Retrieve the  strTdsSubCatTypeID
	 * @return the strTdsSubCatTypeID
	 */
	public String getTdsSubCatTypeID() {
		return strTdsSubCatTypeID;
	}//end of getTdsCatType()
	
	/** sets the strTdsSubCatTypeID
	 * @param strTdsSubCatTypeID the strTdsSubCatTypeID to set
	 */
	public void setTdsSubCatTypeID(String strTdsSubCatTypeID) {
		this.strTdsSubCatTypeID = strTdsSubCatTypeID;
	}//end of stTdsCatType(String strTdsSubCatTypeID)
	
	/** Retrieve the TdsSubCatName
	 * @return the strTdsSubCatName
	 */
	public String getTdsSubCatName() {
		return strTdsSubCatName;
	}//end of getTdsSubCatName()
	
	/** Sets the TdsSubCatName
	 * @param strTdsSubCatName the strTdsSubCatName to set
	 */
	public void setTdsSubCatName(String strTdsSubCatName) {
		this.strTdsSubCatName = strTdsSubCatName;
	}//end of setTdsSubCatName(String strTdsSubCatName)
	
	/** Retrieve the Tds Category Type Id
	 * @return the strTdsCatTypeID
	 */
	public String getTdsCatTypeID() {
		return strTdsCatTypeID;
	}//end of getTdsCatTypeID()
	
	/** Sets the Tds Category Type Id
	 * @param strTdsCatTypeID the strTdsCatTypeID to set
	 */
	public void setTdsCatTypeID(String strTdsCatTypeID) {
		this.strTdsCatTypeID = strTdsCatTypeID;
	}//end of setTdsCatTypeID(String strTdsCatTypeID)
	
	/** Retrieve the Tds Sub Category Description
	 * @return the strTdsSubCatDesc
	 */
	public String getTdsSubCatDesc() {
		return strTdsSubCatDesc;
	}//end of getTdsSubCatDesc()
	
	/** Sets the Tds Sub Category Description
	 * @param strTdsSubCatDesc the strTdsSubCatDesc to set
	 */
	public void setTdsSubCatDesc(String strTdsSubCatDesc) {
		this.strTdsSubCatDesc = strTdsSubCatDesc;
	}//end of setTdsSubCatDesc(String strTdsSubCatDesc) 	
	
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