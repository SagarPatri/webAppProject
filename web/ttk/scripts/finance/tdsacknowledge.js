//javascript used in tdsacknowledge.jsp of Finance TDS flow

//function to sort based on the link selected
	function onAddAcknowledge()
	{
		document.forms[1].finYearTo.value="";
	    document.forms[1].mode.value="doAcknowledgementDetails";
	    document.forms[1].action = "/TDSAcknowledgeAction.do";
	    document.forms[1].submit();
	}//end of onAdd()
	//function for sorting.
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TDSAcknowledgeAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
	
	function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TDSAcknowledgeAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//functin to search
function onSearch()
{
	if (!JS_SecondSubmit) {
		trimForm(document.forms[1]);
		if(parseInt(document.forms[1].financeYear.value) > 2008)
		{
			document.forms[1].mode.value = "doSearch";
			document.forms[1].action = "/TDSAcknowledgeAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(parseInt(document.forms[1].financeYear.value) > 2008)
		else
		{
			alert('Please enter valid Financial Year');
			document.forms[1].financeYear.focus();
			document.forms[1].financeYear.select();
			return false;
		}//end of else	
			
	}//end of if (!JS_SecondSubmit)	  		
}//end of onSearch()