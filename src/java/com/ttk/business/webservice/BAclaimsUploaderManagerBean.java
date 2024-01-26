/**
 * @ (#) WebServiceManagerBean.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServiceManagerBean.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.webservice;




import java.math.BigDecimal;
//import java.rmi.RemoteException;

//import java.util.HashMap;
//import java.util.Map;


//import javax.xml.rpc.holders.BigDecimalHolder;
//import javax.xml.rpc.holders.StringHolder;

import org.apache.log4j.Logger;


import com.ttk.common.exception.TTKException;


//import com.ttk.common.webservices.BjazWebServiceAll_wsdl.BjazWebServiceAllProxy;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.common.CommunicationDAOFactory;
import com.ttk.dao.impl.common.CommunicationDAOImpl;




import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;




@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class BAclaimsUploaderManagerBean implements BAclaimsUploaderManager{

    private CommunicationDAOFactory communicationDAOFactory = null;
	private CommunicationDAOImpl communicationDAO = null;
	//BjazWebServiceAllProxy bjazWebServiceAllProxy=null;
	private static final Logger log = Logger.getLogger( BAclaimsUploaderManagerBean.class);
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getCommunicationDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(communicationDAOFactory == null)
			{
				communicationDAOFactory = new CommunicationDAOFactory();
			}//end of if (communicationDAOFactory == null)
			if(strIdentifier.equals("communication"))
			{
				return communicationDAOFactory.getDAO("communication");
			}//end of if(strIdentifier.equals("enrollment"))
			else
			{
				return null;
			}//end of else
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getCommunicationDAO(String strIdentifier)

 public String saveXmlFromBajaj(String xmlString,String filename,BigDecimal errorCode) throws TTKException
   {
	   communicationDAO = (CommunicationDAOImpl)this.getCommunicationDAO("communication");
	   return	communicationDAO.saveXmlFromBajaj(xmlString,filename,errorCode);
   }//end of insertRecord(String saveXmlFromBajaj)
  
   
  /* public Map getResponseFromBajaj(String pUserId,String pPassKey,String pProcessFlag,String pTieupName,StringHolder pInputDataXmlStr_inout,BigDecimalHolder pErrorCode_out) throws TTKException, RemoteException
   {
	   bjazWebServiceAllProxy=new BjazWebServiceAllProxy();
		HashMap outParams=(HashMap)bjazWebServiceAllProxy.getBjazWebServiceAll_PortType().bjazSingleWsForAll(pUserId, pPassKey, pProcessFlag, pTieupName, pInputDataXmlStr_inout, pErrorCode_out);
		String resultXml=(String)outParams.get("pInputDataXmlStr_inout");
		BigDecimal resultErrorCode=(java.math.BigDecimal)outParams.get("pErrorCode_out");
		
	   return outParams;
   }//end of insertRecord(String saveXmlFromBajaj)
*/   

}// End of WebServiceManagerBean
