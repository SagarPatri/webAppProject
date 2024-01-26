/**
 * @ (#) PreAuthReferralManagerBean.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthReferralManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthDAOFactory;
import com.ttk.dao.impl.preauth.PreAuthDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthMedicalDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthReferralDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthSupportDAOImpl;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per KOC 1140 and 1142(1165)
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.InsOverrideConfVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;
import com.ttk.dto.preauth.PreAuthReferralVO;
import com.ttk.dto.preauth.PreAuthTariffVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.SIInfoVO;
import com.ttk.dto.preauth.ShortfallVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class PreAuthReferralManagerBean implements PreAuthReferralManager{

    private PreAuthDAOFactory preAuthDAOFactory = null;
    private PreAuthReferralDAOImpl preAuthReferralDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getPreAuthDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (preAuthDAOFactory == null)
                preAuthDAOFactory = new PreAuthDAOFactory();
            if(strIdentifier!=null)
            {
                return preAuthDAOFactory.getDAO(strIdentifier);
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
    public String[] getProviderDetails(String licenseNO) throws TTKException{
    	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
        return preAuthReferralDAO.getProviderDetails(licenseNO);
    }//end of getPreAuthList(ArrayList licenseNO)
    
    public String[] getMemberDetails(String memberId) throws TTKException{
    	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
        return preAuthReferralDAO.getMemberDetails(memberId);
    }//end of getMemberDetails(String memberId)
    
    /*
     * Save Referral Details
     */
    public Long saveReferralDetails(PreAuthReferralVO saveReferralVO) throws TTKException{
    	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
        return preAuthReferralDAO.saveReferralDetails(saveReferralVO);
    }//end of PreAuthReferralVO saveReferralDetails(PreAuthReferralVO saveReferralVO)
    
    /*
     * Get Referral Details 
     */
    public PreAuthReferralVO getReferralDetails(Long lRefSeqId) throws TTKException{
    	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
        return preAuthReferralDAO.getReferralDetails(lRefSeqId);
    }//end of PreAuthReferralVO getReferralDetails(Long lRefSeqId)
    /*
     * Search Referral letters
     */
    public ArrayList getReferralList(ArrayList alSearchObjects) throws TTKException
	{
    	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
		return preAuthReferralDAO.getReferralList(alSearchObjects);
	}//end of getHospitalList(ArrayList alSearchObjects)

    /*
     * Mail Send Code
     */
    public Long mailSend(Long lRefSeqId,Long userSeqId) throws TTKException
   	{
       	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
   		return preAuthReferralDAO.mailSend(lRefSeqId,userSeqId);
   			   	
   	}//end of getHospitalList(ArrayList alSearchObjects)
    
    /*
     * Get Mail Status
     */
    public String[] getmailSentStatus(Long lRefSeqId) throws TTKException
   	{
       	preAuthReferralDAO = (PreAuthReferralDAOImpl)this.getPreAuthDAO("preauthreferral");
   		return preAuthReferralDAO.getmailSentStatus(lRefSeqId);
   			   	
   	}//end of getHospitalList(ArrayList alSearchObjects)
    
}//end of PreAuthReferralManagerBean
