//java script for the tariff details screen in the empanelment of hospital flow.

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/TariffAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onHistoryIcon(rownum)
{
	document.forms[1].reset();
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value ="doViewRevisionHistory";
	document.forms[1].child.value="RevisePlan";
    document.forms[1].action = "/TariffAction.do";
	document.forms[1].submit();
}//end of function onHistoryIcon(rownum)