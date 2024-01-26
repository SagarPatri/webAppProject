//java script for the changedobo.jsp

function changeOffice()
{
	document.forms[1].mode.value="doChangeOffice";
	document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/ChangeDoBoAction.do";
	document.forms[1].submit();
}//end of changeOffice()

function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/SaveChangeDoBoAction.do";
	document.forms[1].submit();
}//end of onSave()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MaintainEnrollAction.do";
	document.forms[1].submit();
}//end of onClose()