/**
 * @ (#) ProdPolicyConfigManagerBean.java Jun 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ProdPolicyConfigManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 26, 2008
 *
 * @author       :  RamaKrishna K M
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
import com.ttk.dao.impl.administration.ProdPolicyConfigDAOImpl;
import com.ttk.dto.administration.AuthGroupVO;
import com.ttk.dto.administration.ClauseDocumentVO;
import com.ttk.dto.administration.PlanDetailVO;
import com.ttk.dto.administration.CashbenefitVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

/**
 * @author ramakrishna_km
 *
 */
public class ProdPolicyConfigManagerBean implements ProdPolicyConfigManager{
	
	private AdministrationDAOFactory administrationDAOFactory = null;
	private ProdPolicyConfigDAOImpl prodPolicyConfigDAO = null;
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getProdPolicyConfigDAO(String strIdentifier) throws TTKException
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
     * This method returns the ArrayList, which contains the PlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of PlanVO'S object's which contains the details of the Plan Information
     * @exception throws TTKException
     */
	public ArrayList<Object> getPlanList(ArrayList<Object> alSearchObjects) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        return prodPolicyConfigDAO.getPlanList(alSearchObjects);
	}//end of getPlanList(ArrayList<Object> alSearchObjects)
	
	/**
     * This method returns the PlanDetailVO, which contains all the Plan details
     * @param lngProdPlanSeqID the productplanSequenceID for which the Plan Details has to be fetched
     * @return PlanDetailVO object which contains all the Plan details
     * @exception throws TTKException
     */
	  //KOC 1270 hospital cash benefit
	
	public ArrayList<Object> getCashBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        return prodPolicyConfigDAO.getCashBenefitList(alSearchObjects);
	}//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
	
	public ArrayList<Object> getCanvalescenceBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        return prodPolicyConfigDAO.getCanvalescenceBenefitList(alSearchObjects);
	}//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
	
	/**
     * This method returns the CashbenefitVO, which contains all the Plan details
     * @param lngProdPlanSeqID the productplanSequenceID for which the Plan Details has to be fetched
     * @return PlanDetailVO object which contains all the Plan details
     * @exception throws TTKException
     */
    public CashbenefitVO getCashBenefitDetail(long lnginsCashBenefitSeqID,String strconvBenefit ) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getCashBenefitDetail(lnginsCashBenefitSeqID,strconvBenefit);
    }//end of getCashBenefitDetail(long lngProdPlanSeqID)
    
    public CashbenefitVO getCanvalescenceBenefitDetail(long lnginsConvBenfSeqID,String strconv ) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getCanvalescenceBenefitDetail(lnginsConvBenfSeqID,strconv);
    }//end of getCashBenefitDetail(long lngProdPlanSeqID)
    
    
    /**
     * This method saves the CashBenefit information
     * @param CashbenefitVO the object which contains the Plan Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCashBenefitDetail(CashbenefitVO CashbenefitVO) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.saveCashBenefitDetail(CashbenefitVO);
    }//end of savePlanDetail(CashbenefitVO CashbenefitVO)
    
   
    public int saveCanvalescenceBenefitDetail(CashbenefitVO CashbenefitVO) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.saveCanvalescenceBenefitDetail(CashbenefitVO);
    }//end of savePlanDetail(CashbenefitVO CashbenefitVO)
    
    //KOC 1270 hospital cash benefit
    public PlanDetailVO getPlanDetail(long lngProdPlanSeqID) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getPlanDetail(lngProdPlanSeqID);
    }//end of getPlanDetail(long lngProdPlanSeqID)
    
    /**
     * This method saves the Plan information
     * @param policyDetailVO the object which contains the Plan Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int savePlanDetail(PlanDetailVO planDetailVO) throws TTKException{
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.savePlanDetail(planDetailVO);
    }//end of savePlanDetail(PlanDetailVO planDetailVO)
//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
    public ArrayList<Object> getCriticalBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        return prodPolicyConfigDAO.getCriticalBenefitList(alSearchObjects);
    }//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
        
    public CashbenefitVO getCriticalBenefitDetail(long lnginsCriticalBenefitSeqID, String strcriticalBenefit) throws TTKException{
        	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        	return prodPolicyConfigDAO.getCriticalBenefitDetail(lnginsCriticalBenefitSeqID,strcriticalBenefit);
        }//end of getCashBenefitDetail(long lngProdPlanSeqID)
        
    public int saveCriticalBenefitDetail(CashbenefitVO cashbenefitVO) throws TTKException{
        	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
        	return prodPolicyConfigDAO.saveCriticalBenefitDetail(cashbenefitVO);
        }//end of savePlanDetail(CashbenefitVO CashbenefitVO)
	
    //KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
    
    /**
     * This method returns the ArrayList, which contains the AuthGroupVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of AuthGroupVO'S object's which contains the details of the Auth Group Information
     * @exception throws TTKException
     */
    public ArrayList<Object> getAuthGroupList(ArrayList<Object> alSearchObjects) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getAuthGroupList(alSearchObjects);
    }//end of getAuthGroupList(ArrayList<Object> alSearchObjects)
    
    /**
     * This method returns the AuthGroupVO, which contains Auth Group details
     * @param lngAuthGrpSeqID the Auth Group Seq ID for which the Auth Group Details has to be fetched
     * @return AuthGroupVO object which contains Auth Group details
     * @exception throws TTKException
     */
    public AuthGroupVO getAuthGroupDetail(long lngAuthGrpSeqID) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getAuthGroupDetail(lngAuthGrpSeqID);
    }//end of getAuthGroupDetail(long lngAuthGrpSeqID)
    
    /**
     * This method saves the Auth Group information
     * @param authGroupVO the object which contains the Auth Group Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAuthGroup(AuthGroupVO authGroupVO) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.saveAuthGroup(authGroupVO);
    }//end of saveAuthGroup(AuthGroupVO authGroupVO)
    
    /**
     * This method returns the Arraylist of Cache object which contains Group details for corresponding Product
     * @param lngProdPolicySeqID long value which contains Product Policy Seq ID
     * @return ArrayList of Cache object which contains Group details for corresponding Product
     * @exception throws TTKException
     */
    public ArrayList<Object> getGroupList(long lngProdPolicySeqID) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getGroupList(lngProdPolicySeqID);
    }//end of getGroupList(long lngProdPolicySeqID)
    
    /**
	 * This method returns the ArrayList, which contains Accounthead Information associated to Product
	 * @param lngProdPolicySeqID contains the ProdPolicySeqID
	 * @return ArrayList object which contains Accounthead Information associated to Product
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getAuthAcctheadList(long lngProdPolicySeqID) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
		return prodPolicyConfigDAO.getAuthAcctheadList(lngProdPolicySeqID);
	}//end of getAuthAcctheadList(long lngProdPolicySeqID)
	
	/**
	 * This method saves the Accounthead Information associated to Product
	 * @param alAcctheadInfo contains concatenated string of |wardtypeid|includeacctheadyn|authgrpseqid|showacctheadyn|..| 
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedAcctheadInfo(ArrayList<Object> alAcctheadInfo) throws TTKException {
		prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
		return prodPolicyConfigDAO.saveSelectedAcctheadInfo(alAcctheadInfo);
	}//end of saveSelectedAcctheadInfo(ArrayList<Object> alAcctheadInfo)
	
	/**
     * This method returns the ArrayList, which contains the ClauseDocumentVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ClauseDocumentVO'S object's which contains the details of the Clause Document Information
     * @exception throws TTKException
     */
    public ArrayList<Object> getClauseDocList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getClauseDocList(alSearchCriteria);
    }//end of getClauseDocList(ArrayList<Object> alSearchCriteria)
    
    /**
	 * This method returns the ClauseDocumentVO, which contains Clause Document details
	 * @param lngClauseDocSeqID Clause Doc Seq ID
	 * @return ClauseDocumentVO object which contains Clause Document details
	 * @exception throws TTKException
	 */
    public ClauseDocumentVO getClauseDocInfo(long lngClauseDocSeqID) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.getClauseDocInfo(lngClauseDocSeqID);
    }//end of getClauseDocInfo(long lngClauseDocSeqID)
    
    /**
     * This method saves the Clause Document details
     * @param clauseDocumentVO ClauseDocumentVO object which contains the Clause Document Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveClauseDocInfo(ClauseDocumentVO clauseDocumentVO) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.saveClauseDocInfo(clauseDocumentVO);
    }//end of saveClauseDocInfo(ClauseDocumentVO clauseDocumentVO)
    
    /**
     * This method deletes the Clause Document details from the database
     * @param alDeleteList which contains the id's of the Clause Document
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteClauseDocInfo(ArrayList<Object> alDeleteList) throws TTKException {
    	prodPolicyConfigDAO = (ProdPolicyConfigDAOImpl)this.getProdPolicyConfigDAO("prodpolicyconfig");
    	return prodPolicyConfigDAO.deleteClauseDocInfo(alDeleteList);
    }//end of deleteClauseDocInfo(ArrayList<Object> alDeleteList)

}//end of ProdPolicyConfigManagerBean
