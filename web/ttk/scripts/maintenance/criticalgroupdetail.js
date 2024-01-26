function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EditCriticalGroupsAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}// end of else
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/CriticalGroupsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSave()
{
	if(!JS_SecondSubmit)
    {
		document.forms[1].groupName.value=trim(document.forms[1].groupName.value);
		document.forms[1].groupDesc.value=trim(document.forms[1].groupDesc.value);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateCriticalGroupsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()