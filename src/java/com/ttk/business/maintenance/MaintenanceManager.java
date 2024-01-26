/**
 * @ (#) MaintenanceManager.java Oct 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : MaintenanceManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.business.maintenance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.common.HaadWebServiceVo;
import com.ttk.dto.empanelment.NotifyDetailVO;
import com.ttk.dto.maintenance.ClaimListVO;
import com.ttk.dto.maintenance.CriticalGroupVO;
import com.ttk.dto.maintenance.CustomizeConfigVO;
import com.ttk.dto.maintenance.DaycareGroupVO;
import com.ttk.dto.maintenance.ICDCodeVO;
import com.ttk.dto.maintenance.InvestigationGroupVO;

@Local

public interface MaintenanceManager {
	
	/**
	 * This method returns the Arraylist of DaycareGroupVO's  which contains Daycare Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of DaycareGroupVO object which contains Daycare Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException;
	public ArrayList getInvGroupList(ArrayList alSearchCriteria) throws TTKException; //koc11 koc 11
	
	/**
	 * This method returns the Arraylist of CriticalGroupVO's  which contains Critical ICD/PCS Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalGroupVO object which contains Critical ICD/PCS Group details
	 * @exception throws TTKException
	 */
	public ArrayList getCriticalGroupList(ArrayList alSearchCriteria) throws TTKException;
	
	
	/**
	 * This method returns the PreAuthDetailVO, which contains Daycare Group details
	 * @param strGroupID GroupID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return DaycareGroupVO object which contains Daycare Group details
	 * @exception throws TTKException
	 */
	public DaycareGroupVO getGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException;
	public InvestigationGroupVO getInvGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException;
	
	/**
	 * This method returns the PreAuthDetailVO, which contains Critical Group details
	 * @param strGroupID GroupID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return CriticalGroupVO object which contains Critical Group details
	 * @exception throws TTKException
	 */
	public CriticalGroupVO getCriticalGroupDetail (String strGroupID,long lngUserSeqID) throws TTKException;
	
	
	/**
	 * This method saves the Daycare Group information
	 * @param daycareGroupVO DaycareGroupVO contains Daycare Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveGroupDetail(DaycareGroupVO daycareGroupVO) throws TTKException;
	public int saveInvGroupDetail(InvestigationGroupVO investigationGroupVO) throws TTKException;
	
	/**
	 * This method saves the Critical Group information
	 * @param criticalGroupVO  CriticalGroupVO contains critical ICD/PCS Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int saveCriticalGroupDetail(CriticalGroupVO criticalGroupVO) throws TTKException;
	
	
	/**
	 * This method Deletes the Daycare Group(s).
	 * @param alGroupList ArrayList object which contains the daycar group id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGroup(ArrayList alGroupList) throws TTKException;
	public int deleteInvGroup(ArrayList alGroupList) throws TTKException;
	
	/**
	 * This method Deletes the Critical Group(s).
	 * @param alCriticalGroupList ArrayList object which contains the critical group id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int deleteCriticalGroup(ArrayList alCriticalGroupList) throws TTKException;
	
	
	
	/**
	 * This method returns the Arraylist of ProcedureDetailVO's  which contains Daycare Group Procedure details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ProcedureDetailVO object which contains Daycare Group Procedure details
	 * @exception throws TTKException
	 */
	public ArrayList getProcedureList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns the Arraylist of CriticalProcedureDetailVO's  which contains Critical Group Procedure details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalProcedureDetailVO object which contains Critical Group Procedure details
	 * @exception throws TTKException
	 */
	
	public ArrayList getCriticalProcedureList(ArrayList alSearchCriteria) throws TTKException;
	
	//added for KOC-1273
	/**
	 * This method returns the Arraylist of CriticalICDDetailVO's  which contains Critical ICD Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalICDDetailVO object which contains Critical ICD Group details
	 * @exception throws TTKException
	 */
	
	public ArrayList getCriticalICDList(ArrayList alSearchCriteria) throws TTKException;	
	
	/**
	 * This method associate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateProcedure(ArrayList alProcedureList) throws TTKException;
	
	/**
	 * This method associate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int associateCriticalProcedure(ArrayList alProcedureList)throws TTKException; 
	
	/**
	 * This method associate the ICD(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the ICD sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateCriticalICD(ArrayList alCriticalICDList)throws TTKException;
	
	
	/**
	 * This method DisAssociate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int disAssociateProcedure(ArrayList alProcedureList) throws TTKException;
	
	/**
	 * This method DisAssociate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int disAssociateCriticalProcedure (ArrayList alProcedureList) throws TTKException;
	/**
	 * This method DisAssociate the ICD(s) to the Group ID.
	 * @param alCriticalICDList ArrayList object which contains the icd sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int disAssociateCriticalICD (ArrayList alCriticalICDList) throws TTKException;
	
	/**
	 * This method is used to save ICD Code details
	 * @return int
	 * @throws TTKException
	 */
	public int addUpdateICDDetails(ICDCodeVO icdCodeVO) throws TTKException;
	
	/**
	 * This method is used to fetch the ICD Code details for edit
	 * @param pedCode
	 * @return ICDCodeVO
	 * @throws TTKException
	 */
	public ICDCodeVO selectICD(Long pedCode) throws TTKException;
	
	/**
	 * This method returns the Arraylist of PolicyListVO
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyListVO object which contains Policy Type details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method returns an integer, a positive value if policy type changed to Non-Floater
	 * @param alChangeToPnf ArrayList object which contains 'policy seq id' and 'added by' values 
	 * @return integer which is rows processed
	 * @exception throws TTKException
	 */
	public int changeToNonFloater(ArrayList alChangeToPnf) throws TTKException;
	
	/**
	 * This method returns an integer, a positive value if policy type changed to Floater
	 * @param alChangeToPnf ArrayList object which contains 'policy seq id' and 'added by' values 
	 * @return integer which is rows processed
	 * @exception throws TTKException
	 */
	public int changeToFloater(ArrayList alChangeToPf) throws TTKException;
	
	/**
	 * This method returns the Arraylist of NotifyDetailVO's  which contains Notification details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of NotifyDetailVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public ArrayList getNotifyList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method saves the Notification details
	 * @param notifyDetailVO NotifyDetailVO object which contains the search criteria
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveNotificationInfo(NotifyDetailVO notifyDetailVO) throws TTKException;
	
	/**
	 * This method returns the NotifyDetailVO which contains Notification details
	 * @param strMsgID String object which contains the Message ID
	 * @return notifyDetailVO of NotifyDetailVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public NotifyDetailVO getNotificationInfo(String strMsgID) throws TTKException;
	
	/**
	 * This method returns the Arraylist of CustomizeConfigVO's  which contains Notification details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CustomizeConfigVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public ArrayList getCustomConfigList(ArrayList alSearchCriteria) throws TTKException;
	
	/**
	 * This method saves the Notification Customized Config details
	 * @param customizeConfigVO CustomizeConfigVO object which contains the search criteria
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCustomConfigInfo(CustomizeConfigVO customizeConfigVO) throws TTKException;
	
	/**
	 * This method returns the CustomizeConfigVO which contains Notification Customized Config details
	 * @param lngCustConfigSeqID Long object which contains the Cust Config Seq ID
	 * @return customizeConfigVO of CustomizeConfigVO object which contains Customized Config details
	 * @exception throws TTKException
	 */
	public CustomizeConfigVO getCustomConfigInfo(Long lngCustConfigSeqID) throws TTKException;
	
	/**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallpendingByBranch() throws TTKException;
    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the ProcedureDetailVO which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureDetailVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getPcsList(ArrayList<Object> alSearchCriteria) throws TTKException;
    
    /**
	 * This method returns the ProcedureDetailVO, which contains procedure details
	 * @param lngProcSeqID Procedure Seq ID
	 * @return ProcedureDetailVO object which contains procedure details
	 * @exception throws TTKException
	 */
    public ProcedureDetailVO getPcsInfo(long lngProcSeqID) throws TTKException;
    
    /**
     * This method saves the Procedure details
     * @param procedureDetailVO ProcedureDetailVO object which contains the Procedure Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int savePcsInfo(ProcedureDetailVO procedureDetailVO) throws TTKException;
    
    /**
     * This method returns the CustomizeConfigVO's  which contains Call pending details
     * @param strMRClaimsPendConfig MR Claims Pending Config
     * @return CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public CustomizeConfigVO getMrClaimsPendConfig(String strMRClaimsPendConfig) throws TTKException;
    
    /**
     * This method will update the policy DO/BO details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyDOBO(ArrayList<Object> alUpdateList) throws TTKException;
    
    /**
     * This method will update the policy period details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyPeriod(ArrayList<Object> alUpdateList) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the ClaimListVO which are populated from the database
     * @param strClaimNbr String object which contains the Claim Number
     * @return ArrayList of ClaimListVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getClaimReqamt(String strClaimNbr) throws TTKException;
    
    /**
     * This method saves the New Claims Requested Amount
     * @param claimListVO ClaimListVO object which contains the search criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveClaimReqAmt(ClaimListVO claimListVO) throws TTKException;
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallPendingByAttendedBy() throws TTKException;
    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, ArrayList strAddiParam, String strPrimaryMailID) throws TTKException;

    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertCustomerRecord(String strIdentifier, ArrayList strAddiParam, String strPrimaryMailID,String strFileName) throws TTKException;
   
    public Object[] uploadDhpoNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;
    
    public HashMap<String,byte[]> getPreAuthProccessedFiles() throws TTKException;
    public HashMap<String,byte[]> getClaimsProccessedFiles() throws TTKException;
    public HashMap<String,byte[]> getPersionProccessedFiles() throws TTKException;
    
    
    
    public Object[] updateDhpoUplodedFileStatus(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;
    

    /**
     * This method set the Dhpo New Transacton Details     
     * @param This method set  the DhpoWebServiceVO Dhpo New Transacton Details 
     * @return This method get the Dhpo New Transacton Details records    
     * @exception throws TTKException
     */ 

    public Object[] uploadDhpoGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;
    
  
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public List<DhpoWebServiceVO> getDhpoNewTransactonDetails() throws TTKException;
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
   
    public Object  saveBifurcationDetails(List<DhpoWebServiceVO> dhpoWebServiceVOs) throws TTKException;

    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public Object[] saveDhpoPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;


