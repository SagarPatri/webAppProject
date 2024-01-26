/**
 * @ (#) FinanceDAOFactory.java June 06, 2006
 * Project      : 
 * File         : FinanceDAOFactory.java
 * Author       : Balakrishna E
 * Company      : 
 * Date Created : June 06, 2006
 *
 * @author       :  Balakrishna E
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.finance;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class FinanceDAOFactory extends DAOFactory{
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("bank"))
		{
			baseDAO = new BankDAOImpl();
		}//end of if (strIdentifier.equals("bank"))
		else if (strIdentifier.equals("bankaccount"))
		{
			baseDAO = new BankAccountDAOImpl();
		}//end of else if (strIdentifier.equals("bankaccount"))
		else if (strIdentifier.equals("floataccount"))
		{
			baseDAO = new FloatAccountDAOImpl();
		}//end of else if (strIdentifier.equals("floataccount"))
		else if (strIdentifier.equals("cheque"))
		{
			baseDAO = new ChequeDAOImpl();
		}//end of else if (strIdentifier.equals("cheque"))
		else if (strIdentifier.equals("debitNote"))
		{
			baseDAO = new DebitNoteDAOImpl();
		}//end of else if (strIdentifier.equals("debitNote"))
		else if (strIdentifier.equals("invoice"))
		{
			baseDAO = new InvoiceDAOImpl();
		}//end of else if (strIdentifier.equals("invoice"))
		else if (strIdentifier.equals("tdsremittance"))
		{
			baseDAO = new TDSRemittanceDAOImpl();
		}//end of else if (strIdentifier.equals("tdsremittance"))
		return baseDAO;
	}//end of getDAO(String strIdentifier) 
	
}//end of FinanceDAOFactory
