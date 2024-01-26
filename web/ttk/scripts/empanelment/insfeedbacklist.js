//java script for the insurance feedback list screen in the empanelment of insurance flow.

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/InsuranceFeedbackAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].child.value="FeedbackDetail";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditInsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onAddEditFeedback()
{
    document.forms[1].mode.value = "doAdd";
    document.forms[1].child.value="FeedbackDetail";
    document.forms[1].action = "/EditInsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of onAddEditFeedback()

function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/InsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InsuranceFeedbackAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InsuranceFeedbackAction.do";
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
}//end of isValidated()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		if(isValidated())
		{
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/InsuranceFeedbackAction.do";
			JS_SecondSubmit=true
	    	document.forms[1].submit();
	    }// end of if(isValidated())
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "doViewCompanySummary";
	document.forms[1].child.value="";
	document.forms[1].action = "/InsuranceFeedbackAction.do";
    document.forms[1].submit();
}// end of onClose()