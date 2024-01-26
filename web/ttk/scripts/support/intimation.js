//java script for the Investigation screen in the preauth and support of Preauthorization flow.

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/IntimationAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/IntimationAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/IntimationAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/IntimationAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
   trimForm(document.forms[1]);
    if(isValidated())
    {
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/IntimationAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(isValidated())
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function edit(rownum)
{
    document.forms[1].mode.value="doViewIntimationDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/EditIntimationDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function isValidated()
{
		//checks if Responded Date is entered
		if(document.forms[1].RespondedDate.value.length!=0)
		{
			if(isDate(document.forms[1].RespondedDate,"Intimation Received Before")==false)
				return false;
				document.forms[1].RespondedDate.focus();
		}// end of if(document.forms[1].RespondedDate.value.length!=0)
		//checks if ReceivedAfter Date is entered
		if(document.forms[1].ReceivedAfter.value.length!=0)
		{
			if(isDate(document.forms[1].ReceivedAfter,"Intimation Received After")==false)
				return false;
				document.forms[1].ReceivedAfter.focus();
		}// end of if(document.forms[1].ReceivedAfter.value.length!=0)
		return true;
}//end of isValidated()