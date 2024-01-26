package com.ttk.common;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.beanutils.converters.ByteConverter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
public class UploadMembers 
{
	
	
	
	
	public static String  uploadMembers(Document document,String batchNO) throws Exception{
		
		Connection conn=null;
		CallableStatement callableStatement=null;
		CallableStatement enrlCstm=null;
		PreparedStatement saveMemberPhotoPstm=null;
		String policyLogs="<MemberUpload>";
		
		
		List<Node> policyNodes=document.selectNodes("/MemberUpload/Policy");
		 
		conn=ResourceManager.getConnection();
		callableStatement=conn.prepareCall("{call POLICY_DATA_FEED_PKG.softcopy_upload(?)}");
		enrlCstm=conn.prepareCall("{call POLICY_ENROLLMENT_PKG.GET_WEBSER_MEM_RENE_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?)}");
		saveMemberPhotoPstm=conn.prepareStatement("INSERT INTO idcard.image_data(ENROLMENT_ID,IMAGE,ADDUSER,ADDDATE,UNITCODE,IMG_SEQ_ID,UPDATE_V2) VALUES  (?,?,?,SYSDATE,'DXB',IDCARD.IMG_SEQID.NEXTVAL,'Y'  )");
		String sysDate=new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
		if(policyNodes!=null&&policyNodes.size()>0)
		{
		 for(Node policyNode:policyNodes){
			 
			 Node policyDetailsNode=policyNode.selectSingleNode("./PolicyDetails");
			 if(policyDetailsNode!=null){
				 
				 HashMap<String, String> hmPolicyDetails=getPolicyDetails(conn,policyDetailsNode);
			if(hmPolicyDetails!=null){
				
				hmPolicyDetails.put("BATCH_NO", batchNO);
				hmPolicyDetails.put("RECEIVED_DATE", sysDate);
				
				policyLogs+="<Policy><PolicyDetails><Status>1</Status><SerialNO>"+getAttributeValue(policyNode, "SerialNO")+"</SerialNO><Desc>Policy Details Are Valid</Desc></PolicyDetails>";
				
				List<Node> empNodes=policyNode.selectNodes("./Employee");
				
				if(empNodes!=null){
					
					for(Node empNode:empNodes){
						
						policyLogs+="<Employee><SerialNO>"+getAttributeValue(empNode, "SerialNO")+"</SerialNO>";
						
						
						
						Node empDetailsNode=empNode.selectSingleNode("./EmployeeDetails");
						
						
						
						List<Node> memNodes=empNode.selectNodes("./Member");
						
						if(memNodes!=null){
							
							for(Node memNode:memNodes){
								
								
								String memLog=saveMember(callableStatement,enrlCstm,saveMemberPhotoPstm,empDetailsNode,memNode,hmPolicyDetails);
								policyLogs+=memLog;
							}//for(Node memNode:memNodes){
							
							
						}//if(memNodes!=null){
						
						policyLogs+="</Employee>";
					}//for(Node empNode:empNodes){
					
				}//if(empNodes!=null){
				policyLogs+="</Policy>";
				
			}//if(policyDetails!=null){
			else{
				
				policyLogs+="<Policy><PolicyDetails><Status>0</Status><SerialNO>"+getAttributeValue(policyNode, "SerialNO")+"</SerialNO><Desc>Policy Details Are Not Valid</Desc></PolicyDetails></Policy>";
			}
			 
			 }// if(policyDetailsNode!=null){
			
			 
			 
			 
			 
		 }// for(Node policyNode:policyNodes){
		
	}
		else{
			policyLogs+="<Tanscation><status>0</status><Description>Please provide ProperXmlFormat</Description></Tanscation>";
		}
		  policyLogs+="</MemberUpload>";
	closeStmtResource(saveMemberPhotoPstm, null);
	closeStmtResource(enrlCstm, null);
	closeConnResource(conn, callableStatement, null);
	
	return policyLogs;
	}
	
	
	private static String saveMember(CallableStatement callableStatement ,CallableStatement enrlCstmm,PreparedStatement saveMemberPhotoStm,Node empDetailsNode,Node memNode,HashMap<String, String>hmPolicyDetails){
		
	String memLog="<Member>";
		try{
			String photoAttachment=null;
			Node photoNode=memNode.selectSingleNode(".//Photo");
			
			 memLog+="<SerialNO>"+getAttributeValue(memNode, "SerialNO")+"</SerialNO>";
			 
			 if(photoNode!=null){
			String photoType=photoNode.valueOf("@type");
			Node attchNode=photoNode.selectSingleNode("./Attachment");
			if(attchNode!=null)photoAttachment=attchNode.getText();
			if(photoType!=null&&photoType.length()>0){
			if(!("JPG".equalsIgnoreCase(photoType)||"JPEG".equalsIgnoreCase(photoType)||"PNG".equalsIgnoreCase(photoType))){
				memLog+="<Status>0</Status>";		   		
		   		 memLog+="<Desc>Photo type is not valid</Desc>";
		   		memLog+="</Member>";
		   		return memLog;
			}//if(!("JPG".equals(photoType)||"JPEG".equals(photoType)||"PNG".equals(photoType))){
			}//if(photoType!=null&&photoType.length()>0){
				photoNode.detach();	
			}//if(photoNode!=null){
			
			
			String strBatchPolicy=getPolicyNodeDetails(empDetailsNode,memNode,hmPolicyDetails);
			callableStatement.setString(1,strBatchPolicy);
			callableStatement.executeUpdate();	
			memLog+="<Status>1</Status>";			
			memLog+="<Desc>Success</Desc>";	
			String result[]=getEnrolledDetails(enrlCstmm, empDetailsNode,memNode, hmPolicyDetails);
			memLog+=result[0];
			if(photoAttachment!=null)
			{
			savePhotoAttachment(saveMemberPhotoStm,photoAttachment, result[1]);
			}
			}
			catch(SQLException sqlException)
			{
			memLog+="<Status>0</Status>";
			String errorDetails=GetErrorMessage.getValue(sqlException.getErrorCode());
	   		if("unknownError".equals(errorDetails)) memLog+="<Desc>"+sqlException.getMessage()+"</Desc>";   
	   		else memLog+="<Desc>"+errorDetails+"</Desc>";
			} 
			memLog+="</Member>";
	 return memLog;
	}
	
	
	
