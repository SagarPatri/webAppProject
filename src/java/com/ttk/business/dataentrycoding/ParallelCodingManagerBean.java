/**
 * @ (#) PreAuthCodingManagerBean.java Aug 27, 2007
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthCodingManagerBean.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 27, 2007
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *                  
 */

package com.ttk.business.dataentrycoding;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.dataentrycoding.CodingDAOFactory;
import com.ttk.dao.impl.dataentrycoding.CodingDAOImpl;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ParallelCodingManagerBean implements ParallelCodingManager {
	
	
	private CodingDAOFactory codingDAOFactory = null;
    private CodingDAOImpl codingDAO = null;
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getCodingDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (codingDAOFactory == null)
            	codingDAOFactory = new CodingDAOFactory();
            if(strIdentifier!=null)
            {
                return codingDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getPreAuthDAO(String strIdentifier)
    
    
	 /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthList(ArrayList alSearchCriteria) throws TTKException{
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getPreAuthList(alSearchCriteria);        
    }//end of getPreAuthList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthCodeCleanList(ArrayList alSearchCriteria) throws TTKException{
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getPreAuthCodeCleanList(alSearchCriteria);
    }//end of getPreAuthCodeCleanList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    
    public PreAuthDetailVO getPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException{
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getPreAuthDetail(lngPATEnrollDtlSeqID,lngUserSeqID);        
    }//end of getPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID)
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getCodeCleanupPreAuthDetail(lngPATEnrollDtlSeqID,lngUserSeqID); 
    }//end of getCodeCleanupPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID)
    
    /**
	 * This method returns the Arraylist of PreAuthVO's  which contains the details of Claim details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains the details of Claim
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException{
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getClaimList(alSearchCriteria); 		
	}//end of getClaimList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the ArrayList of Diagnosis which contains diagnosis description
	 * @param claimSeqId which contains the sequence id of related diagnosis
	 * return ArrayList of Diagnosis which contains the details of Diagnosis at Ailment screen
	 * @exception throws TTKException
	 *  
	 */
	public ArrayList getDiagnosisList(long claimSeqId) throws TTKException
	{
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.getDiagnosisList(claimSeqId);
	}	
	/**
     * This method returns the Arraylist of PreAuthVO's  which contains Claim details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getClaimCodeCleanList(ArrayList alSearchCriteria) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
    	return codingDAO.getClaimCodeCleanList(alSearchCriteria);
    }//end of getClaimCodeCleanList(ArrayList alSearchCriteria)
	
	/**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException{
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getClaimDetail(lngClaimSeqID,lngUserSeqID); 		
    }//end of getClaimDetail(long lngClaimSeqID,long lngUserSeqID)
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
        return codingDAO.getCodeCleanupClaimDetail(lngClaimSeqID,lngUserSeqID); 
    }//end of getCodeCleanupClaimDetail(long lngClaimSeqID,long lngUserSeqID)
    
    /**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getAilmentList(ArrayList alAilmentList) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.getAilmentList(alAilmentList);
	}//end of getAilmentList(ArrayList alAilmentList)
	
	/*
	 * 
	 * Saves Promote Status Information for Coding
	 */
	public long saveDataEntryPromote(long lngClaimsSeqId,long userSeqId)throws TTKException
	{
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.saveDataEntryPromote(lngClaimsSeqId,userSeqId);
	}
	
	/*
	 * Saves Revert Status Information for Coding 
	 * 
	 */
	public long saveDataEntryRevert(long lngClaimsSeqId, long userSeqId)throws TTKException
	{
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.saveDataEntryRevert(lngClaimsSeqId,userSeqId);
	}
	
	
	/**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getCodeCleanupAilmentList(ArrayList alAilmentList) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.getCodeCleanupAilmentList(alAilmentList);
	}//end of getCodeCleanupAilmentList(ArrayList alAilmentList)
	
	/**
     * This method gets the ICD Code information
     * @param strICDCode String object which contains ICD Details
     * @return Object[] the values ,of PEDCodeId,PED Description
     * @exception throws TTKException
     */
	public Object[] getExactICD(String strICDCode) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.getExactICD(strICDCode);
	}//end of getExactICD(String strICDCode)
	
	/**
     * This method gets the Procedure information
     * @param strProcCode String object which contains Procedure Code
     * @return Object[] the values ,of Procedure Seq ID,Procedure Description
     * @exception throws TTKException
     */
	public Object[] getExactProcedure(String strProcCode) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.getExactProcedure(strProcCode);
	}//end of getExactProcedure(String strProcCode)
	
	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO savePreauthReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
    	return codingDAO.savePreauthReview(preauthDetailVO,strMode);
    }//end of savePreauthReview(PreAuthDetailVO preauthDetailVO,String strMode)
    
    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveClaimReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
    	return codingDAO.saveClaimReview(preauthDetailVO,strMode);
    }//end of saveClaimReview(PreAuthDetailVO preauthDetailVO,String strMode)
    
    /**
	 * This method saves the ICD Package information
	 * @param icdPCSVO ICDPCSVO contains ICD Package information
	 * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
	 * @return long value which contains ICDPCS Seq ID
	 * @exception throws TTKException
	 */
	public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.saveICDPackage(icdPCSVO,strMode);
	}//end of saveICDPackage(ICDPCSVO icdPCSVO,String strMode)
	
	/**
     * This method Deletes the ICD details entered in the CodeCleanup flow.
     * @param alICDList ArrayList object which contains the icdpcs sequence id's, to delete icd records
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCleanupICD(ArrayList alICDList) throws TTKException {
    	codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
    	return codingDAO.deleteCleanupICD(alICDList);
    }//end of deleteCleanupICD(ArrayList alICDList)
    
    /**
	 * This method saves the PED details
	 * @param pedVO the object which contains the details of the PED
	 * @param lngSeqID long value in Preauthorization flow, Webboard Seq ID as PreauthSeqID and in Claims Flow ClaimSeqID
	 * @param strMode Object which contains Mode - Preauth/Claims flow PAT/CLM
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCodingPED(PEDVO pedVO,long lngSeqID,String strMode) throws TTKException {
		codingDAO = (CodingDAOImpl)this.getCodingDAO("coding");
		return codingDAO.saveCodingPED(pedVO,lngSeqID,strMode);
	}//end of saveCodingPED(PEDVO pedVO,long lngSeqID,String strMode)
}//end of CodingManagerBean
