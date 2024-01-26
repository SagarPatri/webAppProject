/**
 * @ (#) SupportManager.java Mar 14, 2008
 * Project 	     : TTK HealthCare Services
 * File          : SupportManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 14, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.business.support;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;

@Local

public interface SupportManager {
	
	/**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException;

    /**
	 * This method saves the Intimation information
	 * @param intimationVO the object which contains the Intimation Details which has to be  saved
	 * @return long the value contains Intimation Seq ID
	 * @exception throws TTKException
	 */
	public long saveIntimationDetail(OnlineIntimationVO onlineIntimationVO) throws TTKException;
	
	/**
     * This method returns the Arraylist of OnlineAssistanceVO's which contains Online Query details
     * @param alSearchCriteria It contains the Search Criteria
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSupportQueryList(ArrayList<Object> alSearchCriteria) throws TTKException;
    
    /**
     * This method saves the Support Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSupportQueryInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException;
	

	/**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DhpoWebServiceVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getSmartHealthXmlList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DhpoWebServiceVO object which contains Intimation details
     * @exception throws TTKException
     */
    public DhpoWebServiceVO downLoadSmartHealthXmlFiles(Long seqID,String downloadMode,String authMode) throws TTKException;
    
    public ArrayList<DhpoWebServiceVO> bulkDownLoadSmartHealthXmlFiles(StringBuilder seqID,String downloadMode,String authMode) throws TTKException;
}//end of SupportManager
