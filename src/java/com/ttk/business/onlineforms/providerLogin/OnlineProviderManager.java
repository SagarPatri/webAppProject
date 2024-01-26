package com.ttk.business.onlineforms.providerLogin; 

/**
 * @ (#) OnlineProviderManager.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : OnlineProviderManager.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologoes
 * Date Created  : Nov 18, 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


import java.util.ArrayList;

import javax.ejb.Local;
import org.apache.struts.upload.FormFile;
import com.ttk.action.table.onlineforms.providerLogin.OverDueReportTable;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.insuranceLogin.DashBoardVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsCorporateVO;
import com.ttk.dto.onlineforms.providerLogin.BatchListVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;

@Local

public interface OnlineProviderManager {
	
	
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    public ArrayList getPreAuthSearchList(ArrayList arrayList) throws TTKException;
    
   
    /*
     * get The Authorization details on click on the table
     */
    // for preauth docs upload
    public Object[] getAuthDetails(String empanelNo,String status,Long patAuthSeqId,String patientCardId) throws TTKException;

    /*
     * get The Authorization details on click on the table
     */
    
  
    public  PreAuthSearchVO getAuthDetails(String empanelNo,String status,Long patAuthSeqId) throws TTKException;

    
    
    /*
     * get The Shortfall List on click on the table
     */
    
    public ArrayList getShortfallList(Long patAuthSeqId) throws TTKException;
    
    
    /*
     * get the details on click on the link in 
     */
    public ArrayList getClaimSearchList(ArrayList arrayList) throws TTKException;
    
    
    /*
     * To get the Cheque Details
     */
    
    public String[] getChequeDetails(String chequeNumber) throws TTKException;
    
    /*
     * To get the Invoice Details
     */
    
    public String[] getInvoiceDetails(String chequeNumber,String empanelNumber) throws TTKException;

    
    
    
    /*
     * Get Log Details
     */
    
    public ArrayList getLogData(String strUserId) throws TTKException;
    
    /*
     * Get Batch Reconciliation Details
     */
    
    public ArrayList getBatchRenconcilList(ArrayList arrayList) throws TTKException;
    
    
    /*
     * Get Batch Reconciliation Invoice details 
     */
    
    public ArrayList<BatchListVO> getBatchInvDetails(Long BatchSeqId, String strFlag,String EmpnlNo) throws TTKException;
    
    
    /*
     * Get Batch Invoice List
     */
    
    public ArrayList getBatchInvoiceList(ArrayList arrayList) throws TTKException;   
    
    /*
     * Get Batch Invoice report Details
     */
    
    public String[] getBatchInvReportDetails(String invNo,String EmpnlNo) throws TTKException;   
    
    
    /*
     * Get Over Due Report Search List
     */
    
    public ArrayList<BatchListVO> getOverDueList(ArrayList<Object> arrayList) throws TTKException;
    
    
   /*
    *  Finance DashBoard Details
    */
    public String[] getFinanceDahBoard(String empanelNo,String fromDate,String toDate) throws TTKException;
    
   
    /*
     *  Get Recent Reports Downloaded
     */
     public ArrayList<String[]> getRecentReports(String empanelNo,Long userId) throws TTKException;


	public ArrayList getClaimSummarySearchList(ArrayList arrayList, String strEmpanelNumber) throws TTKException;
     
	  
	 //Added for Partner Log Search
    public ArrayList getPartnerClaimSearchList(ArrayList arrayList) throws TTKException;
    

    public Object[] getClaimDetails(Long patAuthSeqId) throws TTKException;
    
    public String[] getMemProviderForShortfall(Long shortfallSeqID,String patOrClm) throws TTKException;
    
    
    public String saveShorfallDocs(Long shortfallSeqID,FormFile formFile) throws TTKException;
    
    
    /*
     * get the details on click on the link in corporate search- leads to corporate focused view 
     */
    public ArrayList getPartnerPreAuthSearchList(ArrayList arrayList) throws TTKException;
    
    
    // for preauth docs upload
    public ArrayList<String[]> getActivityDetails(Long activityDtlSeqId) throws TTKException;
    
}//end of OnlineProviderManager
