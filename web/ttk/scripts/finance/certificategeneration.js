//java script file for certificategeneration.jsp
function onGenerateBatch()
{
	document.forms[1].mode.value="doGenerateBatch";
	document.forms[1].action="/CertificateGenAction.do";
	document.forms[1].submit();
}//end of onGenerateBatch()

function onGenerateInd()
{
	document.forms[1].mode.value="doGenerateInd";
	document.forms[1].action="/CertificateGenAction.do";
	document.forms[1].submit();
}//end of onGenerateInd()