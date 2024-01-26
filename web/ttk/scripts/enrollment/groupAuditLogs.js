
function onSearch()
{
	document.forms[1].mode.value = "doSearchAuditLogs";
	document.forms[1].action = "/CorporateMemberAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}//end of onSearch()

function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackwardAuditLogs";
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForwardAuditLogs";
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchAuditLogs";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchAuditLogs";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)







function onCancel()
{
	document.forms[1].mode.value = "doClose";
	 document.forms[1].action="/CorporateMemberAction.do";
		document.forms[1].submit();
	
}














