package com.ttk.business.onlineforms.providerLogin; 
/**
 * @ (#) OnlineProviderManagerBean.java 20 Nov 2015
 * Project 	     : TTK HealthCare Services
 * File          : OnlineProviderManagerBean.java
 * Author        : Kishor kumar S H
 * Company       : RCS TEchnologies
 * Date Created  : 20 Nov 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


import java.util.ArrayList;
import org.apache.struts.upload.FormFile;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.onlineforms.OnlineAccessDAOFactory;
import com.ttk.dao.impl.onlineforms.providerLogin.ClaimsDAOImpl;
import com.ttk.dao.impl.onlineforms.providerLogin.OnlineProviderDAOImpl;
import com.ttk.dao.impl.onlineforms.providerLogin.OnlineClaimDAOFactory;
import com.ttk.dto.onlineforms.providerLogin.BatchListVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class OnlineProviderManagerBean implements OnlineProviderManager{
	 private OnlineClaimDAOFactory onlineClaimDAOFactory = null;
	private OnlineAccessDAOFactory onlineAccessDAOFactory = null;
	private OnlineProviderDAOImpl onlineProviderDAOImpl = null;
	 private ClaimsDAOImpl claimsDAO = null;
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
/*	private BaseDAO getOnlineAccessDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (onlineAccessDAOFactory == null)
				onlineAccessDAOFactory = new OnlineAccessDAOFactory();
			if(strIdentifier!=null)
			{
				return onlineAccessDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getOnlineAccessDAO(String strIdentifier)
	*/
	
	 
		private BaseDAO getOnlineAccessDAO(String strIdentifier) throws TTKException
		{
			try
			{
				
				if("providerClaim".equals(strIdentifier))
	        	{
	        		 if (onlineClaimDAOFactory == null)
	        			 onlineClaimDAOFactory = new OnlineClaimDAOFactory();
	                 if(strIdentifier!=null)
	                 {
	                     return onlineClaimDAOFactory.getDAO(strIdentifier);
	                 }//end of if(strIdentifier!=null)
	                 else
	                     return null;
	        		
	        	}else{
	        		
				if (onlineAccessDAOFactory == null)
					onlineAccessDAOFactory = new OnlineAccessDAOFactory();
				if(strIdentifier!=null)
				{
					return onlineAccessDAOFactory.getDAO(strIdentifier);
				}//end of if(strIdentifier!=null)
				else
					return null;
	        	}
			}//end of try
			catch(Exception exp)
			{
				throw new TTKException(exp, "error."+strIdentifier+".dao");
			}//end of catch(Exception exp)
		}//End of getOnlineAccessDAO(String strIdentifier)
		
	 /**
     * DashBoard Details
     */

    public ArrayList getPreAuthSearchList(ArrayList alSearchCriteria) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getPreAuthSearchList(alSearchCriteria);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)

    
    /**
     * 
     * get The Authorization details on click on the table
     */

    public Object[] getAuthDetails(String empanelNo,String status,Long patAuthSeqId,String patientCardId) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getAuthDetails(empanelNo,status,patAuthSeqId,patientCardId);
    }//end of PreAuthSearchVO getAuthDetails(String empanelNo,String status)
    
    
    /**
     * 
     * get The Authorization details on click on the table
     */

    public ArrayList getShortfallList(Long patAuthSeqId) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getShortfallList(patAuthSeqId);
    }
    

    
    public ArrayList getClaimSearchList(ArrayList alSearchCriteria) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getClaimSearchList(alSearchCriteria);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
    public String[] getChequeDetails(String chequeNumber) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getChequeDetails(chequeNumber);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
    
    
    public String[] getInvoiceDetails(String empanelNo,String empanelNumber) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getInvoiceDetails(empanelNo,empanelNumber);
    }//end of getCorpFocusClaimsDetails(String empanelNo)
    
    /*
     * Get Log Details
     */
    public ArrayList getLogData(String strUserId) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
 	       return onlineProviderDAOImpl.getLogData(strUserId);
 	   }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
    /*
     * Get Recent Reports Downloaded
     */
    public ArrayList getBatchRenconcilList(ArrayList alSearchCriteria) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getBatchRenconcilList(alSearchCriteria);
    }//end of getBatchRenconcilList(ArrayList alSearchCriteria)
    
    
    
    /*
     * Get Batch Reconciliation Invoice details 
     */
    
    public ArrayList<BatchListVO> getBatchInvDetails(Long BatchSeqId, String strFlag, String EmpnlNo) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getBatchInvDetails(BatchSeqId,strFlag,EmpnlNo);
    }//end of getBatchInvDetails(Long BatchSeqId, String strVar)
    
    
    /*
     * Get getBatch Invoice List
     */
    public ArrayList getBatchInvoiceList(ArrayList alSearchCriteria) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getBatchInvoiceList(alSearchCriteria);
    }//end of getBatchInvoiceList(ArrayList alSearchCriteria)
    
    
    /*To Get the Invoice Report Details
     * (non-Javadoc)
     * @see com.ttk.business.onlineforms.providerLogin.OnlineProviderManager#getBatchInvReportDetails(java.lang.String, java.lang.String)
     */
    
    public String[] getBatchInvReportDetails(String invNo,String EmpnlNo) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getBatchInvReportDetails(invNo, EmpnlNo);
    }//end of getCorpFocusClaimsDetails(String enrollmentId)
    
    
    
    /*
     * Get getBatch Invoice List
     */
    public ArrayList<BatchListVO> getOverDueList(ArrayList<Object> arrayList) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getOverDueList(arrayList);
    }//end of getBatchInvoiceList(ArrayList alSearchCriteria)
    
    
    /*
     * Get Finance DashBoard Details
     */
    public String[] getFinanceDahBoard(String empanelNo,String fromDate,String toDate) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getFinanceDahBoard(empanelNo,fromDate,toDate);
    }//end of String[] getFinanceDahBoard(String empanelNo)
    
    
    /*
     * Get Recent Reports Downloaded
     */
    public ArrayList<String[]> getRecentReports(String empanelNo,Long userId) throws TTKException {
    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
        return onlineProviderDAOImpl.getRecentReports(empanelNo,userId);
    }//end of getBatchInvoiceList(ArrayList alSearchCriteria)


	
	
	
	 public ArrayList getClaimSummarySearchList(ArrayList alSearchCriteria,String strEmpanelNumber) throws TTKException {
	    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	        return onlineProviderDAOImpl.getClaimSummarySearchList(alSearchCriteria,strEmpanelNumber);
	    }//end of getCorpFocusClaimsDetails(String enrollmentId)


	//Added for Partner Log Search
	   public ArrayList  getPartnerClaimSearchList(ArrayList alSearchCriteria) throws TTKException {
		   	claimsDAO = (ClaimsDAOImpl)this.getOnlineAccessDAO("providerClaim");
		       return claimsDAO.getPartnerClaimSearchList(alSearchCriteria);
		   }//end of getCorpFocusClaimsDetails(String enrollmentId)
	
	   public Object[] getClaimDetails(Long patAuthSeqId) throws TTKException {
		   claimsDAO = (ClaimsDAOImpl)this.getOnlineAccessDAO("providerClaim");
	       return claimsDAO.getClaimDetails(patAuthSeqId);
	   }//end of Object[] getClaimSubmittedDetails(String batchNo)
	   
	   
	   public String[] getMemProviderForShortfall(Long shortfallSeqID,String patOrClm) throws TTKException {
	    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	        return onlineProviderDAOImpl.getMemProviderForShortfall(shortfallSeqID,patOrClm);
	    }//end of getBatchInvoiceList(ArrayList alSearchCriteria)
	   
	   
	   public String saveShorfallDocs(Long shortfallSeqID,FormFile formFile) throws TTKException {
	    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	        return onlineProviderDAOImpl.saveShorfallDocs(shortfallSeqID,formFile);
	    }//end of getBatchInvoiceList(ArrayList alSearchCriteria)
	    
	    
	   /**
	    * DashBoard Details
	    */

	   public ArrayList getPartnerPreAuthSearchList(ArrayList alSearchCriteria) throws TTKException {
	   	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	       return onlineProviderDAOImpl.getPartnerPreAuthSearchList(alSearchCriteria);
	   }//end of getCorpFocusClaimsDetails(String enrollmentId)
	    
	   /**
	     * 
	     * get The Authorization details on click on the table
	     */

	    public PreAuthSearchVO getAuthDetails(String empanelNo,String status,Long patAuthSeqId) throws TTKException {
	    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	        return onlineProviderDAOImpl.getAuthDetails(empanelNo,status,patAuthSeqId);
	    }
	    
	 // for preauth docs upload 
	    public ArrayList<String[]> getActivityDetails(Long activityDtlSeqId) throws TTKException{
	    	onlineProviderDAOImpl = (OnlineProviderDAOImpl)this.getOnlineAccessDAO("providerLogin");
	        return onlineProviderDAOImpl.getActivityDetails(activityDtlSeqId);
	    }
	   
}//end of OnlineProviderManagerBean
