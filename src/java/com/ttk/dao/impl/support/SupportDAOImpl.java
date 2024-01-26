/**
 * @ (#) SupportDAOImpl.java Mar 14, 2008
 * Project 	     : TTK HealthCare Services
 * File          : SupportDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 14, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.support;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.maintenance.GeneralAnnouncementVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineAssistanceVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;

public class SupportDAOImpl implements BaseDAO, Serializable{
	
	private static Logger log = Logger.getLogger(SupportDAOImpl.class );
	
	private static final String strIntimationList = "{CALL PRE_AUTH_PKG.SELECT_INTIMATION_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveIntimation = "{CALL PRE_AUTH_PKG.UPDATE_INTIMATION(?,?,?,?,?)}";
	private static final String strSupportQueryList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_SUPPORT_QUERY_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveSupportQueryInfo = "{CALL ONLINE_ENROLMENT_PKG.SAVE_SUPPORT_QUERY_DETAILS(?,?,?,?,?,?,?,?)}";
	private static final String strSmartHealthXmlList = "{CALL CLM_XML_LOAD_PKG.SELECT_SN_XML_FILES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetSmartHealthXml = "{CALL CLM_XML_LOAD_PKG.UPDATE_SH_TRANSACTIONS(?,?,?,?)}";
	private static final String strGetSmartHealthBulkXml = "{CALL CLM_XML_LOAD_PKG.BULK_UPDATE_SH_TRANSACTIONS(?,?,?,?)}";
	private static final String strGeneralAnnounceDetail = "SELECT SEQ_ID,DESCRIPTION FROM APP.TPA_UPDATES WHERE ACTIVE_YN='Y' AND ALLOWED_CAT='HOS'  ORDER BY SORT_ID";

	//private static final String strConnection ="Connection";
	
	/**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineIntimationVO onlineIntimationVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strIntimationList);

			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){
					onlineIntimationVO = new OnlineIntimationVO();
					
					if(rs.getString("PAT_INTIMATION_SEQ_ID") != null){
						onlineIntimationVO.setIntimationSeqID(new Long(rs.getLong("PAT_INTIMATION_SEQ_ID")));
					}//end of if(rs.getString("PAT_INTIMATION_SEQ_ID") != null)
					
					onlineIntimationVO.setIntimationNbr(TTKCommon.checkNull(rs.getString("PAT_INTIMATION_ID")));
					onlineIntimationVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					onlineIntimationVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					onlineIntimationVO.setMemName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					onlineIntimationVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
					
					if(rs.getString("TPA_RESPONDED_DATE") != null){
						onlineIntimationVO.setTTKRespondedDate(new Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime()));
					}//end of if(rs.getString("TPA_RESPONDED_DATE") != null)
					
					alResultList.add(onlineIntimationVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in SupportDAOImpl getIntimationList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SupportDAOImpl getIntimationList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SupportDAOImpl getIntimationList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getIntimationList(ArrayList alSearchCriteria)

    /**
	 * This method saves the Intimation information
	 * @param intimationVO the object which contains the Intimation Details which has to be  saved
	 * @return long the value contains Intimation Seq ID
	 * @exception throws TTKException
	 */
	public long saveIntimationDetail(OnlineIntimationVO onlineIntimationVO) throws TTKException {
		long lngIntimationSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveIntimation);
        	cStmtObject.setLong(1,onlineIntimationVO.getIntimationSeqID());
        	cStmtObject.setString(2,onlineIntimationVO.getTTKRemarks());
        	cStmtObject.setString(3,onlineIntimationVO.getTTKNarrative());
        	cStmtObject.setLong(4,onlineIntimationVO.getUpdatedBy());
        	cStmtObject.registerOutParameter(5,Types.INTEGER);
        	cStmtObject.registerOutParameter(1,Types.BIGINT);
        	cStmtObject.execute();
        	lngIntimationSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in SupportDAOImpl saveIntimationDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SupportDAOImpl saveIntimationDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally		
		return lngIntimationSeqID;
	}//end of saveIntimationDetail(OnlineIntimationVO onlineIntimationVO)
	
	/**
     * This method returns the Arraylist of OnlineAssistanceVO's which contains Online Query details
     * @param alSearchCriteria It contains the Search Criteria
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSupportQueryList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineAssistanceVO onlineAssistanceVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSupportQueryList);
			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.registerOutParameter(13,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(13);
			
			if(rs != null){
				while(rs.next()){
					onlineAssistanceVO = new OnlineAssistanceVO();
					
					if(rs.getString("QUERY_HEADER_SEQ_ID") != null){
						onlineAssistanceVO.setQueryHdrSeqId(Long.valueOf(rs.getLong("QUERY_HEADER_SEQ_ID")));
					}//end of if(rs.getString("QUERY_HEADER_SEQ_ID") != null)
					
					onlineAssistanceVO.setRequestID(TTKCommon.checkNull(rs.getString("REQUEST_ID")));
					onlineAssistanceVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					onlineAssistanceVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					
					if(rs.getString("CLARIFIED_DATE") != null){
						onlineAssistanceVO.setRespondedDate(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()));
					}//end of if(rs.getString("CLARIFIED_DATE") != null)
					
					onlineAssistanceVO.setStatus(TTKCommon.checkNull(rs.getString("STATUS")));
					onlineAssistanceVO.setEmpname(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					onlineAssistanceVO.setFeedBackResponse(TTKCommon.checkNull(rs.getString("FEEDBACK_RESPONSE")));
					
					/*log.info("QUERY_HEADER_SEQ_ID is   : "+onlineAssistanceVO.getQueryHdrSeqId());
					log.info("REQUEST_ID is 		   : "+onlineAssistanceVO.getRequestID());
					log.info("POLICY_NUMBER is         : "+onlineAssistanceVO.getPolicyNbr());
					log.info("TPA_ENROLLMENT_NUMBER is : "+onlineAssistanceVO.getEnrollmentNbr());
					log.info("CLARIFIED_DATE is 	   : "+onlineAssistanceVO.getTTKRespondedDate());
					log.info("STATUS is 			   : "+onlineAssistanceVO.getStatus());
					log.info("INSURED_NAME is          : "+onlineAssistanceVO.getEmpname());*/
					alResultList.add(onlineAssistanceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in SupportDAOImpl getSupportQueryList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SupportDAOImpl getSupportQueryList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SupportDAOImpl getSupportQueryList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)alResultList;
    }//end of getSupportQueryList(ArrayList<Object> alSearchCriteria)
    
    /**
     * This method saves the Support Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSupportQueryInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int intResult = 0;
    	OnlineQueryVO onlineQueryVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveSupportQueryInfo);
    		onlineQueryVO = onlineAssistanceDetailVO.getOnlineQueryVO();
    		if(onlineQueryVO.getQueryDtlSeqID() == null){
    			cStmtObject.setString(1,null);
    		}//end of if(onlineQueryVO.getQueryDtlSeqID() == null)
    		else{
    			cStmtObject.setLong(1,onlineQueryVO.getQueryDtlSeqID());
    		}//end of else
    		cStmtObject.setLong(2,onlineAssistanceDetailVO.getQueryHdrSeqId());
    		cStmtObject.setString(3,onlineQueryVO.getTTKRemarks());
    		//cStmtObject.setString(4,onlineQueryVO.getTtkSubmittedYN());
    		if(onlineQueryVO.getStatus().equals("TTK Responded"))
    		{
    			cStmtObject.setString(4,"Y");
    		}//end of if(onlineQueryVO.getStatus().equals("TTK Responded"))
    		else
    		{
    			cStmtObject.setString(4,onlineQueryVO.getTtkSubmittedYN());
    		}//end of else
    		cStmtObject.setString(5,onlineQueryVO.getTtkfeedBackRemarks());
    		cStmtObject.setString(6,onlineQueryVO.getTtkfeedBackSubmittedYN());
    		cStmtObject.setLong(7,onlineAssistanceDetailVO.getUpdatedBy());
    		cStmtObject.registerOutParameter(8,Types.INTEGER);
    		cStmtObject.execute();
    		intResult = cStmtObject.getInt(8);
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in SupportDAOImpl saveSupportQueryInfo()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SupportDAOImpl saveSupportQueryInfo()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return intResult;
    }//end of saveSupportQueryInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)
	
//	public static void main(String a[]) throws Exception{
		//SupportDAOImpl supportDAO = new SupportDAOImpl();
		
		/*ArrayList<Object> alIntimationList = new ArrayList<Object>();
		alIntimationList.add("");
		alIntimationList.add("");
		alIntimationList.add("");
		alIntimationList.add("");
		alIntimationList.add(new Long(56503));
		alIntimationList.add("mem_name");
		alIntimationList.add("ASC");
		alIntimationList.add("1");
		alIntimationList.add("20");
		supportDAO.getIntimationList(alIntimationList);*/
		
		/*OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
		onlineIntimationVO.setIntimationSeqID(new Long(82));
		onlineIntimationVO.setTTKRemarks("Please go to First Hospital");
		onlineIntimationVO.setUpdatedBy(new Long(56503));
		supportDAO.saveIntimationDetail(onlineIntimationVO);*/
