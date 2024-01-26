//java script for the Investigation screen in the preauth and support of Preauthorization flow.

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/HistoryListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/HistoryListAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HistoryListAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HistoryListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value = "doView";
	if(document.forms[0].leftlink.value == "Online Forms")
	{
		document.forms[1].child.value="Summary Details";
	}
	else
	{
		document.forms[1].child.value="Details";
	}
	document.forms[1].action="/HistoryDetailAction.do";
	document.forms[1].submit();
}// End of edit()

// function to search
function onSearch()
{
   trimForm(document.forms[1]);
    if(isValidated())
    {
	    if(!JS_SecondSubmit)
		 {
		    document.forms[1].mode.value = "doSearch";
		    document.forms[1].action = "/HistoryListAction.do";
			JS_SecondSubmit=true
		    document.forms[1].submit();
		 }//end of if(!JS_SecondSubmit)
    }
}//end of onSearch()
//on Click of reset button
function onReset()
{
    	//document.forms[1].reset();
    	document.forms[1].mode.value="doView";
    	document.forms[1].action = "/InvestigationAction.do";
    	document.forms[1].submit();
}//end of onReset()

//on Click of save button
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
    JS_SecondSubmit=true
    document.forms[1].mode.value="doView";
    document.forms[1].action = "/SaveSupportInvestigationAction.do";
    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/InvestigationListAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSummaryClose()
{
	document.forms[1].mode.value="doSummaryClose";
	document.forms[1].action="/HistoryListAction.do";
	document.forms[1].submit();
}//end of onCloseList()

function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].StartDate.value.length!=0)
		{
			if(isDate(document.forms[1].StartDate,"Start Date")==false)
				return false;
				document.forms[1].StartDate.focus();
		}// end of if(document.forms[1].StartDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].EndDate.value.length!=0)
		{
			if(isDate(document.forms[1].EndDate,"End Date")==false)
				return false;
				document.forms[1].EndDate.focus();
		}// end of if(document.forms[1].EndDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].StartDate.value.length!=0 && document.forms[1].EndDate.value.length!=0)
		{
			if(compareDates("StartDate","Start Date","EndDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].StartDate.value.length!=0 && document.forms[1].EndDate.value.length!=0)
		return true;
}//end of isValidated()

function onAccountInfo(enrollmentId)
{
	if(enrollmentId != "")
	{
		document.forms[1].leftlink.value="Account Info";
		document.forms[1].mode.value = "doSearchOnEnrollmentID";
		document.forms[1].action="/PolicyAccountInfoAction.do?sEnrollmentNumber="+enrollmentId;
		document.forms[1].submit();
	}//end of if(enrollmentId!="")
	else
	{
		alert("Enrollment Id not exists to view Account Info");
		return false;
	}//end of else
}// End of onAccountInfo()
