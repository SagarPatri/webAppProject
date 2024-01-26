
/* function onGeneratReport()
{
	document.forms[1].mode.value="doGenerateExcelReportIntX";
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=PRE&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL&startDate="+document.getElementById("sStartDate").value+"&endDate="+document.getElementById("sEndDate").value+
			"&payerCodes="+document.getElementById("payerCodes").value+"&providerCodes="+document.getElementById("providerCodes").value+"&corporateCodes="+document.getElementById("corporateCodes").value;
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
function onCLMGeneratReport()
{
	document.forms[1].mode.value="doGenerateExcelReportIntX";
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=CLM&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
} */

function onERPGeneratReport()
{
	if(compareDates("sStartDate","From Date","sEndDate","To Date","greater than")==false)
	{
		document.forms[1].sEndDate.value="";
	    return false;
	}
	
	if(document.forms[1].payerCodes.value=="")
		{
		alert("Please Select Authority");
		document.forms[1].payerCodes.focus();
		return false;
		}
 	else if(document.forms[1].eType.value=="")
	{
	alert("Please Select Enrollment Type");
	document.forms[1].eType.focus();
	return false;
	} 
	else if(document.forms[1].insCompanyCode.value=="")
	{
	alert("Please Select Insurance Company");
	document.forms[1].insCompanyCode.focus();
	return false;
	}
	else if(document.forms[1].sStartDate.value=="")
	{
	alert("Please Enter Start Date");
	document.forms[1].sStartDate.focus();
	return false;
	}
	else if(document.forms[1].sEndDate.value=="")
	{
	alert("Please Enter End Date");
	document.forms[1].sEndDate.focus();
	return false;
	}
	if(!isDate(document.forms[1].sStartDate,"From Date"))
   	{
   		document.forms[1].sStartDate.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].sEndDate,"To Date"))
   	{
   		document.forms[1].sEndDate.focus();
   		return false;
   	}
    if(!compareDates('sStartDate','From Date','sEndDate','To Date','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))

	document.forms[1].mode.value="doGenerateExcelReportIntX";
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=ERP&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL&sStartDate="+document.forms[1].sStartDate.value+"&sEndDate="+document.forms[1].sEndDate.value+"&insCompanyCode="+document.forms[1].insCompanyCode.value+"&sAgentCode="+document.getElementById("sAgentCode").value+"&eType="+document.forms[1].eType.value+"&sGroupPolicyNo="+document.forms[1].sGroupPolicyNo.value+"&payerCodes="+document.getElementById("payerCodes").value+"&corporateCodes="+document.getElementById("corporateCodes").value+"&sType="+document.getElementById("sType").value;
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}

function onPRPGeneratReport()
{
	if(compareDates("sStartDate","From Date","sEndDate","To Date","greater than")==false)
	{
		document.forms[1].sEndDate.value="";
	    return false;
	}
	if(document.forms[1].payerCodes.value=="")
	{
	alert("Please Select Authority");
	document.forms[1].payerCodes.focus();
	return false;
	}
	else if(document.forms[1].sStatus.value=="")
	{
	alert("Please Select Status");
	document.forms[1].sStatus.focus();
	return false;
	}
	else if(document.forms[1].insCompanyCode.value=="")
	{
	alert("Please Select Insurance Company");
	document.forms[1].insCompanyCode.focus();
	return false;
	}
	else if(document.forms[1].sStartDate.value=="")
	{
	alert("Please Enter Start Date");
	document.forms[1].sStartDate.focus();
	return false;
	}
	else if(document.forms[1].sEndDate.value=="")
	{
	alert("Please Enter End Date");
	document.forms[1].sEndDate.focus();
	return false;
	}
	if(!isDate(document.forms[1].sStartDate,"From Date"))
   	{
   		document.forms[1].sStartDate.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].sEndDate,"To Date"))
   	{
   		document.forms[1].sEndDate.focus();
   		return false;
   	}
    if(!compareDates('sStartDate','From Date','sEndDate','To Date','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))

	document.forms[1].mode.value="doGenerateExcelReportIntX";	
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=PRP&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL&startDate="+document.forms[1].sStartDate.value+"&endDate="+document.forms[1].sEndDate.value+"&insCompanyCode="+document.forms[1].insCompanyCode.value+"&sGroupPolicyNo="+document.forms[1].sGroupPolicyNo.value+"&payerCodes="+document.forms[1].payerCodes.value+"&sStatus="+document.forms[1].sStatus.value+"&providerCodes="+document.forms[1].providerCodes.value+"&corporateCodes="+document.forms[1].corporateCodes.value;
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}

// cur report 
function onCLRGeneratReport()
{
	if(document.forms[1].dtCriteria.value=="")
	{
	alert("Select at least one Date Criteria");
	document.forms[1].dtCriteria.focus();
	return false;
	}
	
	if(document.forms[1].sStartDate.value=="")
	{
	alert("Please Select From Date");
	document.forms[1].sStartDate.focus();
	return false;
	}
	
	var sEndDate = document.getElementById("sEndDate").value;
	var sStartDate = document.getElementById("sStartDate").value;
	var corporateCodes = document.getElementById("corporateCodes").value;
	var providerCodes = document.getElementById("providerCodes").value;
	var sStatus = document.getElementById("sStatus").value;
	var payerCodes = document.getElementById("payerCodes").value;
	var sGroupPolicyNo=  document.getElementById("sGroupPolicyNo").value;
	var insCompanyCode = document.getElementById("insCompanyCode").value;
	var sType=  document.getElementById("sType").value;
	var benefitTypes = document.getElementById("benefitTypes").value;				
	var claimSource = document.getElementById("claimSource").value;   				 
	var providerCategory = document.getElementById("providerCategory").value;   	 
	var batchNo = document.getElementById("batchNo").value;   						 
	var claimNo = document.getElementById("claimNo").value;   						 
	var remAdvFname = document.getElementById("remAdvFname").value;    			
	var dtCriteria = document.getElementById("dtCriteria").value;    				
	var payStatus = document.getElementById("payStatus").value;   					 
	var PayTransRefNo = document.getElementById("PayTransRefNo").value;    			
	
	document.forms[1].mode.value="doGenerateExcelReportIntX";
	
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=CLR&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL&insCompanyCode="+insCompanyCode+"&sGroupPolicyNo="+sGroupPolicyNo+"&payerCodes="+payerCodes+"&sType="+sType+"&sStatus="+sStatus+"&providerCodes="+providerCodes+"&corporateCodes="+corporateCodes+"&sStartDate="+sStartDate+"&sEndDate="+sEndDate+
	"&benefitTypes="+benefitTypes+"&claimSource="+claimSource+"&providerCategory="+providerCategory+"&batchNo="+batchNo+"&claimNo="+claimNo+"&remAdvFname="+remAdvFname+"&dtCriteria="+dtCriteria+"&payStatus="+payStatus+"&PayTransRefNo="+PayTransRefNo;
	var openPage = "/ReportsAction.do"+partmeter;
	
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	
}

/* function onPRMGeneratReport()
{
	document.forms[1].mode.value="doGenerateExcelReportIntX";
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=PRM&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
function onCSMGeneratReport()
{
	document.forms[1].mode.value="doGenerateExcelReportIntX";
	var partmeter = "?mode=doGenerateExcelReportIntX&parameter=CSM&reportID=PreAuthUtilization&fileName=PreAuthUtilization"+"&reportType=EXL";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
 */


