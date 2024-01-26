/**
 * @ (#) SupportManagerBean.java Mar 14, 2008
 * Project 	     : TTK HealthCare Services
 * File          : SupportManagerBean.java
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

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.support.SupportDAOFactory;
import com.ttk.dao.impl.support.SupportDAOImpl;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class SupportManagerBean implements SupportManager{
	
	private SupportDAOFactory supportDAOFactory = null;
	private SupportDAOImpl supportDAO = null;
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getSupportDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (supportDAOFactory == null)
            	supportDAOFactory = new SupportDAOFactory();
            if(strIdentifier!=null)
            {
                return supportDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end of getSupportDAO(String strIdentifier)
	
    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException{
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.getIntimationList(alSearchCriteria);
    }//end of getIntimationList(ArrayList alSearchCriteria)

    /**
	 * This method saves the Intimation information
	 * @param intimationVO the object which contains the Intimation Details which has to be  saved
	 * @return long the value contains Intimation Seq ID
	 * @exception throws TTKException
	 */
	public long saveIntimationDetail(OnlineIntimationVO onlineIntimationVO) throws TTKException{
		supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
		return supportDAO.saveIntimationDetail(onlineIntimationVO);
	}//end of saveIntimationDetail(OnlineIntimationVO onlineIntimationVO)
	
	/**
     * This method returns the Arraylist of OnlineAssistanceVO's which contains Online Query details
     * @param alSearchCriteria It contains the Search Criteria
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSupportQueryList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.getSupportQueryList(alSearchCriteria);
    }//end of getSupportQueryList(ArrayList<Object> alSearchCriteria)
    
    /**
     * This method saves the Support Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSupportQueryInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException {
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.saveSupportQueryInfo(onlineAssistanceDetailVO);
    }//end of saveSupportQueryInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)

    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DhpoWebServiceVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getSmartHealthXmlList(ArrayList alSearchCriteria) throws TTKException{
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.getSmartHealthXmlList(alSearchCriteria);
    }//end of getSmartHealthXmlList(ArrayList alSearchCriteria)
    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DhpoWebServiceVO object which contains Intimation details
     * @exception throws TTKException
     */
    public DhpoWebServiceVO downLoadSmartHealthXmlFiles(Long seqID,String downloadMode,String authMode) throws TTKException{
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.downLoadSmartHealthXmlFiles(seqID,downloadMode,authMode);
    }//end of getSmartHealthXmlList(ArrayList alSearchCriteria)

    public ArrayList<DhpoWebServiceVO> bulkDownLoadSmartHealthXmlFiles(StringBuilder seqID,String downloadMode,String authMode) throws TTKException{
    	supportDAO = (SupportDAOImpl)this.getSupportDAO("support");
    	return supportDAO.bulkDownLoadSmartHealthXmlFiles(seqID,downloadMode,authMode);
    }//end of getSmartHealthXmlList(ArrayList alSearchCriteria)
   
}//end of SupportManagerBean
