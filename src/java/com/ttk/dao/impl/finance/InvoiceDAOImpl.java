/**
 *   @ (#) InvoiceDAOImpl.java Oct 24, 2007
 *   Project      : TTK HealthCare Services
 *   File         : InvoiceDAOImpl.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : Oct 24, 2007
 *
 *   @author       :  Balakrishna E
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.sun.rowset.CachedRowSetImpl;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.InvoiceBatchVO;
import com.ttk.dto.finance.InvoiceVO;

public class InvoiceDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(InvoiceDAOImpl.class);
	
	private static final String strSelectInvoicesDtlList = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_INVOICES_DTL_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectInvoiceDetail = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_INVOICES_DTL(?,?,?)}";
	private static final String strSaveInvoiceDetail = "{CALL FLOAT_ACCOUNTS_PKG.SAVE_INVOICE_DTL(?,?,?,?,?,?,?)}";
	private static final String strInvoicePolicyList = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_INV_POLICY_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strAssociatePolicies = "{CALL FLOAT_ACCOUNTS_PKG.SAVE_INV_POLICY_ASSOC(?,?,?,?,?)}";
	private static final String strAssociateAllPolicies = "{CALL FLOAT_ACCOUNTS_PKG.SAVE_INV_POLICY_ASSOC_ALL(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetInvoiceFromDate = "{CALL FLOAT_ACCOUNTS_PKG.GET_NEW_INV_FROM_DATE(?)}";
	
	private static final String strDeleteInvoice ="{CALL FLOAT_ACCOUNTS_PKG.INVOICES_DELETE(?,?,?)}";
	private static final String strViewInvoiceBatch = "{CALL FLOAT_ACCOUNTS_PKG.VIEW_INV_BATCH(?,?,?,?,?,?,?,?,?)}";
	private static final String strIndividualInvoiceList = "{CALL FLOAT_ACCOUNTS_PKG.GENERATE_IND_INVOICE_LIST(?,?,?,?,?)}";
	 
	/**
     * This method returns the ArrayList, which contains the InvoiceVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InvoiceVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
	public ArrayList getInvoiceList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		CallableStatement cStmtObject = null;
		Connection conn = null;
		ResultSet rs = null;
		InvoiceVO invoiceVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectInvoicesDtlList);
			
			cStmtObject.setString(1, (String)alSearchCriteria.get(0));
			cStmtObject.setString(2, (String)alSearchCriteria.get(1));
			cStmtObject.setString(3, (String)alSearchCriteria.get(2));
			cStmtObject.setString(4, (String)alSearchCriteria.get(3));
			cStmtObject.setString(5, (String)alSearchCriteria.get(5));
			cStmtObject.setString(6, (String)alSearchCriteria.get(6));
			cStmtObject.setString(7, (String)alSearchCriteria.get(7));
			cStmtObject.setString(8, (String)alSearchCriteria.get(8));
			cStmtObject.setLong(9, (Long)alSearchCriteria.get(4));
			cStmtObject.registerOutParameter(10, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null)
			{
				while(rs.next())
				{
					invoiceVO = new InvoiceVO();
					
					if(rs.getString("INVOICE_SEQ_ID") != null){
						invoiceVO.setSeqID(new Long(rs.getLong("INVOICE_SEQ_ID")));
					}//end of if(rs.getString("INVOICE_SEQ_ID") != null)
					
					invoiceVO.setInvoiceNbr(TTKCommon.checkNull(rs.getString("INVOICE_NUMBER")));
					
					if(rs.getTimestamp("INV_FROM_DATE") != null){
						invoiceVO.setFromDate(new Date(rs.getTimestamp("INV_FROM_DATE").getTime()));
					}//end of if(rs.getTimestamp("INV_FROM_DATE") != null)
					
					if(rs.getTimestamp("INV_TO_DATE") != null){
						invoiceVO.setToDate(new Date(rs.getTimestamp("INV_TO_DATE").getTime()));
					}//end of if(rs.getTimestamp("INV_TO_DATE") != null)
					
					invoiceVO.setStatusDesc(rs.getString("STATUS"));
					invoiceVO.setPolicyCount(TTKCommon.checkNull(rs.getString("NUMOFPOLICIES")));
					
					alResultList.add(invoiceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "invoice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "invoice");
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
					log.error("Error while closing the Resultset in InvoiceDAOImpl getInvoiceList()",sqlExp);
					throw new TTKException(sqlExp, "invoice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InvoiceDAOImpl getInvoiceList()",sqlExp);
						throw new TTKException(sqlExp, "invoice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InvoiceDAOImpl getInvoiceList()",sqlExp);
							throw new TTKException(sqlExp, "invoice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "invoice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInvoiceList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the InvoiceVO which contains the Invoice details
	 * @param lngInvoiceSeqID long value which contains invoice seq id to get the Invoice details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return InvoiceVO this contains the Invoice details
	 * @exception throws TTKException
	 */
	public InvoiceVO getInvoiceDetail(long lngInvoiceSeqID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		InvoiceVO invoiceVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectInvoiceDetail);
			
			cStmtObject.setLong(1,lngInvoiceSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					invoiceVO = new InvoiceVO();
					
					if(rs.getString("INVOICE_SEQ_ID") != null){
						invoiceVO.setSeqID(new Long(rs.getLong("INVOICE_SEQ_ID")));
					}//end of if(rs.getString("INVOICE_SEQ_ID") != null)
					
					invoiceVO.setInvoiceNbr(TTKCommon.checkNull(rs.getString("INVOICE_NUMBER")));
					
					if(rs.getTimestamp("INV_FROM_DATE") != null){
						invoiceVO.setFromDate(new Date(rs.getTimestamp("INV_FROM_DATE").getTime()));
					}//end of if(rs.getTimestamp("INV_FROM_DATE") != null)
					
					if(rs.getTimestamp("INV_TO_DATE") != null){
						invoiceVO.setToDate(new Date(rs.getTimestamp("INV_TO_DATE").getTime()));
					}//end of if(rs.getTimestamp("INV_TO_DATE") != null)
					
					invoiceVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("INV_STATUS_GENERAL_TYPE_ID")));
					invoiceVO.setIncludeOldYN(TTKCommon.checkNull(rs.getString("INCLUDE_OLD_YN")));
					
					if(rs.getString("BATCH_SEQ_ID") != null){
						invoiceVO.setBatchSeqID(new Long(rs.getLong("BATCH_SEQ_ID")));
					}//end of if(rs.getString("BATCH_SEQ_ID") != null)
				}//end of while(rs.next())
			}//end of if(rs != null)
			return invoiceVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "invoice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "invoice");
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
					log.error("Error while closing the Resultset in InvoiceDAOImpl getInvoiceDetail()",sqlExp);
					throw new TTKException(sqlExp, "invoice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InvoiceDAOImpl getInvoiceDetail()",sqlExp);
						throw new TTKException(sqlExp, "invoice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InvoiceDAOImpl getInvoiceDetail()",sqlExp);
							throw new TTKException(sqlExp, "invoice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "invoice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInvoiceDetail(long lngInvoiceSeqID,long lngUserSeqID)
	
	/**
	 * This method saves the Invoice details
	 * @param invoiceVO the object which contains Invoice details
	 * @return long contains Invoice Seq ID
	 * @exception throws TTKException
	 */
	public long saveInvoiceDetail(InvoiceVO invoiceVO) throws TTKException {
		long lngInvoiceSeqID =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveInvoiceDetail);
			
			if(invoiceVO.getSeqID() != null){
				cStmtObject.setLong(1,invoiceVO.getSeqID());
			}//end of if(invoiceVO.getSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			
			if(invoiceVO.getFromDate() != null){
				cStmtObject.setTimestamp(2,new Timestamp(invoiceVO.getFromDate().getTime()));
			}//end of if(invoiceVO.getFromDate() != null)
			else {
				cStmtObject.setTimestamp(2,null);
			}//end of else
			
			if(invoiceVO.getToDate() != null){
				cStmtObject.setTimestamp(3,new Timestamp(invoiceVO.getToDate().getTime()));
			}//end of if(invoiceVO.getToDate() != null)
			else {
				cStmtObject.setTimestamp(3,null);
			}//end of else
			
			cStmtObject.setString(4,invoiceVO.getStatusTypeID());
			cStmtObject.setString(5,invoiceVO.getIncludeOldYN());
			cStmtObject.setLong(6,invoiceVO.getUpdatedBy());
			cStmtObject.registerOutParameter(7,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//INVOICE_SEQ_ID
			cStmtObject.execute();
			lngInvoiceSeqID = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "invoice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "invoice");
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
        			log.error("Error while closing the Statement in InvoiceDAOImpl saveInvoiceDetail()",sqlExp);
        			throw new TTKException(sqlExp, "invoice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in InvoiceDAOImpl saveInvoiceDetail()",sqlExp);
        				throw new TTKException(sqlExp, "invoice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "invoice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngInvoiceSeqID;
	}//end of saveInvoiceDetail(InvoiceVO invoiceVO)
	
	/**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param strPolicySeqID String which contains Policy Seq ID
     * @param strEnrollType String which contains Enrollment Type
     * @param lngAddedBy Long which contains Added User Seq ID
     * @return ArrayList of InviceVO's object's which contains the details of the Invice
     * @exception throws TTKException
     */
    public TTKReportDataSource getGenerateInvoiceList(String strPolicySeqID,String strEnrollType,Long lngAddedBy) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        TTKReportDataSource reportDataSource =null;
        CachedRowSet crs = null;
        Date dtDate = new Date();
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strIndividualInvoiceList);
            cStmtObject.setString(1,strPolicySeqID);
            cStmtObject.setString(2,strEnrollType);
            cStmtObject.setTimestamp(3,new Timestamp(dtDate.getTime()));
            cStmtObject.setLong(4,lngAddedBy);
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            crs = new CachedRowSetImpl();
            if(rs != null){
                reportDataSource = new TTKReportDataSource();
                crs.populate(rs);
                reportDataSource.setData(crs);
            }//end of if(rs != null)
            return reportDataSource;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "invoice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "invoice");
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
					log.error("Error while closing the Resultset in InvoiceDAOImpl getGenerateInvoiceList()",sqlExp);
					throw new TTKException(sqlExp, "invoice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InvoiceDAOImpl getGenerateInvoiceList()",sqlExp);
						throw new TTKException(sqlExp, "invoice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InvoiceDAOImpl getGenerateInvoiceList()",sqlExp);
							throw new TTKException(sqlExp, "invoice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "invoice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGenerateInvoiceList(String strPolicySeqID)
	
	/**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InviceVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getInvoicePolicyList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        InvoiceVO invoiceVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInvoicePolicyList);
            
            if(alSearchCriteria.get(0)!=null){
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if(alSearchCriteria.get(0)!=null)    
            else{
                cStmtObject.setString(1,null);
            }//end of else
            
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            
            if(alSearchCriteria.get(5) != null){
            	cStmtObject.setLong(6,(Long)alSearchCriteria.get(5));
            }//end of if(alSearchCriteria.get(5) != null)
            else{
            	cStmtObject.setString(6,null);
            }//end of else
            
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));
            cStmtObject.setLong(10,(Long)alSearchCriteria.get(9));
            cStmtObject.setString(11,(String)alSearchCriteria.get(10));
            cStmtObject.setLong(12,(Long)alSearchCriteria.get(11));
            cStmtObject.setString(13,(String)alSearchCriteria.get(12));
            cStmtObject.setString(14,(String)alSearchCriteria.get(13));
            cStmtObject.setString(15,(String)alSearchCriteria.get(14));
            cStmtObject.setString(16,(String)alSearchCriteria.get(15));
            cStmtObject.registerOutParameter(17,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(17);

            if(rs != null){
                while(rs.next()){
                	invoiceVO = new InvoiceVO();
                	
                	invoiceVO.setPolicySeqID(rs.getString("FIN_POLICY_SEQ_ID")!=null ? new Long(rs.getLong("FIN_POLICY_SEQ_ID")):null);
                	invoiceVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                	invoiceVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                	invoiceVO.setTotalPremium(rs.getString("NET_PREMIUM")!=null ? new BigDecimal(rs.getString("NET_PREMIUM")):null);
                	invoiceVO.setCommision(rs.getString("COMMISSION")!=null ? new BigDecimal(rs.getString("COMMISSION")):new BigDecimal(0));
                	invoiceVO.setCommissionAmt(rs.getString("COMMISSION_AMT")!=null ? new BigDecimal(rs.getString("COMMISSION_AMT")):new BigDecimal(0));
                	
                	alResultList.add(invoiceVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "invoice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "invoice");
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
					log.error("Error while closing the Resultset in InvoiceDAOImpl getInvoicePolicyList()",sqlExp);
					throw new TTKException(sqlExp, "invoice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InvoiceDAOImpl getInvoicePolicyList()",sqlExp);
						throw new TTKException(sqlExp, "invoice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InvoiceDAOImpl getInvoicePolicyList()",sqlExp);
							throw new TTKException(sqlExp, "invoice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "invoice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getInvoicePolicyList(ArrayList alSearchCriteria)
    
    /**
	 * This method Associates Policies to the Invoice
	 * @param alPolicyList the object which contains the details of the Policies
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associatePolicy(ArrayList alPolicyList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociatePolicies);
			
			if(alPolicyList != null){
				
				cStmtObject.setLong(1,(Long)alPolicyList.get(0));
				cStmtObject.setString(2,(String)alPolicyList.get(1));
				cStmtObject.setString(3,(String)alPolicyList.get(2));
				cStmtObject.setLong(4,(Long)alPolicyList.get(3));
				cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
				cStmtObject.execute();
				iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
			}//end of if(alPolicyList != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "invoice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "invoice");
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
        			log.error("Error while closing the Statement in InvoiceDAOImpl associatePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "invoice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in InvoiceDAOImpl associatePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "invoice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "invoice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of associatePolicy(ArrayList alPolicyList)
	
	/**
	 * This method Associates/Excludes all the Policies to/from the Invoice
	 * @param alPolicyList the object which contains the details of the Policies
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associateAll(ArrayList alPolicyList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateAllPolicies);
			
			if(alPolicyList.get(0) != null){
				cStmtObject.setLong(1,(Long)alPolicyList.get(0));
			}//end of if(alPolicyList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,(String)alPolicyList.get(1));
			cStmtObject.setString(3,(String)alPolicyList.get(2));
			cStmtObject.setString(4,(String)alPolicyList.get(3));
			cStmtObject.setString(5,(String)alPolicyList.get(4));
			
			if(alPolicyList.get(5) != null){
				cStmtObject.setLong(6,(Long)alPolicyList.get(5));
			}//end of if(alPolicyList.get(5) != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else
			
			cStmtObject.setString(7,(String)alPolicyList.get(6));
			cStmtObject.setString(8,(String)alPolicyList.get(7));
			cStmtObject.setString(9,(String)alPolicyList.get(8));
			cStmtObject.setLong(10,(Long)alPolicyList.get(9));
			cStmtObject.setString(11,(String)alPolicyList.get(10));
			cStmtObject.setLong(12,(Long)alPolicyList.get(11));
			cStmtObject.registerOutParameter(13, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(13);//ROWS_PROCESSED
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "invoice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "invoice");
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
        			log.error("Error while closing the Statement in InvoiceDAOImpl associateAll()",sqlExp);
        			throw new TTKException(sqlExp, "invoice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in InvoiceDAOImpl associateAll()",sqlExp);
        				throw new TTKException(sqlExp, "invoice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "invoice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of associateAll(ArrayList alPolicyList)
	
	/**
	 * This method returns the Max of Invoice to Date
	 * @return date the value which contains the Max of Invoice to Date
	 * @exception throws TTKException
	 */
	public Date getNewInvoiceFromDate() throws TTKException {
		Date dtInvoiceFromDate = new Date();
		Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetInvoiceFromDate);
            cStmtObject.registerOutParameter(1,OracleTypes.DATE);
            cStmtObject.execute();
            dtInvoiceFromDate = cStmtObject.getDate(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return dtInvoiceFromDate;
	}//end of getNewInvoiceFromDate()

    /**
     * This method returns the ArrayList, which contains the InvoiceBatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InvoiceBatchVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getViewInvoiceBatch(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        InvoiceBatchVO invoiceBatchVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strViewInvoiceBatch);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setLong(4,(Long)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(9);

            if(rs != null){
                while(rs.next()){
                    invoiceBatchVO = new InvoiceBatchVO();
                    invoiceBatchVO.setSeqID(rs.getString("BATCH_SEQ_ID")!=null ? new Long(rs.getLong("BATCH_SEQ_ID")):null);
                    invoiceBatchVO.setBatchDate(rs.getString("BATCH_DATE")!=null ? new Date(rs.getTimestamp("BATCH_DATE").getTime()):null);
                    invoiceBatchVO.setNbrPolicy(TTKCommon.checkNull(rs.getString("NO_OF_POLICIES")));
                    invoiceBatchVO.setBatchName(TTKCommon.checkNull(rs.getString("BATCH_NAME")));
                    alResultList.add(invoiceBatchVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getViewInvoiceBatch(ArrayList alSearchCriteria)

    /**
     * This method deletes the Invoice Detail information from the database
     * @param alDeleteList ArrayList object which contains seq id for Invoice flow to delete the Invoice information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteInvoice(ArrayList alDeleteList) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            if(alDeleteList != null && alDeleteList.size() > 0){
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteInvoice);
                cStmtObject.setString(1,(String)alDeleteList.get(0));
                cStmtObject.setLong(2,(Long)alDeleteList.get(1));
                cStmtObject.registerOutParameter(3,Types.INTEGER);
                cStmtObject.execute();
                iResult = cStmtObject.getInt(3);
            }//end of if(alDeleteList != null && alDeleteList.size() > 0)

        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl deleteInvoice()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl deleteInvoice()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteInvoice(ArrayList alDeleteList)
	
//	public static void main(String a[]) throws Exception{
		//InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
		/*ArrayList<Object> alSearchCriteria = new ArrayList<Object>();
		invoiceDAO.getInvoiceList(alSearchCriteria);*/
		
		/*InvoiceVO invoiceVO = new InvoiceVO();
		//Long lngInvoiceSeqID = null;
		//invoiceVO.setSeqID(lngInvoiceSeqID);
		//invoiceVO.setFromDate(new Date("10/01/2007"));
		//invoiceVO.setToDate(new Date("10/31/2007"));
		invoiceVO.setStatusTypeID("DDT");
		invoiceVO.setIncludeOldYN("N");
		invoiceVO.setUpdatedBy(new Long(56503));
		invoiceDAO.saveInvoiceDetail(invoiceVO);*/
		
		//invoiceDAO.getInvoiceDetail(new Long(1),new Long(56503));
		
		/*ArrayList<Object> alSearchCriteria = new ArrayList<Object>();
		Long lngInsSeqID = null;
		alSearchCriteria.add(lngInsSeqID);
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		alSearchCriteria.add(new Long(14));
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("DBA");
		alSearchCriteria.add(new Long(1));
		alSearchCriteria.add("N");
		alSearchCriteria.add(new Long(56503));
		alSearchCriteria.add("policy_number");
		alSearchCriteria.add("asc");
		alSearchCriteria.add("1");
		alSearchCriteria.add("100");
		invoiceDAO.getInvoicePolicyList(alSearchCriteria);*/
		
		/*ArrayList<Object> alPolicyList = new ArrayList<Object>();
		alPolicyList.add(new Long(1));
		//alPolicyList.add("|2582|2583|");
		//alPolicyList.add("DBA");
		alPolicyList.add("|2582|");
		alPolicyList.add("DBU");
		alPolicyList.add(new Long(56503));
		invoiceDAO.associatePolicy(alPolicyList);*/
		
		/*ArrayList<Object> alSearchCriteria = new ArrayList<Object>();
		Long lngInsSeqID = null;
		Long lngOfficeSeqID = null;
		alSearchCriteria.add(lngInsSeqID);
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		alSearchCriteria.add("Y");
		//alSearchCriteria.add(new Long(14));
		alSearchCriteria.add(lngOfficeSeqID);
		alSearchCriteria.add("01/11/2007");
		alSearchCriteria.add("03/11/2007");
		alSearchCriteria.add("DBU");
		alSearchCriteria.add(new Long(121));
		alSearchCriteria.add("N");
		alSearchCriteria.add(new Long(56503));
		invoiceDAO.associateAll(alSearchCriteria);*/
		
		//invoiceDAO.getNewInvoiceFromDate();
//	}//end of main
}//end of InvoiceDAOImpl
