/**
 *  @ (#) CallCenterManager.java August 21, 2006
 *   Project       : TTK HealthCare Services
 *   File          : CallCenterManager.java
 *   Author        : RamaKrishna K M
 *   Company       : Span Systems Corporation
 *   Date Created  : August 21, 2006
 *
 *   @author       :  RamaKrishna K M
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.business.customercare;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.customercare.CallCenterAssignmentVO;
import com.ttk.dto.customercare.CallCenterDetailVO;
import com.ttk.dto.customercare.EnquiryDetailVO;

@Local

public interface CallCenterManager
{
	/**
     * This method returns the Arraylist of CallCenterVO's  which contains Customer Care Call Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CallCenterVO object which contains Customer Care Call Details
     * @exception throws TTKException
     */
	public ArrayList getCallList(ArrayList alSearchCriteria) throws TTKException;

	/**
     * This method returns the CallCenterDetailVO, which contains all the Customer Care Call Details
     * @param lngLogSeqID long value contains SeqID to fetch the Customer Care Call Details
     * @param lngUserSeqID long value contains logged-in user seq id
     * @return CallCenterDetailVO object which contains all the Customer Care Call Details
     * @exception throws TTKException
     */
    public CallCenterDetailVO getCallDetail(long lngLogSeqID,long lngUserSeqID) throws TTKException;
    
    /**
	 * This method returns the HashMap,which contains the Reasons related to Callertype and Category
	 * @return HashMap contains Reasons related to Callertype and Category
	 * @exception throws TTKException
	 */
    public HashMap getCategoryInfo() throws TTKException;
    
    /**
	 * This method returns the HashMap,which contains the Reasons related to CategoryType
	 * @return HashMap contains Reasons related to CategoryType
	 * @exception throws TTKException
	 */
    public HashMap getCategoryReasonInfo() throws TTKException;

    /**
	 * This method returns the HashMap,which contains the ReasonInfo,SubReasonInfo and AnswerInfo
	 * @return HashMap containing ReasonInfo,SubReasonInfo and AnswerInfo
	 * @exception throws TTKException
	 */
    public HashMap getReasonInfo() throws TTKException;

    /**
     * This method saves the Customer Care Call information
     * @param callCenterDetailVO CallCenterDetailVO contains Customer Care Call information
     * @return long value which contains Call Log Seq ID
     * @exception throws TTKException
     */
    public long saveCallDetail(CallCenterDetailVO callCenterDetailVO) throws TTKException;

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of Call Center details, which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCall(ArrayList alDeleteList) throws TTKException;

	/**
     * This method assigns the CallCenter information to the corresponding Department
     * @param callcenterAssignmentVO CallCenterAssignmentVO which contains CallCenter information to assign the corresponding Department
     * @return long value which contains Clarify Seq ID
     * @exception throws TTKException
     */
    public long assignDepartment(CallCenterAssignmentVO callcenterAssignmentVO) throws TTKException;

    /**
     * This method returns the CallCenterAssignmentVO which contains Assignment details
     * @param lngClarifySeqID long value contains Clarify Seq ID
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return CallCenterAssignmentVO object which contains Assignment details
     * @exception throws TTKException
     */
    public CallCenterAssignmentVO getAssignTo(long lngClarifySeqID,long lngUserSeqID) throws TTKException;

    /**
     * This method returns EnquiryDetailVO which contains info of Hospital,Insurance or TTK Office
     * details enquired by the User.
     * @param strEnquiryType String Flag to get particular Details
     * @param lngSeqID Long Seq Id of the paricular Detail
     * @return EnquiryDetailVO object
     * @throws TTKException
     */
    public EnquiryDetailVO  getEnquiryDetail(String strEnquiryType,Long lngSeqID) throws TTKException;

    /**
     * This method returns the Arraylist of OfficeVO's  which contains TTK office detail
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OfficeVO object which contains TTK office Details
     * @exception throws TTKException
     */
    public ArrayList getTTKOfficeList(ArrayList alSearchCriteria) throws TTKException;
}//end of CallCenterManager