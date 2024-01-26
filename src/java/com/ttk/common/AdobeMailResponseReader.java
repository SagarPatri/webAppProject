package com.ttk.common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.ttk.business.claims.ClaimIntimationSmsManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.AdobeAttachmentReaderVO;
import com.ttk.dto.claims.AdobePatAttachmentReaderVO;


//1274A
public class AdobeMailResponseReader {
	private static Logger log = Logger.getLogger( AdobeMailResponseReader.class );
	private static final String AdobeFormMailAttachement="AdobeAttachment";

	String strHost ="";	String strUsername = "";	String strPassword = "";	String strMaps = "";
	String strMainfolder ="";	String strBackupfolder = "";	String strInsuredReAdobeForm="";
	String body="";String value="";String strSubject="";String messageSubject="";
	Store store;Session session1 = null;

	AdobeMailResponseReader() throws MessagingException,TTKException
	{
		strHost = TTKPropertiesReader.getPropertyValue("MAILHOST");
		strUsername = TTKPropertiesReader.getPropertyValue("MAILUSERNAME");
		strPassword = TTKPropertiesReader.getPropertyValue("MAILPASSWORD");
		strMaps = TTKPropertiesReader.getPropertyValue("MAILMAPS");
		strMainfolder = TTKPropertiesReader.getPropertyValue("MAILFOLDER");
		strBackupfolder = TTKPropertiesReader.getPropertyValue("MAILBACKUPFOLDER");
		strInsuredReAdobeForm=TTKPropertiesReader.getPropertyValue("insrepliedadobeformsdir");
		Properties properties = System.getProperties();
		session1 = Session.getDefaultInstance(properties);
		store = session1.getStore(strMaps);
		store.connect(strHost, strUsername, strPassword);
	}

	/*This method is to read an Attchment from claimprocess@vidalhealthtpa.cm
	 * The Insurer Appprove PreAuth or Claim 
	 * 
	 * 
	 */
	public void readMailAdobeAttachment()throws TTKException
	{
		ArrayList alInsurancePatResponseList=null;
		ArrayList alInsuranceClaimResponseList=null;
		alInsurancePatResponseList=new ArrayList();
		alInsuranceClaimResponseList=new ArrayList();
		HashMap hashMap=null;
		AdobeAttachmentReaderVO adobeAttachmentReaderVO=null;
		AdobePatAttachmentReaderVO adobePatAttachmentReaderVO=null;
		
		/*Session session1 = null;
		String strHost = TTKPropertiesReader.getPropertyValue("MAILHOST");
		String strUsername = TTKPropertiesReader.getPropertyValue("MAILUSERNAME");
		String strPassword = TTKPropertiesReader.getPropertyValue("MAILPASSWORD");
		String strMaps = TTKPropertiesReader.getPropertyValue("MAILMAPS");
		String strMainfolder = TTKPropertiesReader.getPropertyValue("MAILFOLDER");
		String strBackupfolder = TTKPropertiesReader.getPropertyValue("MAILBACKUPFOLDER");*/

		Properties properties = System.getProperties();
		session1 = Session.getDefaultInstance(properties);

		try
		{
			Folder folder = store.getFolder(strMainfolder);
			Folder strBkpFolder = store.getFolder(strBackupfolder);
			hashMap= new  HashMap();
			PdfStamper stamper=null;
			if (!folder.exists())
			{
				System.exit(0);
			}//end of if (!folder.exists())
			folder.open(Folder.READ_WRITE);
			strBkpFolder.open(Folder.READ_WRITE);
			//int intTotalMsg = folder.getMessageCount();
			//int intunread = folder.getUnreadMessageCount();
			//int intNewMsg = folder.getNewMessageCount();
			Message[] message = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));

