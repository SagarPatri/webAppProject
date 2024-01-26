//this function is called on click of onAssociateCertificates
function onAssociateCertificates()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/AssociateCertificatesList.do?Entry=Y";
	document.forms[1].submit();
}//end of onAssociateCertificates()

//this function is called on click of close button
function onClose()
{
	document.forms[1].mode.value="doAssoClose";
	document.forms[1].action="/AssociateCertificatesList.do";
	document.forms[1].submit();
}//end of onClose()