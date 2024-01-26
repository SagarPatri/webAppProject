//java script for the add user group screen in user management flow
function onSubmit()
{
		document.forms[1].groupName.value=trim(document.forms[1].groupName.value);
		document.forms[1].groupDesc.value=trim(document.forms[1].groupDesc.value);
		document.forms[1].userRestriction.value=trim(document.forms[1].userRestriction.value);//KOC Cigna_insurance_resriction
		if(!JS_SecondSubmit)
		{
	    	document.forms[1].mode.value="doSave";
	    	document.forms[1].action="/AddUserGroupAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
    	}//end of if(!JS_SecondSubmit)
}//end of onSubmit()
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value="";
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of onClose()
function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
	    document.forms[1].mode.value="doReset";
	    document.forms[1].action = "/EditUserGroupAction.do";
	    document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()
function onDelete()
{
	var msg = confirm("Are you sure you want to delete the user group details  ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/UserGroupAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
}//end of onDelete()
