/**
 * @ (#)  AdministrationDAOFactory.java Oct 3, 2005
 * Project      : TTKPROJECT
 * File         : AdministrationDAOFactory.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 3, 2005
 *
 * @author       :  Balakrishna.E
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.dataentryadministration;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class AdministrationDAOFactory extends DAOFactory{
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("tariffplan"))
        {
            baseDAO = new TariffPlanDAOImpl();
        }//end of if (strIdentifier.equals("tariffplan"))
        else if (strIdentifier.equals("tariffitem"))
        {
            baseDAO = new TariffItemDAOImpl();
        }//end of else if (strIdentifier.equals("tariffitem"))
        /* else if (strIdentifier.equals("cardrules"))
         {
             baseDAO = new CardRuleDAOImpl();
         }//end of else if (strIdentifier.equals("cardrules"))
       else if (strIdentifier.equals("circulars"))
       {
           baseDAO = new CircularDAOImpl();
       }//end of else if (strIdentifier.equals("circulars"))
        else if (strIdentifier.equals("officecode"))
        {
            baseDAO = new OfficeDAOImpl();
        }//end of else if (strIdentifier.equals("officecode"))
        else if (strIdentifier.equals("policy"))
        {
            baseDAO = new PolicyDAOImpl();
        }//end of else if (strIdentifier.equals("policy"))
*/      /*  else if (strIdentifier.equals("product"))
        {
            baseDAO = new ProductDAOImpl();
        }//end of else if (strIdentifier.equals("product"))
        else if (strIdentifier.equals("workflow"))
        {
            baseDAO = new WorkflowDAOImpl();
        }//end of else if (strIdentifier.equals("workflow"))
        else if (strIdentifier.equals("ttkoffice"))
        {
            baseDAO = new TtkOfficeDAOImpl();
        }//end of else if (strIdentifier.equals("ttkoffice"))
        else if (strIdentifier.equals("prodpolicyrule"))
        {
            baseDAO = new RuleDAOImpl();
        }//end of else if (strIdentifier.equals("prodpolicyrule"))
        else if (strIdentifier.equals("webconfig"))
        {
            baseDAO = new WebConfigDAOImpl();
        }//end of else if (strIdentifier.equals("webconfig"))
        else if (strIdentifier.equals("prodpolicyconfig"))
        {
            baseDAO = new ProdPolicyConfigDAOImpl();
        }//end of else if (strIdentifier.equals("prodpolicyconfig"))
        else if (strIdentifier.equals("tdsconfig"))
        {
            baseDAO = new ConfigurationDAOImpl();
        }//end of else if (strIdentifier.equals("tdsconfig"))
*/        return baseDAO;
    }//end of getDAO(String strIdentifier)
}//end of class AdministrationDAOFactory
