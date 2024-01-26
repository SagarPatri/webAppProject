//This is the javascript file  for enrollmentruledata.jsp

function onSave()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/UpdateEnrollmentRuleDataAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()
function onClose(errorstate)
{
	if(errorstate=='')
	{
		if(!TrackChanges()) return false;
	}
	document.forms[1].mode.value="doClose";
	document.forms[1].action = "/EditMemberDetailAction.do";
    document.forms[1].submit();
}//end of onClose()

//This function is used to show/hide the conditions based on the
//status of the coverage
function showHideCondition(element)
{
	var id="coverage"+element.name
	if(document.getElementById(id))
	{
		if(element.value == 3)
		{
		  document.getElementById(id).style.display="";
		}
		else
		{
			document.getElementById(id).style.display="none";
		}
	}
}//end of showHideCondition(element)