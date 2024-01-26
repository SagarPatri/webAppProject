

function onWebLogin()
{
	document.forms[1].mode.value = 'doWebLogin';
	document.forms[1].action="/WebConfigAction.do";
	document.forms[1].submit();
}//end of onWebLogin()

function onHomePage()
{
	 document.forms[1].mode.value="doDefault";
  	 document.forms[1].action="/LinkDetailsAction.do";
	 document.forms[1].submit();
}//end of onHomePage()

function onMemberDetails()
{
	 document.forms[1].mode.value="doDefault";
  	 document.forms[1].action="/WebConfigMemDetailsAction.do";
	 document.forms[1].submit();
}//end of  onMemberDetails()

function onInsCompany()
{
	 document.forms[1].mode.value="doViewInsCompInfo";
  	 document.forms[1].action="/InsCompanyInfoAction.do";
	 document.forms[1].submit();
}//end of onInsCompany()

function onRelInformation()
{
	 document.forms[1].mode.value="doRelationshipList";
  	 document.forms[1].action="/RelationshipInfoAction.do";
	 document.forms[1].submit();
}//end of onRelInformation()