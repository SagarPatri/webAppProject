/**
 * @ (#) ProductPolicyManagerBean.java Nov 29, 2005
 * Project      : TTK HealthCare Services
 * File         : ProductPolicyManagerBean
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 29, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

 
package com.ttk.business.administration;

import java.util.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.AdministrationDAOFactory;
import com.ttk.dao.impl.administration.CardRuleDAOImpl;
import com.ttk.dao.impl.administration.CircularDAOImpl;
import com.ttk.dao.impl.administration.OfficeDAOImpl;
import com.ttk.dao.impl.administration.PolicyDAOImpl;
import com.ttk.dao.impl.administration.ProductDAOImpl;
import com.ttk.dao.impl.administration.WebConfigDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthDAOImpl;
import com.ttk.dto.administration.BufferDetailVO;
import com.ttk.dto.administration.CardRuleVO;
import com.ttk.dto.administration.CircularVO;
import com.ttk.dto.administration.ConfigCopayVO;
import com.ttk.dto.administration.CustConfigMsgVO;
import com.ttk.dto.administration.DomConfigVO;
import com.ttk.dto.administration.InsuranceApproveVO;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.administration.ProdPolicyLimitVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.ProviderDetails;
import com.ttk.dto.administration.ProviderRuleVO;
import com.ttk.dto.administration.ShortfallDaysConfigVO;
import com.ttk.dto.administration.SumInsuredRestrictionVO;//added as per koc 1140 and 1142 (1165)
import com.ttk.dto.administration.WebConfigInfoVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.EmirateCopayDetails;
import com.ttk.xml.administration.GeoLocationCopayDetails;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)
public class ProductPolicyManagerBean implements ProductPolicyManager{

    private AdministrationDAOFactory administrationDAOFactory = null;
    private ProductDAOImpl productDAO = null;
    //private HonourDAOImpl honourDAO = null;
    private PolicyDAOImpl policyDAO = null;
    private CardRuleDAOImpl cardRuleDAO = null;
    private CircularDAOImpl circularDAO = null;
    private OfficeDAOImpl officeDAO = null;
    private WebConfigDAOImpl webConfigDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getAdministrationDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if(administrationDAOFactory == null){
            	administrationDAOFactory = new AdministrationDAOFactory();
            }//end of if(administrationDAOFactory == null)
            if(strIdentifier!=null)
            {
                return administrationDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else{
            	return null;
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end of getAdministrationDAO(String strIdentifier) 
   

	
  
	 /**
   * This method returns the ArrayList, which contains the ShortfallDaysConfigVO which are populated from the database
   * @param lngProdPolicySeqID contains the ProdPolicySeqID
   * @return ArrayList of ShortfallDaysConfigVO object's which contains the Shortfall Days Config Details
   * @exception throws TTKException
   */
	
	
  
  
    
    /**
     * This method returns the ArrayList, which contains the ProductVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ProductVO'S object's which contains the details of the insurance products
     * @exception throws TTKException
     */
    public ArrayList getProductList(ArrayList alSearchObjects) throws TTKException {
        productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
        return productDAO.getProductList(alSearchObjects);
    }// End of getProductList(ArrayList alSearchObjects)

    /**
     * This method returns the ArrayList, which contains the RuleSynchronizationVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RuleSynchronizationVO'S object's which contains the details of the Policies to Synchronize the Rules
     * @exception throws TTKException
     */
    public ArrayList getSynchPolicyList(ArrayList alSearchObjects) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.getSynchPolicyList(alSearchObjects);
    }//end of getSynchPolicyList(ArrayList alSearchObjects)

    /**
     * This method synchronizes the rules of Products to Policies
     * @param strSeqID contains concatenated ProdpolicySeqID's of Policies
     * @param lngProdPolicySeqID contains productpolicySeqID of Product
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
  //denial process
    public int synchronizeRule(String[] strSeqID,long lngProdPolicySeqID,String strDenialsyn) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.synchronizeRule(strSeqID,lngProdPolicySeqID,strDenialsyn);
    }//end of synchronizeRule(String strSeqID,long lngProdPolicySeqID)
  //denial process
    
	
    /**
     * This method adds/updates the ProductVO which contains insurance products details
     * @param productVO the details of insurance product which has to be added or updated
     * @return Object[], the values,of  Product Policy Sequence Id and InsAbberevation Code
     * @exception throws TTKException
     */
    public Object[] addUpdateProduct(ProductVO productVO) throws TTKException {
        productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
        return productDAO.addUpdateProduct(productVO);
    }// End of addUpdateProduct(ProductVO productVO)

    /**
     * This method deletes concerned InsuranceProduct details from the database
     * @param alProductList which contains the id's of the Insurance Product's
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteProduct(ArrayList alProductList) throws TTKException {
        productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
        return productDAO.deleteProduct(alProductList);
    }// End of deleteProduct(ArrayList alProductList)

    /**
     * This method returns the ProductVO, which contains all the product details
     * @param lngProdPolicySeqID the productpolicySequenceID for which the product Details has to be fetched
     * @return ProductVO object which contains all the Product details
     * @exception throws TTKException
     */
    public ProductVO getProductDetails(long lngProdPolicySeqID) throws TTKException{
        productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
        return productDAO.getProductDetails(lngProdPolicySeqID);
    }// End of getProductDetails(long lngProdPolicySeqID)

    /**
     * This method returns the Arraylist of PolicyVO's  which contains the details of Administration policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains the details of Administration policy details
     * @exception throws TTKException
     */
    public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
        return policyDAO.getPolicyList(alSearchCriteria);
    }//End of getPolicyList(ArrayList alSearchCriteria)

    /**
     * This method returns the PolicyDetailVO, which contains all the policy details
     * @param lngProdPolicySeqID It contains the Product Policy Seq ID
     * @param lngUserSeqID contains which user has logged in
     * @return PolicyDetailVO object which contains all the policy details
     * @exception throws TTKException
     */
    public PolicyDetailVO getPolicyDetail(Long lngProdPolicySeqID,Long lngUserSeqID) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
        return policyDAO.getPolicyDetail(lngProdPolicySeqID,lngUserSeqID);
    }// End of getPolicyDetail(Long lngProdPolicySeqID,Long lngUserSeqID)

    /**
     * This method returns the ArrayList, which contains Groups corresponding to TTK Branch
     * @param lngOfficeSeqID It contains the TTK Branch
     * @return ArrayList object which contains Groups corresponding to TTK Branch
     * @exception throws TTKException
     */
    public ArrayList getGroup(long lngOfficeSeqID) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
        return policyDAO.getGroup(lngOfficeSeqID);
    }//end of getGroup(long lngOfficeSeqID)

    /**
     * This method saves the Enrollment Policy information
     * @param policyDetailVO the object which contains the Policy Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    //kocb
    public ArrayList getBrokerGroup(long lngOfficeSeqID) throws TTKException
    {
        policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
        return policyDAO.getBrokerGroup(lngOfficeSeqID);
    }//end of getBrokerGroup(long lngOfficeSeqID)
    
    public int savePolicy(PolicyDetailVO policyDetailVO) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
        return policyDAO.savePolicy(policyDetailVO);
    }//end of savePolicy(PolicyDetailVO policyDetailVO)

    /**
     * This method imports the rules to the policy
     * @param lngPolicySeqId the Policy which is going to accept the rules
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int importRules(Long lngPolicySeqId) throws TTKException {
        return 0;
    }//End of importRules(Long lngPolicySeqId)

    /**
     * This method returns the ArrayList, which contains the OfficeVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of OfficeVO'S object's which contains the Office Code details
     * @exception throws TTKException
     */
    public ArrayList getOfficeCodeList(ArrayList alSearchObjects) throws TTKException{
        officeDAO = (OfficeDAOImpl)this.getAdministrationDAO("officecode");
        return officeDAO.getOfficeCodeList(alSearchObjects);
    }//end of getOfficeCodeList(ArrayList alSearchObjects)

    /** This method returns the ArrayList Which contains the Card Rules details
     * @param strEnrollmentTypeId for identifying Enrollment Type Id
     * @param lngProdPolicySeqId for Product Policy Seq Id
     * @param lngInsSeqID for Insurance Seq ID
     * @return ArrayList which contains the Card Rules details
     * @exception throws TTKException
     */
    public ArrayList getCardRule(String strEnrollmentTypeId, Long lngProdPolicySeqId,Long lngInsSeqID) throws TTKException {
        cardRuleDAO = (CardRuleDAOImpl)this.getAdministrationDAO("cardrules");
        return cardRuleDAO.getCardRule(strEnrollmentTypeId,lngProdPolicySeqId,lngInsSeqID);
    }//end of getCardRule(String strEnrollmentTypeId, Long lngProdPolicySeqId,Long lngInsSeqID)

    /**
     * This method adds/updates the CardRuleVO which contains Card Rules details
     * @param cardRuleVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateCardRule(CardRuleVO cardRuleVO) throws TTKException {
        cardRuleDAO = (CardRuleDAOImpl)this.getAdministrationDAO("cardrules");
        return cardRuleDAO.updateCardRule(cardRuleVO);
    }//end of updateCardRule(CardRuleVO cardRuleVO)

    /**
     * This method returns the ArrayList, which contains the CircularVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of CircularVO'S object's which contains the Circular details
     * @exception throws TTKException
     */
    public ArrayList getCircularList(ArrayList alSearchObjects) throws TTKException {
        circularDAO = (CircularDAOImpl)this.getAdministrationDAO("circulars");
        return circularDAO.getCircularList(alSearchObjects);
    }//end of getCircularList(ArrayList alSearchObjects)

    /**
     * This method adds/updates the CircularVO which contains Circulars details
     * @param circularVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateCircular(CircularVO circularVO) throws TTKException {
        circularDAO = (CircularDAOImpl)this.getAdministrationDAO("circulars");
        return circularDAO.addUpdateCircular(circularVO);
    }//end of addUpadateCircular(CircularVO circularVO)

    /**
     * This method delete's the circular records from the database.
     * @param strCircularSeqId String object which contains the circular sequence id's to be deleted
     * @param lngUserSeqID for which user has loggedin
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCircular(String strCircularSeqID,long lngUserSeqID) throws TTKException {
        circularDAO = (CircularDAOImpl)this.getAdministrationDAO("circulars");
        return circularDAO.deleteCircular(strCircularSeqID,lngUserSeqID);
    }//end of deleteCircular(String strCircularSeqID,long lngUserSeqID)

    /**
	 * This method returns the ArrayList of GroupVO's which are populated from the database
	 * @param lngProductSeqID the Product Sequence ID for which the user groups associated to
	 * @return ArrayList Which contains the GroupVO's which are associated with the particular product sequence id
	 * @exception throws TTKException
	 */
	public ArrayList getUserGroup(Long lngProductSeqID) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		 return productDAO.getUserGroup(lngProductSeqID);
	}//End of getUserGroup(Long lngProductSeqID)

	/**
	 * This method saves the associated groups information to the product in to the database
	 * @param lngProductPolicySeqID Product Policy Sequence ID for which the user groups are associated
	 * @param strOfficeSeqID the assciated office sequence ID's
	 * @param lngUserSeqID the user adding the information
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveUserGroup(Long lngProductPolicySeqID, String strOfficeSeqID,Long lngUserSeqID) throws TTKException
	{
		productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		return productDAO.saveUserGroup(lngProductPolicySeqID, strOfficeSeqID, lngUserSeqID);
	}//End of saveUserGroup(Long lngProductSeqID, String strOfficeSeqID,Long lngUserSeqID)

	/**
     * This method returns the ArrayList, which contains the BufferVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of BufferVO'S object's which contains the Buffer details
     * @exception throws TTKException
     */
    public ArrayList getBufferList(ArrayList alSearchObjects) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.getBufferList(alSearchObjects);
    }//end of getBufferList(ArrayList alSearchObjects)

    /**
     * This method returns the Object[], which contains all the Policy Buffer Admin Authority Details
     * @param lngProdPolicySeqID long value contains Product Policy Seq ID
     * @return Object[] object which contains all the Policy Buffer Admin Authority Details
     * @exception throws TTKException
     */
    public Object[] getBufferAuthority(long lngProdPolicySeqID) throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.getBufferAuthority(lngProdPolicySeqID);
    }//end of getBufferAuthority(long lngProdPolicySeqID)

    /**
     * This method adds/updates the BufferDetailVO which contains Buffer details
     * @param bufferDetailVO the details which has to be added or updated
     * @return long value, contains Buffer Seq ID
     * @exception throws TTKException
     */
    public long saveBuffer(BufferDetailVO bufferDetailVO) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveBuffer(bufferDetailVO);
    }//end of saveBuffer(BufferDetailVO bufferDetailVO)
    //added for hyundai buffer
    /**
     * This method adds/updates the BufferDetailVO which contains Buffer details
     * @param bufferDetailVO the details which has to be added or updated
     * @return long value, contains Buffer Seq ID
     * @exception throws TTKException
     */
    public ArrayList saveManyBuffer(BufferDetailVO bufferDetailVO) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveManyBuffer(bufferDetailVO);
    }//end of saveBuffer(BufferDetailVO bufferDetailVO)
   

    /**
	 * This method returns the BufferDetailVO which contains Buffer Details
	 * @param lngBufferSeqID the Buffer Sequence ID
	 * @param lngUserSeqID contains loggedin User
	 * @return BufferDetailVO which contains Buffer Details
	 * @exception throws TTKException
	 */
	public BufferDetailVO getBufferDetail(ArrayList alBufferDetailParam) throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getBufferDetail(alBufferDetailParam);
	}//end of getBufferDetail(Long lngBufferSeqID,Long lngUserSeqID)

	/**
	 * This method used to save the Copy the Product Rule for other product.
	 * @param lngProductSeqID the Product Sequence ID
	 * @param lngProdPolicySeqId the Product Policy Sequence ID
	 * @param lngUserSeqID contains loggedin User
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int copyProductRules(Long lngProductSeqID,Long lngProdPolicySeqId,Long lngUserSeqID) throws TTKException{
		productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		return productDAO.copyProductRules(lngProductSeqID,lngProdPolicySeqId,lngUserSeqID);
	}//end of getBufferDetail(Long lngBufferSeqID,Long lngUserSeqID)
	
	/**
     * This method saves the associated procedure information to the insurance company into the database
     * @param alAssValues ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int getAssociateExecute(ArrayList alAssValues) throws TTKException{
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		return productDAO.getAssociateExecute(alAssValues);
    }//public ArrayList getAssociateExecute(ArrayList alSearchObjects)
    
    /**
     * This method saves the Prod Policy Limit information for the corresponding Enrollment Type
     * @param alProdPolicyLimit ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveProdPolicyLimit(ArrayList alProdPolicyLimit) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.saveProdPolicyLimit(alProdPolicyLimit);
    }//end of saveProdPolicyLimit(ArrayList alProdPolicyLimit)
    
   
    public int saveProdPolicyEscalateLimit(ArrayList alProdPolicyEscalateLimit) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.saveProdPolicyEscalateLimit(alProdPolicyEscalateLimit);
    }//end of saveProdPolicyLimit(ArrayList alProdPolicyLimit)
    
    
    /**
     * This method returns the ArrayList, which contains the ProdPolicyLimitVO's which are populated from the database
     * @param lngProdPolicySeqID long value which contains ProdPolicySeqID
     * @return ArrayList of ProdPolicyLimitVO'S object's which contains the Buffer details
     * @exception throws TTKException
     */
    public ArrayList<Object> getProdPolicyLimit(long lngProdPolicySeqID) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.getProdPolicyLimit(lngProdPolicySeqID);
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
   
    
    //added for bajaj enhan
    public ArrayList<Object> getProdPolicyEscalateLimit(long lngProdPolicySeqID) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.getProdPolicyEscalateLimit(lngProdPolicySeqID);
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
    
    public ArrayList<Object> getProdPolicyEscalateLimitclm(long lngProdPolicySeqID) throws TTKException {
    	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
    	return productDAO.getProdPolicyEscalateLimitclm(lngProdPolicySeqID);
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
    
    
    /**
     * This method returns WebConfigInfoVOs which contains Weblogin configuration information
     * @param lngProdPolicySeqID,long value which contains ProdPolicySeqID
     * @param lngUserSeqID,long value which contains the UserSeqID
     * @return WebConfigInfoVO which contains the Weblogin Configuration info
     * @exception throws TTKException
     */
    public WebConfigInfoVO getWebConfigInfo(long lngProdPolicySeqID,long lngUserSeqID) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getWebConfigInfo(lngProdPolicySeqID,lngUserSeqID);
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
    
    /**
     * This method saves the Weblogin Configuration information
     * @param webConfigInfoVO the object which contains the Weblogin Configuration Info which has to be  saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInfo(WebConfigInfoVO webConfigInfoVO) throws TTKException{
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.saveWebConfigInfo(webConfigInfoVO);
    }//end of saveWebConfigInfo(WebConfigInfoVO webConfigInfoVO)
    
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links 
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException{
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getWebConfigLinkList(alSearchCriteria);
    }//end of getWebConfigLinkList(ArrayList alSearchCriteria)
    
    /**
     * This method returns WebConfigLinkVOs which contains Weblogin link details
     * @param lngConfigLinkSeqID contains the Config Link Seq ID
     * @return WebConfigLinkVO which contains the Weblogin link details
     * @exception throws TTKException
     */
    public WebConfigLinkVO getWebConfigLinkDetail(long lngConfigLinkSeqID) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getWebConfigLinkDetail(lngConfigLinkSeqID);
    }//end of getWebConfigLinkDetail(long lngConfigLinkSeqID)
    
    /**
     * This method saves the Weblogin link information
     * @param webConfigLinkVO the object which contains the Weblogin link details which has to be saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException
    {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.saveWebConfigLinkInfo(webConfigLinkVO);
    }//end of saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException
    
    /**
     * This method deletes the Weblogin link details from the database
     * @param alDeleteList which contains the id's of the WebConfig links
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteWebConfigLinkInfo(ArrayList alDeleteList) throws TTKException
    {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.deleteWebConfigLinkInfo(alDeleteList);
    }//end of saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException
    
    /**
     * This method returns the ArrayList, which contains the WebConfigMemberVOs which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return ArrayList of WebConfigMemberVOs object's which contains the Webconfig memberdetails
     * @exception throws TTKException
     */
    public ArrayList<Object> getWebConfigMemberDetail(long lngProdPolicySeqID) throws TTKException
    {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getWebConfigMemberDetail(lngProdPolicySeqID);
    }//end of getWebConfigMemberDetail(long lngProdPolicySeqID)
    
    /**
     * This method saves the Webconfig Member Details
     * @param alConfigMemberList the object which contains the Webconfig member detials 
     * @param lngProdPolicySeqID) contains the ProdPolicySeqID
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigMemberDetail(ArrayList alConfigMemberList,long lngProdPolicySeqID) throws TTKException
    {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.saveWebConfigMemberDetail(alConfigMemberList,lngProdPolicySeqID);
    }//end of saveWebConfigMemberDetail(ArrayList alConfigMemberList,long lngProdPolicySeqID)
    
    /**
     * This method returns the ArrayList, which contains the WebconfigInsCompInfoVO which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return WebconfigInsCompInfoVO object which contains the Webconfig Ins Company Information
     * @exception throws TTKException
     */
    public WebconfigInsCompInfoVO getWebConfigInsInfo(long lngProdPolicySeqID) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getWebConfigInsInfo(lngProdPolicySeqID);
    }//end of getWebConfigInsInfo(long lngProdPolicySeqID)

    /**
     * This method saves the Webconfig Ins Company Information
     * @param webconfigInsCompInfo the object which contains the Webconfig Ins Company Information 
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.saveWebConfigInsInfo(webconfigInsCompInfoVO);
    }//end of saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfo)
    
    /**
	 * This method returns the ArrayList, which contains Relationship Information
	 * @param lngProdPolicySeqID contains the ProdPolicySeqID
	 * @return ArrayList object which contains Relationship Information
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getRelationshipList(long lngProdPolicySeqID) throws TTKException {
		webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
		return webConfigDAO.getRelationshipList(lngProdPolicySeqID);
	}//end of getRelationshipList(long lngProdPolicySeqID)
	
	/**
	 * This method saves the Relationship Information for Corresponding Policy
	 * @param alRelshipInfo contains concatenated string of relship id's,prod_policy_seq_id,policy_seq_id and updated_by 
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedRelshipInfo(ArrayList alRelshipInfo) throws TTKException {
		webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
		return webConfigDAO.saveSelectedRelshipInfo(alRelshipInfo);
	}//end of saveSelectedRelshipInfo(ArrayList alRelshipInfo)
	
	/**
     * This method returns the Arraylist of Cache object which contains Policy details for corresponding Group
     * @param lngGrpRegSeqID long value which contains Group Reg Seq ID
     * @return ArrayList of Cache object which contains Policy details for corresponding Group
     * @exception throws TTKException
     */
    public ArrayList getReportPolicyList(long lngGrpRegSeqID) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getReportPolicyList(lngGrpRegSeqID);
    }//end of getReportPolicyList(long lngGrpRegSeqID)
    
    /**
     * This method fetches Report From & To information
     * @param lngPolicySeqID long object which contains Policy Seq ID
     * @return Object[] the values,of  REPORT_FROM & REPORT_TO
     * @exception throws TTKException
     */
    public Object[] getReportFromTo(long lngPolicySeqID) throws TTKException {
    	webConfigDAO = (WebConfigDAOImpl)this.getAdministrationDAO("webconfig");
    	return webConfigDAO.getReportFromTo(lngPolicySeqID);
    }//end of getReportFromTo(long lngPolicySeqID)
    
    /**
	 * This method returns the CustConfigMsgVO which contains configuration Details
	 * @param long lngProdPolicySeqId
	 * @return CustConfigMsgVO which contains configuration Details
	 * @exception throws TTKException
	 */
    public CustConfigMsgVO getCustMsgInfo(long lngProdPolicySeqId) throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	 return policyDAO.getCustMsgInfo(lngProdPolicySeqId);
    }//end of  CustConfigMsgVO getCustMsgInfo(long lngProdPolicySeqId)
    
    /**
     * This method adds/updates the CustConfigMsgVO which contains configuration details
     * @param custConfigMsgVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public int saveCustMsgInfo(CustConfigMsgVO custConfigMsgVO)throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveCustMsgInfo(custConfigMsgVO);
    }//end of saveCustMsgInfo(CustConfigMsgVO custConfigMsgVO)
    
    /**
	 * This method returns the ConfigCopayVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return ConfigCopayVO which contains Copay Amount configuration Details
	 * @exception throws TTKException
	 */
    public ConfigCopayVO getConfigCopayAmt(long lngProdPolicySeqId) throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	 return policyDAO.getConfigCopayAmt(lngProdPolicySeqId);
    }//end of getConfigCopayAmt(long lngProdPolicySeqId)
    
    /**
     * This method adds/updates the ConfigCopayVO which contains Copay Amount configuration Details
     * @param configCopayVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public int saveConfigCopayAmt(ConfigCopayVO configCopayVO)throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveConfigCopayAmt(configCopayVO);
    }//end of saveConfigCopayAmt(ConfigCopayVO configCopayVO)
  /**
	 * This method returns the SumInsuredRestrictionVO which contains CSumInsuredRestriction Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return SumInsuredRestrictionVO which contains SumInsuredRestriction Amount configuration Details
	 * @exception throws TTKException
	 *Changes as per KOC 1140(SumInsuredRestrict) ChangeRequest and 1165
	 */
    public SumInsuredRestrictionVO getConfigSumInsuredRestrictedAmt(long lngProdPolicySeqId) throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	 return policyDAO.getConfigSumInsuredRestrictedAmt(lngProdPolicySeqId);
    }//end of getConfigSumInsuredRestrictedAmt(long lngProdPolicySeqId)
    
    /**
     * This method adds/updates the SumInsuredRestrictionVO which contains SumInsuredRestriction Amount configuration Details
     * @param sumInsuredRestrictionVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException //Changes as per KOC 1140(SumInsuredRestrict) ChangeRequest and 1165
     */
    public ArrayList saveConfigSumInsuredRestrictedAmt(SumInsuredRestrictionVO sumInsuredRestrictionVO,String strFlag)throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveConfigSumInsuredRestrictedAmt(sumInsuredRestrictionVO,strFlag);
    }//end of saveConfigSumInsuredRestrictedAmt(SumInsuredRestrictionVO sumInsuredRestrictionVO,String strFlag)
	 
    /**
     * This method adds/updates the DomConfigVO which contains Domicilary  configuration Details
     * @param DomConfigVO the details which has to be added or updated
     * @return ArrayList value,indicates the rows processed
     * @exception throws TTKException
     */
    //Added as per KOC 1285  Change Request 
    public ArrayList saveDomicilaryConfig(DomConfigVO domConfigVO) throws TTKException
    {
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveDomicilaryConfig(domConfigVO);
    }//end of saveDomicilaryConfig(DomConfigVO domConfigVO)
    
    /**1285
	 * This method returns the DomConfigVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return DomConfigVO which contains Domicilary configuration Details i.e Flag Enr or POL || Product
	 * @exception throws TTKException 
	 */
    public DomConfigVO getDomicilaryConfig(long lngProdPolicySeqId) throws TTKException
    {
    policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
	 return policyDAO.getDomicilaryConfig(lngProdPolicySeqId);
}//end of getDomicilaryConfig(long lngProdPolicySeqId)

  /**bajaj
	 * This method returns the InsuranceApproveVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return InsuranceApproveVO which contains Domicilary configuration Details i.e Flag Enr or POL || Product
	 * @exception throws TTKException
	 */
    public InsuranceApproveVO getConfigInsuranceApproveData(Long webBoardId)throws TTKException{
    	 policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	 return policyDAO.getConfigInsuranceApproveData(webBoardId);
     }
	/**bajaj
     * This method adds/updates the InsuranceApproveVO which contains SumInsuredRestriction Amount configuration Details
     * @param InsuranceApproveVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */	
	 public int saveConfigInsuranceApprove(InsuranceApproveVO insuranceApproveVO, String string)throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveConfigInsuranceApprove(insuranceApproveVO,string);
	}
	 /**
     * This method adds/updates the ShortfallDaysConfigVO which contains Shortfall configuration Details
     * @param configCopayVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public long saveShortfallDaysConfig(ShortfallDaysConfigVO shortfallDaysConfigVO)throws TTKException{
    	policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
    	return policyDAO.saveShortfallDaysConfig(shortfallDaysConfigVO);
    }//end of saveConfigCopayAmt(ConfigCopayVO configCopayVO)
 // Changes added for CR KOC1179
	 /**
    * This method returns the ArrayList, which contains the ShortfallDaysConfigVO which are populated from the database
    * @param lngProdPolicySeqID contains the ProdPolicySeqID
    * @return ArrayList of ShortfallDaysConfigVO object's which contains the Shortfall Days Config Details
    * @exception throws TTKException
    */
   public ShortfallDaysConfigVO getShortfallDaysConfig(long lngProdPolicySeqID) throws TTKException
   {
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getShortfallDaysConfig(lngProdPolicySeqID);
   }//end of getPlcyConfigPlanDetail(long lngProdPolicySeqID)
   
   /**
    * This method Fetches the License Numbers for Providers
    * @exception throws TTKException
    */
	public ArrayList<Object> getProductCode(String prviderId) throws TTKException
   {
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getProductCode(prviderId);
   }//end of getLicenceNumbers(String prviderId) throws TTKException
   /**
    * This method returns the ArrayList, which contains the ShortfallDaysConfigVO which are populated from the database
    * @param lngProdPolicySeqID contains the ProdPolicySeqID
    * @return ArrayList of ShortfallDaysConfigVO object's which contains the Shortfall Days Config Details
    * @exception throws TTKException
    */
   public Object savePreApprovedLimit(Object[] fieldsData) throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.savePreApprovedLimit(fieldsData);
  }//end of savePreApprovedLimit(long lngProdPolicySeqID)  
   /**
    * This method returns the ArrayList, which contains the ShortfallDaysConfigVO which are populated from the database
    * @param lngProdPolicySeqID contains the ProdPolicySeqID
    * @return ArrayList of ShortfallDaysConfigVO object's which contains the Shortfall Days Config Details
    * @exception throws TTKException
    */
  public Object[] getPreApprovedLimit(long lngProdPolicySeqID) throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getPreApprovedLimit(lngProdPolicySeqID);
  }//end of getPreApprovedLimit(long lngProdPolicySeqID)




