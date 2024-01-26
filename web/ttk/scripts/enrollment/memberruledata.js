//This is javascript file for MemberRuleData.jsp of Enrollment module

function onSave()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/MemberRuleDataAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of function onSave()

function onClose(errorstate)
{
	if(errorstate=='')
	{
		if(!TrackChanges()) return false;
	}
	document.forms[1].mode.value="doDefault";
	document.forms[1].child.value="";
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action = "/MemberRuleDataAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/MemberRuleDataAction.do"; 

	
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