//java script for the floataccountlist screen

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/GuaranteeDetailsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/GuaranteeDetailsAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/GuaranteeDetailsAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/GuaranteeDetailsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/GuaranteeDetailsAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewBankGuarantee";
    document.forms[1].child.value="Bank Guarantee Details";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BankGuaranteeDetailAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onAdd()
{
	document.forms[1].mode.value = "doAdd";
	document.forms[1].action = "/BankGuaranteeDetailAction.do";
	document.forms[1].submit();
}

//on Click of reset button
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/BankGuaranteeDetailAction.do";
        document.forms[1].submit();
    }
    else
    {
        document.forms[1].reset();
    }
}
//on Click of save button
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/SaveBankGuaranteeDetailAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

//on click od close button
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
    document.forms[1].action = "/GuaranteeDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

function changeBgType(obj)
{
	document.forms[1].mode.value = "doChangeBgType";
	document.forms[1].action = "/BankGuaranteeDetailAction.do";
	document.forms[1].submit();
}