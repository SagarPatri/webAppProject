package com.ttk.common.messageservices;

import gnu.hylafax.HylaFAXClient;
import gnu.hylafax.HylaFAXClientProtocol;
import gnu.hylafax.Pagesize;
import gnu.hylafax.job.Job;

import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

public class NotificationHelper {

	private static final Logger log = Logger.getLogger(NotificationHelper.class );
	
	/**
	 * This method sends the fax to the Recipient using Hylafax server
	 * @param cOption The CommunicationOptionVO Object which contains the details of fax no.,From&To address,Subjectand File
	 * @exception throws TTKException
	 */
	public void sendFax(CommunicationOptionVO cOptionVO) throws TTKException
	{
		log.info("inside fax send method");
		CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
		FAXHelper faxHelper = new FAXHelper();
		String strFromQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADFAXQUEUENAME");
		String strToQueueName = TTKPropertiesReader.getPropertyValue("JMSFAXQUEUENAME");
		String strMailStatus ="SNT";
//		String strRemarks ="";
//		String strGeneralTypeID ="";
		String strfuc = "";
		String strFilename= "";
		FileDataSource objFileDataSource = null;
		ArrayList<String> alFaxStatusInfo = new ArrayList<String>();
		
		String strFaxHost = TTKPropertiesReader.getPropertyValue("FAXHOST");
		String strFaxUser = TTKPropertiesReader.getPropertyValue("FAXUSER");
		String strFaxPassword = TTKPropertiesReader.getPropertyValue("FAXPASSWORD");
		String strKillTime = TTKPropertiesReader.getPropertyValue("KILLTIME");
		String strJobInfo = TTKPropertiesReader.getPropertyValue("JOBINFO");
		String strModemName = TTKPropertiesReader.getPropertyValue("MODEMNAME");
		String strModemValue = TTKPropertiesReader.getPropertyValue("MODEMVALUE");
		
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
		Pagesize pageSize = new Pagesize();
		try
		{
			hylaFaxClient.open(strFaxHost,intPort);
			if(hylaFaxClient.user(strFaxUser))
			{
				hylaFaxClient.pass(strFaxPassword);
			}//end of if
			//perform server No Operation could be used as a keep-alive
			//hylaFaxClient.jparm("MODEM", cOptionVO.getModem());
			hylaFaxClient.noop();
			hylaFaxClient.tzone(HylaFAXClientProtocol.TZONE_LOCAL);
			hylaFaxClient.jparm(strModemName,strModemValue);
			pageSize.setSize(intPageWidth,intPageHeight);
			//create a new job in the server
			Job job =(Job)hylaFaxClient.createJob();
			//java.io.ByteArrayInputStream message = new java.io.ByteArrayInputStream(cOption.getFile().getBytes());
			if(cOptionVO.getFilePathName()!=null )
			{
				if(cOptionVO.getFilePathName().endsWith("pdf"))
				{
					if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL") || cOptionVO.getMsgID().equals("CLAIM_SHORTFALL")){
						strfuc = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+cOptionVO.getFilePathName();
					}//end of if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL"))
					else if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED")){
						strfuc = TTKPropertiesReader.getPropertyValue("authorizationrptdir")+cOptionVO.getFilePathName();
					}//end of if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED"))
					File fileObj = new File(strfuc);
					cOptionVO.setFile(fileObj);
					objFileDataSource = new FileDataSource(fileObj);
					ipStream = new DataHandler(objFileDataSource).getInputStream();
					strFilename= hylaFaxClient.putTemporary(ipStream);
					job.addDocument(strFilename);
				}//end of if(cOptionVO.getFilePathName().endsWith("pdf"))
			}//end of else if(cOptionVO.getFilePathName()!=null )
			job.setDialstring(cOptionVO.getRcptFaxNos());
			job.setKilltime(strKillTime);
			job.setMaximumDials(intMaxDails);
			job.setMaximumTries(intMaxTries);
			job.setPageDimension(pageSize);
			job.setNotifyType(job.NOTIFY_DONE);
			job.setNotifyAddress(cOptionVO.getSenRcptEmailList());
			job.setChopThreshold(intChopThreshold);
			job.setFromUser(cOptionVO.getSentFrom());
			job.setJobInfo(strJobInfo);
			job.setPriority(intPriority);
			lngJobID = job.getId();
			cOptionVO.setMsgJobID(lngJobID.toString());
			
