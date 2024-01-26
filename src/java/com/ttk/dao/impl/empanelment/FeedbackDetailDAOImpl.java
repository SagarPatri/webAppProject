/**
 * @ (#) FeedBackDaoImpl.java Sep 27, 2005
 * Project      :
 * File         : FeedbackDetailDaoImpl.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 27, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : Nagaraj D V
 * Modified date : Jan 19, 2006
 * Reason        : To remove the additional parameters for search query
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

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.FeedbackDetailVO;

public class FeedbackDetailDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(FeedbackDetailDAOImpl.class);

	private static final String strHospFeedBackList = "SELECT * FROM (SELECT FEEDBACK_SEQ_ID,HOSP_SEQ_ID,FEEDBACK_DATE,SUGGESTIONS,(SELECT ac.ANS_DESCRIPTION FROM app.TPA_HOSP_FEEDBACK_DETAILS fd,app.TPA_HOSP_ANSWER_CODE ac  WHERE fd.FEEDBACK_SEQ_ID = f.FEEDBACK_SEQ_ID AND fd.ANS_TYPE_ID = ac.ANS_TYPE_ID AND fd.QUEST_TYPE_ID = '1') as TTK_RATING,ADDED_BY, ADDED_DATE,UPDATED_BY,UPDATED_DATE,row_number() OVER (ORDER BY FEEDBACK_DATE ASC) Q FROM  app.TPA_HOSP_FEEDBACK f"; ////WHERE HOSP_SEQ_ID = ? (Hospital sequence id is required as a mandatory)
	private static final String strHospFeedBackDetails = "SELECT A.FEEDBACK_SEQ_ID,A.FEEDBACK_DATE,A.SUGGESTIONS,B.QUEST_TYPE_ID,B.ANS_TYPE_ID, qc.QUEST_DESCRIPTION FROM app.TPA_HOSP_FEEDBACK A ,app.TPA_HOSP_FEEDBACK_DETAILS B , app.TPA_HOSP_QUEST_CODE qc WHERE A.FEEDBACK_SEQ_ID = ? AND A.FEEDBACK_SEQ_ID = B.FEEDBACK_SEQ_ID AND B.QUEST_TYPE_ID = qc.QUEST_TYPE_ID ORDER BY qc.SORT_NO";
	private static final String strAddUpdateFeedbackDetails = "{CALL HOSPITAL_EMPANEL_PKG_PR_HOSPITAL_FEEDBACK_SAVE (?,?,?,?,?,?,?)}";
	
	private static final String strDeleteFeedbackDetails  = "{CALL HOSPITAL_EMPANEL_PKG_PR_HOSPITAL_FEEDBACK_DELETE (?,?)}";
	private static final String strQuestionList = "SELECT QUEST_TYPE_ID,QUEST_DESCRIPTION FROM app.TPA_HOSP_QUEST_CODE ORDER BY SORT_NO";
	private static final String strAnswerList = "SELECT ANS_TYPE_ID,ANS_DESCRIPTION FROM app.TPA_HOSP_ANSWER_CODE WHERE QUEST_TYPE_ID = ?  ORDER BY SORT_NO";
	private static final String strPtnrFeedBackList = "SELECT * FROM (SELECT FEEDBACK_SEQ_ID , PTNR_SEQ_ID , FEEDBACK_DATE , SUGGESTIONS ,( SELECT ac.ANS_DESCRIPTION  FROM  APP.TPA_PATNR_FEEDBACK_DETAILS fd, APP.TPA_PARTNER_ANSWER_CODE ac  WHERE fd.FEEDBACK_SEQ_ID  =  f.FEEDBACK_SEQ_ID AND fd.ANS_TYPE_ID = ac.ANS_TYPE_ID  AND fd.QUEST_TYPE_ID = '1') as TTK_RATING ,ADDED_BY, ADDED_DATE , UPDATED_BY , UPDATED_DATE, dense_RANK() OVER (ORDER BY #) Q FROM  APP.TPA_PARTNER_FEEDBACK f";
	private static final String strPartnerQuestionList = "SELECT  QUEST_TYPE_ID ,  QUEST_DESCRIPTION   FROM APP.TPA_PARTNER_QUEST_CODE   ORDER BY   SORT_NO";
	private static final String strPartnerAnswerList = "SELECT ANS_TYPE_ID,ANS_DESCRIPTION FROM APP.TPA_PARTNER_ANSWER_CODE WHERE QUEST_TYPE_ID = ?  ORDER BY SORT_NO";
	private static final String strAddUpdatePartnerFeedbackDetails = "{CALL PARTNER_EMPANELL_PKG_PARTNER_FEEDBACK_SAVE (?,?,?,?,?,?,?)}";
	private static final String strPtnrFeedBackDetails = "SELECT A.FEEDBACK_SEQ_ID , A.FEEDBACK_DATE , A.SUGGESTIONS, B.QUEST_TYPE_ID, B.ANS_TYPE_ID, qc.QUEST_DESCRIPTION FROM  APP.TPA_PARTNER_FEEDBACK A ,APP.TPA_PATNR_FEEDBACK_DETAILS B , APP.TPA_PARTNER_QUEST_CODE qc WHERE A.FEEDBACK_SEQ_ID = ?  AND  A.FEEDBACK_SEQ_ID = B.FEEDBACK_SEQ_ID AND B.QUEST_TYPE_ID = qc.QUEST_TYPE_ID ORDER BY qc.SORT_NO";
	private static final String strDeletePartnerFeedbackDetails = "{CALL PARTNER_EMPANELL_PKG_PARTNER_FEEDBACK_DELETE (?,?)}";

	private static final int PTNR_SEQ_ID = 2;
	private static final int FEEDBACK_SEQ_ID = 1;
	private static final int HOSP_SEQ_ID = 2;
	private static final int FEEDBACK_DATE = 3;
	private static final int SUGGESTIONS = 4;
	private static final int QID_AID = 5;
	private static final int USER_SEQ_ID = 6;
	private static final int ROW_PROCESSED = 7;

	
	
	/**
	 * This method returns the ArrayList, which contains the FeedbackDetailVO's
	 * which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of FeedbackDetailVO object's which contains the
	 *         hospital feedback details
	 * @throws TTKException
	 * @exception throws TTKException
	 */
	public ArrayList getFeedbackList(ArrayList alSearchObjects)
			throws TTKException {
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strHospFeedBackList;
        FeedbackDetailVO feedbackDetailVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            String strHospSeqId  = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            String strFromDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(1)).getValue());
            String strToDate = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(2)).getValue());
           
            
            if (TTKCommon.checkNull(strFromDate).length() < 1 && TTKCommon.checkNull(strToDate).length() < 1) {
				strFromDate = null;
				strToDate = null;
			
			sbfDynamicQuery.append(" WHERE HOSP_SEQ_ID ="+strHospSeqId+" AND FEEDBACK_DATE BETWEEN coalesce(to_date("+strFromDate+",'dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  coalesce(to_date("+strToDate+",'dd/mm/yyyy'),FEEDBACK_DATE ))");
            }
            else if(TTKCommon.checkNull(strFromDate).length() > 1 && TTKCommon.checkNull(strToDate).length() < 1){
            	strToDate = null;
            	sbfDynamicQuery.append(" WHERE HOSP_SEQ_ID ="+strHospSeqId+" AND FEEDBACK_DATE BETWEEN coalesce(to_date('"+strFromDate+"','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  coalesce(to_date("+strToDate+",'dd/mm/yyyy'),FEEDBACK_DATE ))");
            }
            else if(TTKCommon.checkNull(strFromDate).length() < 1 && TTKCommon.checkNull(strToDate).length() > 1){
            	strFromDate = null;
            	sbfDynamicQuery.append(" WHERE HOSP_SEQ_ID ="+strHospSeqId+" AND FEEDBACK_DATE BETWEEN coalesce(to_date("+strFromDate+",'dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  coalesce(to_date('"+strToDate+"','dd/mm/yyyy'),FEEDBACK_DATE ))");
            }
        else{
        	sbfDynamicQuery.append(" WHERE HOSP_SEQ_ID ="+strHospSeqId+" AND FEEDBACK_DATE BETWEEN coalesce(to_date('"+strFromDate+"','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  coalesce(to_date('"+strToDate+"','dd/mm/yyyy'),FEEDBACK_DATE ))");
        }
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        //build the Order By Clause
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", TTKCommon.checkNull(((String)alSearchObjects.get(3)))+" "+TTKCommon.checkNull(((String)alSearchObjects.get(4))));
        //build the row numbers to be fetched
        sbfDynamicQuery = sbfDynamicQuery .append(" A WHERE Q >= "+TTKCommon.checkNull(((String)alSearchObjects.get(5)))+ " AND Q <= "+TTKCommon.checkNull(((String)alSearchObjects.get(6))));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString(); 
         //System.out.println("strStaticQuery.."+strStaticQuery);
		try {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            conn.commit();
            if(rs != null){
                while (rs.next()){
                    feedbackDetailVO = new FeedbackDetailVO();
					if (rs.getString("FEEDBACK_SEQ_ID") != null) {
						feedbackDetailVO.setFeedbackSeqId(new Long(rs
								.getString("FEEDBACK_SEQ_ID")));
					}// end of if (rs.getString("FEEDBACK_SEQ_ID")!= null)

					if (rs.getDate("FEEDBACK_DATE") != null) {
						feedbackDetailVO.setFeedbackDate(new java.util.Date((rs
								.getTimestamp("FEEDBACK_DATE").getTime())));
					}// end of if (rs.getDate("FEEDBACK_DATE")!= null)

					feedbackDetailVO.setRating(TTKCommon.checkNull(rs
							.getString("TTK_RATING")));
					feedbackDetailVO.setSuggestions(TTKCommon.checkNull(rs
							.getString("SUGGESTIONS")));

                    resultList.add(feedbackDetailVO);
				}// end of while (rs.next())
			}// end of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "feedback");
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
							"Error while closing the Resultset in FeedbackDetailDAOImpl getFeedbackList()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Statement in FeedbackDetailDAOImpl getFeedbackList()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
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
									"Error while closing the Connection in FeedbackDetailDAOImpl getFeedbackList()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getFeedbackList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains
	 * the hospital feedback details
	 * 
	 * @param lngFeedBackSeqID
	 *            Long object which contains the feed back seq id
	 * @return FeedbackDetailVO object's which contains the hospital feedback
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getFeedback(Long lngFeedBackSeqID) throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> alFeedBackList = null;
		ArrayList<Object> alAnswerList = null;
		FeedbackDetailVO feedbackDetailVO;
		String strParentId = null;
		CacheObject cacheObject = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt1 = conn.prepareStatement(strHospFeedBackDetails);
			pStmt2 = conn.prepareStatement(strAnswerList);
			pStmt1.setLong(1, lngFeedBackSeqID);
			rs1 = pStmt1.executeQuery();
			if (rs1 != null) {
				while (rs1.next()) {
					if (alFeedBackList == null) {
						alFeedBackList = new ArrayList<Object>();
					}// end of if (alFeedBackList == null)
					feedbackDetailVO = new FeedbackDetailVO();
					if (rs1.getTimestamp("FEEDBACK_DATE") != null) {
						feedbackDetailVO.setFeedbackDate(new java.util.Date(
								(rs1.getTimestamp("FEEDBACK_DATE").getTime())));
					}// end of if (rs1.getTimestamp("FEEDBACK_DATE")!= null)
					feedbackDetailVO.setSuggestions(TTKCommon.checkNull(rs1
							.getString("SUGGESTIONS")));
					feedbackDetailVO.setQuestionDesc(TTKCommon.checkNull(rs1
							.getString("QUEST_DESCRIPTION")));
					feedbackDetailVO.setAnswerId(TTKCommon.checkNull(rs1
							.getString("ANS_TYPE_ID")));
					strParentId = TTKCommon.checkNull(rs1
							.getString("QUEST_TYPE_ID"));
					feedbackDetailVO.setQuestionId(strParentId);
					pStmt2.setString(1, strParentId);
					rs2 = pStmt2.executeQuery();
					alAnswerList = null;
					if (rs2 != null) {
						while (rs2.next()) {
							if (alAnswerList == null) {
								alAnswerList = new ArrayList<Object>();
							}// end of if (alAnswerList == null)
							cacheObject = new CacheObject();
							cacheObject.setCacheId(TTKCommon.checkNull(rs2
									.getString("ANS_TYPE_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2
									.getString("ANS_DESCRIPTION")));
							alAnswerList.add(cacheObject);
						}// end of inner while (rs2.next())
					}// end of if(rs2 != null)
					feedbackDetailVO.setAnswerList(alAnswerList);
					alFeedBackList.add(feedbackDetailVO);
					if (rs2 != null) {
						rs2.close();
					}// end of if(rs2 != null)
					rs2 = null;
				} // end of outer while(rs1.next())
			}// End of if (rs1!=null)
			if (pStmt2 != null) {
				pStmt2.close();
			}// end of if (pStmt2 != null)
			pStmt2 = null;
			return alFeedBackList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs2 != null)
						rs2.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Second Resultset in FeedbackDetailDAOImpl getFeedBack()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
				}// end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control
						// reaches here. Try closing the first resultset now.
				{
					try {
						if (rs1 != null)
							rs1.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the First Resultset in FeedbackDetailDAOImpl getFeedBack()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control
							// reaches here. Try closing the Second Statement
							// now.
					{
						try {
							if (pStmt2 != null)
								pStmt2.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Second Statement in FeedbackDetailDAOImpl getFeedBack()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed,
								// control reaches here. Try closing the First
								// Statement now.
						{
							try {
								if (pStmt1 != null)
									pStmt1.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the FirstStatement in FeedbackDetailDAOImpl getFeedBack()",
										sqlExp);
								throw new TTKException(sqlExp, "feedback");
							}// end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed,
									// control reaches here. Try closing the
									// Connection now.
							{
								try {
									if (conn != null)
										conn.close();
								}// end of try
								catch (SQLException sqlExp) {
									log.error(
											"Error while closing the Connection in FeedbackDetailDAOImpl getFeedBack()",
											sqlExp);
									throw new TTKException(sqlExp, "feedback");
								}// end of catch (SQLException sqlExp)
							}// end of finally
						}// end of finally
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}// end of finally
		}// end of finally
	} // end of getFeedBack(int feedbackID)

	/**
	 * This method adds or updates the hospital feedback details The method also
	 * calls other methods on DAO to insert/update the hospital feedback details
	 * to the database
	 * 
	 * @param feedbackDetailVO
	 *            FeedbackDetailVO object which contains the hospital feedback
	 *            details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public long addUpdateFeedback(FeedbackDetailVO feedbackDetailVO)
			throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateFeedbackDetails);
			
			if (feedbackDetailVO.getFeedbackSeqId()!= null)
			{
				//System.out.println("FEEDBACK_SEQ_ID.."+feedbackDetailVO.getFeedbackSeqId().longValue());
				cStmtObject.setLong(FEEDBACK_SEQ_ID,feedbackDetailVO.getFeedbackSeqId().longValue());
			}//end of if (feedbackDetailVO.getFeedbackSeqId()!= null)
			else
			{
				//System.out.println("FEEDBACK_SEQ_ID.."+0);
				cStmtObject.setLong(FEEDBACK_SEQ_ID,0);
			}//end 
			if (feedbackDetailVO.getHospSeqId()!= null)
			{
				cStmtObject.setLong(HOSP_SEQ_ID,feedbackDetailVO.getHospSeqId().longValue());
			}//end of if (feedbackDetailVO.getHospSeqId()!= null)
			else
			{
				cStmtObject.setLong(HOSP_SEQ_ID,0);
			}//end of else
			if(feedbackDetailVO.getFeedbackDate() != null && feedbackDetailVO.getFeedbackDate() != "")
			{
				cStmtObject.setTimestamp(FEEDBACK_DATE,new Timestamp(TTKCommon.getUtilDate(feedbackDetailVO.getFeedbackDate()).getTime()));
			}//end of if(feedbackDetailVO.getFeedbackDate() != null && feedbackDetailVO.getFeedbackDate() != "")
			else
			{
				cStmtObject.setTimestamp(FEEDBACK_DATE, null);
			}//end of else
			cStmtObject.setString(SUGGESTIONS,feedbackDetailVO.getSuggestions());
			System.out.println("question Answers .."+concatQAId(feedbackDetailVO.getQuestions(),feedbackDetailVO.getAnswers()));
			if (feedbackDetailVO.getQuestions() != null && feedbackDetailVO.getAnswers() != null)
			{
				cStmtObject.setString(QID_AID,concatQAId(feedbackDetailVO.getQuestions(),feedbackDetailVO.getAnswers()));
			}//end of if(feedbackDetailVO.getQuestions() != null && feedbackDetailVO.getAnswers() != null)
			//System.out.println("USER_SEQ_ID.."+feedbackDetailVO.getUpdatedBy().longValue());
			cStmtObject.setLong(USER_SEQ_ID,feedbackDetailVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(FEEDBACK_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.INTEGER);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(FEEDBACK_SEQ_ID);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
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
							"Error while closing the Statement in FeedbackDetailDAOImpl addUpdateFeedback()",
							sqlExp);
        			throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Connection in FeedbackDetailDAOImpl addUpdateFeedback()",
								sqlExp);
        				throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}// end of addUpdateFeedback(FeedbackDetailVO feedbackDetailVO)

	/**
	 * This method delete's the hospital feedback records from the database.
	 * 
	 * @param strDeleteSeqID
	 *            String the hospital feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int deleteFeedback(String strDeleteSeqID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDeleteFeedbackDetails);
			cStmtObject.setString(1, strDeleteSeqID);// Feedback Sequence Id's
			cStmtObject.registerOutParameter(2, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(2);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
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
							"Error while closing the Statement in FeedbackDetailDAOImpl deleteFeedback()",
							sqlExp);
        			throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Connection in FeedbackDetailDAOImpl deleteFeedback()",
								sqlExp);
        				throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteFeedback(ArrayList alFeedbackList)

	/**
	 * This method returns the ArrayList, which contains all the details about
	 * the hospital feedback questions and answers list
	 * 
	 * @return ArrayList which contains all the details about the hospital
	 *         feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAList() throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> alQuestionObjects = null;
		FeedbackDetailVO feedBackDetailVO = null;
		ArrayList<Object> alAnswerList = null;
		String strParentId = null;
		CacheObject cacheObject = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt1 = conn.prepareStatement(strQuestionList);
			pStmt2 = conn.prepareStatement(strAnswerList);
			rs1 = pStmt1.executeQuery();
			if (rs1 != null) {
				while (rs1.next()) {
					feedBackDetailVO = new FeedbackDetailVO();
					if (alQuestionObjects == null) {
						alQuestionObjects = new ArrayList<Object>();
					}// end of if(alQuestionObjects == null)
					strParentId = TTKCommon.checkNull(rs1
							.getString("QUEST_TYPE_ID"));
					feedBackDetailVO.setQuestionId(strParentId);
					feedBackDetailVO.setQuestionDesc(TTKCommon.checkNull(rs1
							.getString("QUEST_DESCRIPTION")));
					pStmt2.setString(1, strParentId);
					rs2 = pStmt2.executeQuery();
					alAnswerList = null;
					if (rs2 != null) {
						while (rs2.next()) {
							cacheObject = new CacheObject();
							if (alAnswerList == null) {
								alAnswerList = new ArrayList<Object>();
							}// end of if (alAnswerList == null)
							cacheObject.setCacheId(TTKCommon.checkNull(rs2
									.getString("ANS_TYPE_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2
									.getString("ANS_DESCRIPTION")));
							alAnswerList.add(cacheObject);
						}// end of inner while (rs2.next())
					}// end of if(rs2 != null)
					feedBackDetailVO.setAnswerList(alAnswerList);
					alQuestionObjects.add(feedBackDetailVO);
					if (rs2 != null) {
						rs2.close();
					}// end of if(rs2 != null)
					rs2 = null;
				}// end of outer while (rs1.next())
			}// end of if(rs1 != null)
			if (pStmt2 != null) {
				pStmt2.close();
			}// end of if (pStmt2 != null)
			pStmt2 = null;
			return alQuestionObjects;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs2 != null)
						rs2.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Second Resultset in FeedbackDetailDAOImpl getQAList()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
				}// end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control
						// reaches here. Try closing the first resultset now.
				{
					try {
						if (rs1 != null)
							rs1.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the First Resultset in FeedbackDetailDAOImpl getQAList()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control
							// reaches here. Try closing the Second Statement
							// now.
					{
						try {
							if (pStmt2 != null)
								pStmt2.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Second Statement in FeedbackDetailDAOImpl getQAList()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed,
								// control reaches here. Try closing the First
								// Statement now.
						{
							try {
								if (pStmt1 != null)
									pStmt1.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the FirstStatement in FeedbackDetailDAOImpl getQAList()",
										sqlExp);
								throw new TTKException(sqlExp, "feedback");
							}// end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed,
									// control reaches here. Try closing the
									// Connection now.
							{
								try {
									if (conn != null)
										conn.close();
								}// end of try
								catch (SQLException sqlExp) {
									log.error(
											"Error while closing the Second Connection in FeedbackDetailDAOImpl getQAList()",
											sqlExp);
									throw new TTKException(sqlExp, "feedback");
								}// end of catch (SQLException sqlExp)
							}// end of finally
						}// end of finally
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getQAList()

	/**
	 * This method returns the ArrayList, which contains the FeedbackDetailVO's
	 * which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of FeedbackDetailVO object's which contains the partner
	 *         feedback details
	 * @throws TTKException
	 * @exception throws TTKException
	 */

	public ArrayList getPartnerFeedbackList(ArrayList alSearchObjects)
			throws TTKException {
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strPtnrFeedBackList;
        FeedbackDetailVO feedbackDetailVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			String strPtnrSeqId = ((SearchCriteria) alSearchObjects.get(0))
					.getValue();
			String strFromDate = TTKCommon
					.checkNull(((SearchCriteria) alSearchObjects.get(1))
							.getValue());
			String strToDate = TTKCommon
					.checkNull(((SearchCriteria) alSearchObjects.get(2))
							.getValue());

			strFromDate =(strFromDate==null||"".equals(strFromDate))?null:"'"+strFromDate+"'";
			strToDate =(strToDate==null||"".equals(strToDate))?null:"'"+strToDate+"'";
				
					
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strPtnrSeqId);
				
				if ( TTKCommon.checkNull(strFromDate).length()<1  ) {
				strFromDate=null;
			}
			if ( TTKCommon.checkNull(strToDate).length()<1  ) {
				strToDate=null;
			}
			sbfDynamicQuery
					.append(" WHERE PTNR_SEQ_ID = '" 
							+ strPtnrSeqId
							+ "' AND DATE(FEEDBACK_DATE) BETWEEN COALESCE (to_date("
							+ strFromDate
							+ ",'dd/mm/yyyy'),date('01/01/2005')) and COALESCE (to_date("
							+ strToDate +",'dd/mm/yyyy') ,current_date))aa");
					/*append(" WHERE PTNR_SEQ_ID ="
							+ strPtnrSeqId
							+ " AND FEEDBACK_DATE BETWEEN COALESCE(to_date("
							+ strFromDate
							+ ",'dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  COALESCE(to_date("
							+ strToDate + ",'dd/mm/yyyy'),FEEDBACK_DATE )");*/
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)

		// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						TTKCommon.checkNull(((String) alSearchObjects.get(3)))
								+ " "
								+ TTKCommon.checkNull(((String) alSearchObjects
										.get(4))));

		// build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery.append(" WHERE Q >= "
				+ TTKCommon.checkNull(((String) alSearchObjects.get(5)))
				+ " AND Q <= "
				+ TTKCommon.checkNull(((String) alSearchObjects.get(6))));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();

		try {
			conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
			//System.out.println("Search Partner FeedBack...." + strStaticQuery);
            rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
                    feedbackDetailVO = new FeedbackDetailVO();
					if (rs.getString("FEEDBACK_SEQ_ID") != null) {
						feedbackDetailVO.setFeedbackSeqId(new Long(rs
								.getString("FEEDBACK_SEQ_ID")));
					}// end of if (rs.getString("FEEDBACK_SEQ_ID")!= null)

					if (rs.getDate("FEEDBACK_DATE") != null) {
						feedbackDetailVO.setFeedbackDate(new java.util.Date((rs
								.getTimestamp("FEEDBACK_DATE").getTime())));
					}// end of if (rs.getDate("FEEDBACK_DATE")!= null)

					feedbackDetailVO.setRating(TTKCommon.checkNull(rs
							.getString("TTK_RATING")));
					feedbackDetailVO.setSuggestions(TTKCommon.checkNull(rs
							.getString("SUGGESTIONS")));

                    resultList.add(feedbackDetailVO);
				}// end of while (rs.next())
			}// end of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "feedback");
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
							"Error while closing the Resultset in FeedbackDetailDAOImpl getPartnerFeedbackList()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Statement in FeedbackDetailDAOImpl getPartnerFeedbackList()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
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
									"Error while closing the Connection in FeedbackDetailDAOImpl getPartnerFeedbackList()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getFeedbackList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList, which contains all the details about
	 * the hospital feedback questions and answers list
	 * 
	 * @return ArrayList which contains all the details about the hospital
	 *         feedback questions and answers list
	 * @exception throws TTKException
	 */
	public ArrayList getQAPartnerList() throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> alQuestionObjects = null;
		FeedbackDetailVO feedBackDetailVO = null;
		ArrayList<Object> alAnswerList = null;
		String strParentId = null;
		CacheObject cacheObject = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt1 = conn.prepareStatement(strPartnerQuestionList);
			pStmt2 = conn.prepareStatement(strPartnerAnswerList);
			rs1 = pStmt1.executeQuery();
			if (rs1 != null) {
				while (rs1.next()) {
					feedBackDetailVO = new FeedbackDetailVO();
					if (alQuestionObjects == null) {
						alQuestionObjects = new ArrayList<Object>();
					}// end of if(alQuestionObjects == null)
					strParentId = TTKCommon.checkNull(rs1
							.getString("QUEST_TYPE_ID"));
					feedBackDetailVO.setQuestionId(strParentId);
					//System.out.println("strParentId.."+feedBackDetailVO.getQuestionId());
					feedBackDetailVO.setQuestionDesc(TTKCommon.checkNull(rs1
							.getString("QUEST_DESCRIPTION")));
					pStmt2.setString(1, strParentId);
					rs2 = pStmt2.executeQuery();
					alAnswerList = null;
					if (rs2 != null) {
						while (rs2.next()) {
							cacheObject = new CacheObject();
							if (alAnswerList == null) {
								alAnswerList = new ArrayList<Object>();
							}// end of if (alAnswerList == null)
							cacheObject.setCacheId(TTKCommon.checkNull(rs2
									.getString("ANS_TYPE_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2
									.getString("ANS_DESCRIPTION")));
							alAnswerList.add(cacheObject);
						}// end of inner while (rs2.next())
					}// end of if(rs2 != null)
					feedBackDetailVO.setAnswerList(alAnswerList);
					alQuestionObjects.add(feedBackDetailVO);
					if (rs2 != null) {
						rs2.close();
					}// end of if(rs2 != null)
					rs2 = null;
				}// end of outer while (rs1.next())
			}// end of if(rs1 != null)
			if (pStmt2 != null) {
				pStmt2.close();
			}// end of if (pStmt2 != null)
			pStmt2 = null;
			return alQuestionObjects;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs2 != null)
						rs2.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Second Resultset in FeedbackDetailDAOImpl getQAPartnerList()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
				}// end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control
						// reaches here. Try closing the first resultset now.
				{
					try {
						if (rs1 != null)
							rs1.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the First Resultset in FeedbackDetailDAOImpl getQAPartnerList()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control
							// reaches here. Try closing the Second Statement
							// now.
					{
						try {
							if (pStmt2 != null)
								pStmt2.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Second Statement in FeedbackDetailDAOImpl getQAPartnerList()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed,
								// control reaches here. Try closing the First
								// Statement now.
						{
							try {
								if (pStmt1 != null)
									pStmt1.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the FirstStatement in FeedbackDetailDAOImpl getQAPartnerList()",
										sqlExp);
								throw new TTKException(sqlExp, "feedback");
							}// end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed,
									// control reaches here. Try closing the
									// Connection now.
							{
								try {
									if (conn != null)
										conn.close();
								}// end of try
								catch (SQLException sqlExp) {
									log.error(
											"Error while closing the Second Connection in FeedbackDetailDAOImpl getQAPartnerList()",
											sqlExp);
									throw new TTKException(sqlExp, "feedback");
								}// end of catch (SQLException sqlExp)
							}// end of finally
						}// end of finally
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getQAList()

	/**
	 * This method adds or updates the hospital feedback details The method also
	 * calls other methods on DAO to insert/update the hospital feedback details
	 * to the database
	 * 
	 * @param feedbackDetailVO
	 *            FeedbackDetailVO object which contains the hospital feedback
	 *            details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public long addUpdatePartnerFeedback(FeedbackDetailVO feedbackDetailVO)
			throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdatePartnerFeedbackDetails);
			if (feedbackDetailVO.getFeedbackSeqId() != null) {
				cStmtObject.setLong(FEEDBACK_SEQ_ID,
						feedbackDetailVO.getFeedbackSeqId());
			}// end of if (feedbackDetailVO.getFeedbackSeqId()!= null)
			else {
				cStmtObject.setLong(FEEDBACK_SEQ_ID, 0);
			}// end
			if (feedbackDetailVO.getPtnrSeqId() != null) {
				cStmtObject.setLong(PTNR_SEQ_ID,
						feedbackDetailVO.getPtnrSeqId());
			}// end of if (feedbackDetailVO.getHospSeqId()!= null)
			else {
				cStmtObject.setLong(PTNR_SEQ_ID, 0);
			}// end of else
			if (feedbackDetailVO.getFeedbackDate() != null
					&& feedbackDetailVO.getFeedbackDate() != "") {
				cStmtObject.setTimestamp(FEEDBACK_DATE, new Timestamp(TTKCommon
						.getUtilDate(feedbackDetailVO.getFeedbackDate())
						.getTime()));
			}// end of if(feedbackDetailVO.getFeedbackDate() != null &&
				// feedbackDetailVO.getFeedbackDate() != "")
			else {
				cStmtObject.setTimestamp(FEEDBACK_DATE, null);
			}// end of else
			cStmtObject.setString(SUGGESTIONS,
					feedbackDetailVO.getSuggestions());
			if (feedbackDetailVO.getQuestions() != null
					&& feedbackDetailVO.getAnswers() != null) {
				cStmtObject.setString(
						QID_AID,
						concatQAId(feedbackDetailVO.getQuestions(),
								feedbackDetailVO.getAnswers()));
			}// end of if(feedbackDetailVO.getQuestions() != null &&
				// feedbackDetailVO.getAnswers() != null)
			cStmtObject.setLong(USER_SEQ_ID, feedbackDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(FEEDBACK_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.INTEGER);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(FEEDBACK_SEQ_ID);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
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
							"Error while closing the Statement in FeedbackDetailDAOImpl addUpdateFeedback()",
							sqlExp);
        			throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Connection in FeedbackDetailDAOImpl addUpdateFeedback()",
								sqlExp);
        				throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}// end of addUpdateFeedback(FeedbackDetailVO feedbackDetailVO)

	/**
	 * This method returns the ArrayList of FeedbackDetailVO's, which contains
	 * the hospital feedback details
	 * 
	 * @param lngFeedBackSeqID
	 *            Long object which contains the feed back seq id
	 * @return FeedbackDetailVO object's which contains the hospital feedback
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getPartnerFeedback(Long lngFeedBackSeqID)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> alFeedBackList = null;
		ArrayList<Object> alAnswerList = null;
		FeedbackDetailVO feedbackDetailVO;
		String strParentId = null;
		CacheObject cacheObject = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt1 = conn.prepareStatement(strPtnrFeedBackDetails);
			pStmt2 = conn.prepareStatement(strPartnerAnswerList);
			pStmt1.setLong(1, lngFeedBackSeqID);
			rs1 = pStmt1.executeQuery();
			if (rs1 != null) {
				while (rs1.next()) {
					if (alFeedBackList == null) {
						alFeedBackList = new ArrayList<Object>();
					}// end of if (alFeedBackList == null)
					feedbackDetailVO = new FeedbackDetailVO();
					if (rs1.getTimestamp("FEEDBACK_DATE") != null) {
						feedbackDetailVO.setFeedbackDate(new java.util.Date(
								(rs1.getTimestamp("FEEDBACK_DATE").getTime())));
					}// end of if (rs1.getTimestamp("FEEDBACK_DATE")!= null)
					feedbackDetailVO.setSuggestions(TTKCommon.checkNull(rs1
							.getString("SUGGESTIONS")));
					feedbackDetailVO.setQuestionDesc(TTKCommon.checkNull(rs1
							.getString("QUEST_DESCRIPTION")));
					feedbackDetailVO.setAnswerId(TTKCommon.checkNull(rs1
							.getString("ANS_TYPE_ID")));
					strParentId = TTKCommon.checkNull(rs1
							.getString("QUEST_TYPE_ID"));
					feedbackDetailVO.setQuestionId(strParentId);
					pStmt2.setString(1, strParentId);
					rs2 = pStmt2.executeQuery();
					alAnswerList = null;
					if (rs2 != null) {
						while (rs2.next()) {
							if (alAnswerList == null) {
								alAnswerList = new ArrayList<Object>();
							}// end of if (alAnswerList == null)
							cacheObject = new CacheObject();
							cacheObject.setCacheId(TTKCommon.checkNull(rs2
									.getString("ANS_TYPE_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2
									.getString("ANS_DESCRIPTION")));
							alAnswerList.add(cacheObject);
						}// end of inner while (rs2.next())
					}// end of if(rs2 != null)
					feedbackDetailVO.setAnswerList(alAnswerList);
					alFeedBackList.add(feedbackDetailVO);
					if (rs2 != null) {
						rs2.close();
					}// end of if(rs2 != null)
					rs2 = null;
				} // end of outer while(rs1.next())
			}// End of if (rs1!=null)
			if (pStmt2 != null) {
				pStmt2.close();
			}// end of if (pStmt2 != null)
			pStmt2 = null;
			return alFeedBackList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs2 != null)
						rs2.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Second Resultset in FeedbackDetailDAOImpl getPartnerFeedBack()",
							sqlExp);
					throw new TTKException(sqlExp, "feedback");
				}// end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control
						// reaches here. Try closing the first resultset now.
				{
					try {
						if (rs1 != null)
							rs1.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the First Resultset in FeedbackDetailDAOImpl getPartnerFeedBack()",
								sqlExp);
						throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control
							// reaches here. Try closing the Second Statement
							// now.
					{
						try {
							if (pStmt2 != null)
								pStmt2.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Second Statement in FeedbackDetailDAOImpl getPartnerFeedBack()",
									sqlExp);
							throw new TTKException(sqlExp, "feedback");
						}// end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed,
								// control reaches here. Try closing the First
								// Statement now.
						{
							try {
								if (pStmt1 != null)
									pStmt1.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the FirstStatement in FeedbackDetailDAOImpl getPartnerFeedBack()",
										sqlExp);
								throw new TTKException(sqlExp, "feedback");
							}// end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed,
									// control reaches here. Try closing the
									// Connection now.
							{
								try {
									if (conn != null)
										conn.close();
								}// end of try
								catch (SQLException sqlExp) {
									log.error(
											"Error while closing the Connection in FeedbackDetailDAOImpl getPartnerFeedBack()",
											sqlExp);
									throw new TTKException(sqlExp, "feedback");
								}// end of catch (SQLException sqlExp)
							}// end of finally
						}// end of finally
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}// end of finally
		}// end of finally
	} // end of getFeedBack(int feedbackID)

	/**
	 * This method delete's the partner feedback records from the database.
	 * 
	 * @param strDeleteSeqID
	 *            String the partner feedback sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int deletePartnerFeedback(String strDeleteSeqID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDeletePartnerFeedbackDetails);
			cStmtObject.setString(1, strDeleteSeqID);// Feedback Sequence Id's
			cStmtObject.registerOutParameter(2, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(2);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
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
							"Error while closing the Statement in FeedbackDetailDAOImpl deletePartnerFeedback()",
							sqlExp);
        			throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Connection in FeedbackDetailDAOImpl deletePartnerFeedback()",
								sqlExp);
        				throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteFeedback(ArrayList alFeedbackList)

	/**
	 * This method returns the type Concatenated String For the Given Question
	 * And Answers Array Of Strings
	 * 
	 * @param strQuestion
	 *            Array of QuestionID's which has to be concatenated
	 * @param strAnswerArray
	 *            of AnswerID's Which has to be concatenated
	 * @return String which Contains the details of concatenated QuestionID's
	 *         And AnswerID's
	 */
	public String concatQAId(String[] strQuestion, String[] strAnswer) {
		StringBuffer strBuffConcate = new StringBuffer().append("|");
		for (int i = 0; i < strQuestion.length; i++) {
			strBuffConcate.append(strQuestion[i]).append("|")
					.append(strAnswer[i]).append("|");
		}// end of for(int i=0;i<strQuestion.length;i++)
		return strBuffConcate.toString();
	}// end of concatQAId(String [] strQuestion, String[] strAnswer)

	public int deleteDocs(ArrayList alDeleteBatch) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDeletePartnerFeedbackDetails);
			cStmtObject.registerOutParameter(2, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "feedback");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "feedback");
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
							"Error while closing the Statement in FeedbackDetailDAOImpl deletePartnerFeedback()",
							sqlExp);
        			throw new TTKException(sqlExp, "feedback");
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
								"Error while closing the Connection in FeedbackDetailDAOImpl deletePartnerFeedback()",
								sqlExp);
        				throw new TTKException(sqlExp, "feedback");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "feedback");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}

} // end of FeedbackDetailDAOImpl.
