//java script for the Partner search screen in the empanelment of Partner flow.

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].sEmpanelDate.value=trim(document.forms[1].sEmpanelDate.value);
	    document.forms[1].sPartnerName.value=trim(document.forms[1].sPartnerName.value);
	    if(isValidated())
		{
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/PartnerSearchAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
}//function to add hospital

function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditPartnerSearchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
//function to sort based on the link selected


//function to get the substatus change
function onStatusChanged()
{
	document.forms[1].mode.value="doStatusChange";
	document.forms[1].action="/PartnerAction.do";
	document.forms[1].submit();
}//end of onStatusChanged()
//function to call edit screen

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PartnerAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PartnerAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PartnerSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PartnerSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria

function isValidated()
{
	if(trim(document.forms[1].sEmpanelDate.value).length>0)
	{
		if(isDate(document.forms[1].sEmpanelDate,"Empanel. Date")==false)
		{
			document.forms[1].sEmpanelDate.focus();
			return false;
		}//end of if
	}//end of if(trim(document.forms[1].sEmpanelDate.value).length>0)
	return true;
}//end of isValidated()
function onAddPartner()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditPartnerSearchAction.do";
    document.forms[1].submit();
}//end of onAddPartner()
//copy the select Partner to webboard
function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	//document.forms[1].reset();
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PartnerAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()
