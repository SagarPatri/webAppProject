/**
 * @ (#) DiscrepancyVO.java Jun 24, 2006
 * Project 	     : TTK HealthCare Services
 * File          : DiscrepancyVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 24, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

public class DiscrepancyVO extends PreAuthVO {
	
	private Long lngDiscrepancySeqID = null;
	private String strDiscrepancy = "";
	private String strResolvedYN = "";
	private String strDiscrepancyTypeID = "";
	private String strRemarks = "";
	private String strResolvableYN = "";
	
	/** Retrieve the ResolvableYN
	 * @return Returns the strResolvableYN.
	 */
	public String getResolvableYN() {
		return strResolvableYN;
	}//end of getResolvableYN()

	/** Sets the ResolvableYN
	 * @param strResolvableYN The strResolvableYN to set.
	 */
	public void setResolvableYN(String strResolvableYN) {
		this.strResolvableYN = strResolvableYN;
	}//end of setResolvableYN(String strResolvableYN)

	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()

	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)

	/** Retrieve the Discrepancy Seq ID
	 * @return Returns the lngDiscrepancySeqID.
	 */
	public Long getDiscrepancySeqID() {
		return lngDiscrepancySeqID;
	}//end of getDiscrepancySeqID()

	/** Sets the Discrepancy Seq ID
	 * @param lngDiscrepancySeqID The lngDiscrepancySeqID to set.
	 */
	public void setDiscrepancySeqID(Long lngDiscrepancySeqID) {
		this.lngDiscrepancySeqID = lngDiscrepancySeqID;
	}//end of setDiscrepancySeqID(Long lngDiscrepancySeqID)

	/** Retrieve the Discrepancy Type ID
	 * @return Returns the strDiscrepancyTypeID.
	 */
	public String getDiscrepancyTypeID() {
		return strDiscrepancyTypeID;
	}//end of getDiscrepancyTypeID()

	/** Sets the Discrepancy Type ID
	 * @param strDiscrepancyTypeID The strDiscrepancyTypeID to set.
	 */
	public void setDiscrepancyTypeID(String strDiscrepancyTypeID) {
		this.strDiscrepancyTypeID = strDiscrepancyTypeID;
	}//end of setDiscrepancyTypeID(String strDiscrepancyTypeID)

	/** Retrieve the Discrepancy
	 * @return Returns the strDiscrepancy.
	 */
	public String getDiscrepancy() {
		return strDiscrepancy;
	}//end of getDiscrepancy()

	/** Sets the Discrepancy
	 * @param strDiscrepancyYN The strDiscrepancyYN to set.
	 */
	public void setDiscrepancy(String strDiscrepancy) {
		this.strDiscrepancy = strDiscrepancy;
	}//end of setDiscrepancy(String strDiscrepancy)

	/** Retrieve the ResolvedYN
	 * @return Returns the strResolvedYN.
	 */
	public String getResolvedYN() {
		return strResolvedYN;
	}//end of getResolvedYN()

	/** Sets the ResolvedYN
	 * @param strResolvedYN The strResolvedYN to set.
	 */
	public void setResolvedYN(String strResolvedYN) {
		this.strResolvedYN = strResolvedYN;
	}//end of setResolvedYN(String strResolvedYN)

}//end of DiscrepancyVO
