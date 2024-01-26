//java script for the transaction list screen in the finance flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TransactionSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/TransactionSearchAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TransactionSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TransactionSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
  if(!JS_SecondSubmit)
 {
	if(isValidated())
    {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/TransactionSearchAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
	}
}//end of onSearch()

function onReverse()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to reverse the transaction(s) ?");
		if(msg)
		{
			document.forms[1].mode.value="doReverse";
	    	document.forms[1].action = "/ReverseTransactionAction.do";
	    	document.forms[1].submit();
	    }// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onReverse()

function isValidated()
{
		//checks if From Date is entered
		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"From Date")==false)
				return false;
				document.forms[1].startDate.focus();
		}// end of if(document.forms[1].startDate.value.length!=0)

		//checks if To Date is entered
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
}//end of isValidated()

// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].rownum.value=rownum;
    document.forms[1].mode.value="doViewTransaction";
    document.forms[1].child.value="Transaction Details";
    document.forms[1].action = "/TransactionDetailsAction.do";
    document.forms[1].submit();
}// End of edit()

function onAdd()
{
	document.forms[1].mode.value="doAdd";
    document.forms[1].action = "/TransactionDetailsAction.do";
    document.forms[1].submit();
}//end of onAdd()