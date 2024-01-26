
// Claims Reports
function onSelectCR()
{  
	//alert("function onSelectCR()");
	
	document.forms[1].mode.value="doCrdetails";
	document.forms[1].action="/ClaimsReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectCR()

function onLiabilityClaims()
{  
	document.forms[1].mode.value="doLiabilityClaims";
	document.forms[1].action="/ClaimsReportsListAction.do";
	document.forms[1].submit();
}//end of function onLiabilityClaims()

function onCitibankClaimsRpt()
{
	document.forms[1].mode.value="doCitibankClaimsRpt";
	document.forms[1].action="/ClaimsReportsListAction.do";
	document.forms[1].submit();
}//end of onCitibankClaimsRpt()

function onClmAuthUtilization()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=CLM";
	document.forms[1].submit();
}
function onClmReport()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=CLR";
	document.forms[1].submit();
}
