/**
 *   @ (#) InviceVO.java Nov 06, 2006
 *   Project      : TTK HealthCare Services
 *   File         : InviceVO.java
 *   Author       : Krishna K H
 *   Company      : Span Systems Corporation
 *   Date Created : Nov 06, 2006
 *
 *   @author       : Krishna K H
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dto.finance;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains bank information.
 */

public class InvoiceBatchVO extends BaseVO{

    private Long lngSeqID = null;
    private String strNbrPolicy= "";
    private String strBatchName= "";
    private Date dtBatchDate =null;

    /** Retrieve the Batch Seq ID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()

    /** Sets the Batch Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngInvoiceSeqID)

   /** Retrieve the Batch Name
	 * @return Returns the strBatchName.
	 */
	public String getBatchName() {
		return strBatchName;
	}//end of getBatchName()

	/** Sets the Batch Name
	 * @param strBatchName The strBatchName to set.
	 */
	public void setBatchName(String strBatchName) {
		this.strBatchName = strBatchName;
	}//end of setBatchName(String strBatchName)

	/**Retrieve the Number of Policy in Batch.
     * @return Returns the strNbrPolicy.
     */
    public String getNbrPolicy() {
        return strNbrPolicy;
    }//end of getNbrPolicy()

    /**Sets the Number of Policy in Batch.
     * @param strNbrPolicy The strNbrPolicy to set.
     */
    public void setNbrPolicy(String strNbrPolicy) {
        this.strNbrPolicy = strNbrPolicy;
    }//end of setNbrPolicy(String strNbrPolicy)

    /**
     * Retrieve the Formatted Batch Date.
     * @return Returns the dtBatchDate.
     */
    public String getDisplayBatchDate() {
        return TTKCommon.getFormattedDateHour(dtBatchDate);
    }//end of getDisplayBatchDate()

    /**
     * Retrieve the Batch Date.
     * @return Returns the dtBatchDate.
     */
    public Date getBatchDate() {
        return dtBatchDate;
    }//end of getBatchDate()

     /**Sets the Batch Date.
     * @param dtBatchDate The dtBatchDate to set.
     */
    public void setBatchDate(Date dtBatchDate) {
        this.dtBatchDate = dtBatchDate;
    }//end of setBatchDate(String dtBatchDate)
}//end of InviceVO