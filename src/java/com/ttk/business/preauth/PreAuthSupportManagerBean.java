/**
 * @ (#) PreAuthSupportManagerBean.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthSupportManagerBean.java
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

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.preauth.PreAuthDAOFactory;
import com.ttk.dao.impl.preauth.PreAuthSupportDAOImpl;
import com.ttk.dto.claims.ClaimIntimationVO;
import com.ttk.dto.preauth.BufferDetailVO;
import com.ttk.dto.preauth.InvestigationVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.preauth.SupportVO;
@Stateless

@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class PreAuthSupportManagerBean implements PreAuthSupportManager{

    private PreAuthDAOFactory preAuthDAOFactory = null;
    private PreAuthSupportDAOImpl preAuthSupportDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getPreAuthSupportDAO(String strIdentifier) throws TTKException
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
    }//End of getPreAuthSupportDAO(String strIdentifier)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Pre-Authorization Support Documents Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Pre-Authorization Support Documents Details
     * @exception throws TTKException
     */
    public ArrayList getSupportDocumentList(ArrayList alSearchCriteria) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.getSupportDocumentList(alSearchCriteria);
    }//end of getSupportDocumentList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of BufferVO's  which contains Pre-Authorization Support Buffer Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BufferVO object which contains Pre-Authorization Support Buffer Details
     * @exception throws TTKException
     */
    public ArrayList getSupportBufferList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getSupportBufferList(alSearchCriteria);
    }//end of getSupportBufferList(ArrayList alSearchCriteria)

    public BufferDetailVO getSupportBufferListEdit(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return (BufferDetailVO) preAuthSupportDAO.getSupportBufferListEdit(alSearchCriteria);
    }//end of getSupportBufferList(ArrayList alSearchCriteria)

    
    
    /**
     * This method returns the BufferDetailVO, which contains all the Pre-Authorization Buffer Details
     * @param lngBufferDtlSeqID long value which contains Buffer Dtl seq id to fetch the Pre-Authorization Buffer Details
     * @param lngUserSeqID long value contains Logged-in User ID
     * @param strFlag String Object contains Preauthorization/Claims
     * @return BufferDetailVO object which contains all the Pre-Authorization Buffer Details
     * @exception throws TTKException
     */
    public BufferDetailVO getBufferDetail(long lngBufferDtlSeqID,long lngUserSeqID,String strFlag) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getBufferDetail(lngBufferDtlSeqID,lngUserSeqID,strFlag);
    }//end of getBufferDetail(long lngBufferDtlSeqID,long lngUserSeqID,String strFlag)

    /**
     * This method returns the BufferDetailVO, which contains all the Pre-Authorization Buffer Admin Authority
     * @param lngSeqID long value contains Preauth Seq ID or Clims Seq ID
     * @param strString string value contains to identify the module
     * @return Object[] object which contains all the Pre-Authorization Buffer Admin Authority
     * @exception throws TTKException
     */
    public Object[] getBufferAuthority(long lngSeqID,String strIdentifier) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getBufferAuthority(lngSeqID,strIdentifier);
    }//end of getBufferDetailAuthority(long lngPreauthSeqID)
      //added for hyundai buffer
    
    /**
     * This method returns the BufferDetailVO, which contains all the Pre-Authorization Buffer Admin Authority
     * @param lngSeqID long value contains Preauth Seq ID or Clims Seq ID
     * @param strString string value contains to identify the module
     * @return Object[] object which contains all the Pre-Authorization Buffer Admin Authority
     * @exception throws TTKException
     */
    public Object[] getBufferAuthority(ArrayList alBufferAuthoritylList) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getBufferAuthority(alBufferAuthoritylList);
    }//end of getBufferDetailAuthority(long lngPreauthSeqID)

    /**
     * This method saves the Pre-Authorization Buffer information
     * @param bufferDetailVO BufferDetailVO contains Pre-Authorization Buffer information
     * @param strFlag String Object contains PAT/CLM
     * @return long value which contains Buffer Dtl Seq ID
     * @exception throws TTKException
     */
    public long saveBufferDetail(BufferDetailVO bufferDetailVO,String strFlag) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.saveBufferDetail(bufferDetailVO,strFlag);
    }//end of saveBufferDetail(BufferDetailVO bufferDetailVO,String strFlag)

    /**
     * This method returns the ShortfallVO, which contains all the Pre-Authorization/Claims Shortfall Details
     * @param alShortfallList object contains SeqID,Preauth/ClaimSeqID to fetch the Pre-Authorization/Claims Shortfall Details
     * @return ShortfallVO object which contains all the Pre-Authorization Shortfall/Claims Details
     * @exception throws TTKException
     */
    public ShortfallVO getShortfallDetail(ArrayList alShortfallList) throws TTKException {
         preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
         return preAuthSupportDAO.getShortfallDetail(alShortfallList);
      }//end of getShortfallDetail(ArrayList alShortfallList)

    /**
     * This method saves the Pre-Authorization Shortfall information
     * @param shortfallVO ShortfallVO contains Pre-Authorization Shortfall information
     * @param strSelectionType
     * @return long value which contains Shortfall Seq ID
     * @exception throws TTKException
     */
    public long saveShortfall(ShortfallVO shortfallVO,String strSelectionType) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.saveShortfall(shortfallVO,strSelectionType);
    }//end of saveShortfall(ShortfallVO shortfallVO,String strSelectionType)

    /**
     * This method returns the InvestigationVO, which contains all the Pre-Authorization Investigation Details
     * @param alInvestigationList contains  Type for identifying Pre-Authorization/Claims identifier is PAT/CLM,
     * Seq ID's to get the Pre-Authorization/Claims Investigation Details
     * @return InvestigationVO object which contains all the Pre-Authorization Investigation Details
     * @exception throws TTKException
     */
    public InvestigationVO getInvestigationDetail(ArrayList alInvestigationList) throws TTKException {
         preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
         return preAuthSupportDAO.getInvestigationDetail(alInvestigationList);
    }//end of getInvestigationDetail(ArrayList alInvestigationList)
	
	//koc11 koc 11
    public void sendInvestigation(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        preAuthSupportDAO.sendInvestigation(lngPatGenDtlSeqID,strIdentifier,lngUserID);
   }//end of sendInvestigation(ArrayList alInvestigationList)

    /**
     * This method saves the Pre-Authorization Investigation information
     * @param investigationVO InvestigationVO contains Pre-Authorization Investigation information
     * @return long value which contains Investigation Seq ID
     * @exception throws TTKException
     */
    //INVESTIGATION UAT
    public String getInvestRate(Object agencyId,Object typeDesc) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.getInvestRate(agencyId,typeDesc);
    }
    public long saveInvestigation(InvestigationVO investigationVO) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.saveInvestigation(investigationVO);
    }//end of saveInvestigation(InvestigationVO investigationVO)
	
	 public long saveInvestigationSupport(InvestigationVO investigationVO) throws TTKException {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.saveInvestigationSupport(investigationVO);
    }//end of saveInvestigationSupport(InvestigationVO investigationVO)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Pre-Authorization Support Documents Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Pre-Authorization Support Documents Details
     * @exception throws TTKException
     */
    public ArrayList getSupportInvestigationList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getSupportInvestigationList(alSearchCriteria);
    }//end of getSupportInvestigationList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Quality Control Audit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Quality Control Audit Details
     * @exception throws TTKException
     */
    public ArrayList getSupportQCAuditList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getSupportQCAuditList(alSearchCriteria);
    }//end of getSupportQCAuditList(ArrayList alSearchCriteria)

    /**
     * This method saves the Pre-Authorization Quality information
     * @param supportVO SupportVO contains Quality information
     * @return long value which contains QC Seq ID
     * @exception throws TTKException
     */
    public long saveQualityDetail(SupportVO supportVO) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.saveQualityDetail(supportVO);
    }//end of saveQualityDetail(SupportVO supportVO)

    /**
     * This method returns the SupportVO, which contains all the Pre-Authorization Quality Details
     * @param strType for identifying Pre-Authorization/Claims identifier is PAT/CLM
     * @param lngQCSeqID long value which contains QC seq id to get the Pre-Authorization Quality Details
     * @param lngUserSeqID contains details of logged-in user
     * @return SupportVO object which contains all the Quality Details
     * @exception throws TTKException
     */
    public SupportVO getQualityDetail(String strType,long lngQCSeqID,long lngUserSeqID) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getQualityDetail(strType,lngQCSeqID,lngUserSeqID);
    }//end of getQualityDetail(String strType,long lngQCSeqID,long lngUserSeqID)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Discharge Voucher Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Discharge Voucher details
     * @exception throws TTKException
     */
    public ArrayList getDischargeVoucherList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getDischargeVoucherList(alSearchCriteria);
    }//end of getDischargeVoucherList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the int value
     * @param lngClaimSeqID Claim Seq ID
     * @return integer value
     * @exception throws TTKException
     */
    public int getDischargeVoucherRequired(long lngClaimSeqID) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getDischargeVoucherRequired(lngClaimSeqID);
    }//end of getDischargeVoucherRequired(long lngClaimSeqID)

    /**
     * This method returns the DischargeVoucherVO, which contains all the Discharge Voucher details
     * @param lngDschrgVoucherSeqID long value contains seq id to get the Discharge Voucher Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ShortfallVO object which contains all the Discharge Voucher details
     * @exception throws TTKException
     */
    public ShortfallVO getDischargeVoucherDetail(long lngDschrgVoucherSeqID,long lngUserSeqID) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getDischargeVoucherDetail(lngDschrgVoucherSeqID,lngUserSeqID);
    }//end of getDischargeVoucherDetail(long lngDschrgVoucherSeqID,long lngUserSeqID)

    /**
	 * This method saves the Discharge Voucher information
	 * @param shortfallVO the object which contains the Discharge Voucher Details which has to be  saved
	 * @return long the value contains Discharge Voucher Seq ID
	 * @exception throws TTKException
	 */
	public long saveDischargeVoucherDetail(ShortfallVO shortfallVO) throws TTKException{
		preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
		return preAuthSupportDAO.saveDischargeVoucherDetail(shortfallVO);
	}//end of saveDischargeVoucherDetail(DischargeVoucherVO dischargeVoucherVO)

	/**
     * This method returns the ArrayList contains DocumentChecklistVO objects, which contains all the Document Checklist details
     * @param lngClaimSeqID long value contains Claim Seq Id to get the Document Checklist Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ArrayList contains DocumentChecklistVO objects ,which contains all the Document Checklist details
     * @exception throws TTKException
     */
    public ArrayList<Object> getDocumentChecklist(long lngClaimSeqID,long lngUserSeqID) throws TTKException{
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getDocumentChecklist(lngClaimSeqID,lngUserSeqID);
    }//end of getDocumentChecklist(long lngClaimSeqID,long lngUserSeqID)

    /**
	 * This method saves the Document Checklist information
	 * @param alDocumentChecklist ArrayList object which contains the Document Checklist Details which has to be  saved
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveDocumentChecklist(ArrayList alDocumentChecklist) throws TTKException {
		preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
		return preAuthSupportDAO.saveDocumentChecklist(alDocumentChecklist);
	}//end of saveDocumentChecklist(ArrayList alDocumentChecklist)

	/**
     * This method returns the Arraylist of ClaimIntimationVO's  which contains Claim Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimIntimationVO object which contains Claim Intimation details
     * @exception throws TTKException
     */
    public ArrayList getClaimIntimationList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getClaimIntimationList(alSearchCriteria);
    }//end of getClaimIntimationList(ArrayList alSearchCriteria)
    /**
     * This method returns the Arraylist of ClaimIntimationVO's  which contains Claim Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimIntimationVO object which contains Claim Intimation details
     * @exception throws TTKException
     */
    public ArrayList getShortfallsList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getShortfallsList(alSearchCriteria);
    }//end of getClaimIntimationList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the ClaimIntimationVO, which contains all the Claim Intimation details
     * @param lngClaimSeqID long value contains seq id to get the Claim Intimation Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ClaimIntimationVO object which contains all the Claim Intimation details
     * @exception throws TTKException
     */
    public ClaimIntimationVO getClaimIntimationDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
    	return preAuthSupportDAO.getClaimIntimationDetail(lngClaimSeqID,lngUserSeqID);
    }//end of getClaimIntimationDetail(long lngClaimSeqID,long lngUserSeqID)

    /**
	 * This method saves the Claim Intimation information
	 * @param claimIntimationVO the object which contains the Claim Intimation Details which has to be  saved
	 * @return long the value contains Claim Intimation Seq ID
	 * @exception throws TTKException
	 */
    
    
 
    
    
	public long saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO) throws TTKException {
		preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
		return preAuthSupportDAO.saveClaimIntimationDetail(claimIntimationVO);
	}//end of saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO)
	
	//KOC 1339 for mail
	public long removeClaimIntimationDetail(ClaimIntimationVO claimIntimationVO) throws TTKException {
		preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
		return preAuthSupportDAO.removeClaimIntimationDetail(claimIntimationVO);
	}//end of saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO)
	
	//KOC 1339 for mail

    /**
     * This method returns the missing document
     * @param lngClaimSeqID which contains the Claim Intimation Seq ID for which missing documnet is required
     * @return Document object containing missing document
     * @exception throws TTKException
     */
    public Document selectMissingDocs(long lngClaimSeqID,long lngAddedBy) throws TTKException{
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.selectMissingDocs(lngClaimSeqID,lngAddedBy);
    }//end of saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO)

    
  //shortfall phase1
    public String getClaimTypeID(String strClaimSeqID) throws TTKException   {
        preAuthSupportDAO = (PreAuthSupportDAOImpl)this.getPreAuthSupportDAO("support");
        return preAuthSupportDAO.getClaimTypeID(strClaimSeqID);
    }//end of getClaimType1(String strClaimSeqID) throws TTKException
	
  //shortfall phase1
}//end of PreAuthSupportManagerBean
