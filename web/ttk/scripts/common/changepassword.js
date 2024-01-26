//java script for changepassword.jsp

function onSave()
{
	if(!JS_SecondSubmit)
	{
	document.forms[0].mode.value = "doChangePassword";
	document.forms[0].action = "/SaveChangepasswordAction.do";
	JS_SecondSubmit=true;
    document.forms[0].submit();
	}
}//end of onSave()

function onClose()
{
	document.forms[0].mode.value = "doClose";
	document.forms[0].action = "/ChangepasswordAction.do";
	document.forms[0].submit();
}//end of onClose()