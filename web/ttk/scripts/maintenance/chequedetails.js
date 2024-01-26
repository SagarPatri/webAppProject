//java script for the finance.jsp

function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/SaveMaintainFinanceAction.do";
	document.forms[1].submit();
}//end of onSave()

//function to reset the values
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doDefault";
		document.forms[1].action="/FinanceAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{		
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MaintainFinanceAction.do";
	document.forms[1].submit();
}//end of onClose()