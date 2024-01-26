//java script for the changepolicyprd.jsp

function changeOffice()
{
	document.forms[1].mode.value="doChangeOffice";
	document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/ChangePolicyPeriod.do";
	document.forms[1].submit();
}//end of changeOffice()

function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/SaveChangePolicyPeriod.do";
	document.forms[1].submit();
}//end of changeOffice()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MaintainEnrollAction.do";
	document.forms[1].submit();
}//end of onClose()