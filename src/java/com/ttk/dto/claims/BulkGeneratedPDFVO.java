/*Added asw per KOC 1179 
 * 
 */

package com.ttk.dto.claims;

import java.util.Date;
import com.ttk.common.TTKCommon;

import com.ttk.dto.BaseVO;

public class BulkGeneratedPDFVO extends BaseVO {
	private String strFileName="";
	private String strBatchNumber="";
	private Date dtBatchDate=null;
	private Date dtBatchDate1=null;
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return this.strFileName;
	}
	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return this.strBatchNumber;
	}
	/**
	 * @return the batchDate
	 */
	public Date getBatchDate() {
		return this.dtBatchDate;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.strFileName = fileName;
	}
	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.strBatchNumber = batchNumber;
	}
	/**
	 * @param batchDate the batchDate to set
	 */
	public void setBatchDate(Date batchDate) {
		this.dtBatchDate = batchDate;
	}
	/**
	 * @return the batchDate1
	 */
	
	
	public String getBatchDate1() {
		return TTKCommon.getFormattedDateHour(dtBatchDate1);
	}

	/**
	 * @param batchDate1 the batchDate1 to set
	 */
	public void setBatchDate1(Date batchDate1) {
		this.dtBatchDate1 = batchDate1;
	}
	

}
