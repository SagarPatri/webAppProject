//java script for the tariff revision plan screen in the administration of tariff plan flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/TariffRevisePlanAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of PressForward()

function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function onSearch()
{
	if(isValidated())
	{
		if(!JS_SecondSubmit)
 		{
   			document.forms[1].mode.value = "doSearch";
	 		document.forms[1].action = "/TariffRevisePlanAction.do";
			JS_SecondSubmit=true
		    document.forms[1].submit();
		 }//end of if(!JS_SecondSubmit)
    }
}//end of onSearch()

function isValidated()
{
	document.forms[1].sStartDate.value=trim(document.forms[1].sStartDate.value);
	document.forms[1].sEndDate.value=trim(document.forms[1].sEndDate.value);
	if(document.forms[1].sStartDate.value.length!=0)
	{
		//checks for the validation of Start Date
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
			return false;
	}
	if(document.forms[1].sEndDate.value.length!=0)
	{
		//checks for the validation of end date
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
			return false;
	}
	if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		//checks if both the dates are entered
		if(compareDates("sStartDate","Start Date","sEndDate","End Date",'greater than')==false)
			return false;
	}
	return true;
}//end of isValidated()
// onClose() JavaScript function
function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doSearch";
	document.forms[1].child.value="";
    document.forms[1].action = "/TariffPlanAction.do?Entry=Y";
    document.forms[1].submit();
}//end of onClose()

function onRevise()
{
	document.forms[1].mode.value="doRevise";
	document.forms[1].child.value="AddRevisePlan";
    document.forms[1].action = "/AddTariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of function onRevise()

function edit(rownum)
{
	document.forms[1].mode.value="doPlanPackage";
	document.forms[1].child.value="PlanPackage";
	document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/TariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of function edit(rownum)