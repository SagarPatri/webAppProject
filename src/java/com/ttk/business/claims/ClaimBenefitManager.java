/**
 * @ (#) ClaimBenefitManager.java Jul 2, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBenefitManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 2, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.business.claims;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;

@Local

/**
 * @author ramakrishna_km
 *
 */
public interface ClaimBenefitManager {
	
	/**
     * This method returns the Arraylist of ClaimBenefitVO's  which contains Claim Benefit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBenefitVO object which contains Claim Benefit details
     * @exception throws TTKException
     */
    public ArrayList<Object> getClaimBenefitList(ArrayList<Object> alSearchCriteria) throws TTKException;
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public int getCreateCashBenefitClaim(ArrayList alParams) throws TTKException;

}//end of ClaimBenefitManager
