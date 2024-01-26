/**
 * @ (#) ClaimBatchManagerBean.java July 7, 2015
 * Project 	     : ProjectX
 * File          : ClaimBatchManagerBean.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
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
import com.ttk.dao.impl.claims.ClaimBatchDAOImpl;
import com.ttk.dao.impl.claims.ClaimBenefitDAOImpl;
import com.ttk.dao.impl.claims.ClaimDAOFactory;
import com.ttk.dto.claims.BatchVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

/**
 * @author Nagababu K
 *
 */
public class ClaimBatchManagerBean implements ClaimBatchManager{
	
	private ClaimDAOFactory claimDAOFactory = null;
	private ClaimBatchDAOImpl claimBatchDAO = null;
	
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getClaimBatchDAO(String strIdentifier) throws TTKException
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
    public ArrayList<Object> getClaimBatchList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.getClaimBatchList(alSearchCriteria);
    }//end of getClaimBenefitList(ArrayList alSearchCriteria)
   
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public Object[] getClaimBatchDetails(long batchSeqID) throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.getClaimBatchDetails(batchSeqID);
    }//end of getClaimBatchDetails(Long batchSeqID)
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param batchVO BatchVO object which contains the batch details
     * @return Long batchSeqID Long object which contains Batch SeqID
     * @exception throws TTKException
     */
    public long saveClaimBatchDetails(BatchVO batchVO) throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.saveClaimBatchDetails(batchVO);
    }//end of getCreateCashBenefitClaim(BatchVO batchVO)
    
    public long deleteInvoiceNO(String batchSeqID,String claimSeqID) throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.deleteInvoiceNO(batchSeqID,claimSeqID);
    }//end of deleteInvoiceNO(String batchSeqID,String claimSeqID)
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param batchVO BatchVO object which contains the batch details
     * @return Long batchSeqID Long object which contains Batch SeqID
     * @exception throws TTKException
     */
    public long addClaimDetails(BatchVO batchVO) throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.addClaimDetails(batchVO);
    }//end of addClaimDetails(BatchVO batchVO)
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param batchVO BatchVO object which contains the batch details
     * @return Long batchSeqID Long object which contains Batch SeqID
     * @exception throws TTKException
     */
    public BatchVO getClaimAmountDetails(String cliamSeqID) throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
    	return claimBatchDAO.getClaimAmountDetails(cliamSeqID);
    }//end of getClaimAmountDetails(String cliamSeqID)
    
    
    public HashMap<String,String> getPartnersList() throws TTKException{
    	claimBatchDAO = (ClaimBatchDAOImpl)this.getClaimBatchDAO("batch");
        return claimBatchDAO.getPartnersList();
    }//end of getPartnersList()   
    
    
}//end of ClaimBenefitManagerBean