			folder.copyMessages(message,strBkpFolder);
			folder.setFlags(message, new Flags(Flags.Flag.DELETED),true);
			int count=-1;
			boolean ismail=false;
			for (int messageCount = 0; messageCount< message.length; messageCount++)
			{
				count++;
				String read="read";
				/*	String dot=".";
				String from = InternetAddress.toString(message[messageCount].getFrom());
				String subject = message[messageCount].getSubject();
				java.util.Date sent = message[messageCount].getSentDate();*/
				Object content = message[messageCount].getContent();
				/*if (from != null){
				}//end of if (from != null){
				if (subject != null) {
				}//end of (subject != null)
				if (sent != null)
				{
					String sent1=sent.toString();
				}//end of (sent != null)
				 */				
				try
				{
					if (content instanceof String)
					{ 
						body = (String)content;
						if(!read.equals("rd")){
							read="rd";
						}//end of if(!read.equals("rd"))
					}//end of if (content instanceof String)
					else if (content instanceof Multipart)
					{
						int f=0;
						BodyPart bodyPart=null;
						BodyPart bp3=null;
						BodyPart bpart=null;
						Multipart multipart = (Multipart)content;
						//String body_content="",body_content11="";
						//	String filepath="";
						for (int bodyPartCount = 0; bodyPartCount < multipart.getCount(); bodyPartCount++)
						{
							bodyPart = multipart.getBodyPart(bodyPartCount);
							if(bodyPartCount==0){
								bp3=multipart.getBodyPart(0);
							}//end of if(bodyPartCount==0)
							String disposition = bodyPart.getDisposition();
							Object o2 = bp3.getContent();
							if(bodyPartCount==0){
								if (o2 instanceof String) {
									String new1=(String)o2;
									if(!read.equals("rd")){
										read="rd";
									}// if(!read.equals("rd"))
								}//end of  if (o2 instanceof String) 
								else
								{
									Multipart mp1=(Multipart)o2;
									for(int u=0;u<mp1.getCount();u++)
									{
										bpart=mp1.getBodyPart(u);
										Object o3=bpart.getContent();
										if(o3 instanceof String){
											String new2=(String)o3;
											if(!read.equals("rd")){
												read="rd";
											}//end of if(!read.equals("rd")){
										}//end of if(o3 instanceof String)
									}//end of for(int u=0;u<mp1.getCount();u++)
								}//end of else part if (o2 instanceof String)
							}//end of if  part of if(bodyPartCount==0)
							if (disposition != null && (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT))){

								f++;
								MimeBodyPart mbp = (MimeBodyPart)bodyPart;
								DataHandler handler = bodyPart.getDataHandler();
								String fileName=((String)handler.getName()).trim();
								String OutputFileName=strInsuredReAdobeForm+fileName;
								String strFileExtension=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
								if(strFileExtension.equalsIgnoreCase("pdf"))
								{
								PdfReader pdfReader=new PdfReader(handler.getInputStream());
									stamper=new PdfStamper(pdfReader,new FileOutputStream(new File(OutputFileName)), '\0', true);
									AcroFields acroFieldsForm=pdfReader.getAcroFields();
									//	HashMap fields=(HashMap) acroFieldsForm.getFields();
									if(acroFieldsForm.getField("CLAIM_NO")!=null || acroFieldsForm.getField("PRE_AUTH_NUMBER")!=null){

										if((!(TTKCommon.checkNull(acroFieldsForm.getField("CLAIM_NO"))).equalsIgnoreCase("")) && (!(TTKCommon.checkNull(acroFieldsForm.getField("PRE_AUTH_NUMBER"))).equalsIgnoreCase("")) )
										{		
											adobeAttachmentReaderVO=new AdobeAttachmentReaderVO();
											adobeAttachmentReaderVO.setClaimNo(acroFieldsForm.getField("CLAIM_NO"));
											adobeAttachmentReaderVO.setPreAuthNo(acroFieldsForm.getField("PRE_AUTH_NUMBER"));
											adobeAttachmentReaderVO.setClaimType(acroFieldsForm.getField("CLAIM_TYPE"));
											adobeAttachmentReaderVO.setInsurerRemarks(acroFieldsForm.getField("INSURER_REMARKS"));
											adobeAttachmentReaderVO.setInsuranceCompany(acroFieldsForm.getField("INSURANCE_COMPANY"));
											adobeAttachmentReaderVO.setApproval(acroFieldsForm.getField("APPROVAL"));
											adobeAttachmentReaderVO.setProcessingStatus("Claim");
											alInsuranceClaimResponseList.add(adobeAttachmentReaderVO);
										}//end of if((!(acroFieldsForm.getField("CLAIM_NO").trim()).equalsIgnoreCase("")) && (!(acroFieldsForm.getField("PRE_AUTH_NUMBER").trim()).equalsIgnoreCase("")) )
										else if((!(TTKCommon.checkNull(acroFieldsForm.getField("CLAIM_NO")).equalsIgnoreCase(""))) && (TTKCommon.checkNull(acroFieldsForm.getField("PRE_AUTH_NUMBER"))).equalsIgnoreCase(""))
										{
											adobeAttachmentReaderVO=new AdobeAttachmentReaderVO();
											adobeAttachmentReaderVO.setClaimNo(acroFieldsForm.getField("CLAIM_NO"));
											adobeAttachmentReaderVO.setPreAuthNo("");
											adobeAttachmentReaderVO.setClaimType(acroFieldsForm.getField("CLAIM_TYPE"));
											adobeAttachmentReaderVO.setInsurerRemarks(acroFieldsForm.getField("INSURER_REMARKS"));
											adobeAttachmentReaderVO.setInsuranceCompany(acroFieldsForm.getField("INSURANCE_COMPANY"));
											adobeAttachmentReaderVO.setApproval(acroFieldsForm.getField("APPROVAL"));
											adobeAttachmentReaderVO.setProcessingStatus("Claim");
											alInsuranceClaimResponseList.add(adobeAttachmentReaderVO);
										}//end of else if((!(acroFieldsForm.getField("CLAIM_NO").trim()).equalsIgnoreCase("")) && (acroFieldsForm.getField("PRE_AUTH_NUMBER").trim()).equalsIgnoreCase(""))
										else if((!(TTKCommon.checkNull(acroFieldsForm.getField("PRE_AUTH_NUMBER"))).equalsIgnoreCase("")) && (TTKCommon.checkNull(acroFieldsForm.getField("CLAIM_NO"))).equalsIgnoreCase(""))
										{
											adobePatAttachmentReaderVO=new AdobePatAttachmentReaderVO();
											adobePatAttachmentReaderVO.setPreAuthNo(acroFieldsForm.getField("PRE_AUTH_NUMBER"));
											adobePatAttachmentReaderVO.setClaimType(acroFieldsForm.getField("CLAIM_TYPE"));
											adobePatAttachmentReaderVO.setInsurerRemarks(acroFieldsForm.getField("INSURER_REMARKS"));
											adobePatAttachmentReaderVO.setInsuranceCompany(acroFieldsForm.getField("INSURANCE_COMPANY"));
											adobePatAttachmentReaderVO.setApproval(acroFieldsForm.getField("APPROVAL"));
											adobePatAttachmentReaderVO.setProcessingStatus("Preauth");
											alInsurancePatResponseList.add(adobePatAttachmentReaderVO);
										}//end of  else if((!(acroFieldsForm.getField("PRE_AUTH_NUMBER").trim()).equalsIgnoreCase("")) && (acroFieldsForm.getField("CLAIM_NO").trim()).equalsIgnoreCase(""))
									}//end of if(acroFieldsForm.getField("CLAIM_NO")!=null || acroFieldsForm.getField("PRE_AUTH_NUMBER")!=null){
									stamper.close();
								}// end of if(strFileExtension.equalsIgnoreCase("pdf"))

							}//end of if (disposition != null && (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)))

						}//end of for (int x = 0; x < multipart.getCount(); x++)

					}// end of else if (content instanceof Multipart)

				}// end of try block
				catch(Exception e11)
				{
					e11.printStackTrace();
					
				}
				
			}// end of 	for (int messageCount = 0; messageCount< message.length; messageCount++)

			if(!(alInsuranceClaimResponseList.isEmpty()))	{
				hashMap.put("Claims", alInsuranceClaimResponseList);
			}//end of if(!(alInsuranceClaimResponseList.isEmpty()))
			else if(!(alInsurancePatResponseList.isEmpty())){
				hashMap.put("PreAuths", alInsurancePatResponseList);
			}//end of else if(!(alInsurancePatResponseList.isEmpty()))
			if(!(hashMap.isEmpty()))		{
				UpdateAdobeData(hashMap);
			}//end of if(!(hashMap.isEmpty()))

		}//end of try block
		
		catch(TTKException ttkExp)
		{
			throw new TTKException(ttkExp,AdobeFormMailAttachement);

		}//end of catch
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch


	}


	public  void UpdateAdobeData(HashMap alHashMap)throws TTKException{
		ClaimIntimationSmsManager claimIntimationSmsManager = null;
		claimIntimationSmsManager = this.getClaimIntimationSmsManagerObject();

		try{
			if(alHashMap!=null)
			{
				if((ArrayList)alHashMap.get("Claims")!=null)
				{
					claimIntimationSmsManager.saveInsuranceStatusFromAdobe((ArrayList)alHashMap.get("Claims"));
				}//end of if((ArrayList)alHashMap.get("Claims")!=null)
				if((ArrayList)alHashMap.get("PreAuths")!=null)
				{
					claimIntimationSmsManager.savePatInsuranceStatusFromAdobe((ArrayList)alHashMap.get("PreAuths"));
				}//end of if((ArrayList)alHashMap.get("PreAuths")!=null)
			}// end of if(alHashMap!=null)
		}//try
		catch(TTKException ttkExp)
		{
			throw new TTKException(ttkExp,AdobeFormMailAttachement);

		}//end of catch
		catch(Exception exp)
		{
			new Exception(AdobeFormMailAttachement);
			exp.printStackTrace();
		}//end of catch


	}





	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimIntimationSmsManager getClaimIntimationSmsManagerObject() throws TTKException
	{
		ClaimIntimationSmsManager claimIntimationSmsManager = null;
		try
		{
			if(claimIntimationSmsManager == null)
			{
				InitialContext ctx = new InitialContext();
				//claimIntimationSmsManager = (ClaimIntimationSmsManager) ctx.lookup(ClaimIntimationSmsManager.class.getName());
				claimIntimationSmsManager = (ClaimIntimationSmsManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimIntimationSmsManagerBean!com.ttk.business.claims.ClaimIntimationSmsManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,AdobeFormMailAttachement );
		}//end of catch
		return claimIntimationSmsManager;
	}//end getCommunicationManagerObject()




}
