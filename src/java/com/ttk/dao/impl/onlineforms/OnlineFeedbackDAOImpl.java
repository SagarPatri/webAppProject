
/**
 * @ (#) OnlineFeedbackDAOImpl.java Apr 24, 2012
 * Project 	     : TTK HealthCare Services
 * File          : OnlineFeedbackDAOImpl.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Apr 24, 2012
 *
 * @author       : Manohar
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.onlineforms;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.onlineforms.BajajAlliannzFormVO;
import com.ttk.dto.onlineforms.ClaimFormVO;
import com.ttk.dto.onlineforms.FeedbackCashlessVO;
import com.ttk.dto.onlineforms.FeedbackMReimbursementVO;
import java.sql.Connection;
import java.sql.PreparedStatement;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

public class OnlineFeedbackDAOImpl implements BaseDAO, Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(OnlineFeedbackDAOImpl.class);

	private static final String strGetQuestionsList = "SELECT A.QUESTION_SEQ_ID,A.QUESTION,B.ANSWER_SEQ_ID,B.ANSWER FROM APP.TPA_FEEDBACK_FORM_QUES A JOIN   APP.TPA_FEEDBACK_FORM_ANS B ON(A.QUESTION_SEQ_ID=B.QUESTION_SEQ_ID) WHERE A.GROUP_ID=NVL((SELECT DISTINCT C.GROUP_ID FROM  APP.TPA_FEEDBACK_FORM_QUES C WHERE GROUP_ID=?),'GENERAL') AND A.CLAIM_TYPE=?";
	private static final String saveFeedbackDetails = "{CALL ONLINE_ENROLMENT_PKG.SAVE_CUSTOMER_FEEDBACK(?,?,?,?,?,?,?,?,?)}";
	
	private static final String strGetClaimList="{CALL CLAIMS_APPROVAL_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//aadded asper Bajaj Allainz// denial process added one parameter
	private static final String strGetPreAuthList="{CALL CLAIMS_APPROVAL_PKG.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//aadded asper Bajaj Allainz //denial process added one parameter


	//"Ravi.claims_approval_pkg.select_claims_list"	 	 bajaj
	private static final String strGetPreAuthXmlData="{CALL CLAIMS_APPROVAL_PKG.SELECT_PREAUTH_WEB(?,?,?,?)}";//select_preauth_web 
	private static final String strGetClaimsXmlData="{CALL CLAIMS_APPROVAL_PKG.SELECT_CLAIMS_WEB(?,?,?,?)}";//select_claims_web 

	private static final String strClaimInsApproveSave="{CALL CLAIMS_APPROVAL_PKG.CLAIM_INS_APPR_SAVE(?,?,?,?,?)}";//claim_ins_appr_save 1274A
	private static final String strPreAuthApproveSave="{CALL CLAIMS_APPROVAL_PKG.PAT_INS_APPR_SAVE(?,?,?,?,?)}";//pat_ins_appr_save  1274A

	private static String strSendMailIntimation="{CALL CLAIMS_APPROVAL_PKG.SEND_MAIL_INTIMATION(?,?,?,?,?)}";//send_mail_intimation

	
    /**
	 * This method returns the ArrayList Which contains the Feedback List 
	 * @return ArrayList which contains the Feedback List
	 * @throws TTKException
	 */
	public ArrayList getQuestionCashlessList(String groupid,String claimType) throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String strStaticQuery = strGetQuestionsList;
		FeedbackCashlessVO feedbackVO = null;
		FeedbackCashlessVO feedbackVO1 = null;
		Long lngQuestionSeqId = null;
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			pStmt.setString(1,groupid);
			pStmt.setString(2,claimType);
			rs = pStmt.executeQuery();
			ArrayList<Object> alResultEventList = new ArrayList<Object>();
			ArrayList<Object> alResultList = new ArrayList<Object>();
			if(rs != null){
				while (rs.next()) {
					if (lngQuestionSeqId == null || lngQuestionSeqId != rs.getLong("QUESTION_SEQ_ID"))
					{
						if (lngQuestionSeqId != null ){
							feedbackVO.setFeedBack(alResultEventList);						
							alResultList.add(feedbackVO);						
						}// End of if (lngWorkflowSeqId != null )
						alResultEventList = new ArrayList<Object>();
						feedbackVO = new FeedbackCashlessVO();	
						lngQuestionSeqId = rs.getLong("QUESTION_SEQ_ID");
						feedbackVO.setQuestionSeqID(rs.getString("QUESTION_SEQ_ID") != null ? new Long(rs.getLong("QUESTION_SEQ_ID")):null);
						feedbackVO.setQustname(TTKCommon.checkNull(rs.getString("QUESTION")));					
					}//end of if (lngWorkflowSeqId == null || lngWorkflowSeqId != rs.getLong("WORKFLOW_SEQ_ID"))
					feedbackVO1 = new FeedbackCashlessVO();
					feedbackVO1.setAnswerSeqID(rs.getString("ANSWER_SEQ_ID") !=  null ? new Long(rs.getLong("ANSWER_SEQ_ID")):null);
					feedbackVO1.setAnswerName(new String(rs.getString("ANSWER")));
					alResultEventList.add(feedbackVO1);
				}//end of while
				feedbackVO.setFeedBack(alResultEventList);
				alResultList.add(feedbackVO);
			}//end of if(rs != null)
			return alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "feedbackforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "feedbackforms");
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
					log.error("Error while closing the Resultset in OnlineFeedbackDAOImpl getQuestionCashlessList()",sqlExp);
					throw new TTKException(sqlExp, "feedbackforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineFeedbackDAOImpl getQuestionCashlessList()",sqlExp);
						throw new TTKException(sqlExp, "feedbackforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineFeedbackDAOImpl getQuestionCashlessList()",sqlExp);
							throw new TTKException(sqlExp, "feedbackforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "feedbackforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getQuestionCashlessList(String groupid,String claimType)
	
	/**
	 * This method returns the ArrayList Which contains the Workflow List 
	 * @return ArrayList which contains the Workflow List
	 * @throws TTKException
	 */
	public ArrayList getQuestionMReimbursementList(String groupid,String claimType) throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String strStaticQuery = strGetQuestionsList;
		FeedbackMReimbursementVO feedbackVO = null;
		FeedbackMReimbursementVO feedbackVO1 = null;
		
		Long lngQuestionSeqId = null;
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			pStmt.setString(1,groupid);
			pStmt.setString(2,claimType);
			rs = pStmt.executeQuery();
			ArrayList<Object> alResultEventList = new ArrayList<Object>();
			ArrayList<Object> alResultList = new ArrayList<Object>();
			if(rs != null){
				while (rs.next()) {
					if (lngQuestionSeqId == null || lngQuestionSeqId != rs.getLong("QUESTION_SEQ_ID"))
					{			
						if (lngQuestionSeqId != null ){
							feedbackVO.setFeedBack(alResultEventList);						
							alResultList.add(feedbackVO);
						}// End of if (lngWorkflowSeqId != null )
						alResultEventList = new ArrayList<Object>();
						feedbackVO = new FeedbackMReimbursementVO();	
						lngQuestionSeqId = rs.getLong("QUESTION_SEQ_ID");
						feedbackVO.setQuestionSeqID(rs.getString("QUESTION_SEQ_ID") != null ? new Long(rs.getLong("QUESTION_SEQ_ID")):null);
						feedbackVO.setQuestionName(TTKCommon.checkNull(rs.getString("QUESTION")));					
					}//end of if (lngWorkflowSeqId == null || lngWorkflowSeqId != rs.getLong("WORKFLOW_SEQ_ID"))
					feedbackVO1 = new FeedbackMReimbursementVO();
					feedbackVO1.setAnswerSeqID(rs.getString("ANSWER_SEQ_ID") !=  null ? new Long(rs.getLong("ANSWER_SEQ_ID")):null);
					feedbackVO1.setAnswerName(new String(rs.getString("ANSWER")));				
					alResultEventList.add(feedbackVO1);
				}//end of while
			
				feedbackVO.setFeedBack(alResultEventList);
				alResultList.add(feedbackVO);
			
			}//end of if(rs != null)
			return alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "feedbackforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "feedbackforms");
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
					log.error("Error while closing the Resultset in OnlineFeedbackDAOImpl getQuestionMReimbursementList()",sqlExp);
					throw new TTKException(sqlExp, "feedbackforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineFeedbackDAOImpl getQuestionMReimbursementList()",sqlExp);
						throw new TTKException(sqlExp, "feedbackforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineFeedbackDAOImpl getQuestionMReimbursementList()",sqlExp);
							throw new TTKException(sqlExp, "feedbackforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "feedbackforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getQuestionMReimbursementList(String groupid,String claimType)
	
    /**
     * This method saves the Member information
     * @param fbdetails arraylist object which contains the Feedback Details which has to be  saved
     * @return long the value which returns other then zero for successful execution of the task
     * @exception throws TTKException
     */
    public long saveCashlessFeedbackDetails(ArrayList fbdetails) throws TTKException
    {
    	long lngFeedbackId = 0;
    	Connection conn = null;
		OracleCallableStatement oCstmt = null;
		
        try{
        	conn = ResourceManager.getConnection();
        	   oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(saveFeedbackDetails);
            if(fbdetails.get(0)!=null)
            	oCstmt.setLong(1,(Long)fbdetails.get(0));
            else
            	oCstmt.setString(1,null);
        
            if(fbdetails.get(1)!=null)
            	oCstmt.setLong(2,(Long) fbdetails.get(1));
            else
            	oCstmt.setString(2,null);
   
            if(fbdetails.get(2)!=null)
            	oCstmt.setLong(3,(Long) fbdetails.get(2));	
            else
            	oCstmt.setString(3,null);
   
            if(fbdetails.get(3)!=null)
            	oCstmt.setString(4,(String) fbdetails.get(3));
            else
            	oCstmt.setString(4,null);
            
            if(fbdetails.get(4)!=null)
            	oCstmt.setString(5, (String) fbdetails.get(4));
            else
            	oCstmt.setString(5,null);
 
            if(fbdetails.get(5)!=null)
            	oCstmt.setString(6,(String) fbdetails.get(5));	
            else
            	oCstmt.setString(6,null);

            if(fbdetails.get(6)!=null)
            	oCstmt.setTimestamp(7,new Timestamp((Long)(fbdetails.get(6))));
            else
            	oCstmt.setTimestamp(7,null);

            if(fbdetails.get(7)!=null)
            	oCstmt.setLong(8,(Long) fbdetails.get(7));
            else
            	oCstmt.setString(8,null);
            
            if(fbdetails.get(8)!=null)
            	oCstmt.setInt(9,(Integer) fbdetails.get(8));
            else
            	oCstmt.setString(9,null);
            
            oCstmt.registerOutParameter(2,Types.BIGINT);
            oCstmt.execute();
			lngFeedbackId = oCstmt.getLong(2);
            
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "feedbackforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "feedbackforms");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (oCstmt != null) oCstmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in OnlineFeedbackDAOImpl saveMember()",sqlExp);
        			throw new TTKException(sqlExp, "feedbackforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineFeedbackDAOImpl saveMember()",sqlExp);
        				throw new TTKException(sqlExp, "feedbackforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "feedbackforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		oCstmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngFeedbackId;
   }//end of saveCashlessFeedbackDetails(ArrayList fbdetails)
    
    /**
     * This method saves the Member information
     * @param fbdetails arraylist object which contains the Feedback Details which has to be  saved
     * @return long the value which returns other then zero for successful execution of the task
     * @exception throws TTKException
     */
    public long saveMReimbursementFeedbackDetails(ArrayList fbdetails) throws TTKException
    {
    	long lngMRFeedbackId = 0;
    	Connection conn = null;
		OracleCallableStatement oCstmt = null;
		
        try{
        	conn = ResourceManager.getConnection();
        	   oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(saveFeedbackDetails);
            if(fbdetails.get(0)!=null)
            	oCstmt.setLong(1,(Long)fbdetails.get(0));
            else
            	oCstmt.setString(1,null);
        
            if(fbdetails.get(1)!=null)
            	oCstmt.setLong(2,(Long) fbdetails.get(1));
            else
            	oCstmt.setString(2,null);
   
            if(fbdetails.get(2)!=null)
            	oCstmt.setLong(3,(Long) fbdetails.get(2));	
            else
            	oCstmt.setString(3,null);
   
            if(fbdetails.get(3)!=null)
            	oCstmt.setString(4,(String) fbdetails.get(3));
            else
            	oCstmt.setString(4,null);
            
            if(fbdetails.get(4)!=null)
            	oCstmt.setString(5, (String) fbdetails.get(4));
            else
            	oCstmt.setString(5,null);
 
            if(fbdetails.get(5)!=null)
            	oCstmt.setString(6,(String) fbdetails.get(5));	
            else
            	oCstmt.setString(6,null);

            if(fbdetails.get(6)!=null)
            	oCstmt.setTimestamp(7,new Timestamp((Long)(fbdetails.get(6))));
            else
            	oCstmt.setTimestamp(7,null);

            if(fbdetails.get(7)!=null)
            	oCstmt.setLong(8,(Long) fbdetails.get(7));
            else
            	oCstmt.setString(8,null);
            
            if(fbdetails.get(8)!=null)
            	oCstmt.setInt(9,(Integer) fbdetails.get(8));
            else
            	oCstmt.setString(9,null);
            
            oCstmt.registerOutParameter(2,Types.BIGINT);
            oCstmt.execute();
            lngMRFeedbackId = oCstmt.getLong(2);
            
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "feedbackforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "feedbackforms");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (oCstmt != null) oCstmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in OnlineFeedbackDAOImpl saveMember()",sqlExp);
        			throw new TTKException(sqlExp, "feedbackforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineFeedbackDAOImpl saveMember()",sqlExp);
        				throw new TTKException(sqlExp, "feedbackforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "feedbackforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		oCstmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngMRFeedbackId;
   }//end of saveMReimbursementFeedbackDetails(ArrayList fbdetails)
   	//added as per Bajaj Allinaz Chane request
 /**
	 * This method returns the Arraylist of BajajAllianzVO's  which contains the details of Claim/Pre-Auth details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BajajAllianzVO object which contains the details of Claim
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria,String strSwitchType) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;		
		CallableStatement cStmtObject=null;
		String strSuppressLink[]={"8"};
		String strApprovedYN="";
		String strQuery="";
		BajajAlliannzFormVO bajajAlliannzFormVO= null;
		ResultSet rs = null;		
		try{
			conn = ResourceManager.getConnection();
			if(strSwitchType.equalsIgnoreCase("CLM"))
				strQuery=strGetClaimList;
			else if (strSwitchType.equalsIgnoreCase("PRE"))
				strQuery=strGetPreAuthList;
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strQuery);		
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,((((String)alSearchCriteria.get(7)).equals("null")) ||(alSearchCriteria.get(7)==null) ) ? null:(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));
			cStmtObject.setString(14,(String)alSearchCriteria.get(13));
			cStmtObject.setString(15,(String)alSearchCriteria.get(14));
			cStmtObject.setString(16,(String)alSearchCriteria.get(15));//denial process
			cStmtObject.registerOutParameter(17,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);
			if(rs != null){
				while (rs.next()) {
				bajajAlliannzFormVO = new BajajAlliannzFormVO();

					if(strSwitchType.equalsIgnoreCase("CLM"))
					{
    					 if(rs.getString("CLAIM_SEQ_ID") != null){
								 bajajAlliannzFormVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
						 }//end of if(rs.getString("CLAIM_SEQ_ID") != null)
						 bajajAlliannzFormVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
						 if(rs.getString("DATE_OF_ADMISSION") != null){
							 bajajAlliannzFormVO.setClaimAdmnDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
						 }//end of if(rs.getString("DATE_OF_ADMISSION") != null)

						 if(rs.getString("RECOMMEND_DATE") != null){
							 bajajAlliannzFormVO.setClaimRecommendedDate(new Date(rs.getTimestamp("RECOMMEND_DATE").getTime()));
						 }//end of if(rs.getString("DATE_OF_ADMISSION") != null)
						 if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
							 bajajAlliannzFormVO.setEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
						 }//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)
						if(rs.getString("REQUESTED_AMOUNT") != null){
							 bajajAlliannzFormVO.setRequestedAmount(rs.getBigDecimal("REQUESTED_AMOUNT"));
						 }//end of if(rs.getString("REQUESTED_AMOUNT") != null)

						 if(rs.getString("CLAIM_STATUS") != null){
							 bajajAlliannzFormVO.setPreAuthClaimStatus(rs.getString("CLAIM_STATUS"));
						 }//end of if(rs.getString("CLAIM_STATUS") != null)

						if(rs.getString("ALLOW_YN") != null){
						bajajAlliannzFormVO.setAllowedYN(rs.getString("ALLOW_YN"));
						}//end of if(rs.getString("ALLOW_YN") != null)
	                            
					strApprovedYN = TTKCommon.checkNull(rs.getString("ALLOW_YN"));
					bajajAlliannzFormVO.setSwitchType(strSwitchType);
					if(strApprovedYN.equals("N")){
						bajajAlliannzFormVO.setSuppressLink(strSuppressLink);
					}//end of if(strApprovedYN.equals("N"))
					bajajAlliannzFormVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));	                    
					bajajAlliannzFormVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					bajajAlliannzFormVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));	                    
					if(rs.getString("TOTAL_APP_AMOUNT") != null){
						bajajAlliannzFormVO.setTotalApprovedAmount(rs.getBigDecimal("TOTAL_APP_AMOUNT"));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
					}//strSwitchType.equalsIgnoreCase("CLM")

					else if (strSwitchType.equalsIgnoreCase("PRE"))
					{
						bajajAlliannzFormVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
						if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
							bajajAlliannzFormVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
						if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
							bajajAlliannzFormVO.setHospDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
						}//end of if(rs.getString("DATE_OF_ADMISSION") != null)
						if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
							bajajAlliannzFormVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)
						if(rs.getString("PAT_REQUESTED_AMOUNT") != null){
							bajajAlliannzFormVO.setRequestedAmount(rs.getBigDecimal("PAT_REQUESTED_AMOUNT"));
						}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)
						if(rs.getString("DECISION_DATE") != null){
							 bajajAlliannzFormVO.setClaimRecommendedDate(new Date(rs.getTimestamp("DECISION_DATE").getTime()));
						 }//end of if(rs.getString("DATE_OF_ADMISSION") != null)
						 
						if(rs.getString("PAT_STATUS") != null){
							bajajAlliannzFormVO.setPreAuthClaimStatus(rs.getString("PAT_STATUS"));
						}//end of if(rs.getString("PAT_STATUS") != null)
						
						if(rs.getString("ALLOW_YN") != null){
						bajajAlliannzFormVO.setAllowedYN(rs.getString("ALLOW_YN"));
						}//end of if(rs.getString("ALLOW_YN") != null)
					strApprovedYN = TTKCommon.checkNull(rs.getString("ALLOW_YN"));
					bajajAlliannzFormVO.setSwitchType(strSwitchType);
					if(strApprovedYN.equals("N")){
						bajajAlliannzFormVO.setSuppressLink(strSuppressLink);
					}//end of if(strApprovedYN.equals("N"))

					bajajAlliannzFormVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));	                    
					bajajAlliannzFormVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					bajajAlliannzFormVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));	                    
					if(rs.getString("TOTAL_APP_AMOUNT") != null){
						bajajAlliannzFormVO.setTotalApprovedAmount(rs.getBigDecimal("TOTAL_APP_AMOUNT"));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

					}//	else if (strSwitchType.equalsIgnoreCase("PRE"))
                  alResultList.add(bajajAlliannzFormVO);
				}//end of while(rs.next())
			}//end of if(rs != null)

			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "feedback");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "feedback");
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
					log.error("Error while closing the Resultset in OnlineFeedbackDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "feedback");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineFeedbackDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "feedback");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineFeedbackDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "feedback");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)
	/**
	 * This method returns the Claim/PreAuth xml Data of BajajAllianzVO's a  which contains the details of Claim/Pre-Auth details
	 * @param  lngSeqID object which contains the search criteria
	 * @param  strSwitchType object which contains the search criteria
	 * @exception throws TTKException
	 */
		public ClaimFormVO getClaimXmlData(Long lngSeqID,String strSwitchType) throws TTKException{
		// TODO Auto-generated method stub
		Document doc = null;
		XMLType xml = null;
		Connection conn = null;
		OracleCallableStatement stmt = null;
		OracleResultSet rs = null;
		ClaimFormVO claimFormVO = null;
		String inrApprovalStatus="";
		String insurerRemarks="";
		
		String procedureCall="";
		try{
			if(strSwitchType.equalsIgnoreCase("CLM"))
				procedureCall=strGetClaimsXmlData;
			else if(strSwitchType.equalsIgnoreCase("PRE"))
				procedureCall=strGetPreAuthXmlData;
			conn = ResourceManager.getConnection();
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(procedureCall);
			stmt.setString(1,lngSeqID.toString());
			stmt.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.execute();
			claimFormVO = new ClaimFormVO();
			xml = XMLType.createXML(stmt.getOPAQUE(2));
			String xmlValue=xml.getStringVal();
		    DOMReader domReader = new DOMReader();
			doc = xml != null ? domReader.read(xml.getDOM()):null;
		
			claimFormVO.setClaimPreauthDocument(doc);
			inrApprovalStatus=(stmt.getString(3)!=null)?(String)stmt.getString(3):"";
			insurerRemarks=(stmt.getString(4)!=null)?(String)stmt.getString(4):"";
			
			log.info("insurerRemarks in dao:::::::::::::"+insurerRemarks);
			claimFormVO.setSeqID(lngSeqID);
			if(!inrApprovalStatus.equalsIgnoreCase(""))
			{
				if(!inrApprovalStatus.equalsIgnoreCase("INP"))
				{
					claimFormVO.setInsApprovalStatus(inrApprovalStatus);
				}
				else{
					claimFormVO.setInsApprovalStatus("");
				}
			}
			if(!insurerRemarks.equalsIgnoreCase(""))
			{
				
				  claimFormVO.setInsurerRemarks(insurerRemarks);
			}
				else{
					claimFormVO.setInsurerRemarks("");
				
			}
			claimFormVO.setInpStatus(inrApprovalStatus);
			
			claimFormVO.setSwitchType(strSwitchType);
			return claimFormVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "");
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
					log.error("Error while closing the Resultset in  ()",sqlExp);
					throw new TTKException(sqlExp, "");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in  ()",sqlExp);
						throw new TTKException(sqlExp, "");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in  ()",sqlExp);
							throw new TTKException(sqlExp, "");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimXmlData(long lngSeqID,String strFlag)
/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strConcatenatedSeqID 
	 * @param  strApproveRejectStatus 
	  * @param  strRemarks 
	 * @param  strSwitchType 
	 * @exception throws TTKException
	 */
	public int saveInsurerApproveRejectStatus(String strConcatenatedSeqID,String strApproveRejectStatus, String strRemarks, String strSwitchType,Long lngUserID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		String strProcedureCall="";
		try{
			if(strSwitchType.equalsIgnoreCase("CLM"))
				strProcedureCall=strClaimInsApproveSave;
			else if(strSwitchType.equalsIgnoreCase("PRE"))
				strProcedureCall=strPreAuthApproveSave;
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProcedureCall);

			cStmtObject.setString(1, strConcatenatedSeqID);
			cStmtObject.setString(2, strApproveRejectStatus);
			cStmtObject.setString(3, strRemarks);
			cStmtObject.setLong(4, lngUserID);//1274A
			cStmtObject.registerOutParameter(5,Types.INTEGER);
			cStmtObject.execute();
			iResult=cStmtObject.getInt(5);
	
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "feedbackforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "feedbackforms");
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
					log.error("Error while closing the Statement in OnlineFeedbackDAOImpl saveInsurerApproveRejectStatus ()",sqlExp);
					throw new TTKException(sqlExp, "feedbackforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineFeedbackDAOImpl saveInsurerApproveRejectStatus()",sqlExp);
						throw new TTKException(sqlExp, "feedbackforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "feedbackforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of saveCashlessFeedbackDetails(ArrayList fbdetails)
	
	/**
	 * This method int and updates Approve/reject /required information Staus
	 * @param  strInsCode 
	 * @param  strMsgId 
	 * @param  strFilename
	 * @exception throws TTKException
	 */
	public int sendMailIntimation(String strInsCode, String strMsgId,String strFilename)throws TTKException {
		int iResult = 0;
		int strDestMsgSeqId=0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		// TODO Auto-generated method stub
		try
		{
			conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSendMailIntimation);
		cStmtObject.setString(1,strInsCode);
		cStmtObject.setString(2,strMsgId);
		cStmtObject.setString(3,strFilename);
		cStmtObject.registerOutParameter(4,Types.INTEGER);
		cStmtObject.registerOutParameter(5,Types.INTEGER);
		cStmtObject.execute();
		strDestMsgSeqId=cStmtObject.getInt(4);
		iResult=cStmtObject.getInt(5);

	}// end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "feedbackforms");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "feedbackforms");
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
				log.error("Error while closing the Statement in OnlineFeedbackDAOImpl sendMailIntimation ()",sqlExp);
				throw new TTKException(sqlExp, "feedbackforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in OnlineFeedbackDAOImpl sendMailIntimation()",sqlExp);
					throw new TTKException(sqlExp, "feedbackforms");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "feedbackforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return iResult;
	}//end of sendMailIntimation(insCode,msgID,filename)
	
}//end of OnlineFeedbackDAOImpl



