//javascript used in monthlyremitlist.jsp of Finance->TDS Configuration

//functin to search
function onSearch()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
			if(parseInt(document.forms[1].sYear.value) > 2008)
			{
				document.forms[1].mode.value = "doSearch";
				document.forms[1].action = "/MonthlyRemitAction.do";
				JS_SecondSubmit=true
				document.forms[1].submit();
			}//end of if(parseInt(document.forms[1].financialYear.value) > 2008)
			else
			{
				alert('Please enter valid Year');
				document.forms[1].sYear.focus();
				document.forms[1].sYear.select();
				return false;
			}//end of else		
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to Generate Daily Transfer
function generateMonthlyRemit()
{
	document.forms[1].mode.value = "doGenerateMonthlyRemit";
	document.forms[1].action = "/MonthlyRemitAction.do";	
	document.forms[1].submit();
}//end of function generateMonthlyRemit()

//function for sorting.
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/MonthlyRemitAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/MonthlyRemitAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/MonthlyRemitAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/MonthlyRemitAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display the details.
function onAddIcon(rownum)
{
	document.forms[1].mode.value="doView";
	document.forms[1].rownum.value=rownum;	
	document.forms[1].action="/ViewMonthlyRemitAction.do";
	document.forms[1].submit();	 
}//end of onAddIcon()