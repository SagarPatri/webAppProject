function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EditInvestigationGroupsAction.do";
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
	document.forms[1].action="/InvestigationGroupAction.do";
	document.forms[1].submit();
}//end of onClose() koc11 koc 11

function onSave()
{
	if(!JS_SecondSubmit)
    {
		document.forms[1].groupName.value=trim(document.forms[1].groupName.value);
				if(document.forms[1].activeYN.checked)
			    {
			    	document.forms[1].invActiveYN.value='1';			    	
			    }
			    else
			    {
			    	document.forms[1].invActiveYN.value='0';
			    }//end of if(document.forms[1].activeYN.checked)
		//document.forms[1].groupDesc.value=trim(document.forms[1].groupDesc.value);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateInvestigationGroupsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()