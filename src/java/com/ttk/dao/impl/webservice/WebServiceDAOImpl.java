/**
 * @ (#) MemberDAOImpl.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : MemberDAOImpl.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :	Kishor kumar S H
 * Modified date :18022015
 * Reason        :
 */

package com.ttk.dao.impl.webservice;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.annotation.Documented;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.xml.bind.DatatypeConverter;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.BLOB;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.PublishMembers;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.UploadMembers;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.PolicyVO;

public class WebServiceDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(WebServiceDAOImpl.class );

    private static final String strSavePolicy = "{CALL POLICY_DATA_FEED_PKG.LOAD_POLICY(?,?,?,?,?)}";
    private static final String strSaveBank = "{CALL FLOAT_ACCOUNTS_PKG.UPLOAD_PAYMENT_ADVICE(?)}";
	// Call to procedure is added for bulk upload CR KOC1169
    private static final String strSaveCustBankDtls = "{CALL IFSC_BANK_DETAILS_UPDATE_ENR.UPLOAD_CUST_BNK_DTLS(?)}";
    private static final String strConsolidatedPolicyList = "{CALL POLICY_DATA_FEED_PKG.GET_POLICY_NUMBERS(?,?,?,?,?,?,?)}";
    private static final String strSaveUploadStatus = "{CALL POLICY_DATA_FEED_PKG.COMPLETE_BATCH(?,?,?)}";
    private static final String strGetTableData = "{CALL TTK_UTIL_PKG.GET_TABLE_DATA(?,?,?)}";
    private static final String strGetRuleErrors = "{CALL POLICY_DATA_FEED_PKG.GET_RULE_ERRORS(?,?)}";

    /** Following Services Written for Mobile Application */
	private static final String strIndividualLoginService = "{CALL TTK_API_PKG.INDIVIDUAL_LOGIN(?,?,?,?)}";
	private static final String strEcardService = "{CALL TTK_API_PKG.ECARD(?,?)}";//changes has to be made
	
	private static final String strPolicySearchService= "{CALL TTK_API_PKG.GET_POLICY_HISTORY(?,?,?)}";//done
	private static final String strClaimHistoryService = "{CALL TTK_API_PKG.CLM_HISTORY_INFO(?,?,?,?,?)}";
	private static final String strClaimStatusService= "{CALL TTK_API_PKG.CLM_STATUS_INFO(?,?,?,?,?)}";
	private static final String strFeedBackService = "{CALL TTK_API_PKG.MEM_HOSP_FEEDBACK_SAVE(?,?,?,?)}";
	private static final String strFeedBackServiceWithNewChanges = "{CALL TTK_API_PKG.MEM_HOSP_FEEDBACK_SAVE(?,?,?,?,?)}";
	private static final String strHospitalInfo = "{CALL TTK_API_PKG.TTK_HOSP_INFO(?,?,?,?,?,?,?,?)}";//added param 5th param Double in kilometers
	private static final String strHospitalDetails= "{CALL TTK_API_PKG.TTK_HOSP_LIST(?,?,?,?,?,?,?)}";//added param No of rows to be fetched
	private static final String strDependentInfo= "{CALL TTK_API_PKG.TPA_DEPENDENT_INFO(?,?,?,?)}";
	
	
	private static final String strTemplateInfo= "{CALL TTK_API_PKG.GET_TEMPLATE_INFO(?,?,?,?,?,?)}";
	private static final String strDailyTipInfo= "{CALL TTK_API_PKG.GET_DAILY_TIP_INFO(?,?,?)}";
	private static final String strDailyTipImage= "{CALL TTK_API_PKG.GET_DAILY_TIP_IMAGE(?,?,?)}";
	private static final String strClaimFeedBack = "{CALL TTK_API_PKG.CLM_FEEDBACK_SAVE(?,?,?,?,?)}";
	private static final String strDocAroundClockStatus = "{CALL TTK_API_PKG.DOC_AROUND_CLOCK_STATUS(?,?)}";
	private static final String strAskExpertOpnionService = "{CALL TTK_API_PKG.ASK_EXPERT_REPLY_SAVE(?,?,?,?,?,?)}";
	private static final String strClaimIntimationService = "{CALL TTK_API_PKG.CLM_INTIMATION_SAVE(?,?,?,?,?,?,?,?,?,?,?)}";//donechanges
	private static final String strPreAuthClmCount = "{CALL TTK_API_PKG.GET_PRE_CLM_COUNT(?,?,?,?)}";
	private static final String strgetAllPolicyDetails =   "{CALL policy_enrollment_pkg.policy_going_expire(?,?,?,?)}";
	private static final String strcreatePolicy ="{CALL POLICY_ENROLLMENT_PKG.auto_renewal_policy_save(?,?,?,?,?,?,?)}";
	private static final String strSavePolicyDoc="{CALL  policy_enrollment_pkg.save_web_service_policy_Docs(?,?,?,?,?,?,?,?)}";
	//ProjectX
	//private static final String strFetchXml = "{CALL app.GEN_MEM_XML(?,?,?)}";
	private static final String strFetchXml = "{CALL mohan_babu.sample_xml(?)}";

	/*private static final String strStateListService = "{CALL TTK_API_PKG.STATE_XML(?)}";
	private static final String strCityListService = "{CALL TTK_API_PKG.CITY_XML(?,?)}";
	private static final String strCity_StateListService = "{CALL TTK_API_PKG.STATE_CITY_XML(?)}";

*/
	//SelfFund - added by Kishor
	private static final String strValidatePhoneNum	=	"{CALL health_4_sure_login.cust_login(?,?)}";
	private static final String strProviderList		=	"{CALL health_4_sure_login.select_provider_list(?,?,?,?,?,?)}";
	private static final String strGetDiagList		=	"{CALL health_4_sure_login. select_diag_tests(?,?)}";
    private static final String strgetPolicyDetails =   "{CALL policy_enrollment_pkg.auto_renewal_policy(?,?,?,?,?,?)}";
	/**
     * This method saves the Policy information
     * @param String object which contains the policy and member details
     * @return String object which contains
     * @exception throws TTKException
     */
    public String savePolicy(String document) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
            conn = ResourceManager.getConnection();
            //conn = ResourceManager.getWebserviceConnection();
            //PolicyVO policyVO = new PolicyVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePolicy);
            XMLType policyXML = null;
            if(document != null)
            {
                policyXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), document);
            }//end of if(document != null)
            cStmtObject.setObject(1,policyXML);
            cStmtObject.setLong(2,0); // Place holder for Batch seq id

           // Long lngPolicySeqID = null;
          //  Long lngPolicyGroupSeqID = null;
           // String strEnrTypeID ="";

            cStmtObject.registerOutParameter(2,Types.BIGINT); // batch seq id
            cStmtObject.registerOutParameter(3,Types.BIGINT); //Policy SeqID
            cStmtObject.registerOutParameter(4,Types.BIGINT); //Policy Group SeqID
            cStmtObject.registerOutParameter(5,Types.VARCHAR); //Enrollment Type ID

            cStmtObject.execute();
            strResult = String.valueOf(cStmtObject.getLong(2));

           /*lngPolicySeqID = cStmtObject.getLong(3);
            lngPolicyGroupSeqID = cStmtObject.getLong(4);
            strEnrTypeID = cStmtObject.getString(5);

            policyVO.setPolicySeqID(lngPolicySeqID);
            policyVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
            policyVO.setEnrollmentType(strEnrTypeID);
            if(lngPolicyGroupSeqID!=null && lngPolicyGroupSeqID>0)
            {
                //add the policy detail to MDB for rule execution and return the batch seq id to webservice
                this.addToPolicyQueue(policyVO);
            }//end of if(lngPolicyGroupSeqID!=null && lngPolicyGroupSeqID>0)
            */
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of savePolicy(String document)

    /**
     * This method saves the Policy information
     * @param String object which contains the policy and member details
     * @return String object which contains
     * @exception throws TTKException
     */
    public String savePolicy(String document,String strCompAbbr) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        log.debug(" strCompAbbr "+strCompAbbr);
        try {
            if(strCompAbbr.equalsIgnoreCase("BNK")||strCompAbbr.equalsIgnoreCase("EFT"))
            {
            	conn = ResourceManager.getConnection();
//            	conn = ResourceManager.getWebserviceConnection();
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBank);
            	XMLType policyXML = null;
                if(document != null)
                {
                    policyXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), document);
                }//end of if(document != null)
                cStmtObject.setObject(1,policyXML);
                log.debug(" Before Call Procedure  ");
                cStmtObject.execute();
            }//end of if(strCompAbbr.equalsIgnoreCase("BNK")||strCompAbbr.equalsIgnoreCase("EFT"))
			//	Changes Added for Bulk Upload CR KOC1169
            else if(strCompAbbr.equalsIgnoreCase("HOS")||strCompAbbr.equalsIgnoreCase("MEM"))
            {
            	conn = ResourceManager.getConnection();
//            	conn = ResourceManager.getWebserviceConnection();
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCustBankDtls);
            	XMLType policyXML = null;
                if(document != null)
                {
                    policyXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), document);
                //    Document podoc = (Document)policyXML.getDOM(); 
                //    
                }//end of if(document != null)
                cStmtObject.setObject(1,policyXML);
                log.debug(" Before Call Procedure  ");
                cStmtObject.execute();
            }//end of if(strCompAbbr.equalsIgnoreCase("HOS")||strCompAbbr.equalsIgnoreCase("MEM"))
            //	Changes Added for Bulk Upload CR KOC1169
            else
            {
            	return savePolicy(document);
            }//end of else
        }//end of try
        catch (SQLException sqlExp)
        {
			throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (TTKException exp)
		{
			throw exp;
        }//end of catch (Exception exp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of savePolicy(String document)

    /**
     * This method returns the consolidated list of Policy Number.
     * @param String object which contains the parameter in XML format.
     * @return String object which contains Policy Numbers.
     * @exception throws TTKException.
     */
    public String getConsolidatedPolicyList(String strFromDate,String strToDate,long lngInsSeqID,long lngProductSeqID,long lngOfficeSeqID,String strEnrTypeID) throws TTKException {

        String strResult="";
        Connection conn = null;
        OracleCallableStatement stmt = null;
        try {
            conn = ResourceManager.getConnection();
//          conn = ResourceManager.getWebserviceConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strConsolidatedPolicyList);

            XMLType policyXML = null;
            stmt.setString(1,strFromDate);
            stmt.setString(2,strToDate);
            stmt.setLong(3,lngInsSeqID);
            stmt.setLong(4,lngProductSeqID);
            stmt.setLong(5,lngOfficeSeqID);
            stmt.setString(6,strEnrTypeID);
            stmt.registerOutParameter(7,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            stmt.execute();
            policyXML = XMLType.createXML(stmt.getOPAQUE(7));
            strResult = policyXML.getStringVal();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl getConsolidatedPolicyList()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl getConsolidatedPolicyList()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of getConsolidatedPolicyList(String strValue)

    /**
     * This method method saves the Number of policy uploaded and number of policy rejected in softcopy upload.
     * @param String object which contains the softcopy upolad status in XML format.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String saveUploadStatus(String strBatchNumber,long lngNum_of_policies_rcvd ) throws TTKException {

        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
            conn = ResourceManager.getConnection();
//          conn = ResourceManager.getWebserviceConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveUploadStatus);
            cStmtObject.setString(1,strBatchNumber);
            cStmtObject.setLong(2,lngNum_of_policies_rcvd);
            cStmtObject.registerOutParameter(3,Types.INTEGER);
            cStmtObject.execute();
            strResult = String.valueOf(cStmtObject.getInt(3));
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl saveUploadStatus()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl saveUploadStatus()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of saveUploadStatus(String strValue)

    /**
     * This method method returns the data from TTKOffice,Product or Branch.
     * @param String object which contains the table name for which data in is required.
     * @return String object which contains .
     * @exception throws TTKException.
     */
    public String getTableData(String strIdentifier,String strID) throws TTKException{

        String strResult="";
        Connection conn = null;
        OracleCallableStatement stmt = null;
        XMLType xml = null;
        Document doc = null;
        try {
            conn = ResourceManager.getConnection();
//          conn = ResourceManager.getWebserviceConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strGetTableData);
            //stmt = (OracleCallableStatement)conn.prepareCall(strGetTableData);
            stmt.setString(1,strIdentifier);
            stmt.setString(2,strID);
            stmt.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            stmt.execute();
            xml = XMLType.createXML(stmt.getOPAQUE(3));
            DOMReader domReader = new DOMReader();
            if(xml!=null)
            {
                doc= domReader.read(xml.getDOM());
                strResult= doc.asXML();
                log.debug("strResult value is :"+strResult);
            }//end of if(xml!=null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl getTableData()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl getTableData()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally

        return (doc!=null?doc.asXML():null);
    }//end of getTableData(String strIdentifier)
    
    /**
     * This method method returns the Rule Errors.
     * @param String object which contains the Batch Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
    public String getRuleErrors(String strBatchNbr) throws TTKException {
    	String strResult="";
        Connection conn = null;
        OracleCallableStatement stmt = null;
        XMLType xml = null;
        Document doc = null;
        try {
            conn = ResourceManager.getConnection();
//          conn = ResourceManager.getWebserviceConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strGetRuleErrors);
            //stmt = (OracleCallableStatement)conn.prepareCall(strGetTableData);
            stmt.setString(1,strBatchNbr);
            stmt.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            stmt.execute();
            xml = XMLType.createXML(stmt.getOPAQUE(2));
            DOMReader domReader = new DOMReader();
            if(xml!=null)
            {
                doc= domReader.read(xml.getDOM());
                strResult= doc.asXML();
                log.debug("strResult value is :"+strResult);
            }//end of if(xml!=null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webservice");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl getRuleErrors()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl getRuleErrors()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally

        return (doc!=null?doc.asXML():null);
    }//end of getRuleErrors(String strBatchNbr)

    /**
     * This mehod add the Policy detail to MDB queue for Rule Execution
     *
     * @param strPoicy String object containing policy detail in XML format
     */
    private void addToPolicyQueue(PolicyVO policyVO) throws Exception
    {
       QueueConnection cnn = null;
       QueueSender sender = null;
       QueueSession session = null;
       String strEMailQueueName = TTKPropertiesReader.getPropertyValue("JMSRULEENGINEQUEUENAME");
	   String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
	   InitialContext ctx = null;
	   Queue queue = null;
	   QueueConnectionFactory factory = null;
	   ObjectMessage om = null;
	   try{
		   ctx = new InitialContext();
		   queue = (Queue) ctx.lookup(strEMailQueueName);
		   factory = (QueueConnectionFactory) ctx.lookup(strConnectionFactoryName);
		   cnn = factory.createQueueConnection();
		   session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		   om = session.createObjectMessage(policyVO);
		   sender = session.createSender(queue);
		   sender.send(om);
	   }//end of try
	   catch(Exception exp)
		{
			throw new TTKException(exp, "webservice");
		}//end of catch(Exception exp)
		finally
		{
			try
			{
				try
				{
					sender.close();
				}catch(JMSException jmsExp)
				{
					throw new TTKException(jmsExp, "webservice");
				}//end of catch(JMSException exp)
				finally
				{
					try
					{
						cnn.stop();
					}catch(JMSException jmsExp)
					{
						throw new TTKException(jmsExp, "webservice");
					}//end of catch(JMSException exp)
					finally
					{
						try
						{
							session.close();
						}catch(JMSException jmsExp)
						{
							throw new TTKException(jmsExp, "webservice");
						}//end of catch(JMSException exp)
						finally
						{
							try
							{
								cnn.close();
							}catch(JMSException jmsExp)
							{
								throw new TTKException(jmsExp, "webservice");
							}//end of catch(JMSException exp)
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			finally
			{
				sender=null;
				cnn=null;
				session=null;
			}//end of finally
		}//end of finally
    }//end of addToPolicyQueue(String strPolicy)
       
    /**
     * This method method returns the String 
     * @param String object which contains the strVidalID for which data in is required.
     * @param String object which contains thestrIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */

	public String individualLoginService(String strVidalID,String strIMEINumber)throws TTKException {
		String strResult="";
		String strMessage="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE LOGIN PAGE DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strIndividualLoginService);
			cStmtObject.setString(1,strVidalID);
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(3,Types.VARCHAR);
			cStmtObject.setString(4,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No Data Found";
			strMessage =cStmtObject.getString(3);
			log.info("INSIDE LOGIN PAGE DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl individualLoginService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl individualLoginService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
    }//end of individualLoginService(String strVidalID,StrIMEINumber)

    
       
    /**
     * This method method returns the String or saves a xml in the desired given location
     * @param String object which contains the strVidalID for which data in is required.
     * @param String object which contains thestrIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */

	public String getXmlandSaveXml(String strInsID)throws TTKException {
		
		String strResult		=	"";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		 OracleCallableStatement stmt = null;
		try {
			 conn = ResourceManager.getConnection();
//	          conn = ResourceManager.getWebserviceConnection();
	            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strFetchXml);

	            XMLType policyXML = null;
	            stmt.registerOutParameter(1,OracleTypes.OPAQUE,"SYS.XMLTYPE");
	            stmt.execute();
	            policyXML = XMLType.createXML(stmt.getOPAQUE(1));
	            strResult = policyXML.getStringVal();
	            
	            /*stmt.setString(1, "603900");  
	            stmt.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");  
	            stmt.registerOutParameter(3,Types.INTEGER); 
	            stmt.execute();
	            policyXML = XMLType.createXML(stmt.getOPAQUE(2));*/
	            
	            strResult = policyXML.getStringVal();
	            
	        	
	        	
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl getXmlandSaveXml()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getXmlandSaveXml()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
    }//end of getXmlandSaveXml(String strVidalID,StrIMEINumber)
	/**
     * This method method returns the String Buffer details of Ecard
     * @param String object which contains the Vidal Id for which data in is required.
     * @param String object which contains the strIMEINumber for which data in is required.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public StringBuffer getEcardTemplate(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException {
			String strTemplate="";
			String strPolicyGrpSeqId="";
			String strMemSeqId="";
			Connection conn = null;
			StringBuffer strTemplateData=new StringBuffer();
			CallableStatement cStmtObject=null;
			try {
				log.info("INSIDE ECARD SERVICES DAO START");
				conn = ResourceManager.getConnection();

				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTemplateInfo);
				cStmtObject.setString(1,strPolicyNumber);
				cStmtObject.setString(2,strVidalId);
				cStmtObject.registerOutParameter(3,Types.VARCHAR);
				cStmtObject.registerOutParameter(4,Types.VARCHAR);
				cStmtObject.registerOutParameter(5,Types.VARCHAR);
				cStmtObject.setString(6,strIMEINumber);
				cStmtObject.execute();
		 		strPolicyGrpSeqId = cStmtObject.getString(3);
				strMemSeqId = cStmtObject.getString(4);
				strTemplate = cStmtObject.getString(5);
				strTemplateData=(strTemplate !=null)?strTemplateData.append(strPolicyGrpSeqId).append(",").append(strTemplate).append(",").append(strMemSeqId):new StringBuffer("NA");
				log.info("INSIDE ECARD SERVICES DAO END");
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "webservice");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "webservice");
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
						log.error("Error while closing the Statement in WebServiceDAOImpl feedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WebServiceDAOImpl feedBackService()",sqlExp);
							throw new TTKException(sqlExp, "webservice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "webservice");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
			return strTemplateData;
	}//end of getEcardTemplate(String strVidalId, String strPolicyNumber,String strIMEINumber)

		
	/**
	 * This method method returns theSuccess Message
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @param String object which contains the Diagnostic Details for which data in is required.
	 * @param String object which contains the ClaimAmount for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */

	public String  claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber)throws TTKException {
		String strResult="";
		String strIntimationId="";//new change 27 jan2014
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE CLAIM INTIMATION  DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimIntimationService);
			cStmtObject.setString(1,strPolicyNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.setString(3,strDiagnosticDetails);
			cStmtObject.setString(4,strClaimAmount);
			cStmtObject.setString(5,strHospitalName);
			cStmtObject.setString(6,strHospitalAddress);
			cStmtObject.setString(7,strDtAdmission);
			cStmtObject.setString(8,strDtDischarge);
			cStmtObject.registerOutParameter(9,Types.INTEGER);
			cStmtObject.setString(10,strIMEINumber);
			cStmtObject.registerOutParameter(11,Types.VARCHAR);
			//v_clm_intimation_id   out tpa_call_claim_intimation.clm_intimation_id%type)
			cStmtObject.execute();
			   if(cStmtObject.getString(11)!=null || (!cStmtObject.getString(11).equalsIgnoreCase("")))
			{
			strIntimationId=cStmtObject.getString(11);
			}
		//	strIntimationId=(!cStmtObject.getString(11).equalsIgnoreCase(""))? cStmtObject.getString(11) : "";
			//v_clm_intimation_id   out tpa_call_claim_intimation.clm_intimation_id%type)

			strResult =(cStmtObject.getInt(9)>0)?"Intimation Updated Successfully,Please find your Intimation&#"+strIntimationId+"&#":"Intimation Not Updated";
	

			strResult =(cStmtObject.getInt(9)>0)?"Intimation Updated Successfully,Please find your Intimation&#"+strIntimationId+"&#":"Intimation Not Updated";
			log.info("INSIDE CLAIM INTIMATION  DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl claimsService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl claimsService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
    }//end of   claimIntimationService(String strVidalId,String strPolicyNumber,String strDiagnosticDetails,String strClaimAmount,String strHospitalName,String strHospitalAddress,String strDtAdmission,String strDtDischarge,String strIMEINumber)

	/**
	 * This method method returns the List of Claims and Policies as a form of String
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the strIMEINumber for which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String policySearchService(String strVidalId,String strIMEINumber)throws TTKException {
		String strResult="";
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE POLICY SEARCH DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPolicySearchService);
			cStmtObject.setString(1,strVidalId);
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.setString(3,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No Data Found";
			log.info("INSIDE POLICY SEARCH DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl policySearchService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl policySearchService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of policySearchService(String strVidalId,String strIMEINumber)

	/**
	 * This method method returns the Claim Details
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @param String object which contains the strMode for which data in is required.
	 * @param String object which contains the strIMEINumberfor which data in is required.
	 * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String claimHistoryService(String strVidalId, String strPolicyNumber,String strMode,String strIMEINumber)throws TTKException {
		String strResult="";
		String mode="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE CLAIM HISTORY DAO START");
			if(strMode.equalsIgnoreCase("PREAUTH"))
			{
			mode="PAT";
			}
			else if(strMode.equalsIgnoreCase("CLAIM"))
			{
			mode="CLM";
			}
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimHistoryService);
			cStmtObject.setString(1,strPolicyNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.setString(3,mode);
            
			cStmtObject.registerOutParameter(4,Types.VARCHAR);
			cStmtObject.setString(5,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(4)!=null)?(String)cStmtObject.getString(4):"No Data Found";
			log.info("INSIDE CLAIM HISTORY DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl claimHistoryService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl claimHistoryService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of String claimHistoryService(String strVidalId, String strPolicyNumber,String strMode,String strIMEINumber)

	/**
	 * This method method returns the Claim status as a String
	 * @param String object which contains the Vidal Id for which data in is required.
	 * @param String object which contains the Policy Number for which data in is required.
	 * @param String object which contains the Claim Number for which data in is required.
	 * @param String object which contains the Imei Number for which data in is required.
	 
     * @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String claimStatusService(String strVidalId, String strPolicyNumber,String strClaimNumber,String strIMEINumber)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE CLAIM STATUS DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimStatusService);
			cStmtObject.setString(1,strPolicyNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.setString(3,strClaimNumber);
			cStmtObject.registerOutParameter(4,Types.VARCHAR);
			
			cStmtObject.setString(5,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(4)!=null)?(String)cStmtObject.getString(4):"No Data Found";
			
			log.info("INSIDE CLAIM STATUS DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl claimStatusService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl claimStatusService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of claimStatusService(String strVidalId, String strPolicyNumber,String strClaimNumber,String strIMEINumber)

	
  /**
   * This method method returns the Text 
   * @param String object which contains the Vidal Id for which data in is required.
   *  @param String object which contains the IMEI Nyumber for which data in is required.
   * @param String object which contains the HospID for which data in is required.
   *  @param int  which contains the int rating for which data in is required.
   *  @return String object which contains Rule Errors.
   * @exception throws TTKException.
   */
	public String feedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating)throws TTKException {
		String strResult="";
		int iResult;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE FEEDBACK SERVICES DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFeedBackServiceWithNewChanges);
			cStmtObject.setString(1,strHospId.trim());//SODPID
			cStmtObject.setString(2,strVidalId.trim());//VIDAL ID
			cStmtObject.setInt(3,intRating);//Feed Back Rating
			cStmtObject.registerOutParameter(4,Types.INTEGER);
			cStmtObject.setString(5,strIMEINumber);//IMEI NUMBER
			cStmtObject.execute();
			strResult =(cStmtObject.getInt(4)>0)?"FeedBack Updated Successfully":"FeedBack Not Updated";
			log.info("INSIDE FEEDBACK SERVICES DAO END");
			//int ir  = cStmtObject.getInt(4);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl feedBackService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl feedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end offeedBackService(String strVidalId,String strIMEINumber,String strHospId,int intRating)
	

	/**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     *  @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getDependentDetailsService(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE DEPENDENT DETAILS DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDependentInfo);
			cStmtObject.setString(1,strPolicyNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.registerOutParameter(3,Types.VARCHAR);
			cStmtObject.setString(4,strIMEINumber);
			cStmtObject.execute();

			
			strResult =(cStmtObject.getString(3)!=null)?(String)cStmtObject.getString(3):"No Data Found";
			log.info("INSIDE DEPENDENT DETAILS DAO END");
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl feedBackService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl feedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getDependentDetailsService(String strVidalId, String strPolicyNumber,String strIMEINumber)



	 /**
     * This method returns the list of hospitals matches with longitude and latitude 
     * @param int object which contains the Start Number for which data in is required.
     * @param int object which contains the End Number for which data in is required.
     * @param String object which contains the intLatitude for which data in is required.
     * @param String object which contains the intLongitude for which data in is required.
     * @param String  object which contains the Operator for which data in is required.
     * @param double object which contains the kilometers for which data in is required.
     * @param String  object which contains the IMEi Number for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	 
	public String getHospInfoService(int startNum,int endNum,String intLatitude ,String intLongitude,String strOperator,double dbKilometers,String strIMEINumber)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE HOSPITAL INFORMATION DAO START");
			conn = ResourceManager.getConnection();

			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospitalInfo);
			cStmtObject.setInt(1,startNum);
			cStmtObject.setInt(2,endNum);
			cStmtObject.setString(3,intLatitude.trim());
			cStmtObject.setString(4,intLongitude.trim());
			cStmtObject.setString(5,strOperator);//lessthan,greaterThan,less than or equal to
			cStmtObject.setDouble(6,dbKilometers);
			cStmtObject.registerOutParameter(7,Types.VARCHAR);
			cStmtObject.setString(8,strIMEINumber);
			cStmtObject.execute();
			
			strResult =(cStmtObject.getString(7)!=null)?(String)cStmtObject.getString(7):"No Data Found";
			log.info("INSIDE HOSPITAL INFORMATION DAO END");
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl getHospInfoService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getHospInfoService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID)
	
		
	/**
	 * This method method returns the Text 
	 * @param String object which contains the searchValue for which data in is required.
	 * @param int object which contains the startNum Id for which data in is required.
	 * @param int object which contains the intNoOfRows for which data in is required.
	 * @param String object which contains the strIMEINumber Id for which data in is required.
	 *  @param String object which contains the strStateID for which data in is required.
	 * @param String object which contains the strCityID Id for which data in is required.
	* @return String object which contains Rule Errors.
	 * @exception throws TTKException.
	 */
	public String getHospDetailsService(String searchValue,int startNum,int intNoOfRows,String strIMEINumber,String strStateID,String strCityID)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE HOSPITAL DETAILS DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospitalDetails);
			cStmtObject.setString(1,searchValue);
			cStmtObject.setInt(2,startNum);
			cStmtObject.setInt(3,intNoOfRows);
			cStmtObject.registerOutParameter(4,Types.VARCHAR);
			cStmtObject.setString(5,strIMEINumber);
			cStmtObject.setString(6,strStateID);
			cStmtObject.setString(7,strCityID);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(4)!=null)?(String)cStmtObject.getString(4):"No Data Found";
			log.info("INSIDE HOSPITAL DETAILS DAO START");

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl feedBackService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl feedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getHospDetailsService(int intLatitude ,int intLongitude)

	 /**
     * This method method returns the Text (tip_title,tip_text,tip_color)
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */	
	public String dailyTipService(String strDate,String strIMEINumber) throws TTKException  {
			String dailyTipData="";
			Connection conn = null;
			CallableStatement cStmtObject=null;
			
			try {
				log.info("INSIDE DAILY TIPS DAO START");
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDailyTipInfo);
				cStmtObject.setString(1,strDate);
				cStmtObject.registerOutParameter(2,Types.VARCHAR);
				cStmtObject.setString(3,strIMEINumber);
				cStmtObject.execute();
				dailyTipData =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No Tip Found";
				log.info("INSIDE DAILY TIPS DAO END");

			}
					
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "webservice");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "webservice");
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
						log.error("Error while closing the Statement in WebServiceDAOImpl dailyTipService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WebServiceDAOImpl dailyTipService()",sqlExp);
							throw new TTKException(sqlExp, "webservice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "webservice");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
			return dailyTipData;
	}//end of dailyTipService(String strDate,String strIMEINumber)
		
		
    /**
     * This method method returns the byte[] 
     * @param String object which contains the searchValue for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
		public byte[] dailyTipImageService(String strDate,String strIMEINumber) throws TTKException  {
			BLOB photo = null;
			byte[] data=null;
			Connection conn = null;
			StringBuffer strTemplateData=new StringBuffer();
			CallableStatement cStmtObject=null;
			
			try {
				log.info("INSIDE DAILY TIPS IMAGE DAO START");
				conn = ResourceManager.getConnection();

				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDailyTipImage);
				cStmtObject.setString(1,strDate);
				
				cStmtObject.registerOutParameter(2,Types.BLOB);
				cStmtObject.setString(3,strIMEINumber);
				cStmtObject.execute();
				
				 photo = (BLOB) cStmtObject.getBlob(2);
			//    
           
			if(photo!=null)			{
				
				InputStream in = photo.getBinaryStream();
				int length = (int) photo.length();
				data=photo.getBytes(1,length);
			}  
			else{	data=null;}	
			log.info("INSIDE DAILY TIPS IMAGE DAO END");
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "webservice");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "webservice");
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
						log.error("Error while closing the Statement in WebServiceDAOImpl dailyTipService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WebServiceDAOImpl dailyTipService()",sqlExp);
							throw new TTKException(sqlExp, "webservice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "webservice");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
			return data;
		}//end of dailyTipImageService(String strDate,String strIMEINumber) 
		

    /**
     * This method method returns the Text 
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String docAroundClockStatusService()throws TTKException{
		String strResult="";
		String strIMEINumber="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDocAroundClockStatus);
			cStmtObject.setString(1,"");
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.setString(3,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No Data Found";
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl docAroundClockStatusService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl docAroundClockStatusService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of docAroundClockStatusService()

	/**
     * This method method returns the String (Claim FeedBack Updated Successfully or not)
     * @param String object which contains the strVidalId for which data in is required search Criteria.
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
  	 * @param String object which contains the strClaimNumber for which data in is required search Criteria.
     * @param String object which contains the strFeedback for which data in is required search Criteria.
 	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String claimFeedBackService(String strVidalId,String strIMEINumber, String strClaimNumber, String strFeedback)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE CLAIM FEEDBACK SERVICES DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimFeedBack);
			cStmtObject.setString(1,strClaimNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.setString(3,strFeedback);
			cStmtObject.registerOutParameter(4,Types.VARCHAR);
			cStmtObject.setString(5,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(4)!=null)?"Claim FeedBack Updated Successfully":"Claim Feedback not Updated";
			log.info("INSIDE CLAIM FEEDBACK SERVICES DAO END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl claimfeedBackService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl claimfeedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of claimFeedBackService(String strVidalId,String strIMEINumber, String strClaimNumber, String strFeedback)



    /**
     * This method method returns the Text 
     * @param String object which contains the strExpertType for which data in is required search Criteria.
     * @param String object which contains the strUserEmail for which data in is required search Criteria.
	 * @param String object which contains the strUserQuery for which data in is required search Criteria.
     * @param String object which contains the strVidalId for which data in is required search Criteria.
	  * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	 
	public String askExpertOpnionService(String strExpertType,String strUserEmail, String strUserQuery, String strVidalId,String strIMEINumber) throws TTKException
	{
	String strResult="";

		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE EXPERT OPNION DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAskExpertOpnionService);
			cStmtObject.setString(1,strExpertType);
			cStmtObject.setString(2,strUserEmail);
			cStmtObject.setString(3,strUserQuery);
			cStmtObject.setString(4,strVidalId);
			cStmtObject.registerOutParameter(5,Types.VARCHAR);
			cStmtObject.setString(6,strIMEINumber);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(5)!=null)?(String)cStmtObject.getString(5):"No Data Found";
			log.info("INSIDE EXPERT OPNION DAO END");

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl askExpertOpnionService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl askExpertOpnionService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of askExpertOpnionService(String strExpertType,String strUserEmail, String strUserQuery, String strVidalId,String strIMEINumber) throws TTKException

	
	/**
     * This method method returns the Text 
     * @param String object which contains the strExpertType for which data in is required search Criteria.
     * @param String object which contains the strUserEmail for which data in is required search Criteria.
	 * @param String object which contains the strUserQuery for which data in is required search Criteria.
     * @param String object which contains the strVidalId for which data in is required search Criteria.
	  * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	 
	public String getValidatePhoneNumber(String strValue) throws TTKException
	{
	String strResult="";

		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE VALIDATE PHONE NUMBER START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strValidatePhoneNum);
			cStmtObject.setString(1,strValue);
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No customer is enrolled with given mobile no. Please contact Customer Care";
			log.info("INSIDE VALIDATE PHONE NUMBER END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl getValidatePhoneNumber()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getValidatePhoneNumber()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getValidatePhoneNumber(String strValue) throws TTKException


	
	/**
     * This method method returns the Text 
     * @param String object which contains the strExpertType for which data in is required search Criteria.
     * @param String object which contains the strUserEmail for which data in is required search Criteria.
	 * @param String object which contains the strUserQuery for which data in is required search Criteria.
     * @param String object which contains the strVidalId for which data in is required search Criteria.
	  * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	 
	public String getProviderList(String genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws TTKException
	{
	String strResult="";

		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE GET PROVIDER LIST START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProviderList);
			
			if(genTypeId!=null)
				cStmtObject.setString(1,genTypeId);
			else
				cStmtObject.setString(1,null);
			
			if(hospName!=null)
				cStmtObject.setString(2,hospName);
			else
				cStmtObject.setString(2,null);
			
			if(StateTypeID!=null)
				cStmtObject.setString(3,StateTypeID);
			else
				cStmtObject.setString(3,null);
			
			if(cityDesc!=null)
				cStmtObject.setString(4,cityDesc);
			else
				cStmtObject.setString(4,null);
			
			if(location!=null)
				cStmtObject.setString(5,location);
			else
				cStmtObject.setString(5,null);
			
			cStmtObject.registerOutParameter(6,Types.CLOB);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(6)!=null)?(String)cStmtObject.getString(6):"Customer is In-Active.Please contact Customer Care.";
			log.info("INSIDE GET PROVIDER LIST END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl getProviderList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getProviderList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getProviderList(Long genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws TTKException



	

	
	/**
     * 
     * @param String object which contains the strIMEINumber for which data in is required search Criteria.
	 * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	 
	public String getDiagTests(Long hospSeqId) throws TTKException
	{
	String strResult="";

		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE GET DIAG LIST START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDiagList);
			
			if(hospSeqId!=null)
				cStmtObject.setLong(1,hospSeqId);
			else
				cStmtObject.setString(1,null);
			
			cStmtObject.registerOutParameter(2,Types.CLOB);
			cStmtObject.execute();
			strResult =(cStmtObject.getString(2)!=null)?(String)cStmtObject.getString(2):"No Tariff.";
			log.info("INSIDE GET DIAG LIST END");
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl getProviderList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getProviderList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getProviderList(Long genTypeId, String hospName, String StateTypeID, String cityDesc, String location) throws TTKException
	
	
	
	
	/**
     * This method method returns the Text 
     * @param String object which contains the Vidal Id for which data in is required.
     *  @param String object which contains the strPolicyNumber for which data in is required.
     *  @param String object which contains the strIMEINumber for which data in is required.
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	public String getPreAuthClaimsCount(String strVidalId, String strPolicyNumber,String strIMEINumber)throws TTKException {
		String strResult="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			log.info("INSIDE PREAUTN/CLAIMS COUNT DAO START");
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthClmCount);
			cStmtObject.setString(1,strPolicyNumber);
			cStmtObject.setString(2,strVidalId);
			cStmtObject.registerOutParameter(3,Types.VARCHAR);
			cStmtObject.setString(4,strIMEINumber);
			cStmtObject.execute();

			
			strResult =(cStmtObject.getString(3)!=null)?(String)cStmtObject.getString(3):"No Data Found";
			log.info("INSIDE PREAUTN/CLAIMS COUNT DAO END");
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
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
					log.error("Error while closing the Statement in WebServiceDAOImpl feedBackService()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl feedBackService()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strResult;
	}//end of getPreAuthClaimsCount(String strVidalId, String strPolicyNumber,String strIMEINumber)

	public String getPolicyDetailsXML(String insSeqID,String batchSeqId,String userName,String password, byte[] policyList) throws TTKException
    {
		
		 String strResult="";
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        FileOutputStream fos=null;
	        BufferedOutputStream bout=null;
		
		try{
			
			
			if(policyList==null||policyList.length<1){		
				return getErrorXml("xml content empty","<PolicyDetailsList>","</PolicyDetailsList>");		
			}
			SAXReader saxReader=new SAXReader();
			
			Document document1=saxReader.read(new StringReader(new String(policyList)));
			
			List nodeList=document1.selectNodes("//PolicyNumber");
			if(nodeList==null||nodeList.size()<1){
				return getErrorXml("xml content empty","<PolicyDetailsList>","</PolicyDetailsList>");
			}
			
			
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");

			File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyNumberXmlBatchFile"));
			if(!batchFile.exists())batchFile.mkdir();
			String strBatchFileName="PolicyBatch-"+sf.format(Calendar.getInstance().getTime())+".xml";
			batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);
			
			    fos=new FileOutputStream(batchFile);
			     bout=new BufferedOutputStream(fos);
				bout.write(policyList);
				
			//	if(policyList!=null&&policyList.length>0) for(int i=0;i<policyList.length;i++)document.append((char)policyList[i]);
						
            conn = ResourceManager.getConnection();
       
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strgetPolicyDetails);
            XMLType policyXML = null;
            if(policyList != null)
            {
                policyXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), new String(policyList));
               
            }//end of if(document != null)
            XMLType xml = null;
            cStmtObject.setString(1,strBatchFileName);
            cStmtObject.setString(2,insSeqID);
            cStmtObject.setString(3,userName);
            cStmtObject.setObject(4,password);
            cStmtObject.setObject(5,policyXML);
            cStmtObject.registerOutParameter (6, OracleTypes.OPAQUE,"SYS.XMLTYPE"); 
			cStmtObject.execute();
		 
		     xml = (XMLType) cStmtObject.getObject(6); 
		     
			
			strResult =(xml!=null)?xml.getStringVal():"Policy Data Not Available.";
         
        }//end of try
        catch (SQLException sqlExp)
        {
        	sqlExp.printStackTrace();
            //throw new TTKException(sqlExp, "webservice");
        	return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
        	exp.printStackTrace();
           // throw new TTKException(exp, "webservice");
        	return getErrorXml(exp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
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
        			sqlExp.printStackTrace();
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        			//throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
               
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        				if(bout!=null&&fos!=null)
        				{
                			bout.flush();
        					bout.close();
        					fos.close();
        				}
        			}//end of try
        			catch (IOException e) {
    					// TODO Auto-generated catch block
                	  
    					e.printStackTrace();
    					return getErrorXml(e.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
    				}
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        				//throw new TTKException(sqlExp, "webservice");
        				sqlExp.printStackTrace();
        				return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (Exception exp)
        	{
        		exp.printStackTrace();
        		//return getErrorXml("6"+exp.getMessage());
        		//throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of getPolicyDetailsXML(String document)

	
	
	public String getAllPolicyDetailsXML(String insuranceSeqID,String userName,String password) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        XMLType xml=null;
        try {
            conn = ResourceManager.getConnection();
            //conn = ResourceManager.getWebserviceConnection();
            //PolicyVO policyVO = new PolicyVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strgetAllPolicyDetails);
            cStmtObject.setString(1,insuranceSeqID);
            cStmtObject.setString(2,userName);
            cStmtObject.setString(3,password);
            cStmtObject.registerOutParameter (4, OracleTypes.OPAQUE,"SYS.XMLTYPE"); 
			cStmtObject.execute();
		     xml = (XMLType) cStmtObject.getObject(4); 
			strResult =(xml!=null)?xml.getStringVal():getErrorXml("Policy Data Not Available.","<PolicyDetailsList>","</PolicyDetailsList>");
         
        }//end of try
        catch (SQLException sqlExp)
        {
           // throw new TTKException(sqlExp, "webservice");
        	sqlExp.printStackTrace();
        	 return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
        	exp.printStackTrace();
           // throw new TTKException(exp, "webservice");
           return getErrorXml(exp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
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
        			sqlExp.printStackTrace();
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			//throw new TTKException(sqlExp, "webservice");
        			 return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				sqlExp.printStackTrace();
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        			//	throw new TTKException(sqlExp, "webservice");
        				 return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (Exception exp)
        	{
        		exp.printStackTrace();
        	//	throw new TTKException(exp, "webservice");
        		// return getErrorXml(exp.getMessage());
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of getAllPolicyDetailsXML()
	
	public String createPolicyDetails(String insSeqID,String batchSeqID,String userName,String password,String PolicyType, byte[] policyData) throws TTKException
    {
		 StringBuilder finalLog=new StringBuilder();
		    XMLType xml;
		    String[] filePathsanDesc = null;
	        Connection conn = null;
	        CallableStatement userCheck=null;
	        CallableStatement cStmtObject=null;
	        CallableStatement saveDocStmt=null;
	        FileOutputStream fos=null;
	        BufferedOutputStream bout=null;
	        try {
	        	conn = ResourceManager.getConnection();
	        	
	        	userCheck=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.user_check_at_member_level(?,?,?,?)}");
	        	userCheck.setString(1,insSeqID);
	        	userCheck.setString(2,userName);
	        	userCheck.setString(3,password);
	        	userCheck.registerOutParameter(4,OracleTypes.VARCHAR);
	        	
	        	userCheck.execute();
	        	
	           if(!"Y".equalsIgnoreCase(userCheck.getString(4))) return getErrorXml("User ID or Password Incorect","<PolicyDetailsList>","</PolicyDetailsList>");
	          
	        	if(policyData==null||policyData.length<1) return getErrorXml("xml content empty","<PolicyDetailsList>","</PolicyDetailsList>");		
			
	        				
			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");

				File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyNumberXmlBatchFile"));
				if(!batchFile.exists())batchFile.mkdir();
				String strBatchFileName="PolicyBatch-"+sf.format(Calendar.getInstance().getTime())+".xml";
				batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);
				    fos=new FileOutputStream(batchFile);
				     bout=new BufferedOutputStream(fos);
					bout.write(policyData);
					bout.close();
					fos.close();
					this.getFinalLog(finalLog, strBatchFileName,"success");
					
					
					SAXReader reader=new SAXReader();
				
					//File ff=new File(TTKPropertiesReader.getPropertyValue("PolicyNumberXmlBatchFile")+strBatchFileName);
					FileInputStream fif=new FileInputStream(batchFile);
					Document document=reader.read(fif);
					fif.close();
					List policies=document.selectNodes("//PolicyList/Policy");
					
					
					 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strcreatePolicy);
					 saveDocStmt = (java.sql.CallableStatement)conn.prepareCall(strSavePolicyDoc);
					 if(policies!=null&&policies.size()>0)
					 {
					for (int i = 0; i < policies.size(); i++) {
						boolean flag=true;
						StringBuilder strResult=new StringBuilder();
						StringBuilder sb=new StringBuilder();
						sb.append("<?xml version=\"1.0\" encoding=\"WINDOWS-1252\"?>");
						sb.append("\n");
						sb.append("<PolicyList>");
						sb.append("\n");
						Node policy=(Node) policies.get(i);
						
						Node attachmentNode=policy.selectSingleNode("./Attachments");
						String strTempAttachment=null;
				        if(attachmentNode!=null)
				        {
				        	strTempAttachment=attachmentNode.asXML();
				        	if(strTempAttachment!=null)
				        	{
								SAXReader s=new SAXReader();
								Document attchmentDoc=s.read(new StringReader(strTempAttachment));
								filePathsanDesc=saveAttachment(attchmentDoc); //saving attachment
								if (filePathsanDesc[1].equalsIgnoreCase("error"))
									{
									flag=false;
									 strResult.append(getAttachemntLog(filePathsanDesc[0],"<PolicyDetails>", "</PolicyDetails>",policy.selectSingleNode("PolicyNumber").getText(),policy.selectSingleNode("SerialNo").getText())).toString();
									 //return finalLog.append(strResult).toString();
									}
				        	}
							
				        	attachmentNode.detach();
				        }
				        if(flag)
				        {
				        sb.append(policy.asXML()+"</PolicyList>");
				    XMLType policyXML = null;
				         if(sb != null&&sb.length()!=0)
				         {
				             policyXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), sb.toString());
				            
				         }//end of if(document != null)
				         cStmtObject.setString(1,strBatchFileName);
				         cStmtObject.setString(2,insSeqID);
				         cStmtObject.setString(3,userName);
				         cStmtObject.setObject(4,password);
				         cStmtObject.setObject(5,PolicyType);
				         cStmtObject.setObject(6,policyXML);
				         
				         cStmtObject.registerOutParameter (7, OracleTypes.OPAQUE,"SYS.XMLTYPE"); 
							cStmtObject.execute();
						     xml = (XMLType) cStmtObject.getObject(7); 
							//strResult =(xml!=null)?xml.getStringVal():return getErrorXml("Policy Data Not Available.","<PolicyDetailsList>","</PolicyDetailsList>");
				         if(xml!=null)
				         {
				        	 String gg=xml.getStringVal();
				        document=reader.read(new StringReader(gg));
				        String strStatus=document.selectSingleNode("//PolicyDetailsList/PolicyDetails/Status").getText();
						if(strStatus!=null&&"1".equals(strStatus))
						{
						if(filePathsanDesc!=null&&filePathsanDesc.length==2){
							//SAXReader s=new SAXReader();
							//Document attchmentDoc=s.read(new StringReader(strTempAttachment));
							//String[] filePathsanDesc=saveAttachment(attchmentDoc); //saving attachment
							
							saveDocStmt.setString(1, policy.selectSingleNode("PolicyNumber").getText());
							saveDocStmt.setString(2, policy.selectSingleNode("Category").getText());
							saveDocStmt.setString(3, policy.selectSingleNode("ProductID").getText());
							saveDocStmt.setString(4, policy.selectSingleNode("GroupID").getText());
							saveDocStmt.setString(5, policy.selectSingleNode("SubGroupID").getText());
							saveDocStmt.setString(6, filePathsanDesc[0]);
							saveDocStmt.setString(7, filePathsanDesc[1]);
							saveDocStmt.registerOutParameter(8,OracleTypes.INTEGER);
							saveDocStmt.executeUpdate();
							//calling saveDocStmt prodedure
							
						}
				   }
						strResult.append(document.selectSingleNode("//PolicyDetailsList/PolicyDetails").asXML());
					}
						else strResult.append(getErrorXml("Policy Data Not Available.","<PolicyDetailsList>","</PolicyDetailsList>"));
				         
					}
				         finalLog.append(strResult);
				         finalLog.append("\n");
				         
					}
	        }
					 else{
						 finalLog=new StringBuilder();
						 getFinalLog(finalLog, strBatchFileName,"Please Provide Proper Xml Data");
					 }
					finalLog.append("\n");
					finalLog.append("</PolicyDetailsList>");
				
     }//end of try
     catch (SQLException sqlExp)
     {
     	sqlExp.printStackTrace();
         //throw new TTKException(sqlExp, "webservice");
     	return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
     }//end of catch (SQLException sqlExp)
     catch (Exception exp)
     {
     	exp.printStackTrace();
        // throw new TTKException(exp, "webservice");
     	return getErrorXml(exp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
     }//end of catch (Exception exp)
		
     finally
		{
     	/* Nested Try Catch to ensure resource closure */
     	try // First try closing the Statement
     	{
     		try
     		{
     			if(userCheck!=null)userCheck.close();
     			if (cStmtObject != null) cStmtObject.close();
     			if(saveDocStmt!=null)saveDocStmt.close();
     		}//end of try
     		catch (SQLException sqlExp)
     		{
     			sqlExp.printStackTrace();
     			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
     			return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
     			//throw new TTKException(sqlExp, "webservice");
     		}//end of catch (SQLException sqlExp)
            
     		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
     		{
     			try
     			{
     				if(conn != null) conn.close();
     				if(bout!=null&&fos!=null)
     				{
             			bout.flush();
     					bout.close();
     					fos.close();
     				}
     			}//end of try
     			catch (IOException e) {
 					// TODO Auto-generated catch block
             	  
 					e.printStackTrace();
 					return getErrorXml(e.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
 				}
     			catch (SQLException sqlExp)
     			{
     				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
     				//throw new TTKException(sqlExp, "webservice");
     				sqlExp.printStackTrace();
     				return getErrorXml(sqlExp.getMessage(),"<PolicyDetailsList>","</PolicyDetailsList>");
     			}//end of catch (SQLException sqlExp)
     		}//end of finally Connection Close
     	}//end of try
     	catch (Exception exp)
     	{
     		exp.printStackTrace();
     		//return getErrorXml("6"+exp.getMessage());
     		//throw new TTKException(exp, "webservice");
     	}//end of catch (TTKException exp)
     	finally // Control will reach here in anycase set null to the objects
     	{
     		cStmtObject = null;
     		saveDocStmt=null;
     		conn = null;
     	}//end of finally
		}//end of finally
	       
     return finalLog.toString();
 }//end of getPolicyDetailsXML(String document)

	
	
	public String memberUpload(String insCode,String userName,String password, byte[] memberData) throws TTKException
    {
		       	//StringBuilder document=new StringBuilder();
			    String strResult="";
		        Connection conn = null;
		        CallableStatement cStmtObject=null;
		        FileOutputStream fos=null;
		        BufferedOutputStream bout=null;
		     
	        try {
	        	conn=ResourceManager.getConnection();
	        	cStmtObject=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.user_check_at_member_level(?,?,?,?)}");
	        	cStmtObject.setString(1,insCode);
	        	cStmtObject.setString(2,userName);
	        	cStmtObject.setString(3,password);
	        	cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR);
	        	
	        	cStmtObject.execute();
	        	
	            if("Y".equalsIgnoreCase(cStmtObject.getString(4)))
	            {
	        	if(memberData==null||memberData.length<1)
	        	{		
					return getValidationXml("xml content empty");		
				}
	         	SAXReader saxReader=new SAXReader();
				Document document=saxReader.read(new StringReader(new String(memberData)));
				SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");
			    String batchNO=sf.format(new java.util.Date())+"-"+insCode;
				File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyNumberXmlBatchFile"));
				if(!batchFile.exists())batchFile.mkdir();
				String strBatchFileName=batchNO+".xml";
				batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);
				fos=new FileOutputStream(batchFile);
			    bout=new BufferedOutputStream(fos);
			    bout.write(memberData);
				bout.flush();fos.flush();fos.close();bout.close();
	            strResult= UploadMembers.uploadMembers(document,batchNO);     
	        	}
	        	else
	        	{
	        		return getValidationXml("user id or password Incorect");
	        	}
	    		         
	           
	    		       
     }//end of try
     catch (Exception exp)
     {
     	exp.printStackTrace();
        // throw new TTKException(exp, "webservice");
     	return getValidationXml(exp.getMessage());
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
     			sqlExp.printStackTrace();
     			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
     			return getValidationXml(sqlExp.getMessage());
     			//throw new TTKException(sqlExp, "webservice");
     		}//end of catch (SQLException sqlExp)
            
     		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
     		{
     			try
     			{
     				if(conn != null) conn.close();
     				if(bout!=null&&fos!=null)
     				{
             			bout.flush();
     					bout.close();
     					fos.close();
     				}
     			}//end of try
     			catch (IOException e) {
 					// TODO Auto-generated catch block
             	  
 					e.printStackTrace();
 					return getValidationXml(e.getMessage());
 				}
     			catch (SQLException sqlExp)
     			{
     				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
     				//throw new TTKException(sqlExp, "webservice");
     				sqlExp.printStackTrace();
     				return getValidationXml(sqlExp.getMessage());
     			}//end of catch (SQLException sqlExp)
     		}//end of finally Connection Close
     	}//end of try
     	catch (Exception exp)
     	{
     		exp.printStackTrace();
     		//return getErrorXml("6"+exp.getMessage());
     		//throw new TTKException(exp, "webservice");
     		//return getValidationXml(exp.getMessage());
     	}//end of catch (TTKException exp)
     	finally // Control will reach here in anycase set null to the objects
     	{
     		cStmtObject = null;
     		conn = null;
     	}//end of finally
		}//end of finally
	       
     return strResult;
 }//end of getPolicyDetailsXML(String document)

	public String policyDocumentUpload(String insCode,String userName,String password,String policyNumber,String format, byte[] policyDocument) throws TTKException
    {
		//StringBuilder document=new StringBuilder();
		    String strResult="";
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        FileOutputStream fos=null;
	        BufferedOutputStream bout=null;
	     
	        try {
	       	conn=ResourceManager.getConnection();
	       	cStmtObject=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.user_check_at_member_level(?,?,?,?)}");
	       	cStmtObject.setString(1,insCode);
	       	cStmtObject.setString(2,userName);
	       	cStmtObject.setString(3,password);
	       	cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR); 	
	       	cStmtObject.execute(); 	
	        if("Y".equalsIgnoreCase(cStmtObject.getString(4)))
	        {
	      	if(policyDocument==null||policyDocument.length<1){		
			return getValidationXml("xml content empty");		
			}
	    	SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");	
			String batchNO=sf.format(new java.util.Date())+"-"+insCode;
			File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyDocumentStorage"));
			if(!batchFile.exists())batchFile.mkdir();
			String strBatchFileName=batchNO+"."+format;
			batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);	
		    fos=new FileOutputStream(batchFile);
		    bout=new BufferedOutputStream(fos);
			bout.write(policyDocument);
    	   // strResult= UploadMembers.uploadMembers(document,batchNO);
	        return "uploaded succesfully";    	     
	      	}
	       	else
	       	{
	       	return getValidationXml("user id or password Incorect");
	       	}
	    		         
	           
	    		       
     }//end of try
     catch (Exception exp)
     {
     	exp.printStackTrace();
        // throw new TTKException(exp, "webservice");
     	return exp.getMessage();
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
     			sqlExp.printStackTrace();
     			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
     			return sqlExp.getMessage();
     			//throw new TTKException(sqlExp, "webservice");
     		}//end of catch (SQLException sqlExp)
            
     		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
     		{
     			try
     			{
     				if(conn != null) conn.close();
     				if(bout!=null&&fos!=null)
     				{
             			bout.flush();
     					bout.close();
     					fos.close();
     				}
     			}//end of try
     			catch (IOException e) {
 					// TODO Auto-generated catch block
             	  
 					e.printStackTrace();
 					return e.getMessage();
 				}
     			catch (SQLException sqlExp)
     			{
     				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
     				//throw new TTKException(sqlExp, "webservice");
     				sqlExp.printStackTrace();
     				return getValidationXml(sqlExp.getMessage());
     			}//end of catch (SQLException sqlExp)
     		}//end of finally Connection Close
     	}//end of try
     	catch (Exception exp)
     	{
     		exp.printStackTrace();
     		//return getErrorXml("6"+exp.getMessage());
     		//throw new TTKException(exp, "webservice");
     		//return getValidationXml(exp.getMessage());
     	}//end of catch (TTKException exp)
     	finally // Control will reach here in anycase set null to the objects
     	{
     		cStmtObject = null;
     		conn = null;
     	}//end of finally
		}//end of finally
	       
    
 }//end of getPolicyDetailsXML(String document)
	public String memberPhotoUpload(String insCode,String userName,String password,String enrollementNo,String format,String enrollmentNo, byte[] memberPhoto) throws TTKException
    {
		//StringBuilder document=new StringBuilder();
		 String strResult="";
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        FileOutputStream fos=null;
	        BufferedOutputStream bout=null;
	     
	        try {
	        	conn=ResourceManager.getConnection();
	        	cStmtObject=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.user_check_at_member_level(?,?,?,?)}");
	        	cStmtObject.setString(1,insCode);
	        	cStmtObject.setString(2,userName);
	        	cStmtObject.setString(3,password);
	        	cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR);	        	
	        	cStmtObject.execute();	        	
	            if("Y".equalsIgnoreCase(cStmtObject.getString(4)))
	            {
	        	if(memberPhoto==null||memberPhoto.length<1)
	        	{		
					return "ImageData is Empty";		
				}	       	
				SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");
				String batchNO=sf.format(new java.util.Date())+"-"+insCode;	
				ByteArrayInputStream bai=new ByteArrayInputStream(memberPhoto);
				File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyDocumentStorage"));
				if(!batchFile.exists())batchFile.mkdir();
				String strBatchFileName=batchNO+"."+format;
				batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);
			    fos=new FileOutputStream(batchFile);
			    bout=new BufferedOutputStream(fos);
				bout.write(memberPhoto);
		        return "uploaded succesFully";     
	        	}
	        	else
	        	{
	        		return "user id or password Incorect";
	        	}	       
     }//end of try
     catch (Exception exp)
     {
     	exp.printStackTrace();
        // throw new TTKException(exp, "webservice");
     	return exp.getMessage();
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
     			sqlExp.printStackTrace();
     			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
     			return sqlExp.getMessage();
     			//throw new TTKException(sqlExp, "webservice");
     		}//end of catch (SQLException sqlExp)
            
     		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
     		{
     			try
     			{
     				if(conn != null) conn.close();
     				if(bout!=null&&fos!=null)
     				{
             			bout.flush();
     					bout.close();
     					fos.close();
     				}
     			}//end of try
     			catch (IOException e) {
 					// TODO Auto-generated catch block
             	  
 					e.printStackTrace();
 					return e.getMessage();
 				}
     			catch (SQLException sqlExp)
     			{
     				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
     				//throw new TTKException(sqlExp, "webservice");
     				sqlExp.printStackTrace();
     				return sqlExp.getMessage();
     			}//end of catch (SQLException sqlExp)
     		}//end of finally Connection Close
     	}//end of try
     	catch (Exception exp)
     	{
     		exp.printStackTrace();
     		//return getErrorXml("6"+exp.getMessage());
     		//throw new TTKException(exp, "webservice");
     		//return getValidationXml(exp.getMessage());
     	}//end of catch (TTKException exp)
     	finally // Control will reach here in anycase set null to the objects
     	{
     		cStmtObject = null;
     		conn = null;
     	}//end of finally
		}//end of finally
	       
    
 }//end of getPolicyDetailsXML(String document)

	public String publishMemebers(String insCode,String userName,String password, byte[] memberData) throws TTKException
    {
		//StringBuilder document=new StringBuilder();
		    String strResult="";
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        FileOutputStream fos=null;
	        BufferedOutputStream bout=null;
	        StringBuilder bufferedWriter=new StringBuilder();
	        try {
	        	conn=ResourceManager.getConnection();
	        	cStmtObject=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.user_check_at_member_level(?,?,?,?)}");
	        	cStmtObject.setString(1,insCode);
	        	cStmtObject.setString(2,userName);
	        	cStmtObject.setString(3,password);
	        	cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR);
	        	cStmtObject.execute();
	            if("Y".equalsIgnoreCase(cStmtObject.getString(4)))
	            {
	        	if(memberData==null||memberData.length<1)
	        	{		
					return getErrorXml("xml content empty","<MemberDetailsList>","</MemberDetailsList>");		
				}
				SAXReader saxReader=new SAXReader();
				Document document=saxReader.read(new StringReader(new String(memberData)));
			    SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");
			    String batchNO=sf.format(new java.util.Date())+"-"+insCode;
				File batchFile=new File(TTKPropertiesReader.getPropertyValue("PolicyNumberXmlBatchFile"));
				if(!batchFile.exists())batchFile.mkdir();
				String strBatchFileName=batchNO+".xml";
				batchFile=new File(batchFile.getAbsolutePath()+"/"+strBatchFileName);
			    fos=new FileOutputStream(batchFile);
		        bout=new BufferedOutputStream(fos);
				bout.write(memberData);
				if(document!=null && document.hasContent())	strResult=PublishMembers.createTakafulFormat(document);
	    		       //strResult= UploadMembers.uploadMembers(document,batchNO);
	           }
	           else
	           {
	        	  return getErrorXml("user id or password Incorect","<MemberDetailsList>","</MemberDetailsList>");
	           }
	    		         
	    		       
     }//end of try
     catch (Exception exp)
     {
     	exp.printStackTrace();
        // throw new TTKException(exp, "webservice");
     	return getErrorXml(exp.getMessage(),"<MemberDetailsList>","</MemberDetailsList>");
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
     			sqlExp.printStackTrace();
     			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
     			return getErrorXml(sqlExp.getMessage(),"<MemberDetailsList>","</MemberDetailsList>");
     			//throw new TTKException(sqlExp, "webservice");
     		}//end of catch (SQLException sqlExp)
            
     		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
     		{
     			try
     			{
     				if(conn != null) conn.close();
     				if(bout!=null&&fos!=null)
     				{
             			bout.flush();
     					bout.close();
     					fos.close();
     				}
     			}//end of try
     			catch (IOException e) {
 					// TODO Auto-generated catch block
             	  
 					e.printStackTrace();
 					return getErrorXml(e.getMessage(),"<MemberDetailsList>","</MemberDetailsList>");
 				}
     			catch (SQLException sqlExp)
     			{
     				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
     				//throw new TTKException(sqlExp, "webservice");
     				sqlExp.printStackTrace();
     				return getErrorXml(sqlExp.getMessage(),"<MemberDetailsList>","</MemberDetailsList>");
     			}//end of catch (SQLException sqlExp)
     		}//end of finally Connection Close
     	}//end of try
     	catch (Exception exp)
     	{
     		exp.printStackTrace();
     		//return getErrorXml("6"+exp.getMessage());
     		//throw new TTKException(exp, "webservice");
	     	}//end of catch (TTKException exp)
     	finally // Control will reach here in anycase set null to the objects
     	{
     		cStmtObject = null;
     		conn = null;
     	}//end of finally
		}//end of finally
	       
     return strResult;
 }//end of getMemberDeails

	public String publishProductDetails(String insuranceSeqID,String userName,String password) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        XMLType xml=null;
        try {
        	if(insuranceSeqID!=null&&insuranceSeqID.length()>0)
        	{
            conn = ResourceManager.getConnection();
            //conn = ResourceManager.getWebserviceConnection();
            //PolicyVO policyVO = new PolicyVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.Takaful_products_publishing(?,?,?,?)}");
            cStmtObject.setString(1,insuranceSeqID);
            cStmtObject.setString(2,userName);
            cStmtObject.setString(3,password);
            cStmtObject.registerOutParameter (4, OracleTypes.OPAQUE,"SYS.XMLTYPE"); 
			cStmtObject.execute();
		     xml = (XMLType) cStmtObject.getObject(4); 
			strResult =(xml!=null)?xml.getStringVal():getErrorXml("Data Not Available.","<ProductDetailsList>","</ProductDetailsList>");
        	}
        	else
        	{
        		 return getErrorXml("insuranceLicenceID Id is required","<ProductDetailsList>","</ProductDetailsList>");
        	}
         
        }//end of try
        catch (SQLException sqlExp)
        {
           // throw new TTKException(sqlExp, "webservice");
        	sqlExp.printStackTrace();
        	 return getErrorXml(sqlExp.getMessage(),"<ProductDetailsList>","</ProductDetailsList>");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
        	exp.printStackTrace();
           // throw new TTKException(exp, "webservice");
           return getErrorXml(exp.getMessage(),"<ProductDetailsList>","</ProductDetailsList>");
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
        			sqlExp.printStackTrace();
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			//throw new TTKException(sqlExp, "webservice");
        			 return getErrorXml(sqlExp.getMessage(),"<ProductDetailsList>","</ProductDetailsList>");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				sqlExp.printStackTrace();
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        			//	throw new TTKException(sqlExp, "webservice");
        				 return getErrorXml(sqlExp.getMessage(),"<ProductDetailsList>","</ProductDetailsList>");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (Exception exp)
        	{
        		exp.printStackTrace();
        	//	throw new TTKException(exp, "webservice");
        		// return getErrorXml(exp.getMessage());
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of publishProductDetails
	
	public String publishCorporateDetails(String insuranceSeqID,String userName,String password) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        XMLType xml=null;
        try {
        	if(insuranceSeqID!=null && insuranceSeqID.length()>0)
        	{
            conn = ResourceManager.getConnection();
            //conn = ResourceManager.getWebserviceConnection();
            //PolicyVO policyVO = new PolicyVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.Takaful_Corporates_publishing(?,?,?,?)}");
            cStmtObject.setString(1,insuranceSeqID);
            cStmtObject.setString(2,userName);
            cStmtObject.setString(3,password);
            cStmtObject.registerOutParameter (4, OracleTypes.OPAQUE,"SYS.XMLTYPE");
			cStmtObject.execute();
		    xml = (XMLType) cStmtObject.getObject(4);
			strResult =(xml!=null)?xml.getStringVal():getErrorXml("Data Not Available.","<CorporateDetailsList>","</CorporateDetailsList>");
        	}
        	else {
        		return getErrorXml("insuranceLicenceID is required","<CorporateDetailsList>","</CorporateDetailsList>");
        	}
            }//end of try
        catch (SQLException sqlExp)
        {
           // throw new TTKException(sqlExp, "webservice");
        	 sqlExp.printStackTrace();
        	 return getErrorXml(sqlExp.getMessage(),"<CorporateDetailsList>","</CorporateDetailsList>");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
        	exp.printStackTrace();
           // throw new TTKException(exp, "webservice");
           return getErrorXml(exp.getMessage(),"<CorporateDetailsList>","</CorporateDetailsList>");
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
        			sqlExp.printStackTrace();
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			//throw new TTKException(sqlExp, "webservice");
        			 return getErrorXml(sqlExp.getMessage(),"<CorporateDetailsList>","</CorporateDetailsList>");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				sqlExp.printStackTrace();
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        			//	throw new TTKException(sqlExp, "webservice");
        				 return getErrorXml(sqlExp.getMessage(),"<CorporateDetailsList>","</CorporateDetailsList>");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (Exception exp)
        	{
        		exp.printStackTrace();
        	//	throw new TTKException(exp, "webservice");
        		// return getErrorXml(exp.getMessage());
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return strResult;
    }//end of getAllPolicyDetailsXML()
private  void getFinalLog(StringBuilder finalLog,String strBatchFileName,String discription)
{
	finalLog.append("<PolicyDetailsList>");
	finalLog.append("\n");
	finalLog.append("<TransactionDetails>");
	finalLog.append("\n");
	finalLog.append("<Id>");
	finalLog.append(strBatchFileName);
	finalLog.append("</Id>");
	finalLog.append("\n");
	finalLog.append("<Status>");
	finalLog.append("1");
	finalLog.append("</Status>");
	finalLog.append("\n");
	finalLog.append("<Description>");
	finalLog.append(discription);
	finalLog.append("</Description>");
	finalLog.append("\n");
	finalLog.append("</TransactionDetails>");
	
}
	private String getErrorXml(String errorDesc,String strRootS,String strRootE){
		StringBuilder builder=new StringBuilder();
		
		String strVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			
			
			String strTxtS="<TransactionDetails>";
			String strTxtE="</TransactionDetails>";	
			String strStatusS="<Status>";
			String strStatusE="</Status>";
			String strDescS="<Description>";
			String strDescE="</Description>";
			

			builder.append(strVersion);
			builder.append("\n");
			builder.append(strRootS);
			builder.append("\n");
			builder.append(strTxtS);
			builder.append("\n");
			builder.append(strStatusS);
			builder.append("0");
			builder.append(strStatusE);
			builder.append("\n");
			builder.append(strDescS);
			builder.append(errorDesc);
			builder.append(strDescE);
			builder.append("\n");
			builder.append(strTxtE);
			builder.append("\n");
			
			builder.append(strRootE);
			return builder.toString();
	}
	private String getAttachemntLog(String errorDesc,String strRootS,String strRootE,String policyNumber,String srNo){
		StringBuilder builder=new StringBuilder();
		
	//	String strVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			
			
			String strTxtS="<PolicyNumber>";
			String strTxtE="</PolicyNumber>";	
			String srNoS="<SerialNo>";
			String srNoE="</SerialNo>";
			String strStatusS="<Status>";
			String strStatusE="</Status>";
			String strDescS="<StatusDesc>";
			String strDescE="</StatusDesc>";
			

			//builder.append(strVersion);
			//builder.append("\n");
			builder.append(strRootS);
			builder.append("\n");
			builder.append("   "+strTxtS);
			builder.append(policyNumber);
			builder.append(strTxtE);
			builder.append("\n");
			builder.append("   "+srNoS);
			builder.append(srNo);
			builder.append(srNoE);
			builder.append("\n");
			builder.append("   "+strStatusS);
			builder.append("0");
			builder.append(strStatusE);
			builder.append("\n");
			builder.append("   "+strDescS);
			builder.append(errorDesc);
			builder.append(strDescE);
			builder.append("\n");
			
			builder.append(strRootE);
			return builder.toString();
	}

	private String getValidationXml(String errorDesc){
		StringBuilder builder=new StringBuilder();
		
			String strVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			String strRootS="<MemberDetailsList>";
			String strRootE="</MemberDetailsList>";
			String strTxtS="<TransactionDetails>";
			String strTxtE="</TransactionDetails>";	
			String strStatusS="<Status>";
			String strStatusE="</Status>";
			String strDescS="<Description>";
			String strDescE="</Description>";
			builder.append(strVersion);
			builder.append("\n");
			builder.append(strRootS);
			builder.append("\n");
			builder.append(strTxtS);
			builder.append("\n");
			builder.append(strStatusS);
			builder.append("0");
			builder.append(strStatusE);
			builder.append("\n");
			builder.append(strDescS);
			builder.append(errorDesc);
			builder.append(strDescE);
			builder.append("\n");
			builder.append(strTxtE);
			
			builder.append(strRootE);
			return builder.toString();
	}

	private String[] saveAttachment(Document attchmentDoc) throws IOException
	{   
		String[] result=new String[2];
		FileOutputStream fout=null;
		BufferedOutputStream bout=null;
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-HHmmssSSS");
		String  batchFileLocation=TTKPropertiesReader.getPropertyValue("PolicyDocumentStorage");

		String filePaths="";
		String Discriptions="";
		
		List Attachments=attchmentDoc.selectNodes("//Attachments/File");
		for (int j = 0; j <Attachments.size(); j++) {
			Node fileNode=(Node) Attachments.get(j);
			if(fileNode!=null){
				String	fileName=fileNode.valueOf("@name");
				String	fileType=fileNode.valueOf("@Type");
				String	description=fileNode.valueOf("@description");
				Node documNode=fileNode.selectSingleNode("./Document");
				
				if(documNode!=null )
				{
					String strContent=documNode.getText();
					
					//System.out.println("strContent::"+strContent);
					
					if(fileName!=null&&fileType!=null&&("pdf".equalsIgnoreCase(fileType)||"jpg".equalsIgnoreCase(fileType)||"png".equalsIgnoreCase(fileType)||"jpeg".equalsIgnoreCase(fileType)||"txt".equalsIgnoreCase(fileType)||"doc".equalsIgnoreCase(fileType)))
					{
						if(strContent!=null&&strContent.length()>0 )
						{
					byte[] documentData=DatatypeConverter.parseBase64Binary(strContent);//base64Code
					String strFileName=fileName+"-"+sf.format(Calendar.getInstance().getTime())+"-"+j+"."+fileType;
					fout=new FileOutputStream(new File(batchFileLocation+strFileName));
					fout.write(documentData);
					//bout=new BufferedOutputStream(fout);
					//bout.write(documentData);
					fout.flush();
					//bout.flush();
				   //bout.close();
					fout.close();
					filePaths+=strFileName+"@";
					Discriptions+=description+""+"@";
						}
					}
					else {result[0]="please check File name or Type (File name Should not be empty and File Type should be |txt|pdf|doc|jpj|png|jpeg)";
					result[1]="error";
					return result;
					}
				}
				
			}
			
			
		}
		result[0]=filePaths;
		result[1]=Discriptions;
		return result;
	}
		/*public String getCityStateList()throws TTKException
		{
		String strResult="";
		Document document=null;
		XMLType xml=null;
		Connection conn = null;
		OracleCallableStatement cStmtObject= null;
		try {
			conn = ResourceManager.getConnection();
			//          conn = ResourceManager.getWebserviceConnection();
			cStmtObject= (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCity_StateListService);
			cStmtObject.registerOutParameter(1,OracleTypes.OPAQUE,"SYS.XMLTYPE");
			cStmtObject.execute();
			//strResult =(cStmtObject.getString(1)!=null)?(String)cStmtObject.getString(1):"No Data Found";

			xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(1));
			DOMReader domReader = new DOMReader();
			document = xml != null ? domReader.read(xml.getDOM()):null;
			document.asXML();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
		}//end of catch (Exception exp)

		finally
		{
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in WebServiceDAOImpl getCityStateList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getCityStateList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return document.asXML();
	}//end of getCityStateList(String strVidalID,String strIMEINUmber)
*/

	/**
     * This method method returns the xml Document 
     * * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */

	/*		
	public Document getCityStateList1()throws TTKException
		{
		String strResult="";
		Document document=null;
		XMLType xml=null;
		Connection conn = null;
		OracleCallableStatement cStmtObject= null;
		try {
			conn = ResourceManager.getConnection();
			//          conn = ResourceManager.getWebserviceConnection();
			cStmtObject= (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCity_StateListService);
			cStmtObject.registerOutParameter(1,OracleTypes.OPAQUE,"SYS.XMLTYPE");
			cStmtObject.execute();
			//strResult =(cStmtObject.getString(1)!=null)?(String)cStmtObject.getString(1):"No Data Found";

			xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(1));
			DOMReader domReader = new DOMReader();
			document = xml != null ? domReader.read(xml.getDOM()):null;
			document.asXML();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
		}//end of catch (Exception exp)

		finally
		{
		
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in WebServiceDAOImpl getCityStateList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getCityStateList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return document;
	}//end of getCityStateList1()
	
	
	
	public Document getStateList()throws TTKException
	{
		String strResult="";
		Document document=null;
		XMLType xml=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strStateListService);
			cStmtObject.registerOutParameter(1,OracleTypes.OPAQUE,"SYS.XMLTYPE");
			cStmtObject.execute();
			xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(1));
			DOMReader domReader = new DOMReader();
			document = xml != null ? domReader.read(xml.getDOM()):null;
			document.asXML();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
		}//end of catch (Exception exp)

		finally
		{
			
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in WebServiceDAOImpl getCityStateList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getCityStateList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return document;
	}//end of getStateList()
	
	
	/**
     * This method method returns the xml Document 
     * @param String object which contains the state type id
     * @return String object which contains Rule Errors.
     * @exception throws TTKException.
     */
	/*public Document getCityList(String strStateTypeId)throws TTKException
	{
		Document document=null;
		XMLType xml=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCityListService);
			cStmtObject.setString(1, strStateTypeId);
			cStmtObject.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
			cStmtObject.execute();
			xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(2));
			DOMReader domReader = new DOMReader();
			document = xml != null ? domReader.read(xml.getDOM()):null;
			document.asXML();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webservice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webservice");
		}//end of catch (Exception exp)

		finally
		{
			
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in WebServiceDAOImpl getCityList()",sqlExp);
					throw new TTKException(sqlExp, "webservice");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebServiceDAOImpl getCityList()",sqlExp);
						throw new TTKException(sqlExp, "webservice");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webservice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return document;
	}//end of getCityList()

*/    
	
	
	
}//end of WebServiceDAOImpl
