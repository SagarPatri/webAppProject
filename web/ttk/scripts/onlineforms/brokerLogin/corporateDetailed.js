function onDetailedView(argSummaryView) {
	document.forms[1].summaryView.value=argSummaryView;
	document.forms[1].mode.value="doDetailedView";
	document.forms[1].submit();
}
function onViewClaimDetails(argClmNO,argSummaryView) {
	document.forms[1].claimNO.value=argClmNO;
	document.forms[1].summaryView.value=argSummaryView;
	document.forms[1].mode.value="doViewClaimDetails";
	document.forms[1].submit();
}
function onViewPreauthDetails(argPatNO,argSummaryView) {
	document.forms[1].preAuthNO.value=argPatNO;	
	document.forms[1].summaryView.value=argSummaryView;
	document.forms[1].mode.value="doViewPreauthDetails";
	document.forms[1].submit();
}
function onTob() {
	document.forms[1].summaryView.value='TOB';
	document.forms[1].mode.value="doTob";
	document.forms[1].submit();
}
function onEndorsements() {
	document.forms[1].summaryView.value='END';
	document.forms[1].mode.value="doEndorsements";
	document.forms[1].submit();
}
function onBack(argBackID) {
	document.forms[1].backID.value=argBackID;
	document.forms[1].mode.value="goBack";
	document.forms[1].submit();	
}
function onFileDownload(argFileName) {
	document.forms[1].fileName.value=argFileName;
	document.forms[1].mode.value="doPolicyFileDownload";
	document.forms[1].submit();	
}
