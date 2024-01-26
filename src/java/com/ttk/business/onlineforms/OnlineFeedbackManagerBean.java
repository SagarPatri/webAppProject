package com.ttk.business.onlineforms;
/**
 * @ (#) OnlineFeedbackManagerBean.java Apr 24, 2012
 * Project 	     : TTK HealthCare Services
 * File          : OnlineFeedbackManagerBean.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  :  Apr 24, 2012
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.onlineforms.OnlineFeedbackDAOFactory;
import com.ttk.dao.impl.onlineforms.OnlineFeedbackDAOImpl;
import com.ttk.dto.onlineforms.ClaimFormVO;
/**
 * 
 * This Interface is added for CR KOC1168 Feedback Form
 */
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class OnlineFeedbackManagerBean implements OnlineFeedbackManager{
	private OnlineFeedbackDAOFactory onlineFeedbackDAOFactory = null;
	private OnlineFeedbackDAOImpl onlineFeedbackDAO = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getOnlineFeedbackFormDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (onlineFeedbackDAOFactory == null)
				onlineFeedbackDAOFactory = new OnlineFeedbackDAOFactory();
			if(strIdentifier!=null)
			{
				return onlineFeedbackDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getOnlineFeedbackFormDAO(String strIdentifier)
	
	/**
     * This method returns the ArrayList
     * @param groupid String object identifier for the required data access object
     * @param claimType String object identifier for the required data access object
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public ArrayList getQuestionCashlessList(String groupid,String claimType) throws TTKException {
		onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
        return onlineFeedbackDAO.getQuestionCashlessList(groupid,claimType);
	}//end of getQuestionCashlessList(String groupid,String claimType)
	
	/**
     * This method returns the ArrayList
     * @param groupid String object identifier for the required data access object
     * @param claimType String object identifier for the required data access object
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public ArrayList getQuestionMReimbursementList(String groupid,String claimType) throws TTKException {
		onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
        return onlineFeedbackDAO.getQuestionMReimbursementList(groupid,claimType);
	}//end of getQuestionMReimbursementList(String groupid,String claimType)


	  /**
	    * This method saves the Member information
	    * @param fbdetails the ArrayList object which contains the Feedback Details which has to be  saved
	    * @return long the value which returns zero for successful execution of the task
	    * @exception throws TTKException
	    */
	   public long saveCashlessFeedbackDetails(ArrayList fbdetails) throws TTKException
		{
		   onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
			return onlineFeedbackDAO.saveCashlessFeedbackDetails(fbdetails);
		}// end of saveCashlessFeedbackDetails(ArrayList fbdetails)
	   
	   /**
	    * This method saves the Member information
	    * @param fbdetails the ArrayList object which contains the Feedback Details which has to be  saved
	    * @return long the value which returns zero for successful execution of the task
	    * @exception throws TTKException
	    */
	   public long saveMReimbursementFeedbackDetails(ArrayList fbdetails) throws TTKException
		{
		   onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
			return onlineFeedbackDAO.saveMReimbursementFeedbackDetails(fbdetails);
		}// end of saveMReimbursementFeedbackDetails(ArrayList fbdetails)
		
		 /**
	 * This method returns the Arraylist of BajajAllianzVO's  which contains the details of Claim/Pre-Auth details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BajajAllianzVO object which contains the details of Claim
	 * @exception throws TTKException
	 */	
		public ArrayList getClaimList(ArrayList alSearchCriteria,String strSwitchType) throws TTKException{
			onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
	        return onlineFeedbackDAO.getClaimList(alSearchCriteria,strSwitchType); 		
		}//end of getClaimList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the Claim/PreAuth xml Data of BajajAllianzVO's a  which contains the details of Claim/Pre-Auth details
	 * @param  lngSeqID object which contains the search criteria
	 * @param  strSwitchType object which contains the search criteria
	 * @exception throws TTKException
	 */
		public ClaimFormVO getClaimXmlData(Long lngSeqID,String strSwitchType) throws TTKException{
			onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
	        return onlineFeedbackDAO.getClaimXmlData(lngSeqID,strSwitchType); 		
		}//end of getClaimXmlData(ArrayList alSearchCriteria)
	
	/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strConcatenatedSeqID 
	 * @param  strApproveRejectStatus 
	  * @param  strRemarks 
	 * @param  strSwitchType 
	 * @exception throws TTKException
	 */
		public int saveInsurerApproveRejectStatus(String strConcatenatedSeqID,String strApproveRejectStatus,String strRemarks,String strSwitchType,Long lngUserID) throws TTKException
		{
			onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
	        return onlineFeedbackDAO.saveInsurerApproveRejectStatus(strConcatenatedSeqID,strApproveRejectStatus,strRemarks,strSwitchType,lngUserID);		
		}//end of saveInsurerApproveRejectStatus(ArrayList alSearchCriteria)
	
	/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strInsCode 
	 * @param  strMsgId 
	 * @param  strFilename
	 * @exception throws TTKException
	 */
		public int sendMailIntimation(String strInsCode,String strMsgId,String strFilename)throws TTKException{
		onlineFeedbackDAO = (OnlineFeedbackDAOImpl)this.getOnlineFeedbackFormDAO("feedback");
        return onlineFeedbackDAO.sendMailIntimation(strInsCode,strMsgId,strFilename);		
	}//end of sendMailIntimation(ArrayList alSearchCriteria)
}//end of OnlineFeedbackManagerBean
