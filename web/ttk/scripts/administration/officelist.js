//java script for the office list screen in the administration of products flow.

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OfficeAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{   document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/OfficeAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OfficeAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OfficeAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

// function to search
function onSearch()
{   
if(!JS_SecondSubmit)
 {
	document.forms[1].sOfficeCode.value=trim(document.forms[1].sOfficeCode.value);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/OfficeAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{   
	if(document.forms[0].tab.value=='Card Rules')	
	{
		document.forms[1].child.value="Define Card Rules";
	}//end of if(document.forms[0].tab.value=='Card Rules')	
	if(document.forms[0].tab.value=='Circulars')	
	{
		document.forms[1].child.value="Circulars List";
	}//end of if(document.forms[0].tab.value=='Circulars')	
	document.forms[1].reset();
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value = "doDefineCardRules";
	document.forms[1].action="/OfficeAction.do";
	document.forms[1].submit();
}
// End of edit()