//java script for the add tds sub category screen in the administration  flow.

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/AddTDSConfigurationAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) 
		return false;
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/TDSConfigurationAction.do";
    document.forms[1].submit();
}//end of onClose()

function onSave()
{
    document.forms[1].mode.value="doSave";
    document.forms[1].child.value="";
    document.forms[1].action = "/SaveTDSConfigurationAction.do";
    document.forms[1].submit();
}//end of onClose()
