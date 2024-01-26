//javascript used in courierlist.jsp of Support flow
function edit(rownum)
{
    document.forms[1].mode.value="doViewCourier";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/CourierDetailAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
function onAdd(obj)
{
	var paramType =obj;
    document.forms[1].reset();
    document.forms[1].paramType.value = paramType;
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="General";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/CourierDetailAction.do";
    document.forms[1].submit();
}//end of onAdd()
//function to sort based on the link selected
	function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/CourierSearchAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

	//function to display the selected page
	function pageIndex(pagenumber)
	{
	    document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/CourierSearchAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doBackward";
	    document.forms[1].action = "/CourierSearchAction.do";
	    document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/CourierSearchAction.do";
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
				document.forms[1].action = "/CourierSearchAction.do";
		    	document.forms[1].submit();
		    }
		}//end of if(!JS_SecondSubmit)
	}//end of onSearch()

	function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].sStartDare.value.length!=0)
		{
			if(isDate(document.forms[1].sStartDare,"Start Date")==false)
				return false;
		}// end of if(document.forms[1].sStartDare.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].sEndDate.value.length!=0)
		{
			if(isDate(document.forms[1].sEndDate,"End Date")==false)
				return false;
		}// end of if(document.forms[1].sEndDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].sStartDare.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		{
			if(compareDates("sStartDare","Start Date","sEndDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].sStartDare.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		return true;
}// end of isValidated(

	function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/CourierSearchAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()
	
	
//added  for 2koc courier
	function onReportLink()
	{
		document.forms[1].mode.value="doViewCourierList";
	    document.forms[1].action="/CourierSearchAction.do";
		document.forms[1].submit();
		
		//document.forms[1].submit();
	}//end of onReportLink

	//end added  for 2koc courier