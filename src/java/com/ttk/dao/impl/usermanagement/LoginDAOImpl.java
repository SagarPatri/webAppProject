/**
 * @ (#) LoginDAOImpl.java Jul 29, 2005
 * Project      :
 * File         : LoginDAOImpl.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : Mar 25, 2006
 * Reason        : for updating userValidation method.
 */

package com.ttk.dao.impl.usermanagement;

import java.io.Serializable;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.usermanagement.UserVO;
//Changes Added for Password Policy CR KOC 1235
//End changes for Password Policy CR KOC 1235

public class LoginDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(LoginDAOImpl.class );
	 private static final String strUserValidation="{CALL AUTHENTICATION_PKG_PR_USER_VALIDATION(?,?,?,?)}";		//Changes Added one parameter for Password Policy CR KOC 1235
     private static final String strExternalUserValidation="{CALL AUTHENTICATION_PKG_PR_EXTERNAL_USER_VALIDATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// 1 param for single sign on Modified as per KOC 1257 11PP PASSWORD POLICY FOR ONLINE
     private static final String strIpruUserValidation = "{CALL AUTHENTICATION_PKG.PR_IPRU_USER_VALIDATION(?,?,?)}";
     
	 /**
     * This method taks UserVO object as input which consits of userid and password to
     * validate the user when user trys to log in.
     * If he is valid User then it returns UserSecurityProfile Dom object which consists of
     * User information and his privileges.
     * If he is not valid User then it catches the Exception thrown from the Stored procedure
     * and displays the appropriate message.
     * @param userVO UserVO
     * @return doc Document Object
     * @throws TTKException if not valid User or any run time Exception occures
     */
    public Document userValidation(UserVO userVO)throws TTKException
    {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
        try
        {
             conn = ResourceManager.getConnection();
         
            cStmtObject = conn.prepareCall(strUserValidation);
            cStmtObject.setString(1,userVO.getUSER_ID());
            cStmtObject.setString(2,userVO.getPassword());
			cStmtObject.setString(3,userVO.getHostIPAddress());	//Changes Added get an ip address for Password Policy CR KOC 1235
            cStmtObject.registerOutParameter(4,Types.VARCHAR);
            cStmtObject.execute();
            SAXReader saxReader=new SAXReader();
            doc=saxReader.read(new StringReader(cStmtObject.getString(4)));          
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Statement in LoginDAOImpl userValidation()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in LoginDAOImpl userValidation()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of userValidation(UserVO userVO)

   /**
    * This method is used to authenticate the external Users,
    * who log in to the System.
    * If he is valid User then it returns UserSecurityProfile Dom object which consists of
    * User information and his privileges.
    * @param userVO UserVO
    * @return doc Document
    * @throws TTKException if not valid User or any run time Exception occures
    */
    public ArrayList<Object> externalUserValidation(UserVO userVO)throws TTKException
    {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
        SQLXML xml = null;
        String strExpiryYN="";
        String alertMsg="";//added as per KOC 11PP 1257
		 String strLoginExpiryYN="";;//added as per KOC 11PP 1257
		 String strRandomNo="";;//added as per KOC 1349
        ArrayList<Object> alResult = new ArrayList<Object>();
        try
        {
            conn = ResourceManager.getConnection();
           // cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strExternalUserValidation);
            cStmtObject = conn.prepareCall(strExternalUserValidation);
            cStmtObject.setString(1,userVO.getUSER_ID().trim());
            cStmtObject.setString(2,userVO.getPassword().trim());
            cStmtObject.setString(3,userVO.getPolicyNo().trim());
            cStmtObject.setString(4,userVO.getEnrollmentID().trim());
            cStmtObject.setString(5,userVO.getGroupID().trim());
            cStmtObject.setString(6,userVO.getLoginType().trim());
            cStmtObject.registerOutParameter(7,Types.SQLXML);
            cStmtObject.registerOutParameter(8, Types.CHAR);
            cStmtObject.registerOutParameter(9, Types.VARCHAR);//added as per KOC 11PP 1257
		    cStmtObject.registerOutParameter(10, Types.VARCHAR);//added as per KOC 11PP 1257
		    cStmtObject.registerOutParameter(11, Types.VARCHAR);//added as per KOC 1349
            cStmtObject.setString(12,userVO.getHostIPAddress().trim());//kocbroker
            cStmtObject.setString(13,userVO.getGrpOrInd().trim());//kocprovider
            cStmtObject.setString(14,userVO.getCertificateNbr().trim());
            
            cStmtObject.execute();
            xml = cStmtObject.getSQLXML(7);
           
            conn.commit();
           
            strExpiryYN = TTKCommon.checkNull(cStmtObject.getString(8)) ;
            alertMsg = TTKCommon.checkNull(cStmtObject.getString(9)) ; //added as per KOC 11PP 1257
			 strLoginExpiryYN=TTKCommon.checkNull(cStmtObject.getString(10)) ; //added as per KOC 11PP 1257
			 strRandomNo=TTKCommon.checkNull(cStmtObject.getString(11)) ; //added as per KOC 1349
            SAXReader saxReader = new SAXReader();
            doc = xml != null ? saxReader.read(xml.getCharacterStream()):null;
            alResult.add(doc);
            alResult.add(strExpiryYN);
            alResult.add(alertMsg);//added as per KOC1257 11PP
			alResult.add(strLoginExpiryYN);//added as per KOC1257 11PP
			alResult.add(strRandomNo);//added as per KOC1349
            return (ArrayList<Object>)alResult;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Statement in LoginDAOImpl externalUserValidation()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in LoginDAOImpl externalUserValidation()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
         
    }//end of externalUserValidation(UserVO userVO)
    
    /**
     * This method returns the UserSecurityProfile object which contains all the user details
     * if he is a valid User.
     * @param strCustomerCode Customer Code
     * @return UserSecurityProfile object which contains all the user details along with their role and profile information
     * @exception throws TTKException
     */
    public Document ipruLoginValidation(UserVO userVO) throws TTKException {
    	Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;

        try
        {
            conn = ResourceManager.getConnection();
            cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strIpruUserValidation);
           
            cStmtObject.setString(1,userVO.getLoginType());
            cStmtObject.setString(2,userVO.getCustomerCode());
            cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            cStmtObject.execute();
            xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Statement in LoginDAOImpl externalUserValidation()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in LoginDAOImpl externalUserValidation()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of ipruLoginValidation(UserVO userVO)
}//end of class LoginDAOImpl