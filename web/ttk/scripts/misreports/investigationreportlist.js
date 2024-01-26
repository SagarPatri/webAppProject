




function onInvestigation()
{  
	document.forms[1].mode.value="doInvestigationMonitor";
	document.forms[1].action="/InvestigatReportsListAction.do";
	document.forms[1].submit();
}//end of function onInvestigation()