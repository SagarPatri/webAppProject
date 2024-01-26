/**
 * @ (#) StatusDAOImpl.java Sep 29, 2005
 * Project      :
 * File         : StatusDAOImpl.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 29, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
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
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.empanelment.StatusVO;

public class StatusDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(StatusDAOImpl.class);
	
	private static final String strAddUpdateEmpanelStatus = "{CALL intx.HOSPITAL_EMPANEL_PKG_PR_HOSPITAL_STATUS_SAVE (?,?,?,?,?,?,?,?,?,?)}"; 
	private static final String strEmpanelStatusDetail = "SELECT ESTATUS.EMPANEL_SEQ_ID,ESTATUS.HOSP_SEQ_ID,HGRADE.APPROVED_GRADE_TYPE_ID,EMPANEL_STATUS_TYPE_ID,EMPANEL_RSON_TYPE_ID,FROM_DATE,TO_DATE,NOTIFIED_TO_TPA_ACC,DATE_OF_NOTIFICATION,ESTATUS.REMARKS,HINFO.TPA_REGIST_DATE FROM app.TPA_HOSP_EMPANEL_STATUS ESTATUS left join app.TPA_HOSP_GRADING HGRADE on (ESTATUS.HOSP_SEQ_ID =HGRADE.HOSP_SEQ_ID) join app.TPA_HOSP_INFO HINFO on (ESTATUS.HOSP_SEQ_ID = HINFO.HOSP_SEQ_ID) WHERE ESTATUS.ACTIVE_YN = 'Y' AND ESTATUS.HOSP_SEQ_ID = ?";
	private static final String strStatusTypeIdList = "SELECT EMPANEL_STATUS_TYPE_ID FROM APP.TPA_HOSP_EMPANEL_STATUS_CODE";
	private static final String strReasonInfoList = "SELECT EMPANEL_RSON_TYPE_ID, RSON_DESCRIPTION FROM APP.TPA_HOSP_EMPANEL_RSON_CODE WHERE EMPANEL_STATUS_TYPE_ID = ? ";
	private static final String strPartnerEmpanelStatusDetail = "SELECT ESTATUS.EMPANEL_SEQ_ID , ESTATUS.PTNR_SEQ_ID , EMPANEL_STATUS_TYPE_ID , EMPANEL_RSON_TYPE_ID , FROM_DATE , TO_DATE , DATE_OF_NOTIFICATION , ESTATUS.REMARKS FROM APP.TPA_PARTNER_EMPANEL_STATUS ESTATUS , APP.TPA_PARTNER_INFO HINFO WHERE ESTATUS.PTNR_SEQ_ID = HINFO.PTNR_SEQ_ID  AND ESTATUS.ACTIVE_YN = 'Y' AND ESTATUS.PTNR_SEQ_ID = ? ";
	private static final String strAddUpdatePartnerEmpanelStatus = "{CALL PARTNER_EMPANELL_PKG_PARTNER_STATUS_SAVE (?,?,?,?,?,?,?,?,?,?)}";

	private static final int EMPANEL_SEQ_ID = 1;
    private static final int EMPANEL_STATUS_TYPE_ID = 2;
    private static final int EMPANEL_RSON_TYPE_ID = 3;
    private static final int FROM_DATE = 4;
    private static final int TO_DATE = 5;
    private static final int NOTIFIED_TO_TPA_ACC = 6;
    private static final int DATE_OF_NOTIFICATION = 7;
    private static final int REMARKS = 8;
    private static final int USER_SEQ_ID = 9;
    private static final int ROW_PROCESSED = 10;

  /**
	 * This method updates the hospital status The method also calls other methods on DAO to update the hospital status to the database
	 * @param statusVO StatusVO object which contains the status details to be updated
	 * @return int value, greater than zero indicates sucessfull execution of   the task
	 * @exception throws TTKException
	 */
	public int updateStatus(StatusVO statusVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAddUpdateEmpanelStatus);

			if (statusVO.getEmpanelSeqId()!=null)
			{
				cStmtObject.setLong(EMPANEL_SEQ_ID,statusVO.getEmpanelSeqId());
			}//end of if (statusVO.getEmpanelSeqId()!=null)
			else
			{
				cStmtObject.setLong(EMPANEL_SEQ_ID,0);
			}//end of else
		  	cStmtObject.setString(EMPANEL_STATUS_TYPE_ID,statusVO.getEmplStatusTypeId());
			cStmtObject.setString(EMPANEL_RSON_TYPE_ID,statusVO.getSubStatusCode());
			if(statusVO.getFromDate() != null)
			{
                cStmtObject.setTimestamp(FROM_DATE,new Timestamp(statusVO.getFromDate().getTime()));
			}//end of if(statusVO.getFromDate() != null)
			else
			{
				cStmtObject.setTimestamp(FROM_DATE,null);
			}//end of else
			if(statusVO.getToDate() != null)
			{
                cStmtObject.setTimestamp(TO_DATE,new Timestamp(statusVO.getToDate().getTime()));
			}//end of if(statusVO.getToDate() != null)
			else
			{
				cStmtObject.setTimestamp(TO_DATE,null);
			}//end of else
			cStmtObject.setString(NOTIFIED_TO_TPA_ACC,statusVO.getNotifiedToTpaAcc());
			if(statusVO.getDateOfNotification() != null)
			{
                cStmtObject.setTimestamp(DATE_OF_NOTIFICATION,new Timestamp(statusVO.getDateOfNotification().getTime()));
			}//end of if(statusVO.getDateOfNotification() != null)
			else
			{
				cStmtObject.setTimestamp(DATE_OF_NOTIFICATION,null);
			}//end of else
			cStmtObject.setString(REMARKS,statusVO.getRemarks());
			cStmtObject.setLong(USER_SEQ_ID,statusVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(ROW_PROCESSED);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "status");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "status");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in StatusDAOImpl updateStatus()",sqlExp);
        			throw new TTKException(sqlExp, "status");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in StatusDAOImpl updateStatus()",sqlExp);
        				throw new TTKException(sqlExp, "status");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "status");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// end of updateStatus(StatusVO statusVO)

	/**
     * This method returns the StatusVO, which contains all the hospital status details
     * @param lHospitalSeqId Long object which contains the Hospital sequence Id
     * @return StatusVO object which contains all the hospital status details
     * @exception throws TTKException
     */
    public StatusVO getStatus(Long lHospitalSeqId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	HashMap hmReasonInfo = new HashMap();
    	try
    	{
    		StatusVO statusVO = new StatusVO();
    		conn = ResourceManager.getConnection();
    		pStmt = conn.prepareStatement(strEmpanelStatusDetail);
    		pStmt.setLong(1,lHospitalSeqId);
    		rs = pStmt.executeQuery();
    		if (rs!=null)
    		{
    			while (rs.next()) {
    				if (rs.getString("EMPANEL_SEQ_ID")!=null)
    				{
    					statusVO.setEmpanelSeqId(new Long (rs.getLong("EMPANEL_SEQ_ID")));
    				}//end of if (rs.getString("EMPANEL_SEQ_ID")!=null)
    				if(rs.getString("HOSP_SEQ_ID") != null)
    				{
    					statusVO.setlHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
    				}//end of if(rs.getString("HOSP_SEQ_ID") != null)
    				statusVO.setGradeTypeId(TTKCommon.checkNull(rs.getString("APPROVED_GRADE_TYPE_ID")));
    				statusVO.setEmplStatusTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
	    			if(rs.getTimestamp("FROM_DATE") != null)
	    			{
	    				statusVO.setFromDate(new java.util.Date(rs.getTimestamp("FROM_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("FROM_DATE") != null)
	    			if(rs.getTimestamp("TO_DATE") != null)
	    			{
	    				statusVO.setToDate(new java.util.Date(rs.getTimestamp("TO_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("TO_DATE") != null)
	    			statusVO.setSubStatusCode(TTKCommon.checkNull(rs.getString("EMPANEL_RSON_TYPE_ID")));
	    			if(rs.getTimestamp("DATE_OF_NOTIFICATION") != null)
	    			{
	    				statusVO.setDateOfNotification(new java.util.Date(rs.getTimestamp("DATE_OF_NOTIFICATION").getTime()));
	    			}//end of if(rs.getTimestamp("DATE_OF_NOTIFICATION") != null)
	    			statusVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
	    			statusVO.setNotifiedToTpaAcc(TTKCommon.checkNull(rs.getString("NOTIFIED_TO_TPA_ACC")));
	    			if(rs.getTimestamp("TPA_REGIST_DATE") != null)
	    			{
	    				statusVO.setTpaRegdDate(new java.util.Date(rs.getTimestamp("TPA_REGIST_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("TPA_REGIST_DATE") != null)
	    			hmReasonInfo = (HashMap)getReasonInfo();
	    			statusVO.setReasonInfo(hmReasonInfo);
    			}//end of while
    		}//end of if (rs!=null)
    		return statusVO;
    	}//end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "status");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "status");
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
					log.error("Error while closing the Resultset in StatusDAOImpl getStatus()",sqlExp);
					throw new TTKException(sqlExp, "status");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in StatusDAOImpl getStatus()",sqlExp);
						throw new TTKException(sqlExp, "status");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in StatusDAOImpl getStatus()",sqlExp);
							throw new TTKException(sqlExp, "status");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "status");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getStatus(Long lHospitalSeqId)

    /**
	 * This method returns the HashMap,which contains the ReasonId,Reason Description
	 *@return HashMap containing Reason Type Id and Reason Description
	 * @exception throws TTKException
	 */
    public HashMap getReasonInfo() throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	HashMap<Object,Object> hmReasonInfo = null;
    	ArrayList<Object> alReasonList = null;
    	String strStatusTypeId = "";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strStatusTypeIdList);
    		pStmt2=conn.prepareStatement(strReasonInfoList);
    		rs1= pStmt1.executeQuery();
    		if (rs1!=null)
    		{
    			while(rs1.next()){
    				if(hmReasonInfo == null){
    					hmReasonInfo=new HashMap<Object,Object>();
    				}//end of if(hmReasonInfo == null)
    				strStatusTypeId = TTKCommon.checkNull(rs1.getString("EMPANEL_STATUS_TYPE_ID"));
    				pStmt2.setString(1,strStatusTypeId);
    				rs2=pStmt2.executeQuery();
    				alReasonList=null;
    				if (rs2!=null)
    				{
    					while(rs2.next()){
    						cacheObject = new CacheObject();
    						if(alReasonList == null){
    							alReasonList = new ArrayList<Object>();
    						}//end of if(alReasonList == null)
    						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("EMPANEL_RSON_TYPE_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("RSON_DESCRIPTION")));
    						alReasonList.add(cacheObject);
    					}// end of inner while(rs2.next())
    				}//end of  inner if (rs2!=null)
    				hmReasonInfo.put(strStatusTypeId,alReasonList);
    				if(rs2 != null){
    					rs2.close();
    				}//end of if(rs2 != null)
    				rs2 = null;
    			}//end of outer while(rs1.next())
    		}//end of outer  if (rs1!=null)
    		if(pStmt2 != null){
				pStmt2.close();
			}//end of if(pStmt2 != null)
    		pStmt2 = null;
    		return hmReasonInfo;
    	}// end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "status");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "status");
    	}//end of catch (Exception exp)
    	finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in StatusDAOImpl getReasonInfo()",sqlExp);
					throw new TTKException(sqlExp, "status");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in StatusDAOImpl getReasonInfo()",sqlExp);
						throw new TTKException(sqlExp, "status");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in StatusDAOImpl getReasonInfo()",sqlExp);
							throw new TTKException(sqlExp, "status");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in StatusDAOImpl getReasonInfo()",sqlExp);
								throw new TTKException(sqlExp, "status");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Second Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Second Connection in StatusDAOImpl getReasonInfo()",sqlExp);
									throw new TTKException(sqlExp, "status");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "status");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
    } // end of getReasonInfo()
    
    /**
     * This method returns the StatusVO, which contains all the hospital status details
     * @param lHospitalSeqId Long object which contains the Hospital sequence Id
     * @return StatusVO object which contains all the hospital status details
     * @exception throws TTKException
     */
    public StatusVO getPartnerStatus(Long lPartnerSeqId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	HashMap hmReasonInfo = new HashMap();
    	try
    	{
    		StatusVO statusVO = new StatusVO();
    		conn = ResourceManager.getConnection();
    		pStmt = conn.prepareStatement(strPartnerEmpanelStatusDetail);
    		pStmt.setLong(1,lPartnerSeqId);
    		rs = pStmt.executeQuery();
    		if (rs!=null)
    		{
    			while (rs.next()) {
    				if (rs.getString("EMPANEL_SEQ_ID")!=null)
    				{
    					statusVO.setEmpanelSeqId(new Long (rs.getLong("EMPANEL_SEQ_ID")));
    				}//end of if (rs.getString("EMPANEL_SEQ_ID")!=null)
    				if(rs.getString("PTNR_SEQ_ID") != null)
    				{
    					statusVO.setlPtnrSeqId(new Long(rs.getLong("PTNR_SEQ_ID")));
    				}//end of if(rs.getString("HOSP_SEQ_ID") != null)
    			//	statusVO.setGradeTypeId(TTKCommon.checkNull(rs.getString("APPROVED_GRADE_TYPE_ID")));
    				statusVO.setEmplStatusTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
	    			if(rs.getTimestamp("FROM_DATE") != null)
	    			{
	    				statusVO.setFromDate(new java.util.Date(rs.getTimestamp("FROM_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("FROM_DATE") != null)
	    			if(rs.getTimestamp("TO_DATE") != null)
	    			{
	    				statusVO.setToDate(new java.util.Date(rs.getTimestamp("TO_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("TO_DATE") != null)
	    			statusVO.setSubStatusCode(TTKCommon.checkNull(rs.getString("EMPANEL_RSON_TYPE_ID")));
	    			if(rs.getTimestamp("DATE_OF_NOTIFICATION") != null)
	    			{
	    				statusVO.setDateOfNotification(new java.util.Date(rs.getTimestamp("DATE_OF_NOTIFICATION").getTime()));
	    			}//end of if(rs.getTimestamp("DATE_OF_NOTIFICATION") != null)
	    			statusVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
	    		//	statusVO.setNotifiedToTpaAcc(TTKCommon.checkNull(rs.getString("NOTIFIED_TO_TPA_ACC")));
	    		/*	if(rs.getTimestamp("TPA_REGIST_DATE") != null)
	    			{
	    				statusVO.setTpaRegdDate(new java.util.Date(rs.getTimestamp("TPA_REGIST_DATE").getTime()));
	    			}//end of if(rs.getTimestamp("TPA_REGIST_DATE") != null)
*/	    			hmReasonInfo = (HashMap)getReasonInfo();
	    			statusVO.setReasonInfo(hmReasonInfo);
    			}//end of while
    		}//end of if (rs!=null)
    		return statusVO;
    	}//end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "status");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "status");
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
					log.error("Error while closing the Resultset in StatusDAOImpl getStatus()",sqlExp);
					throw new TTKException(sqlExp, "status");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in StatusDAOImpl getStatus()",sqlExp);
						throw new TTKException(sqlExp, "status");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in StatusDAOImpl getStatus()",sqlExp);
							throw new TTKException(sqlExp, "status");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "status");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getStatus(Long lHospitalSeqId)

    

	 /**
		 * This method updates the hospital status The method also calls other methods on DAO to update the hospital status to the database
		 * @param statusVO StatusVO object which contains the status details to be updated
		 * @return int value, greater than zero indicates sucessfull execution of   the task
		 * @exception throws TTKException
		 */
		public int updatePartnerStatus(StatusVO statusVO) throws TTKException {
			int iResult = 0;
			Connection conn = null;
			CallableStatement cStmtObject=null;
			try {
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAddUpdatePartnerEmpanelStatus);

				if (statusVO.getEmpanelSeqId()!=null)
				{
					cStmtObject.setLong(EMPANEL_SEQ_ID,statusVO.getEmpanelSeqId());
				}//end of if (statusVO.getEmpanelSeqId()!=null)
				else
				{
					cStmtObject.setLong(EMPANEL_SEQ_ID,0);
				}//end of else
		//		System.out.println("EMPANEL_SEQ_ID..."+statusVO.getEmpanelSeqId());
			  	cStmtObject.setString(EMPANEL_STATUS_TYPE_ID,statusVO.getEmplStatusTypeId());
		//	  	System.out.println("EMPANEL_STATUS_TYPE_ID.."+statusVO.getEmplStatusTypeId());
				cStmtObject.setString(EMPANEL_RSON_TYPE_ID,statusVO.getSubStatusCode());
				if(statusVO.getFromDate() != null)
				{
	                cStmtObject.setTimestamp(FROM_DATE,new Timestamp(statusVO.getFromDate().getTime()));
				}//end of if(statusVO.getFromDate() != null)
				else
				{
					cStmtObject.setTimestamp(FROM_DATE,null);

				}//end of else
				if(statusVO.getToDate() != null)
				{
	                cStmtObject.setTimestamp(TO_DATE,new Timestamp(statusVO.getToDate().getTime()));

				}//end of if(statusVO.getToDate() != null)
				else
				{
					cStmtObject.setTimestamp(TO_DATE,null);
				}//end of else
				cStmtObject.setString(NOTIFIED_TO_TPA_ACC,statusVO.getNotifiedToTpaAcc());
				if(statusVO.getDateOfNotification() != null)
				{
	                cStmtObject.setTimestamp(DATE_OF_NOTIFICATION,new Timestamp(statusVO.getDateOfNotification().getTime()));
				}//end of if(statusVO.getDateOfNotification() != null)
				else
				{
					cStmtObject.setTimestamp(DATE_OF_NOTIFICATION,null);
				}//end of else
				cStmtObject.setString(REMARKS,statusVO.getRemarks());
				cStmtObject.setLong(USER_SEQ_ID,statusVO.getUpdatedBy());
				cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
				iResult = cStmtObject.getInt(ROW_PROCESSED);
                conn.commit();
				
			}// end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "status");
			}// end of catch (SQLException sqlExp)
			catch (Exception exp) {
				throw new TTKException(exp, "status");
			}// end of catch (Exception exp)
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
	        			log.error("Error while closing the Statement in StatusDAOImpl updateStatus()",sqlExp);
	        			throw new TTKException(sqlExp, "status");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in StatusDAOImpl updateStatus()",sqlExp);
	        				throw new TTKException(sqlExp, "status");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "status");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
			return iResult;
		}// end of updateStatus(StatusVO statusVO)
	
 }//end of StatusDAOImpl
