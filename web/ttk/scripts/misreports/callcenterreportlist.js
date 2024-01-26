//contains the javascript functions of callcenterreportlist.jsp of MIS Reports module

function onSelectCallPendingReport()
{  
	document.forms[1].mode.value="doVeiwCallPending";
	document.forms[1].action="/CallCenterPendingRptAction.do";
	document.forms[1].submit();
}//end of function onSelectCallPendingReport()