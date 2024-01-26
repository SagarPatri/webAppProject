/**
 *   @ (#) ChequeManager.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : ChequeManager.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 07, 2006
 *
 *   @author       :  Balakrishna E
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */
package com.ttk.business.finance;

import java.io.FileReader;
import java.io.InputStream;
import java.sql.Clob;
import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.ChequeVO;

@Local

public interface ChequeManager {

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO'S object's which contains the details of the cheque
	 * @exception throws TTKException
	 */
	public ArrayList getChequeList(ArrayList alSearchCriteria) throws TTKException;

	/**
	 * This method returns the ChequeDetailVO which contains the Cheque Detail information
	 * @param lngSeqID long value which contains cheque seq id to get the Cheque Detail information
	 * @param lngUserSeqID long value which contailns the Logged-in User
	 * @return ChequeDetailVO this contains the Cheque Detail information
	 * @exception throws TTKException
	 */
	public ChequeDetailVO getChequeDetail(Long lngSeqID, Long lngPaymentSeqId, Long lngFolatSeqId, Long lngUserSeqID) throws TTKException;

	/**
	 * This method saves the Cheque Detail information
	 * @param chequeDetailVO the object which contains the details of the Cheque
	 * @return long value which contains Cheque Seq ID
	 * @exception throws TTKException
	 */
	public long saveCheque(ChequeDetailVO chequeDetailVO) throws TTKException;
	
	/**
	 * This method update the Cheque Detail information from maintenance-finance.
	 * @param chequeVO the object which contains the details of the Cheque
	 * @return int value which contains result
	 * @exception throws TTKException
	 */
	public int updateChequeInfo(ChequeVO chequeVO) throws TTKException;
	
	//bikki
	
	public ArrayList getLogExcelUpload(String startDate, String endDate, String flag)throws TTKException;
	
	public Object ChequeUploadExcel(String batchNo,FileReader fileReader,int fileLength)throws TTKException;

	public ArrayList<String[]> getTotalStatusCount(String batchNo) throws TTKException;



}//end of ChequeManager