	private static void savePhotoAttachment(PreparedStatement saveMemberPhotoStm,String photoAttachment,String enrollemnetID) throws SQLException
	{

				ByteArrayInputStream bai=new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(photoAttachment));
				saveMemberPhotoStm.setString(1,enrollemnetID);
				saveMemberPhotoStm.setBinaryStream(2,bai,photoAttachment.length());
				saveMemberPhotoStm.setInt(3,1);
				saveMemberPhotoStm.executeUpdate();
		
	}

	
	
		private static String getPolicyNodeDetails(Node empDetailsNode,Node memNode,HashMap<String, String>policyDetails)
		{
		Document softCopyDoc=DocumentHelper.createDocument();
		Document tempDoc=DocumentHelper.createDocument();
		Element batchEle=softCopyDoc.addElement("batch");
		
		Element fileDetailsEle=batchEle.addElement("filedetail");
		
		fileDetailsEle.addAttribute("batchnumber", policyDetails.get("BATCH_NO"));
		fileDetailsEle.addAttribute("enroltype","COR");
		fileDetailsEle.addAttribute("insuranceabbrev",policyDetails.get("ABBREVATION_CODE") );
		fileDetailsEle.addAttribute("insurancecode",policyDetails.get("INS_SEQ_ID") );
		fileDetailsEle.addAttribute("insuranceid", policyDetails.get("INS_SEQ_ID"));
		fileDetailsEle.addAttribute("noofpoliciesreceived","1");
		fileDetailsEle.addAttribute("policyCategory",policyDetails.get("POLICYCATEGORY") );
		fileDetailsEle.addAttribute("policyfilename",policyDetails.get("BATCH_NO")+".xml" );
		fileDetailsEle.addAttribute("policyno",policyDetails.get("POLICY_NUMBER") );
		fileDetailsEle.addAttribute("receiveddate", policyDetails.get("RECEIVED_DATE"));
		fileDetailsEle.addAttribute("receivedfrom","WS");
		fileDetailsEle.addAttribute("requestseqid", policyDetails.get("ENROL_BATCH_SEQ_ID"));
		fileDetailsEle.addAttribute("requeststatus", "BEG");
		fileDetailsEle.addAttribute("ttkoffice","1");
		fileDetailsEle.addAttribute("uploadedby", "1");
		fileDetailsEle.addAttribute("uploadtype","END");
		Element policyEle=batchEle.addElement("policy");
		Node persNode=memNode.selectSingleNode("./PersonalInfo");
		if(persNode==null)persNode=tempDoc.addElement("PersonalInfo");
		policyEle.addAttribute("serialNo",getAttributeValue(memNode, "SerialNO"));
		policyEle.addAttribute("capitationType", policyDetails.get("CAPITATIONTYPE"));
		policyEle.addAttribute("groupid", policyDetails.get("GROUP_REG_SEQ_ID"));
		policyEle.addAttribute("grouppolicynumber", policyDetails.get("POLICY_NUMBER"));
		policyEle.addAttribute("policyholdername", getAttributeValue(empDetailsNode, "PrincipalName"));
		policyEle.addAttribute("policyHolderFamilyName", getText(persNode.selectSingleNode("./FamilyName")));
		policyEle.addAttribute("policyHolderMidleName", getText(persNode.selectSingleNode("./SecondName")));
		policyEle.addAttribute("policyno", policyDetails.get("POLICY_NUMBER"));
		policyEle.addAttribute("policytype","COR");
		policyEle.addAttribute("relationship",getText(persNode.selectSingleNode("./Relationship")));
		policyEle.addAttribute("uploadstatus", "N");
	    Element productEle=policyEle.addElement("product");
	    productEle.addAttribute("batchno",policyDetails.get("BATCH_NO"));
	    productEle.addAttribute("productid",policyDetails.get("PRODUCTSEQID"));
	    productEle.addAttribute("producttype",policyDetails.get("PRODUCTTYPE"));
	      
	      
	      Element empleeEle=policyEle.addElement("employee");	      
	      empleeEle.addAttribute("commissionbased", getAttributeValue(empDetailsNode, "Commissionbased"));
	      empleeEle.addAttribute("dateofjoining", empDetailsNode.valueOf("@Doj"));
	      empleeEle.addAttribute("dateofmarriage", getText(persNode.selectSingleNode("./Dom")));
	      empleeEle.addAttribute("designation", empDetailsNode.valueOf("@Designation"));
	      empleeEle.addAttribute("employeeno",empDetailsNode.valueOf("@EmpNO"));
	     // empleeEle.addAttribute("encode", empDetailsNode.valueOf("@pwd"));
	      empleeEle.addAttribute("encode", "");
	      empleeEle.addAttribute("salaryband", empDetailsNode.valueOf("@SalaryBand"));
	     // empleeEle.addAttribute("locationcode", empDetailsNode.valueOf("@LocationCode"));
	      empleeEle.addAttribute("locationcode", "");
	      empleeEle.addAttribute("tpaenrollmentno",empDetailsNode.valueOf("@EnrollmentNO")); 
	      Node contactNode=memNode.selectSingleNode("./ContactDetail");
		  if(contactNode==null)contactNode=tempDoc.addElement("ContactDetail");
	      Element policyContactdetail=policyEle.addElement("contactdetail");
	      policyContactdetail.addElement("address1").setText(getText(contactNode.selectSingleNode("./Address1"))); 
	      policyContactdetail.addElement("city").setText(getText(contactNode.selectSingleNode("./City"))); 
	      policyContactdetail.addElement("state").setText(getText(contactNode.selectSingleNode("./State")));
	      policyContactdetail.addElement("country").setText(getText(contactNode.selectSingleNode("./Country"))); 
	      policyContactdetail.addElement("pincode").setText(getText(contactNode.selectSingleNode("./Pincode"))); 
	      policyContactdetail.addElement("emailid").setText(getText(contactNode.selectSingleNode("./Emailid")));
	      policyContactdetail.addElement("phones").addAttribute("cell",getText(contactNode.selectSingleNode("./PhoneNO")));
	      policyContactdetail.addElement("resilocation").setText(getText(contactNode.selectSingleNode("./ResiLocation")));
	      policyContactdetail.addElement("worklocation").setText(empDetailsNode.valueOf("@WorkLocation"));	
	      Node polInfoNode=memNode.selectSingleNode("./PolicyInfo");
		  if(polInfoNode==null)polInfoNode=tempDoc.addElement("PolicyInfo"); 
	      Element member=policyEle.addElement("member");
		      member.addAttribute("dateofbirth",getText(persNode.selectSingleNode("./Dob")));
		      member.addAttribute("dateofexit",getText(polInfoNode.selectSingleNode("./DateOfExit")));
		      member.addAttribute("dateofinception",getText(polInfoNode.selectSingleNode("./DateOfInception")));
		      member.addAttribute("emirateid",getText(polInfoNode.selectSingleNode("./EmirateID")));		      
		      member.addAttribute("enrolmentid",getText(polInfoNode.selectSingleNode("./EnrollmentID")));
		      member.addAttribute("familyname",getText(persNode.selectSingleNode("./FamilyName")));
		      member.addAttribute("firstname",getText(persNode.selectSingleNode("./FirstName")));
		      member.addAttribute("secondname",getText(persNode.selectSingleNode("./SecondName")));
		      member.addAttribute("gender",getText(persNode.selectSingleNode("./Gender")));
		      member.addAttribute("globalnetid",getText(persNode.selectSingleNode("./GlobalnetID")));
		      member.addAttribute("maritalstatus",getText(persNode.selectSingleNode("./MaritalStatus")));
		      member.addAttribute("nationality",getText(persNode.selectSingleNode("./Nationality")));
		      member.addAttribute("passportnumber",getText(persNode.selectSingleNode("./PassportNO")));
		      member.addAttribute("nationality",getText(persNode.selectSingleNode("./Nationality")));
		      member.addAttribute("proRataPremium",getText(persNode.selectSingleNode("./ProRataPremium")));
		      member.addAttribute("relationship",getText(persNode.selectSingleNode("./Relationship")));
		      //member.addAttribute("secondname",getText(persNode.selectSingleNode("./SecondName")));
		      member.addAttribute("status",getText(persNode.selectSingleNode("./Status")));
		      member.addAttribute("uidnumber",getText(persNode.selectSingleNode("./UidNO")));
		      member.addAttribute("vip",getText(persNode.selectSingleNode("./Vip")));
		      member.addElement("endorsement").addAttribute("endtype",getText(persNode.selectSingleNode("./Status")));
		      
		      
		      Element memContactdetail=member.addElement("contactdetail");
		      memContactdetail.addElement("address1").setText(getText(contactNode.selectSingleNode("./Address1"))); 
		      memContactdetail.addElement("city").setText(getText(contactNode.selectSingleNode("./City"))); 
		      memContactdetail.addElement("state").setText(getText(contactNode.selectSingleNode("./State")));
		      memContactdetail.addElement("country").setText(getText(contactNode.selectSingleNode("./Country"))); 
		      memContactdetail.addElement("pincode").setText(getText(contactNode.selectSingleNode("./Pincode"))); 
		      memContactdetail.addElement("emailid").setText(getText(contactNode.selectSingleNode("./Emailid")));
		      memContactdetail.addElement("phones").addAttribute("cell",getText(contactNode.selectSingleNode("./PhoneNO")));
		      memContactdetail.addElement("resilocation").setText(getText(contactNode.selectSingleNode("./ResiLocation")));
		      memContactdetail.addElement("worklocation").setText(empDetailsNode.valueOf("@WorkLocation"));
		      
		      
		      Node premiumNode=memNode.selectSingleNode("./PremiumDetails");
				if(premiumNode==null)premiumNode=tempDoc.addElement("PremiumDetails");
		      
			
		      Element capitationdetail=member.addElement("capitationdetail");
		      capitationdetail.addElement("brokerMargin").setText(getText(premiumNode.selectSingleNode("./BrokerMargin")));
		      capitationdetail.addElement("brokerMarginUnit").setText(getText(premiumNode.selectSingleNode("./BrokerMarginUnit"))); 
		      capitationdetail.addElement("dentalPremium").setText(getText(premiumNode.selectSingleNode("./DentalPremium")));
		      capitationdetail.addElement("insurerMargin").setText(getText(premiumNode.selectSingleNode("./InsurerMargin"))); 
		      capitationdetail.addElement("insurerMarginUnit").setText(getText(premiumNode.selectSingleNode("./InsurerMarginUnit"))); 
		      capitationdetail.addElement("ipNetPremium").setText(getText(premiumNode.selectSingleNode("./IpNetPremium")));
		      capitationdetail.addElement("maternityPremium").setText(getText(premiumNode.selectSingleNode("./MaternityPremium"))); 
		      capitationdetail.addElement("minAge").setText(getText(premiumNode.selectSingleNode("./MinAge")));
		      capitationdetail.addElement("maxAge").setText(getText(premiumNode.selectSingleNode("./MaxAge")));		      
		      capitationdetail.addElement("medicalPremium").setText(getText(premiumNode.selectSingleNode("./MedicalPremium")));		     
		      capitationdetail.addElement("opticalPremium").setText(getText(premiumNode.selectSingleNode("./OpticalPremium"))); 
		      capitationdetail.addElement("otherMargin").setText(getText(premiumNode.selectSingleNode("./OtherMargin"))); 
		      capitationdetail.addElement("otherMarginUnit").setText(getText(premiumNode.selectSingleNode("./OtherMarginUnit")));
		      capitationdetail.addElement("reInsBrkMargin").setText(getText(premiumNode.selectSingleNode("./ReInsBrkMargin"))); 
		      capitationdetail.addElement("reInsBrkMarginUnit").setText(getText(premiumNode.selectSingleNode("./ReInsBrkMarginUnit"))); 
		      capitationdetail.addElement("tpaMargin").setText(getText(premiumNode.selectSingleNode("./TpaMargin")));
		      capitationdetail.addElement("tpaMarginUnit").setText(getText(premiumNode.selectSingleNode("./TpaMarginUnit"))); 
		      capitationdetail.addElement("wellnessPremium").setText(getText(premiumNode.selectSingleNode("./WellnessPremium")));
		      Node sumInsNode=memNode.selectSingleNode("./SumInsured");
			  if(sumInsNode==null)sumInsNode=tempDoc.addElement("suminsured");
			  Element suminsured=member.addElement("suminsured").addElement("value");
			  suminsured.addAttribute("amount",sumInsNode.valueOf("@amount") ).addAttribute("currency",sumInsNode.valueOf("@currency")); 
			  Node nomineeNode=memNode.selectSingleNode("./Nominee");
			  if(nomineeNode==null)nomineeNode=tempDoc.addElement("nominee");
		      Element nominee=member.addElement("nominee");
		      nominee.addAttribute("nomineeaddress",nomineeNode.valueOf("@nomineeAddress"));
		      nominee.addAttribute("nomineeage",nomineeNode.valueOf("@nomineeAge"));
			  nominee.addAttribute("nomineecontactno",nomineeNode.valueOf("@nomineeContactno"));
			  nominee.addAttribute("nomineegender",nomineeNode.valueOf("@nomineeGender"));
			  nominee.addAttribute("nomineename",nomineeNode.valueOf("@nomineeName"));
		    return softCopyDoc.asXML();
	
	
	}
	
	
	private static String[] getEnrolledDetails(CallableStatement enrlCstm,Node empDetailsNode,Node memNode,HashMap<String, String> hmPolicyDetails){
		
		ResultSet rs=null;
		String[] result=new String[2];
		String enrollementId=null;
		StringBuilder ennrBuilder=new StringBuilder();
		
		try{
		
			ennrBuilder.append("<EnrolledDetails>");
			
			
		
		enrlCstm.setString(1,hmPolicyDetails.get("POLICY_NUMBER"));
		enrlCstm.setString(2,hmPolicyDetails.get("POLICYCATEGORY"));
		enrlCstm.setString(3,hmPolicyDetails.get("PRODUCT_NAME"));
		enrlCstm.setString(4,hmPolicyDetails.get("GROUP_ID"));
		enrlCstm.setString(5,hmPolicyDetails.get("SUB_GROUP_ID"));
		enrlCstm.setString(6,getAttributeValue(empDetailsNode,"EmpNO"));
		enrlCstm.setString(7,getText(memNode.selectSingleNode(".//Relationship")));
		enrlCstm.setString(8,getText(memNode.selectSingleNode(".//FirstName")));
		enrlCstm.setString(9,getText(memNode.selectSingleNode(".//SecondName")));
		enrlCstm.setString(10,getAttributeValue(empDetailsNode,"PrincipalName"));
		enrlCstm.setString(11,getText(memNode.selectSingleNode(".//FamilyName")));
		enrlCstm.registerOutParameter(12, OracleTypes.CURSOR);		
		enrlCstm.execute();
		
		rs=(ResultSet)enrlCstm.getObject(12);
		
		if(rs!=null){
			while (rs.next()) {
				enrollementId=rs.getString("TPA_ENROLLMENT_ID");
				ennrBuilder.append("<EnrollmentID>");
				ennrBuilder.append(enrollementId);
				ennrBuilder.append("</EnrollmentID>");
				
				ennrBuilder.append("<FullName>");
				ennrBuilder.append(rs.getString("MEMBER_NAME"));
				ennrBuilder.append("</FullName>");
				
				ennrBuilder.append("<Relation>");
				ennrBuilder.append(rs.getString("RELATION"));
				ennrBuilder.append("</Relation>");
			}
		}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			closeStmtResource(null, rs);
		}
		ennrBuilder.append("</EnrolledDetails>");
		result[0]=ennrBuilder.toString();
		result[1]=enrollementId;
		return result;
	}
	private static HashMap<String, String> getPolicyDetails(Connection conn,Node policyDetailsNode)throws Exception{
		HashMap<String, String> policyDetails=null;
		
		String policyNO=getText(policyDetailsNode.selectSingleNode("./PolicyNumber"));
		String policyCategory=getText(policyDetailsNode.selectSingleNode("./PolicyCategory"));
		String productName=getText(policyDetailsNode.selectSingleNode("./ProductName"));
		String groupID=getText(policyDetailsNode.selectSingleNode("./GroupID"));
		String subGroupID=getText(policyDetailsNode.selectSingleNode("./SubGroupID"));

		
		CallableStatement cStmtObject = (java.sql.CallableStatement)conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.GET_WEBSER_POLICY_DETAILS(?,?,?,?,?,?)}");
		cStmtObject.setString(1,policyNO);
		cStmtObject.setString(2,policyCategory);
		cStmtObject.setString(3,productName);
		cStmtObject.setString(4,groupID);
		cStmtObject.setString(5,subGroupID);
		cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
		
		cStmtObject.execute();
		ResultSet rs=(ResultSet) cStmtObject.getObject(6);
		
	
			if(rs!=null&&rs.next())
			{
				policyDetails=new HashMap<String, String>();
				
				policyDetails.put("CAPITATIONTYPE",rs.getString("CAPITATIONTYPE"));								
				policyDetails.put("ABBREVATION_CODE",rs.getString("ABBREVATION_CODE"));
				policyDetails.put("INS_SEQ_ID",rs.getString("INS_SEQ_ID"));				
				policyDetails.put("POLICYCATEGORY",rs.getString("POLICYCATEGORY"));
				policyDetails.put("POLICY_NUMBER",rs.getString("POLICY_NUMBER"));
				policyDetails.put("PRODUCTSEQID",rs.getString("PRODUCT_SEQ_ID"));
				policyDetails.put("PRODUCTTYPE",rs.getString("PRODUCT_TYPE"));
				policyDetails.put("ENROL_BATCH_SEQ_ID",rs.getString("ENROL_BATCH_SEQ_ID"));
				policyDetails.put("GROUP_REG_SEQ_ID",rs.getString("GROUP_REG_SEQ_ID"));
				
				policyDetails.put("GROUP_ID",groupID);
				policyDetails.put("SUB_GROUP_ID",subGroupID);
				policyDetails.put("PRODUCT_NAME",productName);
				
		}
		
			closeStmtResource(cStmtObject,rs);
	
		
		return policyDetails;
		
	}
	
private static String   getText(Node node ){
	String text="";

	if(node!=null){
		text=node.getText();
		text=text!=null?text.trim():"";
	}
return text;
}
	

private static String  getAttributeValue(Node node,String name){
	String text="";
	if(node!=null) 
	{
		text=node.valueOf("@"+name);
		text=text!=null?text.trim():"";
	}
	return text;
}
private static void closeStmtResource(Statement stm,ResultSet rs){
	
	try{
		if(rs!=null)rs.close();
		if(stm!=null)stm.close();
	}catch(Exception e){e.printStackTrace();}


}
	
	private static void closeConnResource(Connection conn,Statement stm,ResultSet rs){
		
		try{
			if(rs!=null)rs.close();
			if(stm!=null)stm.close();
		}catch(Exception e){e.printStackTrace();}
	
  try{
		if(conn!=null)conn.close();
	}catch(Exception e){e.printStackTrace();}
	}
}