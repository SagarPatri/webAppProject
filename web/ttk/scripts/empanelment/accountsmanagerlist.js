//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AssociateAcntManagerAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/AssociateAcntManagerAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AssociateAcntManagerAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AssociateAcntManagerAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
	if(!JS_SecondSubmit)
 	{
		trimForm(document.forms[1]);
	   	document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/AssociateAcntManagerAction.do";
	    JS_SecondSubmit=true;
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function close
	function onClose()
	{
		if(!TrackChanges()) return false;

	 	document.forms[1].mode.value="doClose";
	 	document.forms[1].action="/AssociateAcntManagerAction.do";
	 	document.forms[1].submit();
	}//end of function onClose()
	
function edit(rownum)
{
    document.forms[1].mode.value="doSelectAccountsManager";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AssociateAcntManagerAction.do";
    document.forms[1].submit();
}//end of edit(rownum)