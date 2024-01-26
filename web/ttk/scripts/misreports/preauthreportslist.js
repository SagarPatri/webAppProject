
// PreAuth Reports
function onSelectPreAuth()
{  
	document.forms[1].mode.value="doPreAuthDetails";
	document.forms[1].action="/PreAuthReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectPreAuth()


function onSelectPreAuthGrp()
{
	document.forms[1].mode.value="doPreAuthGrpDetails";
	document.forms[1].action="/PreAuthReportsListAction.do";
	document.forms[1].submit();
}//end of onSelectPreAuthGrp()

function onSelectPreAuthSms()
{
	document.forms[1].mode.value="doPreAuthSmsDetails";
	document.forms[1].action="/PreAuthReportsListAction.do";
	document.forms[1].submit();
}//end of onSelectPreAuthSms()

function onPreAuthUtilization()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=PRE";
	document.forms[1].submit();
}

function onPreAuthReport()
{
	document.forms[1].mode.value="doPreAuthUtilization";
	document.forms[1].action="/PreAuthReportsListAction.do?ReportType=PRP";
	document.forms[1].submit();
}


function onMemEligiblityCheck()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/CheckEligibilityReport.do";
	document.forms[1].submit();
}