/**
 * This method get the Dhpo New Transacton Details records where bifurcation not done   
 * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
 * @exception throws TTKException
 */
public Object[] saveDhpoGlobalnetPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;

/**
 * This method get the Dhpo New Transacton Details records where bifurcation not done   
 * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
 * @exception throws TTKException
 */
public List<DhpoWebServiceVO> getDhpoGlobalnetPreAuthTransactonDetails() throws TTKException;


/**
 * This method check the given member id member exist or not  
 * @return This method return status   
 * @exception throws TTKException
 */

public String isMemberBelongs(String memID,String emirateID) throws TTKException;


/*public boolean remittanceAdviceFileDetail()throws TTKException;*/


public List<DhpoWebServiceVO> getRemittanceAdviceDHPOFileUpload() throws TTKException;

public int getRemittanceAdviceDHPOStatus(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;

/**
 * This method set the HAAD New Transacton Details     
 * @param This method set  the DhpoWebServiceVO HAAD New Transacton Details 
 * @return This method get the Dhpo New Transacton Details records    
 * @exception throws TTKException
 */ 

public Object[] uploadHaadGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;

 

/**
     * This method To Get Upload DHPO PreAuths     
     * @return TThis method To Get Upload DHPO PreAuths   Details 
     * @exception throws TTKException
     */ 

    public ArrayList<DhpoWebServiceVO> getDhpoVHPriorAuthorizationList() throws TTKException;

    /**
     * This method To Save Upload DHPO PreAuths Status Details    
     * @return TThis method To Get Updated details
     * @exception throws TTKException
     */ 
    
    public Object[] saveVHDhpoPreauthUploadDetails(ArrayList<DhpoWebServiceVO> dhpoWebServiceVOList) throws TTKException;

    public List<HaadWebServiceVo> getHaadFileUpload(ArrayList<String> allData) throws TTKException;
    public int getMemberUploadStatus(HaadWebServiceVo haadWebServiceVO) throws TTKException;
	public ProcedureDetailVO getOMANMemberUploadData() throws TTKException;
	public int cmaPayLoadBackUp(String stringEntity,String result,String transaction_Id,String SuccessFailRes,String errorMsg,String Ben_Ins_ID,String flag) throws TTKException;
	/**
	 * 
	 * @param memberResponse
	 * @return
	 * @throws TTKException
	 */
	public int saveOmanMemberUploadLogFile(String memberResponse)throws TTKException;
}