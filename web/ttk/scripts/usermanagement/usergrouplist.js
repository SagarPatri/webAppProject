//java script for the user group list screen in user management flow
//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewUserGroup";
    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
    document.forms[1].rownum.value=rownum;
    document.forms[1].child.value="EditUserGroup";
    document.forms[1].action = "/EditUserGroupAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria
function onSearch()
{
    if(!JS_SecondSubmit)
 	{
	    JS_SecondSubmit=true;
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].susergroup.value = trim(document.forms[1].susergroup.value);
	    document.forms[1].action = "/UserGroupSearchAction.do";
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
//function to add hospital
function onAddUserGroup()
{
	document.forms[1].rownum.value="";
	document.forms[1].child.value="EditUserGroup";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/EditUserGroupAction.do";
    document.forms[1].submit();
}//end of onAddUserGroup()
//function to delete User Group
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected User Groups ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDeleteList";
	    document.forms[1].action = "/UserGroupAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//function to call edit screen
function onContactsIcon(rownum)
{
    document.forms[1].mode.value="doAssociateUserList";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/UserGroupAction.do";
    document.forms[1].submit();
}//end of onContactsIcon(rownum)