//java script for the select user in the finance flow.

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/SelectUserAction.do";
		document.forms[1].submit();	
 	}
 	else
 	{
 		document.forms[1].reset(); 			
 	}
}//end of Reset()

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveSelectUserAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the select user screen.
function Close()
{
	if(!TrackChanges()) return false;	
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/SelectUserAction.do";
	document.forms[1].submit();
}//end of Close()

function selectUser()
{
	document.forms[1].mode.value="doSelectUserList";
	document.forms[1].child.value="Users List";
	document.forms[1].action="/SelectUserAction.do";
	document.forms[1].submit();	
}//end of selectUser()