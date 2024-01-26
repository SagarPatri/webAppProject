//java script for the insurance company list screen in the empanelment of insurance flow.

//function to sort based on the link selected
	function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/EmpInsuranceAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

	function edit(rownum)
	{
	    document.forms[1].mode.value="doViewCompanySummary";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].tab.value="Company Details";
	    document.forms[1].action = "/EmpInsuranceAction.do";
	    document.forms[1].submit();
	}//end of edit(rownum)

	//function to display the selected page
	function pageIndex(pagenumber)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/EmpInsuranceAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doBackward";
	    document.forms[1].action = "/EmpInsuranceAction.do";
	    document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/EmpInsuranceAction.do";
	    document.forms[1].submit();
	}//end of PressForward()

	function onSearch()
	{
		if(!JS_SecondSubmit)
		 {
			document.forms[1].sEmpanelDate.value=trim(document.forms[1].sEmpanelDate.value);
			document.forms[1].sCompanyName.value=trim(document.forms[1].sCompanyName.value);
			if(isValidated())
			{
				document.forms[1].mode.value = "doSearch";
				document.forms[1].action = "/EmpInsuranceAction.do";
				JS_SecondSubmit=true
				document.forms[1].submit();
			}//end of if(isValidated())
		 }//end of if(!JS_SecondSubmit)
	}//end of onSearch()

	function isValidated()
	{
		if(trim(document.forms[1].sEmpanelDate.value).length>0)
		{
			if(isDate(document.forms[1].sEmpanelDate,"Empanel. Date")==false)
			{
				document.forms[1].sEmpanelDate.focus();
				return false;
			}//end of if
		}//end of if(trim(document.forms[1].sEmpanelDate.value).length>0)
		return true;
	}//end of isValidated()

	function copyToWebBoard()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		    document.forms[1].mode.value = "doCopyToWebBoard";
	   		document.forms[1].action = "/EmpInsuranceAction.do";
		    document.forms[1].submit();
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of copyToWebBoard()

	function onDelete()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/EmpInsuranceAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of onDelete()

	function onAddInsCompany()
	{
		document.forms[1].tab.value="Company Details";
		document.forms[1].mode.value ="doAdd";
		document.forms[1].action ="/AddEditInsCompanyAction.do";
		document.forms[1].submit();
	}//end of function onAddInsCompany()