@Override
public long savePremiumConfiguration(ProdPolicyLimitVO prodPolicyLimitVO)
		throws TTKException 
		{
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
     return productDAO.savePremiumConfiguration(prodPolicyLimitVO);
       }


public ArrayList getPremiumConfiguration(ProductVO productVO) throws TTKException
{
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.getPremiumConfiguration(productVO);
		
}
public int deletePremiumConfiguration(ProductVO productVO) throws TTKException
{
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
     return productDAO.deletePremiumConfiguration(productVO);
  
}
  public Object saveCapitationCategory(Object[] fieldsData) throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.saveCapitationCategory(fieldsData);
}//end of saveCapitationCategory(Object[] fieldsData)  
  public Object[] getCapitationCategory(long lngProdPolicySeqID) throws TTKException{
		policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getCapitationCategory(lngProdPolicySeqID);
}//end of getCapitationCategory)


public ArrayList<Object> getCardTemplates(String inscode) throws TTKException {
	
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
     return productDAO.getCardTemplates(inscode);
}





public ProdPolicyLimitVO getInsCmpCode(long linscode) throws TTKException {
	
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
     return productDAO.getInsCmpCode(linscode);
}




@Override
public int synchronizeRuleAllSchemes(Long lngProdPolicySeqID) throws TTKException {
	// TODO Auto-generated method stub
	productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	return productDAO.synchronizeRuleAllSchemes(lngProdPolicySeqID);	
}
   



