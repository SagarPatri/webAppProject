/**
 *   @ (#) ChequeManagerBean.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : ChequeManagerBean.java
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
import java.sql.Clob;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.finance.ChequeDAOImpl;
import com.ttk.dao.impl.finance.FinanceDAOFactory;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.ChequeVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ChequeManagerBean implements ChequeManager{

	private FinanceDAOFactory financeDAOFactory = null;
	private ChequeDAOImpl chequeDAOImpl = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getFinanceDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (financeDAOFactory == null)
				financeDAOFactory = new FinanceDAOFactory();
			if(strIdentifier!=null)
			{
				return financeDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getFinanceDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO'S object's which contains the details of the cheque
	 * @exception throws TTKException
	 */
	public ArrayList getChequeList(ArrayList alSearchCriteria) throws TTKException
	{
		chequeDAOImpl = (ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.getChequeList(alSearchCriteria);
	}//end of getChequeList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ChequeDetailVO which contains the Cheque Detail information
	 * @param lngSeqID long value which contains cheque seq id to get the Cheque Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return ChequeDetailVO this contains the Cheque Detail information
	 * @exception throws TTKException
	 */
	public ChequeDetailVO getChequeDetail(Long lngSeqID, Long lngPaymentSeqId, Long lngFolatSeqId, Long lngUserSeqID) throws TTKException
	{
		chequeDAOImpl = (ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.getChequeDetail(lngSeqID,lngPaymentSeqId, lngFolatSeqId,lngUserSeqID);
	}//end of getChequeDetail(long lngSeqID,lngUserSeqID)

	/**
	 * This method saves the Cheque Detail information
	 * @param chequeDetailVO the object which contains the details of the Cheque
	 * @return long value which contains Cheque Seq ID
	 * @exception throws TTKException
	 */
	public long saveCheque(ChequeDetailVO chequeDetailVO) throws TTKException
	{
		chequeDAOImpl =(ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.saveCheque(chequeDetailVO);
	}//end of saveCheque(ChequeDetailVO chequeDetailVO)

	/**
	 * This method update the Cheque Detail information from maintenance-finance.
	 * @param chequeVO the object which contains the details of the Cheque
	 * @return int value which contains result
	 * @exception throws TTKException
	 */
	public int updateChequeInfo(ChequeVO chequeVO) throws TTKException
	{
		chequeDAOImpl =(ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.updateChequeInfo(chequeVO);
	}//end of updateChequeInfo(ChequeVO chequeVO) throws TTKException
	
	//bikki
	
	
	
	public ArrayList getLogExcelUpload(String startDate, String endDate,String flag)throws TTKException
	{
		chequeDAOImpl = (ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.getLogExcelUpload(startDate,endDate,flag);
	}
	
	
	public Object ChequeUploadExcel(String batchNo,FileReader fileReader,int fileLength)throws TTKException
	{
		chequeDAOImpl = (ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.ChequeUploadExcel(batchNo,fileReader,fileLength);
	}

	
	public ArrayList<String[]> getTotalStatusCount(String batchNo) throws TTKException {
		
		chequeDAOImpl = (ChequeDAOImpl)this.getFinanceDAO("cheque");
		return chequeDAOImpl.getTotalStatusCount(batchNo);
	}

	

}//end of ChequeManagerBean