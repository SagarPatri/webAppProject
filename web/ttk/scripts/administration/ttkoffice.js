
//java script for ttkoffice.jsp
function onAddIcon()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="AddOffice";
	document.forms[1].action="/AddTTKOfficeAction.do";
	document.forms[1].submit();	 
}//end of onAddIcon()
function onEditIcon()
{
	document.forms[1].mode.value="doViewOffice";
	document.forms[1].child.value="AddOffice";
	document.forms[1].action="/AddTTKOfficeAction.do";
	document.forms[1].submit();	 
}//end of onEditIcon()
function onRootAddIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doAdd";
	document.forms[1].child.value="AddOffice";
	document.forms[1].action = "/AddTTKOfficeAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();     
}//end of onRootAddIcon(strRootIndex)

//function on click of RootEditIcon
function onRootEditIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doViewOffice";
	document.forms[1].child.value="AddOffice";
	document.forms[1].action = "/AddTTKOfficeAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();     
}//end of onRootEditIcon(strRootIndex)

//function on click of NodeEditIcon
function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doViewOffice";
	document.forms[1].child.value="AddOffice";
	document.forms[1].action = "/AddTTKOfficeAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();     
}//end of onRootEditIcon(strRootIndex)
function onClauseIcon()
{
	document.forms[1].mode.value="doViewProperties";
	document.forms[1].child.value="Configure Properties";
	document.forms[1].action="/ConfProperties.do";
	document.forms[1].submit();	 
}//end of onClauseIcon()
function onRootClauseIcon(strRootIndex)
{
	document.forms[1].mode.value="doViewProperties";
	document.forms[1].child.value="Configure Properties";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].action="/ConfProperties.do";
	document.forms[1].submit();	 
}//end of onRootClauseIcon(strRootIndex)
function onNodeClauseIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doViewProperties";
	document.forms[1].child.value="Configure Properties";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].action="/ConfProperties.do";
	document.forms[1].submit();	 
}//end of onNodeClauseIcon(strRootIndex,strNodeIndex)
function OnShowhideClick(strRootIndex)
{   
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/TTKOfficeAction.do";
	document.forms[1].submit();	 
}//end of OnShowhideClick(strRootIndex)
//function on click of pageIndex
function pageIndex(strPageIndex)
{   
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/TTKOfficeAction.do";
	document.forms[1].submit();	 
}//end of pageIndex()
function onRootDeleteIcon(strRootIndex)
{   
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDeleteList";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/TTKOfficeAction.do";
		document.forms[1].submit();	
	}//end of if(msg)
}//end of onRootDeleteIcon(strRootIndex)
function onNodeDeleteIcon(strRootIndex,strNodeIndex)
{   
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDeleteList";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;	
	    document.forms[1].action="/TTKOfficeAction.do";
		document.forms[1].submit();	 
	}//end of if(msg)
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)
//function to display previous set of pages
function PressBackWard()
{ 
    document.forms[1].mode.value ="doBackward"; 
    document.forms[1].action = "/TTKOfficeAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{  
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TTKOfficeAction.do";
    document.forms[1].submit();     
}//end of PressForward()