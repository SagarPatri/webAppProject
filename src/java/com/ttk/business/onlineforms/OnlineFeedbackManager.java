package com.ttk.business.onlineforms;

/**
 * @ (#) OnlineFeedbackManager.java Apr 24, 2012
 * Project 	     : TTK HealthCare Services
 * File          : OnlineFeedbackManager.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Apr 24, 2012
 *
 * @author       :  Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

import java.util.ArrayList;

import javax.ejb.*;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.ClaimFormVO;

/**
 * 
 * This Interface is added for CR KOC1168 Feedback Form
 */
@Local

public interface OnlineFeedbackManager {
		
    /**
     * This method returns the ArrayList of Feedback object's which contains the feedback Details
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public ArrayList getQuestionCashlessList(String groupid,String claimType) throws TTKException;
	
	 /**
     * This method returns the ArrayList of Feedback object's which contains the feedback Details
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public ArrayList getQuestionMReimbursementList(String groupid,String claimType) throws TTKException;
	
	
	  /**
     * This method returns the ArrayList of Feedback object's which contains the feedback Details
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public long saveCashlessFeedbackDetails(ArrayList fbdetails) throws TTKException;
	
	 /**
     * This method returns the ArrayList of Feedback object's which contains the feedback Details
     * @exception throws TTKException
     * @return ArrayList of Feedback object's which contains the feedback Details
     */
	public long saveMReimbursementFeedbackDetails(ArrayList feedback) throws TTKException;
	
	
	 /**
	 * This method returns the Arraylist of BajajAllianzVO's  which contains the details of Claim/Pre-Auth details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BajajAllianzVO object which contains the details of Claim
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria,String strSwitchType) throws TTKException;
	
	/**
	 * This method returns the Claim/PreAuth xml Data of BajajAllianzVO's a  which contains the details of Claim/Pre-Auth details
	 * @param  lngSeqID object which contains the search criteria
	 * @param  strSwitchType object which contains the search criteria
	 * @exception throws TTKException
	 */
	public ClaimFormVO getClaimXmlData( Long lngSeqID,String strSwitchType) throws TTKException;
	
	/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strConcatenatedSeqID 
	 * @param  strApproveRejectStatus 
	  * @param  strRemarks 
	 * @param  strSwitchType 
	 * @exception throws TTKException
	 */
	public int saveInsurerApproveRejectStatus(String strConcatenatedSeqID,String strApproveRejectStatus,String strRemarks,String strSwitchType,Long lngUserID) throws TTKException;
	
	/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strInsCode 
	 * @param  strMsgId 
	 * @param  strFilename
	 * @exception throws TTKException
	 */
	public int sendMailIntimation(String strInsCode,String strMsgId,String strFilename)throws TTKException;
	
	
}//end of OnlineFeedbackManager

