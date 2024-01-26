//java script for the search batch screen in the inward entry of batch flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewBatch";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Batch Details";
    document.forms[1].action = "/EditBatchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BatchlistAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BatchlistAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BatchlistAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BatchlistAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function onAdd()
{
    document.forms[1].mode.value = "doAdd";
    document.forms[1].tab.value="Batch Details";
    document.forms[1].action = "/EditBatchAction.do";
    document.forms[1].submit();
}//end of onAdd()

function onSearch()
{
    if(!JS_SecondSubmit)
 	{
	    trimForm(document.forms[1]);
	    if(isValidated())
	    {
	    	JS_SecondSubmit=true;
	    	document.forms[1].mode.value = "doSearch";
		    document.forms[1].action = "/BatchlistAction.do";
		    document.forms[1].submit();
	    }
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()
function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"Start Date")==false)
				return false;
				document.forms[1].startDate.focus();
		}// end of if(document.forms[1].startDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].endDate.value.length!=0)
		{
			if(isDate(document.forms[1].endDate,"End Date")==false)
				return false;
				document.forms[1].endDate.focus();
		}// end of if(document.forms[1].endDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		{
			if(compareDates("startDate","Start Date","endDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		return true;
}//end of isValidated()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected Records");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/BatchlistAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()