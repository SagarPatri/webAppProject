/**
 * 
 */
package com.ttk.dto.administration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * @author manikanta_k
 *
 */
public class ServTaxRateVO  extends BaseVO{
	private Date dtRevDateFrom =null; //rev_date_from
	private Date dtRevDateTo =null; //rev_date_to
	private ArrayList<Object> alServTaxRateDetailVOList = null;
	private String strReviseDateFrom = null;
	private Long lServTaxSeqId = null;
	private BigDecimal bdApplRatePerc =null;
	
	/**  Retrieve the ReviseDateFrom
	 * @return the dtRevDateFrom
	 */
	public Date getRevDateFrom() {
		return dtRevDateFrom;
	}//end of getRevDateFrom()
	
	/** sets the  RevDateFrom
	 * @param dtRevDateFrom the dtRevDateFrom to set
	 */
	public void setRevDateFrom(Date dtRevDateFrom) {
		this.dtRevDateFrom = dtRevDateFrom;
	}//end of setRevDateFrom(Date dtRevDateFrom)
	
	/** Retrieve the RevDateTo
	 * @return the dtRevDateTo
	 */
	public Date getRevDateTo() {
		return dtRevDateTo;
	}//end of getRevDateTo()
	
	/** sets the RevDateTo
	 * @param dtRevDateTo the dtRevDateTo to set
	 */
	public void setRevDateTo(Date dtRevDateTo) {
		this.dtRevDateTo = dtRevDateTo;
	}//end of setRevDateTo(Date dtRevDateTo)
	
	/** Retrieve the TDSCateRateDetailVOList
	 * @return the alTDSCateRateDetailVOList
	 */
	public ArrayList<Object> getServTaxRateDetailVOList() {
		return alServTaxRateDetailVOList;
	}//end of getTDSCateRateDetailVOList()
	
	/** sets the TDSCateRateDetailVOList
	 * @param alTDSCateRateDetailVOList the alTDSCateRateDetailVOList to set
	 */
	public void setServTaxRateDetailVOList(
			ArrayList<Object> alServTaxRateDetailVOList) {
		this.alServTaxRateDetailVOList = alServTaxRateDetailVOList;
	}//end of  setTDSCateRateDetailVOList(ArrayList<Object> alTDSCateRateDetailVOList)
	
	/** Retrieve the ReviseDateFrom()
	 * @return the strReviseDateFrom
	 */
	public String getReviseDateFrom() {
		return strReviseDateFrom;
	}//end of getReviseDateFrom()
	
	/** Sets the ReviseDateFrom
	 * @param strReviseDateFrom the strReviseDateFrom to set
	 */
	public void setReviseDateFrom(String strReviseDateFrom) {
		this.strReviseDateFrom = strReviseDateFrom;
	}//end of setReviseDateFrom(String strReviseDateFrom)

	/** Retrieve the ServRevDateFrom
	 * @return the dtRevDateFrom
	 */
	public String getServRevDateFrom() {
		return TTKCommon.getFormattedDate(dtRevDateFrom);
	}//end of getTdsRevDateFrom()
	
	/** Retrieve the ServRevDate To
	 * @return the dtRevDateTo
	 */
	public String getServRevDateTo() {
		return TTKCommon.getFormattedDate(dtRevDateTo);
	}//end of getTdsRevDateTo()

	/**
	 * @return the lServTaxSeqId
	 */
	public Long getServTaxSeqId() {
		return lServTaxSeqId;
	}//end of getServTaxSeqId()

	/**
	 * @param lServTaxSeqId the lServTaxSeqId to set
	 */
	public void setServTaxSeqId(Long lServTaxSeqId) {
		this.lServTaxSeqId = lServTaxSeqId;
	}//end of setServTaxSeqId(Long lServTaxSeqId)

	/**
	 * @return the bdApplRatePerc
	 */
	public BigDecimal getApplRatePerc() {
		return bdApplRatePerc;
	}//end of getApplRatePerc()

	/**
	 * @param bdApplRatePerc the bdApplRatePerc to set
	 */
	public void setApplRatePerc(BigDecimal bdApplRatePerc) {
		this.bdApplRatePerc = bdApplRatePerc;
	}//end of setApplRatePerc(BigDecimal bdApplRatePerc)
	
}//end of ServTaxRateVO
