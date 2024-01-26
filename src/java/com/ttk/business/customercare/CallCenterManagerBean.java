/**
 * @ (#) CallCenterManagerBean.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CallCenterManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.customercare;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.customercare.CallCenterDAOImpl;
import com.ttk.dao.impl.customercare.CustomerCareDAOFactory;
import com.ttk.dto.customercare.CallCenterAssignmentVO;
import com.ttk.dto.customercare.CallCenterDetailVO;
import com.ttk.dto.customercare.EnquiryDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class CallCenterManagerBean implements CallCenterManager
{
    private CustomerCareDAOFactory customerCareDAOFactory = null;
    private CallCenterDAOImpl callCenterDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getCallCenterDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (customerCareDAOFactory == null)
                customerCareDAOFactory = new CustomerCareDAOFactory();
            if(strIdentifier!=null)
            {
                return customerCareDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getCallCenterDAO(String strIdentifier)

    /**
     * This method returns the Arraylist of CallCenterVO's  which contains Customer Care Call Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CallCenterVO object which contains Customer Care Call Details
     * @exception throws TTKException
     */
    public ArrayList getCallList(ArrayList alSearchCriteria) throws TTKException {
        callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
        return callCenterDAO.getCallList(alSearchCriteria);
    }//end of getCallList(ArrayList alSearchCriteria)

    /**
     * This method returns the CallCenterDetailVO, which contains all the Customer Care Call Details
     * @param lngLogSeqID long value contains SeqID to fetch the Customer Care Call Details
     * @param lngUserSeqID long value contains logged-in user seq id
     * @return CallCenterDetailVO object which contains all the Customer Care Call Details
     * @exception throws TTKException
     */
    public CallCenterDetailVO getCallDetail(long lngLogSeqID,long lngUserSeqID) throws TTKException {
         callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
         return callCenterDAO.getCallDetail(lngLogSeqID,lngUserSeqID);
      }//end of getCallDetail(long lngLogSeqID,long lngUserSeqID)

    /**
	 * This method returns the HashMap,which contains the Reasons related to Callertype and Category
	 * @return HashMap contains Reasons related to Callertype and Category
	 * @exception throws TTKException
	 */
    public HashMap getCategoryInfo() throws TTKException {
    	callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
    	return callCenterDAO.getCategoryInfo();
    }//end of getCategoryInfo()

    /**
	 * This method returns the HashMap,which contains the Reasons related to CategoryType
	 * @return HashMap contains Reasons related to CategoryType
	 * @exception throws TTKException
	 */
    public HashMap getCategoryReasonInfo() throws TTKException {
    	callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
    	return callCenterDAO.getCategoryReasonInfo();
    }//end of getCategoryReasonInfo()

    /**
	 * This method returns the HashMap,which contains the ReasonInfo,SubReasonInfo and AnswerInfo
	 * @return HashMap containing ReasonInfo,SubReasonInfo and AnswerInfo
	 * @exception throws TTKException
	 */
    public HashMap getReasonInfo() throws TTKException {
    	callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
    	return callCenterDAO.getReasonInfo();
    }//end of getReasonInfo()

    /**
     * This method saves the Customer Care Call information
     * @param callCenterDetailVO CallCenterDetailVO contains Customer Care Call information
     * @return long value which contains Call Log Seq ID
     * @exception throws TTKException
     */
    public long saveCallDetail(CallCenterDetailVO callCenterDetailVO) throws TTKException {
        callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
        return callCenterDAO.saveCallDetail(callCenterDetailVO);
    }//end of saveCallDetail(CallCenterDetailVO callCenterDetailVO)

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCall(ArrayList alDeleteList) throws TTKException {
		callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
		return callCenterDAO.deleteCall(alDeleteList);
	}//end of deleteCall(ArrayList alDeleteList)

	/**
     * This method assigns the CallCenter information to the corresponding Department
     * @param callcenterAssignmentVO CallCenterAssignmentVO which contains CallCenter information to assign the corresponding Department
     * @return long value which contains Clarify Seq ID
     * @exception throws TTKException
     */
    public long assignDepartment(CallCenterAssignmentVO callcenterAssignmentVO) throws TTKException {
    	callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
    	return callCenterDAO.assignDepartment(callcenterAssignmentVO);
    }//end of assignDepartment(CallCenterAssignmentVO callcenterAssignmentVO)

    /**
     * This method returns the CallCenterAssignmentVO which contains Assignment details
     * @param lngClarifySeqID long value contains Clarify Seq ID
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return CallCenterAssignmentVO object which contains Assignment details
     * @exception throws TTKException
     */
    public CallCenterAssignmentVO getAssignTo(long lngClarifySeqID,long lngUserSeqID) throws TTKException {
    	callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
    	return callCenterDAO.getAssignTo(lngClarifySeqID,lngUserSeqID);
    }//end of getAssignTo(long lngClarifySeqID,long lngUserSeqID)

    /**
     * This method returns EnquiryDetailVO which contains info of Hospital,Insurance or TTK Office
     * details enquired by the User.
     * @param strEnquiryType String Flag to get particular Details
     * @param lngSeqID Long Seq Id of the paricular Detail
     * @return EnquiryDetailVO object
     * @throws TTKException
     */
    public EnquiryDetailVO  getEnquiryDetail(String strEnquiryType,Long lngSeqID) throws TTKException{
        callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
        return callCenterDAO.getEnquiryDetail(strEnquiryType,lngSeqID);
    }//end of getEnquiryDetail(String strEnquiryType,Long lngSeqID)

    /**
     * This method returns the Arraylist of OfficeVO's  which contains TTK office detail
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OfficeVO object which contains TTK office Details
     * @exception throws TTKException
     */
    public ArrayList getTTKOfficeList(ArrayList alSearchCriteria) throws TTKException
    {
        callCenterDAO = (CallCenterDAOImpl)this.getCallCenterDAO("callcenter");
        return callCenterDAO.getTTKOfficeList(alSearchCriteria);
    }//end of getTTKOfficeList(ArrayList alSearchCriteria)
}//end of CallCenterManagerBean