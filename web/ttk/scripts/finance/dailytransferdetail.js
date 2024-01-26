//javascript used in dailytransferdetail.jsp of Finance->TDS Configuration

//function to save
function onSave()
{
	if(!JS_SecondSubmit)
    {
		trimForm(document.forms[1]);
     	document.forms[1].mode.value="doSave";
     	document.forms[1].action="/SaveDailyTransferAction.do";
     	JS_SecondSubmit=true
     	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}//end of onSave()

//function to reset the values
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/DailyTransferAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{		
		document.forms[1].reset();
		if(""!=document.forms[1].dailyTransferDate.value)
		{
			document.forms[1].mode.value="doReset";
			document.forms[1].action="/DailyTransferAction.do";
			document.forms[1].submit();	
		}//end of if(""!=document.forms[1].dailyTransferDate.value)
	}//end of else
}//end of onReset()

//function to close the daily transfer detail screen.
function onClose()
{
	if(!TrackChanges())
		return false;
	document.forms[1].mode.value="doClose";	
	document.forms[1].action="/DailyTransferAction.do";
	document.forms[1].submit();
}//end of Close()