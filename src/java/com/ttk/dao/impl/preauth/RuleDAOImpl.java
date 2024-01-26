/**
 * @ (#) RuleDAOImpl.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  Unni V Mana
 * Modified date :  Apr 14, 2006
 * Reason        :  Adding Policy rule related methods
 */

package com.ttk.dao.impl.preauth;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.customercare.CallCenterDAOImpl;
import com.ttk.dto.BaseVO;
import com.ttk.dto.administration.PolicyRuleVO;
import com.ttk.dto.administration.ProductRuleVO;
import com.ttk.dto.enrollment.MemberRuleVO;
import com.ttk.dto.enrollment.RuleDataVO;
import com.ttk.dto.webservice.ErrorLogVO;

public class RuleDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(CallCenterDAOImpl.class );

	private static final String strAddProductRule = "{CALL Product_Admin_Pkg.save_prod_policy_rules(?,?,?,?,?,?,?)}";
	private static final String strSelectProductDocument = "{CALL PRODUCT_ADMIN_PKG.SELECT_PROD_POLICY_RULES(?,?,?)}";
	private static final String strPolicyRules = "{CALL PRODUCT_ADMIN_PKG.SELECT_POLICY_RULES(?,?)}";
	//private static final String strMemberRules = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_RULES(?,?)}";
	private static final String strMemberRules = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEM_RULES(?,?)}";
    private static final String strSaveErrorLog = "{CALL POLICY_DATA_FEED_PKG.SAVE_ERROR_LOG(";//"{CALL POLICY_DATA_FEED_PKG.SAVE_ERROR_LOG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strClearErrorLog = "{CALL POLICY_ENROLLMENT_PKG.CLEAR_ERRORS(?,?)}";
    //private static final String strSelectErrorLog = "{CALL POLICY_ENROLLMENT_SQL_PKG.SHOW_ERRORS_LIST(?,?,?,?,?,?,?)}";
    private static final String strSelectErrorLog = "{CALL POLICY_ENROLLMENT_PKG_SHOW_ERRORS_LIST(?,?,?,?,?,?,?)}";
    private static final String strUpdateValidationStatus = "{CALL POLICY_ENROLLMENT_PKG.SET_VALIDATION_STATUS(?,?,?)}";
	private static final String strAddMemberRule = "{CALL POLICY_ENROLLMENT_PKG.save_mem_rules(?,?,?,?,?)}";

	/**
     * This method saves the Pre-Authorization Rule Data information
     * @param
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveRuleData() {
        return 0;
    }//end of saveRuleData()

    /**
     * This method used to save the product  rule date which comes in the form of xml type.
     * @param BaseVO
     * @return int: The value which returns greater than zero for succcesssful execution of this method
     */
    public Long addUpdateDocument(BaseVO baseVO) throws TTKException
	{
		Long lngResult =null;
		ProductRuleVO ruleVO=null;
		PolicyRuleVO policyRuleVO=null;
		RuleDataVO ruleDataVO=null;

		if(baseVO instanceof PolicyRuleVO){
			policyRuleVO=(PolicyRuleVO) baseVO;
			return new Long(addUpdateDocument(policyRuleVO));
		}//end of  if(baseVO instanceof PolicyRuleVO)

		if(baseVO instanceof RuleDataVO){
			ruleDataVO=(RuleDataVO) baseVO;
			return new Long(addUpdateDocument(ruleDataVO));
		}//end of if(baseVO instanceof RuleDataVO)

		if(baseVO instanceof ProductRuleVO) ruleVO=(ProductRuleVO) baseVO;

		Connection conn = null;
		OracleCallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strAddProductRule);
			XMLType poXML = null;
			if(ruleVO.getPolicyRule() != null)
			{
				poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), ruleVO.getPolicyRule().asXML());
			}//end of if(ruleVO.getPolicyRule() != null)
			cStmtObject.setLong(1, ruleVO.getPolicyRuleSeqID());
			cStmtObject.setLong(2, (ruleVO.getPrevProdPolicyRuleSeqId()==null)?0:ruleVO.getPrevProdPolicyRuleSeqId());
			cStmtObject.setLong(3, ruleVO.getPolicySeqID());
			cStmtObject.setObject(4, poXML);
			cStmtObject.setTimestamp(5,new Timestamp(ruleVO.getDtFrom().getTime()));
			cStmtObject.setTimestamp(6,null);
			cStmtObject.setInt(7,12);
			cStmtObject.registerOutParameter(1,OracleTypes.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(2,OracleTypes.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			lngResult = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "Product Rule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "Product Rule");
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
	        			log.error("Error while closing the Statement in RuleDAOImpl addUpdateDocument()",sqlExp);
	        			throw new TTKException(sqlExp, "Product Rule");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in RuleDAOImpl addUpdateDocument()",sqlExp);
	        				throw new TTKException(sqlExp, "Product Rule");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "Product Rule");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
		return lngResult;
	}//end of method addUpdateDocument(BaseVO baseVO)

    /**
     * This method used to save the  policy rule date which comes in the form of xml type.
     * @param PolicyRuleVO
     * @return int: The value which returns greater than zero for succcesssful execution of this method
     */
    public int addUpdateDocument(PolicyRuleVO policyRuleVO) throws TTKException
    {
    	int iResult=0;
    	Connection conn = null;
		OracleCallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strAddProductRule);
			XMLType poXML = null;
			if(policyRuleVO.getProdPolicyRule() != null)
			{
				poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), policyRuleVO.getProdPolicyRule().asXML());
			}//end of if(policyRuleVO.getProdPolicyRule() != null)
			cStmtObject.setLong(1, policyRuleVO.getProdPolicyRuleSeqId());
			cStmtObject.setLong(2, (policyRuleVO.getPrevProdPolicyRuleSeqId()==null)?0:policyRuleVO.getPrevProdPolicyRuleSeqId());
			cStmtObject.setLong(3, policyRuleVO.getPolicySeqId());
			cStmtObject.setObject(4, poXML);
			cStmtObject.setTimestamp(5,new Timestamp(policyRuleVO.getDtFrom().getTime()));
			cStmtObject.setTimestamp(6,null);
			cStmtObject.setInt(7,12);
			cStmtObject.execute();
			iResult = 1;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "Policy Rule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "Policy Rule");
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
        			log.error("Error while closing the Statement in RuleDAOImpl addUpdateDocument()",sqlExp);
        			throw new TTKException(sqlExp, "Product Rule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl addUpdateDocument()",sqlExp);
        				throw new TTKException(sqlExp, "Product Rule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "Product Rule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of addUpdateDocument(PolicyRuleVO policyRuleVO)

    /**
     * This method used to get the product rule xml doc along with from date,todate etc .
     * @param ProdcutPolicySeqID
     * @return ArrayList which consists ProductRuleVOs
     */
	public ArrayList getDocument(Long lngProdPolicySeqID,String moduleName) throws TTKException
	{
		Connection conn = null;
		OracleCallableStatement stmt = null;
		OracleResultSet rs = null;
		Document doc = null;
		XMLType xml = null;
		ProductRuleVO ruleVO = null;

		ArrayList<Object> alDateList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strSelectProductDocument);
			stmt.setLong(1,lngProdPolicySeqID);
			stmt.setString(2,moduleName);
			stmt.registerOutParameter(3,OracleTypes.CURSOR);
			rs = (OracleResultSet)stmt.executeQuery();
			rs = (OracleResultSet) stmt.getObject(3);
			DOMReader domReader = new DOMReader();
			if(rs != null){
				while (rs.next()) {
					ruleVO = new ProductRuleVO();
					if(rs.getLong(1) !=0) 
					{
						ruleVO.setPolicyRuleSeqID(rs.getLong(1));
					}//end of if(rs.getLong(1) !=0)
					if(rs.getOPAQUE(3) != null)
					{
						xml = XMLType.createXML(rs.getOPAQUE(3));
						domReader = new DOMReader();
						if(xml != null)
						{
							doc = domReader.read(xml.getDOM());//read method take w3c document and returns dom4j document
						}//end of if(xml != null)
						ruleVO.setPolicyRule(doc);
					}
					if(rs.getDate(4)!=null)
					{
						ruleVO.setDtFrom(new java.util.Date(rs.getTimestamp(4).getTime()));
					}
					if(rs.getDate(5)!=null)
					{
						ruleVO.setDtTo(new java.util.Date(rs.getTimestamp(5).getTime()));
					}
					alDateList.add(ruleVO);
				}// end of while
			}//end of if(rs != null)
			return alDateList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "ProductRule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "ProductRule");
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
					log.error("Error while closing the Resultset in RuleDAOImpl getShortfallDetail()",sqlExp);
					throw new TTKException(sqlExp, "ProductRule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getDocument()",sqlExp);
						throw new TTKException(sqlExp, "ProductRule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getDocument()",sqlExp);
							throw new TTKException(sqlExp, "ProductRule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "ProductRule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDocument(Long lngProdPolicySeqID,String moduleName)

	 /**
	 * This method gets  policy rules
	 * @param lPolicySeqId the Policy which is going to accept the rules
	 * @return xml document that holds the policy ruls for that oartucular policy seq id
	 * @exception throws TTKException
	 */

	public ArrayList getPolicyRules(Long lngPolicySeqId) throws TTKException{

		Connection conn = null;
		OracleCallableStatement stmt = null;
		OracleResultSet rs = null;
		Document doc = null;
		XMLType xml = null;

		PolicyRuleVO policyRuleVO = new PolicyRuleVO();
		ArrayList<Object> alDateList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyRules);
			stmt.setLong(1,lngPolicySeqId);
			stmt.registerOutParameter(2,OracleTypes.CURSOR);
			rs = (OracleResultSet)stmt.executeQuery();
			rs = (OracleResultSet) stmt.getObject(2);
			DOMReader domReader = new DOMReader();

			if(rs != null){
				while (rs.next()) {
					policyRuleVO = new PolicyRuleVO();
					if(rs.getOPAQUE(4) != null)
					{
						xml = XMLType.createXML(rs.getOPAQUE(4));
						domReader = new DOMReader();
						if(xml != null)
						{
							doc = domReader.read(xml.getDOM());//read method take w3c document and returns dom4j document
						}//end of if(xml != null)
						policyRuleVO.setProdPolicyRule(doc);
					}
						policyRuleVO.setProdPolicySeqId(rs.getLong(3));
						policyRuleVO.setProdPolicyRuleSeqId(rs.getLong(2));
						policyRuleVO.setPolicySeqId(rs.getLong(1));

						if(rs.getDate(5)!=null)
						{
							policyRuleVO.setDtFrom(new java.util.Date(rs.getTimestamp(5).getTime()));
						}
						if(rs.getDate(6)!=null)
						{
							policyRuleVO.setDtTo(new java.util.Date(rs.getTimestamp(6).getTime()));
						}

						alDateList.add(policyRuleVO);
				}// end of while
			}//end of if(rs != null)
			return alDateList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "PolicyRule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "PolicyRule");
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
					log.error("Error while closing the Resultset in RuleDAOImpl getPolicyRules()",sqlExp);
					throw new TTKException(sqlExp, "PolicyRule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getPolicyRules()",sqlExp);
						throw new TTKException(sqlExp, "PolicyRule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getPolicyRules()",sqlExp);
							throw new TTKException(sqlExp, "PolicyRule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "PolicyRule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getPolicyRules(Long lngPolicySeqId)

	/**
     * This method used to get the product rule xml doc along with from date,todate etc .
     * @param ProdcutPolicySeqID
     * @param module name
     * @return ArrayList which consists RuleDataVOs
     */
	public ArrayList getEnrollmentRuleData(Long lngProdPolicySeqID,String moduleName) throws TTKException
	{
		Connection conn = null;
		OracleCallableStatement stmt = null;
		OracleResultSet rs = null;
		Document doc = null;
		XMLType xml = null;
		RuleDataVO ruleDataVO=null;
		ArrayList<Object> alDateList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strSelectProductDocument);
			stmt.setLong(1,lngProdPolicySeqID);
			stmt.setString(2,moduleName);
			stmt.registerOutParameter(3,OracleTypes.CURSOR);
			rs = (OracleResultSet)stmt.executeQuery();
			rs = (OracleResultSet) stmt.getObject(3);
			DOMReader domReader = new DOMReader();
			if(rs != null){
				while (rs.next()) {
					ruleDataVO=new RuleDataVO();
					if(rs.getLong(1) !=0) ruleDataVO.setPolicyRuleSeqID(rs.getLong(1));
					if(rs.getOPAQUE(3) != null)
					{
						xml = XMLType.createXML(rs.getOPAQUE(3));
						domReader = new DOMReader();
						if(xml != null)
						{
							doc = domReader.read(xml.getDOM());//read method take w3c document and returns dom4j document
						}//end of if(xml != null)
						ruleDataVO.setPolicyRule(doc);
					}//end of if(rs.getOPAQUE(3) != null)
					if(rs.getDate(4)!=null)
					{
						ruleDataVO.setDtFrom(new java.util.Date(rs.getTimestamp(4).getTime()));
					}//end of if(rs.getDate(4)!=null)
					if(rs.getDate(5)!=null)
					{
						ruleDataVO.setDtTo(new java.util.Date(rs.getTimestamp(5).getTime()));
					}//end of if(rs.getDate(5)!=null)
					alDateList.add(ruleDataVO);
				}// end of while
			}//end of if(rs != null)
			return alDateList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "RuleData");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "RuleData");
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
					log.error("Error while closing the Resultset in RuleDAOImpl getEnrollmentRuleData()",sqlExp);
					throw new TTKException(sqlExp, "RuleData");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getEnrollmentRuleData()",sqlExp);
						throw new TTKException(sqlExp, "RuleData");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getEnrollmentRuleData()",sqlExp);
							throw new TTKException(sqlExp, "RuleData");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "RuleData");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getEnrollmentRuleData(Long lngProdPolicySeqID,String moduleName)


    public int addUpdateDocument(RuleDataVO ruleDataVO) throws TTKException
    {
    	Connection conn = null;
    	OracleCallableStatement cStmtObject=null;
    	int iResult = 1;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strAddProductRule);
    		XMLType poXML = null;
    		if(ruleDataVO.getPolicyRule() != null)
    		{
    			poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), ruleDataVO.getPolicyRule().asXML());
    		}//end of if(ruleDataVO.getPolicyRule() != null)
    		cStmtObject.setLong(1, ruleDataVO.getPolicyRuleSeqID());
    		cStmtObject.setLong(2, (ruleDataVO.getPrevProdPolicyRuleSeqId()==null)?0:ruleDataVO.getPrevProdPolicyRuleSeqId());
    		cStmtObject.setLong(3, ruleDataVO.getPolicySeqID());
    		cStmtObject.setObject(4, poXML);
    		cStmtObject.setTimestamp(5,null);
    		if(ruleDataVO.getDtTo()==null)
    		{
    			cStmtObject.setTimestamp(6,null);
    		}//end of if(ruleDataVO.getDtTo()==null)
    		else
    		{
    			cStmtObject.setTimestamp(6,new Timestamp(ruleDataVO.getDtTo().getTime()));
    		}//end of else
    		
    		cStmtObject.setInt(7,12);
    		cStmtObject.execute();
    		
    	}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "Product Rule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "Product Rule");
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
        			log.error("Error while closing the Statement in RuleDAOImpl addUpdateDocument()",sqlExp);
        			throw new TTKException(sqlExp, "Product Rule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl addUpdateDocument()",sqlExp);
        				throw new TTKException(sqlExp, "Product Rule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "Product Rule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of addUpdateDocument(RuleDataVO ruleDataVO)

    public ArrayList getMemberRuleData(long lngMemberSeqId) throws TTKException
    {
    	Connection conn = null;
		OracleCallableStatement stmt = null;
		OracleResultSet rs = null;
		Document doc = null;
		XMLType xml = null;
		MemberRuleVO memberRuleVO=null;
		ArrayList<Object> alDateList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strMemberRules);
			stmt.setLong(1,lngMemberSeqId);
			stmt.registerOutParameter(2,OracleTypes.CURSOR);
			rs = (OracleResultSet)stmt.executeQuery();
			rs = (OracleResultSet) stmt.getObject(2);
			DOMReader domReader = new DOMReader();
			if(rs != null){
				while (rs.next()) {
					memberRuleVO = new MemberRuleVO();
					if(rs.getLong(1) !=0) memberRuleVO.setMemeberSeqID(rs.getLong(1));
					if(rs.getOPAQUE(2) != null)
					{
						xml = XMLType.createXML(rs.getOPAQUE(2));
						domReader = new DOMReader();
						if(xml != null)
						{
							doc = domReader.read(xml.getDOM());//read method take w3c document and returns dom4j document
						}//end of if(xml != null)
						memberRuleVO.setMemberRule(doc);
					}
					alDateList.add(memberRuleVO);
				}// end of while
			}//end of if(rs != null)
			return alDateList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "MemberRuleData");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "MemberRuleData");
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
					log.error("Error while closing the Resultset in RuleDAOImpl getMemberRuleData()",sqlExp);
					throw new TTKException(sqlExp, "MemberRuleData");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getMemberRuleData()",sqlExp);
						throw new TTKException(sqlExp, "MemberRuleData");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getMemberRuleData()",sqlExp);
							throw new TTKException(sqlExp, "MemberRuleData");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "MemberRuleData");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getMemberRuleData(long lngMemberSeqId)

    /**
     *
     * @param MemberRuleVO memberVO
     * @return int: The value which returns greater than zero for succcesssful execution of this method
     * @throws TTKException
     */
    public int addUpdateMemberRule(MemberRuleVO memberVO) throws TTKException
    {
    	Connection conn = null;
    	OracleCallableStatement cStmtObject=null;
    	int iResult = 1;
    		try{
    			conn = ResourceManager.getConnection();
    			cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strAddMemberRule);
    			XMLType poXML = null;
    			if(memberVO.getMemberRule() != null)
    			{
    				poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), memberVO.getMemberRule().asXML());
    			}//end of if(memberVO.getMemberRule() != null)
    			cStmtObject.setString(1, memberVO.getAction());
    			cStmtObject.setLong(2,   memberVO.getMemeberSeqID());
    			cStmtObject.setObject(3, poXML);
    			cStmtObject.setLong(4,   12);
    			cStmtObject.setInt(5,iResult);
    			cStmtObject.execute();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			throw new TTKException(sqlExp, "Member Rule");
    		}//end of catch (SQLException sqlExp)
    		catch (Exception exp)
    		{
    			throw new TTKException(exp, "Member Rule");
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
            			log.error("Error while closing the Statement in RuleDAOImpl addUpdateMemberRule()",sqlExp);
            			throw new TTKException(sqlExp, "Product Rule");
            		}//end of catch (SQLException sqlExp)
            		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
            		{
            			try
            			{
            				if(conn != null) conn.close();
            			}//end of try
            			catch (SQLException sqlExp)
            			{
            				log.error("Error while closing the Connection in RuleDAOImpl addUpdateMemberRule()",sqlExp);
            				throw new TTKException(sqlExp, "Product Rule");
            			}//end of catch (SQLException sqlExp)
            		}//end of finally Connection Close
            	}//end of try
            	catch (TTKException exp)
            	{
            		throw new TTKException(exp, "Product Rule");
            	}//end of catch (TTKException exp)
            	finally // Control will reach here in anycase set null to the objects
            	{
            		cStmtObject = null;
            		conn = null;
            	}//end of finally
    		}//end of finally
    		return iResult;
    }//end of addUpdateMemberRule(MemberRuleVO memberVO)

    /**
     * This method returns the validation error.
     * @param ArrayList search parameter
     * @return ArrayList containing ErrorLogVO
     * @exception throws TTKException
     */
    public ArrayList selectRuleErrors(ArrayList alSearchParam) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        ErrorLogVO errorLogVO = null;
        ArrayList<Object> alErrorLog = null;
       try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectErrorLog);
            cStmtObject.setString(1,(String)alSearchParam.get(0));
            cStmtObject.setString(2,(String)alSearchParam.get(1));
            cStmtObject.setString(3,(String)alSearchParam.get(2));
            cStmtObject.setString(4,(String)alSearchParam.get(3));
            cStmtObject.setString(5,(String)alSearchParam.get(4).toString());
            cStmtObject.setString(6,(String)alSearchParam.get(5).toString());
            cStmtObject.registerOutParameter(7,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(7);
            conn.commit();
            if (rs!=null)
            {
                alErrorLog = new ArrayList<Object>();
                while (rs.next())
                {
                    errorLogVO = new ErrorLogVO();
                    errorLogVO.setErrorLogSeqID(rs.getString("ERROR_LOG_SEQ_ID")!=null ? new Long(rs.getLong("ERROR_LOG_SEQ_ID")):null);
                    errorLogVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
                    errorLogVO.setAbbrevationCode(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
                    errorLogVO.setInsCompCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                    errorLogVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
                    errorLogVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    errorLogVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    errorLogVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    errorLogVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
                    errorLogVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                    errorLogVO.setPolicyHolder(TTKCommon.checkNull(rs.getString("POLICY_HOLDER")));
                    errorLogVO.setErrorNbr(TTKCommon.checkNull(rs.getString("ERROR_NO")));
                    errorLogVO.setErrorMessage(TTKCommon.replaceInString(TTKCommon.checkNull(rs.getString("ERROR_MESSAGE")),"\n","<br>"));
                    errorLogVO.setErrorType(TTKCommon.checkNull(rs.getString("ERROR_TYPE")));
                    alErrorLog.add(errorLogVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "Policy Rule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "Policy Rule");
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
					log.error("Error while closing the Resultset in RuleDAOImpl selectRuleErros()",sqlExp);
					throw new TTKException(sqlExp, "Policy Rule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl selectRuleErros()",sqlExp);
						throw new TTKException(sqlExp, "Policy Rule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl selectRuleErros()",sqlExp);
							throw new TTKException(sqlExp, "Policy Rule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "Policy Rule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return alErrorLog;
    }//end of selectRuleErros(ArrayList alSearchParam)

    /**
     * This method saves the Rule Error information
     * @param String POLICY_NUMBER or TPA_ENROLLMENT_NUMBER or TPA_ENROLLMENT_ID or BATCH_NUMBER
     * @param Stirng flag 'P' for Policy or 'E' for Enrollment or 'M' for Member or 'B' for Batch
     * @exception throws TTKException
     */
    public void clearRuleErrors(String strSeqID,String strFlag) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClearErrorLog);
            cStmtObject.setString(1,strSeqID);
            cStmtObject.setString(2,strFlag);
            cStmtObject.execute();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "Policy Rule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "Policy Rule");
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
        			log.error("Error while closing the Statement in RuleDAOImpl clearRuleErros()",sqlExp);
        			throw new TTKException(sqlExp, "Product Rule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl clearRuleErros()",sqlExp);
        				throw new TTKException(sqlExp, "Product Rule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "Product Rule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of clearRuleErros(String strSeqID,String strFlag)
    /**
     * This method saves the Error Log information
     * @param ErrorLogVO object which contains the Error Log information
     * @exception throws TTKException
     */
    public int saveErrorLog(ArrayList alErrors) throws TTKException
    {
        int iResult=0;
        StringBuffer sbfSQL = null;
        ErrorLogVO errorLogVO = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ResourceManager.getConnection();
            stmt = (java.sql.Statement)conn.createStatement();
            conn.setAutoCommit(false);
            
            if(alErrors != null){
            	for(int iError=0;iError<alErrors.size();iError++){
                    sbfSQL = new StringBuffer();
                    errorLogVO  = (ErrorLogVO)alErrors.get(iError);
                    sbfSQL = sbfSQL.append(errorLogVO.getErrorLogSeqID()).append(",")
                    .append(errorLogVO.getOfficeSeqID()).append(",")
                    .append(errorLogVO.getInsSeqID()).append(",'")
                    .append(errorLogVO.getAbbrevationCode()).append("','")
                    .append(errorLogVO.getInsCompCode()).append("','")
                    .append(errorLogVO.getBatchNbr()).append("','")
                    .append(errorLogVO.getPolicyNbr()).append("','")
                    .append(errorLogVO.getEndorsementNbr()).append("','")
                    .append(errorLogVO.getEmployeeNbr()).append("','")
                    .append(errorLogVO.getPolicyHolder()).append("','")
                    .append(errorLogVO.getEnrollmentNbr()).append("','")
                    .append(errorLogVO.getEnrollmentID()).append("','")
                    .append(errorLogVO.getErrorNbr()).append("','")
                    .append(errorLogVO.getErrorMessage()).append("','")
                    .append(errorLogVO.getErrorType()).append("',1)}");//Added by and row processed is hardcoded
                    stmt.addBatch(strSaveErrorLog+sbfSQL.toString());
                }//end of for
            	 stmt.executeBatch();
                 conn.commit();
            }//end of if(alErrors != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "Policy Rule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "Policy Rule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl saveErrorLog()",sqlExp);
        			throw new TTKException(sqlExp, "Product Rule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl saveErrorLog()",sqlExp);
        				throw new TTKException(sqlExp, "Product Rule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "Product Rule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveErrorLog(String document)

    /**
     * This method is used to execute the enrollment level validation rules
     * @param String strFlag identifier containing P -> POLICY, -> E enrollment, M -> member
     * @param Long lngSeqID containing Policy Seq_id or Policy_group_seq_id OR Member seq_id
     * @param String strStatus containing U -> unckecked, P -> passed F -> failed
     */
    public void updateValidationStatus(String strFlag,Long lngSeqID,String strStatus) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateValidationStatus);
            cStmtObject.setString(1,strFlag);
            cStmtObject.setLong(2,lngSeqID);
            cStmtObject.setString(3,strStatus);
            cStmtObject.execute();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "Policy Rule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "Policy Rule");
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
        			log.error("Error while closing the Statement in RuleDAOImpl updateValidationStatus()",sqlExp);
        			throw new TTKException(sqlExp, "Product Rule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl updateValidationStatus()",sqlExp);
        				throw new TTKException(sqlExp, "Product Rule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "Product Rule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of updateValidationStatus(String strFlag,Long lngSeqID,String strStatus)
}//end of RuleDAOImpl
