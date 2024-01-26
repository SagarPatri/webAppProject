/**
 *   @ (#) TDSRemittanceManager.java August 06, 2009
 *   Project      : TTK HealthCare Services
 *   File         : TDSRemittanceManager.java
 *   Author       : Navin Kumar R
 *   Company      : Span Systems Corporation
 *   Date Created : August 06, 2009
 *
 *   @author       :  Navin Kumar R
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.business.finance;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.AcknowledgmentInfoVO;
import com.ttk.dto.finance.CertificateVO;
import com.ttk.dto.finance.MonthlyRemittanceDetailVO;

@Local

public interface TDSRemittanceManager {

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> getDailyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException;

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alDailyTransferList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException.
	 */
	public int setDailyTransfer(ArrayList<Object> alDailyTransferList) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the MonthlyRemittanceVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of MonthlyRemittanceVO object's which contains the details of the Monthly Remittance
	 * @throws TTKException
	 */
	public ArrayList<Object> getMonthlyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException;

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param monthlyRemittanceDetailVO MonthlyRemittanceDetailVO object which contains information for Monthly Remittance.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException.
	 */
	public int saveMonthlyRemittance(MonthlyRemittanceDetailVO monthlyRemittanceDetailVO) throws TTKException;

	/**
	 * This method returns the MonthlyRemittanceDetailVO which contains the Monthly Remittance information.
	 * @param lngRepMasterSeqID long value which contains Report Master sequence id to get the Monthly Remittance information
	 * @return MonthlyRemittanceDetailVO this contains the Monthly Remittance information
	 * @throws TTKException
	 */
	public MonthlyRemittanceDetailVO getMonthlyRemitDetail(long lngRepMasterSeqID) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the AcknowledgmentInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of AcknowledgmentInfoVO object's which contains the details of the Acknowledgement Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getAckInfoList(ArrayList<Object> alSearchCriteria) throws TTKException;

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param ackInfoVO AcknowledgmentInfoVO object which contains information for Acknowledgement Information.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException.
	 */
	public int saveAckInfo(AcknowledgmentInfoVO ackInfoVO) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alInclExcluList ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> getIncludExcludTdsClms(ArrayList<Object> alInclExcluList) throws TTKException;

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alPreDailyTranList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int setInclExclTdsClaims(ArrayList<Object> alPreDailyTranList) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> selectFinYearList(ArrayList<Object> alSearchCriteria) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the TDSHospitalInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of TDSHospitalInfoVO object's which contains the details of the tdsDeductedHospitals Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getTdsDeductedHospList(ArrayList<Object> alSearchCriteria) throws TTKException;

	/**
	 * This Method is used for certificate generation individually
	 * @param alGenerateList An arraylist Which Contains details of sequence id's which has to be Generated
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @throws TTKException
	 */
	public int generateTdsBatchInd(ArrayList<Object> alGenerateList) throws TTKException ;

	/**
	 * This method returns the ArrayList, which contains the CertificateVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException
	 */
	public int generateTdsBatch(ArrayList<Object> alSearchCriteria) throws TTKException ;
	
	/**
	 * This method returns the ArrayList, which contains the list of Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertSchedList() throws TTKException ;
	
	/**
	 * This method returns the ArrayList, which contains the list of Individual Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertIndSchedList() throws TTKException ;
	
	 /**
	 * This method update the Hospital Info doc generated field.
	 * @param lngBatchSeqID Long object which contains TDS Batch Seq ID.
	 * @throws TTKException.
	 */
	public void saveDocGenInfo(Long lngBatchSeqID) throws TTKException;

}//end of TDSRemittanceManager
