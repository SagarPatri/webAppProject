function onSummaryView(argSummaryView) {
	document.forms[1].summaryView.value=argSummaryView;
	document.forms[1].mode.value="doSummaryView";
	document.forms[1].submit();
}
function onBack(argBackID) {
	document.forms[1].backID.value=argBackID;
	document.forms[1].mode.value="goBack";
	document.forms[1].submit();	
}