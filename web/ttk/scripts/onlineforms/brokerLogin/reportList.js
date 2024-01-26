function onBack() {
	document.forms[1].mode.value="goBack";
	document.forms[1].submit();	
}
function showReports(argReportID,argReportLabel) {
	document.forms[1].mode.value="doViewReports";
	document.forms[1].reportID.value=argReportID;
	document.forms[1].reportLabel.value=argReportLabel;	
	document.forms[1].submit();	
}