//javascript used in auditlist.jsp of Preauth flow

//function to sort based on the link selected
	function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/AuditSearchAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

	//function to display the selected page
	function pageIndex(pagenumber)
	{
	    document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/AuditSearchAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doBackward";
	    document.forms[1].action = "/AuditSearchAction.do";
	    document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/AuditSearchAction.do";
	    document.forms[1].submit();
	}//end of PressForward()

	//function to search
	function onSearch()
	{
		if(!JS_SecondSubmit)
 		{
	    	trimForm(document.forms[1]);
	    	if(isValidated())
			{
				JS_SecondSubmit=true;
				document.forms[1].mode.value = "doSearch";
		    	document.forms[1].action = "/AuditSearchAction.do";
		    	document.forms[1].submit();
		    }
		}//end of if(!JS_SecondSubmit)
	}//end of onSearch()

	function edit(rownum)
	{
	    document.forms[1].rownum.value=rownum;
   	    document.forms[1].tab.value="General";
	    document.forms[1].mode.value="doViewQuality";
	    document.forms[1].action = "/QualityDetailsAction.do";
	    document.forms[1].submit();
	}//end of edit(rownum)

function isValidated()
{
		//checks if Marked Date is entered
		if(document.forms[1].markedDate.value.length!=0)
		{
			if(isDate(document.forms[1].markedDate,"Marked Date")==false)
				return false;
				document.forms[1].markedDate.focus();
		}// end of if(document.forms[1].markedDate.value.length!=0)
		return true;
}//end of isValidated()