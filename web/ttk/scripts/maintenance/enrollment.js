//java script for the enrollment.jsp

function onPolicySubType()
{
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/ChangePolicyTypeAction.do";
	document.forms[1].submit();
}//end of onPolicySubType()

function onDOBOChanges()
{
	 document.forms[1].mode.value="doDefault";
  	 document.forms[1].action="/ChangeDoBoAction.do";
	 document.forms[1].submit();
}//end of onDOBOChanges()

function onPolicyPeriod()
{
	 document.forms[1].mode.value="doDefault";
  	 document.forms[1].action="/ChangePolicyPeriod.do";
	 document.forms[1].submit();
}//end of  onPolicyPeriod()


//added function 1216B IBM Buffer CONFIG
function onEnrBufferConfig()
{
	 document.forms[1].mode.value="doDefault";
  	 document.forms[1].action="/EnrollBufferSearchAction.do";
	 document.forms[1].submit();
}//end of  onEnrBufferConfig

//added function 1216B IBM Buffer CONFIG
