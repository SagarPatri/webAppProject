/**
 * @ (#) InsuranceCorporateManager.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : InsuranceCorporateManager.java
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

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
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

@Local

public interface InsuranceCorporateManager {
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getEnrollMemberData(ArrayList alSearchCriteria,String strVar) throws TTKException;
    
    
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    public InsCorporateVO getCorpFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException;
    
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    public RetailVO getRetailFocusMemberDetails(String enrollmentId,ArrayList alSortVars,String strVar) throws TTKException;
    
    
    
    /*
     * Get only the Authorizations in this procedure
     */
    public ArrayList<AuthorizationVO> getCorpFocusAuthorizationDetails(String enrollmentId,ArrayList alSortVars) throws TTKException; 
    
    /*
     * Get only the Claims in this procedure
     */
    public ArrayList<ClaimsVO> getCorpFocusClaimsDetails(String enrollmentId,ArrayList alSortVars) throws TTKException; 
    
	
    
    /**
     * DashBoard Details
     */

    public DashBoardVO getDashBoardDetails(String insCompCode) throws TTKException;
    
    
    /**
     * Claim Details - on click on the claim numbers link
     */

    public ClaimDetailsVO getClaimDetails(String clmNumber) throws TTKException;
    
    
    
   /**
    * Auth Details - on click on the Auth numbers link
    */

   public AuthDetailsVO getAuthDetails(String authNumber) throws TTKException;
   
    
   /**
    * GLobal View for corporate 
    */
   public InsGlobalViewVO getCorpGlobalDetails(String insCompCode,String strVar,String CorpOrProdSeqId) throws TTKException;
   
   
   
   
   //RETAIL HERE
   /**
    * sEARCh Retail Products
    */
   public ArrayList<RetailVO> getRetailList(ArrayList alSearchCriteria) throws TTKException;
   
   
   /**
    * sEARCh Retail Member List
    */
   public ArrayList<EnrollMemberVO> getRetailMemberList(ArrayList alSearchCriteria) throws TTKException;
   
   
   
   //PRODUCTS AND POLICIES
   
   /**
    * SEARCH PRODUCT LIST
    */
   public ArrayList<ProductTableVO> getProductsList(ArrayList alSearchCriteria) throws TTKException;
   
   
   /*
    * Get Policy Details on Products
    */

   public ArrayList<PolicyDetailVO> getPolicyDetails(int productSeqId,String insCompCodeWebLogin) throws TTKException;
   
   /*
    * Get The BEnefits of Each Policy for the Product
    */

   public String[] getPolicyBenefitDetails(int productSeqId,String policySeqId,String enrolTypeId,String insCompCodeWebLogin) throws TTKException;
      
   
   /*
    * Get Log Details
    */
   
   public ArrayList getLogData(String strUserId) throws TTKException;
   
   
   /*
	 * Get getEndorsements
	 */

	public ArrayList getEndorsements(String insCompCodeWebLogin,String enrollmentId) throws TTKException;
   
	
	/*
	 * Getting the Corporate List for Insurance login reports
	 */
	public ArrayList<CacheObject> getCorporatesList(String insCompCodeWebLogin) throws TTKException;
	
	

	/*
	 * Getting the Policies List based on the corporate Selectes for Insurance login reports
	 */
	public ArrayList<CacheObject> getPolicyList(String insCompCodeWebLogin,String strCorpSeqId) throws TTKException;

	
	/*
	 * Getting the Policy Period based on the policy for Insurance login reports
	 */
	public ArrayList<CacheObject> getPolicyPeriod(String strPolicySeqId) throws TTKException;

	
	
	/*
	 * Getting the Corporate List for Insurance login reports
	 */
	public ArrayList<CacheObject> getInduPolicyList(String insCompCodeWebLogin) throws TTKException;
	
	

	/*
	 * Getting the getClaimTatData List for Insurance login reports
	 */
	public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException;
	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException;
	public ArrayList<String> getTATForCards(String insCompCodeWebLogin) throws TTKException;
	
	
}//end of InsuranceCorporateManager
