/**
 * @ (#) EmailHelper.java 17th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : EmailHelper.java
 * Author       : Balakrishna.E
 * Company      : Span Systems Corporation
 * Date Created : 17th Feb 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.messageservices;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jms.JMSException;
import javax.mail.*;
import javax.mail.internet.*;

/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPAddressFailedException;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.common.CommunicationDAOImpl;
import com.ttk.dto.common.CommunicationOptionVO;

public class EmailHelper implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger( EmailHelper.class);
	private static String strSmtpHostName = null;
	//private static String strDeadQueueName = null;

	/**
	 * Default constructor
	 */
/*	public EmailHelper()
	{
	}//end of EmailHelper()
*/
	/**
	 * This Method sends the email to the specified Recipient
	 *
	 * @param mapMsg the javax.jms.MapMessage object which contains the from, to, subject, body and File attachment details
	 * @return boolean true if mail was successfully sent.
	 * @exception throws TTKException
	 */
	public boolean sendMessage(javax.jms.MapMessage mapMsg) throws TTKException
	{
		System.out.println("sendMessage");
		CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
		strSmtpHostName = TTKPropertiesReader.getPropertyValue("SMTPHOSTNAME");
		String strSmtpHostValue = TTKPropertiesReader.getPropertyValue("SMTPHOSTVALUE");
		//strDeadQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADEMAILQUEUENAME");
		String strFrom = "";
		String strTo = "";
		String strSubject = "";
		String strBody = "";
		String strRcptSeqID = "";
		String strMsgID = "";
		
		//boolean bHasAttachment = false;
		String strFileName = "";
		Session session = null;
		String strModifiedMsg = "";
		String strMailStatus ="SNT";
//		String strID ="";
//		String strRemarks ="";
//		String strGeneralTypeID ="";
		ArrayList<String> alFaxStatusInfo = new ArrayList<String>();
		// create some properties and get the default Session
		/*Properties properties = new Properties();
		properties.put(strSmtpHostName, strSmtpHostValue);
		properties.put("mail.smtp.sendpartial", "true");
		session = Session.getDefaultInstance(properties,null);*/

		 String host="10.1.0.3";
     		 final String user="admin@vidalhealthtpa.com";//change accordingly
     		 final String password="P@ssw0rd@123";//change accordingly    
              //Get the session object
              Properties properties= new Properties();
              properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.sendpartial", "true");
              properties.put("mail.smtp.auth", "true");
           

       session = Session.getInstance(properties,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user,password);
          }
        });

		
		try
		{
			strFrom = mapMsg.getString("From");
			strTo = mapMsg.getString("To");
			strSubject = mapMsg.getString("Subject");
			strBody = mapMsg.getString("Body");
			strRcptSeqID = mapMsg.getString("RcptSeqID");
			strMsgID = mapMsg.getString("MsgID");
			System.out.println("strMsgID:::inside email helper:::::::::"+strMsgID);
			
			String strHasAttachment = TTKCommon.checkNull(mapMsg.getString("HasAttachment"));
			//bHasAttachment = Boolean.getBoolean(strHasAttachment);
			log.debug("Body		    :"+strBody);
			log.debug("RcptSeqID	:"+strRcptSeqID);
			log.debug("strMsgID		:"+strMsgID);
			log.debug("In EmailHelper strHasAttachment="+strHasAttachment);

			alFaxStatusInfo.add(strRcptSeqID);
			alFaxStatusInfo.add(strMailStatus);
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");

			// create a message
			Message msg = new MimeMessage(session);
			Multipart objMultipart = new MimeMultipart();
			MimeBodyPart mbpText = new MimeBodyPart();
			MimeBodyPart mbpAttachment = new MimeBodyPart();
			FileDataSource objFileDataSource = null;
			msg.setFrom(new InternetAddress(strFrom ));
			InternetAddress[] address = {new InternetAddress(strTo)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(strSubject);
			msg.setSentDate(new Date());

			if(strMsgID.equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))
			{
				strModifiedMsg = createHTMLString(strBody);
				mbpText.setContent(strModifiedMsg,"text/html");
			}//end of if(strMsgID.equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))
			else
			{
				strModifiedMsg = strBody.replaceAll("<NL>","\t\n");
				mbpText.setContent(strModifiedMsg,"text/plain");
			}//end of else
			objMultipart.addBodyPart(mbpText);
			if(strHasAttachment.equalsIgnoreCase("true"))
			{
				strFileName = mapMsg.getString("AttachmentName");
				if(strMsgID.equals("PREAUTH_SHORTFALL") || strMsgID.equals("CLAIM_SHORTFALL") || strMsgID.equals("PREAUTH_SHORTFALL_NHCP") || strMsgID.equals("INITIAL_SHORTFALL_REQUEST") || strMsgID.equals("SHORTFALL_REMINDER_REQUEST") || strMsgID.equals("SHORTFALL_CLOSURE_NOTICE")  || strMsgID.equals("SHORTFALL_CLOSURE_APPROVAL") || strMsgID.equals("SHORTFALL_CLOSURE_LETTER") || strMsgID.equals("CLAIM_SHORTFALL_REGRET") || strMsgID.equals("CLAIM_SHORTFALL_REOPEN_RECOMMANDATION") || strMsgID.equals("CLAIM_SHORTFALL_NONREOPEN_RECOMDANDATION") || strMsgID.equals("RECOMMENDING_WAIVER_OF_DELAY") ){
                    
                   objFileDataSource = new FileDataSource(TTKPropertiesReader.getPropertyValue("shortfallrptdir")+strFileName);
				}//end of if(cOptionVO.getMsgID().equals("PREAUTH_SHORTFALL"))
				else if(strMsgID.equals("CALL_PENDING_REPORT") || strMsgID.equals("CALL_PENDING_BRANCHWISE_REPORT") ||
						strMsgID.equals("MR_CLAIMS_PENDING_REPORT") || strMsgID.equals("MR_CLAIMS_PENDING_SRTFALL_REPORT")){
					objFileDataSource = new FileDataSource(TTKPropertiesReader.getPropertyValue("callpendingdir")+strFileName);
				}//end of else if(strMsgID.equals("CALL_PENDING_REPORT"))
				else if(strMsgID.equals("SCHEDULED_JOBS"))
				{
					objFileDataSource = new FileDataSource(TTKPropertiesReader.getPropertyValue("JobStatusdir")+strFileName);
				}//end of else if(strMsgID.equals("SCHEDULED_JOBS"))
				if(strMsgID.equals("NEW_ONLINE_ENR_EMPLOYEE")){
					objFileDataSource = new FileDataSource(strFileName);
				}//end of if(strMsgID.equals("NEW_ONLINE_ENR_EMPLOYEE"))
				if(strMsgID.equals("NEW_ONLINE_ENR_EMPLOYEE")){
					objFileDataSource = new FileDataSource(strFileName);
				}//end of if(strMsgID.equals("NEW_ONLINE_ENR_EMPLOYEE"))
				else{
					objFileDataSource = new FileDataSource(TTKPropertiesReader.getPropertyValue("authorizationrptdir")+strFileName);
				}//end of else
				mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
				mbpAttachment.setFileName(strFileName);
				objMultipart.addBodyPart(mbpAttachment);
			}//end of if(strHasAttachment.equalsIgnoreCase("true"))
			msg.setContent(objMultipart);
			Transport.send(msg);
//			communicationDAOImpl.messageStatusChange(strRcptSeqID,strID);
//			communicationDAOImpl.messageStatusChangePr(strRcptSeqID, strMailStatus, strID,strRemarks,strGeneralTypeID);
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
		}//end of try
		catch (JMSException je) {
			throw new TTKException(je, "error.jms");
		}//end of catch (JMSException je)
		catch (MessagingException mex) {
			throw new TTKException(mex, "error.jms");
		}//end of catch (MessagingException mex)
		catch(TTKException ttkExp){
			throw new TTKException(ttkExp, "error.jms");
		}//end of catch(TTKException ttkExp)
		return true;
	}//end of sendMessage(javax.jms.MapMessage mapMsg)

	/**
	 * This Method sends the email to the specified Recipients
	 * @param cOptionVO the CommunicationOptionVO object which contains notification records
	 * @return boolean true if mail was successfully sent.
	 * @exception throws TTKException
	 */
	public static void message(CommunicationOptionVO cOptionVO) throws TTKException
	{
		log.info("E-MAIL (4.1) : Inside message() of EmailHelper ");
		//CommunicationManagerBean communicationManagerBean =new CommunicationManagerBean();
		String strSmtpHostName = TTKPropertiesReader.getPropertyValue("SMTPHOSTNAME");
		String strSmtpHostValue = TTKPropertiesReader.getPropertyValue("SMTPHOSTVALUE");
		String strPortName = TTKPropertiesReader.getPropertyValue("PORTNAME");
		String strPortNbr = TTKPropertiesReader.getPropertyValue("PORTNBR");
		NotificationHelper notificationHelper = new NotificationHelper();
		CommunicationDAOImpl communicationDAOImpl = new CommunicationDAOImpl();
		String strFromQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADEMAILQUEUENAME");
		String strToQueueName = TTKPropertiesReader.getPropertyValue("JMSEMAILQUEUENAME");

		//strSMTPHost = "mail.spanservices.com";
		FileDataSource objFileDataSource = null;
		Multipart objMultipart = new MimeMultipart();
		MimeBodyPart mbpText = new MimeBodyPart();
		MimeBodyPart mbpAttachment = new MimeBodyPart();
		String modifiedMsg = null;
		String message = null;
		String strfuc = "";
		String strMailStatus ="SNT";
//		String strID ="";
//		String strRemarks ="";
//		String strGeneralTypeID ="";
		ArrayList<String> alFaxStatusInfo = new ArrayList<String>();
		//p.put("mail.smtp.host", "mail.smtp.host");
		/*Properties properties = new Properties();
		properties.put(strSmtpHostName, strSmtpHostValue);
		properties.put(strPortName, strPortNbr);
		properties.put("mail.smtp.sendpartial", "true");//changed as per fixing mail failure issue
		Session session = Session.getDefaultInstance(properties, null);*/
		// String host="10.1.0.3";
     		// final String user="admin@vidalhealthtpa.com";//change accordingly
     		// final String password="P@ssw0rd@123";//change accordingly    
/*		 String host="alkootmedical-com01e.mail.protection.outlook.com";
 		 final String user="appuser1";//change accordingly
 		 final String password="App@1234";//change accordingly 
              //Get the session object
             Properties properties= new Properties();
              properties.put("mail.smtp.host",host);
		      properties.put("mail.smtp.sendpartial", "true");
              properties.put("mail.smtp.auth", "true");*/
		 /*String host=TTKPropertiesReader.getPropertyValue("hostaddress");
 		 final String user=TTKPropertiesReader.getPropertyValue("emailusername");//change accordingly
 		 final String password=TTKPropertiesReader.getPropertyValue("emailpassword");//change accordingly    
          //Get the session object
          Properties properties= new Properties();
          properties.put("mail.smtp.host",host);
	properties.put("mail.smtp.sendpartial", "true");
          properties.put("mail.smtp.auth", "true");
          properties.put("mail.smtp.port", "465");
          properties.put("mail.smtp.ssl.enable", "true");
           

      		Session  session = Session.getInstance(properties,
     		   new javax.mail.Authenticator() {
       	   protected PasswordAuthentication getPasswordAuthentication() {
       	 return new PasswordAuthentication(user,password);
        	  }
       	 });*/
      		
      	// FROM qatar 108 SERVER
            String host="smtp.rediffmailpro.com";
            final String user="noreply@vidalhealth.com";//change accordingly
            final String password="Kjdn#rf992e";//change accordingly
            //Get the session object
            Properties properties= new Properties();
            properties.put("mail.smtp.host",host);
            properties.put("mail.smtp.sendpartial", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");     //Get the session object
              Session  session = Session.getInstance(properties,
             new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(user,password);
              }
            });
		
      
      		
		try
		{
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(cOptionVO.getSentFrom()));
			msg.setSubject(cOptionVO.getMsgTitle());
			message=cOptionVO.getMessage();
			System.out.println("E-MAIL (4.2)Message Id==================>"+cOptionVO.getMsgID());
			//if(cOptionVO.getMsgID().equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))
			//if((cOptionVO.getMsgID().equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_APPROVED"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_STATUS_MEDICAL"))||(cOptionVO.getMsgID().equalsIgnoreCase("CLAIM_STATUS_MR"))||(cOptionVO.getMsgID().equalsIgnoreCase("CLAIM_STATUS_NHCP"))||(cOptionVO.getMsgID().equalsIgnoreCase("FINANCE_MEDICLAIM_COMPUTATION"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_ENHANCED"))) //added for ibm
		
			if(
					(cOptionVO.getMsgID().equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_APPROVED"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_STATUS_MEDICAL"))||(cOptionVO.getMsgID().equalsIgnoreCase("CLAIM_STATUS_MR"))||(cOptionVO.getMsgID().equalsIgnoreCase("CLAIM_STATUS_NHCP"))||(cOptionVO.getMsgID().equalsIgnoreCase("FINANCE_MEDICLAIM_COMPUTATION"))||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_ENHANCED"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_INITIAL_APP_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_INITIAL_APP_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_REQUEST_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_REQUEST_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("Cashless request registration Email Advisor"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_REJECT_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_REJECT_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_FINAL_APP_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_FINAL_APP_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_FRST_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_FRST_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_REGIS_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_REGIS_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_DOC_SHFALL_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_DOC_SHFALL_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SETTLE_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SETTLE_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_CLOSER_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_CLOSER_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_REJECT_EMAIL_AD"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_DOC_SHFALL_EMAIL_PO"))||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_REJECT_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_REM1_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_REM1_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_FNL_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_PAT_SHFALL_FNL_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SHFALL_REM1_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SHFALL_REM1_EMAIL_PO"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SHFALL_REM2_EMAIL_AD"))
					||(cOptionVO.getMsgID().equalsIgnoreCase("CIGNA_CLM_SHFALL_REM2_EMAIL_PO")) //added for ibm
					||(cOptionVO.getMsgID().equalsIgnoreCase("PREAUTH_SUBMITTED"))
				||(cOptionVO.getMsgID().equalsIgnoreCase("PARTNER_USER_ID"))
				||(cOptionVO.getMsgID().equalsIgnoreCase("FORGOT_PASSWORD_PATR"))
				||(cOptionVO.getMsgID().equalsIgnoreCase("PTNR_PREAUTH_ONLN_SUB"))
				||(cOptionVO.getMsgID().equalsIgnoreCase("WEBSERVICE_CLAIM_EXCEPTION"))) 
			//added for ibm
			{
				modifiedMsg = createHTMLString(message);
				mbpText.setContent(modifiedMsg,"text/html");
			}//end of if(cOptionVO.getMsgID().equalsIgnoreCase("ONLINE_EMPLOYEE_FAMILY_INFO"))
			
			
			else if(cOptionVO.getMsgID().equalsIgnoreCase("CLAIM_APPR_RCPT")){
				
				modifiedMsg = createHTMLString(message);
				mbpText.setContent(modifiedMsg,"text/html");
				
			}
			
			else
			{
				modifiedMsg = message.replaceAll("<NL>","\t\n");
				mbpText.setContent(modifiedMsg,"text/plain");
			}//end of else
			objMultipart.addBodyPart(mbpText);

			if(!(cOptionVO.getPrmRcptEmailList().equals("")))
			{
				String recpinet=cOptionVO.getPrmRcptEmailList();
				System.out.println("E-MAIL (4.3) message is sending to==================="+recpinet);
				//InternetAddress[] internetAddress = {new InternetAddress(cOptionVO.getPrmRcptEmailList())};
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cOptionVO.getPrmRcptEmailList()));
			}//end of if(!(cOptionVO.getPrmRcptEmailList().equals("")))
			if(!(cOptionVO.getSenRcptEmailList()).equals(""))
			{
				System.out.println("E-MAIL (4.3)message is sending cc==================="+cOptionVO.getSenRcptEmailList());
				InternetAddress[] address = InternetAddress.parse(cOptionVO.getSenRcptEmailList());
				System.out.println("E-MAIL (4.4)adres=======================>"+address.length);
				msg.setRecipients(Message.RecipientType.CC, address);
				
			}//end of if(!(cOptionVO.getSenRcptEmailList().equals("")))

			alFaxStatusInfo.add(cOptionVO.getRcptSeqID());
			alFaxStatusInfo.add(strMailStatus);
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			alFaxStatusInfo.add("");
			String strMsgID =cOptionVO.getMsgID().trim();
			if(!TTKCommon.checkNull(cOptionVO.getFilePathName()).equals(""))
			{
				if(cOptionVO.getFilePathName().endsWith("pdf"))
				{
					if(strMsgID.equals("PREAUTH_SHORTFALL") ||
							strMsgID.equals("CLAIM_SHORTFALL") ||
							strMsgID.equals("PREAUTH_SHORTFALL_NHCP") || 
							strMsgID.equals("INITIAL_SHORTFALL_REQUEST") ||
							strMsgID.equals("SHORTFALL_REMINDER_REQUEST") ||
							strMsgID.equals("SHORTFALL_CLOSURE_NOTICE")  ||
							strMsgID.equals("SHORTFALL_CLOSURE_APPROVAL") ||
							strMsgID.equals("SHORTFALL_CLOSURE_LETTER") || 
							strMsgID.equals("CLAIM_SHORTFALL_REGRET") ||
							strMsgID.equals("CLAIM_SHORTFALL_REOPEN_RECOMMANDATION") ||
							strMsgID.equals("CLAIM_SHORTFALL_NONREOPEN_RECOMDANDATION") ||
							strMsgID.equals("RECOMMENDING_WAIVER_OF_DELAY")){
							
						strfuc = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+cOptionVO.getFilePathName();
					}//end of if(strMsgID.equals("PREAUTH_SHORTFALL") ||
								//strMsgID.equals("CLAIM_SHORTFALL") ||
								//strMsgID.equals("PREAUTH_SHORTFALL_NHCP"))
					else if(strMsgID.equals("CALL_PENDING_REPORT") ||
							strMsgID.equals("CALL_PENDING_BRANCHWISE_REPORT") ||
							strMsgID.equals("MR_CLAIMS_PENDING_REPORT") || strMsgID.equals("MR_CLAIMS_PENDING_SRTFALL_REPORT")){
						strfuc = TTKPropertiesReader.getPropertyValue("callpendingdir")+cOptionVO.getFilePathName();
					}//end of else if(strMsgID.equals("CALL_PENDING_REPORT") ||
								//strMsgID.equals("CALL_PENDING_BRANCHWISE_REPORT") ||
								//strMsgID.equals("MR_CLAIMS_PENDING_REPORT"))
					else if(strMsgID.equals("SCHEDULED_JOBS")){
						strfuc = TTKPropertiesReader.getPropertyValue("JobStatusdir")+cOptionVO.getFilePathName();
					}//end of else if(strMsgID.equals("SCHEDULED_JOBS"))
					//start changes for cr koc 1105
					else if(strMsgID.equals("FINANCE_MEDICLAIM_COMPUTATION")){

						strfuc = TTKPropertiesReader.getPropertyValue("computaiondir")+cOptionVO.getFilePathName();
					}//end of else if(strMsgID.equals("FINANCE_MEDICLAIM_COMPUTATION"))
					//end changes for cr koc 1105
					/* else if(strMsgID.equals("CLM_INS_XFDF_PICKUP")|| strMsgID.equals("PAT_INS_XFDF_PICKUP")){
						strfuc=TTKPropertiesReader.getPropertyValue("pdfdirectory")+cOptionVO.getFilePathName();						
					}//		else if(strMsgID.equals("CLM_INS_XFDF_PICKUP"))//added as per 1274A */
					else if(strMsgID.equals("PAT_INS_INTIMATION_REMINDER") || strMsgID.equals("PAT_INS_INTIMATION_ESCALATION") || strMsgID.equals("CLM_INS_INT_REMINDER_XFDF")|| strMsgID.equals("CLM_INS_INT_REMINDER_ONLINE")|| strMsgID.equals("CLM_INS_INT_ESCALATION_XFDF")|| strMsgID.equals("CLM_INS_INT_ESCALATION_ONLINE")|| strMsgID.equals("CLM_INS_XFDF_PICKUP")|| strMsgID.equals("PAT_INS_XFDF_PICKUP") || strMsgID.equals("CLM_INS_INTIMATION") || strMsgID.equals("PAT_INS_INTIMATION")){
						strfuc=TTKPropertiesReader.getPropertyValue("pdfdirectory")+cOptionVO.getFilePathName();						
					}//
					else if(strMsgID.equals("REFERRAL_LETTER_HOSP")){
						strfuc=TTKPropertiesReader.getPropertyValue("preAuthReferralDir")+cOptionVO.getFilePathName();						
					}
					
					else{
						strfuc = TTKPropertiesReader.getPropertyValue("authorizationrptdir")+cOptionVO.getFilePathName();
						System.out.println("E-MAIL (4.5)file path =======================>"+strfuc);
					}//end of else

					File fileObj = new File(strfuc);
					if(fileObj.exists()){
						cOptionVO.setFile(fileObj);
						//objFileDataSource = new FileDataSource(cOptionVO.getFile());
						objFileDataSource = new FileDataSource(fileObj);
						mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
						mbpAttachment.setFileName(cOptionVO.getFile().getName());
						objMultipart.addBodyPart(mbpAttachment);
					}//end of if(fileObj.exists())
				}//end of if(cOptionVO.getFilePathName().endsWith("pdf"))
				
				else if(strMsgID.equalsIgnoreCase("PAT_INT_INSURANCE") || strMsgID.equalsIgnoreCase("PAT_APR_TTKSPOC")	|| strMsgID.equalsIgnoreCase("PAT_REQ_TTKSPOC"))
					   {
					   //    
						strfuc = TTKPropertiesReader.getPropertyValue("inspreauthdir")+cOptionVO.getFilePathName();
						
						File fileObj = new File(strfuc);
						if(fileObj.exists()){
							cOptionVO.setFile(fileObj);
							//objFileDataSource = new FileDataSource(cOptionVO.getFile());
							objFileDataSource = new FileDataSource(fileObj);
							mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
							mbpAttachment.setFileName(cOptionVO.getFile().getName());
							objMultipart.addBodyPart(mbpAttachment);
							
				          }//end of if(fileObj.exists())
			                }//end of else if(strMsgID.equalsIgnoreCase("PAT_INT_INSURANCE") || strMsgID.equalsIgnoreCase("PAT_APR_TTKSPOC")	|| strMsgID.equalsIgnoreCase("PAT_REQ_TTKSPOC"))
			            else if(strMsgID.equalsIgnoreCase("CLM_INT_INSURANCE")	|| strMsgID.equalsIgnoreCase("CLM_APR_TTKSPOC")	|| strMsgID.equalsIgnoreCase("CLM_REQ_TTKSPOC"))
			               {
				  
				   strfuc = TTKPropertiesReader.getPropertyValue("insclaimdir")+cOptionVO.getFilePathName();
				 
				  File fileObj = new File(strfuc);
				  if(fileObj.exists()){
					cOptionVO.setFile(fileObj);
					//objFileDataSource = new FileDataSource(cOptionVO.getFile());
					objFileDataSource = new FileDataSource(fileObj);
					mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
					mbpAttachment.setFileName(cOptionVO.getFile().getName());
					objMultipart.addBodyPart(mbpAttachment);
					
			        }//if(fileObj.exists()){
			  }//	end of else if(strMsgID.equalsIgnoreCase("CLM_INT_INSURANCE")	|| strMsgID.equalsIgnoreCase("CLM_APR_TTKSPOC")	|| strMsgID.equalsIgnoreCase("CLM_REQ_TTKSPOC"))
		
			            else if(strMsgID.equals("ACTIVE_MEMBER_LIST")){
							strfuc=cOptionVO.getFilePathName();
							 File fileObj = new File(strfuc);
							  if(fileObj.exists()){
								cOptionVO.setFile(fileObj);
								//objFileDataSource = new FileDataSource(cOptionVO.getFile());
								objFileDataSource = new FileDataSource(fileObj);
								mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
								mbpAttachment.setFileName(cOptionVO.getFile().getName());
								objMultipart.addBodyPart(mbpAttachment);
							  }
						}
				
			            else if(strMsgID.equals("MEMBER_REGISTER_UPLOAD_LOG"))
			            {
							strfuc=cOptionVO.getFilePathName();
							 File fileObj = new File(strfuc);
							  if(fileObj.exists())
							  {
								cOptionVO.setFile(fileObj);
								//objFileDataSource = new FileDataSource(cOptionVO.getFile());
								objFileDataSource = new FileDataSource(fileObj);
								mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
								mbpAttachment.setFileName(cOptionVO.getFile().getName());
								objMultipart.addBodyPart(mbpAttachment);
							  }
						}
				
				
				
				//Auto Trigger mail for Provider by deepthi 
				else if(strMsgID.equalsIgnoreCase("CLAIM_APPR_RCPT"))
				   {
					strfuc = TTKPropertiesReader.getPropertyValue("ProviderPaymentExceldir")+cOptionVO.getFilePathName();
					File fileObj = new File(strfuc);
					if(fileObj.exists()){
						cOptionVO.setFile(fileObj);
						objFileDataSource = new FileDataSource(fileObj);
						mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
						mbpAttachment.setFileName(cOptionVO.getFile().getName());
						objMultipart.addBodyPart(mbpAttachment);
					}
				
				   }
				
				
				
				
				
				
			}//end of if
			else if(strMsgID.equals("NEW_ONLINE_ENR_EMPLOYEE"))
			{
				//log.info("Inside the user conditions ");
				strfuc = "Document to access Vidal Health TPA WEB Application.zip";
				File fileObj = new File(strfuc);
				if(fileObj.exists()){
					cOptionVO.setFile(fileObj);
					//objFileDataSource = new FileDataSource(cOptionVO.getFile());
					objFileDataSource = new FileDataSource(fileObj);
					mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
					mbpAttachment.setFileName(cOptionVO.getFile().getName());
					objMultipart.addBodyPart(mbpAttachment);
				}//end of if(fileObj.exists())
			}//end of else
			
			else
			{
				log.debug("Sending plain text mail");
			}//end of else
			msg.setContent(objMultipart);
			Transport.send(msg);
			log.info("+++++++++++++++++++++++++++sent successfully+++++++++++++++++++++++++++++");
