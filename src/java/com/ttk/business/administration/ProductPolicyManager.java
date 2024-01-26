/**
 * @ (#) ProductPolicyManager.java Nov 29, 2005
 * Project      : TTK HealthCare Services
 * File         : ProductPolicyManager
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.BufferDetailVO;
import com.ttk.dto.administration.CardRuleVO;
import com.ttk.dto.administration.CircularVO;
import com.ttk.dto.administration.ConfigCopayVO;
import com.ttk.dto.administration.CustConfigMsgVO;
import com.ttk.dto.administration.DomConfigVO;
import com.ttk.dto.administration.InsuranceApproveVO;//bajaj
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.administration.ProdPolicyLimitVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.ProviderDetails;
import com.ttk.dto.administration.ProviderRuleVO;
import com.ttk.dto.administration.ShortfallDaysConfigVO;
import com.ttk.dto.administration.SumInsuredRestrictionVO;//added as per KOC 1140 and 1142(1165)
import com.ttk.dto.administration.WebConfigInfoVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.EmirateCopayDetails;
import com.ttk.xml.administration.GeoLocationCopayDetails;
@Local

public interface ProductPolicyManager {	 
	 
    public ArrayList getProductList(ArrayList alSearchObjects) throws TTKException ;

    /**
     * This method returns the ArrayList, which contains the RuleSynchronizationVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RuleSynchronizationVO'S object's which contains the details of the Policies to Synchronize the Rules
     * @exception throws TTKException
     */
    public ArrayList getSynchPolicyList(ArrayList alSearchObjects) throws TTKException ;

    /**
     * This method synchronizes the rules of Products to Policies
     * @param strSeqID contains concatenated ProdpolicySeqID's of Policies
     * @param lngProdPolicySeqID contains productpolicySeqID of Product
     * @param addedby 
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int synchronizeRule(String[] strSeqID,long lngProdPolicySeqID,String strDenialsyn) throws TTKException ;//denial process

    /**
     * This method adds/updates the ProductVO which contains insurance products details
     * @param productVO the details of insurance product which has to be added or updated
     * @return Object[] the values,of  Product Policy Sequence Id and InsAbberevation Code
     * @exception throws TTKException
     */
    public Object[] addUpdateProduct(ProductVO productVO) throws TTKException ;

    /**
     * This method deletes concerned InsuranceProduct details from the database
     * @param alProductList which contains the id's of the Insurance Product's
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteProduct(ArrayList alProductList) throws TTKException ;

    /**
     * This method returns the ProductVO, which contains all the product details
     * @param lngProdPolicySeqID the productpolicySequenceID for which the product Details has to be fetched
     * @return ProductVO object which contains all the Product details
     * @exception throws TTKException
     */
    public ProductVO getProductDetails(long lngProdPolicySeqID) throws TTKException;

    /**
     * This method returns the Arraylist of PolicyVO's  which contains the details of Administration policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains the details of Administration policy details
     * @exception throws TTKException
     */
    public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException ;

    /**
     * This method returns the PolicyDetailVO, which contains all the policy details
     * @param policyVO It contains the basic details of policy
     * @return PolicyDetailVO object which contains all the policy details
     * @exception throws TTKException
     */
    public PolicyDetailVO getPolicyDetail(Long lngProdPolicySeqID,Long lngUserSeqID) throws TTKException;

    /**
     * This method returns the ArrayList, which contains Groups corresponding to TTK Branch
     * @param lngOfficeSeqID It contains the TTK Branch
     * @return ArrayList object which contains Groups corresponding to TTK Branch
     * @exception throws TTKException
     */
    public ArrayList getGroup(long lngOfficeSeqID) throws TTKException;

    /**
     * This method saves the Enrollment Policy information
     * @param policyDetailVO the object which contains the Policy Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
//kocb
    public ArrayList getBrokerGroup(long lngOfficeSeqID) throws TTKException;

    /**
     * This method saves the Enrollment Policy information
     * @param policyDetailVO the object which contains the Policy Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    
    public int savePolicy(PolicyDetailVO policyDetailVO) throws TTKException;

    /**
     * This method imports the rules to the policy
     * @param lngPolicySeqId the Policy which is going to accept the rules
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int importRules(Long lngPolicySeqId) throws TTKException ;

    /**
     * This method returns the ArrayList, which contains the OfficeVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of OfficeVO'S object's which contains the Office Code details
     * @exception throws TTKException
     */
    public ArrayList getOfficeCodeList(ArrayList alSearchObjects) throws TTKException;

    /** This method returns the ArrayList Which contains the Card Rules details
     * @param strEnrollmentTypeId for identifying Enrollment Type Id
     * @param lngProdPolicySeqId for Product Policy Seq Id
     * @param lngInsSeqID for Insurance Seq ID
     * @return ArrayList which contains the Card Rules details
     * @exception throws TTKException
     */
    public ArrayList getCardRule(String strEnrollmentTypeId, Long lngProdPolicySeqId,Long lngInsSeqID) throws TTKException;

    /**
     * This method adds/updates the CardRuleVO which contains Card Rules details
     * @param cardRuleVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateCardRule(CardRuleVO cardRuleVO) throws TTKException;

    /**
     * This method returns the ArrayList, which contains the CircularVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of CircularVO'S object's which contains the Circular details
     * @exception throws TTKException
     */
    public ArrayList getCircularList(ArrayList alSearchObjects) throws TTKException;

    /**
     * This method adds/updates the CircularVO which contains Circulars details
     * @param circularVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateCircular(CircularVO circularVO) throws TTKException;

    /**
     * This method delete's the circular records from the database.
     * @param strCircularSeqID String object which contains the circular sequence id's to be deleted
     * @param lngUserSeqID for which user has loggedin
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCircular(String strCircularSeqID,long lngUserSeqID) throws TTKException;

	/**
	 * This method returns the ArrayList of GroupVO's which are populated from the database
	 * @param lngProductSeqID the Product Sequence ID for which the user groups associated to
	 * @return ArrayList Which contains the GroupVO's which are associated with the particular product sequence id
	 * @exception throws TTKException
	 */
	public ArrayList getUserGroup(Long lngProductSeqID) throws TTKException	;

	/**
	 * This method saves the associated groups information to the product in to the database
	 * @param lngProductPolicySeqID Product Policy Sequence ID for which the user groups are associated
	 * @param strOfficeSeqID the assciated office sequence ID's
	 * @param lngUserSeqID the user adding the information
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveUserGroup(Long lngProductPolicySeqID, String strOfficeSeqID,Long lngUserSeqID) throws TTKException;

	/**
     * This method returns the ArrayList, which contains the BufferVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of BufferVO'S object's which contains the Buffer details
     * @exception throws TTKException
     */
    public ArrayList getBufferList(ArrayList alSearchObjects) throws TTKException;

    /**
     * This method returns the Object[], which contains all the Policy Buffer Admin Authority Details
     * @param lngProdPolicySeqID long value contains Product Policy Seq ID
     * @return Object[] object which contains all the Policy Buffer Admin Authority Details
     * @exception throws TTKException
     */
    public Object[] getBufferAuthority(long lngProdPolicySeqID) throws TTKException;

    /**
     * This method adds/updates the BufferDetailVO which contains Buffer details
     * @param bufferDetailVO the details which has to be added or updated
     * @return long value, contains Buffer Seq ID
     * @exception throws TTKException
     */
    public long saveBuffer(BufferDetailVO bufferDetailVO) throws TTKException;
    public ArrayList saveManyBuffer(BufferDetailVO bufferDetailVO) throws TTKException;

    /**
	 * This method returns the BufferDetailVO which contains Buffer Details
	 * @param ArrayList alBufferDetailParam
	 * @return BufferDetailVO which contains Buffer Details
	 * @exception throws TTKException
	 */
	public BufferDetailVO getBufferDetail(ArrayList alBufferDetailParam) throws TTKException;

	/**
	 * This method used to save the Copy the Product Rule for other product.
	 * @param lngProductSeqID the Product Sequence ID
	 * @param lngProdPolicySeqId the Product Policy Sequence ID
	 * @param lngUserSeqID contains loggedin User
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int copyProductRules(Long lngProductSeqID,Long lngProdPolicySeqId,Long lngUserSeqID) throws TTKException;
	
	 /**
     * This method saves the associated procedure information to the insurance company into the database
     * @param alAssValues ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int getAssociateExecute(ArrayList alAssValues) throws TTKException;
    
    /**
     * This method saves the Prod Policy Limit information for the corresponding Enrollment Type
     * @param alProdPolicyLimit ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveProdPolicyLimit(ArrayList alProdPolicyLimit) throws TTKException;
    //added for bajaj
    public int saveProdPolicyEscalateLimit(ArrayList alProdPolicyEscalateLimit) throws TTKException;
    /**
     * This method returns the ArrayList, which contains the ProdPolicyLimitVO's which are populated from the database
     * @param lngProdPolicySeqID long value which contains ProdPolicySeqID
     * @return ArrayList of ProdPolicyLimitVO'S object's which contains the Buffer details
     * @exception throws TTKException
     */
    public ArrayList<Object> getProdPolicyLimit(long lngProdPolicySeqID) throws TTKException;
    
    //added for baja enhancement
    public ArrayList<Object> getProdPolicyEscalateLimit(long lngProdPolicySeqID) throws TTKException;
    public ArrayList<Object> getProdPolicyEscalateLimitclm(long lngProdPolicySeqID) throws TTKException;
  
   
    
    /**
     * This method returns WebConfigInfoVOs which contains Weblogin configuration information
     * @param lngProdPolicySeqID,long value which contains ProdPolicySeqID
     * @param lngUserSeqID,long value which contains the UserSeqID
     * @return WebConfigInfoVO which contains the Weblogin Configuration info
     * @exception throws TTKException
     */
    public WebConfigInfoVO getWebConfigInfo(long lngProdPolicySeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method saves the Weblogin Configuration information
     * @param webConfigInfoVO the object which contains the Weblogin Configuration Info which has to be  saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInfo(WebConfigInfoVO webConfigInfoVO) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links 
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns WebConfigLinkVOs which contains Weblogin link details
     * @param lngConfigLinkSeqID contains the Config Link Seq ID
     * @return WebConfigLinkVO which contains the Weblogin link details
     * @exception throws TTKException
     */
    public WebConfigLinkVO getWebConfigLinkDetail(long lngConfigLinkSeqID) throws TTKException;
    
    /**
     * This method saves the Weblogin link information
     * @param webConfigLinkVO the object which contains the Weblogin link details which has to be saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException;
    
    /**
     * This method deletes the Weblogin link details from the database
     * @param alDeleteList which contains the id's of the WebConfig links
     * @return int value, greater than zero indicates sucessful execution of the task
     * @exception throws TTKException
     */
    public int deleteWebConfigLinkInfo(ArrayList alDeleteList) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the WebConfigMemberVOs which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return ArrayList of WebConfigMemberVOs object's which contains the Webconfig memberdetails
     * @exception throws TTKException
     */
    public ArrayList<Object> getWebConfigMemberDetail(long lngProdPolicySeqID) throws TTKException;

    /**
     * This method saves the Webconfig Member Details
     * @param alConfigMemberList the object which contains the Webconfig member detials 
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigMemberDetail(ArrayList alConfigMemberList,long lngProdPolicySeqID) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the WebconfigInsCompInfoVO which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return WebconfigInsCompInfoVO object which contains the Webconfig Ins Company Information
     * @exception throws TTKException
     */
    public WebconfigInsCompInfoVO getWebConfigInsInfo(long lngProdPolicySeqID) throws TTKException;

    /**
     * This method saves the Webconfig Ins Company Information
     * @param webconfigInsCompInfo the object which contains the Webconfig Ins Company Information 
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException;
    
    /**
	 * This method returns the ArrayList, which contains Relationship Information
	 * @param lngProdPolicySeqID contains the ProdPolicySeqID
	 * @return ArrayList object which contains Relationship Information
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getRelationshipList(long lngProdPolicySeqID) throws TTKException;
	
	/**
	 * This method saves the Relationship Information for Corresponding Policy
	 * @param alRelshipInfo contains concatenated string of relship id's,prod_policy_seq_id,policy_seq_id and updated_by 
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedRelshipInfo(ArrayList alRelshipInfo) throws TTKException;
	
	/**
     * This method returns the Arraylist of Cache object which contains Policy details for corresponding Group
     * @param lngGrpRegSeqID long value which contains Group Reg Seq ID
     * @return ArrayList of Cache object which contains Policy details for corresponding Group
     * @exception throws TTKException
     */
    public ArrayList getReportPolicyList(long lngGrpRegSeqID) throws TTKException;
    
    /**
     * This method fetches Report From & To information
     * @param lngPolicySeqID long object which contains Policy Seq ID
     * @return Object[] the values,of  REPORT_FROM & REPORT_TO
     * @exception throws TTKException
     */
    public Object[] getReportFromTo(long lngPolicySeqID) throws TTKException;
    
    /**
	 * This method returns the CustConfigMsgVO which contains configuration Details
	 * @param long lngProdPolicySeqId
	 * @return CustConfigMsgVO which contains configuration Details
	 * @exception throws TTKException
	 */
    public CustConfigMsgVO getCustMsgInfo(long lngProdPolicySeqId) throws TTKException;
    
    /**
     * This method adds/updates the CustConfigMsgVO which contains configuration details
     * @param custConfigMsgVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public int saveCustMsgInfo(CustConfigMsgVO custConfigMsgVO)throws TTKException;
    
    /**
	 * This method returns the ConfigCopayVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return ConfigCopayVO which contains Copay Amount configuration Details
	 * @exception throws TTKException
	 */
    public ConfigCopayVO getConfigCopayAmt(long lngProdPolicySeqId) throws TTKException;
    
    /**
     * This method adds/updates the ConfigCopayVO which contains Copay Amount configuration Details
     * @param configCopayVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public int saveConfigCopayAmt(ConfigCopayVO configCopayVO)throws TTKException;
	
	    // Changes as per KOC 1140 ChangeRequest
    /**
	 * This method returns the SumInsuredRestrictionVO which contains SumInsuredRestriction Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return SumInsuredRestrictionVO which contains SumInsuredRestriction Amount configuration Details
	 * @exception throws TTKException
	 */
    public SumInsuredRestrictionVO getConfigSumInsuredRestrictedAmt(long lngProdPolicySeqId) throws TTKException;
     /**
     * This method adds/updates the DomConfigVO which contains Domicilary  configuration Details
     * @param DomConfigVO the details which has to be added or updated
     * @return ArrayList value,indicates the rows processed
     * @exception throws TTKException
     */
    //added as per KOC 1285  Change request
    public ArrayList saveDomicilaryConfig(DomConfigVO domConfigVO) throws TTKException ;
    
    /**
     * This method adds/updates the SumInsuredRestrictionVO which contains SumInsuredRestriction Amount configuration Details
     * @param sumInsuredRestrictionVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public ArrayList saveConfigSumInsuredRestrictedAmt(SumInsuredRestrictionVO sumInsuredRestrictionVO, String strFlag)throws TTKException;
	//Changes as per KOC 1140 ChangeReques
    
    /**
	 * This method returns the DomConfigVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return DomConfigVO which contains Domicilary configuration Details i.e Flag Enr or POL || Product
	 * @exception throws TTKException
	 */
    public DomConfigVO getDomicilaryConfig(long lngProdPolicySeqId) throws TTKException;//1285

  /**
	 * This method returns the InsuranceApproveVO which contains Copay Amount configuration Details
	 * @param long lngProdPolicySeqId
	 * @return InsuranceApproveVO which contains Domicilary configuration Details i.e Flag Enr or POL || Product
	 * @exception throws TTKException
	 */
	public InsuranceApproveVO getConfigInsuranceApproveData(Long webBoardId)throws TTKException;//bajaj
 /**
     * This method adds/updates the InsuranceApproveVO which contains SumInsuredRestriction Amount configuration Details
     * @param InsuranceApproveVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public int saveConfigInsuranceApprove(InsuranceApproveVO insuranceApproveVO, String string) throws TTKException;//bajaj

 	// Shortfall CR KOC1179
 /**
     * This method adds/updates the ShortfallDaysConfigVO which contains Shortfall Days Configuration
     * @param configCopayVO the details which has to be added or updated
     * @return int value,indicates the rows processed
     * @exception throws TTKException
     */
    public long saveShortfallDaysConfig(ShortfallDaysConfigVO shortfallDaysConfigVO)throws TTKException;

 // Shortfall CR KOC1179
	  /**
   * This method returns the ArrayList, which contains the PlcyConfigPlanDetailsVOs which are populated from the database
   * @param lngProdPolicySeqID contains the ProdPolicySeqID
   * @return ArrayList of PlcyConfigPlanDetailsVOs object's which contains the Policy Config Plan Details
   * @exception throws TTKException
   */
  public ShortfallDaysConfigVO getShortfallDaysConfig(long lngProdPolicySeqID) throws TTKException;
  
  
  /**
   * To Fetch Product Code based on Product name - used Ajax to do
   */
  public ArrayList<Object> getProductCode(String prviderId) throws TTKException;
  
  /**
	   * This method returns the ArrayList, which contains the PlcyConfigPlanDetailsVOs which are populated from the database
	   * @param lngProdPolicySeqID contains the ProdPolicySeqID
	   * @return ArrayList of PlcyConfigPlanDetailsVOs object's which contains the Policy Config Plan Details
	   * @exception throws TTKException
	   */ 	 
	public Object savePreApprovedLimit(Object[] fieldsData) throws TTKException;

	 /**
	   * This method returns the ArrayList, which contains the PlcyConfigPlanDetailsVOs which are populated from the database
	   * @param lngProdPolicySeqID contains the ProdPolicySeqID
	   * @return ArrayList of PlcyConfigPlanDetailsVOs object's which contains the Policy Config Plan Details
	   * @exception throws TTKException
	   */ 
	 public Object[] getPreApprovedLimit(long lngProdPolicySeqID) throws TTKException;
	 public long savePremiumConfiguration(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException;
	 public ArrayList getPremiumConfiguration(ProductVO productVO) throws TTKException;
	 public int deletePremiumConfiguration(ProductVO productVO) throws TTKException;
	 
	 
		public Object saveCapitationCategory(Object[] fieldsData) throws TTKException;
		
		public Object[] getCapitationCategory(long lngProdPolicySeqID) throws TTKException;

		public ArrayList<Object> getCardTemplates(String inscode) throws TTKException;

		public ProdPolicyLimitVO getInsCmpCode(long linscode) throws TTKException;

		public int synchronizeRuleAllSchemes(Long lngProdPolicySeqID) throws TTKException;

		public ArrayList<Object> getCapitationYNData(String premiumConfigSeqId)throws TTKException;

		public int[] synchronizeRuleGenProdCardRule(String[] strSeqID,Long lngProdPolicySeqID, String strDenialsyn, HashMap flagMap,Long addedby)throws TTKException;
		
	 

		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */ 
		 public ArrayList<GeoLocationCopayDetails> getGeographicalLocationts(String authorityType) throws TTKException;
		 

		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */
		 public ArrayList<CountryCopayDetails> getGeoCountries(String geoLocCode) throws TTKException;
		 

		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */
		 public ArrayList<EmirateCopayDetails> getEmirates(String countryCode) throws TTKException;
		 

		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */
		 public ArrayList<ProviderDetails> getProviderList(ProviderRuleVO providerRuleVO) throws TTKException;
		

		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */
		 public ArrayList<ProviderDetails> getMOHProviderList(ProviderRuleVO providerRuleVO) throws TTKException;
		
		 /**
		   * This method returns the ArrayList, which contains the Geographical locations which are populated from the database
		   * @param lngProdPolicySeqID contains the ProdPolicySeqID
		   * @return ArrayList of CacheObject object's which contains the CacheObject id and CacheObject desc
		   * @exception throws TTKException
		   */
		 public HashMap<String,ProviderDetails> getProviderNameList(ProviderRuleVO providerRuleVO) throws TTKException;

		  public Map<String,String> getEncounterTypes(String benefitType)throws TTKException;

		public ArrayList<Object> getInsCompnyList(String authority)throws TTKException;

		public ArrayList<MOUDocumentVO> getPolicyDocsUploads(String policy_seq_id)throws TTKException;

		public ArrayList getAuditPremiumConfiguration(ProductVO productVO)throws TTKException;

		public int deleteAuditlogsConfiguration(ProductVO productVO)throws TTKException;

		public String saveVatConfigYN(ProdPolicyLimitVO prodPolicyLimitVO)throws TTKException;
		
	
		
		public Object[] getPremiumEffectiveDates(ProductVO productVO)throws TTKException;
		
		public ArrayList<Object> getEffectiveDatesList(ProductVO productVO) throws TTKException;

		public long updatePremiumConfigDetails(ProdPolicyLimitVO prodPolicyLimitVO)throws TTKException;

		public int deletePremiumEffectiveConfiguration(ProductVO productVO)throws TTKException;

		public String getVatPercent(long linscode)throws TTKException;

		public String getVatConfigDetail(ProdPolicyLimitVO prodPolicyLimitVO)throws TTKException;

		public ArrayList<String[]> getModificationRemarks(ProductVO productVO) throws TTKException;
		
		
		
		
		
}//end of ProductPolicyManager
