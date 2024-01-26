//java script for the grouplist.jsp screen in the Group Registration of Empanelment flow.

//function to save
function onSave()
{
  if(!JS_SecondSubmit)
  {	
	document.forms[1].mode.value="doSearch";
    document.forms[1].action="/GroupListAction.do";
	JS_SecondSubmit=true
 	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSave()

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/GroupListAction.do";
	document.forms[1].submit();
}//end of OnShowhideClick(strRootIndex)

//function Search
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		document.forms[1].grpCaption.value='- '+document.forms[1].groupType[document.forms[1].groupType.selectedIndex].text;
		document.forms[1].groupId.value=trim(document.forms[1].groupId.value);
		document.forms[1].sGroupName.value=trim(document.forms[1].sGroupName.value);
		document.forms[1].mode.value="doSearch";
		document.forms[1].selectedroot.value="";
	    document.forms[1].action="/GroupListSearchAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/GroupListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/GroupListAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function on click of AddNewGroup
function OnAddNewGroup()
{
	document.forms[1].reset();
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="GroupDetails";
    document.forms[1].action="/GroupListAction.do";
	document.forms[1].submit();
}//end of OnAddNewGroup()

//function on click of RootAddIcon
function onRootAddIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doAdd";
	document.forms[1].child.value="GroupDetails";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootAddIcon(strRootIndex)

//function on click of RootEditIcon
function onRootEditIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doView";
	document.forms[1].child.value="GroupDetails";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootEditIcon(strRootIndex)

//function on click of NodeEditIcon
function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doView";
	document.forms[1].child.value="GroupDetails";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onRootEditIcon(strRootIndex)

//function on click of pageIndex
function pageIndex(strPageIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/GroupListAction.do";
	document.forms[1].submit();
}//end of pageIndex()

function onRootContactsIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].child.value="Contacts";
	document.forms[1].mode.value ="doContact";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootContactsIcon(strRootIndex)

function onNodeContactsIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doContact";
	document.forms[1].child.value="Contacts";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeContactsIcon(strRootIndex,strNodeIndex)

function onNodeContactsIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doContact";
	document.forms[1].child.value="Contacts";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeContactsIcon(strRootIndex,strNodeIndex)

//function of on delete
function onRootDeleteIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want delete the record?");
	if(msg)
	{
		document.forms[1].reset();
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/GroupListAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onRootDeleteIcon(strRootIndex)

//function of on delete
function onNodeDeleteIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want delete the record?");
	if(msg)
	{
		document.forms[1].reset();
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/GroupListAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)

function onRootSendConfirmation(strRootIndex)
{
	document.forms[1].mode.value="doView";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/NotificationDetailsAction.do";
	document.forms[1].submit();
}//end of onRootSendConfirmation(strRootIndex)

//function on click of NodeNotification details Icon
function onNodeSendConfirmation(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doView";
	document.forms[1].action="/NotificationDetailsAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeSendConfirmation(strRootIndex,strNodeIndex)


function onRootHospitalNewIcon(strRootIndex)
{
	document.forms[1].mode.value ="doHospitalContact";
	document.forms[1].child.value="Hospital";
	document.forms[1].action = "/GroupListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootHospitalNewIcon(strRootIndex)