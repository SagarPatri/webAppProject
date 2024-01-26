/**
 * @ (#) CourierManagerBean.java May 26, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CourierManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 26, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.enrollment;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.enrollment.CourierDAOImpl;
import com.ttk.dao.impl.enrollment.EnrollmentDAOFactory;
import com.ttk.dto.enrollment.CourierDetailVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class CourierManagerBean implements CourierManager{

	private EnrollmentDAOFactory enrollmentDAOFactory = null;
	private CourierDAOImpl courierDAO = null;

	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getEnrollmentDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (enrollmentDAOFactory == null)
                enrollmentDAOFactory = new EnrollmentDAOFactory();
            if(strIdentifier!=null)
            {
                return enrollmentDAOFactory.getDAO(strIdentifier);
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
     * This method returns the ArrayList, which contains the CourierVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CourierVO'S object's which contains the details of the Courier
     * @exception throws TTKException
     */
	public ArrayList getCourierList(ArrayList alSearchCriteria) throws TTKException {
		courierDAO = (CourierDAOImpl)this.getEnrollmentDAO("courier");
		return courierDAO.getCourierList(alSearchCriteria);
	}//end of getCourierList(ArrayList alSearchCriteria)

	/**
	 * This method returns the CourierDetailVO, which contains all the Courier details
	 * @param lngCourierSeqID long value which contains seq id for fetching Courier Details
	 * @param lngUserSeqID long value logged-in User Seq ID
	 * @return CourierDetailVO object which contains all the Courier details
	 * @exception throws TTKException
	 */
	public CourierDetailVO getCourierDetail(long lngCourierSeqID,long lngUserSeqID) throws TTKException{
		courierDAO = (CourierDAOImpl)this.getEnrollmentDAO("courier");
		return courierDAO.getCourierDetail(lngCourierSeqID,lngUserSeqID);
     }//end of getCourierDetail(long lngCourierSeqID,long lngUserSeqID)

	/**
	 * This method saves the Courier information
	 * @param courierDetailVO the object which contains Courier details which has to be  saved
	 * @return long value which contains Courier Seq ID
	 * @exception throws TTKException
	 */
	public long saveCourierDetail(CourierDetailVO courierDetailVO) throws TTKException {
		courierDAO = (CourierDAOImpl)this.getEnrollmentDAO("courier");
		return courierDAO.saveCourierDetail(courierDetailVO);
	 }//end of saveCourierDetail(CourierDetailVO courierDetailVO)

	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCourierDetail(ArrayList alDeleteList) throws TTKException {
		courierDAO = (CourierDAOImpl)this.getEnrollmentDAO("courier");
		return courierDAO.deleteCourierDetail(alDeleteList);
	}//end of deleteCourierDetail(ArrayList alDeleteList)

	/**
     * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
     * @exception throws TTKException
     */
    public ArrayList getInwardCourierList(ArrayList alSearchCriteria) throws TTKException {
    	courierDAO = (CourierDAOImpl)this.getEnrollmentDAO("courier");
    	return courierDAO.getInwardCourierList(alSearchCriteria);
    }//end of getInwardCourierList(ArrayList alSearchCriteria)

}//end of CourierManagerBean
