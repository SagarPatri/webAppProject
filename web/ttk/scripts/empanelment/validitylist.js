//java script for the validity list screen in the empanelment of hospital flow.

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="ValidityHistory";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ValidityAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="ValidityHistory";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ValidityAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="Backward";
    document.forms[1].action = "/ValidityAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="Forward";
    document.forms[1].action = "/ValidityAction.do";
    document.forms[1].submit();
}//end of PressForward()
function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].startdate.value.length!=0)
		{
			if(isDate(document.forms[1].startdate,"Start Date")==false)
				return false;
		}//end of if(document.forms[1].startdate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].enddate.value.length!=0)
		{
			if(isDate(document.forms[1].enddate,"End Date")==false)
				return false;
		}//end of if(document.forms[1].enddate.value.length!=0)
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
	    	document.forms[1].mode.value = "ValidityHistory";
	    	document.forms[1].action = "/ValidityAction.do";
			JS_SecondSubmit=true
		    document.forms[1].submit();
	    }// end of if(isValidated())
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "CompanySummary";
	document.forms[1].action = "/ValidityAction.do";
    document.forms[1].submit();
}//end of onClose()