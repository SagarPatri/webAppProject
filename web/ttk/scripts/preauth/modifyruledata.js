//This is java script file for modifyruledata.jsp

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doUpdateRuleData";
		document.forms[1].action="/UpdateRuleDataAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onReset()
{
	document.forms[1].reset();
}//end of onReset()