//	}//end of main

    
    /**
     * This method returns the Arraylist of DhpoWebServiceVO  which contains xml  Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DhpoWebServiceVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getSmartHealthXmlList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	DhpoWebServiceVO webServiceVO=null;
    	SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSmartHealthXmlList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));
			cStmtObject.registerOutParameter(14,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(14);
			if(rs != null){
				while(rs.next()){
					webServiceVO = new DhpoWebServiceVO();
					
					if(rs.getString("DOWNLOAD_SEQ_ID") != null){
						webServiceVO.setFileSeqID(new Long(rs.getLong("DOWNLOAD_SEQ_ID")));
					}//end of if(rs.getString("PAT_INTIMATION_SEQ_ID") != null)
					
					webServiceVO.setFileID(TTKCommon.checkNull(rs.getString("File_ID")));
					webServiceVO.setFileName(TTKCommon.checkNull(rs.getString("File_NAME")));
					if(rs.getDate("DOWN_LOAD_DATE")!=null){
						webServiceVO.setXmlRecievedData(dateFormat.format(new java.util.Date(rs.getDate("DOWN_LOAD_DATE").getTime())));
					}
					if(rs.getDate("USER_DOWNLOADED_DATE")!=null){
						webServiceVO.setUserDownloadDate(dateFormat.format(new java.util.Date(rs.getDate("USER_DOWNLOADED_DATE").getTime())));
					}else webServiceVO.setUserDownloadDate("");
					
					webServiceVO.setUserDownloadStatus(TTKCommon.checkNull(rs.getString("USER_DOWNLOADED_YN")));
					
					if(rs.getString("DHPO_TX_DATE")!=null){
						
					webServiceVO.setDhpoTxDate(rs.getString("DHPO_TX_DATE"));
					}else webServiceVO.setDhpoTxDate("");
					
				    webServiceVO.setDhpoClaimRecCount(TTKCommon.checkNull(rs.getString("DHPO_TOTAL_REC_CNT")));
					webServiceVO.setClaimRecCount(TTKCommon.checkNull(rs.getString("CLM_REC_CNT")));
					webServiceVO.setProviderID(TTKCommon.checkNull(rs.getString("PROVIDER_ID")));
					alResultList.add(webServiceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in SupportDAOImpl getSmartHealthXmlList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SupportDAOImpl getSmartHealthXmlList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SupportDAOImpl getSmartHealthXmlList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getSmartHealthXmlList(ArrayList alSearchCriteria)
    public DhpoWebServiceVO downLoadSmartHealthXmlFiles(Long seqID,String downloadMode,String authMode) throws TTKException{

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	DhpoWebServiceVO webServiceVO=null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetSmartHealthXml);
			cStmtObject.setLong(1,seqID);
			cStmtObject.setString(2,downloadMode);
			cStmtObject.setString(3,authMode);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				if(rs.next()){
					webServiceVO = new DhpoWebServiceVO();					
					
					webServiceVO.setFileID(TTKCommon.checkNull(rs.getString("File_ID")));
					webServiceVO.setFileName(TTKCommon.checkNull(rs.getString("File_NAME")));										
					webServiceVO.setXmlFileReader(rs.getCharacterStream("SH_FILE"));					
				}//end of while(rs.next())
			}//end of if(rs != null)
			
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return webServiceVO;    
    }//downLoadSmartHealthXmlFiles
    
    public ArrayList<DhpoWebServiceVO> bulkDownLoadSmartHealthXmlFiles(StringBuilder seqID,String downloadMode,String authMode) throws TTKException{

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	DhpoWebServiceVO webServiceVO=null;
    	ArrayList<DhpoWebServiceVO> allData=new ArrayList<>();
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetSmartHealthBulkXml);
		 if(seqID!=null)cStmtObject.setString(1,"|"+seqID.toString());
		 else cStmtObject.setString(1, null);
			cStmtObject.setString(2,downloadMode);
			cStmtObject.setString(3,authMode);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					webServiceVO = new DhpoWebServiceVO();					
					
					webServiceVO.setFileID(TTKCommon.checkNull(rs.getString("File_ID")));
					webServiceVO.setFileName(TTKCommon.checkNull(rs.getString("File_NAME")));										
					webServiceVO.setXmlFileReader(rs.getCharacterStream("SH_FILE"));	
					allData.add(webServiceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "support");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SupportDAOImpl downLoadSmartHealthXmlFiles()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return allData;    
    }//bulkdownLoadSmartHealthXmlFiles
    
    
}//end of SupportDAOImpl
