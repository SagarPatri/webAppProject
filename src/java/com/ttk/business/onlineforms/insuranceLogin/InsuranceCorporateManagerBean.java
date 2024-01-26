/**
 * @ (#) InsuranceCorporateManagerBean.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : InsuranceCorporateManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 19, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.business.onlineforms.insuranceLogin;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.onlineforms.OnlineAccessDAOFactory;
import com.ttk.dao.impl.onlineforms.insuranceLogin.InsCorpDAOImpl;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.onlineforms.insuranceLogin.AuthDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.AuthorizationVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimsVO;
import com.ttk.dto.onlineforms.insuranceLogin.DashBoardVO;
import com.ttk.dto.onlineforms.insuranceLogin.EnrollMemberVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsCorporateVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;
import com.ttk.dto.onlineforms.insuranceLogin.PolicyDetailVO;
import com.ttk.dto.onlineforms.insuranceLogin.ProductTableVO;
import com.ttk.dto.onlineforms.insuranceLogin.RetailVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class InsuranceCorporateManagerBean implements InsuranceCorporateManager{
	
	private OnlineAccessDAOFactory onlineAccessDAOFactory = null;
	private InsCorpDAOImpl insCorpDAOImpl = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getOnlineAccessDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (onlineAccessDAOFactory == null)
				onlineAccessDAOFactory = new OnlineAccessDAOFactory();
			if(strIdentifier!=null)
			{
				return onlineAccessDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getOnlineAccessDAO(String strIdentifier)
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getEnrollMemberData(ArrayList alSearchCriteria,String strVar) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getEnrollMemberData(alSearchCriteria,strVar);
    }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
    
    
    
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    
    public InsCorporateVO getCorpFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getCorpFocusMemberDetails(enrollmentId,alSortVars,strVar);
    }//end of getCorpFocusMemberDetails(String enrollmentId)
    
    
    
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    
    public RetailVO getRetailFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getRetailFocusMemberDetails(enrollmentId,alSortVars,strVar);
    }//end of getCorpFocusMemberDetails(String enrollmentId)
    
    
    /*
     * Get only the Authorizations in this procedure focused view 
     */
    
    public ArrayList<AuthorizationVO> getCorpFocusAuthorizationDetails(String enrollmentId,ArrayList alSortVars) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getCorpFocusAuthorizationDetails(enrollmentId,alSortVars);
    }//end of getCorpFocusMemberDetails(String enrollmentId)
    
    
    /*
     * Get only the Authorizations in this procedure focused view 
     */
    
    public ArrayList<ClaimsVO> getCorpFocusClaimsDetails(String enrollmentId,ArrayList alSortVars) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getCorpFocusClaimsDetails(enrollmentId,alSortVars);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
    
    /**
     * DashBoard Details
     */

    public DashBoardVO getDashBoardDetails(String insCompCode) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getDashBoardDetails(insCompCode);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
    
    
    /**
     * Claim Details - on click on the claim numbers link
     */

    public ClaimDetailsVO getClaimDetails(String clmNumber) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getClaimDetails(clmNumber);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
		
    
    /**
     * Auth Details - on click on the Auth numbers link
     */

    public AuthDetailsVO getAuthDetails(String authNumber) throws TTKException {
    	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
        return insCorpDAOImpl.getAuthDetails(authNumber);
    }//end of getCorpFocusClaimsDetails(String clmNumber)
    
    
    
   /**
    * GLobal View for corporate
    */

   public InsGlobalViewVO getCorpGlobalDetails(String insCompCode,String strVar,String CorpOrProdSeqId) throws TTKException {
   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
       return insCorpDAOImpl.getCorpGlobalDetails(insCompCode,strVar,CorpOrProdSeqId);
   }//end of getCorpFocusClaimsDetails(String insCompCode)
    
   
   
   public ArrayList<RetailVO> getRetailList(ArrayList alSearchCriteria) throws TTKException {
   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
       return insCorpDAOImpl.getRetailList(alSearchCriteria);
   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
   
   
   /**
    * sEARCh Retail Member List
    */
   public ArrayList<EnrollMemberVO> getRetailMemberList(ArrayList alSearchCriteria) throws TTKException {
	   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
	       return insCorpDAOImpl.getRetailMemberList(alSearchCriteria);
	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
   
   
   
   //PRODUCTS AND POLICIES
   
   /**
    * SEARCH PRODUCT LIST
    */
   public ArrayList<ProductTableVO> getProductsList(ArrayList alSearchCriteria) throws TTKException {
	   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
	       return insCorpDAOImpl.getProductsList(alSearchCriteria);
	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
   
   
   /**
    * SEARCH policy LIST
    */
   public ArrayList<PolicyDetailVO> getPolicyDetails(int productSeqId,String insCompCodeWebLogin) throws TTKException {
	   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
	       return insCorpDAOImpl.getPolicyDetails(productSeqId,insCompCodeWebLogin);
	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
   
   
   /**
    * Get The BEnefits of Each Policy for the Product
    */
   public String[] getPolicyBenefitDetails(int productSeqId,String policySeqId,String enrolTypeId,String insCompCodeWebLogin) throws TTKException {
	   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
	       return insCorpDAOImpl.getPolicyBenefitDetails(productSeqId,policySeqId,enrolTypeId,insCompCodeWebLogin);
	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)  
   
   /*
    * Get Log Details
    */
   public ArrayList getLogData(String strUserId) throws TTKException {
	   	insCorpDAOImpl = (InsCorpDAOImpl)this.getOnlineAccessDAO("insLoginCorp");
	       return insCorpDAOImpl.getLogData(strUserId);
	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
    
   
   public ArrayList getEndorsements(String insCompCodeWebLogin,String enrollmentId) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getEndorsements(insCompCodeWebLogin,enrollmentId);
	}//end of getEndorsements(String InsName)
   
   
   /*
	 * Getting the Corporate List for Insurance login reports
	 */
   public ArrayList<CacheObject> getCorporatesList(String insCompCodeWebLogin) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getCorporatesList(insCompCodeWebLogin);
	}//end of getEndorsements(String InsName)

   
   /*
	 * Getting the Policies List based on the corporate Selectes for Insurance login reports
	 */
	public ArrayList<CacheObject> getPolicyList(String insCompCodeWebLogin,String strCorpSeqId) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getPolicyList(insCompCodeWebLogin,strCorpSeqId);
	}//end of getPolicyList(String insCompCodeWebLogin,String strCorpSeqId)

	
	/*
	 * Getting the Policy Period based on the policy for Insurance login reports
	 */
	public ArrayList<CacheObject> getPolicyPeriod(String strPolicySeqId) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getPolicyPeriod(strPolicySeqId);
	}//end of getPolicyList(String insCompCodeWebLogin,String strCorpSeqId)
	/*
	 * Getting the Policy Period based on the policy for Insurance login reports
	 */
	public ArrayList<CacheObject> getInduPolicyList(String insCompCodeWebLogin) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getInduPolicyList(insCompCodeWebLogin);
	}//end of getInduPolicyList(String insCompCodeWebLogin,String strCorpSeqId)
	
	public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getClaimTatData(alInputParams);
	}
	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getPreAuthTatData(alInputParams);
	}
	public ArrayList<String> getTATForCards(String insCompCodeWebLogin) throws TTKException
	{
	   insCorpDAOImpl = (InsCorpDAOImpl) this.getOnlineAccessDAO("insLoginCorp");
		return insCorpDAOImpl.getTATForCards(insCompCodeWebLogin);
	}
	
	
}//end of InsuranceCorporateManagerBean
