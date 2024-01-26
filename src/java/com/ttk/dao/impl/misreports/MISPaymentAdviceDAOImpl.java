
/**
  * @ (#) MISPaymentAdviceDAOImpl.java
  * Project      : TTK HealthCare Services
  * File         : MISPaymentAdviceDAOImpl.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : January 15,2009
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.dao.impl.misreports;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.rowset.OracleCachedRowSet;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ReportResourceManager;
import com.ttk.dto.finance.ChequeVO;

public class MISPaymentAdviceDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(MISPaymentAdviceDAOImpl.class );

	private static final String strViewPaymentAdviceList = "{CALL MIS_REPORTS.VIEW_PAYMENT_ADVICE(?,?,?,?,?,?,?)}";
	private static String strPAYMENT_ADVICE_REPORT="MIS_REPORTS.PAYMENT_ADVICE_REPORT";
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getViewPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException {
		log.info("Inside MISPaymentAdviceDAOImpl class getViewPaymentAdviceList method");
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		try{
			//conn = ResourceManager.getConnection();
			conn = ReportResourceManager.getReportConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strViewPaymentAdviceList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);

			if(rs != null){
				while(rs.next()){
					chequeVO = new ChequeVO();

					if(rs.getString("BATCH_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("BATCH_SEQ_ID")));
					}//end of if(rs.getString("BATCH_SEQ_ID") != null)

					if(rs.getTimestamp("BATCH_DATE") != null){
						chequeVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
					}//end of if(rs.getTimestamp("BATCH_DATE") != null)

					chequeVO.setBatchNumber(TTKCommon.checkNull(rs.getString("FILE_NAME")));

					if(rs.getString("BANK_NAME").equals("CITIBANK")){
						chequeVO.setBankName("CITI");
					}//end of if(rs.getString("BANK_NAME").equals("CITIBANK"))
					else if(rs.getString("BANK_NAME").equals("UTIBANK")){
						chequeVO.setBankName("UTI");
					}//end of if(rs.getString("BANK_NAME").equals("UTIBANK"))
					else if(rs.getString("BANK_NAME").equals("ICICIBANK")){
						chequeVO.setBankName("ICICI");
					}//end of if(rs.getString("BANK_NAME").equals("ICICIBANK"))
					else if(rs.getString("BANK_NAME").equals("HDFCBANK")){
						chequeVO.setBankName("HDFC");
					}//end of if(rs.getString("BANK_NAME").equals("HDFCBANK"))

					chequeVO.setAddedBy(new Long(rs.getLong("ADDED_BY")));
					alResultList.add(chequeVO);
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
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
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
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
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
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
	}//end of getViewPaymentAdviceList(ArrayList alSearchCriteria)

	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReports(String strParameter) throws TTKException {
    	log.info("Inside MISPaymentAdviceDAOImpl class getReports method");
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        OracleCachedRowSet crs = null;
        try{
        	String strCall = "{CALL "+strPAYMENT_ADVICE_REPORT.trim()+"(?,?)}";
            crs = new OracleCachedRowSet();
            conn = ReportResourceManager.getReportConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,strParameter);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(2);

            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Statement in MisReportDAOImpl getReport()",sqlExp);
        			throw new TTKException(sqlExp, "tTkReports");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MisReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of getReports(HashMap hMap)



}//end of MISPaymentAdviceDAOImpl
