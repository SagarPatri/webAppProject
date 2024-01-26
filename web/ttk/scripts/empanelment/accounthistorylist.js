//java script for the account history list screen in the empanelment of hospital flow.
function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].child.value="EditHistory";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditAccountsHistoryAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of PressForward()

function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].startdate.value.length!=0)
		{
			if(isDate(document.forms[1].startdate,"Start Date")==false)
				return false;
		}// end of if(document.forms[1].startdate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].enddate.value.length!=0)
		{
			if(isDate(document.forms[1].enddate,"End Date")==false)
				return false;
		}// end of if(document.forms[1].enddate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].startdate.value.length!=0 && document.forms[1].enddate.value.length!=0)
		{
			if(compareDates("startdate","Start Date","enddate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].startdate.value.length!=0 && document.forms[1].enddate.value.length!=0)
		return true;
}// end of isValidated()

function onSearch()
{
if(!JS_SecondSubmit)
 {
    if(isValidated())
	{
    	document.forms[1].mode.value = "doSearch";
    	document.forms[1].action = "/AccountsHistoryAction.do";
		JS_SecondSubmit=true
    	document.forms[1].submit();
    }// end of if(isValidated())
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "doView";
	document.forms[1].child.value="";
	document.forms[1].action = "/AccountsAction.do";
    document.forms[1].submit();
}// end of onClose()