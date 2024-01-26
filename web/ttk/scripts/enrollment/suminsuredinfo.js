//java script for suminsuredinfo.jsp
function onSIClose()
{
	document.forms[1].mode.value="doSIClose";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}//end of onSIClose()