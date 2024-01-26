//function to sort based on the link selected
function edit(rownum)
{
    document.forms[1].mode.value="doViewDaycareGroups";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditDayCareGroupsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DayCareGroupsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/DayCareGroupsAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{ 
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/DayCareGroupsAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/DayCareGroupsAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function onAdd()
{
	document.forms[1].mode.value ="doAdd";
    document.forms[1].action = "/EditDayCareGroupsAction.do";
    document.forms[1].submit(); 
}//end of onAdd()

function onSearch()
{
    document.forms[1].sGroupName.value=trim(document.forms[1].sGroupName.value);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/DayCareGroupsAction.do";
    document.forms[1].submit();
}//end of onSearch()

//function to delete Groups
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    { 
	var msg = confirm("Are you sure you want to delete the selected Group(s)?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/DayCareGroupsAction.do";
		    document.forms[1].submit();
		}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onHistoryIcon(rownum)
{
	document.forms[1].mode.value="doViewProcedureList";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/ProcedureListAction.do";
	document.forms[1].submit();
}//end of onHistoryIcon(rownum)