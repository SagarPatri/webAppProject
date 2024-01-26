//functions for billdetails screen of claims flow.
function onSave()
{
	if(!JS_SecondSubmit)
    {
	  document.forms[1].mode.value="doSave";
 	  document.forms[1].action="/SaveBillDetailsAction.do";
 	  JS_SecondSubmit=true
 	  document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
 	document.forms[1].action="/ListofBillsAction.do";
 	document.forms[1].submit();
}//end of onClose()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/AddEditBillDetailsAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()