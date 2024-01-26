/**
 *   @ (#) FloatAccountManager.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : FloatAccountManager.java
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
import java.util.Date;
import java.util.HashMap;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.finance.AssocGroupVO;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.DebitNoteVO;
import com.ttk.dto.finance.FloatAccountDetailVO;
import com.ttk.dto.finance.InvoiceVO;
import com.ttk.dto.finance.TransactionVO;

@Local

public interface FloatAccountManager {

	/**
	 * This method returns the ArrayList, which contains the FloatAccountVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of FloatAccountVO'S object's which contains the details of the Float Account
	 * @exception throws TTKException
	 */
	public ArrayList getFloatAccountList(ArrayList alSearchCriteria) throws TTKException;

	/**
	 * This method returns the FloatAccountDetailVO which contains the Float Account Detail information
	 * @param lngFloatAcctSeqID long value which contains FloatAccount seq id to get the Float Account Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return FloatAccountDetailVO this contains the Float Account Detail information
	 * @exception throws TTKException
	 */
	public FloatAccountDetailVO getFloatAccountDetail(long lngFloatAcctSeqID, long lngUserSeqID) throws TTKException;

	/**
	 * This method saves the Float Account Detail information
	 * @param floatAccountDetailVO the object which contains the details of the Float account
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public long saveFloatAccount(FloatAccountDetailVO floatAccountDetailVO) throws TTKException;

	/**
	 * This method deletes the Float Account Detail information from the database
	 * @param alDeleteList ArrayList object which contains seq id for Finance flow to delete the Float Account information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteFloatAccount(ArrayList alDeleteList) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the TransactionVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of TransactionVO's object's which contains the details of the Float Transaction
	 * @exception throws TTKException
	 */
	public ArrayList getFloatTransactionList(ArrayList alSearchCriteria) throws TTKException;

	/**
	 * This method returns the TransactionVO which contains the Float transaction information
	 * @param lngFloatTransSeqID long value which contains float transaction seq id to get the Float Account Detail information
	 * @param lngFloatSeqID long value which contains Float seq id to get the Float Account Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return TransactionVO this contains the Transaction information
	 * @exception throws TTKException
	 */
	public TransactionVO getFloatTransactionDetail(long lngFloatTransSeqID, long lngFloatSeqID, long lngUserSeqID) throws TTKException;

	/**
	 * This method saves the Float Transaction information
	 * @param transactionVO the object which contains the details of the transaction
	 * @return long value contains Transaction Seq ID
	 * @exception throws TTKException
	 */
	public long saveFloatTransaction(TransactionVO transactionVO) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException;

	/**
	 * This method returns the ChequeVO which contains the Claims information
	 * @param lngClaimSeqID long value which contains claims seq id to get the Claims information
	 * @param lngFloatAcctSeqID long value which contains FloatAccount seq id to get the Float Account Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return ChequeDetailVO this contains the Claims information
	 * @exception throws TTKException
	 */
	public ChequeDetailVO getClaimDetail(long lngClaimSeqID, long lngFloatAccSeqID, long lngUserSeqID) throws TTKException;

	/**
	 * This method saves the Claims information
	 * @param ChequeDetailVO the object which contains the details of the Claim
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveClaims(ChequeDetailVO chequeDetailVO) throws TTKException;

	/**
	 * This method generates the Cheque information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateCheque(ArrayList alChequeList) throws TTKException;

	/**
     * This method saves the Reverse Transaction information
     * @param alReverseTransList arraylist which contains the details of Transaction Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int reverseTransaction(ArrayList alReverseTransList) throws TTKException;

    /**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
  //start changes for cr koc1103 and 1105
    /**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getEftPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException;
	/**
	 * This method returns the Void, which Generate mail
	 * @param ConcatenatedSeqID 
	 * @param lngUserSeqID
	 * @return  void
	 * @exception throws TTKException
	 */
	public void generateMail(String strConcatenatedSeqID,long lngUserSeqID)throws TTKException;
	
	//end changes for cr koc1103 and 1105
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException;
	

	/**
	 * This method generates the Cheque information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int generateChequeAdvice(ArrayList alChequeList) throws TTKException;

	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateChequeAdviceXL(ArrayList alChequeList) throws TTKException;
	
	
	/*
	 * 
	 */
	public HashMap generateChequeAdviceENBDXL(ArrayList alChequeList) throws TTKException;
	
	public long getENBDCountandAccNum(long alChequeList,String mode) throws TTKException;
	
	public TTKReportDataSource generateChequeAdviceENBDConsXL(ArrayList alChequeList) throws TTKException;
	
	
	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateOICPaymentAdvice(ArrayList alChequeList) throws TTKException;
	
	/**
	 * This method generates the Cheque Advice Detail XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateChequeAdviceXLDetails(ArrayList alChequeList) throws TTKException;
	
	// Change added for BOA CR KOC1220
	/**
	 * This method generates the Payment Advice Detail XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateBOAXLDetails(ArrayList alChequeList) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getViewPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException;

    /**
	 * This method generates the EFT information
	 * @param alChequeList contains Seq ID's to generate EFT information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int setFundTransfer(ArrayList alChequeList) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the DebitNoteVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DebitNoteVO's object's which contains the details of the Debit Note
	 * @exception throws TTKException
	 */
	public ArrayList getDebitNoteList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the DebitNoteVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DebitNoteVO's object's which contains the details of the Debit Note
	 * @exception throws TTKException
	 */
	public ArrayList getBatchList(ArrayList alSearchCriteria) throws TTKException; 
	
	/**
	 * This method deletes the Debit Note Deposit information
	 * @param strDebitSeqId String Object contains Pipe concatenated Debit Seq ID's
	 * @param lngUserSeqID long value contains User Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteDebitNote(String strDebitSeqId,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method returns the DebitNoteVO which contains the Debit Note details
	 * @param lngDebitNoteSeqID long value which contains Debit Note seq id to get the Debit Note details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return DebitNoteVO this contains the Debit Note details
	 * @exception throws TTKException
	 */
	public DebitNoteVO getDebitNoteDetail(long lngDebitNoteSeqID,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method saves the Debit Note details
	 * @param DebitNoteVO the object which contains Debit Note details
	 * @return long contains Debit Note Seq ID
	 * @exception throws TTKException
	 */
	public long saveDebitNoteDetail(DebitNoteVO debitNoteVO) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getDebitNoteClaimList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method Associates Claim(s) to Debit Note
	 * @param alClaimsList the object which contains the details of the Claims
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associateClaims(ArrayList alClaimsList) throws TTKException;
	
	/**
	 * This method returns the String which contains the Float Type ID for displaying the Transaction Type
	 * @param lngFloatSeqID long value which contains Float seq id to get the Float Type ID
	 * @return String this contains the Float Type ID
	 * @exception throws TTKException
	 */
	public String getFloatType(long lngFloatSeqID) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the DebitNoteDepositVO's which are populated from the database
	 * @param lngFloatSeqID long value which contains Float seq id to get the Debit Note details
	 * @param lngFloatTransSeqID long value which contains Float Transaction seq id to get the Debit Note details
	 * @return ArrayList of DebitNoteDepositVO's object's which contains the details of the Debit Note
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getDebitNoteDepositList(long lngFloatSeqID,long lngFloatTransSeqID) throws TTKException;
	
	/**
	 * This method saves the Debit Note Deposit information
	 * @param alDepositList ArrayList contains Debit Note Deposit information
	 * @param lngFloatTransSeqID long value contains Float Trans Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveDebitNoteDepositDetail(ArrayList alDepositList,long lngFloatTransSeqID) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the ClaimDepositVO's which are populated from the database
	 * @param lngDebitSeqID long value which contains Debit seq id to get the Claim Deposit details
	 * @param lngFloatTransSeqID long value which contains Float Transaction seq id to get the Claim Deposit details
	 * @return ArrayList of ClaimDepositVO's object's which contains the details of the Claim Deposit
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getClaimDepositList(long lngDebitSeqID,long lngFloatTransSeqID) throws TTKException;
	
	/**
	 * This method saves the Claim(s) Deposit information
	 * @param alClaimDepositList ArrayList contains Claim(s) Deposit information
	 * @param lngDebitNoteTransSeqID long value contains Debit Note Trans Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveClaimDepositDetail(ArrayList alClaimDepositList,long lngDebitNoteTransSeqID) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains the InvoiceVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InvoiceVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
	public ArrayList getInvoiceList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the InvoiceVO which contains the Invoice details
	 * @param lngInvoiceSeqID long value which contains invoice seq id to get the Invoice details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return InvoiceVO this contains the Invoice details
	 * @exception throws TTKException
	 */
	public InvoiceVO getInvoiceDetail(long lngInvoiceSeqID,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method saves the Invoice details
	 * @param invoiceVO the object which contains Invoice details
	 * @return long contains Invoice Seq ID
	 * @exception throws TTKException
	 */
	public long saveInvoiceDetail(InvoiceVO invoiceVO) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of ChequeVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getInvoicePolicyList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
	 * This method Associates Policies to the Invoice
	 * @param alPolicyList the object which contains the details of the Policies
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associatePolicy(ArrayList alPolicyList) throws TTKException;
	
	/**
	 * This method Associates/Excludes all the Policies to/from the Invoice
	 * @param alPolicyList the object which contains the details of the Policies
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associateAll(ArrayList alPolicyList) throws TTKException;
	
	/**
	 * This method returns the Max of Invoice to Date
	 * @return date the value which contains the Max of Invoice to Date
	 * @exception throws TTKException
	 */
	public Date getNewInvoiceFromDate() throws TTKException;

    /**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param strPolicySeqID String which contains Policy Seq ID
     * @param strEnrollType String which contains Enrollment Type
     * @param lngAddedBy Long which contains Added User Seq ID
     * @return ArrayList of InviceVO's object's which contains the details of the Invice
     * @exception throws TTKException
     */
    public TTKReportDataSource getGenerateInvoiceList(String strPolicySeqID,String strEnrollType,Long lngAddedBy) throws TTKException;

    /**
     * This method deletes the Invoice Detail information from the database
     * @param alDeleteList ArrayList object which contains seq id for Invoice flow to delete the Invoice information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteInvoice(ArrayList alDeleteList) throws TTKException;

    /**
     * This method returns the ArrayList, which contains the InvoiceBatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InvoiceBatchVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getViewInvoiceBatch(ArrayList alSearchCriteria) throws TTKException;
    
    /**
	 * This method returns the String which contains the Batch file name to save the file with the Batch file name
	 * @param strBatchNo long value which contains Batch number to get the Batch file name
	 * @return String this contains the Batch file name
	 * @exception throws TTKException
	 */
	public String getBatchFileName(String strBatchNo) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getBatchClaimList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the AssocGroupVO's which are populated from the database.
	 * @param lngFloatSeqID Long which contains Search Criteria
	 * @return ArrayList of AssocGroupVO'S object's which contains the details of the Groups associated to Float Account
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getFloatAssocGrpList(Long lngFloatSeqID) throws TTKException;
	
	/**
	 * This method associates the Group information to Float account.
	 * @param assocGroupVO the object which contains the details of the Associate Group information
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveFloatAssocGrp(AssocGroupVO assocGroupVO) throws TTKException;
	
	public ArrayList<CacheObject> getSelectedProducts(String inSeqId) throws TTKException;
	
	
	

	public int deleteFloatAssocGrp(Long floatGrpAssSeqID) throws TTKException;

	/**
	 * This method deletes the Associated Group information from the database.
	 * @param lngFloatGrpAssocSeqID Long object which contains seq id for Finance flow to delete the Associate Group information
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */


	public ArrayList setPaymentUploadExcel(ArrayList<Object> alPrintCheque)throws TTKException;

	public ArrayList getLogDetailsExcelUpload(String startDate, String endDate, String flag)throws TTKException;

	public HashMap<String, TTKReportDataSource> generateChequeAdviceUploadENBDXL(ArrayList<Object> alGenerateXL)throws TTKException;

	public ArrayList getLogDetailsPaymentAdviceExcelUpload(String startDate,String endDate)throws TTKException;

	public int associateClaimsExcelUpload(ArrayList<Object> alClaimsList)throws TTKException;

	
}//end of FloatAccountManager