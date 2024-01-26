//contains the javascript functions of codereportlist.jsp of MIS Reports module

function onSelectCodingReport()
{  
	document.forms[1].mode.value="doVeiw";
	document.forms[1].action="/CodingReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectIDCPS()