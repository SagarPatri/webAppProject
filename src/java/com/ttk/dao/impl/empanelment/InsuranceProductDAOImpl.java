/**
 * @ (#) InsuranceCompanyProductImpl.java Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceCompanyProductImpl.java
 * Author       : Ravindra
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
 *
 * @author       :  Ravindra

 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.ttk.dto.empanelment.InsuranceProductVO;

public class InsuranceProductDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(InsuranceProductDAOImpl.class);

	private static final int  ASSOC_OFF_SEQ_ID = 1 ;
	private static final int  PRODUCT_SEQ_ID  =2;
	private static final int  INS_SEQ_ID = 3 ;
	private static final int  ENROL_TYPE_ID = 4;
	private static final int  COMMISSION_TO_TPA= 5 ;
	private static final int  VALID_FROM = 6 ;
	private static final int  VALID_TO = 7 ;
	private static final int USER_SEQ_ID = 8;
	private static final int  ROW_PROCESSED = 9;

	private static final String strProductList="SELECT * FROM (SELECT ASSOC_OFF_SEQ_ID,TPA_INS_INFO.INS_SEQ_ID,TPA_INS_ASSOC_OFF_PRODUCT.PRODUCT_SEQ_ID,PRODUCT_NAME,COMMISSION_TO_TPA, TPA_INS_ASSOC_OFF_PRODUCT.ENROL_TYPE_ID,ENROL_DESCRIPTION,VALID_FROM, VALID_TO,DENSE_RANK() OVER (ORDER BY #)Q FROM APP.TPA_INS_INFO,APP.TPA_INS_ASSOC_OFF_PRODUCT, APP.TPA_INS_PRODUCT,APP.TPA_ENROLMENT_TYPE_CODE WHERE TPA_INS_INFO.INS_SEQ_ID = TPA_INS_PRODUCT.INS_SEQ_ID AND TPA_INS_ASSOC_OFF_PRODUCT.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID AND TPA_INS_ASSOC_OFF_PRODUCT.ENROL_TYPE_ID = TPA_ENROLMENT_TYPE_CODE.ENROL_TYPE_ID AND TPA_INS_ASSOC_OFF_PRODUCT.INS_SEQ_ID = ?";
	private static final String strProductCode="SELECT PRODUCT_SEQ_ID,PRODUCT_NAME FROM APP.TPA_INS_PRODUCT WHERE INS_SEQ_ID=?";
	private static final String strProductDetails = "SELECT ASSOC_OFF_SEQ_ID, PRODUCT_SEQ_ID, COMMISSION_TO_TPA, ENROL_TYPE_ID,VALID_FROM, VALID_TO FROM APP.TPA_INS_ASSOC_OFF_PRODUCT WHERE ASSOC_OFF_SEQ_ID = ?";
	private static final String strAddUpdateInsuranceProduct="{CALL INSCOMP_EMPANEL_PKG_PR_INS_ASSOC_OFF_PRODUCT_SAVE(?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteInsuranceProduct="{CALL INSCOMP_EMPANEL_PKG_PR_INS_ASSOC_OFF_PROD_DELETE (?,?,?)}";

	/**
	 * This method returns the ArrayList, which contains the InsuranceCompanyProductVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompanyProductVO object's which contains the InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getProductList(ArrayList alSearchObjects)  throws TTKException
	{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strProductList;
		String strInsSeqId  ="";
		InsuranceProductVO insuranceProductVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			strInsSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?",strInsSeqId);
			for(int i=1; i < alSearchObjects.size()-4; i++)
			{
				sbfDynamicQuery = sbfDynamicQuery.append( " AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
			}//end of for()
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		//build the Order By Clause
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		//build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
	//	System.out.println("Product List Query..."+strStaticQuery);

		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
				//	System.out.println("Test......");
					insuranceProductVO = new InsuranceProductVO();
					if (rs.getString("ASSOC_OFF_SEQ_ID")!= null)
					{
						insuranceProductVO.setAssocOffSeqId(new Long(rs.getString("ASSOC_OFF_SEQ_ID")));
					}//end of if (rs.getString("ASSOC_OFF_SEQ_ID")!= null)
					if (rs.getString("PRODUCT_SEQ_ID")!= null)
					{
						insuranceProductVO.setProductID(new Long(rs.getString("PRODUCT_SEQ_ID")));
					}//end of if (rs.getString("PRODUCT_SEQ_ID")!= null)
					insuranceProductVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
					if (rs.getString("COMMISSION_TO_TPA")!= null)
					{
						insuranceProductVO.setCommissionPercentage(new BigDecimal(rs.getString("COMMISSION_TO_TPA")));
					}//end of if (rs.getString("COMMISSION_TO_TPA")!= null)
					insuranceProductVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					insuranceProductVO.setEnrollmentDesc(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
					if(rs.getTimestamp("VALID_FROM") != null)
					{
						insuranceProductVO.setStartDate(new java.util.Date(rs.getTimestamp("VALID_FROM").getTime()));
					}//end of if(rs.getTimestamp("VALID_FROM") != null)
					if(rs.getTimestamp("VALID_TO") != null)
					{
						insuranceProductVO.setEndDate(new java.util.Date(rs.getTimestamp("VALID_TO").getTime()));
					}//end of if(rs.getTimestamp("VALID_TO") != null)
					resultList.add(insuranceProductVO);
				}// End of while (rs.next())
			}// End of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in InsuranceProductDAOImpl getProductList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsuranceProductDAOImpl getProductList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InsuranceProductDAOImpl getProductList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of  getProductList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList object of ProductVO's, which contains all the details about the products Associated to parent Insurance Company
	 * @param lInsSeqId long Insurance Sequence Id
	 * @return ArrayList object which contains all the details about the Insurance Company Associated products
	 * @exception throws TTKException
	 */
	public ArrayList getProductCode(long lInsSeqId) throws TTKException{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList<Object> alProductCode = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{
			conn = ResourceManager.getConnection();
			pStmt=conn.prepareStatement(strProductCode);
			pStmt.setLong(1,lInsSeqId);
			rs = pStmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId((rs.getString("PRODUCT_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
					alProductCode.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return alProductCode;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in InsuranceProductDAOImpl getProductCode()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsuranceProductDAOImpl getProductCode()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InsuranceProductDAOImpl getProductCode()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of  getProductCode(long lInsSeqId)

	/**
	 * This method returns the Associated Product Details
	 * @param lngAssociateOfficeSequenceID the associated Office Sequence ID for which the product details are required
	 * @return InsuranceProductVO which contains the Associated Product Details
	 * @exception throws TTKException
	 */
	public InsuranceProductVO getProductDetails(Long  lngAssociateOfficeSequenceID) throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsuranceProductVO insuranceProductVO = new InsuranceProductVO();
		try{
			conn = ResourceManager.getConnection();
			pStmt = (PreparedStatement)conn.prepareStatement(strProductDetails);
		//	System.out.println("fjughvk"+lngAssociateOfficeSequenceID);
			pStmt.setLong(1,lngAssociateOfficeSequenceID);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					insuranceProductVO.setAssocOffSeqId(rs.getString("ASSOC_OFF_SEQ_ID")!= null ? new Long(rs.getLong("ASSOC_OFF_SEQ_ID")):null);
					insuranceProductVO.setProductID(rs.getString("PRODUCT_SEQ_ID")!= null ? new Long(rs.getString("PRODUCT_SEQ_ID")):null);
					insuranceProductVO.setCommissionPercentage(rs.getString("COMMISSION_TO_TPA")!= null ? new BigDecimal(rs.getString("COMMISSION_TO_TPA")):null);
					insuranceProductVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					insuranceProductVO.setStartDate(rs.getTimestamp("VALID_FROM") != null ? new java.util.Date(rs.getTimestamp("VALID_FROM").getTime()):null);
					insuranceProductVO.setEndDate(rs.getTimestamp("VALID_TO") != null ? new java.util.Date(rs.getTimestamp("VALID_TO").getTime()):null);
				//	System.out.println("Office   "+insuranceProductVO.getAssocOffSeqId());
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return insuranceProductVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in InsuranceProductDAOImpl getConfigurationProperties()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in InsuranceProductDAOImpl getConfigurationProperties()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in InsuranceProductDAOImpl getConfigurationProperties()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getConfigurationProperties(ArrayList arrayList)

	/**
	 * This method adds or updates the insurance Products
	 * The method also calls other methods on DAO to insert/update the insurance details to the database
	 * @param insuranceProductVO InsuranceProductVO object which contains the insuranceCompanyProduct details to be added/updated
	 * @return long value, Associate Office Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateProduct(InsuranceProductVO insuranceProductVO) throws TTKException{
		long lResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateInsuranceProduct);
			
			cStmtObject.setLong(ASSOC_OFF_SEQ_ID,insuranceProductVO.getAssocOffSeqId()!=null ? insuranceProductVO.getAssocOffSeqId():0);

		//	System.out.println("ASSOC_OFF_SEQ_ID    "+insuranceProductVO.getAssocOffSeqId());

			cStmtObject.setLong(PRODUCT_SEQ_ID, insuranceProductVO.getProductID());
			if (insuranceProductVO.getInsuranceSeqID() != null)
			{
				cStmtObject.setLong(INS_SEQ_ID,insuranceProductVO.getInsuranceSeqID());
			}//end of if (insuranceProductVO.getInsuranceSeqID() != null)
			else
			{
				cStmtObject.setLong(INS_SEQ_ID,0);
			}//end of else
			cStmtObject.setString(ENROL_TYPE_ID,TTKCommon.checkNull(insuranceProductVO.getEnrollmentType()));
			cStmtObject.setBigDecimal(COMMISSION_TO_TPA,insuranceProductVO.getCommissionPercentage());
			if(insuranceProductVO.getStartDate() != null && insuranceProductVO.getFormattedStartDate() != "")
			{
				cStmtObject.setTimestamp(VALID_FROM,new Timestamp(TTKCommon.getUtilDate(insuranceProductVO.getFormattedStartDate()).getTime()));
			}//end of if(insuranceProductVO.getStartDate() != null && insuranceProductVO.getFormattedStartDate() != "")
			else
			{
				cStmtObject.setTimestamp(VALID_FROM,null);
			}//end of else
			if(insuranceProductVO.getFormattedEndDate() != null && insuranceProductVO.getFormattedEndDate() != "")
			{
				cStmtObject.setTimestamp(VALID_TO,new Timestamp(TTKCommon.getUtilDate(insuranceProductVO.getFormattedEndDate()).getTime()));
			}//end of if(insuranceProductVO.getFormattedEndDate() != null && insuranceProductVO.getFormattedEndDate() != "")
			else
			{
				cStmtObject.setTimestamp(VALID_TO,null);
			}//end of else
			cStmtObject.setLong(USER_SEQ_ID,insuranceProductVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.registerOutParameter(ASSOC_OFF_SEQ_ID,Types.BIGINT);
         	cStmtObject.registerOutParameter(PRODUCT_SEQ_ID, Types.BIGINT);
         	cStmtObject.registerOutParameter(INS_SEQ_ID, Types.BIGINT);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(ASSOC_OFF_SEQ_ID);
			conn.commit();

		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
        			log.error("Error while closing the Statement in InsuranceProductDAOImpl addUpdateProduct()",sqlExp);
        			throw new TTKException(sqlExp, "insurance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in InsuranceProductDAOImpl addUpdateProduct()",sqlExp);
        				throw new TTKException(sqlExp, "insurance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  lResult;
	}//end of addUpdateProduct(InsuranceProductVO insuranceProductVO)

	/**
	 * This method delete's the Products  records from the database.
	 * @param alProductList ArrayList object which contains the Product sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int  deleteProduct(ArrayList alProductList)  throws TTKException{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try
		{
			if(alProductList != null && alProductList.size() > 0)
			{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteInsuranceProduct);
				//System.out.println("sequence id..."+alProductList);
				cStmtObject.setString(1, (String)alProductList.get(0));//string of sequence id's which are separated with | as separator
				cStmtObject.setLong(2, (Long)alProductList.get(1));//user sequence id
				cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
				cStmtObject.execute();
				iResult = cStmtObject.getInt(3);
				conn.commit();
			}//end of if(alProductList != null && alProductList.size() > 0)

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
        			log.error("Error while closing the Statement in InsuranceProductDAOImpl deleteProduct()",sqlExp);
        			throw new TTKException(sqlExp, "insurance");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in InsuranceProductDAOImpl deleteProduct()",sqlExp);
        				throw new TTKException(sqlExp, "insurance");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// end of  deleteProduct(ArrayList alProductList)
}// end of InsuranceProductDAOImpl
