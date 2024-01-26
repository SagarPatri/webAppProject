//java script for the select authorization screen in the claims module of processing flow.

// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].child.value="";
	document.forms[1].mode.value="doSelectAuth";
	document.forms[1].action="/DataEntrySelectAuthorizationAction.do";
	document.forms[1].submit();
}// End of edit()

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].sStartDate.value.length!=0)
		{
			if(isDate(document.forms[1].sStartDate,"Start Date")==false)
				return false;
				document.forms[1].sStartDate.focus();
		}// end of if(document.forms[1].sStartDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].sEndDate.value.length!=0)
		{
			if(isDate(document.forms[1].sEndDate,"End Date")==false)
				return false;
				document.forms[1].sEndDate.focus();
		}// end of if(document.forms[1].sEndDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		{
			if(compareDates("sStartDate","Start Date","sEndDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		return true;
}//end of isValidated()

function onSearch()
{
  if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
    if(isValidated())
	{
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
	     JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(isValidated())
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onCloseList()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value="";
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/DataEntrySelectAuthorizationAction.do";
    document.forms[1].submit();
}//end of onClose()