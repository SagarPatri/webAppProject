//java script for the role list screen in user management flow

function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/RolesAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/RolesAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/RolesAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/RolesAction.do";
    document.forms[1].submit();
}//end of PressForward()
function onSearch()
{
    if(!JS_SecondSubmit)
 	{
	    document.forms[1].sRoleName.value=trim(document.forms[1].sRoleName.value);
	    JS_SecondSubmit=true;
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/RolesSearchAction.do";
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/RolesAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//function to add role
function onAddRole()
{
	document.forms[1].child.value="Role Details";
	document.forms[1].mode.value = "doAdd";
	document.forms[1].action = "/AddEditRolesAction.do";
	document.forms[1].submit();
}//end of onAddRole()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewRole";
    document.forms[1].child.value="Role Details";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AddEditRolesAction.do";
    document.forms[1].submit();
}//end of edit(rownum)