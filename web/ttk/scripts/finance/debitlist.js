function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DebitNoteAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/DebitNoteAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/DebitNoteAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/DebitNoteAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DebitNoteAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function edit(rownum)
{
	if(document.forms[0].tab.value=='General')	
    {
    	document.forms[1].mode.value="doViewDebitNote";
    	document.forms[1].rownum.value=rownum;
    	document.forms[1].action = "/SaveDebitNoteAction.do";
    	document.forms[1].submit();
    }//end of if(document.forms[0].tab.value=='General')
    else
    {
    	document.forms[1].mode.value="doViewDebitNotePayment";
    	document.forms[1].rownum.value=rownum;
    	document.forms[1].action = "/DebitNoteAction.do";
    	document.forms[1].submit();
    }//end of else	
}//end of edit(rownum)

function onAdd()
{
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/SaveDebitNoteAction.do";
    document.forms[1].submit();
}//end of onAddEditCircular()

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
	if(isValidated())
	{
		if(!JS_SecondSubmit)
		 {
		 	trimForm(document.forms[1]);
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/DebitNoteAction.do";
			JS_SecondSubmit=true
	    	document.forms[1].submit();
		 }//end of if(!JS_SecondSubmit)
    }// end of if(isValidated())
}//end of onSearch()

function onClose()
{
	if(!TrackChanges()) return false;
	if(document.forms[0].tab.value=='General')
	{
    	document.forms[1].mode.value="doViewFloatAccount";
    	document.forms[1].action = "/FloatSearchAction.do";
    	document.forms[1].submit();
    }//	end of if(document.forms[0].tab.value=='General')
    else
    {
    document.forms[1].mode.value="doCloseDebitPayment";
    	document.forms[1].action = "/DebitNoteAction.do";
    	document.forms[1].submit();
    }//end of else
}//end of onClose()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DebitNoteAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()