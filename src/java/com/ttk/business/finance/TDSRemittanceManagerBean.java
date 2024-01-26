/**
 *   @ (#) TDSRemittanceManagerBean.java August 06, 2009
 *   Project      : TTK HealthCare Services
 *   File         : TDSRemittanceManagerBean.java
 *   Author       : Navin Kumar R
 *   Company      : Span Systems Corporation
 *   Date Created : August 06, 2009
 *
 *   @author       :
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.business.finance;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.finance.FinanceDAOFactory;
import com.ttk.dao.impl.finance.TDSRemittanceDAOImpl;
import com.ttk.dto.finance.AcknowledgmentInfoVO;
import com.ttk.dto.finance.CertificateVO;
import com.ttk.dto.finance.MonthlyRemittanceDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class TDSRemittanceManagerBean implements TDSRemittanceManager{

	private FinanceDAOFactory financeDAOFactory = null;
	private TDSRemittanceDAOImpl tdsRemittanceDAOImpl = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getTDSRemittanceDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (financeDAOFactory == null)
				financeDAOFactory = new FinanceDAOFactory();
			if(strIdentifier!=null)
			{
				return financeDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else{
				return null;
			}//end of else
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getTDSRemittanceDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getDailyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException
	{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getDailyTransferList(alSearchCriteria);
	}//end of getDailyTransferList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alDailyTransferList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int setDailyTransfer(ArrayList<Object> alDailyTransferList) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.setDailyTransfer(alDailyTransferList);
	}//end of setDailyTransfer(ArrayList<Object> alDailyTransferList)

	/**
	 * This method returns the ArrayList, which contains the MonthlyRemittanceVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of MonthlyRemittanceVO object's which contains the details of the Monthly Remittance
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getMonthlyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getMonthlyTransferList(alSearchCriteria);
	}//end of getMonthlyTransferList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param monthlyRemittanceDetailVO MonthlyRemittanceDetailVO object which contains information for Monthly Remittance.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int saveMonthlyRemittance(MonthlyRemittanceDetailVO monthlyRemittanceDetailVO) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.saveMonthlyRemittance(monthlyRemittanceDetailVO);
	}//end of saveMonthlyRemittance(MonthlyRemittanceDetailVO monthlyRemittanceDetailVO)

	/**
	 * This method returns the MonthlyRemittanceDetailVO which contains the Monthly Remittance information.
	 * @param lngRepMasterSeqID long value which contains Report Master sequence id to get the Monthly Remittance information
	 * @return MonthlyRemittanceDetailVO this contains the Monthly Remittance information
	 * @throws TTKException
	 */
	public MonthlyRemittanceDetailVO getMonthlyRemitDetail(long lngRepMasterSeqID) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getMonthlyRemitDetail(lngRepMasterSeqID);
	}//end of getMonthlyRemitDetail(long lngRepMasterSeqID)

	/**
	 * This method returns the ArrayList, which contains the AcknowledgmentInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of AcknowledgmentInfoVO object's which contains the details of the Acknowledgement Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getAckInfoList(ArrayList<Object> alSearchCriteria) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getAckInfoList(alSearchCriteria);
	}//end of getAckInfoList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param ackInfoVO AcknowledgmentInfoVO object which contains information for Acknowledgement Information.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException.
	 */
	public int saveAckInfo(AcknowledgmentInfoVO ackInfoVO) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.saveAckInfo(ackInfoVO);
	}//end of saveAckInfo(AcknowledgmentInfoVO ackInfoVO)

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alInclExcluList ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> getIncludExcludTdsClms(ArrayList<Object> alInclExcluList) throws TTKException{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getIncludExcludTdsClms(alInclExcluList);
	}//end of getIncludExcludTdsClms(ArrayList<Object> alInclExcluList)

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alPreDailyTranList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int setInclExclTdsClaims(ArrayList<Object> alPreDailyTranList) throws TTKException{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.setInclExclTdsClaims(alPreDailyTranList);
	}//end of setInclExclTdsClaims(ArrayList<Object> alPreDailyTranList)

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> selectFinYearList(ArrayList<Object> alSearchCriteria) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.selectFinYearList(alSearchCriteria);
	}//end of selectFinYearList(String alSearchCriteria)

	/**
	  * This method returns the ArrayList, which contains the TDSHospitalInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of TDSHospitalInfoVO object's which contains the details of the tdsDeductedHospitals Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getTdsDeductedHospList(ArrayList<Object> alSearchCriteria) throws TTKException{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getTdsDeductedHospList(alSearchCriteria);
	}// end of getTdsDeductedHospList(ArrayList<Object> alSearchCriteria)

	/**
	 * This Method is used for certificate generation individually
	 * @param alGenerateList An arraylist Which Contains details of sequence id's which has to be Generated
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @throws TTKException
	 */
	public int generateTdsBatchInd(ArrayList<Object> alGenerateList) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.generateTdsBatchInd(alGenerateList);
	}// end of generateTdsBatchInd(ArrayList<Object> alGenerateList)

	/**
	 * This method returns the ArrayList, which contains the CertificateVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException
	 */
	public int generateTdsBatch(ArrayList<Object> alSearchCriteria) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.generateTdsBatch(alSearchCriteria);
	}//end of generateTdsBatch(ArrayList<Object> alSearchCriteria)
	
	/**
	 * This method returns the ArrayList, which contains the list of Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertSchedList() throws TTKException{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getGenTdsCertSchedList();
	}//end of getGenTdsCertSchedList()
	
	/**
	 * This method returns the ArrayList, which contains the list of Individual Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertIndSchedList() throws TTKException{
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		return tdsRemittanceDAOImpl.getGenTdsCertIndSchedList();
	}//end of getGenTdsCertIndSchedList()
	
	/**
	 * This method update the Hospital Info doc generated field.
	 * @param lngBatchSeqID Long object which contains TDS Batch Seq ID.
	 * @throws TTKException.
	 */
	public void saveDocGenInfo(Long lngBatchSeqID) throws TTKException {
		tdsRemittanceDAOImpl = (TDSRemittanceDAOImpl)this.getTDSRemittanceDAO("tdsremittance");
		tdsRemittanceDAOImpl.saveDocGenInfo(lngBatchSeqID);		
	}//end of saveDocGenInfo(Long lngBatchSeqID) 

}//end of TDSRemittanceManagerBean
