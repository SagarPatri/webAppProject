/**
 *   @ (#) BankAccountManager.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankAccountManager.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.finance.BankAccountDAOImpl;
import com.ttk.dao.impl.finance.FinanceDAOFactory;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.finance.AuthorisedVO;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.BankGuaranteeVO;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.finance.TransactionVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class BankAccountManagerBean implements BankAccountManager{

    private BankAccountDAOImpl bankAccountDAO = null;
    private FinanceDAOFactory financeDAOFactory = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getBankAccountDAO(String strIdentifier) throws TTKException
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
    }//End of getBankAccountDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankList(ArrayList alSearchCriteria) throws TTKException
	{
		bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankList(alSearchCriteria);
	}//end of ArrayList getBankList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the BankAccountVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BankAccountVO'S object's which contains the details of the Bank Account
     * @exception throws TTKException
     */
    public ArrayList getBankAccountList(ArrayList alSearchCriteria) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankAccountList(alSearchCriteria);
    }//end of getBankAccountList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the BankGuaranteeVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BankGuaranteeVO'S object's which contains the details of the Bank Guarantee
     * @exception throws TTKException
     */
    public ArrayList getBankGuaranteeList(ArrayList alSearchCriteria) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankGuaranteeList(alSearchCriteria);
    }//end of getBankAccountList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ChequeVO'S object's which contains the details of the Cheque Series
     * @exception throws TTKException
     */
    public ArrayList getChequeSeriesList(ArrayList alSearchCriteria)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getChequeSeriesList(alSearchCriteria);
    }//end of getChequeSeriesList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the TransactionVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of TransactionVO'S object's which contains the details of the Bank Account Transaction
     * @exception throws TTKException
     */
    public ArrayList getTransactionList(ArrayList alSearchCriteria)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getTransactionList(alSearchCriteria);
    }//end of getTransactionList(ArrayList alSearchCriteria)

    /**
     * This method returns the BankAccountDetailVO, which contains all the bank details
     * @param lngBankAccSeqID  which contains seq id of Bank get the Bank Details
     * @return BankAccountDetailVO object which contains the Bank details
     * @exception throws TTKException
     */
    public BankAccountDetailVO getBankAccountDetail(long lngBankAccSeqID,long lngUserSeqID) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankAccountdetail(lngBankAccSeqID,lngUserSeqID);
    }//end of getBankAccountDetail(long lngBankAccSeqID)


    /**
     * This method returns the AuthorisedVO, which contains the Authorised Signatories details
     * @param lngAuthSeqID  which contains seq id of Authorised Signatories
     * @param lngBank_acc_seq_id  which Bank_acc_seq_id seq id of Authorised Signatories
     * @param lngUserSeqID  which contains UserSeqID of Authorised Signatories
     * @return AuthorisedVO object which contains the Authorised Signatories details
     * @exception throws TTKException
     */
    public AuthorisedVO getSignatorieDetail(long lngAuth_seq_id,long lngBank_acc_seq_id,long lngUserSeqID)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getSignatorieDetail(lngAuth_seq_id,lngBank_acc_seq_id,lngUserSeqID);
    }//end of getSignatorieDetail(long lngAuthSeqID)

    /**
     * This method returns the TransactionVO, which contains the Bank Account Transaction
     * @param lngTransSeqID  which contains seq id of Transaction get the Bank Account Transaction Details
     * @return TransactionVO object which contains the Bank Account Transaction Details
     * @exception throws TTKException
     */
    public TransactionVO getTransactionDetail(long bank_acct_seq_id,long lngTransSeqID,long lngUserSeqID )throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getTransactionDetail(bank_acct_seq_id,lngTransSeqID,lngUserSeqID);
    }//end of getTransactionDetail(long lngTransSeqID)

    /**
     * This method returns the BankGuaranteeVO, which contains the Bank Guarantee Details
     * @param lngGuaranteeSeqID  which Guarantee seq id of BankGuarantee get the Bank Guarantee Details
     * @return BankGuaranteeVO object which contains the Bank Guarantee Details
     * @exception throws TTKException
     */
    public BankGuaranteeVO getBankGuaranteeDetail(long lngGuaranteeSeqID,long lngUserSeqID) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankGuaranteeDetail(lngGuaranteeSeqID,lngUserSeqID);
    }//end of getBankGuaranteeDetail(long lngGuaranteeSeqID,long lngUserSeqID)

    
    /**
     * This method returns the BankGuaranteeVO, which contains the Bank Guarantee History Details
     * @param lngGuaranteeSeqID  which Guarantee seq id of BankGuarantee get the Bank Guarantee Details
     * @return BankGuaranteeVO object which contains the Bank Guarantee Details
     * @exception throws TTKException
     */
    public ArrayList<BankGuaranteeVO> getBankRenewHistory(long lngGuaranteeSeqID,long lngUserSeqID) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.getBankRenewHistory(lngGuaranteeSeqID,lngUserSeqID);
    }//end of getBankRenewHistory(long lngGuaranteeSeqID,long lngUserSeqID)
    
    
    /**
     * This method saves the Bank Account information
     * @param bankAccountDetailVO the object which contains the Bank details which has to be saved
     * @return long the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long saveBankAccount(BankAccountDetailVO bankAccountDetailVO) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.saveBankAccount(bankAccountDetailVO);
    }//end of saveBankAccount(BankAccountDetailVO bankAccountDetailVO)

    /**
     * This method saves the Authorised Signatories information
     * @param authorisedVO the object which contains the Authorised Signatories details which has to be saved
     * @return long the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long saveSignatories(AuthorisedVO authorisedVO)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.saveSignatories(authorisedVO);
    }//end of saveSignatories(AuthorisedVO authorisedVO)

    /**
     * This method saves the Cheque Series information
     * @param chequeVO the object which contains the Cheque Series details which has to be saved
     * @return long the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long saveChequeSeries(ChequeVO chequeVO)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.saveChequeSeries(chequeVO);
    }//end of saveChequeSeries(ChequeVO chequeVO)

    /**
     * This method saves the Bank Account Transaction detail information
     * @param transactionVO the object which contains theBank Account Transaction detail information which has to be saved
     * @return long the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long saveTransaction(TransactionVO transactionVO)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.saveTransaction(transactionVO);
    }//end of saveTransaction(TransactionVO transactionVO)

    /**
     * This method saves the Bank Guarantee information
     * @param bankGuaranteeVO the object which contains the Bank Guarantee details which has to be saved
     * @return long the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long saveBankGuarantee(BankGuaranteeVO bankGuaranteeVO) throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.saveBankGuarantee(bankGuaranteeVO);
    }//end of saveBankGuarantee(BankGuaranteeVO bankGuaranteeVO)

    /**
     * This method deletes the bank information from the database
     * @param alBankAccount arraylist which contains the details of bank
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteBankAccount(ArrayList alBankAccount)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.deleteBankAccount(alBankAccount);
    }//end of deleteBankAccount(ArrayList alBankAccount)

    /**
     * This method deletes the Cheque Series information from the database
     * @param alDeleteList arraylist which contains the details of Cheque Series
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteChequeSeries(ArrayList alDeleteList)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.deleteChequeSeries(alDeleteList);
    }//end of deleteChequeSeries(ArrayList alDeleteList)

    /**
     * This method deletes the Bank Guarantee Series information from the database
     * @param alDeleteList arraylist which contains the details of Bank Guarantee
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteBankGuarantee(ArrayList alDeleteList)throws TTKException
    {
        bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
        return bankAccountDAO.deleteBankGuarantee(alDeleteList);
    }//end of deleteChequeSeries(ArrayList alDeleteList)

    /**
     * This method saves the Reverse Transaction information
     * @param alReverseTransList arraylist which contains the details of Transaction Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int reverseTransaction(ArrayList alReverseTransList) throws TTKException {
    	bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
    	return bankAccountDAO.reverseTransaction(alReverseTransList);
    }//end of reverseTransaction(ArrayList alReverseTransList)
    
    
    public ArrayList<CacheObject> getCurrencyMaster() throws TTKException {
    	bankAccountDAO=(BankAccountDAOImpl)this.getBankAccountDAO("bankaccount");
    	return bankAccountDAO.getCurrencyMaster();
    }//end of reverseTransaction(ArrayList alReverseTransList)
    
}//end of BankAccountManagerBean
