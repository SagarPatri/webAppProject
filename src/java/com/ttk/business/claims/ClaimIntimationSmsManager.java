/**
 * @ (#) ClaimManager.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.claims;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;


@Local

public interface ClaimIntimationSmsManager {
	
	/**
     * This method returns the ArrayList object, which contains list of Letters to be sent for Claims Rejection
     * @param alLetterList ArrayList Object contains ClaimTypeID and Ins Seq ID
     * @return ArrayList object which contains list of Letters to be sent for Claims Rejection
     * @exception throws TTKException
     */

	
	public void saveClaimIntimationSmsDetail(ArrayList intimationDetails) throws TTKException;

	/**
     * This method returns the ArrayList object, which contains list of values to be sent for Claims Approve/Rejection/RequiredInformation
     * @param alLetterList ArrayList Object contains Claim Number,Insurance approved Status,ins Remarks 
     * @exception throws TTKException
     */

	public void saveInsuranceStatusFromAdobe(ArrayList alClmInsuranceDetails) throws TTKException;
	/**
     * This method returns the ArrayList object, which contains list of values to be sent for Pre-Auth  Approve/Rejection/RequiredInformation
     * @param alLetterList ArrayList Object contains Claim Number,Insurance approved Status,ins Remarks 
     * @exception throws TTKException
     */
	
	public void savePatInsuranceStatusFromAdobe(ArrayList alPatInsuranceDetails) throws TTKException;
  
   	
	
   
   

}//end of ClaimManager
