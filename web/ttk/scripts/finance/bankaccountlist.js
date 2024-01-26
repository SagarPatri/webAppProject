//java script for the bank account list screen in the finance module of bank accounts flow.

// JavaScript edit() function
function edit(rownum)
{
	if(document.forms[0].sublink.value=="Bank Account")
	{
		document.forms[1].tab.value="General";
	}
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value="doViewBankAccount";
	document.forms[1].action="/BankAccountSearchAction.do";
	document.forms[1].submit();
}
// End of edit()

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BankAccountSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BankAccountSearchAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BankAccountSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BankAccountSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/BankAccountSearchAction.do";
	    document.forms[1].submit();
    }//end of if
}//end of copyToWebBoard()

function onAddBankAccount()
{
    document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].tab.value="General";
    document.forms[1].action = "/BankAcctGeneralAction.do";
    document.forms[1].submit();
}//end of ()

function onSearch()
{
  if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/BankAccountSearchAction.do";
    JS_SecondSubmit=true
    document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/BankAccountSearchAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onCloseList()
{
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doViewBankAccount";
    document.forms[1].action = "/BankAccountSearchAction.do";
    document.forms[1].submit();
}//end of onClose()