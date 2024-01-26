/**
 * @ (#) CommunicationDAOImpl.java Dec 21, 2006
 * Project       : TTK HealthCare Services
 * File          : CommunicationDAOImpl.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Dec 21, 2006
 *
 * @author       : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CommunicationOptionVO;

public class CommunicationDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(CommunicationDAOImpl.class );
	
	//Enrollment Reports

	private static final String strSendMessageScheduled = "{?=CALL GENERATE_MAIL_PKG_PROC_SEND_MESSAGE_SCHEDULED(?)}";
	private static final String strSendMessageScheduledPreauth = "{CALL GENERATE_MAIL_PKG_proc_send_msg_sched_preauth(?,?)}";
	private static final String strProcEnrNumbers = "{CALL GENERATE_MAIL_PKG.PROC_ENR_NUMBERS(?)}";
	private static final String strSendAuthorization = "{CALL GENERATE_MAIL_PKG_PROC_FORM_MESSAGE(?,?,?,?)}";
	private static final String strProcGetPendingFax = "{CALL GENERATE_MAIL_PKG.PROC_GET_PENDING_FAX(?)}";
	private static final String strProcFormMessage ="{CALL GENERATE_MAIL_PKG.PROC_FORM_MESSAGE(?,?,?,?)}" ;
	
	private static final String strGenerateFormMessage="{CALL GENERATE_MAIL_PKG_PROC_GENERATE_MESSAGE(?,?,?,?)}";
	//procedure for update stmt
	private static final String strMessageStatusChangePr = "{CALL GENERATE_MAIL_PKG_UPDATE_MESSAGE_RCPT_STATUS(?,?,?,?,?)}";
	//Shortfall CR KOC1179
	private static final String strSendShortfallEmailsScheduled="{CALL SCHEDULE_PKG.SEND_SHORTFALL_REQUEST}";
	//private static final String strConnection ="Connection";
	//added for KOC-cigna for preauth schedular
	private static final String strCignaPreauthSendShortfallEmailsScheduled = "{CALL SCHEDULE_PKG.SEND_PAT_SHORTFAL_REQUEST}"; 
	//Added as per KOC BAJAJ ALLIANZ WebService Intergration
	private static final String strUpdateBajajResponse="{CALL APP.DATA_TRANSFER.P_RESPONSE_XML(?,?,?,?)}";
	//Cigna mail-sms for Template
	private static final String strSendCignaAuthorization = "{CALL GENERATE_MAIL_PKG.PROC_FORM_MESSAGE_CIGNA(?,?,?,?)}";
	private static final String strSendMemberXml = "{CALL POLICY_ENROLLMENT_PKG_ACT_MEM_LIST_MAIL_TRIGGER(?,?,?,?)}";
	
	
	private static final String strsendPaymentMailListSchedular ="{CALL GENERATE_MAIL_PKG_MAIL_SCHEDULED_PAYMENT_CMPLTD(?,?)}";
	private static final String strGetProvidersList ="{CALL generate_mail_pkg_hosp_list_trigger(?)}";
	private static final String strSendFailedXmlAlert = "{CALL GENERATE_MAIL_PKG_FAIL_CLAIM_XML(?,?,?,?,?)}";
	                                                          
	/**
	 * This method returns the Arraylist of CommunicationOptionVO's  which contains list of notification records which will take care by Schedular
	 * @return ArrayList of CommunicationOptionVO object which contains notification details
	 * @exception throws TTKException
	 */
	
	public ArrayList getSendMailList(String strMsgType) throws TTKException {
		Connection conn = null;
		CommunicationOptionVO cOptionVO = null;
		ResultSet rs = null;String FINAL_MESSAGE = "";
		CallableStatement cStmtObject = null;
		ArrayList<Object> alMailList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendMessageScheduled);
			cStmtObject.setString(2,strMsgType);
			System.out.println("in =====getSendMailList strMsgType ======="+strMsgType);
			cStmtObject.registerOutParameter(1,Types.OTHER);
			cStmtObject.execute();
			conn.commit();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while(rs.next()){
					cOptionVO = new CommunicationOptionVO();
					cOptionVO.setRcptSeqID(TTKCommon.checkNull(rs.getString("DEST_MSG_RCPT_SEQ_ID")));
					cOptionVO.setSentFrom(TTKCommon.checkNull(rs.getString("SENDER_MAIL_ID")));
					cOptionVO.setMsgTitle(TTKCommon.checkNull(rs.getString("MESSAGE_TITLE")));
					cOptionVO.setMessage(TTKCommon.checkNull(rs.getString("MESSAGE")));
					System.out.println("in =====getSendMailList MESSAGE ======="+rs.getString("MESSAGE"));
					cOptionVO.setEnrollNum(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));
					cOptionVO.setMsgType(TTKCommon.checkNull(rs.getString("MSG_TYPE")));
					cOptionVO.setPrsntYN(TTKCommon.checkNull(rs.getString("PRESENT_YN")));
					cOptionVO.setPrmRcptEmailList(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
					cOptionVO.setRcptFaxNos(TTKCommon.checkNull(rs.getString("PRM_RCPT_FAX_NOS")));
					cOptionVO.setRcptSMS(TTKCommon.checkNull(rs.getString("PRM_RCPT_SMS")));
					cOptionVO.setSenRcptEmailList(TTKCommon.checkNull(rs.getString("SEC_RCPT_EMAIL_LIST")));
					cOptionVO.setFilePathName(TTKCommon.checkNull(rs.getString("FILE_PATH_NAME")));
					//added for Mail-SMS Template for Cigna
					String MESSAGE1 = TTKCommon.checkNull(rs.getString("MESSAGE"));
					//log.info("MESSAGE1--:"+MESSAGE1);
					String MESSAGE2  = TTKCommon.checkNull(rs.getString("CIGNA_MSG"));					
					//log.info("MESSAGE2--:"+MESSAGE2);
					if(rs.getString("MSG_TYPE")=="SMS")
					{
						FINAL_MESSAGE = MESSAGE1;
					}
					else
					{
						FINAL_MESSAGE = MESSAGE1+MESSAGE2;
					}
					 
					//log.info("FINAL_MESSAGE--:"+FINAL_MESSAGE);

					cOptionVO.setMessage(FINAL_MESSAGE);

					//cOptionVO.setMsgID(MSG_ID);
				
					cOptionVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					//added for Mail-SMS Template for Cigna
					cOptionVO.setCignaSmsUrl(TTKCommon.checkNull(rs.getString("CIGNA_SMS_YN")));
							log.info("DEST_MSG_RCPT_SEQ_ID is :"+cOptionVO.getRcptSeqID());
					log.info("getSendMailList MSG_SENT_FROM is 		  :"+cOptionVO.getSentFrom());
					log.info("getSendMailList MESSAGE_TITLE is 		  :"+cOptionVO.getMsgTitle());
					log.info("getSendMailList MESSAGE is 		  	  :"+cOptionVO.getMessage());
					log.info("getSendMailList ENROLLMENT_NUMBER is 	  :"+cOptionVO.getEnrollNum());
					log.info("getSendMailList MSG_TYPE is 		  	  :"+cOptionVO.getMsgType());
					log.info("getSendMailList PRESENT_YN is 		  :"+cOptionVO.getPrsntYN());
					log.info("getSendMailList PRM_RCPT_EMAIL_LIST is  :"+cOptionVO.getPrmRcptEmailList());
					log.info("getSendMailList PRM_RCPT_FAX_NOS is     :"+cOptionVO.getRcptFaxNos());
					log.info("getSendMailList PRM_RCPT_SMS is 		  :"+cOptionVO.getRcptSMS());
					log.info("getSendMailList SEC_RCPT_EMAIL_LIST is  :"+cOptionVO.getSenRcptEmailList());
					log.info("getSendMailList FILE_PATH_NAME is 	  :"+cOptionVO.getFilePathName());

					alMailList.add(cOptionVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alMailList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CommunicationDAOImpl getSendMailList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getSendMailList()",sqlExp);
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
							log.error("Error while closing the Connection in CommunicationDAOImpl getSendMailList()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSendMailList(String strMsgType)
	
	
	
	
	/**
	 * This method returns the Arraylist of CommunicationOptionVO's  which contains list of notification records which will take care by Schedular
	 * @return ArrayList of CommunicationOptionVO object which contains notification details
	 * @exception throws TTKException
	 */
	public ArrayList getSendMailListPreauth(String strMsgType) throws TTKException {
		Connection conn = null;
		CommunicationOptionVO cOptionVO = null;
		ResultSet rs = null;String FINAL_MESSAGE = "";
		CallableStatement cStmtObject = null;
		ArrayList<Object> alMailList = new ArrayList<Object>();
		try{
			log.info("(2.1) : Inside getSendMailListPreauth() of CommunicationDAOImpl strMsgType ->"+strMsgType);
			
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendMessageScheduledPreauth);
			cStmtObject.setString(1,strMsgType);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					log.info("(2.2) :"+strMsgType+" : Inside while ");
					cOptionVO = new CommunicationOptionVO();
					cOptionVO.setRcptSeqID(TTKCommon.checkNull(rs.getString("DEST_MSG_RCPT_SEQ_ID")));
					cOptionVO.setSentFrom(TTKCommon.checkNull(rs.getString("SENDER_MAIL_ID")));
					System.out.println("*** SENDER_MAIL_ID *** ->"+rs.getString("SENDER_MAIL_ID"));
					cOptionVO.setMsgTitle(TTKCommon.checkNull(rs.getString("MESSAGE_TITLE")));
					cOptionVO.setMessage(TTKCommon.checkNull(rs.getString("MESSAGE")));
					System.out.println("*** sms content MESSAGE *** ->"+rs.getString("MESSAGE"));
					cOptionVO.setEnrollNum(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));
					cOptionVO.setMsgType(TTKCommon.checkNull(rs.getString("MSG_TYPE")));
					cOptionVO.setPrsntYN(TTKCommon.checkNull(rs.getString("PRESENT_YN")));
					cOptionVO.setPrmRcptEmailList(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
					cOptionVO.setRcptFaxNos(TTKCommon.checkNull(rs.getString("PRM_RCPT_FAX_NOS")));
					cOptionVO.setRcptSMS(TTKCommon.checkNull(rs.getString("PRM_RCPT_SMS")));
					cOptionVO.setSenRcptEmailList(TTKCommon.checkNull(rs.getString("SEC_RCPT_EMAIL_LIST")));
					cOptionVO.setFilePathName(TTKCommon.checkNull(rs.getString("FILE_PATH_NAME")));
					//added for Mail-SMS Template for Cigna

					String MESSAGE1 = TTKCommon.checkNull(rs.getString("MESSAGE"));
					//log.info("MESSAGE1--:"+MESSAGE1);
					String MESSAGE2  = TTKCommon.checkNull(rs.getString("CIGNA_MSG"));					
					//log.info("MESSAGE2--:"+MESSAGE2);
					if(rs.getString("MSG_TYPE")=="SMS")
					{
						FINAL_MESSAGE = MESSAGE1;
					}
					else
					{
						FINAL_MESSAGE = MESSAGE1+MESSAGE2;
					}
					 
					//log.info("FINAL_MESSAGE--:"+FINAL_MESSAGE);

					cOptionVO.setMessage(FINAL_MESSAGE);

					//cOptionVO.setMsgID(MSG_ID);
				
					cOptionVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					//added for Mail-SMS Template for Cigna
					cOptionVO.setCignaSmsUrl(TTKCommon.checkNull(rs.getString("CIGNA_SMS_YN")));
						log.info("getSendMailListPreauth DEST_MSG_RCPT_SEQ_ID is :"+cOptionVO.getRcptSeqID());
					log.info("getSendMailListPreauth MSG_SENT_FROM is 		  :"+cOptionVO.getSentFrom());
					log.info("MESSAGE_TITLE is 		  :"+cOptionVO.getMsgTitle());
					log.info("MESSAGE is 		  	  :"+cOptionVO.getMessage());
					log.info("ENROLLMENT_NUMBER is 	  :"+cOptionVO.getEnrollNum());
					log.info("MSG_TYPE is 		  	  :"+cOptionVO.getMsgType());
					log.info("PRESENT_YN is 		  :"+cOptionVO.getPrsntYN());
					log.info("getSendMailListPreauth PRM_RCPT_EMAIL_LIST is  :"+cOptionVO.getPrmRcptEmailList());
					log.info("PRM_RCPT_FAX_NOS is     :"+cOptionVO.getRcptFaxNos());
					log.info("getSendMailListPreauth PRM_RCPT_SMS is 		  :"+cOptionVO.getRcptSMS());
					log.info("SEC_RCPT_EMAIL_LIST is  :"+cOptionVO.getSenRcptEmailList());
					log.info("getSendMailListPreauth FILE_PATH_NAME is 	  :"+cOptionVO.getFilePathName());

					alMailList.add(cOptionVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alMailList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CommunicationDAOImpl getSendMailList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getSendMailList()",sqlExp);
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
							log.error("Error while closing the Connection in CommunicationDAOImpl getSendMailList()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSendMailList(String strMsgType)

	
	/**
	 * This method used to change the Message Status
	 * @param strRcptSeqID String Recipient Sequence ID
	 * @param strMsgJobID String Message Job ID
	 * @exception throws TTKException
	 */
	public void messageStatusChangePr(ArrayList alFaxInfo) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strMessageStatusChangePr);
			/*cStmtObject.setString(1,strRcptSeqID);
    		cStmtObject.setString(2,strMailStatus);
    		cStmtObject.setString(3,strMsgJobID);
    		cStmtObject.setString(4,strRemarks);
    		cStmtObject.setString(5,strGeneralTypeID);*/
			cStmtObject.setString(1,(String)alFaxInfo.get(0));
			cStmtObject.setString(2,(String)alFaxInfo.get(1));
			/*if(alFaxInfo.get(1) != null)
			{
				cStmtObject.setString(2,(String)alFaxInfo.get(1));
			}//end of if(alFaxInfo.get(1) != null)
			else
			{
				cStmtObject.setString(2,null);
			}//end of else    		
*/    		cStmtObject.setString(3,(String)alFaxInfo.get(2));
    		cStmtObject.setString(4,(String)alFaxInfo.get(3));
    		cStmtObject.setString(5,(String)alFaxInfo.get(4));
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
        			log.error("Error while closing the Statement in CommunicationDAOImpl messageStatusChangePr()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl messageStatusChangePr()",sqlExp);
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
	}//end of messageStatusChangePr(ArrayList alFaxInfo)
	
	
	/**
     * This method gets Enrollment numbers from Card Details
     * @return String of strCardBatSeqID which contains CardBatSeqID
     * @exception throws TTKException
     */
    public void getEnrollNumbers(String strCardBatSeqID) throws TTKException
    {    	
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strProcEnrNumbers);
			cStmtObject.setString(1,strCardBatSeqID);
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
        			log.error("Error while closing the Statement in CommunicationDAOImpl getEnrollNumbers()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl getEnrollNumbers()",sqlExp);
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
    }//end of getEnrollNumbers(String strCardBatSeqID)
    
    
    /**
     * This method gets ArrayList of Destination message rcpt seq id and message job id
     * @return ArrayList which contains Destination message rcpt seq id and message job id
     * @exception throws TTKException
     */
    public ArrayList getProcGetPendingFax() throws TTKException
    {    	
    	ArrayList<Object> alFaxSendList = new ArrayList<Object>();
    	CommunicationOptionVO communicationOptionVO = null;
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strProcGetPendingFax);
			cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null)
			{
				while(rs.next())
				{
					communicationOptionVO = new CommunicationOptionVO();
					communicationOptionVO.setDestMsgRcptSeqId(rs.getString("DEST_MSG_RCPT_SEQ_ID"));
					communicationOptionVO.setMsgJobID(rs.getString("MESSAGE_JOB_ID"));
					alFaxSendList.add(communicationOptionVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getProcGetPendingFax()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getProcGetPendingFax()",sqlExp);
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
							log.error("Error while closing the Statement in CommunicationDAOImpl getProcGetPendingFax()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alFaxSendList;
    }//end of getProcGetPendingFax()
    
    /**
     * This method inserts the Authorization Information
     * @param lngPatGenDtlSeqID PAT_GEN_DETAIL_SEQ_ID
     * @param strIdentifier Identifier for Preauth/Claim Approved/Rejected
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void sendAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendAuthorization);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setString(2,Long.toString(lngPatGenDtlSeqID));
			cStmtObject.setLong(3,lngUserID);
			cStmtObject.registerOutParameter(4, Types.VARCHAR);
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
        			log.error("Error while closing the Statement in CommunicationDAOImpl sendAuthorization()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl sendAuthorization()",sqlExp);
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
    }//end of sendAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) 
    
    
  //added for Mail-SMS Template for Cigna
    /**
     * This method inserts the Authorization Information
     * @param lngPatGenDtlSeqID PAT_GEN_DETAIL_SEQ_ID
     * @param strIdentifier Identifier for Preauth/Claim Approved/Rejected
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void sendCignaAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendCignaAuthorization);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setLong(2,lngPatGenDtlSeqID);
			cStmtObject.setLong(3,lngUserID);
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
        			log.error("Error while closing the Statement in CommunicationDAOImpl sendCignaAuthorization()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl sendCignaAuthorization()",sqlExp);
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
    }//end of sendCignaAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) 
    
    
    /**
     * This method inserts the Online Information
     * @param strIdentifier Identifier for NEW_ONLINE_ENR_EMPLOYEE
     * @param strPolicyGrpSeqID PolicyGrpSeqID
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void sendOnlineSoftCopy(String strIdentifier,long lngPolicyGrpSeqID,Long lngUserID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strProcFormMessage);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setLong(2,lngPolicyGrpSeqID);
			cStmtObject.setLong(3,lngUserID);
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
        			log.error("Error while closing the Statement in CommunicationDAOImpl sendOnlineSoftCopy()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl sendOnlineSoftCopy()",sqlExp);
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
    }//end of sendOnlineSoftCopy(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) 
    
    /**
     * This method inserts record after generating the report in StatusRptScheduler    
     * @exception throws TTKException
     */
    public void insertRecord(String strIdentifier) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strGenerateFormMessage);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setString(2,"");
			cStmtObject.setLong(3,new Long("0"));
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
        	// Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in CommunicationDAOImpl insertRecord()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl insertRecord()",sqlExp);
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
    }//end of insertRecord(String strIdentifier)
 //Shortfall CR KOC1179
    /**
     * This method inserts record after generating the report in ShortfallRptScheduler    
     * @exception throws TTKException
     */
    public void sendShortfallDetails() throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendShortfallEmailsScheduled);
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
        	// Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in CommunicationDAOImpl sendShortfallDetails()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl sendShortfallDetails()",sqlExp);
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
    }//end of sendShortfallDetails() 
 
    
//added for Cigna preauth email schedular
    
    /**
     * This method inserts record after generating the report in ShortfallRptScheduler    
     * @exception throws TTKException
     */
    public void sendCignaPreauthShorfallDetails() throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strCignaPreauthSendShortfallEmailsScheduled);
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
        	// Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in CommunicationDAOImpl sendCignaPreauthShorfallDetails()",sqlExp);
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
        				log.error("Error while closing the Connection in CommunicationDAOImpl sendCignaPreauthShorfallDetails()",sqlExp);
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
    }//end of sendCignaPreauthShorfallDetails() 
    //ended
    
    
    
  //Added as per KOC BAJAJ ALLIANZ WebService Intergration
    public String saveXmlFromBajaj(String xmlString,String filename,BigDecimal errorCode) throws TTKException {
	
		Connection connection = null;
		CallableStatement callableStatement=null;
		String msg = null;
			try{
         connection = ResourceManager.getMISConnection();
        
        callableStatement = (java.sql.CallableStatement)connection.prepareCall(strUpdateBajajResponse);
         
          XMLType resultXML = null;
            if(xmlString != null)
            {
                resultXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)connection).getUnderlyingConnection()), xmlString);
            }//end of if(xmlString != null)
            callableStatement.setObject(1,filename);
            callableStatement.setObject(2,resultXML);
            callableStatement.setBigDecimal(3,errorCode); 
            callableStatement.registerOutParameter(4,Types.VARCHAR); 
            callableStatement.execute();
             msg =(String) callableStatement.getObject(4);
          
  }catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "communication");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "communication");
	}//end of catch (Exception exp)
	finally
	{
    	// Nested Try Catch to ensure resource closure  
    	try // First try closing the Statement
    	{
    		try
    		{
    			if (callableStatement != null) callableStatement.close();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			log.error("Error while closing the Statement in CommunicationDAOImpl saveXmlFromBajaj()",sqlExp);
    			throw new TTKException(sqlExp, "communication");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(connection != null) connection.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in CommunicationDAOImpl saveXmlFromBajaj()",sqlExp);
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
    		callableStatement = null;
    		connection = null;
    	}//end of finally
	
	}//end of finally
	return msg;
}
    
    public CommunicationOptionVO getMemberXMLDatatoSendMail(String messageID,Long userSeqId,String insCode,String regAuthority) throws TTKException {
		Connection conn = null;
		CommunicationOptionVO cOptionVO = null;
		ResultSet rs = null;String FINAL_MESSAGE = "";
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			
			cStmtObject=conn.prepareCall(strSendMemberXml);
			cStmtObject.setString(1,messageID);
			cStmtObject.setString(2,insCode);
			cStmtObject.setString(3,regAuthority);
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					cOptionVO = new CommunicationOptionVO();
					//cOptionVO.setRcptSeqID(TTKCommon.checkNull(rs.getString("DEST_MSG_RCPT_SEQ_ID")));
					/*cOptionVO.setSentFrom(TTKCommon.checkNull(rs.getString("SENDER_MAIL_ID")));*/
					cOptionVO.setMsgTitle(TTKCommon.checkNull(rs.getString("MESSAGE_TITLE")));
					cOptionVO.setMessage(TTKCommon.checkNull(rs.getString("msg_description")));
				/*	cOptionVO.setEnrollNum(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));*/
					/*cOptionVO.setMsgType(TTKCommon.checkNull(rs.getString("MSG_TYPE")));
					cOptionVO.setPrsntYN(TTKCommon.checkNull(rs.getString("PRESENT_YN")));*/
					cOptionVO.setPrmRcptEmailList(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
					/*cOptionVO.setRcptFaxNos(TTKCommon.checkNull(rs.getString("PRM_RCPT_FAX_NOS")));
					cOptionVO.setRcptSMS(TTKCommon.checkNull(rs.getString("PRM_RCPT_SMS")));*/
					cOptionVO.setSenRcptEmailList(TTKCommon.checkNull(rs.getString("SEC_RCPT_EMAIL_LIST")));
					/*cOptionVO.setFilePathName(TTKCommon.checkNull(rs.getString("FILE_PATH_NAME")));*/
					//added for Mail-SMS Template for Cigna

					/*String MESSAGE1 = TTKCommon.checkNull(rs.getString("MESSAGE"));*/
					//log.info("MESSAGE1--:"+MESSAGE1);
				/*	String MESSAGE2  = TTKCommon.checkNull(rs.getString("CIGNA_MSG"));*/					
					//log.info("MESSAGE2--:"+MESSAGE2);
					/*if(rs.getString("MSG_TYPE")=="SMS")
					{
						FINAL_MESSAGE = MESSAGE1;
					}
					else
					{
						FINAL_MESSAGE = MESSAGE1+MESSAGE2;
					}*/
					 
					//log.info("FINAL_MESSAGE--:"+FINAL_MESSAGE);

					//cOptionVO.setMessage(FINAL_MESSAGE);

					//cOptionVO.setMsgID(MSG_ID);
				
					cOptionVO.setMsgID(TTKCommon.checkNull(messageID));
					//added for Mail-SMS Template for Cigna
				/*	cOptionVO.setCignaSmsUrl(TTKCommon.checkNull(rs.getString("CIGNA_SMS_YN")));*/
						log.info("getSendMailListPreauth DEST_MSG_RCPT_SEQ_ID is :"+cOptionVO.getRcptSeqID());
					log.info("getSendMailListPreauth MSG_SENT_FROM is 		  :"+cOptionVO.getSentFrom());
					log.info("MESSAGE_TITLE is 		  :"+cOptionVO.getMsgTitle());
					log.info("MESSAGE is 		  	  :"+cOptionVO.getMessage());
					log.info("ENROLLMENT_NUMBER is 	  :"+cOptionVO.getEnrollNum());
					log.info("MSG_TYPE is 		  	  :"+cOptionVO.getMsgType());
					log.info("PRESENT_YN is 		  :"+cOptionVO.getPrsntYN());
					log.info("getSendMailListPreauth PRM_RCPT_EMAIL_LIST is  :"+cOptionVO.getPrmRcptEmailList());
					log.info("PRM_RCPT_FAX_NOS is     :"+cOptionVO.getRcptFaxNos());
					log.info("getSendMailListPreauth PRM_RCPT_SMS is 		  :"+cOptionVO.getRcptSMS());
					log.info("SEC_RCPT_EMAIL_LIST is  :"+cOptionVO.getSenRcptEmailList());
					log.info("getSendMailListPreauth FILE_PATH_NAME is 	  :"+cOptionVO.getFilePathName());

					
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return cOptionVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CommunicationDAOImpl getSendMailList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getSendMailList()",sqlExp);
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
							log.error("Error while closing the Connection in CommunicationDAOImpl getSendMailList()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSendMailList(String strMsgType)


    //Added for payment mail for auto trigger
	public ArrayList getsendPaymentMailList(String strIdentifier) throws TTKException{

		Connection conn = null;
		CommunicationOptionVO cOptionVO = null;
		ResultSet rs = null;String FINAL_MESSAGE = "";
		CallableStatement cStmtObject = null;
		ArrayList<Object> alMailList = new ArrayList<Object>();
		try {
			
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strsendPaymentMailListSchedular);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					
					cOptionVO = new CommunicationOptionVO();
					
					cOptionVO.setRcptSeqID(TTKCommon.checkNull(rs.getString("DEST_MSG_RCPT_SEQ_ID")));
					cOptionVO.setSentFrom(TTKCommon.checkNull(rs.getString("SENDER_MAIL_ID")));
					cOptionVO.setMsgTitle(TTKCommon.checkNull(rs.getString("MESSAGE_TITLE")));
					cOptionVO.setMessage(TTKCommon.checkNull(rs.getString("MESSAGE")));
					cOptionVO.setEnrollNum(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));
					cOptionVO.setMsgType(TTKCommon.checkNull(rs.getString("MSG_TYPE")));
					cOptionVO.setPrsntYN(TTKCommon.checkNull(rs.getString("PRESENT_YN")));
					cOptionVO.setPrmRcptEmailList(TTKCommon.checkNull(rs.getString("PRM_RCPT_EMAIL_LIST")));
					cOptionVO.setRcptFaxNos(TTKCommon.checkNull(rs.getString("PRM_RCPT_FAX_NOS")));
					cOptionVO.setRcptSMS(TTKCommon.checkNull(rs.getString("PRM_RCPT_SMS")));
					cOptionVO.setSenRcptEmailList(TTKCommon.checkNull(rs.getString("SEC_RCPT_EMAIL_LIST")));
					cOptionVO.setFilePathName(TTKCommon.checkNull(rs.getString("FILE_PATH_NAME")));
					
					cOptionVO.setMsgID(TTKCommon.checkNull(rs.getString("MSG_ID")));
					//added for Mail-SMS Template for Cigna
					cOptionVO.setCignaSmsUrl(TTKCommon.checkNull(rs.getString("CIGNA_SMS_YN")));
							log.info("DEST_MSG_RCPT_SEQ_ID is :"+cOptionVO.getRcptSeqID());
					log.info("getSendMailList MSG_SENT_FROM is 		   ====>:"+cOptionVO.getSentFrom());
					log.info("getSendMailList MESSAGE_TITLE is 		   ====>:"+cOptionVO.getMsgTitle());
					log.info("getSendMailList MESSAGE is 		  	   ====>:"+cOptionVO.getMessage());
					log.info("getSendMailList ENROLLMENT_NUMBER is 	   ====>:"+cOptionVO.getEnrollNum());
					log.info("getSendMailList MSG_TYPE is 		  	   ====>:"+cOptionVO.getMsgType());
					log.info("getSendMailList PRESENT_YN is 		   ====>:"+cOptionVO.getPrsntYN());
					log.info("getSendMailList PRM_RCPT_EMAIL_LIST is   ====>:"+cOptionVO.getPrmRcptEmailList());
					log.info("getSendMailList PRM_RCPT_FAX_NOS is      ====>:"+cOptionVO.getRcptFaxNos());
					log.info("getSendMailList PRM_RCPT_SMS is 		   ====>:"+cOptionVO.getRcptSMS());
					log.info("getSendMailList SEC_RCPT_EMAIL_LIST is   ====>:"+cOptionVO.getSenRcptEmailList());
					log.info("getSendMailList FILE_PATH_NAME is 	   ====>:"+cOptionVO.getFilePathName());

					alMailList.add(cOptionVO);
					
				}
			}
			
			return alMailList;
		}
		/*catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
*/		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
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
					log.error("Error while closing the Resultset in CommunicationDAOImpl getsendPaymentMailList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getsendPaymentMailList()",sqlExp);
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
							log.error("Error while closing the Connection in CommunicationDAOImpl getsendPaymentMailList()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		
	}


	public ArrayList<CommunicationOptionVO> getProviderList() throws TTKException {
		
		log.info("inside CommunicationDAOImpl getProviderList()");
		Connection conn = null;
		CommunicationOptionVO cOptionVO = null;
		ResultSet rs = null;
		CallableStatement cStmtObject = null;
		ArrayList<CommunicationOptionVO> alProviderList = new ArrayList<CommunicationOptionVO>();
		
		try {
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strGetProvidersList);
			cStmtObject.registerOutParameter(1,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while(rs.next()){
					cOptionVO = new CommunicationOptionVO();
					cOptionVO.setProvideSeqId(rs.getBigDecimal("hosp_seq_id").longValue());
					alProviderList.add(cOptionVO);
				}
				}
			
		} catch (Exception exp) {
			throw new TTKException(exp, "communication");
		}
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
					log.error("Error while closing the Resultset in CommunicationDAOImpl getProviderList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CommunicationDAOImpl getProviderList()",sqlExp);
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
							log.error("Error while closing the Connection in CommunicationDAOImpl getProviderList()",sqlExp);
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
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alProviderList;
	}
	
	 public void sendFailXmlAlert(String msgId,String issueType,String fileName,String fileID,String licenceNO) throws TTKException {
	    	Connection conn = null;
			CallableStatement cStmtObject = null;
			try{
				conn = ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strSendFailedXmlAlert);
				cStmtObject.setString(1,msgId);
				cStmtObject.setString(2,issueType);
				cStmtObject.setString(3,fileName);
				cStmtObject.setString(4,fileID);
				cStmtObject.setString(5,licenceNO);
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
	        	// Nested Try Catch to ensure resource closure  
	        	try // First try closing the Statement
	        	{
	        		try
	        		{
	        			if (cStmtObject != null) cStmtObject.close();
	        		}//end of try
	        		catch (SQLException sqlExp)
	        		{
	        			log.error("Error while closing the Statement in CommunicationDAOImpl sendFailXmlAlert()",sqlExp);
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
	        				log.error("Error while closing the Connection in CommunicationDAOImpl sendFailXmlAlert()",sqlExp);
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
	    }// end of sendFailXmlAlert()

  //Added as per KOC BAJAJ ALLIANZ WebService Intergration    
}//end of CommunicationDAOImpl