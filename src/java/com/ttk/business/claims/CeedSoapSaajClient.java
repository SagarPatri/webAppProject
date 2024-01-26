package com.ttk.business.claims;



import java.io.BufferedWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.dom4j.Document;

import com.ttk.common.TTKPropertiesReader;

public class CeedSoapSaajClient {


	public static String execute(String serviceType,Document xmlData,String mode,String authNO)throws Exception{

		
    	// Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "https://eclaimlink.dimensions-healthcare.net/DHCEG/gateway.asmx";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(xmlData), url);
        // print SOAP Response
       // System.out.print("Response SOAP Message:");
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        soapResponse.writeTo(baos);
        if("CLM".equals(mode)){
        	String path=TTKPropertiesReader.getPropertyValue("ClaimCeedPath");
        	
        	FileOutputStream fos=new FileOutputStream(path+authNO+".xml");
        	soapResponse.writeTo(fos);
        	fos.close();
        }else if("PAT".equals(mode)){
        	String path=TTKPropertiesReader.getPropertyValue("PreauthCeedPath");
        	
        	FileOutputStream fos=new FileOutputStream(path+authNO+".xml");
        	soapResponse.writeTo(fos);
        	fos.close();
        }
        soapConnection.close();
        
    return new String(baos.toByteArray());
    }

    
    
    private static SOAPMessage createSOAPRequest(Document xmlData) throws Exception {
//    	System.out.println("xmlData:::"+xmlData.asXML().toString());
		 MessageFactory messageFactory = MessageFactory.newInstance();
		 SOAPMessage soapMessage = messageFactory.createMessage();
		 SOAPPart soapPart = soapMessage.getSOAPPart();
		 String nameSpaceURI	=	"http://www.Dimensions-healthcare.net/DHCEG/CommonTypes";
   	         SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
   	         soapEnvelope.addNamespaceDeclaration("com", nameSpaceURI);
		 SOAPBody soapBody = soapEnvelope.getBody();
		//gatewaySoap.dhcegClaims("1", new Request(), "TPA033", "TP@o33@2016", dhcegResult, ceedResponse, infoMessage);

		 SOAPElement soapElement = soapBody.addChildElement("DHCEG", "com");
		 SOAPElement serviceElement = soapElement.addChildElement("service", "com");
		 serviceElement.addTextNode("1");//CODING OR MEDICAL NECESSASITY
		 SOAPElement requestElement = soapElement.addChildElement("request", "com");
		 
		
		 
	        /*File xmlFile=new File("C:\\Users\\kishor.kumar\\Desktop\\test.xml");
	        FileReader fileReader=new FileReader(xmlFile);
	        char[]data=new char[new Long(xmlFile.length()).intValue()];
	        fileReader.read(data);

	        if(fileReader!=null){
	            fileReader.close();
	        }
	        String newxmlData=new String(data);
	       System.out.println("data::"+newxmlData);
	        requestElement.addTextNode(newxmlData);*/
	        
	        
        requestElement.addTextNode(xmlData.asXML());
		// element1.addTextNode("EveryOne");
		 SOAPElement soapUserElem = soapElement.addChildElement("user", "com");
	      String username= TTKPropertiesReader.getPropertyValue("ceed.username");
	      soapUserElem.addTextNode(username);//UserName

	        SOAPElement soapPwdElem = soapElement.addChildElement("password", "com");
	        String password=TTKPropertiesReader.getPropertyValue("ceed.pwd");
	        soapPwdElem.addTextNode(password);//Password
	        
	        
	        MimeHeaders headers = soapMessage.getMimeHeaders();
	        headers.addHeader("SOAPAction", nameSpaceURI+"/DHCEG");
		 soapMessage.saveChanges();
		 return soapMessage;
	 }

    
    
public static void writeFileToDisk(String data, String fileName){
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
				bw.write(data);

				//System.out.println("Done");
				
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
	}
} 

