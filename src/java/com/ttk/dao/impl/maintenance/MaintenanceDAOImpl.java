/**
 * @ (#) MaintenanceDAOImpl.java Oct 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : MaintenanceDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.maintenance;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.xml.bind.DatatypeConverter;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.util.Base64;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;

import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.CriticalICDDetailVO;
import com.ttk.dto.administration.CriticalProcedureDetailVO;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.common.HaadWebServiceVo;
import com.ttk.dto.empanelment.NotifyDetailVO;
import com.ttk.dto.maintenance.ClaimListVO;
//added for KOC-1273
import com.ttk.dto.maintenance.CriticalGroupVO;
import com.ttk.dto.maintenance.CustomizeConfigVO;
import com.ttk.dto.maintenance.DaycareGroupVO;
import com.ttk.dto.maintenance.ICDCodeVO;
import com.ttk.dto.maintenance.InvestigationGroupVO;
import com.ttk.dto.maintenance.PolicyListVO;
//ended
public class MaintenanceDAOImpl implements BaseDAO, Serializable{
	
	private static Logger log = Logger.getLogger(MaintenanceDAOImpl.class );
	
	private static final String strDaycareGroupList = "{CALL MAINTENANCE_PKG.SELECT_DAYCARE_LIST(?,?,?,?,?,?,?)}";
	private static final String strInvestigationGroupList = "{CALL MAINTENANCE_PKG.SELECT_AGENCY_LIST(?,?,?,?,?,?,?)}"; //koc 11 koc11
	//private static final String strCriticalGroupList = "{CALL MAINTENANCE_PKG.SELECT_DAYCARE_LIST(?,?,?,?,?,?,?)}";
	
	//added for KOC-1273 - This is for getting selected records from critical group table
	private static final String strCriticalGroupList = "{CALL MAINTENANCE_PKG.SELECT_CRITICAL_LIST(?,?,?,?,?,?,?)}";

	//added for KOC-1273 - This is for saving added Critical Group Name and Critical Group description
	private static final String strSaveCriticalGroupDetail = "{CALL MAINTENANCE_PKG.SAVE_CRITICAL_GROUP(?,?,?,?,?)}";

	
	private static final String strGetDaycareGroupDetail = "{CALL MAINTENANCE_PKG.SELECT_DAYCARE_GROUP(?,?,?)}";
	private static final String strGetInvestigationGroupDetail = "{CALL MAINTENANCE_PKG.SELECT_AGENCY_DETAILS(?,?)}";  // koc 11 koc11 
	
	//added for KOC-1273 - procedure name needs to be changed according to critical group(done)
	private static final String strGetCriticalGroupDetail = "{CALL MAINTENANCE_PKG.SELECT_CRITICAL_GROUP(?,?,?)}";
	
	private static final String strSaveGroupDetail = "{CALL MAINTENANCE_PKG.SAVE_DAYCARE_GROUP(?,?,?,?,?)}";
	private static final String strSaveInvGroupDetail = "{CALL MAINTENANCE_PKG.SAVE_AGENCY_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteGroup = "{CALL MAINTENANCE_PKG.DELETE_DAYCARE_GROUP(?,?,?)}";
	private static final String strDeleteInvGroup = "{CALL MAINTENANCE_PKG.DELETE_AGENCY(?,?,?)}"; //koc 11 koc11  
	
	
	//added for KOC-1273 -- This Procedure is used to delete the critical Group
	
	private static final String strDeleteCriticalGroup = "{CALL MAINTENANCE_PKG.DELETE_CRITICAL_GROUP(?,?,?)}";
	
	private static final String strProcedureList = "{CALL MAINTENANCE_PKG.SELECT_PROCEDURE_LIST(?,?,?,?,?,?,?,?,?,?)}";
	
	
	//added for KOC-1273 - selecting the PCS code depending upon the search criteria (Procedure yet to be implemented)
	private static final String strCriticalProceList = "{CALL MAINTENANCE_PKG.SELECT_CRITICAL_PROC_LIST(?,?,?,?,?,?,?,?,?,?)}";
	
	//added for KOC-1273 - selecting the ICD Code depending upon the search criteria
	private static final String strCriticalICDList = "{CALL MAINTENANCE_PKG.SELECT_CRITICAL_ICD_LIST(?,?,?,?,?,?,?,?,?,?)}";
	
	
	private static final String strAssociateProcedure = "{CALL MAINTENANCE_PKG.ASSOCIATE_DAYCARE_PROCS(?,?,?,?)}";
	
	
	//added for KOC-1273 - associating the Critical Procedure from the unassociated List
	private static final String strAssociateCriticalProcedure = "{CALL MAINTENANCE_PKG.ASSOCIATE_CRITICAL_PROCS(?,?,?,?)}";
	
	
	//added for KOC-1273 - associating the critical ICD's from the unassociate List
	private static final String strAssociateCriticalICD = "{CALL MAINTENANCE_PKG.ASSOCIATE_CRITICAL_ICDS(?,?,?,?)}";
	
	
	private static final String strDisAssociateProcedure = "{CALL MAINTENANCE_PKG.DEASSOCIATE_DAYCARE_PROCS(?,?,?,?)}";
	
	//added for KOC-1273 - DisAssociating the Critical Procedure from associated List
	private static final String strDisAssociateCriticalProcedure = "{CALL MAINTENANCE_PKG.DEASSOCIATE_CRITICAL_PROCS(?,?,?,?)}";
	
	
	//added for KOC-1273 - DisAssociating the Critical ICD from associate list
	private static final String strDisAssociateCriticalICD = "{CALL MAINTENANCE_PKG.DEASSOCIATE_CRITICAL_ICDS(?,?,?,?)}";
	
	// Added for ICD Screen implementation by UNNI V MANA on 15-05-2008
	private static final String strSaveICDDetail = "{CALL MAINTENANCE_PKG.save_ped_code(?,?,?,?,?,?,?)}";
//	private static final String strSelectICDList = "{CALL MAINTENANCE_PKG.select_icd_list (?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectICD     = "{CALL MAINTENANCE_PKG.select_icd(?,?)}";
	private static final String strPolicyList = "{CALL MAINTENANCE_PKG.SELECT_POLICY_LIST(?,?,?,?)}";
	private static final String strChangeToNonFloater = "{CALL MAINTENANCE_PKG.CHANGE_TO_PNF(?,?,?)}";
	private static final String strChangeToFloater = "{CALL MAINTENANCE_PKG.CHANGE_TO_PFL(?,?,?)}";
	private static final String strSelectNotifyList = "{CALL MAINTENANCE_PKG_SELECT_NOTIFY_LIST(?,?,?,?,?)}";
	private static final String strSaveNotificationInfo = "{CALL MAINTENANCE_PKG_SAVE_NOTIFICATION_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectNotificationInfo = "{CALL MAINTENANCE_PKG_SELECT_NOTIFICATION_INFO(?,?)}";
	private static final String strSelectCustomConfigList = "{CALL MAINTENANCE_PKG.SELECT_CUSTOM_CONFIG_LIST(?,?,?,?,?,?)}"; 
	
	private static final String strSaveCustomConfigInfo = "{CALL MAINTENANCE_PKG.SAVE_CUSTOM_CONFIG_INFO(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectCustomConfigInfo = "{CALL MAINTENANCE_PKG.SELECT_CUSTOM_CONFIG_INFO(?,?)}";
	private static final String strPcsList = "{CALL MAINTENANCE_PKG.SELECT_PCS_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strPcsDetail = "{CALL MAINTENANCE_PKG.SELECT_PCS(?,?)}";
	private static final String strSavePcsDetail = "{CALL MAINTENANCE_PKG.SAVE_PCS_CODE(?,?,?,?,?,?,?)}";	
	
//	call pending procedures
	private static final String strGetCallpendingByBranch = "{CALL SCHEDULE_PKG.GET_CALLPENDING_BY_BRANCH(?)}";
	private static final String strGetCallpendingByBranchRpt = "{CALL SCHEDULE_PKG.GET_CALLPENDING_BRANCH_REPORT(?,?,?)}";
	private static final String strProcFormMessage ="{CALL GENERATE_MAIL_PKG.PROC_FORM_MESSAGE(?,?,?,?,?,?)}" ;
	private static final String strProcCustomerFormMessage ="{CALL GENERATE_MAIL_PKG.PROC_FORM_MESSAGE(?,?,?,?,?,?,?,?)}" ;
	
	private static final String strGetMRClaimPendConfig ="{CALL SCHEDULE_PKG.GET_MR_CLAIM_PENDING_CONFIG(?,?)}" ;
	private static final String strChgPolicyDOBO = "{CALL MAINTENANCE_PKG.INS_COMP_CHANGE(?,?,?)}";
	private static final String strChgPolicyPeriod = "{CALL MAINTENANCE_PKG.POLICY_PERIOD_CHANGE(?,?,?,?,?)}";
	
	//Maintenance Claims procedures -10th August 2010
	private static final String strSelectClaimReqamt = "{CALL MAINTENANCE_PKG.SELECT_CLAIM_REQAMT(?,?)}";
	private static final String strSaveClaimReqAmt = "{CALL MAINTENANCE_PKG.SAVE_CLAIM_REQAMOUNT(?,?,?,?,?)}";
	
	//intX
	private static final String strGetCallpendingByAttendBy	=	"{CALL SCHEDULE_PKG.PROC_GEN_CALL_PENDING_REPORT(?)}";
	//private static final String strProcPreAuthUpload	=	"{CALL Pat_xml_load_pkg.load_preauth_request(?)}";
	
	//start
	private static final String strDHPOPreAuthUpload	=	"{CALL PAT_XML_LOAD_PKG.LOAD_PRIOR_REQUEST(?,?,?,?)}";
	private static final String strDHPONewTransaction	=	"{CALL CLM_XML_LOAD_PKG_SAVE_DHPO_NEW_TRANSACTIONS(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDHPOGlobalNetNewTransaction	="{CALL CLM_XML_LOAD_PKG_SAVE_GN_MAIN_TRANSACTIONS(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetDHPOGlobalNetNewTransaction	="{CALL CLM_XML_LOAD_PKG_SELECT_GN_MAIN_TRANSACTIONS(?)}";
	private static final String strSaveBifurcationDetails	         ="{CALL CLM_XML_LOAD_PKG_SAVE_GN_VIDAL_TRANSACTIONS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDHPOPreauthDownload	             ="{CALL PAT_XML_LOAD_PKG_SAVE_PAT_DHPO_TRANSACTIONS(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDHPOGlobalnetPreauthDownload	             ="{CALL PAT_XML_LOAD_PKG_SAVE_GN_PAT_TRANSACTIONS(?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	private static final String strGetDHPOGlobalNetPreAuthTransactionDetails	="{CALL PAT_XML_LOAD_PKG.SELECT_GN_PAT_TRANSACTIONS(?)}";
	private static final String strisMemberExist	="{CALL CLM_XML_LOAD_PKG_GN_VALID_MEMBER(?,?,?)}";
	private static final String strremittancefiledetail	="{CALL my_gene_ra_hosp_wise}";
	private static final String strGetRemitanceDHPOFileUpload	="{CALL RA_AUTOMATION_DHPO_WS_RA_DHPO_UPDATE(?,?,?,?,?,?,?,?)}";
	private static final String strRemitanceDHPOFileUpload	="{CALL RA_AUTOMATION_DHPO_WS_RA_DHPO_UPLOAD(?)}";
	
	private static final String strGetVHDHPOPreAuthUploadedDetails	="{CALL PAT_XML_LOAD_PKG_SELECT_AUTO_UPLOAD_PAT_DTLS(?)}";
	private static final String strSaveVHDHPOPreAuthUploadedDetails	="{CALL PAT_XML_LOAD_PKG_SAVE_AUTO_UPLOAD_PAT_LOGS(?,?,?,?,?,?,?)}";
	private static final String strOmanLogDetails	="{CALL intx.update_oman_responce(?::JSON)}";
	 private static final String strgetOMANMemberData="{CALL INTX.OMAN_MEM_UPLOAD(?)}";
	private static final String strSaveCMAEnrollData	="{CALL intx.policy_enrollment_pkg_save_json_response(?,?,?,?,?,?,?)}";
	
	//end
	
	/**
     * This method returns the Arraylist of DaycareGroupVO's  which contains Daycare Group details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DaycareGroupVO object which contains Daycare Group details
     * @exception throws TTKException
     */
    public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		DaycareGroupVO daycareGroupVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDaycareGroupList);
			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//GROUP_NAME
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));//SORT_VAR
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));//SORT_ORDER
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));//START_NUM
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));//END_NUM
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//UPDATED_BY
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			
			if(rs != null){
				while(rs.next()){
					daycareGroupVO = new DaycareGroupVO();

					daycareGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("DAY_CARE_GROUP_ID")));
					daycareGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					daycareGroupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					
					alResultList.add(daycareGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGroupList(ArrayList alSearchCriteria)
    
    
    /**added for KOC-1273
     * This method returns the Arraylist of CriticalGroupVO's  which contains Critical ICD/CS Group details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CriticalGroupVO object which contains Critical ICD/PCS Group details
     * @exception throws TTKException
     */
    public ArrayList getCriticalGroupList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CriticalGroupVO criticalGroupVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCriticalGroupList);
			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//GROUP_NAME
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));//SORT_VAR
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));//SORT_ORDER
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));//START_NUM
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));//END_NUM
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//UPDATED_BY
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			
			if(rs != null){
				while(rs.next()){
					criticalGroupVO = new CriticalGroupVO();

					criticalGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("CRITICAL_GROUP_ID")));
					criticalGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					criticalGroupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					
					alResultList.add(criticalGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCriticalGroupList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCriticalGroupList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCriticalGroupList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCriticalGroupList(ArrayList alSearchCriteria)
    
	//koc 11 koc11 s
    public ArrayList getInvGroupList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		InvestigationGroupVO investigationGroupVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInvestigationGroupList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//aGENCY_NAME
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));//SORT_VAR
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));//SORT_ORDER
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));//START_NUM
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));//END_NUM
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//UPDATED_BY
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			
			if(rs != null){
				while(rs.next()){
					investigationGroupVO = new InvestigationGroupVO();

					investigationGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("INVEST_AGNCY_SEQ_ID")));
					investigationGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("AGENCY_NAME")));
					//daycareGroupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					
					alResultList.add(investigationGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)	
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGroupList(ArrayList alSearchCriteria)
    //koc 11 koc11 e
    
    /**
     * This method returns the PreAuthDetailVO, which contains Daycare Group details
     * @param strGroupID GroupID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return DaycareGroupVO object which contains Daycare Group details
     * @exception throws TTKException
     */
    public DaycareGroupVO getGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	DaycareGroupVO daycareGroupVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDaycareGroupDetail);
    		
    		cStmtObject.setString(1,strGroupID);
    		cStmtObject.setLong(2,lngUserSeqID);
    		cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(rs != null){
				while(rs.next()){
					daycareGroupVO = new DaycareGroupVO();
					
					daycareGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("DAY_CARE_GROUP_ID")));
					daycareGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					daycareGroupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupDetail()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupDetail()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupDetail()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return daycareGroupVO;
    }//end of getGroupDetail(String strGroupID,long lngUserSeqID)
    
    
    /**added for KOC-1273
     * This method returns the PreAuthDetailVO, which contains Daycare Group details
     * @param strGroupID GroupID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return DaycareGroupVO object which contains Daycare Group details
     * @exception throws TTKException
     */
    public CriticalGroupVO getCriticalGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	CriticalGroupVO criticalGroupVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCriticalGroupDetail);
    		
    		cStmtObject.setString(1,strGroupID);
    		cStmtObject.setLong(2,lngUserSeqID);
    		cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(rs != null){
				while(rs.next()){
					criticalGroupVO = new CriticalGroupVO();
					
					criticalGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("CRITICAL_GROUP_ID")));
					criticalGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					criticalGroupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCriticalGroupDetail()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCriticalGroupDetail()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCriticalGroupDetail()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return criticalGroupVO;
    }//end of getCriticalGroupDetail(String strGroupID,long lngUserSeqID)

	public InvestigationGroupVO getInvGroupDetail(String strGroupID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	InvestigationGroupVO investigationGroupVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetInvestigationGroupDetail);
    		
    		cStmtObject.setString(1,strGroupID);
    		//cStmtObject.setLong(2,lngUserSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			
			if(rs != null){
				while(rs.next()){
					investigationGroupVO = new InvestigationGroupVO();
					
					investigationGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("INVEST_AGNCY_SEQ_ID")));
					investigationGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("AGENCY_NAME")));
				//	investigationGroupVO.setInvActiveYN(TTKCommon.checkNull(rs.getString("AGENCY_EMPANELED_DATE")));agencyEmpanelDate, setAgencyEmpanelTime, 
					if(rs.getString("AGENCY_EMPANELED_DATE") != null){
						investigationGroupVO.setAgencyEmpanelDate(new Date(rs.getTimestamp("AGENCY_EMPANELED_DATE").getTime()));
						investigationGroupVO.setAgencyEmpanelTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("AGENCY_EMPANELED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("AGENCY_EMPANELED_DATE").getTime())).split(" ")[1]:"");
						investigationGroupVO.setAgencyEmpanelDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("AGENCY_EMPANELED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("AGENCY_EMPANELED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("AGENCY_EMPANELED_DATE") != null)
					investigationGroupVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					investigationGroupVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					investigationGroupVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					investigationGroupVO.setStateTypeId(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
					investigationGroupVO.setCityTypeId(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
					investigationGroupVO.setPin_code(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					investigationGroupVO.setCountryId(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
					investigationGroupVO.setOfficePhoneNo1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					investigationGroupVO.setOfficePhoneNo2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					investigationGroupVO.setMobileNo(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					investigationGroupVO.setFaxNo(TTKCommon.checkNull(rs.getString("FAX_NO")));
					investigationGroupVO.setEmail1(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					investigationGroupVO.setEmail2(TTKCommon.checkNull(rs.getString("SECONDARY_EMAIL_ID")));
					//investigationGroupVO.set(TTKCommon.checkNull(rs.getString("ADDED_BY")));
					investigationGroupVO.setInvActiveYN(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
                }//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupDetail()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupDetail()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupDetail()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return investigationGroupVO;
    }//end of getGroupDetail(String strGroupID,long lngUserSeqID)
    
    /**
	 * This method saves the Daycare Group information
	 * @param daycareGroupVO DaycareGroupVO contains Daycare Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveGroupDetail(DaycareGroupVO daycareGroupVO) throws TTKException {
		int iResult =0;
	   	Connection conn = null;
	   	CallableStatement cStmtObject=null;
	   	String strGroupID = "";
	   	try{
	   		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveGroupDetail);
    		
    		if(daycareGroupVO.getGroupID() != null){
    			cStmtObject.setString(1,daycareGroupVO.getGroupID());
    		}//end of if(daycareGroupVO.getGroupID() != null)
    		else{
    			cStmtObject.setString(1,null);
    		}//end of else
    		
    		cStmtObject.setString(2,daycareGroupVO.getGroupName());
    		cStmtObject.setString(3,daycareGroupVO.getGroupDesc());
    		cStmtObject.setLong(4,daycareGroupVO.getUpdatedBy());
    		cStmtObject.registerOutParameter(5,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.VARCHAR);
    		cStmtObject.execute();
    		iResult = cStmtObject.getInt(5);
    		strGroupID = cStmtObject.getString(1);
    		log.debug("strGroupID value is :"+strGroupID);
    	}//end of try
	   	catch (SQLException sqlExp) 
    	{ 
    		throw new TTKException(sqlExp, "maintenancerules");
    	}//end of catch (SQLException sqlExp) 
    	catch (Exception exp) 
    	{
    		throw new TTKException(exp, "maintenancerules");
    	}//end of catch (Exception exp)
    	finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveGroupDetail()",sqlExp);
        			throw new TTKException(sqlExp, "maintenancerules");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveGroupDetail()",sqlExp);
        				throw new TTKException(sqlExp, "maintenancerules");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenancerules");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}//end of saveGroupDetail(DaycareGroupVO daycareGroupVO)
	
	public int saveInvGroupDetail(InvestigationGroupVO investigationGroupVO) throws TTKException {
		int iResult =0;
	   	Connection conn = null;
	   	CallableStatement cStmtObject=null;
	   	String strGroupID = "";
	   	try{
	   		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveInvGroupDetail);
    		log.info(investigationGroupVO.toString()+"--------------------start---- :"+iResult);
    		if(investigationGroupVO.getGroupID() != null){
    			cStmtObject.setString(1,investigationGroupVO.getGroupID());
    		}//end of if(daycareGroupVO.getGroupID() != null)
    		else{
    			cStmtObject.setString(1,null);
    		}//end of else
    		
    		cStmtObject.setString(2,investigationGroupVO.getGroupName());
    		log.info(investigationGroupVO.getGroupID()+"-----------------"+investigationGroupVO.getGroupName()+"----------------"+investigationGroupVO.getAgencyEmpanelDate());
    		//cStmtObject.setString(3,investigationGroupVO.getAgencyEmpanelDate());
    		 if(investigationGroupVO.getInvestStringDate() != null){
                 cStmtObject.setTimestamp(3,new Timestamp(TTKCommon.getOracleDateWithTime(investigationGroupVO.getInvestStringDate(),investigationGroupVO.getAgencyEmpanelTime(),investigationGroupVO.getAgencyEmpanelDay()).getTime()));
             }//end of if(investigationVO.getMarkedDate() != null)
             else{
                 cStmtObject.setTimestamp(3, null);
             }//end of else  		    		    		
    	//	cStmtObject.setTimestamp(3,new Timestamp(TTKCommon.getOracleDateWithTime(investigationGroupVO.getAgencyEmpanelDate(),investigationGroupVO.getAgencyEmpanelTime(),investigationGroupVO.getAgencyEmpanelDay()).getTime()));//MARKED_DATE    		
    		cStmtObject.setString(4,investigationGroupVO.getAddress1());
    		cStmtObject.setString(5,investigationGroupVO.getAddress2());
    		cStmtObject.setString(6,investigationGroupVO.getAddress3());
    		cStmtObject.setString(7,investigationGroupVO.getStateTypeId());
    		cStmtObject.setString(8,investigationGroupVO.getCityTypeId());
    		cStmtObject.setString(9,investigationGroupVO.getPin_code());
    		cStmtObject.setString(10,investigationGroupVO.getCountryId());
    		cStmtObject.setString(11,investigationGroupVO.getOfficePhoneNo1());
    		cStmtObject.setString(12,investigationGroupVO.getOfficePhoneNo2());
    		cStmtObject.setString(13,investigationGroupVO.getFaxNo());
    		cStmtObject.setString(14,investigationGroupVO.getMobileNo());
    		cStmtObject.setString(15,investigationGroupVO.getEmail1());
    		cStmtObject.setString(16,investigationGroupVO.getEmail2());  
    		cStmtObject.setString(17,investigationGroupVO.getInvActiveYN());  
    		cStmtObject.setLong(18,investigationGroupVO.getUpdatedBy());
    		//cStmtObject.registerOutParameter(5,Types.INTEGER);
            cStmtObject.registerOutParameter(19,Types.INTEGER);
    		cStmtObject.execute();
    		iResult = cStmtObject.getInt(19);
    		//strGroupID = cStmtObject.getString(1);
    		log.info("---------------end  :"+iResult);
    	}//end of try
	   	catch (SQLException sqlExp) 
    	{ 
    		throw new TTKException(sqlExp, "maintenancerules");
    	}//end of catch (SQLException sqlExp) 
    	catch (Exception exp) 
    	{
    		throw new TTKException(exp, "maintenancerules");
    	}//end of catch (Exception exp)
    	finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveGroupDetail()",sqlExp);
        			throw new TTKException(sqlExp, "maintenancerules");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveGroupDetail()",sqlExp);
        				throw new TTKException(sqlExp, "maintenancerules");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenancerules");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}//end of saveGroupDetail(DaycareGroupVO daycareGroupVO)
	
	/**added for KOC-1273
	 * This method saves the Critical ICD/PCS Group information
	 * @param criticalGroupVO CriticalGroupVO contains Daycare Group information
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCriticalGroupDetail(CriticalGroupVO criticalGroupVO) throws TTKException {
		int iResult =0;
	   	Connection conn = null;
	   	CallableStatement cStmtObject=null;
	   	String strGroupID = "";
	   	try{
	   		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveCriticalGroupDetail);
    		
    		if(criticalGroupVO.getGroupID() != null){
    			cStmtObject.setString(1,criticalGroupVO.getGroupID());
    		}//end of if(daycareGroupVO.getGroupID() != null)
    		else{
    			cStmtObject.setString(1,null);
    		}//end of else
    		
    		cStmtObject.setString(2,criticalGroupVO.getGroupName());
    		cStmtObject.setString(3,criticalGroupVO.getGroupDesc());
    		cStmtObject.setLong(4,criticalGroupVO.getUpdatedBy());
    		cStmtObject.registerOutParameter(5,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.VARCHAR);
    		cStmtObject.execute();
    		iResult = cStmtObject.getInt(5);
    		strGroupID = cStmtObject.getString(1);
    		log.debug("strGroupID value is :"+strGroupID);
    	}//end of try
	   	catch (SQLException sqlExp) 
    	{ 
    		throw new TTKException(sqlExp, "maintenancerules");
    	}//end of catch (SQLException sqlExp) 
    	catch (Exception exp) 
    	{
    		throw new TTKException(exp, "maintenancerules");
    	}//end of catch (Exception exp)
    	finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveCriticalGroupDetail()",sqlExp);
        			throw new TTKException(sqlExp, "maintenancerules");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveCriticalGroupDetail()",sqlExp);
        				throw new TTKException(sqlExp, "maintenancerules");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenancerules");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}//end of saveCriticalGroupDetail(CriticalGroupVO criticalGroupVO)
	
	
	
	/**
     * This method Deletes the Daycare Group(s).
     * @param alGroupList ArrayList object which contains the daycar group id's to be deleted
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int deleteGroup(ArrayList alGroupList) throws TTKException {
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	 if(alGroupList != null && alGroupList.size() > 0)
             {
                 conn = ResourceManager.getConnection();
                 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteGroup);
                 
                 cStmtObject.setString(1, (String)alGroupList.get(0));//string of daycare group id's which are separated with | as separator (Note: String should also end with | at the end)
                 cStmtObject.setLong(2, (Long)alGroupList.get(1));//user sequence id
                 cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
                 cStmtObject.execute();
                 iResult = cStmtObject.getInt(3);
             }//end of if(alGroupList != null && alGroupList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl deleteGroup()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl deleteGroup()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteGroup(ArrayList alGroupList)
	
	public int deleteInvGroup(ArrayList alGroupList) throws TTKException {
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	 if(alGroupList != null && alGroupList.size() > 0)
             {
                 conn = ResourceManager.getConnection();
                 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteInvGroup);
                 
                 cStmtObject.setString(1, (String)alGroupList.get(0));//string of daycare group id's which are separated with | as separator (Note: String should also end with | at the end)
                 cStmtObject.setLong(2, (Long)alGroupList.get(1));//user sequence id
                 cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
                 cStmtObject.execute();
                 iResult = cStmtObject.getInt(3);
             }//end of if(alGroupList != null && alGroupList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl deleteGroup()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl deleteGroup()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteGroup(ArrayList alGroupList)
    
    /**added for KOC-1273
     * This method Deletes the Critical Group(s).
     * @param alGroupList ArrayList object which contains the critical group id's to be deleted
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int deleteCriticalGroup(ArrayList alCriticalGroupList) throws TTKException {
    	
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	if(alCriticalGroupList != null && alCriticalGroupList.size() > 0)
            {
        		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteCriticalGroup);
                log.info("1---(String)alCriticalGroupList.get(0)--:"+(String)alCriticalGroupList.get(0));
                log.info("2---(Long)alCriticalGroupList.get(1)--:"+(Long)alCriticalGroupList.get(1));
                cStmtObject.setString(1, (String)alCriticalGroupList.get(0));//string of critical group id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(2, (Long)alCriticalGroupList.get(1));//user sequence id
                cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(3);
            }
        	
        }
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl deleteCriticalGroup()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl deleteCriticalGroup()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    
    	
    }
    
    /**
     * This method returns the Arraylist of ProcedureDetailVO's  which contains Daycare Group Procedure details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureDetailVO object which contains Daycare Group Procedure details
     * @exception throws TTKException
     */
    public ArrayList getProcedureList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ProcedureDetailVO procedureDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProcedureList);
			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//DAY_CARE_GROUP_ID
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//PROC_CODE
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//PROC_DESCRIPTION
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//SELECTION_TYPE
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));//SORT_VAR
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));//SORT_ORDER
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));//START_NUM
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));//END_NUM
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));//UPDATED_BY
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			
			if(rs != null){
				while(rs.next()){
					procedureDetailVO = new ProcedureDetailVO();
					
					if (rs.getString("PROC_SEQ_ID") != null){
	                    procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
	                procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
	                procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
					
	                alResultList.add(procedureDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getProcedureList(ArrayList alSearchCriteria)
    
    
    /**added for KOC-1273
     * This method returns the Arraylist of CriticalProcedureDetailVO's  which contains Critical Group Procedure details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CriticalProcedureDetailVO object which contains Critical Group Procedure details
     * @exception throws TTKException
     */
    public ArrayList getCriticalProcedureList(ArrayList alSearchCriteria) throws TTKException {
    	
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CriticalProcedureDetailVO criticalprocedureDetailVO = null;	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCriticalProceList);
    		cStmtObject.setString(1,(String)alSearchCriteria.get(0));//CRITICAL_GROUP_ID
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//PROC_CODE
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//PROC_DESCRIPTION
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//SELECTION_TYPE
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));//SORT_VAR
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));//SORT_ORDER
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));//START_NUM
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));//END_NUM
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));//UPDATED_BY
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null){
				while(rs.next()){					
					criticalprocedureDetailVO = new CriticalProcedureDetailVO();
					if (rs.getString("PROC_SEQ_ID") != null){
						criticalprocedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
					criticalprocedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
					criticalprocedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
					
	                alResultList.add(criticalprocedureDetailVO);
				}
			}
			return (ArrayList)alResultList;	
    	}
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCriticalProcedureList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCriticalProcedureList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCriticalProcedureList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }
    
    //added for KOC-1273
    
    /**
     * This method returns the Arraylist of CriticalICDDetailVO's  which contains Critical ICD Group details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CriticalICDDetailVO object which contains Critical ICD Group details
     * @exception throws TTKException
     */
    public ArrayList getCriticalICDList(ArrayList alSearchCriteria) throws TTKException {
    	
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CriticalICDDetailVO criticalICDDetailVO = null;	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCriticalICDList);
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		cStmtObject.setString(1,(String)alSearchCriteria.get(0));//CRITICAL_GROUP_ID
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//PROC_CODE
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//PROC_DESCRIPTION
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//SELECTION_TYPE
			cStmtObject.setString(5,"ICD_CODE");//SORT_VAR
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));//SORT_ORDER
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));//START_NUM
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));//END_NUM
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));//UPDATED_BY
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null){
				while(rs.next()){					
					criticalICDDetailVO = new CriticalICDDetailVO();
					if (rs.getString("PED_CODE_ID") != null){
						criticalICDDetailVO.setProcedureID(new Long(rs.getLong("PED_CODE_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
					criticalICDDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
					criticalICDDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("ped_description")));
					
	                alResultList.add(criticalICDDetailVO);
				}
			}
			return (ArrayList)alResultList;	
    	}
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCriticalICDList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCriticalICDList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCriticalICDList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }
   
    //ended
    
    
    /**
     * This method associate the procedure(s) to the Group ID.
     * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int associateProcedure(ArrayList alProcedureList) throws TTKException {
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	if(alProcedureList != null && alProcedureList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateProcedure);
                
                cStmtObject.setString(1, (String)alProcedureList.get(0));//DAY_CARE_GROUP_ID
                cStmtObject.setString(2, (String)alProcedureList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alProcedureList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
            }//end of if(alProcedureList != null && alProcedureList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl associateProcedure()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl associateProcedure()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of associateProcedure(ArrayList alProcedureList)
    
    /**added for KOC-1273
     * This method associate the procedure(s) to the Group ID.
     * @param alProcedureList ArrayList object which contains the procedure sequence id's to be associated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    
    public int associateCriticalProcedure(ArrayList alProcedureList) throws TTKException {
    	
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	if(alProcedureList != null && alProcedureList.size() > 0)
        	{
        		conn = ResourceManager.getConnection();
        		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateCriticalProcedure);
        		cStmtObject.setString(1, (String)alProcedureList.get(0));//DAY_CARE_GROUP_ID
                cStmtObject.setString(2, (String)alProcedureList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alProcedureList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
        	}
        	
        	
        }
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl associateCriticalProcedure()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl associateCriticalProcedure()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
  }
    
    //added for KOC-1273
    
    /**
     * This method associate the ICD(s) to the Group ID.
     * @param alCriticalICDList ArrayList object which contains the ICD sequence id's to be associated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    
    public int associateCriticalICD(ArrayList alCriticalICDList) throws TTKException {
    	
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	if(alCriticalICDList != null && alCriticalICDList.size() > 0)
        	{
        		conn = ResourceManager.getConnection();
        		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateCriticalICD);
        		
        		
        		
        		cStmtObject.setString(1, (String)alCriticalICDList.get(0));//DAY_CARE_GROUP_ID
                cStmtObject.setString(2, (String)alCriticalICDList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alCriticalICDList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
        	}
        	
        	
        }
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl associateCriticalICD()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl associateCriticalICD()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
  }
   
    
    //ended
    
    /**
     * This method DisAssociate the procedure(s) to the Group ID.
     * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int disAssociateProcedure(ArrayList alProcedureList) throws TTKException {
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	if(alProcedureList != null && alProcedureList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDisAssociateProcedure);
                
                cStmtObject.setString(1, (String)alProcedureList.get(0));//DAY_CARE_GROUP_ID
                cStmtObject.setString(2, (String)alProcedureList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alProcedureList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
            }//end of if(alProcedureList != null && alProcedureList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl disAssociateProcedure()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl disAssociateProcedure()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of disAssociateProcedure(ArrayList alProcedureList)
    
    /**added for KOC-1273
     * This method DisAssociate the procedure(s) to the Group ID.
     * @param alProcedureList ArrayList object which contains the procedure sequence id's to be disassociated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int disAssociateCriticalProcedure(ArrayList alProcedureList) throws TTKException {
    	
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        
        try
        {
        	if(alProcedureList != null && alProcedureList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDisAssociateCriticalProcedure);
                
                cStmtObject.setString(1, (String)alProcedureList.get(0));//CRITICAL_GROUP_ID
                cStmtObject.setString(2, (String)alProcedureList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alProcedureList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
            }//end of if(alProcedureList != null && alProcedureList.size() > 0)
        }
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl disAssociateCriticalProcedure()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl disAssociateCriticalProcedure()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;    	
    	
    }
    
    //added for KOC-1273
    
    /**
     * This method DisAssociate the ICD(s) to the Group ID.
     * @param alCriticalICDList ArrayList object which contains the icd sequence id's to be disassociated
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    
public int disAssociateCriticalICD(ArrayList alCriticalICDList) throws TTKException {
    	
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        
        try
        {
        	if(alCriticalICDList != null && alCriticalICDList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDisAssociateCriticalICD);
                
                cStmtObject.setString(1, (String)alCriticalICDList.get(0));//CRITICAL_GROUP_ID
                cStmtObject.setString(2, (String)alCriticalICDList.get(1));//string of proc_seq_id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(3, (Long)alCriticalICDList.get(2));//user sequence id
                cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(4);
            }//end of if(alProcedureList != null && alProcedureList.size() > 0)
        }
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "maintenance");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "maintenance");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl disAssociateCriticalICD()",sqlExp);
        			throw new TTKException(sqlExp, "maintenance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl disAssociateCriticalICD()",sqlExp);
        				throw new TTKException(sqlExp, "maintenance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "maintenance");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;    	
    	
    }
    
    
    //ended
    
    
//    public static void main(String a[]) throws Exception {
//    	MaintenanceDAOImpl maintenanceDAO = new MaintenanceDAOImpl();
    	
    	//maintenanceDAO.getGroupDetail("GR1",new Long(56503));
    	
    	/*DaycareGroupVO daycareGroupVO = new DaycareGroupVO();
    	daycareGroupVO.setGroupName("GROUP 2");
    	daycareGroupVO.setGroupDesc("GROUP 2");
    	daycareGroupVO.setUpdatedBy(new Long(56503));
    	maintenanceDAO.saveGroupDetail(daycareGroupVO);*/
    	
