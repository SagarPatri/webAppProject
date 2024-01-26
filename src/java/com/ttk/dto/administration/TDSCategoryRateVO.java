/**
 * @ (#)  TDSCategoryRateVO.java July 27, 2009
 * Project      : TTKPROJECT
 * File         : TDSCategoryRateVO.java
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
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class TDSCategoryRateVO extends BaseVO {

	private Date dtRevDateFrom =null; //rev_date_from
	private Date dtRevDateTo =null; //rev_date_to
	private String strTdsSubCatTypeID =""; //tds_subcat_type_id
	private ArrayList<Object> alTDSCateRateDetailVOList = null;
	private String strReviseDateFrom = null;
	
	/** Retrieve the ReviseDateFrom
	 * @return Returns the strReviseDateFrom.
	 */
	public String getReviseDateFrom() {
		return strReviseDateFrom;
	}//end of getReviseDateFrom()

	/** Sets the ReviseDateFrom
	 * @param strReviseDateFrom The strReviseDateFrom to set.
	 */
	public void setReviseDateFrom(String strReviseDateFrom) {
		this.strReviseDateFrom = strReviseDateFrom;
	}//end of setReviseDateFrom(String strReviseDateFrom)

	/** Retrieve the TDSCateRateDetailVOList
	 * @return Returns the alTDSCateRateDetailVOList.
	 */
	public ArrayList<Object> getTDSCateRateDetailVOList() {
		return alTDSCateRateDetailVOList;
	}//end of getTariffDetailVOList()

	/** Sets the TDSCateRateDetailVOList
	 * @param alTDSCateRateDetailVOList The alTDSCateRateDetailVOList to set.
	 */
	public void setTDSCateRateDetailVOList(ArrayList<Object> alTDSCateRateDetailVOList) {
		this.alTDSCateRateDetailVOList = alTDSCateRateDetailVOList;
	}//end of setTariffDetailVOList(ArrayList alTDSCateRateDetailVOList)

	
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

	/** Retrieve the Revised Date From
	 * @return the dtRevDateFrom
	 */
	public Date getRevDateFrom() {
		return dtRevDateFrom;
	}//end of getRevDateFrom()
	
	/** Retrieve the Revised Date From
	 * @return the dtRevDateFrom
	 */
	public String getTdsRevDateFrom() {
		return TTKCommon.getFormattedDate(dtRevDateFrom);
	}//end of getTdsRevDateFrom()
	
	/** Sets the Revised Date From
	 * @param dtRevDateFrom the strRevDateFrom to set
	 */
	public void setRevDateFrom(Date dtRevDateFrom) {
		this.dtRevDateFrom = dtRevDateFrom;
	}//end of setRevDateFrom(Date dtRevDateFrom)
	
	/** Retrieve the Revised Date To
	 * @return the dtRevDateTo
	 */
	public Date getRevDateTo() {
		return dtRevDateTo;
	}//end of getRevDateTo() 
	
	/** Retrieve the Revised Date To
	 * @return the dtRevDateTo
	 */
	public String getTdsRevDateTo() {
		return TTKCommon.getFormattedDate(dtRevDateTo);
	}//end of getTdsRevDateTo()
	
	/** Sets the Revised Date To
	 * @param dtRevDateTo the dtRevDateTo to set
	 */
	public void setRevDateTo(Date dtRevDateTo) {
		this.dtRevDateTo = dtRevDateTo;
	}//end of setRevDateTo(Date dtRevDateTo)
	
	
}//end of TDSCategoryRateVO