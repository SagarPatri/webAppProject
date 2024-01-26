/**
 * @ (#) EmpanelmentDAOFactory.java Sep 21, 2005
 * Project      :
 * File         : EmpanelmentDAOFactory.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 21, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class EmpanelmentDAOFactory extends DAOFactory {
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;

        if (strIdentifier.equals("hospital"))
        {
            baseDAO = new HospitalDAOImpl();
        }//end of if (strIdentifier.equals("hospital"))
        else if (strIdentifier.equals("feedback"))
        {
            baseDAO = new FeedbackDetailDAOImpl();
        }//end of else if (strIdentifier.equals("feedback"))
        else if (strIdentifier.equals("account"))
        {
            baseDAO = new AccountDAOImpl();
        }//end of else if (strIdentifier.equals("account"))
        else if (strIdentifier.equals("validation"))
        {
            baseDAO = new ValidationDAOImpl();
        }//end of else if (strIdentifier.equals("validation"))
        else if (strIdentifier.equals("status"))
        {
            baseDAO = new StatusDAOImpl();
        }//end of else if (strIdentifier.equals("status"))
        else if (strIdentifier.equals("contacts"))
        {
            baseDAO = new ContactDetailDAOImpl();
        }//end of else if (strIdentifier.equals("contacts"))
        else if (strIdentifier.equals("log"))
        {
            baseDAO = new LogDAOImpl();
        }//end of else if (strIdentifier.equals("log"))
        else if (strIdentifier.equals("grading"))
        {
            baseDAO = new GradingDAOImpl();
        }//end of else if (strIdentifier.equals("grading"))
        else if (strIdentifier.equals("insurance"))
        {
            baseDAO = new InsuranceDAOImpl();
        }//end of else if (strIdentifier.equals("insurance"))
        else if (strIdentifier.equals("broker"))
        {
            baseDAO = new BrokerDAOImpl();
        }//end of else if (strIdentifier.equals("broker"))
        else if (strIdentifier.equals("insuranceproduct"))
        {
            baseDAO = new InsuranceProductDAOImpl();
        }//end of else if (strIdentifier.equals("insuranceproduct"))
        else if (strIdentifier.equals("insurancefeedback"))
        {
            baseDAO = new InsuranceFeedbackDAOImpl();
        }//end of else if (strIdentifier.equals("insurancefeedback"))
        else if (strIdentifier.equals("mou"))
        {
            baseDAO = new MOUDAOImpl();
        }//end fof else if (strIdentifier.equals("mou"))
        else if (strIdentifier.equals("groupregistration"))
        {
        	baseDAO = new GroupRegistrationDAOImpl();
        }//end of else if (strIdentifier.equals("groupregistration"))
        else if (strIdentifier.equals("partner"))
        {
        	baseDAO = new PartnerDAOImpl();
        }//end of else if (strIdentifier.equals("groupregistration"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of class EmpanelmentDAOFactory
