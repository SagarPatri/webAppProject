/** @ (#) ImageEnquiryManagerBean.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageEnquiryManagerBean.java
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: May 28, 2007
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.business.misreports;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.misreports.ImageEnquiryDAOImpl;
import com.ttk.dao.impl.misreports.MisDAOFactory;

import javax.ejb.*;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ImageEnquiryManagerBean implements ImageEnquiryManager {
	
	private MisDAOFactory misDAOFactory = null;
	private ImageEnquiryDAOImpl imageDAO = null;
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getMisDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if(misDAOFactory == null){
            	misDAOFactory = new MisDAOFactory();
            }//end of if(administrationDAOFactory == null)
            if(strIdentifier!=null)
            {
                return misDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else{
            	return null;
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end of getMisDAO(String strIdentifier)
    
    /**
     * This method returns the ArrayList, which contains the ImageEnquiryVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ImageEnquiryVO'S object's which contains the details of the Image Enquiry
     * @exception throws TTKException
     */
	public ArrayList getImageEnquiryList(ArrayList alSearchObjects) throws TTKException {
		imageDAO = (ImageEnquiryDAOImpl)this.getMisDAO("imageenquiry");
        return imageDAO.getImageEnquiryList(alSearchObjects);
	}//end of getImageEnquiryList(ArrayList alSearchObjects)

}//end of ImageEnquiryManagerBean
