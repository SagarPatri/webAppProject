//contains the javascript functions of hospitalreportslist.jsp of MIS Reports module

function onSelectHM()
{  
	document.forms[1].mode.value="doHMdetails";
	document.forms[1].action="/HospitalReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectCR()
//deepthi
function onSelectCSR()
{  
	document.forms[1].mode.value="doCSRdetails";
	document.forms[1].action="/HospitalReportsListAction.do";
	document.forms[1].submit();
}
