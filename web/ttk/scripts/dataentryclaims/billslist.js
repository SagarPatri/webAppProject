//java script for the ListofBills screen

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ListofBillsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/ListofBillsAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ListofBillsAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ListofBillsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function isValidated()
{
	if(trim(document.forms[1].sBillAmount.value).length>0)
		{
			regexp1=/^\d*$/;
			if(regexp1.test(trim(document.forms[1].sBillAmount.value))==false)
			{
				alert("Allowed Amt. should be a numeric value");
				document.forms[1].sBillAmount.focus();
				document.forms[1].sBillAmount.select();
				return false;
			}//end of if(regexp1.test(trim(document.forms[1].sBillAmount.value))==false)
		}//end of if(trim(document.forms[1].sBillAmount.value).length>0)

	//checks if Bill Date is entered
	if(document.forms[1].sBillDate.value.length!=0)
	{
		if(isDate(document.forms[1].sBillDate,"Bill Date")==false)
		{
			return false;
			document.forms[1].sBillDate.focus();
		}//end of if(isDate(document.forms[1].sBillDate,"Bill Date")==false)
	}// end of if(document.forms[1].sBillDate.value.length!=0)
	return true;
}//end of function isValidated()

// function to search
function onSearch()
{
  if(!JS_SecondSubmit)
 {

	if(isValidated()==true)
	{
		trimForm(document.forms[1]);
    	document.forms[1].mode.value = "doSearch";
    	document.forms[1].action = "/ListofBillsAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(isValidated()==true)
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DeleteBillsAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].child.value="LineItems";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AddEditBillDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onAdd()
{
	document.forms[1].mode.value = "doAdd";
	document.forms[1].rownum.value="";
	document.forms[1].action = "/AddEditBillDetailsAction.do";
	document.forms[1].submit();
}

function onViewBillsSummary()
{
	document.forms[1].child.value="Bills Summary";
	document.forms[1].mode.value = "doViewBillSummary";
	document.forms[1].action = "/ListofBillsAction.do";
	document.forms[1].submit();
}

function onListItems(rownum)
{
	document.forms[1].mode.value = "doSearch";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action = "/LineItemsAction.do";
	document.forms[1].submit();
}

// JavaScript onInclude() function
function onInclude()
{
	if(!mChkboxValidation(document.forms[1]))
    { // document.forms[1].reset();
	   document.forms[1].mode.value = "doInclude";
   	   document.forms[1].action = "/ChangeBillStatusAction.do";
	   document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//End of onInclude()

// JavaScript onExclude() function
function onExclude()
{
	if(!mChkboxValidation(document.forms[1]))
    {
	   //document.forms[1].reset();
	   document.forms[1].mode.value = "doExclude";
   	   document.forms[1].action = "/ChangeBillStatusAction.do";
	   document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//End of onExclude()
