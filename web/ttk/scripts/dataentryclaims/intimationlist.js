//functions for intimationlist screen of claims flow.
function onSearch()
{
  if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
	if(isValidated())
    {
		document.forms[1].mode.value="doSearch";
	    document.forms[1].action="/IntimationsAction.do";
	    JS_SecondSubmit=true
		document.forms[1].submit();
	}
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onCloseList()
{
	document.forms[1].child.value="";
	document.forms[1].mode.value="doClose";
    document.forms[1].action="/IntimationsAction.do";
	document.forms[1].submit();
}//end of onCloseList()

function edit(rownum)
{
	document.forms[1].child.value="";
	document.forms[1].mode.value="doClose";
	document.forms[1].rownum.value=rownum;
    document.forms[1].action="/IntimationsAction.do";
	document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/IntimationsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//koc 1339 for mail
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/IntimationsAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//koc 1339 for mail
//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/IntimationsAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/IntimationsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"Start Date")==false)
				return false;
				document.forms[1].startDate.focus();
		}// end of if(document.forms[1].startDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].endDate.value.length!=0)
		{
			if(isDate(document.forms[1].endDate,"End Date")==false)
				return false;
				document.forms[1].endDate.focus();
		}// end of if(document.forms[1].endDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		{
			if(compareDates("startDate","Start Date","endDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		return true;
}//end of isValidated()