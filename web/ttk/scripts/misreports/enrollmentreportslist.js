//contains the javascript functions of enrollmentreportslist.jsp of MIS Reports module

function onSelectEnrolMonitor()
{  
	//alert("function doEnrolMonitordetails()");
	document.forms[1].mode.value="doEnrolMonitordetails";
	document.forms[1].action="/EnrolReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectEnrolMonitor()

function onAccentureEnrolReport()
{
	document.forms[1].mode.value="doAccentureEnrolReport";
	document.forms[1].action="/EnrolReportsListAction.do";
	document.forms[1].submit();
}//end of onAccentureEnrolReport

function onPremiumReport()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=PRM";
	document.forms[1].submit();
}
function onClaimSummary()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=CSM";
	document.forms[1].submit();
}
function onEnReport()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=ERP";
	document.forms[1].submit();
}
