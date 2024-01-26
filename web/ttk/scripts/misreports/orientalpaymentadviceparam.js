

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/MISPaymentAdviceAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/MISPaymentAdviceAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/MISPaymentAdviceAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/MISPaymentAdviceAction.do";
    document.forms[1].submit();
}//end of PressBackWard()




function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/MISPaymentAdviceAction.do";
	document.forms[1].submit();
}//end of onClose()

//functin to search
	function onSearch()
	{
	   if(!JS_SecondSubmit)
       {
		if(isValidated()==true)
		{
			trimForm(document.forms[1]);
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/MISPaymentAdviceAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
	    }//end of if(isValidated()==true)
	   }//end of if(!JS_SecondSubmit)
	 }//end of onSearch()
//functin to search

function isValidated()
	{
		//checks if Batch Date is entered
		if(document.forms[1].sBatchDate.value.length!=0)
		{
			if(isDate(document.forms[1].sBatchDate,"Batch Date")==false)
			{
				return false;
				document.forms[1].sBatchDate.focus();
			}//end of if(isDate(document.forms[1].sBatchDate,"Batch Date")==false)
		}// end of if(document.forms[1].sBatchDate.value.length!=0)
		return true;
	}//end of function isValidated()
function edit(rownum)
{
		document.forms[1].mode.value="doGenerateReport";
		document.forms[1].rownum.value=rownum;
		var openPage = "/MISPaymentAdviceAction.do?mode=doGenerateReport&rownum="+rownum;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 99;
	   var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}//end of function edit(rownum)
	
	