//    	ArrayList<Object> alSearchCriteria = new ArrayList<Object>();
//    	alSearchCriteria.add("100");
//    	alSearchCriteria.add("E110");
//    	alSearchCriteria.add("HE");
//    	alSearchCriteria.add("");
//    	alSearchCriteria.add("");
//    	alSearchCriteria.add("");
//    	alSearchCriteria.add("");
//    	alSearchCriteria.add(new Long(56503));

    	
    	/*ArrayList<Object> alProcedureList = new ArrayList<Object>();
    	alProcedureList.add("GR0004");
    	alProcedureList.add("|1593|00.01|1594|00.02|1595|00.03|");
    	alProcedureList.add(new Long(56503));
    	maintenanceDAO.associateProcedure(alProcedureList);*/
    	
    	/*ArrayList<Object> alProcedureList = new ArrayList<Object>();
    	alProcedureList.add("GR0004");
    	alProcedureList.add("|1595|");
    	alProcedureList.add(new Long(56503));
    	maintenanceDAO.disAssociateProcedure(alProcedureList);*/
    	
//    }//end of main
    
    /**
     * 
     * @return
     * @throws TTKException
     */
    public int saveICDDetails(ICDCodeVO icdCodeVO) throws TTKException
    {
    	int pedCode=-1;
        Connection conn = null;
        CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveICDDetail);

            
            if(icdCodeVO.getPedCode()==null)
            {
            	log.debug("Add scenario ");
            	cStmtObject.setLong(1, 0);
            }
            else
            {
            	log.debug("update scenario "+icdCodeVO.getPedCode().longValue());
            	cStmtObject.setLong(1, icdCodeVO.getPedCode());
            }
            
            cStmtObject.setString(2,icdCodeVO.getIcdCode());
            cStmtObject.setString(3,icdCodeVO.getDescription());
            cStmtObject.setString(4,(icdCodeVO.getMostCommon()==null || "N".equalsIgnoreCase(icdCodeVO.getMostCommon()))?"N":"Y");
            cStmtObject.setLong(5,icdCodeVO.getUpdatedBy());
            if(icdCodeVO.getMasterPedCode()==null || icdCodeVO.getMasterPedCode()==0)
            {
            	cStmtObject.setNull(6, Types.NULL);
            }//end of if(icdCodeVO.getMasterPedCode()==null || icdCodeVO.getMasterPedCode()==0)
            else
            {
            cStmtObject.setLong(6,icdCodeVO.getMasterPedCode());
            }//end of else
            cStmtObject.registerOutParameter(1,Types.INTEGER);
            cStmtObject.registerOutParameter(7,Types.INTEGER);
            cStmtObject.execute();
            pedCode = cStmtObject.getInt(1);
    	} catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "maintenanceicd");
      }//end of catch (SQLException sqlExp)
      catch (Exception exp)
      {
            throw new TTKException(exp, "maintenanceicd");
      }//end of catch (Exception exp)
      finally
		{
      	/* Nested Try Catch to ensure resource closure */ 
      	try // First try closing the Statement
      	{
      		try
      		{
      			if (cStmtObject != null) cStmtObject.close();
      		}//end of try
      		catch (SQLException sqlExp)
      		{
      			log.error("Error while closing the Statement in MaintenanceDAOImpl saveICDDetails()",sqlExp);
      			throw new TTKException(sqlExp, "maintenanceicd");
      		}//end of catch (SQLException sqlExp)
      		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
      		{
      			try
      			{
      				if(conn != null) conn.close();
      			}//end of try
      			catch (SQLException sqlExp)
      			{
      				log.error("Error while closing the Connection in MaintenanceDAOImpl saveICDDetails()",sqlExp);
      				throw new TTKException(sqlExp, "maintenanceicd");
      			}//end of catch (SQLException sqlExp)
      		}//end of finally Connection Close
      	}//end of try
      	catch (TTKException exp)
      	{
      		throw new TTKException(exp, "maintenanceicd");
      	}//end of catch (TTKException exp)
      	finally // Control will reach here in any case set null to the objects 
      	{
      		cStmtObject = null;
      		conn = null;
      	}//end of finally
		}//end of finally
  	return pedCode;
    }
    

    /**
     * This method fetch ICD Code details to be displayed for editing
     * @param pedCode
     * @return ICDCodeVO
     * @throws TTKException
     */	
    public ICDCodeVO selectICD(Long pedCode) throws TTKException
    {
        Connection conn 			  = null;
        CallableStatement cStmtObject = null;
        ICDCodeVO iCDCodeVO 		  = null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectICD);
			cStmtObject.setLong(1,pedCode); 
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			ResultSet rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			while(rs.next())
			{
				iCDCodeVO = new ICDCodeVO();
				iCDCodeVO.setPedCode(rs.getLong("PED_CODE_ID"));
				iCDCodeVO.setIcdCode(rs.getString("ICD_CODE"));
				iCDCodeVO.setDescription(rs.getString("PED_DESCRIPTION"));
				iCDCodeVO.setMasterPedCode(rs.getLong("MASTER_PED_CODE_ID"));
				iCDCodeVO.setMostCommon(rs.getString("MOST_COMMON_YN"));
				iCDCodeVO.setMasterIcdCode(rs.getString("MASTER_ICD_CODE"));			}
    	}catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "maintenance");
      }//end of catch (SQLException sqlExp)
      catch (Exception exp)
      {
            throw new TTKException(exp, "maintenance");
      }//end of catch (Exception exp)
      finally
		{
      	/* Nested Try Catch to ensure resource closure */ 
      	try // First try closing the Statement
      	{
      		try
      		{
      			if (cStmtObject != null) cStmtObject.close();
      		}//end of try
      		catch (SQLException sqlExp)
      		{
      			log.error("Error while closing the Statement in MaintenanceDAOImpl selectICD()",sqlExp);
      			throw new TTKException(sqlExp, "maintenance");
      		}//end of catch (SQLException sqlExp)
      		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
      		{
      			try
      			{
      				if(conn != null) conn.close();
      			}//end of try
      			catch (SQLException sqlExp)
      			{
      				log.error("Error while closing the Connection in MaintenanceDAOImpl selectICD()",sqlExp);
      				throw new TTKException(sqlExp, "maintenance");
      			}//end of catch (SQLException sqlExp)
      		}//end of finally Connection Close
      	}//end of try
      	catch (TTKException exp)
      	{
      		throw new TTKException(exp, "maintenance");
      	}//end of catch (TTKException exp)
      	finally // Control will reach here in any case set null to the objects 
      	{
      		cStmtObject = null;
      		conn = null;
      	}//end of finally
		}//end of finally
      return iCDCodeVO;
    }
    /**
     * This method returns the Arraylist of PolicyListVO's  which contains Daycare Group details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyListVO's object which contains Daycare Group details
     * @exception throws TTKException
     */
    public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyListVO policyListVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPolicyList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//POLICY_NUMBER
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//INS_SEQ_ID
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//ENROL_TYPE_ID
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			
			if(rs != null){
				while(rs.next()){
					policyListVO = new PolicyListVO();

					policyListVO.setPolicySeqID(rs.getLong("POLICY_SEQ_ID"));
					policyListVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					policyListVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("POLICY_HOLDER")));
					policyListVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
					policyListVO.setPolicySubGeneralTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					policyListVO.setPolicySubType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						policyListVO.setEffectiveFromDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
            		}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)
					policyListVO.setInsuranceCompName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					alResultList.add(policyListVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyList(ArrayList alSearchCriteria)
    
    /**
     * This method returns an positive integer on successful execution
     * @param alChangeToPnf ArrayList object which contains the parameters
     * @return positive integer on successful execution
     * @exception throws TTKException
     */
    public int changeToNonFloater(ArrayList alChangeToPnf) throws TTKException
    {
    	int iResult=0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChangeToNonFloater);
            cStmtObject.setLong(1, (Long)alChangeToPnf.get(0));
            cStmtObject.setLong(2,(Long)alChangeToPnf.get(1));
            cStmtObject.registerOutParameter(3,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);
    	} catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "maintenance");
      }//end of catch (SQLException sqlExp)
      catch (Exception exp)
      {
            throw new TTKException(exp, "maintenance");
      }//end of catch (Exception exp)
      finally
		{
      	/* Nested Try Catch to ensure resource closure */ 
      	try // First try closing the Statement
      	{
      		try
      		{
      			if (cStmtObject != null) cStmtObject.close();
      		}//end of try
      		catch (SQLException sqlExp)
      		{
      			log.error("Error while closing the Statement in MaintenanceDAOImpl changeToNonFloater()",sqlExp);
      			throw new TTKException(sqlExp, "maintenance");
      		}//end of catch (SQLException sqlExp)
      		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
      		{
      			try
      			{
      				if(conn != null) conn.close();
      			}//end of try
      			catch (SQLException sqlExp)
      			{
      				log.error("Error while closing the Connection in MaintenanceDAOImpl changeToNonFloater()",sqlExp);
      				throw new TTKException(sqlExp, "maintenance");
      			}//end of catch (SQLException sqlExp)
      		}//end of finally Connection Close
      	}//end of try
      	catch (TTKException exp)
      	{
      		throw new TTKException(exp, "maintenance");
      	}//end of catch (TTKException exp)
      	finally // Control will reach here in any case set null to the objects 
      	{
      		cStmtObject = null;
      		conn = null;
      	}//end of finally
		}//end of finally
  	return iResult;
    }//end of changeToNonFloater(ArrayList alChangeToPnf)
    
    /**
     * This method returns an positive integer on successful execution
     * @param alChangeToPnf ArrayList object which contains the parameters
     * @return positive integer on successful execution
     * @exception throws TTKException
     */
    public int changeToFloater(ArrayList alChangeToPfl) throws TTKException
    {
    	int iResult=0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChangeToFloater);
            cStmtObject.setLong(1, (Long)alChangeToPfl.get(0));
            cStmtObject.setLong(2,(Long)alChangeToPfl.get(1));
            cStmtObject.registerOutParameter(3,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);
    	} catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "maintenance");
      }//end of catch (SQLException sqlExp)
      catch (Exception exp)
      {
            throw new TTKException(exp, "maintenance");
      }//end of catch (Exception exp)
      finally
		{
      	/* Nested Try Catch to ensure resource closure */ 
      	try // First try closing the Statement
      	{
      		try
      		{
      			if (cStmtObject != null) cStmtObject.close();
      		}//end of try
      		catch (SQLException sqlExp)
      		{
      			log.error("Error while closing the Statement in MaintenanceDAOImpl changeToFloater()",sqlExp);
      			throw new TTKException(sqlExp, "maintenance");
      		}//end of catch (SQLException sqlExp)
      		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
      		{
      			try
      			{
      				if(conn != null) conn.close();
      			}//end of try
      			catch (SQLException sqlExp)
      			{
      				log.error("Error while closing the Connection in MaintenanceDAOImpl changeToFloater()",sqlExp);
      				throw new TTKException(sqlExp, "maintenance");
      			}//end of catch (SQLException sqlExp)
      		}//end of finally Connection Close
      	}//end of try
      	catch (TTKException exp)
      	{
      		throw new TTKException(exp, "maintenance");
      	}//end of catch (TTKException exp)
      	finally // Control will reach here in any case set null to the objects 
      	{
      		cStmtObject = null;
      		conn = null;
      	}//end of finally
		}//end of finally
  	return iResult;
    }//end of changeToFloater(ArrayList alChangeToPfl)   
        
    /**
     * This method returns the Arraylist of NotifyDetailVO's  which contains Notification details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of NotifyDetailVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList getNotifyList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		NotifyDetailVO notifyDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectNotifyList);			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//SORT_VAR
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//SORT_ORDER
			cStmtObject.setLong(3,Long.parseLong((String) alSearchCriteria.get(2)));//START_NUM
			cStmtObject.setLong(4,Long.parseLong((String) alSearchCriteria.get(3)));//END_NUM
			cStmtObject.registerOutParameter(5,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);		
			conn.commit();	
			if(rs != null){
				while(rs.next()){
					notifyDetailVO = new NotifyDetailVO();
					notifyDetailVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					notifyDetailVO.setMsgName(TTKCommon.checkNull(rs.getString("msg_name")));	
					notifyDetailVO.setNotifCategory(TTKCommon.checkNull(rs.getString("NOTIFICATION_CATEGORY")));
					notifyDetailVO.setApplicableInsCompany(TTKCommon.checkNull(rs.getString("applicable_insurance_code")));
					notifyDetailVO.setApplicableInsCompanyDesc(TTKCommon.checkNull(rs.getString("applicable_insurance_description")));
					alResultList.add(notifyDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getNotifyList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getNotifyList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getNotifyList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getNotifyList(ArrayList alSearchCriteria)
    
    /**
     * This method saves the Notification details
     * @param notifyDetailVO NotifyDetailVO object which contains the search criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveNotificationInfo(NotifyDetailVO notifyDetailVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveNotificationInfo);	
			cStmtObject.setString(1,notifyDetailVO.getMsgID());
			cStmtObject.setString(2,notifyDetailVO.getEmailDesc());
			cStmtObject.setString(3,notifyDetailVO.getSMSDesc());
			cStmtObject.setString(4,notifyDetailVO.getPrimaryMailID());
			cStmtObject.setString(5,notifyDetailVO.getSecondaryMailID());
			cStmtObject.setString(6,notifyDetailVO.getMsgTitle());
			if(notifyDetailVO.getConfigParam1()!=null)
			{
				cStmtObject.setString(7,notifyDetailVO.getConfigParam1().toString());
			}//end of if(notifyDetailVO.getConfigParam1()!=null)
			else
			{
				cStmtObject.setString(7,null);
			}//end of else
			if(notifyDetailVO.getConfigParam2()!=null)
			{
				cStmtObject.setString(8,notifyDetailVO.getConfigParam2().toString());
			}//end of if(notifyDetailVO.getConfigParam2()!=null)
			else
			{
				cStmtObject.setString(8,null);
			}//end of else
			if(notifyDetailVO.getConfigParam3()!=null)
			{
				cStmtObject.setString(9,notifyDetailVO.getConfigParam3().toString());
			}//end of if(notifyDetailVO.getConfigParam3()!=null)
			else
			{
				cStmtObject.setString(9,null);
			}//end of else	
			cStmtObject.setString(10,notifyDetailVO.getLevel2EmailID());
			cStmtObject.setString(11,notifyDetailVO.getLevel3EmailID());
			cStmtObject.setLong(12,notifyDetailVO.getAddedBy());
			cStmtObject.registerOutParameter(13,Types.INTEGER);
			cStmtObject.setString(14,notifyDetailVO.getApplicableInsCheckCode());
			cStmtObject.setString(15,notifyDetailVO.getApplicableInsCheckName());
			cStmtObject.execute();
			iResult = cStmtObject.getInt(13);
			conn.commit();
			return iResult;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl saveNotificationInfo()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl saveNotificationInfo()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of saveNotificationInfo(ArrayList alSearchCriteria)
    
    /**
     * This method returns the NotifyDetailVO which contains Notification details
     * @param strMsgID String object which contains the Message ID
     * @return notifyDetailVO of NotifyDetailVO object which contains Notification details
     * @exception throws TTKException
     */
    public NotifyDetailVO getNotificationInfo(String strMsgID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		NotifyDetailVO notifyDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectNotificationInfo);			
			cStmtObject.setString(1,strMsgID);//SORT_VAR
			cStmtObject.registerOutParameter(1,Types.VARCHAR);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);			
			conn.commit();
			if(rs != null){
				while(rs.next()){
					
					notifyDetailVO = new NotifyDetailVO();
					notifyDetailVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					notifyDetailVO.setMsgName(TTKCommon.checkNull(rs.getString("MSG_NAME")));
					notifyDetailVO.setNotificationDesc(TTKCommon.checkNull(rs.getString("NOTIFICATION_DESCRIPTION")));
					notifyDetailVO.setMsgTitle(TTKCommon.checkNull(rs.getString("MESSAGE_TITLE")));
					//notifyDetailVO.setEmailDesc(TTKCommon.checkNull(rs.getString("MSG_DESCRIPTION")));
					//changed for cigna mail-sms for combination of 2 messages 					
				//	notifyDetailVO.setEmailDesc(TTKCommon.checkNull(rs.getString("MSG_DESCRIPTION")+rs.getString("MESG2")));
					notifyDetailVO.setEmailDesc(TTKCommon.checkNull(rs.getString("MSG_DESCRIPTION")));		
					notifyDetailVO.setSMSDesc(TTKCommon.checkNull(rs.getString("SMS_DESCRIPTION")));
					notifyDetailVO.setPrimaryMailID(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
					notifyDetailVO.setSecondaryMailID(TTKCommon.checkNull(rs.getString("SEC_RCPT_EMAIL_LIST")));	
					notifyDetailVO.setConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ? new Integer(rs.getInt("CONFIG_PARAM_1")):null);
					notifyDetailVO.setConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ? new Integer(rs.getInt("CONFIG_PARAM_2")):null);
					notifyDetailVO.setConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ? new Integer(rs.getInt("CONFIG_PARAM_3")):null);
					notifyDetailVO.setNotifCategory(TTKCommon.checkNull(rs.getString("NOTIFICATION_CATEGORY")));
					notifyDetailVO.setConfigAllowedYN(TTKCommon.checkNull(rs.getString("PARAM_CONFIG_ALLOWED_YN")));
					notifyDetailVO.setCustConfigAllowedYN(TTKCommon.checkNull(rs.getString("CUSTOMIZED_CONFIG_ALLOWED_YN")));
					notifyDetailVO.setSendEmailYN(TTKCommon.checkNull(rs.getString("SEND_AS_EMAIL_YN")));
					notifyDetailVO.setSendSmsYN(TTKCommon.checkNull(rs.getString("SEND_AS_SMS_YN")));
					notifyDetailVO.setLevel2EmailID(TTKCommon.checkNull(rs.getString("LEVEL2_EMAIL_LIST")));
					notifyDetailVO.setLevel3EmailID(TTKCommon.checkNull(rs.getString("LEVEL3_EMAIL_LIST")));
					notifyDetailVO.setShowMultiLevelYN(TTKCommon.checkNull(rs.getString("SHOW_MULTI_ESCAL_LEVEL_YN")));
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return notifyDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getNotificationInfo()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getNotificationInfo()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getNotificationInfo()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getNotificationInfo(String strMsgID)
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Notification details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList getCustomConfigList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCustomConfigList);			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//MSG_ID
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//SORT_VAR
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//SORT_ORDER
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//START_NUM
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));//END_NUM
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);			
			if(rs != null){
				while(rs.next()){
					customizeConfigVO = new CustomizeConfigVO();
					customizeConfigVO.setCustConfigSeqID(new Long(rs.getLong("CUST_CONFIG_SEQ_ID")));
					customizeConfigVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					customizeConfigVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					customizeConfigVO.setStrConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ?  (rs.getString("CONFIG_PARAM_1")):"");
					customizeConfigVO.setStrConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ?  (rs.getString("CONFIG_PARAM_2")):"");
					customizeConfigVO.setStrConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ?  (rs.getString("CONFIG_PARAM_3")):"");
					customizeConfigVO.setStrConfigParam4(rs.getString("CONFIG_PARAM_4")!=null ?  (rs.getString("CONFIG_PARAM_4")):""); // koc11 koc 11
					customizeConfigVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));					
					alResultList.add(customizeConfigVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCustomConfigList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCustomConfigList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCustomConfigList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCustomConfigList(ArrayList alSearchCriteria)
    
    /**
     * This method saves the Notification Customized Config details
     * @param customizeConfigVO CustomizeConfigVO object which contains the search criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveCustomConfigInfo(CustomizeConfigVO customizeConfigVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCustomConfigInfo);	
			if(customizeConfigVO.getCustConfigSeqID()!=null)
			{
				cStmtObject.setLong(1,customizeConfigVO.getCustConfigSeqID());
			}//end of if(customizeConfigVO.getCustConfigSeqID()!=null)
			else
			{
				cStmtObject.setLong(1,0);
			}//end of else
			cStmtObject.setString(2,customizeConfigVO.getMsgID());
			if(customizeConfigVO.getOfficeSeqID()!=null)
			{
				cStmtObject.setLong(3,customizeConfigVO.getOfficeSeqID());
			}//end of if(customizeConfigVO.getOfficeSeqID()!=null)
			else
			{
				cStmtObject.setString(3,null);
			}//end of else			
			if(customizeConfigVO.getConfigParam1()!=null)
			{
				cStmtObject.setInt(4,customizeConfigVO.getConfigParam1());
			}//end of if(customizeConfigVO.getConfigParam1()!=null)
			else
			{
				cStmtObject.setString(4,null);
			}//end of else
			cStmtObject.setString(5,customizeConfigVO.getPrimaryMailID1());
			if(customizeConfigVO.getConfigParam2()!=null)
			{
				cStmtObject.setInt(6,customizeConfigVO.getConfigParam2());
			}//end of if(customizeConfigVO.getConfigParam2()!=null)
			else
			{
				cStmtObject.setString(6,null);
			}//end of else
			cStmtObject.setString(7,customizeConfigVO.getPrimaryMailID2());
			if(customizeConfigVO.getConfigParam3()!=null)
			{
				cStmtObject.setInt(8,customizeConfigVO.getConfigParam3());
			}//end of if(customizeConfigVO.getConfigParam3()!=null)
			else
			{
				cStmtObject.setString(8,null);
			}//end of else	
			cStmtObject.setString(9,customizeConfigVO.getPrimaryMailID3());
			if(customizeConfigVO.getConfigParam4()!=null)
			{
				cStmtObject.setInt(10,customizeConfigVO.getConfigParam4());
			}//end of if(customizeConfigVO.getConfigParam3()!=null)
			else
			{
				cStmtObject.setString(10,null);
			}//end of else	
			cStmtObject.setString(11,customizeConfigVO.getPrimaryMailID4()); 
			cStmtObject.setLong(12,customizeConfigVO.getAddedBy());
			cStmtObject.registerOutParameter(13,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(13);
			return iResult;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl saveCustomConfigInfo()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl saveCustomConfigInfo()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of saveCustomConfigInfo(CustomizeConfigVO customizeConfigVO)
    
    /**
     * This method returns the CustomizeConfigVO which contains Notification Customized Config details
     * @param lngCustConfigSeqID Long object which contains the Cust Config Seq ID
     * @return customizeConfigVO of CustomizeConfigVO object which contains Customized Config details
     * @exception throws TTKException
     */
    public CustomizeConfigVO getCustomConfigInfo(Long lngCustConfigSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCustomConfigInfo);			
			cStmtObject.setLong(1,lngCustConfigSeqID);//CustConfigSeqID
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);			
			if(rs != null){
				while(rs.next()){
					customizeConfigVO = new CustomizeConfigVO();					
					customizeConfigVO.setCustConfigSeqID(new Long(rs.getLong("CUST_CONFIG_SEQ_ID")));
					customizeConfigVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					customizeConfigVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					customizeConfigVO.setConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ? new Integer(rs.getInt("CONFIG_PARAM_1")):null);
					customizeConfigVO.setPrimaryMailID1(TTKCommon.checkNull(rs.getString("PARAM_1_EMAIL_LIST")));
					customizeConfigVO.setConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ? new Integer(rs.getInt("CONFIG_PARAM_2")):null);
					customizeConfigVO.setPrimaryMailID2(TTKCommon.checkNull(rs.getString("PARAM_2_EMAIL_LIST")));
					customizeConfigVO.setConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ? new Integer(rs.getInt("CONFIG_PARAM_3")):null);
					customizeConfigVO.setPrimaryMailID3(TTKCommon.checkNull(rs.getString("PARAM_3_EMAIL_LIST")));	
					customizeConfigVO.setConfigParam4(rs.getString("CONFIG_PARAM_4")!=null ? new Integer(rs.getInt("CONFIG_PARAM_4")):null);
					customizeConfigVO.setPrimaryMailID4(TTKCommon.checkNull(rs.getString("PARAM_4_EMAIL_LIST")));
					customizeConfigVO.setPrimaryMailID3(TTKCommon.checkNull(rs.getString("PARAM_3_EMAIL_LIST")));					
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return customizeConfigVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCustomConfigInfo()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCustomConfigInfo()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCustomConfigInfo()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCustomConfigInfo(Long lngCustConfigSeqID)
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallpendingByBranch() throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCallpendingByBranch);			
			cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);			
			if(rs != null){
				while(rs.next()){
					customizeConfigVO = new CustomizeConfigVO();
					customizeConfigVO.setOfficeCode(rs.getString("OFFICE_CODE"));
					customizeConfigVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					customizeConfigVO.setConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ? new Integer(rs.getInt("CONFIG_PARAM_1")):null);
					customizeConfigVO.setPrimaryMailID1(TTKCommon.checkNull(rs.getString("PARAM_1_EMAIL_LIST")));
					customizeConfigVO.setConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ? new Integer(rs.getInt("CONFIG_PARAM_2")):null);
					customizeConfigVO.setPrimaryMailID2(TTKCommon.checkNull(rs.getString("PARAM_2_EMAIL_LIST")));
					customizeConfigVO.setConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ? new Integer(rs.getInt("CONFIG_PARAM_3")):null);
					customizeConfigVO.setPrimaryMailID3(TTKCommon.checkNull(rs.getString("PARAM_3_EMAIL_LIST")));
					alResultList.add(customizeConfigVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList<Object>)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCallpendingByBranch(ArrayList alSearchCriteria)
    
    /**
     * This method returns the CustomizeConfigVO's  which contains Call pending details
     * @param strMRClaimsPendConfig MR Claims Pending Config
     * @return CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public CustomizeConfigVO getMrClaimsPendConfig(String strMRClaimsPendConfig) throws TTKException {

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = new CustomizeConfigVO();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMRClaimPendConfig);	
			cStmtObject.setString(1,strMRClaimsPendConfig);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);			
			if(rs != null){
				while(rs.next()){
					customizeConfigVO.setConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ? new Integer(rs.getInt("CONFIG_PARAM_3")):null);
					customizeConfigVO.setPrimaryMailID3(TTKCommon.checkNull(rs.getString("LEVEL3_EMAIL_LIST")));
					customizeConfigVO.setConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ? new Integer(rs.getInt("CONFIG_PARAM_2")):null);
					customizeConfigVO.setPrimaryMailID2(TTKCommon.checkNull(rs.getString("LEVEL2_EMAIL_LIST")));
					customizeConfigVO.setConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ? new Integer(rs.getInt("CONFIG_PARAM_1")):null);
					customizeConfigVO.setPrimaryMailID1(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return customizeConfigVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getMrClaimsPendConfig()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getMrClaimsPendConfig()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getMrClaimsPendConfig()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally    
    }//end of getMrClaimsPendConfig(String strMRClaimsPendConfig)
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @param lngOfficeSeqID Long Office Seq ID
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallPendByBranchRpt(Long lngOfficeSeqID) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCallpendingByBranchRpt);	
			cStmtObject.setLong(1,lngOfficeSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);			
			if(rs != null){
				while(rs.next()){
					customizeConfigVO = new CustomizeConfigVO();
					customizeConfigVO.setOfficeCode(rs.getString("OFFICE_CODE"));
					customizeConfigVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					customizeConfigVO.setConfigParam1(rs.getString("CONFIG_PARAM_1")!=null ? new Integer(rs.getInt("CONFIG_PARAM_1")):null);
					customizeConfigVO.setPrimaryMailID1(TTKCommon.checkNull(rs.getString("PARAM_1_EMAIL_LIST")));
					customizeConfigVO.setConfigParam2(rs.getString("CONFIG_PARAM_2")!=null ? new Integer(rs.getInt("CONFIG_PARAM_2")):null);
					customizeConfigVO.setPrimaryMailID2(TTKCommon.checkNull(rs.getString("PARAM_2_EMAIL_LIST")));
					customizeConfigVO.setConfigParam3(rs.getString("CONFIG_PARAM_3")!=null ? new Integer(rs.getInt("CONFIG_PARAM_3")):null);
					customizeConfigVO.setPrimaryMailID3(TTKCommon.checkNull(rs.getString("PARAM_3_EMAIL_LIST")));
					alResultList.add(customizeConfigVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList<Object>)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCallpendingByBranch(ArrayList alSearchCriteria)

    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, String strAddiParam, String strPrimaryMailID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strProcFormMessage);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setString(2,"");
			cStmtObject.setLong(3,new Long("1"));
			cStmtObject.setString(5,strAddiParam);
			cStmtObject.setString(6,strPrimaryMailID);
			cStmtObject.registerOutParameter(4, Types.VARCHAR);
			cStmtObject.execute();		
		
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl insertRecord()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl insertRecord()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of insertRecord(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID)
    
    /**
     * This method returns the ArrayList, which contains the ClaimListVO which are populated from the database
     * @param strClaimNbr String object which contains the Claim Number
     * @return ArrayList of ClaimListVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getClaimReqamt(String strClaimNbr) throws TTKException {
    	ClaimListVO claimListVO = null;
	    Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    ResultSet rs = null;
	    try{
	    	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectClaimReqamt);
			
			cStmtObject.setString(1,strClaimNbr);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);//ROWS_PROCESSED
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					claimListVO = new ClaimListVO();
					
					if (rs.getString("CLAIM_SEQ_ID") != null){
						claimListVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                    }//end of if (rs.getString("CLAIM_SEQ_ID") != null)
					claimListVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimListVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					claimListVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimListVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					if(rs.getString("REQUESTED_AMOUNT") != null){
						claimListVO.setPreClmReqAmt(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
                    }//end of if(rs.getString("REQUESTED_AMOUNT") != null)
					
					alResultList.add(claimListVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
	    }//end of try
	    catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getClaimReqamt()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getClaimReqamt()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getClaimReqamt()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimReqamt(ArrayList<Object> alSearchCriteria)

    /**
     * This method saves the New Claims Requested Amount
     * @param claimListVO ClaimListVO object which contains the search criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveClaimReqAmt(ClaimListVO claimListVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimReqAmt);	
			if(claimListVO.getClaimSeqID()!=null)
			{
				cStmtObject.setLong(1,claimListVO.getClaimSeqID());
			}//end of if(claimListVO.getClaimSeqID()!=null)
			else
			{
				cStmtObject.setLong(1,0);
			}//end of else
			cStmtObject.setBigDecimal(2,claimListVO.getNewClmReqAmt());
			
			cStmtObject.setString(3,claimListVO.getRemarks());
			cStmtObject.setLong(4,claimListVO.getAddedBy());
			cStmtObject.registerOutParameter(5,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
			return iResult;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl saveClaimReqAmt()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl saveClaimReqAmt()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of saveClaimReqAmt(CustomizeConfigVO customizeConfigVO)
    
    /**
	 * This method returns the ProcedureDetailVO, which contains procedure details
	 * @param lngProcSeqID Procedure Seq ID
	 * @return ProcedureDetailVO object which contains procedure details
	 * @exception throws TTKException
	 */
    public ProcedureDetailVO getPcsInfo(long lngProcSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	ProcedureDetailVO procedureDetailVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPcsDetail);
    		
    		cStmtObject.setLong(1,lngProcSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			
			if(rs != null){
				while(rs.next()){
					procedureDetailVO = new ProcedureDetailVO();
					
					if(rs.getString("PROC_SEQ_ID") != null){
						procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
					}//end of if(rs.getString("PROC_SEQ_ID") != null)
					
					procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
					procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
					procedureDetailVO.setShortDesc(TTKCommon.checkNull(rs.getString("SHORT_DESCRIPTION")));
					procedureDetailVO.setMasterProcCode(TTKCommon.checkNull(rs.getString("MASTER_PROCEDURE_CODE")));
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getGroupDetail()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getGroupDetail()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getGroupDetail()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return procedureDetailVO;
    }//end of getPcsInfo(long lngProcSeqID)
    
    /**
     * This method saves the Procedure details
     * @param procedureDetailVO ProcedureDetailVO object which contains the Procedure Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int savePcsInfo(ProcedureDetailVO procedureDetailVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePcsDetail);	
			
			if(procedureDetailVO.getProcedureID() != null){
				cStmtObject.setLong(1,procedureDetailVO.getProcedureID());
			}//end of if(procedureDetailVO.getProcedureID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			
			cStmtObject.setString(2,procedureDetailVO.getProcedureCode());
			cStmtObject.setString(3,procedureDetailVO.getProcedureDescription());
			cStmtObject.setString(4,procedureDetailVO.getShortDesc());
			cStmtObject.setString(5,procedureDetailVO.getMasterProcCode());
			cStmtObject.setLong(6,procedureDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(7,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);
			
			return iResult;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl savePcsInfo()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl savePcsInfo()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of savePcsInfo(ProcedureDetailVO procedureDetailVO)
    
    /**
     * This method will update the policy DO/BO details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyDOBO(ArrayList<Object> alUpdateList) throws TTKException {

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iUpdateCount = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChgPolicyDOBO);	
			cStmtObject.setString(1,(String)alUpdateList.get(0));
			cStmtObject.setBigDecimal(2,BigDecimal.valueOf((Long)alUpdateList.get(1)));
			cStmtObject.registerOutParameter(3,OracleTypes.INTEGER);
			cStmtObject.execute();
			iUpdateCount = Integer.parseInt(cStmtObject.getObject(3).toString());
			return iUpdateCount;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing CallableStatement statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl updatePolicyDOBO()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl updatePolicyDOBO()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally    
    }//end of updatePolicyDOBO(ArrayList<Object> alUpdateList)
    
    /**
     * This method will update the policy period details.
     * @param alUpdateList object which contains the save criteria
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int updatePolicyPeriod(ArrayList<Object> alUpdateList) throws TTKException {

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iUpdateCount = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChgPolicyPeriod);	
			cStmtObject.setString(1,(String)alUpdateList.get(0));
			cStmtObject.setBigDecimal(2,BigDecimal.valueOf((Long)alUpdateList.get(1)));			
			cStmtObject.setTimestamp(3,new Timestamp(TTKCommon.getUtilDate(alUpdateList.get(2).toString()).getTime()));
			cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getUtilDate(alUpdateList.get(3).toString()).getTime()));
			cStmtObject.registerOutParameter(5,OracleTypes.INTEGER);
			cStmtObject.execute();
			iUpdateCount = Integer.parseInt(cStmtObject.getObject(5).toString());
			return iUpdateCount;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing CallableStatement statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MaintenanceDAOImpl updatePolicyPeriod()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MaintenanceDAOImpl updatePolicyPeriod()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally    
    }//end of updatePolicyPeriod(ArrayList<Object> alUpdateList)
    
    /**
     * This method returns the ArrayList, which contains the ProcedureDetailVO which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureDetailVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList<Object> getPcsList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	ProcedureDetailVO procedureDetailVO = null;
	    Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    ResultSet rs = null;
	    try{
	    	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPcsList);
			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setLong(7,(Long)alSearchCriteria.get(2));
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);//ROWS_PROCESSED
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			if(rs != null){
				while(rs.next()){
					procedureDetailVO = new ProcedureDetailVO();
					if (rs.getString("PROC_SEQ_ID") != null){
	                    procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
	                procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
	                procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
	                
	                alResultList.add(procedureDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
	    }//end of try
	    catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getPcsList()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getPcsList()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getPcsList()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPcsList(ArrayList<Object> alSearchCriteria)
    
    
    
    
    
    /**
     * This method returns the Arraylist of CustomizeConfigVO's  which contains Call pending details
     * @return ArrayList of CustomizeConfigVO object which contains Notification details
     * @exception throws TTKException
     */
    public ArrayList<Object> getCallPendingByAttendedBy() throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CustomizeConfigVO customizeConfigVO = null;
		PreparedStatement pStmt = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCallpendingByAttendBy);			
			cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.execute();
			
	          // pStmt=conn.prepareStatement("SELECT a.call_log_seq_id,to_char(A.CALL_RECORDED_DATE,'DD/MM/YYYY') AS LAST_CORRESPONDENCE_DATE,A.CALL_LOG_NUMBER,C.DESCRIPTION,A.CALL_CONTENT,a.caller_name FROM app.tpa_call_log A,app.tpa_office_info B,app.tpa_general_code C,app.tpa_user_contacts D WHERE A.tpa_office_seq_id = B.tpa_office_seq_id  and A.ACTION_GENERAL_TYPE_ID = C.GENERAL_TYPE_ID AND a.contact_seq_id = d.contact_seq_id AND A.Last_Action_General_Type_Id <> 'SCL' AND A.PARENT_CALL_LOG_SEQ_ID IS NULL AND a.call_bck_yn = 'Y' group by a.call_log_seq_id,a.caller_name,A.CALL_LOG_NUMBER,C.DESCRIPTION,A.CALL_CONTENT,A.CALL_RECORDED_DATE ORDER BY A.CALL_RECORDED_DATE");
	            
	            
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
	          // rs	=	pStmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					customizeConfigVO = new CustomizeConfigVO();
					//customizeConfigVO.setCallLogSeqId(rs.getString("CALL_LOG_SEQ_ID"));
					//customizeConfigVO.setCallLogNo(rs.getString("CALL_LOG_NUMBER"));
					//customizeConfigVO.setDesc(rs.getString("DESCRIPTION"));
					//customizeConfigVO.setCallContent(TTKCommon.checkNull(rs.getString("CALL_CONTENT")));
					//customizeConfigVO.setCallerName(rs.getString("caller_name"));
					customizeConfigVO.setAddedBy(rs.getLong("ADDED_BY"));
					customizeConfigVO.setPrimaryMailID1(rs.getString("AGENT_EMAIL_ID"));
					alResultList.add(customizeConfigVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList<Object>)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "maintenance");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "maintenance");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getCallpendingByBranch()",sqlExp);
							throw new TTKException(sqlExp, "maintenance");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "maintenance");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCallPendingByAttendedBy(ArrayList alSearchCriteria)
    
   /* public static void main(String a[]) throws Exception{
    	
//    	MaintenanceDAOImpl maintanaceDAO = new MaintenanceDAOImpl();
//    	maintanaceDAO.getNotificationInfo("MR_CLAIMS_PENDING_REPORT");
    	ProcedureDetailVO procDetailVO = new ProcedureDetailVO();
    	procDetailVO.setProcedureID(new Long(7));
    	procDetailVO.setProcedureCode("47.09");
    	procDetailVO.setProcedureDescription("OTHER APPENDECTOMY");
    	procDetailVO.setShortDesc("OTHER APPENDECTOMY TEST");
    	procDetailVO.setProcMapSeqID(new Long(1393));
    	procDetailVO.setMasterProcCode("47.09");
    	procDetailVO.setUpdatedBy(new Long(56503));
    	maintanaceDAO.savePcsInfo(procDetailVO);
    	
    	//maintanaceDAO.getPcsInfo(new Long(7));
    	
    	ArrayList<Object> alSearchCriteria = new ArrayList<Object>();
    	alSearchCriteria.add("47.09");
    	alSearchCriteria.add("");
    	alSearchCriteria.add(new Long(56503));
    	alSearchCriteria.add("PROC_CODE");
    	alSearchCriteria.add("ASC");
    	alSearchCriteria.add("1");
    	alSearchCriteria.add("10");
    	maintanaceDAO.getPcsList(alSearchCriteria);
    }//end of main
*/    
    
    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier, ArrayList alAddiParam, String strPrimaryMailID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strProcFormMessage);//MSG -D
			cStmtObject.setString(1,strIdentifier);//MSG -D
			cStmtObject.setString(2,(String)alAddiParam.get(0));//CALL LOG SEQ ID
			cStmtObject.setLong(3,(Long)alAddiParam.get(2));//ADDED BY
			cStmtObject.registerOutParameter(4, Types.VARCHAR);//OUT PARAM  - DESTINATION MSG SEQ ID
			cStmtObject.setString(5,(String)alAddiParam.get(1));//MAIL ID
			cStmtObject.setString(6,(String)alAddiParam.get(1));//MAIL ID
			cStmtObject.execute();		
		
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl insertRecord()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl insertRecord()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of insertRecord(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID)
    
    
    /**
     * This method inserts record after generating the report in ReportScheduler   
     * @param strIdentifier String Identifier
     * @param strAddiParam String Additional parameter
     * @param strPrimaryMailID String Primary Mail ID
     * @exception throws TTKException
     */
    public void insertCustomerRecord(String strIdentifier, ArrayList alAddiParam, String strPrimaryMailID,String strFileName) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strProcCustomerFormMessage);//MSG -D
			cStmtObject.setString(1,strIdentifier);//MSG -D
			cStmtObject.setString(2,(String)alAddiParam.get(0));//CALL LOG SEQ ID
			cStmtObject.setLong(3,0);//ADDED BY
			cStmtObject.registerOutParameter(4, Types.VARCHAR);//OUT PARAM  - DESTINATION MSG SEQ ID
			cStmtObject.setString(5,(String)alAddiParam.get(1));//MAIL ID
			cStmtObject.setString(6,(String)alAddiParam.get(1));//MAIL ID
			cStmtObject.setString(7,"");//MAIL ID
			cStmtObject.setString(8,strFileName);//MAIL ID
			cStmtObject.execute();		
		
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl insertRecord()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl insertRecord()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of insertCustomerRecord(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID)
    public HashMap<String,byte[]> getPreAuthProccessedFiles() throws TTKException{
    	Connection conn = null;
    	OracleCallableStatement cStmtObject = null;
    	HashMap<String,byte[]> proccedFiles=new HashMap<String,byte[]>();
    	OracleResultSet rs=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDHPOPreAuthUpload);
			cStmtObject.registerOutParameter(1, OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (OracleResultSet)cStmtObject.getObject(1);
			if(rs!=null){
			while(rs.next())	
				if(rs.getOPAQUE("xmlFile")!=null){
				proccedFiles.put(rs.getString("fileName"), rs.getOPAQUE("xmlFile").getBytes());
				}
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
				try
        		{
        			if (rs != null) rs.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the rs in MaintenanceDAOImpl getPreAuthProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if rs is not closed, control reaches here. Try closing the connection now.
        		{
				
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getPreAuthProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getPreAuthProccessedFiles()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
				}//end of finally stm Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return proccedFiles;
    
    
    }
    public HashMap<String,byte[]> getClaimsProccessedFiles() throws TTKException{
    	Connection conn = null;
    	OracleCallableStatement cStmtObject = null;
    	HashMap<String,byte[]> proccedFiles=new HashMap<String,byte[]>();
    	OracleResultSet rs=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDHPOPreAuthUpload);
			cStmtObject.registerOutParameter(1, OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (OracleResultSet)cStmtObject.getObject(1);
			if(rs!=null){
			while(rs.next())	
				if(rs.getOPAQUE("xmlFile")!=null){
				proccedFiles.put(rs.getString("fileName"), rs.getOPAQUE("xmlFile").getBytes());
				}
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
				try
        		{
        			if (rs != null) rs.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the RS in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
				
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
				}//end of finally Statement Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return proccedFiles;
    }//getClaimsProccessedFiles
    public HashMap<String,byte[]> getPersionProccessedFiles() throws TTKException{

    	Connection conn = null;
    	OracleCallableStatement cStmtObject = null;
    	HashMap<String,byte[]> proccedFiles=new HashMap<String,byte[]>();
    	OracleResultSet rs=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDHPOPreAuthUpload);
			cStmtObject.registerOutParameter(1, OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (OracleResultSet)cStmtObject.getObject(1);
			if(rs!=null){
			while(rs.next())	
				if(rs.getOPAQUE("xmlFile")!=null){
				proccedFiles.put(rs.getString("fileName"), rs.getOPAQUE("xmlFile").getBytes());
				}
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
				try
        		{
        			if (rs != null) rs.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getClaimsProccessedFiles()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
			}//end of finally Statement Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs=null;
				cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return proccedFiles;
    }
    
   
   
    public Object[] updateDhpoUplodedFileStatus(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	OracleCallableStatement cStmtObject = null;
        Object obj[]=new Object[1];
    	OracleResultSet rs=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDHPOPreAuthUpload);
			cStmtObject.registerOutParameter(1, OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (OracleResultSet)cStmtObject.getObject(1);
			if(rs!=null){
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (rs != null) rs.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the ResultSet in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the stm now.
        		{
				
				
				try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl updateDhpoUplodedFileStatus()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl updateDhpoUplodedFileStatus()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
				}//end of finally Stm Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs=null;
				cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return obj;
    }//updateDhpoUplodedFileStatus()
    
    public Object[] uploadDhpoGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		Object[] resObj=new Object[1];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =conn.prepareCall(strDHPOGlobalNetNewTransaction);
			  
			//  String xmlFileContent=dhpoWebServiceVO.getXmlFileContent() ;
			//  xmlFileContent=xmlFileContent.replaceAll("[^\\x20-\\x7e\n]", "");	
			
		//	String xmlText=new String(xmlFileContent.getBytes(), Charset.defaultCharset());
		//    System.out.println("Be xml="+xmlText);
		   // xmlText=xmlText.replaceAll("[^\\x20-\\x7e\n]", "");

			SQLXML sqlxml=conn.createSQLXML();
			sqlxml.setString(dhpoWebServiceVO.getXmlFileContent());
			
			cStmtObject.setLong(1, 0);
			cStmtObject.setString(2, dhpoWebServiceVO.getFileID());			
			cStmtObject.setString(3, dhpoWebServiceVO.getFileName());			
			cStmtObject.setSQLXML(4, sqlxml);
			cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
			cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
			cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
			cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
			cStmtObject.setString(9, "N");
			cStmtObject.setString(10, dhpoWebServiceVO.getDhpoLicenceNO());
			cStmtObject.registerOutParameter(11, Types.VARCHAR);
            cStmtObject.execute();
            resObj[0]=cStmtObject.getString(11);
            
            conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl uploadDhpoGlobalNetNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl uploadDhpoGlobalNetNewTransactonDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return resObj;
    }//uploadDhpoGlobalNetNewTransactonDetails()
    
    public Object[] uploadHaadGlobalNetNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	OracleCallableStatement cStmtObject = null;
		Object[] resObj=new Object[1];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDHPOGlobalNetNewTransaction);
			XMLType poXML = null;
			  
			  String xmlFileContent=dhpoWebServiceVO.getXmlFileContent() ;
			  xmlFileContent=xmlFileContent.replaceAll("[^\\x20-\\x7e\n]", "");	
			poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), xmlFileContent);
			
			cStmtObject.setString(1, null);
			cStmtObject.setString(2, dhpoWebServiceVO.getFileID());			
			cStmtObject.setString(3, dhpoWebServiceVO.getFileName());			
			cStmtObject.setObject(4, poXML);
			cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
			cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
			cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
			cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
			cStmtObject.setString(9, "N");
			cStmtObject.setString(10, dhpoWebServiceVO.getDhpoLicenceNO());
			cStmtObject.registerOutParameter(11, OracleTypes.VARCHAR);
            cStmtObject.execute();
            resObj[0]=cStmtObject.getString(11);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl uploadDhpoGlobalNetNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl uploadDhpoGlobalNetNewTransactonDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return resObj;
    }//uploadHaddGlobalNetNewTransactonDetails()
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public List<DhpoWebServiceVO> getDhpoNewTransactonDetails() throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		ResultSet rs=null;
		DhpoWebServiceVO webServiceVO=null;
		List<DhpoWebServiceVO> listVOs=new ArrayList<DhpoWebServiceVO>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetDHPOGlobalNetNewTransaction);
			cStmtObject.registerOutParameter(1, Types.OTHER);
            cStmtObject.execute();
            rs = (ResultSet)cStmtObject.getObject(1);
			if(rs!=null){
				while(rs.next()){
					
					webServiceVO=new DhpoWebServiceVO();
					webServiceVO.setFileID(rs.getString("FILE_ID"));
					webServiceVO.setFileName(rs.getString("FILE_NAME"));
					webServiceVO.setFileType(rs.getString("FILE_TYPE"));
					webServiceVO.setDownloadStatus(rs.getString("DOWN_LOAD_STATUS"));
					webServiceVO.setTransactionResult(rs.getInt("RESULT_TYPE"));					
					webServiceVO.setXmlFileReader(rs.getCharacterStream("down_load_file"));
					webServiceVO.setDhpoLicenceNO(rs.getString("RECIEVER_ID"));
					
					listVOs.add(webServiceVO);
				}
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
				
        		try
        		{
        			if (rs != null) rs.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the ResultSet in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the stm now.
        		{
				
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
			 }//end of finally stm Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs=null;
				cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return listVOs;
    }//getDhpoNewTransactonDetails
    
    public Object saveBifurcationDetails(List<DhpoWebServiceVO> dhpoWebServiceVOs) throws TTKException{

    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		int[]result=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSaveBifurcationDetails);
			
			
			if(dhpoWebServiceVOs!=null&&dhpoWebServiceVOs.size()>0){
				
				for(DhpoWebServiceVO dhpoWebServiceVO:dhpoWebServiceVOs){
									
					try{
				SQLXML sqlxml=conn.createSQLXML();
				   sqlxml.setString( dhpoWebServiceVO.getXmlFileContent());
					cStmtObject.setLong(1, 0);
					cStmtObject.setString(2, dhpoWebServiceVO.getFileID());
					cStmtObject.setString(3, dhpoWebServiceVO.getFileName());
					cStmtObject.setObject(4, sqlxml);
					cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
					cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
					cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
					cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
					cStmtObject.setString(9, dhpoWebServiceVO.getClaimFrom());
					if(dhpoWebServiceVO.getDhpoTxDate()!=null){						
					cStmtObject.setString(10, dhpoWebServiceVO.getDhpoTxDate());
					}else cStmtObject.setString(10, null);
					cStmtObject.setInt(11, Integer.parseInt(dhpoWebServiceVO.getDhpoClaimRecCount().trim()));
					cStmtObject.setInt(12, (dhpoWebServiceVO.getClaimRecCount()==null||"".equals(dhpoWebServiceVO.getClaimRecCount())?0:Integer.parseInt(dhpoWebServiceVO.getClaimRecCount().trim())));
					cStmtObject.setString(13, dhpoWebServiceVO.getProviderID());
					cStmtObject.setString(14, dhpoWebServiceVO.getDhpoLicenceNO());
					//cStmtObject.registerOutParameter(10, OracleTypes.VARCHAR);
					//cStmtObject.addBatch();
					cStmtObject.executeUpdate();
					}catch(Exception ee){
						
						System.out.println("FileID::"+dhpoWebServiceVO.getFileID()+" FileName::"+dhpoWebServiceVO.getFileName());
						ee.printStackTrace();
					}
					
				}
			}
			//cStmtObject.executeBatch();		
			conn.commit();
		}//end of try
		
		catch (Exception sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveBifurcationDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveBifurcationDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return result;
    
    }//saveBifurcationDetails
    
    public Object[] saveDhpoPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		Object[] resObj=new Object[1];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDHPOPreauthDownload);
			  String xmlFileContent=dhpoWebServiceVO.getXmlFileContent() ;
			  String xmlText=new String(xmlFileContent.getBytes(), Charset.defaultCharset());
		//	    System.out.println("Be xml="+xmlText);
			    xmlText=xmlText.replaceAll("[^\\x20-\\x7e\n]", "");
			  
		            SQLXML	sqlxml=conn.createSQLXML();
		            sqlxml.setString(xmlText);
			cStmtObject.setLong(1, 0);
			cStmtObject.setString(2, dhpoWebServiceVO.getFileID());			
			cStmtObject.setString(3, dhpoWebServiceVO.getFileName());			
			cStmtObject.setSQLXML(4, sqlxml);
			cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
			cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
			cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
			cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
			cStmtObject.setString(9, dhpoWebServiceVO.getDhpoLicenceNO());
			cStmtObject.registerOutParameter(10, Types.VARCHAR);
            cStmtObject.execute();
            resObj[0]=cStmtObject.getString(10);
            conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveDhpoPreauthDownloadDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveDhpoPreauthDownloadDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return resObj;
    }//saveDhpoPreauthDownloadDetails()
   
    
    public Object[] saveDhpoGlobalnetPreauthDownloadDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		Object[] resObj=new Object[1];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDHPOGlobalnetPreauthDownload);
			  
			 // String xmlFileContent=dhpoWebServiceVO.getXmlFileContent() ;
			 // xmlFileContent=xmlFileContent.replaceAll("[^\\x20-\\x7e\n]", "");	
			
			//String xmlText=new String(xmlFileContent.getBytes(), Charset.defaultCharset());
		//    System.out.println("Be xml="+xmlText);
		  //  xmlText=xmlText.replaceAll("[^\\x20-\\x7e\n]", "");
          SQLXML sqlxml=conn.createSQLXML();
          sqlxml.setString(dhpoWebServiceVO.getXmlFileContent());
			
			cStmtObject.setLong(1, 0);
			cStmtObject.setString(2, dhpoWebServiceVO.getFileID());			
			cStmtObject.setString(3, dhpoWebServiceVO.getFileName());			
			cStmtObject.setSQLXML(4, sqlxml);
			cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
			cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
			cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
			cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
			cStmtObject.setString(9, dhpoWebServiceVO.getBifurcationYN());
			cStmtObject.setString(10, dhpoWebServiceVO.getDhpoLicenceNO());
			cStmtObject.registerOutParameter(11, Types.VARCHAR);
            cStmtObject.execute();
            resObj[0]=cStmtObject.getString(11);
            conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveDhpoGlobalnetPreauthDownloadDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveDhpoGlobalnetPreauthDownloadDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return resObj;
    }//saveDhpoPreauthDownloadDetails()
    
    
    /**
     * This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @return This method get the Dhpo New Transacton Details records where bifurcation not done   
     * @exception throws TTKException
     */
    public List<DhpoWebServiceVO> getDhpoGlobalnetPreAuthTransactonDetails() throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		ResultSet rs=null;
		DhpoWebServiceVO webServiceVO=null;
		List<DhpoWebServiceVO> listVOs=new ArrayList<DhpoWebServiceVO>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =conn.prepareCall(strGetDHPOGlobalNetPreAuthTransactionDetails);
			cStmtObject.registerOutParameter(1, Types.OTHER);
            cStmtObject.execute();
            rs = (ResultSet)cStmtObject.getObject(1);
			if(rs!=null){
				while(rs.next()){
					
					webServiceVO=new DhpoWebServiceVO();
					webServiceVO.setFileID(rs.getString("FILE_ID"));
					webServiceVO.setFileName(rs.getString("FILE_NAME"));
					webServiceVO.setFileType(rs.getString("FILE_TYPE"));
					webServiceVO.setDownloadStatus(rs.getString("DOWN_LOAD_STATUS"));
					webServiceVO.setTransactionResult(rs.getInt("RESULT_TYPE"));					
					webServiceVO.setXmlFileReader(rs.getCharacterStream("XML_FILE"));					
					
					listVOs.add(webServiceVO);
				}
			}
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
			
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
			 try{
			   if (rs != null) rs.close();	
			   }
			catch (SQLException sqlExp)
		    {
			log.error("Error while closing the ResultSet in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
			throw new TTKException(sqlExp, "communication");
		    }//end of catch (Exception exp)
			finally
		   {
				
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getDhpoNewTransactonDetails()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
				}//end of rs finally 
				
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs=null;
				cStmtObject = null;
        		conn = null;
        	}//end of finally		   
		}//end of finally
		return listVOs;
    }//getDhpoNewTransactonDetails
    
    
    
    public Object[] uploadDhpoNewTransactonDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		Object[] resObj=new Object[1];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDHPONewTransaction);
			  String xmlFileContent=dhpoWebServiceVO.getXmlFileContent() ;
			  //xmlFileContent=xmlFileContent.replaceAll("[^\\x20-\\x7e\n]", "");
             SQLXML sqlxml=conn.createSQLXML();
             sqlxml.setString(xmlFileContent);
			cStmtObject.setLong(1, 0);
			cStmtObject.setString(2, dhpoWebServiceVO.getFileID());			
			cStmtObject.setString(3, dhpoWebServiceVO.getFileName());			
			cStmtObject.setSQLXML(4, sqlxml);
			cStmtObject.setString(5, dhpoWebServiceVO.getFileType());
			cStmtObject.setString(6, dhpoWebServiceVO.getDownloadStatus());
			cStmtObject.setInt(7, dhpoWebServiceVO.getTransactionResult());
			cStmtObject.setString(8, dhpoWebServiceVO.getErrorMessage());
			cStmtObject.setString(9, dhpoWebServiceVO.getDhpoLicenceNO());
			cStmtObject.registerOutParameter(10, Types.VARCHAR);
            cStmtObject.execute();
            resObj[0]=cStmtObject.getString(10);
            conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return resObj;
    }//uploadDhpoNewTransactonDetails()
    
    //=======================
    
    
    public String isMemberBelongs(String memID,String emirateID) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
    	String isMemBelongsTo="";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =conn.prepareCall(strisMemberExist);
			
			cStmtObject.setString(1, memID);
			cStmtObject.setString(2, emirateID);
			cStmtObject.registerOutParameter(3, Types.VARCHAR);
            cStmtObject.execute();
            
            isMemBelongsTo=cStmtObject.getString(3);
           
           
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return isMemBelongsTo;
    }//uploadDhpoNewTransactonDetails()


	/*public boolean remittanceAdviceFileDetail() throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
		boolean flag;
    	OracleCallableStatement cStmtObject = null;
    
    	  System.out.println("  inside MaintenanceDAOImpl remittanceAdviceFileDetail method");
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strremittancefiledetail);
			
			
			flag =   cStmtObject.execute();
            
          
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        			
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl dhpoNewTransacton()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		
		return flag;
	}
*/

	public List<DhpoWebServiceVO> getRemittanceAdviceDHPOFileUpload() throws TTKException
	{
		
		Connection conn = null;
		List<DhpoWebServiceVO> 	uploadRAList  = new ArrayList<DhpoWebServiceVO>();
		DhpoWebServiceVO dhpowebservicebj = null;
		CallableStatement cStmtObject = null;

		ResultSet rs1=null; 
		try{
			conn = ResourceManager.getConnection();

			cStmtObject = conn.prepareCall(strRemitanceDHPOFileUpload);
		
			cStmtObject.registerOutParameter(1, Types.OTHER);
			cStmtObject.execute();

			rs1 =  (ResultSet) cStmtObject.getObject(1);
			while( rs1.next()){

				dhpowebservicebj = new DhpoWebServiceVO();

				
				if(rs1.getString("FILE_SEQ_ID")!=null){

					dhpowebservicebj.setFileSeqID(rs1.getLong("FILE_SEQ_ID"));
				}

				if(rs1.getString("FILE_NAME")!=null)
				{
					dhpowebservicebj.setFileName(rs1.getString("FILE_NAME"));   
				}
				if(rs1.getSQLXML("RA_FILE")!=null)
				{
					dhpowebservicebj.setXmlFileReader(rs1.getSQLXML("RA_FILE").getCharacterStream());	
				}

				if(rs1.getString("SETTLEMENT_NUMBER")!=null)
				{
					dhpowebservicebj.setClaimsettlementnumber(rs1.getString("SETTLEMENT_NUMBER"));  
				}

				if(rs1.getString("CLAIM_SEQ_ID")!=null)
				{
					dhpowebservicebj.setClaimSeqId(rs1.getLong("CLAIM_SEQ_ID"));
				}

				if(rs1.getString("SENDER_ID")!=null)
				{
					dhpowebservicebj.setSenderID(rs1.getString("SENDER_ID"));
				}
				
				
				uploadRAList.add(dhpowebservicebj);
			}

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the Statement
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOIMplImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
						throw new TTKException(sqlExp, "communication");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{

							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
							throw new TTKException(sqlExp, "communication");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
			}
			catch (TTKException exp)
			{
				throw new TTKException(exp, "communication");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{ 
				rs1=null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

		return uploadRAList;
	}


	public int getRemittanceAdviceDHPOStatus(DhpoWebServiceVO  dhpoWebServiceVO) throws TTKException {		
		Connection conn = null;
		int update=0;
    	CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			
			cStmtObject =conn.prepareCall(strGetRemitanceDHPOFileUpload);
		   cStmtObject.setLong(1, dhpoWebServiceVO.getFileSeqID());
	         cStmtObject.setString(2, dhpoWebServiceVO.getFileName());
			 cStmtObject.setSQLXML(3, null);
			 cStmtObject.setString(4,dhpoWebServiceVO.getClaimsettlementnumber());
			 cStmtObject.setLong(5, dhpoWebServiceVO.getClaimSeqId());
			 cStmtObject.setString(6, dhpoWebServiceVO.getFlag_YN());
		     cStmtObject.setString(7, dhpoWebServiceVO.getErrorMessage());
		     cStmtObject.setString(8,(dhpoWebServiceVO.getErrorReport()==null?"":new String(dhpoWebServiceVO.getErrorReport())));
			   update =  cStmtObject.executeUpdate();
			   
			   conn.commit();
          
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getRemittanceAdviceDHPOStatus()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getRemittanceAdviceDHPOStatus()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		
		return update;
	}
	
	public int getMemberUploadHaadStatus(HaadWebServiceVo  haadWebServiceVO) throws TTKException {		
		Connection conn = null;
		int update=0;
    	CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			
			cStmtObject = conn.prepareCall(strGetRemitanceDHPOFileUpload);
		   cStmtObject.setLong(1, haadWebServiceVO.getFileSeqID());
	         cStmtObject.setString(2, haadWebServiceVO.getFileName());
			 cStmtObject.setObject(3, null);
			 cStmtObject.setString(4,haadWebServiceVO.getClaimsettlementnumber());
			 cStmtObject.setLong(5, haadWebServiceVO.getClaimSeqId());
			 cStmtObject.setString(6, haadWebServiceVO.getFlag_YN());
		     cStmtObject.setString(7, haadWebServiceVO.getErrorMessage());
	         cStmtObject.setString(8,(haadWebServiceVO.getErrorReport()==null?"":new String(haadWebServiceVO.getErrorReport())));
			 update =  cStmtObject.executeUpdate();
			 conn.commit();
          
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getRemittanceAdviceDHPOStatus()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getRemittanceAdviceDHPOStatus()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		
		return update;
	}
	
	
	public ArrayList<DhpoWebServiceVO> getDhpoVHPriorAuthorizationList() throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject = null;
		ResultSet rs=null;
		DhpoWebServiceVO webServiceVO=null;
		ArrayList<DhpoWebServiceVO> preAutList=new ArrayList<DhpoWebServiceVO>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =conn.prepareCall(strGetVHDHPOPreAuthUploadedDetails);
			cStmtObject.registerOutParameter(1, Types.OTHER);
            cStmtObject.execute();
            rs = (ResultSet)cStmtObject.getObject(1);
			if(rs!=null){
				while(rs.next()){
					webServiceVO=new DhpoWebServiceVO();
					webServiceVO.setFileID(rs.getString("FILE_ID"));
					webServiceVO.setFileName(rs.getString("FILE_NAME"));
					webServiceVO.setPreAuthSeqID(rs.getString("PAT_SEQ_ID"));
					webServiceVO.setDhpoLicenceNO(rs.getString("SENDER_ID"));
					webServiceVO.setXmlFileReader(rs.getCharacterStream("XML_FILE"));
					preAutList.add(webServiceVO);
				}
			}
    
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
	    		try
	    		{
	    			if (rs != null) rs.close();
	    		}//end of try
	    		catch (SQLException sqlExp)
	    		{
	    			log.error("Error while closing the rs in MaintenanceDAOImpl getDhpoVHPriorAuthorizationList()",sqlExp);
	    			throw new TTKException(sqlExp, "communication");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if RS is not closed, control reaches here. Try closing the connection now.
	    		{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MaintenanceDAOImpl getDhpoVHPriorAuthorizationList()",sqlExp);
        			throw new TTKException(sqlExp, "communication");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MaintenanceDAOImpl getDhpoVHPriorAuthorizationList()",sqlExp);
        				throw new TTKException(sqlExp, "communication");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Statement Close
        	}//end of finally RS Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "communication");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		
		return preAutList;
    }
	
	
	 public Object[] saveVHDhpoPreauthUploadDetails(ArrayList<DhpoWebServiceVO> dhpoWebServiceVOList) throws TTKException{

	    	Connection conn = null;
	    	CallableStatement cStmtObject = null;
	    	Object[]result=new Object[1];
			try{
				conn = ResourceManager.getConnection();
				cStmtObject = conn.prepareCall(strSaveVHDHPOPreAuthUploadedDetails);
							
				if(dhpoWebServiceVOList!=null&&dhpoWebServiceVOList.size()>0){
					
					for(DhpoWebServiceVO dhpoWebServiceVO:dhpoWebServiceVOList){
										
						try{
						
						cStmtObject.setBigDecimal(1, new BigDecimal(dhpoWebServiceVO.getPreAuthSeqID()));
						cStmtObject.setString(2, dhpoWebServiceVO.getFileID());
						cStmtObject.setString(3, dhpoWebServiceVO.getFileName());
						cStmtObject.setString(4, dhpoWebServiceVO.getDhpoLicenceNO());
						cStmtObject.setString(5, dhpoWebServiceVO.getErrorMessage());
						
						byte[] errorData=dhpoWebServiceVO.getErrorReport();
						if(errorData!=null){
							  cStmtObject.setString(6, new String(errorData));
						}else{
					           cStmtObject.setString(6,null);
						}
						cStmtObject.setString(7, dhpoWebServiceVO.getPreAuthUploadStatus());
						cStmtObject.executeUpdate();
						}catch(Exception ee){
							
							System.out.println("FileID::"+dhpoWebServiceVO.getFileID()+" FileName::"+dhpoWebServiceVO.getFileName());
							ee.printStackTrace();
						}
						
					}//for(DhpoWebServiceVO dhpoWebServiceVO:dhpoWebServiceVOList){
				}//if(dhpoWebServiceVOList!=null&&dhpoWebServiceVOList.size()>0){
				conn.commit();			
			}//end of try
			catch (Exception sqlExp)
			{
				throw new TTKException(sqlExp, "communication");
			}//end of catch (SQLException sqlExp)
			
			finally
			{
	        	/* Nested Try Catch to ensure resource closure */ 
	        	try // First try closing the Statement
	        	{
	        		try
	        		{
	        			if (cStmtObject != null) cStmtObject.close();
	        		}//end of try
	        		catch (SQLException sqlExp)
	        		{
	        			log.error("Error while closing the Statement in MaintenanceDAOImpl saveVHDhpoPreauthUploadDetails()",sqlExp);
	        			throw new TTKException(sqlExp, "communication");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in MaintenanceDAOImpl saveVHDhpoPreauthUploadDetails()",sqlExp);
	        				throw new TTKException(sqlExp, "communication");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "communication");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
			return result;
	 }
	 public List<HaadWebServiceVo> getMemberHaadUploadXML(ArrayList<String> allData) throws TTKException
		{
			
			Connection conn = null;
			List<HaadWebServiceVo> 	uploadList  = new ArrayList<HaadWebServiceVo>();
			HaadWebServiceVo haadwebservicebj = null;
			OracleCallableStatement cStmtObject = null;

			OracleResultSet rs1=null; 
			try{
				conn = ResourceManager.getConnection();

				conn=ResourceManager.getConnection();
				cStmtObject=(OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall("strgetMemberXML");
				cStmtObject.setString(1,allData.get(0));
				cStmtObject.setString(2,allData.get(1));
				cStmtObject.setString(3,allData.get(2));
				cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
				cStmtObject.execute();
				
				rs1 =  (OracleResultSet) cStmtObject.getObject(4);
				while( rs1.next()){

					haadwebservicebj = new HaadWebServiceVo();

					
					if(rs1.getString("FILE_SEQ_ID")!=null){

						haadwebservicebj.setFileSeqID(rs1.getLong("FILE_SEQ_ID"));
					}

					if(rs1.getString("FILE_NAME")!=null)
					{
						haadwebservicebj.setFileName(rs1.getString("FILE_NAME"));   
					}

					if(rs1.getCharacterStream("RA_FILE")!=null)
					{
						//dhpowebservicebj.setFileContent((rs1.getOPAQUE("RA_FILE").getBytes()));
						haadwebservicebj.setXmlFileReader(rs1.getCharacterStream("RA_FILE"));	
					}

					if(rs1.getString("SETTLEMENT_NUMBER")!=null)
					{
						haadwebservicebj.setClaimsettlementnumber(rs1.getString("SETTLEMENT_NUMBER"));  
					}

					if(rs1.getString("CLAIM_SEQ_ID")!=null)
					{
						haadwebservicebj.setClaimSeqId(rs1.getLong("CLAIM_SEQ_ID"));
					}

					if(rs1.getString("SENDER_ID")!=null)
					{
						haadwebservicebj.setSenderID(rs1.getString("SENDER_ID"));
					}
					
					
					uploadList.add(haadwebservicebj);
				}

			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "communication");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "communication");
			}//end of catch (Exception exp)
			finally
			{
				/* Nested Try Catch to ensure resource closure */ 
				try // First try closing the Statement
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in MaintenanceDAOIMplImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
							throw new TTKException(sqlExp, "communication");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{

								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
								throw new TTKException(sqlExp, "communication");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of try
				}
				catch (TTKException exp)
				{
					throw new TTKException(exp, "communication");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{ 
					rs1=null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally

			return uploadList;
		}



public String UploadActiveMemberToOMAN123(ProcedureDetailVO jsonFile, String string, String format) throws IOException, TTKException{
	
	
	 String result = "";
	 String strSuccessMsg="";
	// String jsonContent = new String(Files.readAllBytes(jsonFile.toPath()));
	// System.out.println("File Content ==>>>>>"+jsonContent);

	 String authHeader = "Basic "+DatatypeConverter.printBase64Binary("vidal_health:$r@MSJ7us=".getBytes("UTF-8"));
	 System.out.println("authHeader====>"+authHeader);
     HttpPost post = new HttpPost("http://91.132.66.94:8333/updateDhamaniRegistry/");
     post.addHeader("Authorization",authHeader);
    
     JSONObject object = new JSONObject();
     
     object.put("Transaction_ID",jsonFile.getTransactionId());
     object.put("Sender_ID", "INS0123");
     object.put("Data", jsonFile.getOmaJsonInput());
     
    System.out.println("Request object ==>>>"+ object.toJSONString());
    String jsonString="";
    String s1=object.toJSONString();  
    if(s1!=null)
    jsonString=s1.replaceAll("\\\\","");
    System.out.println("jsonString ==>>>"+ jsonString);
     // send a JSON data
    // post.setEntity(new StringEntity(object.toJSONString()));
     post.setEntity(new StringEntity(jsonString));
     try (CloseableHttpClient httpClient = HttpClients.createDefault();
          CloseableHttpResponse response = httpClient.execute(post)) {
        
         JSONParser jsonParser = new JSONParser();
         JSONObject responseObject;
         int status=0;
		try {
			ArrayList<String> allinformation=new ArrayList<>();
			result = EntityUtils.toString(response.getEntity());
		   System.out.println("result=====>"+result);
			responseObject = (JSONObject) jsonParser.parse(result);
			boolean successMsg = (boolean) responseObject.get("Success");
		   strSuccessMsg = String.valueOf(successMsg);
		   String transactionId=(String) responseObject.get("Transaction_ID");
        	 String inputFile = jsonFile.getOmaJsonInput();
			 if(!(boolean)responseObject.get("Success") && responseObject.containsKey("Errors")){
	        	 JSONArray errorsArray = (JSONArray) responseObject.get("Errors");
	        	 String outPutFile = errorsArray.toJSONString();
	        	 /*for(int i=0;i<errorsArray.size();i++){
	        		 JSONObject error = (JSONObject) errorsArray.get(i);
	        		 int index = (int)error.get("index");
	        		 String errorMsg = (String)error.get("error");
	        	 }*/
	        	 
	        	 allinformation.add(transactionId);
	        	// allinformation.add(strSuccessMsg);
	        	 allinformation.add(outPutFile);
	            // status= saveOmanMemberUploadLogFile(allinformation);
			 }else{
				 allinformation.add(transactionId);
				 //allinformation.add(strSuccessMsg);
	        	 allinformation.add("");
	        	// status = saveOmanMemberUploadLogFile(allinformation);
	         }
	         System.out.println("API Status ==>>>>> "+ status);
	         
	         if(status>0){
	        	 System.out.println("Response saved successfully");
	         }
	         
	         
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     }

     return strSuccessMsg;

}

public int saveOmanMemberUploadLogFile(String memberResponse) throws TTKException{

	Connection conn=null;
	CallableStatement cStmtObject = null;
         try 
         {
			conn=ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strOmanLogDetails);
			cStmtObject.setString(1, memberResponse);
			cStmtObject.execute();
			conn.commit();
			int result = 1;
		return result;
		 }
         catch (SQLException sqlExp)
     	{
     		throw new TTKException(sqlExp, "communication");
     	}//end of catch (SQLException sqlExp)
     	catch (Exception exp)
     	{
     		throw new TTKException(exp, "communication");
     	}//end of catch (Exception exp)
    finally
    {
    	try // First try closing the Statement
		{
			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in MaintenanceDAOImpl saveOmanMemberUploadLogFile()",sqlExp);
				throw new TTKException(sqlExp, "communication");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in MaintenanceDAOImpl saveOmanMemberUploadLogFile()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
}


public ProcedureDetailVO getOMANMemberUploadData() throws TTKException{
	Connection conn=null;
	CallableStatement cStmtObject = null;
	ProcedureDetailVO procedureDetailVO = null;
         try 
         {
        	 procedureDetailVO=new ProcedureDetailVO();
			conn=ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strgetOMANMemberData);
			cStmtObject.registerOutParameter(1,Types.OTHER);
			cStmtObject.execute();
			conn.commit();
			Object content = cStmtObject.getObject(1);
			if(content!=null){
				procedureDetailVO.setOmaJsonInput(content.toString());
			}
		//	System.out.println("content====>"+content);
			System.out.println("MaintenanceDAOImpl: getOMANMemberUploadData(): Payload From database ->"+content);	
			return procedureDetailVO;
		 }
         catch (SQLException sqlExp)
     	{
     		throw new TTKException(sqlExp, "communication");
     	}//end of catch (SQLException sqlExp)
     	catch (Exception exp)
     	{
     		throw new TTKException(exp, "communication");
     	}//end of catch (Exception exp)
    finally
    {
    	try // First try closing the Statement
		{
			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in MaintenanceDAOImpl getOMANMemberUploadData()",sqlExp);
				throw new TTKException(sqlExp, "communication");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in MaintenanceDAOImpl getOMANMemberUploadData()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
}
	public int cmaPayLoadBackUp(String stringEntity,String result,String transaction_Id,String SuccessFailRes,String errorMsg,String Ben_Ins_ID,String flag) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject = null;
	int res=0;
	try{	
		System.out.println("=========== saving cmaPayLoadBackUp data and res ==============");
		conn = ResourceManager.getConnection();
		cStmtObject =conn.prepareCall(strSaveCMAEnrollData);
		cStmtObject.setString(1, stringEntity);				// json data db
		cStmtObject.setString(2, result);					// response from cma
		cStmtObject.setString(3, transaction_Id);			// transaction_Id
		cStmtObject.setString(4, SuccessFailRes);			// SuccessFailRes
		cStmtObject.setString(5, errorMsg);					// errorMsg
		cStmtObject.setString(6, Ben_Ins_ID);				// Ben_Ins_ID
		cStmtObject.setString(7, flag);						// flag
        cStmtObject.execute();
        conn.commit();
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "communication");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "communication");
	}//end of catch (Exception exp)
	finally
	{
    	/* Nested Try Catch to ensure resource closure */ 
    	try // First try closing the Statement
    	{
    		try
    		{
    			if (cStmtObject != null) cStmtObject.close();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			log.error("Error while closing the Statement in MaintenanceDAOImpl cmaPayLoadBackUp()",sqlExp);
    			throw new TTKException(sqlExp, "communication");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in MaintenanceDAOImpl cmaPayLoadBackUp()",sqlExp);
    				throw new TTKException(sqlExp, "communication");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "communication");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally
	return res;
}//cmaPayLoadBackUp()
}//end of MaintenanceDAOImpl