//			communicationDAOImpl.messageStatusChange(cOptionVO.getRcptSeqID(),strID);
			communicationDAOImpl.messageStatusChangePr(alFaxStatusInfo);
			//NotificationHelper.moveMessages(strFromQueueName,strToQueueName);
		}//end of try
		catch(TTKException ttkExp){
			log.error(ttkExp.fillInStackTrace());
			ArrayList<String> alErrStatusInfo = new ArrayList<String>();
			alErrStatusInfo.add(cOptionVO.getRcptSeqID());
			alErrStatusInfo.add("QUE");
			alErrStatusInfo.add("");
			alErrStatusInfo.add("");
			alErrStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alErrStatusInfo);
			throw new TTKException(ttkExp, "ttkExp");
		}//end of catch(TTKException ttkExp)
		catch (AddressException sfe){
			log.error(sfe.fillInStackTrace());
			ArrayList<String> alStatusInfo = new ArrayList<String>();
			alStatusInfo.add(cOptionVO.getRcptSeqID());
			alStatusInfo.add("QUE");
			alStatusInfo.add("");
			alStatusInfo.add("");
			alStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alStatusInfo);
 			throw new TTKException(sfe, "addressExp");
		}//end of catch
		catch (SMTPAddressFailedException smtpExp) {
			log.error(smtpExp.fillInStackTrace());
			ArrayList<String> alMsgStatusInfo = new ArrayList<String>();
			alMsgStatusInfo.add(cOptionVO.getRcptSeqID());
			alMsgStatusInfo.add("QUE");
			alMsgStatusInfo.add("");
			alMsgStatusInfo.add("");
			alMsgStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alMsgStatusInfo);
			//log.info("after updating SMTPAddressFailedException ");
			throw new TTKException(smtpExp, "smtpExp");
		}//end of catch (SMTPAddressFailedException smtpExp)
		catch (MessagingException mex) {
			log.error(mex.fillInStackTrace());
			ArrayList<String> alMsgStatusInfo = new ArrayList<String>();
			alMsgStatusInfo.add(cOptionVO.getRcptSeqID());
			alMsgStatusInfo.add("QUE");
			alMsgStatusInfo.add("");
			alMsgStatusInfo.add("");
			alMsgStatusInfo.add("");
			communicationDAOImpl.messageStatusChangePr(alMsgStatusInfo);
			throw new TTKException(mex, "messageExp");
		}//end of catch (MessagingException mex)
	}//end of Message(CommunicationOptionVO cOptionVO)

	/**
	 * This Method sends the email to the specified Recipients
	 * @param cOptionVO the CommunicationOptionVO object which contains sender and recipients information
	 * @exception throws Exception
	 *//*
	public  void Message(CommunicationOptionVO cOptionVO) throws Exception
	{
		String strFromQueueName = TTKPropertiesReader.getPropertyValue("JMSDEADEMAILQUEUENAME");
		String strToQueueName = TTKPropertiesReader.getPropertyValue("JMSEMAILQUEUENAME");
		 String strSMTPHost = TTKPropertiesReader.getPropertyValue("SMTPHOST");
		//strSMTPHost = "mail.spanservices.com";
		
		FileDataSource objFileDataSource = null;
		Multipart objMultipart = new MimeMultipart();
		MimeBodyPart mbpText = new MimeBodyPart();
		MimeBodyPart mbpAttachment = new MimeBodyPart();
		String modifiedMsg = null;
		String message = null;
		String strfuc = "D:\\StatusRpt_09_12_2010.pdf";
		Properties p = new Properties();
		//p.put("mail.smtp.host", "mail.smtp.host");
		p.put("mail.smtp.host", "webmail.spanservices.com");
		p.put("mail.smtp.port", "25");
		p.put("mail.smtp.debug", "true");
		Session session = Session.getDefaultInstance(p, null);
		try
		{
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(cOptionVO.getSentFrom()));
			msg.setSubject(cOptionVO.getMsgTitle());
			message=cOptionVO.getMessage();
			modifiedMsg = message.replaceAll("<NL>","\t\n");
			mbpText.setContent(modifiedMsg,"text/plain");
			objMultipart.addBodyPart(mbpText);
			
			
			if(!(cOptionVO.getPrmRcptEmailList().equals("")))
			{
				//InternetAddress[] internetAddress = {new InternetAddress(cOptionVO.getPrmRcptEmailList())};
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cOptionVO.getPrmRcptEmailList()));
			}//end of if(!(cOptionVO.getPrmRcptEmailList().equals("")))
			if(!(cOptionVO.getSenRcptEmailList()).equals(""))
			{
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cOptionVO.getSenRcptEmailList()));
			}//end of if(!(cOptionVO.getSenRcptEmailList().equals("")))

			//this code is for sending attachment in mail
			File fileObj = new File(strfuc);
			if(fileObj.exists()){
				cOptionVO.setFile(fileObj);
				//objFileDataSource = new FileDataSource(cOptionVO.getFile());
				objFileDataSource = new FileDataSource(fileObj);
				mbpAttachment.setDataHandler(new DataHandler(objFileDataSource));
				mbpAttachment.setFileName(cOptionVO.getFile().getName());
				objMultipart.addBodyPart(mbpAttachment);
			}//end of if(fileObj.exists())
			msg.setContent(objMultipart);
			
			Transport.send(msg);
			
			NotificationHelper.moveMessages(strFromQueueName,strToQueueName);
		}//end of try
		catch (AddressException addrExp)
		{
			addrExp.printStackTrace();
		}//end of catch
		catch (MessagingException mex) {
			NotificationHelper notificationHelper = new NotificationHelper();
			MessagingException meObj = mex;
	        int n = 0;
	        mex.printStackTrace();
	        while(meObj != null) {
	        	
	            //_mex.printStackTrace();
	            if(meObj instanceof SendFailedException) {
	                SendFailedException sfex = (SendFailedException)meObj;
	                Address[] invalid = sfex.getInvalidAddresses();
	                if(invalid != null) {
	                	
	                    if(invalid != null) {
	                        for(int i = 0; i < invalid.length; i++)
	                        {
	                        	
	                        }//end of for(int i = 0; i < invalid.length; i++)
	                    }//end of if (invalid != null)
	                }//end of if (invalid != null)
	                Address[] valid = sfex.getValidUnsentAddresses();
	                if(valid != null) {
	                	
	                    if(valid != null) {
	                        for(int i = 0; i < valid.length; i++)
	                        {
	                        	
	                        }//end of for(int i = 0; i < valid.length; i++)s
	                        //notificationHelper.sendEmail(cOptionVO,strFromQueueName);
	                    }//end of if(valid != null)
	                }//end of if(valid != null)
	            }//end of if (meObj instanceof SendFailedException)
	            meObj = (MessagingException)meObj.getNextException();
	        }//end of while(meObj != null)
	    }//end of catch (MessagingException mex)
	}//end of Message(CommunicationOptionVO cOptionVO)

*/


	/**
	 * This Method create HTML String
	 * @param strMessage the Sting object which contains mail content
	 * @return String html converted string
	 * @exception throws TTKException
	 */
	public static String createHTMLString(String strMessage) throws TTKException{
		String strMsg ="<html><body Style=\"font-family:Courier;font-size:10px\">"+strMessage+"</body></html> ";
		return strMsg;
	}//end of createHTMLString(String strMessage)

}//end of EmailHelper
