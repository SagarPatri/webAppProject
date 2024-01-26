/** 
 *  @ (#) MemberHistoryManager.java April 10, 2006
 *   Project       : TTK HealthCare Services
 *   File          : MemberHistoryManager.java
 *   Author        : RamaKrishna K M
 *   Company       : Span Systems Corporation
 *   Date Created  : April 10, 2006
 *   
 *   @author       :  RamaKrishna K M
 *   Modified by   :  
 *   Modified date :  
 *   Reason        : 
 */

package com.ttk.business.preauth;
import java.util.ArrayList;
import javax.ejb.Local;
import org.dom4j.Document;
import com.ttk.common.exception.TTKException;
@Local

public interface MemberHistoryManager {
    
    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getPolicyHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId) throws TTKException ;
    public Document getPolicyHistory(Long lngSeqId) throws TTKException ;
    
    
   /* *//**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     *//*
    public ArrayList getHospitalPreAuthHistoryList(ArrayList alSearchCriteria) throws TTKException;
    
    
    
    *//**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     *//*
    public ArrayList getHospitalClaimHistoryList(ArrayList alSearchCriteria) throws TTKException;*/
    
    public Document getHospitalHistory(String strHistoryType, Long lngSeqId, Long lngHospSeqID) throws TTKException ;
    
}//end of MemberHistoryManager