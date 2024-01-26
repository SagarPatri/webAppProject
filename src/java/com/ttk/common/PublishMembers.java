package com.ttk.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleTypes;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;

public class PublishMembers 
{
static Connection conn=null;
static CallableStatement callableStatement=null;
static CallableStatement memberStateMent=null;
static CallableStatement policyCstm=null;
static ResultSet memberResult=null;
static ResultSet employeeResultSet=null;
static ResultSet policyRS=null;
public static String createTakafulFormat(Document document)
{
	
	String policyNumber;
	String policyCategory;
	String productName;
	String groupID;
	String subGroupID;
	try {
		policyNumber=document.selectSingleNode("//PolicyNumber").getText();
		policyCategory=document.selectSingleNode("//PolicyCategory").getText();
		productName=document.selectSingleNode("//ProductName").getText();
		groupID=document.selectSingleNode("//GroupID").getText();
		subGroupID=document.selectSingleNode("//SubGroupID").getText();
		if(policyNumber!=null&&policyCategory!=null&&productName!=null&&groupID!=null&&subGroupID!=null)
		{
		Document takafulFormat=DocumentHelper.createDocument();
		Element memberUploadElem=takafulFormat.addElement("MemberUpload");
		Element policyElem=memberUploadElem.addElement("Policy").addAttribute("SerialNO","1");
		
		Element policyDetailsElem=policyElem.addElement("PolicyDetails");
		policyDetailsElem.addElement("PolicyNumber").setText(policyNumber);
		policyDetailsElem.addElement("PolicyCategory").setText(policyCategory);
		policyDetailsElem.addElement("ProductName").setText(productName);
		policyDetailsElem.addElement("GroupID").setText(groupID);
		policyDetailsElem.addElement("SubGroupID").setText(subGroupID);
		
		
		
		conn=ResourceManager.getConnection();
		
		policyCstm = (java.sql.CallableStatement)conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.GET_WEBSER_POLICY_DETAILS(?,?,?,?,?,?)}");
		policyCstm.setString(1,policyNumber);
		policyCstm.setString(2,policyCategory);
		policyCstm.setString(3,productName);
		policyCstm.setString(4,groupID);
		policyCstm.setString(5,subGroupID);
		policyCstm.registerOutParameter(6,OracleTypes.CURSOR);
		
		policyCstm.execute();
		policyRS=(ResultSet) policyCstm.getObject(6);
		
		
	
	   if(policyRS!=null&&policyRS.next()){
			
		
		callableStatement=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.GET_WEBSER_EMPLOYEE_DETAILS(?,?,?,?,?,?)}");
		
		memberStateMent=conn.prepareCall("{CALL POLICY_ENROLLMENT_PKG.get_webser_member_details(?,?)}");
		
		callableStatement.setString(1, policyNumber);
		callableStatement.setString(2, policyCategory);
		callableStatement.setString(3, productName);
		callableStatement.setString(4, groupID);
		callableStatement.setString(5, subGroupID);
		callableStatement.registerOutParameter(6,OracleTypes.CURSOR);
		
		callableStatement.execute();
		int i=0;
		 employeeResultSet =(ResultSet) callableStatement.getObject(6);
		if(employeeResultSet!=null)
		{
		while (employeeResultSet.next()) 
		{
		   i++;
			Element employeeElement =createEmployee(policyElem, employeeResultSet,i); //calling create EmployeeEmlement function
			
			memberStateMent.setString(1,employeeResultSet.getString("policy_group_seq_id"));
			memberStateMent.registerOutParameter(2,OracleTypes.CURSOR);
			memberStateMent.execute();
			int j=0;
			
			ResultSet memberResult=(ResultSet) memberStateMent.getObject(2);
			
			if(memberResult!=null)
			{
				while(memberResult.next())
				{
					j++;
			 createMemberElem(employeeElement, memberResult,j);
				}
			}
			
			
		}
		}
		
		if(i==0){
			
			return getErrorXml("Members Are Not Available");
		}
	   }//if(policyRS!=null&&policyRS.next()){
		
	   else{
		   
		   return getErrorXml("Policy Details Are Not Valied");
	   }
		
		return takafulFormat.asXML();
		}
		else{return  getErrorXml("Please Provide proper Xml Format");}
	} catch (TTKException e) {
		
		e.printStackTrace();
		
		return getErrorXml(e.getMessage());
	} catch (SQLException e) {
		
		e.printStackTrace();
		return  getErrorXml(e.getMessage());
	}
	finally{
		closeConnResource(conn, callableStatement,employeeResultSet);
		closeConnResource(conn, memberStateMent,memberResult);
		closeStmtResource(policyCstm, policyRS);
	}
	
	
}

