/**
 * @ (#)  TDSCateRateDetailVO.java July 27, 2009
 * Project      : TTKPROJECT
 * File         : TDSCateRateDetailVO.java
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

import java.math.BigDecimal;
import com.ttk.dto.BaseVO;

public class TDSCateRateDetailVO extends BaseVO {

	private String strTdsAtrTypeID =""; //tds_atr_type_id
	private String strTdsAtrName =""; //tds_atr_name
	private String strTdsAtrDesc =""; //attribute_description
	private Long lngTdsCatRateSeqID =null; //tds_cat_rate_seq_id
	private BigDecimal bdApplRatePerc =null; //applicable_rate_percent
	private BigDecimal bdCalculation =null; //calculation/formula
	private String strCalcAmount ="";//calculated amount
	private BigDecimal bdTotTDSAmount =null;//total amount
	
	/** Sets the Total TDS Amount
	 * @return the bdTotTDSAmount
	 */
	public BigDecimal getTotTDSAmount() {
		return bdTotTDSAmount;
	}//end of getTotTDSAmount()

	/** Retrieve the Total TDS Amount
	 * @param bdTotTDSAmount the bdTotTDSAmount to set
	 */
	public void setTotTDSAmount(BigDecimal bdTotTDSAmount) {
		this.bdTotTDSAmount = bdTotTDSAmount;
	}//end of setTotTDSAmount(BigDecimal bdTotTDSAmount)

	/** Retrieve the TDS attribute ID
	 * @return the strCalcAmount
	 */
	public String getCalcAmount() {
		return strCalcAmount;
	}//end of getCalcAmount
	
	/** Sets the Calculate amount
	 * @param strCalcAmount the strCalcAmount to set
	 */
	public void setCalcAmount(String strCalcAmount) {
		this.strCalcAmount = strCalcAmount;
	}//end of setCalcAmount(String strCalcAmount)
	
	/** Retrieve the  Calculate amount
	 * @return the strTdsAtrTypeID
	 */
	public String getTdsAtrTypeID() {
		return strTdsAtrTypeID;
	}//end of getTdsAtrTypeID
	
	/** Sets the TDS attribute ID
	 * @param strTdsAtrTypeID the strTdsAtrTypeID to set
	 */
	public void setTdsAtrTypeID(String strTdsAtrTypeID) {
		this.strTdsAtrTypeID = strTdsAtrTypeID;
	}//end of setTdsAtrTypeID(String strTdsAtrTypeID)
	
	/** Retrieve the TDS attribute Name
	 * @return the strTdsAtrName
	 */
	public String getTdsAtrName() {
		return strTdsAtrName;
	}//end of getTdsAtrName()
	
	/** Sets the  TDS attribute ID
	 * @param strTdsAtrName the strTdsAtrName to set
	 */
	public void setTdsAtrName(String strTdsAtrName) {
		this.strTdsAtrName = strTdsAtrName;
	}//end of setTdsAtrName(String strTdsAtrName)
	
	/** Retrieve the TDS Attribute Description
	 * @return the strTdsAtrDesc
	 */
	public String getTdsAtrDesc() {
		return strTdsAtrDesc;
	}//end of getTdsAtrDesc
	
	/** Sets the TDS Attribute Description
	 * @param strTdsAtrDesc the strTdsAtrDesc to set
	 */
	public void setTdsAtrDesc(String strTdsAtrDesc) {
		this.strTdsAtrDesc = strTdsAtrDesc;
	}//end of setTdsAtrDesc(String strTdsAtrDesc)
	
	/** Retrieve the TDS Category Rate Seq ID
	 * @return the strTdsCatRateSeqID
	 */
	public Long getTdsCatRateSeqID() {
		return lngTdsCatRateSeqID;
	}//end of getTdsCatRateSeqID()
	
	/** Sets the TDS Category Rate Seq ID
	 * @param strTdsCatRateSeqID the strTdsCatRateSeqID to set
	 */
	public void setTdsCatRateSeqID(Long lngTdsCatRateSeqID) {
		this.lngTdsCatRateSeqID = lngTdsCatRateSeqID;
	}//end of setTdsCatRateSeqID(Long lngTdsCatRateSeqID)
	
	/** Retrieve the Applicable Rate Percentage
	 * @return the bdApplRatePerc
	 */
	public BigDecimal getApplRatePerc() {
		return bdApplRatePerc;
	}//end of getApplRatePerc()
	
	/** Sets the Applicable Rate Percentage
	 * @param bdApplRatePerc the bdApplRatePerc to set
	 */
	public void setApplRatePerc(BigDecimal bdApplRatePerc) {
		this.bdApplRatePerc = bdApplRatePerc;
	}//end of setApplRatePerc(BigDecimal bdApplRatePerc)
	
	/** Retrieve the Calculation
	 * @return the bdCalculation
	 */
	public BigDecimal getCalculation() {
		return bdCalculation;
	}//end of getCalculation()
	
	/** sets the Calculation
	 * @param bdCalculation the bdCalculation to set
	 */
	public void setCalculation(BigDecimal bdCalculation) {
		this.bdCalculation = bdCalculation;
	}//end of setCalculation(String strCalculation)
	
}//end of TDSCategoryRateVO