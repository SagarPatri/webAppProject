/**
 * @ (#) MOUDAOImpl.java Dec 7, 2005
 * Project      :
 * File         : MOUDAOImpl.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Dec 7, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;

public class MOUDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(MOUDAOImpl.class);

	private static final String strMOUDetail = "SELECT EMPANEL_SEQ_ID, HOSP_MOU FROM app.tpa_hosp_mou WHERE EMPANEL_SEQ_ID = (SELECT empanel_seq_id FROM app.tpa_hosp_empanel_status WHERE hosp_seq_id = ? AND active_yn = 'Y')";
	private static final String strAddUpdateMou = "{CALL HOSPITAL_MOU_PR_MOU_SAVE (?,?,?)}";

	/**
	 * This method returns the modified MOU document
	 * @param lHospSeqId long object which contains the Hospital seq id
	 * @return Document the XML based MOU document
	 * @exception throws TTKException
	 */
	public Document getMOUDocument(long lHospitalSeqId) throws TTKException
	{
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		Document doc = null;
		java.sql.SQLXML xml = null;

		try{
			conn = ResourceManager.getConnection();
			stmt = conn.prepareCall(strMOUDetail);
			stmt.setLong(1,lHospitalSeqId);
			rs = stmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					
					if(rs.getSQLXML(2) != null)
					{
						xml =rs.getSQLXML(2);
					}//end of if(rs.getOPAQUE(2) != null)
				}// end of while
			}//end of if(rs != null)
			if(xml != null)
			{
				SAXReader saxReader=new SAXReader();
				
				doc = saxReader.read(xml.getCharacterStream());
			}//end of if(xml != null)
			return doc;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "mou");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "mou");
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
					log.error("Error while closing the Resultset in MOUDAOImpl getMOUDocument()",sqlExp);
					throw new TTKException(sqlExp, "mou");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MOUDAOImpl getMOUDocument()",sqlExp);
						throw new TTKException(sqlExp, "mou");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MOUDAOImpl getMOUDocument()",sqlExp);
							throw new TTKException(sqlExp, "mou");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "mou");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getMOUDocument(long lHospitalSeqId)

	/**
	 * This method adds or updates the MOU details
	 * @param mouDocument Document object which contains MOU details
	 * @param lHospSeqId Long object which contains the hospital sequence id
	 * @param lUpdatedBy Long object which contains the user sequence id
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateDocument(Document mouDocument, Long lHospSeqId, Long lUpdatedBy) throws TTKException
	{
		int iResult =1;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strAddUpdateMou);
			//XMLType poXML = null;
			SQLXML sqlxml = null;
			if(mouDocument != null)
			{
				sqlxml = conn.createSQLXML();// mouDocument.asXML());
				sqlxml.setString(mouDocument.asXML());		
			}//end of if(mouDocument != null)
			cStmtObject.setLong(1, lHospSeqId);
			cStmtObject.setObject(2, sqlxml);
			cStmtObject.setLong(3, lUpdatedBy);
			cStmtObject.execute();
			iResult = 1;
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "mou");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "mou");
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
        			log.error("Error while closing the Statement in MOUDAOImpl addUpdateDocument()",sqlExp);
        			throw new TTKException(sqlExp, "mou");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MOUDAOImpl addUpdateDocument()",sqlExp);
        				throw new TTKException(sqlExp, "mou");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "mou");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of method addUpdateDocument(Document mouDocument, Long lHospSeqId, Long lUpdatedBy)
}//end of class MOUDAOImpl
