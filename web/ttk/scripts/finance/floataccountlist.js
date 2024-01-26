//java script for the floataccountlist screen

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/FloatSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/FloatSearchAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/FloatSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/FloatSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
   if(!JS_SecondSubmit)
 {
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/FloatSearchAction.do";
    JS_SecondSubmit=true
    document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// function to copyToWebBoard
function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {


	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/FloatSearchAction.do";
	    document.forms[1].submit();
    }//end of if
}//end of copyToWebBoard()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/FloatSearchAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewFloatAccount";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/FloatSearchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onAdd()
{
	document.forms[1].mode.value = "doAdd";
	document.forms[1].tab.value="General";
	document.forms[1].action = "/FloatAccAction.do";
	document.forms[1].submit();
}