function getProviderList()
{

	document.forms[1].mode.value="changeAuthority";
	document.forms[1].action="/CheckEligibilityReport.do";
	JS_SecondSubmit=true
	document.forms[1].submit();


}//end of PressForward()



function onGenerateReport()
{
	   
	   var authority=document.getElementById("payerCodes").value;
	   var sHospitalName=document.getElementById("sHospitalName").value;
	   var insCompanyName=document.getElementById("insCompanyName").value;
	   var sEnrolmentId=document.getElementById("sEnrolmentId").value;
	   var startDate = document.forms[1].chkFromDate.value;
	   var endDate = document.forms[1].chkToDate.value;
	   var reportlinkname= "Member Eligibility checked from provider login";
	
	   
	 /*  if(document.getElementById("payerCodes").value=="")
		{
			alert("Please Select Authority");
			document.forms[1].payerCodes.focus();
			return false;
		}*/
	
	    if(document.getElementById("chkFromDate").value==""){
		   alert("Please Select From Date");
			document.forms[1].chkFromDate.focus();
			return false;
	   } 
	   else if(document.getElementById("chkToDate").value==""){
		   alert("Please Select To Date");
			document.forms[1].chkToDate.focus();
			return false;
	   } 
	   
	   else{
		   
	var fileName = "reports/misreports/MemberCheckEligibilityReport.jrxml";
	var partmeter = "?mode=doGenerateReport&fileName="+fileName+"&reportType=EXCEL&authority="+authority+"&sHospitalName="+sHospitalName+"&insCompanyName="+insCompanyName+"&sEnrolmentId="+sEnrolmentId+"&startDate="+startDate+"&endDate="+endDate+"&reportlinkname="+reportlinkname;
	
	    var openPage = "/CheckEligibilityReport.do"+partmeter;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
	
	
	   }
	
}






function onClose()
{
	
	document.forms[1].mode.value="closeEligibility";
	document.forms[1].action="/CheckEligibilityReport.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
	
}


