//java script for the quality details screen

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/FloatTransactionDetailsAction.do";
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
		document.forms[1].action="/SaveTransactionsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the quality details screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/FloatTransactionAction.do";
	document.forms[1].submit();
}//end of Close()

function onDebitIcon()
{
	document.forms[1].mode.value="doViewDebitNotes";
	document.forms[1].action="/DebitNoteDepositAction.do";
	document.forms[1].submit();
}//end of onDebitIcon()