static Element createEmployee(Element policyElem,ResultSet employeeResultSet,int serialNO) throws SQLException 
{
	Element employeeElement=policyElem.addElement("Employee").addAttribute("SerialNO",""+serialNO);
	Element employeeDetailselem=employeeElement.addElement("EmployeeDetails");
	//employeeDetailselem.addAttribute("LocationCode",employeeResultSet.getString("LOCATION_CODE"));
	employeeDetailselem.addAttribute("WorkLocation",employeeResultSet.getString("WORK_LOC"));
	employeeDetailselem.addAttribute("EmpNO",employeeResultSet.getString("EMPLOYEE_NO"));
	//employeeDetailselem.addAttribute("pwd","");
	//employeeDetailselem.addAttribute("name",employeeResultSet.getString("FIRSTNAME"));
	employeeDetailselem.addAttribute("PrincipalName",employeeResultSet.getString("PRINCIPAL_NAME"));
	employeeDetailselem.addAttribute("Doj",employeeResultSet.getString("DATE_OF_JOINING"));
	employeeDetailselem.addAttribute("Commissionbased",employeeResultSet.getString("commisionbased"));
	employeeDetailselem.addAttribute("Designation",employeeResultSet.getString("DESIGNATION"));
	employeeDetailselem.addAttribute("SalaryBand",employeeResultSet.getString("SALARY_BAND"));
	employeeDetailselem.addAttribute("EnrollmentNO",employeeResultSet.getString("ENROLLMENTNO"));
	
	return employeeElement;
	
}
static String createMemberElem(Element employeeElement,ResultSet resElement,int serialNO) throws SQLException
{
	
	Element membElement=employeeElement.addElement("Member").addAttribute("SerialNO",""+serialNO);
	
	Element contactDetailElem=membElement.addElement("ContactDetail");
	contactDetailElem.addElement("Address1").setText(TTKCommon.checkNull(resElement.getString("ADDRESS_1")));
	contactDetailElem.addElement("City").setText(TTKCommon.checkNull(resElement.getString("CITY")));
	contactDetailElem.addElement("State").setText(TTKCommon.checkNull(resElement.getString("STATE_NAME")));
	contactDetailElem.addElement("Country").setText(TTKCommon.checkNull(resElement.getString("COUNTRY_NAME")));
	contactDetailElem.addElement("Pincode").setText(TTKCommon.checkNull(resElement.getString("PIN_CODE")));
	contactDetailElem.addElement("Emailid").setText(TTKCommon.checkNull(resElement.getString("EMAIL")));
	contactDetailElem.addElement("PhoneNO").setText(TTKCommon.checkNull(resElement.getString("PHONENO")));
	contactDetailElem.addElement("ResiLocation").setText(TTKCommon.checkNull(resElement.getString("RESIDENCIAL_LOC")));
	
	Element policyInfoElem=membElement.addElement("PolicyInfo");
	policyInfoElem.addElement("DateOfInception").setText(TTKCommon.checkNull(resElement.getString("DATE_OF_INCEPTION")));
	policyInfoElem.addElement("DateOfExit").setText(TTKCommon.checkNull(resElement.getString("DATE_OF_EXIT")));
	policyInfoElem.addElement("EnrollmentID").setText(TTKCommon.checkNull(resElement.getString("VIDAL_ID")));
	policyInfoElem.addElement("AnnualAggregateLimit").setText(TTKCommon.checkNull(resElement.getString("ANUAL_AGGREGATED_LIMIT")));
	 
	Element personalInfoElem=membElement.addElement("PersonalInfo");
	
	personalInfoElem.addElement("Relationship").setText(TTKCommon.checkNull(resElement.getString("Relationship")));
	personalInfoElem.addElement("Dob").setText(TTKCommon.checkNull(resElement.getString("DOB")));
	personalInfoElem.addElement("FamilyName").setText(TTKCommon.checkNull(resElement.getString("FAMILYNAME")));
	personalInfoElem.addElement("FirstName").setText(TTKCommon.checkNull(resElement.getString("FirstName")));
	personalInfoElem.addElement("SecondName").setText(TTKCommon.checkNull(resElement.getString("SecondName")));
	personalInfoElem.addElement("Gender").setText(TTKCommon.checkNull(resElement.getString("Gender")));
	personalInfoElem.addElement("GlobalnetID").setText(TTKCommon.checkNull(resElement.getString("GLOBAL_NET_MEMBER_ID")));
	personalInfoElem.addElement("MaritalStatus").setText(TTKCommon.checkNull(resElement.getString("MaritalStatus")));
	personalInfoElem.addElement("Nationality").setText(TTKCommon.checkNull(resElement.getString("Nationality")));
	personalInfoElem.addElement("PassportNO").setText(TTKCommon.checkNull(resElement.getString("PassportNO")));
	personalInfoElem.addElement("ProRataPremium").setText(TTKCommon.checkNull(resElement.getString("ProRataPremium")));
	personalInfoElem.addElement("Dom").setText(TTKCommon.checkNull(resElement.getString("DATE_OF_MARRIAGE")));
	personalInfoElem.addElement("Status").setText(TTKCommon.checkNull(resElement.getString("Status")));
	personalInfoElem.addElement("UidNO").setText(TTKCommon.checkNull(resElement.getString("UID_NUMBER")));
	personalInfoElem.addElement("Vip").setText(TTKCommon.checkNull(resElement.getString("vip")));
	personalInfoElem.addElement("EmirateID").setText(TTKCommon.checkNull(resElement.getString("EMIRATE_ID")));
	
	Element premiumDetailsElem=membElement.addElement("PremiumDetails");
	premiumDetailsElem.addElement("BrokerMargin").setText(TTKCommon.checkNull(resElement.getString("BROKER_MARGIN")));
	premiumDetailsElem.addElement("BrokerMarginUnit").setText(TTKCommon.checkNull(resElement.getString("BROKERMARGINUNIT")));
	premiumDetailsElem.addElement("DentalPremium").setText(TTKCommon.checkNull(resElement.getString("DENTAL_PREM")));
	premiumDetailsElem.addElement("InsurerMargin").setText(TTKCommon.checkNull(resElement.getString("INSURER_MARGIN")));
	premiumDetailsElem.addElement("InsurerMarginUnit").setText(TTKCommon.checkNull(resElement.getString("insurermarginunit")));
	premiumDetailsElem.addElement("IpNetPremium").setText(TTKCommon.checkNull(resElement.getString("IP_NET_PREM")));
	premiumDetailsElem.addElement("MaternityPremium").setText(TTKCommon.checkNull(resElement.getString("MEM_MATERNITY_AMOUNT")));
	premiumDetailsElem.addElement("MaxAge").setText(TTKCommon.checkNull(resElement.getString("MAX_AGE")));
	premiumDetailsElem.addElement("MedicalPremium").setText(TTKCommon.checkNull(resElement.getString("MEDICAL_PREM")));
	premiumDetailsElem.addElement("MinAge").setText(TTKCommon.checkNull(resElement.getString("MIN_AGE")));
	premiumDetailsElem.addElement("OpticalPremium").setText(TTKCommon.checkNull(resElement.getString("OPTICAL_PREM")));
	premiumDetailsElem.addElement("OtherMargin").setText(TTKCommon.checkNull(resElement.getString("OTHER_MARGIN")));
	premiumDetailsElem.addElement("OtherMarginUnit").setText(TTKCommon.checkNull(resElement.getString("OTHERMARGINUNIT")));
	premiumDetailsElem.addElement("ReInsBrkMargin").setText(TTKCommon.checkNull(resElement.getString("RE_INSURERBROKER_MARGIN")));
	premiumDetailsElem.addElement("ReInsBrkMarginUnit").setText(TTKCommon.checkNull(resElement.getString("RE_INSURER_MARGIN_UNIT")));
	premiumDetailsElem.addElement("TpaMargin").setText(TTKCommon.checkNull(resElement.getString("TPA_MARGIN")));
	premiumDetailsElem.addElement("TpaMarginUnit").setText(TTKCommon.checkNull(resElement.getString("TPA_MARGIN_UNIT")));
	premiumDetailsElem.addElement("WellnessPremium").setText(TTKCommon.checkNull(resElement.getString("WELLNESS_PREM")));
	
	membElement.addElement("SumInsured").addAttribute("amount",resElement.getString("SumInsured")).addAttribute("currency", resElement.getString("SumInsured_currency"));
	
	
	Element nomineeElem=membElement.addElement("Nominee");
	nomineeElem.addAttribute("nomineeAddress",resElement.getString("NOMINEE_ADDRESS"));
	nomineeElem.addAttribute("nomineeAge",resElement.getString("nomineeAge"));
	nomineeElem.addAttribute("nomineeContactno",resElement.getString("NOMINEE_CONTACT_NO"));
	nomineeElem.addAttribute("nomineeGender",resElement.getString("nominee_gender"));
	nomineeElem.addAttribute("nomineeName",resElement.getString("nominee_name"));
	return null;
	
}
private static String getErrorXml(String errorDesc){
	StringBuilder builder=new StringBuilder();
	
	String strVersion="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		String strRootS="<MemberDetails>";
		String strRootE="</MemberDetails>";
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
		builder.append("\n");
		builder.append(strTxtE);
		builder.append("\n");
		builder.append(strDescS);
		builder.append(errorDesc);
		builder.append(strDescE);
		builder.append("\n");
		builder.append(strRootE);
		
		return builder.toString();
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

/*
PRINCIPAL_NAME
FIRSTNAME
SECONDNAME
FAMILYNAME
RELATIONSHIP
ORDEE
PHONENO
DOB
GENDER
NATIONALITY
PASSPORTNO
MARITALSTATUS
EMAIL
EMIRATES
RESIDENCIAL_LOC
WORK_LOC
SALARY_BAND
EMIRATE_ID
UID_NUMBER
STATUS
PRODUCT_NETWORK_TYPE
EMPLOYEE_NO
VIDAL_ID
ANUAL_AGGREGATED_LIMIT
CURRENCY_TYPE
MEM_AGE
DATE_OF_JOINING
PHOTO_PRESENT_YN
ADDRESS_1
STATE_NAME
COUNTRY_NAME
PIN_CODE
ACCNO
REMARKS
DATE_OF_INCEPTION
DATE_OF_EXIT
LOCATION_CODE
EMP_PASSWORD
DESIGNATION
DATE_OF_MARRIAGE
NOMINEE_NAME
NOMINEE_AGE
NOMINEE_GENDER
NOMINEE_CONTACT_NO
NOMINEE_ADDRESS
vip(y/n)
GLOBAL_NET_MEMBER_ID
commisionbased(y/n)
MIN_AGE
MAX_AGE
MEDICAL_PREM
MEM_MATERNITY_AMOUNT
OPTICAL_PREM
DENTAL_PREM
WELLNESS_PREM
IP_NET_PREM
INSURER_MARGIN
insurer margin unit
BROKER_MARGIN
PROKERMARGIN UNIT
TPA_MARGIN
TPA_MARGIN_UNIT
RE_INSURERBROKER_MARGIN
RE_INSURER_MARGIN_UNIT
OTHER_MARGIN
OTHERMARGINUNIT
ACTUAL_PREMIUM
ENROLLMENTNO
PRODUCTNAME
POLICYNUMBER
POLICYCATEGORY
CAPITATION_YN
GROUPID
SUBGROUPID
INS_COMP_NAME

*/
