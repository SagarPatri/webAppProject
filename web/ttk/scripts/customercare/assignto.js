//java script for the assignto screen

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/AssignAction.do";
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
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveAssignAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

// function to close the assignto screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value="";
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/AssignAction.do";
	document.forms[1].submit();
}//end of Close()