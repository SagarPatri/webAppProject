package com.ttk.common.webservices;


import javax.naming.InitialContext;

import org.apache.axis.AxisFault;

import com.ttk.business.webservice.WebServiceManager;
import com.ttk.common.exception.TTKException;


public class VidalPolicyWebService
{
public byte[] getPolicyDetails(String insuranceLicenceID,String userName,String passWord,byte[] policyList) throws AxisFault
{
	
	StringBuilder document=new StringBuilder();
	String  strResult=null;

try{

				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.getPolicyDetailsXML(insuranceLicenceID,strResult,userName,passWord,policyList);
			
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	document=new StringBuilder();
	document.append(strResult);
	return document.toString().getBytes();
	
}

public byte[] getAllPolicyDetails(String insuranceLicenceID,String userName,String passWord) throws AxisFault
{
	
	StringBuilder outPutFile=new StringBuilder();
	String  strResult=null;
	try{
			
				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		            strResult = webServiceManager.getAllPolicyDetailsXML(insuranceLicenceID,userName,passWord);
				
				
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
       /* AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));
        throw fault;*/
        return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	outPutFile.append(strResult);
	return outPutFile.toString().getBytes();
	
}


public byte[] createPolicy(String insuranceLicenceID,String userName,String passWord,String policyType,byte[] inputXML) throws AxisFault
{
	
	StringBuilder document=new StringBuilder();
	String  strResult=null;
	

try{

				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.craetePolicyDetails(insuranceLicenceID,strResult,userName,passWord,policyType,inputXML);
			
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	document=new StringBuilder();
	document.append(strResult);
	return document.toString().getBytes();
	
}

public byte[] uploadMembers(String insuranceLicenceID,String userName,String passWord,byte[] inputMemberXML) throws AxisFault
{
	String strResult = null;
 
try{
				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.memberUpload(insuranceLicenceID, userName, passWord,  inputMemberXML);
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	
	return strResult.getBytes();
	
}

public byte[] publishMembers(String insuranceLicenceID,String userName,String passWord,byte[] inputPolicyDetails) throws AxisFault
{
	String strResult = "";
 
try{
				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.publishMembers(insuranceLicenceID, userName, passWord,  inputPolicyDetails);
			
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	
	return strResult.getBytes();
	
}
public byte[] publishProductDetails(String insuranceLicenceID,String userName,String passWord) throws AxisFault
{
	String strResult = "";
 
try{
				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.publishProductDetails(insuranceLicenceID, userName, passWord);
			
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	
	return strResult.getBytes();
	
}

public byte[] publishCorporateDetails(String insuranceLicenceID,String userName,String passWord) throws AxisFault
{
	String strResult = "";
 
try{
				  WebServiceManager webServiceManager=this.getWebServiceManagerObject();
		          strResult = webServiceManager.publishCorporateDetails(insuranceLicenceID, userName, passWord);
			
	
	}// end of try
	catch(Exception exp)
    {
        exp.printStackTrace();
      /*  AxisFault fault=new AxisFault();
        fault.setFaultCodeAsString(ValidatorResourceHelper.getResourceBundle().getString(exp.getMessage()));*/
        //throw fault;
       // return getErrorXml(exp.getMessage()).getBytes();
    }//end of catch
	
	
	return strResult.getBytes();
	
}
private WebServiceManager getWebServiceManagerObject() throws TTKException
{
    WebServiceManager webServiceManager = null;
    try
    {
        if(webServiceManager == null)
        {
            InitialContext ctx = new InitialContext();
            webServiceManager = (WebServiceManager) ctx.lookup("java:global/TTKServices/business.ejb3/WebServiceManagerBean!com.ttk.business.webservice.WebServiceManager");
        }//end if(webServiceManager == null)
    }//end of try
    catch(Exception exp)
    {
        throw new TTKException(exp, "webservice");
    }//end of catch
    return webServiceManager;  
}//end getWebServiceManagerObject()

private String getErrorXml(String errorDesc){
	StringBuilder builder=new StringBuilder();
	
	String strVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		String strRootS="<PolicyDetailsList>";
		String strRootE="</PolicyDetailsList>";
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
		builder.append(strTxtE);
		builder.append("\n");
		builder.append(strDescS);
		builder.append(errorDesc);
		builder.append(strDescE);
		builder.append(strRootE);
		return builder.toString();
}

}
