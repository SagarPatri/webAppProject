/** 
 *   @ (#) BankManager.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankManager.java
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

import java.util.ArrayList;
import javax.ejb.Local;
import java.io.InputStream;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.BankDetailVO;

@Local

public interface BankManager {
	
	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param lngHOSeqID long value which contains HeadOffice Seq ID
	 * @param lngUserSeqID long value which contains Logged-in User Seq ID
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankBranchList(long lngHOSeqID,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method returns the BankDetailVO which contains the Bank information 
	 * @param lngBankSeqID long value which contains bank seq id to get the Bank Detail information  
	 * @return BankDetailVO this contains the Bank information 
	 * @exception throws TTKException
	 */
	public BankDetailVO getBankDetail(long lngBankSeqID, long lngUserSeqID) throws TTKException;
	
	/**
	 * This method saves the Bank Detail information 
	 * @param bankDetailVO the object which contains the details of the Bank
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBank(BankDetailVO bankDetailVO,InputStream inputStream, int formFileSize,String finalPath) throws TTKException;
	
	/**
	 * This method deletes the Bank information from the database
	 * @param alDeleteList ArrayList object which contains seq id for Finance flow to delete the Bank information 
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteBank(ArrayList alDeleteList) throws TTKException;
	
}//end of BankManager