/**
 * @ (#) WorkflowDAOImpl.java Dec 16, 2005
 * Project      : TTK HealthCare Services
 * File         : WorkflowDAOImpl.java
 * Author       : Bharat
 * Company      : Span Systems Corporation
 * Date Created : Dec 16, 2005
 *
 * @author       :  Bharat
 * Modified by   :  Bharat 
 * Modified date :  Jan 6, 2005
 * Reason        :  Renaming the Stored Procedures
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.security.RoleVO;


public class WorkflowDAOImpl implements BaseDAO,Serializable {
	
	private static Logger log = Logger.getLogger(WorkflowDAOImpl.class);
	
	private static final String strGetWorkflowList = "SELECT TPA_WORKFLOW.WORKFLOW_SEQ_ID, WORKFLOW_NAME, EVENT_SEQ_ID, EVENT_NAME FROM APP.TPA_WORKFLOW, APP.TPA_EVENT WHERE TPA_WORKFLOW.WORKFLOW_SEQ_ID = TPA_EVENT.WORKFLOW_SEQ_ID ORDER BY TPA_WORKFLOW.WORKFLOW_SEQ_ID, EVENT_SEQ_ID";
	private static final String strGetEventInfo = "{CALL WORKFLOW_PKG_SELECT_EVENT_DETAILS(?,?,?)}";
	private static final String strAddUpdateWorkflowInfo = "{CALL WORKFLOW_PKG_SAVE_EVENT_DETAILS(?,?,?,?,?,?,?,?,?)}";
	
	private static final int EVENT_SEQ_ID = 1;
	private static final int EVENT_NAME = 2;
	private static final int EVENT_DESCRIPTION = 3;
	private static final int SIMPLE_REVIEW_COUNT = 4;
	private static final int MEDIUM_REVIEW_COUNT = 5;
	private static final int COMPLEX_REVIEW_COUNT = 6;
	private static final int ROLE_SEQ_ID = 7;
	private static final int USER_SEQ_ID = 8;
	private static final int ROW_PROCESSED = 9;
	
	/**
	 * This method returns the ArrayList Which contains the Workflow List 
	 * @return ArrayList which contains the Workflow List
	 * @throws TTKException
	 */
	public ArrayList getWorkflowList() throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String strStaticQuery = strGetWorkflowList;
		WorkflowVO workflowVO = null;
		EventVO eventVO = null;
		Long lngWorkflowSeqId = null;
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			ArrayList<Object> alResultEventList = new ArrayList<Object>();
			ArrayList<Object> alResultList = new ArrayList<Object>();
			if(rs != null){
				while (rs.next()) {
					if (lngWorkflowSeqId == null || lngWorkflowSeqId != rs.getLong("WORKFLOW_SEQ_ID"))
					{
						if (lngWorkflowSeqId != null ){
							workflowVO.setEventVO(alResultEventList);
							alResultList.add(workflowVO);
						}// End of if (lngWorkflowSeqId != null )
						alResultEventList = new ArrayList<Object>();
						workflowVO = new WorkflowVO();	
						lngWorkflowSeqId = rs.getLong("WORKFLOW_SEQ_ID");
						workflowVO.setWorkflowSeqID(rs.getString("WORKFLOW_SEQ_ID") != null ? new Long(rs.getLong("WORKFLOW_SEQ_ID")):null);
						workflowVO.setWorkflowName(TTKCommon.checkNull(rs.getString("WORKFLOW_NAME")));
					}//end of if (lngWorkflowSeqId == null || lngWorkflowSeqId != rs.getLong("WORKFLOW_SEQ_ID"))
					eventVO = new EventVO();
					eventVO.setEventSeqID(rs.getString("EVENT_SEQ_ID") !=  null ? new Long(rs.getLong("EVENT_SEQ_ID")):null);
					eventVO.setEventName(new String(rs.getString("EVENT_NAME")));
					alResultEventList.add(eventVO);
				}//end of while
				workflowVO.setEventVO(alResultEventList);
				alResultList.add(workflowVO);
			}//end of if(rs != null)
			return alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "workflow");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "workflow");
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
					log.error("Error while closing the Resultset in WorkflowDAOImpl getWorkflowList()",sqlExp);
					throw new TTKException(sqlExp, "workflow");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WorkflowDAOImpl getWorkflowList()",sqlExp);
						throw new TTKException(sqlExp, "workflow");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WorkflowDAOImpl getWorkflowList()",sqlExp);
							throw new TTKException(sqlExp, "workflow");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "workflow");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getWorkflowList()
	
	/** This method returns the ArrayList Which contains the Event details 
	 * @param lEventSeqId for Event Seq Id
	 * @return ArrayList which contains the Event details
	 * @exception throws TTKException
	 */
	public EventVO getWorkflowEvent(Long lngEventSeqId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rsRole = null;
		ResultSet rsEvent= null;
		ArrayList<Object> alRoleList = null ;
		ArrayList<Object> alUnAssocRoleList = null ;
		EventVO eventVO = null;
		RoleVO roleVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetEventInfo);
			cStmtObject.setLong(1,lngEventSeqId);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();   
			rsEvent = (java.sql.ResultSet)cStmtObject.getObject(2);
			rsRole = (java.sql.ResultSet)cStmtObject.getObject(3);
			if (rsEvent!=null)
			{
				while(rsEvent.next()) {
					eventVO = new EventVO();
					if(rsEvent.getString("EVENT_SEQ_ID") != null){
						eventVO.setEventSeqID(new Long(rsEvent.getLong("EVENT_SEQ_ID")));
					}//end of if(rsEvent.getString("EVENT_SEQ_ID") != null)
					eventVO.setEventName(TTKCommon.checkNull(rsEvent.getString("EVENT_NAME")));
					eventVO.setEventDesc(TTKCommon.checkNull(rsEvent.getString("EVENT_DESCRIPTION")));
					if(rsEvent.getString("SIMPLE_REVIEW_COUNT") != null){
						eventVO.setSimpleCount(new Integer(rsEvent.getInt("SIMPLE_REVIEW_COUNT")));
					}//end of if(rsEvent.getString("SIMPLE_REVIEW_COUNT") != null)
					if(rsEvent.getString("MEDIUM_REVIEW_COUNT") != null){
						eventVO.setMediumCount(new Integer(rsEvent.getInt("MEDIUM_REVIEW_COUNT")));
					}//end of if(rsEvent.getString("MEDIUM_REVIEW_COUNT") != null)
					if(rsEvent.getString("COMPLEX_REVIEW_COUNT") != null){
						eventVO.setComplexCount(new Integer(rsEvent.getInt("COMPLEX_REVIEW_COUNT")));
					}//end of if(rsEvent.getString("COMPLEX_REVIEW_COUNT") != null)
					if (rsRole!=null)
					{
						while(rsRole.next()) {
							if (alRoleList == null){
								alRoleList = new ArrayList<Object>();
							}//end of if (alRoleList == null)
							if (alUnAssocRoleList == null){
								alUnAssocRoleList = new ArrayList<Object>();
							}//end of if (alUnAssocRoleList == null)
							roleVO = new RoleVO();
							if(rsRole.getString("ROLE_SEQ_ID") != null){
								roleVO.setRoleSeqID(new Long(rsRole.getLong("ROLE_SEQ_ID")));
							}//end of if(rsRole.getString("ROLE_SEQ_ID") != null)
							roleVO.setRoleName(TTKCommon.checkNull(rsRole.getString("ROLE_NAME")));
							if(rsRole.getString("STATUS").equals("AS"))
							{
								alRoleList.add(roleVO);
								eventVO.setRole(alRoleList);
							}//end of if(rsRole.getString("STATUS").equals("AS"))
							else
							{
								alUnAssocRoleList.add(roleVO);
								eventVO.setUnAssociatedRole(alUnAssocRoleList);
							}//end of else
						}//end of inner while
					} //end of if (rsRole!=null)
				}//end of while
			} //end of if (rsEvent!=null)
			return eventVO;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "workflow");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "workflow");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rsRole != null) rsRole.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WorkflowDAOImpl getWorkflowEvent()",sqlExp);
					throw new TTKException(sqlExp, "workflow");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rsEvent != null) rsEvent.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in WorkflowDAOImpl getWorkflowEvent()",sqlExp);
						throw new TTKException(sqlExp, "workflow");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in WorkflowDAOImpl getWorkflowEvent()",sqlExp);
							throw new TTKException(sqlExp, "workflow");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in WorkflowDAOImpl getWorkflowEvent()",sqlExp);
								throw new TTKException(sqlExp, "workflow");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "workflow");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rsRole = null;
				rsEvent = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getWorkflowEvent(Long lEventSeqId)
	
	/**
	 * This method updates the EventVO which contains Event details
	 * @param EventVO the details which has to be updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveWorkflowEvent(EventVO eventVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;
		StringBuffer sbfRoleSeqId = new StringBuffer();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateWorkflowInfo);
			ArrayList alAssRoleList = new ArrayList();
			if(eventVO.getEventSeqID() == null){
				cStmtObject.setLong(EVENT_SEQ_ID,0);//EVENT_SEQ_ID
			}//end of if(eventVO.getEventSeqID() == null)
			else{
				cStmtObject.setLong(EVENT_SEQ_ID,eventVO.getEventSeqID());//EVENT_SEQ_ID
			}//end of else
			cStmtObject.setString(EVENT_NAME,eventVO.getEventName());//EVENT_NAME
			if(eventVO.getEventDesc() == null){
				cStmtObject.setString(EVENT_DESCRIPTION,null);//EVENT_DESCRIPTION
			}//end of if(eventVO.getEventDesc() == null)
			else{
				cStmtObject.setString(EVENT_DESCRIPTION,eventVO.getEventDesc());//EVENT_DESCRIPTION
			}//end of else
			cStmtObject.setInt(SIMPLE_REVIEW_COUNT,eventVO.getSimpleCount());//SIMPLE_REVIEW_COUNT
			cStmtObject.setInt(MEDIUM_REVIEW_COUNT,eventVO.getMediumCount());//MEDIUM_REVIEW_COUNT
			cStmtObject.setInt(COMPLEX_REVIEW_COUNT,eventVO.getComplexCount());//COMPLEX_REVIEW_COUNT
			alAssRoleList = eventVO.getRole();
			if(alAssRoleList!=null && (alAssRoleList.size()>=0)){			
				sbfRoleSeqId.append("|");
				for(int i=0; i<alAssRoleList.size(); i++)
				{
					sbfRoleSeqId.append(String.valueOf(((RoleVO)alAssRoleList.get(i)).getRoleSeqID())).append("|");
				}//end of for(int i=0; i<alAssRoleList.size(); i++)
			}//end of if(alAssRoleList!=null && (alAssRoleList.size()>=0))
			cStmtObject.setString(ROLE_SEQ_ID,sbfRoleSeqId.toString());//|ROLE_SEQ_ID| pipe concatenated
			cStmtObject.setLong(USER_SEQ_ID, eventVO.getUpdatedBy());//USER_SEQ_ID
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(ROW_PROCESSED);//ROW_PROCESSED
			
		}//end of try
		catch (SQLException sqlExp) 
		{ 
			throw new TTKException(sqlExp, "workflow");
		}//end of catch (SQLException sqlExp) 
		catch (Exception exp) 
		{
			throw new TTKException(exp, "workflow");
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
        			log.error("Error while closing the Statement in WorkflowDAOImpl saveWorkflowEvent()",sqlExp);
        			throw new TTKException(sqlExp, "workflow");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WorkflowDAOImpl saveWorkflowEvent()",sqlExp);
        				throw new TTKException(sqlExp, "workflow");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "workflow");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveWorkflowEvent(EventVO eventVO)
} //End of WorkflowDAOImpl