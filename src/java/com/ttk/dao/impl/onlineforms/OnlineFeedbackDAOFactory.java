/**
 * @ (#) OnlineFeedbackDAOFactory.java Apr 24, 2012
 * Project 	     : TTK HealthCare Services
 * File          : OnlineFeedbackDAOFactory.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Apr 24, 2012
 *
 * @author       :  Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.onlineforms;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
/**
 * This class is added For CR KOC1168 Feedback Form 
 * 
 */
public class OnlineFeedbackDAOFactory extends DAOFactory {

	@Override
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("feedback"))
		{
			baseDAO = new OnlineFeedbackDAOImpl();
		}//end of if (strIdentifier.equals("feedback"))\
		return baseDAO;
	}
}// End of OnlineFeedbackDAOFactory