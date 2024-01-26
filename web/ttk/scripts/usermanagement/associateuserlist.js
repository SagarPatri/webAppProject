//java script for the list of associated users screen in user management flow

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AssociateUserAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/AssociateUserAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AssociateUserAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AssociateUserAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
	if(!JS_SecondSubmit)
 	{
		document.forms[1].sEmpNo.value=trim(document.forms[1].sEmpNo.value);
		document.forms[1].sName.value=trim(document.forms[1].sName.value);
	    JS_SecondSubmit=true;
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/AssociateUserSearchAction.do";
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to associate users
function onAssociate(empNo,name)
{
 if(!mChkboxValidation(document.forms[1]))
  {
	setDefault(empNo,name);
	document.forms[1].mode.value="doAssociate";
	document.forms[1].action="/AssociateUserAction.do";
	document.forms[1].submit();
  }
}//end of onAssociate()

//function to remove users from group
function onRemove(empNo,name)
{
if(!mChkboxValidation(document.forms[1]))
{
if(confirm("Are you sure you want to remove the record(s)?"))
 {
 	setDefault(empNo,name);
	document.forms[1].mode.value="doRemove";
	document.forms[1].action="/AssociateUserAction.do";
	document.forms[1].submit();
 }
else
	return false;
}
}//end of onRemove()

//function to close the screen.
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value="";
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/AssociateUserAction.do";
	document.forms[1].submit();
}//end of onClose()
// set the Changed properties to pre-selection.
function setDefault(empNo,name)
{
	document.forms[1].associateUsers.value='<bean:write name="frmAssociateUser" property="associateUsers" />';
	document.forms[1].officeInfo.value='<bean:write name="frmAssociateUser" property="officeInfo" />';
	document.forms[1].sEmpNo.value=empNo;
	document.forms[1].userRoles.value='<bean:write name="frmAssociateUser" property="userRoles" />';
	document.forms[1].sName.value=name;
} //end of setDefault()