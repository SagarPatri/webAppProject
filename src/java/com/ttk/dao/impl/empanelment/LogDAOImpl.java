/**
 * @ (#) LogDAOImpl.java Sep 27, 2005
 * Project      :
 * File         : LogDAOImpl.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 27, 2005
 *
 * @author       :Suresh.M
 * Modified by   : Ramakrishna K M
 * Modified date : Dec 28, 2005
 * Reason        : To make the change in the search query
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.LogVO;

public class LogDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(LogDAOImpl.class);

	//private static final String strHopitalLogList= "SELECT * FROM(SELECT LOG_SEQ_ID,A.HOSP_SEQ_ID,A.SYSTEM_GEN_YN,A.LOG_TYPE_ID,A.MOD_REASON_TYPE_ID,A.REFERENCE_DATE,A.REFERENCE_NO,A.REMARKS,B.CONTACT_NAME,A.ADDED_BY,A.ADDED_DATE,A.UPDATED_BY,A.UPDATED_DATE,C.DESCRIPTION AS LOG_TYPE,DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM TPA_HOSP_LOG A,TPA_USER_CONTACTS B,TPA_HOSP_LOG_CODE C";
	private static final String strHopitalLogList= "SELECT * FROM (SELECT LOG_SEQ_ID,A.HOSP_SEQ_ID,A.SYSTEM_GEN_YN,A.LOG_TYPE_ID,A.MOD_REASON_TYPE_ID,A.REFERENCE_DATE,A.REFERENCE_NO,A.REMARKS,B.CONTACT_NAME,A.ADDED_BY,A.ADDED_DATE,A.UPDATED_BY,A.UPDATED_DATE,C.DESCRIPTION AS LOG_TYPE,/*DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q */ROW_NUMBER() OVER (ORDER BY #) Q FROM app.TPA_HOSP_LOG A,app.TPA_USER_CONTACTS B,app.TPA_HOSP_LOG_CODE C";
	private static final String strAddUpdateLog = "{CALL TTK_UTIL_PKG_PR_USER_LOG(?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strAddUpdateLog = "{CALL TTK_UTIL_PKG_PR_USER_LOG(?,?,?,?,?,?,?,?,?)}";
	// private static final String strPolicylogList =
	// "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_POLICY_LOG(?,?,?,?,?,?,?)}";
	private static final String strPolicylogList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_POLICY_LOG(?,?,?,?,?,?,?)}";
    private static final String strAddPolicylog = "{CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_LOG(?,?,?,?,?,?,?,?)}";
    private static final String strAddPreAuthLog = "{CALL PRE_AUTH_PKG.SAVE_PAT_LOG(?,?,?,?,?,?,?,?,?,?)}";
	// private static final String strPreauthLogList =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_PAT_LOG(?,?,?,?,?,?,?,?)}";
    private static final String strPreauthLogList = "{CALL PRE_AUTH_PKG.SELECT_PAT_LOG(?,?,?,?,?,?,?,?)}";
	private static final String strPartnerLogList = "SELECT * FROM(SELECT LOG_SEQ_ID,A.PTNR_SEQ_ID,A.SYSTEM_GEN_YN,A.LOG_TYPE_ID,A.MOD_REASON_TYPE_ID,A.REFERENCE_DATE,A.REFERENCE_NO,A.REMARKS,B.CONTACT_NAME,A.ADDED_BY,A.ADDED_DATE,A.UPDATED_BY,A.UPDATED_DATE,C.DESCRIPTION AS LOG_TYPE,DENSE_RANK() OVER (ORDER BY #) Q   FROM APP.TPA_PARTNER_LOG A,APP.TPA_USER_CONTACTS B,APP.TPA_PARTNER_LOG_CODE C";
	private static final String strAddUpdatePartnerLog = "{CALL ttk_util_pkg_ptnr_user_log(?,?,?,?,?,?,?,?,?,?)}";

	private static final int LOG_SEQ_ID = 1;
	private static final int HOSP_SEQ_ID = 2;
	private static final int SYSTEM_GEN_YN = 3;
	private static final int LOG_TYPE_ID = 4;
	private static final int MOD_REASON_TYPE = 5;
	private static final int REFERENCE_DATE = 6;
	private static final int REFERENCE_NO = 7;
	private static final int REMARKS = 8;
	private static final int USER_SEQ_ID  = 9;
	private static final int ROW_PROCESSED = 10;
	private static final int PTNR_SEQ_ID = 2;

	/**
	 * This method returns the ArrayList, which contains the LogVO's which are
	 * populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
     * @return ArrayList of LogVO object's which contains the log details
     * @exception throws TTKException
     */
	
    public ArrayList<Object> getLogList(ArrayList alSearchObjects) throws TTKException
    {
        String strStaticQuery = strHopitalLogList;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        LogVO logVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            String strHospSeqId = TTKCommon.checkNull((String)alSearchObjects.get(0));
            String strUserTypeId = TTKCommon.checkNull((String)alSearchObjects.get(1));
            String strFromDate = TTKCommon.checkNull((String)alSearchObjects.get(2));
            String strEndDate = TTKCommon.checkNull((String)alSearchObjects.get(3));
            //strBuildSql = strBuildSql+" WHERE A.HOSP_SEQ_ID ="+strHospSeqId+" AND A.added_by = B.contact_seq_id AND nvl('"+strUserTypeId+"', '-1') = decode('"+strUserTypeId+"', NULL, '-1', log_type_id)  AND trunc(A.added_date) BETWEEN nvl(to_date('"+strFromDate+"','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND nvl(to_date('"+strEndDate+"','dd/mm/yyyy'), SYSDATE) ";
          //  sbfDynamicQuery.append(" WHERE A.HOSP_SEQ_ID ="+strHospSeqId+" AND A.added_by = B.contact_seq_id AND nvl('"+strUserTypeId+"', '-1') = decode('"+strUserTypeId+"', NULL, '-1', A.log_type_id)  AND trunc(A.added_date) BETWEEN nvl(to_date('"+strFromDate+"','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND nvl(to_date('"+strEndDate+"','dd/mm/yyyy'), SYSDATE) AND A.LOG_TYPE_ID = C.LOG_TYPE_ID");
           sbfDynamicQuery.append(" WHERE A.HOSP_SEQ_ID ="+strHospSeqId+" AND A.added_by = B.contact_seq_id AND COALESCE(envl(''), '-1') = (case when envl('') is null then '-1' else A.log_type_id end)  AND A.added_date :: date BETWEEN COALESCE(to_date( envl('"+strFromDate+"'),'dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND COALESCE(to_date(envl('"+strEndDate+"'),'dd/mm/yyyy'), current_date) AND A.LOG_TYPE_ID = C.LOG_TYPE_ID");
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        //build the Order By Clause
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        //build the row numbers to be fetched
        sbfDynamicQuery.append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		try {
            conn = ResourceManager.getConnection();
            //System.out.println("strStaticQuery.."+strStaticQuery);
            pStmt = conn.prepareStatement(strStaticQuery);
           
            rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
                    logVO = new LogVO();
					if (rs.getTimestamp("ADDED_DATE") != null) {
						logVO.setLogDate(new java.util.Date(rs.getTimestamp(
								"ADDED_DATE").getTime()));
					}// end of if (rs.getTimestamp("ADDED_DATE") != null)
					logVO.setLogDesc(TTKCommon.checkNull(rs
							.getString("REMARKS")));
					if (rs.getString("LOG_SEQ_ID") != null) {
                        logVO.setLogId(new Long(rs.getLong("LOG_SEQ_ID")));
					}// end of if (rs.getString("LOG_SEQ_ID") != null)
					logVO.setLogType(TTKCommon.checkNull(rs
							.getString("LOG_TYPE_ID")));
					logVO.setUserName(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					if (rs.getString("HOSP_SEQ_ID") != null) {
                        logVO.setHospSeqId(new Long(rs.getString("HOSP_SEQ_ID")));
					}// end of if(rs.getString("HOSP_SEQ_ID") != null)
					logVO.setSystemGenYN(TTKCommon.checkNull(rs
							.getString("SYSTEM_GEN_YN")));
					if (TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN"))
							.equalsIgnoreCase("Y")) {
                        logVO.setImageName("SystemIcon");
                        logVO.setImageTitle("System Log");
					}// end of else if(rs.getString("LOG_TYPE").equals("SYS"))

					logVO.setLogTypeDesc(TTKCommon.checkNull(rs
							.getString("LOG_TYPE")));
                    resultList.add(logVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
            return (ArrayList<Object>) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in LogDAOImpl getLogList()",
							sqlExp);
					throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in LogDAOImpl getLogList()",
								sqlExp);
						throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in LogDAOImpl getLogList()",
									sqlExp);
							throw new TTKException(sqlExp, "log");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw exp;
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getLogList(ArrayList alSearchObjects)

   /**
	 * This method adds the log details The method also calls another method on
	 * DAO to insert the log details to the database
	 * 
	 * @param logVO
	 *            LogVO object which contains the log details to be added
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
     * @exception throws TTKException
     */
	public int addLog(LogVO logVO) throws TTKException {
		int iResult = 0;
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAddUpdateLog);
    		if (logVO.getLogId()!= null)
    		{
    			cStmtObject.setLong(LOG_SEQ_ID,logVO.getLogId().longValue());
    		}//end of if (logVO.getLogId()!= null)
    		else
    		{
    			cStmtObject.setLong(LOG_SEQ_ID,0);
    		}//end of else
    		cStmtObject.setLong(HOSP_SEQ_ID,logVO.getHospSeqId().longValue());
    		cStmtObject.setString(SYSTEM_GEN_YN,"N");
    		cStmtObject.setString(LOG_TYPE_ID,"USR");
    		cStmtObject.setString(MOD_REASON_TYPE,null);
    		
    		cStmtObject.setTimestamp(REFERENCE_DATE,new Timestamp(TTKCommon.getDate().getTime()));
    		cStmtObject.setString(REFERENCE_NO,null);
    		cStmtObject.setString(REMARKS,logVO.getLogDesc());
    		cStmtObject.setLong(USER_SEQ_ID,logVO.getUpdatedBy().longValue());
    		//cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
    		cStmtObject.registerOutParameter(LOG_SEQ_ID,Types.BIGINT);
    		cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
    		cStmtObject.execute();
    		iResult = cStmtObject.getInt(ROW_PROCESSED);
    		conn.commit();
     	}//end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
    		throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in LogDAOImpl addLog()",
							sqlExp);
        			throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
        		{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in LogDAOImpl addLog()",
								sqlExp);
        				throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
    	return iResult;
	}// end of addLog(LogVO logVO)

    /**
	 * This method returns the ArrayList, which contains the LogVO's which are
	 * populated from the database
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
     * @return ArrayList of LogVO'S object's which contains Log details
     * @exception throws TTKException
     */
	public ArrayList<Object> getPolicyLogList(ArrayList<?> alSearchCriteria)
			throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
		CallableStatement cStmtObject = null;
        ResultSet rs = null;
        LogVO logVO = null;
		try {
            conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strPolicylogList);

			cStmtObject.setLong(1, (Long) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			if(!"".equals(TTKCommon.checkNull( alSearchCriteria.get(2)))){
			   cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			}
			else {
				cStmtObject.setString(3, null);
			}
			if(!"".equals(TTKCommon.checkNull( alSearchCriteria.get(3)))){
				cStmtObject.setString(4, (String) alSearchCriteria.get(3));
				}
				else {
					cStmtObject.setString(4, null);
				}
		//	cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(7, Types.OTHER);
            cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(7);
			conn.commit();
			if (rs != null) {
				while (rs.next()) {
                    logVO = new LogVO();
					if (rs.getString("POLICY_LOG_SEQ_ID") != null) {
						logVO.setPolicyLogSeqID(new Long(rs
								.getLong("POLICY_LOG_SEQ_ID")));
					}// end of if(rs.getString("POLICY_LOG_SEQ_ID") != null)
					if (rs.getString("POLICY_SEQ_ID") != null) {
						logVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)
					if (rs.getString("MEMBER_SEQ_ID") != null) {
						logVO.setMemberSeqID(new Long(rs
								.getLong("MEMBER_SEQ_ID")));
					}// end of if(rs.getString("MEMBER_SEQ_ID") != null)
					logVO.setUserName(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					if (rs.getString("ENDORSEMENT_SEQ_ID") != null) {
						logVO.setEndorsementSeqID(new Long(rs
								.getLong("ENDORSEMENT_SEQ_ID")));
					}// end of if(rs.getString("ENDORSEMENT_SEQ_ID") != null)
					logVO.setLogDesc(TTKCommon.checkNull(rs
							.getString("REMARKS")));
					if (rs.getString("ADDED_DATE") != null) {
						logVO.setLogDate(new java.util.Date(rs.getTimestamp(
								"ADDED_DATE").getTime()));
					}// end of if (rs.getString("ADDED_DATE") != null)
					if (TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN"))
							.equalsIgnoreCase("Y")) {
                        logVO.setImageName("SystemIcon");
                        logVO.setImageTitle("System Log");
					}// end of
						// if(TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN")).equalsIgnoreCase("Y"))

                    alResultList.add(logVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList<Object>) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in LogDAOImpl getPolicyLogList()",
							sqlExp);
					throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in LogDAOImpl getPolicyLogList()",
								sqlExp);
						throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in LogDAOImpl getPolicyLogList()",
									sqlExp);
							throw new TTKException(sqlExp, "log");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPolicyLogList(ArrayList alSearchCriteria)

    /**
     * This method adds the log details
	 * 
	 * @param logVO
	 *            LogVO object which contains the log details to be added
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
     * @exception throws TTKException
     */
    public int addPolicyLog(LogVO logVO) throws TTKException {
		int iResult = 0;
        Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
            conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddPolicylog);

			if (logVO.getPolicyLogSeqID() != null) {
				cStmtObject.setLong(1, logVO.getPolicyLogSeqID());
			}// end of if (logVO.getPolicyLogSeqID()!= null)
			else {
				cStmtObject.setLong(1, 0);
			}// end of else

			if (logVO.getPolicySeqID() != null) {
				cStmtObject.setLong(2, logVO.getPolicySeqID());
			}// end of if(logVO.getPolicySeqID() != null)
			else {
				cStmtObject.setLong(2, 0);
			}// end of else

			if (logVO.getMemberSeqID() != null) {
				cStmtObject.setLong(3, logVO.getMemberSeqID());
			}// end of if(logVO.getMemberSeqID() != null)
			else {
				cStmtObject.setLong(3, 0);
			}// end of else

			if (logVO.getEndorsementSeqID() != null) {
				cStmtObject.setLong(4, logVO.getEndorsementSeqID());
			}// end of if(logVO.getEndorsementSeqID() != null)
			else {
				cStmtObject.setLong(4, 0);
			}// end of else
			cStmtObject.setString(5, "N");
			cStmtObject.setString(6, logVO.getLogDesc());
			cStmtObject.setLong(7, logVO.getUpdatedBy());
			cStmtObject.registerOutParameter(1, Types.NUMERIC);	
			cStmtObject.registerOutParameter(8, Types.INTEGER);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(8);
            conn.commit();

		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in LogDAOImpl addPolicyLog()",
							sqlExp);
        			throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
        		{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in LogDAOImpl addPolicyLog()",
								sqlExp);
        				throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return iResult;
	}// end of addPolicyLog(LogVO logVO)

    /**
	 * This method returns the ArrayList, which contains the LogVO's which are
	 * populated from the database
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of LogVO'S object's which contains Pre-Authorization
	 *         Log details
     * @exception throws TTKException
     */
	public ArrayList<Object> getPreAuthLogList(ArrayList alSearchCriteria)
			throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
		CallableStatement cStmtObject = null;
    	ResultSet rs = null;
        LogVO logVO = null;
		try {
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strPreauthLogList);

			if (alSearchCriteria.get(0) != null) {
				cStmtObject.setLong(1, (Long) alSearchCriteria.get(0));// Mandatory
			}// end of if(alSearchCriteria.get(0) != null)
			else {
				cStmtObject.setString(1, null);
			}// end of else

			if (alSearchCriteria.get(1) != null) {
				cStmtObject.setLong(2, (Long) alSearchCriteria.get(1));// Mandatory
			}// end of if(alSearchCriteria.get(1) != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(5));
			cStmtObject.setString(7, (String) alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(8, OracleTypes.CURSOR);
            cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(8);
			if (rs != null) {
				while (rs.next()) {
            		logVO = new LogVO();

					if (rs.getString("PAT_LOG_SEQ_ID") != null) {
            			logVO.setLogId(new Long(rs.getLong("PAT_LOG_SEQ_ID")));
					}// end of if(rs.getString("PAT_LOG_SEQ_ID") != null)

					if (rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null) {
						logVO.setSeqID(new Long(rs
								.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}// end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					logVO.setUserName(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					logVO.setLogDesc(TTKCommon.checkNull(rs
							.getString("REMARKS")));

					if (rs.getString("ADDED_DATE") != null) {
						logVO.setLogDate(new java.util.Date(rs.getTimestamp(
								"ADDED_DATE").getTime()));
					}// end of if (rs.getString("ADDED_DATE") != null)

					if (TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN"))
							.equalsIgnoreCase("Y")) {
                        logVO.setImageName("SystemIcon");
                        logVO.setImageTitle("System Log");
					}// end of
						// if(TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN")).equalsIgnoreCase("Y"))

					logVO.setLogTypeDesc(TTKCommon.checkNull(rs
							.getString("LOG_TYPE")));

            		alResultList.add(logVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList<Object>) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in LogDAOImpl getPreAuthLogList()",
							sqlExp);
					throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in LogDAOImpl getPreAuthLogList()",
								sqlExp);
						throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in LogDAOImpl getPreAuthLogList()",
									sqlExp);
							throw new TTKException(sqlExp, "log");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPreAuthLogList(ArrayList alSearchCriteria)

    /**
	 * This method adds the log details The method also calls another method on
	 * DAO to insert the Pre-Authorization Log details to the database
	 * 
	 * @param logVO
	 *            LogVO object which contains the Pre-Authorization Log details
	 *            to be added
	 * @param strIdentifier
	 *            object which contains Identifier -
	 *            Coding/Pre-Authorization/Claims
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
     * @exception throws TTKException
     */
	public int addPreAuthLog(LogVO logVO, String strIdentifier)
			throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		int iResult = 0;
		try {
            conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddPreAuthLog);

			if (logVO.getLogId() != null) {
				cStmtObject.setLong(1, logVO.getLogId());
			}// end of if (logVO.getLogId()!= null)
			else {
				cStmtObject.setLong(1, 0);
			}// end of else

			if (logVO.getClaimSeqID() != null) {
				cStmtObject.setLong(2, logVO.getClaimSeqID());
			}// end of if(logVO.getClaimSeqID() != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			if (logVO.getSeqID() != null) {
				cStmtObject.setLong(3, logVO.getSeqID());
			}// end of if(logVO.getSeqID() != null)
			else {
				cStmtObject.setString(3, null);
			}// end of else

			/*
			 * if(strIdentifier.equalsIgnoreCase("Coding")){
			 * cStmtObject.setString(4,"COL"); }//end of
			 * if(strIdentifier.equalsIgnoreCase("Coding")) else{
			 * cStmtObject.setString(4,"NAR"); }//end of else
			 */
			cStmtObject.setString(4, logVO.getLogType());
			cStmtObject.setString(5, "N");
			cStmtObject.setTimestamp(6, new Timestamp(TTKCommon.getDate()
					.getTime()));
			cStmtObject.setString(7, logVO.getLogDesc());
			cStmtObject.setLong(8, logVO.getUpdatedBy());
			cStmtObject.setString(9, "EXT");
			cStmtObject.registerOutParameter(10, Types.INTEGER);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(10);
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in LogDAOImpl addPreAuthLog()",
							sqlExp);
        			throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
        		{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in LogDAOImpl addPreAuthLog()",
								sqlExp);
        				throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return iResult;
	}// end of addPreAuthLog(LogVO logVO,String strIdentifier)

    /**
	 * This method returns the ArrayList, which contains the LogVO's which are
	 * populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
     * @return ArrayList of LogVO object's which contains the log details
     * @exception throws TTKException
     */
	public ArrayList<Object> getPartnerLogList(ArrayList<?> alSearchObjects)
			throws TTKException {
        String strStaticQuery = strPartnerLogList;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        LogVO logVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        Connection conn = null;
		if (alSearchObjects != null && alSearchObjects.size() > 0) {

			String strPtnrSeqId = TTKCommon.checkNull((String) alSearchObjects
					.get(0));
			String strUserTypeId = TTKCommon.checkNull((String) alSearchObjects
					.get(1));
			String strFromDate = TTKCommon.checkNull((String) alSearchObjects
					.get(2));
			String strToDate = TTKCommon.checkNull((String) alSearchObjects
					.get(3));

			strFromDate = (strFromDate == null || "".equals(strFromDate)) ? null
					: "'" + strFromDate + "'";
			strToDate = (strToDate == null || "".equals(strToDate)) ? null
					: "'" + strToDate + "'";

			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strPtnrSeqId);

			if (TTKCommon.checkNull(strFromDate).length() < 1) {
				strFromDate = null;
			}
			if (TTKCommon.checkNull(strToDate).length() < 1) {
				strToDate = null;
			}
			
			// strBuildSql =
			// strBuildSql+" WHERE A.HOSP_SEQ_ID ="+strHospSeqId+" AND A.added_by = B.contact_seq_id AND nvl('"+strUserTypeId+"', '-1') = decode('"+strUserTypeId+"', NULL, '-1', log_type_id)  AND trunc(A.added_date) BETWEEN nvl(to_date('"+strFromDate+"','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND nvl(to_date('"+strEndDate+"','dd/mm/yyyy'), SYSDATE) ";

			sbfDynamicQuery
					.append(" WHERE A.PTNR_SEQ_ID ="
							+ strPtnrSeqId
							+ " AND A.added_by = B.contact_seq_id AND COALESCE("
							+ "envl('"+strUserTypeId+"'), '-1') = (case when envl('"+strUserTypeId+"') is null then '-1' else A.log_type_id end) AND DATE(A.added_date) BETWEEN COALESCE(to_date("
							+ strFromDate
							+ ",'dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND COALESCE(to_date("
							+ strToDate
							+ ",'dd/mm/yyyy'), current_date) AND A.LOG_TYPE_ID = C.LOG_TYPE_ID");

			/*
			 * sbfDynamicQuery.append(" WHERE A.PTNR_SEQ_ID ="+strPtnrSeqId+
			 * " AND A.added_by = B.contact_seq_id AND COALESCE(envl(''), '-1') = (case when envl('') is null then '-1' else A.log_type_id end)  AND to_date(to_char(A.added_date,'DD/MM/YYYY'),'DD/MM/YYYY')= COALESCE(to_date(envl(''),'dd/mm/yyyy'), current_date) AND A.LOG_TYPE_ID = C.LOG_TYPE_ID"
			 * );
			 */

		}

		// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
		// build the row numbers to be fetched
		sbfDynamicQuery.append(")gg WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		try {
			conn = ResourceManager.getConnection();
	//		System.out.println("Alert Search..." + strStaticQuery);
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
                    logVO = new LogVO();
					if (rs.getTimestamp("ADDED_DATE") != null) {
						logVO.setLogDate(new java.util.Date(rs.getTimestamp(
								"ADDED_DATE").getTime()));
					}// end of if (rs.getTimestamp("ADDED_DATE") != null)
					logVO.setLogDesc(TTKCommon.checkNull(rs
							.getString("REMARKS")));
					if (rs.getString("LOG_SEQ_ID") != null) {
                        logVO.setLogId(new Long(rs.getLong("LOG_SEQ_ID")));
					}// end of if (rs.getString("LOG_SEQ_ID") != null)
					logVO.setLogType(TTKCommon.checkNull(rs
							.getString("LOG_TYPE_ID")));
					logVO.setUserName(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					if (rs.getString("PTNR_SEQ_ID") != null) {
                        logVO.setPtnrSeqId(new Long(rs.getString("PTNR_SEQ_ID")));
					}// end of if(rs.getString("HOSP_SEQ_ID") != null)
					logVO.setSystemGenYN(TTKCommon.checkNull(rs
							.getString("SYSTEM_GEN_YN")));
					if (TTKCommon.checkNull(rs.getString("SYSTEM_GEN_YN"))
							.equalsIgnoreCase("Y")) {
                        logVO.setImageName("SystemIcon");
                        logVO.setImageTitle("System Log");
					}// end of else if(rs.getString("LOG_TYPE").equals("SYS"))

					logVO.setLogTypeDesc(TTKCommon.checkNull(rs
							.getString("LOG_TYPE")));
                    resultList.add(logVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
            return (ArrayList<Object>) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in LogDAOImpl getLogList()",
							sqlExp);
					throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in LogDAOImpl getLogList()",
								sqlExp);
						throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in LogDAOImpl getLogList()",
									sqlExp);
							throw new TTKException(sqlExp, "log");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw exp;
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getLogList(ArrayList alSearchObjects)

	public int addPartnerLog(LogVO logVO) throws TTKException {
		int iResult = 0;
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdatePartnerLog);
			if (logVO.getLogId() != null) {
				cStmtObject.setLong(LOG_SEQ_ID, logVO.getLogId());
			}// end of if (logVO.getLogId()!= null)
			else {
				cStmtObject.setLong(LOG_SEQ_ID, 0);
			}// end of else
    		cStmtObject.setLong(PTNR_SEQ_ID, logVO.getPtnrSeqId());

			cStmtObject.setString(SYSTEM_GEN_YN, "N");

			cStmtObject.setString(LOG_TYPE_ID, "USR");

			cStmtObject.setString(MOD_REASON_TYPE, null);

			cStmtObject.setTimestamp(REFERENCE_DATE, new Timestamp(TTKCommon
					.getDate().getTime()));

			cStmtObject.setString(REFERENCE_NO, null);

			cStmtObject.setString(REMARKS, logVO.getLogDesc());

			cStmtObject.setLong(USER_SEQ_ID, logVO.getUpdatedBy());

			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.INTEGER);
			cStmtObject.registerOutParameter(LOG_SEQ_ID, Types.BIGINT);
    		cStmtObject.execute();
			conn.commit();
    		iResult = cStmtObject.getInt(ROW_PROCESSED);

		}// end of try
		catch (SQLException sqlExp) {
    		throw new TTKException(sqlExp, "log");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
    		throw new TTKException(exp, "log");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in LogDAOImpl addLog()",
							sqlExp);
        			throw new TTKException(sqlExp, "log");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
        		{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in LogDAOImpl addLog()",
								sqlExp);
        				throw new TTKException(sqlExp, "log");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "log");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
    	return iResult;
	}// end of addLog(LogVO logVO)

}// end of LogDAOImpl
