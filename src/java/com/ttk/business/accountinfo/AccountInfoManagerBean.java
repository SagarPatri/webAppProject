/**
 * @ (#) AccountInfoManagerBean.java Jul 26, 2007
 * Project 	     : TTK HealthCare Services
 * File          : AccountInfoManagerBean.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.business.accountinfo;

import java.util.ArrayList;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.accountinfo.AccountInfoDAOFactory;
import com.ttk.dao.impl.accountinfo.AccountInfoDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthDAOImpl;
//import com.ttk.dao.impl.maintenance.MaintenanceDAOImpl;
import com.ttk.dto.accountinfo.PolicyAccountInfoDetailVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.maintenance.EnrollBufferVO;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class AccountInfoManagerBean implements AccountInfoManager{

	private AccountInfoDAOFactory accountInfoDAOFactory = null;
	private AccountInfoDAOImpl accountInfoDAO = null;


	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getAccountInfoDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (accountInfoDAOFactory == null)
				accountInfoDAOFactory = new AccountInfoDAOFactory();
			if(strIdentifier!=null)
			{
				return accountInfoDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getEnrollmentDAO(String strIdentifier)

	/**
	 * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVO object which contains enrollment policy details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
		accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getPolicyList(alSearchCriteria);
	}//end of getPolicyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException
	{
		accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getMemberList(alSearchCriteria);
	}// end of getMemberList(ArrayList alSearchCriteria)

	 /**
     * This method returns the MemberDetailVO, which contains the Enrollment details which are populated from the database
     * @param alEnrollment ArrayList which contains seq id for Enrollment or Endorsement flow to get the Enrollment information
     * @return MemberDetailVO object's which contains the details of the Enrollment
     * @exception throws TTKException
     */
    public MemberDetailVO getEnrollment(ArrayList alEnrollment) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getEnrollment(alEnrollment);
	}//end of getEnrollment(ArrayList alEnrollment)

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public ArrayList getDependentList(ArrayList alMember) throws TTKException
	{
		accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getDependentList(alMember);
	}// end of getDependentList(ArrayList alMember)

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public PolicyAccountInfoDetailVO getPolicy(ArrayList alSearchCriteria) throws TTKException
	{
		accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getPolicy(alSearchCriteria);
	}// end of getMemberList(ArrayList alSearchCriteria)

	 /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getHistoryList(alSearchCriteria);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    
    public ArrayList<String[]> getPreauthHistoryList(Long memberSeqID,String authType,String preauthorclaimNumber) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getPreauthHistoryList(memberSeqID,authType,preauthorclaimNumber);
    }
    
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    /*public Document getPolicyHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getHistory(strHistoryType,lngSeqId,lngEnrollDtlSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
*/
    public Document getPreAuthHistory(Long lngSeqId) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getPreAuthHistory(lngSeqId);
    }//end of getPreAuthHistory(ArrayList alSearchCriteria)

    public Document getClaimHistory(Long lngSeqId) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getClaimHistory(lngSeqId);
    }//end of getClaimHistory(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getEndorsementList(long lngSeqId) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
        return accountInfoDAO.getEndorsementList(lngSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)

  /**
	 * This method returns the Arraylist of PolicyVo's  which contains BufferConfig  details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVo's object which contains BufferConfig details
	 * @exception throws TTKException
	 */
    //Added as per KOC 1216B CR...................
    public ArrayList getBufferEnrollmentlist(ArrayList alSearchCriteria) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getBufferEnrollmentlist(alSearchCriteria);
	}//end of getBufferEnrollmentlist(ArrayList alSearchCriteria)
    
    
    public ArrayList getBufferConfiguredList(ArrayList alSearchCriteria) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.getBufferConfiguredList(alSearchCriteria);
	}//end of getBufferConfiguredList(ArrayList alSearchCriteria)
    
    public Object[] Addbuffer(ArrayList alAddBufferParam) throws TTKException {
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
		return accountInfoDAO.Addbuffer(alAddBufferParam);
	}//end of Addbuffer(ArrayList alSearchCriteria)
    
    public Long saveEnrbuffer(EnrollBufferVO enrollBufferVO) throws TTKException{
    accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
	return accountInfoDAO.saveEnrbuffer(enrollBufferVO);
	
	   }//end of saveEnrbuffer(EnrollBufferVO enrollBufferVO)
    public ArrayList getEnrBufferDetail(ArrayList alSearchCriteria) throws TTKException{
    	accountInfoDAO = (AccountInfoDAOImpl)this.getAccountInfoDAO("accountinfo");
    	return accountInfoDAO.getEnrBufferDetail(alSearchCriteria);
    }//end of getEnrBufferDetail(ArrayList alSearchCriteria)
   //Added as per KOC 1216B IBM CR

	@Override
	public Document getPolicyHistory(String strHistoryType, Long lngSeqId,
			Long lngEnrollDtlSeqId) throws TTKException {
		// TODO Auto-generated method stub
		return null;
	}

}
