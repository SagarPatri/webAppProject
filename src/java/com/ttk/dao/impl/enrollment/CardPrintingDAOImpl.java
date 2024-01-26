/**
 * @ (#) CardPrintingDAOImpl.java Feb 21, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CardPrintingDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 21, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.enrollment;

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

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.BatchVO;


public class CardPrintingDAOImpl implements BaseDAO, Serializable{
	
	private static Logger log = Logger.getLogger(CardPrintingDAOImpl.class);

    private static final String strCardPrintingList = "{CALL CARD_BATCH_PKG.SELECT_PRINT_CARD_LIST(?,?,?,?,?,?,?,?,?)}";
    private static final String strCreateCardBatch = "{CALL CARD_BATCH_PKG.SAVE_CARD_BATCH(?,?,?,?,?,?,?,?)}";
    private static final String strSetcardBatch = "{CALL CARD_BATCH_PKG.SET_CARD_BATCH(?,?,?,?)}";
    private static final String strCardBatchDetail = "SELECT A.card_rule,A.card_template,B.DESCRIPTION card_type FROM tpa_card_batch A,tpa_general_code B WHERE A.card_batch_seq_id = ? AND A.CARD_TYPE_ID = B.GENERAL_TYPE_ID AND B.HEADER_TYPE = 'CARD_TYPE'";

    private static final int INS_SEQ_ID = 1;
    private static final int TPA_OFFICE_SEQ_ID = 2;
    private static final int GROUP_REG_SEQ_ID = 3;
    private static final int POLICY_AGENT_CODE = 4;
    private static final int PRODUCT_SEQ_ID = 5;
    private static final int ENROL_TYPE_ID = 6;
    private static final int USER_SEQ_ID = 7;
    private static final int ROW_PROCESSED = 8;

    /**
     * This method returns the ArrayList, which contains the BatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BatchVO'S object's which contains the details of the Card Printing
     * @exception throws TTKException
     */
    public ArrayList getCardPrintingList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        BatchVO batchVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCardPrintingList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(4));
            cStmtObject.setString(5,(String)alSearchCriteria.get(5));
            cStmtObject.setString(6,(String)alSearchCriteria.get(6));
            cStmtObject.setString(7,(String)alSearchCriteria.get(7));
            cStmtObject.setLong(8,(Long)alSearchCriteria.get(3));
            cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(9);
            if(rs != null){
                while(rs.next()){
                    batchVO = new BatchVO();
                    batchVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NO")));
                    batchVO.setCompanyName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));

                    if(rs.getString("CARD_BATCH_SEQ_ID") != null){
                        batchVO.setBatchSeqID(new Long(rs.getLong("CARD_BATCH_SEQ_ID")));
                    }//end of if(rs.getString("CARD_BATCH_SEQ_ID") != null)

                    if(rs.getString("BATCH_DATE") != null){
                    	batchVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
                    }//end of if(rs.getString("BATCH_DATE") != null)

                    batchVO.setCardTypeID(TTKCommon.checkNull(rs.getString("CARD_TYPE_ID")));
                    if(rs.getString("ENROL_TYPE_ID").equals("COR")){
                    	batchVO.setSendMailName("shortfall");
                    	batchVO.setSendMailTitle("Send Mail");
                    }//end of if(rs.getString("ENROL_TYPE_ID").equals("COR"))
                    alResultList.add(batchVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "cardPrint");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "cardPrint");
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
					log.error("Error while closing the Resultset in CardPrintingDAOImpl getCardPrintingList()",sqlExp);
					throw new TTKException(sqlExp, "cardPrint");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CardPrintingDAOImpl getCardPrintingList()",sqlExp);
						throw new TTKException(sqlExp, "cardPrint");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CardPrintingDAOImpl getCardPrintingList()",sqlExp);
							throw new TTKException(sqlExp, "cardPrint");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "cardPrint");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCardPrintingList(ArrayList alSearchCriteria)

    /**
     * This method creates Card Batch
     * @param batchVO the object which contains the Card Batch Details to Create a Card Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int createCardBatch(BatchVO batchVO) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCreateCardBatch);
            if(batchVO.getInsuranceSeqID() != null){
                cStmtObject.setLong(INS_SEQ_ID,batchVO.getInsuranceSeqID());
            }//end of if(batchVO.getInsuranceSeqID() != null)
            else{
                cStmtObject.setString(INS_SEQ_ID,null);
            }//end of else

            if(batchVO.getOfficeSeqID() != null){
                cStmtObject.setLong(TPA_OFFICE_SEQ_ID,batchVO.getOfficeSeqID());
            }//end of if(batchVO.getOfficeSeqID() != null)
            else{
                cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
            }//end of else

            if(batchVO.getGroupRegnSeqID() != null){
                cStmtObject.setLong(GROUP_REG_SEQ_ID,batchVO.getGroupRegnSeqID());
            }//end of if(batchVO.getGroupRegnSeqID() != null)
            else{
                cStmtObject.setString(GROUP_REG_SEQ_ID,null);
            }//end of else

            cStmtObject.setString(POLICY_AGENT_CODE,batchVO.getAgentCode());

            if(batchVO.getProductSeqID() != null){
                cStmtObject.setLong(PRODUCT_SEQ_ID,batchVO.getProductSeqID());
            }//end of if(batchVO.getProductSeqID() != null)
            else{
                cStmtObject.setString(PRODUCT_SEQ_ID,null);
            }//end of else

            //cStmtObject.setString(BATCH_MODE,batchVO.getBatchMode());
            cStmtObject.setString(ENROL_TYPE_ID,batchVO.getEnrolTypeID());
            cStmtObject.setLong(USER_SEQ_ID,batchVO.getUpdatedBy());
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(ROW_PROCESSED);//ROW_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "cardPrint");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "cardPrint");
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
        			log.error("Error while closing the Statement in CardPrintingDAOImpl createCardBatch()",sqlExp);
        			throw new TTKException(sqlExp, "cardPrint");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CardPrintingDAOImpl createCardBatch()",sqlExp);
        				throw new TTKException(sqlExp, "cardPrint");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "cardPrint");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of createCardBatch(BatchVO batchVO)

    /**
     * This method Sets Card Batch
     * @param strCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @param lngUserSeqID Long, which user has logged in
     * @param strFlag for identifying the Mode  'PL' - Print Complete, GL - Generate Labels , CB - Cancel Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int setCardBatch(String strCardbatchSeqID,Long lngUserSeqID,String strFlag) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSetcardBatch);
            cStmtObject.setString(1,strCardbatchSeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.setString(3,strFlag);
            cStmtObject.registerOutParameter(4,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(4);//ROW_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "cardPrint");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "cardPrint");
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
        			log.error("Error while closing the Statement in CardPrintingDAOImpl setCardBatch()",sqlExp);
        			throw new TTKException(sqlExp, "cardPrint");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CardPrintingDAOImpl setCardBatch()",sqlExp);
        				throw new TTKException(sqlExp, "cardPrint");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "cardPrint");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of setCardBatch(String strCardbatchSeqID,Long lngUserSeqID,String strFlag)

    /**
     * This method gets Card Batch Detail
     * @param lngCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @return ArrayList which contains detail of card batch
     * @exception throws TTKException
     */
    public ArrayList getCardBatchDetail(Long lngCardbatchSeqID) throws TTKException{
            Connection conn = null;
            PreparedStatement pStmt = null;
            ResultSet rs = null;
            ArrayList<Object> alCardDetail = null;
            try{
                conn = ResourceManager.getConnection();
                pStmt = conn.prepareStatement(strCardBatchDetail);
                pStmt.setLong(1,lngCardbatchSeqID);
                rs = pStmt.executeQuery();
                if (rs!=null)
                {
                    alCardDetail= new ArrayList<Object>();
                    while(rs.next()) {
                        alCardDetail.add(TTKCommon.checkNull(rs.getString("CARD_RULE")));
                        alCardDetail.add(TTKCommon.checkNull(rs.getString("CARD_TEMPLATE")));
                        alCardDetail.add(TTKCommon.checkNull(rs.getString("CARD_TYPE")));
                    }//end of while(rs.next())
                }//end of if (rs!=null)
            }//end of try
            catch (SQLException sqlExp)
            {
                throw new TTKException(sqlExp, "cardPrint");
            }//end of catch (SQLException sqlExp)
            catch (Exception exp)
            {
                throw new TTKException(exp, "cardPrint");
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
    					log.error("Error while closing the Resultset in CardPrintingDAOImpl getCardBatchDetail()",sqlExp);
    					throw new TTKException(sqlExp, "cardPrint");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
    				{
    					try
    					{
    						if (pStmt != null) pStmt.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Statement in CardPrintingDAOImpl getCardBatchDetail()",sqlExp);
    						throw new TTKException(sqlExp, "cardPrint");
    					}//end of catch (SQLException sqlExp)
    					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    					{
    						try
    						{
    							if(conn != null) conn.close();
    						}//end of try
    						catch (SQLException sqlExp)
    						{
    							log.error("Error while closing the Connection in CardPrintingDAOImpl getCardBatchDetail()",sqlExp);
    							throw new TTKException(sqlExp, "cardPrint");
    						}//end of catch (SQLException sqlExp)
    					}//end of finally Connection Close
    				}//end of finally Statement Close
    			}//end of try
    			catch (TTKException exp)
    			{
    				throw new TTKException(exp, "cardPrint");
    			}//end of catch (TTKException exp)
    			finally // Control will reach here in anycase set null to the objects 
    			{
    				rs = null;
    				pStmt = null;
    				conn = null;
    			}//end of finally
    		}//end of finally
            return alCardDetail;
        }//end of getCardBatchDetail(String strCardbatchSeqID)
}//end of CardPrintingDAOImpl
