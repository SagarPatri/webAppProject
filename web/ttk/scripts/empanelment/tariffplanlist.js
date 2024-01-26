//java script for the tariff plan list screen in the empanelment of hospital flow.

function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AssociatePlanAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/AssociatePlanAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AssociatePlanAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AssociatePlanAction.do";
    document.forms[1].submit();
}//end of PressForward()
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		document.forms[1].planname.value=trim(document.forms[1].planname.value);
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/SearchAssociatePlanAction.do ";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
function onClose()
{
	if(!TrackChanges()) return false;

    	document.forms[1].mode.value="doViewPlan";
    	document.forms[1].child.value="Add/Edit Tariff Plan";
    	document.forms[1].action="/HospitalTariffRevisePlanAction.do";
    	document.forms[1].submit();
}// end of onClose()
function edit(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value="doViewPlan";
	document.forms[1].child.value="Add/Edit Tariff Plan";
	document.forms[1].action="/AssociatePlanAction.do";
	document.forms[1].submit();
}// end of edit(rownum)