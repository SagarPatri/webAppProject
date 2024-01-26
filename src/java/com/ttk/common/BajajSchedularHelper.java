package com.ttk.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import javax.naming.InitialContext;
import javax.xml.rpc.holders.BigDecimalHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.log4j.Logger;

import com.ttk.business.webservice.BAclaimsUploaderManager;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;

import com.ttk.common.externalwebservices.BjazWebServiceAll_wsdl.BjazWebServiceAllProxy;


public class BajajSchedularHelper {
	private static Logger log = Logger.getLogger( BajajSchedularHelper.class );
	private static final long serialVersionUID = 1L;
	private static final String strBajajAllianz="Scheduled Xml Insertion";
	
	private static final String pUserId="ttk.claim@vidalhealth.com";
	private static final String pPassKey="TTKhealth$2013$%";
	private static final String pProcessFlag="CLAIM_DATA_PUSH";
	private static final String pTieupName="TTK";
	
	BAclaimsUploaderManager bAclaimsUploaderManager = null ;
	BjazWebServiceAllProxy bjazWebServiceAllProxy=null;
	//private static final Logger log = Logger.getLogger( BajajSchedularHelper.class);
	public  void processBajajXml() throws RemoteException,TTKException
	{
		
		log.info("Inside processBajajXml method in BajajSchedularHelper");
		
		String filename="";	String filefullpath="";	String outputFolder="";File folder=null;
		String folderlocation=(String)TTKPropertiesReader.getPropertyValue("BajajAllianzXmlDirectory");
		bAclaimsUploaderManager = this.getBAclaimsUploaderManagerObject();
			try{
		      folder = new File(folderlocation);
			  File  fileList[]=folder.listFiles();	
		                  	for(int i=0;i<fileList.length;i++)
		                  	{
		                  		if (fileList[i].isFile()) {
		                  			filename = fileList[i].getName();
		                  			filefullpath=folderlocation+fileList[i].getName();	
		                  			
		                  			String fileExtension=filename.substring(filename.lastIndexOf('.') + 1, filename.length()).toLowerCase();
		                  			if (fileExtension.equalsIgnoreCase("xml"))  {
		                  				//File readingFile=new File(filefullpath);
		                  				   
		                  				    String xmlData=getClobData(new FileInputStream(new File(filefullpath)));
		                  				   // 
		                  				    StringHolder pInputDataXmlStr_inout=new StringHolder();
		                  				    pInputDataXmlStr_inout.value=xmlData;
		                  				    BigDecimalHolder pErrorCode_out=new BigDecimalHolder();
		                  				    pErrorCode_out.value=new BigDecimal(0.00);
		                  				    
		                  					                  				
		                  			   //	String pUserId="ttk.claim@ttkhealthcareservices.com";String pPassKey="TTKhealth$2013$%";String pProcessFlag="CLAIM_DATA_PUSH";	String pTieupName="TTK";
	             				
		                  				 HashMap outParams=(HashMap)getResponseFromBajaj(pUserId, pPassKey, pProcessFlag, pTieupName, pInputDataXmlStr_inout, pErrorCode_out);
		                  				String resultXml=(String)outParams.get("pInputDataXmlStr_inout");
		                  				BigDecimal resultErrorCode=(java.math.BigDecimal)outParams.get("pErrorCode_out");
										
		                  			 
		                  				String message=	bAclaimsUploaderManager.saveXmlFromBajaj(resultXml,filename,resultErrorCode);
		                  				
                                        if(message.equalsIgnoreCase("success"))	{
		                  					             outputFolder=folder+"/processed/"+filename;
                                                                           
		                  					             new File(filefullpath).renameTo(new File(outputFolder));
		                  				}//end of if(message.equalsIgnoreCase("success"))
		                  				else if(message.equalsIgnoreCase("fail")){
		                  					             outputFolder=folder+"/unprocessed/"+filename;
		                  					             new File(filefullpath).renameTo(new File(outputFolder));
		                  				}//end of 	else if(message.equalsIgnoreCase("fail"))
		                  				else {
		                  					             outputFolder=folder+"/noresponse/"+filename;
		                  					             new File(filefullpath).renameTo(new File(outputFolder));
		                  				}//end of 	else 
	                  			}//end of if (fileExtension.equalsIgnoreCase("xml"))  {
		                  			
		                  		}//end of if (fileList[i].isFile())
		                  	}//End of for loop
		
	            }
				 catch(TTKException TTKExp){
                  new File(filefullpath).renameTo(new File(folder+"/sqlexception/"+filename));
                  new Exception(strBajajAllianz);
                  TTKExp.printStackTrace();
                 }//end of catch(Exception e)
			        catch(SQLException sqlExp){
			 	                      new File(filefullpath).renameTo(new File(folder+"/sqlexception/"+filename));
                                       new Exception(strBajajAllianz);
		                               sqlExp.printStackTrace();
	                                  }//end of catch(Exception e)
			        catch(RemoteException rmiEXP){
				             		new File(filefullpath).renameTo(new File(folder+"/rmiexception/"+filename));
				                    new Exception(strBajajAllianz);
				                    rmiEXP.printStackTrace();
			                               }//end of catch(Exception e)
			        catch(Exception exp)  {
				       new File(filefullpath).renameTo(new File(folder+"/exception/"+filename));
		               exp.printStackTrace();
		                                  }//end of catch


		
	}
	
	public  String getClobData(InputStream inputStream)throws Exception{
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream); 
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);    
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		                  while((line = bufferedReader.readLine()) != null) {  //Read till end
			                      stringBuffer.append(line);
			                      stringBuffer.append("\n");
		                     }//end of while
		//String fileString = stringBuffer.toString();
		bufferedReader.close();         
		inputStreamReader.close();
		inputStream.close();
		// item.delete();
		return stringBuffer.toString();

	}
	
	

	   public Map getResponseFromBajaj(String pUserId,String pPassKey,String pProcessFlag,String pTieupName,StringHolder pInputDataXmlStr_inout,BigDecimalHolder pErrorCode_out) throws TTKException, RemoteException  {
		   bjazWebServiceAllProxy=new BjazWebServiceAllProxy();
			HashMap outParams=(HashMap)bjazWebServiceAllProxy.getBjazWebServiceAll_PortType().bjazSingleWsForAll(pUserId, pPassKey, pProcessFlag, pTieupName, pInputDataXmlStr_inout, pErrorCode_out);
			//String resultXml=(String)outParams.get("pInputDataXmlStr_inout");
			//BigDecimal resultErrorCode=(java.math.BigDecimal)outParams.get("pErrorCode_out");
		
		   return outParams;
	   }//end of getResponseFromBajaj(String saveXmlFromBajaj)
	

	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private BAclaimsUploaderManager getBAclaimsUploaderManagerObject() throws TTKException
	{
		BAclaimsUploaderManager bAclaimsUploaderManager = null;
		try
		{
			if(bAclaimsUploaderManager == null)
			{
				InitialContext ctx = new InitialContext();
				bAclaimsUploaderManager = (BAclaimsUploaderManager) ctx.lookup("java:global/TTKServices/business.ejb3/BAclaimsUploaderManagerBean!com.ttk.business.webservice.BAclaimsUploaderManager");
			}//end of if(BAclaimsUploaderManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBajajAllianz);
		}//end of catch
		return bAclaimsUploaderManager;
	}//end getBAclaimsUploaderManagerObject()
	
	

}