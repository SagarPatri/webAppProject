/**
 * @ (#) TDSConfigurationManager.java July 28, 2009
 * Project       : TTK HealthCare Services
 * File          : TDSConfigurationManager.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : July 28, 2009
 * @author       : Balakrishna Erram
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.administration;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ServTaxRateVO;
import com.ttk.dto.administration.TDSCategoryRateVO;
import com.ttk.dto.administration.TDSSubCategoryVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;

@Local
public interface ConfigurationManager {
	
	/**
     * This method returns the ArrayList, which contains the TDSCategoryVOs which are populated from the database
     * @return ArrayList object's which contains the list of TDSCategoryVO 
     * @exception throws TTKException
     */
    public ArrayList<Object> getCategoryList() throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the TDSSubCategoryVOs which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return ArrayList object's which contains the list of TDSSubCategoryVO 
     * @exception throws TTKException
     */
    public ArrayList<Object> getSubCategoryList(String strTdsCategoryTypeID)throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSelectRevisionList(String strTdsCategoryTypeID)throws TTKException;
    
    /**
     * This method returns the TDSCategoryVO, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public TDSCategoryRateVO getSelCatRateList(ArrayList<Object> alRevisionList)throws TTKException ;
    
    /**
     * This method will add/update the TDS sub category rate information to database.
     * @param tdsCategoryRateVO TDSCategoryRateVO, which contains the TDS Category details
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCatRate(TDSCategoryRateVO tdsCategoryRateVO)throws TTKException;
     
    /**
	 * This method adds/updates the TDS Sub Category Details
	 * @param tdsSubCategoryVO which contains the details of TDS sub category to be updated/added
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateTdsSubCategory(TDSSubCategoryVO tdsSubCategoryVO) throws TTKException;
	
	/**
	 * This method returns the TDSSubCategoryVO which contains the details of tds sub category. 
	 * @param sTdsSubCategoryId the TDS Sub Category Id for which the details are required
	 * @return TDSSubCategoryVO Object which contains the details of TDS Sub Category.
	 * @exception throws TTKException
	 */
	public TDSSubCategoryVO getTdsSubCategoryDetails(String sTdsSubCategoryId) throws TTKException;
	
	 /**
     * This method will give the Service Tax Revision list from database.
     * @return Arraylist which Contains the details of Service Tax
     * @exception throws TTKException
     */
    public ArrayList<Object> getServRevisionList() throws TTKException;
    
    /**
     * This method will give the detailed Service Tax rate information from database.
     * @param lServTaxSeqId ServTaxSeqId, which contains the Service Tax Seq Id
     * @return ServTaxRateVO which contains all the Service Tax Information
     * @exception throws TTKException
     */
    public ServTaxRateVO getServTaxDetail(Long lServTaxSeqId)throws TTKException;
    
    /**
     * This method will add/update the Service Tax rate information to database.
     * @param servTaxRateVO ServTaxRateVO, which contains the Service Tax details
     * @return long ServTaxSeqId which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public long saveSerTaxRate(ServTaxRateVO servTaxRateVO)throws TTKException;
    
    //kocnewhosp
    public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO)throws TTKException;
    //Not required nw public WebconfigInsCompInfoVO getWebConfigHospInfo(long lngProdPolicySeqID) throws TTKException;
    
    /**
     * To Save Links - Uploads for Hospitals
     */
    public int saveWebConfigLinkInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO,ArrayList alFileAUploadList) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links 
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException;
    /**
     * This method deletes the Weblogin link details from the database
     * @param alDeleteList which contains the id's of the WebConfig links
     * @return int value, greater than zero indicates sucessful execution of the task
     * @exception throws TTKException
     */
    public int deleteWebConfigLinkInfo(ArrayList alDeleteList) throws TTKException;
    

    public WebconfigInsCompInfoVO getHospInfo() throws TTKException;
    
    
}//end of ConfigurationManager
