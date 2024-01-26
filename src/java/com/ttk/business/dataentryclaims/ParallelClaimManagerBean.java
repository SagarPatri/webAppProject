/**
 * @ (#) ClaimManagerBean.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.dataentryclaims;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.dataentryclaims.ClaimDAOFactory;
import com.ttk.dao.impl.dataentryclaims.ClaimDAOImpl;
import com.ttk.dao.impl.dataentrypreauth.PreAuthDAOImpl;
import com.ttk.dto.claims.ClaimInwardDetailVO;
import com.ttk.dto.claims.ClauseVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.PreAuthDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ParallelClaimManagerBean implements ParallelClaimManager {

	private ClaimDAOFactory claimDAOFactory = null;
	private ClaimDAOImpl claimDAO = null;

	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getClaimDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (claimDAOFactory == null)
            	claimDAOFactory = new ClaimDAOFactory();
            if(strIdentifier!=null)
            {
               return claimDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else{
            	return null;
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getClaimDAO(String strIdentifier)

	/**
     * This method returns the Arraylist of ClaimInwardVO's  which contains Claim Inward Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimInwardVO object which contains Claim Inward details
     * @exception throws TTKException
     */
    public ArrayList getClaimInwardList(ArrayList alSearchCriteria) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimInwardList(alSearchCriteria);
    }//end of getClaimInwardList(ArrayList alSearchCriteria)

    /**
     * This method returns the ClaimInwardDetailVO, which contains all the Claim Inward details
     * @param lngClaimInwardSeqID long value contains seq id to get the Claim Inward Details
     * @param strClaimTypeID contains Claim Type
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ClaimInwardDetailVO object which contains all the Claim Inward details
     * @exception throws TTKException
     */
    public ClaimInwardDetailVO getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimInwardDetail(lngClaimInwardSeqID,strClaimTypeID,lngUserSeqID);
    }//end of getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID)

    /**
     * This method returns the Arraylist of ClaimantVO's which contains Claim Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getClaimShortfallList(ArrayList alSearchCriteria) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimShortfallList(alSearchCriteria);
    }//end of getClaimShortfallList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of ShortfallVO's which contains Claim Shortfall Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ShortfallVO object which contains Claim Shortfall details
     * @exception throws TTKException
     */
    public ArrayList getInwardShortfallDetail(ArrayList alSearchCriteria) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getInwardShortfallDetail(alSearchCriteria);
    }//end of getInwardShortfallDetail(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of DocumentChecklistVO's  which contains Claim Document Details
     * @param strClaimTypeID contains Claim Type ID
     * @return ArrayList of DocumentChecklistVO object which contains Claim Inward Document details
     * @exception throws TTKException
     */
    public ArrayList getClaimDocumentList(String strClaimTypeID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimDocumentList(strClaimTypeID);
    }//end of getClaimDocumentList(String strClaimTypeID)

    /**
	 * This method saves the Claim Inward information
	 * @param claimInwardDetailVO the object which contains the Claim Inward Details which has to be saved
	 * @return long the value contains Claim Inward Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO) throws TTKException {
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.saveClaimInwardDetail(claimInwardDetailVO);
	}//end of saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO)

	/**
     * This method returns the Arraylist of PreAuthVO's  which contains Claim Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimList(alSearchCriteria);
    }//end of getClaimList(ArrayList alSearchCriteria)

    /**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getClaimDetail(ArrayList alClaimList) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClaimDetail(alClaimList);
    }//end of getClaimDetail(ArrayList alClaimList)

    /**
     * This method releases the Preauth Associated to NHCPClaim
     * @param lngPATEnrollDtlSeqID PAT Enroll Dtl Seq ID
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int releasePreauth(long lngPATEnrollDtlSeqID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.releasePreauth(lngPATEnrollDtlSeqID);
    }//end of releasePreauth(long lngPATEnrollDtlSeqID)

    /**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimDetail(PreAuthDetailVO preauthDetailVO) throws TTKException {
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.saveClaimDetail(preauthDetailVO);
	}//end of saveClaimDetail(PreAuthDetailVO preauthDetailVO)

	/**
     * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
     * @exception throws TTKException
     */
    public ArrayList getPreauthList(ArrayList alSearchCriteria) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getPreauthList(alSearchCriteria);
    }//end of getPreauthList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of Cache Object's which contains Previous Claim Seq ID's
     * @param lngMemberSeqID Member Seq ID
     * @return ArrayList of Cache Object's Previous Claim Seq ID's
     * @exception throws TTKException
     */
    public ArrayList getPrevClaim(long lngMemberSeqID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getPrevClaim(lngMemberSeqID);
    }//end of getPrevClaim(long lngMemberSeqID)

    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.saveReview(preauthDetailVO,strMode,strType);
    }//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

    
    //added for CR KOC-Decoupling
    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public int saveDataEntryReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.saveDataEntryReview(preauthDetailVO,strMode,strType);
    }//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

    
    //ended
    /**added for CR KOC-Decoupling
	 * This method will save the Review Information and allows you to modify the detail and save
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    
    public int revertProcess(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException
    {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.revertProcess(preauthDetailVO,strMode);
    	
    }
    
    
    /**
     * This method returns the HashMap contains ArrayList of HospitalizationVO's which contains Previous Hospitalization Details
     * @param lngMemberSeqID long value contains Member Seq ID
     * @param strMode contains CTM - > MR / CNH -> NHCP
     * @param lngClaimSeqID long value contains Claim Seq ID
     * @return ArrayList of CacheVO object which contains Previous Hospitalization details
     * @exception throws TTKException
     */
    public HashMap getPrevHospList(long lngMemberSeqID,String strMode,long lngClaimSeqID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getPrevHospList(lngMemberSeqID,strMode,lngClaimSeqID);
    }//end of getPrevHospList(long lngMemberSeqID,String strMode,long lngClaimSeqID)

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteClaimGeneral(ArrayList alDeleteList) throws TTKException {
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.deleteClaimGeneral(alDeleteList);
	}//end of deleteClaimGeneral(ArrayList alDeleteList)

	/**
     * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
     * @param alAssignUserList ArrayList Object contains ClaimSeqID,PolicySeqID and TTKBranch
     * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
     * @exception throws TTKException
     */
    public ArrayList getAssignUserList(ArrayList alAssignUserList) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getAssignUserList(alAssignUserList);
    }//end of getAssignUserList(ArrayList alAssignUserList)

    /**
	 * This method will allow to Override the Claim information
	 * @param lngClaimSeqID ClaimSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO overrideClaim(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.overrideClaim(lngClaimSeqID,lngUserSeqID);
    }//end of overrideClaim(long lngClaimSeqID,long lngUserSeqID)

    /**
     * This method returns the ClauseVO, which contains all the Clause details
     * @param alClauseList contains Claim seq id,Policy Seq ID,EnrolTypeID,AdmissionDate to get the Clause Details
     * @return ClauseVO object which contains all the Clause details
     * @exception throws TTKException
     */
    public ClauseVO getClauseDetail(ArrayList alClauseList) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getClauseDetail(alClauseList);
    }//end of getClauseDetail(ArrayList alClauseList)

    /**
	 * This method saves the Clause Details
	 * @param alClauseList the arraylist which contains the Clause Details which has to be saved
	 * @return String value which contains clauses description
	 * @exception throws TTKException
	 */
	public String saveClauseDetail(ArrayList alClauseList) throws TTKException {
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.saveClauseDetail(alClauseList);
	}//end of saveClauseDetail(ArrayList alClauseList)
	
	/**
     * This method returns the ArrayList object, which contains list of Letters to be sent for Claims Rejection
     * @param alLetterList ArrayList Object contains ClaimTypeID and Ins Seq ID
     * @return ArrayList object which contains list of Letters to be sent for Claims Rejection
     * @exception throws TTKException
     */
    public ArrayList getRejectionLetterList(ArrayList alLetterList) throws TTKException {
    	claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
    	return claimDAO.getRejectionLetterList(alLetterList);
    }//end of getRejectionLetterList(ArrayList alLetterList)
    
    /**
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException
	{
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.reAssignEnrID(alReAssignEnrID);
	}//end of reAssignEnrID(ArrayList alReAssignEnrID)
	
	 /**
	 * This method gets the service tax  amount
	 * @param AuthorizationVO It contains all the authorization details
	 * @return Object[] the values,of Service tax amt to be paid,Fianl approved amount and service tax percentage
	 * @exception throws TTKException
	 */
	public Object[] getServTaxCal(AuthorizationVO authorizationVO) throws TTKException{
		claimDAO = (ClaimDAOImpl)this.getClaimDAO("claim");
		return claimDAO.getServTaxCal(authorizationVO);
	}//getServTaxCal(AuthorizationVO authorizationVO)
}//end of ClaimManagerBean
