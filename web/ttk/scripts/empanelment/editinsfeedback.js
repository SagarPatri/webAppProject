//This file contains the javascript functions for editinsfeedback.jsp

function onSubmit()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateInsFeedbackAction.do";
		JS_SecondSubmit=true
	 	document.forms[1].submit();
	 }
}//end of onSubmit()
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/InsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of onClose()
function onReset()
{
	document.forms[1].mode.value = "doReset";
    document.forms[1].action = "/EditInsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of onReset()