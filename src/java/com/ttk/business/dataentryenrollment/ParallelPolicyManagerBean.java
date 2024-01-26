/**
 * @ (#) PolicyManagerBean.java Jan 31, 2006
 * Project       : TTK HealthCare Services
 * File          : PolicyManagerBean.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jan 31, 2006
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.dataentryenrollment;

import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;
import org.dom4j.Document;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.enrollment.BatchDAOImpl;
import com.ttk.dao.impl.dataentryenrollment.EnrollmentDAOFactory;
import com.ttk.dao.impl.dataentryenrollment.PolicyDAOImpl;
import com.ttk.dto.enrollment.BatchVO;
import com.ttk.dto.enrollment.EndorsementVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)
public class ParallelPolicyManagerBean implements ParallelPolicyManager{

	private EnrollmentDAOFactory enrollmentDAOFactory = null;
	private BatchDAOImpl batchDAO = null;
	private PolicyDAOImpl policyDAO = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getEnrollmentDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (enrollmentDAOFactory == null)
				enrollmentDAOFactory = new EnrollmentDAOFactory();
			if(strIdentifier!=null)
			{
				return enrollmentDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getEnrollmentDAO(String strIdentifier)

	/**
	 * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVO object which contains enrollment policy details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.getPolicyList(alSearchCriteria);
	}//end of getPolicyList(ArrayList alSearchCriteria)

	/**
     * This method returns the Arraylist of ClaimantVO  which contains Policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Policy details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthPolicyList(ArrayList alSearchCriteria) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.getPreAuthPolicyList(alSearchCriteria);
    }//end of getPreAuthPolicyList(ArrayList alSearchCriteria)

	/**
     * This method returns the PolicyDetailVO, which contains all the policy details
     * @param alPolicy ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
     * @return PolicyDetailVO object which contains all the policy details
     * @exception throws TTKException
     */
    public PolicyDetailVO getPolicy(ArrayList alPolicy) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.getPolicy(alPolicy);
	}//end of getPolicy(ArrayList alPolicy)

    /**
     * This method returns the ArrayList object, which contains all the details about the Products for the corresponding Insurance Company
     * @param lngInsSeqID long Insurance Seq ID
     * @param strPolicyType String contains Policy Type
     * @return ArrayList object which contains all the details about the Products for the corresponding Insurance Company
     * @exception throws TTKException
     */
    public ArrayList getProductList(long lngInsSeqID,String strPolicyType) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.getProductList(lngInsSeqID,strPolicyType);
    }//end of getProductList(long lngInsSeqID,String strPolicyType)

    /**
     * This method returns the ArrayList object, which contains all the details about the Products for the corresponding Insurance Company
     * @param lngInsSeqID long Insurance Seq ID
     * @return ArrayList object which contains all the details about the Products for the corresponding Insurance Company
     * @exception throws TTKException
     */
    public HashMap getProductInfo(long lngInsSeqID) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.getProductInfo(lngInsSeqID);
    }//end of getProductInfo(long lngInsSeqID)
    
    /**
     * This method returns the ArrayList object, which contains all the details about the Products for the corresponding Insurance Company
     * @param lngInsSeqID long Insurance Seq ID
     * @return ArrayList object which contains all the details about the Products for the corresponding Insurance Company
     * @exception throws TTKException
     */
    public ArrayList getInsProductList(long lngInsSeqID) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.getInsProductList(lngInsSeqID);
    }//end of getInsProductList(long lngInsSeqID)

	/**
	 * This method returns the Arraylist of InsuranceVO's  which contains insurance company details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceVO object which contains insurance company details
	 * @exception throws TTKException
	 */
	public ArrayList getInsuranceCompanyList(ArrayList alSearchCriteria) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.getInsuranceCompanyList(alSearchCriteria);
	}//end of getInsuranceCompanyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PolicyGroupVO's  which contains Policy Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyGroupVO object which contains Policy Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.getGroupList(alSearchCriteria);
	}//end of getGroupList(ArrayList alSearchCriteria)

	/**
     * This method saves the Enrollment Policy information
     * @param policyDetailVO the object which contains the Enrollment Policy Details which has to be  saved
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public long savePolicy(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.savePolicy(policyDetailVO,strPolicyMode,strPolicyType);
	}//end of savePolicy(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType)

    /**
     * This method deletes the policy information from the database
     * @param alDeletePolicy arraylist which contains the details of policies
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deletePolicy(ArrayList alDeletePolicy) throws TTKException
    {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.deletePolicy(alDeletePolicy);
    }// End ofdeletePolicy(ArrayList alDeletePolicy)

	/**
	 * This method deletes the policy information from the database
	 * @param strSeqID String concatenation of Endorsement and policy sequence id's for which the details has to be deleted
	 *@param lngUserSeqID long value which contains which user has logged in
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteBatchPolicy(String strSeqID,long lngUserSeqID) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.deleteBatchPolicy(strSeqID, lngUserSeqID);
    }//end deleteBatchPolicy(String strSeqID,long lngUserSeqID)

	/**
	 * This method returns the batchVO which contains the Batch Enrollment Details
	 * @param lngBatchSeqID the batch sequence id for which  Batch Enrollment Details has to be fetched
	 * @return BatchVO batchVO which contains the Batch Enrollment Details
	 * @exception throws TTKException
	 */
	public BatchVO getBatch(Long lngBatchSeqID) throws TTKException{
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.getBatch(lngBatchSeqID);
	}// End of getBatch(Long lngBatchSeqID)

	/**
	 * This method saves the Batch Enrollment information
	 * @param batchVO the object which contains the Batch Enrollment Details which has to be  saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public long saveBatch(BatchVO batchVO) throws TTKException
	{
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.saveBatch(batchVO);
	}// End of saveBatch(BatchVO batchVO)

    /**
     * This method returns the Arraylist of PolicyVO's  which contains Other Policies details
     * @param lngMemberSeqID long value which contains the member sequence id
     * @return ArrayList of PolicyVO object which contains Other Policies details
     * @exception throws TTKException
     */
    public ArrayList getOtherPolicyList(long lngMemberSeqID) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.getOtherPolicyList(lngMemberSeqID);
    }//end of getOtherPolicyList(long lngMemberSeqID)

    /**
     * This method returns the ArrayList which contains compare policy details
     * @param strMemberSeqID String object for comparing Policies
     * @return ArrayList object which contains compare policy details
     * @Exception throws TTKException
     */
    public ArrayList comparePolicy(String strMemberSeqID) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.comparePolicy(strMemberSeqID);
    }//end of comparePolicy(String strMemberSeqID)

    /**
     * This method associates the other policy infomation for the given sequence id's
     * @param lngMemAssocSeqID long value for which policy  to be associated
     * @param lngUserSeqID long value for which user has logged in
     * @param strFlag String which is for Associated/Un-Associated
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exceptionthrows TTKException
     */
    public int associatePolicy(long lngMemAssocSeqID,long lngUserSeqID,String strFlag) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.associatePolicy(lngMemAssocSeqID,lngUserSeqID,strFlag);
    }//end of associatePolicy(long lngMemAssocSeqID,long lngUserSeqID,String strFlag)

    /**
     * This method returns the endorsement details of the policy
     * @param lngEndorsementSeqID the Endorsement sequence id for which the Endorsement Details has to be fetched
     * @return EndorsementVO the object which contains the Endorsement  details of policy
     * @exception throws TTKException
     */
    public EndorsementVO getEndorsement(Long lngEndorsementSeqID) throws TTKException{
        policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
        return policyDAO.getEndorsement(lngEndorsementSeqID);
    }// End of getEndorsement(Long lngEndorsementSeqID)

    /**
     * This method saves saves/updates the endorsement details of the policy
     * @param endorsementVO the endorsement details of the policy which has to be saved/updated
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveEndorsement(EndorsementVO endorsementVO) throws TTKException{
        policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
        return policyDAO.saveEndorsement(endorsementVO);
    }// End of saveEndorsement(EndorsementVO endorsementVO)

    /**
     * This method saves the Review information
     * @param policyDetailVO the object which contains the Review Details which has to be  saved
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return PolicyDetailVO object which contains Review Information
     * @exception throws TTKException
     */
    public PolicyDetailVO saveReview(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.saveReview(policyDetailVO,strPolicyMode,strPolicyType);
    }//end of saveReview(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType)

    /**
     * This method saves the Review information
     * @param endorsementVO the object which contains the Review Details which has to be  saved
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return EndorsementVO object which contains Review Information
     * @exception throws TTKException
     */
    public EndorsementVO saveEndorsementReview(EndorsementVO endorsementVO,String strPolicyMode,String strPolicyType) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.saveEndorsementReview(endorsementVO,strPolicyMode,strPolicyType);
    }//end of saveEndorsementReview(EndorsementVO endorsementVO,String strPolicyMode,String strPolicyType)

    /**
	 * This method returns the Arraylist of BatchVO's  which contains the details of batch enrollment details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BatchVO object which contains the details of batch enrollment details
	 * @exception throws TTKException
	 */
	public ArrayList getBatchList(ArrayList alSearchCriteria) throws TTKException
	{
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.getBatchList(alSearchCriteria);
	}//End of getBatchList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList of PolicyVO's, which contains all the policy details for given batch
	 * @param alSearchCriteria ArrayList which contains the collection of SearchCriteria objects
	 * @return ArrayList of PolicyVO's, which contains all the policy details for given batch
	 */
	public ArrayList getBatchPolicyList(ArrayList alSearchCriteria) throws TTKException {
		policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
		return policyDAO.getBatchPolicyList(alSearchCriteria);
	}//end of getBatchPolicyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the PolicyDetailVO which contains the details of batch policy
	 * @param lngPolicySeqID the Policy sequence id for which the details of policy are required
	 * @param lngEndorsementSeqID the Endorsment sequence if for which the details of policy are required
	 * @return PolicyDetailVO object which contains the details of batch policy
	 * @Exception throws TTKException
	 */
	public PolicyDetailVO getBatchPolicy(Long lngPolicySeqID,Long lngEndorsementSeqID,Long lngInsSeqID) throws TTKException
	{
		policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
		return policyDAO.getBatchPolicy(lngPolicySeqID, lngEndorsementSeqID,lngInsSeqID);
	}// End of getBatchPolicy(Long lngPolicySeqID,Long lngEndorsementSeqID,Long lngInsSeqID)

	/**
	 * This method deletes the batch Enrollment infomation for the given sequence id's
	 * @param alDeleteBatch the details of the batch sequence id's which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exceptionthrows TTKException
	 */
	public int deleteBatch(ArrayList alDeleteBatch) throws TTKException {
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.deleteBatch(alDeleteBatch);
	}//End of deleteBatch(ArrayList alDeleteBatch)

	/**
	 * This method saves the Batch policy information
	 * @param policyDetailVO the object which contains the policy details  which has to be updated
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBatchPolicy(PolicyDetailVO policyDetailVO) throws TTKException
	{
		policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
		return policyDAO.saveBatchPolicy(policyDetailVO);
	}// End of saveBatchPolicy(PolicyDetailVO policyDetailVO)

	/**
	 * This method saves the Batch policy information
	 * @param lngEnrollBatchSeqID long value which contains the enroll batch seq id which has to be updated
     * @param lngUserSeqID long value for which user has logged in
     * @param strFlag String Object contains Flag for InwardComplete/DataEntryComplete - INW/BAT
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBatchComplete(long lngEnrollBatchSeqID,long lngUserSeqID,String strFlag) throws TTKException {
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.saveBatchComplete(lngEnrollBatchSeqID,lngUserSeqID,strFlag);
	}//end of saveBatchComplete(long lngEnrollBatchSeqID,long lngUserSeqID,String strFlag)

    /**
     * This method deletes the Endorsement information from the database
     * @param alDeleteEndorsement the arraylist of endorsement details which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteEndorsement(ArrayList alDeleteEndorsement) throws TTKException {
    	policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
    	return policyDAO.deleteEndorsement(alDeleteEndorsement);
    }//end of deleteEndorsement(ArrayList alDeleteEndorsement)

	/**
     * This method deletes the policy information from the database
     * @param strPolicySeqID the policy sequence id's for which the details has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteGeneral(ArrayList alDeleteGeneral) throws TTKException {
        policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
        return policyDAO.deleteGeneral(alDeleteGeneral);
    }//end of deleteGeneral(ArrayList alDeleteGeneral)

    /**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getOnlinePolicyList(ArrayList alSearchCriteria) throws TTKException {
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.getOnlinePolicyList(alSearchCriteria);
    }//end of getPolicyList(ArrayList alSearchCriteria)

    /**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param strFlag String object which contains Flag - P / E
     * @param lngSeqID long value which contains POLICY_SEQ_ID /POLICY_GROUP_SEQ_ID to get the Rule Data Details
     * @return Document object which contains Rule Data XML
     * @exception throws TTKException
     */
    public Document validateEnrollment(String strFlag,long lngSeqID) throws TTKException{
        policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
        return policyDAO.validateEnrollment(strFlag,lngSeqID);
    }//end of validateEnrollment(String strFlag,long lngSeqID)
    
    /**
     * This method checks wheteher Rule defined for Policy or not
     * @param lngPolicySeqID the policy sequence id
     * @return int the value which returns 0 or greater than 0 . If values is '0' rule is not defined and greater than 0 rule is defined
     * @exception throws TTKException
     */
    public int checkRuleDefined(long lngPolicySeqID) throws TTKException {
    	policyDAO = (PolicyDAOImpl)this.getEnrollmentDAO("enrollment");
    	return policyDAO.checkRuleDefined(lngPolicySeqID);
    }//end of checkRuleDefined(long lngPolicySeqID)
    
    /**
	 * This method returns the Arraylist of BatchVO's  which contains the details of Softcopy Batch
	 * This method used in Reports->Enrollment->IOB Report
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BatchVO object which contains the details of Softcopy Batch
	 * @exception throws TTKException
	 */
	public ArrayList getSoftcopyBatchList(ArrayList alSearchCriteria) throws TTKException {
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.getSoftcopyBatchList(alSearchCriteria);
	}//end of getSoftcopyBatchList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the Arraylist of CardBatchVO's  which contains the details of ID Card Printing Batch
	 * This method used in Support->Customized List->HDFC Certificate Generation
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CardBatchVO object which contains the details of ID Card Printing Batch
	 * @exception throws TTKException
	 */
	public ArrayList getCardBatchList(ArrayList alSearchCriteria) throws TTKException {
		batchDAO = (BatchDAOImpl)this.getEnrollmentDAO("batch");
		return batchDAO.getCardBatchList(alSearchCriteria);
	}//end of getCardBatchList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the PolicyDetailVO  which contains the details of Sum Insured Information
	 * @param lngPolicySeqID Long Value which contains the Policy SeqID
	 * @return  PolicyDetailVO object which contains the details of Sum Insured Information
	 * @exception throws TTKException
	 */
	public PolicyDetailVO getPolicySIInfo(Long lngPolicySeqID) throws TTKException
	{
		policyDAO = (PolicyDAOImpl) this.getEnrollmentDAO("enrollment");
		return policyDAO.getPolicySIInfo(lngPolicySeqID);
	}//end of getPolicySIInfo(Long lngPolicySeqID)
}// End of PolicyManagerBean
