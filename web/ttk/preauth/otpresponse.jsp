<%@page import="java.util.Map,java.util.HashMap" %>
<%
HashMap<String,String> holdResult=(HashMap<String,String>)request.getAttribute("holdResult");
boolean status;     
String result;
String color;
String membSqid="";
String memberName="";
String memberAge="";
String emirateId="";
String payerId="";
String insSeqId="";
String insCompName="";
String policySeqId="";
String gender="";
String policyNumber="";
String corporateName="";
Long parentClaimSeqID;
String availableSumInsured="";
String sumInsured="";
String policyStartDate="";
String policyEndDate="";
String nationality="";
String productName="";
String payerAuthority="";
String vipyn="";
String clmMemIncDt="";
String policyCategory="";

out.println("<html><head><script>");
if(holdResult==null||holdResult.isEmpty()){
	 result="Some Problem Occured Please Contact Adminstrator";
	 color="red";
	 status=true;
}else{
	 membSqid=holdResult.get("MEMBER_SEQ_ID");
	if(membSqid==null||membSqid.length()<1){
		result="Your Given Member Id Incorrect";
		 color="red";
		 status=true;
	}else{
		String validationStatus=holdResult.get("validationStatus");
		if(validationStatus==null||validationStatus.length()<1||"N".equalsIgnoreCase(validationStatus)){
			result="OTP Incorrect";
			 color="red";
			 status=true;
		}else{
			result="OTP SUCCESS";
			 color="green";
			 status=false;
			 memberName=holdResult.get("MEM_NAME");
			 memberAge=holdResult.get("MEM_AGE");
			 emirateId=holdResult.get("EMIRATE_ID");
			 payerId=holdResult.get("PAYER_ID");
			 insSeqId=holdResult.get("INS_SEQ_ID");			
			 insCompName=holdResult.get("INS_COMP_NAME");
			 policySeqId=holdResult.get("POLICY_SEQ_ID");
			 gender=holdResult.get("GENDER");
			 policyNumber=holdResult.get("POLICY_NUMBER");
			 corporateName=holdResult.get("CORPORATE_NAME");
			 policyStartDate=holdResult.get("POLICY_START_DATE");
			 policyEndDate=holdResult.get("POLICY_END_DATE");
			 nationality=holdResult.get("NATIONALITY");
			 sumInsured=holdResult.get("SUM_INSURED");
			 availableSumInsured=holdResult.get("AVA_SUM_INSURED");
			 productName=holdResult.get("PRODUCT_NAME");
			 payerAuthority=holdResult.get("PAYER_AUTHORITY");
			 vipyn=holdResult.get("VIP_YN");
			 clmMemIncDt=holdResult.get("CLM_MEMB_INCP_DT");
			 policyCategory = holdResult.get("CLASSIFICATION");
			 
		}
	}
}	
	out.println("window.opener.document.getElementById('memberIdResult1').style.color='"+color+"';");
	out.println("window.opener.document.getElementById('memberIdResult2').innerHTML='"+result+"';");
	out.println("window.opener.document.getElementById('memberSeqID').value='"+membSqid+"';");
	//out.println("window.opener.document.getElementById('writePatientName').innerHTML='"+memberName+"';");
	out.println("window.opener.document.getElementById('patientName').value='"+memberName+"';");
	//out.println("window.opener.document.getElementById('writeMemberAge').innerHTML='"+memberAge+"';");
	out.println("window.opener.document.getElementById('memberAge').value='"+memberAge+"';");
	//out.println("window.opener.document.getElementById('writePatientGender').innerHTML='"+gender+"';");
	out.println("window.opener.document.getElementById('patientGender').value='"+gender+"';");
	//out.println("window.opener.document.getElementById('writeEmirateId').innerHTML='"+emirateId+"';");
	out.println("window.opener.document.getElementById('emirateId').value='"+emirateId+"';");
	//out.println("window.opener.document.getElementById('writePayerId').innerHTML='"+payerId+"';");
	out.println("window.opener.document.getElementById('payerId').value='"+payerId+"';");
	out.println("window.opener.document.getElementById('insSeqId').value='"+insSeqId+"';");
	//out.println("window.opener.document.getElementById('writePayerName').innerHTML='"+insCompName+"';");
	out.println("window.opener.document.getElementById('payerName').value='"+insCompName+"';");
	out.println("window.opener.document.getElementById('policySeqId').value='"+policySeqId+"';");
	out.println("window.opener.document.getElementById('policyNumber').value='"+policyNumber+"';");
	out.println("window.opener.document.getElementById('corporateName').value='"+corporateName+"';");
	out.println("window.opener.document.getElementById('policyStartDate').value='"+policyStartDate+"';");
	out.println("window.opener.document.getElementById('policyEndDate').value='"+policyEndDate+"';");
	out.println("window.opener.document.getElementById('nationality').value='"+nationality+"';");
	out.println("window.opener.document.getElementById('sumInsured').value='"+sumInsured+"';");
	out.println("window.opener.document.getElementById('availableSumInsured').value='"+availableSumInsured+"';");
	out.println("window.opener.document.getElementById('productName').value='"+productName+"';");
	out.println("window.opener.document.getElementById('payerAuthority').value='"+payerAuthority+"';");
	out.println("window.opener.document.getElementById('vipYorN').value='"+vipyn+"';");
	out.println("window.opener.document.getElementById('policyCategory').value='"+policyCategory+"';");
	out.println("window.opener.document.getElementById('preMemInceptionDt').value='"+clmMemIncDt+"';");
	out.println("window.self.close();");
	out.println("</script></head></html>");
%>