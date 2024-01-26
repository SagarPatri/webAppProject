//java script for the banktreelist.jsp screen in the Bank Search of Finance flow.

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/BankTreeListAction.do";
	document.forms[1].submit();
}//end of OnShowhideClick(strRootIndex)

//function Search
function onSearch()
{
  if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value="doSearch";
	document.forms[1].selectedroot.value="";
    document.forms[1].action="/BankTreeSearchAction.do";
    JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BankTreeListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BankTreeListAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function on click of AddNewGroup
function OnAddNewGroup()
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value="doAdd";
    document.forms[1].action="/BankTreeListAction.do";
	document.forms[1].submit();
}//end of OnAddNewGroup()

//function on click of RootAddIcon
function onRootAddIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value ="doAdd";
	document.forms[1].action = "/BankTreeListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootAddIcon(strRootIndex)

//function on click of RootEditIcon
function onRootEditIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value ="doViewBank";
	document.forms[1].action = "/BankTreeListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootEditIcon(strRootIndex)

//function on click of NodeEditIcon
function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value ="doViewBank";
	document.forms[1].action = "/BankTreeListAction.do";
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
    document.forms[1].action="/BankTreeListAction.do";
	document.forms[1].submit();
}//end of pageIndex()

function onRootContactsIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value ="doContact";
	document.forms[1].action = "/BankTreeListAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootContactsIcon(strRootIndex)

function onNodeContactsIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].tab.value="Bank Details";
	document.forms[1].mode.value ="doContact";
	document.forms[1].action = "/BankTreeListAction.do";
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
	    document.forms[1].action="/BankTreeListAction.do";
		document.forms[1].submit();
	}
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
	    document.forms[1].action="/BankTreeListAction.do";
		document.forms[1].submit();
	}
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)

