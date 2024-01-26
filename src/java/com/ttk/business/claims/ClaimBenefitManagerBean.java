/**
 * @ (#) ClaimBenefitManagerBean.java Jul 2, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBenefitManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.claims.ClaimBenefitDAOImpl;
import com.ttk.dao.impl.claims.ClaimDAOFactory;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

/**
 * @author ramakrishna_km
 *
 */
public class ClaimBenefitManagerBean implements ClaimBenefitManager{
	
	private ClaimDAOFactory claimDAOFactory = null;
	private ClaimBenefitDAOImpl claimBenefitDAO = null;
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getClaimBenefitDAO(String strIdentifier) throws TTKException
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
    }//End of getClaimBenefitDAO(String strIdentifier)
	
	/**
     * This method returns the Arraylist of ClaimBenefitVO's  which contains Claim Benefit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBenefitVO object which contains Claim Benefit details
     * @exception throws TTKException
     */
    public ArrayList<Object> getClaimBenefitList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	claimBenefitDAO = (ClaimBenefitDAOImpl)this.getClaimBenefitDAO("cashbenefit");
    	return claimBenefitDAO.getClaimBenefitList(alSearchCriteria);
    }//end of getClaimBenefitList(ArrayList alSearchCriteria)
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public int getCreateCashBenefitClaim(ArrayList alParams) throws TTKException{
    	claimBenefitDAO = (ClaimBenefitDAOImpl)this.getClaimBenefitDAO("cashbenefit");
    	return claimBenefitDAO.getCreateCashBenefitClaim(alParams);
    }//end of getCreateCashBenefitClaim(Long lngPrvClaimsNbr, Long lngAddedBy)

}//end of ClaimBenefitManagerBean
