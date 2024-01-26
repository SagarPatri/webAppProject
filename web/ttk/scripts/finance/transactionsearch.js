function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/FloatTransactionAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

	//function to display the selected page
	function pageIndex(pagenumber)
	{
	    document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/FloatTransactionAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doBackward";
	    document.forms[1].action = "/FloatTransactionAction.do";
	    document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/FloatTransactionAction.do";
	    document.forms[1].submit();
	}//end of PressForward()

	//functin to search
	function onSearch()
	{
	  if(!JS_SecondSubmit)
      {
		trimForm(document.forms[1]);
    	if(isValidated())
		{
			document.forms[1].mode.value = "doSearch";
		    document.forms[1].action = "/FloatTransactionAction.do";
		    JS_SecondSubmit=true
		    document.forms[1].submit();
	    }
	 }//end of if(!JS_SecondSubmit)
	}//end of onSearch()

	function isValidated()
	{
		//checks if start date is entered
		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"From Date")==false)
				return false;
				document.forms[1].startDate.focus();
		}// end of if(document.forms[1].startDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].endDate.value.length!=0)
		{
			if(isDate(document.forms[1].endDate,"To Date")==false)
				return false;
				document.forms[1].endDate.focus();
		}// end of if(document.forms[1].endDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		{
			if(compareDates("startDate","From Date","endDate","To Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		return true;
	}// end of isValidated()
	//function to add
	function addTransactions()
	{
		document.forms[1].reset();
	    document.forms[1].rownum.value = "";
	    document.forms[1].mode.value = "doAdd";
	    document.forms[1].action = "/FloatTransactionDetailsAction.do";
	    document.forms[1].submit();
	}//end of addTransactions()

	// JavaScript edit() function
	function edit(rownum)
	{
		document.forms[1].rownum.value=rownum;
	    document.forms[1].mode.value="doViewTransaction";
	    document.forms[1].action="/FloatTransactionDetailsAction.do";
	    document.forms[1].submit();
	}// End of edit()

	function reverseTransactions()
	{
		if(!mChkboxValidation(document.forms[1]))
    	{
			var msg = confirm("Are you sure you want to reverse the transaction(s) ?");
			if(msg)
			{
				document.forms[1].mode.value = "doReverseTransaction";
		    	document.forms[1].action = "/TransactionReverseAction.do";
		    	document.forms[1].submit();
		    }// end of if(msg)
		}//end of if(!mChkboxValidation(document.forms[1]))
	}//end of reverseTransactions()