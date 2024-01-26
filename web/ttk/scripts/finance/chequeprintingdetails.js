//java script for the Cheque printing details screen in the finance flow.

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
  		document.forms[1].mode.value="Reset";
		document.forms[1].action="/ChequePrintingAction.do";
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
		document.forms[1].mode.value="UpdateChequePrintingDetails";
		document.forms[1].action="/SaveChequePrintingAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the transaction details screen.
function Close()
{
	if(!TrackChanges()) return false;	
	document.forms[1].mode.value="Close";
	document.forms[1].child.value="";
	document.forms[1].action="/TransactionSearchAction.do";
	document.forms[1].submit();
}//end of Close()