public ArrayList<Object> getCapitationYNData(String premiumConfigSeqId)throws TTKException
{
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	 return productDAO.getCapitationYNData( premiumConfigSeqId);
	}


public int[] synchronizeRuleGenProdCardRule(String[] strSeqID,Long lngProdPolicySeqID, String strDenialsyn, HashMap flagMap,Long addedby)throws TTKException
{
	 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	 return productDAO.synchronizeRuleGenProdCardRule(strSeqID,lngProdPolicySeqID,strDenialsyn,flagMap,addedby);
}


  /**
   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
   * @param lngProdPolicySeqID contains the ProdPolicySeqID
   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
   * @exception throws TTKException
   */ 
  public ArrayList<GeoLocationCopayDetails> getGeographicalLocationts(String authorityType) throws TTKException{
	  policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getGeographicalLocationts(authorityType);
  }
  
  /**
   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
   * @param lngProdPolicySeqID contains the ProdPolicySeqID
   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
   * @exception throws TTKException
   */ 
  public ArrayList<CountryCopayDetails> getGeoCountries(String geoLocCode) throws TTKException{
	  policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getGeoCountries(geoLocCode);
  }
  
  /**
   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
   * @param lngProdPolicySeqID contains the ProdPolicySeqID
   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
   * @exception throws TTKException
   */
 public ArrayList<EmirateCopayDetails> getEmirates(String countryCode) throws TTKException{
	 policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getEmirates(countryCode);
 }
	 /**
	   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
	   * @param lngProdPolicySeqID contains the ProdPolicySeqID
	   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
	   * @exception throws TTKException
	   */
	 public ArrayList<ProviderDetails> getProviderList(ProviderRuleVO providerRuleVO) throws TTKException{
	 policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
		return policyDAO.getProviderList(providerRuleVO);
}
	 

	 /**
	   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
	   * @param lngProdPolicySeqID contains the ProdPolicySeqID
	   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
	   * @exception throws TTKException
	   */
	 public ArrayList<ProviderDetails> getMOHProviderList(ProviderRuleVO providerRuleVO) throws TTKException{
		 policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
			return policyDAO.getMOHProviderList(providerRuleVO);
	 }

	 /**
	   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
	   * @param lngProdPolicySeqID contains the ProdPolicySeqID
	   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
	   * @exception throws TTKException
	   */
	 public HashMap<String,ProviderDetails> getProviderNameList(ProviderRuleVO providerRuleVO) throws TTKException{
		 policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
			return policyDAO.getProviderNameList(providerRuleVO);
	 }
	 
	  public Map<String, String> getEncounterTypes(String benefitType)throws TTKException{
	
		  policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
			return policyDAO.getEncounterTypes(benefitType);    
	  }




	
	public ArrayList<Object> getInsCompnyList(String authority)
			throws TTKException {

		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.getInsCompnyList(authority);
	}
	
	public ArrayList<MOUDocumentVO> getPolicyDocsUploads(String policy_seq_id)throws TTKException
	{
		  policyDAO = (PolicyDAOImpl)this.getAdministrationDAO("policy");
			return policyDAO.getPolicyDocsUploads(policy_seq_id);    
	}
	  
	  public ArrayList getAuditPremiumConfiguration(ProductVO productVO)throws TTKException
	  {
		  productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		     return productDAO.getAuditPremiumConfiguration(productVO);
			
	  }
	  
	  public int deleteAuditlogsConfiguration(ProductVO productVO)throws TTKException
	  {
		  productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		  return productDAO.deleteAuditlogsConfiguration(productVO);
	  }





	public String saveVatConfigYN(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException {
		
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.saveVatConfigYN(prodPolicyLimitVO);
	}
	
	public Object[] getPremiumEffectiveDates(ProductVO productVO) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.getPremiumEffectiveDates(productVO);
	}
	
	public ArrayList<Object> getEffectiveDatesList(ProductVO productVO) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.getEffectiveDatesList(productVO);
	}
	
	public long updatePremiumConfigDetails(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
	     return productDAO.updatePremiumConfigDetails(prodPolicyLimitVO);
	}




	
	public int deletePremiumEffectiveConfiguration(ProductVO productVO)	throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		  return productDAO.deletePremiumEffectiveConfiguration(productVO);
	}



	public String getVatPercent(long linscode) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		  return productDAO.getVatPercent(linscode);
	}




	@Override
	public String getVatConfigDetail(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException {
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		  return productDAO.getVatConfigDetail(prodPolicyLimitVO);
	}




	
	public ArrayList<String[]> getModificationRemarks(ProductVO productVO) throws TTKException {
		
		 productDAO = (ProductDAOImpl)this.getAdministrationDAO("product");
		  return productDAO.getModificationRemarks(productVO);
	}

}//end of ProductPolicyManagerBean
