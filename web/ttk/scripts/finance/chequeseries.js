//javascript for the Cheque Series screen of Finance module

function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doDefault";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/ChequeSeriesAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/ChequeSeriesAction.do";
        document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
    }//end of else if(typeof(ClientReset)!= 'undefined' && !ClientReset)
}//end of onReset()

function onSave()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/UpdateChequeSeriesAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function edit(rownum)
{
    document.forms[1].mode.value="doViewChequeSeries";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ChequeSeriesAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected Cheque Series ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/DelChequeSeriesAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()