/**
 * @ (#) MaintenanceManagerBean.java Oct 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : MaintenanceManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.maintenance.MaintenanceDAOFactory;
import com.ttk.dao.impl.maintenance.MaintenanceDAOImpl;
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

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class MaintenanceManagerBean implements MaintenanceManager{
	
	private MaintenanceDAOFactory maintenanceDAOFactory = null;
	private MaintenanceDAOImpl maintenanceDAO = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getMaintenanceDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (maintenanceDAOFactory == null)
			{
				maintenanceDAOFactory = new MaintenanceDAOFactory();
			}//end of if (maintenanceDAOFactory == null)
			if(strIdentifier!=null)
			{
				return maintenanceDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
			{
				return null;
			}//end of else
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getPreAuthDAO(String strIdentifier)
	
	/**
	 * This method returns the Arraylist of DaycareGroupVO's  which contains Daycare Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of DaycareGroupVO object which contains Daycare Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getGroupList(alSearchCriteria);
	}//end of getGroupList(ArrayList alSearchCriteria)
	
	public ArrayList getInvGroupList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getInvGroupList(alSearchCriteria);
	}//end of getGroupList(ArrayList alSearchCriteria) koc11 koc 11
	
	
	/**
	 * This method returns the Arraylist of CriticalGroupVO's  which contains Critical ICD/PCS Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalGroupVO object which contains Critical Group details
	 * @exception throws TTKException
	 */
	
	public ArrayList getCriticalGroupList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCriticalGroupList(alSearchCriteria);
	}//end of getCriticalGroupList(ArrayList alSearchCriteria)
	
		
	/**
	 * This method returns the PreAuthDetailVO, which contains Daycare Group details
	 * @param strGroupID GroupID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return DaycareGroupVO object which contains Daycare Group details
	 * @exception throws TTKException
	 */
	public DaycareGroupVO getGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getGroupDetail(strGroupID,lngUserSeqID);
	}//end of getGroupDetail(String strGroupID,long lngUserSeqID)
	
	public InvestigationGroupVO getInvGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getInvGroupDetail(strGroupID,lngUserSeqID);
	}//end of getInvGroupDetail(String strGroupID,long lngUserSeqID) koc 11 koc11
	
	
	/**
	 * This method returns the PreAuthDetailVO, which contains Critical Group details
	 * @param strGroupID GroupID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return CriticalGroupVO object which contains Critical Group details
	 * @exception throws TTKException
	 */
	
	public CriticalGroupVO getCriticalGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCriticalGroupDetail(strGroupID,lngUserSeqID);
	}//end of getCriticalGroupDetail(String strGroupID,long lngUserSeqID)
	
	
	/**
	 * This method saves the Daycare Group information
	 * @param daycareGroupVO DaycareGroupVO contains Daycare Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveGroupDetail(DaycareGroupVO daycareGroupVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveGroupDetail(daycareGroupVO);
	}//end of saveGroupDetail(DaycareGroupVO daycareGroupVO)
	
	//koc11 koc 11
	public int saveInvGroupDetail(InvestigationGroupVO investigationGroupVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveInvGroupDetail(investigationGroupVO);
	}//end of saveGroupDetail(DaycareGroupVO daycareGroupVO)
	
	
	/**
	 * This method saves the Critical ICD/PCS Group information
	 * @param criticalGroupVO CriticalGroupVO contains Daycare Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int saveCriticalGroupDetail(CriticalGroupVO criticalGroupVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveCriticalGroupDetail(criticalGroupVO);
	}//end of saveCriticalGroupDetail(DaycareGroupVO daycareGroupVO)
	
	
	/**
	 * This method Deletes the Daycare Group(s).
	 * @param alGroupList ArrayList object which contains the daycar group id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGroup(ArrayList alGroupList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.deleteGroup(alGroupList);
	}//end of deleteGroup(ArrayList alGroupList)
	
	public int deleteInvGroup(ArrayList alGroupList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.deleteInvGroup(alGroupList);
	}//end of deleteGroup(ArrayList alGroupList)
	
	/**
	 * This method Deletes the Critical Group(s).
	 * @param alCriticalGroupList ArrayList object which contains the critical group id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int deleteCriticalGroup(ArrayList alCriticalGroupList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.deleteCriticalGroup(alCriticalGroupList);
	}//end of deleteGroup(ArrayList alGroupList)
	
	
	/**
	 * This method returns the Arraylist of ProcedureDetailVO's  which contains Daycare Group Procedure details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ProcedureDetailVO object which contains Daycare Group Procedure details
	 * @exception throws TTKException
	 */
	public ArrayList getProcedureList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getProcedureList(alSearchCriteria);
	}//end of getProcedureList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the Arraylist of CriticalProcedureDetailVO's  which contains Critical Group Procedure details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalProcedureDetailVO object which contains Critical Group Procedure details
	 * @exception throws TTKException
	 */
	
	public ArrayList getCriticalProcedureList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCriticalProcedureList(alSearchCriteria);
	}//end of getProcedureList(ArrayList alSearchCriteria)
	
	//added for KOC-1273
	/**
	 * This method returns the Arraylist of CriticalICDDetailVO's  which contains Critical ICD Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CriticalICDDetailVO object which contains Critical ICD Group details
	 * @exception throws TTKException
	 */
	public ArrayList getCriticalICDList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCriticalICDList(alSearchCriteria);
	}//end of getProcedureList(ArrayList alSearchCriteria)
	
	
	/**
	 * This method associate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateProcedure(ArrayList alProcedureList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.associateProcedure(alProcedureList);
	}//end of associateProcedure(ArrayList alProcedureList)

	/**
	 * This method associate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int associateCriticalProcedure(ArrayList alProcedureList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.associateCriticalProcedure(alProcedureList);
	}//end of associateProcedure(ArrayList alProcedureList)

	
	/**
	 * This method associate the ICD(s) to the Group ID.
	 * @param alCriticalICDList ArrayList object which contains the ICD sequence id's to be associated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int associateCriticalICD(ArrayList alCriticalICDList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.associateCriticalICD(alCriticalICDList);
	}//end of associateProcedure(ArrayList alProcedureList)

	
	/**
	 * This method DisAssociate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int disAssociateProcedure(ArrayList alProcedureList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.disAssociateProcedure(alProcedureList);
	}//end of disAssociateProcedure(ArrayList alProcedureList)
	
	/**
	 * This method DisAssociate the procedure(s) to the Group ID.
	 * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int disAssociateCriticalProcedure(ArrayList alProcedureList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.disAssociateCriticalProcedure(alProcedureList);
	}//end of disAssociateProcedure(ArrayList alProcedureList)
	
	/**
	 * This method DisAssociate the ICD(s) to the Group ID.
	 * @param alCriticalICDList ArrayList object which contains the icd sequence id's to be disassociated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int disAssociateCriticalICD(ArrayList alCriticalICDList) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.disAssociateCriticalICD(alCriticalICDList);
	}//end of disAssociateProcedure(ArrayList alProcedureList)
	
	
	/**
	 * This method saves the ICD Code details.
	 * @param icdCodeVO ICDCodeVO object which contains the ICD Code details
	 * @return ICDCodeVO
	 * @exception throws TTKException
	 */
	public int addUpdateICDDetails(ICDCodeVO icdCodeVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveICDDetails(icdCodeVO);    
	}//end of addUpdateICDDetails
	
	
	/**
	 * This method is used to fetch the ICD Code details for edit
	 * @param alSearchCriteria
	 * @return ArrayList of ICDCodeVO instances
	 * @throws TTKException
	 */
	public ICDCodeVO selectICD(Long pedCode) throws TTKException
	{
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.selectICD(pedCode);
	}
	
	/**
	 * This method returns the Arraylist of PolicyListVO
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyListVO object
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getPolicyList(alSearchCriteria);
	}//end of getPolicyList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns an integer, a positive value if policy type changed to Non-Floater
	 * @param alChangeToPnf ArrayList object which contains 'policy seq id' and 'added by' values 
	 * @return integer which is rows processed
	 * @exception throws TTKException
	 */
	public int changeToNonFloater(ArrayList alChangeToPnf) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.changeToNonFloater(alChangeToPnf);
	}//end of changeToNonFloater(ArrayList alSearchCriteria)
	
	/**
	 * This method returns an integer, a positive value if policy type changed to Floater
	 * @param alChangeToPnf ArrayList object which contains 'policy seq id' and 'added by' values 
	 * @return integer which is rows processed
	 * @exception throws TTKException
	 */
	public int changeToFloater(ArrayList alChangeToPf) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.changeToFloater(alChangeToPf);
	}//end of changeToFloater(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the Arraylist of NotifyDetailVO's  which contains Notification details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of NotifyDetailVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public ArrayList getNotifyList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getNotifyList(alSearchCriteria);
	}//end of getNotifyList(ArrayList alSearchCriteria)
	
	/**
	 * This method saves the Notification details
	 * @param notifyDetailVO NotifyDetailVO object which contains the search criteria
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveNotificationInfo(NotifyDetailVO notifyDetailVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveNotificationInfo(notifyDetailVO);
	}//end of saveNotificationInfo(NotifyDetailVO notifyDetailVO) 
	
	/**
	 * This method returns the NotifyDetailVO which contains Notification details
	 * @param strMsgID String object which contains the Message ID
	 * @return notifyDetailVO of NotifyDetailVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public NotifyDetailVO getNotificationInfo(String strMsgID) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getNotificationInfo(strMsgID);
	}//end of getNotificationInfo(String strMsgID)
	
	/**
	 * This method returns the Arraylist of CustomizeConfigVO's  which contains Notification details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CustomizeConfigVO object which contains Notification details
	 * @exception throws TTKException
	 */
	public ArrayList getCustomConfigList(ArrayList alSearchCriteria) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCustomConfigList(alSearchCriteria);
	}//end of getCustomConfigList(ArrayList alSearchCriteria)
	
	/**
	 * This method saves the Notification Customized Config details
	 * @param customizeConfigVO CustomizeConfigVO object which contains the search criteria
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCustomConfigInfo(CustomizeConfigVO customizeConfigVO) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveCustomConfigInfo(customizeConfigVO);
	}//end of saveCustomConfigInfo(CustomizeConfigVO customizeConfigVO)
	
	/**
	 * This method returns the CustomizeConfigVO which contains Notification Customized Config details
	 * @param lngCustConfigSeqID Long object which contains the Cust Config Seq ID
	 * @return customizeConfigVO of CustomizeConfigVO object which contains Customized Config details
	 * @exception throws TTKException
	 */
	public CustomizeConfigVO getCustomConfigInfo(Long lngCustConfigSeqID) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCustomConfigInfo(lngCustConfigSeqID);
	}//end of getCustomConfigInfo(Long lngCustConfigSeqID)
	
	/**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallpendingByBranch() throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCallpendingByBranch();
    }//end of getCustomConfigList()
    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	maintenanceDAO.insertRecord(strIdentifier, strAddiParam, strPrimaryMailID);
    }//end of insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) 
    
    /**
     * This method returns the ArrayList, which contains the ProcedureDetailVO which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureDetailVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getPcsList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getPcsList(alSearchCriteria);
    }//end of getPcsList(ArrayList<Object> alSearchCriteria)
    
    /**
	 * This method returns the ProcedureDetailVO, which contains procedure details
	 * @param lngProcSeqID Procedure Seq ID
	 * @return ProcedureDetailVO object which contains procedure details
	 * @exception throws TTKException
	 */
    public ProcedureDetailVO getPcsInfo(long lngProcSeqID) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getPcsInfo(lngProcSeqID);
    }//end of getPcsInfo(long lngProcSeqID)
    
    /**
     * This method saves the Procedure details
     * @param procedureDetailVO ProcedureDetailVO object which contains the Procedure Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int savePcsInfo(ProcedureDetailVO procedureDetailVO) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.savePcsInfo(procedureDetailVO);
    }//end of savePcsInfo(ProcedureDetailVO procedureDetailVO)
    
    /**
     * This method returns the CustomizeConfigVO's  which contains Call pending details
     * @param strMRClaimsPendConfig MR Claims Pending Config
     * @return CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public CustomizeConfigVO getMrClaimsPendConfig(String strMRClaimsPendConfig) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getMrClaimsPendConfig(strMRClaimsPendConfig);
    }//end of CustomizeConfigVO getMrClaimsPendConfig(String strMRClaimsPendConfig)
    
    /**
     * This method will update the policy DO/BO details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyDOBO(ArrayList<Object> alUpdateList) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.updatePolicyDOBO(alUpdateList);
    }//end of int updatePolicyDOBO(ArrayList<Object> alUpdateList)
    
    /**
     * This method will update the policy period details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyPeriod(ArrayList<Object> alUpdateList) throws TTKException {
	    maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.updatePolicyPeriod(alUpdateList);
	}//end of int updatePolicyPeriod(ArrayList<Object> alUpdateList)
    
    /**
     * This method returns the ArrayList, which contains the ClaimListVO which are populated from the database
     * @param strClaimNbr String object which contains the Claim Number
     * @return ArrayList of ClaimListVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getClaimReqamt(String strClaimNbr) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getClaimReqamt(strClaimNbr);
    }//end of getClaimReqamt(String strClaimNbr)
    
    /**
     * This method saves the New Claims Requested Amount
     * @param claimListVO ClaimListVO object which contains the search criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveClaimReqAmt(ClaimListVO claimListVO) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.saveClaimReqAmt(claimListVO);
    }//end of saveClaimReqAmt(ClaimListVO claimListVO)
    
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallPendingByAttendedBy() throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
		return maintenanceDAO.getCallPendingByAttendedBy();
    }//end of getCustomConfigList()
    
    
    /**
     * This method inserts record after generating the report in CustomerCallBack Scheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, ArrayList strAddiParam, String strPrimaryMailID) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	maintenanceDAO.insertRecord(strIdentifier, strAddiParam, strPrimaryMailID);
    }//end of insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) 
    
    /**
     * This method inserts record after generating the report in CustomerCallBack Scheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertCustomerRecord(String strIdentifier, ArrayList strAddiParam, String strPrimaryMailID,String strFileName) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	maintenanceDAO.insertCustomerRecord(strIdentifier, strAddiParam, strPrimaryMailID,strFileName);
    }//end of insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) 
   
    public Object[] uploadDhpoNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.uploadDhpoNewTransactonDetails(dhpoWebServiceVO);
    }//end of dhpoNewTransacton(HashMap<String,String> fileData) 

    public HashMap<String,byte[]> getPreAuthProccessedFiles() throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getPreAuthProccessedFiles();
    }//end of getPreAuthProccessedFiles()
    public HashMap<String,byte[]> getClaimsProccessedFiles() throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getClaimsProccessedFiles();
    }//end of getClaimsProccessedFiles() 
    public HashMap<String,byte[]> getPersionProccessedFiles() throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getPersionProccessedFiles();
    }//end of getPersionProccessedFiles()
    public Object[] updateDhpoUplodedFileStatus(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.updateDhpoUplodedFileStatus(dhpoWebServiceVO);
    }//end of updatePreAuthUplodedFileStatus()
    
    
    /**
     * This method set the Dhpo New Transacton Details     
     * @param This method set  the DhpoWebServiceVO Dhpo New Transacton Details 
     * @return This method get the Dhpo New Transacton Details records    
     * @exception throws TTKException
     */     
    public Object[] uploadDhpoGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.uploadDhpoGlobalNetNewTransactonDetails(dhpoWebServiceVO);
    }//end of uploadDhpoGlobalNetNewTransactonDetails()
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public List<DhpoWebServiceVO> getDhpoNewTransactonDetails() throws TTKException{

    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getDhpoNewTransactonDetails();
    }//getDhpoNewTransactonDetails()
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public Object saveBifurcationDetails(List<DhpoWebServiceVO> dhpoWebServiceVOs) throws TTKException{
    
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.saveBifurcationDetails(dhpoWebServiceVOs);
    }//saveBifurcationDetails()
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public Object[] saveDhpoPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.saveDhpoPreauthDownloadDetails(dhpoWebServiceVO);
    }//saveDhpoPreauthDownloadDetails()
   
    
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public Object[] saveDhpoGlobalnetPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.saveDhpoGlobalnetPreauthDownloadDetails(dhpoWebServiceVO);
    }//saveDhpoGlobalnetPreauthDownloadDetails()
   

    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public List<DhpoWebServiceVO> getDhpoGlobalnetPreAuthTransactonDetails() throws TTKException{

    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getDhpoGlobalnetPreAuthTransactonDetails();
    }//getDhpoNewTransactonDetails()
    
    /**
     * This method check the given member id member exist or not  
     * @return This method return status   
     * @exception throws TTKException
     */
    public String isMemberBelongs(String memID,String emirateID) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.isMemberBelongs(memID,emirateID);
    }
    
 /*   public boolean remittanceAdviceFileDetail()throws TTKException
    {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.remittanceAdviceFileDetail();
    }
    */
    public List<DhpoWebServiceVO> getRemittanceAdviceDHPOFileUpload() throws TTKException
    {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
    	return maintenanceDAO.getRemittanceAdviceDHPOFileUpload();
    }
   
    public int getRemittanceAdviceDHPOStatus(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
    	return maintenanceDAO.getRemittanceAdviceDHPOStatus(dhpoWebServiceVO);
    }
    
    
    
    /**
     * This method set the Dhpo New Transacton Details     
     * @param This method set  the DhpoWebServiceVO Dhpo New Transacton Details 
     * @return This method get the Dhpo New Transacton Details records    
     * @exception throws TTKException
     */     
    public Object[] uploadHaadGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.uploadHaadGlobalNetNewTransactonDetails(dhpoWebServiceVO);
    }//end of uploadHaadGlobalNetNewTransactonDetails()
   
    
    
    /**
     * This method To Get Upload DHPO PreAuths     
     * @return TThis method To Get Upload DHPO PreAuths   Details 
     * @exception throws TTKException
     */ 

    public ArrayList<DhpoWebServiceVO> getDhpoVHPriorAuthorizationList() throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.getDhpoVHPriorAuthorizationList();
    }
    
    /**
     * This method To Save Upload DHPO PreAuths Status Details    
     * @return TThis method To Get Updated details
     * @exception throws TTKException
     */ 

    public Object[] saveVHDhpoPreauthUploadDetails(ArrayList<DhpoWebServiceVO> dhpoWebServiceVOList) throws TTKException{
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");
    	return maintenanceDAO.saveVHDhpoPreauthUploadDetails(dhpoWebServiceVOList);
  
    }

    public List<HaadWebServiceVo> getHaadFileUpload(ArrayList<String> allData) throws TTKException
    {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
    	return maintenanceDAO.getMemberHaadUploadXML(allData);
    }
    
    public int getMemberUploadStatus(HaadWebServiceVo haadWebServiceVO) throws TTKException {
    	maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
    	return maintenanceDAO.getMemberUploadHaadStatus(haadWebServiceVO);
    }
	public ProcedureDetailVO getOMANMemberUploadData() throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
	return maintenanceDAO.getOMANMemberUploadData();
	}
	public int saveOmanMemberUploadLogFile(String memberResponse)throws TTKException{
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
		return maintenanceDAO.saveOmanMemberUploadLogFile(memberResponse);
	}
	public int cmaPayLoadBackUp(String stringEntity,String result,String transaction_Id,String SuccessFailRes,String errorMsg,String Ben_Ins_ID,String flag) throws TTKException {
		maintenanceDAO = (MaintenanceDAOImpl)this.getMaintenanceDAO("maintenance");	
	return maintenanceDAO.cmaPayLoadBackUp(stringEntity,result,transaction_Id,SuccessFailRes,errorMsg,Ben_Ins_ID,flag);
	}
	
}//end of MaintenanceManagerBean