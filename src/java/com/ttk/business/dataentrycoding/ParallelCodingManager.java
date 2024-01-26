/**
 * @ (#) PreAuthCodingManager.java Aug 27, 2007
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthCodingManager.java
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
import javax.ejb.Local;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthDetailVO;

@Local
public interface ParallelCodingManager {
	
	/**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
	public ArrayList getPreAuthList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthCodeCleanList(ArrayList alSearchCriteria) throws TTKException; 
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException;
    
    /**
	 * This method returns the Arraylist of PreAuthVO's  which contains the details of Claim details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains the details of Claim
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException;
	
	/**added for KOC-Decoupling
	 * This method returns the ArrayList of Diagnosis which contains diagnosis description
	 * @param claimSeqId which contains the sequence id of related diagnosis
	 * return ArrayList of Diagnosis which contains the details of Diagnosis at Ailment screen
	 * @exception throws TTKException
	 *  
	 */
	public ArrayList getDiagnosisList(long claimSeqId) throws TTKException;
	
	/**
     * This method returns the Arraylist of PreAuthVO's  which contains Claim details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getClaimCodeCleanList(ArrayList alSearchCriteria) throws TTKException; 
	
	/**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException; 
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException; 
    
    /**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getAilmentList(ArrayList alAilmentList) throws TTKException;
	
	/**
	 * Saves Promote Status in the coding module
	 * 
	 * 
	 */
	public long saveDataEntryPromote(long lngClaimsSeqId,long userSeqId) throws TTKException;
	
	
	/**
	 * Saves Promote Status in the coding module
	 * 
	 * 
	 */
	public long saveDataEntryRevert(long lngClaimsSeqId,long userSeqId) throws TTKException;
	
	
	/**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getCodeCleanupAilmentList(ArrayList alAilmentList) throws TTKException;
	
	/**
     * This method gets the ICD Code information
     * @param strICDCode String object which contains ICD Details
     * @return Object[] the values ,of PEDCodeId,PED Description
     * @exception throws TTKException
     */
	public Object[] getExactICD(String strICDCode) throws TTKException;
	
	/**
     * This method gets the Procedure information
     * @param strProcCode String object which contains Procedure Code
     * @return Object[] the values ,of Procedure Seq ID,Procedure Description
     * @exception throws TTKException
     */
	public Object[] getExactProcedure(String strProcCode) throws TTKException;
	
	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO savePreauthReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException;
    
    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveClaimReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException;
    
    /**
	 * This method saves the ICD Package information
	 * @param icdPCSVO ICDPCSVO contains ICD Package information
	 * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
	 * @return long value which contains ICDPCS Seq ID
	 * @exception throws TTKException
	 */
	public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException;
	
	/**
     * This method Deletes the ICD details entered in the CodeCleanup flow.
     * @param alICDList ArrayList object which contains the icdpcs sequence id's, to delete icd records
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCleanupICD(ArrayList alICDList) throws TTKException;
    
    /**
	 * This method saves the PED details
	 * @param pedVO the object which contains the details of the PED
	 * @param lngSeqID long value in Preauthorization flow, Webboard Seq ID as PreauthSeqID and in Claims Flow ClaimSeqID
	 * @param strMode Object which contains Mode - Preauth/Claims flow PAT/CLM
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCodingPED(PEDVO pedVO,long lngSeqID,String strMode) throws TTKException;
	
}//end of CodingManager
