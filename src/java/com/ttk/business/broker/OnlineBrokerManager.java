/**
 *   @ (#) BrokerManager.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerManager.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.business.broker;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.brokerlogin.BrokerVO;
import com.ttk.dto.common.CacheObject;

@Local
public interface OnlineBrokerManager {
	 /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public String[] getPolicyDetails(String brokerID) throws TTKException;
    
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<BrokerVO> getLogDetails(String brokerID) throws TTKException;
    
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<Object> getPolicyList(ArrayList<Object> searchList) throws TTKException;
    

    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<Object> getMemberList(ArrayList<Object> searchList) throws TTKException;
    
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,String> getSummaryViewDetails(String brokerCode,String groupType,Long groupSeqID,String summaryMode) throws TTKException;
   
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,Object> getDetailedViewDetails(String enrolmentID,String summaryMode) throws TTKException;
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,Object> getClaimDetails(String claimNO) throws TTKException;
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,Object> getTob(Long policySeqID) throws TTKException;
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,Object> getEndorsements(String broCode,String enrolID) throws TTKException;
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public HashMap<String,Object> getPreauthDetails(String preAuthNO) throws TTKException;
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<CacheObject> getCorporateList(String brokerCode) throws TTKException; 
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<CacheObject> getPolicyNumberList(Long corporateSeqID) throws TTKException; 
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<CacheObject> getPolicyPeriodList(Long policySeqID) throws TTKException; 
    
    /**
     * @param brokerID String object which contains Broker ID
     * @return String[] object which contains all the Policy details
     * @exception throws TTKException
     */
    
    public ArrayList<CacheObject> getINDPolicyNumberList(String brokerCode) throws TTKException; 
    
    public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException;
	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException;
	
}
