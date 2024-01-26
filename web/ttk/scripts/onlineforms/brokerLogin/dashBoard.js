function corporateSearch(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/BrokerCorporateSummaryAction.do";
	document.forms[1].submit();		
}//end of corporateSearch()
function onClickLinks(argLinkMode){
	document.forms[1].mode.value="doClickLinks";
	document.forms[1].linkMode.value=argLinkMode;
	document.forms[1].action="/BrokerAction.do";
	document.forms[1].submit();		
}//end of corporateSearch()