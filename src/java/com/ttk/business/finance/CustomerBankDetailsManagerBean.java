package com.ttk.business.finance;

import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.AccountDAOImpl;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dao.impl.finance.BankAccountDAOImpl;
import com.ttk.dao.impl.finance.CustomerBankDetailsDAOFactory;
import com.ttk.dao.impl.finance.CustomerBankDetailsDAOImpl;

//import com.ttk.dao.impl.finance.FinanceNewDAOFactory;
//import com.ttk.dao.impl.finance.FinanceTestDAOImpl;

import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.finance.CustomerBankDetailsVO;


import com.ttk.common.exception.TTKException;
/**
 * This class is added for cr koc 1103
 * added eft
 */
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class CustomerBankDetailsManagerBean implements CustomerBankDetailsManager {
	private CustomerBankDetailsDAOImpl customerBankDAO = null;
    private CustomerBankDetailsDAOFactory customerBankDetailsDAOFactory = null;
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getFinanceBankNewDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (customerBankDetailsDAOFactory == null)
                customerBankDetailsDAOFactory = new CustomerBankDetailsDAOFactory();

            if(strIdentifier!=null)
            {
                return customerBankDetailsDAOFactory.getDAO(strIdentifier);
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
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
	public HashMap getbankStateInfo()throws TTKException {
		
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
    	return customerBankDAO.getbankStateInfo();
	}
	 /**
	 * This method returns the HashMap,which contains the Distict Types associating the State
	 * @return HashMap containing Distict Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
    public HashMap getBankCityInfo(String bankState,String bankName)throws TTKException {
		
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
    	return customerBankDAO.getBankCityInfo(bankState,bankName);
	}
    /**
	 * This method returns the HashMap,which contains the BankBranch Types associating the State,BankNmae,BankCity
	 * @return HashMap containing BankBranch Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
   public HashMap getBankBranchtInfo(String bankState,String bankName,String bankDistict)throws TTKException {
		
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
    	return customerBankDAO.getBankBranchtInfo(bankState,bankName,bankDistict);
	}
   /**
	 * This method returns the HashMap,which contains the IFSC Types associating the State,BankNmae,BankCity,BankBranch
	 * @return CustomerBankDetailsVO which containes IFSC code
	 * @exception throws TTKException
	 */
   public CustomerBankDetailsVO getBankIfscInfo(String bankState,String bankName,String bankDistict,String bankBranch)throws TTKException {
		
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
   	return customerBankDAO.getBankIfscInfo(bankState,bankName,bankDistict,bankBranch);
	}
   
   /**
	 * This method returns the ArrayList, which contains the customerBankDetailsVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of customerBankDetailsVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
   public ArrayList getCustomerBankAccountList(ArrayList alSearchCriteria) throws TTKException
       {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
           return customerBankDAO.getCustomerBankAccountList(alSearchCriteria);
       }//end of getBankAccountList(ArrayList alSearchCriteria)
   /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
   public CustomerBankDetailsVO getPolicyBankAccountDetail(long lngBankAccSeqID,long lngUserSeqID) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.getPolicyBankAccountDetail(lngBankAccSeqID,lngUserSeqID);
   }//end of getBankAccountDetail(long lngBankAccSeqID)
   /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains hospital seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
   public CustomerBankDetailsVO getHospBankAccountDetail(long lngBankAccSeqID,long lngUserSeqID) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.getHospBankAccountdetail1(lngBankAccSeqID,lngUserSeqID);
   }//end of getBankAccountDetail(long lngBankAccSeqID)
   
   /**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains membergroup seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
   
   
   public int saveMouUploads(CustomerBankDetailsVO customerBankDetailsVO,ArrayList<Object> alFileAUploadList, Long userSeqId,String hosp_seq_id, String origFileName, InputStream inputStream,int formFileSize)throws TTKException{
	 
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.saveMouUploads(customerBankDetailsVO,alFileAUploadList,userSeqId,hosp_seq_id,origFileName,inputStream,formFileSize);

   }
   
   public ArrayList<CustomerBankDetailsVO> getMouUploadsList(String hosp_seq_id) throws TTKException{

	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.getMouUploadsList(hosp_seq_id);
   }
   
   public CustomerBankDetailsVO getMemberBankAccountDetail(long lngBankAccSeqID,long lngUserSeqID) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.getMemberBankAccountdetail(lngBankAccSeqID,lngUserSeqID);
   }//end of getBankAccountDetail(long lngBankAccSeqID)


   /**
 	 * This method saves the Policy Bank Account information
 	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
 	 * @return long the value which returns greater than zero for succcesssful execution of the task
 	 * @exception throws TTKException
 	 */
   public long saveBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.saveBankAccountIfsc(customerBankDetailsVO);
   }//end of saveBankAccount(BankAccountDetailVO bankAccountDetailVO)
   
   /**
	 * This method saves the Member Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
      public long saveMemberBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.saveMemberBankAccountIfsc(customerBankDetailsVO);
   }//end of saveBankAccount(BankAccountDetailVO bankAccountDetailVO)
     /**
  	 * This method saves the Hospiatl Bank Account information
  	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
  	 * @return long the value which returns greater than zero for succcesssful execution of the task
  	 * @exception throws TTKException
  	 */
      public long saveHospBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
   {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
       return customerBankDAO.saveHospBankAccountIfsc(customerBankDetailsVO);
   }//end of saveBankAccount(BankAccountDetailVO bankAccountDetailVO)
   
   //end eft
      
      public ArrayList getCustomerBankAccountReviewList() throws TTKException
      {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
          return customerBankDAO.getCustomerBankAccountReviewList();
      }//end of getBankAccountList(ArrayList alSearchCriteria)

      public ArrayList getCustomerBankAccountReviewList(String flag) throws TTKException
      {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
          return customerBankDAO.getCustomerBankAccountReviewList(flag);
      }//end of getBankAccountList(ArrayList alSearchCriteria)
      
      //INTx
      /**
  	 * This method returns the HashMap,which contains the City Types associating the State
  	 * @return HashMap containing City Types associating the State
  	 * @exception throws TTKException
  	 */
      public HashMap getCityInfo(String stateCode) throws TTKException {
    	  customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
      	return customerBankDAO.getCityInfo(stateCode);
      }//end of getCityInfo(stateCode)
 
      /**
  	 * This method returns the HospitalDetailVO, which contains all the hospital details
  	 * @param lHospSeqId Long object which contains the hospital seq id
  	 * @return HospitalDetailVO object which contains all the hospital details
  	 * @exception throws TTKException
  	 */
  	public CustomerBankDetailsVO getHospitaBanklDetail(Long lHospSeqId) throws TTKException
  	{
  		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
  		return customerBankDAO.getHospitaBanklDetail(lHospSeqId);
  	}//end of getHospitalDetail(Long lHospSeqId)
  	
  	/**
	 * This method adds or updates the account details
	 * The method also calls other methods on DAO to insert/update the account details to the database
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	{
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
		return customerBankDAO.addUpdateReviewAccount(customerBankDetailsVO);
	}//end of addUpdateAccount(AccountDetailVO accountDetailVO)
	

	
	
	public int addUpdateRevisedReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	{
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
		return customerBankDAO.addUpdateRevisedReviewAccount(customerBankDetailsVO);
	}//end of addUpdateAccount(AccountDetailVO accountDetailVO)
	
	/**
	 * This method gets the files uploaded at empanelment for a provider   
	 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public ArrayList<String> getFilesUploadedAtEmpnl(Long lngHospSeqId) throws TTKException
	{
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
		return customerBankDAO.getFilesUploadedAtEmpnl(lngHospSeqId);
	}//end of addUpdateAccount(AccountDetailVO accountDetailVO)
	
	
	
	public String getBankCode(String bankName) throws TTKException
	{
		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
		return customerBankDAO.getBankCode(bankName);
	}//end of getBankCode(String bankName)

	  public ArrayList getPartnerBankAccountReviewList() throws TTKException
      {
	   customerBankDAO=(CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
          return customerBankDAO.getPartnerBankAccountReviewList();
      }//end of getBankAccountList(ArrayList alSearchCriteria)
      
		/**
	  	 * This method returns the HospitalDetailVO, which contains all the hospital details
	  	 * @param lHospSeqId Long object which contains the hospital seq id
	  	 * @return HospitalDetailVO object which contains all the hospital details
	  	 * @exception throws TTKException
	  	 */
	  	public CustomerBankDetailsVO getPartnerBankDetail(Long lPtnrSeqId) throws TTKException
	  	{
	  		customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
	  		return customerBankDAO.getPartnerBankDetail(lPtnrSeqId);
	  	}//end of getPartnerBankDetail(Long lPtnrSeqId)
	  	
		/**
		 * This method adds or updates the account details
		 * The method also calls other methods on DAO to insert/update the account details to the database
		 * @param accountDetailVO AccountDetailVO object which contains the account details to be added/updated
		 * @return int value, greater than zero indicates successful execution of the task
		 * @exception throws TTKException
		 */
		public int addUpdatePartnerReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
		{
			customerBankDAO = (CustomerBankDetailsDAOImpl)this.getFinanceBankNewDAO("bankcity");
			return customerBankDAO.addUpdatePartnerReviewAccount(customerBankDetailsVO);
		}//end of addUpdateAccount(AccountDetailVO accountDetailVO)
		
}
