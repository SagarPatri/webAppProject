/**
 * @ (#) TDSConfigurationManagerBean.java July 28, 2009
 * Project       : TTK HealthCare Services
 * File          : TDSConfigurationManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.AdministrationDAOFactory;
import com.ttk.dao.impl.administration.ConfigurationDAOImpl;
import com.ttk.dao.impl.administration.WebConfigDAOImpl;
import com.ttk.dao.impl.empanelment.LogDAOImpl;
import com.ttk.dto.administration.ServTaxRateVO;
import com.ttk.dto.administration.TDSCategoryRateVO;
import com.ttk.dto.administration.TDSSubCategoryVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)
public class ConfigurationManagerBean implements ConfigurationManager {

	private AdministrationDAOFactory administrationDAOFactory = null;
	private ConfigurationDAOImpl ConfDAOImpl = null;
	 private WebConfigDAOImpl webConfigDAO = null;
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getTDSConfigurationDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(administrationDAOFactory == null)
				administrationDAOFactory = new AdministrationDAOFactory();
			if(strIdentifier!=null)
			{
				return administrationDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end of getTDSConfigurationDAO(String strIdentifier)

	/**
     * This method returns the ArrayList, which contains the TDSCategoryVOs which are populated from the database
     * @return ArrayList object's which contains the list of TDSCategoryVO 
     * @exception throws TTKException
     */
    public ArrayList<Object> getCategoryList() throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.getCategoryList();
    }//end of getCategoryList()
    
    /**
     * This method returns the ArrayList, which contains the TDSSubCategoryVO's which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return ArrayList object's which contains the list of TDSSubCategoryVO 
     * @exception throws TTKException
     */
    public ArrayList<Object> getSubCategoryList(String strTdsCategoryTypeID)throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.getSubCategoryList(strTdsCategoryTypeID);
    }//end of getSelCatRateList(String strTdsCategoryTypeID)
    
    /**
     * This method returns the ArrayList, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSelectRevisionList(String strTdsCategoryTypeID)throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.getSelectRevisionList(strTdsCategoryTypeID);
    }//end of getSelectRevisionList(String strTdsCategoryTypeID)
    
    /**
     * This method returns the TDSCategoryVO, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public TDSCategoryRateVO getSelCatRateList(ArrayList<Object> alRevisionList)throws TTKException {
       ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.getSelCatRateList(alRevisionList);
    }//end of getSelCatRateList(ArrayList<Object> alRevisionList)
    
    /**
     * This method will add/update the TDS sub category rate information to database.
     * @param tdsCategoryRateVO TDSCategoryRateVO, which contains the TDS Category details
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCatRate(TDSCategoryRateVO tdsCategoryRateVO)throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.saveCatRate(tdsCategoryRateVO);
    }//end of getSaveCatRate(ArrayList<Object> alRevisionList)    
     
    /**
	 * This method will add/update the TDS sub category information to database.
	 * @param tdsCategoryRateVO TDSCategoryRateVO, which contains the TDS Category details
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateTdsSubCategory(TDSSubCategoryVO tdsSubCategoryVO) throws TTKException {
		ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.addUpdateTdsSubCategory(tdsSubCategoryVO);
	}//end of addUpdateTdsSubCategory(TDSSubCategoryVO tdsSubCategoryVO)
	
	/**
	 * This method returns the TDSSubCategoryVO which contains the details of tds sub category. 
	 * @param sTdsSubCategoryId the TDS Sub Category Id for which the details are required
	 * @return TDSSubCategoryVO Object which contains the details of TDS Sub Category.
	 * @exception throws TTKException
	 */
	public TDSSubCategoryVO getTdsSubCategoryDetails(String sTdsSubCategoryId) throws TTKException {
		ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
		return ConfDAOImpl.getTdsSubCategoryDetails(sTdsSubCategoryId);
	}// end of getTdsSubCategoryDetails(Long lngOfficeSequenceID)	
	
	 /**
     * This method will give the Service Tax Revision list from database.
     * @return Arraylist which Contains the details of Service Tax
     * @exception throws TTKException
     */
    public ArrayList<Object> getServRevisionList() throws TTKException {
	    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
			return ConfDAOImpl.getServRevisionList();
	}//end of getServRevisionList()
    
    /**
     * This method will give the detailed Service Tax rate information from database.
     * @param lServTaxSeqId ServTaxSeqId, which contains the Service Tax Seq Id
     * @return ServTaxRateVO which contains all the Service Tax Information
     * @exception throws TTKException
     */
    public ServTaxRateVO getServTaxDetail(Long lServTaxSeqId)throws TTKException{
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.getServTaxDetail(lServTaxSeqId);
    }//end of getServTaxDetail(Long lServTaxSeqId)
    
    /**
     * This method will add/update the Service Tax rate information to database.
     * @param servTaxRateVO ServTaxRateVO, which contains the Service Tax details
     * @return long ServTaxSeqId which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public long saveSerTaxRate(ServTaxRateVO servTaxRateVO)throws TTKException{
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.saveSerTaxRate(servTaxRateVO);
    }//end of saveSerTaxRate(ServTaxRateVO servTaxRateVO)
    
    
    /**
    kocnewhosp1
     */
    public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.saveWebConfigInsInfo(webconfigInsCompInfoVO);
    }//end of saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfo)
    
    public int saveWebConfigLinkInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO,ArrayList alFileAUploadList) throws TTKException
    {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.saveWebConfigLinkInfo(webconfigInsCompInfoVO,alFileAUploadList);
    }//end of saveWebConfigLinkInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException
    
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links 
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException{
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.getWebConfigLinkList(alSearchCriteria);
    }//end of getWebConfigLinkList(ArrayList alSearchCriteria)
    
    /**
     * This method deletes the Weblogin link details from the database
     * @param alDeleteList which contains the id's of the WebConfig links
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteWebConfigLinkInfo(ArrayList alDeleteList) throws TTKException
    {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.deleteWebConfigLinkInfo(alDeleteList);
    }//end of deleteWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException
    
    
    public WebconfigInsCompInfoVO getHospInfo() throws TTKException {
    	ConfDAOImpl = (ConfigurationDAOImpl)this.getTDSConfigurationDAO("tdsconfig");
    	return ConfDAOImpl.getHospInfo();
    }//end of getHospInfo(WebconfigInsCompInfoVO webconfigInsCompInfo)
	  
	  
}//End of ConfigurationManagerBean
