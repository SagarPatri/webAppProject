//java script for the policies list screen in the online forms module of enrollment flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doSelectOnlinePolicy";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Members";
    document.forms[1].action = "/OnlinePolicyDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlinePoliciesAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function onSummaryIcon(rownum)
{
	document.forms[1].mode.value="doViewPolicy";
	document.forms[1].historymode.value="Summary";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action = "/OnlinePoliciesAction.do";
	document.forms[1].submit();
}
//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlinePoliciesAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlinePoliciesAction.do";
    document.forms[1].submit();
}//end of PressForward()

//kocb
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/OnlinePoliciesAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }
}

function SelectCorporate()
{
	//document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/OnlinePoliciesAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()