			alFaxStatusInfo.add(cOptionVO.getRcptSeqID());
			alFaxStatusInfo.add(strMailStatus);
			alFaxStatusInfo.add(cOptionVO.getMsgJobID());
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			
			//put a file with a unique name
			//hylaFaxClient.put(ipStream);
			log.info("Fax no.  			:"+job.getDialstring());
			log.info("Document  		:"+job.getDocumentName());
			log.info("Job  				:"+hylaFaxClient.job());
			log.info("NotifyAddress  	:"+job.getNotifyAddress());
			log.info("FromUser			:"+job.getFromUser());
			log.info("PageDimension		:"+job.getPageDimension());
			log.info("Job ID			:"+job.getId());
			log.info("Retrytime  		:"+job.getRetrytime());
			log.info("Priority  		:"+job.getPriority());
			log.info("NOTIFY_REQUEUE  	:"+job.NOTIFY_REQUEUE);
			log.info("mdmfmt  			:"+hylaFaxClient.mdmfmt());
			
			//submit the given job to the scheduler
			hylaFaxClient.submit(job);
			log.info("Fax sent successfully:");
//			communicationDAOImpl.messageStatusChange(cOptionVO.getRcptSeqID(), cOptionVO.getMsgJobID());
//			communicationDAOImpl.messageStatusChangePr(cOptionVO.getRcptSeqID(), strMailStatus, cOptionVO.getMsgJobID(), strRemarks, strGeneralTypeID);
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
			FAXHelper.moveMessages(strFromQueueName,strToQueueName);
		}//end of try
		catch (Exception ex) {
			alFaxStatusInfo.add(cOptionVO.getRcptSeqID());
			alFaxStatusInfo.add("QUE");
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
			faxHelper.sendFAX(cOptionVO);
			ex.printStackTrace();
			throw new TTKException(ex, "error.hylafaxserver");
		}//end of catch (Exception ex)
		finally
		{
			if (hylaFaxClient == null) {
				return;
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
	}//end of sendFax(CommunicationOptionVO cOption)

	/**
	 * This method builds a MapMessage object and sends to the specified JMS Queue
	 * @param cOptionVO The CommunicationOptionVO Object which contains the details of From&To address,Subject,Body and File name
	 * @exception throws TTKException
	 */
	/*public void sendEmail(CommunicationOptionVO cOptionVO, String strEMailQueueName) throws TTKException
	{
		//String strEMailQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADEMAILQUEUENAME");
		//String strEMailQueueName = TTKPropertiesReader.getPropertyValue(strQueueName);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue mailQueue = null;
		QueueSender sender = null;
		//File objFile = cOption.getFile();
		MapMessage mapMsg = null;
		String strfuc = "";
		try {
			FileInputStream inStream = null;
			FileOutputStream outStream = null;
			String strFileName = "";
			//Save the file passed in the server
			if(!(cOptionVO.getEnrollNum().equals("")) && (cOptionVO.getEnrollNum()!=null)){
				if(cOptionVO.getFile().exists())
				{
					strFileName = cOptionVO.getFile().getName();
					inStream = new FileInputStream(cOptionVO.getFile());
					if(!(cOptionVO.getEnrollNum().equals("")) && (cOptionVO.getEnrollNum()!=null)){
						outStream = new FileOutputStream(TTKPropertiesReader.getPropertyValue("AttachedFileDestination")+strFileName);
					}//end of if(!(cOptionVO.getEnrollNum().equals("")) && (cOptionVO.getEnrollNum()!=null))
					else 
					if(cOptionVO.getFilePathName()!=null )
					{
						if(cOptionVO.getFilePathName().endsWith("pdf"))
						{
							if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL") || cOptionVO.getMsgID().equals("CLAIM_SHORTFALL")){
								strfuc = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+cOptionVO.getFilePathName();
							}//end of if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL"))
							else if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED")){
								strfuc = TTKPropertiesReader.getPropertyValue("authorizationrptdir")+cOptionVO.getFilePathName();
							}//end of if(cOptionVO.getMsgID().equals("PREAUTH_APPROVED"))
							File fileObj = new File(strfuc);
							if(fileObj.exists()){
								outStream = new FileOutputStream(strfuc);
							}//end of if(fileObj.exists())
						}//end of if(cOptionVO.getFilePathName().endsWith("pdf"))
					}//end of else if
					int aData = 0;
					while((aData = inStream.read()) != -1)
					{
						outStream.write(aData);
					}//end of while((aData = inStream.read()) != -1)
					inStream.close();
					outStream.close();
				}//end of if(cOptionVO.getFile().exists())
				else
				{
					log.debug("inside file not exist in send email method");
				}//end of else
			}//end of if(!(cOptionVO.getEnrollNum().equals("")) && (cOptionVO.getEnrollNum()!=null))
			m_context = TTKCommon.getJMSInitialContext();
			conFactory = (QueueConnectionFactory) m_context.lookup(strConnectionFactoryName);
			connection = conFactory.createQueueConnection();
			connection.start();
			session =  connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			mailQueue = (Queue) m_context.lookup(strEMailQueueName);
			sender = session.createSender(mailQueue);
			mapMsg = session.createMapMessage();
			//mapMsg.setString("From", cOption.getFrom());
			mapMsg.setString("To", cOptionVO.getPrmRcptEmailList());
			mapMsg.setString("Subject", cOptionVO.getMsgTitle());
			mapMsg.setString("Body", cOptionVO.getMessage());
			mapMsg.setString("From", cOptionVO.getSentFrom());
			mapMsg.setString("RcptSeqID", cOptionVO.getRcptSeqID());
			mapMsg.setString("MsgID", cOptionVO.getMsgID());
			mapMsg.setString("HasAttachment", "true");
			mapMsg.setString("AttachmentName", strFileName);

			sender.send(mapMsg);

			sender.close();
			connection.stop();
			session.close();
			connection.close();
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
	}//end of sendEmail(CommunicationOptionVO cOption)
*/
	/**
	 * This Method moves the messages from one queue to another queue
	 *
	 * @param mapMsg the MapMessage object which contains the messages
	 * @param strQueueName the String object which contains the queue name
	 * @exception throws TTKException
	 */
	public static void sendMessage(MapMessage mapMsg,String strQueueName)throws TTKException
	{
		//String strEMailQueueName = TTKPropertiesReader.getPropertyValue(strQueueName);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue mailQueue = null;
		QueueSender sender = null;
		try {
			m_context = TTKCommon.getJMSInitialContext();
			conFactory = (QueueConnectionFactory) m_context.lookup(strConnectionFactoryName);
			connection = conFactory.createQueueConnection();
			connection.start();
			session =  connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			mailQueue = (Queue) m_context.lookup(strQueueName);
			sender = session.createSender(mailQueue);
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
		//String strEMailQueueName = TTKPropertiesReader.getPropertyValue(strFromQueue);
		String strConnectionFactoryName = TTKPropertiesReader.getPropertyValue("JMSCONNECTIONFACTORYNAME");
		Context m_context = null;
		QueueConnectionFactory conFactory = null;
		QueueConnection connection = null;
		QueueSession session = null;
		Queue mailQueue = null;
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
			mailQueue = (Queue) m_context.lookup(strFromQueue);
			queueReceiver = session.createReceiver(mailQueue);
			queueBrowser = session.createBrowser(mailQueue);
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

	/**
	 * This method builds a SMS object and sends to the specified recipient
	 * @param cOption The CommunicationOptionVO Object which contains the details of From&To address,Subject,Body and File name
	 * @exception throws TTKException
	 */
	public void sendSMS(CommunicationOptionVO cOptionVO) throws TTKException
	{	log.info("sendSMS (4.1) : Inside sendSMS() of NotificationHelper ");
		CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
		//String strSMS = TTKPropertiesReader.getPropertyValue("SMSIP");
		//modified for Cigna
		String strSMS = "";
		String strMessage = "";
		String strNumber = "";
		String strMailStatus ="SNT";
		//String strUserName = TTKPropertiesReader.getPropertyValue("USERNAME");
		//String strPassWord = TTKPropertiesReader.getPropertyValue("PASSWORD");
		//String strSendType = TTKPropertiesReader.getPropertyValue("SENDTYPE");
//		String strID ="";
//		String strRemarks ="";
//		String strGeneralTypeID ="";
		//modified for Cigna
		String strFeedID = "";
		String strUserName = "";
		String strPassWord = "";
		String strSendType = "";
		String Cigna_URL_YN = cOptionVO.getCignaSmsUrl();
		if(Cigna_URL_YN.equals("Y"))
		{
			strUserName = TTKPropertiesReader.getPropertyValue("aid");
			strPassWord = TTKPropertiesReader.getPropertyValue("pin").trim();
			strSMS 	    = TTKPropertiesReader.getPropertyValue("CIGNA_SMS_SMSIP");
		}
		else
		{
			strUserName = TTKPropertiesReader.getPropertyValue("USERNAME");
			strPassWord = TTKPropertiesReader.getPropertyValue("PASSWORD");
			strSendType = TTKPropertiesReader.getPropertyValue("SENDTYPE");
			strSMS 	   = TTKPropertiesReader.getPropertyValue("SMSIP");
			strFeedID        = TTKPropertiesReader.getPropertyValue("FEEDID");
		}				
		ArrayList<String> alFaxStatusInfo = new ArrayList<String>();
		StringBuffer sb = null;
		URLConnection connection=null;
		URL url =null;
		try{
			
			sb= new StringBuffer();
			strMessage = cOptionVO.getMessage().replace(" ","+");
			strNumber = cOptionVO.getRcptSMS();
			if(Cigna_URL_YN.equals("Y"))
			{
				sb = sb.append(strSMS).append("aid=").append(strUserName)
				.append("&pin=").append(strPassWord)
				.append("&mnumber=").append(strNumber)
				.append("&message=").append(strMessage);
			}
			else
			{
				/*FROM Domestic*/
	            sb = sb.append(strSMS).append("feedid=").append(strFeedID)
	                                  .append("&username=").append(strUserName)
	                                  .append("&password=").append(strPassWord)
	                                  .append("&To=").append(strNumber)
	                                  .append("&Text=").append(strMessage)
	                                  .append("&senderid=").append(strSendType);
			}
			//sb = sb.append(strSMS).append("uname=TTKHealth&pass=ttkh19&send=TTK").append("&dest=").append("919845202341").append("&msg=").append("SMS+Impl+is+over+regards+Bala");
			url = new URL(sb.toString());
			log.info("sendSMS (4.2) : sms url ->"+url);
			connection=url.openConnection();
			if(connection == null)
			{
				log.debug("SMS, URLConnection object is null");
			}//end of if(connection == null)
			connection.getContent();
			log.info("connection time out value :"+connection.getConnectTimeout());
			alFaxStatusInfo.add(cOptionVO.getRcptSeqID());
			alFaxStatusInfo.add(strMailStatus);
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
//			communicationDAOImpl.messageStatusChangePr(cOptionVO.getRcptSeqID(), strMailStatus, strID,strRemarks,strGeneralTypeID);
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
			log.info("sendSMS (4.3) : SMS Sent Successfully");
		}//end of try
		catch(Exception exp)
		{
			ArrayList<String> alStatusInfo = new ArrayList<String>();
			alStatusInfo.add(cOptionVO.getRcptSeqID());
			alStatusInfo.add("QUE");
			alStatusInfo.add("");
			alStatusInfo.add("");
			alStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alStatusInfo);
			throw new TTKException(exp, "error.sms");
		}//end of catch(Exception exp)
		finally{
			connection= null;
			url =null;
		}//end of finally
	}//end of sendSMS(CommunicationOptionVO cOptionVO)

}//end of NotificationHelper
