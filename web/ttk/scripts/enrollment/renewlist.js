//java script for renewlist.jsp which is called in enrollment flow
//function onClose()
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
	document.forms[1].action = "/RenewMemberAction.do";
	document.forms[1].submit();
}//end of onClose()
//function onRenew()
function onRenew()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to Renew the selected record(s) ?");
		if(msg)
		{
			document.forms[1].mode.value="doRenew";
			document.forms[1].action = "/RenewMemberAction.do";
			document.forms[1].submit();
		}
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end ofonRenew()
//function onReset()
function onReset()
{
	document.forms[1].reset();
}//end of onReset()

