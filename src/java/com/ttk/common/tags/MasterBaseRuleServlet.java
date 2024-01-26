/**
 * @ (#) MasterBaseRuleServlet.java JAN 25, 2008
 * Project       : TTK HealthCare Services
 * File          : MasterBaseRuleServlet.java
 * Author        : UNNI VEMANCHERY MANA
 * Company       : Span INFOTECH
 * Date Created  : JAN 25, 2008
 *
 * @author       : UNNI VEMANCHERY MANA
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;

public class MasterBaseRuleServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( MasterBaseRuleServlet.class );
	MasterBaseRuleServlet masterBaseRuleServlet;
	private final String strAddBaseRule = "{CALL update_rule_display_values(?)}";
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		super.service(arg0, arg1);
	}//end of service(HttpServletRequest arg0, HttpServletResponse arg1)

	@Override
	public void destroy() {
		super.destroy();
		if(masterBaseRuleServlet !=null){
			masterBaseRuleServlet = null;
		}//end of if(masterBaseRuleServlet !=null)
	}//end of destroy
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.info("inside the init method MasterBaseRuleServlet");
		Document document = getXMLFile();
		Connection connection = null;
		try{
			//connection = getConnection();
			//saveMasterBaseRule(document,connection);
				Object[]ruleObj=loadRuleBenefitList();
				config.getServletContext().setAttribute("RuleBenefitList",ruleObj[0] );
				config.getServletContext().setAttribute("RuleBenefitNodeList",ruleObj[1] );
			
		}catch(TTKException ttke){
		 log.error(ttke.getStackTrace());
		}//end of catch(TTKException ttke)
	}//end of init method

	/**
	 * @Description This method returns a connection object 
	 * @return      java.SQL.Connection object
	 * @throws      TTKException
	 */
	private Connection getConnection() throws TTKException {
		return ResourceManager.getConnection();
	}//end of getConnection
	
	/**
	 * @Description This method returns a XML file from server location
	 * @return       file 
	 */
	private Document getXMLFile(){
		File file = null;
		Document xmlDocument = null;
		file = new File(TTKPropertiesReader.getPropertyValue("MasterBaseRuleXml"));
		try{
		xmlDocument = loadXMLDocument(file);
		}catch(Exception exp){
			TTKCommon.logStackTrace(exp);
		}//end of catch(Exception exp)
		return xmlDocument;
	}//end of getXMLFile()
	
	/**
	 * 
	 * @param f
	 * @return
	 * @throws TTKException
	 */
	private Document loadXMLDocument(File f) throws Exception {
		Document xmlDocument = null;
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = fact.newDocumentBuilder();
		DOMReader domReader = new DOMReader();
		xmlDocument = domReader.read(db.parse(f));//read method take w3c document and returns dom4j document
		//log.info("Document Read Sucessfully...");
		return xmlDocument;
	}//end of loadXMLDocument(File f)
	
	/**
	 * 
	 * @param connection
	 * @param stmt
	 * @throws TTKException
	 */
	private void closeResources(Connection connection,OracleCallableStatement stmt) throws TTKException{
		try 
		{
			try
			{
				if (stmt != null) stmt.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in MasterBaseRuleServlet closeResources()",sqlExp);
				throw new TTKException(sqlExp, "masterbaserule");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (stmt != null) stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MasterBaseRuleServlet closeResources()",sqlExp);
					throw new TTKException(sqlExp, "masterbaserule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(connection != null) connection.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in MasterBaseRuleServlet closeResources()",sqlExp);
						throw new TTKException(sqlExp, "masterbaserule");
					}//end of catch (SQLException sqlExp)
				}//end of finally
			}//end of finally
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "masterbaserule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			stmt = null;
			connection = null;
		}//end of finally
	}//end of closeResources(Connection connection,OracleCallableStatement stmt)
	
	private int saveMasterBaseRule(Document document,Connection connection) throws TTKException{
		int iStatus = 0;
		OracleCallableStatement stmt=null;
		try{
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)connection).getUnderlyingConnection()).prepareCall(strAddBaseRule);
			XMLType poXML = null;
			if(document != null)
				poXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)connection).getUnderlyingConnection()), document.asXML());
			stmt.setObject(1, poXML);
			stmt.execute();
			iStatus = 1;
			closeResources(connection,stmt);
		}//end of try
		catch(SQLException sqe){
			throw new TTKException(sqe,"masterbaserule");
		}//end of catch(SQLException sqe)
		return iStatus;
	}//end of saveMasterBaseRule(Document document,Connection connection)
	
	/**
	 * This method is used to have all the display nodes present in the XML file
	 * as HashMap.	 *
	 * 
	 * @return hmDisplayNodes HashMap of display nodes
	 * @throws TTKException
	 */
	private Object[] loadRuleBenefitList() throws TTKException
	{
		log.info("Inside loadBenefitList method-- Constructing Hashmap");
		LinkedHashMap<String,String> hmBenefitList=null;
		LinkedHashMap<String,Node> hmBenefitNodeList=null;
		   hmBenefitList=new LinkedHashMap<String,String>();
		   hmBenefitNodeList=new LinkedHashMap<String,Node>();
			Document document=TTKCommon.getDocument("BenefitRules.xml");
			
			if(document!=null){
				List<Node> beneNodes=document.selectNodes("//benefit");
				
				if(beneNodes!=null){
					for(Node beneNode:beneNodes){
						hmBenefitList.put(beneNode.valueOf("@gtid"), beneNode.valueOf("@name"));
						hmBenefitNodeList.put(beneNode.valueOf("@gtid"), beneNode);
					}
				}
			}
		Object[]ruleObj=new Object[2];
		
		ruleObj[0]=hmBenefitList;
		ruleObj[1]=hmBenefitNodeList;
		return ruleObj;
	}//end of loadBenefitNameList()
}//end of MasterBaseRuleServlet
