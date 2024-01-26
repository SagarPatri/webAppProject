function onBack() {
	document.forms[1].mode.value="doDefault";
	document.forms[1].submit();	
}
function showReports(argReportID,argReportLabel) {
	document.forms[1].mode.value="doViewReports";
	document.forms[1].reportID.value=argReportID;
	document.forms[1].reportLabel.value=argReportLabel;	
	document.forms[1].submit();	
}
function onGenerateReports() {
	if("BroTATForClaims"==document.forms[1].reportID.value)
		document.forms[1].mode.value="doViewBroTATForClaims";
	else if("BroTATForPreAuth"==document.forms[1].reportID.value)
		document.forms[1].mode.value="doViewBroTATForPreAuth";
	else document.forms[1].mode.value="onGenerateReports";
	
	var selBox=document.forms[1].corporateName;
	document.forms[1].corporateNameText.value=selBox.options[selBox.selectedIndex].text;
	
	document.forms[1].action="/GenerateBrokerReportsAction.do";	
	document.forms[1].submit();
}
function getPolicyNumberList(){	
	document.forms[1].mode.value="getPolicyNumberList";
	document.forms[1].submit();	
}
function getPolicyPeriodList(){	
	document.forms[1].mode.value="getPolicyPeriodList";
	document.forms[1].submit();	
}
