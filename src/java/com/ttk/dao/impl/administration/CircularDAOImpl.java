/**
 * @ (#) CircularsDAOImpl.java Nov 05, 2005
 * Project      : TTK HealthCare Services
 * File         : CircularsDAOImpl
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 05, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.administration;

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
import com.ttk.dto.administration.CircularVO;
import com.ttk.dto.common.SearchCriteria;

public class CircularDAOImpl implements BaseDAO,Serializable {
	
	private static Logger log = Logger.getLogger(CircularDAOImpl.class);
    
    private static final String strCircularList = "SELECT * FROM (SELECT CIRCULAR_SEQ_ID,CIRCULAR_NUMBER,CIRCULAR_RECEIVED_DATE,CIRCULAR_ISSUED_DATE,TITLE,TPA_INS_CIRCULARS.DESCRIPTION,DENSE_RANK() OVER (ORDER BY #)Q FROM app.TPA_INS_CIRCULARS, app.TPA_INS_PRODUCT WHERE TPA_INS_CIRCULARS.INS_SEQ_ID = ? AND TPA_INS_CIRCULARS.PRODUCT_SEQ_ID = $ AND TPA_INS_CIRCULARS.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID " ;//WHERE INS_SEQ_ID = ? and product_seq_id=$(Insurance sequence id and Product Sequence Id is required as a mandatory) 
    private static final String strAddUpdateCircularInfo = "{CALL PRODUCT_ADMIN_PKG_PR_PRODUCT_CIRCULARS_SAVE(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeleteCircularInfo = "{CALL PRODUCT_ADMIN_PKG_PR_PRODUCT_CIRCULARS_DELETE(?,?,?)}";
    
    private static final int CIRCULAR_SEQ_ID = 1;
    private static final int PRODUCT_SEQ_ID  = 2;
    private static final int INS_SEQ_ID      = 3;
    private static final int CIRCULAR_NUMBER = 4;
    private static final int CIRCULAR_RECEIVED_DATE = 5;
    private static final int CIRCULAR_ISSUED_DATE = 6;
    private static final int TITLE = 7;
    private static final int DESCRIPTION = 8;
    private static final int USER_SEQ_ID = 9;
    private static final int ROW_PROCESSED = 10;
    
    /**
     * This method returns the ArrayList, which contains the CircularVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of CircularVO'S object's which contains the Circular details
     * @exception throws TTKException
     */
    public ArrayList getCircularList(ArrayList alSearchObjects) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        String strStaticQuery = strCircularList;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        CircularVO circularVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        String strInsSeqId = "";
        String strProductSeqId = "";
        String strFromDate = "";
        String strToDate = "";
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            strInsSeqId  = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            strProductSeqId = ((SearchCriteria)alSearchObjects.get(1)).getValue();
            strFromDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(2)).getValue());
            strToDate = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(3)).getValue());
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?",strInsSeqId);
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"$",strProductSeqId);
            sbfDynamicQuery.append("AND CIRCULAR_RECEIVED_DATE BETWEEN TO_DATE( COALESCE ('"+strFromDate+"','01/01/2005'),'DD/MM/YYYY') AND  TO_DATE (COALESCE ('"+strToDate+"',TO_CHAR(CURRENT_DATE,'DD-MM-YYYY')),'DD-MM-YYYY')");
                                  //AND CIRCULAR_RECEIVED_DATE BETWEEN to_date(colaesce(TO_char('"+strFromDate+"' :: varchar,'DD/MM/YYYY'), TO_char('01/01/2005' :: varchar,'DD/MM/YYYY') ),'DD/MM/YYYY') AND  colaesce(TO_DATE('"+strToDate+"','DD/MM/YYYY'), SYSDATE)
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", TTKCommon.checkNull(((String)alSearchObjects.get(4)))+" "+TTKCommon.checkNull(((String)alSearchObjects.get(5))));
        
        //build the row numbers to be fetched  
        sbfDynamicQuery = sbfDynamicQuery .append(" )A WHERE Q >= "+TTKCommon.checkNull(((String)alSearchObjects.get(6)))+ " AND Q <= "+TTKCommon.checkNull(((String)alSearchObjects.get(7))));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    circularVO = new CircularVO();
                    
                    if(rs.getString("CIRCULAR_SEQ_ID") != null){
                    	circularVO.setCircularSeqId(new Long(rs.getLong("CIRCULAR_SEQ_ID")));
                    }//end of if(rs.getString("CIRCULAR_SEQ_ID") != null)
                    circularVO.setCircularNumber(TTKCommon.checkNull(rs.getString("CIRCULAR_NUMBER")));
                    if (rs.getDate("CIRCULAR_RECEIVED_DATE")!= null){
                    	circularVO.setRecievedDate(new java.util.Date((rs.getTimestamp("CIRCULAR_RECEIVED_DATE").getTime())));
                    }//end of if (rs.getDate("CIRCULAR_RECEIVED_DATE")!= null)
                    if (rs.getDate("CIRCULAR_RECEIVED_DATE")!= null){
                    	circularVO.setReceiveDate(new java.util.Date((rs.getTimestamp("CIRCULAR_RECEIVED_DATE").getTime())));
                    }//end of if (rs.getDate("CIRCULAR_RECEIVED_DATE")!= null)
                    if (rs.getDate("CIRCULAR_ISSUED_DATE")!= null){
                    	circularVO.setIssueDate(new java.util.Date((rs.getTimestamp("CIRCULAR_ISSUED_DATE").getTime())));
                    }//end of if (rs.getDate("CIRCULAR_ISSUED_DATE")!= null)
                    if (rs.getDate("CIRCULAR_ISSUED_DATE")!= null){
                    	circularVO.setIssuedDate(new java.util.Date((rs.getTimestamp("CIRCULAR_ISSUED_DATE").getTime())));
                    }//end of if (rs.getDate("CIRCULAR_ISSUED_DATE")!= null)
                    circularVO.setCirclarTitle(TTKCommon.checkNull(rs.getString("TITLE")));
                    circularVO.setCirclarDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    resultList.add(circularVO);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
        }//end of try
        catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "circulars");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "circulars");
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
					log.error("Error while closing the Resultset in CircularDAOImpl getCircularList()",sqlExp);
					throw new TTKException(sqlExp, "circulars");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CircularDAOImpl getCircularList()",sqlExp);
						throw new TTKException(sqlExp, "circulars");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CircularDAOImpl getCircularList()",sqlExp);
							throw new TTKException(sqlExp, "circulars");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "circulars");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCircularList(ArrayList alSearchObjects)
    
    /**
     * This method adds/updates the CircularVO which contains Circulars details
     * @param circularVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateCircular(CircularVO circularVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult = 0;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = conn.prepareCall(strAddUpdateCircularInfo);
            if(circularVO.getCircularSeqId() == null){
            	cStmtObject.setLong(CIRCULAR_SEQ_ID,0);//CIRCULAR_SEQ_ID
            }//end of if(circularVO.getCircularSeqId() == null)
            else{
            	cStmtObject.setLong(CIRCULAR_SEQ_ID,circularVO.getCircularSeqId());//CIRCULAR_SEQ_ID
            }//end of else
                
            if(circularVO.getProductSeqId() != null){
            	cStmtObject.setLong(PRODUCT_SEQ_ID,circularVO.getProductSeqId());//PRODUCT_SEQ_ID
            }//end of if(circularVO.getProductSeqId() != null)
            else{
            	cStmtObject.setString(PRODUCT_SEQ_ID,null);
            }//end of else
                
            cStmtObject.setLong(INS_SEQ_ID,circularVO.getInsSeqId());//INS_SEQ_ID
            cStmtObject.setString(CIRCULAR_NUMBER,circularVO.getCircularNumber());//CIRCULAR_NUMBER
            if(circularVO.getReceiveDate() != null){
            	cStmtObject.setTimestamp(CIRCULAR_RECEIVED_DATE,new Timestamp(circularVO.getReceiveDate().getTime()));//CIRCULAR_RECEIVED_DATE
            }//end of if(circularVO.getReceiveDate() != null)
            else{
            	cStmtObject.setTimestamp(CIRCULAR_RECEIVED_DATE, null);//CIRCULAR_RECEIVED_DATE
            }//end of else
                
            if(circularVO.getIssueDate() != null){
            	cStmtObject.setTimestamp(CIRCULAR_ISSUED_DATE,new Timestamp(circularVO.getIssueDate().getTime()));//CIRCULAR_ISSUED_DATE
            }//end of if(circularVO.getIssueDate() != null)
            else{
            	cStmtObject.setTimestamp(CIRCULAR_ISSUED_DATE, null);//CIRCULAR_ISSUED_DATE
            }//end of else
                
            cStmtObject.setString(TITLE,circularVO.getCirclarTitle());//TITLE
            cStmtObject.setString(DESCRIPTION,circularVO.getCirclarDesc());//DESCRIPTION
            cStmtObject.setLong(USER_SEQ_ID, circularVO.getUpdatedBy());//USER_SEQ_ID
            cStmtObject.registerOutParameter(CIRCULAR_SEQ_ID, Types.NUMERIC);
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.BIGINT);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = (int)cStmtObject.getLong(ROW_PROCESSED);//ROW_PROCESSED
        }//end of try
        catch (SQLException sqlExp) 
        { 
              throw new TTKException(sqlExp, "circulars");
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
              throw new TTKException(exp, "circulars");
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
        			log.error("Error while closing the Statement in CircularDAOImpl addUpdateCircular()",sqlExp);
        			throw new TTKException(sqlExp, "circulars");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CircularDAOImpl addUpdateCircular()",sqlExp);
        				throw new TTKException(sqlExp, "circulars");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "circulars");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of addUpdateCircular(CircularVO circularVO)
    
    /**
     * This method delete's the circular records from the database.  
     * @param strCircularSeqId String object which contains the circular sequence id's to be deleted
     * @param lnguserSeqID for which user has loggedin
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCircular(String strCircularSeqID,long lnguserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult =0;
        try
        {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteCircularInfo);
            cStmtObject.setString(1, strCircularSeqID);//string of sequence id's which are separated with | as separator (Note: String should also end with | at the end)  
            cStmtObject.setLong(2, lnguserSeqID);//user sequence id
            cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);
        }//end of try
        catch (SQLException sqlExp) 
        { 
              throw new TTKException(sqlExp, "circulars");
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
              throw new TTKException(exp, "circulars");
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
        			log.error("Error while closing the Statement in CircularDAOImpl deleteCircular()",sqlExp);
        			throw new TTKException(sqlExp, "circulars");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CircularDAOImpl deleteCircular()",sqlExp);
        				throw new TTKException(sqlExp, "circulars");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "circulars");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteCircular(ArrayList alCircularSeqId)
}//end of CircularDAOImpl
