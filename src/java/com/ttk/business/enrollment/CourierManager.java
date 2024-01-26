/**
 * @ (#) CourierManager.java May 26, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CourierManager.java
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

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.CourierDetailVO;
@Local

public interface CourierManager {

	/**
     * This method returns the ArrayList, which contains the CourierVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CourierVO'S object's which contains the details of the Courier
     * @exception throws TTKException
     */
	public ArrayList getCourierList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the CourierDetailVO, which contains all the Courier details
	 * @param lngCourierSeqID long value which contains seq id for fetching Courier Details
	 * @param lngUserSeqID long value logged-in User Seq ID
	 * @return CourierDetailVO object which contains all the Courier details
	 * @exception throws TTKException 
	 */
	public CourierDetailVO getCourierDetail(long lngCourierSeqID,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method saves the Courier information
	 * @param courierDetailVO the object which contains Courier details which has to be  saved
	 * @return long value which contains Courier Seq ID
	 * @exception throws TTKException
	 */
	public long saveCourierDetail(CourierDetailVO courierDetailVO) throws TTKException;
	
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCourierDetail(ArrayList alDeleteList) throws TTKException;
	
	/**
     * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
     * @exception throws TTKException
     */
    public ArrayList getInwardCourierList(ArrayList alSearchCriteria) throws TTKException;
	
}//end of CourierManager
