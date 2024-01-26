/**
 * @ (#) FAXHelper.java 25th May 2007
 * Project      : TTK HealthCare Services
 * File         : FAXHelper.java
 * Author       : Balakrishna.E
 * Company      : Span Systems Corporation
 * Date Created : 25th May 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.common.messageservices;

import gnu.hylafax.HylaFAXClient;
import gnu.hylafax.HylaFAXClientProtocol;
import gnu.hylafax.Pagesize;
import gnu.hylafax.job.Job;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;

import org.apache.log4j.Logger;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.common.CommunicationDAOImpl;
import com.ttk.dto.common.CommunicationOptionVO;

public class FAXHelper {


	private static Logger log = Logger.getLogger( FAXHelper.class ); // Getting Logger for this Class file

	private static String strFaxHost = null;

	public FAXHelper()
	{
	}//end of FAXHelper()

	/**
	 * This Method sends the FAX to the specified Recipient
	 *
	 * @param mapMsg the javax.jms.MapMessage object which contains the from, to, subject, body and File attachment details
	 * @return boolean true if FAX was successfully sent.
	 * @exception throws TTKException
	 */
	public boolean sendFax(javax.jms.MapMessage mapMsg) throws TTKException
	{
		CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
		String strFrom = "";
		String strTo = "";
		String strSubject = "";
		String strBody = "";
		String strMsgID = "";
		boolean bHasAttachment = false;
		String strFileName = "";
		String strRcptSeqID = "";
		String strNotify = "";
		String strMailStatus ="SNT";
//		String strRemarks ="";
//		String strGeneralTypeID ="";
		ArrayList<String> alFaxStatusInfo = new ArrayList<String>();
		
		strFaxHost = TTKPropertiesReader.getPropertyValue("FAXHOST");
		String strFaxUser = TTKPropertiesReader.getPropertyValue("FAXUSER");
		String strFaxPassword = TTKPropertiesReader.getPropertyValue("FAXPASSWORD");
		String strKillTime = TTKPropertiesReader.getPropertyValue("KILLTIME");
		String strModemName = TTKPropertiesReader.getPropertyValue("MODEMNAME");
		String strModemValue = TTKPropertiesReader.getPropertyValue("MODEMVALUE");
		String strJobInfo = TTKPropertiesReader.getPropertyValue("JOBINFO");
		
		int intPort = Integer.parseInt(TTKPropertiesReader.getPropertyValue("HYLAFAXPORT"));
		int intPageWidth = Integer.parseInt(TTKPropertiesReader.getPropertyValue("PAGEWIDTH"));
		int intPageHeight = Integer.parseInt(TTKPropertiesReader.getPropertyValue("PAGEHEIGHT"));
		int intMaxDails = Integer.parseInt(TTKPropertiesReader.getPropertyValue("MAXIMUMDIALS"));
		int intMaxTries = Integer.parseInt(TTKPropertiesReader.getPropertyValue("MAXIMUMTRIES"));
		int intChopThreshold = Integer.parseInt(TTKPropertiesReader.getPropertyValue("CHOPTHRESHOLD"));
		int intPriority = Integer.parseInt(TTKPropertiesReader.getPropertyValue("PRIORITY"));
		
		HylaFAXClient hylaFaxClient= new HylaFAXClient();
		InputStream ipStream =null;
		Long lngJobID = null;
		FileDataSource objFileDataSource = null;
		Pagesize pageSize = new Pagesize();
		try
		{
			strFrom = mapMsg.getString("From");
			strTo = mapMsg.getString("To");
			strSubject = mapMsg.getString("Subject");
			strBody = mapMsg.getString("Body");
			strNotify = mapMsg.getString("Notify");
			strFileName = mapMsg.getString("AttachmentName");
			strRcptSeqID = mapMsg.getString("RcptSeqID");
			strMsgID = mapMsg.getString("MsgID");
			String strHasAttachment = TTKCommon.checkNull(mapMsg.getString("HasAttachment"));
			bHasAttachment = Boolean.getBoolean(strHasAttachment);
			log.debug("From 		:"+strFrom);
			log.debug("To		    :"+strTo);
			log.debug("Subject	    :"+strSubject);
			log.debug("Body		    :"+strBody);
			log.debug("Notify	    :"+strNotify);
			log.debug("RcptSeqID	:"+strRcptSeqID);
			log.debug("MsgID		:"+strMsgID);
			log.debug("bHasAttachment		:"+bHasAttachment);			
			log.debug("In FAXHelper strHasAttachment="+strHasAttachment);
			
			hylaFaxClient.open(strFaxHost,intPort);
			if(hylaFaxClient.user(strFaxUser))
			{
				hylaFaxClient.pass(strFaxPassword);
			}//end of if
			hylaFaxClient.noop();
			hylaFaxClient.tzone(HylaFAXClientProtocol.TZONE_LOCAL);
			hylaFaxClient.jparm(strModemName,strModemValue);
			pageSize.setSize(intPageWidth,intPageHeight);
			Job job =(Job)hylaFaxClient.createJob();
			//String strfuc= cOptionVO.getFilePathName()+cOptionVO.getEnrollNum()+".pdf";
			if(strHasAttachment.equalsIgnoreCase("true"))
			{
				String strfuc= "";
				if(strMsgID.equals("PREAUTH_SHORTFALL") || strMsgID.equals("CLAIM_SHORTFALL")){
					strfuc= TTKPropertiesReader.getPropertyValue("shortfallrptdir")+strFileName;
				}//end of if(strMsgID.equals("PREAUTH_SHORTFALL") || strMsgID.equals("CLAIM_SHORTFALL"))
				else if(strMsgID.equals("PREAUTH_APPROVED")){
					strfuc= TTKPropertiesReader.getPropertyValue("authorizationrptdir")+strFileName;
				}//end of else if(strMsgID.equals("PREAUTH_APPROVED"))				
				objFileDataSource = new FileDataSource(strfuc);
				try{
					//URL url = new URL(strfuc);
					DataHandler dh = new DataHandler(objFileDataSource);
					ipStream = dh.getInputStream();
				}catch(MalformedURLException malurlexp)
				{
					throw new TTKException(malurlexp, "malformedurl");
				}//end of catch
				String strFilename= hylaFaxClient.putTemporary(ipStream);
				job.addDocument(strFilename);
			}//end of if(strHasAttachment.equalsIgnoreCase("true"))
			job.setDialstring(strTo);
			job.setKilltime(strKillTime);
			job.setMaximumDials(intMaxDails);
			job.setMaximumTries(intMaxTries);
			job.setPageDimension(pageSize);
			job.setNotifyType(job.NOTIFY_DONE);
			job.setNotifyAddress(strNotify);
			job.setChopThreshold(intChopThreshold);
			job.setFromUser(strFrom);
			lngJobID = job.getId();
			job.setJobInfo(strJobInfo);
			job.setPriority(intPriority);
			log.debug("Fax no.  		:"+job.getDialstring());
			log.debug("Document  		:"+job.getDocumentName());
			log.debug("Job  			:"+hylaFaxClient.job());
			log.debug("NotifyAddress  	:"+job.getNotifyAddress());
			log.debug("FromUser			:"+job.getFromUser());
			log.debug("Job ID			:"+job.getId());
			
			alFaxStatusInfo.add(strRcptSeqID);
			alFaxStatusInfo.add(strMailStatus);
			alFaxStatusInfo.add(lngJobID.toString());
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");			

			hylaFaxClient.submit(job);
//			communicationDAOImpl.messageStatusChange(strRcptSeqID, lngJobID.toString());
//			communicationDAOImpl.messageStatusChangePr(strRcptSeqID, strMailStatus, lngJobID.toString(), strRemarks, strGeneralTypeID);
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
		}//end of try
		catch (Exception ex) {
			throw new TTKException(ex, "error.hylafaxserver");
		}//end of catch (Exception ex)
		finally
		{
			if (hylaFaxClient == null) {
				return false;
			}//end of if (hylaFaxClient == null)
			try {
				if(hylaFaxClient !=null)
				{
					hylaFaxClient.quit();
				}//end of if(hylaFaxClient !=null)
			}//end of try
			catch (Exception ex) {
				throw new TTKException(ex, "error.hylafaxserver");
			}//end of catch (Exception ex)
			finally
			{
				hylaFaxClient = null;
			}//end of finally
		}//end of finally
		return true;
	}//end of sendfax(javax.jms.MapMessage mapMsg)

	/**
	 * This method builds a MapMessage object and sends to the specified JMS Queue
	 * @param cOptionVO The CommunicationOptionVO Object which contains the details of From&To address,Subject,Body and File name
	 * @exception throws TTKException
	 */
	public void sendFAX(CommunicationOptionVO cOptionVO) throws TTKException
	{
		String strFAXQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADFAXQUEUENAME");
		//String strFAXQueueName = TTKPropertiesReader.getPropertyValue(strQueueName);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue faxQueue = null;
		QueueSender sender = null;
		//File objFile = cOption.getFile();
		MapMessage mapMsg = null;
		try {
//			FileInputStream inStream = null;
//			FileOutputStream outStream = null;
			String strFileName = "";
			strFileName = cOptionVO.getFilePathName();
			log.info("file name is :"+strFileName);
//			String strfuc = "";
			//Save the file passed in the server
			/*if(cOptionVO.getFile().isFile())
			{
				strFileName = cOptionVO.getFile().getName();
				log.info("file name is :"+strFileName);
				inStream = new FileInputStream(cOptionVO.getFile());
				if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL") || cOptionVO.getMsgID().equals("CLAIM_SHORTFALL")){
					strfuc = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+cOptionVO.getFilePathName();
				}//end of if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL"))
				else if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED")){
					strfuc = TTKPropertiesReader.getPropertyValue("authorizationrptdir")+cOptionVO.getFilePathName();
				}//end of if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED"))
				outStream = new FileOutputStream(strfuc);
				int aData = 0;
				while((aData = inStream.read()) != -1)
				{
					outStream.write(aData);
				}//end of while((aData = inStream.read()) != -1)
				inStream.close();
				outStream.close();
			}//end of if(cOptionVO.getFile().isFile())
			else
			{
				log.debug("inside file not exist in send fax method");
			}//end of else
*/			m_context = TTKCommon.getJMSInitialContext();
			conFactory = (QueueConnectionFactory) m_context.lookup(strConnectionFactoryName);
			connection = conFactory.createQueueConnection();
			connection.start();
			session =  connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			faxQueue = (Queue) m_context.lookup(strFAXQueueName);
			sender = session.createSender(faxQueue);
			mapMsg = session.createMapMessage();
			//mapMsg.setString("From", cOption.getFrom());
			mapMsg.setString("To", cOptionVO.getRcptFaxNos());
			mapMsg.setString("Subject", cOptionVO.getMsgTitle());
			mapMsg.setString("Body", cOptionVO.getMessage());
			mapMsg.setString("From", cOptionVO.getSentFrom());
			mapMsg.setString("Notify", cOptionVO.getSenRcptEmailList());
			mapMsg.setString("RcptSeqID", cOptionVO.getRcptSeqID());
			mapMsg.setString("MsgID", cOptionVO.getMsgID());
			mapMsg.setString("HasAttachment", "true");
			mapMsg.setString("AttachmentName", strFileName);

			sender.send(mapMsg);

			/*sender.close();
			connection.stop();
			session.close();
			connection.close();*/
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.jms");
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
					throw new TTKException(jmsExp, "error.jms");
				}//end of catch(JMSException exp)
				finally
				{
					try
					{
						connection.stop();
					}catch(JMSException jmsExp)
					{
						throw new TTKException(jmsExp, "error.jms");
					}//end of catch(JMSException exp)
					finally
					{
						try
						{
							session.close();
						}catch(JMSException jmsExp)
						{
							throw new TTKException(jmsExp, "error.jms");
						}//end of catch(JMSException exp)
						finally
						{
							try
							{
								connection.close();
							}catch(JMSException jmsExp)
							{
								throw new TTKException(jmsExp, "error.jms");
							}//end of catch(JMSException exp)
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			finally
			{
				sender=null;
				connection=null;
				session=null;
			}//end of finally
		}//end of finally
	}//end of sendFAX(CommunicationOptionVO cOption)

	/**
	 * This Method moves the messages from one queue to another queue
	 *
	 * @param mapMsg the MapMessage object which contains the messages
	 * @param strQueueName the String object which contains the queue name
	 * @exception throws TTKException
	 */
	public static void sendMessage(MapMessage mapMsg,String strQueueName)throws TTKException
	{
		//String strFAXQueueName = TTKPropertiesReader.getPropertyValue(strQueueName);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue faxQueue = null;
		QueueSender sender = null;
		try {
			m_context = TTKCommon.getJMSInitialContext();
			conFactory = (QueueConnectionFactory) m_context.lookup(strConnectionFactoryName);
			connection = conFactory.createQueueConnection();
			connection.start();
			session =  connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			faxQueue = (Queue) m_context.lookup(strQueueName);
			sender = session.createSender(faxQueue);
			sender.send(mapMsg);

			/*sender.close();
			connection.stop();
			session.close();
			connection.close();*/
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.jms");
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
					throw new TTKException(jmsExp, "error.jms");
				}//end of catch(JMSException exp)
				finally
				{
					try
					{
						connection.stop();
					}catch(JMSException jmsExp)
					{
						throw new TTKException(jmsExp, "error.jms");
					}//end of catch(JMSException exp)
					finally
					{
						try
						{
							session.close();
						}catch(JMSException jmsExp)
						{
							throw new TTKException(jmsExp, "error.jms");
						}//end of catch(JMSException exp)
						finally
						{
							try
							{
								connection.close();
							}catch(JMSException jmsExp)
							{
								throw new TTKException(jmsExp, "error.jms");
							}//end of catch(JMSException exp)
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			finally
			{
				sender=null;
				connection=null;
				session=null;
			}//end of finally
		}//end of finally
	}//End of sendMessage(MapMessage mapMsg,String strQueueName)

	/**
	 * Method that moves the messages from one queue to another queue
	 * @param strFromQueue the String object which contains the from queue name
	 * @param strToQueue the String object which contains the To queue name
	 * @exception throws TTKException
	 */
	public static void moveMessages(String strFromQueue, String strToQueue) throws TTKException
	{
		//String strFAXQueueName = TTKPropertiesReader.getPropertyValue(strFromQueue);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue faxQueue = null;
		QueueReceiver	queueReceiver = null;
		Message message = null;
		QueueBrowser queueBrowser = null;
		MapMessage mapMsg = null;
		try {
			m_context = TTKCommon.getJMSInitialContext();
			conFactory = (QueueConnectionFactory) m_context.lookup(strConnectionFactoryName);
			connection = conFactory.createQueueConnection();
			connection.start();
			session =  connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			faxQueue = (Queue) m_context.lookup(strFromQueue);
			queueReceiver = session.createReceiver(faxQueue);
			queueBrowser = session.createBrowser(faxQueue);
			int iCount = 0;
			for (Enumeration en = queueBrowser.getEnumeration() ; en.hasMoreElements();) {
				message = (Message)en.nextElement();
				iCount++;
			}//loop to find the no of messages

			for(int iLen=0;iLen<iCount;iLen++)
			{
				message = (Message)queueReceiver.receive();
				mapMsg = (MapMessage) message;
				mapMsg.acknowledge();
				sendMessage(mapMsg, strToQueue);
				log.debug("The message successfully moved from "+strFromQueue+" To "+strToQueue);
			}//for(int iLen=0;iLen<iCount;iLen++)

			/*connection.stop();
			session.close();
			connection.close();*/
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.jms");
		}//end of catch(Exception exp)
		finally
		{
			try
			{
				try
				{
					connection.stop();
				}catch(JMSException jmsExp)
				{
					throw new TTKException(jmsExp, "error.jms");
				}//end of catch(JMSException exp)
				finally
				{
					try
					{
						session.close();
					}catch(JMSException jmsExp)
					{
						throw new TTKException(jmsExp, "error.jms");
					}//end of catch(JMSException exp)
					finally
					{
						try
						{
							connection.close();
						}catch(JMSException jmsExp)
						{
							throw new TTKException(jmsExp, "error.jms");
						}//end of catch(JMSException exp)
					}//end of finally
				}//end of finally
			}//end of finally
			finally
			{
				connection=null;
				session=null;
			}//end of finally
		}//end of finally
	}//End of moveMessages(String strFromQueue, String strToQueue)
}//end of FAXHelper
