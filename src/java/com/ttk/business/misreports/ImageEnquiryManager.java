/** @ (#) ImageEnquiryManager.java May 28, 2007
 * Project     : TTK Healthcare Services
 * File        : ImageEnquiryManager.java
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

import javax.ejb.*;

@Local

public interface ImageEnquiryManager {
	 
	/**
     * This method returns the ArrayList, which contains the ImageEnquiryVO'S which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ImageEnquiryVO'S object's which contains the details of the Image Enquiry
     * @exception throws TTKException
     */
	public ArrayList getImageEnquiryList(ArrayList alSearchObjects)throws TTKException;

}//end of ImageEnquiryManager
