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

package com.ttk.business.claims;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.claims.ClaimDAOFactory;
import com.ttk.dao.impl.claims.ClaimIntimationSmsDAOImpl;


@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ClaimIntimationSmsManagerBean implements ClaimIntimationSmsManager {

	private ClaimDAOFactory claimDAOFactory = null;
	private ClaimIntimationSmsDAOImpl claimIntimaionSmsDAO = null;

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
	 * This method saves the Clause Details
	 * @param alClauseList the arraylist which contains the Clause Details which has to be saved
	 * @return String value which contains clauses description
	 * @exception throws TTKException
	 */
	public void saveClaimIntimationSmsDetail(ArrayList intimationDetails) throws TTKException {
		claimIntimaionSmsDAO = (ClaimIntimationSmsDAOImpl)this.getClaimDAO("claimintimation");
		 claimIntimaionSmsDAO.saveClaimIntimationSmsDetail(intimationDetails);
	}//end of saveClauseDetail(ArrayList alClauseList)
	
	/**
     * This method returns the ArrayList object, which contains list of values to be sent for Claims Approve/Rejection/RequiredInformation
     * @param alLetterList ArrayList Object contains Claim Number,Insurance approved Status,ins Remarks 
     * @exception throws TTKException
     */
	public void saveInsuranceStatusFromAdobe(ArrayList alClmInsuranceDetails) throws TTKException{
		claimIntimaionSmsDAO = (ClaimIntimationSmsDAOImpl)this.getClaimDAO("claimintimation");
		 claimIntimaionSmsDAO.saveInsuranceStatusFromAdobe(alClmInsuranceDetails);
	}//end of saveInsuranceStatusFromAdobe(ArrayList alClauseList)
		
	
	/**
     * This method returns the ArrayList object, which contains list of values to be sent for PreAuth Approve/Rejection/RequiredInformation
     * @param alLetterList ArrayList Object contains Claim Number,Insurance approved Status,ins Remarks 
     * @exception throws TTKException
     */
		public void savePatInsuranceStatusFromAdobe(ArrayList alPatInsuranceDetails) throws TTKException{
			claimIntimaionSmsDAO = (ClaimIntimationSmsDAOImpl)this.getClaimDAO("claimintimation");
			 claimIntimaionSmsDAO.savePatInsuranceStatusFromAdobe(alPatInsuranceDetails);
		}//end of saveInsuranceStatusFromAdobe(ArrayList alClauseList)

		
	
}//end of ClaimManagerBean
