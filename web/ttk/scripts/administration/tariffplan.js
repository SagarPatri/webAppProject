//java script for the plan list screen in the administration of tariff plan flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewTariffplan";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditTariffPlanAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TariffPlanAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TariffPlanAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{ 
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TariffPlanAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TariffPlanAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function onSearch()
{
    document.forms[1].sName.value=trim(document.forms[1].sName.value);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/TariffPlanSearchAction.do";
    document.forms[1].submit();
}//end of onSearch()

function onAddTariffPlan()
{
    document.forms[1].rownum.value="";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/EditTariffPlanAction.do";
    document.forms[1].submit();
}//end of onAddEditUser()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    { 
	var msg = confirm("Are you sure you want to delete the selected Plan(s)?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/DeleteTariffPlanAction.do";
		    document.forms[1].submit();
		}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onHistoryIcon(rownum)
{
	document.forms[1].mode.value="doTariffRevisePlan";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/TariffPlanAction.do";
	document.forms[1].submit();
}//end of onReviseIcon(rownum)